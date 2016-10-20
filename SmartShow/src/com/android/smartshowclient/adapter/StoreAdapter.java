package com.android.smartshowclient.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.smartshowclient.R;
import com.android.smartshowclient.adapter.StoreData.StoreDataInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StoreAdapter extends BaseAdapter{
    private List<StoreDataInfo> mData;
    private Context mContext;
    public StoreAdapter(Context context) {
        mContext = context;
        mData = StoreData.instance(context).getData();
    } 
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v;
        if (convertView == null) {
            v = inflater.inflate(R.layout.store_info, null);
        } else {
            v = convertView;
        }
        ImageView storeImage = (ImageView) v.findViewById(R.id.store_image);
        storeImage.setImageDrawable(mData.get(position).mStoreDrawable);
        TextView textView = (TextView) v.findViewById(R.id.store_name);
        textView.setText(mData.get(position).mStoreName);
        return v;
    }

}
