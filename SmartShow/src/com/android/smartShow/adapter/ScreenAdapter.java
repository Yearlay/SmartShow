package com.android.smartShow.adapter;

import java.util.ArrayList;

import com.android.smartShow.R;
import com.android.smartShow.view.ScreenItemView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ScreenAdapter extends BaseAdapter {
    
    private ArrayList<ScreenItem> mList = new ArrayList<ScreenItem>();
    private Context mContext;
    private LayoutInflater inflater = null;
    
    private static int[] sImageCount = {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 1
    };
    
    public class ScreenItem {
        int iamgeCount;
        String screenName;

        public ScreenItem(int imageCount, String screenName) {
            super();
            this.iamgeCount = imageCount;
            this.screenName = screenName;
        }
    }

    public ScreenAdapter(Context context) {
        super();
        inflater = LayoutInflater.from(context);
        
        String[] screenNameList = context.getResources().getStringArray(R.array.screen_list);
        for (int index = 0; index < screenNameList.length && index < sImageCount.length; index++) {
            mList.add(new ScreenItem(sImageCount[index], screenNameList[index]));
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
            view = inflater.inflate(R.layout.screen_item_layout, null);
            ScreenItemView screenItemView = (ScreenItemView) view.findViewById(R.id.screen_imageview);
            screenItemView.setScreens(mList.get(position).iamgeCount);
            TextView textView = (TextView) view.findViewById(R.id.screen_name);
            textView.setText(mList.get(position).screenName);
        }
        return view;
    }

}
