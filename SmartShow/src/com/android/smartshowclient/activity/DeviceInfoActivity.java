package com.android.smartshowclient.activity;


import java.util.Map;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ListView;

import com.android.smartshowclient.R;
import com.android.smartshowclient.actionbar.ActionBarManager;
import com.android.smartshowclient.actionbar.DeviceActionBar;
import com.android.smartshowclient.adapter.DeviceAdapter;
import com.android.smartshowclient.adapter.ModelAdapter;
import com.android.smartshowclient.view.HorizontalListView;

public class DeviceInfoActivity extends BaseActivity{
    private Gallery mHorizontalListView = null;
    private ListView mListView = null;
    
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
        setContentView(R.layout.device_info);
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
                .udpateActionBar(DeviceActionBar.DEVICE_INFO);
    }
    
    private ModelAdapter mAdapter;
    private ModelAdapter.OnImageLoadListener mListener = new ModelAdapter.OnImageLoadListener() {
        @Override
        public void onImageLoad(int index) {
            mAdapter.updateImage(mListView.getChildAt(index - mListView.getFirstVisiblePosition()), index);
        }
    };
    
    @Override
    protected void initWidget() {
        mHorizontalListView = (Gallery) findViewById(R.id.device_info_gallery);
        mHorizontalListView.setAdapter(new DeviceAdapter(getApplicationContext()));
        mListView = (ListView) findViewById(R.id.device_info_listview);
        
        mAdapter = new ModelAdapter(getApplicationContext(), mListener);
        mListView.setAdapter(mAdapter);
        
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View listItem = mAdapter.getView(i, null, mListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = mListView.getLayoutParams();
        params.height = totalHeight + (mListView.getDividerHeight() * (mAdapter.getCount() - 1));
        mListView.setLayoutParams(params);
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
