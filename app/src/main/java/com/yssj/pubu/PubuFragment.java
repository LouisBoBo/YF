package com.yssj.pubu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.model.ComModel2;
import com.yssj.model.ModQingfeng;
import com.yssj.pubu.XListView;
import com.yssj.pubu.XListView.IXListViewListener;
import com.yssj.spl.PuBuAdapter;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class PubuFragment extends Fragment implements IXListViewListener

{
	com.yssj.pubu.XListView mAdapterView;
	PuBuAdapter mAdapter;
	private int curPager;

	private int la = 0; // 1下拉刷新 ----------2上拉加载更多

	private static Context mContext;
	private List<HashMap<String, Object>> mListDatas;

	private static String mJumpFrom;
	private boolean isSign;// 是否是签到跳过来的
	private boolean isInvite;// 邀请好友

	private boolean isTixian;// 浏览赢提现


	public static PubuFragment newInstances(Context context, String jumpFrom) {
		mJumpFrom = jumpFrom;
		mContext = context;

		PubuFragment fragment = new PubuFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pubu, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (mJumpFrom.equals("pubuliu_sign")) {
			isTixian  = false;
			isSign = true;
			isInvite = false;
		} else if ("intivate".equals(mJumpFrom)) {
			isTixian  = false;
			isSign = false;
			isInvite = true;
		}else if("pubuliu_sign_tixian".equals(mJumpFrom)){
			isTixian  = true;
			isSign = false;
			isInvite = false;

		}

		curPager = 1;
		mAdapterView = (com.yssj.pubu.XListView) getView().findViewById(R.id.ptrstgv);
		mAdapterView.setPullLoadEnable(true);
		mAdapterView.setXListViewListener(this);
		mListDatas = new ArrayList<HashMap<String, Object>>();
		mAdapter = new PuBuAdapter(mContext, mListDatas, isSign, isInvite,isTixian);
		// 测试headerView
		// mAdapterView.addHeaderView(View.inflate(mContext,
		// R.layout.dialog_version_error, null));

		// 设置adapter
		mAdapterView.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
		XListView.mFooterView.setVisibility(View.GONE);

		// 获取话题广场数据
		getIntimateList();

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
					// ptrstgv.onRefreshComplete();
					return;
				}
				List<HashMap<String, Object>> dataList = result;
				if (dataList != null) {
					if (isInvite) {
						for (HashMap<String, Object> hashMap : dataList) {
							hashMap.put("isChecked", false);
						}
					}
					if (dataList.size() == 0) { // 没有数据了
						XListView.hasData = false;
						if (curPager > 1) {// 上拉
							ToastUtil.showShortText(mContext, "没有更多了");

							mAdapterView.stopLoadMore();
							LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
									LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
							layoutParam.setMargins(0, 0, 0, DP2SPUtil.px2dip(mContext, -280));
							mAdapterView.setLayoutParams(layoutParam);

						} else {// 下拉刷新
							mAdapterView.stopRefresh();

						}
					} else {// 有数据
						XListView.mFooterView.setVisibility(View.VISIBLE);
						XListView.hasData = true;
						if (curPager == 1) { // 下拉刷新

							if (la == 0) {// 第一次加载数据
								mListDatas.clear();
								mListDatas.addAll(dataList);
								mAdapter.notifyDataSetChanged();
								mAdapterView.stopRefresh();
							} else {
								//
								//
								mAdapterView.stopRefresh();
							}
							//

							//
							// mListDatas.clear();
							// mListDatas.addAll(dataList);
							//// mAdapterView.stopRefresh();
							// mAdapter.notifyDataSetChanged();

							// int h =mAdapterView.getHeight();
							// ViewGroup.LayoutParams lp;
							// lp= mAdapterView.getLayoutParams();
							// lp.height= h/2;
							// mAdapterView.setLayoutParams(lp);

						} else {// 上拉加载
							mListDatas.addAll(dataList);
							mAdapter.notifyDataSetChanged();
							LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
									LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
							layoutParam.setMargins(0, 0, 0, DP2SPUtil.px2dip(mContext, -280));
							mAdapterView.setLayoutParams(layoutParam);
							mAdapterView.stopLoadMore();
						}

					}
				} else { // 请求异常

					// if (curPager == 1) {
					// mListDatas.clear();
					// mAdapter.notifyDataSetChanged();
					// } else {
					// }
				}

				// ptrstgv.onRefreshComplete();

				super.onPostExecute(context, result, e);
			}

		}.execute();
	}

	@Override
	public void onRefresh() {

		mAdapterView.stopRefresh();

		// TODO Auto-generated method stub
		// curPager = 1;
		// la = 1;
		// getIntimateList();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		la = 2;
		curPager++;
		getIntimateList();

	}

}