package com.project.template.ui.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;

import com.frank.template.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageUtils {

    int width;

    int height;

    int mBoardColor;

    int mBoardWidth;

    /**
     * @param width
     *            图片宽度
     * @param height
     *            图片高度
     * @param mBoardColor
     *            边框颜色
     * @param mBoardWidth
     *            边框宽度
     */
    public ImageUtils(int width, int height, int mBoardColor, int mBoardWidth) {
        super();
        int radius = getRadius(mBoardWidth, height);
        this.width = 2 * radius;
        this.height = 2 * radius;

        this.mBoardColor = mBoardColor;
        this.mBoardWidth = mBoardWidth;
    }

    /**
     * @param context
     * @param resourceId
     *            图片处理模板资源图片id
     * @param mBoardColorId
     *            图片边框颜色
     * @param mBoardWidth
     *            图片边框宽度，不需边框，则宽度为0
     */
    public ImageUtils(Context context, int resourceId, int mBoardColorId,
            int mBoardWidth) {
        super();
        init(context, resourceId);
        this.mBoardColor = context.getResources().getColor(mBoardColorId);
        this.mBoardWidth = mBoardWidth;
    }

    public void init(Context context, int resourceId) {
        Resources res = context.getResources();
        Bitmap resource = BitmapFactory.decodeResource(res, resourceId);
        width = resource.getWidth();
        height = resource.getHeight();
    }

    /**
     * 带边框的圆角图像
     * 
     * @param bitmap
     *            源图片
     * @param pixels
     *            圆角图片的角度，大于180度时将会是圆形
     * @return
     */
    public Bitmap toBoardRoundCorner(Bitmap bitmap, float pixels) {
        Bitmap bitmap2 = toRoundCorner(bitmap, pixels);
        bitmap2 = zoomImg(bitmap2, width - 2 * mBoardWidth, height - 2
                * mBoardWidth);

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Rect src = new Rect(0, 0, width, height);
        Rect dist = new Rect(mBoardWidth * 2, mBoardWidth * 2, width
                - mBoardWidth / 2, height - mBoardWidth / 2);// 区域
        Paint paint = new Paint();
        // 防止锯齿
        paint.setAntiAlias(true);
        // 相当于清屏
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(mBoardColor);
        // 画背景
        // canvas.drawCircle(radius, radius, radius, paint);

        RectF rectF = new RectF(src);
        canvas.drawRoundRect(rectF, pixels, pixels, paint);
        // 画图片
        canvas.drawBitmap(bitmap2, src, dist, paint);
        return output;
    }

    /**
     * 带边框的圆角图像
     * 
     * @param bitmap
     *            源图片
     * @param pixels
     *            圆角图片的角度，大于180度时将会是园
     * @return
     */
    // public Bitmap toBoardRoundCorner2(Bitmap bitmap, float pixels) {
    // bitmap = toRoundCorner(bitmap, pixels);
    // bitmap = zoomImg(bitmap, width - 2 * mBoardWidth, height - 2 *
    // mBoardWidth);
    // int radius = getRadius(width, height);
    // Bitmap output = Bitmap.createBitmap(radius * 2, radius * 2,
    // Config.ARGB_8888);
    // Canvas canvas = new Canvas(output);
    // Rect src = new Rect(0, 0, radius * 2, radius * 2);// 区域
    // Rect dist = new Rect(mBoardWidth * 2, mBoardWidth * 2, radius * 2, radius
    // * 2);// 区域
    // Paint paint = new Paint();
    // // 防止锯齿
    // paint.setAntiAlias(true);
    // // 相当于清屏
    // canvas.drawARGB(0, 0, 0, 0);
    // paint.setColor(mBoardColor);
    // canvas.drawCircle(radius, radius, radius, paint);
    //
    // canvas.drawBitmap(bitmap, src, dist, paint);
    // return output;
    // }

    /**
     * @param bitmap
     *            源图片
     * @param pixels
     *            圆角图片的角度，大于180度时将会是园
     * @return
     */
    public Bitmap toRoundCorner(Bitmap bitmap, float pixels) {
        bitmap = zoomImg(bitmap, width, height);
        Bitmap roundCornerBitmap = Bitmap.createBitmap(width, height,
                Config.ARGB_8888);
        Canvas canvas = new Canvas(roundCornerBitmap);
        Paint paint = new Paint();// 画笔
        Rect rect = new Rect(0, 0, width, height);// 区域
        RectF rectF = new RectF(rect);// 区域
        // 防止锯齿
        paint.setAntiAlias(true);
        // 相当于清屏
        canvas.drawARGB(0, 0, 0, 0);
        // 先画了一个带圆角的矩形
        canvas.drawRoundRect(rectF, pixels, pixels, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        // 再把原来的bitmap画到现在的bitmap！！！注意这个理解
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return roundCornerBitmap;
    }

    /**
     * 缩放到指定宽度
     * 
     * @param bitmap
     *            源图片
     * @param newWidth
     * @param newHeight
     * @return
     */
    private static Bitmap zoomImg(Bitmap bitmap, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                true);
        return newbm;
    }

    private int getRadius(int width, int height) {
        if (width < height) {
            return width / 2;
        } else {
            return height / 2;
        }
    }

    /**
     * @param context
     * @param url
     *            图片链接地址
     * @param imageView
     *            显示图片的ImageView
     * @param defaultImageResource
     *            默认图片，用来获取图片生成的宽度高度
     * @param pixels
     *            圆角图片的角度，大于180度时将会是圆形
     * @param board
     *            边框宽度
     */
    public static void showImage(final Context context, String url,
            final ImageView imageView, final int defaultImageResource,
            final float pixels, final int board, final int color) {

        ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLoadingFailed(String imageUri, View view,
                    FailReason failReason) {
                if (defaultImageResource > 0) {
                    imageView.setImageResource(defaultImageResource);
                } else {
                    imageView.setImageResource(R.drawable.ic_launcher);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view,
                    Bitmap loadedImage) {
                ImageUtils iu = new ImageUtils(context, defaultImageResource,
                        color, board);
                imageView.setImageBitmap(iu.toBoardRoundCorner(loadedImage,
                        pixels));

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                // TODO Auto-generated method stub

            }
        });

    }
}
