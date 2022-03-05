package com.yssj.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.yssj.activity.R;

/***
 * 计时器
 * 
 * @author Administrator
 * 
 */
public class TimeCount extends CountDownTimer {
	private TextView tv = null;

	public TimeCount(long millisInFuture, long countDownInterval, TextView tv) {
		super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		this.tv = tv;

	}

	@Override
	public void onFinish() {// 计时完毕时触发
		try {
			tv.setText("重新获取");
			tv.setEnabled(true);
			tv.setBackgroundResource(R.color.white);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTick(long millisUntilFinished) {// 计时过程显示
		try {
			tv.setEnabled(false);
			tv.setBackgroundResource(R.color.white);
			tv.setText(millisUntilFinished / 1000 + "秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
