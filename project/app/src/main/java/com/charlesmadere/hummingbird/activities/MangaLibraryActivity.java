package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.MangaLibraryFragmentAdapter;
import com.charlesmadere.hummingbird.misc.CurrentUser;

import butterknife.BindView;

public class MangaLibraryActivity extends BaseDrawerActivity {

    private static final String TAG = "MangaLibraryActivity";
    private static final String CNAME = MangaLibraryActivity.class.getCanonicalName();
    private static final String EXTRA_USERNAME = CNAME + ".Username";

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    public static Intent getLaunchIntent(final Context context, final String username) {
        return new Intent(context, MangaLibraryActivity.class)
                .putExtra(EXTRA_USERNAME, username);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_library);

        final Intent intent = getIntent();
        final String username = intent.getStringExtra(EXTRA_USERNAME);
        getSupportActionBar().setSubtitle(username);

        mViewPager.setAdapter(new MangaLibraryFragmentAdapter(this, username,
                CurrentUser.get().getUserId().equalsIgnoreCase(username)));
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
    }

}