package com.android.smartShow.actionbar;

import com.android.smartShow.R;
import com.android.smartShow.activity.BaseActivity;
import com.android.smartShow.activity.DeviceListActivity;
import com.android.smartShow.activity.MainActivity;
import com.android.smartShow.adapter.StoreData;
import com.android.smartShow.adapter.StoreData.StoreDataInfo;
import com.android.smartShow.fragment.MainPageOneFragment;
import com.project.template.utils.Log;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActionBar extends BaseActionBar {
    public static final int MAIN_PAGE_ONE = 1;
    public static final int MAIN_PAGE_TWO = 2;
    public static final int MAIN_PAGE_THREE = 3;
    public static final int MAIN_PAGE_FOUR = 4;

    public MainActionBar(BaseActivity activity) {
        super(activity);
    }

    public void udpateActionBar(int stateValue) {
        super.udpateActionBar(stateValue);

        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.main_actionbar);
        View rootView = mActionBar.getCustomView();
        ImageView imageViewLeft = (ImageView) rootView.findViewById(R.id.actionbar_image_left);
        TextView titleTextView = (TextView) rootView.findViewById(R.id.actionbar_title);
        ImageView imageViewRight = (ImageView) rootView.findViewById(R.id.actionbar_image_right);
        imageViewRight.setOnClickListener(this);
        titleTextView.setTextColor(mActivity.getResources().getColor(R.color.fragment_title));
        switch (stateValue) {
            case MAIN_PAGE_ONE:
                imageViewLeft.setImageResource(R.drawable.main_page_one);
                titleTextView.setText(R.string.main_page_hotseat_one_message);
                imageViewRight.setImageResource(R.drawable.add_device);
                imageViewRight.setVisibility(View.VISIBLE);
                break;
            case MAIN_PAGE_TWO:
                imageViewLeft.setImageResource(R.drawable.main_page_two);
                titleTextView.setText(R.string.main_page_hotseat_two_message);
                imageViewRight.setVisibility(View.INVISIBLE);
                break;
            case MAIN_PAGE_THREE:
                imageViewLeft.setImageResource(R.drawable.main_page_three);
                titleTextView.setText(R.string.main_page_hotseat_three_message);
                imageViewRight.setVisibility(View.INVISIBLE);
                break;
            case MAIN_PAGE_FOUR:
                imageViewLeft.setImageResource(R.drawable.main_page_four);
                titleTextView.setText(R.string.main_page_hotseat_four_message);
                imageViewRight.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    
    }

    @Override
    public void onClick(View view) {
        switch (mStateValue) {
            case MAIN_PAGE_ONE:
                onClickFromPageOne(view);
                break;

            default:
                break;
        }
        
    }
    
    private void onClickFromPageOne(View view) {
        switch (view.getId()) {
            case R.id.actionbar_image_right:
                final EditText editText = new EditText(mActivity);
                new AlertDialog.Builder(mActivity)
                .setTitle(R.string.add_store_title)
                .setView(editText)
                .setPositiveButton(R.string.OK, 
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                StoreDataInfo storeDataInfo = new StoreDataInfo();
                                storeDataInfo.mStoreName = editText.getText().toString();
                                storeDataInfo.mStoreDrawable = mActivity.getResources().getDrawable(R.drawable.store);
                                StoreData.instance(mActivity).getData().add(storeDataInfo);
                            }
                        }
                )
                .setNegativeButton(R.string.CANCEL, 
                        new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
                break;

            default:
                break;
        }
    }

}
