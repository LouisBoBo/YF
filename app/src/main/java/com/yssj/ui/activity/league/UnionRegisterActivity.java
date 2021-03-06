//package com.yssj.ui.activity.league;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.CompoundButton;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.yssj.YConstance;
//import com.yssj.YUrl;
//import com.yssj.activity.R;
//import com.yssj.app.AppManager;
//import com.yssj.app.SAsyncTask;
//import com.yssj.entity.Business;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.setting.SelectAreaActivity;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.upyun.UpYunException;
//import com.yssj.upyun.UpYunUtils;
//import com.yssj.upyun.Uploader;
//import com.yssj.utils.DateUtil;
//import com.yssj.utils.LogYiFu;
//import com.yssj.utils.TakePhotoUtil;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.YCache;
//
//public class UnionRegisterActivity extends BasicActivity implements
//		OnCheckedChangeListener {
//
//	private AppManager appManager;
//
//	private EditText et_salesman_name, et_org_name, et_org_user_name,
//			et_org_user_sex, et_org_user_phone, et_org_user_age,
//			et_org_user_id, et_org_detail_area,
//			et_consume_per_person, et_vip_discount, et_vip_service;
//
//	private TextView tv_org_area;
//	
//	private ImageView img_choose_org_big_pic;
//
//	private RelativeLayout rel_org_big_pic;
//	private LinearLayout lin_container;
//
//	private TextView tv_org_small_pic_notice;
//	private TextView tv_cat_name;
//
//	private Business business = new Business();
//
//	private String provinceId, cityId, areaId, streetId;
//
//	private HashMap<String, Object> mapObj;
//	private Business bzIntent;
//
//	/*public LocationClient mLocationClient = null;
//	public BDLocationListener myListener = new MyLocationListener();*/
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		aBar.hide();
//		setContentView(R.layout.union_register_activity);
//		mapObj = (HashMap<String, Object>) getIntent().getSerializableExtra(
//				"mapObj");
//		appManager = AppManager.getAppManager();
//		if (null != mapObj) {
//			bzIntent = (Business) mapObj.get("business");
//		}
////		initLocation();
//		initView();
//
//	}
//
//	private void initView() {
//		findViewById(R.id.btn_commit).setOnClickListener(this);
//
//		((TextView) findViewById(R.id.tvTitle_base)).setText("??????");
//		findViewById(R.id.imgbtn_left_icon).setOnClickListener(this);
//
//		et_salesman_name = (EditText) findViewById(R.id.et_salesman_name);
//		et_org_name = (EditText) findViewById(R.id.et_org_name);
//		et_org_user_name = (EditText) findViewById(R.id.et_org_user_name);
//		et_org_user_sex = (EditText) findViewById(R.id.et_org_user_sex);
//		et_org_user_sex.setOnClickListener(this);
//		et_org_user_phone = (EditText) findViewById(R.id.et_org_user_phone);
//		et_org_user_age = (EditText) findViewById(R.id.et_org_user_age);
//		et_org_user_id = (EditText) findViewById(R.id.et_org_user_id);
//		tv_org_area = (TextView) findViewById(R.id.tv_org_area);
//		tv_org_area.setOnClickListener(this);
//		et_org_detail_area = (EditText) findViewById(R.id.et_org_detail_area);
//		et_consume_per_person = (EditText) findViewById(R.id.et_consume_per_person);
//		et_vip_discount = (EditText) findViewById(R.id.et_vip_discount);
//		et_vip_discount.setOnClickListener(this);
//		et_vip_service = (EditText) findViewById(R.id.et_vip_service);
//
//		rel_org_big_pic = (RelativeLayout) findViewById(R.id.rel_org_big_pic);// ????????????
//		lin_container = (LinearLayout) findViewById(R.id.lin_container);// ???????????????
//
//		tv_org_small_pic_notice = (TextView) findViewById(R.id.tv_org_small_pic_notice);
//		tv_cat_name = (TextView) findViewById(R.id.tv_cat_name);
//
//		findViewById(R.id.rel_category).setOnClickListener(this);
//		img_choose_org_big_pic = (ImageView) findViewById(R.id.img_choose_org_big_pic);
//		img_choose_org_big_pic.setOnClickListener(this);
//		findViewById(R.id.img_choose_small_pic).setOnClickListener(this);
////		SwitchButton btn_sbt = (SwitchButton) findViewById(R.id.btn_sbt);
////		btn_sbt.setOnCheckedChangeListener(this);
////		btn_sbt.setChecked(true);
//		
//	}
//
//	/*private void initLocation() {
//		mLocationClient = new LocationClient(getApplicationContext()); // ??????LocationClient???
//		mLocationClient.registerLocationListener(myListener); // ??????????????????
//		LocationClientOption option = new LocationClientOption();
//		option.setLocationMode(LocationMode.Hight_Accuracy);// ?????????????????????????????????????????????????????????????????????????????????
//		option.setCoorType("bd09ll");// ???????????????gcj02???????????????????????????????????????
//		int span = 1000;
//		option.setScanSpan(span);// ???????????????0???????????????????????????????????????????????????????????????????????????1000ms???????????????
//		option.setIsNeedAddress(true);// ?????????????????????????????????????????????????????????
//		option.setOpenGps(true);// ???????????????false,??????????????????gps
//		option.setLocationNotify(true);// ???????????????false??????????????????gps???????????????1S1???????????????GPS??????
//		option.setIsNeedLocationDescribe(true);// ???????????????false??????????????????????????????????????????????????????BDLocation.getLocationDescribe?????????????????????????????????????????????????????????
//		option.setIsNeedLocationPoiList(true);// ???????????????false?????????????????????POI??????????????????BDLocation.getPoiList?????????
//		option.setIgnoreKillProcess(true);// ???????????????false?????????SDK???????????????SERVICE?????????????????????????????????????????????stop??????????????????????????????????????????
//		option.SetIgnoreCacheException(false);// ???????????????false?????????????????????CRASH?????????????????????
//		option.setEnableSimulateGps(false);// ???????????????false???????????????????????????gps???????????????????????????
//		mLocationClient.setLocOption(option);
//	}*/
//
////	private boolean checkEmpty() {
////		business.setUser_id(YCache.getCacheUserSafe(this).getUser_id());
////		String salesmanName = et_salesman_name.getText().toString().trim();
////		if (TextUtils.isEmpty(salesmanName)) {
////			ToastUtil.showShortText(this, "??????????????????????????????");
////			return false;
////		}
////		business.setSalesman_name(salesmanName);
////		String orgName = et_org_name.getText().toString().trim();
////		if (TextUtils.isEmpty(orgName)) {
////			ToastUtil.showShortText(this, "?????????????????????");
////			return false;
////		}
////		business.setBus_name(orgName);
////
////		String realName = et_org_user_name.getText().toString().trim();
////		if (TextUtils.isEmpty(realName)) {
////			ToastUtil.showShortText(this, "????????????????????????");
////			return false;
////		}
////		business.setReal_name(realName);
////
////		String phone = et_org_user_phone.getText().toString().trim();
////		if (TextUtils.isEmpty(phone)) {
////			ToastUtil.showShortText(this, "??????????????????????????????");
////			return false;
////		}
////		business.setBus_telephone(phone);
////
////		String age = et_org_user_age.getText().toString().trim();
////		if (TextUtils.isEmpty(age)) {
////			ToastUtil.showShortText(this, "????????????????????????");
////			return false;
////		}
////		business.setAge(Integer.valueOf(age));
////
////		String idCard = et_org_user_id.getText().toString().trim();
////		if (TextUtils.isEmpty(idCard)) {
////			ToastUtil.showShortText(this, "????????????????????????????????????");
////			return false;
////		}
////		business.setIdcard(idCard);
////
////		String consumePerPerson = et_consume_per_person.getText().toString()
////				.trim();
////		if (TextUtils.isEmpty(consumePerPerson)) {
////			ToastUtil.showShortText(this, "?????????????????????");
////			return false;
////		}
////		business.setSvg_price(Double.valueOf(consumePerPerson));
////
////		if(TextUtils.isEmpty(bus_type)){
////			ToastUtil.showShortText(this, "???????????????");
////			return false;
////		}
////		
////		/*
////		 * String vipDiscount = et_vip_discount.getText().toString().trim();
////		 * if(TextUtils.isEmpty(vipDiscount)){ ToastUtil.showShortText(this,
////		 * "?????????VIP??????"); return false; }
////		 */
////		// business.setSvg_price(Double.valueOf(vipDiscount));
////
////		String vipService = et_vip_service.getText().toString().trim();
////		if (TextUtils.isEmpty(vipService)) {
////			ToastUtil.showShortText(this, "?????????VIP??????");
////			return false;
////		}
////		business.setUser_service(vipService);
////
////		String orgDetailArae = et_org_detail_area.getText().toString().trim();
////		if (TextUtils.isEmpty(orgDetailArae)) {
////			ToastUtil.showShortText(this, "?????????????????????");
////			return false;
////		}
////		business.setAddr(orgDetailArae);
////
////		if(null == business.getDef_pic()){
////			ToastUtil.showShortText(this, "??????????????????????????????");
////			return false;
////		}
////		
////		if(list.size()<3 || list.size()>15){
////			ToastUtil.showShortText(this, "?????????????????????????????????3??????????????????15???");
////			return false;
////		}
////		
////		return true;
////	}
//
////	private void commitData(View v) {
////		StringBuffer sb = new StringBuffer();
////		for (int i = 0; i < list.size(); i++) {
////			sb.append(list.get(i));
////			if (i != list.size() - 1) {
////				sb.append(",");
////			}
////		}
////		business.setBus_pic(sb.toString());
////
////		if (!checkEmpty()) {
////			return;
////		}
////
////		new SAsyncTask<Void, Void, ReturnInfo>(this, v, R.string.wait) {
////
////			@Override
////			protected boolean isHandleException() {
////				// TODO Auto-generated method stub
////				return true;
////			}
////
////			@Override
////			protected ReturnInfo doInBackground(FragmentActivity context,
////					Void... params) throws Exception {
////				// TODO Auto-generated method stub
////				if (null == bzIntent) {
////					return ComModel2.bizInfoCommit(context, business,
////							YUrl.ADD_BUSINESS);
////				} else {
////					business.setBus_code(bzIntent.getBus_code());
////					return ComModel2.bizInfoCommit(context, business,
////							YUrl.UPDATE_BUSINESS);
////				}
////			}
////
////			@Override
////			protected void onPostExecute(FragmentActivity context,
////					ReturnInfo result, Exception e) {
////				// TODO Auto-generated method stub
////				if (e == null) {
////					ToastUtil.showShortText(context, result.getMessage());
////					if(null != LeagueBusinessHomeActivity.instance)
////						LeagueBusinessHomeActivity.instance.finish();
////					context.finish();
////				}
////
////				super.onPostExecute(context, result, e);
////			}
////
////		}.execute();
////	}
//
//	private boolean isBigPic = false;
//
//	private List<String> list = new ArrayList<String>();
//
//	@Override
//	public void onClick(View v) {
//		super.onClick(v);
//		Intent intent = null;
//		switch (v.getId()) {
//		case R.id.et_org_user_sex:
////			chooseSex();
//
//			break;
//		case R.id.imgbtn_left_icon:
//			finish();
//			break;
//		case R.id.rel_category:// ????????????
//			intent = new Intent(getApplication(), UnionCategoryActivity.class);
//			startActivityForResult(intent, 10010);
//			break;
//		case R.id.img_choose_org_big_pic:// ????????????
//			isBigPic = true;
//			TakePhotoUtil.doPickPhotoAction(this);
//
//			break;
//		case R.id.img_choose_small_pic:// ????????????
//			isBigPic = false;
//			TakePhotoUtil.doPickPhotoAction(this);
//			break;
//		case R.id.btn_commit:// ??????
////			commitData(v);
//			break;
//		case R.id.tv_org_area:// ????????????
//			intent = new Intent(this, SelectAreaActivity.class);
//			startActivityForResult(intent, 10080);
//			provinceId = cityId = areaId = streetId = null;
//			break;
//		case R.id.et_vip_discount:// VIP ????????????
//			intent = new Intent(this, VipDiscountActivity.class);
//			startActivityForResult(intent, 10086);
//
//			break;
//		default:
//			break;
//		}
//	}
//
//	private AlertDialog dialog;
//
////	private void chooseSex() {
////		AlertDialog.Builder builder = new Builder(this);
////		// ???????????????????????????
////		View view = View.inflate(this, R.layout.choose_sex_dialog, null);
////		view.findViewById(R.id.lin_male).setOnClickListener(
////				new OnClickListener() {
////
////					@Override
////					public void onClick(View arg0) {
////						// TODO Auto-generated method stub
////						et_org_user_sex.setText("???");
////						business.setSex('0');
////						dialog.dismiss();
////					}
////				});
////
////		view.findViewById(R.id.lin_famale).setOnClickListener(
////				new OnClickListener() {
////
////					@Override
////					public void onClick(View arg0) {
////						// TODO Auto-generated method stub
////						et_org_user_sex.setText("???");
////						business.setSex('1');
////						dialog.dismiss();
////					}
////				});
////
////		dialog = builder.create();
////		dialog.setView(view, 0, 0, 0, 0);
////		dialog.show();
////	}
//
//	private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
//
//	private String bus_type;
//	
//	@SuppressLint("NewApi")
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (resultCode == RESULT_OK) {
//
//			if (requestCode == TakePhotoUtil.RESULT_LOAD_IMAGE) {
//
//				final Uri originalUri = data.getData(); // ???????????????uri
//				String path;
//				if (originalUri.getScheme().equals("content")) {
//					path = TakePhotoUtil.getRealPathFromURI(originalUri, this);
//				} else {
//					path = originalUri.getPath();
//				}
//
//				BitmapFactory.Options options = new BitmapFactory.Options();
//				options.inSampleSize = 2;
//				Bitmap bm = BitmapFactory.decodeFile(path, options);
//				if (isBigPic) {
//					Drawable d = new BitmapDrawable(bm);
//					rel_org_big_pic.setBackground(d);
//					img_choose_org_big_pic.setVisibility(View.INVISIBLE);
//				} else {
//					bitmaps.add(bm);
//					addImageView(bitmaps);
//				}
//
//				submitPic(path);
//			} else if (requestCode == TakePhotoUtil.RESULT_LOAD_PICTURE) {
//				String path = YConstance.saveUploadPicPath
//						+ TakePhotoUtil.fileImageName;
//
//				BitmapFactory.Options options = new BitmapFactory.Options();
//				options.inSampleSize = 2;
//				Bitmap bm = BitmapFactory.decodeFile(path, options);
//				if (isBigPic) {
//					Drawable d = new BitmapDrawable(bm);
//					rel_org_big_pic.setBackground(d);
//				} else {
//					bitmaps.add(bm);
//					addImageView(bitmaps);
//				}
//				submitPic(path);
//			} else if (requestCode == 10080) {
//
//				StringBuffer sb = new StringBuffer();
//				List<HashMap<String, String>> listData = (List<HashMap<String, String>>) data
//						.getSerializableExtra("list");
//				for (int i = 0; i < listData.size(); i++) {
//					HashMap<String, String> map = listData.get(i);
//					sb.append(map.get("AreaName"));
//					String id = map.get("id");
//					switch (i) {
//					case 0:
//						provinceId = id;
//						business.setProvince(Integer.valueOf(provinceId));
//						break;
//					case 1:
//						cityId = id;
//						business.setCity(Integer.valueOf(cityId));
//						break;
//					case 2:
//						areaId = id;
//						business.setArea(Integer.valueOf(areaId));
//						break;
//					case 3:
//						streetId = id;
//						business.setStreet(Integer.valueOf(streetId));
//						break;
//					default:
//						break;
//					}
//				}
//				tv_org_area.setText(sb.toString());
//			} else if (requestCode == 10010) {
//				HashMap<String, String> mapChecked = (HashMap<String, String>) data
//						.getSerializableExtra("mapChecked");
//				business.setBus_type_two(Integer.valueOf(mapChecked.get("_id")));
//				business.setBus_type(Integer.valueOf(mapChecked.get("p_id")));
//				bus_type = mapChecked.get("_id");
//				tv_cat_name.setText(mapChecked.get("attr_name"));
//			} else if (requestCode == 10086) {
//				HashMap<String, String> map = (HashMap<String, String>) data
//						.getSerializableExtra("map");
//				StringBuffer sb = new StringBuffer();
//				sb.append(map.get("vipDiscount")).append("   ").append(
//						map.get("vipDiscountExplain"));
//				business.setUser_service(sb.toString());
//				business.setVip_dis(map.get("vipDiscount"));
//				business.setDis_note(map.get("vipDiscountExplain"));
//				
//				et_vip_discount.setText("????????????    "+sb.toString());
//				LogYiFu.e("sssss", sb.toString());
//			}
//		}
//	}
//
//	private void addImageView(List<Bitmap> bitmaps) {
//		tv_org_small_pic_notice.setText("?????????" + bitmaps.size() + "??????????????????15???");
//		lin_container.removeAllViews();
//		for (int i = bitmaps.size() - 1; i >= 0; i--) {
//			ImageView img = new ImageView(this);
//			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
//					LayoutParams.MATCH_PARENT);
//			img.setImageBitmap(bitmaps.get(i));
//			lin_container.addView(img);
//		}
//	}
//
//	private void submitPic(String path) {
//
//		new SAsyncTask<String, Void, String>(this, R.string.wait) {
//
//			@Override
//			protected String doInBackground(FragmentActivity context,
//					String... params) throws Exception {
//				String string = null;
//
//				try {
//					String path;
//					if (isBigPic) {
//						path = "business/"
//								+ DateUtil.FormatMill(System
//										.currentTimeMillis()) + "/default";
//					} else {
//						path = "business/"
//								+ DateUtil.FormatMill(System
//										.currentTimeMillis()) + "/pic";
//					}
//					LogYiFu.e("path", path);
//					// ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
//					String SAVE_KEY = File.separator + path + File.separator
//							+ System.currentTimeMillis() + ".jpg";
//
//					// ??????base64????????????policy
//					String policy = UpYunUtils.makePolicy(SAVE_KEY,
//							Uploader.EXPIRATION, Uploader.BUCKET);
//
//					// ????????????api???????????????policy????????????
//					// ???????????????????????????????????????????????????????????????????????????http?????????????????????????????????
//					String signature = UpYunUtils.signature(policy + "&"
//							+ Uploader.TEST_API_KEY);
//
//					// ????????????????????????bucket?????????
//					string = Uploader.upload(policy, signature,
//							Uploader.BUCKET, params[0]);
//
//				} catch (UpYunException e) {
//					e.printStackTrace();
//				}
//
//				return string;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, String result) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result);
//				if (result != null) {
//					if (isBigPic) {
//						business.setDef_pic(result);
//					} else {
//						list.add(result);
//					}
//
//				}
//			}
//
//		}.execute(path);
//	}
//
//	/*public class MyLocationListener implements BDLocationListener {
//
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			if (null != location) {
////				et_org_area.setClickable(false);
//				tv_org_area.setText(location.getProvince() + location.getCity()
//						+ location.getDistrict() + location.getStreet());// ???????????????
//				business.setLat(location.getLatitude());
//				business.setLng(location.getLongitude());
//				business.setIntact_addr(location.getProvince() + location.getCity() + location.getDistrict()+ location.getStreet());
//			}
//		}
//	}*/
//
//	@Override
//	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//		// TODO Auto-generated method stub
//		/*if (arg0.getId() == R.id.btn_sbt) {
//			if (arg1) {
//				tv_org_area.setClickable(false);
//				mLocationClient.start();
//			} else {
//				tv_org_area.setClickable(true);
//				mLocationClient.stop();
//			}
//		}*/
//	}
//
//}
