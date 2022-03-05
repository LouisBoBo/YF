package com.yssj.entity;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.ToggleButton;

public class MyToggleButton extends ToggleButton {

	public MyToggleButton(Context context) {
		super(context);
	}

	public MyToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);

		setFocusable(false);
		setFocusableInTouchMode(false);

		setSingleLine();
		setEllipsize(TruncateAt.MARQUEE);
		setMarqueeRepeatLimit(-1);
	}

	public MyToggleButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setFocusable(false);
		setFocusableInTouchMode(false);

		setSingleLine();
		setEllipsize(TruncateAt.MARQUEE);
		setMarqueeRepeatLimit(-1);
	}

	
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		if (isChecked()) {
			return true;
		} else{
			return super.isFocused();
		}
	}

}
