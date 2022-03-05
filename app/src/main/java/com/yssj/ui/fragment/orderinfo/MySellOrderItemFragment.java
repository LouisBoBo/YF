//package com.yssj.ui.fragment.orderinfo;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.entity.Order;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.MainMenuActivity;
//import com.yssj.utils.LogYiFu;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//
///****
// * 买单列表界面
// *
// * @author Administrator
// *
// */
//@SuppressLint("NewApi")
//public class MySellOrderItemFragment extends Fragment implements
//		OnItemClickListener, OnClickListener {
//
//	private int status;
//
//	private PullToRefreshListView listView;
//	private MySellOrderAdapter mAdapter;
//
//	private int index = 1;
//
//	private boolean isAdd = false;
//
//	private List<Order> orders = new ArrayList<Order>();
//
//	private LinearLayout layout_nodata;
//
//	private Button btn_to_shop;
//
//	public MySellOrderItemFragment(int status) {
//		this.status = status;
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		LogYiFu.e("nearbyFragment onCreate", "nearbyFragment onCreate");
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View v = inflater.inflate(R.layout.trade_fragment, container, false);
//		initView(v);
//		getData(index);
//		return v;
//	}
//
//	private void initView(View v) {
//		layout_nodata = (LinearLayout) v.findViewById(R.id.layout_nodata);
//
//		listView = (PullToRefreshListView) v.findViewById(R.id.trade_listview);
//		mAdapter = new MySellOrderAdapter(getActivity(), orders);
//		listView.setAdapter(mAdapter);
//		listView.setOnItemClickListener(this);
//		listView.setMode(Mode.BOTH);
//
//		btn_to_shop = (Button) v.findViewById(R.id.btn_to_shop);
//		btn_to_shop.setOnClickListener(this);
//
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
//				getData(++index);
//			}
//		});
//	}
//
//	private void getData(int index) {
//		new SAsyncTask<Integer, Void, List<Order>>(getActivity(), 0) {
//
//			@Override
//			protected List<Order> doInBackground(FragmentActivity context,
//					Integer... params) throws Exception {
//				return ComModel2.getMySellOrderList(context, params[0], status);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			};
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
//				} else {// 查询商品详情成功，刷新界面
//					listView.onRefreshComplete();
//					if (isAdd) {
//						orders.addAll(result);
//					} else {
//						if (result != null && result.size() == 0) {
//
//							layout_nodata.setVisibility(View.VISIBLE);
//							listView.setVisibility(View.GONE);
//						}
//
//						orders.clear();
//						orders.addAll(result);
//					}
//					mAdapter.notifyDataSetChanged();
//				}
//			}
//
//		}.execute(index);
//	}
//
//	@Override
//	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		// TODO Auto-generated method stub
//		Intent intent = new Intent(getActivity(),
//				MySellOrderDetailsActivity.class);
//		Bundle bundle = new Bundle();
//		bundle.putSerializable("order", orders.get(arg2));
//		intent.putExtras(bundle);
//		startActivity(intent);
//	}
//
//	@Override
//	public void onClick(View arg0) {
//		// TODO Auto-generated method stub
//		Intent intent = null;
//		switch (arg0.getId()) {
//		case R.id.btn_to_shop:
////			intent = new Intent();
////			getActivity().setResult(10001,intent);
//
//			intent = new Intent(getActivity(), MainMenuActivity.class);
//			intent.putExtra("toYf", "toYf");
//			startActivity(intent);
//			getActivity().finish();
//			break;
//
//		default:
//			break;
//		}
//	}
//
//}
