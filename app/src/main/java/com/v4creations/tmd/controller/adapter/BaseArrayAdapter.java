package com.v4creations.tmd.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.util.List;

public class BaseArrayAdapter<T> extends ArrayAdapter {
    private LayoutInflater mLayoutInflater;

    public BaseArrayAdapter(Context context, List<T> objects) {
        super(context, android.R.layout.simple_expandable_list_item_1, objects);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }
}
