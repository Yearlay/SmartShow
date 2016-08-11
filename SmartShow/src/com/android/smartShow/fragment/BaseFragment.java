package com.android.smartShow.fragment;

import com.android.smartShow.view.OnePieceProgressDialog;
import com.project.template.logic.callback.RequestCallback;
import com.project.template.ui.utils.HandlerCache;
import com.project.template.ui.utils.HandlerInterface;
import com.project.template.ui.utils.TemplateHandler;
import com.project.template.utils.Log;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements
        HandlerInterface, RequestCallback {

    protected final String TAG = this.getClass().getSimpleName();

    protected Activity mActivity;

    protected TemplateHandler mHandler;

    protected Bundle mBundle;

    protected HandlerCache mHandlerCache;

    private View mRootView;

    protected boolean isPauseOnScroll = false;

    protected boolean isPauseOnFling = true;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if (null == savedInstanceState) {
            mBundle = (null == getArguments()) ? new Bundle() : 
                new Bundle(getArguments());
        } else {
            mBundle = savedInstanceState;
        }
        initData(mBundle);
        mHandler = new TemplateHandler(this);
        initHandlerCache();
    }

    private void initHandlerCache() {
        mHandlerCache = new HandlerCache();
        mHandlerCache.registerHandler(mHandler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        if (mRootView == null) {
            mRootView = initView(inflater, container, mBundle);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }

        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint " + isVisibleToUser);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(mBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (mHandlerCache != null) {
            mHandlerCache.unRegisterHandler(mHandler);
        }
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        super.onDetach();
    }

    @Override
    public final void handleCommonMsg(Message msg) {

    }

    public void setAutoDismiss(final Context context, final Dialog dialog,
            int delay) {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (context != null && dialog != null && dialog.isShowing()) {
                    if (context instanceof Activity) {
                        if (!((Activity) context).isFinishing()) {
                            dialog.dismiss();
                        }
                    }
                }
            }
        }, delay <= 0 ? 1000 : delay);
    }

    public void resetProgressMessage(Context context, OnePieceProgressDialog dialog,
            String message, Drawable d) {
        if (context != null && dialog != null && dialog.isShowing()) {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    dialog.resetMessage(message);
                    dialog.setMessageDrawable(d);
                }
            }
        }
    }

    public void dismissDialogSafety(Context context, Dialog dialog) {
        if (context != null && dialog != null && dialog.isShowing()) {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    dialog.dismiss();
                }
            }
        }
    }

    protected void bindOnClickLister(OnClickListener onClick, View... views) {
        if (onClick != null && views != null) {
            for (View view : views) {
                view.setOnClickListener(onClick);
            }
        }
    }

    protected void getActivityTitle() {
        mActivity.getTitle();
    }

    protected abstract String setFragmentTag();

    protected abstract void initData(Bundle bundle);

    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle bundle);
}
