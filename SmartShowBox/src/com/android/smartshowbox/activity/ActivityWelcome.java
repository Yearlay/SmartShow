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
 * 重要功能：检查是否存在本地的License，设备是否被绑定。
 * 未绑定：进入初始化界面。
 * 被绑定：跳转初始化本地任务。
 *
 */
public class ActivityWelcome extends ActivityBase {

    private static final int JUMP_DELAY = 1500;

    protected void initWidget() {
        setContentView(R.layout.welcome_activity_start);
    }

    protected void initData(Bundle savedInstanceState) {
    }

    protected void refreshView() {
        sendStartMessage();
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
        Intent intent = null;
        switch (msg.what) {
        case Constant.Start.LOGIN:
            intent = new Intent(this,
                    ActivityInitDevice.class);
            break;

        case Constant.Start.MAIN:
//            intent = new Intent(WelcomeActivity.this, FirstActivity.class);
            break;

        default:
//            intent = new Intent(WelcomeActivity.this, FirstActivity.class);
        }

        startActivity(intent);
        finish();
        return false;
    }

    private void sendStartMessage() {
        // 判断登陆状态选择启动的Activity
        mHandler.sendEmptyMessageDelayed(Constant.Start.LOGIN, JUMP_DELAY);
    }

    public void onClick(View view) {

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
