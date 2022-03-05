//package com.yssj.ui.activity;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//
//import com.yssj.activity.R;
//import com.yssj.app.AppManager;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.NoScrollGridView;
//import com.yssj.data.YDBHelper;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.entity.UserInfo;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.adpter.FilterGridViewAdapter;
//import com.yssj.ui.adpter.FilterGridViewAdapter.CheckCallback;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.YCache;
//
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
///***
// * 我喜欢
// * 
// * @author Administrator
// * 
// */
//public class TaskMineLikeActivity extends BasicActivity implements
//		CheckCallback {
//
//	private AppManager appManager;
//
//	private YDBHelper helper;
//
//	private List<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();
//
//	// private ListView listView;
//
//	private List<ArrayList<HashMap<String, String>>> listDatas = new ArrayList<ArrayList<HashMap<String, String>>>();
//
//	// private ListViewAdapter mAdapter;
//
//	private LinkedList<HashMap<String, String>> linkList = new LinkedList<HashMap<String, String>>();
//
//	private LinearLayout container, container_item;
//	private HashMap<Integer, Boolean> isCheckMap = new HashMap<Integer, Boolean>();
//
//	private UserInfo userInfo;
//	private ArrayList<String> list_icon;
//	private ArrayList<String> list_text;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_perfect_prefereces);
//		list_icon = new ArrayList<String>();
//		list_text = new ArrayList<String>();
//		aBar.hide();
//		helper = new YDBHelper(this);
//		appManager = AppManager.getAppManager();
//		findViewById(R.id.tv_commit).setOnClickListener(this);
//		// listView = (ListView) findViewById(R.id.listView);
//		container = (LinearLayout) findViewById(R.id.container);
//		container_item = (LinearLayout) findViewById(R.id.container_item);
//
//		((TextView) findViewById(R.id.tvTitle_base)).setText("完善喜好");
//		findViewById(R.id.imgbtn_left_icon).setVisibility(View.GONE);
//
//		userInfo = (UserInfo) getIntent().getSerializableExtra("userinfo");
//		queryData();
//	}
//
//	private void queryData() {
//		new SAsyncTask<Void, Void, Void>(this, R.string.wait) {
//
//			@Override
//			protected Void doInBackground(Void... params) {
//				// TODO Auto-generated method stub
//				categoryList = helper
//						.query("select * from tag_info where p_id=0 and is_show=1 order by sequence");
//
//				ArrayList<HashMap<String, String>> listChild;
//				for (int i = 0; i < categoryList.size(); i++) {
//					if (i != 0 && i != 1 && i != 2 && i != 3 && i != 6) {
//						listChild = (ArrayList<HashMap<String, String>>) helper
//								.query("select * from tag_info where p_id = "
//										+ categoryList.get(i).get("_id")
//										+ " order by sequence");
//						for (HashMap<String, String> map : listChild) {
//							map.put("isChecked", "0");
//						}
//						listDatas.add(listChild);
//						list_text.add(categoryList.get(i).get("attr_name"));
//						list_icon.add(categoryList.get(i).get("icon"));
//					}
//					
//				}
//				return super.doInBackground(params);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, Void result) {
//				// TODO Auto-generated method stub
//				addItemView();
//				super.onPostExecute(context, result);
//			}
//
//		}.execute();
//	}
//
//	@Override
//	public boolean onMenuItemSelected(int featureId, MenuItem item) {
//		if (item.getItemId() == android.R.id.home) {
//			appManager.finishActivity();
//		}
//		return super.onMenuItemSelected(featureId, item);
//	}
//
//	@Override
//	public void onBackPressed() {
//		appManager.finishActivity();
//
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tv_commit:
//			if (judgeCheck()) {
//				summitMyLike(v);
//			} else {
//				ToastUtil.showShortText(this, "每一项至少选一项");
//			}
//			break;
//		default:
//			break;
//		}
//
//	}
//
//	/***
//	 * 返回主界面
//	 */
//	private void summitMyLike(View v) {
//
//		StringBuffer sb = new StringBuffer();
//		
//		String hobby = YCache.getCacheUser(context).getHobby();
//		sb.append(hobby);
//		for (int i = 0; i < linkList.size(); i++) {
//			HashMap<String, String> map = linkList.get(i);
//			sb.append(map.get("_id"));
//			if (i != linkList.size() - 1) {
//				sb.append(",");
//			}
//		}
//
//		new SAsyncTask<String, Void, ReturnInfo>(this, null, R.string.wait) {
//
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context,
//					String... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.setMyHobby(context, params[0]);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					ReturnInfo result, Exception e) {
//				// TODO Auto-generated method stub
//				if (null == e) {
//					if (result.getStatus().equals("1")) {
//						setResult(RESULT_OK);
//						onBackPressed();
//					}
//				}
//				super.onPostExecute(context, result, e);
//			}
//
//		}.execute(sb.toString());
//
//	}
//
//	private void addItemView() {
//		container_item.removeAllViews();
//		LayoutInflater inflater = LayoutInflater.from(this);
//		LinearLayout layoutNotice = (LinearLayout) inflater.inflate(
//				R.layout.my_like_notice, null);
//		container_item.addView(layoutNotice);
//		for (int i = 0; i < listDatas.size(); i++) {
//			LinearLayout layout = (LinearLayout) inflater.inflate(
//					R.layout.listview_item, null);
//			TextView listview_item_textview = (TextView) layout
//					.findViewById(R.id.listview_item_textview);
//			NoScrollGridView listview_item_gridview = (NoScrollGridView) layout
//					.findViewById(R.id.listview_item_gridview);
//			ImageView img_icon = (ImageView) layout.findViewById(R.id.img_icon);
//			SetImageLoader.initImageLoader(context, img_icon,
//					list_icon.get(i), "");
//			listview_item_textview
//					.setText(list_text.get(i));
//			ArrayList<HashMap<String, String>> arrayListForEveryGridView = this.listDatas
//					.get(i);
//			FilterGridViewAdapter gridViewAdapter = new FilterGridViewAdapter(
//					this, arrayListForEveryGridView);
//			gridViewAdapter.setListener(this);
//			listview_item_gridview.setAdapter(gridViewAdapter);
//			container_item.addView(layout);
//
//		}
//	}
//
//	private void addSelectView() {
//		container.removeAllViews();
//		for (int i = 0; i < linkList.size(); i++) {
//			HashMap<String, String> map = linkList.get(i);
//			TextView tv = new TextView(this);
//			tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//					LayoutParams.WRAP_CONTENT));
//			tv.setBackgroundResource(R.drawable.check_bg);
//			tv.setGravity(Gravity.CENTER);
//			tv.setPadding(10, 0, 25, 0);
//			tv.setText(map.get("attr_name"));
//			container.addView(tv);
//		}
//	}
//
//	/*
//	 * @Override public void onCheckCallback(int positon, boolean isChecked,
//	 * HashMap<String, String> map) { // TODO Auto-generated method stub if
//	 * (isChecked) { MyLogYiFu.e("_id", map.get("_id")); linkList.add(map);
//	 * addSelectView(); } else { MyLogYiFu.e("unChecked_id", map.get("_id"));
//	 * linkList.remove(map); addSelectView(); }
//	 * 
//	 * }
//	 */
//
//	private boolean judgeCheck() {
//		// for (int n = 0; n < listDatas.size(); n++) {
//		// isCheckMap.put(n, false);
//		// }
//		// for (int i = 0; i < linkList.size(); i++) {
//		// HashMap<String, String> hashMap = linkList.get(i);
//		// for (int j = 0; j < listDatas.size(); j++) {
//		// if (listDatas.get(j).contains(hashMap)) {
//		// isCheckMap.put(j, true);
//		// break;
//		// }
//		//
//		// }
//		// }
//		// MyLogYiFu.e("TAG", "ischeckMap=" + isCheckMap.toString() + "==size"
//		// + isCheckMap.size());
//		// for (int k = 0; k < isCheckMap.size(); k++) {
//		// if (!isCheckMap.get(k)) {
//		// return false;
//		// }
//		// }
//		if (linkList.size() == listDatas.size()) {
//			return true;
//		}
//
//		return false;
//	}
//
//	@Override
//	public void onCheckCallback(int positon, boolean isChecked,
//			HashMap<String, String> map, FilterGridViewAdapter mAdapter) {
//		// TODO Auto-generated method stub
//		if (isChecked) {
//			List<HashMap<String, String>> list = mAdapter.getData();
//			for (int i = 0; i < list.size(); i++) {
//				list.get(i).put("isChecked", "0");
//			}
//			list.get(positon).put("isChecked", "1");
//			mAdapter.notifyDataSetChanged();
//			// listMap.add(list.get(positon));
//			if (!linkList.contains(list.get(positon))) {
//				linkList.add(list.get(positon));
//			}
//
//		} else {
//			List<HashMap<String, String>> list = mAdapter.getData();
//			list.get(positon).put("isChecked", "0");
//			mAdapter.notifyDataSetChanged();
//			// listMap.remove(list.get(positon));
//			if (linkList.contains(list.get(positon))) {
//				linkList.remove(list.get(positon));
//			}
//		}
//	}
//
//}
