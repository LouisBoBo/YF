package com.yssj.ui.fragment.payback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.ui.activity.setting.SettingActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.base.BasePager;
import com.yssj.ui.fragment.BackHandledFragment;
import com.yssj.ui.pager.BuyedGoodsListPage;
import com.yssj.ui.pager.SelledGoodsListPage;
import com.yssj.utils.WXminiAppUtil;

@SuppressLint("NewApi") public class PayBackListFragment extends BackHandledFragment implements OnClickListener {
	
	private boolean hadIntercept;
	private HashMap<String, Object> map;
	private ViewPager content_pager;
	private LinearLayout ll;  
	private List<BasePager> pageLists;
	private TextView textView1,textView2;
	private int currIndex = 0;// 当前页卡编号
	
	private LinearLayout ll_head;
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private ImageView img_right_icon;
	
	private Button btn_i_want_apply,btn_sale_help;
	
	public PayBackListFragment() {
	}
	
	
	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_payback_list, null);
		view.setBackgroundColor(Color.WHITE);
		ll_head = (LinearLayout) view.findViewById(R.id.ll_head);
		ll_head.setBackgroundColor(Color.TRANSPARENT);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("退款/售后");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		btn_i_want_apply = (Button) view.findViewById(R.id.btn_i_want_apply);	// 我要申请
		btn_i_want_apply.setOnClickListener(this);
		
		btn_sale_help = (Button) view.findViewById(R.id.btn_sale_help);	// 售后帮助
		btn_sale_help.setOnClickListener(this);
		
		
		ll = (LinearLayout) view.findViewById(R.id.ll);
		content_pager = (ViewPager) view.findViewById(R.id.content_pager);
		
		textView1 = (TextView) view.findViewById(R.id.textView1);
		textView2 = (TextView) view.findViewById(R.id.textView2);
		
		
		/*
		 * 右上角点点点
		 */
		img_right_icon = (ImageView) view.findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.GONE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		img_right_icon.setVisibility(View.GONE);
		
		return view;
	}

	
	
	/**初始化ViewPager*/
	private void initViewPager() {
		pageLists = new ArrayList<BasePager>();
		pageLists.add(new BuyedGoodsListPage(getActivity()));//已买
//		pageLists.add(new SelledGoodsListPage(getActivity()));//已卖
		
		pageLists.get(0).initData();   // 第一次进来时加载数据
		
		content_pager.setOffscreenPageLimit(1);   // 设置预加载页面
		content_pager.setAdapter(new MyPagerAdapter(pageLists));
		content_pager.setOnPageChangeListener(new MyOnPageChangeListener());
		content_pager.setCurrentItem(currIndex);
		setTextTitleSelectedColor(currIndex);
	}
	
	private void initTextView() {
		
//		textView1.setTextColor(getResources().getColor(R.color.white));
		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
	}
	
	/* 页卡切换监听 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			pageLists.get(arg0).initData();
			currIndex=arg0;
			setTextTitleSelectedColor(arg0);

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
	
	/** 设置标题文本的颜色 **/
	private void setTextTitleSelectedColor(int arg0) {
			if (arg0 == 0) {
				textView1.setTextColor(getResources().getColor(R.color.white));
				textView1.setBackground(getResources().getDrawable(R.drawable.payback_buyed_goods_bg_selected));
				textView2.setTextColor(getResources().getColor(R.color.title_color));
				textView2.setBackground(getResources().getDrawable(R.drawable.payback_saled_goods_bg_normal));
				
				// 设置我要申请按钮为黑色背景，能点击
				btn_i_want_apply.setBackground(getResources().getDrawable(R.drawable.payback_i_want_apply));
				btn_i_want_apply.setEnabled(true);
				btn_i_want_apply.setTextColor(getResources().getColor(R.color.white));
			} else{
				textView2.setTextColor(getResources().getColor(R.color.white));
				textView2.setBackground(getResources().getDrawable(R.drawable.payback_saled_goods_bg_selected));
				textView1.setTextColor(getResources().getColor(R.color.title_color));
				textView1.setBackground(getResources().getDrawable(R.drawable.payback_buyed_goods_bg_normal));
				
				// 设置我要申请按钮为白色背景，且不能点击
				btn_i_want_apply.setBackground(getResources().getDrawable(R.drawable.payback_i_want_apply_white_bg));
				btn_i_want_apply.setEnabled(false);
				btn_i_want_apply.setTextColor(getResources().getColor(R.color.text1_color));
		}
	}
	
	/* 标题点击监听 */
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			content_pager.setCurrentItem(index);
		}
	}
	

	@Override
	public void initData() {
		
	}

	@Override
	public void onResume() {
		super.onResume();
		currIndex=0;
		initViewPager();
        initTextView();
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
		Intent intent;
		Fragment mFragment ;
		switch (v.getId()) {
		case R.id.img_back:   // 返回
			getActivity().finish();
			break;
		case R.id.btn_i_want_apply:   // 我要申请
			
			mFragment = new IWantApplyFragment();
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).addToBackStack(null).commit();
			
			break;
		case R.id.btn_sale_help:   // 售后帮助
			Intent intent2=new Intent(getActivity(),SelledHelpActivity.class);
			startActivity(intent2);
			
			break;
		
		case R.id.img_right_icon:// 消息盒子
			WXminiAppUtil.jumpToWXmini(getActivity());

			break;
			
		}
	}


	@Override
	public boolean onBackPressed() {
		if (hadIntercept) {
			return false;
		} else {
			getActivity().finish();
			hadIntercept = true;
			return true;
		}
	}


	
}
