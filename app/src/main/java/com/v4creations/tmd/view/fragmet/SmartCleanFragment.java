package com.v4creations.tmd.view.fragmet;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;
import com.v4creations.tmd.R;
import com.v4creations.tmd.controller.adapter.ShareMessageAdapter;
import com.v4creations.tmd.model.ShareMessage;
import com.v4creations.tmd.system.event.TMDEventBus;

import java.util.ArrayList;

public class SmartCleanFragment extends ListFragment {
    private ShareMessageAdapter mAdapter;

    public static SmartCleanFragment newInstance() {
        SmartCleanFragment fragment = new SmartCleanFragment();
        return fragment;
    }

    public SmartCleanFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_smart_message,
                container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        TMDEventBus.getBus().register(this);
        ShareMessage.loadShareMessages();
    }

    @Override
    public void onPause() {
        super.onPause();
        TMDEventBus.getBus().unregister(this);
    }

    @Subscribe
    public void onShareMessageLoad(ArrayList<ShareMessage> shareMessages) {
        mAdapter = new ShareMessageAdapter(getActivity(), shareMessages);
        setListAdapter(mAdapter);
    }

    @Subscribe
    public void onNewShareMessage(ShareMessage shareMessage) {
        if (mAdapter != null)
            mAdapter.insert(shareMessage, 0);
    }
}
