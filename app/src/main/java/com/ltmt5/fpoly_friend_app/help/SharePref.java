package com.ltmt5.fpoly_friend_app.help;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {
    private final String IS_FIRST_TIME_LAUNCH = "IS_FIRST_TIME_LAUNCH";
    private final String IS_SIGN_IN = "IS_SIGN_IN";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public SharePref(Context context) {
        this.preferences = context.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
        this.editor = this.preferences.edit();
    }

    public boolean isFirstTimeLaunch() {
        return preferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch(boolean firstTimeLaunch) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, firstTimeLaunch);
        editor.commit();
    }

    public boolean isSignIn() {
        return preferences.getBoolean(IS_SIGN_IN, false);
    }

    public void setSignIn(boolean signIn) {
        editor.putBoolean(IS_SIGN_IN, signIn);
        editor.commit();
    }
}
