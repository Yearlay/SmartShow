package com.android.smartshowbox.activity;

import java.util.ArrayList;

import com.android.smartshowbox.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;
 
public class ActivityDefaultShow extends Activity {
    /*
     * 这个类用来当测试的物件，会沿着方形路线持续移动
     */
    class GameObject {
        float x;
        float y;
        private Bitmap img;
        private Paint paint;
        boolean mDown = true;
 
        public GameObject(int x, int y) {
            this.img = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
            this.x = x;
            this.y = y;
            mDown= true;
            this.paint = new Paint();
        }
 
        // 在SurfaceView加锁同步后传给自己的Canvas上绘制自己
        public void drawSelf(Canvas canvas) {
            canvas.drawBitmap(img, x, y, paint);
        }
 
        // 获取物件下一次要绘制的位置(这里是沿着一个边长为400的正方形不断运动的)
        public void getNextPos(int top, int bottom) {
            if (mDown) {
                if (y < bottom) {
                    y += 5;
                } else {
                    mDown = false;
                }
            } else {
                if (y > top) {
                    y -= 5;
                } else {
                    mDown = true;
                }
            }
        }
    }
 
    /*
     * 这个类就是加工了SurfaceView之后的类，所有要运动的物件都最终放在这里进行绘制
     */
    class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
        private Thread thread; // SurfaceView通常需要自己单独的线程来播放动画
        private Canvas canvas;
        private SurfaceHolder surfaceHolder;
        
        private ArrayList<GameObject> mGameObjectList = new ArrayList<GameObject>();
        
        int xSet[] = {100, 250, 400, 550, 700, 850, 1000};
        int ySet[] = {100, 150, 200, 250, 300, 350, 400};
 
        public MySurfaceView(Context c) {
            super(c);
            this.surfaceHolder = this.getHolder();
            this.surfaceHolder.addCallback(this);
            for (int i = 0; i < xSet.length; i++) {
                mGameObjectList.add(new GameObject(xSet[i], ySet[i]));
            }
        }
 
        @Override
        public void run() {
            while (true) {
                for (GameObject obj : mGameObjectList) {
                    obj.getNextPos(50, 450);
                }
                canvas = this.surfaceHolder.lockCanvas(); // 通过lockCanvas加锁并得到該SurfaceView的画布
                if (canvas == null) {
                    return;
                }
                canvas.drawColor(Color.BLACK);
                for (GameObject obj : mGameObjectList) {
                    obj.drawSelf(canvas); // 把SurfaceView的画布传给物件，物件会用这个画布将自己绘制到上面的某个位置
                }
                this.surfaceHolder.unlockCanvasAndPost(canvas); // 释放锁并提交画布进行重绘
                try {
                    Thread.sleep(10); // 这个就相当于帧频了，数值越小画面就越流畅
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
 
        @Override
        public void surfaceDestroyed(SurfaceHolder arg0) {
            // Toast.makeText(getApplicationContext(), "SurfaceView已经销毁", Toast.LENGTH_LONG).show();
        }
 
        @Override
        public void surfaceCreated(SurfaceHolder arg0) {
            // Toast.makeText(getApplicationContext(), "SurfaceView已经创建", Toast.LENGTH_LONG).show();
            this.thread = new Thread(this);
            this.thread.start();
        }
 
        @Override
        public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
            // 这里是SurfaceView发生变化的时候触发的部分
        }
    }
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceView(getApplicationContext())); // 别忘了开始的时候载入我们加工好的的SurfaceView
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
