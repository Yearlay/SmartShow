package com.project.template.templateActivity;

import android.app.ActionBar;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.project.template.inf.MenuClickListener;
import com.project.template.menu.MyMenu;
import com.project.template.utils.InvokeUtils;

public abstract class BaseActivity extends FragmentActivity {
    private Button mBackButton;
    private Button mSearchButton;
    private Button mMenuButton;
    private TextView mTitleTextView;
    private ActionBar mActionBar;
    private MenuClickListener mMenuClickListener;
    private MyMenu myMenu;
    private PopupWindow mMenuPopup;
    private boolean mMenuShowing = false;
    private int ID_BACK;
    private int ID_MENU;
    private int ID_SEARCH;
    private int DRAWABLE_MENU;
    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == ID_BACK) {
                onBackPress(view);
            }
            if (view.getId() == ID_SEARCH) {
                onSearchPress(view);
            }
            if (view.getId() == ID_MENU) {
                if (onMenuPress(view)) {
                    Rect actionBarRec = new Rect();
                    mActionBar.getCustomView().getGlobalVisibleRect(actionBarRec);
                    Rect menuIconRec = new Rect();
                    view.getGlobalVisibleRect(menuIconRec);
                    LinearLayout v = myMenu.createMenu(getApplicationContext(), mMenuClickListener);
                    if (mMenuPopup == null) {
                        mMenuPopup = new PopupWindow(v, 200, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                        Drawable bg = getResources().getDrawable(DRAWABLE_MENU);
                        mMenuPopup.setBackgroundDrawable(bg);
                        mMenuPopup.setOutsideTouchable(true);
                        mMenuPopup.setTouchInterceptor(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if (mMenuShowing) {
                                    mMenuPopup.dismiss();
                                }
                                return false;
                            }
                        });
                        mMenuPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                mMenuShowing = false;
                            }
                        });
                    }
                    if (mMenuShowing) {
                        mMenuPopup.dismiss();
                    } else {
                        mMenuPopup.setFocusable(true);
                        mMenuPopup.showAsDropDown(view, 0, actionBarRec.bottom - menuIconRec.bottom);
                        mMenuShowing = true;
                    }

                }
            }
        }

    };

    abstract public void onBackPress(View view);

    abstract public void onSearchPress(View view);

    // 如果为true,表示需要执行menu，为false，不要menu，自定义点击事件.
    abstract public boolean onMenuPress(View view);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 设置返回图标
        if (mBackButton != null) {
            mMenuButton.setClickable(backEnable());
            if (backEnable()) {
                mBackButton.setBackgroundDrawable(setBackRes() == null ? getResources().getDrawable(InvokeUtils.create(getApplicationContext()).getIdByName("drawable", "template_back")) : setBackRes());
                mBackButton.setOnClickListener(listener);
                mBackButton.setVisibility(View.VISIBLE);
            } else {
                mBackButton.setVisibility(View.INVISIBLE);
            }
        }
        // 设置搜索图标
        if (mSearchButton != null) {
            mSearchButton.setClickable(searchEnable());
            if (searchEnable()) {
                mSearchButton.setBackgroundDrawable(setSearchRes() == null ? getResources().getDrawable(InvokeUtils.create(getApplicationContext()).getIdByName("drawable", "template_search")) : setSearchRes());
                mSearchButton.setOnClickListener(listener);
                mSearchButton.setVisibility(View.VISIBLE);
            } else {
                mSearchButton.setVisibility(View.INVISIBLE);
            }
        }
        // 设置更多图标
        if (mMenuButton != null) {
            mMenuButton.setClickable(menuEnable());
            if (menuEnable()) {
                mMenuButton.setBackgroundDrawable(setMenuRes() == null ? getResources().getDrawable(InvokeUtils.create(getApplicationContext()).getIdByName("drawable", "template_menu")) : setMenuRes());
                mMenuButton.setVisibility(View.VISIBLE);
                mMenuButton.setOnClickListener(listener);
            } else {
                mMenuButton.setVisibility(View.INVISIBLE);
            }
        }
        // 设置标题
        if (mTitleTextView != null) {
            mTitleTextView.setText(TextUtils.isEmpty(setTitleTextView()) ? getString(InvokeUtils.create(getApplicationContext()).getIdByName("string", "action_bar_title")) : setTitleTextView());
        }
        onCreateMyOptionMenu(myMenu, mMenuClickListener);
        return true;
    }

    public boolean onCreateMyOptionMenu(MyMenu menu, MenuClickListener menuClickListener) {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initView();
        super.onCreate(savedInstanceState);
    }

    private void initView() {
        DRAWABLE_MENU = InvokeUtils.create(getApplicationContext()).getIdByName("drawable", "template_menu_bg");
        ID_BACK = InvokeUtils.create(getApplicationContext()).getIdByName("id", "back_button");
        ID_SEARCH = InvokeUtils.create(getApplicationContext()).getIdByName("id", "search_button");
        ID_MENU = InvokeUtils.create(getApplicationContext()).getIdByName("id", "menu_button");
        mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(InvokeUtils.create(getApplicationContext()).getIdByName("layout", "template_activity_action_bar"));
        mBackButton = (Button) mActionBar.getCustomView().findViewById(ID_BACK);
        mSearchButton = (Button) mActionBar.getCustomView().findViewById(ID_SEARCH);
        mMenuButton = (Button) mActionBar.getCustomView().findViewById(ID_MENU);
        mTitleTextView = (TextView) mActionBar.getCustomView().findViewById(InvokeUtils.create(getApplicationContext()).getIdByName("id", "title"));
        myMenu = new MyMenu(this);
    }

    public boolean backEnable() {
        return true;
    }

    public boolean searchEnable() {
        return true;
    }

    public boolean menuEnable() {
        return true;
    }

    public Drawable setBackRes() {
        return null;
    }

    public Drawable setSearchRes() {
        return null;
    }

    public Drawable setMenuRes() {
        return null;
    }

    public String setTitleTextView() {
        return "";
    }

    public int getActionBarHeight() {
        return mActionBar.getHeight();
    }
}
