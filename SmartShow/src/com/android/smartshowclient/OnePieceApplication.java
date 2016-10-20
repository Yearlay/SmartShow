package com.android.smartshowclient;

import java.util.concurrent.ThreadPoolExecutor;

import android.app.Application;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import com.android.smartshowclient.io.DefaultThreadFactory;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class OnePieceApplication extends Application {
    // private static final String TAG = "QixinApplication";
    private static Context instance;

    public static Context getContext() {
        return instance;
    }

    public static void setContext(Context context) {
        instance = context.getApplicationContext();
    }

    public static Context getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initImageLoader(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.

        // MAX size of Image to save to memory,Screen Size;
        // default download thread 3

        // FOR CACHED IMAGE THREAD POOL
        final int CACHED_IMAGE_THREAD_POOL_SIZE = 1;
        final String CACHED_IMAGE_THREAD_POOL_NAME = "uil-cached";
        ThreadPoolExecutor cachedeExecutors = DefaultThreadFactory
                .createExecutor(CACHED_IMAGE_THREAD_POOL_SIZE,
                        Thread.NORM_PRIORITY, QueueProcessingType.LIFO,
                        CACHED_IMAGE_THREAD_POOL_NAME);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .taskExecutorForCachedImages(cachedeExecutors)
                .memoryCacheSize(8 * 1024 * 1024)
                // 4M, remove this setting to use default : 1/8 APP Available
                // Memory
                // .defaultDisplayImageOptions(
                // DisplayImageOption.getHttpBuilder().build())
                // .tasksProcessingOrder(QueueProcessingType.LIFO)
                // .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    //��ȡ��Ļ�Ŀ�� 
    public static int getScreenWidth() { 
        WindowManager manager = (WindowManager) instance 
                .getSystemService(Context.WINDOW_SERVICE); 
        Display display = manager.getDefaultDisplay(); 
        return display.getWidth(); 
    } 
    //��ȡ��Ļ�ĸ߶� 
    public static int getScreenHeight() { 
        WindowManager manager = (WindowManager) instance 
                .getSystemService(Context.WINDOW_SERVICE); 
        Display display = manager.getDefaultDisplay(); 
        return display.getHeight(); 
    }
}
