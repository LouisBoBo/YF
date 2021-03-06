/*package com.yssj.custom.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.yssj.Constants;
import com.yssj.YConstance;
import com.yssj.YUrl;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.shopdetails.NoShareActivity;
import com.yssj.ui.dialog.SignFinishDialog;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongjiShareCount;

public class SignPopupwindow extends PopupWindow implements OnClickListener {
	private UMSocialService mController = UMServiceFactory
			.getUMSocialService(Constants.DESCRIPTOR_SHARE);
	private Activity mActivity;

	private double feedBack;

	private Boolean isQQShare;
	
	private int now_type_id;
	private int now_type_id_value;
	private int next_type_id;
	private int next_type_id_value;
	

	private Intent qqShareIntent = ShareUtil
			.shareMultiplePictureToQZone(ShareUtil.getImage());

	private Intent weixinShareIntent = ShareUtil.shareToWechat(ShareUtil
			.getImage());

	private Intent wXinShareIntent = ShareUtil
			.shareMultiplePictureToTimeLine(ShareUtil.getImage());

	private TextView tv_title;

	private String link9="";

	private int type;

	private int isNoFeed = 0;

	private int ids;

	public SignPopupwindow(Activity activity, int now_type_id,int now_type_id_value , int next_type_id,int next_type_id_value) {//
		super(activity);

		this.now_type_id = now_type_id;
		this.now_type_id_value = now_type_id_value;
		this.next_type_id = next_type_id;
		this.next_type_id_value = next_type_id_value ;
		
		
		this.mActivity = activity;

		initView(activity);
	}

	@SuppressWarnings("deprecation")
	private void initView(Context context) {
		View rootView = LayoutInflater.from(context).inflate(
				R.layout.share_popupwindow_sign, null);
		rootView.setBackgroundColor(Color.WHITE);
		rootView.findViewById(R.id.ll_qq).setOnClickListener(this);

		rootView.findViewById(R.id.ll_wxin_circle).setOnClickListener(this);
		// rootView.findViewById(R.id.btn_cancle).setOnClickListener(this);
		// tv_title = (TextView) rootView.findViewById(R.id.tv_title);

		// TextView tv_kickback = (TextView)
		// rootView.findViewById(R.id.kick_back);
		// if (feedBack == 0) {
		// tv_kickback.setVisibility(View.GONE);
		// if (isNoFeed == 0)
		// tv_title.setText("??????????????????????????????????????????????????????");
		// else
		// tv_title.setText("?????????????????????~??????????????????????????????");
		// } else {
		// tv_kickback.setVisibility(View.VISIBLE);
		// tv_title.setText("?????????????????????~??????????????????????????????");
		// }
		// tv_kickback.setText(context.getString(R.string.kick_back, feedBack +
		// ""));
		//

		// tv_title.setText("???????????????????????????????????????????????????????????????????????????????????????10%??????????????????");

		qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil
				.getImage());
		setContentView(rootView);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setAnimationStyle(R.style.mypopwindow_anim_style);
		setFocusable(true);
		setTouchable(true);
		setBackgroundDrawable(new ColorDrawable(R.color.white));
		setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
			}
		});

		qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil
				.getImage());
	}

	@Override
	public void onClick(View v) {

		// share();

		int id = v.getId();
		switch (id) {
		case R.id.iv_qq_share:
			break;
		case R.id.iv_wxin_share:
			break;
		case R.id.ll_qq: // ?????????QQ??????

			isQQShare = true;
			

//			if (ShareUtil.intentIsAvailable(mActivity, qqShareIntent)) {
//
//				ToastUtil.showShortText(mActivity, "???????????????????????????~");
//				task = new TimerTask() {
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						Message message = new Message();
//						message.what = 1;
//						handler.sendMessage(message);
//					}
//				};
//
//				timer.schedule(task, 5000);
//
//				// ????????????
//				ShareUtil.addQQQZonePlatform(mActivity);
//				CreatePic(ids);
//			} else {
//				ToastUtil.showShortText(mActivity, "??????????????????QQ???QQ?????????~");
//			}
//			
//			
			
			
			ToastUtil.showShortText(mActivity, "???????????????????????????~");
			
			
			
			
			if ( now_type_id == 5){
				
				// ????????????
				ShareUtil.addQQQZonePlatform(mActivity);
				CreatePic(ids);
				
			}else{
				task = new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
					}
				};

				timer.schedule(task, 8000);

				// ????????????
				ShareUtil.addQQQZonePlatform(mActivity);
				CreatePic(ids);
				
			}
			
			
			
			
			
		
			
			
			
			
			
			
			
			
			
			
			dismiss();

			break;
		case R.id.ll_wxin_circle: // ??????????????????

			isQQShare = false;

			if (ShareUtil.intentIsAvailable(mActivity, wXinShareIntent)) {
				ToastUtil.showShortText(mActivity, "???????????????????????????~");
				task = new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
					}
				};

				timer.schedule(task, 8000);

				// ????????????
				ShareUtil.addWXPlatform(mActivity);
				CreatePic(ids);
			} else {
				ToastUtil.showShortText(mActivity, "???????????????????????????~");
			}

			dismiss();

		default:
			break;
		}
	}

	*//** ????????????????????? *//*

	private void CreatePic(int id) {
		
		shareCommon(id);

//		// if (table == 1) {
//		switch (type) {
//		case 0: // ??????
//
//			break;
//		case 1: // ??????0????????????
//
//			shareZero(id);
//
//			break;
//		case 2: // ??????????????????
//			shareCommon(id);
//			break;
//		case 3: // ???????????????
//			String HDUrl = YUrl.imgurl + SignFragment.value;
//			// createSharePicHD(HDUrl, id); //?????????????????????
//
//			break;
//		}

	}

	// private void createSharePicHD(final String picPath,
	//
	// final int id) {
	// new SAsyncTask<Void, Void, Void>((FragmentActivity) mActivity,
	// R.string.wait) {
	//
	// @Override
	// protected boolean isHandleException() {
	// // TODO Auto-generated method stub
	// return true;
	// }
	//
	// @Override
	// protected Void doInBackground(FragmentActivity context, Void... params)
	// throws Exception {
	// // TODO Auto-generated method stub
	// String pic = picPath;
	// Bitmap bmBg = downloadPic(pic);
	//
	// QRCreateUtil.saveBitmap(bmBg, YConstance.savePicPath,
	// MD5Tools.md5(String.valueOf(9)) + ".jpg");// ?????????
	// return super.doInBackground(context, params);
	// }
	//
	// @Override
	// protected void onPostExecute(FragmentActivity context, Void result,
	// Exception e) {
	// // TODO Auto-generated method stub
	// super.onPostExecute(context, result, e);
	// if (null == e) {
	// File file = new File(YConstance.savePicPath,
	// MD5Tools.md5(String.valueOf(9)) + ".jpg");
	// share(file);
	// }
	// }
	//
	// }.execute();
	// }

	private void shareZero(final int id) {
		new SAsyncTask<String, Void, HashMap<String, String>>(
				(FragmentActivity) mActivity, R.string.wait) {

			@Override
			protected HashMap<String, String> doInBackground(
					FragmentActivity context, String... params)
					throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getSignShareZero(context);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, String> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				if (null == e) {
					if (result.get("status").equals("1")) {

						String pic = result.get("four_pic");
						createSharePic((String) result.get("link"), pic,
								(String) result.get("price"),
								(String) result.get("shop_code"), null, id);

					} else if (result.get("status").equals("1050")) {// ??????
						Intent intent = new Intent(context,
								NoShareActivity.class);
						intent.putExtra("isNomal", true);
						context.startActivity(intent); // ?????????????????????
					}
				}
			}

		}.execute();
	}

	private void createSharePic(final String link, final String picPath,
			final String price, final String shop_code, final View v,
			final int id) {
		new SAsyncTask<Void, Void, Void>((FragmentActivity) mActivity,
				R.string.wait) {

			@Override
			protected boolean isHandleException() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected Void doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				// TODO Auto-generated method stub
				Bitmap bmQr = QRCreateUtil.createQrImage(link, 160, 160);// ?????????????????????
				String pic = shop_code.substring(1, 4) + "/" + shop_code + "/"
						+ picPath;
				Bitmap bmBg = downloadPic(pic);

				bmNew = QRCreateUtil.drawNewBitmap1(context, bmBg, bmQr, price,
						"1");
				LogYiFu.e("WD", bmNew.getWidth() + "");
				LogYiFu.e("HG", bmNew.getHeight() + "");
				QRCreateUtil.saveBitmap(bmNew, YConstance.savePicPath,
						MD5Tools.md5(String.valueOf(9)) + ".jpg");// ?????????????????????
				return super.doInBackground(context, params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result,
					Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				if (null == e) {
					file = new File(YConstance.savePicPath, MD5Tools.md5(String
							.valueOf(9)) + ".jpg");
					// share(file); ????????????????????????????????????

					share(file);

				}
			}

		}.execute();
	}

	private Bitmap bmNew;
	private File file;

	private Bitmap downloadPic(String picPath) {
		try {
			URL url = new URL(YUrl.imgurl + picPath);
			// ????????????
			URLConnection con = url.openConnection();
			// ?????????????????????
			int contentLength = con.getContentLength();
//			System.out.println("?????? :" + contentLength);
			// ?????????
			InputStream is = con.getInputStream();
			// 1K???????????????
			byte[] bs = new byte[8192];
			// ????????????????????????
			int len;
			BitmapDrawable bmpDraw = new BitmapDrawable(is);

			// ???????????????????????????
			is.close();
			return bmpDraw.getBitmap();
		} catch (Exception e) {
			LogYiFu.e("TAG", "????????????");

			e.printStackTrace();
			return null;
		}

	}

	private void shareCommon(final int id) {

	

		// ?????????????????????????????????????????????????????????

		if (now_type_id == 5) {// ???????????????????????????????????????

			new SAsyncTask<String, Void, HashMap<String, String>>(
					(FragmentActivity) mActivity, R.string.wait) {

				@Override
				protected HashMap<String, String> doInBackground(
						FragmentActivity context, String... params)
						throws Exception {
					// TODO Auto-generated method stub
					return ComModel2.getShareShopLinkHobby(context, "true");
				}

				@Override
				protected boolean isHandleException() {
					return true;
				}

				@Override
				protected void onPostExecute(FragmentActivity context,
						HashMap<String, String> result, Exception e) {
					// TODO Auto-generated method stub
					super.onPostExecute(context, result, e);
					if (null == e) {
						if (result.get("status").equals("1")) {

							String[] picList = result.get("four_pic")
									.split(",");
							
							
							String shop_code = result.get("shop_code");
							
							TongjiShareCount.tongjifenxiangCount();
							TongjiShareCount.tongjifenxiangwho(shop_code);

							 
							createSharePic((String) result.get("link"),
									picList.length > 2 ? picList[2] : "",
									(String) result.get("shop_se_price"),
									(String) result.get("shop_code"), null, id);

						} else if (result.get("status").equals("1050")) {// ??????
							Intent intent = new Intent(context,
									NoShareActivity.class);
							intent.putExtra("isNomal", true);
							context.startActivity(intent); // ?????????????????????
						}
					}
				}

			}.execute();

		} else {

			// // ???????????????9??????

			new SAsyncTask<String, Void, HashMap<String, String>>(
					(FragmentActivity) mActivity, R.string.wait) {

				@Override
				protected HashMap<String, String> doInBackground(
						FragmentActivity context, String... params)
						throws Exception {
					// TODO Auto-generated method stub
					return ComModel2.getShareShopLinkHobby(context, "true");
				}

				@Override
				protected boolean isHandleException() {
					return true;
				}

				@Override
				protected void onPostExecute(FragmentActivity context,
						HashMap<String, String> result, Exception e) {
					// TODO Auto-generated method stub
					super.onPostExecute(context, result, e);
					if (null == e) {
						if (result.get("status").equals("1")) {

							String[] picList = result.get("shop_pic")
									.split(",");
							link9 = result.get("link");
							String code = result.get("shop_code");
							
							String shop_code = result.get("shop_code");
							
//							
							TongjiShareCount.tongjifenxiangCount();
							TongjiShareCount.tongjifenxiangwho(shop_code);

							// download(null, picList, code, result, shop,
							// link,four_pic);
							download9(null, picList, code, result, link9,
									result.get("four_pic"));

							//
							// createSharePic((String) result.get("link"),
							// picList.length > 2 ? picList[2] : "",
							// (String) result.get("shop_se_price"), (String)
							// result.get("shop_code"), null, id);

						} else if (result.get("status").equals("1050")) {// ??????
							Intent intent = new Intent(context,
									NoShareActivity.class);
							intent.putExtra("isNomal", true);
							context.startActivity(intent); // ?????????????????????
						}

						*//**
						 * if (result.get("status").equals("1")) { LogYiFu.e("pic",
						 * result.get("shop_pic")); String[] picList =
						 * result.get("shop_pic").split(","); String link =
						 * result.get("link"); download(null, picList,
						 * sc.getShop_code(), result, sc, link); } else if
						 * (result.get("status").equals("1050")) {// ?????? Intent
						 * intent = new Intent(context, NoShareActivity.class);
						 * intent.putExtra("isNomal", true);
						 * context.startActivity(intent); // ????????????????????? }
						 * 
						 *//*
					}
				}

			}.execute();
			//
		}

	}

	// download9(null, picList, code, result, link,result.get("four_pic"));

	*//** ????????????????????? *//*
	private void download9(View v, final String[] picList,
			final String shop_code, final HashMap<String, String> mapInfos,
			final String link, final String four_pic) {
		final List<String> pics = new ArrayList<String>();

		new SAsyncTask<Void, Void, Void>((FragmentActivity) mActivity, v,
				R.string.wait) {

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				File fileDirec = new File(YConstance.savePicPath);
				if (!fileDirec.exists()) {
					fileDirec.mkdir();
				}
				File[] listFiles = new File(YConstance.savePicPath).listFiles();
				if (listFiles.length != 0) {
					LogYiFu.e("TAG", "??????????????? ?????????????????????");
					for (File file : listFiles) {
						file.delete();
					}
				}
				// Log.i("TAG", "piclist=" + picList.length);
				// List<String> pics = new ArrayList<String>();
				for (int j = 0; j < picList.length; j++) {
					if (!picList[j].contains("reveal_")
							&& !picList[j].contains("detail_")
							&& !picList[j].contains("real_")) {
						pics.add(picList[j]);
					}
				}
				int j = pics.size() + 1;
				if (pics.size() > 8) {
					j = 9;
				}

				// deletePIC(YConstance.savePicPath ,false);
				// deletePIC(YConstance.saveUploadPicPath ,false);

				for (int i = 0; i < j; i++) {
					if (i == j - 1) {
						
						 * ComModel2.saveQRCode(PaymentSuccessActivity.this,
						 * shop_code);
						 
						// if (isMeal ||
						// "SignShopDetail".equals(signShopDetail)) {
						// Bitmap bm = QRCreateUtil.createZeroImage(link, 500,
						// 700, mapInfos.get("shop_se_price"),
						// ShopDetailsActivity.this);// ?????????????????????
						// QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
						// MD5Tools.md5(String.valueOf(9)) + ".jpg");// ?????????????????????
						// } else {
						Bitmap bm = QRCreateUtil.createImage(
								mapInfos.get("QrLink"), 500, 700,
								mapInfos.get("shop_se_price"), mActivity);// ?????????????????????
						QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
								MD5Tools.md5(String.valueOf(9)) + ".jpg");// ?????????????????????
						// }
						// downloadPic(mapInfos.get("qr_pic"), 9);
						break;
					}
					downloadPic(shop_code.substring(1, 4) + "/" + shop_code
							+ "/" + pics.get(i) + "!450", i);
					bmBg = downloadPic(shop_code.substring(1, 4) + "/"
							+ shop_code + "/" + four_pic.split(",")[2] + "!450");
				}
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result) {
				// TODO Auto-generated method stub
				// showShareDialog();
				// if (instance == null) {
				// return;
				// }
				if (null != context
						&& null != context.getWindow().getDecorView()
						&& !context.isFinishing()) {
					// LogYiFu.e("TAG", "????????????=" + shop.getShop_name() + ",????????????="
					// + result);
					// ShareUtil.configPlatforms(context);

					// UMImage umImage = new UMImage(context, bmBg);
					// ShareUtil.setShareContent(context, umImage,
					// "????????????????????????????????????????????????????????????????????????~~", link);
					//

					// WeiXinShareContent wei = new WeiXinShareContent();
					// wei.setTitle("??????????????????????????????????????????");
					// wei.setShareMedia(umImage);
					// wei.setShareContent("????????????????????????????????????????????????????????????????????????~~");
					// wei.setTargetUrl(link);
					// mController.setShareMedia(wei);
					//
					if (isQQShare == true) {
						onceShare2(qqShareIntent, "qq??????", null);
					} else {
						onceShare3(wXinShareIntent, "??????");
					}

					// ShareUtil.share(ShopDetailsActivity.this);
					// myPopupwindow.setLink(link);
					// myPopupwindow.setUmImage(umImage);
					// myPopupwindow = new MyPopupwindow(context,
					// shop.getKickback(), umImage, link);
					// if (ShopDetailsActivity.instance != null) {
					// myPopupwindow.showAtLocation(context.getWindow()
					// .getDecorView(), Gravity.BOTTOM, 0, 0);
					// }
					// shareTo();
					super.onPostExecute(context, result);
				}

			}

		}.execute();

	}


	private Bitmap bmBg;

	private void onceShare(Intent intent, String perform) {
		if (ShareUtil.intentIsAvailable(mActivity, intent)) {
			mActivity.startActivity(intent);
		} else {
			// performShare(SHARE_MEDIA.QZONE, qqShareIntent);
		}
	}

	private void share(File file) {

		if (isQQShare) {
			onceShare2(qqShareIntent, "qq??????", file);
		} else {
			ShareUtil.configPlatforms(mActivity);
			UMImage umImage = new UMImage(mActivity, file);
			ShareUtil.shareShop(mActivity, umImage);

			mController.postShare(mActivity, SHARE_MEDIA.WEIXIN_CIRCLE,
					new SnsPostListener() {

						@Override
						public void onStart() {

						}

						@Override
						public void onComplete(SHARE_MEDIA platform, int eCode,
								SocializeEntity entity) {
							// String showText = platform.toString();
							if (eCode == StatusCode.ST_CODE_SUCCESSED) {// ????????????
//								MobclickAgent.onEvent(mActivity,
//										"sign_sharesuccess");
								sign();

							} else {

							}
						}
					});
		}

	}


	private void downloadPic(String picPath, int i) {
		try {
			URL url = new URL(YUrl.imgurl + picPath);
			// ????????????
			URLConnection con = url.openConnection();
			// ?????????????????????
			int contentLength = con.getContentLength();
			// ?????????
			InputStream is = con.getInputStream();
			// 1K???????????????
			byte[] bs = new byte[8192];
			// ????????????????????????
			int len;
			// ?????????????????? /sdcard/yssj/
			File file = new File(YConstance.savePicPath, MD5Tools.md5(String
					.valueOf(i)) + ".jpg");
			if (file.exists()) {
				file.delete();
			}
			LogYiFu.e("TAG", "??????????????????????????????????????????");
			OutputStream os = new FileOutputStream(file);
			// ????????????
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			Log.i("TAG", "?????????????????????file=" + file.toString());
			// ???????????????????????????
			os.close();
			is.close();
		} catch (Exception e) {
			LogYiFu.e("TAG", "????????????");
			e.printStackTrace();
		}
	}

	private void onceShare3(Intent intent, String perform) {
		if (ShareUtil.intentIsAvailable(mActivity, intent)) {
			mActivity.startActivity(intent);
			// performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
			//
		}
	}

	private void onceShare2(Intent intent, String perform, File file) {
		if (ShareUtil.intentIsAvailable(mActivity, intent)) {

			if (now_type_id == 5) {
				performShare(SHARE_MEDIA.QZONE, qqShareIntent);

			} else {
				mActivity.startActivity(intent);
			}

		} else {
			performShare(SHARE_MEDIA.QZONE, qqShareIntent);
		}
	}

	public void performShare(SHARE_MEDIA platform, final Intent intent) {
		UMImage umImage;

		if (now_type_id == 5) { // ????????????????????????
			umImage = new UMImage(mActivity, bmNew);
		} else {
			umImage = new UMImage(mActivity, bmBg);// ??????
		}
		ShareUtil.setShareContent(mActivity, umImage,
				"????????????????????????????????????????????????????????????????????????~~", link9);

		mController.postShare(mActivity, platform, new SnsPostListener() {

			@Override
			public void onStart() {
				LogYiFu.e("showText", "asdsafdsf");
				// chooseDialog();
			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode,
					SocializeEntity entity) {
				String showText = platform.toString();
				LogYiFu.e("showText", showText);

				if (eCode == StatusCode.ST_CODE_SUCCESSED) {

					 sign();
					 ToastUtil.showShortText(mActivity, "QQ??????????????????");

				} else {

				}

			}
		});
	}

	private void sign() {
		// ??????
		new SAsyncTask<Void, Void, HashMap<String, Object>>(
				(FragmentActivity) mActivity, 0) {

			@Override
			protected HashMap<String, Object> doInBackground(
					FragmentActivity context, Void... params) throws Exception {
				
				if (now_type_id  == 8){    //???????????????????????????????????????????????????????????????  
					
					
					LogYiFu.e("ids", ids+"");
					
					return ComModel2.getSignIn(mActivity ,true);
				}else{
					//??????????????????????????????????????????
					
					LogYiFu.e("ids", ids+"");
					return ComModel2.getSignIn(mActivity ,false);
				}
			
				
			}

			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, Object> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null && result != null) {
					// ???????????????????????????
					// chooseDialog();
					SharedPreferencesUtil.saveBooleanData(context, "isFirstFenXiang", true);
					if (now_type_id  == 8){   
						String t_time = (String) result.get("t_time");
						
						SharedPreferencesUtil.saveStringData(context, Pref.TWOFOLDNESS, "2"); // ??????????????????
						SharedPreferencesUtil.saveStringData(context, Pref.END_DATE,t_time+""); // ????????????????????????
						SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, true);   //?????????????????????????????????
						SharedPreferencesUtil.saveStringData(context, Pref.IS_OPEN, "1");//???????????????   0????????????  1????????????
						
						
						
						
					}
					
					new SignFinishDialog(mActivity, now_type_id,  now_type_id_value,  next_type_id,next_type_id_value, false, "",false).show();
					

				} else {
					ToastUtil.showLongText(mActivity, "????????????");
				}

				//
				// if(result.get("changeTable") == true +""){
				// SignFragment.queryLoginSignList();
				// SignFragment.querySignListYet();
				// //
				// }
			}

		}.execute();
	}

	private final Timer timer = new Timer();
	private TimerTask task;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			if (now_type_id  == 5){ //?????????????????????????????????
		
				return;
			}else{
				sign();// ??????
				
				
				
//				new SignFinishDialog(mActivity, now_type_id,  now_type_id_value,  next_type_id,next_type_id_value, false, "").show();
				
				
				
				
				
				
				
				
				
				
				
				
				
			}
//			sign();// ??????
//			chooseDialog(); // ?????????????????????
			
//			if (ids == 23){   //?????????????????????
//				new DialogFanbei2(mActivity, R.style.DialogStyle1).show();
//			}else{
				//??????????????????????????????????????????????????????
//				new SignFinishDialog(mActivity, ids,nextID , false, "").show();
//			}
			
			super.handleMessage(msg);
		}
	};

}
*/