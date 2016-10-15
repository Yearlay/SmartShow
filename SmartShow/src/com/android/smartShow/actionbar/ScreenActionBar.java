package com.android.smartShow.actionbar;

import com.android.smartShow.R;
import com.android.smartShow.activity.BaseActivity;
import com.android.smartShow.activity.DeviceAddActivity;
import com.android.smartShow.activity.DeviceListActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ScreenActionBar extends BaseActionBar {
    public static final int SCREEN_LIST = 1;

    public ScreenActionBar(BaseActivity activity) {
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
            case SCREEN_LIST:
                titleTextView.setText(R.string.screen_manager_of_store_title);
                imageViewRight.setImageResource(R.drawable.device_list);
                imageViewRight.setVisibility(View.VISIBLE);
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
                    case SCREEN_LIST:
                        intent = new Intent(mActivity, DeviceListActivity.class);
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
