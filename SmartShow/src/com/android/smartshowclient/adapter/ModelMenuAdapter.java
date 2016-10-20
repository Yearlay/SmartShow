package com.android.smartshowclient.adapter;

import java.util.List;

import com.android.smartshowclient.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ModelMenuAdapter extends BaseAdapter{
    private List<ModelMenuData> mModelMenu;
    private Context mContext;
    private int mParentPosition;

    public ModelMenuAdapter(List<ModelMenuData> modelMenu, Context context, int parentPosition) {
        mContext = context;
        mModelMenu = modelMenu;
        mParentPosition = parentPosition;
    }

    public ModelMenuAdapter(List<ModelMenuData> modelMenu, Context context) {
        mContext = context;
        mModelMenu = modelMenu;
        mParentPosition = -1;
    }

    @Override
    public int getCount() {
        if (mParentPosition == -1) {
            return mModelMenu.size();
        } else {
            return mModelMenu.get(mParentPosition).getSubText().size();
        }
    }

    public boolean isExpand(int position) {
        return mModelMenu.get(position).getIsExpand();
    }

    public List<ModelMenuData> getData() {
        return mModelMenu;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        if (mParentPosition == -1) {
            return mModelMenu.get(position).getMenuText();
        } else {
            return mModelMenu.get(mParentPosition).getSubText().get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        View view = null;
        if (convertView == null) {
            view = parent.inflate(mContext, R.layout.model_menu_list_item, null);
            holder = new ViewHolder();
            holder.mMenuText = (TextView) view.findViewById(R.id.model_menu_text);
            holder.mIsExpand = (ImageView) view.findViewById(R.id.model_next);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }
        if (mParentPosition == -1) {
            holder.mIsExpand.setVisibility(mModelMenu.get(position).getIsExpand()? View.VISIBLE : View.INVISIBLE);
            holder.mMenuText.setText(mModelMenu.get(position).getMenuText());
        } else {
            holder.mIsExpand.setVisibility(View.INVISIBLE);
            holder.mMenuText.setText(mModelMenu.get(mParentPosition).getSubText().get(position));
        }
        return view;
    }

    private class ViewHolder {
        public TextView mMenuText;
        public ImageView mIsExpand;
    }
}
