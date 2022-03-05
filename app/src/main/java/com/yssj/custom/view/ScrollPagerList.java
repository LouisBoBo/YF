package com.yssj.custom.view;

import com.yssj.activity.R;
import com.yssj.ui.fragment.HomePageFragment;
import com.yssj.ui.fragment.MyShopFragment;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.LogYiFu;

import android.content.Context;
import android.graphics.Matrix;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.OverScroller;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 主店頁面View
 *
 * @author lbp
 */
public class ScrollPagerList extends LinearLayout {
    /**
     * 1: dispatchTouchEvent的执行顺序为： 首先触发ACTIVITY的dispatchTouchEvent
     * 然后触发ACTIVITY的onUserInteraction 然后触发LAYOUT的dispatchTouchEvent
     * 然后触发LAYOUT的onInterceptTouchEvent
     * 这就解释了重写ViewGroup时必须调用super.dispatchTouchEvent()； 2:（1）dispatchTouchEvent：
     * 此方法一般用于初步处理事件，因为动作是由此分发，所以通常会调用
     * super.dispatchTouchEvent。这样就会继续调用onInterceptTouchEvent，
     * 再由onInterceptTouchEvent决定事件流向。 （2）onInterceptTouchEvent：
     * 若返回值为True事件会传递到自己的onTouchEvent()；
     * 若返回值为False传递到下一个view的dispatchTouchEvent()；
     * （3）onTouchEvent()：若返回值为True，事件由自己处理消耗，后续动作序列让其处理；
     * 若返回值为False，自己不消耗事件了，向上返回让其他的父view的onTouchEvent接受处理；
     */

    private View mRefresh;
    // private boolean isShopTitle = false;

    private ImageView animRefresh;
    private int refreshHeight = 0;

    private ListView mmlv;

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

    private int width, heights;

    private boolean isHomepage3;

    public void setHomepage3(boolean isHomepage3) {
        this.isHomepage3 = isHomepage3;
    }

    public ScrollPagerList(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        mTopViewHeight = 0;
        mScroller = new OverScroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        anim = AnimationUtils.loadAnimation(context, R.anim.pull_refresh_anim);
        width = context.getResources().getDisplayMetrics().widthPixels;
        heights = width * 9 / 6;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRefresh = findViewById(R.id.refreshView);
        mTop = findViewById(R.id.top_view);
        mNav = findViewById(R.id.title);
        View view = findViewById(R.id.content_viewpager);
        mViewPager = (ViewPager) view;
        animRefresh = (ImageView) findViewById(R.id.refreshAnim);
        text = (TextView) findViewById(R.id.pull_to_refresh_text);
        loading = (ImageView) findViewById(R.id.img_arrow);
        textLoading = findViewById(R.id.lin_text);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        params.height = getMeasuredHeight() - mNav.getMeasuredHeight();
        if (refreshHeight == 0) {
            refreshHeight = mRefresh.getLayoutParams().height;
        }

    }

    // TODO:
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mTopViewHeight == 0) {

            if (isHomepage3) {
                mTopViewHeight = mTop.getMeasuredHeight();

            } else {
                mTopViewHeight = mTop.getMeasuredHeight() - DP2SPUtil.dp2px(getContext(), 50)
                        + DP2SPUtil.dp2px(getContext(), 10);
            }

        }


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float y = ev.getY();

        switch (action) {
            // 按下
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            // 滑动
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                getCurrentScrollView();

                if (mInnerScrollView instanceof ScrollView) {
                    if (mInnerScrollView.getScrollY() == 0 && isTopHidden && dy > 0 && !isInControl) {
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

                    if (!isInControl && c != null && c.getTop() == 0 && isTopHidden && dy > 0) {
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

    private boolean isSroll;

    // 处理事件
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
                if (Math.abs(dy) > mTouchSlop) {// 上滑
                    mDragging = true;
                    if (mInnerScrollView instanceof ScrollView) {
                        if (!isTopHidden || (mInnerScrollView.getScrollY() == 0 && isTopHidden && dy > 0)) {

                            initVelocityTrackerIfNotExists();
                            mVelocityTracker.addMovement(ev);
                            mLastY = y;
                            return true;
                        }
                    } else if (mInnerScrollView instanceof ListView) {

                        mmlv = (ListView) mInnerScrollView;
                        // 当前可以看到的位置上的view
                        View c = mmlv.getChildAt(mmlv.getFirstVisiblePosition());

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

                    mmlv.setOnScrollListener(new AbsListView.OnScrollListener() {
                        private int myposition;
                        // private ImageButton aa2;
                        // private RelativeLayout img_cart_top;
                        // private LinearLayout img_fenx_top;
                        // private ImageView lin_contact2;

                        @Override
                        public void onScrollStateChanged(AbsListView view, int arg1) {

                            View childAt = view.getChildAt(0);
                            switch (arg1) {
                                case SCROLL_STATE_TOUCH_SCROLL:// 触摸后滚动
                                    HomePageFragment.tv_fenlei.setBackgroundResource(R.drawable.icon_fenlei_black);
                                    HomePageFragment.tv_zhuanqian.setBackgroundResource(R.drawable.icon_zhuanqian_black);
                                    HomePageFragment.ll_search.setBackgroundColor(getResources().getColor(R.color.white));

                                    if (childAt == null) {
                                        myposition = 0;
                                    } else {
                                        myposition = -childAt.getTop() + view.getFirstVisiblePosition() * childAt.getHeight();
                                    }
                                    break;

                                case SCROLL_STATE_FLING: // 滚动状态
                                    isSroll = true;


                                    int newPosition = 0;
                                    if (childAt == null) {
                                        newPosition = 0;
                                    } else {
                                        newPosition = -childAt.getTop() + view.getFirstVisiblePosition() * childAt.getHeight();
                                    }

                                    break;

                                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 空闲状态

                                    // 判断滚动到顶部 ----第一个可见的位置
                                    int pos = mmlv.getFirstVisiblePosition();
                                    LogYiFu.e("首页listView的pos", pos + "");
//							HomePageFragment.ll_search.setBackgroundResource(R.drawable.zhezhao2x);
//							HomePageFragment.ll_search.getBackground().setAlpha(0);
//							HomePageFragment.tv_fenlei.setBackgroundResource(R.drawable.icon_fenlei_white);
//							HomePageFragment.tv_zhuanqian.setBackgroundResource(R.drawable.icon_zhuanqian_white);

                                    break;
                            }
                        }

                        @Override
                        public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
                            int perHeight = heights / 100;
                            float currentY = 0;
                            int viewTop = -1;
                            // aa2 = (ImageButton)
                            // findViewById(R.id.imgbtn_left_icon);

                            //
//						 HomePageFragment.tv_fenlei.setBackgroundResource(R.drawable.icon_fenlei_black);
//						 HomePageFragment.tv_zhuanqian.setBackgroundResource(R.drawable.icon_zhuanqian_black);
//						 HomePageFragment.ll_search.setBackgroundColor(getResources().getColor(R.color.white));
                            //

                            //
                            //
                            // /* 滚动title渐变的效果 */
                            // if (arg1 == 0) {// 当前第一位显示为1
                            // View childAt = arg0.getChildAt(0);// 这个是headerView
                            // LogYiFu.e("滑动测试", "滑到位置");
                            // if (childAt != null) {
                            // LogYiFu.e("滑动测试", "滑到位置");
                            // currentY = childAt.getTop();
                            // viewTop = childAt.getMeasuredHeight() +
                            // childAt.getTop();
                            // }
                            //
                            // } else if (arg1 > 0) {
                            // LogYiFu.e("滑动测试", "滑到位置");
                            // currentY = heights;
                            // viewTop = HomePageFragment.ll_search.getHeight();
                            // }
                            // if (currentY == 0) {
                            // LogYiFu.e("滑动测试", "滑到位置");
                            // HomePageFragment.ll_search.setBackgroundResource(R.drawable.zhezhao2x);
                            // // aa2.setBackgroundResource(R.drawable.icon_fanhui);
                            // HomePageFragment.tv_fenlei.setBackgroundResource(R.drawable.icon_fenlei_white);
                            // HomePageFragment.tv_zhuanqian.setBackgroundResource(R.drawable.icon_zhuanqian_white);
                            //
                            // HomePageFragment.ll_search.getBackground().setAlpha(255);
                            // // mTopView.setVisibility(View.GONE);
                            // }
                            // if (Math.abs(currentY) > 0 && Math.abs(currentY) <
                            // heights / 2) {
                            // LogYiFu.e("滑动测试", "滑到位置");
                            // // aa2.setBackgroundResource(R.drawable.icon_fanhui);
                            // HomePageFragment.tv_fenlei.setBackgroundResource(R.drawable.icon_fenlei_white);
                            // HomePageFragment.tv_zhuanqian.setBackgroundResource(R.drawable.icon_zhuanqian_white);
                            // // img_cart_top
                            // // .setBackgroundResource(R.drawable.icon_gouwuche);
                            // // img_fenx_top
                            // // .setBackgroundResource(R.drawable.icon_fenxiang);
                            // int i = (int) Math.abs(currentY / heights * 255);
                            //
                            // if (Math.abs(currentY) == 0) {
                            // i = 1;
                            // }
                            // // aa2.getBackground().setAlpha(255 - i);
                            // HomePageFragment.tv_fenlei.getBackground().setAlpha(255
                            // - i);
                            // HomePageFragment.tv_zhuanqian.getBackground().setAlpha(255
                            // - i);
                            // // img_cart_top.getBackground()
                            // // .setAlpha(255 - i * 2 / 5);
                            // // img_fenx_top.getBackground()
                            // // .setAlpha(255 - i * 2 / 5);
                            //
                            // }
                            //
                            // if (Math.abs(currentY) >= heights / 2 &&
                            // Math.abs(currentY) < heights) {
                            // LogYiFu.e("滑动测试", "滑到位置");
                            // //
                            // aa2.setBackgroundResource(R.drawable.icon_fanhui_black);
                            // //
                            // mShuaixuanNew.setBackgroundResource(R.drawable.icon_shaixuan_new);
                            // //
                            // lin_contact.setBackgroundResource(R.drawable.icon_lianxikefu_gray);
                            //
                            // HomePageFragment.tv_fenlei.setBackgroundResource(R.drawable.icon_fenlei_black);
                            // HomePageFragment.tv_zhuanqian.setBackgroundResource(R.drawable.icon_zhuanqian_black);
                            //
                            // // img_cart_top
                            // //
                            // .setBackgroundResource(R.drawable.icon_gouwuche_black);
                            // // img_fenx_top
                            // //
                            // .setBackgroundResource(R.drawable.icon_fenxiang_black);
                            // int i = (int) Math.abs(currentY / heights * 255);
                            //
                            // if (Math.abs(currentY) == 0) {
                            // i = 1;
                            // }
                            // // aa2.getBackground().setAlpha(i);
                            // // mShuaixuanNew.getBackground().setAlpha(i);
                            // // lin_contact.getBackground().setAlpha(i);
                            // HomePageFragment.tv_fenlei.getBackground().setAlpha(i);
                            // HomePageFragment.tv_zhuanqian.getBackground().setAlpha(i);
                            // // img_cart_top.getBackground()
                            // // .setAlpha(255 * i / 100);
                            // // img_fenx_top.getBackground()
                            // // .setAlpha(255 * i / 100);
                            // }
                            //
                            // if (Math.abs(currentY) <= heights &&
                            // Math.abs(currentY) > 0) {
                            // LogYiFu.e("滑动测试", "滑到位置");
                            // HomePageFragment.ll_search.setBackgroundColor(getResources().getColor(R.color.white));
                            // int i = (int) Math.abs(currentY / heights * 255);
                            //
                            // if (Math.abs(currentY) == 0) {
                            // i = 1;
                            // }
                            // HomePageFragment.ll_search.getBackground().setAlpha(i);
                            // }
                            //
                            // if (Math.abs(currentY) >= heights) {
                            // LogYiFu.e("滑动测试", "滑到位置");
                            // HomePageFragment.ll_search.getBackground().setAlpha(255);
                            // //
                            // aa2.setBackgroundResource(R.drawable.icon_fanhui_black);
                            // //
                            // mShuaixuanNew.setBackgroundResource(R.drawable.icon_shaixuan_new);
                            // //
                            // lin_contact.setBackgroundResource(R.drawable.icon_lianxikefu_gray);
                            // HomePageFragment.tv_fenlei.setBackgroundResource(R.drawable.icon_fenlei_black);
                            // HomePageFragment.tv_zhuanqian.setBackgroundResource(R.drawable.icon_zhuanqian_black);
                            //
                            // }

                        }
                    });

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
        FragmentStatePagerAdapter fsAdapter = (FragmentStatePagerAdapter) a;
        Fragment item = (Fragment) fsAdapter.instantiateItem(mViewPager, currentItem);
        mInnerScrollView = (ViewGroup) (item.getView().findViewById(R.id.dataList));
    }

    private TextView text;
    private ImageView loading;
    private View textLoading;

    private boolean isAnim = false;

    // 处理刷新头
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
                    if (dy > 0 && getScrollY() <= 0) {
                        scrollBy(0, (int) (-dy / 1.8));
                        if (Math.abs(getScrollY()) < refreshHeight) {
                            animRefresh.setVisibility(View.GONE);
                            textLoading.setVisibility(View.VISIBLE);
                            loading.clearAnimation();
                            text.setText("下拉刷新");
                            loading.setImageResource(R.drawable.img_arrow_down);
                            isAnim = false;
                        } else if (Math.abs(getScrollY()) >= refreshHeight) {
                            if (isAnim == false) {
                                animRefresh.setVisibility(View.GONE);
                                textLoading.setVisibility(View.VISIBLE);
                                text.setText("释放刷新");
                                // loading.setImageResource(R.drawable.arrow_up);
                                loading.startAnimation(anim);
                                isAnim = true;
                            }
                        }

                    } else {
                        scrollBy(0, (int) (-dy));
                    }
                    if (getScrollY() == mTopViewHeight && dy < 0) {
                        event.setAction(MotionEvent.ACTION_DOWN);
                        dispatchTouchEvent(event);
                        isInControl = false;
                    }

                }
                if (getScrollY() >= mTopViewHeight && dy < 0) {
                    HomePageFragment.tv_fenlei.setBackgroundResource(R.drawable.icon_fenlei_black);
                    HomePageFragment.tv_zhuanqian.setBackgroundResource(R.drawable.icon_zhuanqian_black);
                    HomePageFragment.ll_search.setBackgroundResource(R.drawable.white_fanzao);
                } else {
                    HomePageFragment.tv_fenlei.setBackgroundResource(R.drawable.icon_fenlei_white);
                    HomePageFragment.tv_zhuanqian.setBackgroundResource(R.drawable.icon_zhuanqian_white);
                    HomePageFragment.ll_search.setBackgroundResource(R.drawable.zhezhao2x);
                }
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                mDragging = false;
                isAnim = false;
                recycleVelocityTracker();
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_UP:
                mDragging = false;
                isAnim = false;
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityY = (int) mVelocityTracker.getYVelocity();

                if (getScrollY() <= -refreshHeight) {
                    mScroller.fling(0, getScrollY(), 0, mMaximumVelocity, 0, 0, 0, -refreshHeight);
                    invalidate();
                    if (lintener != null) {
                        animRefresh.setVisibility(View.VISIBLE);
                        ((AnimationDrawable) animRefresh.getBackground()).start();
                        textLoading.setVisibility(View.GONE);
                        lintener.onRefreshlintener();
                    }
                }

                if (getScrollY() > -refreshHeight) {
                    if (Math.abs(velocityY) > mMinimumVelocity) {
                        fling(-velocityY);
                    } else {
                        fling(mMinimumVelocity);
                    }
                }

                recycleVelocityTracker();
                break;
        }

        return super.onTouchEvent(event);
    }

    private MyOnRefreshLintener lintener;

    public interface MyOnRefreshLintener {
        public void onRefreshlintener();
    }

    public void refreshDone() {
        if (getScrollY() != 0) {
            // mScroller.fling(0, getScrollY(), 0, mMaximumVelocity, 0, 0, 0,
            // 0);
            // invalidate();
            fling(0);
            recycleVelocityTracker();
        }
        isAnim = false;
    }

    public void setOnRefreshLintener(MyOnRefreshLintener l) {
        this.lintener = l;
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
