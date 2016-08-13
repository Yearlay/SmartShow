package com.android.smartShow.adapter;

import java.util.ArrayList;

import com.android.smartShow.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ScreenAdapter extends BaseAdapter {
    
    private ArrayList<Integer> mList = new ArrayList<Integer>();
    private Context mContext;
    private LayoutInflater inflater = null;

    public ScreenAdapter(Context context) {
        super();
        inflater = LayoutInflater.from(context);
        
        for (int i=0; i<11; i++) {
            mList.add(new Integer(R.drawable.default_screen));
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
            ImageView imageView = (ImageView) view.findViewById(R.id.screen_imageview);
            imageView.setImageResource(mList.get(position));
        }
        return view;
    }

}
