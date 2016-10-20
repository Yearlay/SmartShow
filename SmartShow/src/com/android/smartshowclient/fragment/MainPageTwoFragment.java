package com.android.smartshowclient.fragment;

import java.util.ArrayList;
import java.util.Map;

import com.android.smartshowclient.R;
import com.android.smartshowclient.adapter.ModelAdapter;
import com.android.smartshowclient.adapter.ModelMenuAdapter;
import com.android.smartshowclient.adapter.ModelMenuData;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * 个人
 */
public class MainPageTwoFragment extends BaseFragment implements OnClickListener {
    private Button mMenuFirst;
    private Button mMenuSecond;
    private Button mMenuThird;
    private ListView mList;
    private ModelAdapter mAdapter;
    private ModelMenuAdapter mParentMenuAdapter;
    private ModelMenuAdapter mSubMenuAdapter;
    private ArrayList<ModelMenuData> mFirstMenuList = new ArrayList<ModelMenuData>();
    private ArrayList<ModelMenuData> mSecondMenuList = new ArrayList<ModelMenuData>();
    private ArrayList<ModelMenuData> mThirdMenuList = new ArrayList<ModelMenuData>();
    private String mMenu;

    private class MyOnItemCickListener implements OnItemClickListener {

        private ListView mSubListView;

        public MyOnItemCickListener(ListView listview) {
            mSubListView = listview;
        }

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub
            mMenu = new StringBuilder().append(mParentMenuAdapter.getItem(arg2)).toString();
            if (mParentMenuAdapter.isExpand(arg2)) {
                mSubListView.setVisibility(View.VISIBLE);
                mSubMenuAdapter = new ModelMenuAdapter(mParentMenuAdapter.getData(), getContext(), arg2);
                mSubListView.setAdapter(mSubMenuAdapter);
                mSubListView.setOnItemClickListener(mSubMenuListener);
            }
        }
    }

    private OnItemClickListener mSubMenuListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub
            mMenu = new StringBuilder(mMenu).append("/").append(mSubMenuAdapter.getItem(arg2)).toString();
        }
    };

    private ModelAdapter.OnImageLoadListener mListener = new ModelAdapter.OnImageLoadListener() {
        @Override
        public void onImageLoad(int index) {
            mAdapter.updateImage(mList.getChildAt(index - mList.getFirstVisiblePosition()), index);
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        mAdapter = new ModelAdapter(getContext(), mListener);
        initMenuList();
        super.onCreate(savedInstanceState);
    }

    private void initMenuList() {
        // 测试数据
        ArrayList<String> subMenu = new ArrayList<String>();
        subMenu.add("小吃");
        subMenu.add("火锅");
        subMenu.add("烧烤");
        ModelMenuData firstData = new ModelMenuData("美食", subMenu);
        for (int i = 0; i < 10; i++) {
            mFirstMenuList.add(firstData);
        }

        ModelMenuData seconddata = new ModelMenuData("红色", null);
        for (int i = 0; i < 8; i++) {
            mSecondMenuList.add(seconddata);
        }

        mThirdMenuList.add(new ModelMenuData("横屏", null));
        mThirdMenuList.add(new ModelMenuData("竖屏", null));
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
        // TODO Auto-generated method stub

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.main_page_two, container, false);
        mMenuFirst = (Button) view.findViewById(R.id.menu_first);
        mMenuSecond = (Button) view.findViewById(R.id.menu_second);
        mMenuThird = (Button) view.findViewById(R.id.menu_third);
        mList = (ListView) view.findViewById(R.id.model_list);
        String[] modelMenu = getResources().getStringArray(R.array.model_menu);
        mMenuFirst.setText(modelMenu[0]);
        mMenuSecond.setText(modelMenu[1]);
        mMenuThird.setText(modelMenu[2]);
        mMenuFirst.setOnClickListener(this);
        mMenuSecond.setOnClickListener(this);
        mMenuThird.setOnClickListener(this);
        mList.setAdapter(mAdapter);
        return view;
    }

    private void showPopupMenu(View view, ArrayList<ModelMenuData> menuData) {
        boolean isSubMenuShow = false;
        for (ModelMenuData data : menuData) {
            if (data.getIsExpand()) {
                isSubMenuShow = true;
                break;
            }
        }

        View MenuView = LayoutInflater.from(getContext()).inflate(R.layout.model_menu_list, null);
        ListView parentMenu = (ListView) MenuView.findViewById(R.id.parent_menu);
        ListView subMenu = (ListView) MenuView.findViewById(R.id.sub_menu);
        if (isSubMenuShow) {
            subMenu.setVisibility(View.INVISIBLE);
        } else {
            subMenu.setVisibility(View.GONE);
        }
        mParentMenuAdapter = new ModelMenuAdapter(menuData, getContext());
        parentMenu.setAdapter(mParentMenuAdapter);
        parentMenu.setOnItemClickListener(new MyOnItemCickListener(subMenu));
        PopupWindow pw = new PopupWindow(MenuView, LayoutParams.MATCH_PARENT, getView().getMeasuredHeight() - view.getMeasuredHeight());
        pw.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.popup_full_dark));
        pw.showAsDropDown(view);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_first:
                showPopupMenu(view, mFirstMenuList);
                break;
            case R.id.menu_second:
                showPopupMenu(view, mSecondMenuList);
                break;
            case R.id.menu_third:
                showPopupMenu(view, mThirdMenuList);
                break;
            default:
                break;
        }
    }

}
