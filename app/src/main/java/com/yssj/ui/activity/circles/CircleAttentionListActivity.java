//<<<<<<< .mine
////package com.yssj.ui.activity.circles;
//=======
//package com.yssj.ui.activity.circles;
//
//import java.util.HashMap;
//import java.util.List;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.handmark.pulltorefresh.library.ILoadingLayout;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.yssj.YJApplication;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.adpter.CircleAttentionAdapter;
//import com.yssj.ui.base.BasicActivity;
//
//public class CircleAttentionListActivity extends BasicActivity implements OnClickListener {
//	private LinearLayout img_back;
//	private TextView tvTitle_base;
//	private PullToRefreshListView lv_homepage_fans_list;
//	
//	private int currPage = 1;
//	
//	private boolean flag = true;		// 判断上拉、下拉的标志
//	
//	private LinearLayout circle_nodata;
//	private Button btn_view_allcircle;
//	private TextView tv_qin,tv_no_join;
//	
//	private CircleAttentionAdapter mAdapter;
//	
//	private String user_id;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
////		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		aBar.hide();
////		
////		initView();
////		initListener();
////		initData();
//	}
//
////
////	private void initView() {
////		setContentView(R.layout.circle_homepage_fans_list);
////		img_back = (LinearLayout) findViewById(R.id.img_back);
////		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
////		lv_homepage_fans_list = (PullToRefreshListView) findViewById(R.id.lv_homepage_fans_list);
////		
////		circle_nodata = (LinearLayout) findViewById(R.id.circle_nodata);
////		
////		btn_view_allcircle = (Button) findViewById(R.id.btn_view_allcircle);
////		btn_view_allcircle.setVisibility(View.GONE);
////		
////		tv_qin = (TextView) findViewById(R.id.tv_qin);
////		tv_qin.setText("O(∩_∩)O~亲~");
////		tv_no_join = (TextView) findViewById(R.id.tv_no_join);
////		tv_no_join.setText("还没有关注哦 !");
////		
////	}
////	
////	private void initListener() {
////		img_back.setOnClickListener(this);
////		
////	}
//>>>>>>> .r26813
////
////import java.util.HashMap;
////import java.util.List;
////
////import android.content.Intent;
////import android.os.Bundle;
////import android.support.v4.app.FragmentActivity;
////import android.view.View;
////import android.view.View.OnClickListener;
////import android.view.Window;
////import android.widget.Button;
////import android.widget.LinearLayout;
////import android.widget.ListView;
////import android.widget.TextView;
////
////import com.handmark.pulltorefresh.library.ILoadingLayout;
////import com.handmark.pulltorefresh.library.PullToRefreshBase;
////import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
////import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
////import com.handmark.pulltorefresh.library.PullToRefreshListView;
////import com.yssj.YJApplication;
////import com.yssj.activity.R;
////import com.yssj.app.SAsyncTask;
////import com.yssj.model.ComModel2;
////import com.yssj.ui.adpter.CircleAttentionAdapter;
////import com.yssj.ui.base.BasicActivity;
////
////public class CircleAttentionListActivity extends BasicActivity implements OnClickListener {
////	private LinearLayout img_back;
////	private TextView tvTitle_base;
////	private PullToRefreshListView lv_homepage_fans_list;
////	
////	private int currPage = 1;
////	
////	private boolean flag = true;		// 判断上拉、下拉的标志
////	
////	private LinearLayout circle_nodata;
////	private Button btn_view_allcircle;
////	private TextView tv_qin,tv_no_join;
////	
////	private CircleAttentionAdapter mAdapter;
////	
////	private String user_id;
////
////	@Override
////	protected void onCreate(Bundle savedInstanceState) {
////		super.onCreate(savedInstanceState);
//////		requestWindowFeature(Window.FEATURE_NO_TITLE);
////		aBar.hide();
////		
//////		initView();
//////		initListener();
//////		initData();
////	}
////
////
//////	private void initView() {
//////		setContentView(R.layout.circle_homepage_fans_list);
//////		img_back = (LinearLayout) findViewById(R.id.img_back);
//////		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
//////		lv_homepage_fans_list = (PullToRefreshListView) findViewById(R.id.lv_homepage_fans_list);
//////		
//////		circle_nodata = (LinearLayout) findViewById(R.id.circle_nodata);
//////		
//////		btn_view_allcircle = (Button) findViewById(R.id.btn_view_allcircle);
//////		btn_view_allcircle.setVisibility(View.GONE);
//////		
//////		tv_qin = (TextView) findViewById(R.id.tv_qin);
//////		tv_qin.setText("O(∩_∩)O~亲~");
//////		tv_no_join = (TextView) findViewById(R.id.tv_no_join);
//////		tv_no_join.setText("还没有关注哦 !");
//////		
//////	}
//////	
//////	private void initListener() {
//////		img_back.setOnClickListener(this);
//////		
//////	}
//////
//////	private void initData() {
//////		tvTitle_base.setText("关注");
//////		user_id = getIntent().getStringExtra("user_id");
//////		
//////		queryMineData();
//////		
//////		mAdapter = new CircleAttentionAdapter(this);
//////		lv_homepage_fans_list.setAdapter(mAdapter);
//////		
//////		initIndicator(lv_homepage_fans_list);
//////		
//////		lv_homepage_fans_list.setMode(Mode.BOTH);
//////		
//////		lv_homepage_fans_list.setOnRefreshListener(new OnRefreshListener2<ListView>() {
//////			@Override
//////			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//////					flag = true;
//////					currPage = 1 ;
//////					queryMineData();
//////			}
//////
//////			@Override
//////			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//////					flag = false;
//////					currPage ++;
//////					queryMineData();
//////			}
//////		});
//////	}
//////	
//////	private void queryMineData() {
//////		new SAsyncTask<Integer, Void, List<HashMap<String, Object>> >(this,null, R.string.wait){
//////
//////			@Override
//////			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Integer... params) throws Exception {
//////				
//////				return YJApplication.instance.isLoginSucess()?
//////						ComModel2.getMyCircleList(context, currPage,user_id):
//////							ComModel2.getMyCircleList2(context, currPage,user_id);
//////			}
//////
//////
//////			@Override
//////			protected boolean isHandleException() {
//////				return true;
//////			}
//////			
//////			@Override
//////			protected void onPostExecute(FragmentActivity context,
//////					List<HashMap<String, Object>> result, Exception e) {
//////				super.onPostExecute(context, result, e);
//////				
//////				if (e != null) {// 查询异常
//////					circle_nodata.setVisibility(View.VISIBLE);
//////					lv_homepage_fans_list.setVisibility(View.GONE);
//////					lv_homepage_fans_list.onRefreshComplete();
//////				} else {
//////					
//////					if(flag){
//////						
//////						if(result != null && result.size() == 0){
//////							circle_nodata.setVisibility(View.VISIBLE);
//////							lv_homepage_fans_list.setVisibility(View.GONE);
//////						}
//////						
//////						mAdapter.addItemFirst(result != null ?result:null);
//////					}else{
//////						mAdapter.addItemLast(result != null ?result:null);
//////					}
//////					
//////					mAdapter.notifyDataSetChanged();
//////					lv_homepage_fans_list.onRefreshComplete();
//////					
//////				}
//////			}
//////			
//////		}.execute(currPage);
//////	}
//////	
//////	private void initIndicator(PullToRefreshListView lv){
//////		ILoadingLayout startLabels = lv.getLoadingLayoutProxy(true, false);
//////		startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
//////		startLabels.setRefreshingLabel("正在刷新...");// 刷新时
//////		startLabels.setReleaseLabel("释放刷新...");// 下来达到一定距离时，显示的提示
//////
//////		ILoadingLayout endLabels = lv.getLoadingLayoutProxy(false, true);
//////		endLabels.setPullLabel("加载更多");
//////		endLabels.setRefreshingLabel("正在加载...");
//////		endLabels.setReleaseLabel("释放加载");
//////	}
//////
//////
//////	@Override
//////	public void onClick(View v) {
//////		Intent intent ;
//////		switch (v.getId()) {
//////		case R.id.img_back:
//////			finish();
//////			/*intent = new Intent(this, CircleCommonFragmentActivity.class);
//////			intent.putExtra("user_id", user_id);
//////			intent.putExtra("flag", "circleHomePage");
//////			startActivity(intent);*/
//////			break;
//////		}
//////	}
//////	@Override
//////	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
//////		// TODO Auto-generated method stub
//////		super.onActivityResult(arg0, arg1, arg2);
//////		if(arg0==3214){
//////			if(arg1==-1){
//////				initView();
//////				initListener();
//////				initData();
//////			}
//////		}
//////	}
////
////}
