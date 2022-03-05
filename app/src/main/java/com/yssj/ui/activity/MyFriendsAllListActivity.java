package com.yssj.ui.activity;

import java.util.HashMap;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.adpter.CenterMessageHuatiPager;
import com.yssj.ui.adpter.MyfriendsListAdapter;
import com.yssj.ui.base.BasicActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MyFriendsAllListActivity extends BasicActivity implements OnClickListener {
	private TextView tvTitle_base;
	private String type = "";
	private LinearLayout img_back;
	private ImageButton ib_shuoming;
	private TextView tv_qin, tv_no_join;
	private LinearLayout account_nodata;
	private Button btn_view_allcircle;
	private PullToRefreshListView lv_common;
	private int currPage;
	private MyfriendsListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myfriendsall);
		type = getIntent().getStringExtra("type");
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		img_back = (LinearLayout) findViewById(R.id.img_back);
		ib_shuoming = (ImageButton) findViewById(R.id.ib_shuoming);
		tv_qin = (TextView) findViewById(R.id.tv_qin);
		tv_no_join = (TextView) findViewById(R.id.tv_no_join);
		account_nodata = (LinearLayout) findViewById(R.id.account_nodata);
		btn_view_allcircle = (Button) findViewById(R.id.btn_view_allcircle);
		btn_view_allcircle.setVisibility(View.GONE);
		lv_common = (PullToRefreshListView) findViewById(R.id.lv_common);
		img_back.setOnClickListener(this);
		ib_shuoming.setOnClickListener(this);

		tv_qin.setText("O(∩_∩)O~亲~");
		if (type.equals("1")) {
			tvTitle_base.setText("好友");
			tv_no_join.setText("暂无好友哦");
		} else {
			tvTitle_base.setText("密友");
			tv_no_join.setText("暂密友哦");
		}

		currPage = 1;

		// 好友列表
		getFriends();

		mAdapter = new MyfriendsListAdapter(this);
		lv_common.setAdapter(mAdapter);
		// 设置刷新文字
		super.initIndicator(lv_common);
		lv_common.setMode(Mode.BOTH);
		lv_common.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			// 下拉刷新
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				currPage = 1;
				getFriends();
			}

			// 上拉加载更
			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				currPage++;
				getFriends();
			}
		});

	}

	protected void getFriends() {
		new SAsyncTask<Integer, Void, List<HashMap<String, Object>>>(this, R.string.wait) {
			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Integer... params)
					throws Exception {

				return ModQingfeng.getMyFridensList(context, currPage + "", type);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
				super.onPostExecute(context, result, e);

				if (e != null) {
					if (currPage > 1) { // 上拉加载更多
						if (result.size() >= 1) {
							mAdapter.addItemLast(result);
							mAdapter.notifyDataSetChanged();
							lv_common.onRefreshComplete();
						} else {
							lv_common.onRefreshComplete();
						}

					} else { // 第一次进来或者下拉刷新
						if (result == null || result.size() == 0) {
							account_nodata.setVisibility(View.VISIBLE);
							lv_common.setVisibility(View.GONE);
							lv_common.onRefreshComplete();
						} else {
							account_nodata.setVisibility(View.GONE);
							lv_common.setVisibility(View.VISIBLE);
							mAdapter.addItemFirst(result != null ? result : null);
							mAdapter.notifyDataSetChanged();
							lv_common.onRefreshComplete();
						}

					}

				} else {
					account_nodata.setVisibility(View.VISIBLE);
					lv_common.setVisibility(View.GONE);
					lv_common.onRefreshComplete();
				}

			}

		}.execute(currPage);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.img_back:
			onBackPressed();

			break;
		case R.id.ib_shuoming:
			onBackPressed();

			break;

		default:
			break;
		}

	}

}
