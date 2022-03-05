package com.yssj.ui.activity.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.data.YDBHelper;
import com.yssj.entity.VipDikouData;
import com.yssj.model.ComModel2;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.ShopCartNewNewActivity;
import com.yssj.ui.adpter.ForceLookAdapter;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.LeaveDialog;
import com.yssj.ui.dialog.XunBaoDialog;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;
import com.yssj.utils.sqlite.ShopCartDao;

import org.apache.commons.lang.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class ForceLookActivity extends BasicActivity {
	public static int click_num; // 记录点击次数
	public static int num_tongji;// 运营统计的点击次数

	public static int now_type_id_value;
	public static int now_type_id;
	public static int next_type_id;
	public static int next_type_id_value;
	private String theme_id = "";
    private boolean isMoreShop;//密友圈帖子详情更多商品推荐

	private long recLen; // 30秒 定时时长 （毫秒）
	Timer timer = new Timer();

	private PullToRefreshListView r_list_view;

	private ForceLookAdapter mAdapter;
	public static  ForceLookActivity instance;

	private int index = 1;

	private Boolean isTimeout = false;

	private int mType = 1;// 1：初始化数据；2：加载更多数据

	private HashMap<String, Object> map;
	private HashMap<String, String> mapItem;
	private String oldId, idSearch;
	private String id, fromName;

	private String pinJievalue = "";

	private LinearLayout llContaint, lv_kaiqifanbei;

	private HorizontalScrollView hsv_containt;

	// private String checkId;

	private int pageSize = 30;
	private RelativeLayout rl_yuefanbei;

	private boolean isComplete = false;// 当数据少时，下拉刷新 既调用刷新 又调用 加载跟多，

	private LinearLayout llNodata;
	// 所以当每次返回的数据<pageSize的时候
	private boolean isTuijian = false;
	private boolean isSignQiangzhiliulan = false; // 夺宝强制浏览5分钟跳过来的

	private ImageButton imgbtn_left_icon_sign, imgbtn_left_icon;

//	private String is_new = null, order_by_price = null;
	private String is_hot = null, is_new = "is_new", order_by_price = null;
	private TextView tv_title, liulanTime, tv_cart_count_Force;
	private TextView mTitle;//
	private boolean notType = false;
	private TextView tvNew, tvHot, tvDesc, tvAsc;
	private View horizontal_title_ll;
	private String doIconId;
	private Context context;
	private VipDikouData vipDikouData;

	// isComplete为true
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		context = this;
//		SignListAdapter.doType = 19;
		// formatter = new SimpleDateFormat ("yyyy:MM:dd HH:mm:ss");
		// Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		// come_time = formatter.format(curDate);
		// System.out.println("进来的时间="+come_time);
		instance = this;
		long come_time = System.currentTimeMillis();
		come_time_in = DateFormatUtils.format(Long.parseLong(come_time + ""), "yyyy.MM.dd HH:mm:ss");
		// System.out.println("进来的时间="+come_time_in);

		random = Integer.valueOf((int) (Math.random() * 3 + 3));
		click_num = 1;
		num_tongji = 0;// 运营统计的点击次数

		id_new = getIntent().getIntExtra("now_type_id", -1); // 奖励内容
		nextID = getIntent().getIntExtra("now_type_id_value", -1);
		isMoreShop = getIntent().getBooleanExtra("isMoreShop", false);
		theme_id = getIntent().getStringExtra("themeId");
		type_id = getIntent().getIntExtra("next_type_id", -1);
		type_id_value = getIntent().getIntExtra("next_type_id_value", -1);

		now_type_id_value = nextID;
		now_type_id = id_new;
		next_type_id = type_id;
		next_type_id_value = type_id_value;

		// Boolean isfirst = SharedPreferencesUtil.getBooleanData(context,
		// "isfirst", true);
		// if (isfirst) {
		// explain();
		// }

		// SignExplain signExplain = new SignExplain(context);
		// signExplain.show();

//		aBar.hide();
		map = (HashMap<String, Object>) getIntent().getSerializableExtra("condition");
		mapItem = (HashMap<String, String>) getIntent().getSerializableExtra("item");
		if (mapItem != null) {
			idSearch = mapItem.get("_id");
			oldId = idSearch;
		}
		id = getIntent().getStringExtra("id");
		fromName = getIntent().getStringExtra("title");
		if(SignListAdapter.doType == 4||SignListAdapter.doType ==19){//件数浏览 或是 浏览赢提现
			pinJievalue = getIntent().getStringExtra("pinJievalue");
			doIconId = getIntent().getStringExtra("doIconId");//分类类目ID
		}else if(SignListAdapter.doType == 5){//分钟数浏览
			pinJievalue = SignListAdapter.fenzhongDoValueMap.get(YConstance.SCAN_SHOP_TIME);
			doIconId =SignListAdapter.fenzhongIconID.get(YConstance.SCAN_SHOP_TIME);//分类类目ID
		}else{
			pinJievalue = SignListAdapter.doValue.split(",")[0];
//			doIconId = getIntent().getStringExtra("doIconId");//分类类目ID
		}

		isTuijian = getIntent().getBooleanExtra("isTuijian", false);
		isSignQiangzhiliulan = getIntent().getBooleanExtra("qiangZhiLiuLan", false);
//		isSignLiulan = getIntent().getBooleanExtra("isSignLiulan", false);
		// checkId = getIntent().getStringExtra("checkId");
		setContentView(R.layout.force_look);

		haisheng = (TextView) findViewById(R.id.haisheng);
		if (id_new == 11) {
			haisheng.setText("亲，2元现金就藏在这些商品详情页里噢，快去领取吧~");
		}
		if (id_new == 12) {
			haisheng.setText("亲，5元现金就藏在这些商品详情页里噢，快去领取吧~");
		}
		if (id_new == 15) {
			haisheng.setText("亲，1元现金就藏在这些商品详情页里噢，快去领取吧~");
		}
		if (id_new == 20) {
			haisheng.setText("亲，3元现金就藏在这些商品详情页里噢，快去领取吧~");
		}
		r_list_view = (PullToRefreshListView) findViewById(R.id.r_list_view);
		headView = LayoutInflater.from(this).inflate(R.layout.view_scan_header,null);
//		addHeadeViewBanner();//上面的图已经去掉
		horizontal_title_ll = headView.findViewById(R.id.horizontal_title_ll);
		tvNew = (TextView) headView.findViewById(R.id.create_is_new);
		tvHot = (TextView) headView.findViewById(R.id.create_is_hot);
		tvDesc = (TextView) headView.findViewById(R.id.create_price_desc);
		tvAsc = (TextView) headView.findViewById(R.id.create_price_asc);
		tvHot.setOnClickListener(this);
		tvNew.setOnClickListener(this);
		tvDesc.setOnClickListener(this);
		tvAsc.setOnClickListener(this);

		if ("collection=browse_shop".equals(pinJievalue)){
			tvHot.setVisibility(View.GONE);
		}
		if (isMoreShop){
			horizontal_title_ll.setVisibility(View.GONE);
		}

		tv_shuoming = (TextView) findViewById(R.id.tv_shuoming);
		tv_shuoming.setOnClickListener(this);
		mTitle = (TextView) findViewById(R.id.tv_forcelook_title);
		tv_title = (TextView) findViewById(R.id.tv_title);
		liulanTime = (TextView) findViewById(R.id.liulanTime);
		imgbtn_left_icon_sign = (ImageButton) findViewById(R.id.imgbtn_left_icon_sign);
		imgbtn_left_icon = (ImageButton) findViewById(R.id.imgbtn_left_icon);
		lv_kaiqifanbei = (LinearLayout) findViewById(R.id.lv_kaiqifanbei);

//		shop_cart = (ImageButton) findViewById(R.id.shop_cart);

		findViewById(R.id.img_back).setOnClickListener(this);
//		img_btn_filter = (ImageButton) findViewById(R.id.img_btn_filter);
//		img_btn_filter.setOnClickListener(this);
		// 添加搜索条件
		// llContaint = (LinearLayout) findViewById(R.id.ll_containt);
		// addView(llContaint, map);

		rl_yuefanbei = (RelativeLayout) findViewById(R.id.rl_yuefanbei);
		tv_cart_count_Force = (TextView) findViewById(R.id.tv_cart_count_Force);


		llNodata = (LinearLayout) findViewById(R.id.ll_nodata);
		findViewById(R.id.btn_view_allcircle).setVisibility(View.GONE);
		((TextView) findViewById(R.id.tv_no_join)).setText("暂无数据");
		r_list_view.setMode(Mode.BOTH);



		int shopCartCount = new ShopCartDao(context).queryCartCount(context);

		if (shopCartCount > 0) {
			tv_cart_count_Force.setText(shopCartCount + "");
			tv_cart_count_Force.setVisibility(View.VISIBLE);
		} else {
			tv_cart_count_Force.setVisibility(View.GONE);
		}

		mAdapter = new ForceLookAdapter(this,isMoreShop);
		r_list_view.setAdapter(mAdapter);

		// 如果是签到跳过来的要修改布局

		if (isSignQiangzhiliulan) {
			tv_title.setText("签到领现金");
			imgbtn_left_icon_sign.setVisibility(View.VISIBLE);
//			shop_cart.setVisibility(View.VISIBLE);
			rl_yuefanbei.setVisibility(View.VISIBLE);

			imgbtn_left_icon.setVisibility(View.GONE);
//			img_btn_filter.setVisibility(View.GONE);
		}
		if (SignListAdapter.doType == 5 || SignListAdapter.doType == 6 ||
				SignListAdapter.doType == 24) {// 浏览分钟 购买(不是浏览个数的)
			mTitle.setText(fromName);
			rl_yuefanbei.setVisibility(View.GONE);
//			img_btn_filter.setVisibility(View.GONE);
			tv_cart_count_Force.setVisibility(View.GONE);
//			shop_cart.setVisibility(View.GONE);
		}
		setListViewRefresh();

		if (YJApplication.instance.isLoginSucess()) {
			//查询抵扣
			HashMap<String, String> pairsMap = new HashMap<>();
			YConn.httpPost(ForceLookActivity.this, YUrl.GETALLDIKOU, pairsMap, new HttpListener<VipDikouData>() {
				@Override
				public void onSuccess(VipDikouData result) {

					vipDikouData = result;
					initData(index + "", is_new, order_by_price);

				}

				@Override
				public void onError() {

				}
			});


		} else {
			initData(index + "", is_new, order_by_price);

		}












		// if(SharedPreferencesUtil.getStringData(context,
		// "tags","").equals("11")||SharedPreferencesUtil.getStringData(context,
		// "tags","").equals("10")){
		// new VersionChangDialog(context, R.style.DialogStyle1).show();
		// }

		// // 签到完成倒计时
		// if (mTask != null) {
		// mTask.cancel();
		// mTask = new MyTimerTask();
		// } else {
		// mTask = new MyTimerTask();
		// }
		// recLen=120*1000;
		// timer.schedule(mTask, 0, 1000); // timeTask
		if (SignListAdapter.doType == 4&&!isMoreShop) {// 强制浏览个数
			if (!SignListAdapter.isSignComplete) {
				showDialog();
			}

			// 获取当前任务的需要浏览的次数
			String value = SignListAdapter.doValue;
			String values[] = value.split(",");
			if (values.length > 1) {
				singvalue = values[1];
				if (!Pattern.compile("^\\+?[1-9][0-9]*$").matcher(singvalue).find()) {
					singvalue = "" + SignListAdapter.doNum;
				}

			} else {
				singvalue = "" + SignListAdapter.doNum;
			}
		}
        if (SignListAdapter.doType == 19&&!isMoreShop) {// 新增强制浏览个数 奖励提现额度

            // 获取当前任务的需要浏览的次数
            String value = SignListAdapter.doValue;
            String values[] = value.split(",");
            if (values.length > 1) {
                singvalue = values[1];
				if (!Pattern.compile("^\\+?[1-9][0-9]*$").matcher(singvalue).find()) {
					singvalue = "10";
				}
            }
			else {
				singvalue = "10";
			}
            haisheng.setText("每浏览"+singvalue+"件衣服，即得"+SignListAdapter.jiangliValue+"元提现额度哦，快去领取吧~");
			if (!SignListAdapter.isSignComplete) {
				showTXDialog();
			}
			if(TextUtils.isEmpty(SignListAdapter.doNeedCount)||"-1".equals(SignListAdapter.doNeedCount)
					||"null".equals(SignListAdapter.doNeedCount)){
				//没有完成过一次浏览 没有调用过签到接口
			}else{
				int forcelookLimitNum = Integer
						.parseInt(
								SharedPreferencesUtil
										.getStringData(ForceLookActivity.this,
												SignListAdapter.signIndex + Pref.ISFORCELOOKLIMITNUM
														+ YCache.getCacheUser(context).getUser_id(),
												"0"));
				int forceLookLimitNumTemp = ((SignListAdapter.doNum-Integer.parseInt(SignListAdapter.doNeedCount))*Integer.parseInt(singvalue)
				+forcelookLimitNum%Integer.parseInt(singvalue));//根据还剩任务完成次数（调用接口次数 也就是 领取奖励次数）来判断并覆盖还剩浏览次数
				SharedPreferencesUtil.saveStringData(ForceLookActivity.this,
								SignListAdapter.signIndex + Pref.ISFORCELOOKLIMITNUM
										+ YCache.getCacheUser(context).getUser_id(),
								"" + forceLookLimitNumTemp);
			}
        }
		
		
		
		if(isMoreShop){
			mTitle.setText(fromName);
			rl_yuefanbei.setVisibility(View.GONE);
//			img_btn_filter.setVisibility(View.GONE);
			tv_cart_count_Force.setVisibility(View.GONE);
		}
		
	}

	private void setListViewRefresh() {
		r_list_view.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				mType = 1;
				index = 1;
				if (isTuijian) {
					initData(index + "",  is_new, order_by_price);
					return;
				}
				initData(index + "", is_new, order_by_price);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				// if (!isComplete) {// 当数据没加载完成 上拉可以加载更多
				// if (SignListAdapter.doType!=4) {
				index++;
				mType = 2;
				if (isTuijian) {
					initData(index + "", is_new, order_by_price);
				} else {
					initData(index + "",  is_new, order_by_price);
				}
				// }else{
				// r_list_view.onRefreshComplete();//强制浏览个数 只获取最前面十件商品
				// }
			}
		});

	}

	/**
	 * 强制浏览签到寻宝提示框 每天只显示一次
	 */
	private void showDialog() {
		SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String forceLook = SharedPreferencesUtil.getStringData(context, Pref.FORCELOOK, "0");
		long forceLookTime = Long.parseLong(forceLook);
		if ("0".equals(forceLook) || !df.format(new Date()).equals(df.format(new Date(forceLookTime)))) {
			new XunBaoDialog(context).show();
			SharedPreferencesUtil.saveStringData(context, Pref.FORCELOOK, System.currentTimeMillis() + "");
		}

	}
	/**
	 * 强制浏览签到寻宝提示框 每天只显示一次(浏览赢提现)
	 */
	private void showTXDialog() {
		SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String forceLookTX = SharedPreferencesUtil.getStringData(context, Pref.FORCELOOKTX, "0");
		long forceLookTimeTX = Long.parseLong(forceLookTX);
		if ("0".equals(forceLookTX) || !df.format(new Date()).equals(df.format(new Date(forceLookTimeTX)))) {
			XunBaoDialog dialog = new XunBaoDialog(context);
			dialog.show();
			dialog.setContentText("每浏览"+singvalue+"件美衣，即得"+SignListAdapter.jiangliValue+"元提现额度，任务奖励就藏在商品里，快去领取吧~");
			SharedPreferencesUtil.saveStringData(context, Pref.FORCELOOKTX, System.currentTimeMillis() + "");
		}

	}
	private MyTimerTask mTask;

	private void initData(final String index,  final String is_new,
			final String order_by_price) {

		// 下面是真数据
		final int pageSize = 10;
		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(this, 0) {
			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
					throws Exception {
				if(isMoreShop){//穿搭详情--更多推荐商品
					return ComModel2.getMoreShop(context, pageSize, index,theme_id);
				}else{
					//热门推首拿强制浏览的数据
					if ("collection=browse_shop".equals(pinJievalue)){
						return ComModel2.getForceLook(context, pageSize, index,is_new,is_hot,order_by_price);
					}else{
						//热卖推荐商品
						return ComModel2.getSignShop(context, index, pinJievalue,is_new,is_hot,order_by_price);
					}

				}










//				return ComModel2.getForceLook(context, pageSize, index);
//				if (SignListAdapter.doType == 4) {// 浏览个数的
//					if ("collection=browse_shop".equals(SignListAdapter.doValue.split(",")[0])) {// 热门推首
//						// 浏览个数
//						return ComModel2.getForceLook(context, pageSize, index);
//					} else if (mapItem != null) {
//						return ComModel2.getProductListBySearch(context, params[0], idSearch, "2", pageSize, is_new,
//								order_by_price, notType, oldId);
//					} else {
//						return ComModel2.getFilterProductList2(title, id, context, params[0], map, true + "", pageSize,
//								is_new, order_by_price, false);
//					}
//				} else {// 浏览分钟数的
//					return ComModel2.getProductList1(context, index, id + "", "1", title + "", pageSize, false);
//				}
			}

			@Override
			protected boolean isHandleException() {

				return true;
			}

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
						if (null != vipDikouData && vipDikouData.getIsVip() > 0) {
							mAdapter.setDataVip(result, true, vipDikouData);

						} else {
//                            mAdapter.setData(result);
							mAdapter.setDataVip(result, false, vipDikouData);


						}
					} else if (mType == 2) {
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

		}.execute(index);
	}

	private String singvalue;

	// // 修改系统返回键
	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// // TODO Auto-generated method stub
	// if(SignListAdapter.doType==4){
	// countTaskBack();
	// }else if(SignListAdapter.doType==5){
	// timeTaskBack();
	// }else{
	// onBackPressed();
	// }
	// return true;
	//
	// }

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.img_back: // 返回
			onBackPressed();
			break;
		case R.id.img_btn_filter:

			Intent intent = new Intent(this, ShopCartNewNewActivity.class);
			intent.putExtra("where", "0");
			startActivity(intent);
			break;

		case R.id.tv_shuoming:
			if(SignListAdapter.doType==19){
				XunBaoDialog dialog = new XunBaoDialog(context);
				dialog.show();
				dialog.setContentText("每浏览"+singvalue+"件美衣，即得"+SignListAdapter.jiangliValue+"元提现额度，任务奖励就藏在商品里，快去领取吧~");
			}else {
				new XunBaoDialog(context).show();
			}
			break;
			
		case R.id.create_is_new:// 上新
			tvNew.setTextColor(Color.parseColor("#FF3F8B"));
			tvHot.setTextColor(Color.parseColor("#3E3E3E"));
			tvDesc.setTextColor(Color.parseColor("#3E3E3E"));
			tvAsc.setTextColor(Color.parseColor("#3E3E3E"));
			is_new = "is_new";
			is_hot = null;
			order_by_price = null;
			mType = 1;
			index = 1;
			initData(index + "", is_new, order_by_price);
			break;
		case R.id.create_is_hot:// 热销
			tvHot.setTextColor(Color.parseColor("#FF3F8B"));
			tvNew.setTextColor(Color.parseColor("#3E3E3E"));
			tvDesc.setTextColor(Color.parseColor("#3E3E3E"));
			tvAsc.setTextColor(Color.parseColor("#3E3E3E"));
			is_hot = "is_hot";
			is_new = null;
			order_by_price = null;
			mType = 1;
			index = 1;
			initData(index + "",  is_new, order_by_price);
			break;
		case R.id.create_price_asc:// 价格
			tvAsc.setTextColor(Color.parseColor("#FF3F8B"));
			tvNew.setTextColor(Color.parseColor("#3E3E3E"));
			tvHot.setTextColor(Color.parseColor("#3E3E3E"));
			tvDesc.setTextColor(Color.parseColor("#3E3E3E"));
			is_new = null;
			is_hot = null;
			order_by_price = "asc";
			mType = 1;
			index = 1;
			initData(index + "",  is_new, order_by_price);
			break;
		case R.id.create_price_desc:// 价格
			tvDesc.setTextColor(Color.parseColor("#FF3F8B"));
			tvNew.setTextColor(Color.parseColor("#3E3E3E"));
			tvHot.setTextColor(Color.parseColor("#3E3E3E"));
			tvAsc.setTextColor(Color.parseColor("#3E3E3E"));
			is_new = null;
			is_hot = null;
			order_by_price = "desc";
			mType = 1;
			index = 1;
			initData(index + "",  is_new, order_by_price);
			break;
		default:
			break;
		}
	}

	// private void explain() {
	// // TODO Auto-generated method stub
	// SignExplain signExplain = new SignExplain(this, id_new);
	// signExplain.show();
	// SharedPreferencesUtil.saveBooleanData(context, "isfirst", false);
	// }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	private boolean isLou = true;

	private TextView tv_shuoming;

	public static int random;

	private int id_new;

	private int nextID;

	private TextView haisheng;
	private SimpleDateFormat formatter;
	private int type_id;
	private int type_id_value;
	private String come_time;
	private String come_time_in;

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
//			if (id == R.id.create_is_new) {
//				is_new = "is_new";
//				order_by_price = null;
//			} else if (id == R.id.create_price_desc) {
//				is_new = null;
//				order_by_price = "desc";
//			} else if (id == R.id.create_price_asc) {
//				is_new = null;
//				order_by_price = "asc";
//			}
			notType = true;
			mType = 1;
			index = 1;
			// initData(index + "", SearchResultActivity.this.id,
			// level,is_new,order_by_price, notType);
			initData(index + "",  is_new, order_by_price);
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

	// private long recLen = (1 * 1000 * 60) / 2; // 30秒 定时时长 （毫秒）
	// Timer timer = new Timer();

	// 倒计时
	class MyTimerTask extends TimerTask {

		@Override
		public void run() {

			((Activity) context).runOnUiThread(new Runnable() { // UI thread
				@Override
				public void run() {
					recLen -= 1000;
					String days;
					String hours;
					String minutes;
					String seconds;
					long minute = recLen / 60000;
					long second = (recLen % 60000) / 1000;
					if (minute >= 60) {
						long hour = minute / 60;
						minute = minute % 60;
						if (hour >= 24) {
							long day = hour / 24;
							hour = hour % 24;
							if (day < 10) {
								days = "0" + day;
							} else {
								days = "" + day;
							}
							if (hour < 10) {
								hours = "0" + hour;
							} else {
								hours = "" + hour;
							}
							if (minute < 10) {
								minutes = "0" + minute;
							} else {
								minutes = "" + minute;
							}
							if (second < 10) {
								seconds = "0" + second;
							} else {
								seconds = "" + second;
							}
							liulanTime.setText("" + days + "天" + hours + ":" + minutes + ":" + seconds);
						} else {
							if (hour < 10) {
								hours = "0" + hour;
							} else {
								hours = "" + hour;
							}
							if (minute < 10) {
								minutes = "0" + minute;
							} else {
								minutes = "" + minute;
							}
							if (second < 10) {
								seconds = "0" + second;
							} else {
								seconds = "" + second;
							}
							liulanTime.setText("" + hours + ":" + minutes + ":" + seconds);
						}
					} else if (minute >= 10 && second >= 10) {
						liulanTime.setText("" + minute + ":" + second);
					} else if (minute >= 10 && second < 10) {
						liulanTime.setText("" + minute + ":0" + second);
					} else if (minute < 10 && second >= 10) {
						liulanTime.setText("0" + minute + ":" + second);
					} else {
						liulanTime.setText("0" + minute + ":0" + second);
					}
					// tv_time.setText("" + recLen);
					if (recLen <= 0) {
						timer.cancel();

					}

					String time = (String) liulanTime.getText();

					if (recLen <= 0) { // 时间到了以后可以返回签到完成签到
						isTimeout = true;
					}

				}
			});
		}

	}

	private String tongJiTime() {
		Date leave_time = new Date(System.currentTimeMillis());// 获取当前时间
		String str_leave_time = formatter.format(leave_time);
		// System.out.println("离开时间="+str_leave_time);
		// TODO 等接口 问准要传什么格式再做
		return str_leave_time;
	}

	private void yunYunTongJi(final String shop_code, final int type, final int tab_type) {
		new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) this, R.string.wait) {

			@Override
			protected boolean isHandleException() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
					throws Exception {

				return ComModel2.getOperator(context, shop_code, type, tab_type);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				// System.out.println("购物车这块调用成功");
			}

		}.execute();
	}

	private void countTaskBack() {
		int forcelookNum=0;
		int forcelookLimitNum=0;
		SimpleDateFormat dfNum = new SimpleDateFormat("yyyy-MM-dd");
		if (SignListAdapter.doType ==4) {
			String nowTimeForcelook = SharedPreferencesUtil.getStringData(ForceLookActivity.this,
                    "forcelookNowTime" + YCache.getCacheUser(context).getUser_id(), "");
			forcelookNum = Integer.parseInt(SharedPreferencesUtil.getStringData(ForceLookActivity.this,
                    SignListAdapter.signIndex + "forcelookNum" + YCache.getCacheUser(context).getUser_id(), "0"));
			if (!dfNum.format(new Date()).equals(nowTimeForcelook)) {
                forcelookNum = 0;// 不是同一天点击分享任务 或者不是同一个用户 就 或者取出的数据大于浏览次数 计数分享重置
            }
		} else if (SignListAdapter.doType ==19){
			String nowTimeForcelookLimit = SharedPreferencesUtil.getStringData(
					ForceLookActivity.this,
					"nowTimeForcelookLimit" + YCache.getCacheUser(context).getUser_id(), "");
			forcelookLimitNum = Integer
					.parseInt(
							SharedPreferencesUtil
									.getStringData(ForceLookActivity.this,
											SignListAdapter.signIndex + Pref.ISFORCELOOKLIMITNUM
													+ YCache.getCacheUser(context).getUser_id(),
											"0"));
			if (!dfNum.format(new Date()).equals(nowTimeForcelookLimit)) {
				forcelookLimitNum = 0;// 不是同一天点击分享任务
				// 或者不是同一个用户 就
				// 或者取出的数据大于浏览次数
				// 重新开始计数分享的次数
			}
		}
		if (/* isTimeout || */ (SignListAdapter.doType ==4 && forcelookNum >= Integer.parseInt(singvalue))
				||(SignListAdapter.doType ==19 && forcelookLimitNum/Integer.parseInt(singvalue) +1 > SignListAdapter.doNum)){
			// Toast.makeText(context, "签到已完成", Toast.LENGTH_SHORT).show();
			long time = System.currentTimeMillis();
			String leave_time = DateFormatUtils.format(Long.parseLong(time + ""), "yyyy.MM.dd HH:mm:ss");

			DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			try {
				Date d1 = df.parse(leave_time + "");
				Date d2 = df.parse(come_time_in);
				long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
				long days = diff / (1000 * 60 * 60 * 24);
				long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
				long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);

				long time_stay = diff / 1000; // 停留时间

				StringBuffer sb = new StringBuffer();
				sb.append(time_stay + ",");
				sb.append(num_tongji);
				String string = sb.toString();

				yunYunTongJi(string + "", 400, 5);

			} catch (Exception e) {

			}

			////
			finish();
			overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
		} else {
			// Toast.makeText(context, "亲，在逛一会才能领取现金哦",
			// Toast.LENGTH_SHORT).show();
			final LeaveDialog leaveDialog = new LeaveDialog(this);
			leaveDialog.show();

			View btn_left = leaveDialog.findViewById(R.id.btn_left);
			btn_left.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 获取离开时间 用来统计用户平均浏览时长

					////

					long time = System.currentTimeMillis();
					String leave_time = DateFormatUtils.format(Long.parseLong(time + ""), "yyyy.MM.dd HH:mm:ss");

					DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
					try {
						Date d1 = df.parse(leave_time + "");
						Date d2 = df.parse(come_time_in);
						long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
						long days = diff / (1000 * 60 * 60 * 24);
						long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
						long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);

						long time_stay = diff / 1000; // 停留时间

						StringBuffer sb = new StringBuffer();
						sb.append(time_stay + ",");
						sb.append(num_tongji);
						String string = sb.toString();

						yunYunTongJi(string + "", 400, 5);

					} catch (Exception e) {

					}

					////
					if(leaveDialog!=null){
						leaveDialog.dismiss();
					}
					finish();
					overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
				}
			});

		}
	}

	/**
	 * 浏览分钟数返回时候的弹窗提示
	 */
	private void timeTaskBack() {
		if (SignListAdapter.isForceLookTimeOut) {// 分钟数时间到了
			finish();
			overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
		} else {
			final LeaveDialog leaveDialog = new LeaveDialog(this);
			leaveDialog.show();
			leaveDialog.setContentText("你正在进行浏览商品任务，浏览时长还未完成，你可以选择再逛逛当前页面，或者去浏览其它商品，浏览时长达到任务要求即可完成任务喔~");
			leaveDialog.setButtonText("不了，谢谢", "其他商品");
			View btn_left = leaveDialog.findViewById(R.id.btn_left);
			btn_left.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(leaveDialog!=null){
						leaveDialog.dismiss();
					}
					finish();
					overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
				}
			});
			View btn_right = leaveDialog.findViewById(R.id.btn_right);
			btn_right.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent((Activity) context, MainMenuActivity.class);
					intent.putExtra("toYf", "toYf");
					context.startActivity(intent);
				}
			});
		}
	}

	@Override
	public void onBackPressed() {
		if(isMoreShop){
			super.onBackPressed();
			overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
			return;
		}
		if ((SignListAdapter.doType == 4||SignListAdapter.doType == 19)&& !SignListAdapter.isSignComplete) {
			countTaskBack();
		} else if (SignListAdapter.doType == 5 && !SignListAdapter.isSignComplete) {
			timeTaskBack();
		} else {
			super.onBackPressed();
			overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
		}
	}



	private int width;
	private  View  headView;
	private ImageView imgBanner;
	private YDBHelper dbHelp;
	private List<HashMap<String,String>> listData;
	private void  addHeadeViewBanner(){
		if(isMoreShop){
			return;
		}
		r_list_view.getRefreshableView().addHeaderView(headView);
		imgBanner = (ImageView) headView.findViewById(R.id.img_head);
		imgBanner.setVisibility(View.GONE);
		if(SignListAdapter.doType==6||SignListAdapter.doType==24){
			return;
		}
		dbHelp = new YDBHelper(context);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		ViewGroup.LayoutParams lp = imgBanner.getLayoutParams();
		lp.width =width;
		lp.height =lp.width*200/340;
		imgBanner.setLayoutParams(lp);
//		imgBanner.setImageResource(R.drawable.bg_group_shop);
		listData= new ArrayList<HashMap<String,String>>();
		String sql = "select * from shop_group_list where _id = "+doIconId;
		listData =  dbHelp.query(sql);
		if(listData!=null&&listData.size()>0){
			String banner = listData.get(0).get("banner");
			if("null".equals(banner)||TextUtils.isEmpty(banner)) {
				imgBanner.setVisibility(View.GONE);
			}else{
				PicassoUtils.initImage(this,banner+"!560",imgBanner);
				imgBanner.setVisibility(View.VISIBLE);
			}
		}

	}

}
