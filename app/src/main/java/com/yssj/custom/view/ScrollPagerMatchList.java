package com.yssj.custom.view;

import com.yssj.activity.R;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.OverScroller;
import android.widget.ScrollView;
import android.widget.TextView;
/**
 * 搭配详情 页面 View
 */
public class ScrollPagerMatchList extends LinearLayout implements OnGlobalLayoutListener{
	
	private View mRefresh;
	private ImageView animRefresh;
	private int refreshHeight = 0;
	
	private View mTop;
	private View mNav;
	private ViewPager mViewPager;

	private static int mTopViewHeight = 0;
	private ViewGroup mInnerScrollView;
	private boolean isTopHidden = false;

	private OverScroller mScroller;
	private VelocityTracker mVelocityTracker;
	private int mTouchSlop;
	private int mMaximumVelocity, mMinimumVelocity;

	private float mLastY;
	private boolean mDragging;

	private boolean isInControl = false;
	
	private Animation anim;
	
	private int width;
	
	private int mTopHeigth2;//textview 展开 或收起 之后 的 顶部view的新的高度
	private ViewTreeObserver vto; 
	public int getmTextViewHeigth() {
		return mTopHeigth2;
	}

	public void setmTextViewHeigth(int mTextViewHeigth) {
		this.mTopHeigth2 = mTextViewHeigth;
	}

	public ScrollPagerMatchList(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(LinearLayout.VERTICAL);
		mTopViewHeight = 0;
		mScroller = new OverScroller(context);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mMaximumVelocity = ViewConfiguration.get(context)
				.getScaledMaximumFlingVelocity();
		mMinimumVelocity = ViewConfiguration.get(context)
				.getScaledMinimumFlingVelocity();
		anim=AnimationUtils.loadAnimation(context, R.anim.pull_refresh_anim);
		width=context.getResources().getDisplayMetrics().widthPixels;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mRefresh=findViewById(R.id.refreshView);
		mTop = findViewById(R.id.top_view_match);
		mNav = findViewById(R.id.title);
		View view = findViewById(R.id.content_viewpager);
		mViewPager = (ViewPager) view;
		animRefresh=(ImageView) findViewById(R.id.refreshAnim);
		text=(TextView) findViewById(R.id.pull_to_refresh_text);
		loading=(ImageView) findViewById(R.id.img_arrow);
		textLoading=findViewById(R.id.lin_text);
		TextView tvContent = (TextView) mTop.findViewById(R.id.content_tv);
		vto = tvContent.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(this);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
		params.height = getMeasuredHeight() - mNav.getMeasuredHeight();
		if(refreshHeight==0){
			refreshHeight=mRefresh.getLayoutParams().height;
		}
		
	}
	
	
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if(mTopViewHeight==0){
			mTopViewHeight = mTop.getMeasuredHeight();
//					-findViewById(R.id.xgsp_ll).getMeasuredHeight();
		}
			
	}
	
	/**
	 * Textview 改变的时候 回调
	 */
	public void onGlobalLayout() {
		//当Textview收起  展开 时候  重新计算TopView的高度
		mTopViewHeight = mTop.getMeasuredHeight();
//				-findViewById(R.id.xgsp_ll).getMeasuredHeight();
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		float y = ev.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			float dy = y - mLastY;
			getCurrentScrollView();

			if (mInnerScrollView instanceof ScrollView) {
				if (mInnerScrollView.getScrollY() == 0 && isTopHidden && dy > 0
						&& !isInControl) {
					isInControl = true;
					ev.setAction(MotionEvent.ACTION_CANCEL);
					MotionEvent ev2 = MotionEvent.obtain(ev);
					dispatchTouchEvent(ev);
					ev2.setAction(MotionEvent.ACTION_DOWN);
					return dispatchTouchEvent(ev2);
				}
			} else if (mInnerScrollView instanceof ListView) {

				ListView lv = (ListView) mInnerScrollView;
				View c = lv.getChildAt(lv.getFirstVisiblePosition());

				if (!isInControl && c != null && c.getTop() == 0 && isTopHidden
						&& dy > 0) {
					isInControl = true;
					ev.setAction(MotionEvent.ACTION_CANCEL);
					MotionEvent ev2 = MotionEvent.obtain(ev);
					dispatchTouchEvent(ev);
					ev2.setAction(MotionEvent.ACTION_DOWN);
					return dispatchTouchEvent(ev2);
				}
			}
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		float y = ev.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			float dy = y - mLastY;
			getCurrentScrollView();
			if (Math.abs(dy) > mTouchSlop) {
				mDragging = true;
				if (mInnerScrollView instanceof ScrollView) {
					if (!isTopHidden
							|| (mInnerScrollView.getScrollY() == 0
									&& isTopHidden && dy > 0)) {

						initVelocityTrackerIfNotExists();
						mVelocityTracker.addMovement(ev);
						mLastY = y;
						return true;
					}
				} else if (mInnerScrollView instanceof ListView) {

					ListView lv = (ListView) mInnerScrollView;
					View c = lv.getChildAt(lv.getFirstVisiblePosition());

					if (!isTopHidden || //
							(c != null //
									&& c.getTop() == 0//
									&& isTopHidden && dy > 0)) {

						initVelocityTrackerIfNotExists();
						mVelocityTracker.addMovement(ev);
						mLastY = y;
						return true;
					}
				}

			}
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mDragging = false;
			recycleVelocityTracker();
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	private void getCurrentScrollView() {

		int currentItem = mViewPager.getCurrentItem();
		PagerAdapter a = mViewPager.getAdapter();
		if(a==null){
			return;
		}
		FragmentStatePagerAdapter fsAdapter = (FragmentStatePagerAdapter) a;
		Fragment item = (Fragment) fsAdapter.instantiateItem(mViewPager,
					currentItem);
		mInnerScrollView = (ViewGroup) (item.getView()
					.findViewById(R.id.dataList));
	}
	
	
	private TextView text;
	private ImageView loading;
	private View textLoading;
	
	private boolean isAnim=false;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		initVelocityTrackerIfNotExists();
		mVelocityTracker.addMovement(event);
		int action = event.getAction();
		float y = event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (!mScroller.isFinished())
				mScroller.abortAnimation();
			mLastY = y;
			return true;
		case MotionEvent.ACTION_MOVE:
			float dy = y - mLastY;

			if (!mDragging && Math.abs(dy) > mTouchSlop) {
				mDragging = true;
			}
			if (mDragging) {
				if(dy>0&&getScrollY()<=0){
					scrollBy(0, (int) (-dy/1.8));
					if(Math.abs(getScrollY())<refreshHeight){
						animRefresh.setVisibility(View.GONE);
						textLoading.setVisibility(View.VISIBLE);
						loading.clearAnimation();
						text.setText("下拉刷新");
						loading.setImageResource(R.drawable.img_arrow_down);
						isAnim=false;
					}else if(Math.abs(getScrollY())>=refreshHeight){
						if(isAnim==false){
							animRefresh.setVisibility(View.GONE);
							textLoading.setVisibility(View.VISIBLE);
							text.setText("释放刷新");
//							loading.setImageResource(R.drawable.arrow_up);
							loading.startAnimation(anim);
							isAnim=true;
						}
					}
					
				}else{
					scrollBy(0, (int) (-dy));
				}
				if (getScrollY() == mTopViewHeight && dy < 0) {
					event.setAction(MotionEvent.ACTION_DOWN);
					dispatchTouchEvent(event);
					isInControl = false;
				}
			}

			mLastY = y;
			break;
		case MotionEvent.ACTION_CANCEL:
			mDragging = false;
			isAnim=false;
			recycleVelocityTracker();
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			break;
		case MotionEvent.ACTION_UP:
			mDragging = false;
			isAnim=false;
			mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
			int velocityY = (int) mVelocityTracker.getYVelocity();
			
			if(getScrollY()<=-refreshHeight){
				mScroller.fling(0, getScrollY(), 0, mMaximumVelocity, 0, 0, 0, -refreshHeight);
				invalidate();
				if(lintener!=null){
					animRefresh.setVisibility(View.VISIBLE);
					((AnimationDrawable)animRefresh.getBackground()).start();
					textLoading.setVisibility(View.GONE);
					lintener.onRefreshlintener();
				}
			}
			
			if(getScrollY()>-refreshHeight){
				if (Math.abs(velocityY) > mMinimumVelocity) {
					fling(-velocityY);
				}else{
					fling(mMinimumVelocity);
				}
			}
			
			recycleVelocityTracker();
			break;
		}

		return super.onTouchEvent(event);
	}
	private MyOnRefreshLintener lintener;
	public interface MyOnRefreshLintener{
		public void onRefreshlintener();
	}
	
	
	
	public void refreshDone(){
		if(getScrollY()!=0){
			mScroller.fling(0, getScrollY(), 0, 0, 0, 0, 0, 0);
			invalidate();
//			fling(mMinimumVelocity);
			recycleVelocityTracker();
		}
		isAnim=false;
	}
	
	public void setOnRefreshLintener(MyOnRefreshLintener l){
		this.lintener=l;
	}
	
	public void fling(int velocityY) {
		mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
		invalidate();
	}
	
	@Override
	public void scrollTo(int x, int y) {
		
		if (y > mTopViewHeight) {
			y = mTopViewHeight;
		}
		if (y != getScrollY()) {
			super.scrollTo(x, y);
		}

		isTopHidden = getScrollY() == mTopViewHeight;

	}
	
	
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(0, mScroller.getCurrY());
			invalidate();
		}
	}

	private void initVelocityTrackerIfNotExists() {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
	}

	private void recycleVelocityTracker() {
		if (mVelocityTracker != null) {
			mVelocityTracker.recycle();
			mVelocityTracker = null;
		}
	}
}
