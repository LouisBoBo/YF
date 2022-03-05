package com.yssj.ui.fragment.payback;

import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.Order;
import com.yssj.entity.ReturnShop;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.payback.PayBackDetailsActivity;
import com.yssj.ui.activity.payback.PaybackCommonFragmentActivity;
import com.yssj.ui.activity.setting.SettingActivity;
import com.yssj.ui.adpter.PayBackApplyPagerAdapter;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.fragment.BackHandledFragment;
import com.yssj.ui.fragment.orderinfo.OrderDetailsActivity;
import com.yssj.ui.fragment.payback.tk.TKFragment;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAppUtil;

public class IWantApplyFragment extends BackHandledFragment implements OnItemClickListener{
	
	private PullToRefreshListView lv_common;
	private boolean hadIntercept;
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private ImageView img_right_icon;
	
	private int currPage = 1;
	private PayBackApplyPagerAdapter mAdapter;
	
	private Button btn_to_shop;
	
	private boolean flag = true;		// 判断上拉、下拉的标志
	
	private LinearLayout layout_nodata;


	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_payback_iwant_apply, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("请选择退款商品");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		/*
		 * 右上角点点点
		 */
		img_right_icon = (ImageView) view.findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		img_right_icon.setVisibility(View.GONE);
		
		lv_common = (PullToRefreshListView) view.findViewById(R.id.lv_common);
//		lv_common.setOnItemClickListener(this);
		
		layout_nodata = (LinearLayout) view.findViewById(R.id.layout_nodata);
		
		btn_to_shop = (Button) view.findViewById(R.id.btn_to_shop);
		btn_to_shop.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void initData() {
		queryAllData();
		mAdapter = new PayBackApplyPagerAdapter(context,"iWantApplyFragment");
		lv_common.setAdapter(mAdapter);
		
		super.initIndicator(lv_common);
		
		lv_common.setMode(Mode.BOTH);
		
		lv_common.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
					flag = true;
					
					currPage = 1 ;
					queryAllData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
					flag = false;
					currPage ++;
					queryAllData();
			}
		});
	}
	
	private void queryAllData() {
		new SAsyncTask<Integer, Void, List<Order> >((FragmentActivity) context,null, R.string.wait){

			@Override
			protected List<Order> doInBackground(FragmentActivity context, Integer... params) throws Exception {
				//status 为 -1 查询 没有未付款的数据
				return ComModel2.getOrderList(context, currPage, -1, "");
			}

			@Override
			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context,
					List<Order> result, Exception e) {
				super.onPostExecute(context, result, e);
				
				if (e != null) {// 查询异常
					layout_nodata.setVisibility(View.VISIBLE);
					lv_common.setVisibility(View.GONE);
					lv_common.onRefreshComplete();
				} else {
					
					if(flag){
						
						if(result != null && result.size() == 0){
							layout_nodata.setVisibility(View.VISIBLE);
							lv_common.setVisibility(View.GONE);
						}
						
						mAdapter.addItemFirst(result != null ?result:null);
					}else{
						mAdapter.addItemLast(result != null ?result:null);
					}
					
					mAdapter.notifyDataSetChanged();
					lv_common.onRefreshComplete();
					
				}
			}
			
		}.execute(currPage);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			Fragment mFragment = new PayBackListFragment();
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).addToBackStack(null).commit();
			break;
			
		case R.id.btn_to_shop:
			CommonUtils.finishActivity(MainMenuActivity.instances);

			Intent intent2 = new Intent(getActivity(), MainMenuActivity.class);
			intent2.putExtra("toYf", "toYf");
			startActivity(intent2);
			getActivity().finish();
			break;
		case R.id.img_right_icon:// 消息盒子
			WXminiAppUtil.jumpToWXmini(getActivity());

			break;
		}

	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		/**
		Order order = (Order) arg0.getItemAtPosition(arg2);
		
		if("1".equals(order.getStatus().toString()) && order.getChange()==0){	// 还未付款的
//			ToastUtil.showLongText(context, "该商品未付款，暂不能申请售后");
			Intent intent = new Intent(context, OrderDetailsActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("order", order);
			intent.putExtras(bundle);
			context.startActivity(intent);
		}else if("2".equals(order.getStatus().toString()) && order.getChange()==0){
			
			Fragment mFragment = new TKFragment();
			Bundle bundle = new Bundle();
			bundle.putString("order_code", order.getOrder_code());
			bundle.putString("order_price", order.getOrder_price() + "");
			bundle.putString("flag", "iWantApplyFragment");
			mFragment.setArguments(bundle);
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			
		}else if(! "1".equals(order.getStatus().toString()) && ! "2".equals(order.getStatus().toString()) && ! "9".equals(order.getStatus().toString()) && order.getChange()==0){
			
			Bundle bundle = new Bundle();
			bundle.putString("order_code", order.getOrder_code());
			bundle.putString("order_price",order.getOrder_price() + "");
			if("4".equals(order.getStatus().toString())||"5".equals(order.getStatus().toString())||"6".equals(order.getStatus().toString())){
				bundle.putBoolean("isHH", true);
			}
			PayBackChoiceServiceFragment mFragment = new PayBackChoiceServiceFragment();
			mFragment.setArguments(bundle);
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			
		}else if("9".equals(order.getStatus().toString()) && order.getChange()==0){
			
			ToastUtil.showShortText(getActivity(), "该订单已被取消，不能再申请售后");
			
		}else if(order.getChange() != 0){	// 已经申请过的
			ToastUtil.showShortText(context, "该商品已经申请过售后，不能再次申请 !");
		}else if("1".equals(order.getIs_kick())){
			ToastUtil.showShortText(context, "该订单已过期，不能再申请售后 !");
		}*/
	}
	
	
	public void getItemValue(final String order_code,final int is_kick){
		new SAsyncTask<Void, Void, ReturnShop>((FragmentActivity)context, R.string.wait){

			@Override
			protected ReturnShop doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel2.checkPayback(context, order_code);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnShop result, Exception e) {
				super.onPostExecute(context, result, e);
				if(null == e){
				if(is_kick == 1){
					ToastUtil.showShortText(context, "该订单已过期，不能再申请售后 !");
				}else{
					Intent intent = new Intent(context, PayBackDetailsActivity.class);
					intent.putExtra("returnShop", (Serializable)result);
					context.startActivity(intent);
				}
				}
			}
			
		}.execute();
	}

	@Override
	public boolean onBackPressed() {
		if (hadIntercept) {
			return false;
		} else {
			// Toast.makeText(getActivity(), "Click MyFragment",
			// Toast.LENGTH_SHORT).show();
			Fragment mFragment = new PayBackListFragment();
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).addToBackStack(null).commit();
			hadIntercept = true;
			return true;
		}
	}
	

}
