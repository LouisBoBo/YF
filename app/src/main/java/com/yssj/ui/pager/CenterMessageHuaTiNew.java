package com.yssj.ui.pager;

import java.util.HashMap;

/**
 * 消息中心，话题消息了列表
 * 
 * 先加载历史消息---再加载最新消息
 */
import java.util.List;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.adpter.CenterMessageHuatiPager;
import com.yssj.ui.base.BasePager;

public class CenterMessageHuaTiNew extends BasePager {
	public CenterMessageHuaTiNew(Context context) {
		super(context);

	}

	private PullToRefreshListView lv_common;
	private int currPage = 1;
	private CenterMessageHuatiPager mAdapter;
	private List<HashMap<String, Object>> newList;// 最新消息
	private List<HashMap<String, Object>> historyList;// 历史消息
	private List<HashMap<String, Object>> allList;// 所有消息
	private LinearLayout account_nodata;
	private Button btn_view_allcircle;
	private TextView tv_qin, tv_no_join;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.pulltorefreshlistview_coupons, null);
		lv_common = (PullToRefreshListView) view.findViewById(R.id.lv_common);
		account_nodata = (LinearLayout) view.findViewById(R.id.account_nodata);
		btn_view_allcircle = (Button) view.findViewById(R.id.btn_view_allcircle);
		btn_view_allcircle.setVisibility(View.GONE);
		tv_qin = (TextView) view.findViewById(R.id.tv_qin);
		tv_no_join = (TextView) view.findViewById(R.id.tv_no_join);
		tv_qin.setText("O(∩_∩)O~亲~");
		tv_no_join.setText("暂无相关话题哦");

		return view;
	}

	@Override
	public void initData() {
		currPage = 1;

		// 获取历史消息
		getHistory(true);

		mAdapter = new CenterMessageHuatiPager(context);
		lv_common.setAdapter(mAdapter);
		// 设置刷新文字
		super.initIndicator(lv_common);
		lv_common.setMode(Mode.BOTH);
		lv_common.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			// 下拉刷新-----只获取新消息
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				currPage = 1;
				getNew(false);
			}

			// 上拉加载更-----只获取历史消息
			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				currPage++;
				// 上拉加载更多只调已读消息的接口
				getHistory(false);
			}
		});
	}

	/**
	 * 
	 * @param isFist是否刚刚打开第一次请求数据
	 */
	private void getNew(final boolean isFist) {
		new SAsyncTask<Integer, Void, List<HashMap<String, Object>>>((FragmentActivity) context, R.string.wait) {
			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Integer... params)
					throws Exception {

				return ModQingfeng.getMessageHuaTi(context, currPage, false);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
				super.onPostExecute(context, result, e);

				if (e != null) {// 查询异常
				} else {
					newList = result;
					if (isFist) { // 进来第一次查询
						// 合并历史消息和最新消息
						newList.addAll(historyList);
						allList = newList;

						if (allList.size() > 0) {
							account_nodata.setVisibility(View.GONE);
							lv_common.setVisibility(View.VISIBLE);
							mAdapter.addItemFirst(allList != null ? allList : null);
							mAdapter.notifyDataSetChanged();
							lv_common.onRefreshComplete();
						} else {
							account_nodata.setVisibility(View.VISIBLE);
							lv_common.setVisibility(View.GONE);
							lv_common.onRefreshComplete();
						}

					} else { // 下拉刷新
						if (allList != null && allList.size() > 0) {
							if (result != null && result.size() > 0) {
								mAdapter.addItemFirst(result);
								mAdapter.notifyDataSetChanged();
							}
							lv_common.onRefreshComplete();
						}

					}

				}
			}

		}.execute(currPage);
	}

	/**
	 * 获取历史消息
	 */
	protected void getHistory(final boolean isFirst) {
		new SAsyncTask<Integer, Void, List<HashMap<String, Object>>>((FragmentActivity) context, R.string.wait) {
			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Integer... params)
					throws Exception {

				return ModQingfeng.getMessageHuaTi(context, currPage, true);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
				super.onPostExecute(context, result, e);

				if (e != null) {// 历史消息查询异常

				} else {// 有历史消息
					historyList = result;
					if (isFirst) { // 第一次进来获取历史小
						// 获取最新消息
						getNew(true);
					} else { // 上拉加载更多
						if (allList != null && allList.size() > 0) {
							if (result != null && allList.size() > 0) {
								mAdapter.addItemLast(result);
								mAdapter.notifyDataSetChanged();
							}
							lv_common.onRefreshComplete();

						}

					}

				}
			}

		}.execute(currPage);

	}

}
