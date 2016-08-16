package com.android.smartShow.fragment;

import java.util.Map;

import com.android.smartShow.R;
import com.android.smartShow.adapter.ModelAdapter;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

/**
 * 个人
 */
public class MainPageTwoFragment extends BaseFragment implements OnClickListener {
    private Button mType;
    private Button mClass;
    private Button mColor;
    private ListView mList;
    private ModelAdapter mAdapter;
    private ModelAdapter.OnImageLoadListener mListener = new ModelAdapter.OnImageLoadListener() {
        @Override
        public void onImageLoad(int index) {
            mAdapter.updateImage(mList.getChildAt(index - mList.getFirstVisiblePosition()), index);
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        mAdapter = new ModelAdapter(getContext(), mListener);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean handleNotifyMessage(Message msg) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean handleUIMessage(Message msg) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean handleMessage(Message msg) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onResult(int event, boolean isok, Map<String, Object> result) {
        // TODO Auto-generated method stub

    }

    @Override
    protected String setFragmentTag() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void initData(Bundle bundle) {
        // TODO Auto-generated method stub

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.main_page_two, container, false);
        mType = (Button) view.findViewById(R.id.model_type);
        mClass = (Button) view.findViewById(R.id.model_class);
        mColor = (Button) view.findViewById(R.id.model_color);
        mList = (ListView) view.findViewById(R.id.model_list);
        String[] modelMenu = getResources().getStringArray(R.array.model_menu);
        mType.setText(modelMenu[0]);
        mClass.setText(modelMenu[1]);
        mColor.setText(modelMenu[2]);
        mType.setOnClickListener(this);
        mClass.setOnClickListener(this);
        mColor.setOnClickListener(this);
        mList.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onClick(View view) {
    }

}
