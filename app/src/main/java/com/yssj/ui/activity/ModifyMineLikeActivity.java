package com.yssj.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.NoScrollGridView;
import com.yssj.data.YDBHelper;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.adpter.FilterGridViewAdapter;
import com.yssj.ui.adpter.FilterGridViewAdapter.CheckCallback;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/***
 * 修改我的喜好标签
 * 
 * @author Administrator
 * 
 */
//public class ModifyMineLikeActivity extends BasicActivity implements CheckCallback {
//
//	private AppManager appManager;
//
//	private YDBHelper helper ;
//	
//	//整个列表（每个大类的名称和图标在此 attr_name，icon ，还有每个子目录的名称）
//	private List<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();
//
//	// private ListView listView;
//	
//	//整个列表----放好了各个数据         第二层ArrayList：一级目录； 第三层：HashMap，具体的各个条目，包含了标志isChecked
//	private List<ArrayList<HashMap<String, String>>> listDatas = new ArrayList<ArrayList<HashMap<String, String>>>();
//
//	// private ListViewAdapter mAdapter;
//
//	//用于提交  就是已经选中的条目，提交时取出里面的_id拼成字符串
//	private List<HashMap<String, String>> linkList = new ArrayList<HashMap<String, String>>();
//
//	private LinearLayout container, container_item;
//	private HashMap<Integer, Boolean> isCheckMap = new HashMap<Integer, Boolean>();
//
////	private UserInfo userInfo;
//	private RelativeLayout xihuanbiaoqian;
//	//是否设置过喜好
//	private boolean isHasHobby=false;  
//	
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_mine_like);
//		aBar.hide();
//		helper = new YDBHelper(this);
//		appManager = AppManager.getAppManager();
//		findViewById(R.id.tv_commit).setOnClickListener(this);
//		// listView = (ListView) findViewById(R.id.listView);
//		container = (LinearLayout) findViewById(R.id.container);
//		container_item = (LinearLayout) findViewById(R.id.container_item);
//
//		((TextView) findViewById(R.id.tvTitle_base)).setText("我喜欢");
//		findViewById(R.id.img_back).setOnClickListener(this);
//		
//		xihuanbiaoqian =(RelativeLayout) findViewById(R.id.xihuanbiaoqian);
//		xihuanbiaoqian.setBackgroundColor(Color.WHITE);
//
////		userInfo = (UserInfo) getIntent().getSerializableExtra("userinfo");
//		//查询列表
//		queryData();
//	}
//
//	private void queryData() {
//		
//		final UserInfo user=YCache.getCacheUser(context);
//		
//		//通过用户信息查询是否已经设置过喜好
//		if(null == user.getHobby() || user.getHobby().equals("0")){
//			isHasHobby=false;
//		}else{
//			isHasHobby=true;
//		}
//		
//		new SAsyncTask<Void, Void, Void>(this, R.string.wait) {
//
//			@Override
//			protected Void doInBackground(Void... params) {
//				//查询数据库得到喜好的整个列表
//				categoryList = helper
//						.query("select * from tag_info where p_id=0 and is_show=1 order by sequence");
//				//子目录  ，最爱，定价，风格。。。。。
//				ArrayList<HashMap<String, String>> listChild;
//				//遍历整个列表，通过 _id 拿到单个子目录
//				for (int i = 0; i < categoryList.size(); i++) {
//					//通过 _id查询数据库
//					listChild = (ArrayList<HashMap<String, String>>) helper
//							.query("select * from tag_info where p_id = "
//									+ categoryList.get(i).get("_id")
//									+ " order by sequence");
//					
//					//遍历子目录信息 ，将是否已经设置过的标志加进去     0代表设置过，1代表设置过
//					for (HashMap<String, String> map : listChild) {
//						if(isHasHobby){
//							if((","+user.getHobby()+",").contains(","+map.get("_id")+",")){
//								map.put("isChecked", "1");
//								linkList.add(map);
//							}else{
//								map.put("isChecked", "0");
//							}
//						}else{
//							map.put("isChecked", "0");
//						}
//					}
//				//将子目录全部填充， 里面已经加入标志	isChecked： 0 未设置过  1设置过
//				listDatas.add(listChild);
//				}
//				return super.doInBackground(params);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, Void result) {
//				//填充整个列表
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
//		case R.id.img_back:
//			finish();
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
//	
//	private String hobbyList;
//	
//	private void summitMyLike(View v) {
//
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < linkList.size(); i++) {
//			HashMap<String, String> map = linkList.get(i);
//			sb.append(map.get("_id"));
//			if (i != linkList.size() - 1) {
//				sb.append(",");
//			}
//		}
//		hobbyList=sb.toString();
//
//		new SAsyncTask<String, Void, ReturnInfo>(this, null, R.string.wait) {
//
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context,
//					String... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.updateMyLike(context,hobbyList);
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
//				if(null == e){
//				if (result.getStatus().equals("1")) {
//					if (result != null ) {
//						ToastUtil.showShortText(ModifyMineLikeActivity.this, "修改喜好成功");
//						UserInfo user=YCache.getCacheUser(context);
//						user.setHobby(hobbyList);
//						YCache.setCacheUser(context, user);
//						finish();
//					}
//
//					// 弹出注册成功对话框
////					MyDialog dialog = new MyDialog(context, 300, 200,
////							R.layout.dialog_register_successful,
////							R.style.Theme_dialog);
////					dialog.show();// 显示Dialog
//					// 注册成功对话框停留3秒
////					Handler handler = new Handler();
////					handler.postDelayed(new Runnable() {
////						public void run() {
////							// dialog.dismiss();
////							// finish();
////							Intent intent = new Intent(ModifyMineLikeActivity.this,
////									MainMenuActivity.class);
////							startActivity(intent);
////							ModifyMineLikeActivity.this.finish();
////						}
////
////					}, 3000);
////
////					if (null != RegisterFragment.instance) {
////						RegisterFragment.instance.finish();
////					}
////					if (null != LoginFragment.instance) {
////						LoginFragment.instance.finish();
////					}
//				}
//				}
//				super.onPostExecute(context, result, e);
//			}
//
//		}.execute();
//
//	}
//
//	private void addItemView() {
//		container_item.removeAllViews();
//		LayoutInflater inflater = LayoutInflater.from(this);
//		
//		//填充说明部分
//		LinearLayout layoutNotice = (LinearLayout) inflater.inflate(
//				R.layout.my_like_notice, null);
//		container_item.addView(layoutNotice);
//		
//		
//		
//		for (int i = 0; i < listDatas.size(); i++) {
//			//具体每个大类-----包含 大类的名称，大类的图标，以及具体每个小类的内容
//			LinearLayout layout = (LinearLayout) inflater.inflate(
//					R.layout.listview_item, null);
//			
//			//这里大类的名称和图标在categoryList中，而categoryList 和 listDatas的长度是一样的，可以用i直接去取
//			
//			//大类的名称
//			TextView listview_item_textview = (TextView) layout
//					.findViewById(R.id.listview_item_textview);
//			listview_item_textview
//			.setText(categoryList.get(i).get("attr_name"));
//			//每个小类的内容
//			NoScrollGridView listview_item_gridview = (NoScrollGridView) layout
//					.findViewById(R.id.listview_item_gridview);
//			//大类的图标
//			ImageView img_icon = (ImageView) layout.findViewById(R.id.img_icon);
//			SetImageLoader.initImageLoader(context, img_icon,
//					categoryList.get(i).get("icon"),"");
//			
//			//循环添加每个小项的内容
//			ArrayList<HashMap<String, String>> arrayListForEveryGridView = this.listDatas
//					.get(i);
//			FilterGridViewAdapter gridViewAdapter = new FilterGridViewAdapter(this,
//					arrayListForEveryGridView,false,false);
//			gridViewAdapter.setListener(this);
//			listview_item_gridview.setAdapter(gridViewAdapter);
//			
//			
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
//	/*@Override
//	public void onCheckCallback(int positon, boolean isChecked,
//			HashMap<String, String> map) {
//		// TODO Auto-generated method stub
//		if (isChecked) {
//			MyLogYiFu.e("_id", map.get("_id"));
//			linkList.add(map);
//			addSelectView();
//		} else {
//			MyLogYiFu.e("unChecked_id", map.get("_id"));
//			linkList.remove(map);
//			addSelectView();
//		}
//
//	}*/
//
//	private boolean judgeCheck() {
//		/**
//		for (int n = 0; n < listDatas.size(); n++) {
//			isCheckMap.put(n, false);
//		}
//		for (int i = 0; i < linkList.size(); i++) {
//			HashMap<String, String> hashMap = linkList.get(i);
//			for (int j = 0; j < listDatas.size(); j++) {
//				if (listDatas.get(j).contains(hashMap)) {
//					isCheckMap.put(j, true);
//					break;
//				}
//
//			}
//		}
//		MyLogYiFu.e("TAG", "ischeckMap=" + isCheckMap.toString() + "==size"
//				+ isCheckMap.size());
//		for (int k = 0; k < isCheckMap.size(); k++) { 
//			if (!isCheckMap.get(k)) {
//				return false;
//			}
//		}*/
//		//是否每一个类别都勾选过
//		if(linkList.size()==listDatas.size()){
//			return true;
//		}
//		return false;
//	}
//
//	@Override
//	public void onCheckCallback(int positon, boolean isChecked,
//			HashMap<String, String> map, FilterGridViewAdapter mAdapter,boolean isDuoxuan) {
//		// TODO Auto-generated method stub
//		if(isChecked){
//			List<HashMap<String, String>> list = mAdapter.getData();
//			for (int i = 0; i < list.size(); i++) {
////				list.get(i).put("isChecked", "0");
//				onCheckCallback(i, false, list.get(i), mAdapter,isDuoxuan);
//			}
//			list.get(positon).put("isChecked", "1");
//			
//			// listMap.add(list.get(positon));
//			if(!linkList.contains(list.get(positon))){
//				linkList.add(list.get(positon));
//			}
//		}else{
//			List<HashMap<String, String>> list = mAdapter.getData();
//			list.get(positon).put("isChecked", "0");
//			// listMap.remove(list.get(positon));
//			if(linkList.contains(list.get(positon))){
//				linkList.remove(list.get(positon));
//			}
//		}
//		
//		mAdapter.notifyDataSetChanged();
//	}
//
//}
