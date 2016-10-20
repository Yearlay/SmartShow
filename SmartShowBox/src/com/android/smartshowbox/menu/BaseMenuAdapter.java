package com.android.smartshowbox.menu;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.crypto.spec.IvParameterSpec;

import com.android.smartshowbox.utils.InvokeUtils;

/**
 * Created by frank on 2016/7/31.
 */

public class BaseMenuAdapter extends BaseExpandableListAdapter {

    private class Item {
        private int mTag;
        private Drawable mDrawable;
        private String mDec;

        public Item(int tag, Drawable d, String dec) {
            mTag = tag;
            mDrawable = d;
            mDec = dec;
        }

        public Drawable getmDrawable() {
            return mDrawable;
        }

        public int getTag() {
            return mTag;
        }

        public String getDec() {
            return mDec;
        }
    }

    private HashMap<Integer, Item> mGroupItem = new HashMap<Integer, Item>();
    private HashMap<Integer, HashMap<Integer, Item>> mSubItem = new HashMap<Integer, HashMap<Integer, Item>>();

    public BaseMenuAdapter(ArrayList<MenuItem> itemlist) {
        for (MenuItem item : itemlist) {
            if (item.getSubNum() == -1) {
                mGroupItem.put(item.getmGroupNum(), new Item(item.getTag(), item.getmDrable(), item.getmDec()));
            }
            if (item.getSubNum() != -1) {
                if (mSubItem.get(item.getmGroupNum()) != null) {
                    mSubItem.get(item.getmGroupNum()).put(item.getSubNum(), new Item(item.getTag(), item.getmDrable(), item.getmDec()));
                } else {
                    // 如果expandAdapter没有添加父菜单，暂时添加一个空的
                    if (!mGroupItem.containsKey(item.getmGroupNum())) {
                        mGroupItem.put(item.getmGroupNum(), new Item(-1, null, null));
                    }
                    HashMap<Integer, Item> sub = new HashMap<Integer, Item>();
                    sub.put(item.getSubNum(), new Item(item.getTag(), item.getmDrable(), item.getmDec()));
                    mSubItem.put(item.getmGroupNum(), sub);
                }
            }
        }
    }

    @Override
    public int getGroupCount() {
        return mGroupItem.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mSubItem.get(getGroupNum(groupPosition)) == null) {
            return 0;
        }
        return mSubItem.get(getGroupNum(groupPosition)).size();
    }

    @Override
    public Integer getGroup(int groupPosition) {
        if (mGroupItem.containsKey(getGroupNum(groupPosition))) {
            return mGroupItem.get(getGroupNum(groupPosition)).getTag();
        } else {
            return -1;
        }
    }

    @Override
    public Integer getChild(int groupPosition, int childPosition) {
        if (mSubItem.containsKey(getGroupNum(groupPosition)) && mSubItem.get(getGroupNum(groupPosition)).containsKey(getChildNum(groupPosition, childPosition))) {
            return mSubItem.get(getGroupNum(groupPosition)).get(getChildNum(groupPosition, childPosition)).getTag();
        } else {
            return -1;
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = null;
        if (convertView == null) {
            v = View.inflate(parent.getContext(), InvokeUtils.create(parent.getContext()).getIdByName("layout", "template_list_item"), null);
        } else {
            v = convertView;
        }
        Item item = mGroupItem.get(getGroupNum(groupPosition));
        if (item != null) {
            ImageView icon = (ImageView) v.findViewById(InvokeUtils.create(parent.getContext()).getIdByName("id", "menu_icon"));
            icon.setBackgroundDrawable(item.getmDrawable());
            TextView dec = (TextView) v.findViewById(InvokeUtils.create(parent.getContext()).getIdByName("id", "menu_dec"));
            dec.setText(item.getDec());
        } else {
            v = null;
        }
        return v;
    }

    private int getGroupNum(int groupPosition) {
        Iterator iterator = mGroupItem.keySet().iterator();
        Integer ret = 0;
        for (int i = 0; i < groupPosition + 1; i++) {
            ret = (Integer) iterator.next();
        }
        return ret;
    }

    private int getChildNum(int groupPosition, int childPosition) {
        int groupNum = getGroupNum(groupPosition);

        Integer ret = 0;
        if (mSubItem.containsKey(groupNum)) {
            HashMap<Integer, Item> subList = mSubItem.get(groupNum);
            Iterator iterator = subList.keySet().iterator();
            for (int i = 0; i < childPosition + 1; i++) {
                ret = (Integer) iterator.next();
            }
        }
        return ret;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = null;
        Item item = null;
        if (mSubItem.get(getGroupNum(groupPosition)) != null && mSubItem.get(getGroupNum(groupPosition)).get(getChildNum(groupPosition, childPosition)) != null) {
            item = mSubItem.get(getGroupNum(groupPosition)).get(getChildNum(groupPosition, childPosition));
        }
        if (item == null) {
            return v;
        }
        if (convertView == null) {
            v = View.inflate(parent.getContext(), InvokeUtils.create(parent.getContext()).getIdByName("layout", "template_sub_menu_list_item"), null);
        } else {
            v = convertView;
        }
        TextView dec = (TextView) v.findViewById(InvokeUtils.create(parent.getContext()).getIdByName("id", "menu_dec"));
        dec.setText(item.getDec());
        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
