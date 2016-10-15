package com.android.smartShow.utils;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * 共享文件
 */
public class PreferenceUtils {
    protected final String TAG = this.getClass().getSimpleName();

    private static HashMap<String, PreferenceUtils> mShareMap;

    SharedPreferences mShare;

    private PreferenceUtils(Context context, String name) {
        mShare = context.getSharedPreferences(name, Context.MODE_APPEND);
    }

    /**
     * @param context
     *            不能为空，否则返回空。
     * @param name
     *            如果为空，则使用默认的共享文件名称；如果不为空,则使用指定的共享文件
     * @return PreferenceUtils 共享文件工具类
     */
    public static PreferenceUtils getInstance(Context context, String name) {
        if (context == null) {
            return null;
        }

        if (TextUtils.isEmpty(name)) {
            // 默认使用当前文件的全名称作为共享文件的名称
            name = PreferenceUtils.class.getName();
        }

        if (mShareMap == null) {
            mShareMap = new HashMap<String, PreferenceUtils>();
        }

        if (mShareMap.get(name) == null) {
            PreferenceUtils preferenceUtils = new PreferenceUtils(context, name);
            mShareMap.put(name, preferenceUtils);
        }

        return mShareMap.get(name);
    }

    public void saveBoolean(String key, boolean value) {
        mShare.edit().putBoolean(key, value).commit();
    }

    public Boolean getBoolean(String key) {
        return mShare.getBoolean(key, false);
    }

    public void saveInt(String key, int value) {
        mShare.edit().putInt(key, value).commit();
    }

    public int getInt(String key) {
        return mShare.getInt(key, 0);
    }

    public void saveLong(String key, long value) {
        mShare.edit().putLong(key, value).commit();
    }

    public long getLong(String key) {
        return mShare.getLong(key, 0l);
    }

    public void saveFloat(String key, float value) {
        mShare.edit().putFloat(key, value).commit();
    }

    public float getFloat(String key) {
        return mShare.getFloat(key, 0.0f);
    }

    public void saveString(String key, String value) {
        mShare.edit().putString(key, value).commit();
    }

    public String getString(String key) {
        return mShare.getString(key, "");
    }

    public void removeObject(String key) {
        if (mShare.contains(key)) {
            mShare.edit().remove(key);
        }
    }
}
