//
//package com.yssj.ui.pager;
//
//import java.util.HashMap;
//import java.util.List;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v4.app.FragmentActivity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.yssj.YJApplication;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.ToLoginDialog;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.circles.CircleCommonFragmentActivity;
//import com.yssj.ui.adpter.CircleMainPagerAdapter;
//import com.yssj.ui.base.BaseFragment;
//import com.yssj.ui.base.BasePager;
//
//public class MinePager extends BasePager implements OnClickListener {
//	
//	private PullToRefreshListView lv_cirlce;
//	private RelativeLayout rl_bottom;
//	private ImageButton img_star;
//	
//	private int currPage = 1;
//	private CircleMainPagerAdapter mAdapter;
//	
//	private boolean flag = true;		// 判断上拉、下拉的标志
//	private ToLoginDialog loginDialog;
//	
//	private LinearLayout circle_nodata;
//	private Button btn_view_allcircle;
//
//	public MinePager(Context context) {
//		super(context);
//	}
//
//	@Override
//	public View initView() {
//		view = View.inflate(context, R.layout.circle_list_allpager, null);
//		rl_bottom = (RelativeLayout) view.findViewById(R.id.rl_bottom);
//		rl_bottom.setVisibility(View.VISIBLE);
//		rl_bottom.setOnClickListener(this);
//		img_star = (ImageButton) view.findViewById(R.id.img_star);
//		img_star.setVisibility(View.VISIBLE);
//		img_star.setOnClickListener(this);
//		
//		lv_cirlce = (PullToRefreshListView) view.findViewById(R.id.lv_cirlce);
//		
//		circle_nodata = (LinearLayout) view.findViewById(R.id.circle_nodata);
//		
//		btn_view_allcircle = (Button) view.findViewById(R.id.btn_view_allcircle);
//		btn_view_allcircle.setOnClickListener(this);
//		
//		return view;
//	}
//	
//	@Override
//	public void initData() {
//		currPage=1;
//		queryMineData();
//		mAdapter = new CircleMainPagerAdapter(context);
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
//					currPage = 1 ;
//					queryMineData();
//			}
//
//			@Override
//			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//					flag = false;
//					currPage ++;
//					queryMineData();
//			}
//		});
//	}
//	
//	
//	private void queryMineData() {
//		new SAsyncTask<Integer, Void, List<HashMap<String, Object>> >((FragmentActivity) context,null){
//
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Integer... params) throws Exception {
//				if(!YJApplication.instance.isLoginSucess()){
//					
//					return null;
//				}
//				return ComModel2.getMyCircleList(context, currPage,"");
//			}
//			
//			
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<HashMap<String, Object>> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				
//				if (e != null) {// 查询异常
//					
//					lv_cirlce.onRefreshComplete();
//				} else {
//					
//					if(flag){
//						
////						if(result != null && result.size() == 0){
////							circle_nodata.setVisibility(View.VISIBLE);
////							lv_cirlce.setVisibility(View.GONE);
////						}
//						
//						mAdapter.addItemFirst(result != null ?result:null);
//					}else{
//						mAdapter.addItemLast(result != null ?result:null);
//					}
//					
//					mAdapter.notifyDataSetChanged();
//					lv_cirlce.onRefreshComplete();
//					
//				}
//				if(mAdapter.getList().isEmpty()){
//					circle_nodata.setVisibility(View.VISIBLE);
//					lv_cirlce.setVisibility(View.GONE);
//				}else{
//					circle_nodata.setVisibility(View.GONE);
//					lv_cirlce.setVisibility(View.VISIBLE);
//				}
//				
//			}
//
//		}.execute(currPage);
//	}
//	
//	public interface TransToAllPager{
//		void turnToAll();
//	}
//	private TransToAllPager mTransToAllPager;
//	public void setTransToAllPager(BaseFragment fragment){
//		this.mTransToAllPager = (TransToAllPager) fragment;
//	}
//
//	@Override
//	public void onClick(View v) {
//		Intent intent;
//		switch (v.getId()) {
//		case R.id.rl_bottom:  // 更多圈子
//			mTransToAllPager.turnToAll();
//			break;
//		case R.id.btn_view_allcircle:  // 查看所有圈子
//			mTransToAllPager.turnToAll();
//			break;
//		case R.id.img_star:  // 我的记录
//			if(!YJApplication.instance.isLoginSucess()){
//				
//				if(loginDialog==null){
//					loginDialog=new ToLoginDialog(context);
//				}
//				loginDialog.show();
//				return;
//			}
//			intent = new Intent(context, CircleCommonFragmentActivity.class);
//			intent.putExtra("flag", "myRecord");
//			context.startActivity(intent);
//			break;
//
//		}
//	}
//
//}
