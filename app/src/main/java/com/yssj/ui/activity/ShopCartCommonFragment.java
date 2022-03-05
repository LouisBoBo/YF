package com.yssj.ui.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.assist.ImageScaleType;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.YJApplication;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.entity.GoodsEntity;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.Shop;
import com.yssj.entity.ShopCart;
import com.yssj.entity.StockType;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.HomeWatcherReceiver;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsDialog;
import com.yssj.ui.activity.shopdetails.SubmitMultiShopActivty;
import com.yssj.ui.adpter.ShopCartCommonAdpter;
import com.yssj.ui.adpter.ShopCartCommonAdpter.ShopCartAdpterInterface;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.YCache;
import com.yssj.utils.YunYingTongJi;
import com.yssj.utils.sqlite.ShopCartDao;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class ShopCartCommonFragment extends Fragment implements OnClickListener, ShopCartAdpterInterface {
	private boolean tongJiFirst = true;
	private TextView tv_join_my_love;
	private LinearLayout lay_cartpay, ll_goto_pay;
	private TextView tv_allchoose, tv_price, tv_no_freight, tv_time, tv_pay;
	private boolean mFLagCommom = false;
	private static List<List<ShopCart>> mListCommon2 = new ArrayList<List<ShopCart>>();
	// private ArrayList<Integer> spLocal;
	// private ArrayList<Integer> spImgLocal;
	private int height;
	private int width;
	// public static List<List<ShopCart>> listShopCarts = new
	// ArrayList<List<ShopCart>>();
	private static ArrayList<Integer> sp = new ArrayList<Integer>();
	private static ArrayList<Integer> spImg = new ArrayList<Integer>();

	private ShopCartCommonAdpter adpter;
	private PullToRefreshListView listView;
	private int statusBarHeight;

	private TextView tv_edit_cart;

	private double cPrice;// 结算商品的总价格

	private boolean refeshFlag = true, allFlag = false;

	private LinearLayout layout_nodata_shopcar;
	private Button btn_to_shop;
	private Boolean mPayFlag = false;// 去结算标记
	private LinearLayout llBottom;// new TextView(context);MainMenuActivity
	private Context mContext;
	private String TAG = "ShopCartCommonFragment";
	// private long recLen = 30 * 60 * 1000;
	boolean isShowShopCart = false;
	// Timer timer = new Timer();
	// private MyTimerTask mTask;
	// public List<List<ShopCart>> mListInValid = new
	// ArrayList<List<ShopCart>>();
	public List<List<ShopCart>> mListInValidTeml = new ArrayList<List<ShopCart>>();
	public List<ShopCart> mListDao = new ArrayList<ShopCart>();
	// public List<ShopCart> mListDaoInvalid = new ArrayList<ShopCart>();
	// List<List<ShopCart>> mListAll = new ArrayList<List<ShopCart>>();
	private List<ShopCart> mListAllNew = new ArrayList<ShopCart>();
	private List<ShopCart> mListValidNew = new ArrayList<ShopCart>();
	private List<ShopCart> mListInValidNew = new ArrayList<ShopCart>();
	private long mDeadTime = 0;// 结束时间（过期时间）
	private long mSysTime = 0;// 系统时间（系统当前时间）
	private String flag_activity = "0";// "0"代表为fragment过来的“1”代表activity
	private TextView mEditButton;// 编辑按钮
	public static boolean mEditFlag = false;// false正常状态，true编辑状态
	private int mType = 1;// 1：初始化数据；2：加载更多数据

	public interface ShopCartCommonInterface {
		void getShopCommonCount(int count);
	}

	// public void setCartOncallback(ShopCartNewNewActivity f) {
	// this.shopCartCommonInterface = f;
	// }
	private ShopCartCommonInterface shopCartCommonInterface;

	public ShopCartCommonFragment(ShopCartNewNewFragment f, TextView editButton) {
		this.mEditButton = editButton;
		mEditButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mEditFlag = !mEditFlag;
//				if(adpter!=null){
//				adpter.notifyDataSetChanged();
//				}
				if (mEditFlag) {
					tv_join_my_love.setVisibility(View.VISIBLE);
					tv_price.setVisibility(View.GONE);
					tv_no_freight.setVisibility(View.GONE);
					tv_pay.setText("删除");
					mEditButton.setText("完成");
					mEditButton.setTextColor(0xffff3f8b);
					int flag = 0;
					for (int i = 0; i < spImg.size(); i++) {
						if (spImg.get(i) == 2) {
							flag = 1;
							break;
						}
					}
					if (flag == 1) {
						ll_goto_pay.setBackgroundColor(0xfffb3b3b);
					} else {
						ll_goto_pay.setBackgroundColor(0xffc5c5c5);
					}
				} else {
					tv_join_my_love.setVisibility(View.GONE);
					tv_price.setVisibility(View.VISIBLE);
					tv_no_freight.setVisibility(View.VISIBLE);
					tv_pay.setText("结算");
					int flag = 0;
					for (int i = 0; i < spImg.size(); i++) {
						if (spImg.get(i) == 2) {
							flag = 1;
							break;
						}
					}
					if (flag == 1) {
						ll_goto_pay.setBackgroundColor(0xffff3f8b);
					} else {
						ll_goto_pay.setBackgroundColor(0xffc5c5c5);
					}
					mEditButton.setText("编辑");
					mEditButton.setTextColor(0xff3e3e3e);
				}
				// setEditSelect(mEditFlag);
			}
		});
		this.shopCartCommonInterface = f;
		flag_activity = "0";

	}

	// 设置全选
	private void setEditSelect(boolean isSelectedAll) {
		if (isSelectedAll) { // 全选 选中状态

		} else {
		}
//		adpter.notifyDataSetChanged();
	}

	public ShopCartCommonFragment(ShopCartNewNewActivity f, TextView editButton) {
		this.mEditButton = editButton;
		mEditButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mEditFlag = !mEditFlag;
//				if(adpter!=null){
//				adpter.notifyDataSetChanged();
//				}
				if (mEditFlag) {
					tv_join_my_love.setVisibility(View.VISIBLE);
					tv_price.setVisibility(View.GONE);
					tv_no_freight.setVisibility(View.GONE);
					tv_pay.setText("删除");
					mEditButton.setText("完成");
					mEditButton.setTextColor(0xffff3f8b);
					int flag = 0;
					for (int i = 0; i < spImg.size(); i++) {
						if (spImg.get(i) == 2) {
							flag = 1;
							break;
						}
					}
					if (flag == 1) {
						ll_goto_pay.setBackgroundColor(0xfffb3b3b);
					} else {
						ll_goto_pay.setBackgroundColor(0xffc5c5c5);
					}
				} else {
					tv_join_my_love.setVisibility(View.GONE);
					tv_price.setVisibility(View.VISIBLE);
					tv_no_freight.setVisibility(View.VISIBLE);
					tv_pay.setText("结算");
					int flag = 0;
					for (int i = 0; i < spImg.size(); i++) {
						if (spImg.get(i) == 2) {
							flag = 1;
							break;
						}
					}
					if (flag == 1) {
						ll_goto_pay.setBackgroundColor(0xffff3f8b);
					} else {
						ll_goto_pay.setBackgroundColor(0xffc5c5c5);
					}
					mEditButton.setText("编辑");
					mEditButton.setTextColor(0xff3e3e3e);
				}
				// setEditSelect(mEditFlag);
			}
		});
		this.shopCartCommonInterface = f;
		flag_activity = "1";
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		// mListCommon2 = new ArrayList<List<ShopCart>>();
		// sp = new ArrayList<Integer>();
		// spImg = new ArrayList<Integer>();
		// spLocal=new ArrayList<Integer>();
		// spImgLocal=new ArrayList<Integer>();
		View view = View.inflate(mContext, R.layout.activity_shopcart_common, null);
		listView = (PullToRefreshListView) view.findViewById(R.id.listview_shopcart);
		// flush();
		lay_cartpay = (LinearLayout) view.findViewById(R.id.lay_cartpay);
		// lay_cartremove = (LinearLayout) v.findViewById(R.id.lay_cartremove);
		tv_price = (TextView) view.findViewById(R.id.tv_price);
		tv_no_freight = (TextView) view.findViewById(R.id.tv_no_freight);
		tv_pay = (TextView) view.findViewById(R.id.tv_pay);
		ll_goto_pay = (LinearLayout) view.findViewById(R.id.ll_goto_pay);
		tv_join_my_love = (TextView) view.findViewById(R.id.tv_join_my_love);
		tv_join_my_love.setOnClickListener(this);
		tv_time = (TextView) view.findViewById(R.id.tv_time);
		ll_goto_pay.setOnClickListener(this);
		// tv_remove = (TextView) v.findViewById(R.id.tv_remove);
		// tv_remove.setOnClickListener(this);
		// tv_delete_all = (TextView) v.findViewById(R.id.tv_delete_all);
		// tv_delete_all.setOnClickListener(this);
		tv_allchoose = (TextView) view.findViewById(R.id.tv_allchoose);
		tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
				mContext.getResources().getDrawable(R.drawable.icon_dapeigou_normal), null, null, null);
		tv_allchoose.setOnClickListener(this);

		// int resource = R.layout.listview_shop_cart;
		// listShopCarts = new ArrayList<List<ShopCart>>();
		// queryListShopCart(1);
		// adpter = new
		// ShopCartAdpter(mContext.getApplicationmContext(),
		// resource,
		// listShopCarts, sp, spImg);
		// adpter.setCartOncallback(this);
		//
		// listView.setAdapter(adpter);
		// listView.setRefreshing();
		layout_nodata_shopcar = (LinearLayout) view.findViewById(R.id.layout_nodata_shopcar);
		llBottom = (LinearLayout) view.findViewById(R.id.lay_bottom);
		// if (listShopCarts!=null&&listShopCarts.size() > 0) {
		// llBottom.setVisibility(View.VISIBLE);
		// }else{
		// layout_nodata_shopcar.setVisibility(View.VISIBLE);
		// listView.setVisibility(View.GONE);
		// }

		btn_to_shop = (Button) view.findViewById(R.id.btn_to_shop);
		btn_to_shop.setOnClickListener(this);

		mDeadTime = Long.parseLong(SharedPreferencesUtil.getStringData(mContext, Pref.SHOPCART_COMMON_TIME, "0"));
		mSysTime = System.currentTimeMillis();

		// recLen = mDeadTime - mSysTime;

		// if (mTask != null) {
		// mTask.cancel();
		// mTask = new MyTimerTask();
		// } else {
		// mTask = new MyTimerTask();
		// }

		// if (timer == null) {
		// timer = new Timer();
		// }
		// timer.schedule(mTask, 0, 1000); // timeTask

		// int resource = R.layout.listview_shop_cart;
		// adpter = new ShopCartAdpter((FragmentActivity) mContext, resource,
		// listShopCarts, sp, spImg, 1);
		// adpter.setCartOncallback(this);
		// flush();
		// mListAll.clear();
		return view;
	}

	/**
	 * 编辑全部
	 */
	private void editorAllGoods(TextView tv_edit_cart) {
		String strTitle = tv_edit_cart.getText().toString();
		if (strTitle.equals("编辑全部")) {
			tv_edit_cart.setText("完成");
			lay_cartpay.setVisibility(View.GONE);
			// lay_cartremove.setVisibility(View.VISIBLE);
			refeshFlag = false;
			for (int i = 0; i < sp.size(); i++) {
				sp.set(i, 3);
			}

		} else {
			tv_edit_cart.setText("编辑全部");
			lay_cartpay.setVisibility(View.VISIBLE);
			// lay_cartremove.setVisibility(View.GONE);
			refeshFlag = true;
			for (int i = 0; i < sp.size(); i++) {
				sp.set(i, 1);
			}
		}
//		if(adpter!=null){
//		adpter.notifyDataSetChanged();
//		}
	}

	private int curPage = 1;
	private int pageSize = 10;
	private AlertDialog dialog;

	/****
	 * 单个删除购物车信息
	 */
	private void deleteShopCart(final int position) {
		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, null, R.string.wait) {
			@Override
			protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {
				String isSelect = "";
				String paired_code = "";
				String shop_code = "";
				// if (position < listShopCarts.size()) {// 有效
				// isSelect = "yes_single";
				// } else {// 无效
				isSelect = "yes_single";
				// }
				ReturnInfo r = ComModel.deleteShopCartNew(mContext, YCache.getCacheToken(mContext), params[0],
						params[1], isSelect, paired_code, shop_code);
				return r;
			}

			@Override
			protected void onPostExecute(FragmentActivity mContext, ReturnInfo r, Exception e) {

				if (e != null) {
					LogYiFu.e("异常 -----", "异常");
				} else {
					ShopCartDao dao = new ShopCartDao(mContext);
					if (position < mListValidNew.size()) {
						dao.delete("" + mListAllNew.get(position).getStock_type_id());
						spImg.remove(position);
						mListAllNew.remove(position);
						mListValidNew.remove(position);
						if (shopCartCommonInterface != null) {
							shopCartCommonInterface.getShopCommonCount(dao.queryCartCount(mContext));
						}
						if (spImg.size() <= 0) {

						}
						int flag = 0;
						for (int i = 0; i < spImg.size(); i++) {
							if (spImg.get(i) == 1) {
								flag = 1;
								break;
							}
						}
						if (flag == 0) {
							ll_goto_pay.setBackgroundColor(0xffc5c5c5);
						}
						setPrice();
					} else {
						dao.delete("" + mListAllNew.get(position).getStock_type_id());
						mListAllNew.remove(position);
						mListInValidNew.remove(position - mListValidNew.size());
					}
					ToastUtil.showShortText(mContext, "删除成功");
//					if(adpter!=null){
//					adpter.notifyDataSetChanged();
//					}
					// adpter.deleteSucess(mListAllNew.get(position));
					// getSystemTime(getActivity());

				}
			};

			@Override
			protected boolean isHandleException() {
				return true;
			};
		}.execute(mListAllNew.get(position).getId() + "", mListAllNew.get(position).getP_code());
	}

	public void flush() {
		// MobclickAgent.onPageStart("ShopCartActivity");
		// MobclickAgent.onResume(mContext);

		// UserInfo userInfo = YCache.getCacheUserSafe(ShopCartActivity.this);
		curPage = 1;
		// queryListShopCart(1);
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// if (!mEditFlag) {
				// curPage = 1;
				// } else {
				// Toast.makeText(mContext, "编辑状态下不能刷新",
				// Toast.LENGTH_SHORT).show();
				// }
				curPage = 1;
				listView.onRefreshComplete();
				// TODO:删
				// setAllSelect(false);
				allFlag = false;
				mType = 1;	
				initMyLoveData("" + curPage);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// if (!mEditFlag) {
				// curPage++;
				// } else {
				// Toast.makeText(mContext, "编辑状态下不能刷新",
				// Toast.LENGTH_SHORT).show();
				// }
				curPage++;
				mType = 2;
				listView.onRefreshComplete();
				// setAllSelect(false);
				allFlag = false;
				// getSystemTime(mContext);
				initMyLoveData("" + curPage);
			}

		});

		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {

				LogYiFu.e("End of List! == ", "End of List!");
			}
		});
		// listView.setOnItemClickListener(this);
	}

	/***
	 * 修改购物车信息
	 */
	private void updateShopCart(final int position, final int shop_number,final List<ShopCart> allListNew) {

		final ShopCart cart = mListAllNew.get(position);
		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, null, R.string.wait) {
			@Override
			protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {
				ReturnInfo r = ComModel.updateShopCart(mContext, YCache.getCacheToken(mContext), "" + shop_number,
						cart.getId() + "", cart.getStock_type_id() + "", cart.getP_code());
				return r;
			}

			@Override
			protected void onPostExecute(FragmentActivity mContext, ReturnInfo r, Exception e) {

				if (e != null) {
					LogYiFu.e("异常 -----", "异常");
				} else {
					if ("1".equals("" + r.getStatus())) {
						allListNew.get(position).setShop_num(shop_number);
						ShopCartDao dao = new ShopCartDao(mContext);
						dao.modify("" + mListAllNew.get(position).getStock_type_id(), shop_number);
//						if(adpter!=null){
//						adpter.notifyDataSetChanged();
//						}
						if (shopCartCommonInterface != null) {
							shopCartCommonInterface.getShopCommonCount(dao.queryCartCount(mContext));
						}
						setPrice();
					}
					// getSystemTime(ShopCartCommonFragment.this.mContext);
				}
			};

			@Override
			protected boolean isHandleException() {
				return true;
			};
		}.execute();

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.tv_join_my_love:// 加入我的喜欢
			addShopLike();
			break;
		case R.id.tv_edit_cart:// 编辑全部
			editorAllGoods(tv_edit_cart);
			break;
		case R.id.btn_to_shop:// 购物车没商品时随便逛逛
			/*
			 * if(MainMenuActivity.instances!=null){
			 * MainMenuActivity.instances.finish(); } intent=new Intent(this,
			 * MainMenuActivity.class);
			 * 
			 * startActivity(intent);
			 */
			Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
			intent2.putExtra("toYf", "toYf");
			((Activity) mContext).startActivity(intent2);
			if ("1".equals(flag_activity)) {
				((Activity) mContext).finish();
			}
			break;
		case R.id.tv_allchoose:// 全选
			allFlag = !allFlag;
			setAllSelect(allFlag);
			break;
		case R.id.ll_goto_pay:// 结算或删除
			if (mEditFlag) {// 删除所有有效商品
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < mListValidNew.size(); i++) {
					if (spImg.get(i) == 2) {
						sb.append(mListValidNew.get(i).getId()).append(",");
					}
				}

				if (sb.toString() == null || "".equals(sb.toString())) {
					return;
				} else {
					String ids = sb.toString().substring(0, sb.toString().length() - 1);
					getDeleteDialog(false, 0, ids, 1);
					// deleteShopCartList(mContext, ids, 1);
				}

			} else {// 结算
				YunYingTongJi.yunYingTongJi(mContext, 109);

				// TODO:删
				mPayFlag = true;
				getOrderNo();
			}
			break;
		// case R.id.tv_remove:// 移到收藏夹
		// addShopLike(tv_remove);
		// break;
		// case R.id.tv_delete_all:// 批量删除
		// deleteShopCartList();
		// break;
		default:
			break;
		}

	}

	private void getOrderNo() {
		List<ShopCart> listGoods = new ArrayList<ShopCart>();
		double mYouhuiMoneyCount = 0;
		for (int i = 0; i < mListValidNew.size(); i++) {
			if (spImg.get(i) == 2) {
				listGoods.add(mListValidNew.get(i));
			}
			// double mYouhuiMoney = 0;
			// for (int j = 0; j < listShopCarts.get(i).size(); j++) {
			// if (listShopCarts.get(i).size() > 1) {
			// ShopCart shopCart2 = listShopCarts.get(i).get(j);
			// if (shopCart2.getValid() != 1) {
			// mYouhuiMoney += shopCart2.getShop_se_price() *
			// shopCart2.getShop_num() * 1 / 10;
			// }
			// }
			// ShopCart shopCart = listShopCarts.get(i).get(j);
			// if (shopCart.getValid() != 1) {
			// if (shopCart != null) {
			// listGoods.add(shopCart);
			// }
			// }
			// }
			//
			// mYouhuiMoneyCount += mYouhuiMoney;
		}
		if (listGoods.size() == 0) {
			return;
		}
		// 运营统计拼接shop_code
		StringBuffer bf = new StringBuffer();
		for (int i = listGoods.size() - 1; i >= 0; i--) {
			String shop_code = listGoods.get(i).getShop_code();

			if (i != 0) {
				bf.append(shop_code + ",");
			} else {
				bf.append(shop_code);
			}
		}
		if (listGoods.size() >= 1) {
			yunYunTongJi(bf.toString(), 200, 2);
		}

		// 如果数量为0 不跳转
		if (listGoods.size() == 0) {
			// Toast.makeText(mContext, "商品已下架", Toast.LENGTH_SHORT).show();
			ToastUtil.showShortText(mContext, "商品已下架");
			return;
		}
		Intent intent = new Intent(mContext, SubmitMultiShopActivty.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("listGoods", (Serializable) listGoods);
		intent.putExtras(bundle);
		intent.putExtra("mYouhuiMoneyCount", mYouhuiMoneyCount);
		intent.putExtra("Time", 30 * 60 * 1000);
		((Activity) mContext).startActivityForResult(intent, 101);
		// getActivity().finish();
	}

	// 默认全选，已不能点击
	/*
	 * @Override public void selectOnCallBack(int position) { int p =
	 * sp.get(position); if (p == 1) { sp.set(position, 2); } else if (p == 2) {
	 * sp.set(position, 1); } setPrice(); // adpter.notifyDataSetChanged();
	 * 
	 * }
	 */

	@Override
	public void addOnCallBack(int position, int shop_number,List<ShopCart> allListNew) {
		updateShopCart(position, shop_number,allListNew);

	}

	// @Override
	// public void reduceOnCallBack(int position, int shop_number) {
	// updateShopCart(position, shop_number);
	//
	// }

	@Override
	public void updatetOnCallBack(int position) {
		LogYiFu.e(TAG, "你到底走不走");
		// queryShopQueryAttr(position);

	}

	/**
	 * 删除所有失效商品
	 */
	@Override
	public void deleteAllInvalid() {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mListInValidNew.size(); i++) {
			if (i != mListInValidNew.size() - 1) {
				sb.append(mListInValidNew.get(i).getId()).append(",");
			} else {
				sb.append(mListInValidNew.get(i).getId());
			}
		}
		getDeleteDialog(false, 0, sb.toString(), 2);
		// if (sb.toString().length() > 0 && !("").equals(sb.toString())) {

		// }
	}

	/**
	 * 同时删除多个商品
	 * 
	 * @param context
	 * @param ids
	 * @param flag
	 */
	private void deleteShopCartList(final Context context, final String ids, final int flag) {// flag=1,删除所有有效商品，flag=2，删除所有失效商品
		// TODO Auto-generated method stub
		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, null, R.string.wait) {
			@Override
			protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {
				ReturnInfo r = ComModel.deleteShopCartList(context, YCache.getCacheToken(mContext), ids);
				return r;
			}

			@Override
			protected void onPostExecute(FragmentActivity mContext, ReturnInfo r, Exception e) {

				if (e == null) {
					ShopCartDao dao = new ShopCartDao(mContext);
					if (flag == 2) {
						for (int i = 0; i < mListInValidNew.size(); i++) {
							dao.delete("" + mListInValidNew.get(i).getStock_type_id());
						}
						mListAllNew.removeAll(mListInValidNew);
						mListInValidNew.clear();
					} else {
						List<ShopCart> listTemp = new ArrayList<ShopCart>();
						for (int i = 0; i < mListValidNew.size(); i++) {
							if (spImg.get(i) == 2) {
								listTemp.add(mListValidNew.get(i));
								dao.delete("" + mListValidNew.get(i).getStock_type_id());
							}
						}
						if (shopCartCommonInterface != null) {
							shopCartCommonInterface.getShopCommonCount(dao.queryCartCount(context));
						}
						mListAllNew.removeAll(listTemp);
						mListValidNew.removeAll(listTemp);
						spImg.clear();
						for (int j = 0; j < mListValidNew.size(); j++) {
							spImg.add(1);
						}
						setPrice();
					}
					if (mListValidNew.size() == 0) {
						ll_goto_pay.setBackgroundColor(0xffc5c5c5);
					}
					tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
							mContext.getResources().getDrawable(R.drawable.icon_dapeigou_normal), null, null, null);
//					if(adpter!=null){
//					adpter.notifyDataSetChanged();
//					}
				} else {

				}
			};

			@Override
			protected boolean isHandleException() {
				return true;
			};
		}.execute();
	}

	/**
	 * 单个删除按钮
	 */
	@Override
	public void deleteOnCallBack(int position) {
		if (position < mListValidNew.size()) {// 有效的删除
			if (spImg.get(position) == 1) {// 没有选中，不能删除
				return;
			}
		}
		// deleteShopCart(position);
		getDeleteDialog(true, position, "", 0);
		// if (spImg.get(position) == 2) {
		// AlertDialog.Builder builder = new Builder(mContext);
		// // 自定义一个布局文件
		// View view = View.inflate(mContext, R.layout.payback_esc_apply_dialog,
		// null);
		// TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
		//
		// tv_des.setText("是否确定删除");
		//
		// Button ok = (Button) view.findViewById(R.id.ok);
		// ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
		// Button cancel = (Button) view.findViewById(R.id.cancel);
		//
		// cancel.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // 把这个对话框取消掉
		// dialog.dismiss();
		// }
		// });
		// ok.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// deleteShopCart(position, mIpostion);
		// dialog.dismiss();
		// }
		// });
		// dialog = builder.create();
		// dialog.setView(view, 0, 0, 0, 0);
		// dialog.show();

	}

	// 左边按钮被勾选
	@Override
	public void selectImgOnCallBack(int position) {
		int p = spImg.get(position);
		if (p == 1) {
			spImg.set(position, 2);
		} else {
			spImg.set(position, 1);
		}
		setPrice();
		// 设置全选状态
		setSelectAllStatus();
	}

	// 如果有勾选没有勾选 全选状态取消
	private void setSelectAllStatus() {
		if (spImg.size() == 0) {
			tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dapeigou_normal, 0, 0, 0);
			if (mEditFlag) {
				ll_goto_pay.setBackgroundColor(0xffc5c5c5);
			} else {
				ll_goto_pay.setBackgroundColor(0xffc5c5c5);
			}
		}

		int flag = 0;
		for (int i = 0; i < spImg.size(); i++) {
			int a = spImg.get(i);
			if (a == 2) {
				flag = 1;
				break;
			}
		}

		if (mEditFlag) {
			if (flag == 0) {
				ll_goto_pay.setBackgroundColor(0xffc5c5c5);
			} else {
				ll_goto_pay.setBackgroundColor(0xfffb3b3b);
			}
		} else {
			if (flag == 0) {
				ll_goto_pay.setBackgroundColor(0xffc5c5c5);
			} else {
				ll_goto_pay.setBackgroundColor(0xffff3f8b);
			}
		}
		for (int i = 0; i < spImg.size(); i++) {
			int p = spImg.get(i);
			if (p == 1) {
				tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dapeigou_normal, 0, 0, 0);
				return;
			}
		}
		tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dapeigou_celect, 0, 0, 0);
	}

	// 合计多少钱
	private void setPrice() {
		if (mListValidNew.size() > 0) {
			// int chooseNo = 0;
			double mCountMoney = 0;
			// double mYouhuiMoneyCount = 0;
			double mSaveMoney = 0;
			for (int i = 0; i < mListValidNew.size(); i++) {
				// double mYouhuiMoney = 0;
				// for (int j = 0; j < listShopCarts.get(i).size(); j++) {
				// if (listShopCarts.get(i).size() > 1) {
				// ShopCart shopCart2 = listShopCarts.get(i).get(j);
				// mYouhuiMoney += shopCart2.getShop_se_price() *
				// shopCart2.getShop_num() * 1 / 10;
				// }
				// ShopCart shopCart = listShopCarts.get(i).get(j);
				// if (shopCart.getValid() != 1) {
				// mCountMoney += shopCart.getShop_se_price() *
				// shopCart.getShop_num();
				// mSaveMoney += (shopCart.getShop_price() -
				// shopCart.getShop_se_price()) * shopCart.getShop_num();
				// }
				// }
				// mYouhuiMoneyCount += mYouhuiMoney;
				if (spImg.get(i) == 2) {
					mCountMoney += (mListValidNew.get(i).getShop_se_price()*0.9) * mListValidNew.get(i).getShop_num();
					mSaveMoney += (mListValidNew.get(i).getShop_price() - (mListValidNew.get(i).getShop_se_price())*0.9)
							* mListValidNew.get(i).getShop_num();
				}
			}

			// tv_pay.setText("结算(" + chooseNo + ")");
			// new java.text.DecimalFormat("#0.00").format(saveMoney)
			tv_price.setText("合计 " + new java.text.DecimalFormat("#0.0").format(mCountMoney) + "元");
			tv_no_freight.setText("比专柜节省¥" + new java.text.DecimalFormat("#0.0").format(mSaveMoney)+"元");//
			// 比专柜节省
			// tv_no_freight.setText("比专柜节省￥0.00"+"元");// 比专柜节省

		} else {
			tv_price.setText("合计 " + new java.text.DecimalFormat("#0.0").format(0) + "元");
			tv_no_freight.setText("比专柜节省¥" + new java.text.DecimalFormat("#0.0").format(0)+"元");//
		}

	}

	/*
	 * public void initdata() { int resource = R.layout.listview_shop_cart; if
	 * (mFLagCommom) { adpter = new ShopCartAdpter((FragmentActivity) mContext,
	 * resource, mListCommon2, sp, spImg, 1); } else {
	 * 
	 * queryListShopCart(1); int resource = R.layout.listview_shop_cart; adpter
	 * = new ShopCartAdpter((FragmentActivity) mContext, resource,
	 * listShopCarts, sp, spImg, 1); adpter.setCartOncallback(this); }
	 * listView.setAdapter(adpter); flush(); }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onStart()
	 */

	@Override
	public void onResume() {
		mType=1;
		allFlag = false;
		tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
				mContext.getResources().getDrawable(R.drawable.icon_dapeigou_normal), null, null, null);
		super.onResume();
		if (mEditFlag) {
			mEditButton.setText("完成");
			tv_pay.setText("删除");
		} else {
			mEditButton.setText("编辑");
			tv_pay.setText("结算");
		}
		ll_goto_pay.setBackgroundColor(0xffc5c5c5);
		flush();
		getListShopCart(mContext);
		if("110".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))
				&&!tongJiFirst){
			SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_HOME, "110");
			TongJiUtils.TongJi(mContext, 10+"");
			LogYiFu.e("TongJiNew", 10+"");
		}
		tongJiFirst = false;//首次进来时候 在点击首页下面五个按钮时候统计 所以首次这里不统计避免重复

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// HomeWatcherReceiver.unregisterHomeKeyReceiver(mContext);
		if("110".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))){
			SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_HOME, "110");
			TongJiUtils.TongJi(mContext, 110+"");
			LogYiFu.e("TongJiNew", 110+"");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getWindownPixes();
//		initImageLoader();
		// final ShopCartDao dao = new ShopCartDao(mContext);
		// mListAllNew.clear();
		// mListValidNew.clear();
		// mListInValidNew.clear();
		// mListDao = dao.findAll();
		// if (mListDao != null) {
		// for (int i = mListDao.size() - 1; i >= 0; i--) {
		//
		// if (mListDao.get(i).getIs_meal_flag() == 0) {// 取正价商品的数据
		// mListAllNew.add(mListDao.get(i));
		// }
		//
		// }
		// }

	}

	/**
	 * 获取购物车列表及判断是否下架
	 */
	private void getListShopCart(Context context) {
		final int resource = R.layout.item_list_shopcart;
		final ShopCartDao dao = new ShopCartDao(context);
		final List<ShopCart> listShopCartTempNew = new ArrayList<ShopCart>();
		listShopCartTempNew.clear();
		mListDao = dao.findAll();
		if (mListDao != null) {
			for (int i = mListDao.size() - 1; i >= 0; i--) {

				if (mListDao.get(i).getIs_meal_flag() == 0) {// 取正价商品的数据
					listShopCartTempNew.add(mListDao.get(i));
				}

			}
		}
		if (shopCartCommonInterface != null) {
			shopCartCommonInterface.getShopCommonCount(dao.queryCartCount(context));
		}
		// if (mListAllNew.size() > 0) {
		// llBottom.setVisibility(View.VISIBLE);
		// listView.setVisibility(View.VISIBLE);
		// // layout_nodata_shopcar.setVisibility(View.GONE);
		// } else {
		// llBottom.setVisibility(View.VISIBLE);
		// listView.setVisibility(View.VISIBLE);
		// // layout_nodata_shopcar.setVisibility(View.VISIBLE);
		// }

		if (listShopCartTempNew.size() > 0) {
			new SAsyncTask<Void, Void, List<String>>((FragmentActivity) context, R.string.wait) {
				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
					LoadingDialog.show((FragmentActivity) mContext);
				}

				@Override
				protected List<String> doInBackground(FragmentActivity context, Void... params) throws Exception {
					List<String> list = new ArrayList<String>();
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < listShopCartTempNew.size(); i++) {
						if (i != listShopCartTempNew.size() - 1) {
							sb.append(listShopCartTempNew.get(i).getShop_code()).append(",");
						} else {
							sb.append(listShopCartTempNew.get(i).getShop_code());
						}
					}
					list = ComModel.getInvalidList(context, YCache.getCacheToken(mContext), sb.toString(), "common");
					return list;
				}

				@Override
				protected boolean isHandleException() {
					return true;
				}

				@Override
				protected void onPostExecute(FragmentActivity context, List<String> list, Exception e) {
					super.onPostExecute(context, list, e);
					mListAllNew.clear();
					mListValidNew.clear();
					mListInValidNew.clear();
					mListAllNew.addAll(listShopCartTempNew);
					spImg.clear();
					if (e == null) {
						if (list != null) {
							for (int i = 0; i < list.size(); i++) {
								String shop_code = list.get(i);
								for (int j = 0; j < mListAllNew.size(); j++) {

									if (("" + shop_code).equals("" + mListAllNew.get(j).getShop_code())) {// 该商品已下架
										mListAllNew.get(j).setValid(1);// 设置商品下架
										dao.modify_state(shop_code, 1);// 修改购物车里下架状态
									}
								}

							}
							for (int i = 0; i < mListAllNew.size(); i++) {
								if (mListAllNew.get(i).getValid() == 1) {// 添加到下架列表中
									mListInValidNew.add(mListAllNew.get(i));
								} else {// 添加到有效列表
									mListValidNew.add(mListAllNew.get(i));
								}
							}
							// 重新排序，失效放在后面
							mListAllNew.clear();
							mListAllNew.addAll(mListValidNew);
							mListAllNew.addAll(mListInValidNew);

						} else {
							mListValidNew.addAll(mListAllNew);
						}
						if (shopCartCommonInterface != null) {
							shopCartCommonInterface.getShopCommonCount(dao.queryCartCount(context));
						}
					} else {
						mListValidNew.addAll(mListAllNew);
					}
					initMyLoveData(curPage + "");
					for (int i = 0; i < mListValidNew.size(); i++) {
						dao.modify_state(mListValidNew.get(i).getShop_code(), 0);// 数据库里修改为上架
						spImg.add(1);
					}
					setPrice();
					if (adpter == null) {
						adpter = new ShopCartCommonAdpter((FragmentActivity) mContext, resource, mListAllNew, sp, spImg,
								1, mListValidNew, mListInValidNew, flag_activity);
						listView.setAdapter(adpter);
						adpter.setCartOncallback(ShopCartCommonFragment.this);
					} else {
						adpter.notifyDataSetChanged();
					}
					// if (listShopCarts != null && listShopCarts.size() >
					// 0) {
					// setPrice();
					// } else {
					// tv_price.setText("合计 " + 0.0 + "元");
					// tv_no_freight.setText("比专柜节省¥" + 0.0);
					// }
					LoadingDialog.hide(mContext);
				}

			}.execute();
		} else {
			mListAllNew.clear();
			mListValidNew.clear();
			mListInValidNew.clear();
			mListAllNew.addAll(listShopCartTempNew);
			spImg.clear();
			for (int i = 0; i < mListValidNew.size(); i++) {
				spImg.add(1);
			}
			//TODO:_MODIFY_恢复购物车列表
			if (adpter == null) {
				adpter = new ShopCartCommonAdpter((FragmentActivity) mContext, resource, mListAllNew, sp, spImg, 1,
						mListValidNew, mListInValidNew, flag_activity);
				listView.setAdapter(adpter);
			} else {
				adpter.notifyDataSetChanged();
			}
			adpter.setCartOncallback(ShopCartCommonFragment.this);

			initMyLoveData(curPage + "");
		}

	}

	private void deleteShopCartList(final String ids) {
		// TODO Auto-generated method stub
		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, null, R.string.wait) {
			@Override
			protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {
				ReturnInfo r = ComModel.deleteShopCartList(ShopCartCommonFragment.this.mContext,
						YCache.getCacheToken(mContext), ids);
				return r;
			}

			@Override
			protected void onPostExecute(FragmentActivity mContext, ReturnInfo r, Exception e) {

				if (e != null) {
					// LogYiFu.e("异常 -----", "异常");
				} else {

				}
			};

			@Override
			protected boolean isHandleException() {
				return true;
			};
		}.execute();
	}

	/****
	 * 批量删除购物车信息
	 */
	/*
	 * private void deleteShopCartList() { if (listShopCarts != null &&
	 * listShopCarts.size() > 0) { new SAsyncTask<String, Void, ReturnInfo>(
	 * (FragmentActivity) mContext, null, R.string.wait) {
	 * 
	 * @Override protected ReturnInfo doInBackground(FragmentActivity mContext,
	 * String... params) throws Exception { String ids = ""; StringBuffer buffer
	 * = new StringBuffer(); int length = spImg.size() - 1; for (int i = 0; i <
	 * length + 1; i++) { int p = spImg.get(i);
	 * 
	 * if (p == 2) { String id = listShopCarts.get(i).getId() + "";
	 * buffer.append(id + ","); } ids = buffer.toString(); ids =
	 * ids.substring(0, ids.length() - 1);
	 * 
	 * } ReturnInfo r = ComModel.deleteShopCartList(mContext,
	 * YCache.getCacheToken(mContext), ids); return r; }
	 * 
	 * @Override protected void onPostExecute(FragmentActivity mContext,
	 * ReturnInfo r, Exception e) {
	 * 
	 * if (e != null) { MyLogYiFu.e("异常 -----", "异常"); } else { for (int i =
	 * spImg.size() - 1; i >= 0; i--) { if (spImg.get(i) == 2) {
	 * listShopCarts.remove(i); spImg.remove(i); sp.remove(i);
	 * 
	 * } }
	 * 
	 * adpter.notifyDataSetChanged(); // setPrice(); Toast.makeText(mContext,
	 * "删除成功", Toast.LENGTH_SHORT) .show(); } };
	 * 
	 * @Override protected boolean isHandleException() { return true; };
	 * }.execute(); }
	 * 
	 * }
	 */

	// 已没用，待用
	/*
		*//***
			 * 添加我的喜欢
			 * 
			 */

	private void addShopLike() {
		if (mListValidNew != null && mListValidNew.size() > 0) {
			StringBuffer bfAdd = new StringBuffer();
			for (int i = 0; i < spImg.size(); i++) {
				int f = spImg.get(i);
				if (f == 2) {
					ShopCart cart = mListValidNew.get(i);
					// String like_id = cart.getLike_id();
					// if (like_id.equals("-1")) {
					String shop_code = cart.getShop_code();
					bfAdd.append(shop_code + ",");
					// }
				}
			}

			 String shop_code = bfAdd.toString();
			if (!TextUtils.isEmpty(shop_code)) {
				shop_code = shop_code.substring(0, shop_code.length() - 1);// 去掉最后一个逗号
				final String shop_code_temp=shop_code;
				new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, R.string.wait) {

					@Override
					protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {

						return ComModel.addLikeShop(mContext, YCache.getCacheToken(mContext), params[0]);
					}

					@Override
					protected boolean isHandleException() {
						return true;
					}

					@Override
					protected void onPostExecute(FragmentActivity mContext, ReturnInfo result, Exception e) {

						if (null == e) {
							if (null != result) {
								String str = SharedPreferencesUtil.getStringData(ShopCartCommonFragment.this.mContext,
										"" + YCache.getCacheUser(ShopCartCommonFragment.this.mContext).getUser_id(), "");
								StringBuffer sb = new StringBuffer(str);
								sb.append(shop_code_temp);
								SharedPreferencesUtil.saveStringData(ShopCartCommonFragment.this.mContext,
										"" + YCache.getCacheUser(ShopCartCommonFragment.this.mContext).getUser_id(), sb.toString());
								Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(mContext, "添加失败", Toast.LENGTH_SHORT).show();
							}
						}
						super.onPostExecute(mContext, result, e);
					}

				}.execute(shop_code);
			}
		}
	}

	/*
	 * 
	 * }
	 * 
	 * }
	 * 
	 * @Override public void onItemClickLintener(int position) { Intent intent =
	 * new Intent(mContext, ShopDetailsActivity.class);
	 * intent.putExtra("height", statusBarHeight); String p_code =
	 * listShopCarts.get(position).getP_code(); if (TextUtils.isEmpty(p_code)) {
	 * intent.putExtra("code", listShopCarts.get(position).getShop_code()); }
	 * else { intent.putExtra("code", p_code); intent.putExtra("isMeal", true);
	 * }
	 * 
	 * // startActivity(intent); ((Activity)
	 * mContext).startActivityForResult(intent, 100); ((Activity)
	 * mContext).overridePendingTransition(R.anim.slide_left_in,
	 * R.anim.slide_left_out);
	 * 
	 * }
	 */

	/*
	 * // 设置全选 private void setAllSelect(boolean isSelectedAll) { if
	 * (isSelectedAll) { // 全选 选中状态 cPrice = 0; int goodsNum = 0; int saveMoney
	 * = 0; for (int i = 0; i < spImg.size(); i++) { spImg.set(i, 2);
	 * 
	 * if (listShopCarts.get(i).getValid() == 0) {
	 * tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
	 * mContext.getResources().getDrawable( R.drawable.icon_dapeigou_celect),
	 * null, null, null); ShopCart shopCart = listShopCarts.get(i); cPrice =
	 * cPrice + shopCart.getShop_se_price() shopCart.getShop_num(); goodsNum +=
	 * shopCart.getShop_num(); saveMoney += shopCart.getShop_num()
	 * (shopCart.getShop_price() - shopCart .getShop_se_price()); } } String
	 * price = new java.text.DecimalFormat("#0.0").format(cPrice); String
	 * savemoneyStr = new java.text.DecimalFormat("#0.0") .format(saveMoney);
	 * tv_price.setText("合计 " + price + "元"); tv_pay.setText("结算(" + goodsNum +
	 * ")"); tv_no_freight.setText("比专柜节省￥" + savemoneyStr);
	 * 
	 * } else { for (int i = 0; i < spImg.size(); i++) { spImg.set(i, 1);
	 * 
	 * tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
	 * mContext.getResources().getDrawable( R.drawable.icon_dapeigou_normal),
	 * null, null, null); } cPrice = 0; tv_price.setText("合计 " + cPrice + "元");
	 * tv_pay.setText("结算(0)"); tv_no_freight.setText("比专柜节省0.00元"); }
	 * adpter.notifyDataSetChanged(); }
	 */

	// 设置全选
	private void setAllSelect(boolean isSelectedAll) {
		if (isSelectedAll) { // 全选 选中状态
			cPrice = 0;
			int goodsNum = 0;
			int saveMoney = 0;
			if (spImg.size() > 0 && mEditFlag) {
				ll_goto_pay.setBackgroundColor(0xfffb3b3b);
			} else if (mEditFlag) {
				ll_goto_pay.setBackgroundColor(0xffc5c5c5);
			} else if (spImg.size() > 0 && !mEditFlag) {
				ll_goto_pay.setBackgroundColor(0xffff3f8b);
			} else if (!mEditFlag) {
				ll_goto_pay.setBackgroundColor(0xffc5c5c5);
			}
			for (int i = 0; i < spImg.size(); i++) {
				spImg.set(i, 2);
				tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
						mContext.getResources().getDrawable(R.drawable.icon_dapeigou_celect), null, null, null);
				if(mListValidNew.size()>0){
				ShopCart shopCart = mListValidNew.get(i);
				cPrice = cPrice + (shopCart.getShop_se_price()*0.9) * shopCart.getShop_num();
				goodsNum += shopCart.getShop_num();
				saveMoney += shopCart.getShop_num() * (shopCart.getShop_price() - shopCart.getShop_se_price()*0.9);
				}
			}
			String price = new java.text.DecimalFormat("#0.0").format(cPrice);
			String savemoneyStr = new java.text.DecimalFormat("#0.0").format(saveMoney);
			if (!mEditFlag) {
				tv_price.setText("合计 " + price + "元");
				tv_pay.setText("结算(" + goodsNum + ")");
				tv_no_freight.setText("比专柜节省¥" + savemoneyStr+"元");
			}

		} else {
			// if (mEditFlag) {
			ll_goto_pay.setBackgroundColor(0xffc5c5c5);
			// }
			for (int i = 0; i < spImg.size(); i++) {
				spImg.set(i, 1);

				tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
						mContext.getResources().getDrawable(R.drawable.icon_dapeigou_normal), null, null, null);
			}
			cPrice = 0;
			if (!mEditFlag) {
				tv_price.setText("合计 " + cPrice + "元");
				tv_pay.setText("结算(0)");
				tv_no_freight.setText("比专柜节省￥0.0"+"元");
			}
		}
//		if(adpter!=null){
//		adpter.notifyDataSetChanged();
//		}
	}
	// 已经没使用了
	/***
	 * 查找商品属性
	 */
	// private void queryShopQueryAttr(final int position) {
	// if (listShopCarts != null && listShopCarts.size() > 0) {
	// ShopCart cart = listShopCarts.get(position);
	// new SAsyncTask<String, Void, List<StockType>>(
	// (FragmentActivity) mContext, null, R.string.wait) {
	// @Override
	// protected List<StockType> doInBackground(
	// FragmentActivity mContext, String... params)
	// throws Exception {
	//
	// List<StockType> list = ComModel.queryShop_Stokect(mContext,
	// params[0]);
	// return list;
	// }
	//
	// @Override
	// protected void onPostExecute(FragmentActivity mContext,
	// List<StockType> list, Exception e) {
	//
	// if (e != null) {// 查询异常
	// Toast.makeText(mContext, "连接超时，请重试", Toast.LENGTH_LONG)
	// .show();
	//
	// } else {// 查询商品详情成功，刷新界面
	// if (list != null && list.size() > 0) {
	// showPopWindow(position, list);
	// }
	// }
	//
	// };
	//
	// @Override
	// protected boolean isHandleException() {
	// return true;
	// };
	// }.execute(cart.getShop_code());
	//
	// }
	//
	// }
	/****
	 * 弹出底部对话框
	 * 
	 * @param i
	 */

	/*
	 * private void showPopWindow(final int position, List<StockType> list,int
	 * mIposition) { if (listShopCarts != null && listShopCarts.size() > 0) {
	 * final ShopCartDialog dlg = new ShopCartDialog(mContext,
	 * R.style.DialogStyle, height, position, listShopCarts, list); Window
	 * window = dlg.getWindow(); window.setGravity(Gravity.BOTTOM);
	 * window.setWindowAnimations(R.style.dlg_down_to_top); dlg.show();
	 * 
	 * dlg.cartDialogOncallBack = new ShopCartDialog.ShopCartDialogOncallBack()
	 * {
	 * 
	 * @Override public void updateOncallBack(double shop_se_price, String
	 * color, String size, int shop_num, String def_pic, String stock_type_id,
	 * View v) { dlg.dismiss(); updateShopCartType(shop_se_price + "", color,
	 * size, shop_num, def_pic, stock_type_id, position, v);
	 * 
	 * } };
	 * 
	 * }
	 * 
	 * }
	 */
	// 留着待用
	/**
	 * 修改购物车的商品数量,属性，尺码等
	 */
	/*
	 * private void updateShopCartType(final String shop_se_price, final String
	 * color, final String size, final int shop_num, String def_pic, String
	 * stock_type_id, int p, final View v) {
	 * 
	 * final ShopCart cart = listShopCarts.get(p); if (cart == null) return; new
	 * SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, null,
	 * R.string.wait) {
	 * 
	 * @Override protected ReturnInfo doInBackground(FragmentActivity mContext,
	 * String... params) throws Exception { ReturnInfo r =
	 * ComModel.updateShopCartType(mContext, YCache.getCacheToken(mContext),
	 * params[0], params[1], params[2], params[3], params[4], params[5],
	 * params[6]); return r; }
	 * 
	 * @Override protected void onPostExecute(FragmentActivity mContext,
	 * ReturnInfo r, Exception e) {
	 * 
	 * if (e != null) { MyLogYiFu.e("异常 -----", "异常" + e);
	 * 
	 * } else {// 修改成功 cart.setShop_num(shop_num);
	 * cart.setShop_se_price(Double.parseDouble(shop_se_price));
	 * cart.setShop_price(cart.getShop_price()); cart.setColor(color);
	 * cart.setSize(size); adpter.notifyDataSetChanged(); setPrice();
	 * 
	 * } };
	 * 
	 * @Override protected boolean isHandleException() { return true; };
	 * }.execute(cart.getId() + "", shop_se_price, color, size, shop_num + "",
	 * def_pic, stock_type_id); }
	 */
	//
	private void yunYunTongJi(final String shop_code, final int type, final int tab_type) {
		new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {

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
				if (e == null) {
				}

			}

		}.execute();
	}

	public void initMyLoveData(String index) {

		final String s_code = YCache.getCacheStore(getActivity()).getS_code();
		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(getActivity(), 0) {

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
				super.onPostExecute(context, result);

				if (e != null) {// 查询异常
					listView.onRefreshComplete();
				} else {
					listView.onRefreshComplete();
					if (mType == 1) {
						if (result == null || result.isEmpty()) {
							// circle_nodata.setVisibility(View.VISIBLE);
							// w_list_view.setVisibility(View.GONE);
						} else {
							// circle_nodata.setVisibility(View.GONE);
							// w_list_view.setVisibility(View.VISIBLE);
						}
//						adpter.addItemTop(result);
					} else if (mType == 2) {
						if (result == null || result.isEmpty()) {
							// Toast.makeText(context, "到底了",
							// Toast.LENGTH_SHORT).show();
							ToastUtil.showShortText(context, "没有更多商品了");
						} else {
							// listView.smoothScrollBy(DP2SPUtil.dp2px(mContext,
							// 44), 300);
//							adpter.addItemLast(result);
							ListView refreshableView = listView.getRefreshableView();
							refreshableView.smoothScrollBy(DP2SPUtil.dp2px(mContext, 44), 300);
						}
					}
				}
//				if(adpter!=null){
//				adpter.notifyDataSetChanged();
//				}
			}

			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
					throws Exception {

				// return ComModel.queryMyStepsList(context, params[0], s_code,
				// "2", String.valueOf(pageSize));// 这个是我的足迹里面的接口,为了测试数据用
				return ComModel2.getMyLovewList(context, "" + curPage, "" + pageSize);// 获取真正我的喜欢列表

			}

		}.execute(index);
	}

	@Override
	public void modifyColorSize(int postion) {
		queryShopDetails(postion);

	}

//	private DisplayImageOptions options;
//
//	private void initImageLoader() {
//
//		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.image_default)
//				.showImageForEmptyUri(R.drawable.ic_empty).cacheInMemory(false).bitmapConfig(Bitmap.Config.RGB_565)
//				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).considerExifParams(true)
//				// .displayer(new FadeInBitmapDisplayer(35))
//				.build();
//
//	}

	private void getWindownPixes() {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
	}

	Shop mShop;

	/***
	 * 查找商品属性
	 */
	public void queryShopQueryAttr(final ShopCart shopCart, final int i) {
		// clickFlag = false;
		if (shopCart != null) {
			// List<StockType> list = shop.getList_stock_type();
			// if (list != null && list.size() > 0) {
			// showPopWindow(i);
			// } else {
			if (mShop != null) {
				mShop = null;
			}
			mShop = new Shop();
			mShop.setShop_code(shopCart.getShop_code());
			mShop.setShop_name(shopCart.getShop_name());
			mShop.setShop_price(shopCart.getShop_price());
			mShop.setShop_se_price(shopCart.getShop_se_price());
			mShop.setDef_pic(shopCart.getDef_pic());
			new SAsyncTask<String, Void, Shop>((FragmentActivity) mContext, null, R.string.wait) {
				@Override
				protected Shop doInBackground(FragmentActivity context, String... params) throws Exception {
					return ComModel.queryShopQueryAttr(mContext, mShop, params[0]);
				}

				@Override
				protected void onPostExecute(FragmentActivity context, Shop shop, Exception e) {

					if (e != null) {// 查询异常
						Toast.makeText(mContext, "连接超时，请重试", Toast.LENGTH_LONG).show();
					} else {// 查询商品详情成功，刷新界面
						if (shop != null) {
							LoadingDialog.hide(mContext);
							mShop = shop;
							showPopWindow(i, shopCart, mShop);
						}
					}

				};

				@Override
				protected boolean isHandleException() {
					return true;
				};
			}.execute("false");
			// }

		} else {
			ToastUtil.showShortText(mContext, "无效操作");
		}
	}

	private GoodsEntity entity;
//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
//
//	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
//
//		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
//
//		@Override
//		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//			if (loadedImage != null) {
//				ImageView imageView = (ImageView) view;
//				boolean firstDisplay = !displayedImages.contains(imageUri);
//				if (firstDisplay) {
//					FadeInBitmapDisplayer.animate(imageView, 500);
//					displayedImages.add(imageUri);
//				}
//			}
//		}
//	}

	/****
	 * 弹出底部对话框
	 * 
	 * @param i
	 */
	private void showPopWindow(int i, final ShopCart shopCart, Shop mShop) {
		if (mShop != null && !((Activity) mContext).isFinishing()) {
			final ShopDetailsDialog dlg;
			dlg = new ShopDetailsDialog(mContext, R.style.DialogStyle, width, height, mShop
					, 2, false, shopCart.getColor(), shopCart.getSize(),
					"" + shopCart.getShop_num());
			Window window = dlg.getWindow();
			window.setGravity(Gravity.BOTTOM);
			window.setWindowAnimations(R.style.dlg_down_to_top);
			dlg.show();
			dlg.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface arg0) {
					// TODO Auto-generated method stub
				}
			});
			dlg.callBackShopCart = new ShopDetailsDialog.OnCallBackShopCart() {

				@Override
				public void callBackChoose(int type, final String size, final String color, double price,
						final int shop_num, final int stock_type_id, int stock, final String pic, int supp_id,
						double kickback, int original_price, View v) {

					// 调接口通知后台改
					new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mContext, null,
							R.string.wait) {

						@Override
						protected void onPreExecute() {
							// TODO Auto-generated method stub
							super.onPreExecute();
							LoadingDialog.show((FragmentActivity) mContext);
						}

						@Override
						protected HashMap<String, String> doInBackground(FragmentActivity mContext, String... params)
								throws Exception {
							HashMap<String, String> map;

							map = ComModel2.modifyColorSize(mContext, "" + shopCart.getId(), color, size, "" + shop_num,
									"" + stock_type_id,""+pic);
							return map;
						}

						@Override
						protected void onPostExecute(final FragmentActivity context, HashMap<String, String> map,
								Exception e) {
							LoadingDialog.hide(mContext);
							if (e == null) {
								if (map != null && "1".equals(map.get("status"))) {
									dlg.dismiss();
									ShopCartDao dao = new ShopCartDao(mContext);
									dao.modifyColorSize("" + shopCart.getId(), shop_num, color, size,pic);
									shopCart.setColor(color);
									shopCart.setSize(size);
									shopCart.setShop_num(shop_num);
									shopCart.setDef_pic(pic);
//									if(adpter!=null){
//									adpter.notifyDataSetChanged();
//									}
								}
							}
						};

						@Override
						protected boolean isHandleException() {
							return true;
						};
					}.execute();
				}
			};
		}
	}

	public void getDeleteDialog(final boolean single, final int position, final String ids, final int flag) {// 删除弹窗
		LayoutInflater mInflater = LayoutInflater.from(mContext);
		final Dialog deleteDialog = new Dialog(mContext, R.style.invate_dialog_style);
		View view = mInflater.inflate(R.layout.dialog_delete_shopcart, null);
		Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				deleteDialog.dismiss();
				if (single) {
					deleteShopCart(position);
				} else {
					deleteShopCartList(mContext, ids, flag);
				}

			}
		});
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				deleteDialog.dismiss();
			}
		});
		deleteDialog.setCanceledOnTouchOutside(false);
		deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		deleteDialog.show();

	}

	/***
	 * 查询普通商品详情页(为了获得属性列表)
	 */
	private void queryShopDetails(final int position) {

		// attrDateStr = getSharedPreferences(Pref.sync, Context.MODE_PRIVATE)
		// .getString(Pref.sync_attr_date, "");
		new SAsyncTask<String, Void, HashMap<String, Object>>((FragmentActivity) mContext, null, R.string.wait) {

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				LoadingDialog.show((FragmentActivity) mContext);
			}

			@Override
			protected HashMap<String, Object> doInBackground(FragmentActivity mContext, String... params)
					throws Exception {
				Shop shop;
				HashMap<String, Object> map;

				map = ComModel.queryShopDetails2(mContext, mListValidNew.get(position).getShop_code(), "");
				return map;
			}

			@Override
			protected void onPostExecute(final FragmentActivity context, HashMap<String, Object> map, Exception e) {
				if (e == null) {
					queryShopQueryAttr(mListValidNew.get(position), 1);// 1,代表加入购物车
				} else {
					LoadingDialog.hide(mContext);
				}
			};

			@Override
			protected boolean isHandleException() {
				return true;
			};
		}.execute();
	}
}
