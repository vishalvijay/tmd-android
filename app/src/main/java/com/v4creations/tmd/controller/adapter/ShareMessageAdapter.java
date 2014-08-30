package com.v4creations.tmd.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.v4creations.tmd.R;
import com.v4creations.tmd.model.ShareMessage;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ShareMessageAdapter extends BaseArrayAdapter<ShareMessage> {

    public ShareMessageAdapter(Context context, List<ShareMessage> objects) {
        super(context, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = getLayoutInflater().inflate(R.layout.item_share_message, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        ShareMessage shareMessage = getItem(position);
        holder.message.setText(shareMessage.getMessage());
        return convertView;
    }

    @Override
    public ShareMessage getItem(int position) {
        return (ShareMessage) super.getItem(position);
    }

    static class ViewHolder {
        @InjectView(R.id.tvMessage)
        TextView message;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
