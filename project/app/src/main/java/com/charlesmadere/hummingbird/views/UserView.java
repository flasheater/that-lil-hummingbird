package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.UserActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserView extends CardView implements AdapterView<User>, View.OnClickListener {

    private User mUser;

    @BindView(R.id.avatarView)
    AvatarView mAvatar;

    @BindView(R.id.tvBio)
    TextView mBio;

    @BindView(R.id.tvTitle)
    TextView mTitle;

    @BindView(R.id.proBadge)
    View mProBadge;


    public UserView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public UserView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(final View v) {
        final Context context = getContext();
        context.startActivity(UserActivity.getLaunchIntent(context, mUser));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void setContent(final User content) {
        mUser = content;

        mAvatar.setContent(mUser);
        mProBadge.setVisibility(mUser.isPro() ? VISIBLE : GONE);
        mTitle.setText(mUser.getId());

        if (mUser.hasBio()) {
            mBio.setText(mUser.getBio());
            mBio.setVisibility(VISIBLE);
        } else {
            mBio.setVisibility(GONE);
        }
    }

}
