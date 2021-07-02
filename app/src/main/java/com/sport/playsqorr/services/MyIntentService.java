package com.sport.playsqorr.services;


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import com.sport.playsqorr.R;
import com.sport.playsqorr.views.OnBoarding;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class MyIntentService extends IntentService {
    private static final String NOTIFICATION_ID = "3";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        /*Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("My Title");
        builder.setContentText("This is the Body");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        Intent notifyIntent = new Intent(this, OnBoarding.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);*/

        Log.e("43","SServices");
          Intent notificationIntent = new Intent(this, OnBoarding.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(OnBoarding.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        Notification notification = builder.setContentTitle("Hey,did you know we have new cards up? Check out the latest matchups and win big!")
               // .setContentText("Hey,did you know we have new cards up? Check out the latest matchups and win big!")
              //  .setTicker("New Message Alert!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(NOTIFICATION_ID);
        }

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_ID,"NotificationDemo",IMPORTANCE_DEFAULT );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notification);
    }

}