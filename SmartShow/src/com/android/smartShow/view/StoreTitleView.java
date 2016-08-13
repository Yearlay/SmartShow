package com.android.smartShow.view;

import com.android.smartShow.R;
import com.android.smartShow.fragment.MainPageOneFragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StoreTitleView extends RelativeLayout implements View.OnClickListener {

    private Context mContext = null;
    
    private TextView mStoreTitle = null;
    private ImageView mBottomline = null;
    
    private boolean mSelected = false;
    
    private MainPageOneFragment mPageOneFragment;

    public MainPageOneFragment getPageOneFragment() {
        return mPageOneFragment;
    }

    public void setPageOneFragment(MainPageOneFragment mPageOneFragment) {
        this.mPageOneFragment = mPageOneFragment;
    }

    public TextView getStoreTitle() {
        return mStoreTitle;
    }

    public void setStoreTitle(TextView storeTitle) {
        this.mStoreTitle = storeTitle;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        this.mSelected = selected;
        mBottomline.setVisibility(selected ? View.INVISIBLE : View.VISIBLE);
    }

    public StoreTitleView(Context context) {
        super(context);
        mContext = context;
    }

    public StoreTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View parentView = inflater.inflate(R.layout.store_title_item_layout, this,
                true);
        mStoreTitle = (TextView) findViewById(R.id.store_name);
        mStoreTitle.setOnClickListener(this);
        mBottomline = (ImageView) findViewById(R.id.store_right_line);
        
        setSelected(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.store_name:
                for (StoreTitleView storeTitleView : mPageOneFragment.getStoreItemList()) {
                    storeTitleView.setSelected(false);
                }
                mPageOneFragment.setCurrentStoreTitleView(this);
                break;

            default:
                break;
        }
    }

}
