package com.example.rongfu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SharedPrefsUtils {

    private static final String SHARED_PREFERENCE_NAME = "com.xxx.tools.SharedPrefs";

    private static final String SERVICE_URL = "service.url";


    public static void putServiceUrl(Context context, String value) {
        putString(context, SERVICE_URL, value);
    }

    public static String getServiceUrl(Context context) {
        return getString(context, SERVICE_URL, "");
    }

    /**
     * 保存字符串类型
     *
     * @param context
     * @param value
     * @param key
     */
    public static void putString(Context context, String key, String value) {
        if (context == null) {
            return;
        }
        SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPrefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取字符串
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Context context, String key,
                                   String defaultValue) {
        if (context == null) {
            return null;
        }
        SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        String value = sharedPrefs.getString(key, defaultValue);
        return value;
    }

    /**
     * 保存boolean
     *
     * @param context
     * @param value
     * @param key
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPrefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获取int
     *
     * @param context
     * @param key     默认为 0
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        boolean value = sharedPrefs.getBoolean(key, false);
        return value;
    }

    /**
     * 获取int
     *
     * @param context
     * @param key     默认为 0
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        boolean value = sharedPrefs.getBoolean(key, defaultValue);
        return value;
    }

    /**
     * 保存int
     *
     * @param context
     * @param value
     * @param key
     */
    public static void putInt(Context context, String key, int value) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPrefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 获取int
     *
     * @param context
     * @param key
     * @param defaultValue 默认为 0
     * @return
     */
    public static int getInt(Context context, String key, int defaultValue) {
        if (context == null) {
            return defaultValue;
        }
        SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        int value = sharedPrefs.getInt(key, defaultValue);
        return value;
    }


    /**
     * 保存int
     *
     * @param context
     * @param value
     * @param key
     */
    public static void putLong(Context context, String key, long value) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPrefs.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 保存float
     *
     * @param context
     * @param value
     * @param key
     */
    public static void putFloat(Context context, String key, float value) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPrefs.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * 获取float
     *
     * @param context
     * @param key
     * @param defaultValue 默认为 0
     * @return
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        if (context == null) {
            return defaultValue;
        }
        SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPrefs.getFloat(key, defaultValue);
    }

    /**
     * 获取Long
     *
     * @param context
     * @param key     默认为 0
     * @return
     */
    public static Long getLong(Context context, String key) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Long value = sharedPrefs.getLong(key, 0);
        return value;
    }

    /**
     * 判断是否包含某key
     *
     * @param context
     * @param key
     * @return
     */

    public static boolean contains(Context context, String key) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPrefs.contains(key);
    }


    public static void remove(Context context, String key) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPrefs.edit();
        editor.remove(key);
        editor.apply();
    }


}
