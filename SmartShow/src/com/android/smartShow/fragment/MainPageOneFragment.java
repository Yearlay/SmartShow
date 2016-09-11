
package com.android.smartShow.fragment;

import java.util.ArrayList;
import java.util.Map;

import com.android.smartShow.R;
import com.android.smartShow.adapter.ScreenAdapter;
import com.android.smartShow.adapter.StoreAdapter;
import com.android.smartShow.utils.SoftwareConfig;
import com.android.smartShow.view.StoreTitleView;
import com.project.template.utils.Log;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

/**
 * 个人
 */
public class MainPageOneFragment extends BaseFragment 
            implements OnClickListener{
    
    private LinearLayout mStoreListLinearLayout;
    
    private ArrayList<StoreTitleView> mStoreItemList;
    private StoreTitleView mCurrentStoreTitleView;
    private GridView mScreenGridView;
    
    String[] mStoreListStr = {"门店一", "门店二"};

    public ArrayList<StoreTitleView> getStoreItemList() {
        return mStoreItemList;
    }

    public void setStoreItemList(ArrayList<StoreTitleView> mStoreItemList) {
        this.mStoreItemList = mStoreItemList;
    }
    
    public StoreTitleView getCurrentStoreTitleView() {
        return mCurrentStoreTitleView;
    }
    
    public void setCurrentStoreTitleView(StoreTitleView currentStoreTitleView) {
        mCurrentStoreTitleView = currentStoreTitleView;
        mCurrentStoreTitleView.setSelected(true);
    }

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
        mStoreListLinearLayout = (LinearLayout) view.findViewById(R.id.store_list_linearlayout);
        if (SoftwareConfig.isStoreListShow) {
        mStoreItemList = new ArrayList<StoreTitleView>();
        for (String storeItemStr : mStoreListStr) {
            StoreTitleView storeTitleView = new StoreTitleView(getContext());
            storeTitleView.setPageOneFragment(this);
            storeTitleView.getStoreTitle().setText(storeItemStr);
            mStoreItemList.add(storeTitleView);
        }
        reloadStore();
        if (mStoreItemList.size() > 0) {
            setCurrentStoreTitleView(mStoreItemList.get(0));
        }
        } else {
            mStoreListLinearLayout.setVisibility(View.GONE);
        }
        mScreenGridView = (GridView) view.findViewById(R.id.screen_gridview);
        mScreenGridView.setAdapter(new StoreAdapter(getContext()));
        
        view.findViewById(R.id.add_store_button).setOnClickListener(this);
        
        return view;
    }

    private void reloadStore() {
        while (mStoreListLinearLayout.getChildCount() != 1) {
            mStoreListLinearLayout.removeViewAt(0);
        }
        
        for (StoreTitleView storeTitleView : mStoreItemList) {
            mStoreListLinearLayout.addView(storeTitleView,
                    mStoreListLinearLayout.getChildCount() - 1);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_store_button:
                final EditText editText = new EditText(mActivity);
                new AlertDialog.Builder(mActivity)
                .setTitle(R.string.add_store_title)
                .setView(editText)
                .setPositiveButton(R.string.OK, 
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                if (editText.getText().length() != 0) {
                                    StoreTitleView storeTitleView = new StoreTitleView(mActivity);
                                    storeTitleView.setPageOneFragment(MainPageOneFragment.this);
                                    storeTitleView.getStoreTitle().setText(editText.getText());
                                    Log.d(TAG, "editText.getText():" + editText.getText());
                                    mStoreItemList.add(storeTitleView);
                                    
                                    reloadStore();
                                }
                            }
                        }
                )
                .setNegativeButton(R.string.CANCEL, 
                        new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
                break;

            default:
                break;
        }
    }

}
