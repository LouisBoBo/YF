package com.yssj.custom.view;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.Animator.AnimatorListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.DeviceConfig;
import com.yssj.Constants;
import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.MatchShop.CollocationShop;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelZ;
import com.yssj.ui.activity.shopdetails.NoShareActivity;
import com.yssj.ui.dialog.PublicToastDialog;
import com.yssj.ui.fragment.MakeMoneySecret;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;

public class MatchSharePopupwindow extends PopupWindow implements OnClickListener {
	private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);

	private int where = 0; // 1:微信 2：朋友圈 3：QQ空间
	private Activity mActivity;

	private Boolean isQQShare;
	private Boolean isWeChat;
	private ImageView iv_img;
	private RelativeLayout rl_comm_back_money;
	private LinearLayout wenzi;
	public static PublicToastDialog shareWaitDialog = null;
	private Intent qqShareIntent /*
									 * = ShareUtil.shareMultiplePictureToQZone(
									 * ShareUtil.getImage())
									 */;
	// 实现对微信好友的分享：
	private Intent weixinShareIntent /*
										 * = ShareUtil.shareToWechat(ShareUtil
										 * .getImage())
										 */;
	// 实现对微信朋友圈的分享：
	private Intent wXinShareIntent/*
									 * = ShareUtil
									 * .shareMultiplePictureToTimeLine(ShareUtil
									 * .getImage())
									 */;
	private TextView tv_cancal;
	// private TextView tv_title;
	private String code;
	// private double price;
	// private boolean isHaveQQZone;

	private AlphaAnimation alphaAnimation;
	private TextView img_why;
	private String systime_now;
	private RelativeLayout rl_fw;
	private double feedBack;
	private List<CollocationShop> mCollocationShopsList;
	private String mCollocationName;
	private String shareMiniMatchPic; //小程序分享专用


	public MatchSharePopupwindow(Activity activity, String code, String collocationName, double feedBack,
			List<CollocationShop> collocationShopsList) {//
		super(activity);
		this.mActivity = activity;
		this.code = code;
		this.mCollocationName = collocationName;
		this.feedBack = feedBack;
		this.mCollocationShopsList = collocationShopsList;
		initView(activity);
	}

	private boolean mQqInstallFlag = true;// true代表安装了qq
	private boolean mWxInstallFlag = true;// true代表安装了微信
	private boolean mQqZone = true;// true代表安装了QQ空间

	@SuppressLint("ResourceAsColor")
	@SuppressWarnings("deprecation")
	private void initView(Context context) {
		View rootView = LayoutInflater.from(context).inflate(R.layout.share_popupwindow_sign, null);

		try {
			// 是否安装了QQ
			if (DeviceConfig.isAppInstalled("com.tencent.mobileqq", context)) {
				mQqInstallFlag = true;
			} else {
				mQqInstallFlag = false;
			}
		} catch (Exception e) {
		}

		try {
			// // 是否安装了微信
			if (WXcheckUtil.isWeChatAppInstalled(context)) {
				mWxInstallFlag = true;
			} else {
				mWxInstallFlag = false;
			}
		} catch (Exception e) {
		}
		try {
			// // 是否安装了QQ空间客户端
			if (DeviceConfig.isAppInstalled("com.tencent.sc.activity.SplashActivity", context)) {
				mQqZone = true;
			} else {
				mQqZone = false;
			}
		} catch (Exception e) {
		}

		
		
		
		rootView.findViewById(R.id.ll_wxin).setVisibility(View.VISIBLE);
		rootView.findViewById(R.id.ll_wxin).setOnClickListener(this);
		rootView.findViewById(R.id.ll_qq).setOnClickListener(this);
		rootView.findViewById(R.id.ll_wxin_circle).setOnClickListener(this);
		tv_cancal = (TextView) rootView.findViewById(R.id.tv_cancal);
		iv_img = (ImageView) rootView.findViewById(R.id.iv_img);
		rl_comm_back_money = (RelativeLayout) rootView.findViewById(R.id.rl_comm_back_money);
		tv_cancal.setOnClickListener(this);
		wenzi = (LinearLayout) rootView.findViewById(R.id.wenzi);
		wenzi.setBackgroundColor(Color.WHITE);
		TextView tv_kickback = (TextView) rootView.findViewById(R.id.kick_back);
		rl_fw = (RelativeLayout) rootView.findViewById(R.id.rl_fw);
		rl_fw.setOnClickListener(this);
		img_why = (TextView) rootView.findViewById(R.id.img_why);

//		if (feedBack == 0) {
//			rl_comm_back_money.setVisibility(View.INVISIBLE);
//		} else {
//			tv_kickback.setText(feedBack + "");
//			tv_kickback.setVisibility(View.VISIBLE);
//			rl_comm_back_money.setVisibility(View.VISIBLE);
//		}
		//改为固定显示分享赚15元
		tv_kickback.setVisibility(View.VISIBLE);
		rl_comm_back_money.setVisibility(View.VISIBLE);
		
		getStartPic();
		// tv_title = (TextView) rootView.findViewById(R.id.tv_title);
		shareWaitDialog = new PublicToastDialog(mActivity, R.style.DialogStyle1, "");
		// tv_title.setText("美是用来分享哒~分享最爱的美衣给好友");

		// qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil
		// .getImage());
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
		//
		// qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil
		// .getImage());

		// iv_img.setVisibility(View.GONE);
		// rl_comm_back_money.setVisibility(View.VISIBLE);
		// getStartPic();
		//
		// String[] article = { "1", "2", "3", "4", "5" };
		// Random rands = new Random();
		//
		// int rand1 = Integer.parseInt(article[rands.nextInt(5)]);
		//
		//
		// switch (rand1) {
		// case 1 :
		// iv_img.setImageResource(R.drawable.putongfengxiang1);
		// break;
		// case 2 :
		// iv_img.setImageResource(R.drawable.putongfengxiang2);
		// break;
		// case 3 :
		// iv_img.setImageResource(R.drawable.putongfengxiang3);
		// break;
		// case 4 :
		// iv_img.setImageResource(R.drawable.putongfengxiang4);
		// break;
		// case 5 :
		// iv_img.setImageResource(R.drawable.putongfengxiang5);
		// break;
		//
		// default:
		// break;
		// }

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		systime_now = formatter.format(curDate);
		String time_pass = SharedPreferencesUtil.getStringData(mActivity, "first_wen", "-1");

		// img_why.setOnClickListener(this);

		alphaAnimation = new AlphaAnimation(1f, 0.3f);
		alphaAnimation.setDuration(550);

		final ObjectAnimator alphaAnimation2 = ObjectAnimator.ofFloat(img_why, "alpha", 0.1f, 0.2f, 0.3f, 0.4f, 0.5f,
				0.6f, 0.7f, 0.8f, 0.9f, 1.0f);
		alphaAnimation2.setDuration(550);

		final AlphaAnimation alphaAnimation3 = new AlphaAnimation(1f, 0.3f);
		alphaAnimation3.setDuration(550);

		final ObjectAnimator alphaAnimation4 = ObjectAnimator.ofFloat(img_why, "alpha", 0.1f, 0.2f, 0.3f, 0.4f, 0.5f,
				0.6f, 0.7f, 0.8f, 0.9f, 1.0f);
		alphaAnimation4.setDuration(550);

		final ObjectAnimator alphaAnimation5 = ObjectAnimator.ofFloat(img_why, "alpha", 1.0f, 0.9f, 0.8f, 0.7f, 0.6f,
				0.5f, 0.4f, 0.3f, 0.2f, 0.1f);
		alphaAnimation5.setDuration(550);

		final ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(250);

		final ScaleAnimation scaleAnimation1 = new ScaleAnimation(0.9f, 1.1f, 0.9f, 1.1f, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation1.setDuration(250);

		final ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation2.setDuration(250);

		alphaAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub

				img_why.setCompoundDrawablesWithIntrinsicBounds(null, null,
						mActivity.getResources().getDrawable(R.drawable.wen_red), null);

				alphaAnimation2.start();

			}
		});

		alphaAnimation2.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				// tab_three.startAnimation(alphaAnimation3);
				img_why.startAnimation(scaleAnimation);
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});

		alphaAnimation3.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				// tab_three.setCompoundDrawablesWithIntrinsicBounds (null,
				// getResources().getDrawable(R.drawable.sign_gray), null,
				// null);
				img_why.startAnimation(scaleAnimation);
			}
		});

		scaleAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				// tab_three.setCompoundDrawablesWithIntrinsicBounds (null,
				// getResources().getDrawable(R.drawable.sign_gray), null,
				// null);
				img_why.startAnimation(scaleAnimation1);

			}
		});

		scaleAnimation1.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				img_why.startAnimation(scaleAnimation2);
			}
		});

		scaleAnimation2.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				// tab_three.setCompoundDrawablesWithIntrinsicBounds (null,
				// getResources().getDrawable(R.drawable.sign_gray), null,
				// null);
				alphaAnimation5.start();// 红色变浅
			}
		});

		alphaAnimation5.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub

				img_why.setCompoundDrawablesWithIntrinsicBounds(null, null,
						mActivity.getResources().getDrawable(R.drawable.wen_gray), null);
				alphaAnimation4.start();
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});

		alphaAnimation4.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				img_why.startAnimation(alphaAnimation);
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});

		// tab_three.setBackgroundResource(R.drawable.sign_red);

		if (!time_pass.equals(systime_now)) {
			img_why.startAnimation(alphaAnimation);
		}

	}

	@Override
	public void onClick(View v) {

		// share();

		int id = v.getId();
		switch (id) {
		case R.id.iv_qq_share:
			break;
		case R.id.ll_wxin:// 分享到微信好友

			if (!mWxInstallFlag) {
				ToastUtil.showShortText(mActivity, "还未安装微信哦~");
				return;
			}

			where = 1;

			isQQShare = false;
			isWeChat = true;
			// if (ShareUtil.intentIsAvailable(mActivity, weixinShareIntent)) {
			// ToastUtil.showShortText(mActivity, "分享加载中，稍等哟~");
			// 生成图片
			ShareUtil.addWXPlatform(mActivity);
			CreatePic();
			// } else {
			// ToastUtil.showShortText(mActivity, "您还没有安装微信哦~");
			// }

			dismiss();
			break;
		case R.id.ll_qq: // 分享到QQ空间

			if (!mQqInstallFlag && !mQqZone) {
				ToastUtil.showShortText(mActivity, "您还未安装QQ或QQ空间哦~");
				return;
			}

			where = 3;
			isQQShare = true;
			isWeChat = false;
			// if(ShareUtil.intentIsAvailable(mActivity, qqShareIntent)){
			// //是否安装的QQ空间客户端
			// // 生成图片
			// isHaveQQZone = true;
			// ShareUtil.addQQQZonePlatform(mActivity);
			// CreatePic();
			// }else{
			// isHaveQQZone = false;
			// ToastUtil.showShortText(mActivity, "分享加载中，稍等哟~");
			// 生成图片
			ShareUtil.addQQQZonePlatform(mActivity);
			CreatePic();
			// }
			dismiss();

			break;
		case R.id.ll_wxin_circle: // 分享到朋友圈
			if (!mWxInstallFlag) {
				ToastUtil.showShortText(mActivity, "还未安装微信哦~");
				return;
			}
			where = 2;
			isQQShare = false;
			isWeChat = false;
			// if (ShareUtil.intentIsAvailable(mActivity, wXinShareIntent)) {
			// ToastUtil.showShortText(mActivity, "分享加载中，稍等哟~");
			// 配置微信分享
			ShareUtil.addWXPlatform(mActivity);
			CreatePic();
			// } else {
			// ToastUtil.showShortText(mActivity, "您还没有安装微信哦~");
			// }

			dismiss();
			break;

		case R.id.tv_cancal:
			dismiss();
			break;
		case R.id.rl_fw:
			SharedPreferencesUtil.saveStringData(mActivity, "first_wen", systime_now);
			img_why.clearAnimation();
			Intent intent1 = new Intent(mActivity, MakeMoneySecret.class);
			mActivity.startActivity(intent1);
			break;
		default:
			break;
		}
	}

	/**
	 * 分享到微信好友
	 */
	private void shareToWeChat(String link, UMImage umImage,String title,String content) {



		//改为小程序
		String wxMiniPath = "/pages/shouye/detail/collocationDetail/collocationDetail?code=" + code +
				"&isShareFlag=true&user_id=" + YCache.getCacheUser(mActivity).getUser_id();

		//统一分享到微信小程序
		WXminiAPPShareUtil.shareToWXminiAPP(mActivity, shareMiniMatchPic+"!280", title, wxMiniPath,false);




//
//		WeiXinShareContent wei = new WeiXinShareContent();
//		// wei.setTitle("一件美衣正在等待亲爱哒打开哦");
//		wei.setShareContent(content);
//		// wei.setTitle("男神喜欢你这样穿");
//		wei.setTitle(title);
////		wei.setTitle(StringUtils.getShareContent(YCache.getCacheUser(mActivity).getNickname()));
//		// wei.setShareContent("撩汉必备手册，全在这里>>");
//		wei.setTargetUrl(link);
//		wei.setShareMedia(umImage);
//		mController.setShareMedia(wei);
//		performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);
	}

	private void createSharePic(final String link, final String picPath, final String collocationName, final View v) {
		new SAsyncTask<Void, Void, Void>((FragmentActivity) mActivity, R.string.wait) {

			@Override
			protected boolean isHandleException() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected Void doInBackground(FragmentActivity context, Void... params) throws Exception {
				// TODO Auto-generated method stub

				bmBg = downloadPic(picPath);
				Bitmap bmQr =
						// bmBg.getWidth()==bmBg.getHeight()?
						QRCreateUtil.createQrImage(link, 160, 160);
				// :QRCreateUtil.createQrImage(link, 135, 135);// 得到二维码图片
				// if(bmBg.getWidth()==bmBg.getHeight()){
				// 搭配获取到分享单宫图 图片宽高相等的情况
				bmNew = QRCreateUtil.drawNewBitmapMatch2(context, bmBg, bmQr, collocationName, mCollocationShopsList);
				// }
				// else{
				// //搭配获取到分享单宫图 图片600*900的情况
				// bmNew = QRCreateUtil.drawNewBitmapMatch(context, bmBg, bmQr,
				// collocationName);
				// }
				LogYiFu.e("WD", bmNew.getWidth() + "");
				LogYiFu.e("HG", bmNew.getHeight() + "");

				boolean WxCircleFlag = SharedPreferencesUtil.getBooleanData(mActivity, Pref.RECORD_WXCIRCLE, false);
				if (isWeChat || isQQShare || WxCircleFlag) {
					QRCreateUtil.saveBitmap(bmBg, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存图片
																													// 不加二维码图片不加文字
				} else {
					QRCreateUtil.saveBitmap(bmNew, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
				}

				return super.doInBackground(context, params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				if (null == e) {
					file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");
					// share(file); 创建图片完毕后分享到微信

//					share(file, link);
					getTyepe2SuppLabel(mCollocationShopsList.get(0).getShop_code(),file, link);//取出第1个商品相关信息

				} else {
					if (null != shareWaitDialog) {
						shareWaitDialog.dismiss();
					}
				}
			}

		}.execute();
	}

	private Bitmap bmNew;
	private File file;

	private Bitmap downloadPic(String picPath) {
		try {
			URL url = new URL(YUrl.imgurl + picPath);
			// https://yssj-real-test.b0.upaiyun.com/collocationShop/2016-07-13/Pg3holtx.jpg
			// 打开连接
			URLConnection con = url.openConnection();
			// 获得文件的长度
			int contentLength = con.getContentLength();
			// System.out.println("长度 :" + contentLength);
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

	private void CreatePic() {

		shareWaitDialog.show();

		// 分享单宫图

		new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mActivity, R.string.wait) {

			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
					throws Exception {
				// TODO Auto-generated method stub
				// System.out.println("？？？？？？？="+code);
				return ComModel2.getMatchShopLinkOrPic(code, mActivity, true);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				if (null == e) {

					if (where == 1) {
						yunYunTongJi(code, 106, 4);
					}
					if (where == 2) {
						yunYunTongJi(code, 1, 4);
					}
					if (where == 3) {
						yunYunTongJi(code, 104, 4);
					}

					if (result.get("status").equals("1")) {
						String link = "";
						if (!result.get("link").startsWith("http")) {
							link = "http://" + result.get("link");
						} else {
							link = result.get("link");
						}
						shareMiniMatchPic = result.get("pic");

						createSharePic(link, result.get("pic"), mCollocationName, null);

					} else if (result.get("status").equals("1050")) {// 表明
						Intent intent = new Intent(context, NoShareActivity.class);
						intent.putExtra("isNomal", true);
						context.startActivity(intent); // 分享已经超过了

						if (null != shareWaitDialog) {
							shareWaitDialog.dismiss();
						}

					} else {
						if (null != shareWaitDialog) {
							shareWaitDialog.dismiss();
						}
					}
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

	private void share(File file, String link,String title,String content) {
		qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
		wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
		weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());
		if (file == null) {
			ToastUtil.showShortText(mActivity, "您的网络状态不太好哦~~");
			return;
		}
		UMImage umImage = new UMImage(mActivity, file);
		if (isQQShare) {
			// if(isHaveQQZone){
			// ShareUtil.configPlatforms(mActivity);
			// ShareUtil.shareShop(mActivity, umImage);
			// performShare(SHARE_MEDIA.QZONE, qqShareIntent);
			// }else{
			// ShareUtil.setShareContent(mActivity, umImage,
			// "撩汉必备手册，全在这里>>", link);
//			ShareUtil.setShareContent(mActivity, umImage, "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);
			ShareUtil.setShareContentNewMatch(mActivity, umImage, content, link, title);
			performShare(SHARE_MEDIA.QZONE, qqShareIntent);
			// }

		} else if (ShareUtil.intentIsAvailable(mActivity, wXinShareIntent)) {
			if (isWeChat) {
				// 分享到微信好友
				// UMImage umImage = new UMImage(mActivity, file);
				shareToWeChat(link, umImage,title,content);
			} else {
				boolean WxCircleFlag = SharedPreferencesUtil.getBooleanData(mActivity, Pref.RECORD_WXCIRCLE, false);
				if (WxCircleFlag) {
					SharedPreferencesUtil.saveBooleanData(mActivity, Pref.RECORD_WXCIRCLE, false);
//					ShareUtil.setShareContent(mActivity, umImage, "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);
					ShareUtil.setShareContentNewMatch(mActivity, umImage, content, link, title);
					performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
				} else {
					SharedPreferencesUtil.saveBooleanData(mActivity, Pref.RECORD_WXCIRCLE, true);
					ShareUtil.configPlatforms(mActivity);
					// UMImage umImage = new UMImage(mActivity, file);
					ShareUtil.shareShop(mActivity, umImage);
					mController.postShare(mActivity, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {

						@Override
						public void onStart() {

						}

						@Override
						public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
							// String showText = platform.toString();
							if (eCode == StatusCode.ST_CODE_SUCCESSED) {// 分享完成
								// MobclickAgent.onEvent(mActivity,
								// "sign_sharesuccess");
								// sign();

							} else {

							}
						}
					});
				}

			}

		} else {
			ToastUtil.showShortText(mActivity, "您还没有安装微信哦~");
		}

	}

	public void performShare(SHARE_MEDIA platform, final Intent intent) {
		// UMImage umImage = new UMImage(mActivity, bmNew);
		// ShareUtil.setShareContent(mActivity, umImage,
		// "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~~", link9);

		mController.postShare(mActivity, platform, new SnsPostListener() {

			@Override
			public void onStart() {
				// chooseDialog();
			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
				String showText = platform.toString();
				LogYiFu.e("showText", showText);

				if (eCode == StatusCode.ST_CODE_SUCCESSED) {

				} else {

				}

			}
		});
	}

	private void yunYunTongJi(final String shop_code, final int type, final int tab_type) {
		new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mActivity, R.string.wait) {

			@Override
			protected boolean isHandleException() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
					throws Exception {

				return ComModel2.getOperator(context, shop_code, type, tab_type);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				// System.out.println("搭配调用成功");
			}

		}.execute();
	}

	/**
	 * 获取生活状态图Pic
	 */
	private void getStartPic() {
		new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) mActivity, R.string.wait) {

			@Override
			protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				return ComModel.ShareLifeGetPic(context);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
				super.onPostExecute(context, result, e);

				if (null == e && result != null && !("".equals(result))) {

					String mStartPic = (String) result.get("pic");
					if (mStartPic == null || mStartPic.equals("null") || mStartPic.equals("")) {
//						iv_img.setImageResource(R.drawable.putongfengxiang1);
					} else {
						// SetImageLoader.initImageLoader(null, iv_img,
						// mStartPic, "");
						PicassoUtils.initImage(context, mStartPic, iv_img);
					}

				}

			}

		}.execute();
	}

	/**
	 *  获取分享文案 需要的 品牌和类目
	 * @param shop_code
	 * @param file
	 * @param link
	 */
	public void getTyepe2SuppLabel(final String shop_code,final File file,final String link) {
		new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mActivity, R.string.wait) {

			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
				return ComModelZ.geType2SuppLabe(mActivity,shop_code);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e && result != null) {
					String type2=result.get("type2");
					if("".equals(type2)){
						type2=mCollocationShopsList.get(0).getShop_name();
					}
					String label_id=result.get("supp_label_id");
					getShareTitleText(link, file,type2,label_id);
				} else {
					if (null != shareWaitDialog) {
						shareWaitDialog.dismiss();
					}
				}
			}

		}.execute();
	}
	public void getShareTitleText(final String link,final File file ,final String type2, final String label_id) {
		new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mActivity, R.string.wait) {

			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
				return ComModelZ.getShareTitleContent();
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e && result != null) {
					shareWaitDialog.dismiss();
					String text=result.get("text");
					if("".equals(text)){
						text=mCollocationShopsList.get(0).getShop_name();
					}
					String str=result.get("title");
					String str1;
					String str2;
					String str3;
					String str4;
					str1= str.replaceFirst("\\$\\{replace\\}",label_id);
					str2= str1.replaceFirst("\\$\\{replace\\}",type2);
					str3=str2.replaceFirst("\\$\\{replace\\}",""+new DecimalFormat("#0.0").format(Math.round(mCollocationShopsList.get(0).getShop_se_price()*0.9 *10)*0.1d));
					str4=str3.replaceFirst("\\$\\{replace\\}",""+new DecimalFormat("#0.0").format(Math.round(mCollocationShopsList.get(0).getShop_se_price()*0.9 *10)*0.1d));
					share(file, link,str4,text);
				} else {
					if (null != shareWaitDialog) {
						shareWaitDialog.dismiss();
					}
				}
			}

		}.execute();
	}
}
