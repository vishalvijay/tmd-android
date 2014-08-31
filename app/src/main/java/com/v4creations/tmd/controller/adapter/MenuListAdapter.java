package com.v4creations.tmd.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.v4creations.tmd.model.NavMenuItem;

import java.util.List;


public class MenuListAdapter extends ArrayAdapter<NavMenuItem> {

    public MenuListAdapter(Context context, List<NavMenuItem> navMenuItems) {
        super(context, android.R.layout.simple_list_item_activated_1, navMenuItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView,
                parent);
        textView.setCompoundDrawablesWithIntrinsicBounds(getItem(position)
                .getIconRid(), 0, 0, 0);
        textView.setTextColor(getContext().getResources().getColor(
                android.R.color.white));
        int viewPadding = 5;
        textView.setPadding(viewPadding, viewPadding, viewPadding, viewPadding);
        return textView;
    }

}
