package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.FeedFragmentAdapter;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;

public class FeedActivity extends BaseUserActivity {

    private static final String TAG = "FeedActivity";


    public static Intent getLaunchIntent(final Context context) {
        return createDrawerActivityIntent(context, FeedActivity.class);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_feed;
    }

    @Override
    protected NavigationDrawerItemView.Entry getSelectedNavigationDrawerItemViewEntry() {
        return NavigationDrawerItemView.Entry.FEED;
    }

    @Override
    public UserDigest getUserDigest() {
        return CurrentUser.get();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAdapter(new FeedFragmentAdapter(this));
    }

    @Override
    public void onFeedPostSubmit() {
        // TODO
    }

}