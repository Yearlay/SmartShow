package com.project.template.utils;

import android.content.Context;

public class InvokeUtils {
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
            TemplateLog.e(e.getMessage(), true);
        } catch (IllegalAccessException e) {
            TemplateLog.e(e.getMessage(), true);
        } catch (IllegalArgumentException e) {
            TemplateLog.e(e.getMessage(), true);
        } catch (NoSuchFieldException e) {
            TemplateLog.e(e.getMessage(), true);
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
            TemplateLog.e(e.getMessage(), true);
        } catch (IllegalAccessException e) {
            TemplateLog.e(e.getMessage(), true);
        } catch (IllegalArgumentException e) {
            TemplateLog.e(e.getMessage(), true);
        } catch (NoSuchFieldException e) {
            TemplateLog.e(e.getMessage(), true);
        }
        return id;
    }
}
