package com.yssj.ui.fragment.orderinfo;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.MyListView;
import com.yssj.entity.Order;
import com.yssj.entity.OrderShop;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.infos.LogisticsInfoActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DateUtil;
import com.yssj.utils.WXminiAppUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*****
 * 订单详情界面
 * 
 * @author Administrator
 * 
 */
public class MySellOrderDetailsActivity extends BasicActivity implements
		OnClickListener {
	private MyListView listview;

	private Order order; // 订单
	private List<OrderShop> listOrderShops = new ArrayList<OrderShop>();
	private OrderListSonAdapter adapter;

	private TextView tvTitle_base;

	private TextView tv_order_status, tv_order_no, tv_create_time, tv_pay_time;

	private TextView tv_receiver, tv_phone, tv_detail_address;

	private TextView tv_buyer_name, tv_buyer_remark;

	private TextView tv_sum;

	private TextView tv_logistic_info, tv_add_time;
	private View logistic_divider;
	private RelativeLayout rel_logistic,mysell;

	private List<HashMap<String, Object>> mapLogistic;
	private ImageView img_right_icon;
	private String phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_sell_order_details);
		aBar.hide();
		Bundle b = getIntent().getExtras();
		order = (Order) b.getSerializable("order");
		if (order == null) {
			Toast.makeText(this, "该订单不存在，请重新确认", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		listOrderShops = order.getList();
		initView();
	}

	private void initView() {

		findViewById(R.id.img_back).setOnClickListener(this);
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("订单详情");

//System.out.println("这里的名称="+order.getOrder_name());
		/*
		 * 右上角点点点
		 */
		mysell = (RelativeLayout) findViewById(R.id.mysell);
		mysell.setBackgroundColor(Color.WHITE);
		img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		img_right_icon.setVisibility(View.GONE);

		findViewById(R.id.lin_contact_seller).setOnClickListener(this);// 联系买家
		findViewById(R.id.lin_call).setOnClickListener(this);// 拨打电话

		tv_order_status = (TextView) findViewById(R.id.tv_order_status);// 订单状态
		tv_order_no = (TextView) findViewById(R.id.tv_order_no);// 订单编号
		tv_receiver = (TextView) findViewById(R.id.tv_receiver);// 收货人
		tv_phone = (TextView) findViewById(R.id.tv_phone);// 电话号码
		tv_detail_address = (TextView) findViewById(R.id.tv_detail_address);// 收货地址

		tv_create_time = (TextView) findViewById(R.id.tv_create_time);
		tv_pay_time = (TextView) findViewById(R.id.tv_pay_time);

		tv_buyer_name = (TextView) findViewById(R.id.tv_buyer_name);
		tv_buyer_remark = (TextView) findViewById(R.id.tv_buyer_remark);

		rel_logistic = (RelativeLayout) findViewById(R.id.rel_logistic);
		rel_logistic.setOnClickListener(this);

		tv_logistic_info = (TextView) findViewById(R.id.tv_logistic_info);
		tv_add_time = (TextView) findViewById(R.id.tv_add_time);

		logistic_divider = findViewById(R.id.logistic_divider);

		tv_sum = (TextView) findViewById(R.id.tv_sum);

		listview = (MyListView) findViewById(R.id.listview);// 商品列表
		adapter = new OrderListSonAdapter(this, order, false,false,"");
		listview.setAdapter(adapter);

		getBuyerInfo();
		refreshView();
	}

	private void getBuyerInfo() {
		new SAsyncTask<Void, Void, HashMap<String, String>>(this, R.string.wait) {

			@Override
			protected HashMap<String, String> doInBackground(
					FragmentActivity context, Void... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getBuyerInfo(context, order.getOrder_code());
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, String> result, Exception e) {
				if (null == e) {
					String nickName = result.get("nick_name");
					if (!TextUtils.isEmpty(nickName)) {
						tv_buyer_name.setText("买家： " + nickName);
					}

					String remark = result.get("sell_msg");
					if ((!TextUtils.isEmpty(remark))
							&& !("null".equals(remark))) {
						tv_buyer_remark.setText("备注： " + remark);
					}
				}
				super.onPostExecute(context, result, e);
			}

		}.execute();
	}

	/****
	 * 刷新界面
	 */
	private void refreshView() {
		adapter.notifyDataSetChanged();
		int status = Integer.parseInt(order.getStatus().toString());
		switch (status) {
		case 1:
			tv_order_status.setText("待付款");
			logistic_divider.setVisibility(View.GONE);
			rel_logistic.setVisibility(View.GONE);
			break;
		case 2:
			tv_order_status.setText("待发货");
			logistic_divider.setVisibility(View.GONE);
			rel_logistic.setVisibility(View.GONE);
			break;
		case 3:
			getLogistics(order);
			tv_order_status.setText("待收货");
			break;
		case 4:
			getLogistics(order);
			tv_order_status.setText("待评价");
			break;
		case 5:
			getLogistics(order);
			tv_order_status.setText("退款中");
			break;
		case 6:
			getLogistics(order);
			tv_order_status.setText("已完结");
			break;

		case 7:
			getLogistics(order);
			tv_order_status.setText("延长收货");
			break;
		case 9:
			getLogistics(order);
			tv_order_status.setText("取消订单");
			break;

		}
		String consignee = order.getConsignee();
		if (!TextUtils.isEmpty(consignee)) {
			tv_receiver.setText("收货人： " + consignee);
		}
		phone = order.getPhone();
		if (!TextUtils.isEmpty(phone)) {
			tv_phone.setText("电话： " + phone);
		}

		String address = order.getAddress();
		if (!TextUtils.isEmpty(address)) {
			tv_detail_address.setText("收货人地址： " + address);
		}
		String order_code = order.getOrder_code();
		if (!TextUtils.isEmpty(order_code)) {
			tv_order_no.setText("订单号 ： " + order_code);
		}

		String createTime = DateUtil.FormatMillisecond(order.getAdd_time());
		if (!TextUtils.isEmpty(createTime)) {
			tv_create_time.setText("下单时间 ： " + createTime);
		}
		String payTime = DateUtil.FormatMillisecond(order.getPay_time());
		if (!TextUtils.isEmpty(payTime)) {
			tv_pay_time.setText("付款时间 ： " + payTime);
			if (order.getPay_time() == 0) {
				tv_pay_time.setVisibility(View.GONE);
			}
		}
		// String buyerName = order.get
		tv_sum.setText("共" + order.getShop_num() + "件商品    实付：¥"
				+ order.getPay_money() + "元");

		tv_buyer_name = (TextView) findViewById(R.id.tv_buyer_name);
		tv_buyer_remark = (TextView) findViewById(R.id.tv_buyer_remark);

	}

	// 去快递100中得到数据
	private void getLogistics(final Order order) {
		new SAsyncTask<Void, Void, List<HashMap<String, Object>>>(this,
				R.string.wait) {

			@Override
			protected List<HashMap<String, Object>> doInBackground(
					FragmentActivity context, Void... params) throws Exception {
				return ComModel2.getLogistics(context, order.getLogi_code());
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					List<HashMap<String, Object>> result, Exception e) {
				super.onPostExecute(context, result, e);

				if (null == e) {

					if (null == result) {
						return;
					}

					if (0 != result.size()) {
						tv_logistic_info.setText((CharSequence) result.get(0)
								.get("context"));
						tv_add_time.setText((CharSequence) result.get(0).get(
								"time"));

						logistic_divider.setVisibility(View.VISIBLE);
						rel_logistic.setVisibility(View.VISIBLE);
					}
					MySellOrderDetailsActivity.this.mapLogistic = result;
				}
			}

		}.execute();
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		Bundle bundle = null;
		switch (v.getId()) {
		case R.id.img_back:// 返回按钮
			finish();
			break;
		case R.id.img_right_icon:// 消息盒子
			WXminiAppUtil.jumpToWXmini(this);

			break;
		case R.id.lin_contact_seller:// 联系卖家
//			intent = new Intent(this, ChatActivity.class);
//			intent.putExtra("userId", order.getUser_id() + "");
//			intent.putExtra("sell", "seller");
//			startActivity(intent);
			WXminiAppUtil.jumpToWXmini(this);

			break;
		case R.id.lin_call:// 拨打电话
			String number = phone; // 客服电话
			// 用intent启动拨打电话
			intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;
		case R.id.rel_logistic:// 查看物流
			intent = new Intent(this, LogisticsInfoActivity.class);
			bundle = new Bundle();
			bundle.putSerializable("result", (Serializable) mapLogistic);
			intent.putExtras(bundle);
			bundle = new Bundle();
			bundle.putSerializable("order", order);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		}
	}

}
