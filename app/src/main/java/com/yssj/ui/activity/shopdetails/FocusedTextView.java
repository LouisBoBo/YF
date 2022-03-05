package com.yssj.ui.activity.shopdetails;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

public class FocusedTextView extends TextView {

	public FocusedTextView(Context context) {
		this(context, null);

		// new 对象时是使用
	}

	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// 应用到xml布局中

		// android:singleLine="true"
		setSingleLine();

		// android:ellipsize="marquee"
		setEllipsize(TruncateAt.MARQUEE);

		// android:focusable="true"
		setFocusable(true);

		// android:focusableInTouchMode="true"
		setFocusableInTouchMode(true);

		// android:marqueeRepeatLimit="marquee_forever"
		setMarqueeRepeatLimit(-1);
	}

	@Override
	public boolean isFocused() {
		// 设置控件有焦点
		return true;
	}

	/**
	 * 本window中view的焦点发生改变时的回调
	 */
	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		if (focused) {
			// 只有有焦点时
			super.onFocusChanged(focused, direction, previouslyFocusedRect);
		}
	}

	/**
	 * window和window间焦点发生改变时的回调
	 */
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		if (hasWindowFocus) {
			super.onWindowFocusChanged(hasWindowFocus);
		}
	}
}
