package com.android.smartShow.activity;


import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.smartShow.R;
import com.android.smartShow.actionbar.ActionBarManager;
import com.android.smartShow.actionbar.DeviceActionBar;
import com.android.smartShow.actionbar.MainActionBar;
import com.android.smartShow.actionbar.ScreenActionBar;
import com.android.smartShow.adapter.DeviceAdapter;
import com.android.smartShow.adapter.ScreenAdapter;

/**
 * 门店内的屏幕管理的
 * @author yelei
 *
 */
public class ScreenManagerActivity extends BaseActivity implements OnItemClickListener{
    
    private GridView mScreenGridView;
    
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
        setContentView(R.layout.device_list);
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
        ActionBarManager.instance().getActionBar(ActionBarManager.SCREEN_ACTION_BAR, this)
                .udpateActionBar(ScreenActionBar.SCREEN_LIST);
    }
    @Override
    protected void initWidget() {
        mScreenGridView = (GridView) findViewById(R.id.device_gridview);
        mScreenGridView.setAdapter(new ScreenAdapter(getApplicationContext()));
        mScreenGridView.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int positon, long arg3) {
    }
}
