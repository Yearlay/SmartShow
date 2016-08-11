package com.project.template.ui.utils;

import com.project.template.utils.Log;

public class CmdCode {
    private static final String TAG = CmdCode.class.getSimpleName();

    public static final int MSG_COMMON_HANDLE = 0xF0000001;

    public static class MsgTypeCode {

        public static final int BASE_UI_MSG = 0x10000000;

        public static final int BASE_NOTIFY_MSG = 0x20000000;

        public static int setUp(int orgCode, int orgMsgTypeCode) {
            int msgTypeCode = orgMsgTypeCode;
            if (!Mask.verify(Mask.MSG_TYPE_MASK, orgMsgTypeCode)) {
                msgTypeCode = orgMsgTypeCode & Mask.MSG_TYPE_FILTER;
            }
            return (orgCode & Mask.MSG_TYPE_MASK) | msgTypeCode;
        }

    }

    public static class Mask {

        public static final int MSG_TYPE_MASK = 0x0FFFFFFF;

        public static final int MSG_TYPE_FILTER = ~MSG_TYPE_MASK;

        public static boolean verify(int mask, int code) {
            Log.e(TAG, "verify mask = " + Integer.toHexString(mask)
                    + " code = " + Integer.toHexString(code)
                    + " (mask & code) = " + (mask & code));
            return (mask & code) == 0;
        }

    }

    public static boolean isUICode(int code) {
        return (Mask.MSG_TYPE_FILTER & code) == MsgTypeCode.BASE_UI_MSG;
    }

    public static boolean isNotifyCode(int code) {
        return (Mask.MSG_TYPE_FILTER & code) == MsgTypeCode.BASE_NOTIFY_MSG;
    }

    public static class UIMsgCode {
        public static final int UI_CODE_TEST = 0x11111111;

        public static final int NOTIFY_LOAD_NEWS_FINISHED = 0x11111112;

        public static final int NOTIFY_LOAD_ACTIVE_FINISHED = 0x11111113;

        public static final int NOTIFY_LOAD_NOTICE_FINISHED = 0x11111114;

        public static final int NOTIFY_LOAD_ABOUT_GARTEN_FINISHED = 0x11111115;

        public static final int NOTIFY_LOAD_BABY_SHOW_FINISHED = 0x11111115;

        public static final int NOTIFY_LOAD_DAILY_COMMENT_FINISHED = 0x11111117;

        public static final int NOTIFY_LOAD_WONDERFUL_GARTEN_FINISHED = 0x11111118;
    }

    public static class NotifyMsgCode {
        public static final int NOTIFY_CODE_TEST = 0x21111111;

    }

}
