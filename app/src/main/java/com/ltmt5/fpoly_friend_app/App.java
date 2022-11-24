package com.ltmt5.fpoly_friend_app;

import android.app.Application;
import android.content.Context;

import com.ltmt5.fpoly_friend_app.help.SharePref;

public class App extends Application {
    public static final String STORY = "STORY";
    public static Context context;
    public static SharePref sharePref;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        sharePref = new SharePref(context);
    }
}
