package com.yssj.ui.fragment.setting;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel;
import com.yssj.ui.adpter.LoginDeviceAdapter;
import com.yssj.ui.base.BaseFragment;

public class LoginDevicesFragment extends BaseFragment implements OnClickListener {
	private PullToRefreshListView lv_logindevices;
	private TextView tvTitle_base;
	private LinearLayout img_back;
	
	private LoginDeviceAdapter mLoginDeviceAdapter;
	
	private int currPage = 1;
	private boolean flag ;		// 判断上拉、下拉的标志
	
	public CloseLoginDevicesFragment mCloseLoginDevicesFragment;
	public interface CloseLoginDevicesFragment{
		public void closeFragment();
	}
	
	public void setCloseLoginDevicesFragment(Activity activity){
		this.mCloseLoginDevicesFragment = (CloseLoginDevicesFragment) activity;
	}

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_setting_secure_logindevice, null);
		lv_logindevices = (PullToRefreshListView) view.findViewById(R.id.lv_logindevices);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("登录设备");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void initData() {
		queryLoginDeviceData();
		
		mLoginDeviceAdapter = new LoginDeviceAdapter(context);
		
		lv_logindevices.setAdapter(mLoginDeviceAdapter);
		
		mLoginDeviceAdapter.initIndicator(lv_logindevices);
		
		lv_logindevices.setMode(Mode.BOTH);
		lv_logindevices.setEnabled(false);
		
		lv_logindevices.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
					flag = true;
					
					currPage = 1 ;
					queryLoginDeviceData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
					flag = false;
					currPage ++;
					queryLoginDeviceData();
			}
		});
	}
	
	private void queryLoginDeviceData() {
		
		new SAsyncTask<Integer, Void, List<HashMap<String, Object>> >((FragmentActivity) context,null, R.string.wait){

			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Integer... params) throws Exception {
				
				return ComModel.loginDevices(context, currPage);
			}

			@Override
			protected void onPostExecute(List<HashMap<String, Object>> result) {
				super.onPostExecute(result);
				
				if(flag){
					mLoginDeviceAdapter.addItemFirst(result!=null?result:null);
				}else{
					mLoginDeviceAdapter.addItemLast(result!=null?result:null);
				}
				
				mLoginDeviceAdapter.notifyDataSetChanged();
				lv_logindevices.onRefreshComplete();
			}
			
		}.execute(currPage);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			mCloseLoginDevicesFragment.closeFragment();
			break;

		default:
			break;
		}
	}

}
