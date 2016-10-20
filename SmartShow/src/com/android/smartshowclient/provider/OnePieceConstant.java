
package com.android.smartshowclient.provider;

import android.net.Uri;

public interface OnePieceConstant {

    /**
     * 使用URI对数据库进行操作时，URI使用方法如下:<br/>
     * Uri.withAppendedPath(KindergartenConstant.CONTENT_URI, table);<br/>
     * <b>注：table为要操作的数据表名称。</b><br/>
     * 
     * @see Table
     */
    Uri CONTENT_URI = OnePieceProvider.CONTENT_URI;

    /**
     * 数据库表名称集合,所有本地数据库表名称和各表对应的字段均可通过该接口获取
     */
    interface Table {

        /**
         * <ul>
         * 1.园区管理表
         */
        String T_ACCOUNT = "t_account";

        /**
         * <ul>
         * <li>"uuid ",// 账户唯一标示
         * <li>"usercode ",// 用户号码
         * <li>"password ",// 登陆的密码
         * <li>"type ",// 用户类型
         * <li>"registertime ",// 注册的时间
         * <li>"status ",// 用户状态
         */
        String[] COLUMNS_ACCOUNT = new String[] {
                "uuid ",
                "usercode ", "password ", "type ", "registertime ", "status ",
        };

        /**
         * <ul>
         * 数据字典表
         */
        String T_DATA_DICTIONARY = "t_data_dictionary";
        String[] COLUMNS_DATA_DICTIONARY = new String[] {
                "_id",
                "itemname", "propertyid", "orderby"
        };
        /**
         * <ul> 任务表
         */
        public String T_TASK = "t_task";
        public String[] COLUMNS_TASK = new String[] {"_id", "uuid", "task_time",
                "task_type", "task_title", "task_info", "alarm", "done"};

        /**
         * <ul>问题列表
         */
        String T_QUESTION_CONTENT = "t_question_content";
        String[] COLUMNS_QUESTION_CONTENT = new String[] {
                "_id",
                "question_num", "question_content", "question_opts", "question_type", "question_ans"
        };

        /**
         * <ul>用药助手
         */
        String T_TREATMENT = "t_treatment";
        String[] COLUMNS_TREATMENT = new String[] {
                "_id", "uuid", "name", "start_time", "end_time", "frequency"
        };
        String T_MEDICINE_DOSAGE = "t_medicine_dosage";
        String[] COLUMNS_MEDICINE_DOSAGE = new String[] {
                "_id", "uuid", "time", "dosage", "group_id"
        };
    }

}
