package com.android.smartshowclient.actionbar;

import com.android.smartshowclient.R;
import com.android.smartshowclient.activity.BaseActivity;
import com.android.smartshowclient.activity.DeviceAddActivity;
import com.android.smartshowclient.activity.DeviceListActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.location.Address;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DeviceActionBar extends BaseActionBar {
    public static final int DEVICE_LIST = 1;
    public static final int DEVICE_INFO = 2;
    public static final int DEVICE_ADD = 3;

    public DeviceActionBar(BaseActivity activity) {
        super(activity);
    }

    public void udpateActionBar(int stateValue) {
        super.udpateActionBar(stateValue);

        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.common_actionbar);
        View rootView = mActionBar.getCustomView();
        ImageView imageViewLeft = (ImageView) rootView.findViewById(R.id.actionbar_image_left);
        imageViewLeft.setOnClickListener(this);
        TextView titleTextView = (TextView) rootView.findViewById(R.id.actionbar_title);
        ImageView imageViewRight = (ImageView) rootView.findViewById(R.id.actionbar_image_right);
        imageViewRight.setOnClickListener(this);
        titleTextView.setTextColor(mActivity.getResources().getColor(R.color.fragment_title));
        
        imageViewLeft.setImageResource(R.drawable.back);
        switch (stateValue) {
            case DEVICE_LIST:
                titleTextView.setText(R.string.devicelist_activity_name);
                imageViewRight.setImageResource(R.drawable.add_device);
                imageViewRight.setVisibility(View.VISIBLE);
                break;
                
            case DEVICE_ADD:
                titleTextView.setText(R.string.deviceadd_activity_name);
                imageViewRight.setVisibility(View.INVISIBLE);
                break;
                
            case DEVICE_INFO:
                titleTextView.setText(R.string.deviceinfo_activity_name);
                imageViewRight.setVisibility(View.INVISIBLE);
                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actionbar_image_left:
                mActivity.onBackPressed();
                break;
            case R.id.actionbar_image_right:
                Log.e("yearlay", "actionbar_image_right mStateValue: " + mStateValue);
                Intent intent = null;
                switch (mStateValue) {
                    case DEVICE_LIST:
                        intent = new Intent(mActivity, DeviceAddActivity.class);
                        break;

                    default:
                        break;
                }
                if (intent != null) {
                    mActivity.startActivity(intent);
                }
                break;

            default:
                break;
        }
    }
}
