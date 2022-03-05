//package com.yssj.ui.activity.league;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.yssj.activity.R;
//import com.yssj.app.AppManager;
//import com.yssj.app.SAsyncTask;
//import com.yssj.entity.Order;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.MainFragment;
//import com.yssj.ui.activity.MainMenuActivity;
//import com.yssj.utils.LogYiFu;
///****
// * 买单列表界面
// * @author Administrator
// *
// */
//@SuppressLint("NewApi")
//public class OrderInfoFragment extends Fragment implements OnClickListener{
//
//	private int status;
//
//	private PullToRefreshListView listView;
////	private OrderListAdapter mAdapter;
//
//	private int index = 1;
//
//	private boolean isAdd = false;
//
//	private List<Order> orders = new ArrayList<Order>();
//
//	private LinearLayout layout_nodata;
//	private Button btn_to_shop;
//
//	protected int PAGESIZE = 10;
//	private boolean isComplete = false;
//
//	private View v;
//	private String user_id;
//
//	@SuppressLint("ValidFragment")
//	public OrderInfoFragment(int status, String userId) {
//		this.status = status;
//		this.user_id = userId;
//	}
//
//	public OrderInfoFragment() {
//	}
//
//	@Override
//	public void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		getData(index);
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		LogYiFu.e("nearbyFragment onCreate", "nearbyFragment onCreate");
//
//
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		v = inflater.inflate(R.layout.trade_fragment, container,
//				false);
//		initView(v);
//		return v;
//	}
//
//	private void initView(View v){
//		layout_nodata = (LinearLayout) v.findViewById(R.id.layout_nodata);
//
//		btn_to_shop = (Button) v.findViewById(R.id.btn_to_shop);
//		btn_to_shop.setOnClickListener(this);
//
//		listView = (PullToRefreshListView) v.findViewById(R.id.trade_listview);
////		mAdapter = new OrderListAdapter(getActivity(), orders,this,v.findViewById(R.id.main));
////		listView.setAdapter(mAdapter);
//		listView.setMode(Mode.BOTH);
//		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
//
//			@Override
//			public void onPullDownToRefresh(
//					PullToRefreshBase<ListView> refreshView) {
//				// TODO Auto-generated method stub
//				index = 1;
//				isAdd = false;
//				getData(index);
//			}
//
//			@Override
//			public void onPullUpToRefresh(
//					PullToRefreshBase<ListView> refreshView) {
//				// TODO Auto-generated method stub
//				isAdd = true;
//				if(isComplete){
//					Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_LONG).show();
//				}else {
//					getData(++index);
//				}
//			}
//		});
//	}
//
//	public void showNoDataPager(){
//		layout_nodata.setVisibility(View.VISIBLE);
//		listView.setVisibility(View.GONE);
//		listView.onRefreshComplete();
//	}
//
//	private void getData(int index){
//		new SAsyncTask<Integer, Void, List<Order>>(getActivity(),null,0){
//
//			@Override
//			protected List<Order> doInBackground(FragmentActivity context,
//					Integer... params) throws Exception {
//				// TODO Auto-generated method stub
//				LogYiFu.e("用户的id只是那",user_id);
//				return ComModel2.getMerchantBuyOrderList(context, params[0], status,"",user_id);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<Order> result, Exception e) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result);
//
//				if (e != null) {// 查询异常
//					layout_nodata.setVisibility(View.VISIBLE);
//					listView.setVisibility(View.GONE);
//					listView.onRefreshComplete();
//				}else {// 查询商品详情成功，刷新界面
//					if (isAdd) {
//						orders.addAll(result);
//					} else {
//						if (result != null && result.size() == 0) {
//							layout_nodata.setVisibility(View.VISIBLE);
//							listView.setVisibility(View.GONE);
//						}
//						orders.clear();
//						orders.addAll(result);
//					}
//					if(result.size()<PAGESIZE  ){
//						isComplete  = true;
//					}else {
//						isComplete = false;
//					}
////					mAdapter.notifyDataSetChanged();
//					listView.onRefreshComplete();
//				}
//
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			};
//
//		}.execute(index);
//	}
//
//
//	@Override
//	public void onClick(View arg0) {
//		// TODO Auto-generated method stub
//		Intent intent = null;
//		switch (arg0.getId()) {
//		case R.id.btn_to_shop:
//			if(MainMenuActivity.instances != null)
//				MainMenuActivity.instances.finish();
//			 intent = new Intent(getActivity(), MainMenuActivity.class);
//			intent.putExtra("toYf", "toYf");
//			startActivity(intent);
//			finish();
//
//		default:
//			break;
//		}
//	}
//
//	/**
//	 * 跳转到评价的时候需要关闭当前Fragment
//	 */
//	public void finish(){
//		getActivity().finish();
//	}
//
//}
