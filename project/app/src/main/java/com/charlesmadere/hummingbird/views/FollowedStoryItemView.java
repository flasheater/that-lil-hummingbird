package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.AbsUser;
import com.charlesmadere.hummingbird.models.FollowedStory;
import com.charlesmadere.hummingbird.models.FollowedSubstory;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowedStoryItemView extends CardView implements AdapterView<FollowedStory> {

    private NumberFormat mNumberFormat;

    @BindView(R.id.fsivZero)
    FollowedSubstoryItemView mFollowedZero;

    @BindView(R.id.fsivOne)
    FollowedSubstoryItemView mFollowedOne;

    @BindView(R.id.fsivTwo)
    FollowedSubstoryItemView mFollowedTwo;

    @BindView(R.id.sdvAvatar)
    SimpleDraweeView mAvatar;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public FollowedStoryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FollowedStoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
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

    @Override
    public void setContent(final FollowedStory content) {
        final AbsUser user = content.getUser();
        mAvatar.setImageURI(Uri.parse(user.getAvatar()));

        final Resources res = getResources();
        mTitle.setText(res.getString(R.string.x_y, user.getName(),
                res.getQuantityString(R.plurals.followed_x_users, content.getSubstoryCount(),
                        mNumberFormat.format(content.getSubstoryCount()))));
        mTimeAgo.setText(content.getCreatedAt().getRelativeTimeText(getContext()));

        final ArrayList<AbsSubstory> substories = content.getSubstories();
        mFollowedZero.setContent((FollowedSubstory) substories.get(0));

        if (substories.size() >= 2) {
            mFollowedOne.setContent((FollowedSubstory) substories.get(1));
            mFollowedOne.setVisibility(VISIBLE);
        } else {
            mFollowedOne.setVisibility(GONE);
        }

        if (substories.size() >= 3) {
            mFollowedTwo.setContent((FollowedSubstory) substories.get(2));
            mFollowedTwo.setVisibility(VISIBLE);
        } else {
            mFollowedTwo.setVisibility(GONE);
        }
    }

}