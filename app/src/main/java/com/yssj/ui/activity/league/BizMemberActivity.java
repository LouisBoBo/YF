//package com.yssj.ui.activity.league;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnCreateContextMenuListener;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;
//
//import com.handmark.pulltorefresh.library.ILoadingLayout;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.entity.MyCoupon;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.adpter.BizMemberAdapter;
//import com.yssj.ui.adpter.BizMemberDetailAdapter;
//import com.yssj.ui.adpter.MyCouponsPagerAdapter;
//import com.yssj.ui.base.BasePager;
//import com.yssj.ui.base.BasicActivity;
//
//public class BizMemberActivity extends BasicActivity {
//	private HashMap<String, Object> map;
//
//	private PullToRefreshListView lv_common;
//	private int currPage = 1;
//	private BizMemberAdapter mAdapter;
//
//	private boolean flag = true; // 判断上拉、下拉的标志
//
//	private LinearLayout account_nodata;
//	private Button btn_view_allcircle;
//	private TextView tv_qin, tv_no_join, tvTitle_base;
//	private LinearLayout img_back;
//
//	private List<HashMap<String, Object>> retInfo;
//	private List<HashMap<String, Object>> retInfo2;
//	private HashMap<String, Object> retMap = new HashMap<String, Object>();
//	private HashMap<String, Object> retMap2 = new HashMap<String, Object>();
//	private String tag;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		aBar.hide();
//		setContentView(R.layout.pulltorefreshlistview_biz_member_detail);
//
//		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
//		tvTitle_base.setText("我的会员");
//
//		lv_common = (PullToRefreshListView) findViewById(R.id.lv_common);
//
//		account_nodata = (LinearLayout) findViewById(R.id.account_nodata);
//
//		btn_view_allcircle = (Button) findViewById(R.id.btn_view_allcircle);
//		btn_view_allcircle.setVisibility(View.GONE);
//
//		tv_qin = (TextView) findViewById(R.id.tv_qin);
//		tv_qin.setText("O(∩_∩)O~亲~");
//		tv_no_join = (TextView) findViewById(R.id.tv_no_join);
//		tv_no_join.setText("暂无会员信息");
//		
//		tag = getIntent().getStringExtra("tag");
//
//		img_back = (LinearLayout) findViewById(R.id.img_back);
//		img_back.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				finish();
//
//			}
//		});
//
//		initData();
//	}
//
//	public void initData() {
//		queryMemberData();
//		mAdapter = new BizMemberAdapter(context,tag);
//		lv_common.setAdapter(mAdapter);
//
//		initIndicator(lv_common);
//
//		lv_common.setMode(Mode.BOTH);
//
//		lv_common.setOnRefreshListener(new OnRefreshListener2<ListView>() {
//			@Override
//			public void onPullDownToRefresh(
//					PullToRefreshBase<ListView> refreshView) {
//				flag = true;
//
//				currPage = 1;
//				queryMemberData();
//			}
//
//			@Override
//			public void onPullUpToRefresh(
//					PullToRefreshBase<ListView> refreshView) {
//				flag = false;
//				currPage++;
//				queryMemberData();
//			}
//		});
//	}
//
//	public void initIndicator(PullToRefreshListView lv) {
//		ILoadingLayout startLabels = lv.getLoadingLayoutProxy(true, false);
//		startLabels.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
//		startLabels.setRefreshingLabel("正在刷新...");// 刷新时
//		startLabels.setReleaseLabel("释放刷新");// 下来达到一定距离时，显示的提示
//
//		ILoadingLayout endLabels = lv.getLoadingLayoutProxy(false, true);
//		endLabels.setPullLabel("加载更多");
//		endLabels.setRefreshingLabel("正在加载...");
//		endLabels.setReleaseLabel("释放加载");
//	}
//
//	private void queryMemberData() {
//		new SAsyncTask<Integer, Void, List<HashMap<String, Object>>>(
//				(FragmentActivity) context, null, R.string.wait) {
//
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(
//					FragmentActivity context, Integer... params)
//					throws Exception {
//
//				return ComModel2.getAllianceMember(context, currPage);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<HashMap<String, Object>> result,
//					Exception e) {
//				super.onPostExecute(context, result, e);
//				if (e != null) {// 查询异常
//					// circle_nodata.setVisibility(View.VISIBLE);
//					// lv_cirlce.setVisibility(View.GONE);
//						lv_common.onRefreshComplete();
//					} else {
//				
//
//					if (flag) {
//
//						// if(result != null && result.size() == 0){
//						// circle_nodata.setVisibility(View.VISIBLE);
//						// lv_cirlce.setVisibility(View.GONE);
//						// }
//
//						mAdapter.addItemFirst(result != null ? result
//								: null);
//					} else {
//						mAdapter.addItemLast(result != null ? result
//								 : null);
//					}
//
//					mAdapter.notifyDataSetChanged();
//					new Handler().postDelayed(new Runnable() {
//
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							lv_common.onRefreshComplete();
//						}
//					}, 1000);
//				}
//
//				if (mAdapter.getList().isEmpty()) {
//
//					account_nodata.setVisibility(View.VISIBLE);
//					lv_common.setVisibility(View.GONE);
//				} else {
//					account_nodata.setVisibility(View.GONE);
//					lv_common.setVisibility(View.VISIBLE);
//				}
//			}
//		}.execute(currPage);
//	}
//
//}
