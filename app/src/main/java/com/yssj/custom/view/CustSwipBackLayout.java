package com.yssj.custom.view;

import com.yssj.app.AppManager;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsGroupIndianaActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsIndianaActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsMoneyIndianaActivity;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;
/**
 * 左滑返回
 * @author lbp
 *
 */
public class CustSwipBackLayout extends LinearLayout{
	
	private int width;
	
	private boolean isDrag=false;
	
	private Scroller s;
	
	private int X;
	
	private int minX;
	
	private boolean isClose=false;
	
	private boolean isMove=false;
	private Context mContext;
	public CustSwipBackLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		width=context.getResources().getDisplayMetrics().widthPixels;
		s=new Scroller(context);
		minX=ViewConfiguration.get(context).getScaledTouchSlop();
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		
		int rawX=(int) event.getRawX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		{
			if(rawX<100){
				isDrag=true;
			}else{
				isDrag=false;
			}
			X=rawX;
			isClose=false;
			isMove=false;
		}
		break;
		case MotionEvent.ACTION_MOVE:
		{
			if(isDrag&&rawX-X>minX){
				scrollTo(-rawX, 0);
				isMove=true;
				return true;
			}
			
		}
		break;
		case MotionEvent.ACTION_UP:
		{	
			if(rawX>=width/2&&isDrag){
				s.startScroll(-rawX, 0, -width+rawX, 0);
				invalidate();
				isClose=true;
				isDrag=false;
				isMove=false;
				return true;
			}else if(isMove){
				s.startScroll(-rawX, 0, rawX, 0);
				invalidate();
				isDrag=false;
				isMove=false;
				return true;
			}
		}
		break;
		case MotionEvent.ACTION_CANCEL:
		{
			if(rawX>=width/2&&isDrag){
				s.startScroll(-rawX, 0, -width+rawX, 0);
				invalidate();
				isDrag=false;
				isClose=true;
				isMove=false;
				return true;
			}else if(isMove){
				s.startScroll(-rawX, 0, rawX, 0);
				invalidate();
				isDrag=false;
				isMove=false;
				return true;
			}
		}
		break;
		default:
			break;
		}
		
		return super.dispatchTouchEvent(event);
	}
	
	@Override
	public void computeScroll() {
		if(s.computeScrollOffset()){
			scrollTo(s.getCurrX(), s.getCurrY());
			postInvalidate();
		}else{
			if(isClose){
//				if(mContext!=null&&mContext instanceof ShopDetailsActivity&& ShopDetailsActivity.instance!=null){
//					ShopDetailsActivity.instance.onBackPressed();
//				}
//				if(mContext!=null&&mContext instanceof ShopDetailsIndianaActivity&&ShopDetailsIndianaActivity.instance!=null){
//					ShopDetailsIndianaActivity.instance.onBackPressed();
//				}
//				if(mContext!=null&&mContext instanceof ShopDetailsMoneyIndianaActivity&&ShopDetailsMoneyIndianaActivity.instance!=null){
//					ShopDetailsMoneyIndianaActivity.instance.onBackPressed();
//				}
				AppManager.getAppManager().finishDetailsActivity((Activity) mContext);
//				if(mContext!=null&&mContext instanceof ShopDetailsGroupIndianaActivity&&ShopDetailsGroupIndianaActivity.instance!=null){
//					ShopDetailsGroupIndianaActivity.instance.onBackPressed();
//				}

			}
		}
	}
}
