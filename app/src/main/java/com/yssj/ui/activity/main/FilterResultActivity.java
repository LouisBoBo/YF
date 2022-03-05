package com.yssj.ui.activity.main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.CustImageGallery;
import com.yssj.data.YDBHelper;
import com.yssj.entity.Order;
import com.yssj.entity.VipDikouData;
import com.yssj.model.ComModel2;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.HomeWatcherReceiver;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.shopdetails.MealPaymentActivity;
import com.yssj.ui.adpter.StaggeredAdapter;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.LeaveDialog;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;

public class FilterResultActivity extends BasicActivity {

	private PullToRefreshListView r_list_view;

	private StaggeredAdapter mAdapter;

	private int index = 1;

	private int mType = 1;// 1：初始化数据；2：加载更多数据

	private HashMap<String, Object> map;
	private String id, title;

	private LinearLayout llContaint, lv_kaiqifanbei;

	private HorizontalScrollView hsv_containt;

	private boolean isSign;

	// private String checkId;

	private int pageSize = 10;
	private RelativeLayout rl_yuefanbei;

	private boolean isComplete = false;// 当数据少时，下拉刷新 既调用刷新 又调用 加载跟多，

	private LinearLayout llNodata;
	// 所以当每次返回的数据<pageSize的时候
	private boolean isTuijian = false;
	private boolean isSignQiangzhiliulan = false; // 夺宝强制浏览5分钟跳过来的

	private ImageButton img_btn_filter, shop_cart, imgbtn_left_icon_sign, imgbtn_left_icon;

	private String is_new = null, order_by_price = null;

	private TextView tv_title, liulanTime;

	private boolean notType = false;
	private String shop_name = "";
	private TextView create_is_new, create_price_desc, create_price_asc;
	private VipDikouData vipDikouData;
	private Context context;


	// isComplete为true
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		context = this;
//		aBar.hide();
		map = (HashMap<String, Object>) getIntent().getSerializableExtra("condition");

		isFilterConditionActivity = getIntent().getBooleanExtra("isFilterConditionActivity", false);
		isWhere = getIntent().getStringExtra("isWhere");

		id = getIntent().getStringExtra("id");
		shop_name = getIntent().getStringExtra("shop_name");
		title = getIntent().getStringExtra("title");
		isTuijian = getIntent().getBooleanExtra("isTuijian", false);
		isSign = getIntent().getBooleanExtra("isSign", false);

		isSignQiangzhiliulan = getIntent().getBooleanExtra("qiangZhiLiuLan", false);

		// checkId = getIntent().getStringExtra("checkId");
		setContentView(R.layout.list_result_filter);
		// YDBHelper helper = new YDBHelper(this);
		// id = getIntent().getStringExtra("id");
		// String sort_name = helper.querySort_name(id);
		if (null != shop_name && !("".equals(shop_name))) {
			TextView mTitle = (TextView) findViewById(R.id.new_title);
			mTitle.setText("" + shop_name);
		}
		tv_title = (TextView) findViewById(R.id.tv_title);
		liulanTime = (TextView) findViewById(R.id.liulanTime);
		imgbtn_left_icon_sign = (ImageButton) findViewById(R.id.imgbtn_left_icon_sign);
		imgbtn_left_icon = (ImageButton) findViewById(R.id.imgbtn_left_icon);
		lv_kaiqifanbei = (LinearLayout) findViewById(R.id.lv_kaiqifanbei);

		shop_cart = (ImageButton) findViewById(R.id.shop_cart);

		findViewById(R.id.img_back).setOnClickListener(this);
		img_btn_filter = (ImageButton) findViewById(R.id.img_btn_filter);
		img_btn_filter.setOnClickListener(this);
		// 添加搜索条件
		// llContaint = (LinearLayout) findViewById(R.id.ll_containt);
		// addView(llContaint, map);

		rl_yuefanbei = (RelativeLayout) findViewById(R.id.rl_yuefanbei);

		r_list_view = (PullToRefreshListView) findViewById(R.id.r_list_view);
		llNodata = (LinearLayout) findViewById(R.id.ll_nodata);
		findViewById(R.id.btn_view_allcircle).setVisibility(View.GONE);
		((TextView) findViewById(R.id.tv_no_join)).setText("暂无数据");
		r_list_view.setMode(Mode.BOTH);
		mAdapter = new StaggeredAdapter(this, id, isWhere);

		r_list_view.setAdapter(mAdapter);

		create_is_new = (TextView) findViewById(R.id.create_is_new);
		create_price_desc = (TextView) findViewById(R.id.create_price_desc);
		create_price_asc = (TextView) findViewById(R.id.create_price_asc);

		create_is_new.setOnClickListener(this);
		create_price_desc.setOnClickListener(this);
		create_price_asc.setOnClickListener(this);

		// 如果是签到跳过来的要修改布局

		if (isSignQiangzhiliulan) {
			tv_title.setText("签到领现金");
			imgbtn_left_icon_sign.setVisibility(View.VISIBLE);
			shop_cart.setVisibility(View.VISIBLE);
			rl_yuefanbei.setVisibility(View.VISIBLE);

			imgbtn_left_icon.setVisibility(View.GONE);
			img_btn_filter.setVisibility(View.GONE);

		}




		r_list_view.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				mType = 1;
				index = 1;
				if (isTuijian) {
					initData(index + "", null, is_new, order_by_price);
					return;
				}
				initData(index + "", map, is_new, order_by_price);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				if (!isComplete) {// 当数据没加载完成 上拉可以加载更多
					index++;
					mType = 2;
					if (isTuijian) {
						initData(index + "", null, is_new, order_by_price);
					} else {
						initData(index + "", map, is_new, order_by_price);
					}
				}
			}
		});


		//查询会员情况
		if (YJApplication.instance.isLoginSucess()) {
			//查询抵扣
			HashMap<String, String> pairsMap = new HashMap<>();
			YConn.httpPost(this, YUrl.GETALLDIKOU, pairsMap, new HttpListener<VipDikouData>() {
				@Override
				public void onSuccess(VipDikouData result) {
					vipDikouData = result;
					if (isTuijian) {
						initData(index + "", null, is_new, order_by_price);
					} else {
						initData(index + "", map, is_new, order_by_price);
					}

				}

				@Override
				public void onError() {
					if (isTuijian) {
						initData(index + "", null, is_new, order_by_price);
					} else {
						initData(index + "", map, is_new, order_by_price);
					}

				}
			});

		}else{
			if (isTuijian) {
				initData(index + "", null, is_new, order_by_price);
			} else {
				initData(index + "", map, is_new, order_by_price);
			}
		}




	}

	private void initData(String index, final HashMap<String, Object> map, final String is_new,
			final String order_by_price) {
		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(this, 0) {

			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				if (e == null) {
					if (mType == 1) {
						if (result == null || result.size() == 0) {
							llNodata.setVisibility(View.VISIBLE);
							r_list_view.setVisibility(View.GONE);
						} else {
							llNodata.setVisibility(View.GONE);
							r_list_view.setVisibility(View.VISIBLE);
						}
						mAdapter.setData(result,vipDikouData);

					} else if (mType == 2) {
//						mAdapter.addItemLast(result);
						if (result == null || result.size() == 0) {
							ToastUtil.showShortText(context, "已没有更多商品了哦~");
						}else{
							mAdapter.addItemLast(result);
							r_list_view.getRefreshableView().smoothScrollBy(100, 20);
						}
					}

				}
				r_list_view.onRefreshComplete();
			}

			@Override
			protected boolean isHandleException() {

				return true;
			}

			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
					throws Exception {
				// TODO Auto-generated method stub
				// if(YJApplication.instance.isLoginSucess()){
				// return ComModel2.getFilterProductList(id,context, params[0],
				// map, pageSize);
				// }else{
				if (isTuijian) {
					return ComModel2.getTuiJianFilterProductList2(context, params[0], pageSize, title, is_new,
							order_by_price);
				} else {
					return ComModel2.getFilterProductList2(title, id, context, params[0], map, true + "", pageSize,
							is_new, order_by_price,false);
				}
				// }

			}

		}.execute(index);
	}

	// @Override
	// public void onLoadMore() {
	// // TODO Auto-generated method stub
	// if (!isComplete) {// 当数据没加载完成 上拉可以加载更多
	// Toast.makeText(this, "加载更多", Toast.LENGTH_LONG).show();
	// index++;
	// mType = 2;
	// initData(index + "", map);
	// }
	// }

	// @Override
	// public void onRefresh() {
	// // TODO Auto-generated method stub
	// mType = 1;
	// index = 1;
	// initData(index + "", map);
	// }

	@Override
	public void onBackPressed() {
		if (isSign&&!SignListAdapter.isForceLookTimeOut&&!SignListAdapter.isSignComplete) {//赚钱任务浏览分钟数 并且时间没有到 并且是没有完成的任务
			LeaveDialog leaveDialog = new LeaveDialog(this);
			leaveDialog.show();
			leaveDialog.setContentText("你正在进行浏览商品任务，浏览时长还未完成，你可以选择去浏览其它商品，浏览时长达到任务要求即可完成任务喔~");
			leaveDialog.setButtonText("不了谢谢", "其他商品");
			View btn_left = leaveDialog.findViewById(R.id.btn_left);
			View btn_right = leaveDialog.findViewById(R.id.btn_right);
			btn_right.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					CommonUtils.finishActivity(MainMenuActivity.instances);

					Intent intent = new Intent((Activity) context, MainMenuActivity.class);
					intent.putExtra("toYf", "toYf");
					context.startActivity(intent);
				}
			});
			
			btn_left.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
					overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
				}
			});
			

		} else {
			finish();
			overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
		}
	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.img_back: // 返回

			onBackPressed();

			break;
		// case R.id.img_btn_filter: // 筛选
		// startActivity(new Intent(this, FilterConditionActivity.class));
		// finish();
		// break;
		case R.id.img_btn_filter:
			OrderByPopupWindow popupWindow = new OrderByPopupWindow(this);
			popupWindow.showAsDropDown(img_btn_filter, 0, 10);
			break;
		case R.id.create_is_new:
			is_new = "is_new";
			order_by_price = null;
			notType = true;
			mType = 1;
			index = 1;
			// initData(index + "", SearchResultActivity.this.id,
			// level,is_new,order_by_price, notType);
			initData(index + "", map, is_new, order_by_price);
			break;
		case R.id.create_price_desc:
			is_new = null;
			order_by_price = "desc";
			notType = true;
			mType = 1;
			index = 1;
			// initData(index + "", SearchResultActivity.this.id,
			// level,is_new,order_by_price, notType);
			initData(index + "", map, is_new, order_by_price);
			break;
		case R.id.create_price_asc:
			is_new = null;
			order_by_price = "asc";
			notType = true;
			mType = 1;
			index = 1;
			// initData(index + "", SearchResultActivity.this.id,
			// level,is_new,order_by_price, notType);
			initData(index + "", map, is_new, order_by_price);
			break;
		default:
			break;
		}
	}

	private boolean isLou = true;

	/** 只看楼主和收藏帖子的popupwindow */
	private class OrderByPopupWindow extends PopupWindow implements OnClickListener {
		private Context mContext;
		private LinearLayout create_is_new, create_price_desc, create_price_asc;
		private String[] names;

		private TextView is_lou;

		public OrderByPopupWindow(Context context) {
			super(context);
			mContext = context;
			init();
		}

		// public OrderByPopupWindow(Context context, String[] names) {
		// super(context);
		// mContext = context;
		// this.names = names;
		// init();
		// }

		private void init() {
			setWidth(LayoutParams.WRAP_CONTENT);
			setHeight(LayoutParams.WRAP_CONTENT);
			ColorDrawable dw = new ColorDrawable(0x00);
			setBackgroundDrawable(dw);
			View view = LayoutInflater.from(mContext).inflate(R.layout.popup_order, null);
			create_is_new = (LinearLayout) view.findViewById(R.id.create_is_new);
			create_price_desc = (LinearLayout) view.findViewById(R.id.create_price_desc);
			create_price_asc = (LinearLayout) view.findViewById(R.id.create_price_asc);

			create_is_new.setOnClickListener(this);
			create_price_desc.setOnClickListener(this);
			create_price_asc.setOnClickListener(this);
			setContentView(view);
			setAnimationStyle(R.style.PopupWindowAnimation);
			setOutsideTouchable(true);
			setFocusable(true);
		}

		@Override
		public void onClick(View v) {
			// Intent intent = new Intent();
			// int enterAnimID = R.anim.slide_right_in;
			// int exitAnimID = R.anim.slide_left_out;
			int id = v.getId();
			if (id == R.id.create_is_new) {
				is_new = "is_new";
				order_by_price = null;
			} else if (id == R.id.create_price_desc) {
				is_new = null;
				order_by_price = "desc";
			} else if (id == R.id.create_price_asc) {
				is_new = null;
				order_by_price = "asc";
			}
			notType = true;
			mType = 1;
			index = 1;
			// initData(index + "", SearchResultActivity.this.id,
			// level,is_new,order_by_price, notType);
			initData(index + "", map, is_new, order_by_price);
			// enterAnimID = R.anim.slide_down_in;
			// exitAnimID = R.anim.slide_top_in;
			dismiss();
		}

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 101 && arg1 == 102) {
			CommonUtils.finishActivity(MainMenuActivity.instances);

			Intent intent = new Intent(this, MainMenuActivity.class);
			intent.putExtra("index", 3);
			startActivity(intent);
			finish();
		}
	}

	private long recLen = (1 * 1000 * 60) / 2; // 30秒 定时时长 （毫秒）
	Timer timer = new Timer();

	private int Ffg = -2;

	private boolean isFilterConditionActivity;

	private String isWhere;

	@Override
	protected void onResume() {
		super.onResume();
		Ffg = CustImageGallery.fg;

		if (Ffg == 1) {
			SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1002");
			// SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_PAGE,
			// "风格1");
			HomeWatcherReceiver.registerHomeKeyReceiver(this);
		}
		if (Ffg == 2) {
			SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1003");
			// SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_PAGE,
			// "风格2");
			HomeWatcherReceiver.registerHomeKeyReceiver(this);
		}
		if (Ffg == 3) {
			SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1004");
			// SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_PAGE,
			// "风格3");
			HomeWatcherReceiver.registerHomeKeyReceiver(this);
		}
		if (Ffg == 4) {
			SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1005");
			// SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_PAGE,
			// "风格4");
			HomeWatcherReceiver.registerHomeKeyReceiver(this);
		}
		if (Ffg == 5) {
			SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1006");
			// SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_PAGE,
			// "风格5");
			HomeWatcherReceiver.registerHomeKeyReceiver(this);
		}
		if (Ffg == 6) {
			SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1007");
			// SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_PAGE,
			// "风格6");
			HomeWatcherReceiver.registerHomeKeyReceiver(this);
		}
		if (Ffg == 7) {
			SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1008");
			// SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_PAGE,
			// "风格7");
			HomeWatcherReceiver.registerHomeKeyReceiver(this);
		}
		if (Ffg == 8) {
			SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1009");
			// SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_PAGE,
			// "风格8");
			HomeWatcherReceiver.registerHomeKeyReceiver(this);
		}
		if (Ffg == 9) {
			SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1010");
			// SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_PAGE,
			// "风格9");
			HomeWatcherReceiver.registerHomeKeyReceiver(this);
		}
		if (Ffg == 10) {
			SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1011");
			// SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_PAGE,
			// "风格10");
			HomeWatcherReceiver.registerHomeKeyReceiver(this);
		}

		if (isFilterConditionActivity == true) {
			SharedPreferencesUtil.saveStringData(this, Pref.TONGJI_TYPE, "1018");
			HomeWatcherReceiver.registerHomeKeyReceiver(this);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (Ffg >= 1 && Ffg <= 10 || isFilterConditionActivity) {
			HomeWatcherReceiver.unregisterHomeKeyReceiver(this);
		}

	}

}
