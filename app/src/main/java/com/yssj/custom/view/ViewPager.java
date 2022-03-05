//package com.yssj.custom.view;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.util.AttributeSet;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//
///**
// * 
// * @author pangxg
// * @title 扩展viewPager
// * @description 和一般的ViewPager一样使用
// */
//public class ViewPager extends android.support.v4.view.ViewPager {
//	// private static final String tag=ViewPager.class.getSimpleName();
//	protected ViewPager viewPager = null;
//	protected final int wrap_content = -2;
//	protected final int match_parent = -1;
//	protected int w = match_parent;
//	protected int h = match_parent;
//	protected int ph = match_parent;
//	protected int pw = match_parent;
//
//	public ViewPager(Context context) {
//		super(context);
//		viewPager = this;
//	}
//
//	public ViewPager(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		viewPager = this;
//	}
//
//	@Override
//	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
//		super.onLayout(arg0, arg1, arg2, arg3, arg4);
//		ViewGroup.LayoutParams params = null;
//		ph = viewPager.getLayoutParams().height;
//		pw = viewPager.getLayoutParams().width;
//
//		Drawable bg = viewPager.getBackground();
//		if (bg != null) {
//			int h = bg.getIntrinsicHeight();
//			int w = bg.getIntrinsicWidth();
//			if (ph == wrap_content) {
//				this.h = h;
//			} else if (ph == match_parent) {
//				this.h = ph;
//			}
//
//			if (pw == wrap_content) {
//				this.w = w;
//			} else if (pw == match_parent) {
//				this.w = pw;
//			}
//		}
//		viewPager.setBackgroundColor(getResources().getColor(
//				android.R.color.transparent));
//		ViewParent pv = viewPager.getParent();
//		if (pv instanceof LinearLayout) {
//			params = new LinearLayout.LayoutParams(this.w, this.h);
//		} else if (pv instanceof RelativeLayout) {
//			params = new RelativeLayout.LayoutParams(this.w, this.h);
//		} else if (pv instanceof FrameLayout) {
//			params = new FrameLayout.LayoutParams(this.w, this.h);
//		}
//		viewPager.setLayoutParams(params);
//	}
//
//	@Override
//	protected void onMeasure(int arg0, int arg1) {
//		super.onMeasure(arg0, arg1);
//	}
//
//}