package com.android.smartShow.actionbar;

import com.android.smartShow.R;
import com.android.smartShow.activity.BaseActivity;

import android.app.ActionBar;
import android.view.View;
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
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.main_actionbar);
        View rootView = mActionBar.getCustomView();
        ImageView imageView = (ImageView) rootView.findViewById(R.id.actionbar_image);
        TextView titleTextView = (TextView) rootView.findViewById(R.id.actionbar_title);
        titleTextView.setTextColor(mActivity.getResources().getColor(R.color.fragment_title));
        switch (stateValue) {
            case MAIN_PAGE_ONE:
                imageView.setImageResource(R.drawable.main_page_one);
                titleTextView.setText(R.string.main_page_hotseat_one_message);
                break;
            case MAIN_PAGE_TWO:
                imageView.setImageResource(R.drawable.main_page_two);
                titleTextView.setText(R.string.main_page_hotseat_two_message);
                break;
            case MAIN_PAGE_THREE:
                imageView.setImageResource(R.drawable.main_page_three);
                titleTextView.setText(R.string.main_page_hotseat_three_message);
                break;
            case MAIN_PAGE_FOUR:
                imageView.setImageResource(R.drawable.main_page_four);
                titleTextView.setText(R.string.main_page_hotseat_four_message);
                break;
            default:
                break;
        }
    
    }

}
