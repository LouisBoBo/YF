package com.yssj.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.YJApplication;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ShopCart;
import com.yssj.huanxin.PublicUtil;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.model.ComModel2;
import com.yssj.ui.HomeWatcherReceiver;
import com.yssj.ui.activity.ShopCartCommonFragment.ShopCartCommonInterface;
//import com.yssj.ui.activity.ShopCartSpecialFragment.ShopCartSpecialInterface;
import com.yssj.ui.base.BasePager;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.WXminiAppUtil;

public class ShopCartNewNewActivity extends BasicActivity implements OnClickListener, ShopCartCommonInterface
// , ShopCartSpecialInterface
{
	private List<Fragment> pageLists;
	private TextView mShopCartCommon, mShopCartSpecial;
	private int mCurrIndex = 0;// 当前页卡编号
	private FragmentTransaction ft;
	private LinearLayout mLineHead;
	private TextView mTitleBase;
	private LinearLayout img_back;
	private ImageView img_right_icon;
	private LinearLayout mBack;
	private List<ShopCart> mListCommon;
	private List<ShopCart> mListSpecial;
	private FragmentManager fm;
	private LinearLayout newnew;
	private android.support.v4.app.Fragment commonFragment;
	private FrameLayout mFLayout;
	private android.support.v4.app.Fragment specialFragment;
	private boolean flag = true;
	private String specialFlag;
	private String str;
	private boolean shopFlag = false;// 标记第一次进去的是那个购物车
	public static ShopCartNewNewActivity instance;
	private TextView mEditButton;
	private ImageView ivBalanceLottory;//悬浮红包

	// private List<ShopCart>
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shop_cart_new);
		instance = this;
		initView();
		initFragment();
		PublicUtil.getBalanceNum(this,ivBalanceLottory,false);
	}

	public void initView() {
		fm = getSupportFragmentManager();
		ft = fm.beginTransaction();
		mLineHead = (LinearLayout) findViewById(R.id.shopcart_ll);
		mLineHead.setBackgroundColor(Color.TRANSPARENT);
		mTitleBase = (TextView) findViewById(R.id.tvTitle_base);
		mTitleBase.setText("购物车");
		img_back = (LinearLayout) findViewById(R.id.img_back);
		mFLayout = (FrameLayout) findViewById(R.id.shopcart_content_fragment);
		img_back.setOnClickListener(this);
		mBack = (LinearLayout) findViewById(R.id.shopcart_img_back);
		mBack.setOnClickListener(this);
		// ll = (LinearLayout) view.findViewById(R.id.ll);
		mEditButton = (TextView) findViewById(R.id.shopcart_btn_edit);
		ivBalanceLottory= (ImageView) findViewById(R.id.img_balance_lottery);
		mShopCartCommon = (TextView) findViewById(R.id.shopcart_tv_common);
		mShopCartSpecial = (TextView) findViewById(R.id.shopcart_tv_specal);
		newnew = (LinearLayout) findViewById(R.id.newnew);
		newnew.setBackgroundColor(Color.WHITE);
		commonFragment = new ShopCartCommonFragment(this, mEditButton);
		// specialFragment = new ShopCartSpecialFragment(this);
		/*
		 * 右上角点点点
		 */
		img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		// 返回进来特卖购物无车
		str = getIntent().getStringExtra("submit");
		// 从特卖进来进入特卖购物车
		String zeroMeal = getIntent().getStringExtra("where");
		// System.out.println("*************************pppp" + zeroMeal);
		if (str != null && str.equals("special") || (zeroMeal != null && zeroMeal.equals("1"))) {
			getSupportFragmentManager().beginTransaction().add(R.id.shopcart_content_fragment, specialFragment)
					.commit();
			shopFlag = true;
		} else {
			// 一进来加载
			getSupportFragmentManager().beginTransaction().add(R.id.shopcart_content_fragment, commonFragment).commit();
			shopFlag = false;
		}

		if ((getIntent().getStringExtra("submit") != null && getIntent().getStringExtra("submit").equals("special")
				&& flag) || ("1".equals(getIntent().getStringExtra("where")) && flag)) {
			flag = false;
			mCurrIndex = 1;
			shopFlag = true;
		} else {
			mCurrIndex = 0;
			shopFlag = false;
		}
		
		img_right_icon.setVisibility(View.GONE);
		initFragment();
		initTextView();
	}

	/** 初始化Fragment */
	private void initFragment() {
		pageLists = new ArrayList<Fragment>();
		pageLists.add(commonFragment);// 普通商品
		pageLists.add(specialFragment);// 特卖商品
		// setTextTitleSelectedColor(mCurrIndex);
	}

	private void initTextView() {

		// textView1.setTextColor(getResources().getColor(R.color.white));
		// mShopCartCommon.setOnClickListener(new MyOnClickListener(0));
		mShopCartSpecial.setOnClickListener(new MyOnClickListener(1));
	}

	/** 设置标题文本的颜色 **/
	private void setTextTitleSelectedColor(int arg0) {
		if (arg0 == 0) {
			mShopCartCommon.setTextColor(getResources().getColor(R.color.white));
			mShopCartCommon.setBackgroundResource(R.drawable.title_red_left);
			mShopCartSpecial.setTextColor(getResources().getColor(R.color.shopcart_title));
			mShopCartSpecial.setBackgroundResource(R.drawable.title_white_right);
		} else {
			mShopCartSpecial.setTextColor(getResources().getColor(R.color.white));
			mShopCartSpecial.setBackgroundResource(R.drawable.title_red_right);
			mShopCartCommon.setTextColor(getResources().getColor(R.color.shopcart_title));
			mShopCartCommon.setBackgroundResource(R.drawable.title_white_left);
		}
	}

	/* 标题点击监听 */
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			ft = fm.beginTransaction();
			// pageLists.get(index).initData();
			ft.replace(R.id.shopcart_content_fragment, pageLists.get(index));
			ft.commitAllowingStateLoss();
			// setTextTitleSelectedColor(index);
			mCurrIndex = index;
			if (mCurrIndex == 0) {
				SharedPreferencesUtil.saveStringData(ShopCartNewNewActivity.this, "TONGJI_SHOPCART", "1");
				SharedPreferencesUtil.saveStringData(ShopCartNewNewActivity.this, "TONGJI_SHOPCART_PAGE", "1");
			} else {
				SharedPreferencesUtil.saveStringData(ShopCartNewNewActivity.this, "TONGJI_SHOPCART_PAGE", "0");
				SharedPreferencesUtil.saveStringData(ShopCartNewNewActivity.this, "TONGJI_SHOPCART", "0");
			}
		}
	}

	// @Override
	// public void onResume() {
	// super.onResume();
	// if (getIntent().getStringExtra("submit") != null
	// && getIntent().getStringExtra("submit").equals("special")
	// && flag) {
	// flag = false;
	// mCurrIndex = 1;
	// } else {
	// mCurrIndex = 0;
	// }
	// initFragment();
	// initTextView();
	// }
	@Override
	protected void onResume() {
		super.onResume();
		// MobclickAgent.onPageStart("PaymentActivity");
		// MobclickAgent.onResume(this);
		HomeWatcherReceiver.registerHomeKeyReceiver(this);
		if (mCurrIndex == 0) {
			SharedPreferencesUtil.saveStringData(this, "TONGJI_SHOPCART", "1");
			SharedPreferencesUtil.saveStringData(this, "TONGJI_SHOPCART_PAGE", "1");
		} else {
			SharedPreferencesUtil.saveStringData(this, "TONGJI_SHOPCART_PAGE", "0");
			SharedPreferencesUtil.saveStringData(this, "TONGJI_SHOPCART", "0");
		}
		SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1052");
		
		TongJiUtils.TongJi(this, 10+"");
		LogYiFu.e("TongJiNew", 10+"");
	}

	@Override
	protected void onPause() {
		super.onPause();
		// MobclickAgent.onPageEnd("PaymentActivity");
		// MobclickAgent.onPause(this);
		// SharedPreferencesUtil.saveStringData(this, "TONGJI_SHOPCART_PAGE",
		// "0");
		SharedPreferencesUtil.saveStringData(this, "TONGJI_SHOPCART", "0");
		HomeWatcherReceiver.unregisterHomeKeyReceiver(this);
		TongJiUtils.TongJi(this, 110+"");
		LogYiFu.e("TongJiNew", 110+"");

	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.shopcart_img_back: // 返回
			onBackPressed();
			break;

		case R.id.img_right_icon:// 消息盒子
			WXminiAppUtil.jumpToWXmini(this);

			break;

		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
		setResult(RESULT_OK);
		finish();

	}

	@Override
	public void getShopCommonCount(int count) {
		// TODO Auto-generated method stub
		if (count > 0) {
			if (count > 99) {
				mShopCartCommon.setText("购物车" + " (" + 99 + ")");
			} else {
				mShopCartCommon.setText("购物车" + " (" + count + ")");
			}
		} else {
			mShopCartCommon.setText("购物车 (0)");
		}
		// mShopCartSpecial.setText("特卖" + " (" + (count - common) + ")");
		// if (YJApplication.instance.isLoginSucess()) {
		// queryCartCount(count);
		// }
	}

	// @Override
	// public void getShopSpecialCount(int count, int special) {
	// // TODO Auto-generated method stub
	// if (special > 0) {
	// mShopCartSpecial.setText("特卖" + " (" + special + ")");
	// } else {
	// mShopCartSpecial.setText("特卖 (0)");
	// }
	// mShopCartCommon.setText("购物车" + " (" + (count - special) + ")");
	// // if (shopFlag) {
	// // queryCartCount(count);
	// // }
	// }

	// 查询购物车数量
	private void queryCartCount(final int num) {

		new SAsyncTask<Void, Void, String>(ShopCartNewNewActivity.this, R.string.wait) {

			@Override
			protected String doInBackground(FragmentActivity context, Void... params) throws Exception {
				return ComModel2.getShopCartCount(context);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, String count, Exception e) {
				super.onPostExecute(context, count, e);
				if (e != null || count == null) {
					return;
				}
				if (shopFlag) {
					if (Integer.parseInt(count) > 0) {
						if (Integer.parseInt(count) - num > 0) {
							mShopCartCommon.setText("购物车" + " (" + (Integer.parseInt(count) - num) + ")");
						} else {
							mShopCartCommon.setText("购物车 (0)");
						}
					} else {
						mShopCartCommon.setText("购物车 (0)");
					}
				} else {
					if (Integer.parseInt(count) > 0) {
						if (Integer.parseInt(count) - num > 0) {
							mShopCartSpecial.setText("特卖" + " (" + (Integer.parseInt(count) - num) + ")");
						} else {
							mShopCartSpecial.setText("特卖 (0)");
						}
					} else {
						mShopCartSpecial.setText("特卖 (0)");
					}
				}
				shopFlag = false;
			}

		}.execute();

	}
}
