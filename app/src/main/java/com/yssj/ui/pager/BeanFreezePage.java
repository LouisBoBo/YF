package com.yssj.ui.pager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
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
import com.yssj.entity.MyCoupon;
import com.yssj.model.ComModel2;
import com.yssj.ui.adpter.MyCouponsPagerAdapter;
import com.yssj.ui.adpter.MyIntergralPageAdapter;
import com.yssj.ui.adpter.MyPearsPageAdapter;
import com.yssj.ui.base.BasePager;
import com.yssj.utils.DP2SPUtil;

public class BeanFreezePage extends BasePager {
	private HashMap<String, Object> map;

	public BeanFreezePage(Context context) {
		super(context);
	}

	private PullToRefreshListView lv_common;
	private int currPage = 1;
	private MyPearsPageAdapter mAdapter;

	private boolean flag = true; // 判断上拉、下拉的标志

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
		tv_qin.setText("O(∩_∩)O~亲~");
		tv_no_join = (TextView) view.findViewById(R.id.tv_no_join);
		tv_no_join.setText("暂无衣豆冻结明细");

		return view;
	}

	@Override
	public void initData() {
		currPage = 1;
		queryPearsFreezeData();
		mAdapter = new MyPearsPageAdapter(context, 2);
		lv_common.setAdapter(mAdapter);
		ListView refreshableView = lv_common.getRefreshableView();
		TextView tv = new TextView(context);
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
				DP2SPUtil.dp2px(context, 38));
		tv.setText("订单签收7天后衣豆才能解冻喔~");
		tv.setTextSize(12);
		tv.setBackgroundColor(Color.parseColor("#FFFFFF"));
		tv.setTextColor(Color.parseColor("#ff3f8b"));
		tv.setGravity(Gravity.CENTER);
		tv.setLayoutParams(params);
		View view = new View(context);
		AbsListView.LayoutParams params2 = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
				DP2SPUtil.dp2px(context, 1));
		view.setBackgroundColor(Color.parseColor("#E7E7E7"));
		view.setLayoutParams(params2);
		if (refreshableView.getHeaderViewsCount() == 0) {
			refreshableView.addHeaderView(tv);
			refreshableView.addHeaderView(view);
		}
		super.initIndicator(lv_common);

		lv_common.setMode(Mode.BOTH);

		lv_common.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				flag = true;

				currPage = 1;
				queryPearsFreezeData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				flag = false;
				currPage++;
				queryPearsFreezeData();
			}
		});
	}

	private void queryPearsFreezeData() {

		new SAsyncTask<Integer, Void, List<HashMap<String, Object>>>((FragmentActivity) context, R.string.wait) {
			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Integer... params)
					throws Exception {

				return ComModel2.getPearsDetail(context, currPage, "desc", "id", 3);

			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
				super.onPostExecute(context, result, e);

				if (e != null) {// 查询异常
					account_nodata.setVisibility(View.VISIBLE);
					lv_common.setVisibility(View.GONE);
					lv_common.onRefreshComplete();
				} else {

					if (flag) {

						List<HashMap<String, Object>> listTemp1=new LinkedList<HashMap<String, Object>>() ;
						if (result != null) {
							for (int i = 0; i < result.size(); i++) {
								if ("1".equals(result.get(i).get("freeze"))) {
									listTemp1.add(result.get(i));
								} 
							}
						}

						if (listTemp1 != null && (listTemp1.size() == 0 || listTemp1.isEmpty())) {
							account_nodata.setVisibility(View.VISIBLE);
							lv_common.setVisibility(View.GONE);
						}
						mAdapter.addItemFirst(listTemp1 != null ? listTemp1 : null);
					} else {
						List<HashMap<String, Object>> listTemp2=new LinkedList<HashMap<String, Object>>() ;
						if (result != null) {
							for (int i = 0; i < result.size(); i++) {
								if ("1".equals(result.get(i).get("freeze"))) {
									listTemp2.add(result.get(i));
								} 
							}
						}
						mAdapter.addItemLast(listTemp2 != null ? listTemp2 : null);
					}

					mAdapter.notifyDataSetChanged();
					lv_common.onRefreshComplete();
				}
			}

		}.execute(currPage);
	}

}
