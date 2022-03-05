package com.yssj.app;

import android.content.Context;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class SPullToRefreshListView extends PullToRefreshListView {

	public SPullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SPullToRefreshListView(Context context, Mode mode,
			AnimationStyle style) {
		super(context, mode, style);
	}

	public SPullToRefreshListView(Context context, Mode mode) {
		super(context, mode);
	}

	public SPullToRefreshListView(Context context) {
		super(context);
	}

	/*
	@Override
	protected LoadingLayout createLoadingLayout(Context context, Mode mode, TypedArray attrs) {
		SLoadingLayout layout = new SLoadingLayout(context, mode, getPullToRefreshScrollDirection(), attrs);
		layout.setVisibility(View.INVISIBLE);
		return layout;
	}
	*/

}
