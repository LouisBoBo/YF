package com.yssj.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.base.BasePager;
import com.yssj.ui.pager.AllCouponsPage;
import com.yssj.ui.pager.UsefulCouponsPage;
import com.yssj.ui.pager.UselessCouponsPage;
import com.yssj.utils.WXminiAppUtil;


public class UsefulCouponsListFragment extends BaseFragment implements OnClickListener  {
	private ViewPager content_pager;
	  
	private List<BasePager> pageLists;
	
	private int currIndex = 0;// 当前页卡编号
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private ImageView img_right_icon;
	private double amount;
	private double  jinquan;

	public UsefulCouponsListFragment(double amount,double jinquan){
		this.amount = amount;
		this.jinquan= jinquan;
	}
	@Override
	public View initView() {
		view = View.inflate(context, R.layout.usefulcoupons_list, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("我的优惠券");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		img_right_icon = (ImageView) view.findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		img_right_icon.setVisibility(View.GONE);
		
		content_pager = (ViewPager) view.findViewById(R.id.coupons_content_pager);
		
		
		
		return view;
	}

	@Override
	public void initData() {
		initViewPager();
        
	}
	
	
	/**初始化ViewPager*/
	private void initViewPager() {
		pageLists = new ArrayList<BasePager>();
		pageLists.add(new UsefulCouponsPage(getActivity(),amount,jinquan));
		
		
		pageLists.get(0).initData();   // 第一次进来时加载数据
		
		content_pager.setOffscreenPageLimit(1);   // 设置预加载页面
		content_pager.setAdapter(new MyPagerAdapter(pageLists));
		
		content_pager.setCurrentItem(currIndex);
		content_pager.setOnPageChangeListener(new MyOnPageChangeListener());
		
	}
	
	
	
	/* 页卡切换监听 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			pageLists.get(arg0).initData();
			

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
	
	

	
	class MyPagerAdapter extends PagerAdapter {
		private List<BasePager> pageLists;

		public MyPagerAdapter(List<BasePager> pageLists) {
			this.pageLists = pageLists;
		}

		@Override
		public int getCount() {
			return pageLists.size();
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(pageLists.get(position).getRootView());
			return pageLists.get(position).getRootView();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.img_back:
			getActivity().finish();
			break;
		case R.id.img_right_icon:// 消息盒子
			WXminiAppUtil.jumpToWXmini(getActivity());

			break;

		}
	}


}
