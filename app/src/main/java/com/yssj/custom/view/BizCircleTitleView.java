//package com.yssj.custom.view;
//
//
//import com.yssj.activity.R;
//import com.yssj.ui.activity.BizTitleCheckActivity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
///**
// * 
// * 商家圈导航栏
// * @author lbp
// *
// */
//public class BizCircleTitleView extends LinearLayout {
//	
//	private ViewPager pagers; 
//	
//	private LinearLayout one,two;
//	
//	private ImageView imageOne,imageTwo;
//	
//	private LayoutInflater inflater;
//	
//	private Context context;
//	
//	public BizCircleTitleView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		this.context=context;
//		inflater=LayoutInflater.from(context);
//		inflater.inflate(R.layout.biz_circle_title_view, this);
//		pagers=(ViewPager) findViewById(R.id.titles);
//		imageOne=(ImageView) findViewById(R.id.one);
//		imageTwo=(ImageView) findViewById(R.id.two);
//		imageOne.setImageResource(R.drawable.title_selected);
//		imageTwo.setImageResource(R.drawable.title_selector);
//		one=(LinearLayout) inflater.inflate(R.layout.biz_circle_title_item_one, null);
//		two=(LinearLayout) inflater.inflate(R.layout.biz_circle_title_item_two, null);
//		for (int i = 0; i < one.getChildCount(); i++) {
//			one.getChildAt(i).setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					BizCircleTitleView.this.context.startActivity(new Intent(BizCircleTitleView.this.context, BizTitleCheckActivity.class));
//				}
//			});
//		}
//		PagersAdapter adapter=new PagersAdapter();
//		pagers.setAdapter(adapter);
//		pagers.setOnPageChangeListener(new OnPageChangeListener() {
//			
//			@Override
//			public void onPageSelected(int arg0) {
//				if(arg0==0){
//					imageOne.setImageResource(R.drawable.title_selected);
//					imageTwo.setImageResource(R.drawable.title_selector);
//				}else{
//					imageOne.setImageResource(R.drawable.title_selector);
//					imageTwo.setImageResource(R.drawable.title_selected);
//				}
//			}
//			
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//				
//			}
//			
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//				
//			}
//		});
//	}
//
//	private class PagersAdapter extends PagerAdapter{
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return 2;
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			
//			return arg0==arg1;
//		}
//		
//		@Override
//		public Object instantiateItem(ViewGroup container, int position) {
//			
//			container.addView(position==0?one:two);
//			return position==1?two:one;
//		}
//		@Override
//		public void destroyItem(ViewGroup container, int position, Object object) {
//			
//			container.removeView((View) object);
//		}
//	}
//}
