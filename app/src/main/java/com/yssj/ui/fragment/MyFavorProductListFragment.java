package com.yssj.ui.fragment;

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
//import com.yssj.custom.plaview.MultiColumnPullToRefreshListView;
import com.yssj.data.YDBHelper;
import com.yssj.entity.Like;
import com.yssj.entity.VipDikouData;
import com.yssj.model.ComModel2;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.adpter.MyFavorStaggeredAdapter;
//import me.maxwin.view.XListView;
//import me.maxwin.view.XListView.IXListViewListener;
import com.yssj.utils.ToastUtil;

//import com.yssj.ui.adpter.StaggeredAdapter;

public class MyFavorProductListFragment extends Fragment implements
		OnClickListener
		{

	private MyFavorStaggeredAdapter mAdapter;

	private int index = 1;

	private int mType = 1;// 1：初始化数据；2：加载更多数据

	private PullToRefreshListView w_list_view;


	private VipDikouData vipDikouData;

	private LinearLayout circle_nodata;
	private Button btn_view_allcircle;
	private TextView tv_qin, tv_no_join;

	private final int pageSize = 10;
	private String del = "0";
	//private boolean isComplete = false;

	public void setDel(String isDel){
		del=isDel;
		index=1;
		mAdapter.clearData(isDel);
		mAdapter.notifyDataSetChanged();
		initData(index+"");
	}

	public MyFavorProductListFragment() {
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.my_footprint_product_list,container, false);
		initView(v);
		return v;
	}

	private void initView(View v) {
		w_list_view = (PullToRefreshListView) v.findViewById(R.id.w_list_view);

		mAdapter = new MyFavorStaggeredAdapter(getActivity(),this);
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
						initData(index + "");
					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase refreshView) {
						index += 1;
						mType = 2;
						initData(index + "");
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

		initData(index + "");
	}

	public void setType(int type){
		this.mType=type;
	}

	public void showNoData() {
		circle_nodata.setVisibility(View.VISIBLE);
		w_list_view.setVisibility(View.GONE);
		w_list_view.onRefreshComplete();
	}
	public void showData() {
		circle_nodata.setVisibility(View.GONE);
		w_list_view.setVisibility(View.VISIBLE);
		w_list_view.onRefreshComplete();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void initData(String index) {
		new SAsyncTask<String, Void, List<Like>>(getActivity(),0) {

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					List<Like> result, Exception e) {
				super.onPostExecute(context, result, e);

				if (e != null) {// 查询异常
					circle_nodata.setVisibility(View.VISIBLE);
					w_list_view.setVisibility(View.GONE);
					w_list_view.onRefreshComplete();
				} else {
					if (mType == 1) {
						if (result != null&& (result.size() == 0 || result.isEmpty())) {
							circle_nodata.setVisibility(View.VISIBLE);
							w_list_view.setVisibility(View.GONE);
						}
//						mAdapter.addItemTop(result);

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
					w_list_view.onRefreshComplete();
					mAdapter.notifyDataSetChanged();
				}
			}

			@Override
			protected List<Like> doInBackground(FragmentActivity context,
					String... params) throws Exception {

				return ComModel2.getMyFavourList(context,
						Integer.parseInt(params[0]),del);
			}

		}.execute(index);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	public void onRefresh() {
//		isComplete = false;

	}

	//

	public void onLoadMore() {

	}

	public interface scroll {
		public void onScroll(int scrollY);
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = null;
		switch (arg0.getId()) {
		case R.id.btn_filter:
			// intent = new Intent(getActivity(),FilterConditionActivity.class);
			break;
		case R.id.btn_search:
			break;

		default:
			break;
		}
	}

	public MyFavorStaggeredAdapter getAdapter() {
		return mAdapter;
	}

}
