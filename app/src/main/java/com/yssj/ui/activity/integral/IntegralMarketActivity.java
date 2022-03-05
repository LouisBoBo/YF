//package com.yssj.ui.activity.integral;
//
//import java.util.HashMap;
//import java.util.List;
//
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.plaview.MultiColumnPullToRefreshListView;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.adpter.IntegralMarketAdapter;
//import com.yssj.ui.base.BasicActivity;
//
//public class IntegralMarketActivity extends BasicActivity implements
//		MultiColumnPullToRefreshListView.OnRefreshListener,
//		MultiColumnPullToRefreshListView.OnLoadMoreListener {
//
//	private MultiColumnPullToRefreshListView i_list_view;
//	private int index = 1;
//	
//	private int mType = 1;// 1：初始化数据；2：加载更多数据
//	
//	private IntegralMarketAdapter mAdapter;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.integral_market);
//		i_list_view = (MultiColumnPullToRefreshListView) findViewById(R.id.i_list_view);
//		mAdapter = new IntegralMarketAdapter(this);
//		i_list_view.setAdapter(mAdapter);
//		i_list_view.setOnRefreshListener(this);
//		i_list_view.setOnLoadMoreListener(this);
//		initData(index+"");
//	}
//
//	private void initData(String index){
//		
//		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(this, 0){
//
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.getInteProdList(context, params[0]);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<HashMap<String, Object>> result) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result);
//				if (mType == 1) {
//					mAdapter.addItemTop(result);
//					i_list_view.onRefreshComplete();
//				} else if (mType == 2) {
//					mAdapter.addItemLast(result);
//					i_list_view.onLoadMoreComplete();
//				}
//			}
//			
//		}.execute(index);
//	}
//	
//	@Override
//	public void onLoadMore() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onRefresh() {
//		// TODO Auto-generated method stub
//
//	}
//
//}
