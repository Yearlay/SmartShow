package com.android.smartshowclient.view;

import com.android.smartshowclient.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * 注意context的赋值：只能使用Activity.this；不能使用getApplicationContext()。
 * @author 叶蕾
 *
 */

public class OnePieceProgressDialog extends Dialog {

    private ProgressBar mProgress;
    private TextView mMessageView;

    private CharSequence mMessage;

    public OnePieceProgressDialog(Context context) {
        this(context, R.style.Theme_Dialog_Progress);
    }

    private OnePieceProgressDialog(Context context, int theme) {
        super(context, R.style.Theme_Dialog_Progress);
    }

    public static OnePieceProgressDialog show(Context context,
            boolean cancelable) {
        return show(context, cancelable, null, null);
    }

    public static OnePieceProgressDialog show(Context context,
            boolean cancelable, CharSequence msg,
            OnCancelListener cancelListener) {
        OnePieceProgressDialog dialog = new OnePieceProgressDialog(context);
        dialog.setMessage(msg);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.show();
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context mContext = getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.drawable.popup_full_dark);
        View view = inflater.inflate(R.layout.onepiece_progress_dialog, null,
                false);
        mProgress = (ProgressBar) view.findViewById(R.id.progress);
        mMessageView = (TextView) view.findViewById(R.id.p_message);
        getWindow().setContentView(view);
        if (mMessage != null) {
            setMessage(mMessage);
        }
        super.onCreate(savedInstanceState);
    }

    public void setMessage(CharSequence message) {
        if (mProgress != null) {
            if (mMessageView.getVisibility() == View.GONE) {
                mMessageView.setVisibility(View.VISIBLE);
            }
            mMessageView.setText(message);
        } else {
            mMessage = message;
        }
    }

    public void resetMessage(CharSequence message) {
        if (mProgress != null) {
            mProgress.setVisibility(View.GONE);
            if (mMessageView.getVisibility() == View.GONE) {
                mMessageView.setVisibility(View.VISIBLE);
            }
            mMessageView.setText(message);
        } else {
            mMessage = message;
        }
    }

    public void setMessageDrawable(Drawable drawable) {
        if (mProgress != null) {
            mProgress.setVisibility(View.GONE);
            mMessageView.setCompoundDrawablePadding(12);
            mMessageView.setCompoundDrawablesWithIntrinsicBounds(null,
                    drawable, null, null);
        }
    }

}
