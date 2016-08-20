package com.android.smartShow.view;

import java.util.ArrayList;

import com.android.smartShow.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ScreenItemView extends RelativeLayout implements View.OnClickListener {

    private Context mContext = null;
    
    private ArrayList<ImageView> mImageViewList = new ArrayList<ImageView>();
    
    private int mScreenCount;
    
    private static int[] sImageViewIds = {
        R.id.screen_imageview_1,
        R.id.screen_imageview_2,
        R.id.screen_imageview_3,
        R.id.screen_imageview_4,
        R.id.screen_imageview_5,
        R.id.screen_imageview_6,
        R.id.screen_imageview_7,
        R.id.screen_imageview_8,
        R.id.screen_imageview_9
    };
    
    public ScreenItemView(Context context) {
        super(context);
        mContext = context;
    }
    
    public void setScreens(int screenCount) {
        mScreenCount = screenCount;

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        int layoutId = 0;
        if (mScreenCount <= 1) {
            layoutId = R.layout.screen_item_one_image_layout;
        } else if (mScreenCount <= 4) {
            layoutId = R.layout.screen_item_four_image_layout;
        } else {
            layoutId = R.layout.screen_item_nine_image_layout;
        }
        View parentView = inflater.inflate(layoutId, this, true);
        for (int index = 0; index < mScreenCount && index < sImageViewIds.length; index++) {
            mImageViewList.add((ImageView) parentView.findViewById(sImageViewIds[index]));
        }
        
        // set vision and invision
        for (int index = 0; index < mScreenCount && index < sImageViewIds.length; index++) {
            mImageViewList.get(index).setVisibility(View.VISIBLE);
        }
    }

    public ScreenItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public void onClick(View view) {
    }
}
