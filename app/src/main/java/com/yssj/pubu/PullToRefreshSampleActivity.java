//package com.yssj.pubu;
//
//import java.util.HashMap;
//import java.util.List;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.LoadingDialog;
//import com.yssj.model.ComModel2;
//import com.yssj.model.ModQingfeng;
//import com.yssj.pubu.XListView.IXListViewListener;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.SharedPreferencesUtil;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//
//public class PullToRefreshSampleActivity extends BasicActivity implements IXListViewListener {
//	private XListView mAdapterView = null;
//	private StaggeredAdapter mAdapter = null;
//	private int currentPage = 0;
//
//	private Context mContext;
//
//	@SuppressWarnings("unchecked")
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.act_pull_to_refresh_sample);
//		mContext = this;
//		mAdapterView = (XListView) findViewById(R.id.list);
//		mAdapterView.setPullLoadEnable(true);
//		mAdapterView.setXListViewListener(this);
//		mAdapterView.setPullLoadEnable(true);
//		mAdapter = new StaggeredAdapter(this, mAdapterView);
//
//	}
//
//	public class StaggeredAdapter extends BaseAdapter {
//		private Context mContext;
//		private XListView mListView;
//
//		public StaggeredAdapter(Context context, XListView xListView) {
//			mContext = context;
//			mListView = xListView;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//
//			ViewHolder holder;
//
//			if (convertView == null) {
//				LayoutInflater layoutInflator = LayoutInflater.from(parent.getContext());
//				convertView = layoutInflator.inflate(R.layout.infos_list2, null);
//				holder = new ViewHolder();
//				holder.left = (ImageView) convertView.findViewById(R.id.left);
//				convertView.setTag(holder);
//			}
//
//			return convertView;
//		}
//
//		class ViewHolder {
//			ImageView left;
//		}
//
//		@Override
//		public int getCount() {
//			return 20;
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			return arg0;
//		}
//
//		@Override
//		public long getItemId(int arg0) {
//			return 0;
//		}
//
//		// public void addItemLast(List<DuitangInfo> datas) {
//		// mInfos.addAll(datas);
//		// }
//
//		// public void addItemTop(List<DuitangInfo> datas) {
//		// for (DuitangInfo info : datas) {
//		// mInfos.addFirst(info);
//		// }
//		// }
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		return super.onCreateOptionsMenu(menu);
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//
//		return true;
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		mAdapterView.setAdapter(mAdapter);
//	}
//
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//
//	}
//
//	@Override
//	public void onRefresh() {
//		// AddItemToContainer(++currentPage, 1);
//		mAdapterView.stopRefresh();
//
//	}
//
//	@Override
//	public void onLoadMore() {
//		// mAdapterView.stopRefresh();
//		// AddItemToContainer(++currentPage, 2);
//
//		new SAsyncTask<Void, Void, List<HashMap<String, Object>>>((FragmentActivity) mContext, R.string.wait) {
//
//			@Override
//			protected void onPreExecute() {
//				// TODO Auto-generated method stub
//				super.onPreExecute();
//				LoadingDialog.show((FragmentActivity) mContext);
//			}
//
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Void... params)
//					throws Exception {
//
//				return ComModel2.getIntimateList(context, "1", 1, "");
//
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
//				if (e != null) {// 查询异常
//					return;
//				}
//				mAdapterView.stopRefresh();
//
//				super.onPostExecute(context, result, e);
//			}
//
//		}.execute();
//
//	}
//
//}// end of class
