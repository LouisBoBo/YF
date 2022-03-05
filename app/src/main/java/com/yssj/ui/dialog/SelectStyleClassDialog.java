package com.yssj.ui.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.custom.view.NoScrollViewPager;
import com.yssj.data.YDBHelper;
import com.yssj.ui.base.BasePager;
import com.yssj.ui.dialog.SignShareShopDialog.MyPagerAdapter;
import com.yssj.ui.fragment.ClothesBeanDetailListFragment.MyOnPageChangeListener;
import com.yssj.ui.fragment.circles.SignPagerFragment02;
import com.yssj.ui.pager.BeanAddPage;
import com.yssj.ui.pager.BeanConsumePage;
import com.yssj.ui.pager.BeanFreezePage;
import com.yssj.ui.pager.SelectClassPager;
import com.yssj.ui.pager.SelectClassPager.ClassListener;
import com.yssj.ui.pager.SelectStylePager;
import com.yssj.ui.pager.SelectStylePager.StyleListener;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class SelectStyleClassDialog extends Dialog implements android.view.View.OnClickListener,StyleListener,ClassListener{
	
	private Context mContext;
	private YDBHelper dbHelp;
	private LayoutInflater mInflater;
	private NoScrollViewPager mViewPager;
	private TextView leftTitleTv,rightTitleTv;
	private View leftLineView,rightLineView;
	private List<BasePager> mListPager;
	private PagerAdapter mAdapter;
	private int currentIndext;
	public SelectStyleClassDialog(Context context) {
		super(context);
		setCanceledOnTouchOutside(false);
		this.mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.select_style_class_dialog);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		initView();
	}

	private void initView() {
		dbHelp = new YDBHelper(mContext);
		mInflater = LayoutInflater.from(mContext);
		mViewPager= (NoScrollViewPager) findViewById(R.id.select_content_viewpager);
		leftTitleTv = (TextView) findViewById(R.id.left_title_tv);
		rightTitleTv = (TextView) findViewById(R.id.right_title_tv);
		leftLineView = findViewById(R.id.left_line_view);
		rightLineView = findViewById(R.id.right_line_view);
		leftTitleTv.setOnClickListener(this);
		rightTitleTv.setOnClickListener(this);
		rightTitleTv.setClickable(false);
		mViewPager.setNoScroll(true);
		initViewPager(currentIndext);
	}
	/** 初始化ViewPager */
	private void initViewPager(int index) {
		mListPager = new ArrayList<BasePager>();
		SelectStylePager ssP = new SelectStylePager(mContext,mViewPager,rightTitleTv);
		SelectClassPager scP = new SelectClassPager(mContext);
		ssP.setStyleListener(this);
		scP.setClassListener(this);
		mListPager.add(ssP);
		mListPager.add(scP);
		mAdapter = new MyPagerAdapter(mListPager);
		mViewPager.setAdapter(mAdapter);
		mListPager.get(index).initData(); // 第一次进来时加载数据

		mViewPager.setOffscreenPageLimit(1); // 设置预加载页面
		mListPager.get(1).initData();

		mViewPager.setCurrentItem(index);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				if (position == 0) {
					leftTitleTv.setTextColor(Color.parseColor("#FF3F8B"));
					rightTitleTv.setTextColor(Color.parseColor("#3E3E3E"));
					leftLineView.setVisibility(View.VISIBLE);
					rightLineView.setVisibility(View.GONE);
				} else if (position == 1) {
					leftTitleTv.setTextColor(Color.parseColor("#3E3E3E"));
					rightTitleTv.setTextColor(Color.parseColor("#FF3F8B"));
					leftLineView.setVisibility(View.GONE);
					rightLineView.setVisibility(View.VISIBLE);
				} 
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_title_tv:
			leftTitleTv.setTextColor(Color.parseColor("#FF3F8B"));
			rightTitleTv.setTextColor(Color.parseColor("#3E3E3E"));
			leftLineView.setVisibility(View.VISIBLE);
			rightLineView.setVisibility(View.GONE);
			mViewPager.setCurrentItem(0, true);
			break;
		case R.id.right_title_tv:
			leftTitleTv.setTextColor(Color.parseColor("#3E3E3E"));
			rightTitleTv.setTextColor(Color.parseColor("#FF3F8B"));
			leftLineView.setVisibility(View.GONE);
			rightLineView.setVisibility(View.VISIBLE);
			mViewPager.setCurrentItem(1, true);
			break;

		default:
			break;
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
			View view = ((BasePager) pageLists.get(position)).getRootView();
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}
	
	public interface  StyleClassListener{
		void setStyleClassListener(HashMap<String,String> hashMapStyle,HashMap<String, String> hashMapClass1,HashMap<String, String> hashMapClass2);
	}
	private StyleClassListener listener;
	public void setStyleClassListener(StyleClassListener listener){
		this.listener = listener;
	}
	
	
	private HashMap<String,String> hashMapStyle;

	/**
	 * 选中风格
	 */
	@Override
	public void setStyleListener(HashMap<String,String> hashMap) {
		hashMapStyle = hashMap;
	}


	/**
	 * 选中类目
	 */
	@Override
	public void setClassListener(HashMap<String, String> hashMapClass1,HashMap<String, String> hashMapClass2) {
		if(listener!=null){
			listener.setStyleClassListener(hashMapStyle,hashMapClass1,hashMapClass2);
		}
		this.dismiss();
	};	
	
}
