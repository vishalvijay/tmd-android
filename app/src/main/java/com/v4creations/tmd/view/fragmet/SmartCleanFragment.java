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

import de.timroes.android.listview.EnhancedListView;

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EnhancedListView enhancedListView = (EnhancedListView) getListView();
        enhancedListView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView enhancedListView, final int position) {
                final ShareMessage shareMessage = mAdapter.getItem(position);
                mAdapter.remove(shareMessage);
                return new EnhancedListView.Undoable() {
                    @Override
                    public void undo() {
                        mAdapter.insert(shareMessage, position);
                    }

                    @Override
                    public void discard() {
                        super.discard();
                        shareMessage.delete();
                    }
                };
            }
        });
        enhancedListView.enableSwipeToDismiss();
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
