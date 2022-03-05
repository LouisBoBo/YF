package com.yssj.ui.activity.main;

import java.util.HashMap;
import java.util.List;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.data.YDBHelper;
import com.yssj.model.ComModel2;
import com.yssj.ui.adpter.MoreSubAdapter;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.ToastUtil;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 专题---更多专题和 购物页——更多供应商
 * 
 * @author Administrator
 * @date 2016年10月12日下午3:28:17
 */
public class MoreSubjectActivity extends BasicActivity implements OnClickListener {
	public static PullToRefreshListView r_list_view;
	private MoreSubAdapter mAdapter;
	private View img_back;
	private TextView tv_title;
	private int mType = 1;// 1：初始化数据；2：加载更多数据
	private int index = 1;
	private String singvalue;
	private ImageView fight_groups_icon;
	public static boolean isEnd;
	private boolean isforcelookMatch;
	private List<HashMap<String, String>> listDataTop;
	private YDBHelper dbHelp;

	public static int width;
	// 更多供应商
	private boolean isManufacture;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		ActionBar aBar = getActionBar();
//		aBar.setDisplayHomeAsUpEnabled(true);
//		aBar.hide();
		context = this;
		AppManager.getAppManager().addActivity(this);
		setContentView(R.layout.morsub_activity);
		dbHelp = new YDBHelper(this);
		isforcelookMatch = getIntent().getBooleanExtra("isforcelookMatch", false);
		isManufacture = getIntent().getBooleanExtra("isManufacture", false);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		initView();
	}

	private void initView() {
		img_back = findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		r_list_view = (PullToRefreshListView) findViewById(R.id.r_list_view);
		tv_title = (TextView) findViewById(R.id.tv_title);
		if (isManufacture) {
			tv_title.setText("所有人气品牌");
		}
		mAdapter = new MoreSubAdapter(this, isforcelookMatch, isManufacture);
		r_list_view.setAdapter(mAdapter);
		setListViewRefresh();

		if (isManufacture) {
//			String sql = "select * from supp_label where type = 1 order by _id";
			String sql = "select * from supp_label where type = 1 order by sort desc";
			listDataTop = dbHelp.query(sql);
			mAdapter.setData(listDataTop);
		} else {
			initData(index);
		}

	}

	private void setListViewRefresh() {
		if (isManufacture) {
			r_list_view.setMode(Mode.DISABLED);
		} else {
			r_list_view.setMode(Mode.BOTH);
			r_list_view.setOnRefreshListener(new OnRefreshListener2<ListView>() {

				@Override
				public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
					if (!isManufacture) {
						index = 1;
						mType = 1; // 下拉
						initData(index);
						// r_list_view.onRefreshComplete();
					}

				}

				@Override
				public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
					// r_list_view.onRefreshComplete();
					if (!isManufacture) {
						index++;
						mType = 2; // 上拉
						initData(index);
					}

				}

			});

		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		isEnd = false;
	}

	private void initData(final int index) {

		new SAsyncTask<String, Void, List<HashMap<String, String>>>(this, 0) {
			@Override
			protected List<HashMap<String, String>> doInBackground(FragmentActivity context, String... params)
					throws Exception {

				return ComModel2.getMoreSub(context, index);
			}

			@Override
			protected boolean isHandleException() {

				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, String>> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				if (e == null) {
					if (mType == 1) { // 下拉
						if (result == null || result.size() == 0) {
							r_list_view.setVisibility(View.GONE);
						} else {
							if (result.size() < 10) {
								isEnd = true; // 已经到底
							}
							r_list_view.setVisibility(View.VISIBLE);
						}

						mAdapter.setData(result);

					} else if (mType == 2) { // 上拉
						//
						// if (result == null || result.size() < 5) {
						// isEnd = true;
						// mAdapter.notifyDataSetChanged();
						// }
						//
						// if (result == null || result.size() == 0) {
						// ToastUtil.showShortText(context, "没有更多商品了哦~");
						//
						// } else {
						// mAdapter.addItemLast(result);
						// r_list_view.getRefreshableView().smoothScrollBy(100,
						// 10);
						// }
						//

						int size = 0;
						try {
							size = result.size();
						} catch (Exception e2) {
							e2.printStackTrace();
						}

						if (0 < size && size < 10) { // 拿到了数据但是不到10个
							isEnd = true; // 已经到底
							mAdapter.addItemLast(result); // 添加进去
							r_list_view.getRefreshableView().smoothScrollBy(100, 10); // 自动滚动一下
						}

						if (size == 0) { // 没有拿到数据
							isEnd = true; // 已经到底
							mAdapter.addItemLast(result); // 添加进去
							r_list_view.getRefreshableView().setSelection(mAdapter.getCount() - 1);// 让end自动显示出来

							/**
							 * 滚动到底部 android:stackFromBottom="true"
							 * android:transcriptMode="alwaysScroll"
							 */

							ToastUtil.showShortText(context, "没有更多商品了哦~");
						}

						if (size == 10) { // 正好就拿到了10条
							mAdapter.addItemLast(result); // 添加进去
							r_list_view.getRefreshableView().smoothScrollBy(100, 10);
						}

					}

				}
				r_list_view.onRefreshComplete();
			}

		}.execute();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.img_back:
			onBackPressed();

			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);

	}

}
