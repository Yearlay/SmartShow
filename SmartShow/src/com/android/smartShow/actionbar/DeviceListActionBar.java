package com.android.smartShow.actionbar;

import com.android.smartShow.R;
import com.android.smartShow.activity.BaseActivity;
import com.android.smartShow.activity.DeviceListActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DeviceListActionBar extends BaseActionBar {

    public DeviceListActionBar(BaseActivity activity) {
        super(activity);
    }

    public void udpateActionBar(int stateValue) {
        super.udpateActionBar(stateValue);

        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.device_actionbar);
        View rootView = mActionBar.getCustomView();
        ImageView imageViewLeft = (ImageView) rootView.findViewById(R.id.actionbar_image_left);
        imageViewLeft.setOnClickListener(this);
        TextView titleTextView = (TextView) rootView.findViewById(R.id.actionbar_title);
        ImageView imageViewRight = (ImageView) rootView.findViewById(R.id.actionbar_image_right);
        imageViewRight.setOnClickListener(this);
        titleTextView.setTextColor(mActivity.getResources().getColor(R.color.fragment_title));
        
        imageViewLeft.setImageResource(R.drawable.back);
        titleTextView.setText(R.string.devicelist_activity_name);
        imageViewRight.setImageResource(R.drawable.add_device);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actionbar_image_left:
                mActivity.onBackPressed();
                break;

            default:
                break;
        }
    }
}
