package com.yssj.ui.activity.shopdetails;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.DeliveryAddress;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.setting.SetDeliverAddressActivity;
import com.yssj.ui.adpter.MyDeliverListAdapter;
import com.yssj.ui.base.BasicActivity;

public class ChooseMyDeliverAddr extends BasicActivity {

	private ListView lv;
	private MyDeliverListAdapter mAdapter;
	private List<DeliveryAddress> listData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_deliver_address);
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

	private void initView() {
		lv = (ListView) findViewById(R.id.lv);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				DeliveryAddress da = (DeliveryAddress) arg0.getItemAtPosition(arg2);
				Intent intent = new Intent(ChooseMyDeliverAddr.this, SubmitOrderActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("item", da);
				intent.putExtras(bundle);
				setResult(1001, intent);
				ChooseMyDeliverAddr.this.finish();
			}
		});

		findViewById(R.id.btn_add_address).setOnClickListener(this);
	}

	private void initData() {
		new SAsyncTask<Void, Void, List<DeliveryAddress>>(this, R.string.wait) {

			@Override
			protected List<DeliveryAddress> doInBackground(
					FragmentActivity context, Void... params) throws Exception {
				// TODO Auto-generated method stub
				listData = ComModel2.getDeliverAddr(context);
				return ComModel2.getDeliverAddr(context);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					List<DeliveryAddress> result, Exception e) {
				// TODO Auto-generated method stub
				if(null == e){
				mAdapter = new MyDeliverListAdapter(ChooseMyDeliverAddr.this,
						result);
				lv.setAdapter(mAdapter);
				}
				super.onPostExecute(context, result, e);
			}

		}.execute();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_add_address:
			Intent intent = new Intent(this, SetDeliverAddressActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
