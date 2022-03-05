package com.yssj.utils;

/**
 * 限制点击间隔
 * @author lifeng
 *
 */
public class LimitDoubleClicked {
    private static long lastClickTime = System.currentTimeMillis();
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 1000) {   
            return true;   
        }   
        lastClickTime = time;   
        return false;   
    }
    public static boolean isFastDoubleClick500() {
    	long time = System.currentTimeMillis();
    	long timeD = time - lastClickTime;
    	if ( 0 < timeD && timeD < 500) {   
    		return true;   
    	}   
    	lastClickTime = time;   
    	return false;   
    }
}

