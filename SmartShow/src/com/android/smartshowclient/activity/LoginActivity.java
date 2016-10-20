package com.android.smartshowclient.activity;


import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.android.smartshowclient.R;

/**
 * 登录界面
 * @author yelei
 *
 */
public class LoginActivity extends BaseActivity{
    
    private Button mLoginButton;
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
        setContentView(R.layout.login_ativity);
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
        mLoginButton = (Button) findViewById(R.id.login_button);
        mRegisterButton = (Button) findViewById(R.id.register_button);
    }

    @Override
    protected void initListener() {
        bindOnClickLister(this, mLoginButton, mRegisterButton);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.login_button:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
                
            case R.id.register_button:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
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
