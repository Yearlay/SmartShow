package com.android.smartshowbox.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import com.android.smartshowbox.R;
import com.android.smartshowbox.inf.MenuClickListener;
import com.android.smartshowbox.menu.MyMenu;
import com.project.template.logic.callback.RequestCallback;
import com.project.template.ui.utils.HandlerCache;
import com.project.template.ui.utils.HandlerInterface;
import com.project.template.ui.utils.TemplateHandler;

public abstract class ActivityBase extends FragmentActivity
        implements OnClickListener, RequestCallback, HandlerInterface {
    
    
    protected Intent mLastIntent;
    
    protected ActionBar mActionBar;
    
    enum ActivityStatus {
        ACTIVITY_RESUME,
        ACTIVITY_PAUSE,
        ACTIVITY_STOP,
        ACTIVITY_DESTROY,
    }

    private ActivityStatus mActivityStatus = ActivityStatus.ACTIVITY_DESTROY;

    public ActivityStatus getActivityStatus() {
        return mActivityStatus;
    }

    public void setActivityStatus(ActivityStatus mActivityStatus) {
        this.mActivityStatus = mActivityStatus;
    }

    

    abstract public void onBackPress(View view);

    abstract public void onSearchPress(View view);

    // 如果为true,表示需要执行menu，为false，不要menu，自定义点击事件.
    abstract public boolean onMenuPress(View view);

    

    public boolean onCreateMyOptionMenu(MyMenu menu, MenuClickListener menuClickListener) {
        return true;
    }
    
    protected TemplateHandler mHandler;
    private HandlerCache mHandlerCache;

    private void initHandlerCache() {
        mHandlerCache = new HandlerCache();
        mHandlerCache.registerHandler(mHandler);
    }
    
    /**
     * 初始化HandlerCache，保证当前Activity可以接收到通知消息
     */
    private void initBaseData() {
        mLastIntent = getIntent();
        mHandler = new TemplateHandler(this);
        initHandlerCache();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBaseData(); // 初始化HandlerCache，保证当前Activity可以接收到通知消息
        initData(savedInstanceState);
        initWidget();
        initListener();
        initActionBar();
    }
    
    protected void initActionBar() {
        mActionBar = getActionBar();
        if (null != mActionBar) {
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(true);
            mActionBar.setLogo(R.drawable.logo);
            Drawable d = new Drawable() {
                @Override
                public void setColorFilter(ColorFilter cf) {
                }
                
                @Override
                public void setAlpha(int alpha) {
                }
                
                @Override
                public int getOpacity() {
                    return 0;
                }
                
                @Override
                public void draw(Canvas canvas) {
                    canvas.drawColor(getResources().getColor(R.color.fragment_title_background));
                }
            };
            mActionBar.setBackgroundDrawable(d);
        }
    }
    
    @Override
    protected void onStart() {
        // 调用抽象方法。
        refreshView();
        super.onStart();
    }

    @Override
    public void onResume() {
        setActivityStatus(ActivityStatus.ACTIVITY_RESUME);
        super.onResume();
    }

    @Override
    public void onStop() {
        setActivityStatus(ActivityStatus.ACTIVITY_STOP);
        super.onStop();
    }

    @Override
    public void onPause() {
        setActivityStatus(ActivityStatus.ACTIVITY_PAUSE);
        super.onPause();
    }
    
    @Override
    public void onDestroy() {
        setActivityStatus(ActivityStatus.ACTIVITY_DESTROY);
        // 调用抽象方法。
        clearData();
        super.onDestroy();
    }
    
    @Override
    public void finish() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mHandlerCache != null) {
            mHandlerCache.unRegisterHandler(mHandler);
        }
        super.finish();
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

    /**
     * 批量设置View对象的点击事件。
     * @param onClick
     * @param views 要设置点击事件的View对象的数组
     */
    protected void bindOnClickLister(OnClickListener onClick, View... views) {
        if (onClick != null && views != null) {
            for (View view : views) {
                view.setOnClickListener(onClick);
            }
        }
    }

    /**
     * 用于批量处理触摸事件
     * @param onTouch
     * @param views
     */
    protected void bindOnTouchLister(OnTouchListener onTouch, View... views) {
        if (onTouch != null && views != null) {
            for (View view : views) {
                view.setOnTouchListener(onTouch);
            }
        }
    }
    
    /**
     * 该接口用来初始化Intent传递的数据。
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 该接口用来初始化视图控件
     */
    protected abstract void initWidget();
    
    /**
     * <b>Description:</b> <br/>
     * 当关键数据发生改变可以调用此方法。<br/>
     * 在 {@link ActivityBase#onStart()}中也调用了此方法。
     */
    protected abstract void refreshView();

    /**
     * 该接口用来初始化监听器
     */
    protected abstract void initListener();
    
    /**
     * <b>Description:</b> 清空数据。<br/>
     * 在{@link ActivityBase#onDestroy()}中调用了此方法。
     */
    protected abstract void clearData();
}
