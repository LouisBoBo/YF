//package com.yssj.spl;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore.Images;
//import android.support.v4.app.FragmentActivity;
//import android.view.Menu;
//import android.view.View;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.plaview.MultiColumnListView;
//import com.yssj.custom.view.LoadingDialog;
//import com.yssj.model.ComModel2;
//import com.yssj.model.ModQingfeng;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.ToastUtil;
//
//public class SampleActivity extends BasicActivity {
//	private MultiColumnListView mAdapterView = null;
//	private ArrayList<String> imageUrls;
//	private ImageGridAdapter adapter;
//	private Context context;
//	private List<HashMap<String, Object>> mListDatas  ;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.sample_act);
//		// mAdapterView = (PLA_AdapterView<Adapter>) findViewById(R.id.list);
//		context = this;
//		mListDatas  = new  ArrayList<HashMap<String,Object>>();
//		mAdapterView = (MultiColumnListView) findViewById(R.id.list);
//		adapter = new ImageGridAdapter(this,mListDatas);
//		mAdapterView.setAdapter(adapter);
//		getIntimateList();
//	}
//	
//
//	/**
//	 * 获取密友圈首页列表 type //参数1 话题广场 2密友圈
//	 */
//	private void getIntimateList() {
//
//		new SAsyncTask<Void, Void, List<HashMap<String, Object>>>((FragmentActivity) context, R.string.wait) {
//
//			@Override
//			protected void onPreExecute() {
//				// TODO Auto-generated method stub
//				super.onPreExecute();
//				LoadingDialog.show((FragmentActivity) context);
//			}
//
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Void... params)
//					throws Exception {
//
//				return ComModel2.getIntimateList(context, "2", 1, "");
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
//				List<HashMap<String, Object>> dataList = result;
//				mListDatas.addAll(dataList);
//				adapter.notifyDataSetChanged();
//
//				super.onPostExecute(context, result, e);
//			}
//
//		}.execute();
//	}
//
//	private void queryMediaImages() {
//		Cursor c = getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
//		if (c != null) {
//			if (c.moveToFirst()) {
//				do {
//					long id = c.getLong(c.getColumnIndex(Images.Media._ID));
//					Uri imageUri = Uri.parse(Images.Media.EXTERNAL_CONTENT_URI + "/" + id);
//					imageUrls.add(imageUri.toString());
//					// imageUrls.add(getRealFilePath(MainActivity.this,
//					// imageUri));
//				} while (c.moveToNext());
//			}
//		}
//		c.close();
//		c = null;
//		adapter.notifyDataSetChanged();
//	}
//
//}
