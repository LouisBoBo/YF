package com.yssj.mypullto;

import com.yssj.activity.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;

public class PullToRefreshStaggeredGridView extends PullToRefreshBase<StaggeredGridView> {

    public PullToRefreshStaggeredGridView(Context context) {
        super(context);
    }

    public PullToRefreshStaggeredGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshStaggeredGridView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshStaggeredGridView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected StaggeredGridView createRefreshableView(Context context, AttributeSet attrs) {
        StaggeredGridView stgv;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            stgv = new InternalStaggeredGridViewSDK9(context, attrs);
        } else {
            stgv = new StaggeredGridView(context, attrs);
        }

        int margin = getResources().getDimensionPixelSize(R.dimen.stgv_margin);
        stgv.setColumnCount(2);
        stgv.setItemMargin(margin);
        stgv.setPadding(margin, 0, margin, 0);

        stgv.setId(R.id.stgv);
        return stgv;
    }

    
    public static final class anim {
        public static final int slide_in_from_bottom=0x7f040000;
        public static final int slide_in_from_top=0x7f040001;
        public static final int slide_out_to_bottom=0x7f040002;
        public static final int slide_out_to_top=0x7f040003;
    }
    
    @Override
    protected boolean isReadyForPullStart() {
        return mRefreshableView.mGetToTop;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        return false;
    }

    public void setAdapter(BaseAdapter adapter) {
        mRefreshableView.setAdapter(adapter);
    }

    @TargetApi(9)
    final class InternalStaggeredGridViewSDK9 extends StaggeredGridView {

        public InternalStaggeredGridViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                       int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

            final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                    scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

            // Does all of the hard work...
            OverscrollHelper.overScrollBy(PullToRefreshStaggeredGridView.this, deltaX, scrollX, deltaY, scrollY,
                    getScrollRange(), isTouchEvent);

            return returnValue;
        }

        /**
         * Taken from the AOSP ScrollView source
         */
        private int getScrollRange() {
            int scrollRange = 0;
            if (getChildCount() > 0) {
                View child = getChildAt(0);
                scrollRange = Math.max(0, child.getHeight() - (getHeight() - getPaddingBottom() - getPaddingTop()));
            }
            return scrollRange;
        }
    }

    public final void setOnLoadmoreListener(StaggeredGridView.OnLoadmoreListener listener) {
        mRefreshableView.setOnLoadmoreListener(listener);
    }

}
