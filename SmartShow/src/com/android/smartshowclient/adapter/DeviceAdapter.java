package com.android.smartshowclient.adapter;

import java.util.ArrayList;

import com.android.smartshowclient.R;
import com.android.smartshowclient.view.ScreenItemView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DeviceAdapter extends BaseAdapter {
    
    private ArrayList<DeviceItem> mList = new ArrayList<DeviceItem>();
    private LayoutInflater inflater = null;
    
    public class DeviceItem {
        int imageId;
        String screenName;

        public DeviceItem(int imageId, String screenName) {
            super();
            this.imageId = imageId;
            this.screenName = screenName;
        }
    }

    public DeviceAdapter(Context context) {
        super();
        inflater = LayoutInflater.from(context);
        
        String[] screenNameList = context.getResources().getStringArray(R.array.device_list);
        for (int index = 0; index < screenNameList.length; index++) {
            mList.add(new DeviceItem(R.drawable.default_screen, screenNameList[index]));
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = null;
        if (inflater != null) {
            view = inflater.inflate(R.layout.device_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.device_imageview);
            imageView.setImageResource(mList.get(position).imageId);
            TextView textView = (TextView) view.findViewById(R.id.device_name);
            textView.setText(mList.get(position).screenName);
        }
        return view;
    }

}
