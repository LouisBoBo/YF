package com.yssj.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.WithdrawalLimitActivity;
import com.yssj.ui.activity.main.HotSaleActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.base.BasePager;
import com.yssj.ui.pager.BeanAddPage;
import com.yssj.ui.pager.BeanConsumePage;
import com.yssj.ui.pager.BeanFreezePage;
import com.yssj.ui.pager.IntergralIncomePage;
import com.yssj.ui.pager.IntergralOutputPage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
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

@SuppressLint("ValidFragment")
public class ClothesBeanDetailListFragment extends BaseFragment implements OnClickListener {
	private ViewPager content_pager;
	private LinearLayout ll;
	private List<BasePager> pageLists;
	private TextView textView1, textView2, textView3, intergralTv, integral_count_freeze;
	private int currIndex;// 当前页卡编号
	private View mView1, mView2, mView3;
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private Button img_right_icon;
	private TextView mGetBeans;
	private String pearsCount = "0";// 可用衣豆数量
	private String freezeCount = "0";// 冻结衣豆数量

	@SuppressLint("ValidFragment")
	public ClothesBeanDetailListFragment(int currIndex, String pearsCount, String freezeCount) {
		this.currIndex = currIndex;
		this.pearsCount = pearsCount;
		this.freezeCount = freezeCount;
	}

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.fragment_clothes_bean_list, null);
		view.setBackgroundColor(Color.WHITE);
		mView1 = view.findViewById(R.id.bean_ll_line1);
		mView2 = view.findViewById(R.id.bean_ll_line2);
		mView3 = view.findViewById(R.id.bean_ll_line3);
		mGetBeans = (TextView) view.findViewById(R.id.bean_tv_get);
		mGetBeans.setOnClickListener(this);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("衣豆明细");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);

		img_right_icon = (Button) view.findViewById(R.id.img_right_icon);
		// img_right_icon.setVisibility(View.GONE);
		// img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);

		ll = (LinearLayout) view.findViewById(R.id.ll);
		content_pager = (ViewPager) view.findViewById(R.id.content_pager);

		textView1 = (TextView) view.findViewById(R.id.textView1);
		textView1.setText("消耗");
		textView2 = (TextView) view.findViewById(R.id.textView2);
		textView2.setText("增加");
		textView3 = (TextView) view.findViewById(R.id.textView3);
		textView3.setText("冻结");
		intergralTv = (TextView) view.findViewById(R.id.integral_count_tv);
		intergralTv.setText("" + pearsCount);
		integral_count_freeze = (TextView) view.findViewById(R.id.integral_count_freeze);
		integral_count_freeze.setText("(冻结衣豆:" + freezeCount + ")");
		return view;
	}

	@Override
	public void initData() {
		initViewPager(currIndex);
		initTextView();

	}

	/** 初始化ViewPager */
	private void initViewPager(int index) {
		pageLists = new ArrayList<BasePager>();
		pageLists.add(new BeanConsumePage(getActivity()));
		pageLists.add(new BeanAddPage(getActivity()));
		pageLists.add(new BeanFreezePage(getActivity()));
		pageLists.get(index).initData(); // 第一次进来时加载数据

		content_pager.setOffscreenPageLimit(1); // 设置预加载页面
		content_pager.setAdapter(new MyPagerAdapter(pageLists));

		content_pager.setCurrentItem(index);

		content_pager.setOnPageChangeListener(new MyOnPageChangeListener());

	}

	private void initTextView() {
		if (currIndex == 0) {
			textView1.setTextColor(getResources().getColor(R.color.pink_color));
			mView1.setVisibility(View.VISIBLE);

		} else if (currIndex == 1) {
			textView2.setTextColor(getResources().getColor(R.color.pink_color));
			mView2.setVisibility(View.VISIBLE);

		} else if (currIndex == 2) {
			textView3.setTextColor(getResources().getColor(R.color.pink_color));
			mView3.setVisibility(View.VISIBLE);
		}
		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
		textView3.setOnClickListener(new MyOnClickListener(3));
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
		// for (int i = 0; i < 3; i++) {
		// TextView tv = (TextView) ll.getChildAt(i);
		// if (((arg0 == 0) && (i == 0)) || ((arg0 == 1) && (i == 2))) {
		// tv.setTextColor(getResources().getColor(R.color.pink_color));
		// } else {
		// tv.setTextColor(getResources().getColor(R.color.black));
		// }
		// }
		if (arg0 == 0) {
			textView1.setTextColor(getResources().getColor(R.color.pink_color));
			textView2.setTextColor(getResources().getColor(R.color.black));
			textView3.setTextColor(getResources().getColor(R.color.black));
			mView1.setVisibility(View.VISIBLE);
			mView2.setVisibility(View.GONE);
			mView3.setVisibility(View.GONE);
		} else if (arg0 == 1) {
			textView1.setTextColor(getResources().getColor(R.color.black));
			textView2.setTextColor(getResources().getColor(R.color.pink_color));
			textView3.setTextColor(getResources().getColor(R.color.black));
			mView1.setVisibility(View.GONE);
			mView2.setVisibility(View.VISIBLE);
			mView3.setVisibility(View.GONE);
		} else {
			textView1.setTextColor(getResources().getColor(R.color.black));
			textView2.setTextColor(getResources().getColor(R.color.black));
			textView3.setTextColor(getResources().getColor(R.color.pink_color));
			mView1.setVisibility(View.GONE);
			mView2.setVisibility(View.GONE);
			mView3.setVisibility(View.VISIBLE);
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
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}

	@Override
	public void onClick(View v) {
		// Intent intent = null;
		switch (v.getId()) {
		case R.id.img_back:
			getActivity().finish();
			break;
		case R.id.bean_tv_get:
			Intent intent = new Intent(context, HotSaleActivity.class);
			intent.putExtra("id", "6");
			intent.putExtra("title", "热卖");
			context.startActivity(intent);
			((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

			break;
		case R.id.img_right_icon:// 衣豆说明
			getBeanExplain();
			break;

		}
	}

	public void getBeanExplain() {
		final Dialog dialog = new Dialog(context, R.style.invate_dialog_style);
		View view = View.inflate(context, R.layout.dialog_bean_explain, null);
		TextView mKnow = (TextView) view.findViewById(R.id.tv_go_on);// 关闭
		TextView mGoOn = (TextView) view.findViewById(R.id.start_balance);// 跳到抽奖
		TextView btn_close = (TextView) view.findViewById(R.id.dialog_tv_dialog_colse);
		btn_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 把这个对话框取消掉
				dialog.dismiss();
			}
		});
		mKnow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 把这个对话框取消掉
				dialog.dismiss();
			}
		});
		mGoOn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				Intent intent = new Intent(context, WithdrawalLimitActivity.class);
				startActivity(intent);
			}
		});
		dialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		dialog.show();
		dialog.setCancelable(false);
	}
}
