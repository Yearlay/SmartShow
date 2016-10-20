package com.android.smartshowclient.activity;

import java.util.ArrayList;
import java.util.Map;

import com.android.smartshowclient.R;
import com.android.smartshowclient.actionbar.ActionBarManager;
import com.android.smartshowclient.actionbar.MainActionBar;
import com.android.smartshowclient.adapter.MainViewPagerAdapter;
import com.android.smartshowclient.fragment.BaseFragment;
import com.android.smartshowclient.fragment.MainPageFourFragment;
import com.android.smartshowclient.fragment.MainPageOneFragment;
import com.android.smartshowclient.fragment.MainPageThreeFragment;
import com.android.smartshowclient.fragment.MainPageTwoFragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.RadioButton;

public class MainActivity extends BaseActivity {
    
    MainViewPagerAdapter mMainViewPagerAdapter;
    
    ViewPager mViewPager;
    BaseFragment mPageOneFragment;
    BaseFragment mPageTwoFragment;
    BaseFragment mPageThreeFragment;
    BaseFragment mPageFourFragment;
    
    RadioButton mOneRadioButton;
    RadioButton mTwoRadioButton;
    RadioButton mThreeRadioButton;
    RadioButton mFourRadioButton;

    /* -------------------------------------------------------------------- */
    @Override
    public void onResult(int event, boolean isok, Map<String, Object> result) {
    }

    @Override
    public void handleCommonMsg(Message msg) {
    }

    @Override
    public boolean handleNotifyMessage(Message msg) {
        return false;
    }

    @Override
    public boolean handleUIMessage(Message msg) {
        return false;
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onBackPress(View view) {
    }

    @Override
    public void onSearchPress(View view) {
    }

    @Override
    public boolean onMenuPress(View view) {
        return false;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
     // new fragment object.
        mPageOneFragment = new MainPageOneFragment();
        mPageTwoFragment = new MainPageTwoFragment();
        mPageThreeFragment = new MainPageThreeFragment();
        mPageFourFragment = new MainPageFourFragment();

        // add fragment list
        ArrayList<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(mPageOneFragment);
        fragmentList.add(mPageTwoFragment);
        fragmentList.add(mPageThreeFragment);
        fragmentList.add(mPageFourFragment);

        // add fragment name
        ArrayList<String> tabNameList = new ArrayList<String>();
        tabNameList.add(mPageOneFragment.getClass().getSimpleName());
        tabNameList.add(mPageTwoFragment.getClass().getSimpleName());
        tabNameList.add(mPageThreeFragment.getClass().getSimpleName());
        tabNameList.add(mPageFourFragment.getClass().getSimpleName());

        // init fragment adapter
        FragmentManager fm = this.getSupportFragmentManager();
        mMainViewPagerAdapter = new MainViewPagerAdapter(this, fm, fragmentList, tabNameList, null);
    }
    
    @Override
    protected void initActionBar() {
        super.initActionBar();
        ActionBarManager.instance().getActionBar(ActionBarManager.MAIN_ACTION_BAR, this)
                .udpateActionBar(MainActionBar.MAIN_PAGE_ONE);
    }


    @Override
    protected void initWidget() {
        setContentView(R.layout.main_hotseat);
        
        setTitle(R.string.main_page_hotseat_one_message);
        
        mOneRadioButton = (RadioButton) findViewById(R.id.main_page_one);
        mTwoRadioButton = (RadioButton) findViewById(R.id.main_page_two);
        mThreeRadioButton = (RadioButton) findViewById(R.id.main_page_three);
        mFourRadioButton = (RadioButton) findViewById(R.id.main_page_four);
        
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mMainViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);

        mOneRadioButton.setChecked(true);
    }

    @Override
    protected void refreshView() {
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void initListener() {
        bindOnClickLister(this, mOneRadioButton, mTwoRadioButton, mThreeRadioButton,
                mFourRadioButton);
        
        // 设置viewpager和上方标题的关联关系
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mMainViewPagerAdapter.getItem(position);
                switch (position) {
                    case 0:
                        changeToMainPageOne();
                        break;
                    case 1:
                        changeToMainPageTwo();
                        break;
                    case 2:
                        changToMainPageThree();
                        break;
                    case 3:
                        changeToMainPageFour();
                        break;
                }
            }
    
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
    
            }
    
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
    
            }
        });
    }
    

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_page_one:
                changeToMainPageOne();
                mViewPager.setCurrentItem(0);
                break;
            case R.id.main_page_two:
                changeToMainPageTwo();
                mViewPager.setCurrentItem(1);
                break;
            case R.id.main_page_three:
                changToMainPageThree();
                mViewPager.setCurrentItem(2);
                break;
            case R.id.main_page_four:
                changeToMainPageFour();
                mViewPager.setCurrentItem(3);
                break;

            default:
                break;
        }
    }

    @Override
    protected void clearData() {
    }
    /* -------------------------------------------------------------------- */
    
    private void changeToMainPageOne() {
        mOneRadioButton.setChecked(true);
        setTitle(R.string.main_page_hotseat_one_message);
        ActionBarManager.instance().getActionBar(ActionBarManager.MAIN_ACTION_BAR, this)
                .udpateActionBar(MainActionBar.MAIN_PAGE_ONE);
    }

    private void changeToMainPageTwo() {
        mTwoRadioButton.setChecked(true);
        setTitle(R.string.main_page_hotseat_two_message);
        ActionBarManager.instance().getActionBar(ActionBarManager.MAIN_ACTION_BAR, this)
                .udpateActionBar(MainActionBar.MAIN_PAGE_TWO);
    }

    private void changToMainPageThree() {
        mThreeRadioButton.setChecked(true);
        setTitle(R.string.main_page_hotseat_three_message);
        ActionBarManager.instance().getActionBar(ActionBarManager.MAIN_ACTION_BAR, this)
                .udpateActionBar(MainActionBar.MAIN_PAGE_THREE);
    }

    private void changeToMainPageFour() {
        mFourRadioButton.setChecked(true);
        setTitle(R.string.main_page_hotseat_four_message);
        ActionBarManager.instance().getActionBar(ActionBarManager.MAIN_ACTION_BAR, this)
                .udpateActionBar(MainActionBar.MAIN_PAGE_FOUR);
    }
}
