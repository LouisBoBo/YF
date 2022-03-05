package com.yssj.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/6/16.
 */

public class FlickTextView extends TextView {


    private int mViewWidth;

    private Paint mPaint;

    /**
     * 颜色渐变
     */
    private LinearGradient mLinearGradient;

    private Matrix mGradientMatrix;

    private float mTranslate;


    public FlickTextView(Context context) {
        super(context, null);
    }

    public FlickTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mViewWidth == 0) {
//            mViewWidth = getMeasuredWidth();
            mViewWidth = getMeasuredWidth();

            if (mViewWidth > 0) {
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                        new int[]{0XFFFD052E, 0XFFFFFFFF, 0XFFFD052E},
                        null,
                        Shader.TileMode.CLAMP
                );

                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mGradientMatrix != null) {
            mTranslate += mViewWidth / 5;

            //当该控件渲染器的颜色变化正好移除屏幕时，从左侧进入
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);

            //100ms 后继续刷新试图，即调用onDraw()方法。
            postInvalidateDelayed(100);
        }
    }
}
