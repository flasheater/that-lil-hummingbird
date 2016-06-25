package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.charlesmadere.hummingbird.R;

import butterknife.BindView;
import butterknife.OnClick;

public class WatchStatusUpdateFragment extends BaseBottomSheetDialogFragment {

    private static final String TAG = "WatchStatusUpdateFragment";

    @BindView(R.id.ibSave)
    ImageButton mSave;


    public static WatchStatusUpdateFragment create() {
        final Bundle args = new Bundle();

        final WatchStatusUpdateFragment fragment = new WatchStatusUpdateFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @OnClick(R.id.ibClose)
    void onCloseClick() {
        dismiss();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_watch_status_update, container, false);
    }

    @OnClick(R.id.ibSave)
    void onSaveClick() {

    }

}