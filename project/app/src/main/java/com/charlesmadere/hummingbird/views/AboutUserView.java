package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.models.UserV2;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutUserView extends CardView implements AdapterView<UserDigest> {

    private NumberFormat mNumberFormat;
    private UserDigest mUserDigest;

    @BindView(R.id.kvtvFollowers)
    KeyValueTextView mFollowers;

    @BindView(R.id.kvtvFollowing)
    KeyValueTextView mFollowing;

    @BindView(R.id.kvtvLivesIn)
    KeyValueTextView mLivesIn;

    @BindView(R.id.kvtvWaifuOrHusbando)
    KeyValueTextView mWaifuOrHusbando;

    @BindView(R.id.tvAbout)
    TextView mAbout;

    @BindView(R.id.tvTitle)
    TextView mTitle;

    @BindView(R.id.tvWebsite)
    TextView mWebsite;


    public AboutUserView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AboutUserView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    @OnClick(R.id.kvtvFollowers)
    void onFollowersClick() {
        // TODO
    }

    @OnClick(R.id.kvtvFollowing)
    void onFollowingClick() {
        // TODO
    }

    @OnClick(R.id.kvtvWaifuOrHusbando)
    void onWaifuOrHusbandoClick() {
        // TODO
    }

    @OnClick(R.id.tvWebsite)
    void onWebsiteClick() {
        final Context context = getContext();
        context.startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(mUserDigest.getUser().getWebsite())));
    }

    @Override
    public void setContent(final UserDigest content) {
        mUserDigest = content;

        final UserV2 user = mUserDigest.getUser();
        final Resources res = getResources();
        mTitle.setText(res.getString(R.string.about_x, user.getName()));

        if (user.hasAbout()) {
            mAbout.setText(user.getAbout());
            mAbout.setVisibility(VISIBLE);
        } else {
            mAbout.setVisibility(GONE);
        }

        if (user.hasWaifuOrHusbando()) {
            mWaifuOrHusbando.setText(res.getText(user.getWaifuOrHusbando().getExtendedTextResId()),
                    user.getWaifu());
            mWaifuOrHusbando.setVisibility(VISIBLE);
        } else {
            mWaifuOrHusbando.setVisibility(GONE);
        }

        if (user.hasLocation()) {
            mLivesIn.setText(res.getText(R.string.lives_in), user.getLocation());
            mLivesIn.setVisibility(VISIBLE);
        } else {
            mLivesIn.setVisibility(GONE);
        }

        if (user.hasWebsite()) {
            mWebsite.setText(user.getWebsite());
            mWebsite.setVisibility(VISIBLE);
        } else {
            mWebsite.setVisibility(GONE);
        }

        mFollowers.setText(res.getQuantityText(R.plurals.followers, user.getFollowerCount()),
                mNumberFormat.format(user.getFollowerCount()));
        mFollowing.setText(res.getText(R.string.following),
                mNumberFormat.format(user.getFollowingCount()));
    }

}
