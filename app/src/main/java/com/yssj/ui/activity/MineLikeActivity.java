package com.yssj.ui.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
import com.yssj.upyun.UpYunException;
import com.yssj.upyun.UpYunUtils;
import com.yssj.upyun.Uploader;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;

import android.content.Context;
import android.graphics.Bitmap;
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
 * 我喜欢
 * 
 * @author Administrator
 * 
 */
public class MineLikeActivity extends BasicActivity implements CheckCallback {

	private AppManager appManager;

	private YDBHelper helper;

	private List<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();

	// private ListView listView;

	private List<ArrayList<HashMap<String, String>>> listDatas = new ArrayList<ArrayList<HashMap<String, String>>>();

	// private ListViewAdapter mAdapter;

	private LinkedList<HashMap<String, String>> linkList = new LinkedList<HashMap<String, String>>();

	private LinearLayout container, container_item, img_back;
	private HashMap<Integer, Boolean> isCheckMap = new HashMap<Integer, Boolean>();

	private UserInfo userInfo;
	private ArrayList<String> list_icon;
	private ArrayList<String> list_text;

	private Boolean isSign; // 点击签到的开店领取跳过来的

	private RelativeLayout xihuanbiaoqian;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_like);
		list_icon = new ArrayList<String>();
		list_text = new ArrayList<String>();
//		aBar.hide();
		helper = new YDBHelper(this);
		appManager = AppManager.getAppManager();
		findViewById(R.id.tv_commit).setOnClickListener(this);
		// listView = (ListView) findViewById(R.id.listView);
		container = (LinearLayout) findViewById(R.id.container);
		container_item = (LinearLayout) findViewById(R.id.container_item);
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		xihuanbiaoqian = (RelativeLayout) findViewById(R.id.xihuanbiaoqian);
		xihuanbiaoqian.setBackgroundColor(Color.WHITE);
		TextView mTitle = ((TextView) findViewById(R.id.tvTitle_base));
		mTitle.setText("我喜欢");
		mTitle.setTextColor(Color.parseColor("#FF398B"));
		findViewById(R.id.imgbtn_left_icon).setOnClickListener(this);
		isSign = getIntent().getBooleanExtra("isSign", false);
		userInfo = (UserInfo) getIntent().getSerializableExtra("userinfo");
		queryData();
	}

	private void queryData() {
		new SAsyncTask<Void, Void, Void>(this, R.string.wait) {

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				categoryList = helper.query("select * from tag_info where p_id=0 and is_show=1 order by sequence");
				ArrayList<HashMap<String, String>> listChild;
				for (int i = 0; i < categoryList.size(); i++) {
					if (i == 0 || i == 1 || i == 2 || i == 3 || i == 6) {
						listChild = (ArrayList<HashMap<String, String>>) helper
								.query("select * from tag_info where p_id = " + categoryList.get(i).get("_id")
										+ " order by sequence");
						for (HashMap<String, String> map : listChild) {
							map.put("isChecked", "0");
						}
						listDatas.add(listChild);
						list_text.add(categoryList.get(i).get("attr_name"));
						list_icon.add(categoryList.get(i).get("icon"));
					}
				}
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result) {
				// TODO Auto-generated method stub
				addItemView();
				super.onPostExecute(context, result);
			}

		}.execute();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			appManager.finishActivity();
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onBackPressed() {
		appManager.finishActivity();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_commit:
			if (judgeCheck()) {
				summitMyLike(v);
			} else {
				ToastUtil.showShortText(this, "每一项至少选一项");
			}
			break;
		case R.id.img_back:
			finish();
			break;
		default:
			break;
		}

	}

	/***
	 * 返回主界面
	 */
	private void summitMyLike(View v) {
		// 开店同时 上传店铺二维码
		try {
			uploadQrImage();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < linkList.size(); i++) {
			HashMap<String, String> map = linkList.get(i);
			sb.append(map.get("_id"));
			if (i != linkList.size() - 1) {
				sb.append(",");
			}
		}

		new SAsyncTask<String, Void, ReturnInfo>(this, null, R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.setMyHobbyKaiDian(context, params[0]);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
				// TODO Auto-generated method stub
				if (null == e) {
					
					SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);

					if (isSign) { // 说明是签到跳过来的 --- 关闭当前页面弹出提示开店完成即可

						if (result.getStatus().equals("1")) {
							
							
							
							SharedPreferencesUtil.saveBooleanData(context, "showkaidiandialog", true);
							
							
							
							context.getSharedPreferences("isFirstGuide", Context.MODE_PRIVATE).edit()
									.putString("showGuide", "true").commit();
							
							finish();
						}
						
						
//						setResult(RESULT_OK);
//						onBackPressed();

					} else {

						if (result.getStatus().equals("1")) {
							SharedPreferencesUtil.saveBooleanData(context, "showkaidiandialog", true);
							context.getSharedPreferences("isFirstGuide", Context.MODE_PRIVATE).edit()
									.putString("showGuide", "true").commit();
							if (MainMenuActivity.instances != null)
								((MainFragment) MainMenuActivity.instances.getSupportFragmentManager()
										.findFragmentByTag("tag")).setIndex(0);
						}
						setResult(RESULT_OK);
						onBackPressed();
					}

					// if (result.getStatus().equals("1")) {
					// context.getSharedPreferences("isFirstGuide",
					// Context.MODE_PRIVATE).edit()
					// .putString("showGuide", "true").commit();
					// if (MainMenuActivity.instances != null)
					// ((MainFragment) MainMenuActivity.instances
					// .getSupportFragmentManager()
					// .findFragmentByTag("tag")).setIndex(0);
					// }
					// setResult(RESULT_OK);
					// onBackPressed();
				}
				super.onPostExecute(context, result, e);
				LogYiFu.e("resultQQ", "onPostExecute");
			}

		}.execute(sb.toString());
	}

	/**
	 * 通过店铺的链接 获取到店铺二维码的的图片
	 * 
	 * @throws Exception
	 */
	private void uploadQrImage() throws Exception {

		new SAsyncTask<Void, Void, HashMap<String, Object>>(this, R.string.wait) {
			@Override
			protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				HashMap<String, Object> result = new HashMap<String, Object>();
				result = ComModel2.getMyShopLink(MineLikeActivity.this);
				return result;
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, Object> shopInfoMap, Exception e) {
				if (null == e) {

					String link = (String) shopInfoMap.get("url");
					String picPath = (String) shopInfoMap.get("pic");
					Bitmap bmQr = QRCreateUtil.createQrImage(link, 160, 160);// 得到二维码图片
					String qrImagePath = saveBitmapFile(bmQr);
					// 上传二维码
					submit(qrImagePath, picPath);
				}
				super.onPostExecute(context, shopInfoMap, e);
			}

		}.execute();
	}

	/**
	 * 获得的二维码bitmap 转换为文件保存 并返回文件路径
	 * 
	 * @param bitmap
	 * @return
	 */
	private String saveBitmapFile(Bitmap bitmap) {
		File file = new File(this.getCacheDir(), "qrimage.jpg");// 将要保存图片的路径
		try {
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
			return file.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过二维码图片路径上传二维码图片
	 * 
	 * @param qrImagePath
	 *            本地图片的保存位置
	 * @param picPath
	 *            文件上传到服务器上面的位置
	 */
	private void submit(String qrImagePath, final String picPath) {

		if (qrImagePath == null) {
			return;
		}

		new SAsyncTask<String, Void, String>(this, R.string.wait) {

			@Override
			protected String doInBackground(FragmentActivity context, String... params) throws Exception {
				String string = null;

				try {
					// 设置服务器上保存文件的目录和文件名，如果服务器上同目录下已经有同名文件会被自动覆盖的。
					String SAVE_KEY = picPath;
					LogYiFu.e("SAVE_KEYSAVE_KEY", SAVE_KEY);

					// 取得base64编码后的policy
					String policy = UpYunUtils.makePolicy(SAVE_KEY, Uploader.EXPIRATION, Uploader.BUCKET);

					// 根据表单api签名密钥对policy进行签名
					// 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
					String signature = UpYunUtils.signature(policy + "&" + Uploader.TEST_API_KEY);

					// 上传文件到对应的bucket中去。
					string = Uploader.upload(policy, signature, Uploader.BUCKET, params[0]);

				} catch (UpYunException e) {
					e.printStackTrace();
				}

				return string;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result);
				if (result != null) {
					LogYiFu.e("SAVE_KEY", result);
				}

			}

		}.execute(qrImagePath);
	}

	private void addItemView() {
		container_item.removeAllViews();
		LayoutInflater inflater = LayoutInflater.from(this);
		LinearLayout layoutNotice = (LinearLayout) inflater.inflate(R.layout.my_like_notice, null);
		container_item.addView(layoutNotice);
		for (int i = 0; i < listDatas.size(); i++) {
			LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.listview_item, null);
			TextView listview_item_textview = (TextView) layout.findViewById(R.id.listview_item_textview);
			NoScrollGridView listview_item_gridview = (NoScrollGridView) layout
					.findViewById(R.id.listview_item_gridview);
			ImageView img_icon = (ImageView) layout.findViewById(R.id.img_icon);
//			SetImageLoader.initImageLoader(context, img_icon, list_icon.get(i), "");
			PicassoUtils.initImage(this, list_icon.get(i), img_icon);
			listview_item_textview.setText(list_text.get(i));
			ArrayList<HashMap<String, String>> arrayListForEveryGridView = this.listDatas.get(i);
			FilterGridViewAdapter gridViewAdapter = new FilterGridViewAdapter(this, arrayListForEveryGridView,false,false);
			gridViewAdapter.setListener(this);
			listview_item_gridview.setAdapter(gridViewAdapter);
			container_item.addView(layout);

		}
	}

	private void addSelectView() {
		container.removeAllViews();
		for (int i = 0; i < linkList.size(); i++) {
			HashMap<String, String> map = linkList.get(i);
			TextView tv = new TextView(this);
			tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tv.setBackgroundResource(R.drawable.check_bg);
			tv.setGravity(Gravity.CENTER);
			tv.setPadding(10, 0, 25, 0);
			tv.setText(map.get("attr_name"));
			container.addView(tv);
		}
	}

	/*
	 * @Override public void onCheckCallback(int positon, boolean isChecked,
	 * HashMap<String, String> map) { // TODO Auto-generated method stub if
	 * (isChecked) { MyLogYiFu.e("_id", map.get("_id")); linkList.add(map);
	 * addSelectView(); } else { MyLogYiFu.e("unChecked_id", map.get("_id"));
	 * linkList.remove(map); addSelectView(); }
	 * 
	 * }
	 */

	private boolean judgeCheck() {
		// for (int n = 0; n < listDatas.size(); n++) {
		// isCheckMap.put(n, false);
		// }
		// for (int i = 0; i < linkList.size(); i++) {
		// HashMap<String, String> hashMap = linkList.get(i);
		// for (int j = 0; j < listDatas.size(); j++) {
		// if (listDatas.get(j).contains(hashMap)) {
		// isCheckMap.put(j, true);
		// break;
		// }
		//
		// }
		// }
		// MyLogYiFu.e("TAG", "ischeckMap=" + isCheckMap.toString() + "==size"
		// + isCheckMap.size());
		// for (int k = 0; k < isCheckMap.size(); k++) {
		// if (!isCheckMap.get(k)) {
		// return false;
		// }
		// }
		if (linkList.size() == listDatas.size()) {
			return true;
		}

		return false;
	}

	@Override
	public void onCheckCallback(int positon, boolean isChecked, HashMap<String, String> map,
			FilterGridViewAdapter mAdapter,boolean isDuoxuan) {
		// TODO Auto-generated method stub
		if (isChecked) {
			List<HashMap<String, String>> list = mAdapter.getData();
			for (int i = 0; i < list.size(); i++) {
				list.get(i).put("isChecked", "0");
			}
			list.get(positon).put("isChecked", "1");
			mAdapter.notifyDataSetChanged();
			// listMap.add(list.get(positon));
			if (!linkList.contains(list.get(positon))) {
				linkList.add(list.get(positon));
			}

		} else {
			List<HashMap<String, String>> list = mAdapter.getData();
			list.get(positon).put("isChecked", "0");
			mAdapter.notifyDataSetChanged();
			// listMap.remove(list.get(positon));
			if (linkList.contains(list.get(positon))) {
				linkList.remove(list.get(positon));
			}
		}
	}

	// private void checkStore() {
	//
	// new SAsyncTask<String, Void, HashMap<String, Object>>(
	// this, R.string.wait) {
	//
	// @Override
	// protected HashMap<String, Object> doInBackground(
	// FragmentActivity context, String... params)
	// throws Exception {
	// // TODO Auto-generated method stub
	// return ComModel2.checkStore(context);
	// }
	//
	// @Override
	// protected boolean isHandleException() {
	// return true;
	// }
	//
	// @Override
	// protected void onPostExecute(FragmentActivity context,
	// HashMap<String, Object> result, Exception e) {
	// // TODO Auto-generated method stub
	// super.onPostExecute(context, result, e);
	// if (null == e) {
	// if(null!=result)
	// context.getSharedPreferences("isFirstGuide",
	// Context.MODE_PRIVATE).edit().putString("showGuide",
	// result.get("is_store") + "").commit();
	// }
	// }
	//
	// }.execute();
	// }

}
