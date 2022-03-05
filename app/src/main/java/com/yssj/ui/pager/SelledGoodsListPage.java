package com.yssj.ui.pager;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.Order;
import com.yssj.entity.Store;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.payback.PaybackCommonFragmentActivity;
import com.yssj.ui.adpter.PayBackSellPagerAdapter;
import com.yssj.ui.base.BasePager;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.YCache;

public class SelledGoodsListPage extends BasePager implements OnClickListener {

	public SelledGoodsListPage(Context context) {
		super(context);
	}

	
	private LinearLayout layout_nodata;
	private PullToRefreshListView lv_common;
	private int currPage = 1;
	private PayBackSellPagerAdapter mAdapter;
	
	private Button btn_to_shop;
	
	private boolean flag = true;		// 判断上拉、下拉的标志
	
	private String store_code = "";

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.pulltorefreshlistview_common, null);
		lv_common = (PullToRefreshListView) view.findViewById(R.id.lv_common);
		
		layout_nodata = (LinearLayout) view.findViewById(R.id.layout_nodata);
		
		btn_to_shop = (Button) view.findViewById(R.id.btn_to_shop);
		btn_to_shop.setOnClickListener(this);
		
		return view;
	}
	
	@Override
	public void initData() {
		queryData();
		mAdapter = new PayBackSellPagerAdapter(context,"selledGoodsListPage");
		lv_common.setAdapter(mAdapter);
		
		super.initIndicator(lv_common);
		
		lv_common.setMode(Mode.BOTH);
		
		lv_common.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
					flag = true;
					currPage = 1 ;
					queryData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
					flag = false;
					currPage ++;
					queryData();
			}
		});
	}
	
	
	
	private void queryData() {
		Store cacheStore = YCache.getCacheStore(context);
		
		if(cacheStore != null){
			store_code = cacheStore.getS_code();
		}
		new SAsyncTask<Integer, Void, List<Order> >((FragmentActivity) context,null, R.string.wait){

			@Override
			protected List<Order> doInBackground(FragmentActivity context, Integer... params) throws Exception {
				
				return ComModel2.getSellOrderList(context, currPage, 0, "-1", store_code);
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
		case R.id.btn_to_shop:
			//((PaybackCommonFragmentActivity)context).setResult();
			CommonUtils.finishActivity(MainMenuActivity.instances);
			Intent intent = new Intent(context, MainMenuActivity.class);
			intent.putExtra("toYf", "toYf");
			context.startActivity(intent);
			((Activity) context).finish();
			
			break;
		}
	}
	
}
