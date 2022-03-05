//<<<<<<< .mine
////package com.yssj.ui.fragment.circles;
////
////import java.util.ArrayList;
////import java.util.List;
////
////import android.content.Context;
////import android.graphics.Color;
////import android.graphics.Matrix;
////import android.os.Bundle;
////import android.support.v4.view.PagerAdapter;
////import android.support.v4.view.ViewPager;
////import android.support.v4.view.ViewPager.OnPageChangeListener;
////import android.view.View;
////import android.view.View.OnClickListener;
////import android.view.ViewGroup;
////import android.view.ViewTreeObserver.OnGlobalLayoutListener;
////import android.view.WindowManager;
////import android.view.animation.Animation;
////import android.view.animation.TranslateAnimation;
////import android.widget.ImageView;
////import android.widget.LinearLayout;
////import android.widget.LinearLayout.LayoutParams;
////import android.widget.TextView;
////
////import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
////import com.yssj.activity.R;
////import com.yssj.ui.base.BaseFragment;
////import com.yssj.ui.base.BasePager;
////import com.yssj.ui.fragment.ZeroShopFragment;
////import com.yssj.ui.pager.AllPager;
////import com.yssj.ui.pager.MinePager;
////import com.yssj.ui.pager.MinePager.TransToAllPager;
////import com.yssj.ui.pager.RecommendPager;
////
////public class CircleFragment 
//////extends BaseFragment 
////
//////implements TransToAllPager,com.yssj.ui.pager.RecommendPager.TransToAllPager
////{
////
//////	private ViewPager content_pager;
//////	private LinearLayout ll_cursor; // 下划线
//////	private ImageView cursor;
//////	private LinearLayout ll;
//////	private List<BasePager> pageLists;
//////	private TextView textView1, textView2, textView3;
//////	private int tvWidth = 0;// 页卡标题的宽度
//////	private int currIndex = 1;// 当前页卡编号
//////	private static  Context mContext;
//////	
//////	private LinearLayout ll_head;
//////	
//////	public CircleFragment() {
//////	}
//////
//////	public static CircleFragment newInstance(Context context) {
//////		CircleFragment fragment = new CircleFragment();
////////		Bundle args = new Bundle();
////////		args.putString("circle", title);
////////		fragment.setArguments(args);
//////		
//////		mContext = context;
//////		return fragment;
//////
//////	}
//////	@Override
//////	public void onCreate(Bundle savedInstanceState) {
//////		super.onCreate(savedInstanceState);
//////		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
//////		WindowManager wm = (WindowManager) mContext
//////                .getSystemService(Context.WINDOW_SERVICE);
//////
//////		tvWidth = wm.getDefaultDisplay().getWidth()/3;
//////	}
//////
//////	@Override
//////	public View initView() {
//////		view = View.inflate(mContext, R.layout.circle_main, null);
//////
//////		ll_cursor = (LinearLayout) view.findViewById(R.id.ll_cursor);
//////		cursor    = (ImageView) view.findViewById(R.id.cursor);
//////		ll = (LinearLayout) view.findViewById(R.id.ll);
//////		content_pager = (ViewPager) view.findViewById(R.id.content_pager);
//////
//////		textView1 = (TextView) view.findViewById(R.id.text1);
//////		textView2 = (TextView) view.findViewById(R.id.text2);
//////		textView3 = (TextView) view.findViewById(R.id.text3);
//////		
//////		ll_head = (LinearLayout) view.findViewById(R.id.ll_head);
////////		tvWidth = textView1.getWidth();
//////		
//////		/**
//////		 * 初始化下划线位置
//////		 */
//////		ll_head.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//////			
//////			@Override
//////			public void onGlobalLayout() {
//////				ll_head.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//////				tvWidth = textView1.getWidth();
//////				setImageViewWidth(tvWidth);
//////			}
//////		});
//////		return view;
//////	}
//////
//////	@Override
//////	public void initData() {
//////		
//////		initViewPager();
//////		initTextView();
//////		InitImageView();
//////	};
//////	
//////	@Override
//////	public void onResume() {
//////		// TODO Auto-generated method stub
//////		super.onResume();
//////		if(!isHidden()){
////////			initTextView();
////////			InitImageView();
//////			initViewPager();
//////		}
//////		
//////	}
//////	
//////	@Override
//////	public void onHiddenChanged(boolean hidden) {
//////		// TODO Auto-generated method stub
//////		super.onHiddenChanged(hidden);
//////		
//////		if(!hidden){
//////			currIndex = 1;
//////			initTextView();
//////			InitImageView();
//////			initViewPager();
//////		}
//////	}
//////	
//////	/** 初始化ViewPager */
//////	private void initViewPager() {
//////		pageLists = new ArrayList<BasePager>();
//////		pageLists.add(new MinePager(mContext));
//////		pageLists.add(new RecommendPager(mContext));
//////		pageLists.add(new AllPager(mContext));
//////
////////		pageLists.get(1).initData(); // 第一次进来时加载数据
//////		((MinePager) pageLists.get(0)).setTransToAllPager(this);
//////		pageLists.get(0).initData();
//////		((RecommendPager) pageLists.get(1)).setTransToAllPager(this);
//////		
//////		content_pager.setOffscreenPageLimit(3); // 设置预加载页面
//////		content_pager.setAdapter(new MyPagerAdapter(pageLists));
//////		content_pager.setOnPageChangeListener(new MyOnPageChangeListener());
//////		
//////		content_pager.setCurrentItem(currIndex);
//////		
//////	}
//////
//////	private void initTextView() {
//////
//////		textView2.setTextColor(getResources().getColor(R.color.pink_color));
//////		textView1.setOnClickListener(new MyOnClickListener(0));
//////		textView2.setOnClickListener(new MyOnClickListener(1));
//////		textView3.setOnClickListener(new MyOnClickListener(2));
//////	}
//////
//////	/* 页卡切换监听 */
//////	public class MyOnPageChangeListener implements OnPageChangeListener {
//////
//////		@Override
//////		public void onPageSelected(int arg0) {
//////			
//////			// 停留在第一个页面的时候才可以去做响应拖拽的操作
////////			if(arg0 == 0){
////////				slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
////////			}else{
////////			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
////////			}
//////			
//////			pageLists.get(arg0).initData();
//////			Animation animation = new TranslateAnimation(tvWidth* currIndex,
//////					tvWidth * arg0, 0, 0);
//////			currIndex = arg0;
//////			animation.setFillAfter(true);/* True:图片停在动画结束位置 */
//////			animation.setDuration(300);
//////			ll_cursor.startAnimation(animation);
//////			setTextTitleSelectedColor(arg0);
//////			setImageViewWidth(textView1.getWidth());
//////
//////		}
//////
//////		@Override
//////		public void onPageScrolled(int arg0, float arg1, int arg2) {
//////		}
//////
//////		@Override
//////		public void onPageScrollStateChanged(int arg0) {
//////		}
//////	}
//////
//////	/** 设置标题文本的颜色 **/
//////	private void setTextTitleSelectedColor(int arg0) {
//////		int count = pageLists.size();
//////		for (int i = 0; i < count; i++) {
//////			TextView mTextView = (TextView) ll.getChildAt(i);
//////			if (arg0 == i) {
//////				mTextView.setTextColor(getResources().getColor(
//////						R.color.pink_color));
//////			} else {
//////				mTextView.setTextColor(Color.BLACK);
//////			}
//////		}
//////	}
//////
//////	/* 设置图片宽度 */
//////	private void setImageViewWidth(int width) {
//////		if (width != ll_cursor.getWidth()) {
//////			LayoutParams laParams = (LayoutParams) ll_cursor.getLayoutParams();
//////			laParams.width = tvWidth;
//////			ll_cursor.setLayoutParams(laParams);
//////		}
//////	}
//////
//////	/* 标题点击监听 */
//////	private class MyOnClickListener implements OnClickListener {
//////		private int index = 1;
//////
//////		public MyOnClickListener(int i) {
//////			index = i;
//////		}
//////
//////		public void onClick(View v) {
//////			content_pager.setCurrentItem(index);
//////		}
//////	}
//////
//////	/** 初始化动画 */
//////	private void InitImageView() {
//////		Matrix matrix = new Matrix();
//////		matrix.postTranslate(0, 0);
//////		cursor.setImageMatrix(matrix);// 设置动画初始位置
//////	}
//////
//////	class MyPagerAdapter extends PagerAdapter {
//////		private List<BasePager> pageLists;
//////
//////		public MyPagerAdapter(List<BasePager> pageLists) {
//////			this.pageLists = pageLists;
//////		}
//////
//////		@Override
//////		public int getCount() {
//////			return pageLists.size();
//////		}
//////
//////		@Override
//////		public Object instantiateItem(ViewGroup container, int position) {
//////			container.addView(pageLists.get(position).getRootView());
//////			return pageLists.get(position).getRootView();
//////		}
//////
//////		@Override
//////		public void destroyItem(ViewGroup container, int position, Object object) {
//////			container.removeView((View) object);
//////		}
//////
//////		@Override
//////		public boolean isViewFromObject(View arg0, Object arg1) {
//////			return arg0 == arg1;
//////		}
//////	}
//////
//////	@Override
//////	public void turnToAll() {
//////		content_pager.setCurrentItem(2);
//////	}
//////
//////	@Override
//////	public void onClick(View v) {
//////		
//////	}
////
////}
//=======
//package com.yssj.ui.fragment.circles;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.Matrix;
//import android.os.Bundle;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.ViewTreeObserver.OnGlobalLayoutListener;
//import android.view.WindowManager;
//import android.view.animation.Animation;
//import android.view.animation.TranslateAnimation;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.LinearLayout.LayoutParams;
//import android.widget.TextView;
//
//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
//import com.yssj.activity.R;
//import com.yssj.ui.base.BaseFragment;
//import com.yssj.ui.base.BasePager;
//import com.yssj.ui.fragment.ZeroShopFragment;
//import com.yssj.ui.pager.AllPager;
//import com.yssj.ui.pager.MinePager;
//import com.yssj.ui.pager.MinePager.TransToAllPager;
//import com.yssj.ui.pager.RecommendPager;
//
//public class CircleFragment extends BaseFragment
////implements TransToAllPager,com.yssj.ui.pager.RecommendPager.TransToAllPager
//{
//
//	private ViewPager content_pager;
//	private LinearLayout ll_cursor; // 下划线
//	private ImageView cursor;
//	private LinearLayout ll;
//	private List<BasePager> pageLists;
//	private TextView textView1, textView2, textView3;
//	private int tvWidth = 0;// 页卡标题的宽度
//	private int currIndex = 1;// 当前页卡编号
//	private static  Context mContext;
//	
//	private LinearLayout ll_head;
//	
//	public CircleFragment() {
//	}
//
//	public static CircleFragment newInstance(Context context) {
//		CircleFragment fragment = new CircleFragment();
////		Bundle args = new Bundle();
////		args.putString("circle", title);
////		fragment.setArguments(args);
//		
//		mContext = context;
//		return fragment;
//
//	}
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
//		WindowManager wm = (WindowManager) mContext
//                .getSystemService(Context.WINDOW_SERVICE);
//
//		tvWidth = wm.getDefaultDisplay().getWidth()/3;
//	}
//
//	@Override
//	public View initView() {
//		view = View.inflate(mContext, R.layout.circle_main, null);
//
//		ll_cursor = (LinearLayout) view.findViewById(R.id.ll_cursor);
//		cursor    = (ImageView) view.findViewById(R.id.cursor);
//		ll = (LinearLayout) view.findViewById(R.id.ll);
//		content_pager = (ViewPager) view.findViewById(R.id.content_pager);
//
//		textView1 = (TextView) view.findViewById(R.id.text1);
//		textView2 = (TextView) view.findViewById(R.id.text2);
//		textView3 = (TextView) view.findViewById(R.id.text3);
//		
//		ll_head = (LinearLayout) view.findViewById(R.id.ll_head);
////		tvWidth = textView1.getWidth();
//		
//		/**
//		 * 初始化下划线位置
//		 */
//		ll_head.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//			
//			@Override
//			public void onGlobalLayout() {
//				ll_head.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//				tvWidth = textView1.getWidth();
//				setImageViewWidth(tvWidth);
//			}
//		});
//		return view;
//	}
//
//	@Override
//	public void initData() {
//		
//		initViewPager();
//		initTextView();
//		InitImageView();
//	};
//	
////	@Override
////	public void onResume() {
////		// TODO Auto-generated method stub
////		super.onResume();
////		if(!isHidden()){
//////			initTextView();
//////			InitImageView();
////			initViewPager();
////		}
////		
////	}
//	
////	@Override
////	public void onHiddenChanged(boolean hidden) {
////		// TODO Auto-generated method stub
////		super.onHiddenChanged(hidden);
////		
////		if(!hidden){
////			currIndex = 1;
////			initTextView();
////			InitImageView();
////			initViewPager();
////		}
////	}
//	
//	/** 初始化ViewPager */
//	private void initViewPager() {
//		pageLists = new ArrayList<BasePager>();
//		pageLists.add(new MinePager(mContext));
////		pageLists.add(new RecommendPager(mContext));
////		pageLists.add(new AllPager(mContext));
//
////		pageLists.get(1).initData(); // 第一次进来时加载数据
//		((MinePager) pageLists.get(0)).setTransToAllPager(this);
//		pageLists.get(0).initData();
////		((RecommendPager) pageLists.get(1)).setTransToAllPager(this);
//		
//		content_pager.setOffscreenPageLimit(3); // 设置预加载页面
//		content_pager.setAdapter(new MyPagerAdapter(pageLists));
//		content_pager.setOnPageChangeListener(new MyOnPageChangeListener());
//		
//		content_pager.setCurrentItem(currIndex);
//		
//	}
//
//	private void initTextView() {
//
//		textView2.setTextColor(getResources().getColor(R.color.pink_color));
//		textView1.setOnClickListener(new MyOnClickListener(0));
//		textView2.setOnClickListener(new MyOnClickListener(1));
//		textView3.setOnClickListener(new MyOnClickListener(2));
//	}
//
//	/* 页卡切换监听 */
//	public class MyOnPageChangeListener implements OnPageChangeListener {
//
//		@Override
//		public void onPageSelected(int arg0) {
//			
//			// 停留在第一个页面的时候才可以去做响应拖拽的操作
////			if(arg0 == 0){
////				slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
////			}else{
////			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
////			}
//			
//			pageLists.get(arg0).initData();
//			Animation animation = new TranslateAnimation(tvWidth* currIndex,
//					tvWidth * arg0, 0, 0);
//			currIndex = arg0;
//			animation.setFillAfter(true);/* True:图片停在动画结束位置 */
//			animation.setDuration(300);
//			ll_cursor.startAnimation(animation);
//			setTextTitleSelectedColor(arg0);
//			setImageViewWidth(textView1.getWidth());
//
//		}
//
//		@Override
//		public void onPageScrolled(int arg0, float arg1, int arg2) {
//		}
//
//		@Override
//		public void onPageScrollStateChanged(int arg0) {
//		}
//	}
//
//	/** 设置标题文本的颜色 **/
//	private void setTextTitleSelectedColor(int arg0) {
//		int count = pageLists.size();
//		for (int i = 0; i < count; i++) {
//			TextView mTextView = (TextView) ll.getChildAt(i);
//			if (arg0 == i) {
//				mTextView.setTextColor(getResources().getColor(
//						R.color.pink_color));
//			} else {
//				mTextView.setTextColor(Color.BLACK);
//			}
//		}
//	}
//
//	/* 设置图片宽度 */
//	private void setImageViewWidth(int width) {
//		if (width != ll_cursor.getWidth()) {
//			LayoutParams laParams = (LayoutParams) ll_cursor.getLayoutParams();
//			laParams.width = tvWidth;
//			ll_cursor.setLayoutParams(laParams);
//		}
//	}
//
//	/* 标题点击监听 */
//	private class MyOnClickListener implements OnClickListener {
//		private int index = 1;
//
//		public MyOnClickListener(int i) {
//			index = i;
//		}
//
//		public void onClick(View v) {
//			content_pager.setCurrentItem(index);
//		}
//	}
//
//	/** 初始化动画 */
//	private void InitImageView() {
//		Matrix matrix = new Matrix();
//		matrix.postTranslate(0, 0);
//		cursor.setImageMatrix(matrix);// 设置动画初始位置
//	}
//
//	class MyPagerAdapter extends PagerAdapter {
//		private List<BasePager> pageLists;
//
//		public MyPagerAdapter(List<BasePager> pageLists) {
//			this.pageLists = pageLists;
//		}
//
//		@Override
//		public int getCount() {
//			return pageLists.size();
//		}
//
//		@Override
//		public Object instantiateItem(ViewGroup container, int position) {
//			container.addView(pageLists.get(position).getRootView());
//			return pageLists.get(position).getRootView();
//		}
//
//		@Override
//		public void destroyItem(ViewGroup container, int position, Object object) {
//			container.removeView((View) object);
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			return arg0 == arg1;
//		}
//	}
//
////	@Override
////	public void turnToAll() {
////		content_pager.setCurrentItem(2);
////	}
//
//	@Override
//	public void onClick(View v) {
//		
//	}
//
//}
//>>>>>>> .r26813
