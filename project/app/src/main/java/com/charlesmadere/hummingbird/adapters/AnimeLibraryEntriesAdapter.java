package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeLibraryEntry;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.LibrarySort;
import com.charlesmadere.hummingbird.views.AnimeLibraryEntryItemView;

import java.util.ArrayList;
import java.util.Collections;

public class AnimeLibraryEntriesAdapter extends BaseAdapter<AnimeLibraryEntry> {

    private final AnimeLibraryEntryItemView.Listeners mListeners;


    public AnimeLibraryEntriesAdapter(final Context context) {
        this(context, null);
    }

    public AnimeLibraryEntriesAdapter(final Context context,
            @Nullable final AnimeLibraryEntryItemView.Listeners listeners) {
        super(context);
        mListeners = listeners;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(final int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_anime_library_entry;
    }

    @Override
    public AdapterView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final AdapterView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        final AnimeLibraryEntryItemView view = (AnimeLibraryEntryItemView) viewHolder.itemView;
        view.setListeners(mListeners);
        return viewHolder;
    }

    public void set(final Feed feed, final LibrarySort sort) {
        if (feed == null || !feed.hasAnimeLibraryEntries()) {
            super.set(null);
            return;
        }

        final ArrayList<AnimeLibraryEntry> entries = new ArrayList<>(feed.getAnimeLibraryEntries());

        switch (sort) {
            case DATE:
                Collections.sort(entries, AnimeLibraryEntry.DATE);
                break;

            case RATING:
                Collections.sort(entries, AnimeLibraryEntry.RATING);
                break;

            case TITLE:
                Collections.sort(entries, AnimeLibraryEntry.TITLE);
                break;

            default:
                throw new IllegalArgumentException("encountered illegal " +
                        LibrarySort.class.getName() + ": \"" + sort + '"');
        }

        super.set(entries);
    }

}
