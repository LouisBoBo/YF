//package com.yssj.ui.activity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.entity.ShopCart;
//import com.yssj.huanxin.activity.ChatAllHistoryActivity;
//import com.yssj.ui.base.BasePager;
//
//public class ShopCartNewActivity extends FragmentActivity implements
//		OnClickListener {
//	private ViewPager mContentViewPager;
//	private List<BasePager> pageLists;
//	private TextView mShopCartCommon, mShopCartSpecial;
//	private int mCurrIndex = 0;// 当前页卡编号
//
//	private LinearLayout mLineHead;
//	private TextView mTitleBase;
//	private LinearLayout img_back;
//	private ImageView img_right_icon;
//	private LinearLayout mBack;
//	private List<ShopCart> mListCommon;
//	private List<ShopCart> mListSpecial;
//private static ShopCartNewActivity instance;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.activity_shop_cart_new);
//		instance=this;
//		initView();
//		initViewPager();
//	}
//
//	public void initView() {
//		mLineHead = (LinearLayout) findViewById(R.id.shopcart_ll);
//		mLineHead.setBackgroundColor(Color.TRANSPARENT);
//		mTitleBase = (TextView) findViewById(R.id.tvTitle_base);
//		mTitleBase.setText("购物车");
//		img_back = (LinearLayout) findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//		mBack = (LinearLayout) findViewById(R.id.shopcart_img_back);
//		mBack.setOnClickListener(this);
//		// ll = (LinearLayout) view.findViewById(R.id.ll);
//		mContentViewPager = (ViewPager) findViewById(R.id.shopcart_content_pager);
//
//		mShopCartCommon = (TextView) findViewById(R.id.shopcart_tv_common);
//		mShopCartSpecial = (TextView) findViewById(R.id.shopcart_tv_specal);
//
//		/*
//		 * 右上角点点点
//		 */
//		img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
//		img_right_icon.setVisibility(View.VISIBLE);
//		img_right_icon.setImageResource(R.drawable.mine_message_center);
//		img_right_icon.setOnClickListener(this);
//
//	}
//
//	/** 初始化ViewPager */
//	private void initViewPager() {
//		pageLists = new ArrayList<BasePager>();
//		pageLists.add(new ShopCartCommonPager(this));// 普通商品
//		pageLists.add(new ShopCartSpecialPager(this));// 特卖商品
//		pageLists.get(0).initData();
//		mContentViewPager.setOffscreenPageLimit(1); // 设置预加载页面
//		mContentViewPager.setAdapter(new MyPagerAdapter(pageLists));
//		mContentViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
//		mContentViewPager.setCurrentItem(mCurrIndex);
//		setTextTitleSelectedColor(mCurrIndex);
//	}
//
//	private void initTextView() {
//
//		// textView1.setTextColor(getResources().getColor(R.color.white));
//		mShopCartCommon.setOnClickListener(new MyOnClickListener(0));
//		mShopCartSpecial.setOnClickListener(new MyOnClickListener(1));
//	}
//
//	/* 页卡切换监听 */
//	public class MyOnPageChangeListener implements OnPageChangeListener {
//
//		@Override
//		public void onPageSelected(int arg0) {
//			pageLists.get(arg0).initData();
//			mCurrIndex = arg0;
//			setTextTitleSelectedColor(arg0);
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
//		if (arg0 == 0) {
//			mShopCartCommon
//					.setTextColor(getResources().getColor(R.color.white));
//			mShopCartCommon
//					.setBackgroundResource(R.drawable.payback_buyed_goods_bg_selected);
//			mShopCartSpecial.setTextColor(getResources().getColor(
//					R.color.title_color));
//			mShopCartSpecial
//					.setBackgroundResource(R.drawable.payback_saled_goods_bg_normal);
//		} else {
//			mShopCartSpecial.setTextColor(getResources()
//					.getColor(R.color.white));
//			mShopCartSpecial
//					.setBackgroundResource(R.drawable.payback_saled_goods_bg_selected);
//			mShopCartCommon.setTextColor(getResources().getColor(
//					R.color.title_color));
//			mShopCartCommon
//					.setBackgroundResource(R.drawable.payback_buyed_goods_bg_normal);
//		}
//	}
//
//	/* 标题点击监听 */
//	private class MyOnClickListener implements OnClickListener {
//		private int index = 0;
//
//		public MyOnClickListener(int i) {
//			index = i;
//		}
//
//		public void onClick(View v) {
//			mContentViewPager.setCurrentItem(index);
//		}
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		mCurrIndex = 0;
//		initViewPager();
//		initTextView();
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
//			View view = ((BasePager) pageLists.get(position)).getRootView();
//			container.addView(view);
//			return view;
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
//	@Override
//	public void onClick(View v) {
//		Intent intent;
//		switch (v.getId()) {
//		case R.id.shopcart_img_back: // 返回
//			onBackPressed();
//			break;
//
//		case R.id.img_right_icon:// 消息盒子
//			WXminiAppUtil.jumpToWXmini(this);
//			startActivity(intent);
//			break;
//
//		}
//	}
//
//	@Override
//	public void onBackPressed() {
//		// TODO Auto-generated method stub
//		// super.onBackPressed();
//		setResult(RESULT_OK);
//		finish();
//
//	}
//
//	// @Override
//	// protected void onPause() {
//	// finish();
//	// super.onPause();
//	// }
//}
