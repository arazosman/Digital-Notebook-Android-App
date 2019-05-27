package com.example.NotDefteri_16011020;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Random random = new Random();
        int notificationID = random.nextInt();//Integer.valueOf(intent.getStringExtra("id"));
        Log.d("HEEEEEEEEY", "onReceive: " + notificationID);
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");

        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);

        builder.setSmallIcon(android.R.drawable.ic_dialog_info).
                setContentTitle(title).
                setContentText(description).
                setWhen(System.currentTimeMillis()).
                setAutoCancel(true).
                setContentIntent(contentIntent);

        notificationManager.notify(notificationID, builder.build());
    }
}