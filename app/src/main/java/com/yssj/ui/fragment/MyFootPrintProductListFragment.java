package com.yssj.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.data.YDBHelper;
import com.yssj.entity.VipDikouData;
import com.yssj.model.ComModel;
//import com.yssj.ui.activity.main.FilterConditionActivity;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.main.ForceLookActivity;
import com.yssj.ui.activity.main.SeachNoticeActivity;
import com.yssj.ui.adpter.MyFootPrintStaggeredAdapter;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;
//import me.maxwin.view.XListView;
//import me.maxwin.view.XListView.IXListViewListener;

//import com.yssj.ui.adpter.StaggeredAdapter;

public class MyFootPrintProductListFragment extends Fragment implements
		OnClickListener{

	private String id = -1 + "";

	private MyFootPrintStaggeredAdapter mAdapter;

	private int index = 1;

	private int mType = 1;// 1：初始化数据；2：加载更多数据

	private PullToRefreshListView w_list_view;

	private List<HashMap<String, Object>> mapSecond = new ArrayList<HashMap<String, Object>>();
	private YDBHelper dbHelper;

	private LinearLayout circle_nodata;
	private Button btn_view_allcircle;
	private TextView tv_qin, tv_no_join;

	private int pageSize = 10;
	private String del = "2";
	private VipDikouData vipDikouData;


	public MyFootPrintProductListFragment() {
		setRetainInstance(true);
	}
	
	public void setDel(String isDel){
		del=isDel;
		index=1;
		mAdapter.clearData();
		mAdapter.notifyDataSetChanged();
		initStepData(index+"");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState != null)
			id = savedInstanceState.getString("id");

		View v = inflater.inflate(R.layout.my_footprint_product_list,
				container, false);
		initView(v);
		return v;
	}

	private void initView(View v) {
		w_list_view = (PullToRefreshListView) v
				.findViewById(R.id.w_list_view);
		mAdapter = new MyFootPrintStaggeredAdapter(getActivity());
		w_list_view.setAdapter(mAdapter);
		w_list_view.setMode(Mode.BOTH);


		//查询抵扣
		HashMap<String, String> pairsMap = new HashMap<>();
		YConn.httpPost(getActivity(), YUrl.GETALLDIKOU, pairsMap, new HttpListener<VipDikouData>() {
			@Override
			public void onSuccess(VipDikouData result) {

				 vipDikouData = result;
				w_list_view.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2() {

					@Override
					public void onPullDownToRefresh(PullToRefreshBase refreshView) {
						mType = 1;
						index = 1;
						initStepData(index + "");
					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase refreshView) {
						//if (!isComplete) {
						index += 1;
						mType = 2;
						initStepData(index + "");
						//}else{

						//}

					}
				});
			}

			@Override
			public void onError() {

			}
		});




		
		circle_nodata = (LinearLayout) v.findViewById(R.id.circle_nodata);

		btn_view_allcircle = (Button) v.findViewById(R.id.btn_view_allcircle);
		btn_view_allcircle.setVisibility(View.GONE);

		tv_qin = (TextView) v.findViewById(R.id.tv_qin);
		tv_qin.setText("O(∩_∩)O~亲~");
		tv_no_join = (TextView) v.findViewById(R.id.tv_no_join);
		tv_no_join.setText("暂无数据");

		initStepData(index + "");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void showNoData() {
		circle_nodata.setVisibility(View.VISIBLE);
		w_list_view.setVisibility(View.GONE);
		w_list_view.onRefreshComplete();
	}

	public void initStepData(String index) {
//		if("1".equals(is_del)){
//			del = "1";
////			mType = 1;
//		}else{
//			del = "";
//		}
		
		final String s_code = YCache.getCacheStore(getActivity()).getS_code();
		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(
				getActivity(),0) {

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					List<HashMap<String, Object>> result, Exception e) {
				super.onPostExecute(context, result);

				if (e != null) {// 查询异常
					circle_nodata.setVisibility(View.VISIBLE);
					w_list_view.setVisibility(View.GONE);
					w_list_view.onRefreshComplete();
				} else {
					w_list_view.onRefreshComplete();
					if (mType == 1) {
						if (result == null|| result.isEmpty()) {
							circle_nodata.setVisibility(View.VISIBLE);
							w_list_view.setVisibility(View.GONE);
						}else{
							circle_nodata.setVisibility(View.GONE);
							w_list_view.setVisibility(View.VISIBLE);
						}

						if (null != vipDikouData && vipDikouData.getIsVip() > 0) {
							mAdapter.setDataVip(result, true, vipDikouData);
						} else {
							mAdapter.setDataVip(result, false, vipDikouData);
						}


					} else if (mType == 2) {
						if(result==null||result.isEmpty()){
//							Toast.makeText(context, "到底了", Toast.LENGTH_SHORT).show();
							ToastUtil.showShortText(context, "没有更多商品了");
						}else{
							mAdapter.addItemLast(result);
							w_list_view.getRefreshableView().smoothScrollBy(100, 20);
						}
					}
				}
			}

			@Override
			protected List<HashMap<String, Object>> doInBackground(
					FragmentActivity context, String... params)
					throws Exception {
				
				return ComModel.queryMyStepsList(context, params[0], s_code,del,String.valueOf(pageSize));

			}

		}.execute(index);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("id", id);
	}

//	@Override
//	public void onRefresh() {
////		isComplete = false;
//		mType = 1;
//		index = 1;
//		initData(index + "", id,del);
//		
//	}
//
//	@Override
//	public void onLoadMore() {
//		if (!isComplete) {
//			index += 1;
//			mType = 2;
//			initData(index + "", id,del);
//		}
//
//	}

	public interface scroll {
		public void onScroll(int scrollY);
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = null;
		switch (arg0.getId()) {
		case R.id.btn_filter:
//			intent = new Intent(getActivity(), FilterConditionActivity.class);
//			startActivity(intent);
			break;
		case R.id.btn_search:
			intent = new Intent(getActivity(), SeachNoticeActivity.class);
			intent.putExtra("id", id);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	public MyFootPrintStaggeredAdapter getAdapter() {
		return mAdapter;
	}

}
