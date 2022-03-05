package com.yssj.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yssj.activity.R;

public class LoadMoreView extends FrameLayout {

	private ProgressBar bar;
	private TextView text;
	
	public static final int STATE_IDLE = 0;
	public static final int STATE_LOADING = 1;
	public static final int STATE_NO_MORE = 2;
	private int state = STATE_IDLE;
	private View divider;

	public LoadMoreView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLoadMoreView(context);
	}

	public LoadMoreView(Context context) {
		this(context, null);
	}

	private void initLoadMoreView(Context context) {
		View v = LayoutInflater.from(context).inflate(R.layout.load_more, null);
		divider = v.findViewById(R.id.divider);
		bar = (ProgressBar) v.findViewById(R.id.load_more_progress_bar);
		text = (TextView) v.findViewById(R.id.load_more_text);
		update();
		addView(v);
	}
	
	public void isShowDivider(boolean isShow) {
		divider.setVisibility(isShow ? View.VISIBLE : View.GONE);
	}
	
	public boolean isReady() {
		if (state == STATE_IDLE) {
			return true;
		}
		return false;
	}
	
	public void setState(int state) {
		if (state != STATE_IDLE 
				&& state != STATE_LOADING 
				&& state != STATE_NO_MORE) {
			throw new IllegalArgumentException("state wrong: " + state);
		}
		this.state = state;
		update();
	}

	// 如果这些设置了setClickable，会夺取list item的焦�?
	private void update() {
		switch (state) {
			case STATE_IDLE :
				bar.setVisibility(View.GONE);
				text.setText(R.string.more);
				break;
			case STATE_LOADING :
				bar.setVisibility(View.VISIBLE);
				text.setText(R.string.loading);
				break;
			case STATE_NO_MORE :
				bar.setVisibility(View.GONE);
				text.setText(R.string.no_more_data);
				break;
		}
	}
	
	

}
