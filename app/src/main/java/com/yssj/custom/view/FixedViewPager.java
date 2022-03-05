package com.yssj.custom.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 解决在密友圈首页查看大图  viewpager 里面  通过手势绘制图片 放大缩小 造成了 java.lang.IllegalArgumentException: pointerIndex out of range 
* @author Administrator
* @date 2017年3月6日下午1:57:09
 */
public class FixedViewPager extends ViewPager {  
        public FixedViewPager(Context context) {  
               super(context);  
       }  
  
        public FixedViewPager(Context context, AttributeSet attrs) {  
               super(context, attrs);  
       }  
  
        @Override  
        public boolean dispatchTouchEvent(MotionEvent ev) {  
               try {  
                      return super .dispatchTouchEvent(ev);  
              } catch (IllegalArgumentException ignored) {  
              } catch (ArrayIndexOutOfBoundsException e) {  
              }  
  
               return false ;  
  
       }  
} 