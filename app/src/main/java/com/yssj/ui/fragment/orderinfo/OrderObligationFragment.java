package com.yssj.ui.fragment.orderinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.entity.Order;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.shopdetails.OrderPaymentActivity;
import com.yssj.ui.fragment.orderinfo.OrderObligationListAdapter.onCheckedCallback;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.wxpay.WxPayUtil;

@SuppressLint("NewApi")
public class OrderObligationFragment extends Fragment implements
		OnClickListener, OnItemClickListener, onCheckedCallback {

	private int status;

	private PullToRefreshListView listView;
	private OrderObligationListAdapter mAdapter;
	private long nowTime = 0;
	private int index = 1;

	private boolean isAdd = false;

	private List<Order> orders = new ArrayList<Order>();

	private TextView tv_order_info;

	private List<Order> listChecked = new ArrayList<Order>();

	private AlertDialog dialog2 = null, dialogPay = null;

	IWXAPI msgApi;// 微信api

	private String orderNo;
	private Bundle bundle;
	private static final int SDK_PAY_FLAG = 1;

	private RelativeLayout rel_bottom;

	private Intent intent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil
			.getImage());

	private LinearLayout layout_nodata;
	private Button btn_to_shop;

	private TextView tvCheckAll;
	private boolean isAllChecked;

	protected final int PAGESIZE = 10;

	protected boolean isComplete = false;
	private Context mContext;

	public OrderObligationFragment(int status) {
		this.status = status;
	}

	public OrderObligationFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		msgApi = WXAPIFactory.createWXAPI(getActivity(), null);
		msgApi.registerApp(WxPayUtil.APP_ID);
		mContext=getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.order_obligation_fragment, container,
				false);
		initView(v);
		return v;
	}

	private void initView(View v) {

		layout_nodata = (LinearLayout) v.findViewById(R.id.layout_nodata);

		btn_to_shop = (Button) v.findViewById(R.id.btn_to_shop);
		btn_to_shop.setOnClickListener(this);

		tv_order_info = (TextView) v.findViewById(R.id.tv_order_info);
		tvCheckAll = (TextView) v.findViewById(R.id.tv_check_all);
		tvCheckAll.setOnClickListener(this);

		rel_bottom = (RelativeLayout) v.findViewById(R.id.rel_bottom);
		v.findViewById(R.id.btn_pay).setOnClickListener(this);
		listView = (PullToRefreshListView) v.findViewById(R.id.trade_listview);
		mAdapter = new OrderObligationListAdapter(getActivity(), orders, this,
				v.findViewById(R.id.main));
		listView.setAdapter(mAdapter);
		mAdapter.setCartOncallback(this);
		listView.setOnItemClickListener(this);
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				index = 1;
				isAdd = false;

				getData(index);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				isAdd = true;
				if (isComplete) {
					// Toast.makeText(getActivity(), "没有数据",
					// Toast.LENGTH_LONG).show();
					listView.onRefreshComplete();
				} else {
					getData(++index);
				}
			}
		});
	}

	public void showNoDataPage() {
		layout_nodata.setVisibility(View.VISIBLE);
		listView.setVisibility(View.GONE);
		rel_bottom.setVisibility(View.GONE);
		listView.onRefreshComplete();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		index = 1;
		isAdd = false;
		if (!YJApplication.instance.isLoginSucess()) {// 没登录的情况下提示去登录
			toLogin();
		}else {
			getData(index);
		}
	}
	private void toLogin() {
		Intent intent = new Intent(mContext, LoginActivity.class);
		intent.putExtra("login_register", "login");
		((Activity)mContext).startActivity(intent);
		((Activity)mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
	}

	private void getData(int index) {
		new SAsyncTask<Integer, Void, List<Order>>(getActivity(), null, 0) {
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				 LoadingDialog.show((FragmentActivity) mContext);
			}

			@Override
			protected List<Order> doInBackground(FragmentActivity context,
					Integer... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getOrderList(context, params[0], status, "");
			}

			@Override
			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context,
					List<Order> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result);
				LoadingDialog.hide(mContext);
				if (e != null) {// 查询异常
					layout_nodata.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
					rel_bottom.setVisibility(View.GONE);
					listView.onRefreshComplete();
				} else {// 查询商品详情成功，刷新界面
					LogYiFu.e("TAG", "result.size=" + result.size());
					if (isAdd) {
						orders.addAll(result);
					} else {
						if (result != null && result.size() == 0) {
							layout_nodata.setVisibility(View.VISIBLE);
							listView.setVisibility(View.GONE);
							rel_bottom.setVisibility(View.GONE);
						} else {
							rel_bottom.setVisibility(View.VISIBLE);
						}
						orders.clear();
						orders.addAll(result);
					}
					if (result.size() < PAGESIZE) {
						isComplete = true;
					} else {
						isComplete = false;
					}
					setAllSelected(false);
					mAdapter.notifyDataSetChanged();
					listView.onRefreshComplete();
				}

			}

		}.execute(index);
	}

	// 待用(为了获取服务器当前时间now)
	private void getDataModify(int index) {
		new SAsyncTask<Integer, Void, HashMap<String, Object>>(getActivity(),
				null, 0) {

			@Override
			protected HashMap<String, Object> doInBackground(
					FragmentActivity context, Integer... params)
					throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getOrderListModify(context, params[0], status,
						"");
			}

			@Override
			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, Object> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result);
				if (e != null) {// 查询异常
					layout_nodata.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
					rel_bottom.setVisibility(View.GONE);
					listView.onRefreshComplete();
				} else {// 查询商品详情成功，刷新界面
					LogYiFu.e("TAG", "result.size=" + result.size());
					List<Order> or = (List<Order>) result.get("orders");
					nowTime = (Long) result.get("now");
//					System.out.println("***//***now" + nowTime);
					if (isAdd) {
						orders.addAll(or);
					} else {
						if (result != null && result.size() == 0) {
							layout_nodata.setVisibility(View.VISIBLE);
							listView.setVisibility(View.GONE);
							rel_bottom.setVisibility(View.GONE);
						} else {
							rel_bottom.setVisibility(View.VISIBLE);
						}
						orders.clear();
						orders.addAll(or);
					}
					if (result.size() < PAGESIZE) {
						isComplete = true;
					} else {
						isComplete = false;
					}
					setAllSelected(false);
//					mAdapter.notifyDataSetChanged();
					listView.onRefreshComplete();
				}
				Intent intent = new Intent(getActivity(),
						OrderDetailsActivity.class);
				intent.putExtra("nowTime", nowTime);

				intent.putExtras(bundle);
				startActivityForResult(intent, 103);

			}

		}.execute(index);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// Order order=orders.get(arg2);
		// Date date=order.getLast_time();
		// TODO Auto-generated method stub
		bundle = new Bundle();
		bundle.putSerializable("order", orders.get(arg2));
		getDataModify(index);

	}

	@Override
	public void checked(int position, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked) {
			listChecked.add(orders.get(position));
		} else {
			listChecked.remove(orders.get(position));
		}

		int sum = 0;
		double price = 0.0;
		for (Order order : listChecked) {
			sum = sum + order.getShop_num();
			price = price + order.getRemain_money();
		}

		String priceStr = new java.text.DecimalFormat("#0.00").format(price);
		tv_order_info.setText("共" + sum + "件商品\n总金额 " + priceStr + "元");
		if (listChecked.size() == orders.size()) {// 全选状态

			tvCheckAll.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.tvchooseno_selected, 0, 0, 0);
			isAllChecked = true;
		} else {
			tvCheckAll.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.tvchooseno_normal, 0, 0, 0);
			isAllChecked = false;
		}
	}

	// 多个物品支付
	private void multiOrderPay(View v, final String wxPayUrl) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < listChecked.size(); i++) {
			sb.append(listChecked.get(i).getOrder_code());
			if (i != listChecked.size() - 1) {
				sb.append(",");
			}
		}

		new SAsyncTask<StringBuffer, Void, HashMap<String, String>>(
				getActivity(), v, R.string.wait) {

			@Override
			protected HashMap<String, String> doInBackground(
					FragmentActivity context, StringBuffer... params)
					throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.payOrders(context, params[0].toString());
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, String> result, Exception e) {
				// TODO Auto-generated method stub
				if (null == e) {
					orderNo = result.get("g_code");
					// showPayDialog(orderNo, YUrl.ALI_NOTIFY_URL_MULTI,
					// G150829eIHL5iXE
					// result.get("orderPrice"), wxPayUrl, false);
					intentTOPayment(orderNo,
							Double.valueOf(result.get("orderPrice")), true);
				}
				super.onPostExecute(context, result, e);
			}

		}.execute(sb);
	}

	private View v;

	private void intentTOPayment(String order_code, double price,
			boolean isMulti) {
		//判断有 已经揭晓的 夺宝商品 正在支付
//		for (Order order : listChecked) {
//			long nowTimes = new Date().getTime();
//			long issueEndTime = order.getIssue_endtime();
//			if(order.getShop_from() == 4 && issueEndTime!=0 && nowTimes>=issueEndTime){
//				ToastUtil.showShortText(getActivity(), "中奖号码已经揭晓！");
//				return;
//			}
//		}
		Intent intent = new Intent(getActivity(), OrderPaymentActivity.class);
		boolean existBaoOrder = false;
		boolean existDuoOrder = false;
		
		Order baoOrder= null;
		Order duoOrder = null;
		for (Order order : listChecked) {
			if (order.getShop_from() == 3) {
				existBaoOrder = true;
				baoOrder = order;
				break;
			}
		}
		
		for (Order order : listChecked) {
			if (order.getShop_from() == 4) {
				existDuoOrder = true;
				duoOrder = order;
				break;
			}
		}
				
		if(existBaoOrder&&!existDuoOrder){
			intent.putExtra("signShopDetail", "SignShopDetail");
			intent.putExtra("signType", this.getSignType(baoOrder));
		}else if(existDuoOrder){
			intent.putExtra("isDuobao", true);
			intent.putExtra("signType", this.getSignType(duoOrder));
		}
		
		intent.putExtra("order_code", order_code);
		intent.putExtra("totlaAccount", price);
		intent.putExtra("isMulti", isMulti);
		intent.putExtra("single", "single");
		intent.putExtra("paycount", "paycount");
		Bundle bundle = new Bundle();
		bundle.putSerializable("listOrder", (Serializable) listChecked);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	private int getSignType(Order order){
		if(order.getShop_from()==4||order.getShop_from()==3){
			String[] str  = (String.valueOf(order.getOrder_price())).split("\\.");
			String type  = str[0];
			return Integer.parseInt(type);
		}else {
			return 0;
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_pay:// 付款
			if (listChecked.size() <= 0) {
				ToastUtil.showShortText(getActivity(), "请选择要支付的订单");
				return;
			}
			if (listChecked.size() > 1) {
				multiOrderPay(v, YUrl.WX_PAY_MULTI);
			} else {
//				if (listChecked.get(0).getList().size() == 1) {
//					String shop_code = listChecked.get(0).getList().get(0)
//							.getShop_code();
//					ShareUtil.getPicPath(shop_code, null, getActivity());
//				}
				intentTOPayment(listChecked.get(0).getOrder_code(), listChecked
						.get(0).getRemain_money(), false);
				// showPayDialog(listChecked.get(0).getOrder_code(),
				// YUrl.ALI_NOTIFY_URL_SINGLE, listChecked.get(0)
				// .getOrder_price() + "", YUrl.WX_PAY_SINGLE,
				// true);
			}

			break;
		case R.id.btn_to_shop:
			CommonUtils.finishActivity(MainMenuActivity.instances);

			intent = new Intent(getActivity(), MainMenuActivity.class);
			intent.putExtra("toYf", "toYf");
			startActivity(intent);
			getActivity().finish();
			break;
		case R.id.tv_check_all:// 全选
			if (isAllChecked) {
				isAllChecked = false;
				setAllSelected(isAllChecked);
			} else {
				isAllChecked = true;
				setAllSelected(isAllChecked);
			}
			mAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
	}

	// 设置全选
	private void setAllSelected(boolean isCheckAll) {
		int sum = 0;
		double price = 0;
		if (isCheckAll) {
			tvCheckAll.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.tvchooseno_selected, 0, 0, 0);
			listChecked.clear();
			for (int i = 0; i < orders.size(); i++) {
				Order order = orders.get(i);
				listChecked.add(order);
				sum = sum + order.getShop_num();
				price = price + order.getRemain_money();
			}
			String priceStr = new java.text.DecimalFormat("#0.00")
					.format(price);
			tv_order_info.setText("共" + sum + "件商品\n总金额 " + priceStr + " 元");
			// setCheckBox(true);
			mAdapter.setSelected(true);
		} else {
			listChecked.clear();
			tvCheckAll.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.tvchooseno_normal, 0, 0, 0);
			tv_order_info.setText("共0件商品\n总金额 0.00 元");
			mAdapter.setSelected(false);
			// setCheckBox(false);
		}

	}

	/*
	 * public void setCheckBox(boolean isCheckAll) { int count =
	 * listView.getChildCount(); for (int i = 0; i < count; i++) { LinearLayout
	 * layout = (LinearLayout) listView.getChildAt(i); int c =
	 * layout.getChildCount(); for (int j = 0; j < c; j++) { View view =
	 * layout.getChildAt(j); if (view instanceof CheckBox) { if (isCheckAll) {
	 * ((CheckBox) view).setChecked(true); } else {
	 * 
	 * ((CheckBox) view).setChecked(false); } } } } }
	 */

	// 计时器
	class TimeCount extends CountDownTimer {
		private Button btn = null;

		public TimeCount(long millisInFuture, long countDownInterval, Button btn) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
			this.btn = btn;

		}

		@Override
		public void onFinish() {// 计时完毕时触发
			try {
				// btn.setText("重发(30)");
				// btn.setEnabled(true);
				dialog2.dismiss();
				startActivityForResult(intent, 101);
				// btn.setBackgroundResource(R.color.actionbar_bn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			try {
				// btn.setEnabled(false);
				// btn.setBackgroundResource(R.color.time_count);
				btn.setText("" + millisUntilFinished / 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
