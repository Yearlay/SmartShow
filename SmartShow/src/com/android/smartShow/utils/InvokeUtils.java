package com.android.smartShow.utils;

import com.project.template.utils.Log;

import android.content.Context;

public class InvokeUtils {
    public final static String TAG = "InvokeUtils";
    
    private Context mContext;

    private InvokeUtils(Context context) {
        mContext = context;
    }

    private static InvokeUtils mInstance;

    public static InvokeUtils create(Context context) {
        if (mInstance == null && context != null) {
            mInstance = new InvokeUtils(context);
        }
        return mInstance;
    }

    public int getIdByName(String className, String name) {
        String packageName = mContext.getPackageName();
        Class source = null;
        Class dex = null;
        int id = 0;
        try {
            source = Class.forName(packageName + ".R");
            // 获取所有.R下面的类
            Class[] classes = source.getClasses();
            for (Class d : classes) {
                if (d.getName().split("\\$")[1].equals(className)) {
                    dex = d;
                    break;
                }
            }
            if (dex != null) {
                id = dex.getField(name).getInt(dex);
            }
        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.getMessage());
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        } catch (NoSuchFieldException e) {
            Log.e(TAG, e.getMessage());
        }
        return id;
    }

    public int[] getTypeArray(String className, String name) {
        String packageName = mContext.getPackageName();
        Class source = null;
        Class dex = null;
        int[] id = new int[0];
        try {
            source = Class.forName(packageName + ".R");
            // 获取所有.R下面的类
            Class[] classes = source.getClasses();
            for (Class d : classes) {
                // string 转译字符 \\
                if (d.getName().split("\\$")[1].equals(className)) {
                    dex = d;
                    break;
                }
            }
            if (dex != null) {
                id = (int[])dex.getField(name).get(dex);
            }
        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.getMessage());
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        } catch (NoSuchFieldException e) {
            Log.e(TAG, e.getMessage());
        }
        return id;
    }
}
