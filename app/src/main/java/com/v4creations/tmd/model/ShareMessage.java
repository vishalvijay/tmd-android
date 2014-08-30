package com.v4creations.tmd.model;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import com.orm.SugarRecord;
import com.v4creations.tmd.R;
import com.v4creations.tmd.system.event.TMDEventBus;
import com.v4creations.tmd.view.activity.TMDMainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShareMessage extends SugarRecord<ShareMessage> {
    private String message, type, title;

    public ShareMessage() {

    }

    public ShareMessage(String title, String message, String type) {
        this.message = message;
        this.type = type;
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public static void loadShareMessages() {
        new AsyncTask<Void, Void, List<ShareMessage>>() {
            @Override
            protected List<ShareMessage> doInBackground(Void[] objects) {
                return ShareMessage.listAll(ShareMessage.class);
            }

            @Override
            protected void onPostExecute(List<ShareMessage> shareMessages) {
                super.onPostExecute(shareMessages);
                Collections.reverse(shareMessages);
                TMDEventBus.getBus().post(new ArrayList<ShareMessage>(shareMessages));
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void showNotification(Context context) {
        NotificationManager mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, TMDMainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(getMessage()))
                        .setContentText(getMessage());

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(getId().intValue(), mBuilder.build());
    }
}
