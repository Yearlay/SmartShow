
package com.android.smartshowclient.adapter;

import java.util.ArrayList;

import com.android.smartshowclient.fragment.BaseFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    public final String TAG = MainViewPagerAdapter.this.getClass().getSimpleName();

    public static final int NO_TITLE = 0;

    /**
     * 默认方式
     */
    public static final int TYPE_TEXT_TAB = 1;

    /**
     * 资源图标
     */
    public static final int TYPE_ICON_TAB = 2;

    /**
     * 资源图文
     */
    public static final int TYPE_ICON_TEXT_TAB = 3;

    private int mType;

    private int mCount = 4;

    private ArrayList<BaseFragment> mFragmentList;

    private ArrayList<String> mTabNameTextList;

    private ArrayList<Integer> mTabIconResourceList;

    private ArrayList<Bitmap> mTabIconBitmapList;

    Context mContext;

    /**
     * fragmentList 不能为空<br/>
     * tabNameList 和 tabIconResourceList至少一个不为空<br/>
     * 
     * @param context
     * @param fm
     * @param fragmentList 不能为空
     * @param tabNameList 标题名称列表
     * @param tabIconResourceList 标题资源图片列表
     */
    public MainViewPagerAdapter(Context context, FragmentManager fm,
            ArrayList<BaseFragment> fragmentList, ArrayList<String> tabNameList,
            ArrayList<Integer> tabIconResourceList) {
        super(fm);
        mContext = context;
        mFragmentList = fragmentList;
        mTabNameTextList = tabNameList;
        mTabIconResourceList = tabIconResourceList;

        boolean hasIcon = false;
        boolean hasText = false;
        if (mTabIconResourceList != null && mTabIconResourceList.size() > 0) {
            hasIcon = true;
        }
        if (mTabNameTextList != null && mTabNameTextList.size() > 0) {
            hasText = true;
        }

        if (hasIcon && hasText) {
            mType = TYPE_ICON_TEXT_TAB;
        } else if (hasIcon) {
            mType = TYPE_ICON_TEXT_TAB;
        } else if (hasText) {
            mType = TYPE_ICON_TEXT_TAB;
        } else {
            mType = NO_TITLE;
        }

        // TODO 根据资源生成 mTabIconBitmapList
        if (hasIcon) {
            mTabIconBitmapList = new ArrayList<Bitmap>();
            Options options = new Options();

            int count = getCount();
            for (int i = 0; i < count; i++) {
                mTabIconBitmapList.add(BitmapFactory.decodeResource(context.getResources(),
                        mTabIconResourceList.get(i), options));

            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        Log.i(TAG, "getItem position = " + position);
        if (mFragmentList != null) {
            return mFragmentList.get(position);
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTabNameTextList != null) {
            return mTabNameTextList.get(position);
        }
        return "测试";
    }

    public Bitmap getPageTitleIcon(int position) {
        if (mTabIconBitmapList != null) {
            return mTabIconBitmapList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (mCount == 0) {
            mCount = mFragmentList.size();
        }
        return mCount;
    }

    public int getType() {
        return mType;
    }

}
