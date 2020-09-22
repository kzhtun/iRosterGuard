package com.info121.iguard.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.info121.iguard.App;
import com.info121.iguard.R;
import com.info121.iguard.activities.MainActivity;


import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.atomic.AtomicInteger;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMessagingService";
    private static final String EXTRA_MESSAGE = "extra.message";
    public final String text = "";

    final AtomicInteger c = new AtomicInteger(0);

    String OLD_CH = "";
    String NEW_CH = "";

    private void showNotification(String title, String body) {
        OLD_CH  = App.getOldChannelId();
        NEW_CH = App.getNewChannelId();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = App.getNotificationSoundUri();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NEW_CH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);



        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (soundUri != null) {
                // Changing Default mode of notification
                notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
                // Creating an Audio Attribute
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build();


//                //it will delete existing channel if it exists
                if (mNotificationManager.getNotificationChannel(OLD_CH) != null) {
                    mNotificationManager.deleteNotificationChannel(OLD_CH);
                }

                // Creating Channel
                NotificationChannel notificationChannel = new NotificationChannel(NEW_CH, NEW_CH, NotificationManager.IMPORTANCE_HIGH);

                notificationChannel.enableLights(true);
                notificationChannel.enableVibration(true);
                notificationChannel.setSound(soundUri, audioAttributes);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
        }

        mNotificationManager.notify(c.incrementAndGet(), notificationBuilder.build());
    }




    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
        EventBus.getDefault().post("REFRESH_MESSAGES");

        super.onMessageReceived(remoteMessage);

    }


    public void showDialog(String jobNo, String name, String phone, String displayMessage) {
        OLD_CH  = App.getOldChannelIdP();
        NEW_CH = App.getNewChannelIdP();

        Intent intent = new Intent(this, MainActivity.class);
        //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getService(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = App.getProminentSoundUri();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NEW_CH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My Coach")
                .setContentText("A job has been alerted for your confirmation.")
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);



        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (soundUri != null) {
                // Changing Default mode of notification
                notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
                // Creating an Audio Attribute
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build();


//                //it will delete existing channel if it exists
                if (mNotificationManager.getNotificationChannel(OLD_CH) != null) {
                    mNotificationManager.deleteNotificationChannel(OLD_CH);
                }

                // Creating Channel
                NotificationChannel notificationChannel = new NotificationChannel(NEW_CH, NEW_CH, NotificationManager.IMPORTANCE_HIGH);

                notificationChannel.enableLights(true);
                notificationChannel.enableVibration(true);
                notificationChannel.setSound(soundUri, audioAttributes);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
        }

        mNotificationManager.notify(c.incrementAndGet(), notificationBuilder.build());

        //mNotificationManager.cancel(0);


        Bundle bundle = new Bundle();

//        bundle.putString(ShowDialogService.JOB_NO, jobNo);
//        bundle.putString(ShowDialogService.NAME, name);
//        bundle.putString(ShowDialogService.PHONE, phone);
//        bundle.putString(ShowDialogService.MESSAGE, displayMessage);
//
//        intent.putExtras(bundle);
//        // startService(intent);
//
//        startActivity(intent);
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }

}

