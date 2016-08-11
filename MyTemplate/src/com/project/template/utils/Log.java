package com.project.template.utils;

/**
 * 用于调试的Log。</br>
 * DEBUG 当前的值为 true; </br>
 * @author 叶蕾
 *
 */
public class Log {
    private static final boolean DEBUG = true;

    public static void v(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable th) {
        if (DEBUG) {
            android.util.Log.v(tag, msg, th);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable th) {
        if (DEBUG) {
            android.util.Log.d(tag, msg, th);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable th) {
        if (DEBUG) {
            android.util.Log.i(tag, msg, th);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.w(tag, msg);
        }
    }

    public static void w(String tag, Throwable th) {
        if (DEBUG) {
            android.util.Log.w(tag, th);
        }
    }

    public static void w(String tag, String msg, Throwable th) {
        if (DEBUG) {
            android.util.Log.w(tag, msg, th);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable th) {
        if (DEBUG) {
            android.util.Log.e(tag, msg, th);
        }
    }

    public static void wtf(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.wtf(tag, msg);
        }
    }

    public static void wtf(String tag, Throwable th) {
        if (DEBUG) {
            android.util.Log.wtf(tag, th);
        }
    }

    public static void wtf(String tag, String msg, Throwable th) {
        if (DEBUG) {
            android.util.Log.wtf(tag, msg, th);
        }
    }

    public static void println(int priority, String tag, String msg) {
        android.util.Log.println(priority, tag, msg);
    }

    public static boolean isDebug() {
        return DEBUG;
    }

    public static void isLoggable(String tag, int priority) {
        android.util.Log.isLoggable(tag, priority);
    }

    public static void getStackTraceString(Throwable tr) {
        android.util.Log.getStackTraceString(tr);
    }

}
