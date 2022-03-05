//package com.yssj.ui.pager;
//
//import java.util.HashMap;
//import java.util.List;
//
//import android.content.Context;
//import android.support.v4.app.FragmentActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.yssj.YJApplication;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.adpter.CircleAllPagerAdapter;
//import com.yssj.ui.base.BasePager;
//
///**
// * 美圈 全部
// * 
// * @author Administrator
// * 
// */
//public class AllPager extends BasePager {
//
//	private PullToRefreshListView lv_cirlce;
//	private int currPage = 1;
//	private CircleAllPagerAdapter mAdapter;
//
//	private LinearLayout circle_nodata;
//	private Button btn_view_allcircle;
//	private TextView tv_qin, tv_no_join;
//
//	private boolean flag; // 判断上拉、下拉的标志
//
//	public AllPager(Context context) {
//		super(context);
//	}
//
//	@Override
//	public View initView() {
//		view = View.inflate(context, R.layout.circle_list_allpager, null);
//		lv_cirlce = (PullToRefreshListView) view.findViewById(R.id.lv_cirlce);
//
//		circle_nodata = (LinearLayout) view.findViewById(R.id.circle_nodata);
//
//		btn_view_allcircle = (Button) view
//				.findViewById(R.id.btn_view_allcircle);
//		btn_view_allcircle.setVisibility(View.GONE);
//
//		tv_qin = (TextView) view.findViewById(R.id.tv_qin);
//		tv_qin.setText("~O(∩_∩)O~");
//		tv_no_join = (TextView) view.findViewById(R.id.tv_no_join);
//		tv_no_join.setText("亲暂时没有圈子");
//
//		return view;
//	}
//
//	@Override
//	public void initData() {
//		currPage = 1;
//		queryAllData();
//
//		mAdapter = new CircleAllPagerAdapter(context);
//		lv_cirlce.setAdapter(mAdapter);
//
//		super.initIndicator(lv_cirlce);
//
//		lv_cirlce.setMode(Mode.BOTH);
//
//		lv_cirlce.setOnRefreshListener(new OnRefreshListener2<ListView>() {
//			@Override
//			public void onPullDownToRefresh(
//					PullToRefreshBase<ListView> refreshView) {
//				flag = true;
//
//				currPage = 1;
//				queryAllData();
//			}
//
//			@Override
//			public void onPullUpToRefresh(
//					PullToRefreshBase<ListView> refreshView) {
//				flag = false;
//				currPage++;
//				queryAllData();
//			}
//		});
//	}
//
//	private void queryAllData() {
//		new SAsyncTask<Integer, Void, List<HashMap<String, Object>>>(
//				(FragmentActivity) context, null, R.string.wait) {
//
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(
//					FragmentActivity context, Integer... params)
//					throws Exception {
//				if (YJApplication.instance.isLoginSucess()) {
//					return ComModel2.getAllCircle(context, currPage);
//				} else {
//					return ComModel2.getAllCircle2(context, currPage);
//				}
//
//				
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<HashMap<String, Object>> result, Exception e) {
//				super.onPostExecute(context, result, e);
//
//				if (e != null) {// 查询异常
//				// circle_nodata.setVisibility(View.VISIBLE);
//				// lv_cirlce.setVisibility(View.GONE);
//					lv_cirlce.onRefreshComplete();
//				} else {
//
//					if (flag) {
//
//						// if(result != null && result.size() == 0){
//						// circle_nodata.setVisibility(View.VISIBLE);
//						// lv_cirlce.setVisibility(View.GONE);
//						// }
//
//						mAdapter.addItemFirst(result != null ? result : null);
//					} else {
//						mAdapter.addItemLast(result != null ? result : null);
//					}
//
//					mAdapter.notifyDataSetChanged();
//					lv_cirlce.onRefreshComplete();
//				}
//
//				if (mAdapter.getList().isEmpty()) {
//
//					circle_nodata.setVisibility(View.VISIBLE);
//					lv_cirlce.setVisibility(View.GONE);
//				} else {
//					circle_nodata.setVisibility(View.GONE);
//					lv_cirlce.setVisibility(View.VISIBLE);
//				}
//			}
//		}.execute(currPage);
//	}
//}
