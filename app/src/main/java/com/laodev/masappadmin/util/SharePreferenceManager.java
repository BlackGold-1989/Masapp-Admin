package com.laodev.masappadmin.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharePreferenceManager {

    public static void setEmail(Context context, String email) {
        SharedPreferences pref = context.getSharedPreferences("com.laodev.uyuk", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("email", email);
        edit.apply();
    }

    public static String getEmail(Context context) {
        SharedPreferences pref = context.getSharedPreferences("com.laodev.uyuk", MODE_PRIVATE);
        return pref.getString("email", "");
    }

    public static void setPassword(Context context, String pass) {
        SharedPreferences pref = context.getSharedPreferences("com.laodev.uyuk", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("password", pass);
        edit.apply();
    }

    public static String getPassword(Context context) {
        SharedPreferences pref = context.getSharedPreferences("com.laodev.uyuk", MODE_PRIVATE);
        return pref.getString("password", "");
    }

    public static boolean getSellerable(Context context) {
        SharedPreferences pref = context.getSharedPreferences("com.laodev.uyuk", MODE_PRIVATE);
        return pref.getBoolean("isseller", true);
    }

    public static void setSellerable(Context context, boolean flag) {
        SharedPreferences pref = context.getSharedPreferences("com.laodev.uyuk", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean("isseller", flag);
        edit.apply();
    }

    public static boolean isCheckKey(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences("com.laodev.uyuk", MODE_PRIVATE);
        return pref.contains(key);
    }

}
