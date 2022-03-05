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
//import com.yssj.YJApplication;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.adpter.AllPostListPagerAdapter;
//import com.yssj.ui.base.BasePager;
//
//public class HotPostListPager extends BasePager {
//	private HashMap<String, Object> map;
//	
//	private PullToRefreshListView lv_cirlce;
//	private int currPage = 1;
//	private AllPostListPagerAdapter mAdapter;
//	
//	private boolean flag = true;		// 判断上拉、下拉的标志
//	
//	private LinearLayout circle_nodata;
//	private Button btn_view_allcircle;
//	private TextView tv_qin,tv_no_join;
//	
//	public HotPostListPager(Context context,HashMap<String, Object> map) {
//		super(context);
//		this.map = map;
//	}
//
//	@Override
//	public View initView() {
//		view = View.inflate(context, R.layout.circle_list, null);
//		lv_cirlce = (PullToRefreshListView) view.findViewById(R.id.lv_cirlce);
//		
//		circle_nodata = (LinearLayout) view.findViewById(R.id.circle_nodata);
//		
//		btn_view_allcircle = (Button) view.findViewById(R.id.btn_view_allcircle);
//		btn_view_allcircle.setVisibility(View.GONE);
//		
//		tv_qin = (TextView) view.findViewById(R.id.tv_qin);
//		tv_qin.setText("O(∩_∩)O~亲~");
//		tv_no_join = (TextView) view.findViewById(R.id.tv_no_join);
//		tv_no_join.setText("暂无热门帖子");
//		
//		return view;
//	}
//	
//	@Override
//	public void initData() {
//		currPage = 1 ;
//		queryAllPostListData();
//		mAdapter = new AllPostListPagerAdapter(context,(String)map.get("circle_id"));
//		lv_cirlce.setAdapter(mAdapter);
//		
//		super.initIndicator(lv_cirlce);
//		
//		lv_cirlce.setMode(Mode.BOTH);
//		
//		lv_cirlce.setOnRefreshListener(new OnRefreshListener2<ListView>() {
//			@Override
//			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//					flag = true;
//					
//					currPage = 1 ;
//					queryAllPostListData();
//			}
//
//			@Override
//			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//					flag = false;
//					currPage ++;
//					queryAllPostListData();
//			}
//		});
//	}
//	
//	
//	
//	private void queryAllPostListData() {
//		new SAsyncTask<Integer, Void, Map<String,List<HashMap<String, Object>>> >((FragmentActivity) context,null, R.string.wait){
//
//			@Override
//			protected Map<String,List<HashMap<String, Object>>> doInBackground(FragmentActivity context, Integer... params) throws Exception {
//				
//				return YJApplication.instance.isLoginSucess()?ComModel2.getPostList(context,
//						(String)map.get("circle_id"), currPage,"null",0,1):
//							ComModel2.getPostList2(context,
//									(String)map.get("circle_id"), currPage,"null",0,1);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					Map<String,List<HashMap<String, Object>>> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				
//				if (e != null) {// 查询异常
//					circle_nodata.setVisibility(View.VISIBLE);
//					lv_cirlce.setVisibility(View.GONE);
//					lv_cirlce.onRefreshComplete();
//				} else {
//					
//					if(flag){
//						
//						if(result != null && (result.get("listData").size() == 0 || result.get("listData").isEmpty())){
//							circle_nodata.setVisibility(View.VISIBLE);
//							lv_cirlce.setVisibility(View.GONE);
//						}
//						
//						mAdapter.addItemFirst(result != null ?result.get("listData"):null);
//					}else{
//						mAdapter.addItemLast(result != null ?result.get("listData"):null);
//					}
//					
//					mAdapter.notifyDataSetChanged();
//					lv_cirlce.onRefreshComplete();
//					
//				}
//			}
//			
//		}.execute(currPage);
//	}
//
//}
