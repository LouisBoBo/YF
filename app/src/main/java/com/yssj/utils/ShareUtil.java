package com.yssj.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.yssj.Constants;
import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.MyPopupwindow;
import com.yssj.model.ComModel2;
import com.yssj.ui.receiver.TaskReceiver;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ShareUtil {

	public final static UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);
	public SHARE_MEDIA mPlatform = SHARE_MEDIA.SINA;

	// 实现对微信好友的分享：
	public static Intent shareToWechat(File... files) {
		String imgPath = "/sdcard/share_pic.png";
		Intent intent = new Intent(Intent.ACTION_SEND);
		File file = new File(imgPath);
		if (file != null && file.exists() && file.isFile()) {
			intent.setType("image/*");
			Uri u = Uri.fromFile(file);
			intent.putExtra(Intent.EXTRA_STREAM, u);
		}
		ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
		intent.setComponent(comp);
		intent.setAction("android.intent.action.SEND");
		intent.setType("image/*");
		// intent.setFlags(0x3000001);
		intent.putExtra(Intent.EXTRA_TEXT, "固定字段");
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		// intent.putExtra(Intent.EXTRA_TEXT, "固定字段");
		// intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
		// intent.putExtra(Intent.EXTRA_STREAM, imageUris);
		// startActivity(intent);
		return intent;
	}

	/** 分享至朋友圈 */
	public static Intent shareMultiplePictureToTimeLine(File... files) {
		Intent intent = new Intent();
		ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
		intent.setComponent(comp);
		intent.setAction(Intent.ACTION_SEND_MULTIPLE);
		intent.setType("image/*");

		ArrayList<Uri> imageUris = new ArrayList<Uri>();

		if (files != null) {
			for (File f : files) {
				imageUris.add(Uri.fromFile(f));
			}
		}

		intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);

		return intent;

		// startActivityForResult(intent, 1001);
	}

	/** 分享至sina微博 */
	public static Intent shareMultiplePictureToSina(File... files) {
		Intent intent = new Intent();
		// ComponentName comp = new ComponentName("com.sina.weibo",
		// "com.sina.weibo.ComposerDispatchActivity");
		intent.setAction(Intent.ACTION_SEND_MULTIPLE);
		// ComponentName comp = new ComponentName("com.sina.weibo",
		// "com.sina.weibo.EditActivity");
		// intent.setComponent(comp);
		intent.setPackage("com.sina.weibo");
		intent.setType("image/*");

		ArrayList<Uri> imageUris = new ArrayList<Uri>();
		for (File f : files) {
			imageUris.add(Uri.fromFile(f));
		}
		intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);

		return intent;
		// startActivityForResult(intent, 1001);
	}

	/** 分享至qq空间 */
	public static Intent shareMultiplePictureToQZone(File... files) {
		Intent intent = new Intent();
		// ComponentName comp = new ComponentName("com.qzone",
		// "com.qzonex.module.operation.ui.QZonePublishMoodActivity");
		ComponentName comp = new ComponentName("com.qzone", "com.qzonex.module.operation.ui.QZonePublishMoodActivity");
		intent.setComponent(comp);
		intent.setAction(Intent.ACTION_SEND_MULTIPLE);
		intent.setType("image/*");

		ArrayList<Uri> imageUris = new ArrayList<Uri>();
		if (files != null) {
			for (File f : files) {
				imageUris.add(Uri.fromFile(f));
			}
		}

		intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
		return intent;
		// startActivityForResult(intent, 1001);
	}

	/** 分享至陌陌 */
	public static Intent shareMultiplePictureToMomo(File... files) {
		Intent intent = new Intent();
		ComponentName comp = new ComponentName("com.immomo.momo",
				"com.immomo.momo.android.activity.feed.SharePublishFeedActivity");
		intent.setComponent(comp);
		intent.setAction(Intent.ACTION_SEND_MULTIPLE);
		intent.setType("image/*");

		ArrayList<Uri> imageUris = new ArrayList<Uri>();
		for (File f : files) {
			imageUris.add(Uri.fromFile(f));
		}
		intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);

		return intent;
		// startActivityForResult(intent, 1001);
	}

	// 图片
	public static File[] getImage() {
		// File root = Environment.getExternalStorageDirectory();
		File fileDirec = new File(YConstance.savePicPath);
		if (!fileDirec.exists()) {
			fileDirec.mkdir();
		}
		File root = new File(YConstance.savePicPath);
		File[] files = root.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				if (pathname.getName().endsWith(".jpg")) {
					return true;
				}
				return false;
			}
		});
		return files;
	}

	/**
	 * 邀请好友拿出文件
	 * 
	 * @return
	 */
	public static File[] getInviteImage() {
		// File root = Environment.getExternalStorageDirectory();
		File fileDirec = new File(YConstance.saveInviteWinPath);
		if (!fileDirec.exists()) {
			fileDirec.mkdir();
		}
		File root = new File(YConstance.saveInviteWinPath);
		File[] files = root.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				if (pathname.getName().endsWith(".png")) {
					return true;
				}
				return false;
			}
		});
		return files;
	}

	// 判断是否应用安装
	public static boolean intentIsAvailable(Context context, Intent intent) {
		final PackageManager packageManager = context.getPackageManager();
		@SuppressLint("WrongConstant") List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.GET_ACTIVITIES);
		return list.size() > 0;
	}

	// 下载图片
	public static void getPicPath(final String shop_code, final View v, final FragmentActivity activity) {
		new SAsyncTask<Void, Void, HashMap<String, Object>>(activity, v, R.string.wait) {

			@Override
			protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getDetailsPic(context, shop_code);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				if (null == e) {
					String[] picList = (String[]) result.get("picList");
					LogYiFu.e("TAG", "...分享下载图片");
					// downloadPic(picList);
					download(v, picList, activity, shop_code);
				}
			}
		}.execute();
	}

	/**
	 * 配置分享平台参数</br>
	 */
	public static void configPlatforms(Context context) {
		// 添加新浪SSO授权
//		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		// 添加腾讯微博SSO授权
		// mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
		// // 添加人人网SSO授权
		// RenrenSsoHandler renrenSsoHandler = new
		// RenrenSsoHandler(getActivity(),
		// "201874", "28401c0964f04a72a14c812d6132fcef",
		// "3bf66e42db1e4fa9829b955cc300b737");
		// mController.getConfig().setSsoHandler(renrenSsoHandler);

		// 添加QQ、QZone平台
		addQQQZonePlatform(context);

		// 添加微信、微信朋友圈平台
		addWXPlatform(context);
	}

	public static void setWxShareContent(UMSocialService mController, Context context, UMImage urlImage, String content,
			String url, boolean isWxShare) {
//		// 设置朋友圈分享的内容
//		CircleShareContent circleMedia = new CircleShareContent();
//		circleMedia.setShareContent(content + "\t" + url);
//		circleMedia.setTitle(content);
//		// circleMedia.setShareMedia(urlImage);
//		// circleMedia.setShareMedia(uMusic);
//		// circleMedia.setShareMedia(video);
//		if (!isWxShare) {
//			circleMedia.setShareImage(urlImage);
//		}
//		circleMedia.setTargetUrl(url);
//		mController.setShareMedia(circleMedia);
	}

	public static void setQQShareContent(UMSocialService mController, Context context, UMImage urlImage, String content,
			String url, boolean isQzoneShare) {
//		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context, "1104724623", "VpAQVytFGidSRx6l");
//		qZoneSsoHandler.addToSocialSDK();
//
//		// mController.setShareContent(content+"\t"+url);
//
//		// 设置QQ空间分享内容
//		QZoneShareContent qzone = new QZoneShareContent();
//		qzone.setShareContent(content + "\t" + url);
//		qzone.setTargetUrl(url);
//		qzone.setTitle(content);
//		// if (!isQzoneShare) {
//		// qzone.setShareMedia(urlImage);
//		// }
//		// qzone.setShareMedia(uMusic);
//		mController.setShareMedia(qzone);
	}

	public static void setSinaShareContent(UMSocialService mController, Context context, UMImage urlImage,
			String content, String url, boolean isSinaShare) {
//		mController.getConfig().setSsoHandler(new SinaSsoHandler());

		// 新浪微博分享的内容
		SinaShareContent sinaContent = new SinaShareContent();
		sinaContent.setShareContent(content + "\t" + url);
		sinaContent.setTitle("来自于云商世纪的分享");
		if (!isSinaShare) {
			sinaContent.setShareImage(urlImage);
		}
		// sinaContent.setShareImage(urlImage);
		mController.setShareMedia(sinaContent);
	}

	public static void performShare(SHARE_MEDIA platform, final Context context) {
		mController.postShare(context, platform, new SnsPostListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
				String showText = platform.toString();
				if (eCode == StatusCode.ST_CODE_SUCCESSED) {
				} else {
					// showText += "平台分享失败";
					// Toast.makeText(context, showText, Toast.LENGTH_SHORT)
					// .show();
				}
			}
		});
	}

	/**
	 * 分享店铺
	 * 
	 * @param platform
	 * @param context
	 */
	public static void shareShopLink(SHARE_MEDIA platform, final Context context) {
		mController.postShare(context, platform, new SnsPostListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
				String showText = platform.toString();
				if (eCode == StatusCode.ST_CODE_SUCCESSED) {
					List<String> taskMap = YJApplication.instance.getTaskMap();
					if (taskMap == null) {
						taskMap = new ArrayList<String>();
					}
					int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
					int id = 16;
					int type = context.getSharedPreferences("TheFirstTask", Context.MODE_PRIVATE).getInt("type", 0);
					LogYiFu.e("每日任务type值", type + "");
					if (type == 1) {// 更换模板
						if (!taskMap.contains("19")) {
							// id = 19;
						} else if (!taskMap.contains("12") && week == 2) {
							id = 12;
						}
					} else if (type == 2) {// 发布店铺公告
						if (!taskMap.contains("22")) {
							id = 22;
						} else if (!taskMap.contains("13") && week == 3) {
							id = 13;
						}
					} else if (type == 3) {// 更换轮播图
						if (!taskMap.contains("23")) {
							id = 23;
						} else if (!taskMap.contains("14") && week == 4) {
							id = 14;
						}
					} else if (type == 4) {// 更换店主最爱
						if (!taskMap.contains("24")) {
							id = 24;
						} else if (!taskMap.contains("15") && week == 5) {
							id = 15;
						}
					} else {
						new SAsyncTask<Void, Void, Integer>((FragmentActivity) context, R.string.wait) {

							@Override
							protected boolean isHandleException() {
								// TODO Auto-generated method stub
								return true;
							}

							@Override
							protected Integer doInBackground(FragmentActivity context, Void... params)
									throws Exception {
								// TODO Auto-generated method stub
								return ComModel2.doMission(context, "16");
							}

							@Override
							protected void onPostExecute(FragmentActivity context, Integer result, Exception e) {
								super.onPostExecute(context, result, e);
								if (e == null) {
									if (result == 0) {
										context.getSharedPreferences("EverydayTaskFriday", Context.MODE_PRIVATE).edit()
												.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
												.commit();
									}
								}
							}

						}.execute();

						return;
					}
					new SAsyncTask<String, Void, Integer>((FragmentActivity) context, R.string.wait) {

						@Override
						protected boolean isHandleException() {
							// TODO Auto-generated method stub
							return true;
						}

						@Override
						protected Integer doInBackground(FragmentActivity context, String... params) throws Exception {
							// TODO Auto-generated method stub
							return ComModel2.doMission(context, params[0]);
						}

						@Override
						protected void onPostExecute(FragmentActivity context, Integer result, Exception e) {
							super.onPostExecute(context, result, e);
							if (result != null && result != 0) {
								Toast.makeText(context, "任务已完成，获得" + result + "积分！", Toast.LENGTH_SHORT).show();
							}

						}

					}.execute(id + "");

				}
			}
		});
	}

	public static void setShareContent(Context context, UMImage urlImage, String content, String url) {

//		// 配置SSO
//		mController.getConfig().setSsoHandler(new SinaSsoHandler());
//
//		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context, "1104724623", "VpAQVytFGidSRx6l");
//		qZoneSsoHandler.addToSocialSDK();
//
//		// mController.setShareContent(content+"\t"+url);
//
//		// 设置QQ空间分享内容
//		QZoneShareContent qzone = new QZoneShareContent();
//		qzone.setShareContent(content);
//		qzone.setTargetUrl(url);
//		if ("我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~~".equals(content)) {
//			qzone.setTitle("一件特卖宝贝正在等待亲爱哒打开哦");
//		} else if ("我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~".equals(content)) {
//			qzone.setTitle(StringUtils.getShareContent(YCache.getCacheUser(context).getNickname()));
//		} else if ("撩汉必备手册，全在这里>>".equals(content)) {
//			qzone.setTitle("男神喜欢你这样穿");
//		} else if ("男神喜欢你这样穿".equals(content)) {
//			qzone.setTitle("撩汉必备手册，全在这里>>");
//		} else if ("少不了这件吸睛单品".equals(content)) {
//			qzone.setTitle("一秒俘获男神");
//		} else if ("能赚钱的女装特卖神器".equals(content)) {
//			qzone.setTitle("加入衣蝠领取30元现金——衣蝠APP");
//		} else if ("买了肯定不后悔，数量不多，快来抢购吧~".equals(content)) {
//			qzone.setTitle("精品超值特卖");
//		} else if ("上衣蝠每日做小任务快速赚钱。更有万款美衣1折起哦。".equals(content)) {
//			qzone.setTitle("加入衣蝠！每月多赚600+");
//		} else {
//			qzone.setTitle("一件美衣正在等待亲爱哒打开哦");
//		}
//		qzone.setShareMedia(urlImage);
//		// qzone.setShareMedia(uMusic);
//		qzone.setTargetUrl(url);
//		mController.setShareMedia(qzone);
//
//		// 设置朋友圈分享的内容
//		CircleShareContent circleMedia = new CircleShareContent();
//		circleMedia.setShareContent(content + "\t" + url);
//		if ("我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~".equals(content)) {
//			circleMedia.setTitle(StringUtils.getShareContent(YCache.getCacheUser(context).getNickname()));
//		} else {
//
//			if (SharedPreferencesUtil.getStringData(context, "messageSubSub", "").length() != 0) {
//				circleMedia.setTitle(SharedPreferencesUtil.getStringData(context, "messageSubSub", ""));
//				SharedPreferencesUtil.saveStringData(context, "messageSubSub", "");
//			} else {
//				circleMedia.setTitle(YCache.getCacheStore(context).getS_name() + "—姐妹们的美丽小屋~");
//			}
//
//		}
//		circleMedia.setShareMedia(urlImage);
//		// circleMedia.setShareMedia(uMusic);
//		// circleMedia.setShareMedia(video);
//		circleMedia.setTargetUrl(url);
//		mController.setShareMedia(circleMedia);
//		// 新浪微博分享的内容
//		SinaShareContent sinaContent = new SinaShareContent();
//		sinaContent.setShareContent(content + "\t" + url);
//		// sinaContent.setTitle("来自于云商世纪的分享");
//		// sinaContent.setShareImage(urlImage);
//		mController.setShareMedia(sinaContent);

	}

	//搭配分享专用
	public static void setShareContentNewMatch(Context context, UMImage urlImage, String content, String url,String title) {
//
//		// 配置SSO
//		mController.getConfig().setSsoHandler(new SinaSsoHandler());
//
//		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context, "1104724623", "VpAQVytFGidSRx6l");
//		qZoneSsoHandler.addToSocialSDK();
//
//		// mController.setShareContent(content+"\t"+url);
//
//		// 设置QQ空间分享内容
//		QZoneShareContent qzone = new QZoneShareContent();
//		qzone.setShareContent(content);
//		qzone.setTargetUrl(url);
//		qzone.setTitle(title);
//		qzone.setShareMedia(urlImage);
//		// qzone.setShareMedia(uMusic);
//		qzone.setTargetUrl(url);
//		mController.setShareMedia(qzone);
//
//		// 设置朋友圈分享的内容
//		CircleShareContent circleMedia = new CircleShareContent();
//		circleMedia.setShareContent(content + "\t" + url);
//
//		circleMedia.setTitle(title);
//
//		circleMedia.setShareMedia(urlImage);
//		// circleMedia.setShareMedia(uMusic);
//		// circleMedia.setShareMedia(video);
//		circleMedia.setTargetUrl(url);
//		mController.setShareMedia(circleMedia);
//		// 新浪微博分享的内容
//		SinaShareContent sinaContent = new SinaShareContent();
//		sinaContent.setShareContent(content + "\t" + url);
//		// sinaContent.setTitle("来自于云商世纪的分享");
//		// sinaContent.setShareImage(urlImage);
//		mController.setShareMedia(sinaContent);

	}
	// 密友圈专用
	public static void setShareContentFriend(Context context, UMImage urlImage, String content, String url,
			String title) {
//
//		// 配置SSO
//		mController.getConfig().setSsoHandler(new SinaSsoHandler());
//
//		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context, "1104724623", "VpAQVytFGidSRx6l");
//		qZoneSsoHandler.addToSocialSDK();
//
//		// mController.setShareContent(content+"\t"+url);
//
//		// 设置QQ空间分享内容
//		QZoneShareContent qzone = new QZoneShareContent();
//		if (content.equals("")) {
//			content = " ";
//		}
//		qzone.setShareContent(content);
//		qzone.setTargetUrl(url);
//		qzone.setShareMedia(urlImage);
//		// qzone.setShareMedia(uMusic);
//		qzone.setTargetUrl(url);
//		mController.setShareMedia(qzone);
//
//		// 设置朋友圈分享的内容
//		CircleShareContent circleMedia = new CircleShareContent();
//		circleMedia.setShareContent(content + "\t" + url);
//		if ("我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~".equals(content)) {
//			circleMedia.setTitle(StringUtils.getShareContent(YCache.getCacheUser(context).getNickname()));
//		} else {
//
//			if (SharedPreferencesUtil.getStringData(context, "messageSubSub", "").length() != 0) {
//				circleMedia.setTitle(SharedPreferencesUtil.getStringData(context, "messageSubSub", ""));
//				SharedPreferencesUtil.saveStringData(context, "messageSubSub", "");
//			} else {
//				circleMedia.setTitle(YCache.getCacheStore(context).getS_name() + "—姐妹们的美丽小屋~");
//			}
//
//		}
//		circleMedia.setShareMedia(urlImage);
//		// circleMedia.setShareMedia(uMusic);
//		// circleMedia.setShareMedia(video);
//		circleMedia.setTargetUrl(url);
//		mController.setShareMedia(circleMedia);
//		// 新浪微博分享的内容
//		SinaShareContent sinaContent = new SinaShareContent();
//		sinaContent.setShareContent(content + "\t" + url);
//		// sinaContent.setTitle("来自于云商世纪的分享");
//		// sinaContent.setShareImage(urlImage);
//		mController.setShareMedia(sinaContent);

	}

	public static void setShareContentBaoYou(Context context, UMImage urlImage, String content, String url,
			int signType, int isDuoBao) {// isDuoBao 1 夺宝 0包邮

//		// 配置SSO
//		mController.getConfig().setSsoHandler(new SinaSsoHandler());
//
//		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context, "1104724623", "VpAQVytFGidSRx6l");
//		qZoneSsoHandler.addToSocialSDK();
//
//		// mController.setShareContent(content+"\t"+url);
//
//		// 设置QQ空间分享内容
//		QZoneShareContent qzone = new QZoneShareContent();
//		qzone.setShareContent(content);
//		qzone.setTargetUrl(url);
//		if (isDuoBao == 0) {
//			qzone.setTitle("我正在参与" + signType + "元包邮活动");
//		} else {
//			qzone.setTitle("我正在参与" + signType + "元夺宝活动");
//		}
//		qzone.setShareMedia(urlImage);
//		// qzone.setShareMedia(uMusic);
//		qzone.setTargetUrl(url);
//		mController.setShareMedia(qzone);
//
//		// 设置朋友圈分享的内容
//		CircleShareContent circleMedia = new CircleShareContent();
//		circleMedia.setShareContent(content + "\t" + url);
//		circleMedia.setTitle(YCache.getCacheStore(context).getS_name() + "—姐妹们的美丽小屋~");
//		circleMedia.setShareMedia(urlImage);
//		// circleMedia.setShareMedia(uMusic);
//		// circleMedia.setShareMedia(video);
//		circleMedia.setTargetUrl(url);
//		mController.setShareMedia(circleMedia);
//		// 新浪微博分享的内容
//		SinaShareContent sinaContent = new SinaShareContent();
//		sinaContent.setShareContent(content + "\t" + url);
//		sinaContent.setTitle("来自于云商世纪的分享");
//		// sinaContent.setShareImage(urlImage);
//		mController.setShareMedia(sinaContent);

	}

	public static void shareRedPackets(final Context mContext, String url// 分享红包到微信好友
			, String name, String word) {
//		UMImage umImage = new UMImage(mContext, R.drawable.red_packet2);
//		addWXPlatform(mContext);
//		CircleShareContent circleMedia = new CircleShareContent();
//		circleMedia.setTitle(name);
//		circleMedia.setShareMedia(umImage);
//		// circleMedia.setShareMedia(uMusic);
//		// circleMedia.setShareMedia(video);
//		mController.setShareMedia(circleMedia);
//		WeiXinShareContent wei = new WeiXinShareContent();
//		wei.setTargetUrl(url);
//		wei.setShareContent(word);
//		wei.setTitle(name);
//		wei.setShareMedia(umImage);
//		mController.setShareMedia(wei);
//		mController.postShare(mContext, SHARE_MEDIA.WEIXIN, new SnsPostListener() {
//
//			@Override
//			public void onStart() {
//
//			}
//
//			@Override
//			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
//				if (eCode == StatusCode.ST_CODE_SUCCESSED) {
//					// showText += "平台分享成功";
//					// Toast.makeText(mContext, showText,
//					// Toast.LENGTH_SHORT)
//					// .show();
//					// 调接口~发红包
//					ToastUtil.showShortText(mContext, "分享成功");
//				} else {
//					Toast.makeText(mContext, "分享失败", Toast.LENGTH_SHORT).show();
//				}
//			}
//		});
	}

	public static void shareRedPacketsCircle(final Context mContext, String url// 分享红包到微信朋友圈
			, String name, String word) {
		UMImage umImage = new UMImage(mContext, R.drawable.red_packet2);
//		addWXPlatform(mContext);
//
//		CircleShareContent circleMedia = new CircleShareContent();
//		circleMedia.setShareContent(word);
//		circleMedia.setTitle(word);
//		circleMedia.setShareMedia(umImage);
//		circleMedia.setTargetUrl(url);
//		mController.setShareMedia(circleMedia);
//
//		mController.postShare(mContext, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {
//
//			@Override
//			public void onStart() {
//
//			}
//
//			@Override
//			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
//				if (eCode == StatusCode.ST_CODE_SUCCESSED) {
//					// showText += "平台分享成功";
//					// Toast.makeText(mContext, showText,
//					// Toast.LENGTH_SHORT)
//					// .show();
//					// 调接口~发红包
//					ToastUtil.showShortText(mContext, "分享成功");
//				} else {
//					Toast.makeText(mContext, "分享失败", Toast.LENGTH_SHORT).show();
//				}
//			}
//		});
	}

	public static void sharePic(final Context mContext, Bitmap bm, boolean isFriend) {
//		UMImage umImage = new UMImage(mContext, bm);
//		addWXPlatform(mContext);
//		CircleShareContent circleMedia = new CircleShareContent();
//		circleMedia.setTitle("来自于云商世纪的分享");
//		circleMedia.setShareMedia(umImage);
//		// circleMedia.setShareMedia(uMusic);
//		// circleMedia.setShareMedia(video);
//		mController.setShareMedia(circleMedia);
//		WeiXinShareContent wei = new WeiXinShareContent();
//		wei.setTitle("来自于云商世纪的分享");
//		wei.setShareMedia(umImage);
//		mController.setShareMedia(wei);
//		if (isFriend) {
//			mController.postShare(mContext, SHARE_MEDIA.WEIXIN, new SnsPostListener() {
//
//				@Override
//				public void onStart() {
//
//				}
//
//				@Override
//				public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
//					if (eCode == StatusCode.ST_CODE_SUCCESSED) {
//						// showText += "平台分享成功";
//						// Toast.makeText(mContext, showText,
//						// Toast.LENGTH_SHORT)
//						// .show();
//						// 调接口~发红包
//						// ToastUtil.showShortText(mContext, "分享成功");
//					} else {
//						Toast.makeText(mContext, "分享失败", Toast.LENGTH_SHORT).show();
//					}
//				}
//			});
//		} else {
//			mController.postShare(mContext, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {
//
//				@Override
//				public void onStart() {
//
//				}
//
//				@Override
//				public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
//					if (eCode == StatusCode.ST_CODE_SUCCESSED) {
//						// showText += "平台分享成功";
//						// Toast.makeText(mContext, showText,
//						// Toast.LENGTH_SHORT)
//						// .show();
//						// 调接口~发红包
//						// ToastUtil.showShortText(mContext, "分享成功");
//					} else {
//						Toast.makeText(mContext, "分享失败", Toast.LENGTH_SHORT).show();
//					}
//				}
//			});
//		}

	}

	public static void shareMainMenu(final Context mContext, File file) {

		if (file == null) {
			Toast.makeText(mContext, "您的网络状态不太好哦~~", Toast.LENGTH_SHORT).show();
			return;
		}

		UMImage umImage = new UMImage(mContext, file);
		ShareUtil.configPlatforms(mContext);
		ShareUtil.shareShop(mContext, umImage);

		mController.postShare(mContext, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
				String showText = platform.toString();
				if (eCode == StatusCode.ST_CODE_SUCCESSED) {
					// showText += "平台分享成功";
					// Toast.makeText(mContext, showText,
					// Toast.LENGTH_SHORT)
					// .show();
					// 调接口~发红包
					new SAsyncTask<Void, Void, Integer>((FragmentActivity) mContext, R.string.wait) {

						@Override
						protected boolean isHandleException() {
							// TODO Auto-generated method stub
							return true;
						}

						@Override
						protected Integer doInBackground(FragmentActivity context, Void... params) throws Exception {
							// TODO Auto-generated method stub
							return ComModel2.checkIsShow(context);
						}

						@Override
						protected void onPostExecute(FragmentActivity context, Integer result, Exception e) {
							// TODO Auto-generated method stub
							super.onPostExecute(context, result, e);
							if (null == e) {
								if (result == 1) {
									Toast.makeText(mContext, "获取红包成功", Toast.LENGTH_SHORT).show();
								}
							}
						}

					}.execute();

				} else {
					showText += "平台分享失败";
					Toast.makeText(mContext, showText, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public static void shareH5(final Context mContext, File file) {

		if (file == null) {
			Toast.makeText(mContext, "您的网络状态不太好哦~~", Toast.LENGTH_SHORT).show();
			return;
		}

		UMImage umImage = new UMImage(mContext, file);
		ShareUtil.configPlatforms(mContext);
		ShareUtil.shareShop(mContext, umImage);

		mController.postShare(mContext, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
				String showText = platform.toString();
				if (eCode == StatusCode.ST_CODE_SUCCESSED) {
					// showText += "平台分享成功";
					// Toast.makeText(mContext, showText,
					// Toast.LENGTH_SHORT)
					// .show();

					// 调接口~发红包
					new SAsyncTask<Void, Void, Integer>((FragmentActivity) mContext, R.string.wait) {

						@Override
						protected boolean isHandleException() {
							// TODO Auto-generated method stub
							return true;
						}

						@Override
						protected Integer doInBackground(FragmentActivity context, Void... params) throws Exception {
							// TODO Auto-generated method stub
							return ComModel2.h5GetCoupon(context);
						}

						@Override
						protected void onPostExecute(FragmentActivity context, Integer result, Exception e) {
							// TODO Auto-generated method stub
							super.onPostExecute(context, result, e);
							if (null == e) {
								if (result == 1) {
									Toast.makeText(mContext, "获取红包成功", Toast.LENGTH_SHORT).show();
									ComModel2.flag = 1;
								}
							}
						}

					}.execute();
					mContext.sendBroadcast(new Intent(TaskReceiver.newMemberTask_3));

				} else {
					showText += "平台分享失败";
					Toast.makeText(mContext, showText, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	// // 去收集补签卡
	// public static void shareGetSignBuQianKa(Context context) {
	// UMImage umImage = new UMImage(context, R.drawable.ic_launcher);
	//
	// addWXPlatform(context);
	// // CircleShareContent circleMedia = new CircleShareContent();
	// // circleMedia.setTitle("即刻到衣蝠领取现金奖励！");
	// // circleMedia.setShareMedia(umImage);
	// // circleMedia.setShareMedia(uMusic);
	// // circleMedia.setShareMedia(video);
	// // mController.setShareMedia(circleMedia);
	// WeiXinShareContent wei = new WeiXinShareContent();
	// wei.setTargetUrl(YUrl.YSS_URL_ANDROID_H5 +
	// "/view/yifuapp10.html"+"?realm="+YCache.getCacheStore(context).getRealm());
	// wei.setShareContent("天天签到，分享赢壕礼！");
	// wei.setTitle("即刻到衣蝠领取现金奖励！");
	// wei.setShareMedia(umImage);
	// mController.setShareMedia(wei);
	//
	// mController.postShare(context, SHARE_MEDIA.WEIXIN,
	// new SnsPostListener() {
	//
	// @Override
	// public void onStart() {
	//
	// }
	//
	// @Override
	// public void onComplete(SHARE_MEDIA platform, int eCode,
	// SocializeEntity entity) {
	// String showText = platform.toString();
	// if (eCode == StatusCode.ST_CODE_SUCCESSED) {// 分享完成
	//
	// } else {
	// /*
	// * showText += "平台分享失败";
	// * Toast.makeText(MealSubmitOrderActivity.this,
	// * showText, Toast.LENGTH_SHORT).show();
	// */
	// }
	// }
	// });
	// }

	public static void shareShop(Context context, UMImage urlImage) {
		// 配置SSO
//		mController.getConfig().setSsoHandler(new SinaSsoHandler());
//
//		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context, "1104724623", "VpAQVytFGidSRx6l");
//		qZoneSsoHandler.addToSocialSDK();
//
//		// mController.setShareContent(content+"\t"+url);
//
//		// 设置QQ空间分享内容
//		QZoneShareContent qzone = new QZoneShareContent();
//		qzone.setTitle("来自于云商世纪的分享");
//		qzone.setShareMedia(urlImage);
//		// qzone.setShareMedia(uMusic);
//		mController.setShareMedia(qzone);
//
//		// 设置朋友圈分享的内容
//		CircleShareContent circleMedia = new CircleShareContent();
//		circleMedia.setTitle("来自于云商世纪的分享");
//		circleMedia.setShareMedia(urlImage);
//		// circleMedia.setShareMedia(uMusic);
//		// circleMedia.setShareMedia(video);
//		mController.setShareMedia(circleMedia);
//
//		// 微信分享
//		WeiXinShareContent wei = new WeiXinShareContent();
//
//		wei.setTitle("来自于云商世纪的分享");
//		wei.setShareMedia(urlImage);
//		mController.setShareMedia(wei);
//
//		// 新浪微博分享的内容
//		SinaShareContent sinaContent = new SinaShareContent();
//		sinaContent.setTitle("来自于云商世纪的分享");
//		sinaContent.setShareImage(urlImage);
//		mController.setShareMedia(sinaContent);
	}

	public static void share(Context context, double kickBack) {
		MyPopupwindow myPopupwindow = new MyPopupwindow((Activity) context, kickBack, null, null);
		myPopupwindow.showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
	}

	public static void addQQQZonePlatform(Context context) {
		String appId = "1104724623";
		String appKey = "VpAQVytFGidSRx6l";
		// 添加QQ支持, 并且设置QQ分享内容的target url
//		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) context, appId, appKey);
//		// qqSsoHandler.setTargetUrl("http://www.umeng.com/social");
//		qqSsoHandler.addToSocialSDK();
//
//		// 添加QZone平台
//		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context, appId, appKey);
//		qZoneSsoHandler.addToSocialSDK();
	}

	/**
	 * @param context
	 * @功能描述 : 添加微信平台分享
	 * @return
	 */
	public static void addWXPlatform(Context context) {
		// 注意：在微信授权的时候，必须传递appSecret
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		// String appId = "wx967daebe835fbeac";
		// String appSecret = "5bb696d9ccd75a38c8a0bfe0675559b3";

//
//
//
//		if (null == MainMenuActivity.APP_ID || "".equals(MainMenuActivity.APP_ID)) {
//			//是否是测试环境
//			boolean flag = SharedPreferencesUtil.getBooleanData(YJApplication.instance, "change_change", true);
//			if (flag) {
//				MainMenuActivity.APP_ID = "wxbb9728502635a425";
//			} else {
//				MainMenuActivity.APP_ID = "wx8c5fe3e40669c535";
//			}
//		}
//
//
//		if (null == MainMenuActivity.APP_SECRET || "".equals(MainMenuActivity.APP_SECRET)) {
//			//是否是测试环境
//			boolean flag = SharedPreferencesUtil.getBooleanData(YJApplication.instance, "change_change", true);
//			if (flag) {
//				MainMenuActivity.APP_SECRET = "d4624c36b6795d1d99dcf0547af5443d";
//			} else {
//				MainMenuActivity.APP_SECRET = "10d080a714d768427242e9b091d33959";
//			}
//		}
//
//
//		if (null == WxPayUtil.APP_ID || WxPayUtil.APP_ID.equals("")) {
//			ToastUtil.showShortText(context, "微信APPID不存在");
//
//			try {
//				ShopDetailsActivity.shareWaitDialog.dismiss();
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			// try {
//			// ShopDetailsIndianaActivity.shareWaitDialog.dismiss();
//			// } catch (Exception e) {
//			// e.printStackTrace();
//			// }
//
//			try {
//				SignShareShopDialog.shareWaitDialog.dismiss();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			try {
//				MatchSharePopupwindow.shareWaitDialog.dismiss();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			return;
//		} else {
//
//			// 添加微信平台
//			UMWXHandler wxHandler = new UMWXHandler(context, WxPayUtil.APP_ID, WxPayUtil.APP_SECRET);
//			wxHandler.addToSocialSDK();
//
//			// 支持微信朋友圈
//			UMWXHandler wxCircleHandler = new UMWXHandler(context, WxPayUtil.APP_ID, WxPayUtil.APP_SECRET);
//			wxCircleHandler.setToCircle(true);
//			wxCircleHandler.addToSocialSDK();
//		}
	}

	public static void download(View v, final String[] picList, final FragmentActivity activity,
			final String shop_code) {
		new SAsyncTask<Void, Void, Void>(activity, v, R.string.wait) {

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				File fileDi = new File(YConstance.savePicPath);
				if (!fileDi.exists()) {
					fileDi.mkdir();
				}
				File[] listFiles = fileDi.listFiles();
				if (listFiles.length != 0) {
					for (File file : listFiles) {
						file.delete();
					}
				}
				for (int i = 0; i < picList.length; i++) {
					if (i == 8) {
						ComModel2.saveQRCode(activity, shop_code);
						break;
					}
					downloadPic(picList[i], i);
				}
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result) {
				// TODO Auto-generated method stub
				// showShareDialog();
				super.onPostExecute(context, result);
			}

		}.execute();
	}

	public static void downloadPic(String picPath, int i) {
		try {
			URL url = new URL(YUrl.imgurl + picPath);
			// 打开连接
			URLConnection con = url.openConnection();
			// 获得文件的长度
			int contentLength = con.getContentLength();
			// System.out.println("长度 :" + contentLength);
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[8192];// 8k
			// 读取到的数据长度
			int len;
			File fileDirec = new File(YConstance.savePicPath);
			if (!fileDirec.exists()) {
				fileDirec.mkdir();
			}
			// 输出的文件流 /sdcard/yssj/
			File file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(i)) + ".jpg");
			if (file.exists()) {
				file.delete();
			}
			OutputStream os = new FileOutputStream(file);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);

			}
			// 完毕，关闭所有链接
			os.close();
			is.close();
		} catch (Exception e) {
			LogYiFu.e("TAG", "下载失败");
			e.printStackTrace();
		}
	}


	public static void setShareNewTitleContent(Context context, UMImage urlImage, String content, String url,String newTitle) {

//		// 配置SSO
//		mController.getConfig().setSsoHandler(new SinaSsoHandler());
//
//		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context, "1104724623", "VpAQVytFGidSRx6l");
//		qZoneSsoHandler.addToSocialSDK();
//
//		// mController.setShareContent(content+"\t"+url);
//
//		// 设置QQ空间分享内容
//		QZoneShareContent qzone = new QZoneShareContent();
//		qzone.setShareContent(content);
//		qzone.setTargetUrl(url);
//		qzone.setTitle(StringUtils.getShareContent(YCache.getCacheUser(context).getNickname()));
//		qzone.setShareMedia(urlImage);
//		// qzone.setShareMedia(uMusic);
//		qzone.setTargetUrl(url);
//		mController.setShareMedia(qzone);
//
//		// 设置朋友圈分享的内容
//		CircleShareContent circleMedia = new CircleShareContent();
//		circleMedia.setShareContent(content + "\t" + url);
////		if ("我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~".equals(content)) {
//		circleMedia.setTitle(""+newTitle);
////		}
//		circleMedia.setShareMedia(urlImage);
//		circleMedia.setTargetUrl(url);
//		mController.setShareMedia(circleMedia);
//		// 新浪微博分享的内容
//		SinaShareContent sinaContent = new SinaShareContent();
//		sinaContent.setShareContent(content + "\t" + url);
//		// sinaContent.setTitle("来自于云商世纪的分享");
//		// sinaContent.setShareImage(urlImage);
//		mController.setShareMedia(sinaContent);

	}
}
