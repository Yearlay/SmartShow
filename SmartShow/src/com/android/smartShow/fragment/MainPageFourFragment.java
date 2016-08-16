
package com.android.smartShow.fragment;

import java.util.Map;

import com.android.smartShow.R;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 个人
 */
public class MainPageFourFragment extends BaseFragment 
            implements OnClickListener{
    
    private ListView mSettingsListView = null;

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
        View view = inflater.inflate(R.layout.main_page_four, container, false);
        
        mSettingsListView = (ListView) view.findViewById(R.id.setting_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.settings_item, R.id.item_textview);
        String[] settingListStr = getResources().getStringArray(R.array.settings_list);
        for (String itemStr : settingListStr) {
            arrayAdapter.add(itemStr);
        }
        mSettingsListView.setAdapter(arrayAdapter);
        return view;
    }

    @Override
    public void onClick(View view) {
    }

}
