package com.example.vachhani.place_order.Utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.vachhani.place_order.Activity.CartActivity_;
import com.example.vachhani.place_order.Activity.MainActivity;
import com.example.vachhani.place_order.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

/**
 * Created by nisarg on 05/09/18.
 */

public class HandleMsg extends FirebaseMessagingService {
    int fcid = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Date date = new Date();
        fcid = (int) date.getTime();

        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        //Log.i("DATA------>", remoteMessage.getData().toString());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            //Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Log.d("NOTIFICATION", "From: " + remoteMessage.getFrom().toString());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //  scheduleJob();
            } else {
                // Handle message within 10 seconds
                //handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("", "Message Notification Body:---------> " + remoteMessage.getNotification().getBody());
            Log.d("NOTIFICATION--------->", "From: " + remoteMessage.getFrom().toString());

            sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle(), "");

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }


    private void sendNotification(String messageBody, String title, String id) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            String CHANNEL_ID = "my_channel_01";// The id of the channel.
            CharSequence name = "order";

            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setLightColor(Color.GREEN);
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            Intent intent = new Intent(this, CartActivity_.class);
            intent.putExtra("msg", messageBody);


            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setContentIntent(pendingIntent)
                    .setColor(255)
                    .setAutoCancel(true);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(mChannel);
            notificationManager.notify(fcid, notificationBuilder.build());

        } else {
            //Intent intent = new Intent(this, AnnouncementDetails_.class);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("msg", messageBody);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setContentIntent(pendingIntent)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(fcid, notificationBuilder.build());

        }
    }
}
