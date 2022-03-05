//package com.yssj.ui.pager;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
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
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.entity.MyCoupon;
//import com.yssj.model.ComModel2;
//import com.yssj.model.ModQingfeng;
//import com.yssj.ui.adpter.CenterMessageHuatiPager;
//import com.yssj.ui.adpter.MyCouponsPagerAdapter;
//import com.yssj.ui.adpter.MyIntergralPageAdapter;
//import com.yssj.ui.base.BasePager;
//import com.yssj.ui.fragment.IntergralDetailListFragment;
//
//public class CenterMessageHuaTi extends BasePager {
//	private HashMap<String, Object> map;
//
//	public CenterMessageHuaTi(Context context) {
//		super(context);
//
//	}
//
//	private PullToRefreshListView lv_common;
//	private int currPage = 1;
//	private CenterMessageHuatiPager mAdapter;
//
//	private List<HashMap<String, Object>> noReadMessage;// 未读消息
//	private List<HashMap<String, Object>> readMessageYet;// 已读消息
//
//	private List<HashMap<String, Object>> allMessAgeList;// 所有消息
//
//	private boolean flag = true; // 判断上拉、下拉的标志
//
//	private LinearLayout account_nodata;
//	private Button btn_view_allcircle;
//	private TextView tv_qin, tv_no_join;
//
//	@Override
//	public View initView() {
//		view = View.inflate(context, R.layout.pulltorefreshlistview_coupons, null);
//		lv_common = (PullToRefreshListView) view.findViewById(R.id.lv_common);
//
//		account_nodata = (LinearLayout) view.findViewById(R.id.account_nodata);
//
//		btn_view_allcircle = (Button) view.findViewById(R.id.btn_view_allcircle);
//		btn_view_allcircle.setVisibility(View.GONE);
//
//		tv_qin = (TextView) view.findViewById(R.id.tv_qin);
//		tv_qin.setText("O(∩_∩)O~亲~");
//		tv_no_join = (TextView) view.findViewById(R.id.tv_no_join);
//
//		return view;
//	}
//
//	@Override
//	public void initData() {
//
//		tv_no_join.setText("暂无相关话题哦");
//		currPage = 1;
//		queryOutputIntergralConponsData(true);
//		mAdapter = new CenterMessageHuatiPager(context);
//		lv_common.setAdapter(mAdapter);
//
//		super.initIndicator(lv_common);
//
//		lv_common.setMode(Mode.BOTH);
//
//		lv_common.setOnRefreshListener(new OnRefreshListener2<ListView>() {
//			@Override
//			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//				flag = true;
//
//				currPage = 1;
//				queryOutputIntergralConponsData(false);
//			}
//
//			@Override
//			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//				flag = false;
//				currPage++;
//				// queryOutputIntergralConponsData(false);
//				// 上拉加载更多只调已读消息的接口
//				getReadMessageYe();
//			}
//		});
//	}
//
//	/**
//	 * 获取未读消息
//	 * 
//	 * @param isFist
//	 *            :是否刚刚打开第一次请求数据
//	 * 
//	 */
//
//	private void queryOutputIntergralConponsData(final boolean isFist) {
//
//		// 取出未读消息
//		new SAsyncTask<Integer, Void, List<HashMap<String, Object>>>((FragmentActivity) context, R.string.wait) {
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Integer... params)
//					throws Exception {
//
//				return ModQingfeng.getMessageHuaTi(context, currPage, false);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
//				super.onPostExecute(context, result, e);
//
//				if (e != null) {// 查询异常
//					account_nodata.setVisibility(View.VISIBLE);
//					lv_common.setVisibility(View.GONE);
//					lv_common.onRefreshComplete();
//				} else {
//					noReadMessage = result;
//
//					if (isFist) {
//						getReadMessageYe();
//					} else {
//						if (flag) { // 下拉刷新
//
//							if (result != null) {
//								// account_nodata.setVisibility(View.VISIBLE);
//								// lv_common.setVisibility(View.GONE);
//
//								// allMessAgeList.addAll(0,result);
//
//								if (allMessAgeList != null && allMessAgeList.size() > 0) {
//									result.addAll(allMessAgeList);
//									mAdapter.addItemFirst(result != null ? result : null);
//								}
//
//							}
//						}
//						mAdapter.notifyDataSetChanged();
//						lv_common.onRefreshComplete();
//					}
//
//				}
//			}
//
//		}.execute(currPage);
//	}
//
//	protected void getReadMessageYe() {
//		// 取出已读消息
//		new SAsyncTask<Integer, Void, List<HashMap<String, Object>>>((FragmentActivity) context, R.string.wait) {
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Integer... params)
//					throws Exception {
//
//				return ModQingfeng.getMessageHuaTi(context, currPage, true);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
//				super.onPostExecute(context, result, e);
//
//				if (e != null) {// 查询异常
//
//					if (noReadMessage.size() > 0) {// 有新消息
//						allMessAgeList = noReadMessage;
//						mAdapter.addItemFirst(allMessAgeList != null ? allMessAgeList : null);
//						mAdapter.notifyDataSetChanged();
//						lv_common.onRefreshComplete();
//
//					} else {
//						account_nodata.setVisibility(View.VISIBLE);
//						lv_common.setVisibility(View.GONE);
//						lv_common.onRefreshComplete();
//					}
//
//				} else {
//					readMessageYet = result;
//					noReadMessage.addAll(readMessageYet);
//					allMessAgeList = noReadMessage;
//
//					// 上拉加载更多的都是已读消息
//					// 下拉刷新的都是未读消息
//					//
//					// if (flag) {
//					//
//					// if (result != null && (result.size() == 0 ||
//					// result.isEmpty())) {
//					// account_nodata.setVisibility(View.VISIBLE);
//					// lv_common.setVisibility(View.GONE);
//					// }
//					//
//					// mAdapter.addItemFirst(result != null ? result : null);
//					// } else {
//
//					if (!flag) {// 上拉
//						mAdapter.addItemLast(result != null ? result : null);
//					} else { // 第一次进来
//
//						if (allMessAgeList.size() == 0) {
//							account_nodata.setVisibility(View.VISIBLE);
//							lv_common.setVisibility(View.GONE);
//						} else {
//							account_nodata.setVisibility(View.GONE);
//							lv_common.setVisibility(View.VISIBLE);
//							mAdapter.addItemFirst(allMessAgeList != null ? allMessAgeList : null);
//						}
//
//					}
//
//					// }
//
//					mAdapter.notifyDataSetChanged();
//					lv_common.onRefreshComplete();
//				}
//			}
//
//		}.execute(currPage);
//
//	}
//
//}
