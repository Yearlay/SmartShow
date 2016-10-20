package com.android.smartshowbox.activity;

import java.util.Map;

import com.android.smartshowbox.R;
import com.project.template.utils.Constant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

/**
 * 欢迎界面
 * @author yelei
 * 重要功能：
 * 1. 下载客户端的二维码显示
 * 2. 设备信息
 * 3. 软件版本信息，显示信息。
 * 4. 扫描二维码绑定设备。
 *
 */
public class ActivityInitDevice extends ActivityBase {

    protected void initWidget() {
        setContentView(R.layout.activity_init_device);
        findViewById(R.id.show_init_task).setOnClickListener(this);
    }

    protected void initData(Bundle savedInstanceState) {
    }

    protected void refreshView() {
    }

    protected void clearData() {
    }

    public void onResult(int event, boolean isok, Map<String, Object> result) {
    }

    protected void initListener() {
    }

    public boolean handleNotifyMessage(Message msg) {
        return false;
    }

    public boolean handleUIMessage(Message msg) {
        return false;
    }

    public boolean handleMessage(Message msg) {
        return false;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_init_task:
                Intent intent = new Intent(this, ActivityDefaultShow.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPress(View arg0) {
    }

    @Override
    public boolean onMenuPress(View arg0) {
        return false;
    }

    @Override
    public void onSearchPress(View arg0) {
        
    }

    @Override
    public void handleCommonMsg(Message msg) {
    }
}
