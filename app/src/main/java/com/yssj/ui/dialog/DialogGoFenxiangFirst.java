//package com.yssj.ui.dialog;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLConnection;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.bean.SocializeEntity;
//import com.umeng.socialize.bean.StatusCode;
//import com.umeng.socialize.controller.UMServiceFactory;
//import com.umeng.socialize.controller.UMSocialService;
//import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
//import com.umeng.socialize.media.UMImage;
//import com.yssj.Constants;
//import com.yssj.YConstance;
//import com.yssj.YConstance.Pref;
//import com.yssj.YUrl;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.entity.MatchShop;
//import com.yssj.entity.MatchShop.CollocationShop;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.shopdetails.NoShareActivity;
//import com.yssj.ui.activity.shopdetails.ShareDetailsActivity;
//import com.yssj.ui.fragment.circles.SignFragment;
//import com.yssj.utils.LogYiFu;
//import com.yssj.utils.MD5Tools;
//import com.yssj.utils.QRCreateUtil;
//import com.yssj.utils.ShareUtil;
//import com.yssj.utils.SharedPreferencesUtil;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.TongjiShareCount;
//import com.yssj.utils.YCache;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class DialogGoFenxiangFirst extends Dialog implements OnClickListener {
//
//	private ImageView icon_close;
//	// private LinearLayout go_share_to_sign;
//	private Boolean isQQShare;
//	private Activity mActivity;
//	private int now_type_id;
//	private int now_type_id_value;
//	private int next_type_id;
//	private int next_type_id_value;
//	private ImageView iv_qq_share, iv_wxin_share;
//	private int ids;
//	private Intent wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
//	private final Timer timer = new Timer();
//	private TimerTask task;
//	private TextView text1, text2, title, tv_liaojie_more;
//
//	private PublicToastDialog shareWaitDialog = null;
//	private String collocation_code;/* = "CS20160919xaQ69M7i" */// 要分享的搭配编号
//	private boolean WxCircleIsLink;// 朋友圈分享 链接还是图片的标志 false：分享图片 true：分享链接
//	private int shareNum;
//	Boolean firstsharefensiliulianshu = false;
//
//	public DialogGoFenxiangFirst(Activity activity, Context context, int style, int now_type_id, int now_type_id_value,
//			int next_type_id, int next_type_id_value) {
//		super(context, style);
//		setCanceledOnTouchOutside(true);
//		this.now_type_id = now_type_id;
//		this.now_type_id_value = now_type_id_value;
//		this.next_type_id = next_type_id;
//		this.next_type_id_value = next_type_id_value;
//		this.mActivity = activity;
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		firstsharefensiliulianshu = SharedPreferencesUtil.getBooleanData(mActivity, "firstsharefensiliulianshu", true);
//
//		if (firstsharefensiliulianshu) {
//
//			SharedPreferencesUtil.saveBooleanData(mActivity, "firstsharefensiliulianshu", false);
//			setContentView(R.layout.sign_share_receive_first);
//			tv_liaojie_more = (TextView) findViewById(R.id.tv_liaojie_more);
//			tv_liaojie_more.setOnClickListener(this);
//
//		} else {
//			SharedPreferencesUtil.saveBooleanData(mActivity, "isFirstFenXiang", false);
//			setContentView(R.layout.sign_share_receive);
//		}
//
//		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//		iv_wxin_share = (ImageView) findViewById(R.id.iv_wxin_share);
//		iv_qq_share = (ImageView) findViewById(R.id.iv_qq_share);
//		icon_close = (ImageView) findViewById(R.id.icon_close);
//		title = (TextView) findViewById(R.id.title);
//		text1 = (TextView) findViewById(R.id.text1);
//		text2 = (TextView) findViewById(R.id.text2);
//		icon_close.setOnClickListener(this);
//		// go_share_to_sign = (LinearLayout)
//		// findViewById(R.id.go_share_to_sign);
//		// go_share_to_sign.setOnClickListener(this);
//		iv_wxin_share.setOnClickListener(this);
//		iv_qq_share.setOnClickListener(this);
//		shareWaitDialog = new PublicToastDialog(mActivity, R.style.DialogStyle1, "");
//		String[] value = SignFragment.doValue.split(",");
//		if (SignFragment.doType == 801 || SignFragment.doType == 802 || SignFragment.doType == 803
//				|| SignFragment.doType == 8) {// 分享搭配
//
//			if (value.length > 1) {
//				if ("2".equals(value[1])) {
//					WxCircleIsLink = true;
//				} else {
//					WxCircleIsLink = false;
//				}
//			} else {
//				WxCircleIsLink = false;
//			}
//			// if(TextUtils.isEmpty(value[0])||"null".equals(value[0])){
//			// shareNum =1;
//			// }else{
//			try {
//				shareNum = Integer.parseInt(value[0]);
//			} catch (Exception e) {
//				shareNum = SignFragment.doNum;
//			}
//			// }
//		} else if (SignFragment.doType == 701 || SignFragment.doType == 702 || SignFragment.doType == 703
//				|| SignFragment.doType == 7) {// 分享正价
//			if (value.length > 2) {
//				if ("2".equals(value[2])) {
//					WxCircleIsLink = true;
//				} else {
//					WxCircleIsLink = false;
//				}
//			} else {
//				WxCircleIsLink = false;
//			}
//
//			// if(TextUtils.isEmpty(value[1])||"null".equals(value[1])){
//			// shareNum =1;
//			// }else{
//			try {
//				shareNum = Integer.parseInt(value[1]);
//			} catch (Exception e) {
//				shareNum = SignFragment.doNum;
//			}
//			// }
//		}
//
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.icon_close: // 关闭
//
//			this.dismiss();
//
//			break;
//
//		case R.id.iv_wxin_share: // 分享到微信朋友圈
//
//			shareWaitDialog.show();
//
//			isQQShare = false;
//
//			if (ShareUtil.intentIsAvailable(mActivity, wXinShareIntent)) {
//
//				// task = new TimerTask() {
//				// @Override
//				// public void run() {
//				// // TODO Auto-generated method stub
//				// Message message = new Message();
//				// message.what = 1;
//				// handler.sendMessage(message);
//				// }
//				// };
//				//
//				// timer.schedule(task, 8000);
//
//				// 生成图片
//				ShareUtil.addWXPlatform(mActivity);
//				CreatePic(ids);
//			} else {
//				shareWaitDialog.dismiss();
//				ToastUtil.showShortText(mActivity, "您还没有安装微信哦~");
//			}
//
//			dismiss();
//
//			break;
//		case R.id.iv_qq_share: // 分享到QQ
//			isQQShare = true;
//
//			shareWaitDialog.show();
//
//			// if (now_type_id == 5) {
//
//			// 生成图片
//			ShareUtil.addQQQZonePlatform(mActivity);
//			CreatePic(ids);
//
//			// } else {
//			// task = new TimerTask() {
//			// @Override
//			// public void run() {
//			// // TODO Auto-generated method stub
//			// Message message = new Message();
//			// message.what = 1;
//			// handler.sendMessage(message);
//			// }
//			// };
//			//
//			// timer.schedule(task, 8000);
//
//			// 生成图片
//			// ShareUtil.addQQQZonePlatform(mActivity);
//			// CreatePic(ids);
//			//
//			// }
//			dismiss();
//
//			break;
//
//		case R.id.tv_liaojie_more:
//
//			// 跳至自强那个类
//			mActivity.startActivity(new Intent(mActivity, ShareDetailsActivity.class));
//			break;
//
//		// case R.id.go_share_to_sign: // 去分享
//		//
//		// SignPopupwindow myPopupwindow = new SignPopupwindow(activity,
//		// now_type_id, now_type_id_value, next_type_id,
//		// next_type_id_value);
//		// myPopupwindow.showAtLocation(activity.getWindow().getDecorView(),
//		// Gravity.BOTTOM, 0, 0);
//		//
//		// this.dismiss();
//		// break;
//
//		default:
//			break;
//		}
//
//	}
//
//	// Handler handler = new Handler() {
//	// @Override
//	// public void handleMessage(Message msg) {
//	// // TODO Auto-generated method stub
//	//
//	// // if (now_type_id == 5||SignFragment.type==6) { // 单宫图可以判断分享成功
//	// //
//	// // return;
//	// // } else {
//	// if (!WxCircleIsLink && now_type_id != 5) {
//	// sign();// 签到
//	// }
//	//
//	// // new SignFinishDialog(mActivity, now_type_id,
//	// // now_type_id_value, next_type_id,next_type_id_value, false,
//	// // "").show();
//	// // }
//	// // sign();// 签到
//	// // chooseDialog(); // 弹出签到完成框
//	//
//	// // if (ids == 23){ //余额翻倍单独跳
//	// // new DialogFanbei2(mActivity, R.style.DialogStyle1).show();
//	// // }else{
//	// // 其他的跳到完成签到（预告明天的任务）
//	// // new SignFinishDialog(mActivity, ids,nextID , false, "").show();
//	// // }
//	//
//	// super.handleMessage(msg);
//	// }
//	// };
//	private void takeTimeSign() {
//		task = new TimerTask() {
//			@Override
//			public void run() {
//				sign();
//			}
//		};
//		timer.schedule(task, 6000);
//	}
//
//	private int num;
//
//	private void sign() {
//		final String SHARENUMKEY = SignFragment.signIndex + "share_num" + YCache.getCacheUser(mActivity).getUser_id();
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String now_time = sdf.format(date);
//		String nowTime = SharedPreferencesUtil.getStringData(mActivity,
//				"share_now_time" + YCache.getCacheUser(mActivity).getUser_id(), "");
//		num = Integer.valueOf(SharedPreferencesUtil.getStringData(mActivity, SHARENUMKEY, "0"));
//
//		if (!now_time.equals(nowTime)) {
//			num = 0;
//		}
//		num++;
//		SharedPreferencesUtil.saveStringData(mActivity, "share_now_time" + YCache.getCacheUser(mActivity).getUser_id(),
//				now_time);
//		SharedPreferencesUtil.saveStringData(mActivity, SHARENUMKEY, "" + num);
//		if (num < shareNum) {// 小于要求的分享次数
//			if (SignFragment.doNum == 1) {// 就是一次性发放奖励 是现金奖励 就需要每次调接口 分次奖励
//				ToastUtil.showShortText(mActivity, "再分享" + (shareNum - num) + "件可完成任务喔~");
//				return;
//			}
//		}
//		// 签到
//		new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) mActivity, 0) {
//
//			@Override
//			protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
//					throws Exception {
//
//				if (now_type_id == 8) { // 如果是余额翻倍任务需要拿到余额翻倍剩余时间
//
//					return ComModel2.getSignIn(mActivity, true, true, SignFragment.signIndex, SignFragment.doClass);
//				} else {
//					// 如果不是余额翻倍任务就不用管
//
//					return ComModel2.getSignIn(mActivity, false, true, SignFragment.signIndex, SignFragment.doClass);
//				}
//
//			}
//
//			protected boolean isHandleException() {
//				return true;
//			};
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (e == null && result != null) {
//					// 签到完成弹出成功框
//					// chooseDialog();
//					// SharedPreferencesUtil.saveBooleanData(context,
//					// "isFirstFenXiang", true);
//					if (now_type_id == 8) {
//						String t_time = (String) result.get("t_time");
//
//						SharedPreferencesUtil.saveStringData(context, Pref.TWOFOLDNESS, "2"); // 余额翻倍倍数
//						SharedPreferencesUtil.saveStringData(context, Pref.END_DATE, t_time + ""); // 余额翻倍剩余时间
//						SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, true); // 余额翻倍是否有开启资格
//						SharedPreferencesUtil.saveStringData(context, Pref.IS_OPEN, "1");// 是否已开启
//																							// 0：未开启
//																							// 1：已开启
//
//					}
//
//					// new SignFinishDialog(mActivity, now_type_id,
//					// now_type_id_value, next_type_id, next_type_id_value,
//					// false, "", false).show();
//					if (num < shareNum) {
//						String ss = "";
//						switch (SignFragment.jiangliID) {
//						case 3:
//							ss = "元优惠券";
//							break;
//						case 4:
//							ss = "积分";
//							break;
//						case 5:
//							ss = "元";
//							break;
//						default:
//							break;
//						}
//						ToastUtil.showShortText(mActivity, "分享成功奖励" +
//						// new
//						// java.text.DecimalFormat("#0.00").format(Double.valueOf(SignFragment.jiangliValue)/shareNum)
//						SignFragment.jiangliValue + ss + "，还有" + (shareNum - num) + "次机会喔~");
//					} else if (num >= shareNum) {
//						String ss = "";
//						switch (SignFragment.jiangliID) {
//						case 3:
//							ss = "元优惠券";
//							NewSignCommonDiaolg dialog3 = new NewSignCommonDiaolg(context, R.style.DialogQuheijiao2,
//									"share_sign_finish", new java.text.DecimalFormat("0.##").format(
//											Double.parseDouble(SignFragment.jiangliValue) * SignFragment.doNum) + ss);
//							dialog3.show();
//							SharedPreferencesUtil.saveStringData(mActivity, SHARENUMKEY, "0");
//							SharedPreferencesUtil.removeData(mActivity, SHARENUMKEY);
//							break;
//						case 4:
//							ss = "积分";
//							NewSignCommonDiaolg dialog4 = new NewSignCommonDiaolg(context, R.style.DialogQuheijiao2,
//									"share_sign_finish", new java.text.DecimalFormat("0.##").format(
//											Double.parseDouble(SignFragment.jiangliValue) * SignFragment.doNum) + ss);
//							dialog4.show();
//							SharedPreferencesUtil.saveStringData(mActivity, SHARENUMKEY, "0");
//							SharedPreferencesUtil.removeData(mActivity, SHARENUMKEY);
//							break;
//						case 5:
//							ss = "元";
//							NewSignCommonDiaolg dialog5 = new NewSignCommonDiaolg(context, R.style.DialogQuheijiao2,
//									"share_sign_finish", new java.text.DecimalFormat("0.##").format(
//											Double.parseDouble(SignFragment.jiangliValue) * SignFragment.doNum) + ss);
//							dialog5.show();
//							SharedPreferencesUtil.saveStringData(mActivity, SHARENUMKEY, "0");
//							SharedPreferencesUtil.removeData(mActivity, SHARENUMKEY);
//							break;
//						case 8:// 余额翻倍
//							NewSignCommonDiaolg dialog8 = new NewSignCommonDiaolg(context, R.style.DialogQuheijiao2,
//									"share_sign_fanbei_finish");
//							dialog8.show();
//							SharedPreferencesUtil.saveStringData(mActivity, SHARENUMKEY, "0");
//							SharedPreferencesUtil.removeData(mActivity, SHARENUMKEY);
//							break;
//						case 9:// 升级金币
//							NewSignCommonDiaolg dialog9 = new NewSignCommonDiaolg(context, R.style.DialogQuheijiao2,
//									"share_sign_jinbi_finish");
//							dialog9.show();
//							SharedPreferencesUtil.saveStringData(mActivity, SHARENUMKEY, "0");
//							SharedPreferencesUtil.removeData(mActivity, SHARENUMKEY);
//							break;
//						case 10:// 升级金券
//							NewSignCommonDiaolg dialog10 = new NewSignCommonDiaolg(context, R.style.DialogQuheijiao2,
//									"share_sign_jinquan_finish");
//							dialog10.show();
//							SharedPreferencesUtil.saveStringData(mActivity, SHARENUMKEY, "0");
//							SharedPreferencesUtil.removeData(mActivity, SHARENUMKEY);
//							break;
//
//						default:
//							break;
//						}
//					}
//
//				} else {
//					// ToastUtil.showLongText(mActivity, "未知错误");
//				}
//
//				//
//				// if(result.get("changeTable") == true +""){
//				// SignFragment.queryLoginSignList();
//				// SignFragment.querySignListYet();
//				// //
//				// }
//			}
//
//		}.execute();
//	}
//
//	private void CreatePic(int id) {
//
//		shareCommon(id);
//
//	}
//
//	private String link9 = "";
//
//	private void shareCommon(final int id) {
//
//		// 非现金奖励的签到分享由单宫图改为九宫图
//
//		if (now_type_id == 5) {// 如果是现金奖励，分享单宫图
//
//			if (SignFragment.doType == 801 || SignFragment.doType == 802 || SignFragment.doType == 803
//					|| SignFragment.doType == 8) {// 分享搭配购商品
//
//				getCollocationCode();
//
//			} else {
//				new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mActivity, R.string.wait) {
//
//					@Override
//					protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
//							throws Exception {
//						// TODO Auto-generated method stub
//						return ComModel2.getShareShopLinkHobby(context, "true");
//					}
//
//					@Override
//					protected boolean isHandleException() {
//						return true;
//					}
//
//					@Override
//					protected void onPostExecute(FragmentActivity context, HashMap<String, String> result,
//							Exception e) {
//						// TODO Auto-generated method stub
//						super.onPostExecute(context, result, e);
//						if (null == e) {
//							if (result.get("status").equals("1")) {
//
//								String[] picList = result.get("four_pic").split(",");
//
//								String shop_code = result.get("shop_code");
//								link1 = (String) result.get("link");
//								TongjiShareCount.tongjifenxiangCount();
//								TongjiShareCount.tongjifenxiangwho(shop_code);
//
//								createSharePic((String) result.get("link"), picList.length > 2 ? picList[2] : "",
//										(String) result.get("shop_se_price"), (String) result.get("shop_code"), null,
//										id);
//
//							} else if (result.get("status").equals("1050")) {// 表明
//								if (null != shareWaitDialog) {
//									shareWaitDialog.dismiss();
//								}
//								Intent intent = new Intent(context, NoShareActivity.class);
//								intent.putExtra("isNomal", true);
//								context.startActivity(intent); // 分享已经超过了
//							} else {
//								if (null != shareWaitDialog) {
//									shareWaitDialog.dismiss();
//								}
//							}
//						} else {
//							if (null != shareWaitDialog) {
//								shareWaitDialog.dismiss();
//							}
//						}
//					}
//
//				}.execute();
//			}
//		} else {
//
//			if (SignFragment.doType == 801 || SignFragment.doType == 802 || SignFragment.doType == 803
//					|| SignFragment.doType == 8) {// 分享搭配购商品
//
//				getCollocationCode();
//
//			} else {
//				// // 其他的分享9宫图
//
//				new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mActivity, R.string.wait) {
//
//					@Override
//					protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
//							throws Exception {
//						// TODO Auto-generated method stub
//						return ComModel2.getShareShopLinkHobby(context, "true");
//					}
//
//					@Override
//					protected boolean isHandleException() {
//						return true;
//					}
//
//					@Override
//					protected void onPostExecute(FragmentActivity context, HashMap<String, String> result,
//							Exception e) {
//						// TODO Auto-generated method stub
//						super.onPostExecute(context, result, e);
//						if (null == e) {
//							if (result.get("status").equals("1")) {
//
//								String[] picList = result.get("shop_pic").split(",");
//								link9 = result.get("link");
//								String code = result.get("shop_code");
//
//								String shop_code = result.get("shop_code");
//
//								//
//								TongjiShareCount.tongjifenxiangCount();
//								TongjiShareCount.tongjifenxiangwho(shop_code);
//
//								// download(null, picList, code, result, shop,
//								// link,four_pic);
//								// download9(null, picList, code, result, link9,
//								// result.get("four_pic"));
//								getNineBmBg(null, picList, code, result, link9, result.get("four_pic"));
//
//								//
//								// createSharePic((String) result.get("link"),
//								// picList.length > 2 ? picList[2] : "",
//								// (String) result.get("shop_se_price"),
//								// (String)
//								// result.get("shop_code"), null, id);
//
//							} else if (result.get("status").equals("1050")) {// 表明
//								if (null != shareWaitDialog) {
//									shareWaitDialog.dismiss();
//								}
//								Intent intent = new Intent(context, NoShareActivity.class);
//								intent.putExtra("isNomal", true);
//								context.startActivity(intent); // 分享已经超过了
//							} else {
//								if (null != shareWaitDialog) {
//									shareWaitDialog.dismiss();
//								}
//							}
//
//							/**
//							 * if (result.get("status").equals("1")) {
//							 * MyLogYiFu.e("pic", result.get("shop_pic"));
//							 * String[] picList =
//							 * result.get("shop_pic").split(","); String link =
//							 * result.get("link"); download(null, picList,
//							 * sc.getShop_code(), result, sc, link); } else if
//							 * (result.get("status").equals("1050")) {// 表明
//							 * Intent intent = new Intent(context,
//							 * NoShareActivity.class);
//							 * intent.putExtra("isNomal", true);
//							 * context.startActivity(intent); // 分享已经超过了 }
//							 * 
//							 */
//						} else {
//							if (null != shareWaitDialog) {
//								shareWaitDialog.dismiss();
//							}
//						}
//					}
//
//				}.execute();
//				//
//			}
//		}
//	}
//
//	private Bitmap bmBg1;
//
//	private void createSharePic(final String link, final String picPath, final String price, final String shop_code,
//			final View v, final int id) {
//		new SAsyncTask<Void, Void, Void>((FragmentActivity) mActivity, R.string.wait) {
//
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//
//			@Override
//			protected Void doInBackground(FragmentActivity context, Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				Bitmap bmQr = QRCreateUtil.createQrImage(link, 160, 160);// 得到二维码图片
//				String pic = shop_code.substring(1, 4) + "/" + shop_code + "/" + picPath;
//				bmBg1 = downloadPic(pic);
//
//				bmNew = QRCreateUtil.drawNewBitmap1(context, bmBg1, bmQr, price, "1");
//				LogYiFu.e("WD", bmNew.getWidth() + "");
//				LogYiFu.e("HG", bmNew.getHeight() + "");
//				if (WxCircleIsLink && !isQQShare) {
//					QRCreateUtil.saveBitmap(bmBg1, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");
//				} else {
//					QRCreateUtil.saveBitmap(bmNew, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
//				}
//				return super.doInBackground(context, params);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, Void result, Exception e) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result, e);
//				if (null == e) {
//					file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");
//					// share(file); 创建图片完毕后分享到微信
//					if (null != shareWaitDialog) {
//						shareWaitDialog.dismiss();
//					}
//					share(file, shop_code, link);
//
//				} else {
//					if (null != shareWaitDialog) {
//						shareWaitDialog.dismiss();
//					}
//				}
//			}
//
//		}.execute();
//	}
//
//	private Bitmap downloadPic(String picPath) {
//		try {
//			URL url = new URL(YUrl.imgurl + picPath);
//			// 打开连接
//			URLConnection con = url.openConnection();
//			// 获得文件的长度
//			int contentLength = con.getContentLength();
//			// System.out.println("长度 :" + contentLength);
//			// 输入流
//			InputStream is = con.getInputStream();
//			// 1K的数据缓冲
//			byte[] bs = new byte[8192];
//			// 读取到的数据长度
//			int len;
//			BitmapDrawable bmpDraw = new BitmapDrawable(is);
//
//			// 完毕，关闭所有链接
//			is.close();
//			return bmpDraw.getBitmap();
//		} catch (Exception e) {
//			LogYiFu.e("TAG", "下载失败");
//
//			e.printStackTrace();
//			return null;
//		}
//
//	}
//
//	private void share(File file, String shop_code, String link) {
//
//		if (isQQShare) {
//			yunYunTongJi(shop_code, 104, 2);
//			onceShare2(qqShareIntent, "qq空间", file);
//		} else {
//			yunYunTongJi(shop_code, 108, 2);
//			if (WxCircleIsLink) {// true 分享链接
//				UMImage umImage = new UMImage(mActivity, file);
//				ShareUtil.setShareContent(mActivity, umImage, "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);
//				performShareMatch(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
//			} else {
//				ShareUtil.configPlatforms(mActivity);
//				UMImage umImage = new UMImage(mActivity, file);
//				ShareUtil.shareShop(mActivity, umImage);
//
//				mController.postShare(mActivity, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {
//
//					@Override
//					public void onStart() {
//
//					}
//
//					@Override
//					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
//						// String showText = platform.toString();
//						if (eCode == StatusCode.ST_CODE_SUCCESSED) {// 分享完成
//							// MobclickAgent.onEvent(mActivity,
//							// "sign_sharesuccess");
//							sign();
//
//						} else {
//
//						}
//					}
//				});
//			}
//		}
//
//	}
//
//	private String[] picListNine;
//	private String shop_codeNine;
//	private HashMap<String, String> mapInfosNine;
//	private String linkNine;
//	private String four_picNine;
//
//	/**
//	 * 获取九宫图的二维码背景图片
//	 * 
//	 * @param context
//	 */
//	public void getNineBmBg(View v, final String[] picList, final String shop_code,
//			final HashMap<String, String> mapInfos, final String link, final String four_pic) {
//		new SAsyncTask<Void, Void, String>((FragmentActivity) mActivity, R.string.wait) {
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//
//			@Override
//			protected String doInBackground(FragmentActivity context, Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.getShareBg(context);
//
//			}
//
//			protected void onPostExecute(FragmentActivity context, String result, Exception e) {
//				super.onPostExecute(context, result, e);
//				// Bitmap bmNineBg = downloadPic(result);
//				// download9(null, picList, shop_code, mapInfos, link,
//				// four_pic,bmNineBg);
//				picListNine = picList;
//				shop_codeNine = shop_code;
//				mapInfosNine = mapInfos;
//				linkNine = link;
//				four_picNine = four_pic;
//				getPicture(result);
//
//			}
//		}.execute();
//	}
//
//	private byte[] picByte;
//	private Bitmap nineBitmap;
//
//	public void getPicture(final String picPath) {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				if (TextUtils.isEmpty(picPath)) {
//					Message message = new Message();
//					message.what = 99;
//					handle.sendMessage(message);
//				} else {
//
//					try {
//						URL url = new URL(YUrl.imgurl + picPath);
//						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//						conn.setRequestMethod("GET");
//						conn.setReadTimeout(10000);
//
//						if (conn.getResponseCode() == 200) {
//							InputStream fis = conn.getInputStream();
//							ByteArrayOutputStream bos = new ByteArrayOutputStream();
//							byte[] bytes = new byte[1024];
//							int length = -1;
//							while ((length = fis.read(bytes)) != -1) {
//								bos.write(bytes, 0, length);
//							}
//							picByte = bos.toByteArray();
//							bos.close();
//							fis.close();
//
//							Message message = new Message();
//							message.what = 99;
//							handle.sendMessage(message);
//						}
//
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}).start();
//	}
//
//	Handler handle = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			if (msg.what == 99) {
//				if (picByte != null) {
//					nineBitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
//					download9(null, picListNine, shop_codeNine, mapInfosNine, linkNine, four_picNine, nineBitmap);
//				} else {
//					download9(null, picListNine, shop_codeNine, mapInfosNine, linkNine, four_picNine, null);
//				}
//			}
//		}
//	};
//
//	private Bitmap bmNew;
//	private File file;
//
//	private Intent qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
//
//	private void download9(View v, final String[] picList, final String shop_code,
//			final HashMap<String, String> mapInfos, final String link, final String four_pic, final Bitmap bmNineBg) {
//		final List<String> pics = new ArrayList<String>();
//
//		new SAsyncTask<Void, Void, Void>((FragmentActivity) mActivity, v, R.string.wait) {
//
//			@Override
//			protected Void doInBackground(Void... params) {
//				// TODO Auto-generated method stub
//				File fileDirec = new File(YConstance.savePicPath);
//				if (!fileDirec.exists()) {
//					fileDirec.mkdir();
//				}
//				File[] listFiles = new File(YConstance.savePicPath).listFiles();
//				if (listFiles.length != 0) {
//					LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
//					for (File file : listFiles) {
//						file.delete();
//					}
//				}
//				// LogYiFu.i("TAG", "piclist=" + picList.length);
//				// List<String> pics = new ArrayList<String>();
//				for (int j = 0; j < picList.length; j++) {
//					if (!picList[j].contains("reveal_") && !picList[j].contains("detail_")
//							&& !picList[j].contains("real_")) {
//						pics.add(picList[j]);
//					}
//				}
//				int j = pics.size() + 1;
//				if (pics.size() > 8) {
//					j = 9;
//				}
//
//				// deletePIC(YConstance.savePicPath ,false);
//				// deletePIC(YConstance.saveUploadPicPath ,false);
//				int nP = j > 5 ? 4 : j - 1;
//				for (int i = 0; i < j; i++) {
//					if (i == nP) {
//						/*
//						 * ComModel2.saveQRCode(PaymentSuccessActivity.this,
//						 * shop_code);
//						 */
//						// if (isMeal ||
//						// "SignShopDetail".equals(signShopDetail)) {
//						// Bitmap bm = QRCreateUtil.createZeroImage(link, 500,
//						// 700, mapInfos.get("shop_se_price"),
//						// ShopDetailsActivity.this);// 得到二维码图片
//						// QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
//						// MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
//						// } else {
//						// Bitmap bm =
//						// QRCreateUtil.createImage(mapInfos.get("QrLink"), 500,
//						// 700,
//						// mapInfos.get("shop_se_price"), mActivity);// 得到二维码图片
//						// 九宫图二维码新样式
//						// Bitmap bmQr =
//						// QRCreateUtil.createQrImage(mapInfos.get("QrLink"),
//						// 250, 250);
//						// Bitmap bm = QRCreateUtil.drawNewBitmapNine(mActivity,
//						// bmQr, mapInfos.get("shop_se_price"),false);
//						Bitmap bmQr = QRCreateUtil.createQrImage(mapInfos.get("QrLink"), 190, 190);
//						Bitmap bm = QRCreateUtil.drawNewBitmapNine2(mActivity, bmQr, bmNineBg);
//
//						QRCreateUtil.saveBitmap(bm, YConstance.savePicPath, MD5Tools.md5(String.valueOf(i)) + ".jpg");// 保存二维码图片
//						// }
//						// downloadPic(mapInfos.get("qr_pic"), 9);
//						continue;
//					}
//					int m = i > 4 ? i - 1 : i;
//					downloadPic(shop_code.substring(1, 4) + "/" + shop_code + "/" + pics.get(m) + "!450", i);
//					bmBg = downloadPic(
//							shop_code.substring(1, 4) + "/" + shop_code + "/" + four_pic.split(",")[2] + "!450");
//				}
//				return super.doInBackground(params);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, Void result) {
//				// TODO Auto-generated method stub
//				// showShareDialog();
//				// if (instance == null) {
//				// return;
//				// }
//				if (null != context && null != context.getWindow().getDecorView() && !context.isFinishing()) {
//					shareWaitDialog.dismiss();
//					// MyLogYiFu.e("TAG", "宝贝内容=" + shop.getShop_name() +
//					// ",宝贝链接="
//					// + result);
//					// ShareUtil.configPlatforms(context);
//
//					//
//
//					// WeiXinShareContent wei = new WeiXinShareContent();
//					// wei.setTitle("一件美衣正在等待亲爱哒打开哦");
//					// wei.setShareMedia(umImage);
//					// wei.setShareContent("我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~~");
//					// wei.setTargetUrl(link);
//					// mController.setShareMedia(wei);
//					//
//					if (isQQShare == true) {
//						yunYunTongJi(shop_code, 104, 2);
//						onceShare2(qqShareIntent, "qq空间", null);
//					} else {
//						yunYunTongJi(shop_code, 108, 2);
//						if (WxCircleIsLink && ShareUtil.intentIsAvailable(mActivity, wXinShareIntent)) {
//							UMImage umImage = new UMImage(context, bmBg);
//							ShareUtil.setShareContent(context, umImage, "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);
//							performShareMatch(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
//						} else {
//							onceShare3(wXinShareIntent, "微信");
//						}
//					}
//
//					// ShareUtil.share(ShopDetailsActivity.this);
//					// myPopupwindow.setLink(link);
//					// myPopupwindow.setUmImage(umImage);
//					// myPopupwindow = new MyPopupwindow(context,
//					// shop.getKickback(), umImage, link);
//					// if (ShopDetailsActivity.instance != null) {
//					// myPopupwindow.showAtLocation(context.getWindow()
//					// .getDecorView(), Gravity.BOTTOM, 0, 0);
//					// }
//					// shareTo();
//					super.onPostExecute(context, result);
//				}
//
//			}
//
//		}.execute();
//
//	}
//
//	private void onceShare2(Intent intent, String perform, File file) {
//		if (ShareUtil.intentIsAvailable(mActivity, intent)) {
//
//			if (now_type_id == 5) {
//				performShare(SHARE_MEDIA.QZONE, qqShareIntent);
//
//			} else {
//				mActivity.startActivity(intent);
//				takeTimeSign();// 九宫图 通过计时 调用签到
//			}
//
//		} else {
//			performShare(SHARE_MEDIA.QZONE, qqShareIntent);
//		}
//	}
//
//	private String link1 = "";
//
//	public void performShare(SHARE_MEDIA platform, final Intent intent) {
//		UMImage umImage;
//
//		if (now_type_id == 5) { // 现金的使用单宫图
//			umImage = new UMImage(mActivity, bmBg1);
//			ShareUtil.setShareContent(mActivity, umImage, "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link1);
//		} else {
//			umImage = new UMImage(mActivity, bmBg);// 其他
//			ShareUtil.setShareContent(mActivity, umImage, "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link9);
//		}
//
//		mController.postShare(mActivity, platform, new SnsPostListener() {
//
//			@Override
//			public void onStart() {
//				LogYiFu.e("showText", "asdsafdsf");
//				// chooseDialog();
//			}
//
//			@Override
//			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
//				String showText = platform.toString();
//				LogYiFu.e("showText", showText);
//
//				if (eCode == StatusCode.ST_CODE_SUCCESSED) {
//
//					sign();
//					// ToastUtil.showShortText(mActivity, "QQ空间分享成功");
//
//				} else {
//
//				}
//
//			}
//		});
//	}
//
//	private Bitmap bmBg;
//
//	private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);
//
//	private void downloadPic(String picPath, int i) {
//		try {
//			URL url = new URL(YUrl.imgurl + picPath);
//			// 打开连接
//			URLConnection con = url.openConnection();
//			// 获得文件的长度
//			int contentLength = con.getContentLength();
//			// 输入流
//			InputStream is = con.getInputStream();
//			// 1K的数据缓冲
//			byte[] bs = new byte[8192];
//			// 读取到的数据长度
//			int len;
//			// 输出的文件流 /sdcard/yssj/
//			File file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(i)) + ".jpg");
//			if (file.exists()) {
//				file.delete();
//			}
//			LogYiFu.e("TAG", "多分享选择下载的图片。。。。");
//			OutputStream os = new FileOutputStream(file);
//			// 开始读取
//			while ((len = is.read(bs)) != -1) {
//				os.write(bs, 0, len);
//			}
//			LogYiFu.i("TAG", "下载完毕。。。file=" + file.toString());
//			// 完毕，关闭所有链接
//			os.close();
//			is.close();
//		} catch (Exception e) {
//			LogYiFu.e("TAG", "下载失败");
//			e.printStackTrace();
//		}
//	}
//
//	private void onceShare3(Intent intent, String perform) {
//		if (ShareUtil.intentIsAvailable(mActivity, intent)) {
//			mActivity.startActivity(intent);
//			takeTimeSign();// 九宫图 通过计时 调用签到
//		}
//	}
//
//	private void yunYunTongJi(final String shop_code, final int type, final int tab_type) {
//		new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mActivity, R.string.wait) {
//
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//
//			@Override
//			protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
//					throws Exception {
//
//				return ComModel2.getOperator(context, shop_code, type, tab_type);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result, e);
//
//			}
//
//		}.execute();
//	}
//
//	///////////////////////////////////////////// 搭配购的签到分享/////////////////////////////////////////////////
//	/**
//	 * 先获取要分享的搭配商品编号
//	 */
//	private void getCollocationCode() {
//
//		shareWaitDialog.show();
//		new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) mActivity, R.string.wait) {
//
//			@Override
//			protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
//					throws Exception {
//				return ComModel2.getCollectionCode(context);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result, e);
//				if (e != null || result == null) {
//					// CenterToast.centerToast(mActivity, "分享失败！");
//					shareWaitDialog.dismiss();
//					return;
//				} else {
//					collocation_code = (String) result.get("code");
//					TongjiShareCount.tongjifenxiangCount();
//					TongjiShareCount.tongjifenxiangwho(collocation_code);
//					String link = "";
//					if (!((String) result.get("link")).startsWith("http")) {
//						link = "http://" + result.get("link");
//					} else {
//						link = (String) result.get("link");
//					}
//
//					queryMatchDetails(link);
//				}
//			}
//
//		}.execute();
//	}
//
//	private String mCollocationName;// 分享单供图的标题
//	private List<CollocationShop> mCollocationShopsList;// 搭配购 中的商品的集合
//
//	/**
//	 * 分享前 先请求搭配购 商品详情 查询相关分享内容
//	 */
//	private void queryMatchDetails(final String link) {
//		new SAsyncTask<String, Void, MatchShop>((FragmentActivity) mActivity, R.string.wait) {
//
//			@Override
//			protected MatchShop doInBackground(FragmentActivity context, String... params) throws Exception {
//				return ComModel2.getMatchDetails(context, collocation_code);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, MatchShop result, Exception e) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result, e);
//				if (e != null || result == null) {
//					// CenterToast.centerToast(mActivity, "分享失败！");
//					shareWaitDialog.dismiss();
//					return;
//				}
//				mCollocationName = result.getCollocation_name();
//				mCollocationShopsList = result.getCollocation_shop();
//				// createPicMatch(collocation_code);
//				createSharePicMatch(link, result.getCollocation_pic(), null);
//			}
//
//		}.execute();
//	}
//
//	// private void createPicMatch(final String code){
//	// new SAsyncTask<String, Void, HashMap<String, String>>(
//	// (FragmentActivity) mActivity, R.string.wait) {
//	//
//	// @Override
//	// protected HashMap<String, String> doInBackground(
//	// FragmentActivity context, String... params)
//	// throws Exception {
//	// return ComModel2.getMatchShopLinkOrPic(code, mActivity, true);
//	// }
//	//
//	// @Override
//	// protected boolean isHandleException() {
//	// return true;
//	// }
//	//
//	// @Override
//	// protected void onPostExecute(FragmentActivity context,
//	// HashMap<String, String> result, Exception e) {
//	// super.onPostExecute(context, result, e);
//	// if (null == e) {
//	//
//	// if (result.get("status").equals("1")) {
//	// String link = "";
//	// if(!result.get("link").startsWith("http")){
//	// link ="http://"+result.get("link");
//	// }else{
//	// link=result.get("link");
//	// }
//	// createSharePicMatch(link,
//	// result.get("pic"),null);
//	//
//	// } else if (result.get("status").equals("1050")) {// 表明
//	// Intent intent = new Intent(context,
//	// NoShareActivity.class);
//	// intent.putExtra("isNomal", true);
//	// context.startActivity(intent); // 分享已经超过了
//	// }
//	// }
//	// }
//	//
//	// }.execute();
//	// }
//
//	private void createSharePicMatch(final String link, final String picPath, final View v) {
//		new SAsyncTask<Void, Void, Void>((FragmentActivity) mActivity, R.string.wait) {
//
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//
//			@Override
//			protected Void doInBackground(FragmentActivity context, Void... params) throws Exception {
//				// TODO Auto-generated method stub
//
//				bmBg = downloadPic(picPath);
//				Bitmap bmQr = QRCreateUtil.createQrImage(link, 160, 160);
//				bmNew = QRCreateUtil.drawNewBitmapMatch2(context, bmBg, bmQr, mCollocationName, mCollocationShopsList);
//				LogYiFu.e("WD", bmNew.getWidth() + "");
//				LogYiFu.e("HG", bmNew.getHeight() + "");
//
//				if (isQQShare || WxCircleIsLink) {
//					QRCreateUtil.saveBitmap(bmBg, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存图片
//																													// 不加二维码图片不加文字
//				} else {
//					QRCreateUtil.saveBitmap(bmNew, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
//				}
//
//				return super.doInBackground(context, params);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, Void result, Exception e) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result, e);
//				if (null == e) {
//					shareWaitDialog.dismiss();
//					file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");
//					// share(file); 创建图片完毕后分享到微信
//
//					shareMatch(file, link);
//
//				} else {
//					if (null != shareWaitDialog) {
//						shareWaitDialog.dismiss();
//					}
//				}
//			}
//
//		}.execute();
//	}
//
//	private void shareMatch(File file, String link) {
//		if (file == null) {
//			ToastUtil.showShortText(mActivity, "您的网络状态不太好哦~~");
//			return;
//		}
//		UMImage umImage = new UMImage(mActivity, file);
//		if (isQQShare) {
//			// if(isHaveQQZone){
//			// ShareUtil.configPlatforms(mActivity);
//			// ShareUtil.shareShop(mActivity, umImage);
//			// performShare(SHARE_MEDIA.QZONE, qqShareIntent);
//			// }else{
//			// ShareUtil.setShareContent(mActivity, umImage,
//			// "撩汉必备手册，全在这里>>", link);
//			ShareUtil.setShareContent(mActivity, umImage, "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);
//			performShareMatch(SHARE_MEDIA.QZONE, qqShareIntent);
//			// }
//
//		} else {
//			if (WxCircleIsLink) {
//				ShareUtil.setShareContent(mActivity, umImage, "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);
//				performShareMatch(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
//			} else {
//				ShareUtil.configPlatforms(mActivity);
//				// UMImage umImage = new UMImage(mActivity, file);
//				ShareUtil.shareShop(mActivity, umImage);
//				mController.postShare(mActivity, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {
//
//					@Override
//					public void onStart() {
//
//					}
//
//					@Override
//					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
//						// String showText = platform.toString();
//						if (eCode == StatusCode.ST_CODE_SUCCESSED) {// 分享完成
//							// MobclickAgent.onEvent(mActivity,
//							// "sign_sharesuccess");
//							sign();
//
//						} else {
//
//						}
//					}
//				});
//			}
//
//		}
//
//	}
//
//	public void performShareMatch(SHARE_MEDIA platform, final Intent intent) {
//		// UMImage umImage = new UMImage(mActivity, bmNew);
//		// ShareUtil.setShareContent(mActivity, umImage,
//		// "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~~", link9);
//
//		mController.postShare(mActivity, platform, new SnsPostListener() {
//
//			@Override
//			public void onStart() {
//				// chooseDialog();
//			}
//
//			@Override
//			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
//				String showText = platform.toString();
//				LogYiFu.e("showText", showText);
//
//				if (eCode == StatusCode.ST_CODE_SUCCESSED) {
//					sign();
//				} else {
//
//				}
//
//			}
//		});
//	}
//
//}
