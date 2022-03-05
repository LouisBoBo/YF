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
		// tv_title.setText("亲，福利手慢则无，快分享给其他姐妹吧");
		// else
		// tv_title.setText("美是用来分享哒~分享最爱的美衣给好友");
		// } else {
		// tv_kickback.setVisibility(View.VISIBLE);
		// tv_title.setText("美是用来分享哒~分享最爱的美衣给好友");
		// }
		// tv_kickback.setText(context.getString(R.string.kick_back, feedBack +
		// ""));
		//

		// tv_title.setText("分享我们精心挑选的美衣给好友，让好友得到美丽与实惠，您更有10%现金奖励哦！");

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
		case R.id.ll_qq: // 分享到QQ空间

			isQQShare = true;
			

//			if (ShareUtil.intentIsAvailable(mActivity, qqShareIntent)) {
//
//				ToastUtil.showShortText(mActivity, "分享加载中，稍等哟~");
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
//				// 生成图片
//				ShareUtil.addQQQZonePlatform(mActivity);
//				CreatePic(ids);
//			} else {
//				ToastUtil.showShortText(mActivity, "您还没有安装QQ或QQ空间哦~");
//			}
//			
//			
			
			
			ToastUtil.showShortText(mActivity, "分享加载中，稍等哟~");
			
			
			
			
			if ( now_type_id == 5){
				
				// 生成图片
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

				// 生成图片
				ShareUtil.addQQQZonePlatform(mActivity);
				CreatePic(ids);
				
			}
			
			
			
			
			
		
			
			
			
			
			
			
			
			
			
			
			dismiss();

			break;
		case R.id.ll_wxin_circle: // 分享到朋友圈

			isQQShare = false;

			if (ShareUtil.intentIsAvailable(mActivity, wXinShareIntent)) {
				ToastUtil.showShortText(mActivity, "分享加载中，稍等哟~");
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

				// 生成图片
				ShareUtil.addWXPlatform(mActivity);
				CreatePic(ids);
			} else {
				ToastUtil.showShortText(mActivity, "您还没有安装微信哦~");
			}

			dismiss();

		default:
			break;
		}
	}

	*//** 得到分享的链接 *//*

	private void CreatePic(int id) {
		
		shareCommon(id);

//		// if (table == 1) {
//		switch (type) {
//		case 0: // 开店
//
//			break;
//		case 1: // 分享0元购商品
//
//			shareZero(id);
//
//			break;
//		case 2: // 分享正价商品
//			shareCommon(id);
//			break;
//		case 3: // 分享活动图
//			String HDUrl = YUrl.imgurl + SignFragment.value;
//			// createSharePicHD(HDUrl, id); //不再分享活动图
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
	// MD5Tools.md5(String.valueOf(9)) + ".jpg");// 活动图
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

					} else if (result.get("status").equals("1050")) {// 表明
						Intent intent = new Intent(context,
								NoShareActivity.class);
						intent.putExtra("isNomal", true);
						context.startActivity(intent); // 分享已经超过了
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
				Bitmap bmQr = QRCreateUtil.createQrImage(link, 160, 160);// 得到二维码图片
				String pic = shop_code.substring(1, 4) + "/" + shop_code + "/"
						+ picPath;
				Bitmap bmBg = downloadPic(pic);

				bmNew = QRCreateUtil.drawNewBitmap1(context, bmBg, bmQr, price,
						"1");
				LogYiFu.e("WD", bmNew.getWidth() + "");
				LogYiFu.e("HG", bmNew.getHeight() + "");
				QRCreateUtil.saveBitmap(bmNew, YConstance.savePicPath,
						MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
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
					// share(file); 创建图片完毕后分享到微信

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
			// 打开连接
			URLConnection con = url.openConnection();
			// 获得文件的长度
			int contentLength = con.getContentLength();
//			System.out.println("长度 :" + contentLength);
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[8192];
			// 读取到的数据长度
			int len;
			BitmapDrawable bmpDraw = new BitmapDrawable(is);

			// 完毕，关闭所有链接
			is.close();
			return bmpDraw.getBitmap();
		} catch (Exception e) {
			LogYiFu.e("TAG", "下载失败");

			e.printStackTrace();
			return null;
		}

	}

	private void shareCommon(final int id) {

	

		// 非现金奖励的签到分享由单宫图改为九宫图

		if (now_type_id == 5) {// 如果是现金奖励，分享单宫图

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

						} else if (result.get("status").equals("1050")) {// 表明
							Intent intent = new Intent(context,
									NoShareActivity.class);
							intent.putExtra("isNomal", true);
							context.startActivity(intent); // 分享已经超过了
						}
					}
				}

			}.execute();

		} else {

			// // 其他的分享9宫图

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

						} else if (result.get("status").equals("1050")) {// 表明
							Intent intent = new Intent(context,
									NoShareActivity.class);
							intent.putExtra("isNomal", true);
							context.startActivity(intent); // 分享已经超过了
						}

						*//**
						 * if (result.get("status").equals("1")) { LogYiFu.e("pic",
						 * result.get("shop_pic")); String[] picList =
						 * result.get("shop_pic").split(","); String link =
						 * result.get("link"); download(null, picList,
						 * sc.getShop_code(), result, sc, link); } else if
						 * (result.get("status").equals("1050")) {// 表明 Intent
						 * intent = new Intent(context, NoShareActivity.class);
						 * intent.putExtra("isNomal", true);
						 * context.startActivity(intent); // 分享已经超过了 }
						 * 
						 *//*
					}
				}

			}.execute();
			//
		}

	}

	// download9(null, picList, code, result, link,result.get("four_pic"));

	*//** 下载分享的图片 *//*
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
					LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
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
						// ShopDetailsActivity.this);// 得到二维码图片
						// QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
						// MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
						// } else {
						Bitmap bm = QRCreateUtil.createImage(
								mapInfos.get("QrLink"), 500, 700,
								mapInfos.get("shop_se_price"), mActivity);// 得到二维码图片
						QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
								MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
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
					// LogYiFu.e("TAG", "宝贝内容=" + shop.getShop_name() + ",宝贝链接="
					// + result);
					// ShareUtil.configPlatforms(context);

					// UMImage umImage = new UMImage(context, bmBg);
					// ShareUtil.setShareContent(context, umImage,
					// "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~~", link);
					//

					// WeiXinShareContent wei = new WeiXinShareContent();
					// wei.setTitle("一件美衣正在等待亲爱哒打开哦");
					// wei.setShareMedia(umImage);
					// wei.setShareContent("我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~~");
					// wei.setTargetUrl(link);
					// mController.setShareMedia(wei);
					//
					if (isQQShare == true) {
						onceShare2(qqShareIntent, "qq空间", null);
					} else {
						onceShare3(wXinShareIntent, "微信");
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
			onceShare2(qqShareIntent, "qq空间", file);
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
							if (eCode == StatusCode.ST_CODE_SUCCESSED) {// 分享完成
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
			// 打开连接
			URLConnection con = url.openConnection();
			// 获得文件的长度
			int contentLength = con.getContentLength();
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[8192];
			// 读取到的数据长度
			int len;
			// 输出的文件流 /sdcard/yssj/
			File file = new File(YConstance.savePicPath, MD5Tools.md5(String
					.valueOf(i)) + ".jpg");
			if (file.exists()) {
				file.delete();
			}
			LogYiFu.e("TAG", "多分享选择下载的图片。。。。");
			OutputStream os = new FileOutputStream(file);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			Log.i("TAG", "下载完毕。。。file=" + file.toString());
			// 完毕，关闭所有链接
			os.close();
			is.close();
		} catch (Exception e) {
			LogYiFu.e("TAG", "下载失败");
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

		if (now_type_id == 5) { // 现金的使用单宫图
			umImage = new UMImage(mActivity, bmNew);
		} else {
			umImage = new UMImage(mActivity, bmBg);// 其他
		}
		ShareUtil.setShareContent(mActivity, umImage,
				"我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~~", link9);

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
					 ToastUtil.showShortText(mActivity, "QQ空间分享成功");

				} else {

				}

			}
		});
	}

	private void sign() {
		// 签到
		new SAsyncTask<Void, Void, HashMap<String, Object>>(
				(FragmentActivity) mActivity, 0) {

			@Override
			protected HashMap<String, Object> doInBackground(
					FragmentActivity context, Void... params) throws Exception {
				
				if (now_type_id  == 8){    //如果是余额翻倍任务需要拿到余额翻倍剩余时间  
					
					
					LogYiFu.e("ids", ids+"");
					
					return ComModel2.getSignIn(mActivity ,true);
				}else{
					//如果不是余额翻倍任务就不用管
					
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
					// 签到完成弹出成功框
					// chooseDialog();
					SharedPreferencesUtil.saveBooleanData(context, "isFirstFenXiang", true);
					if (now_type_id  == 8){   
						String t_time = (String) result.get("t_time");
						
						SharedPreferencesUtil.saveStringData(context, Pref.TWOFOLDNESS, "2"); // 余额翻倍倍数
						SharedPreferencesUtil.saveStringData(context, Pref.END_DATE,t_time+""); // 余额翻倍剩余时间
						SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, true);   //余额翻倍是否有开启资格
						SharedPreferencesUtil.saveStringData(context, Pref.IS_OPEN, "1");//是否已开启   0：未开启  1：已开启
						
						
						
						
					}
					
					new SignFinishDialog(mActivity, now_type_id,  now_type_id_value,  next_type_id,next_type_id_value, false, "",false).show();
					

				} else {
					ToastUtil.showLongText(mActivity, "未知错误");
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
			
			if (now_type_id  == 5){ //单宫图可以判断分享成功
		
				return;
			}else{
				sign();// 签到
				
				
				
//				new SignFinishDialog(mActivity, now_type_id,  now_type_id_value,  next_type_id,next_type_id_value, false, "").show();
				
				
				
				
				
				
				
				
				
				
				
				
				
			}
//			sign();// 签到
//			chooseDialog(); // 弹出签到完成框
			
//			if (ids == 23){   //余额翻倍单独跳
//				new DialogFanbei2(mActivity, R.style.DialogStyle1).show();
//			}else{
				//其他的跳到完成签到（预告明天的任务）
//				new SignFinishDialog(mActivity, ids,nextID , false, "").show();
//			}
			
			super.handleMessage(msg);
		}
	};

}
*/