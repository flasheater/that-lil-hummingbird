package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.SearchBundle;

import butterknife.ButterKnife;

public class AnimeResultItemView extends CardView implements AdapterView<SearchBundle.AnimeResult>,
        View.OnClickListener {

    public AnimeResultItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeResultItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(final View v) {
        // TODO
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void setContent(final SearchBundle.AnimeResult content) {
        // TODO
    }

}