package com.yssj.spl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.model.ComModel2;
import com.yssj.model.ModQingfeng;
import com.yssj.mypullto.PullToRefreshStaggeredGridView;
import com.yssj.mypullto.StaggeredGridView;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.FriendsListCommnonFragment;
import com.yssj.utils.BitmapCompress;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.yssj.mypullto.PullToRefreshBase;

public class DoublePuBuCommmonFragment extends Fragment {
	PullToRefreshStaggeredGridView ptrstgv;
	PuBuAdapter mAdapter;
	private int curPager;

	private static Context mContext;
	private List<HashMap<String, Object>> mListDatas;

	private static String mJumpFrom;
	private boolean isSign;// 是否是签到跳过来的

	public static DoublePuBuCommmonFragment newInstances(Context context, String jumpFrom) {
		mJumpFrom = jumpFrom;
		mContext = context;

		DoublePuBuCommmonFragment fragment = new DoublePuBuCommmonFragment();
		return fragment;
	}

	// public void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.ac_stgv_with_ptr);
	// context = this;
	// curPager = 1;
	// ptrstgv = (PullToRefreshStaggeredGridView) findViewById(R.id.ptrstgv);
	// ptrstgv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
	// // ptrstgv.getRefreshableView().setHeaderView(new Button(this));
	// mListDatas = new ArrayList<HashMap<String, Object>>();
	// mAdapter = new PuBuAdapter(this, mListDatas);
	// // 添加刷新尾
	//// View footerView;
	//// LayoutInflater inflater = (LayoutInflater)
	// getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	//// footerView = inflater.inflate(R.layout.layout_loading_footer, null);
	//// ptrstgv.getRefreshableView().setFooterView(footerView);
	//
	// //设置adapter
	// ptrstgv.setAdapter(mAdapter);
	// mAdapter.notifyDataSetChanged();
	//
	// // 获取话题广场数据
	// getIntimateList();
	//
	// ptrstgv.setOnRefreshListener(new
	// PullToRefreshBase.OnRefreshListener<StaggeredGridView>() {
	// // 下拉刷新
	// @Override
	// public void onRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {
	// // mAdapter.getNewItem();
	// // mAdapter.notifyDataSetChanged();
	// curPager = 1;
	// // ptrstgv.onRefreshComplete();
	// getIntimateList();
	// }
	//
	// });
	//
	// // 上拉加载更多
	// ptrstgv.setOnLoadmoreListener(new StaggeredGridView.OnLoadmoreListener()
	// {
	// @Override
	// public void onLoadmore() {
	//
	// curPager++;
	//
	// getIntimateList();
	// }
	// });
	//
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.ac_stgv_with_ptr, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

//		if (mJumpFrom.equals("pubuliu_sign")) {
//			isSign = true;
//		}

		curPager = 1;
		ptrstgv = (PullToRefreshStaggeredGridView) getView().findViewById(R.id.ptrstgv);
		ptrstgv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
		// ptrstgv.getRefreshableView().setHeaderView(new Button(this));
		mListDatas = new ArrayList<HashMap<String, Object>>();
		mAdapter = new PuBuAdapter(mContext, mListDatas);
		// 添加刷新尾
		// View footerView;
		// LayoutInflater inflater = (LayoutInflater)
		// getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// footerView = inflater.inflate(R.layout.layout_loading_footer, null);
		// ptrstgv.getRefreshableView().setFooterView(footerView);

		// 设置adapter
		ptrstgv.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();

		// 获取话题广场数据
		getIntimateList();

		ptrstgv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<StaggeredGridView>() {
			// 下拉刷新
			@Override
			public void onRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {
				// mAdapter.getNewItem();
				// mAdapter.notifyDataSetChanged();
				curPager = 1;
				// ptrstgv.onRefreshComplete();
				getIntimateList();
			}

		});

		// 上拉加载更多
		ptrstgv.setOnLoadmoreListener(new StaggeredGridView.OnLoadmoreListener() {
			@Override
			public void onLoadmore() {

				curPager++;

				getIntimateList();
			}
		});

	}

	/**
	 * 获取密友圈首页列表 type //参数1 话题广场 2密友圈
	 */
	private void getIntimateList() {

		new SAsyncTask<Void, Void, List<HashMap<String, Object>>>((FragmentActivity) mContext, R.string.wait) {

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				LoadingDialog.show((FragmentActivity) mContext);
			}

			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				//
				if (mJumpFrom.endsWith("xiangguantuijian")) {// 穿搭详情---相关推荐
					String them_id = SharedPreferencesUtil.getStringData(mContext, "xiangtuijianhthemid", "");
					return ModQingfeng.getFridendDetislMore(context, them_id, curPager + "");
				} else {
					return ComModel2.getIntimateList(context, "1", curPager, "");
				}

			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
				if (e != null) {// 查询异常
					ptrstgv.onRefreshComplete();
					return;
				}
				List<HashMap<String, Object>> dataList = result;
				// mListView.setVisibility(View.VISIBLE);
				// nodataView.setVisibility(View.GONE);
				if (dataList != null) {
					if (dataList.size() == 0) {
						if (curPager > 1) {
							// ToastUtil.showShortText(context, "已没有更多了");
						} else {
							// mListView.setVisibility(View.GONE);
							// nodataView.setVisibility(View.VISIBLE);
						}
					} else {
						if (curPager == 1) {
							mListDatas.clear();
						}
						mListDatas.addAll(dataList);
						mAdapter.notifyDataSetChanged();
					}
				} else {
					if (curPager == 1) {
						mListDatas.clear();
						mAdapter.notifyDataSetChanged();
						// mListView.setVisibility(View.GONE);
						// nodataView.setVisibility(View.VISIBLE);
					} else {
						// ToastUtil.showShortText(context, "已没有更多了");
					}
				}

				ptrstgv.onRefreshComplete();

				super.onPostExecute(context, result, e);
			}

		}.execute();
	}

	// public class LoadMoreTask extends AsyncTask<Void, Void, Void> {
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// }
	//
	// @Override
	// protected Void doInBackground(Void... params) {
	// try {
	// Thread.sleep(1000);
	// } catch (InterruptedException e) {
	// }
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(Void result) {
	// mAdapter.getMoreItem();
	// mAdapter.notifyDataSetChanged();
	// super.onPostExecute(result);
	// }
	//
	// }
}