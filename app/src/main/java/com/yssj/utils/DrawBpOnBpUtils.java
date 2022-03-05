package com.yssj.utils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class DrawBpOnBpUtils {
    private Context context;
    private Bitmap bgDrawable, dpDrawable;
    /**
     *
     * @param context 上下文环境
     * @param bgDrawableId  背景图片
     * @param dpDrawableId  印章
     */
    public DrawBpOnBpUtils(Context context, Bitmap mBgDrawable, Bitmap mDpDrawable) {
        this.context = context;
        this.bgDrawable = mBgDrawable;
        this.dpDrawable = mDpDrawable;
    }


    /**
     * 绘制图片
     * @param scale  两张Bitmap相对比例
     * @param x  dpDrawable在canvas的       left
     * @param y  dpDrawable在canvase的      top
     * @return
     */
    public Bitmap onDrawBitmap(float width,float height,float x,float y){

        Bitmap result = Bitmap.createBitmap(bgDrawable.getWidth(), bgDrawable.getHeight(), Config.ARGB_8888);
        Rect mSrcRect = new Rect(0, 0, dpDrawable.getWidth(), dpDrawable.getHeight());
        Rect mDesRect = new Rect((int)x, (int)y, (int)(x + width), (int)(y + height));
        Canvas mCanvas = new Canvas(result);
        mCanvas.drawBitmap(bgDrawable, 0, 0, null);
        mCanvas.drawBitmap(dpDrawable, mSrcRect, mDesRect, null);
        Log.e(getClass().getName(), "x:" + x + ";y:" + y + ";top:" + (x + dpDrawable.getWidth()) + "right:" + (y + dpDrawable.getHeight()));
        bgDrawable = null;
        dpDrawable = null;
        return result;
    }

}
