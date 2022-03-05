package com.handmark.pulltorefresh.library.internal;


import com.handmark.pulltorefresh.library.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * 自定义刷新动画
 * @author Administrator
 *
 */
public class TweenAnimLoadingLayout extends LoadingLayout {

	private AnimationDrawable animationDrawable;
	
	private Animation anim;
	
	public TweenAnimLoadingLayout(Context context, Mode mode,
			Orientation scrollDirection, TypedArray attrs) {
		super(context, mode, scrollDirection, attrs);
		// 初始化
		mHeaderImage.setImageResource(R.drawable.header_loading);
		animationDrawable = (AnimationDrawable) mHeaderImage.getDrawable();
		anim=AnimationUtils.loadAnimation(context, R.anim.pull_refresh_anim);
	}
	// 默认图片
	@Override
	protected int getDefaultDrawableResId() {
		return R.drawable.refresh_1;
	}
	
	@Override
	protected void onLoadingDrawableSet(Drawable imageDrawable) {
		// NO-OP
	}
	
	@Override
	protected void onPullImpl(float scaleOfLayout) {
		// NO-OP
	}
	// 下拉以刷新
	@Override
	protected void pullToRefreshImpl() {
		// NO-OP
		lin_text.setVisibility(View.VISIBLE);
		fm_anim.setVisibility(View.GONE);
		img_arrow.setImageResource(R.drawable.img_arrow_down);
	}
	// 正在刷新时回调
	@Override
	protected void refreshingImpl() {
		// 播放帧动画
		animationDrawable.start();
		lin_text.setVisibility(View.GONE);
		fm_anim.setVisibility(View.VISIBLE);
		
	}
	// 释放以刷新
	@Override
	protected void releaseToRefreshImpl() {
		// NO-OP
		lin_text.setVisibility(View.VISIBLE);
		fm_anim.setVisibility(View.GONE);
		//img_arrow.setImageResource(R.drawable.arrow_up);
		img_arrow.startAnimation(anim);
	}
	// 重新设置
	@Override
	protected void resetImpl() {
		mHeaderImage.setVisibility(View.VISIBLE);
		mHeaderImage.clearAnimation();
		img_arrow.clearAnimation();
	}

}