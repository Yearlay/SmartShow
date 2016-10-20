package com.android.smartshowclient.activity;


import java.util.Map;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.android.smartshowclient.R;
import com.android.smartshowclient.actionbar.ActionBarManager;
import com.android.smartshowclient.actionbar.DeviceActionBar;
import com.android.smartshowclient.actionbar.MainActionBar;
import com.android.smartshowclient.adapter.DeviceAdapter;

public class DeviceAddActivity extends BaseActivity{
    
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
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.device_add);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResult(int event, boolean isok, Map<String, Object> result) {
    }

    @Override
    public void handleCommonMsg(Message msg) {
    }

    @Override
    public boolean handleNotifyMessage(Message msg) {
        return false;
    }

    @Override
    public boolean handleUIMessage(Message msg) {
        return false;
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        ActionBarManager.instance().getActionBar(ActionBarManager.DEVICE_ACTION_BAR, this)
                .udpateActionBar(DeviceActionBar.DEVICE_ADD);
    }
    @Override
    protected void initWidget() {
    }

    @Override
    protected void initListener() {
    }

    @Override
    public void onClick(View view) {
    }
    
    @Override
    protected void refreshView() {
    }

    @Override
    protected void clearData() {
    }
}
