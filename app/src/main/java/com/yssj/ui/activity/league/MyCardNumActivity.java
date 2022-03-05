//package com.yssj.ui.activity.league;
//
//import java.util.HashMap;
//import java.util.List;
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.app.FragmentActivity;
//import android.text.Html;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnFocusChangeListener;
//import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.TextView.OnEditorActionListener;
//
//import com.handmark.pulltorefresh.library.ILoadingLayout;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.EditTextWithDel;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.adpter.MyCardNumAdapter;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.DP2SPUtil;
//import com.yssj.utils.StringUtils;
//import com.yssj.utils.ToastUtil;
//
//public class MyCardNumActivity extends BasicActivity {
//	private HashMap<String, Object> map;
//
//	private PullToRefreshListView lv_common;
//	private int currPage = 1;
//	private MyCardNumAdapter mAdapter;
//	private EditTextWithDel et_search;
//
//	private boolean flag = true; // 判断上拉、下拉的标志
//
//	private LinearLayout account_nodata,myka;
//	private Button btn_view_allcircle;
//	private TextView tv_qin, tv_no_join, tvTitle_base;
//	private LinearLayout img_back;
//	private String search;
//	private EditText ll_null;
//
//	private String tag;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		aBar.hide();
//		setContentView(R.layout.pulltorefreshlistview_my_card_num);
//		myka = (LinearLayout) findViewById(R.id.myka);
//		myka.setBackgroundColor(Color.WHITE);
//		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
//		tvTitle_base.setText("我的卡号");
//
//		lv_common = (PullToRefreshListView) findViewById(R.id.lv_common);
//
//		account_nodata = (LinearLayout) findViewById(R.id.account_nodata);
//		ll_null        = (EditText) findViewById(R.id.ll_null);
//
//		btn_view_allcircle = (Button) findViewById(R.id.btn_view_allcircle);
//		btn_view_allcircle.setVisibility(View.GONE);
//
//		tv_qin = (TextView) findViewById(R.id.tv_qin);
//		tv_qin.setText("O(∩_∩)O~亲~");
//		tv_no_join = (TextView) findViewById(R.id.tv_no_join);
//		tv_no_join.setText("暂无卡号信息");
//		
//		
//		et_search = (EditTextWithDel) findViewById(R.id.et_search);
//		initEdittext();
//		et_search.setSelected(false);
//		ll_null.setFocusable(true);
//		et_search.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(View arg0, boolean arg1) {
//				// TODO Auto-generated method stub
//				if (arg1) {
//					et_search
//							.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
//					et_search.setTag(et_search.getHint());
//					et_search.setHint("");
//				} else {
//					et_search.setGravity(Gravity.CENTER);
//					et_search.setText("输入卡号或ID搜索…");
//					et_search.setHint((CharSequence) et_search.getTag());
//				}
//			}
//		});
//		et_search.setOnEditorActionListener(new OnEditorActionListener() {
//
//			@Override
//			public boolean onEditorAction(TextView v, int actionId,
//					KeyEvent event) {
//				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//					// 先隐藏键盘
//					((InputMethodManager) et_search.getContext()
//							.getSystemService(Context.INPUT_METHOD_SERVICE))
//							.hideSoftInputFromWindow(MyCardNumActivity.this
//									.getCurrentFocus().getWindowToken(),
//									InputMethodManager.HIDE_NOT_ALWAYS);
//
//					if (et_search.getText().toString().trim().equals("")) {
//						ToastUtil.showShortText(context, "请输入搜索条件");
//						return false;
//					}
//					if(StringUtils.containsEmoji(et_search.getText().toString().trim())){
//						ToastUtil.showShortText(context, "搜索条件不能含特殊字符");
//					}
//
//						search =  et_search.getText().toString();
//						initData();
//
//					return true;
//				}
//				return false;
//			}
//
//		});
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
//		mAdapter = new MyCardNumAdapter(context);
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
//				return ComModel2.getSupermanCardList(context, currPage,search);
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
//					}else{
//						
//					}
//				
//
//					if (flag) {
//
//
//						mAdapter.addItemFirst(result != null ? result
//								 : null);
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
//	private void initEdittext() {
//		final String hintText = "<img src=\"" + R.drawable.card_search
//				+ "\" /> 输入卡号或者ID搜索…";
//		et_search.setHint(Html.fromHtml(hintText, imageGetter, null));
//		et_search.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
//		et_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
//
//	}
//	Html.ImageGetter imageGetter = new Html.ImageGetter() {
//
//		@Override
//		public Drawable getDrawable(String source) {
//			Drawable drawable = null;
//			int rId = Integer.parseInt(source);
//			drawable = getResources().getDrawable(rId);
//			// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//			// drawable.getIntrinsicHeight());
//			drawable.setBounds(DP2SPUtil.dp2px(context, 0),
//					DP2SPUtil.dp2px(context, 0),
//					DP2SPUtil.dp2px(context, 17),
//					DP2SPUtil.dp2px(context, 17));
//			return drawable;
//		}
//	};
//
//}
