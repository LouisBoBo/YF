//package com.yssj;
//
//import java.util.HashMap;
//import java.util.List;
//
//import com.yssj.activity.R;
//import com.yssj.custom.view.MyTitleView;
//import com.yssj.custom.view.MyTitleView.OnCheckTitleLentener;
//import com.yssj.custom.view.ScrollPagerList;
//import com.yssj.data.YDBHelper;
//import com.yssj.ui.fragment.ItemFragmentShopBeauty;
//import com.yssj.ui.fragment.MyShopFragment;
//import com.yssj.ui.fragment.MyShopFragment.PagerAdapter;
//
//import android.R.color;
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.text.GetChars;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//public class ShopBeautyActivity extends FragmentActivity implements OnCheckTitleLentener, OnClickListener{
//	private ImageButton mBack;
//	private MyTitleView mMyTitleView;
//	private ViewPager mContentViewpager;
//	private List<HashMap<String, String>> listTitle;
//	private ItemFragmentShopBeauty[] mFragment;
//	private PagerAdapter mAdapter;
//	private int currentPosition=0;
//	private LinearLayout root;
//	@Override
//	protected void onCreate(Bundle arg0) {
//		// TODO Auto-generated method stub
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		super.onCreate(arg0);
//		setContentView(R.layout.activity_shopbeauty);
//		initData();
//		initView();
//		
//		initEvent();
//	}
//
//private void initEvent() {
//	mMyTitleView.setData(listTitle);
//	mMyTitleView.setCheckLintener(this);
//	mFragment = new ItemFragmentShopBeauty[listTitle.size()];
//	mAdapter = new PagerAdapter(getSupportFragmentManager());
//	mContentViewpager.setAdapter(mAdapter);
//	mContentViewpager.setOnPageChangeListener(new OnPageChangeListener() {
//
//		@Override
//		public void onPageSelected(int arg0) {
//			mMyTitleView.setPosition(arg0);
//			currentPosition = arg0;
//			if (null != ((ItemFragmentShopBeauty) mAdapter.getItem(arg0)).getmList()
//					&& ((ItemFragmentShopBeauty) mAdapter.getItem(arg0)).getmList()
//							.getCount() != 0)
//				((ItemFragmentShopBeauty) mAdapter.getItem(arg0)).getmList()
//						.setSelection(0);
//		}
//
//		@Override
//		public void onPageScrolled(int arg0, float arg1, int arg2) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onPageScrollStateChanged(int arg0) {
//			// TODO Auto-generated method stub
//
//		}
//	});
//	mBack.setOnClickListener(this);
//}
//
//private void initData() {
//	YDBHelper helper = new YDBHelper(this);
//	String sql = "select * from sort_info where p_id = 0 and is_show = 1 order by sequence";
//	listTitle = helper.query(sql);
//	
//}
//
//private void initView() {
//	mBack=(ImageButton) findViewById(R.id.shopbeauty_iv_back);
//	
////	mScrollPagerList=(ScrollPagerList) findViewById(R.id.shopbeauty_scroll);
//	mMyTitleView=(MyTitleView) findViewById(R.id.shopbeauty_mytitleview_title);
//	mContentViewpager=(ViewPager) findViewById(R.id.shopbeauty_content_viewpager);
//	root = (LinearLayout) findViewById(R.id.root);
//	root.setBackgroundColor(Color.WHITE);
//}
//
//@Override
//public void checkTitle(View v) {
//	mContentViewpager.setCurrentItem(v.getId());
//	currentPosition = v.getId();
//	
//}
///**
// * 此处应当继承FragmentStatePagerAdapter
// * 在处理数据量较大的页面应当使用FragmentStatePagerAdapter，而不是FragmentPagerAdapter
// */
//public class PagerAdapter extends FragmentStatePagerAdapter {
//	public PagerAdapter(FragmentManager fm) {
//		super(fm);
//	}
//
//	@Override
//	public CharSequence getPageTitle(int position) {
//		return listTitle.get(position).get("sort_name");
//	}
//
//	@Override
//	public int getCount() {
//		return listTitle.size();
//	}
//
//	@Override
//	public Fragment getItem(int position) {
//		ItemFragmentShopBeauty fragment = mFragment[position];
//		if (fragment == null) {
//			fragment = ItemFragmentShopBeauty.newInstances(position,
//					listTitle.get(position).get("sort_name"), listTitle
//							.get(position).get("_id"), ShopBeautyActivity.this);
//			mFragment[position] = fragment;
//		}
//		return fragment;
//	}
//
//}
//@Override
//public void onClick(View v) {
//	if(v.getId()==R.id.shopbeauty_iv_back){
//		finish();
//	}
//	
//}
//
//}
