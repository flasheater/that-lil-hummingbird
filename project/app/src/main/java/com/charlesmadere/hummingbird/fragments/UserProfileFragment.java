package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.Space;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.AboutUserView;
import com.charlesmadere.hummingbird.views.AnimeBreakdownView;
import com.charlesmadere.hummingbird.views.FavoriteAnimeView;
import com.charlesmadere.hummingbird.views.FavoriteMangaView;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.UserBioView;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class UserProfileFragment extends BaseFragment implements ObjectCache.KeyProvider,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "UserDigestFragment";
    private static final String KEY_USERNAME = "Username";

    private String mUsername;
    private UserDigest mUserDigest;

    @BindView(R.id.aboutUserView)
    AboutUserView mAboutUserView;

    @BindView(R.id.animeBreakdownView)
    AnimeBreakdownView mAnimeBreakdownView;

    @BindView(R.id.favoriteAnimeView)
    FavoriteAnimeView mFavoriteAnimeView;

    @BindView(R.id.favoriteMangaView)
    FavoriteMangaView mFavoriteMangaView;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.nestedScrollView)
    NestedScrollView mNestedScrollView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;

    @BindView(R.id.animeBreakdownSpace)
    Space mAnimeBreakdownSpace;

    @BindView(R.id.userBioView)
    UserBioView mUserBioView;


    public static UserProfileFragment create() {
        return new UserProfileFragment();
    }

    public static UserProfileFragment create(final String username) {
        final Bundle args = new Bundle(1);
        args.putString(KEY_USERNAME, username);

        final UserProfileFragment fragment = new UserProfileFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void fetchUserDigest() {
        mRefreshLayout.setRefreshing(true);
        Api.getUserDigest(mUsername, new GetUserDigestListener(this));
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getFragmentName(), mUsername };
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        if (args == null || args.isEmpty()) {
            mUsername = CurrentUser.get().getUserId();
            mUserDigest = CurrentUser.get();
        } else {
            mUsername = args.getString(KEY_USERNAME);
            mUserDigest = ObjectCache.get(this);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onRefresh() {
        fetchUserDigest();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mUserDigest != null) {
            ObjectCache.put(mUserDigest, this);
        }
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRefreshLayout.setOnRefreshListener(this);

        if (mUserDigest == null) {
            fetchUserDigest();
        } else {
            showUserDigest(mUserDigest);
        }
    }

    private void showError() {
        mNestedScrollView.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    private void showUserDigest(final UserDigest userDigest) {
        mUserDigest = userDigest;

        if (userDigest.equals(CurrentUser.get())) {
            CurrentUser.set(userDigest);
        }

        mAboutUserView.setContent(userDigest);

        if (userDigest.getInfo().hasTopGenres()) {
            mAnimeBreakdownView.setContent(userDigest);
            mAnimeBreakdownView.setVisibility(View.VISIBLE);
            mAnimeBreakdownSpace.setVisibility(View.VISIBLE);
        } else {
            mAnimeBreakdownView.setVisibility(View.GONE);
            mAnimeBreakdownSpace.setVisibility(View.GONE);
        }

        mUserBioView.setContent(userDigest);
        mFavoriteAnimeView.setContent(userDigest);
        mFavoriteMangaView.setContent(userDigest);

        mError.setVisibility(View.GONE);
        mNestedScrollView.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetUserDigestListener implements ApiResponse<UserDigest> {
        private final WeakReference<UserProfileFragment> mFragmentReference;

        private GetUserDigestListener(final UserProfileFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final UserProfileFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.showError();
            }
        }

        @Override
        public void success(final UserDigest userDigest) {
            final UserProfileFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.showUserDigest(userDigest);
            }
        }
    }

}
