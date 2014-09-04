package com.v4creations.tmd.controller.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.v4creations.tmd.R;
import com.v4creations.tmd.model.ShareMessage;

import java.util.List;
import java.util.Locale;

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
        int color = Color.RED;
        String word = ShareMessage.TYPE_SELL;
        if (shareMessage.isBuy()) {
            color = Color.GREEN;
            word = ShareMessage.TYPE_BUY;
        }
        Spannable message = new SpannableString(shareMessage.getMessage());
        int start = shareMessage.getMessage().toLowerCase(Locale.getDefault()).indexOf(word);
        if (start != -1)
            message.setSpan(new BackgroundColorSpan(color), start, start + word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.message.setText(message);
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
