package com.android.smartShow.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.android.smartShow.R;

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
        mData = new ArrayList<StoreDataInfo>();
        StoreDataInfo storeDataInfo;
        mContext = context;
        for(int i = 0; i < 10;i++) {
            storeDataInfo = new StoreDataInfo();
            storeDataInfo.mStoreName = "测试门店";
            storeDataInfo.mStoreDrawable = mContext.getResources().getDrawable(R.drawable.store);
            mData.add(storeDataInfo);
        }
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
