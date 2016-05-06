package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.SearchBundle;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeResultItemView extends CardView implements AdapterView<SearchBundle.AnimeResult>,
        View.OnClickListener {

    @BindView(R.id.sdvPoster)
    SimpleDraweeView mPoster;

    @BindView(R.id.tvDescription)
    TextView mDescription;

    @BindView(R.id.tvTitle)
    TextView mTitle;


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
        mPoster.setImageURI(Uri.parse(content.getImage()));
        mTitle.setText(content.getTitle());

        if (content.hasDescription()) {
            mDescription.setText(content.getDescription());
            mDescription.setVisibility(VISIBLE);
        } else {
            mDescription.setVisibility(GONE);
        }
    }

}
