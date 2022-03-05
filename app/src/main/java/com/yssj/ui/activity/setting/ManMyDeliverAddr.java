package com.yssj.ui.activity.setting;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.data.DBService;
import com.yssj.entity.DeliveryAddress;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.adpter.MyDeliverListAdapter;
import com.yssj.ui.base.BasicActivity;

public class ManMyDeliverAddr extends BasicActivity {

	private ListView lv;
	private MyDeliverListAdapter mAdapter;
	private List<DeliveryAddress> listData;

	private TextView tvTitle_base;
	private LinearLayout img_back;
	private LinearLayout ll_no_address;
	private RelativeLayout rl_btn_add_address,root;
	private DBService db;

	private String flag = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.manage_deliver_address);
		db = new DBService(this);
		flag = getIntent().getStringExtra("flag");
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

	private void initView() {
		root =(RelativeLayout) findViewById(R.id.root);
		root.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("我的收货地址");

		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
//		findViewById(R.id.rel_right).setOnClickListener(this);
		Button btn_right = (Button)findViewById(R.id.btn_right);
		btn_right.setOnClickListener(this);
		btn_right.setText("管理");
		btn_right.setTextColor(Color.parseColor("#222222"));
		btn_right.setTextSize(16);
		ll_no_address = (LinearLayout) findViewById(R.id.ll_no_address);

		rl_btn_add_address = (RelativeLayout) findViewById(R.id.rl_btn_add_address);
		rl_btn_add_address.setOnClickListener(this);
		
		if(("submitmultishop").equals(flag)){
			btn_right.setVisibility(View.VISIBLE);
			rl_btn_add_address.setVisibility(View.GONE);
		}

		lv = (ListView) findViewById(R.id.lv);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				DeliveryAddress da = (DeliveryAddress) arg0
						.getItemAtPosition(arg2);
				Intent intent;
				if ("hHDetailAuditPassFragment".equals(flag)) { // 从换货过来选地址id
					intent = new Intent();
					intent.putExtra("id", String.valueOf(da.getId()));
					intent.putExtra("address", da.getDetailAddress());
					setResult(102, intent);
					finish();
				} else if ("submitmultishop".equals(flag)) {    // 提交订单选择地址id
//					setDefaultAddress(da);
					intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putSerializable("item", da);
					intent.putExtras(bundle);
					setResult(1001, intent);
					finish();
				} else {
					intent = new Intent(ManMyDeliverAddr.this,
							UpdateDeliverAddressActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("item", da);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});

	}

	// 设置默认地址
//	private void setDefaultAddress(final DeliveryAddress da) {
//
//		new SAsyncTask<String, Void, ReturnInfo>(this, null, R.string.wait) {
//
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context,
//					String... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.updateReceiverAddr(context,
//						String.valueOf(da.getProvince()),
//						String.valueOf(da.getCity()),
//						String.valueOf(da.getArea()),
//						String.valueOf(da.getStreet()), da.getConsignee(),
//						da.getPhone(), da.getPostcode(), da.getDetailAddress(),
//						da.getId(), 1);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					ReturnInfo result, Exception e) {
//				// TODO Auto-generated method stub
//				if(null == e){
//				if (result.getStatus().equals("1")) {
//					Toast.makeText(ManMyDeliverAddr.this, result.getMessage(),
//							Toast.LENGTH_SHORT).show();
//					ManMyDeliverAddr.this.finish();
//				}
//				}
//				super.onPostExecute(context, result,e );
//			}
//
//		}.execute(String.valueOf(da.getProvince()),
//				String.valueOf(da.getCity()), String.valueOf(da.getArea()),
//				String.valueOf(da.getStreet()), da.getConsignee(),
//				da.getPhone(), da.getPostcode(), da.getDetailAddress());
//	}

	private void initData() {
		new SAsyncTask<Void, Void, List<DeliveryAddress>>(this, R.string.wait) {

			@Override
			protected List<DeliveryAddress> doInBackground(
					FragmentActivity context, Void... params) throws Exception {
				listData = ComModel2.getDeliverAddr(context);
				if (listData != null && !listData.isEmpty()) {
					for (int i = 0; i < listData.size(); i++) {
						StringBuffer sb = new StringBuffer();


                        if(null != listData.get(i).getProvince()&& 0 < listData.get(i).getProvince()) {


                            sb.append(db
                                    .query("select * from areatbl where id = '"
                                            + listData.get(i).getProvince() + "'")
                                    .get(0).get("AreaName"));


                        }



                        if(null != listData.get(i).getCity()&& 0 < listData.get(i).getCity()) {

                            sb.append(db
                                    .query("select * from areatbl where id = '"
                                            + listData.get(i).getCity() + "'")
                                    .get(0).get("AreaName"));
                        }







						if(null != listData.get(i).getArea()&& 0 < listData.get(i).getArea()){
						sb.append(db
								.query("select * from areatbl where id = '"
										+ listData.get(i).getArea() + "'")
								.get(0).get("AreaName"));
						}
						if (null != listData.get(i).getStreet()
								&& 0 != listData.get(i).getStreet()) {
							sb.append(db
									.query("select * from areatbl where id = '"
											+ listData.get(i).getStreet() + "'")
									.get(0).get("AreaName"));
						}
						sb.append(listData.get(i).getAddress());

						listData.get(i).setDetailAddress(sb.toString());
					}
				}
				return listData;
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					List<DeliveryAddress> result, Exception e) {
				super.onPostExecute(context, result, e);
				if(null == e){
				if (result.isEmpty() || result == null) {
					ll_no_address.setVisibility(View.VISIBLE);
					lv.setVisibility(View.GONE);
				} else {
					lv.setVisibility(View.VISIBLE);
					ll_no_address.setVisibility(View.GONE);

					mAdapter = new MyDeliverListAdapter(ManMyDeliverAddr.this,
							result);
					lv.setAdapter(mAdapter);
				}
				}
			}

		}.execute();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent intent = null;
		switch (v.getId()) {
		case R.id.rl_btn_add_address:
			intent = new Intent(this, SetDeliverAddressActivity.class);
			startActivity(intent);
			break;
		case R.id.img_back:
			finish();
			break;
		case R.id.rel_right:
		case R.id.btn_right:
			intent = new Intent(this, ManaMyDeliverAddr.class);
			startActivity(intent);
			break;
		
		default:
			break;
		}
	}

}
