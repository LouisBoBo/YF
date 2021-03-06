//package com.yssj.ui.activity;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.yssj.YConstance.Pref;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.LoadingDialog;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.entity.ShopCart;
//import com.yssj.entity.StockType;
//import com.yssj.model.ComModel;
//import com.yssj.ui.activity.ShopCartCommonFragment.MyTimerTask;
//import com.yssj.ui.activity.shopdetails.ShopCartDialog;
//import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
//import com.yssj.ui.activity.shopdetails.StockBean;
//import com.yssj.ui.activity.shopdetails.SubmitMultiShopSpecialActivty;
//import com.yssj.ui.adpter.ShopCartAdpter;
//import com.yssj.ui.adpter.ShopCartCommonAdpter;
//import com.yssj.ui.adpter.ShopCartAdpter.ShopCartAdpterInterface;
//import com.yssj.utils.LogYiFu;
//import com.yssj.utils.SharedPreferencesUtil;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.YCache;
//import com.yssj.utils.YunYingTongJi;
//import com.yssj.utils.sqlite.ShopCartDao;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class ShopCartSpecialFragment extends Fragment implements OnClickListener, ShopCartAdpterInterface {
//
//	private LinearLayout lay_cartpay, ll_goto_pay;
//	private TextView tv_allchoose, tv_price, tv_no_freight, tv_time, tv_pay;
//	private List<ShopCart> mListCommon;
//	private int height;
//	public static List<ShopCart> listShopCarts = new ArrayList<ShopCart>();
//	private List<ShopCart> listShopCartsInValid = new ArrayList<ShopCart>();
//	public List<ShopCart> mListDao = new ArrayList<ShopCart>();
//	// private List<ShopCart> listShopCartsAll = new ArrayList<ShopCart>();
//	private static int mInvalidPosition = -1;// ?????????????????????
//	private static ArrayList<Integer> sp = new ArrayList<Integer>();
//	private static ArrayList<Integer> spImg = new ArrayList<Integer>(); // ???????????????????????????
//	private boolean mIsLoding = false; // 1????????????
//	private long mDeadTime = 0;
//	private long mSysTime = 0; // 2?????????
//	private Boolean mPayFlag = false;
//	private ShopCartAdpter adpter;
//	private PullToRefreshListView listView;
//	private int statusBarHeight;
//
//	private TextView tv_edit_cart;
//
//	private double cPrice;// ????????????????????????
//
//	private boolean refeshFlag = true, allFlag = false;
//
//	private LinearLayout layout_nodata_shopcar;
//	private Button btn_to_shop;
//
//	private LinearLayout llBottom;
//	private Boolean mFlag = false;
//	private Context mContext;
//
//	private long recLen = 30 * 1000 * 60;
//	boolean isShowShopCart = false;
//	Timer timer = new Timer();
//	private MyTimerTask mTask;
//	private String flag_activity = "0";
//
//	public interface ShopCartSpecialInterface {
//		void getShopSpecialCount(int count, int special);
//	}
//
//	// public void setCartOncallback(ShopCartNewNewActivity f) {
//	// this.shopCartCommonInterface = f;
//	// }
//	private ShopCartSpecialInterface shopCartSpecialInterface;
//
//	public ShopCartSpecialFragment(ShopCartNewNewFragment f) {
//		this.shopCartSpecialInterface = f;
//		flag_activity = "0";
//	}
//
//	public ShopCartSpecialFragment(ShopCartNewNewActivity f) {
//		this.shopCartSpecialInterface = f;
//		flag_activity = "1";
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		// sp = new ArrayList<Integer>();
//		// spImg = new ArrayList<Integer>();
//		// listShopCarts = new ArrayList<ShopCart>();
//		mContext = getActivity();
//		View view = View.inflate(mContext, R.layout.activity_shopcart, null);
//		listView = (PullToRefreshListView) view.findViewById(R.id.listview_shopcart);
//		// flush();
//		lay_cartpay = (LinearLayout) view.findViewById(R.id.lay_cartpay);
//		// lay_cartremove = (LinearLayout) v.findViewById(R.id.lay_cartremove);
//		tv_price = (TextView) view.findViewById(R.id.tv_price);
//		tv_no_freight = (TextView) view.findViewById(R.id.tv_no_freight);
//		tv_pay = (TextView) view.findViewById(R.id.tv_pay);
//		ll_goto_pay = (LinearLayout) view.findViewById(R.id.ll_goto_pay);
//		tv_time = (TextView) view.findViewById(R.id.tv_time);
//		ll_goto_pay.setOnClickListener(this);
//		// tv_remove = (TextView) v.findViewById(R.id.tv_remove);
//		// tv_remove.setOnClickListener(this);
//		// tv_delete_all = (TextView) v.findViewById(R.id.tv_delete_all);
//		// tv_delete_all.setOnClickListener(this);
//		tv_allchoose = (TextView) view.findViewById(R.id.tv_allchoose);
//		tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
//				mContext.getResources().getDrawable(R.drawable.icon_dapeigou_normal), null, null, null);
//		tv_allchoose.setOnClickListener(this);
//
//		// int resource = R.layout.listview_shop_cart;
//		mListCommon = new ArrayList<ShopCart>();
//		// queryListShopCart(1);
//		// adpter = new ShopCartAdpter(mContext.getApplicationmContext(),
//		// resource,
//		// listShopCarts, sp, spImg);
//		// adpter.setCartOncallback(this);
//		//
//		// listView.setAdapter(adpter);
//		// listView.setRefreshing();
//		layout_nodata_shopcar = (LinearLayout) view.findViewById(R.id.layout_nodata_shopcar);
//		llBottom = (LinearLayout) view.findViewById(R.id.lay_bottom);
//		// if (listShopCarts!=null&&listShopCarts.size() > 0) {
//		// llBottom.setVisibility(View.VISIBLE);
//		// }else{
//		// layout_nodata_shopcar.setVisibility(View.VISIBLE);
//		// listView.setVisibility(View.GONE);
//		// }
//
//		btn_to_shop = (Button) view.findViewById(R.id.btn_to_shop);
//		btn_to_shop.setOnClickListener(this);
//		mDeadTime = Long.parseLong(SharedPreferencesUtil.getStringData(mContext, Pref.SHOPCART_MEAL_TIME, "0"));
//		mSysTime = System.currentTimeMillis();
//		recLen = mDeadTime - mSysTime;
//		if (mTask != null) {
//			mTask.cancel();
//			mTask = new MyTimerTask();
//		} else {
//			mTask = new MyTimerTask();
//		}
//
//		if (timer == null) {
//			timer = new Timer();
//		}
//		timer.schedule(mTask, 0, 1000); // timeTask
//
//		flush();
//
//		return view;
//	}
//
//	/**
//	 * ????????????
//	 */
//	private void editorAllGoods(TextView tv_edit_cart) {
//		String strTitle = tv_edit_cart.getText().toString();
//		if (strTitle.equals("????????????")) {
//			tv_edit_cart.setText("??????");
//			lay_cartpay.setVisibility(View.GONE);
//			// lay_cartremove.setVisibility(View.VISIBLE);
//			refeshFlag = false;
//			for (int i = 0; i < sp.size(); i++) {
//				sp.set(i, 3);
//			}
//
//		} else {
//			tv_edit_cart.setText("????????????");
//			lay_cartpay.setVisibility(View.VISIBLE);
//			// lay_cartremove.setVisibility(View.GONE);
//			refeshFlag = true;
//			for (int i = 0; i < sp.size(); i++) {
//				sp.set(i, 1);
//			}
//		}
//		adpter.notifyDataSetChanged();
//
//	}
//
//	private int curPage = 1;
//	private int pageSize = 10;
//	private AlertDialog dialog;
//	private String TAG = "ShopCartSpecialFragment";
//
//	/****
//	 * ???????????????????????????
//	 */
//	// public void queryListShopCart(final int type) {
//	// new SAsyncTask<String, Void, List<ShopCart>>((FragmentActivity) mContext,
//	// R.string.wait) {
//	//
//	// @Override
//	// protected void onPreExecute() {
//	// // TODO Auto-generated method stub
//	// super.onPreExecute();
//	// LoadingDialog.show(getActivity());
//	// }
//	//
//	// @Override
//	// protected List<ShopCart> doInBackground(FragmentActivity mContext,
//	// String... params) throws Exception {
//	// List<ShopCart> list = ComModel.queryShopCartsSpecial(mContext,
//	// YCache.getCacheToken(mContext),
//	// params[0], params[1], 0 + "");
//	//
//	// listShopCartsInValid.clear();
//	// // if (list.size() < 10 && !mIsLoding) {
//	// // LogYiFu.e(TAG, list.size() + "");
//	// // mIsLoding = true;
//	// // listShopCartsInValid =
//	// // ComModel.queryShopCartsSpecial(mContext,
//	// // YCache.getCacheToken(mContext),
//	// // 1 + "", 10 + "", 1 + "");
//	// // LogYiFu.e(TAG, listShopCartsInValid.size() +
//	// // "listShopCartsInValid");
//	// // }
//	// return list;
//	// }
//	//
//	// @Override
//	// protected void onPostExecute(FragmentActivity mContext, List<ShopCart>
//	// list, Exception e) {
//	//
//	// if (e != null) {// ????????????
//	//
//	// LogYiFu.e("?????? -----", "??????");
//	// layout_nodata_shopcar.setVisibility(View.VISIBLE);
//	// listView.setVisibility(View.GONE);
//	// llBottom.setVisibility(View.GONE);
//	// listView.onRefreshComplete();
//	//
//	// } else {// ???????????????????????????????????????
//	// if (curPage == 1 && list != null && list.size() > 0) {
//	// ShopCart shopCart = list.get(0);
//	// mInvalidPosition = list.size();
//	// } else if (curPage == 1) {
//	// mInvalidPosition = 0;
//	// }
//	// if (mTask != null) {
//	// mTask.cancel();
//	// mTask = new MyTimerTask();
//	// } else {
//	// mTask = new MyTimerTask();
//	// }
//	// if (curPage == 1 && list != null && list.size() > 0) {
//	// ll_goto_pay.setClickable(true);
//	// ll_goto_pay.setBackgroundColor(Color.parseColor("#ff3f8b"));
//	// ShopCart shopCart = list.get(0);
//	// mDeadTime = shopCart.getP_deadline();
//	// mSysTime = shopCart.getS_time();
//	// // recLen = mDeadTime - mSysTime;
//	// LogYiFu.e(TAG, "recLen" + recLen);
//	// if (timer == null) {
//	// timer = new Timer();
//	// }
//	// timer.schedule(mTask, 0, 1000); // timeTask
//	//
//	// } else if (curPage == 1) {
//	// // tv_time.setText("???????????????");
//	// // ll_goto_pay.setClickable(false);
//	// // ll_goto_pay.setBackgroundColor(Color.parseColor("#cecece"));
//	//
//	// }
//	// if (curPage == 1 && list != null && list.size() == 0 &&
//	// listShopCartsInValid != null
//	// && listShopCartsInValid.size() == 0) {
//	// LogYiFu.e("TAG", "?????????????????????");
//	// layout_nodata_shopcar.setVisibility(View.VISIBLE);
//	// listView.setVisibility(View.GONE);
//	// llBottom.setVisibility(View.GONE);
//	// } else {
//	// layout_nodata_shopcar.setVisibility(View.GONE);
//	// listView.setVisibility(View.VISIBLE);
//	// if (mInvalidPosition > 0) {
//	// llBottom.setVisibility(View.VISIBLE);
//	// } else {
//	// llBottom.setVisibility(View.GONE);
//	// }
//	// }
//	// if (list != null || listShopCartsInValid != null) {
//	// LogYiFu.i("TAG", "list= " + list.toString());
//	// mFlag = true;
//	// // System.out.println("type:" + type);
//	// if (type == 1) { // ????????????
//	//
//	// listShopCarts.clear();
//	// if (list != null) {
//	// listShopCarts.addAll(list);
//	// }
//	// // mInvalidPosition = listShopCarts.size();
//	// if (listShopCartsInValid != null && listShopCartsInValid.size() > 0) {
//	//
//	// listShopCarts.addAll(listShopCartsInValid);
//	// }
//	// sp.clear();
//	// spImg.clear();
//	//
//	// } else if (type == 2 || type == 0) { // ????????????
//	// if (list != null) {
//	// listShopCarts.addAll(list);
//	// }
//	// // mInvalidPosition = listShopCarts.size();
//	// if (listShopCartsInValid != null && listShopCartsInValid.size() > 0) {
//	//
//	// listShopCarts.addAll(listShopCartsInValid);
//	// }
//	// }
//	// LogYiFu.e(TAG, mInvalidPosition + "");
//	// // if (listShopCartsInValid != null
//	// // && listShopCartsInValid.size() > 0) {
//	// // for (int i = 0; i < list.size()
//	// // + listShopCartsInValid.size(); i++) {
//	// // sp.add(1);
//	// // }
//	// // for (int i = 0; i < list.size()
//	// // + listShopCartsInValid.size(); i++) {
//	// // spImg.add(1);
//	// // }
//	// // } else {
//	// if (list != null) {
//	// for (int i = 0; i < list.size(); i++) {
//	// sp.add(1);
//	// }
//	// for (int i = 0; i < list.size(); i++) {
//	// spImg.add(1);
//	// }
//	// }
//	//
//	// if (shopCartSpecialInterface != null) {
//	// if (list != null) {
//	// // shopCartSpecialInterface.getShopSpecialCount(listShopCarts.size());
//	// }
//	//
//	// }
//	// // }
//	//
//	// }
//	// }
//	// if (type == 1 || type == 2) {
//	// listView.onRefreshComplete();
//	// }
//	// // setPrice();
//	// tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dapeigou_normal,
//	// 0, 0, 0);
//	// tv_price.setText("?????? " + 0.0 + "???");
//	// tv_pay.setText("??????(" + 0 + ")");
//	// tv_no_freight.setText("???????????????" + 0.0 + "???");
//	// allFlag = false;
//	// if (curPage == 1) {
//	// int resource = R.layout.listview_shop_cart_special;
//	// adpter = new ShopCartAdpter(mContext, resource, listShopCarts, sp, spImg,
//	// 2, mInvalidPosition);
//	//
//	// adpter.setCartOncallback(ShopCartSpecialFragment.this);
//	// // llBottom.setVisibility(View.VISIBLE);
//	// listView.setAdapter(adpter);
//	//
//	// }
//	// adpter.notifyDataSetChanged();
//	// };
//	//
//	// @Override
//	// protected boolean isHandleException() {
//	// return true;
//	// };
//	// }.execute(String.valueOf(curPage), String.valueOf(pageSize));
//	// }
//
//	@Override
//	public void newJoinOnCallBack(int position) {
//		// TODO Auto-generated method stub
//		new_join(mContext, YCache.getCacheToken(mContext), listShopCarts.get(position).getP_code(), "isMeal",
//				listShopCarts.get(position));
//	}
//
//	/**
//	 * ????????????
//	 * 
//	 * @param context
//	 * @param token
//	 * @param code
//	 * @param isWhat
//	 */
//	public void new_join(Context context, final String token, final String code, final String isWhat,
//			final ShopCart shopCart) {
//
//		final ShopCartDao dao = new ShopCartDao(context);
//		if (dao.queryCartSpecialCount(context) + shopCart.getShop_num() > 20) {
//			ToastUtil.showShortText(context, "???????????????????????????20???????????????");
//			return;
//		}
//
//		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) context, R.string.wait) {
//
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
//				ReturnInfo returnInfo = new ReturnInfo();
//				returnInfo = ComModel.newJoinShopCartsCommon(context, token, code, isWhat);
//				return returnInfo;
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (e == null && result != null && "1".equals(result.getStatus())) {
//					ToastUtil.showShortText(context, "????????????");
//					SharedPreferencesUtil.saveBooleanData(context, "undo_view4", true);// ????????????????????????????????????????????????
//					List<ShopCart> list = dao.findAll();
//					boolean flag = false;
//					if (list != null) {
//						for (int i = 0; i < list.size(); i++) {
//							if (("" + list.get(i).getP_s_t_id()).equals("" + shopCart.getP_s_t_id())) {
//								flag = true;
//								break;
//							}
//						}
//						if (flag) {// genggai
//							dao.p_modify("" + shopCart.getP_s_t_id(), 2);
//						} else {// tianjia
//							if (timer != null) {
//								recLen = 30 * 60 * 1000;
//							} else {
//								recLen = 30 * 60 * 1000;
//								timer = new Timer();
//								if (mTask != null) {
//									mTask = null;
//								}
//								mTask = new MyTimerTask();
//								timer.schedule(mTask, 0, 1000);
//							}
//							SharedPreferencesUtil.saveStringData(context, Pref.SHOPCART_MEAL_TIME,
//									"" + (System.currentTimeMillis() + 30 * 60 * 1000));
//							dao.add(null, null, null, shopCart.getShop_num(), 0, (String) shopCart.getDef_pic(), 0,
//									null, null, "" + shopCart.getShop_price(), "" + shopCart.getShop_se_price(),
//									shopCart.getSupp_id() + "", "" + 0, "" + 0, 1, shopCart.getP_code(),
//									"" + shopCart.getPostage(), shopCart.getP_s_t_id(), shopCart.getP_shop_code(),
//									shopCart.getP_color(), shopCart.getId(),
//									shopCart.getP_s_t_id().split(",").length > 1 ? "????????????" : "????????????", 0);
//						}
//					} else {// tian jia
//						if (timer != null) {
//							recLen = 30 * 60 * 1000;
//						} else {
//							recLen = 30 * 60 * 1000;
//							timer = new Timer();
//							if (mTask != null) {
//								mTask = null;
//							}
//							mTask = new MyTimerTask();
//							timer.schedule(mTask, 0, 1000);
//						}
//						SharedPreferencesUtil.saveStringData(context, Pref.SHOPCART_MEAL_TIME,
//								"" + (System.currentTimeMillis() + 30 * 60 * 1000));
//						dao.add(null, null, null, shopCart.getShop_num(), 0, (String) shopCart.getDef_pic(), 0, null,
//								null, "" + shopCart.getShop_price(), "" + shopCart.getShop_se_price(),
//								shopCart.getSupp_id() + "", "" + 0, "" + 0, 1, shopCart.getP_code(),
//								"" + shopCart.getPostage(), shopCart.getP_s_t_id(), shopCart.getP_shop_code(),
//								shopCart.getP_color(), shopCart.getId(),
//								shopCart.getP_s_t_id().split(",").length > 1 ? "????????????" : "????????????", 0);
//					}
//					dao.p_delete_invalid("" + shopCart.getP_s_t_id());
//					getSystemTime(context);
//				}
//
//			}
//
//		}.execute();
//	}
//
//	/****
//	 * ???????????????????????????
//	 */
//	private void deleteShopCart(final int position) {
//		if (position < mInvalidPosition) {
//			ShopCartDao dao = new ShopCartDao(mContext);
//			dao.p_delete("" + listShopCarts.get(position).getP_s_t_id());
//			// ????????????????????????
//			List<ShopCart> list_invalid = dao.findAll_invalid();
//			boolean flag = false;
//			for (int i = 0; i < list_invalid.size(); i++) {
//				if (("" + listShopCarts.get(position).getP_s_t_id()).equals("" + list_invalid.get(i).getP_s_t_id())) {
//					flag = true;
//					break;
//				}
//			}
//			if (flag) {// ????????????????????????
//				dao.p_modify_invalid("" + listShopCarts.get(position).getP_s_t_id(), 2);
//			} else {// ????????????????????????
//				ShopCart p = listShopCarts.get(position);
//				List<StockBean> shop_list = p.getShop_list();
//				StringBuffer mShop_code_p = new StringBuffer();
//				StringBuffer mColor_p = new StringBuffer();
//				for (int j = 0; j < shop_list.size(); j++) {
//					if (j < shop_list.size() - 1) {
//						mShop_code_p.append(shop_list.get(j).getShop_code()).append(",");
//						mColor_p.append(shop_list.get(j).getColor()).append(",");
//					} else {
//						mShop_code_p.append(shop_list.get(j).getShop_code());
//						mColor_p.append(shop_list.get(j).getColor());
//					}
//				}
//				boolean hh = dao.add_invalid(null, null, null, p.getShop_num(), 0, p.getDef_pic(), 0, null, null,
//						"" + p.getShop_price(), "" + p.getShop_se_price(),
//						p.getSupp_id() != null ? p.getSupp_id() + "" : "" + p.getShop_list().get(0).getSupp_id(),
//						"" + 0, "" + 0, 1, p.getP_code(), "" + p.getPostage(), p.getP_s_t_id(), mShop_code_p.toString(),
//						mColor_p.toString(), p.getId() != null ? p.getId() : 0,
//						p.getP_s_t_id().split(",").length > 1 ? "????????????" : "????????????", 0);
//			}
//			getSystemTime(mContext);
//			if (dao.queryCartSpecialCount(mContext) == 0) {
//				SharedPreferencesUtil.saveStringData(mContext, Pref.SHOPCART_MEAL_TIME, "0");
//			}
//			ToastUtil.showShortText(mContext, "????????????");
//			// Toast.makeText(mContext, "????????????", Toast.LENGTH_SHORT).show();
//		} else {
//			new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, null, R.string.wait) {
//				@Override
//				protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {
//					ReturnInfo r = ComModel.deleteShopCartSpecial(mContext, YCache.getCacheToken(mContext), params[0]);
//					return r;
//				}
//
//				@Override
//				protected void onPostExecute(FragmentActivity mContext, ReturnInfo r, Exception e) {
//
//					if (e != null) {
//						LogYiFu.e("?????? -----", "??????");
//					} else {
//						adpter.deleteSucess(listShopCarts.get(position));
//						// listShopCarts.remove(position);
//						if (position < mInvalidPosition) {
//							sp.remove(position);
//							spImg.remove(position);
//						}
//						mIsLoding = false;
//						// curPage = 1;
//						// queryListShopCart(1);
//						ShopCartDao dao = new ShopCartDao(getActivity());
//						if (position < mInvalidPosition) {// ?????????????????????
//							dao.p_delete(listShopCarts.get(position).getP_s_t_id());
//						} else {// ?????????????????????
//							dao.p_delete_invalid(listShopCarts.get(position).getP_s_t_id());
//						}
//
//						getSystemTime(getActivity());
//						// int resource = R.layout.listview_shop_cart_special;
//						//
//						// listShopCarts.clear();
//						// sp.clear();
//						// spImg.clear();
//						// mListDao = dao.findAll();
//						// if (mListDao != null) {
//						// for (int i = mListDao.size() - 1; i >= 0; i--) {
//						//
//						// if (mListDao.get(i).getIs_meal_flag() == 1) {
//						// listShopCarts.add(mListDao.get(i));
//						// }
//						// }
//						// }
//						// if (shopCartSpecialInterface != null) {
//						// shopCartSpecialInterface.getShopSpecialCount(mListDao.size(),
//						// listShopCarts.size());
//						//
//						// }
//						// for (int i = 0; i < listShopCarts.size(); i++) {
//						// sp.add(1);
//						// }
//						// for (int i = 0; i < listShopCarts.size(); i++) {
//						// spImg.add(1);
//						// }
//						// mInvalidPosition = listShopCarts.size();
//						// if (mInvalidPosition > 0) {
//						// listView.setVisibility(View.VISIBLE);
//						// llBottom.setVisibility(View.VISIBLE);
//						// layout_nodata_shopcar.setVisibility(View.GONE);
//						// } else {
//						// listView.setVisibility(View.GONE);
//						// llBottom.setVisibility(View.GONE);
//						// layout_nodata_shopcar.setVisibility(View.VISIBLE);
//						// }
//						// mPayFlag = false; // !=
//						//
//						// adpter = new ShopCartAdpter(mContext, resource,
//						// listShopCarts, sp, spImg, 2, mInvalidPosition);
//						//
//						// adpter.setCartOncallback(ShopCartSpecialFragment.this);
//						// listView.setAdapter(adpter);
//						// tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
//						// mContext.getResources().getDrawable(R.drawable.icon_dapeigou_celect),
//						// null, null, null);
//						// setAllSelect(false);
//						// allFlag = false;
//						// adpter.notifyDataSetChanged();
//						// Toast.makeText(mContext, "????????????",
//						// Toast.LENGTH_SHORT).show();
//						ToastUtil.showShortText(mContext, "????????????");
//					}
//				};
//
//				@Override
//				protected boolean isHandleException() {
//					return true;
//				};
//			}.execute(listShopCarts.get(position).getId() + "", listShopCarts.get(position).getP_code());
//		}
//	}
//
//	/*
//	 * @Override public void onPause() { // TODO Auto-generated method stub
//	 * super.onPause(); MobclickAgent.onPageEnd("ShopCartActivity");
//	 * MobclickAgent.onPause(mContext); }
//	 */
//	// TODO:
//	public void flush() {
//		// MobclickAgent.onPageStart("ShopCartActivity");
//		// MobclickAgent.onResume(mContext);
//
//		// UserInfo userInfo = YCache.getCacheUserSafe(ShopCartActivity.this);
//		curPage = 1;
//		// queryListShopCart(1);
//		listView.setMode(Mode.BOTH);
//		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
//
//			@Override
//			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//				if (refeshFlag) {
//					curPage = 1;
//					// MyLogYiFu.e("????????????curpage == ", ""+ curPage);
//					mIsLoding = false;
//					// queryListShopCart(1);
//				} else {
//					Toast.makeText(mContext, "???????????????????????????", Toast.LENGTH_SHORT).show();
//				}
//				listView.onRefreshComplete();
//				// setAllSelect(false);
//				// allFlag = false;
//
//			}
//
//			@Override
//			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//				if (refeshFlag) {
//					curPage++;
//					// MyLogYiFu.e("????????????curpage == ", ""+ curPage);
//					// queryListShopCart(2);
//				} else {
//					Toast.makeText(mContext, "???????????????????????????", Toast.LENGTH_SHORT).show();
//				}
//				listView.onRefreshComplete();
//				// setAllSelect(false);
//				// allFlag = false;
//			}
//
//		});
//
//		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
//
//			@Override
//			public void onLastItemVisible() {
//
//				LogYiFu.e("End of List! == ", "End of List!");
//			}
//		});
//
//		// listView.setOnItemClickListener(this);
//	}
//
//	/****
//	 * ???????????????????????????
//	 */
//	private void deleteShopCartList(final String ids) {
//		if (listShopCarts != null && listShopCarts.size() > 0) {
//			new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, null, R.string.wait) {
//				@Override
//				protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {
//					// String ids = "";
//					// StringBuffer buffer = new StringBuffer();
//					// int length = spImg.size() - 1;
//					// for (int i = 0; i < length + 1; i++) {
//					// int p = spImg.get(i);
//					//
//					// if (p == 2) {
//					// String id = listShopCarts.get(i).getId() + "";
//					// buffer.append(id + ",");
//					// }
//					// ids = buffer.toString();
//					// ids = ids.substring(0, ids.length() - 1);
//					//
//					// }
//					ReturnInfo r = ComModel.deleteShopCartList(mContext, YCache.getCacheToken(mContext), ids);
//					return r;
//				}
//
//				@Override
//				protected void onPostExecute(FragmentActivity mContext, ReturnInfo r, Exception e) {
//
//					if (e != null) {
//						// LogYiFu.e("?????? -----", "??????");
//					} else {
//						// for (int i = spImg.size() - 1; i >= 0; i--) {
//						// if (spImg.get(i) == 2) {
//						// listShopCarts.remove(i);
//						// spImg.remove(i);
//						// sp.remove(i);
//						//
//						// }
//						// }
//						//
//						// adpter.notifyDataSetChanged();
//						// setPrice();
//						// Toast.makeText(mContext, "????????????",
//						// Toast.LENGTH_SHORT).show();
//						// ToastUtil.showShortText(mContext, "????????????");
//					}
//				};
//
//				@Override
//				protected boolean isHandleException() {
//					return true;
//				};
//			}.execute();
//		}
//
//	}
//
//	/***
//	 * ?????????????????????
//	 */
//	private void updateShopCart(final int position, final int shop_number) {
//
//		final ShopCart cart = listShopCarts.get(position);
//		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, null, R.string.wait) {
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {
//				ReturnInfo r = ComModel.updateShopCartSpecial(mContext, YCache.getCacheToken(mContext),
//						"" + shop_number, cart.getId() + "");
//				return r;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity mContext, ReturnInfo r, Exception e) {
//
//				if (e != null) {
//					LogYiFu.e("?????? -----", "??????");
//				} else {
//					listShopCarts.get(position).setShop_num(shop_number);
//					ShopCartDao dao = new ShopCartDao(getActivity());
//					dao.p_modify(cart.getP_s_t_id(), shop_number);
//					int resource = R.layout.listview_shop_cart_special;
//
//					listShopCarts.clear();
//					sp.clear();
//					spImg.clear();
//					mListDao = dao.findAll();
//					if (mListDao != null) {
//						for (int i = mListDao.size() - 1; i >= 0; i--) {
//
//							if (mListDao.get(i).getIs_meal_flag() == 1) {
//								listShopCarts.add(mListDao.get(i));
//							}
//						}
//					}
//					for (int i = 0; i < listShopCarts.size(); i++) {
//						sp.add(1);
//					}
//					for (int i = 0; i < listShopCarts.size(); i++) {
//						spImg.add(1);
//					}
//					mInvalidPosition = listShopCarts.size();
//					if (mInvalidPosition > 0) {
//						listView.setVisibility(View.VISIBLE);
//						llBottom.setVisibility(View.VISIBLE);
//						layout_nodata_shopcar.setVisibility(View.GONE);
//					} else {
//						listView.setVisibility(View.GONE);
//						llBottom.setVisibility(View.GONE);
//						layout_nodata_shopcar.setVisibility(View.VISIBLE);
//					}
//					mPayFlag = false; // !=
//
//					adpter = new ShopCartAdpter(mContext, resource, listShopCarts, sp, spImg, 2, mInvalidPosition,
//							flag_activity);
//
//					adpter.setCartOncallback(ShopCartSpecialFragment.this);
//					listView.setAdapter(adpter);
//					tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
//							mContext.getResources().getDrawable(R.drawable.icon_dapeigou_celect), null, null, null);
//					setAllSelect(false);
//					allFlag = false;
//					setPrice();
//					adpter.notifyDataSetChanged();
//				}
//			};
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			};
//		}.execute();
//
//	}
//
//	/***
//	 * ??????????????????
//	 */
//	private void queryShopQueryAttr(final int position) {
//		if (listShopCarts != null && listShopCarts.size() > 0) {
//			ShopCart cart = listShopCarts.get(position);
//			new SAsyncTask<String, Void, List<StockType>>((FragmentActivity) mContext, null, R.string.wait) {
//				@Override
//				protected List<StockType> doInBackground(FragmentActivity mContext, String... params) throws Exception {
//
//					List<StockType> list = ComModel.queryShop_Stokect(mContext, params[0]);
//					return list;
//				}
//
//				@Override
//				protected void onPostExecute(FragmentActivity mContext, List<StockType> list, Exception e) {
//
//					if (e != null) {// ????????????
//						Toast.makeText(mContext, "????????????????????????", Toast.LENGTH_LONG).show();
//
//					} else {// ???????????????????????????????????????
//						if (list != null && list.size() > 0) {
//							showPopWindow(position, list);
//						}
//					}
//
//				};
//
//				@Override
//				protected boolean isHandleException() {
//					return true;
//				};
//			}.execute(cart.getShop_code());
//
//		}
//
//	}
//
//	/****
//	 * ?????????????????????
//	 * 
//	 * @param i
//	 */
//	private void showPopWindow(final int position, List<StockType> list) {
//		if (listShopCarts != null && listShopCarts.size() > 0) {
//			final ShopCartDialog dlg = new ShopCartDialog(mContext, R.style.DialogStyle, height, position,
//					listShopCarts, list);
//			Window window = dlg.getWindow();
//			window.setGravity(Gravity.BOTTOM);
//			window.setWindowAnimations(R.style.dlg_down_to_top);
//			dlg.show();
//
//			dlg.cartDialogOncallBack = new ShopCartDialog.ShopCartDialogOncallBack() {
//
//				@Override
//				public void updateOncallBack(double shop_se_price, String color, String size, int shop_num,
//						String def_pic, String stock_type_id, View v) {
//					dlg.dismiss();
//					updateShopCartType(shop_se_price + "", color, size, shop_num, def_pic, stock_type_id, position, v);
//
//				}
//			};
//
//		}
//
//	}
//
//	/**
//	 * ??????????????????????????????,??????????????????
//	 */
//	private void updateShopCartType(final String shop_se_price, final String color, final String size,
//			final int shop_num, String def_pic, String stock_type_id, int p, final View v) {
//
//		final ShopCart cart = listShopCarts.get(p);
//		if (cart == null)
//			return;
//		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, null, R.string.wait) {
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {
//				ReturnInfo r = ComModel.updateShopCartType(mContext, YCache.getCacheToken(mContext), params[0],
//						params[1], params[2], params[3], params[4], params[5], params[6]);
//				return r;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity mContext, ReturnInfo r, Exception e) {
//
//				if (e != null) {
//					LogYiFu.e("?????? -----", "??????" + e);
//
//				} else {// ????????????
//					cart.setShop_num(shop_num);
//					cart.setShop_se_price(Double.parseDouble(shop_se_price));
//					cart.setPrice(cart.getShop_se_price());
//					cart.setColor(color);
//					cart.setSize(size);
//					adpter.notifyDataSetChanged();
//					setPrice();
//
//				}
//			};
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			};
//		}.execute(cart.getId() + "", shop_se_price, color, size, shop_num + "", def_pic, stock_type_id);
//	}
//
//	@Override
//	public void onClick(View v) {
//		Intent intent = null;
//		switch (v.getId()) {
//		case R.id.tv_edit_cart:// ????????????
//			editorAllGoods(tv_edit_cart);
//			break;
//		case R.id.btn_to_shop:// ?????????????????????????????????
//
//			// if (MainMenuActivity.instances != null) {
//			// MainMenuActivity.instances.finish();
//			// }
//			Intent intent2 = new Intent(getActivity(), MainMenuActivity.class);
//			intent2.putExtra("toYf", "toYf");
//			intent2.putExtra("specialCart", "specialCart");
//			startActivity(intent2);
//			if ("1".equals(flag_activity)) {
//				((Activity) mContext).finish();
//			}
//			// ((MainFragment) MainMenuActivity.instances
//			// .getSupportFragmentManager().findFragmentByTag("tag"))
//			// .setIndex(1);
//			// MainFragment.mfragment.setIndex(1);
//			// intent = new Intent();
//			// if(ShopNewFragment.newInstances("tab2", mContext)!=null){
//			// ShopNewFragment.newInstances("tab2", mContext)=null;
//			// }
//			// ShopNewFragment.setCurrentIndex(1);
//
//			// SharedPreferencesUtil.saveBooleanData(getActivity(),
//			// "SPECIAL_GUANGGUANG", true);
//			// ((MainFragment) MainMenuActivity.instances
//			// .getSupportFragmentManager().findFragmentByTag("tag"))
//			// .setIndex(1);
//			// ((Activity) mContext).finish();
//			break;
//		case R.id.tv_allchoose:// ??????
//			allFlag = !allFlag;
//			setAllSelect(allFlag);
//			break;
//		case R.id.ll_goto_pay:// ??????
////			YunYingTongJi.yunYingTongJi(mContext, 109);
//			mPayFlag = true;
//			getOrderNo();
//			break;
//		// case R.id.tv_remove:// ???????????????
//		// addShopLike(tv_remove);
//		// break;
//		// case R.id.tv_delete_all:// ????????????
//		// deleteShopCartList();
//		// break;
//		default:
//			break;
//		}
//
//	}
//
//	// ????????????
//	private void setAllSelect(boolean isSelectedAll) {
//		if (isSelectedAll) { // ?????? ????????????
//			cPrice = 0;
//			int goodsNum = 0;
//			int saveMoney = 0;
//			for (int i = 0; i < spImg.size(); i++) {
//				spImg.set(i, 2);
//				// TODO:
//				if (listShopCarts.get(i).getValid() == 0) {
//					tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
//							mContext.getResources().getDrawable(R.drawable.icon_dapeigou_celect), null, null, null);
//					ShopCart shopCart = listShopCarts.get(i);
//					cPrice = cPrice + shopCart.getShop_se_price() * shopCart.getShop_num()
//							+ shopCart.getPostage() * shopCart.getShop_num();
//					goodsNum += shopCart.getShop_num();
//					// saveMoney += shopCart.getShop_num()
//					// * (shopCart.getShop_price() - shopCart
//					// .getShop_se_price());
//				}
//			}
//			String price = new java.text.DecimalFormat("#0.0").format(cPrice);
//			String savemoneyStr = new java.text.DecimalFormat("#0.0").format(saveMoney);
//			tv_price.setText("?????? " + price + "???");
//			// tv_pay.setText("??????(" + goodsNum + ")");
//			tv_pay.setText("??????");
//			tv_no_freight.setText("???????????????0.0???");
//
//		} else {
//			for (int i = 0; i < spImg.size(); i++) {
//				spImg.set(i, 1);
//
//				tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
//						mContext.getResources().getDrawable(R.drawable.icon_dapeigou_normal), null, null, null);
//			}
//			cPrice = 0;
//			tv_price.setText("?????? " + cPrice + "???");
//			// tv_pay.setText("??????(0)");
//			tv_pay.setText("??????");
//			tv_no_freight.setText("???????????????0.0???");
//		}
//		adpter.notifyDataSetChanged();
//	}
//
//	private void getOrderNo() {
//		List<ShopCart> listGoods = new ArrayList<ShopCart>();
//		StringBuffer sb = new StringBuffer();
//		// for (int i = 0; i < spImg.size(); i++) {
//		//
//		// int f = spImg.get(i);
//		// if (f == 2) {
//		// // TODO:
//		// if (listShopCarts.get(i).getValid() == 0) {
//		// ShopCart shopCart = listShopCarts.get(i);
//		// String id = shopCart.getId() + ",";
//		// sb.append(id);
//		// shopCart.setShop_list(null);
//		// if (shopCart != null) {
//		// listGoods.add(shopCart);
//		// }
//		// }
//		// }
//		// }
//		for (int i = 0; i < listShopCarts.size(); i++) {
//			if (i < mInvalidPosition) {
//				if (listShopCarts.get(i).getValid() == 0) {
//					ShopCart shopCart = listShopCarts.get(i);
//					String id = shopCart.getId() + ",";
//					sb.append(id);
//					shopCart.setShop_list(null);
//					if (shopCart != null) {
//						listGoods.add(shopCart);
//					}
//				}
//			}
//		}
//
//		String mCardIds = sb.toString().substring(0, sb.toString().length());
//		// ???????????????0 ?????????
//		if (listGoods.size() == 0) {
//			ToastUtil.showShortText(mContext, "???????????????");
//			// Toast.makeText(mContext, "???????????????", Toast.LENGTH_SHORT).show();
//			return;
//		}
//		Intent intent = new Intent(mContext, SubmitMultiShopSpecialActivty.class);
//		// TODO:
//		// intent.putExtra(name, value);
//		Bundle bundle = new Bundle();
//		bundle.putSerializable("listGoods", (Serializable) listGoods);
//		intent.putExtra("mCardIds", mCardIds);
//		intent.putExtra("mTime", recLen);
//		intent.putExtras(bundle);
//		mContext.startActivity(intent);
//		// getActivity().finish();
//	}
//
//	@Override
//	public void selectOnCallBack(int position) {
//		int p = sp.get(position);
//		if (p == 1) {
//			sp.set(position, 2);
//		} else if (p == 2) {
//			sp.set(position, 1);
//		}
//		setPrice();
//		// adpter.notifyDataSetChanged();
//
//	}
//
//	@Override
//	public void addOnCallBack(int position, int shop_number) {
//		updateShopCart(position, shop_number);
//
//	}
//
//	@Override
//	public void reduceOnCallBack(int position, int shop_number) {
//		updateShopCart(position, shop_number);
//
//	}
//
//	@Override
//	public void updatetOnCallBack(int position) {
//		queryShopQueryAttr(position);
//
//	}
//
//	@Override
//	public void deleteOnCallBack(final int position) {
//		deleteShopCart(position);
//		// if (spImg.get(position) == 2) {
//		// AlertDialog.Builder builder = new Builder(mContext);
//		// // ???????????????????????????
//		// View view = View.inflate(mContext, R.layout.payback_esc_apply_dialog,
//		// null);
//		// TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
//		//
//		// tv_des.setText("??????????????????");
//		//
//		// Button ok = (Button) view.findViewById(R.id.ok);
//		// ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
//		// Button cancel = (Button) view.findViewById(R.id.cancel);
//		//
//		// cancel.setOnClickListener(new OnClickListener() {
//		//
//		// @Override
//		// public void onClick(View v) {
//		// // ???????????????????????????
//		// dialog.dismiss();
//		// }
//		// });
//		// ok.setOnClickListener(new OnClickListener() {
//		//
//		// @Override
//		// public void onClick(View arg0) {
//		// deleteShopCart(position);
//		// dialog.dismiss();
//		// }
//		// });
//		// dialog = builder.create();
//		// dialog.setView(view, 0, 0, 0, 0);
//		// dialog.show();
//
//		// } else {
//		// Toast.makeText(mContext, "?????????????????????????????????", Toast.LENGTH_SHORT)
//		// .show();
//		// }
//
//	}
//
//	// ?????????????????????
//	@Override
//	public void selectImgOnCallBack(int position) {
//		int p = spImg.get(position);
//		if (p == 1) {
//			spImg.set(position, 2);
//		} else {
//			spImg.set(position, 1);
//		}
//		setPrice();
//		// ??????????????????
//		setSelectAllStatus();
//	}
//
//	// ??????????????????????????? ??????????????????
//	private void setSelectAllStatus() {
//		if (spImg.size() == 0) {
//			tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dapeigou_normal, 0, 0, 0);
//		}
//		for (int i = 0; i < spImg.size(); i++) {
//			int p = spImg.get(i);
//			if (p == 1) {
//				tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dapeigou_normal, 0, 0, 0);
//				return;
//			}
//		}
//		tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_dapeigou_celect, 0, 0, 0);
//	}
//
//	// ???????????????
//	private void setPrice() {
//		if (spImg != null && listShopCarts != null) {
//			cPrice = 0;
//			int chooseNo = 0;
//			double saveMoney = 0;
//			for (int i = 0; i < spImg.size(); i++) {
//				int f = spImg.get(i);
//				if (f == 2) {
//					ShopCart shopCart = listShopCarts.get(i);
//					if (shopCart.getValid() == 0) {
//						if (shopCart != null) {
//							int number = shopCart.getShop_num();
//							// double se_price = shopCart.getShop_se_price();
//							double se_price = shopCart.getShop_se_price();
//							cPrice = cPrice + number * se_price + shopCart.getPostage() * shopCart.getShop_num();
//							chooseNo = chooseNo + number;
//							// saveMoney += shopCart.getShop_num()
//							// * (shopCart.getShop_price() - se_price);
//						}
//					}
//				}
//			}
//			// tv_pay.setText("??????(" + chooseNo + ")");
//			tv_pay.setText("??????");
//			// new java.text.DecimalFormat("#0.00").format(saveMoney)
//			tv_price.setText("?????? " + new java.text.DecimalFormat("#0.0").format(cPrice) + "???");
//			// tv_no_freight.setText("??????????????????"
//			// + new java.text.DecimalFormat("#0.00").format(saveMoney));//
//			// ???????????????
//			tv_no_freight.setText("???????????????0.0???");// ???????????????
//
//		}
//
//	}
//
//	/***
//	 * ??????????????????
//	 * 
//	 * @param v
//	 */
//	private void addShopLike(View v) {
//		if (listShopCarts != null && listShopCarts.size() > 0) {
//			StringBuffer bfAdd = new StringBuffer();
//			for (int i = 0; i < spImg.size(); i++) {
//				int f = spImg.get(i);
//				if (f == 2) {
//					ShopCart cart = listShopCarts.get(i);
//					String like_id = cart.getLike_id();
//					if (like_id.equals("-1")) {
//						String shop_code = cart.getShop_code();
//						bfAdd.append(shop_code + ",");
//					}
//				}
//			}
//
//			String shop_code = bfAdd.toString();
//			if (!TextUtils.isEmpty(shop_code)) {
//				shop_code = shop_code.substring(0, shop_code.length() - 1);// ????????????????????????
//				new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, v, R.string.wait) {
//
//					@Override
//					protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {
//
//						return ComModel.addLikeShop(mContext, YCache.getCacheToken(mContext), params[0]);
//					}
//
//					@Override
//					protected boolean isHandleException() {
//						return true;
//					}
//
//					@Override
//					protected void onPostExecute(FragmentActivity mContext, ReturnInfo result, Exception e) {
//
//						if (null == e) {
//							if (null != result) {
//								Toast.makeText(mContext, "????????????", Toast.LENGTH_SHORT).show();
//							} else {
//								Toast.makeText(mContext, "????????????", Toast.LENGTH_SHORT).show();
//							}
//						}
//						super.onPostExecute(mContext, result, e);
//					}
//
//				}.execute(shop_code);
//			}
//
//		}
//
//	}
//
//	@Override
//	public void onItemClickLintener(int position) {
//		if (listShopCarts.get(position).getValid() == 0) {
//			Intent intent = new Intent(mContext, ShopDetailsActivity.class);
//			intent.putExtra("height", statusBarHeight);
//			String p_code = listShopCarts.get(position).getP_code();
//			if (TextUtils.isEmpty(p_code)) {
//				intent.putExtra("code", listShopCarts.get(position).getShop_code());
//			} else {
//				intent.putExtra("code", p_code);
//				intent.putExtra("isMeal", true);
//				intent.putExtra("ShopCart", true);
//			}
//
//			// startActivity(intent);
//			((Activity) mContext).startActivityForResult(intent, 100);
//			((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
//			;
//		}
//	}
//
//	/*
//	 * public void initData() { int resource =
//	 * R.layout.listview_shop_cart_special; if (mFlag) { adpter = new
//	 * ShopCartAdpter(mContext, resource, mListCommon, sp, spImg, 2); } else {
//	 * queryListShopCart(1);
//	 * 
//	 * adpter = new ShopCartAdpter(mContext, resource, listShopCarts, sp, spImg,
//	 * 2); adpter.setCartOncallback(this); } listView.setAdapter(adpter);
//	 * flush(); }
//	 */
//	@Override
//	public void onResume() {
//		super.onResume();
//		// int resource = R.layout.listview_shop_cart_special;
//		//
//		// ShopCartDao dao = new ShopCartDao(getActivity());
//		// listShopCarts.clear();
//		// mListDao = dao.findAll();
//		// if (mListDao != null) {
//		// for (int i = mListDao.size() - 1; i >= 0; i--) {
//		//
//		// if (mListDao.get(i).getIs_meal_flag() == 1) {
//		// listShopCarts.add(mListDao.get(i));
//		//
//		// }
//		//
//		// }
//		// }
//		// for (int i = 0; i < listShopCarts.size(); i++) {
//		// sp.add(1);
//		// }
//		// for (int i = 0; i < listShopCarts.size(); i++) {
//		// spImg.add(1);
//		// }
//		// if (shopCartSpecialInterface != null) {
//		// shopCartSpecialInterface.getShopSpecialCount(mListDao.size(),
//		// listShopCarts.size());
//		//
//		// }
//		// mInvalidPosition = listShopCarts.size();
//		// if (mInvalidPosition > 0) {
//		// llBottom.setVisibility(View.VISIBLE);
//		// listView.setVisibility(View.VISIBLE);
//		// layout_nodata_shopcar.setVisibility(View.GONE);
//		// } else {
//		// llBottom.setVisibility(View.GONE);
//		// listView.setVisibility(View.GONE);
//		// layout_nodata_shopcar.setVisibility(View.VISIBLE);
//		// }
//		// // if (mFlag && (mInvalidPosition > 0 || listShopCartsInValid.size()
//		// >
//		// // 0) && !mPayFlag) {// &&
//		// // listShopCarts.size()
//		// mPayFlag = false; // !=
//		// // 0
//		// sp.clear();
//		// spImg.clear();
//		// for (int i = 0; i < mInvalidPosition; i++) {
//		// sp.add(1);
//		// }
//		// for (int i = 0; i < mInvalidPosition; i++) {
//		// spImg.add(1);
//		// }
//		// adpter = new ShopCartAdpter(mContext, resource, listShopCarts, sp,
//		// spImg, 2, mInvalidPosition);
//		//
//		// adpter.setCartOncallback(this);
//		// // if (mInvalidPosition > 0) {
//		// // llBottom.setVisibility(View.VISIBLE);
//		// // } else {
//		// // llBottom.setVisibility(View.GONE);
//		// // }
//		// // if (mInvalidPosition <= 0) {
//		// // // tv_time.setText("???????????????");
//		// // // ll_goto_pay.setClickable(false);
//		// // // ll_goto_pay.setBackgroundColor(Color.parseColor("#cecece"));
//		// // }
//		// // }
//		// // else {
//		// //// queryListShopCart(1);
//		// //
//		// // adpter = new ShopCartAdpter(mContext, resource, listShopCarts, sp,
//		// // spImg, 2, mInvalidPosition);
//		// // adpter.setCartOncallback(this);
//		// // }
//		// listView.setAdapter(adpter);
//		//
//		// // llBottom.setVisibility(View.VISIBLE);
//		//
//		// tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
//		// mContext.getResources().getDrawable(R.drawable.icon_dapeigou_celect),
//		// null, null, null);
//		// setAllSelect(false);
//		// allFlag = false;
//		// adpter.notifyDataSetChanged();
//		getSystemTime(getActivity());
//	}
//
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		LogYiFu.e(TAG, "??????");
//		// if (mTask != null) {
//		// mTask.cancel();
//		// mTask = new MyTimerTask();
//		// } else {
//		// mTask = new MyTimerTask();
//		// }
//		// timer.schedule(mTask, 0, 1000); // timeTask
//
//	}
//
//	// ?????????
//	class MyTimerTask extends TimerTask {
//
//		@Override
//		public void run() {
//			((Activity) mContext).runOnUiThread(new Runnable() { // UI thread
//				@Override
//				public void run() {
//					tv_time.setVisibility(View.VISIBLE);
//					ll_goto_pay.setClickable(true);
//					ll_goto_pay.setBackgroundColor(Color.parseColor("#ff3f8b"));
//					recLen -= 1000;
//					String days;
//					String hours;
//					String minutes;
//					String seconds;
//					long minute = recLen / 60000;
//					long second = (recLen % 60000) / 1000;
//					if (minute >= 60) {
//						long hour = minute / 60;
//						minute = minute % 60;
//						if (hour >= 24) {
//							long day = hour / 24;
//							hour = hour % 24;
//							if (day < 10) {
//								days = "0" + day;
//							} else {
//								days = "" + day;
//							}
//							if (hour < 10) {
//								hours = "0" + hour;
//							} else {
//								hours = "" + hour;
//							}
//							if (minute < 10) {
//								minutes = "0" + minute;
//							} else {
//								minutes = "" + minute;
//							}
//							if (second < 10) {
//								seconds = "0" + second;
//							} else {
//								seconds = "" + second;
//							}
//							tv_time.setText("" + days + ":" + hours + ":" + minutes + ":" + seconds);
//						} else {
//							if (hour < 10) {
//								hours = "0" + hour;
//							} else {
//								hours = "" + hour;
//							}
//							if (minute < 10) {
//								minutes = "0" + minute;
//							} else {
//								minutes = "" + minute;
//							}
//							if (second < 10) {
//								seconds = "0" + second;
//							} else {
//								seconds = "" + second;
//							}
//							tv_time.setText("" + hours + ":" + minutes + ":" + seconds);
//						}
//					} else if (minute >= 10 && second >= 10) {
//						tv_time.setText("" + minute + ":" + second);
//					} else if (minute >= 10 && second < 10) {
//						tv_time.setText("" + minute + ":0" + second);
//					} else if (minute < 10 && second >= 10) {
//						tv_time.setText("0" + minute + ":" + second);
//					} else {
//						tv_time.setText("0" + minute + ":0" + second);
//					}
//					// tv_time.setText("" + recLen);
//					if (recLen < 0) {
//						if(timer!=null){
//						timer.cancel();
//						timer = null;
//						}
//						if (isShowShopCart) {
//							// queryListShopCart(1);
//							getSystemTime(mContext);
//							isShowShopCart = true;
//						}
//						tv_time.setText("???????????????");
//
//						// tv_time.setVisibility(View.GONE);
//						ll_goto_pay.setClickable(false);
//						ll_goto_pay.setBackgroundColor(Color.parseColor("#cecece"));
//					}
//				}
//			});
//		}
//
//	}
//
//	/**
//	 * ??????????????????
//	 */
//	private void getSystemTime(Context context) {
//
//		// new SAsyncTask<Void, Void, HashMap<String,
//		// Object>>((FragmentActivity) context, R.string.wait) {
//		//
//		// @Override
//		// protected HashMap<String, Object> doInBackground(FragmentActivity
//		// context, Void... params)
//		// throws Exception {
//		// return ComModel2.getSystemTime(context);
//		// }
//		//
//		// @Override
//		// protected boolean isHandleException() {
//		// return true;
//		// }
//		//
//		// @Override
//		// protected void onPostExecute(FragmentActivity context,
//		// HashMap<String, Object> result, Exception e) {
//		// super.onPostExecute(context, result, e);
//		//
//		// if (null == e && result != null) {
//		// long nowTime = (Long) result.get("now");
//		final int resource = R.layout.listview_shop_cart_special;
//		final ShopCartDao dao = new ShopCartDao(getActivity());
//		listShopCarts.clear();
//		mListDao = dao.findAll();
//		final List<ShopCart> mListDaoInvalid = dao.findAll_invalid();
//		final List<ShopCart> list_common = new ArrayList<ShopCart>();// ????????????
//		final List<ShopCart> list_invalid_common = new ArrayList<ShopCart>();// ????????????
//		if (mListDao != null) {
//			for (int i = mListDao.size() - 1; i >= 0; i--) {
//
//				if (mListDao.get(i).getIs_meal_flag() == 1) {// ????????????????????????
//					list_common.add(mListDao.get(i));
//				}
//
//			}
//		}
//		if (mListDaoInvalid != null) {
//			for (int i = mListDaoInvalid.size() - 1; i >= 0; i--) {
//
//				if (mListDaoInvalid.get(i).getIs_meal_flag() == 1) {// ????????????????????????
//					list_invalid_common.add(mListDaoInvalid.get(i));
//				}
//
//			}
//		}
//		String time = SharedPreferencesUtil.getStringData(context, Pref.SHOPCART_MEAL_TIME, "0");
//		if (Long.parseLong(time) <= System.currentTimeMillis()) {// ??????????????????
//			for (int i = 0; i < list_common.size(); i++) {
//				boolean flag = false;
//				for (int j = 0; j < list_invalid_common.size(); j++) {
//					if (list_common.get(i).getP_s_t_id().equals(list_invalid_common.get(j).getP_s_t_id())) {
//						flag = true;
//						break;
//					}
//				}
//				if (flag) {// ???????????????????????????????????????
//					dao.p_modify_invalid(list_common.get(i).getP_s_t_id(), 2);
//
//				} else {// ????????????????????????????????????
//					dao.add_invalid(null, null, null, list_common.get(i).getShop_num(), 0,
//							(String) list_common.get(i).getDef_pic(), 0, null, null,
//							"" + list_common.get(i).getShop_price(), "" + list_common.get(i).getShop_se_price(),
//							list_common.get(i).getSupp_id() + "", "" + 0, "" + 0, 1, list_common.get(i).getP_code(),
//							"" + list_common.get(i).getPostage(), list_common.get(i).getP_s_t_id(),
//							list_common.get(i).getP_shop_code(), list_common.get(i).getP_color(),
//							list_common.get(i).getId(),
//							list_common.get(i).getP_s_t_id().split(",").length > 1 ? "????????????" : "????????????", 0);
//				}
//				dao.p_delete("" + list_common.get(i).getP_s_t_id());
//			} // ?????????????????????????????? ?????????????????????????????????????????????????????????
//		} else {// ????????????
//
//		}
//		// TODO:?????????????????????????????????
//		listShopCarts.clear();
//		list_common.clear();
//		list_invalid_common.clear();
//		mListDao = dao.findAll();
//		List<ShopCart> mListDaoInvalid2 = dao.findAll_invalid();
//		if (mListDao != null) {
//			for (int i = mListDao.size() - 1; i >= 0; i--) {
//
//				if (mListDao.get(i).getIs_meal_flag() == 1) {// ??????????????????????????????
//					list_common.add(mListDao.get(i));
//				}
//
//			}
//		}
//		if (mListDaoInvalid2 != null) {
//			for (int i = mListDaoInvalid2.size() - 1; i >= 0; i--) {
//
//				if (mListDaoInvalid2.get(i).getIs_meal_flag() == 1) {// ??????????????????????????????
//					list_invalid_common.add(mListDaoInvalid2.get(i));
//				}
//
//			}
//		}
//		int count_valid = 0;
//		for (int i = 0; i < list_common.size(); i++) {
//			count_valid += list_common.get(i).getShop_num();
//			if (count_valid > 20) {
//				for (int j = i; j < list_common.size(); j++) {
//					list_invalid_common.add(list_common.get(j));
//				}
//			} else {
//				listShopCarts.add(list_common.get(i));
//				sp.add(1);
//				spImg.add(1);
//			}
//		}
//		mInvalidPosition = listShopCarts.size();
//		int count_invalid = 0;
//		int count_new = 0;
//		for (int i = 0; i < list_invalid_common.size(); i++) {
//			count_invalid += list_invalid_common.get(i).getShop_num();
//			count_new++;
//			if (count_invalid > 20 || count_new > 10) {// ??????????????????????????????
//				for (int j = i; j < list_invalid_common.size(); j++) {
//					boolean flag = false;
//					for (int k = 0; k < listShopCarts.size(); k++) {
//						if (list_invalid_common.get(j).getP_s_t_id().equals(listShopCarts.get(k).getP_s_t_id())) {
//							flag = true;
//						}
//					}
//					if (!flag) {
//						dao.p_delete("" + list_invalid_common.get(j).getP_s_t_id());
//						dao.p_delete_invalid("" + list_invalid_common.get(j).getP_s_t_id());
//						StringBuffer sb = new StringBuffer();
//						if (j != list_invalid_common.size() - 1) {
//							sb.append(list_invalid_common.get(j).getId()).append(",");
//						} else {
//							sb.append(list_invalid_common.get(j).getId());
//						}
//						if (sb.toString().length() > 0 && !("".equals(sb.toString()))) {
//							deleteShopCartList(sb.toString());
//						}
//					}
//				}
//				break;
//			} else {
//				listShopCarts.add(list_invalid_common.get(i));
//			}
//		}
//
//		if (shopCartSpecialInterface != null) {
//			shopCartSpecialInterface.getShopSpecialCount(dao.queryCartCount(context),
//					dao.queryCartSpecialCount(context));
//
//		}
//		if (mInvalidPosition > 0 || list_invalid_common.size() > 0) {
//			llBottom.setVisibility(View.VISIBLE);
//			listView.setVisibility(View.VISIBLE);
//			layout_nodata_shopcar.setVisibility(View.GONE);
//		} else {
//			llBottom.setVisibility(View.GONE);
//			listView.setVisibility(View.GONE);
//			layout_nodata_shopcar.setVisibility(View.VISIBLE);
//		}
//		// if (mFlag && (mInvalidPosition > 0 || listShopCartsInValid.size() >
//		// 0) && !mPayFlag) {// &&
//		// listShopCarts.size()
//		mPayFlag = false; // !=
//		// 0
//		sp.clear();
//		spImg.clear();
//		for (int i = 0; i < mInvalidPosition; i++) {
//			sp.add(1);
//		}
//		for (int i = 0; i < mInvalidPosition; i++) {
//			spImg.add(1);
//		}
//		if (dao.queryCartSpecialCount(mContext) <= 0) {
//			recLen = 0;
//		}
//		if (listShopCarts.size() > 0) {
//			new SAsyncTask<Void, Void, List<String>>((FragmentActivity) context, R.string.wait) {
//
//				@Override
//				protected List<String> doInBackground(FragmentActivity context, Void... params) throws Exception {
//					List<String> list = new ArrayList<String>();
//					StringBuffer sb = new StringBuffer();
//					for (int i = 0; i < listShopCarts.size(); i++) {
//						if (i != listShopCarts.size() - 1) {
//							sb.append(listShopCarts.get(i).getP_code()).append(",");
//						} else {
//							sb.append(listShopCarts.get(i).getP_code());
//						}
//					}
//					list = ComModel.getInvalidList(context, YCache.getCacheToken(mContext), sb.toString(), "meal");
//					return list;
//				}
//
//				@Override
//				protected boolean isHandleException() {
//					return true;
//				}
//
//				@Override
//				protected void onPostExecute(FragmentActivity context, List<String> list, Exception e) {
//					super.onPostExecute(context, list, e);
//					if (e == null) {
//						if (list != null) {
//							for (int i = 0; i < list.size(); i++) {
//								String shop_code = list.get(i);
//								for (int j = 0; j < listShopCarts.size(); j++) {
//
//									if (("" + shop_code).equals("" + listShopCarts.get(j).getP_code())) {
//										listShopCarts.get(j).setValid(1);
//										dao.p_delete_invalid("" + listShopCarts.get(j).getP_s_t_id());
//										dao.p_delete("" + listShopCarts.get(j).getP_s_t_id());
//										ShopCart p = listShopCarts.get(j);
//										List<StockBean> shop_list = p.getShop_list();
//										StringBuffer mShop_code_p = new StringBuffer();
//										StringBuffer mColor_p = new StringBuffer();
//										for (int j3 = 0; j3 < shop_list.size(); j3++) {
//											if (j3 < shop_list.size() - 1) {
//												mShop_code_p.append(shop_list.get(j3).getShop_code()).append(",");
//												mColor_p.append(shop_list.get(j3).getColor()).append(",");
//											} else {
//												mShop_code_p.append(shop_list.get(j3).getShop_code());
//												mColor_p.append(shop_list.get(j3).getColor());
//											}
//										}
//										boolean hh = dao.add_invalid(null, null, null, p.getShop_num(), 0,
//												p.getDef_pic(), 0, null, null, "" + p.getShop_price(),
//												"" + p.getShop_se_price(),
//												p.getSupp_id() != null ? p.getSupp_id() + ""
//														: "" + p.getShop_list().get(0).getSupp_id(),
//												"" + 0, "" + 0, 1, p.getP_code(), "" + p.getPostage(), p.getP_s_t_id(),
//												mShop_code_p.toString(), mColor_p.toString(),
//												p.getId() != null ? p.getId() : 0,
//												p.getP_s_t_id().split(",").length > 1 ? "????????????" : "????????????", 1);
//										// if(j<mInvalidPosition){//??????
//										// ShopCart p=listShopCarts.get(j);
//										// listShopCarts.remove(p);
//										// listShopCarts.add(p);
//										// mInvalidPosition--;
//										//
//										// }else{//??????
//										//
//										// }
//									}
//
//								}
//							}
//							// TODO:?????????????????????????????????
//							listShopCarts.clear();
//							list_common.clear();
//							list_invalid_common.clear();
//							List<ShopCart> mListDao2 = dao.findAll();
//							List<ShopCart> mListDaoInvalid2 = dao.findAll_invalid();
//							if (mListDao2 != null) {
//								for (int i = mListDao2.size() - 1; i >= 0; i--) {
//
//									if (mListDao2.get(i).getIs_meal_flag() == 1) {// ??????????????????????????????
//										list_common.add(mListDao2.get(i));
//									}
//
//								}
//							}
//							if (mListDaoInvalid2 != null) {
//								for (int i = mListDaoInvalid2.size() - 1; i >= 0; i--) {
//
//									if (mListDaoInvalid2.get(i).getIs_meal_flag() == 1) {// ??????????????????????????????
//										list_invalid_common.add(mListDaoInvalid2.get(i));
//									}
//
//								}
//							}
//							for (int i = 0; i < list_common.size(); i++) {
//								listShopCarts.add(list_common.get(i));
//							}
//							for (int i = 0; i < list_invalid_common.size(); i++) {
//								if (i < 10) {
//									listShopCarts.add(list_invalid_common.get(i));
//								}
//							}
//							mInvalidPosition = list_common.size();
//
//							if (mInvalidPosition > 0 || list_invalid_common.size() > 0) {
//								llBottom.setVisibility(View.VISIBLE);
//								listView.setVisibility(View.VISIBLE);
//								layout_nodata_shopcar.setVisibility(View.GONE);
//							} else {
//								llBottom.setVisibility(View.GONE);
//								listView.setVisibility(View.GONE);
//								layout_nodata_shopcar.setVisibility(View.VISIBLE);
//							}
//							// if (mFlag && (mInvalidPosition > 0 ||
//							// listShopCartsInValid.size() >
//							// 0) && !mPayFlag) {// &&
//							// listShopCarts.size()
//							mPayFlag = false; // !=
//							// 0
//							sp.clear();
//							spImg.clear();
//							for (int i = 0; i < mInvalidPosition; i++) {
//								sp.add(1);
//							}
//							for (int i = 0; i < mInvalidPosition; i++) {
//								spImg.add(1);
//							}
//							if (dao.queryCartSpecialCount(mContext) <= 0) {
//								recLen = 0;
//							}
//							// TODO:
//							if (shopCartSpecialInterface != null) {
//								shopCartSpecialInterface.getShopSpecialCount(dao.queryCartCount(context),
//										dao.queryCartSpecialCount(context));
//
//							}
//							adpter = new ShopCartAdpter(mContext, resource, listShopCarts, sp, spImg, 2,
//									mInvalidPosition, flag_activity);
//
//							adpter.setCartOncallback(ShopCartSpecialFragment.this);
//							listView.setAdapter(adpter);
//
//							tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
//									mContext.getResources().getDrawable(R.drawable.icon_dapeigou_celect), null, null,
//									null);
//							// setAllSelect(false);
//							// allFlag = false;
//							setAllSelect(true);
//							adpter.notifyDataSetChanged();
//						} else {
//							adpter = new ShopCartAdpter(mContext, resource, listShopCarts, sp, spImg, 2,
//									mInvalidPosition, flag_activity);
//
//							adpter.setCartOncallback(ShopCartSpecialFragment.this);
//							listView.setAdapter(adpter);
//
//							tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
//									mContext.getResources().getDrawable(R.drawable.icon_dapeigou_celect), null, null,
//									null);
//							// setAllSelect(false);
//							// allFlag = false;
//							setAllSelect(true);
//							adpter.notifyDataSetChanged();
//						}
//					} else {
//						adpter = new ShopCartAdpter(mContext, resource, listShopCarts, sp, spImg, 2, mInvalidPosition,
//								flag_activity);
//
//						adpter.setCartOncallback(ShopCartSpecialFragment.this);
//						listView.setAdapter(adpter);
//
//						tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
//								mContext.getResources().getDrawable(R.drawable.icon_dapeigou_celect), null, null, null);
//						// setAllSelect(false);
//						// allFlag = false;
//						setAllSelect(true);
//						adpter.notifyDataSetChanged();
//					}
//
//				}
//
//			}.execute();
//		} else {
//			adpter = new ShopCartAdpter(mContext, resource, listShopCarts, sp, spImg, 2, mInvalidPosition,
//					flag_activity);
//
//			adpter.setCartOncallback(this);
//			listView.setAdapter(adpter);
//
//			tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
//					mContext.getResources().getDrawable(R.drawable.icon_dapeigou_celect), null, null, null);
//			// setAllSelect(false);
//			// allFlag = false;
//			setAllSelect(true);
//			adpter.notifyDataSetChanged();
//		}
//
//		// adpter = new ShopCartAdpter(mContext, resource, listShopCarts, sp,
//		// spImg, 2, mInvalidPosition);
//		//
//		// adpter.setCartOncallback(this);
//		// listView.setAdapter(adpter);
//		//
//		// tv_allchoose.setCompoundDrawablesWithIntrinsicBounds(
//		// mContext.getResources().getDrawable(R.drawable.icon_dapeigou_celect),
//		// null, null, null);
//		// // setAllSelect(false);
//		// // allFlag = false;
//		// setAllSelect(true);
//		// adpter.notifyDataSetChanged();
//
//	}
//
//}
