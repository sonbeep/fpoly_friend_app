package com.ltmt5.fpoly_friend_app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ltmt5.fpoly_friend_app.help.SharePref;
import com.ltmt5.fpoly_friend_app.model.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    public static final String CHANNEL_ID = "push_notification_id";
    public static final String STORY = "STORY";
    public static Context context;
    public static SharePref sharePref;
    public static String TAG = "AAA";
    public static FirebaseUser user;
    public static List<UserProfile> userProfileList;
    public static UserProfile currentUser;

    @Override
    public void onCreate() {
        super.onCreate();
        userProfileList = new ArrayList<>();
        currentUser = new UserProfile();
        context = getApplicationContext();
        sharePref = new SharePref(context);
        user = FirebaseAuth.getInstance().getCurrentUser();
        createChanelNotification();
    }

    private void createChanelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "PushNotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
