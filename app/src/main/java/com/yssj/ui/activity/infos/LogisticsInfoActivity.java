package com.yssj.ui.activity.infos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.MyListView;
import com.yssj.entity.Order;
import com.yssj.entity.OrderShop;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.setting.SettingActivity;
import com.yssj.ui.adpter.TimelineAdapter;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.orderinfo.OrderListSonAdapter;
import com.yssj.utils.WXminiAppUtil;

public class LogisticsInfoActivity extends BasicActivity {

	private List<HashMap<String, Object>> result;
	private MyListView list;

	private Order order;
	
	private TimelineAdapter mAdapter;
	
	private MyListView listview;
	private List<OrderShop> listOrderShops = new ArrayList<OrderShop>();
	private OrderListSonAdapter adapter;
	
	private TextView tv_logistic_name, tv_logistic_code;
	
	private TextView tvTitle_base;
	private ImageView img_right_icon;
	private  LinearLayout root;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		aBar.hide();
		result = (List<HashMap<String, Object>>) getIntent()
				.getSerializableExtra("result");
		order = (Order) getIntent().getSerializableExtra("order");
		listOrderShops = order.getList();
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_logistics);
		root = (LinearLayout) findViewById(R.id.root);
		root.setBackgroundColor(Color.WHITE);
		list = (MyListView) findViewById(R.id.list);
		tv_logistic_name = (TextView) findViewById(R.id.tv_logistic_name);
		tv_logistic_name.setText(YJApplication.mapKuaidi.get(order.getLogi_name()));
		tv_logistic_code = (TextView) findViewById(R.id.tv_logistic_code);
		tv_logistic_code.setText("运单编号："+order.getLogi_code());
		
		img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		img_right_icon.setVisibility(View.GONE);
		
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("查看物流");
		findViewById(R.id.img_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		listview = (MyListView) findViewById(R.id.listview);// 商品列表
		adapter = new OrderListSonAdapter(this, order, false,false,"");
		listview.setAdapter(adapter);
		if (null != result) {
			mAdapter = new TimelineAdapter(this, result);
			list.setAdapter(mAdapter);
		} else {
			getLogistics(order);
		}
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		Intent intent = null;
		switch (arg0.getId()) {
		case R.id.img_right_icon:
			WXminiAppUtil.jumpToWXmini(this);

			break;

		default:
			break;
		}
	}

	// 去快递100中得到数据
	private void getLogistics(final Order order) {
		new SAsyncTask<Void, Void, List<HashMap<String, Object>>>(this,
				R.string.wait) {

			@Override
			protected List<HashMap<String, Object>> doInBackground(
					FragmentActivity context, Void... params) throws Exception {
				return ComModel2.getLogistics(context,
						order.getLogi_code());
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					List<HashMap<String, Object>> result, Exception e) {
				super.onPostExecute(context, result, e);
				if(null == e){
				mAdapter = new TimelineAdapter(LogisticsInfoActivity.this, result);
				list.setAdapter(mAdapter);
				}
			}

		}.execute();
	}

}
