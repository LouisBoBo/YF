package com.yssj.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yssj.ui.activity.CommonActivity;

public class SharedPreferencesUtil {

    private static SharedPreferences sharedPreferences;
    private static String CONFIG = "config";

    public static void saveStringData(Context context, String key, String value) {


        if ("commonactivityfrom".equals(key)) {
            if (null != CommonActivity.instance) {
                CommonActivity.instance.finish();
            }
        }


        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static String getStringData(Context context, String key, String defValue) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(key, defValue);
    }

    public static void saveBooleanData(Context context, String key, Boolean value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBooleanData(Context context, String key, Boolean defValue) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getBoolean(key, defValue);
    }

    public static void removeData(Context context, String key) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().remove(key).commit();
    }
}
