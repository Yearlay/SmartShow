
package com.android.smartShow.fragment;

import java.util.Map;

import com.android.smartShow.R;
import com.android.smartShow.activity.ScreenManagerActivity;
import com.android.smartShow.adapter.StoreAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

/**
 * 第一个主界面，门店的页面，主要是门店的管理。
 */
public class MainPageOneFragment extends BaseFragment 
            implements OnClickListener, OnItemClickListener{
    
    private GridView mScreenGridView;

    @Override
    public boolean handleNotifyMessage(Message msg) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean handleUIMessage(Message msg) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean handleMessage(Message msg) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onResult(int event, boolean isok, Map<String, Object> result) {
        // TODO Auto-generated method stub

    }

    @Override
    protected String setFragmentTag() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void initData(Bundle bundle) {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.main_page_one, container, false);
        mScreenGridView = (GridView) view.findViewById(R.id.screen_gridview);
        mScreenGridView.setAdapter(new StoreAdapter(getContext()));
        mScreenGridView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), ScreenManagerActivity.class);
        startActivity(intent);
    }

}
