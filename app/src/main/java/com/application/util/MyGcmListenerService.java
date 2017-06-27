package com.application.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.application.page101.EventActivity;
import com.application.page101.EventDetailActivity;
import com.application.page101.MainActivity;
import com.application.page101.MyPageActivity;
import com.application.page101.OrderActivity;
import com.application.page101.R;
import com.application.page101.SplashActivity;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by Administrator on 2015-09-28.
 */
public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    @Override
    public void onMessageReceived(String form, Bundle data) {

        String flag = data.getString("flag");

        ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(1000);

        if(flag.equals("N")) {
            String menu_name = data.getString("menu_name");
            String menu_price = data.getString("menu_price");
            sendNotificationNew(menu_name, menu_price);
        }

        if(flag.equals("D")) {
            String first_menu = data.getString("first_menu");
            String menu_amount = data.getString("menu_amount");
            String message = first_menu + " 외 " +menu_amount +"잔";
            sendNotificationDrink(message);
        }

        if (flag.equals("E")) {
            String event_title = data.getString("event_title");
            String event_img = data.getString("event_image");
            sendNotificationEvent(event_title, event_img);
        }

    }

    private void sendNotificationNew(String menu_name, String menu_price) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle("신메뉴 나왔어연~")
                .setContentText("\""+menu_name+"\" "+menu_price+"원")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void sendNotificationDrink(String message) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle("음료 가져가세연~")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void sendNotificationEvent(String event_title, String event_img) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra("img", event_img);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle("이벤트 왔어연~")
                .setContentText(event_title)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


}
