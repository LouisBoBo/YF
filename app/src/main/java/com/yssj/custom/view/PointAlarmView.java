package com.yssj.custom.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
/**
 * 圆形扩散
 * @author Administrator
 *
 */
public class PointAlarmView extends View {

    private Paint paint;
    private int maxWidth = 128;
    private List<String> alphaList = new ArrayList<String>();
    private List<String> startWidthList = new ArrayList<String>();

    public PointAlarmView(Context context) {
        super(context);
        init();
    }

    public PointAlarmView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PointAlarmView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
//    	handleDelay(42);
        paint = new Paint();
        paint.setAntiAlias(true);  
        paint.setColor(Color.WHITE);
        alphaList.add("125");// 圆心的不透明度
        startWidthList.add("0");
    }

	@Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.TRANSPARENT);// 颜色：完全透明
        // 依次绘制 同心圆
        for (int i = 0; i < alphaList.size(); i++) {
            int alpha = Integer.parseInt(alphaList.get(i));
            int startWidth = Integer.parseInt(startWidthList.get(i));
            paint.setAlpha(alpha);// 设置透明度
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, startWidth,
                    paint);
            // 同心圆扩散
            if (alpha >0 && startWidth < maxWidth) {
                alphaList.set(i, (alpha - 2) + "");
                startWidthList.set(i, (startWidth + 1) + "");
            }
        }
        if (Integer.parseInt(startWidthList.get(startWidthList.size() - 1)) == maxWidth /10) {
            alphaList.add("125");
            startWidthList.add("0");
        }
        // 删除外层圆
        if (startWidthList.size() == 4) {
            startWidthList.remove(0);
            alphaList.remove(0);
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {
                // 刷新界面
                invalidate();
            }
        }, 82);
    }
}