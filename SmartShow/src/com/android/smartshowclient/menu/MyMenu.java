package com.android.smartshowclient.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.project.template.utils.InvokeUtils;

import java.util.ArrayList;

import com.android.smartshowclient.inf.MenuClickListener;

/**
 * Created by frank on 2016/7/31.
 */

public class MyMenu {
    private MenuClickListener mListener;
    private ArrayList<MenuItem> mMenuList;
    private LinearLayout mView;
    private Context mContext;
    private BaseMenuAdapter mMenuAdapter;
    private ExpandableListView.OnGroupClickListener mGroupListener = new ExpandableListView.OnGroupClickListener() {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
            if (mListener != null) {
                mListener.onMenuClick(mMenuAdapter.getGroup(groupPosition));
            }
            return false;
        }
    };
    private ExpandableListView.OnChildClickListener mChildListener = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            if (mListener != null) {
                mListener.onMenuClick(mMenuAdapter.getChild(groupPosition, childPosition));
            }
            return false;
        }
    };

    public MyMenu(Context context) {
        mContext = context;
        LayoutInflater l = LayoutInflater.from(context);
        mView = (LinearLayout) l.inflate(InvokeUtils.create(mContext).getIdByName("layout", "template_menu_list_view"), null);
        ExpandableListView listView = (ExpandableListView) mView.findViewById(InvokeUtils.create(mContext).getIdByName("id", "expand_list"));
        listView.setGroupIndicator(null);
        listView.setOnGroupClickListener(mGroupListener);
        listView.setOnChildClickListener(mChildListener);
    }

    public LinearLayout createMenu(Context context, MenuClickListener listener) {
        mMenuAdapter = new BaseMenuAdapter(mMenuList);
        ExpandableListView listView = (ExpandableListView) mView.findViewById(InvokeUtils.create(mContext).getIdByName("id", "expand_list"));
        listView.setAdapter(mMenuAdapter);
        mMenuAdapter.notifyDataSetChanged();
        mListener = listener;
        return mView;
    }

    public void addMenu(MenuItem menu) {
        if (mMenuList == null) {
            mMenuList = new ArrayList<MenuItem>();
        }
        mMenuList.add(menu);
    }

    public void removeAll() {
        if (mMenuList != null) {
            mMenuList.clear();
        }
        ExpandableListView listView = (ExpandableListView) mView.findViewById(InvokeUtils.create(mContext).getIdByName("id", "expand_list"));
        listView.setAdapter(mMenuAdapter);
    }
}
