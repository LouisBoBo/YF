package com.yssj.custom.view;

import com.yssj.activity.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SToast extends Toast{
	
	public SToast(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;
	
	private static Toast mToast;
    
//	@SuppressLint("ShowToast")
//	public static Toast makeText(Context context, CharSequence text, int duration) {
////		View v = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
////		TextView tv_show = (TextView) v.findViewById(R.id.tv_show);
////		tv_show.setText(text);
//		mToast = new Toast(context);
//		mToast.setDuration(duration); 
////		mToast.setGravity(Gravity.CENTER, 0, 0);
////		mToast.setView(v);
//		return mToast;
//	}
	
//	@SuppressLint("ShowToast")
//	public static Toast makeText(Context context, int resId, int duration) {
////		View v = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
////		TextView tv_show = (TextView) v.findViewById(R.id.tv_show);
////		tv_show.setText(resId);
//		mToast = new Toast(context);
//		mToast.setDuration(duration); 
////		mToast.setGravity(Gravity.CENTER, 0, 0);
////		mToast.setView(v);
//		return mToast;
//	}
	
	public void show() {
		mToast.show();
	}
	
	public void  cancel(){
		mToast.cancel();
	}
	
	
}
