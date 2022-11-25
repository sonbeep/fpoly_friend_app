package com.ltmt5.fpoly_friend_app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.ltmt5.fpoly_friend_app.help.SharePref;

public class App extends Application {
    public static final String STORY = "STORY";
    public static final String CHANNEL_ID = "push_notification_id";
    public static Context context;
    public static SharePref sharePref;
    public static String TAG = "AAA";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        sharePref = new SharePref(context);
        createChanelNotification();
    }

    private void createChanelNotification() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"PushNotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
