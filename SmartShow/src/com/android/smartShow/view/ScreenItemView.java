package com.android.smartShow.view;

import com.android.smartShow.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class ScreenItemView extends RelativeLayout implements View.OnClickListener {

    private Context mContext = null;
    
    public ScreenItemView(Context context) {
        super(context);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View parentView = inflater.inflate(R.layout.store_title_item_layout, this,
                true);
    }

    public ScreenItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public void onClick(View view) {
    }
}
