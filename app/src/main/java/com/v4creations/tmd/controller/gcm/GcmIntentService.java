package com.v4creations.tmd.controller.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.v4creations.tmd.model.ShareMessage;
import com.v4creations.tmd.system.event.TMDEventBus;

public class GcmIntentService extends IntentService {
    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                ShareMessage sm = new ShareMessage(extras.getString("title"), extras.getString("message"), extras.getString("type"), extras.getString("at"));
                sm.save();
                sm.showNotification(getApplicationContext());
                TMDEventBus.getBus().post(sm);
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
}