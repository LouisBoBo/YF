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
//import com.yssj.ui.adpter.CircleMyRecordAdapter;
//import com.yssj.ui.base.BasePager;
//
//public class DynamicPager extends BasePager {
//	private PullToRefreshListView lv_myrecord;
//	private int currPage = 1;
//	private CircleMyRecordAdapter mAdapter;
//	
//	private boolean flag = true;		// 判断上拉、下拉的标志
//	
//	private LinearLayout circle_nodata;
//	private Button btn_view_allcircle;
//	private TextView tv_qin,tv_no_join;
//	
//	private String user_id;
//
//	public DynamicPager(Context context,String user_id) {
//		super(context);
//		this.user_id = user_id;
//	}
//	
//	@Override
//	public View initView() {
//		view = View.inflate(context, R.layout.circle_record_list, null);
//		lv_myrecord = (PullToRefreshListView) view.findViewById(R.id.lv_myrecord);
//		
//		circle_nodata = (LinearLayout) view.findViewById(R.id.circle_nodata);
//		
//		btn_view_allcircle = (Button) view.findViewById(R.id.btn_view_allcircle);
//		btn_view_allcircle.setVisibility(View.GONE);
//		
//		tv_qin = (TextView) view.findViewById(R.id.tv_qin);
//		tv_qin.setText("O(∩_∩)O~亲~");
//		tv_no_join = (TextView) view.findViewById(R.id.tv_no_join);
//		tv_no_join.setText("还没有发布动态哦 !");
//		
//		return view;
//	}
//	
//	@Override
//	public void initData() {
//		currPage=1;
//		queryDynamicListData();
//		mAdapter = new CircleMyRecordAdapter(context);
//		lv_myrecord.setAdapter(mAdapter);
//		
//		super.initIndicator(lv_myrecord);
//		
//		lv_myrecord.setMode(Mode.BOTH);
//		
//		lv_myrecord.setOnRefreshListener(new OnRefreshListener2<ListView>() {
//			@Override
//			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//					flag = true;
//					
//					currPage = 1 ;
//					queryDynamicListData();
//			}
//
//			@Override
//			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//					flag = false;
//					currPage ++;
//					queryDynamicListData();
//			}
//		});
//	}
//	
//	
//	
//	private void queryDynamicListData() {
//		new SAsyncTask<Integer, Void, List<HashMap<String, Object>> >((FragmentActivity) context,null, R.string.wait){
//
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Integer... params) throws Exception {
//				if(YJApplication.instance.isLoginSucess()){
//					return ComModel2.getMyRecordList(context, currPage, "false", user_id);
//				}else{
//					return ComModel2.getMyRecordList2(context, currPage, "false", user_id);
//				}
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
//					circle_nodata.setVisibility(View.VISIBLE);
//					lv_myrecord.setVisibility(View.GONE);
//					lv_myrecord.onRefreshComplete();
//				} else {
//					
//					if(flag){
//						
//						if(result != null && result.size() == 0){
//							circle_nodata.setVisibility(View.VISIBLE);
//							lv_myrecord.setVisibility(View.GONE);
//						}
//						
//						mAdapter.addItemFirst(result != null ?result:null);
//					}else{
//						mAdapter.addItemLast(result != null ?result:null);
//					}
//					
//					mAdapter.notifyDataSetChanged();
//					lv_myrecord.onRefreshComplete();
//					
//				}
//			}
//
//			
//		}.execute(currPage);
//	}
//
//}
