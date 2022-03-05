//package com.yssj.ui.fragment;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.plaview.MultiColumnPullToRefreshListView;
//import com.yssj.data.YDBHelper;
//import com.yssj.ui.activity.main.FilterConditionActivity;
//import com.yssj.ui.activity.main.SeachNoticeActivity;
//import com.yssj.ui.adpter.StaggeredAdapter;
//import com.yssj.utils.LogYiFu;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.Toast;
////import me.maxwin.view.XListView;
////import me.maxwin.view.XListView.IXListViewListener;
//
////import com.yssj.ui.adpter.StaggeredAdapter;
//
//public class ProductListFragmentCopyOf extends Fragment implements OnClickListener,
//		MultiColumnPullToRefreshListView.OnRefreshListener,
//		MultiColumnPullToRefreshListView.OnLoadMoreListener {
//
//	private String id = -1 + "";
//
//	private StaggeredAdapter mAdapter;
//
//	private int index = 1;
//
//	private int mType = 1;// 1：初始化数据；2：加载更多数据
//
//	private MultiColumnPullToRefreshListView w_list_view;
//
//	private Button btn_search, btn_filter;
//
//	private List<HashMap<String, String>> mapSecond = new ArrayList<HashMap<String, String>>();
//	private YDBHelper dbHelper;
//
//	private String level = 1 + "";
//	
//	private int pageSize = 10;
//	
//
//	public ProductListFragmentCopyOf(String id) {
//		this.id = id;
//		setRetainInstance(true);
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		if (savedInstanceState != null)
//			id = savedInstanceState.getString("id");
//
//		View v = inflater.inflate(R.layout.product_list, container, false);
//		initView(v);
//		return v;
//	}
//
//	private void initView(View v) {
//		w_list_view = (MultiColumnPullToRefreshListView) v
//				.findViewById(R.id.w_list_view);
//		mAdapter = new StaggeredAdapter(getActivity());
//		w_list_view.setAdapter(mAdapter);
//		w_list_view.setOnRefreshListener(this);
//		w_list_view.setOnLoadMoreListener(this);
//
//		btn_search = (Button) v.findViewById(R.id.btn_search);
//		btn_search.setOnClickListener(this);
//		btn_filter = (Button) v.findViewById(R.id.btn_filter);
//		btn_filter.setOnClickListener(this);
//
//		initData(index + "", id, level);
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		dbHelper = new YDBHelper(getActivity());
//		mapSecond = dbHelper.query("select * from sort_info where p_id =" + id);
//		LogYiFu.e("二级目录大小", mapSecond.size() + "");
//	}
//
//	private void initData(String index, String id, String level) {
//		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(
//				getActivity(), 0) {
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<HashMap<String, Object>> result) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result);
//				// listResult.addAll(result);
//				if (mType == 1) {
//					mAdapter.addItemTop(result);
//					w_list_view.onRefreshComplete();
//				} else if (mType == 2) {
//					mAdapter.addItemLast(result);
//					w_list_view.onLoadMoreComplete();
//				}
//			}
//
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//				return null;
//					/*return ComModel2.getProductList(context, params[0], params[1],
//							params[2],pageSize);*/
//				
//			}
//
//		}.execute(index, id, level);
//	}
//
//	@Override
//	public void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		outState.putString("id", id);
//	}
//
//	@Override
//	public void onRefresh() {
//		mType = 1;
//		index = 1;
//		initData(index + "", id, level);
//	}
//
//	@Override
//	public void onLoadMore() {
//		// TODO Auto-generated method stub
//		Toast.makeText(getActivity(), "加载更多", Toast.LENGTH_LONG).show();
//		index++;
//		mType = 2;
//		initData(index + "", id, level);
//	}
//
//	public interface scroll {
//		public void onScroll(int scrollY);
//	}
//
//	@Override
//	public void onClick(View arg0) {
//		// TODO Auto-generated method stub
//		Intent intent = null;
//		switch (arg0.getId()) {
//		case R.id.btn_filter:
//			intent = new Intent(getActivity(),FilterConditionActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.btn_search:
//			intent = new Intent(getActivity(), SeachNoticeActivity.class);
//			intent.putExtra("id", id);
//			startActivity(intent);
//			break;
//
//		default:
//			break;
//		}
//	}
//
//}
