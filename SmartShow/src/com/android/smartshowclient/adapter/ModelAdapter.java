package com.android.smartshowclient.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.smartshowclient.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class ModelAdapter extends BaseAdapter {
    private Context mContext;
    ImageLoaderConfiguration mConfiguration;
    BitmapFactory bf = new BitmapFactory();
    private MyImageLoadingListener mListener = new MyImageLoadingListener();
    private Map<Integer, Drawable> mResources = new HashMap<Integer, Drawable>();
    private List<String> mUrlsList = new ArrayList<String>();
    private OnImageLoadListener mImageListener;

    public interface OnImageLoadListener {
        public void onImageLoad(int index);
    }

    DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
    // 显示下载时的图片
            .showImageOnLoading(R.drawable.ic_launcher)
            // 显示加载内容找不到或为空时的图片
            .showImageForEmptyUri(R.drawable.ic_launcher)
            // 设置图片加载/解码过程中错误时候显示的图片
            .showImageOnFail(R.drawable.ic_launcher)
            // 是否缓存到内存
            .cacheInMemory(false)
            // 是否缓存到sd卡
            .cacheOnDisk(true)
            // 是否包含图片中写入的设备信息
            .considerExifParams(true)
            // 设置图片以如何的编码方式显示
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
            // 设置图片显示格式
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            // 设置图片下载前的延迟
            .delayBeforeLoading(100)
            // 设置下载前是否复位
            .resetViewBeforeLoading(true)
            // 淡入
            .displayer(new FadeInBitmapDisplayer(100)).build();

    public ModelAdapter(Context context, OnImageLoadListener listener) {
        mContext = context;
        mImageListener = listener;
        // 获取缓存图片的路径
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "SmartShow/back_up");
        mConfiguration = new ImageLoaderConfiguration.Builder(mContext)
                .memoryCacheExtraOptions(mContext.getResources().getDimensionPixelSize(R.dimen.model_menu_image_width), mContext.getResources().getDimensionPixelSize(R.dimen.model_menu_image_height)).threadPoolSize(4)
                .threadPriority(Thread.NORM_PRIORITY - 2).tasksProcessingOrder(QueueProcessingType.FIFO).memoryCache(new LruMemoryCache(2 * 1024 * 1024)).diskCacheFileCount(1024).diskCache(new UnlimitedDiskCache(cacheDir)).build();
        ImageLoader.getInstance().init(mConfiguration);
        String[] urls = mContext.getResources().getStringArray(R.array.model_test);
        Collections.addAll(mUrlsList, urls);
        for (String s : mUrlsList) {
            ImageLoader.getInstance().loadImage(s, new ImageSize(mContext.getResources().getDimensionPixelSize(R.dimen.model_menu_image_width), mContext.getResources().getDimensionPixelSize(R.dimen.model_menu_image_height)), mListener);
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 20;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    static class ViewHolder {
        ImageView mImgView;
        TextView mTitleText;
        TextView mHotView;
        int mPostion;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        if (convertView == null) {
            view = parent.inflate(mContext, R.layout.model_list_item, null);
            holder = new ViewHolder();
            holder.mTitleText = (TextView) view.findViewById(R.id.list_title);
            holder.mImgView = (ImageView) view.findViewById(R.id.list_image);
            holder.mHotView = (TextView) view.findViewById(R.id.list_hot);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mResources.containsKey(position)) {
            holder.mImgView.setBackgroundDrawable(mResources.get(position));
        } else {
            holder.mImgView.setBackgroundResource(R.drawable.ic_launcher);
        }
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        mResources.clear();
        super.notifyDataSetChanged();
    }

    private class MyImageLoadingListener implements ImageLoadingListener {
        @Override
        public void onLoadingCancelled(String arg0, View arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
            // TODO Auto-generated method stub
            int position = mUrlsList.indexOf(arg0);
            if (!mResources.containsKey(position)) {
                mResources.put(position, new BitmapDrawable(mContext.getResources(), arg2));
            }
            mImageListener.onImageLoad(position);
        }

        @Override
        public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onLoadingStarted(String arg0, View arg1) {
            // TODO Auto-generated method stub

        }

    }

    public void updateImage(View childView, int postion) {
        if (childView == null) {
            return;
        }
        ViewHolder holder = (ViewHolder) childView.getTag();
        if (holder != null) {
            holder.mImgView.setBackgroundDrawable(mResources.get(postion));
        }
    }

}
