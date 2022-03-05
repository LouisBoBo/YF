package com.yssj.utils;

/**
 * Created by Administrator on 2020/5/18.
 */


import android.util.Log;
import android.view.View;
import java.util.Calendar;


public abstract class NoDoubleListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 3000;
    private static final String TAG ="NoDoubleListener" ;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }else {
            Log.w(TAG,"点击过快");
        }
    }
    protected abstract void onNoDoubleClick(View v);


}
