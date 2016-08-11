package com.project.template.utils;

/**
 * 定义一些常用的常量
 */
public interface Constant {
    /***/
    public String KEY_REQUEST_RESULT = "request_result";

    /**
     * 登陆相关逻辑用到的常量
     */
    public interface Login {
        public String KEY_USER_NAME = "user_name";

        public String KEY_PASSWORD = "password";

        public String KEY_AUTO_FILLING = "audo_filling";

        /** 登陆初始化状态 */
        public int LOGIN_INITIAL = 100;

        /** 登陆失败的状态 */
        public int LOGIN_FAILURE = 101;

        /** 登陆成功的状态 */
        public int LOGIN_SUCCESS = 102;

        /** 正在登陆的状态 */
        public int LOGIN_PENDING = 103;

    }

    /**
     * Preferences key.
     */
    interface Preference {
        String KEY_DATABASE_VER = "db_ver";
    }

    /**
     * 定义异常错误码
     */
    interface ErrorCodes {
        public int ERROR_NETWORK_NOT_AVAILABLE = 10001;
    }

    public interface ContentType {
        public int TEXT = 1;

        public int FACE = 2;

        public int IMAGE = 3;

        public int VOICE = 4;

        public int VIDEO = 5;

        public int VCARD = 6;

        public int FILE = 7;

        public int LOCATION = 8;

        public int OTHER = -1;
    }

    public interface Trend {
        public String DYNAMIC_KEY = "dynamic_key";

        public String COMMENT_KEY = "comment_key";

        public String CONTENT = "content";

        public String PRAISE = "praise";
    }

    /**
     * 定义登陆启动流handle常量
     */
    public interface Start {
        public int LOGIN = 1;

        public int MAIN = 2;
    }

    /**
     * 圈子中的常量定义
     */
    public interface Circle {
        public String KEY_URL = "circle_url";
    }
    
    /**
     * 评估中的常量定义
     */
    public interface AssessDiagnose {
        public String ASSESS_DIAGNOSE = "assess_diagnose";
        public String ASSESS_DIAGNOSE_HEART_RATE = "assess_diagnose_heart_rate";
        public String ASSESS_DIAGNOSE_BLOOD_SUGAR = "assess_diagnose_blood_sugar";
        public String ASSESS_DIAGNOSE_LIPID = "assess_diagnose_lipid";
        public String ASSESS_DIAGNOSE_WAISTHIP = "assess_diagnose_waisthip";
        public String ASSESS_DIAGNOSE_WEIGHT = "assess_diagnose_weight";

        public String ASSESS_DIAGNOSE_HEART_RATE_TIME = "assess_diagnose_heart_rate_time";
        public String ASSESS_DIAGNOSE_BLOOD_SUGAR_TIME = "assess_diagnose_blood_sugar_time";
        public String ASSESS_DIAGNOSE_LIPID_TIME = "assess_diagnose_lipid_time";
        public String ASSESS_DIAGNOSE_WAISTHIP_TIME = "assess_diagnose_waisthip_time";
        public String ASSESS_DIAGNOSE_WEIGHT_TIME = "assess_diagnose_weight_time";

        public String WEIGHT = "weight";
        public String BLOOD_PRESSURE = "blood_pressure";
        public String DOWN_PRESSURE = "down_pressure";
        public String UP_PRESSURE = "up_pressure";
        public String HEAR_TRATE = "hear_trate";
        public String CHOLESTEROL = "Cholesterol";
        public String TRIGLYCERIDE = "Triglyceride";
        public String LDL = "LDL";
        public String HDL = "HDL";
        public String WAISTLINE = "Waistline";
        public String HIPS = "Hips";
    }

    /**
     * CoreService中的消息 
     */
    public interface UIRequestCode {
        public static int ALARM = 1;
    }

    /**
     * 任务
     */
    public interface Task {
        public static final String KEY_UUID = "key_uudi";
        public static final String KEY_TASK_ID = "key_task_id";
        public static final String KEY_CAN_ALARM = "key_can_alarm";
    }
}
