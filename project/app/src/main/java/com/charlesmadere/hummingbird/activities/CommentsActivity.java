package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.CommentsAdapter;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.CommentPost;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

public class CommentsActivity extends BaseDrawerActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "CommentsActivity";
    private static final String CNAME = CommentsActivity.class.getCanonicalName();
    private static final String EXTRA_COMMENT_STORY = CNAME + ".CommentStory";
    private static final String KEY_FEED = "Feed";

    private CommentsAdapter mAdapter;
    private CommentStory mCommentStory;
    private Feed mFeed;

    @BindView(R.id.etComment)
    EditText mCommentField;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context, final CommentStory commentStory) {
        return new Intent(context, CommentsActivity.class)
                .putExtra(EXTRA_COMMENT_STORY, commentStory);
    }

    public void fetchSubstories() {
        mRefreshLayout.setRefreshing(true);
        Api.getSubstories(mCommentStory.getId(), new GetSubstoriesListener(this));
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    private boolean isCommentFormValid() {
        final CharSequence text = mCommentField.getText();
        return !TextUtils.isEmpty(text) && TextUtils.getTrimmedLength(text) >= 1;
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @OnEditorAction(R.id.etComment)
    boolean onCommentFieldEditorAction(final int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            postComment();
        }

        return false;
    }

    @OnTextChanged(R.id.etComment)
    void onCommentFieldTextChanged() {
        supportInvalidateOptionsMenu();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        final Intent intent = getIntent();
        mCommentStory = intent.getParcelableExtra(EXTRA_COMMENT_STORY);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mFeed = savedInstanceState.getParcelable(KEY_FEED);
        }

        if (mFeed == null) {
            fetchSubstories();
        } else {
            showFeed(mFeed);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_comments, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDrawerOpened() {
        super.onDrawerOpened();
        MiscUtils.closeKeyboard(this);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miPostComment:
                postComment();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        menu.findItem(R.id.miPostComment).setEnabled(isCommentFormValid());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onRefresh() {
        fetchSubstories();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFeed != null) {
            outState.putParcelable(KEY_FEED, mFeed);
        }
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        mAdapter = new CommentsAdapter(this);
    }

    private void postComment() {
        if (!isCommentFormValid()) {
            return;
        }

        MiscUtils.closeKeyboard(this);
        mRefreshLayout.setRefreshing(true);
        mCommentField.setEnabled(false);

        final String comment = mCommentField.getText().toString().trim();
        final CommentPost commentPost = new CommentPost(comment, mCommentStory.getId());
        Api.postComment(commentPost, new PostCommentListener(this));
    }

    private void showCommentError() {
        mCommentField.setEnabled(true);
        mRefreshLayout.setRefreshing(false);
        Toast.makeText(this, R.string.error_posting_comment, Toast.LENGTH_LONG).show();
    }

    private void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mCommentField.setEnabled(false);
        mRefreshLayout.setRefreshing(false);
    }

    private void showFeed(final Feed feed) {
        mFeed = feed;
        mAdapter.set(mCommentStory, feed);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mCommentField.setEnabled(true);
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetSubstoriesListener implements ApiResponse<Feed> {
        private final WeakReference<CommentsActivity> mActivityReference;

        private GetSubstoriesListener(final CommentsActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final CommentsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final CommentsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showFeed(feed);
            }
        }
    }

    private static class PostCommentListener implements ApiResponse<Void> {
        private final WeakReference<CommentsActivity> mActivityReference;

        private PostCommentListener(final CommentsActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final CommentsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showCommentError();
            }
        }

        @Override
        public void success(@Nullable final Void object) {
            final CommentsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.fetchSubstories();
            }
        }
    }

}