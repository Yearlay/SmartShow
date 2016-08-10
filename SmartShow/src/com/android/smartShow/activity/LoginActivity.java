package com.android.smartShow.activity;


import android.os.Bundle;
import android.view.View;

import com.android.smartShow.R;
import com.project.template.templateActivity.BaseActivity;

public class LoginActivity extends BaseActivity{

    @Override
    public void onBackPress(View arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onMenuPress(View arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onSearchPress(View arg0) {
        // TODO Auto-generated method stub
        
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_main_ativity);
    }
}
