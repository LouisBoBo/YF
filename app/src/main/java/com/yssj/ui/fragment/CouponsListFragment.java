package com.yssj.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.base.BasePager;
import com.yssj.ui.dialog.SignShuomingDialog;
import com.yssj.ui.dialog.kaquanShuomingDialog2;
import com.yssj.ui.pager.AllCouponsPage;
import com.yssj.ui.pager.UselessCouponsPage;
import com.yssj.utils.GetJinBiJinQuanUtils;


public class CouponsListFragment extends BaseFragment implements OnClickListener  {
	private ViewPager content_pager;
	private LinearLayout ll;  
	private List<BasePager> pageLists;
	private TextView textView1,textView2;
	private int currIndex = 0;// 当前页卡编号
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private Button img_right_icon;
	

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.my_card_list, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("我的卡券");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		img_right_icon = (Button) view.findViewById(R.id.img_right_icon);
		
		img_right_icon.setVisibility(View.VISIBLE);
		
		img_right_icon.setOnClickListener(this);
		
		ll = (LinearLayout) view.findViewById(R.id.ll);
		content_pager = (ViewPager) view.findViewById(R.id.content_pager);
		
		textView1 = (TextView) view.findViewById(R.id.textView1);
		textView2 = (TextView) view.findViewById(R.id.textView2);
		
		return view;
	}

	@Override
	public void initData() {
		GetJinBiJinQuanUtils.getJinQuan(context);
		initViewPager();
        initTextView();
        
      
	}
	
	
	/**初始化ViewPager*/
	private void initViewPager() {
		pageLists = new ArrayList<BasePager>();

		pageLists.add(new AllCouponsPage(getActivity()));//未使用
		pageLists.add(new UselessCouponsPage(getActivity()));//已失效
		
		pageLists.get(0).initData();   // 第一次进来时加载数据
		
		content_pager.setOffscreenPageLimit(1);   // 设置预加载页面
		content_pager.setAdapter(new MyPagerAdapter(pageLists));
		
		content_pager.setCurrentItem(currIndex);
		content_pager.setOnPageChangeListener(new MyOnPageChangeListener());
		
	}
	
	private void initTextView() {
		
		textView1.setTextColor(getResources().getColor(R.color.pink_color));
		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
	}
	
	/* 页卡切换监听 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			pageLists.get(arg0).initData();
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
		for (int i = 0; i < 3; i++) {
			TextView tv = (TextView) ll.getChildAt(i);
			if (((arg0 == 0) && (i == 0)) || ((arg0 == 1) && (i == 2))) {
				tv.setTextColor(getResources().getColor(R.color.pink_color));
			} else {
				tv.setTextColor(getResources().getColor(R.color.title_color));
			}
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
		case R.id.img_right_icon:// 卡券说明
//			intent = new Intent(getActivity(), ChatAllHistoryActivity.class);
//			startActivity(intent);
//			new kaquanShuomingDialog2(getActivity(), R.style.DialogStyle1).show();

			kaquanShuomingDialog2 dialog = new kaquanShuomingDialog2(getActivity(), R.style.DialogStyle1);
			dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
			dialog.show();

			break;

		}
	}


}
