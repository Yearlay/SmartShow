package com.android.smartShow.activity;


import java.util.Map;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.android.smartShow.R;

public class RegisterActivity extends BaseActivity{
    
    private Button mRegisterButton;

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
        setContentView(R.layout.register_ativity);
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
    protected void initWidget() {
        mRegisterButton = (Button) findViewById(R.id.register_button);
    }

    @Override
    protected void initListener() {
        bindOnClickLister(this, mRegisterButton);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_button:
                
                break;

            default:
                break;
        }
    }
    
    @Override
    protected void refreshView() {
    }

    @Override
    protected void clearData() {
    }
}
