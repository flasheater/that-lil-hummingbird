package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;

import com.charlesmadere.hummingbird.adapters.MangaLibraryFragmentAdapter;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.models.LaunchScreen;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;

public class CurrentUserMangaLibraryActivity extends BaseMangaLibraryActivity {

    private static final String TAG = "CurrentUserMangaLibraryActivity";


    public static Intent getLaunchIntent(final Context context) {
        final Intent intent = createDrawerActivityIntent(context, CurrentUserMangaLibraryActivity.class);

        if (Preferences.General.DefaultLaunchScreen.get() == LaunchScreen.MANGA_LIBRARY) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        return intent;
    }

    @Override
    protected MangaLibraryFragmentAdapter createAdapter() {
        return new MangaLibraryFragmentAdapter(this, CurrentUser.get().getUserId(), true);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected NavigationDrawerItemView.Entry getSelectedNavigationDrawerItemViewEntry() {
        return NavigationDrawerItemView.Entry.MANGA_LIBRARY;
    }

}
