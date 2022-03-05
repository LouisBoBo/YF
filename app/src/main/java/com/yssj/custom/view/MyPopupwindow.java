package com.yssj.custom.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.DeviceConfig;
import com.yssj.Constants;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.activity.wxapi.WXEntryActivity;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.Shop;
import com.yssj.entity.ShopCart;
import com.yssj.model.ComModelZ;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsShareActivity;
import com.yssj.ui.adpter.ShopCartAdpter;
import com.yssj.ui.adpter.ShopCartCommonAdpter;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongjiShareCount;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.yssj.ui.activity.shopdetails.ShopDetailsActivity.shareWaitDialog;

/**
 * 商品详情右上角点击分享（普通商品和新特卖商品公用）
 */
public class MyPopupwindow extends PopupWindow implements OnClickListener {
	private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);
	private Activity mActivity;

	private int signType;
	private Intent qqShareIntent/*
								 * = ShareUtil.shareMultiplePictureToQZone(
								 * ShareUtil.getImage())

	private Intent weixinShareIntent /*
										 * = ShareUtil.shareToWechat(ShareUtil.
										 * getImage())
										 */;

	private double feedBack;
	private String link, four_pic;
	private HashMap<String, String> mapInfos;

	private boolean isSecondShare;

	private boolean isEveryDayTask;

	private TextView tv_title, tv_cancal;
	private LinearLayout ll_xiamian;
	private int isNoFeed = 0;
	public static ImageView iv_img;
	private Bitmap bm;
	private TextView tv1, tv3, tv4;
	private UMImage umImage;
	private ShopDetailsGetShare mGetPshareShop;
	private Shop mShop;
	private boolean isMeal = false;// 是否是套餐
	private RelativeLayout rl_comm_back_money;
	private int mSign = 1;
	private ShopCartGetShare mShopCartGetShare;
	private ShopCart mShopCart;
	private String signShopDetail;// 签到商品详情 分享
	private String shareFromFlag;
	private boolean isSignActiveshop;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public UMImage getUmImage() {
		return umImage;
	}

	public void setUmImage(UMImage umImage) {
		this.umImage = umImage;
	}

	public boolean isSecondShare() {
		return isSecondShare;
	}

	public void setSecondShare(boolean isSecondShare) {
		this.isSecondShare = isSecondShare;
	}

	private String mStartPic = "";
	private boolean isNewMeal;

	public MyPopupwindow(Activity activity, double feedBack, String link, HashMap<String, String> mapInfos,
			String four_pic, String mStartPic) {//
		super(activity);
		this.feedBack = feedBack;
		this.mActivity = activity;
		isSecondShare = true;
		this.link = link;
		this.mapInfos = mapInfos;
		this.four_pic = four_pic;
		this.mStartPic = mStartPic;
		initView(activity);
	}

	public MyPopupwindow(Activity activity, double feedBack, UMImage umImage, String link) {//
		super(activity);
		this.feedBack = feedBack;
		this.mActivity = activity;
		isSecondShare = true;
		this.umImage = umImage;
		this.link = link;

		initView(activity);
	}

	public MyPopupwindow(Activity activity, double feedBack, int isNoFeed) {// 此处是判断
																			// 任务分享的时候
																			// 无回佣，显示不同的文字
																			// isNoFeed
																			// =
																			// 1
		super(activity);
		this.feedBack = feedBack;
		this.mActivity = activity;
		isSecondShare = true;
		this.isNoFeed = isNoFeed;
		initView(activity);
	}

	private String price_h;

	public MyPopupwindow(Activity activity, double feedBack, ShopDetailsGetShare instance, Shop shop, boolean isMeal,
			String signShopDetail, String shareFromFlag, int signType, String mStartPic) {
		super(activity);
		this.signType = signType;
		this.feedBack = feedBack;
		this.mActivity = activity;
		isSecondShare = true;
		this.mGetPshareShop = instance;
		this.mStartPic = mStartPic;
		this.mShop = shop;
		this.isMeal = isMeal;
		this.shareFromFlag = shareFromFlag;
		this.signShopDetail = signShopDetail;
		initView(activity);
	}

	public MyPopupwindow(boolean isNewMeal, Activity activity, double feedBack, ShopDetailsGetShare instance, Shop shop, boolean isMeal,
			String signShopDetail, String shareFromFlag, int signType, String mStartPic, boolean isSignActiveshop) {
		super(activity);
		this.signType = signType;
		this.feedBack = feedBack;
		this.isNewMeal = isNewMeal;
		this.mActivity = activity;
		isSecondShare = true;
		this.mGetPshareShop = instance;
		this.mStartPic = mStartPic;
		this.mShop = shop;
		this.isMeal = isMeal;
		this.shareFromFlag = shareFromFlag;
		this.signShopDetail = signShopDetail;
		this.isSignActiveshop = isSignActiveshop;
		initView(activity);
	}

	public MyPopupwindow(Activity activity, double feedBack, ShopCartAdpter instance, ShopCart shopCart, int mSign,
			String shareFromFlag) {
		super(activity);
		this.feedBack = feedBack;
		this.mActivity = activity;
		isSecondShare = true;
		this.mSign = mSign;
		this.mShopCartGetShare = instance;
		this.mShopCart = shopCart;
		this.shareFromFlag = shareFromFlag;
		initView(activity);
	}

	public MyPopupwindow(Activity activity, double feedBack, ShopCartCommonAdpter instance, ShopCart shopCart,
			int mSign, String shareFromFlag) {
		super(activity);
		this.feedBack = feedBack;
		this.mActivity = activity;
		isSecondShare = true;
		this.mSign = mSign;
		this.mShopCartGetShare = (ShopCartGetShare) instance;
		this.mShopCart = shopCart;
		this.shareFromFlag = shareFromFlag;
		initView(activity);
	}

	public boolean isGou() {
		return isGou;
	}

	public void setGou(boolean isGou) {
		this.isGou = isGou;
	}

	public MyPopupwindow(Activity activity, double feedBack, boolean isSecondShare) {
		this.feedBack = feedBack;
		this.mActivity = activity;
		this.isSecondShare = isSecondShare;
		initView(activity);
	}

	private boolean mQqInstallFlag = true;// true代表安装了qq
	private boolean mWxInstallFlag = true;// true代表安装了微信
	private boolean mQqZone = true;// true代表安装了QQ空间


	@SuppressLint("ResourceAsColor")
	@SuppressWarnings("deprecation")
	private void initView(Context context) {
		View rootView = LayoutInflater.from(context).inflate(R.layout.share_popupwindow, null);

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

		iv_img = (ImageView) rootView.findViewById(R.id.iv_img);
		rl_comm_back_money = (RelativeLayout) rootView.findViewById(R.id.rl_comm_back_money);
		ll_xiamian = (LinearLayout) rootView.findViewById(R.id.ll_xiamian);
		ll_xiamian.setBackgroundColor(Color.WHITE);
		if (signType == 0 && signShopDetail != null && signShopDetail.equals("SignShopDetail")) {// 夺宝
																									// 分享文案不显示
			rl_comm_back_money.setVisibility(View.INVISIBLE);
		}
		rootView.findViewById(R.id.iv_qq_share).setOnClickListener(this);
		rootView.findViewById(R.id.iv_wxin_share).setOnClickListener(this);
		rootView.findViewById(R.id.iv_sina_share).setOnClickListener(this);
		rootView.findViewById(R.id.tv_cancal).setOnClickListener(this);

		rootView.findViewById(R.id.iv_wxin_circle_share).setOnClickListener(this);
		iv_wxin_circle_share = (LinearLayout) rootView.findViewById(R.id.iv_wxin_circle_share);



		iv_wxin_share = (ImageView) rootView.findViewById(R.id.iv_wxin_share);
		iv_qq_share = (ImageView) rootView.findViewById(R.id.iv_qq_share);
		tv1 = (TextView) rootView.findViewById(R.id.tv1);
		tv3 = (TextView) rootView.findViewById(R.id.tv3);
		tv3.setOnClickListener(this);
		tv4 = (TextView) rootView.findViewById(R.id.tv4);
		tv_title = (TextView) rootView.findViewById(R.id.tv_title);

		TextView tv_kickback = (TextView) rootView.findViewById(R.id.kick_back);

		rl_fw = (RelativeLayout) rootView.findViewById(R.id.rl_fw);
		rl_fw.setOnClickListener(this);
		img_why = (TextView) rootView.findViewById(R.id.img_why);


		//改为固定显示分享赚15元
		tv_kickback.setVisibility(View.VISIBLE);

		if (isMeal || mSign != 1 || "SignShopDetail".equals(signShopDetail)) { // 特卖分享包括购物车
			iv_img.setVisibility(View.GONE);
			tv3.setText("亲，福利手慢则无，快分享给其他姐妹吧");
			tv1.setVisibility(View.GONE);
			tv4.setVisibility(View.GONE);
			tv_kickback.setVisibility(View.GONE);
			img_why.setVisibility(View.GONE);
			rl_fw.setVisibility(View.GONE);
		}
		if (isSignActiveshop) {// 活动商品分享文案不显示
			rl_comm_back_money.setVisibility(View.INVISIBLE);
			iv_img.setVisibility(View.GONE);
			tv3.setText("  ");
			tv1.setVisibility(View.GONE);
			tv4.setVisibility(View.GONE);
			tv_kickback.setVisibility(View.GONE);
			img_why.setVisibility(View.GONE);
			rl_fw.setVisibility(View.GONE);
		}
		setContentView(rootView);
		setWidth(LayoutParams.MATCH_PARENT);
		if (iv_img.getVisibility() != View.VISIBLE) {
			setHeight(DP2SPUtil.dp2px(context, 205));
		} else {
			setHeight(LayoutParams.WRAP_CONTENT);
		}
		setAnimationStyle(R.style.mypopwindow_anim_style);
		setFocusable(true);
		setTouchable(true);
		setBackgroundDrawable(new ColorDrawable(R.color.white));
		// BackgroudAlpha((float)0.5);
		// setOnDismissListener(new OnDismissListener() {
		//
		// @Override
		// public void onDismiss() {
		//
		// BackgroudAlpha((float)1);
		// }
		// });

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

	// 设置屏幕背景透明度
	// private void BackgroudAlpha(float alpha) {
	// // TODO Auto-generated method stub
	// WindowManager.LayoutParams l = mActivity.getWindow().getAttributes();
	// l.alpha = alpha;
	// mActivity. getWindow().setAttributes(l);
	// }

	/**
	 * 分享到哪个平台
	 */
	private int shareId;

	public int getShareId() {
		return shareId;
	}

	public void setShareId(int shareId) {
		this.shareId = shareId;
	}

	@Override
	public void onClick(View v) {

		shareId = v.getId();
		// /**
		// * 先下载要分享的数据
		// */
		// if ("ShopDetails".equals(shareFromFlag)) {
		// if (isMeal) {
		// mGetPshareShop.getPshareShop();
		// } else if ("SignShopDetail".equals(signShopDetail)) {
		//
		// if (signType == 0) {
		// mGetPshareShop.share(mShop.getShop_code(), mShop);
		// } else {
		// mGetPshareShop.getPshareSignShop();
		// }
		// } else {
		// mGetPshareShop.share(mShop.getShop_code(), mShop);
		// }
		// // } else {
		// //
		// // }
		// } else if ("ShopCart".equals(shareFromFlag)) {// 购物车商品分享
		// if (mSign != 1) {
		// mShopCartGetShare.getPshareShop();
		// } else {
		// mShopCartGetShare.getShopLink(mShopCart);
		// }
		// }

		switch (shareId) {
		case R.id.iv_qq_share:

			// ToastUtil.showShortText(mActivity, "分享加载中，稍等哟~");

			if (!mQqInstallFlag && !mQqZone) {
				ToastUtil.showShortText(mActivity, "您还未安装QQ或QQ空间哦~");
				return;
			}

			/**
			 * 先下载要分享的数据
			 */
			if ("ShopDetails".equals(shareFromFlag)) {
				if (isMeal) {
					mGetPshareShop.getPshareShop();
				} else if ("SignShopDetail".equals(signShopDetail)) {

					if (signType == 0) {
						mGetPshareShop.share(mShop.getShop_code(), mShop);
					} else {
						mGetPshareShop.getPshareSignShop();
					}
				} else {
					mGetPshareShop.share(mShop.getShop_code(), mShop);
				}
				// } else {
				//
				// }
			} else if ("ShopCart".equals(shareFromFlag)) {// 购物车商品分享
				if (mSign != 1) {
					mShopCartGetShare.getPshareShop();
				} else {
					mShopCartGetShare.getShopLink(mShopCart);
				}
			}

			if (isSecondShare) {
				this.dismiss();
				// onceShare(qqShareIntent, "qq空间");

			}
			break;
		// if(ShareUtil.intentIsAvailable(mActivity, qqShareIntent)){
		// onceShare(qqShareIntent, "qq空间");
		// recordShare();
		// }else{
		// performShare(SHARE_MEDIA.QZONE, qqShareIntent);
		// }

		case R.id.iv_wxin_share:
			// ToastUtil.showShortText(mActivity, "分享加载中，稍等哟~");

			if (!mWxInstallFlag) {
				ToastUtil.showShortText(mActivity, "您还未安装微信哦~");
				return;
			}

			/**
			 * 先下载要分享的数据
			 */
			if ("ShopDetails".equals(shareFromFlag)) {
				if (isMeal) {
					mGetPshareShop.getPshareShop();
				} else if ("SignShopDetail".equals(signShopDetail)) {

					if (signType == 0) {
						mGetPshareShop.share(mShop.getShop_code(), mShop);
					} else {
						mGetPshareShop.getPshareSignShop();
					}
				} else {
					mGetPshareShop.share(mShop.getShop_code(), mShop);
				}
				// } else {
				//
				// }
			} else if ("ShopCart".equals(shareFromFlag)) {// 购物车商品分享
				if (mSign != 1) {
					mShopCartGetShare.getPshareShop();
				} else {
					mShopCartGetShare.getShopLink(mShopCart);
				}
			}

			if (isSecondShare) {
				this.dismiss();
				// onceShare(wXinShareIntent, "微信");
				// recordShare();
				// String currentTime = DateFormat.format("HH", new
				// Date(System.currentTimeMillis())).toString();
				// int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
				// if((hour > 7 || hour == 7) && hour < 14){
				// recordEveryDayTask("7");
				// mActivity.getSharedPreferences("EveryDayShareAm",
				// Context.MODE_PRIVATE).edit().putInt("day",
				// Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
				// }else if((hour > 14 || hour == 14) && hour < 20){
				// recordEveryDayTask("8");
				// mActivity.getSharedPreferences("EveryDaySharePm",
				// Context.MODE_PRIVATE).edit().putInt("day",
				// Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
				// }
			} else {
				// performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
			}
			break;
		case R.id.iv_sina_share:
			break;
		case R.id.tv3:
			// Intent intent = new Intent(mActivity, MakeMoneySecret.class);
			// mActivity.startActivity(intent);
			break;
		case R.id.rl_fw:
//			SharedPreferencesUtil.saveStringData(mActivity, "first_wen", systime_now);
//
////			Intent intent1 = new Intent(mActivity, MakeMoneySecret.class);
////			mActivity.startActivity(intent1);
//			mActivity.startActivity(new Intent(mActivity, PointLikeRankingActivity.class));
//			((Activity) mActivity).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
			Intent intent1 = new Intent(mActivity, ShopDetailsShareActivity.class);

			intent1.putExtra("isNewMeal",isNewMeal);
			intent1.putExtra("shop_code",mShop.getShop_code());
            mActivity.startActivity(intent1);
			((Activity) mActivity).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
			break;
		case R.id.iv_wxin_circle_share:

			// ToastUtil.showShortText(mActivity, "分享加载中，稍等哟~");
			if (!mWxInstallFlag) {
				ToastUtil.showShortText(mActivity, "您还未安装微信哦~");
				return;
			}

			/**
			 * 先下载要分享的数据
			 */
			if ("ShopDetails".equals(shareFromFlag)) {
				if (isMeal) {
					mGetPshareShop.getPshareShop();
				} else if ("SignShopDetail".equals(signShopDetail)) {

					if (signType == 0) {
						mGetPshareShop.share(mShop.getShop_code(), mShop);
					} else {
						mGetPshareShop.getPshareSignShop();
					}
				} else {
					mGetPshareShop.share(mShop.getShop_code(), mShop);
				}
				// } else {
				//
				// }
			} else if ("ShopCart".equals(shareFromFlag)) {// 购物车商品分享
				if (mSign != 1) {
					mShopCartGetShare.getPshareShop();
				} else {
					mShopCartGetShare.getShopLink(mShopCart);
				}
			}

			// if(!"SignShopDetail".equals(signShopDetail)){
			// tongjifenxiang();
			// }

			dismiss();
			// shareToWxin();

			// if (bm != null) {
			// if (isSecondShare) {//只分享第二次
			// onceShare(weixinShareIntent, "微信");
			// }else{
			// performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);
			// }
			// } else {
			// download(true);
			// }
			// case R.id.btn_cancel:
			// dismiss();
			break;
		case R.id.tv_cancal:

			dismiss();

			break;
		default:
			break;
		}
	}

	/**
	 * 分享到微信好友
	 */
	public void shareToWxin() {
//		weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());
//		WeiXinShareContent wei = new WeiXinShareContent();
//
//		if (signShopDetail != null && signShopDetail.equals("SignShopDetail")) { // 夺宝或包邮
//
//			if (signType == 0) { // 夺宝
//				wei.setTitle("我正在参与" + ((int) mShop.getShop_se_price() + "") + "元夺宝活动");
//				wei.setShareContent(((int) mShop.getShop_se_price() + "") + "元赢iPhone6，不中还能退钱，居然有这样的好事");
//			} else { // 包邮
//				wei.setTitle("我正在参与" + signType + "元包邮活动");
//				wei.setShareContent(signType + "元带走心爱的商品。首次签到还能领3元现金哦");
//			}
//
//		} else { // 正价或特卖
//			if (isMeal) { // 特卖
//				// wei.setTitle("一件特卖宝贝正在等待亲爱哒打开哦");
//				// wei.setShareContent("我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~~");
//				wei.setTitle("精品超值特卖");
//				wei.setShareContent("买了肯定不后悔，数量不多，快来抢购吧~");
//			} else {// 正价
//
//				// wei.setTitle("一件美衣正在等待亲爱哒打开哦");
//				wei.setShareContent("我挺喜欢的美衣，分享给你进来看看还能领现金红包哦~");
//				// wei.setTitle("男神喜欢你这样穿");
//				// wei.setShareContent("撩汉必备手册，全在这里>>");
//				wei.setTitle(StringUtils.getShareContent(YCache.getCacheUser(mActivity).getNickname()));
//			}
//
//		}

		// if(mShop == null){ //特卖
		//
		// wei.setTitle("一件特卖宝贝正在等待亲爱哒打开哦");
		// wei.setShareContent("我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~~");
		//
		// }else{ //签到夺宝商品 普通商品 包邮
		//
		// if(signShopDetail == null){
		// //普通商品
		//
		// wei.setTitle("一件美衣正在等待亲爱哒打开哦");
		// wei.setShareContent("我挺喜欢的美衣，分享给你进来看看还能领现金红包哦~~");
		// }else {
		//
		// if(signType == 0){
		// //夺宝
		//
		// wei.setTitle("我正在参与"+ ((int)mShop.getShop_se_price()+"")+"元夺宝活动");
		// wei.setShareContent(((int)mShop.getShop_se_price()+"")+"元赢iPhone6，不中还能退钱，居然有这样的好事");
		// }else{
		// //包邮
		// wei.setTitle("我正在参与"+signType+"元包邮活动");
		// wei.setShareContent(signType +"元带走心爱的商品。首次签到还能领3元现金哦");
		// }
		//
		//
		//
		// }
		//
		// }
		//

//		wei.setTargetUrl(link);
//		wei.setShareMedia(umImage);
//		mController.setShareMedia(wei);
//		performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);

		// if(price_h!= null){
		//
		// wei.setTitle("我正在参与"+price_h+"元夺宝活动");
		// wei.setShareContent(price_h+"元赢iPhone6，不中还能退钱，居然有这样的好事");
		// }else{
		// if (signType == 0) {
		//
		// if (isMeal) {
		// wei.setTitle("一件特卖宝贝正在等待亲爱哒打开哦");
		// } else {
		// wei.setTitle("一件美衣正在等待亲爱哒打开哦");
		// }
		//
		// wei.setShareContent("我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~~");
		//
		// } else {
		//
		// wei.setTitle("我正在参与"+signType+"元包邮活动");
		// wei.setShareContent(signType+"元带走心爱的商品。首次签到还能领3元现金哦");
		//
		// }
		// }

	}

	public void onceShare(Intent intent, String perform) {
		if (ShareUtil.intentIsAvailable(mActivity, intent)) {
			mActivity.startActivity(intent);
			int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			if ((hour > 7 || hour == 7) && hour < 14) {
				// recordEveryDayTask("7");
				mActivity.getSharedPreferences("EveryDayShareAm", Context.MODE_PRIVATE).edit()
						.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
			} else if ((hour > 14 || hour == 14) && hour < 20) {
				// recordEveryDayTask("8");
				mActivity.getSharedPreferences("EveryDaySharePm", Context.MODE_PRIVATE).edit()
						.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
			} else {

			}
		} else {
			qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
			performShare(SHARE_MEDIA.QZONE, qqShareIntent);
		}
	}

	// 供后台记录分享次数
	// private void recordShare() {
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
	// return ComModel2.getIntegral(context);
	// }
	//
	// @Override
	// protected void onPostExecute(FragmentActivity context, Void result,
	// Exception e) {
	// super.onPostExecute(context, result, e);
	// }
	//
	// }.execute();
	// }

	public void performShare(SHARE_MEDIA platform, final Intent intent) {
		mController.postShare(mActivity, platform, new SnsPostListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
				String showText = platform.toString();
				if (eCode == StatusCode.ST_CODE_SUCCESSED) {
					showText += "平台分享成功";
					Toast.makeText(mActivity, showText, Toast.LENGTH_SHORT).show();
					// recordShare();
					// 获取当前时间
					// String currentTime = DateFormat.format("HH", new
					// Date()).toString();
					// int hour = Integer.parseInt(currentTime);

					int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
					// Date d = new Date();
					// @SuppressWarnings("deprecation")
					// int hour = d.getHours();

					if ((hour > 7 || hour == 7) && hour < 14) {
						// recordEveryDayTask("7");
						mActivity.getSharedPreferences("EveryDayShareAm", Context.MODE_PRIVATE).edit()
								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
					} else if ((hour > 14 || hour == 14) && hour < 20) {
						// recordEveryDayTask("8");
						mActivity.getSharedPreferences("EveryDaySharePm", Context.MODE_PRIVATE).edit()
								.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
					}

					/*
					 * if (isSecondShare) { if
					 * (ShareUtil.intentIsAvailable(mActivity, intent)) {
					 * mActivity.startActivity(intent); } else {
					 * Toast.makeText(mActivity, "没有安装" + showText + "客户端",
					 * Toast.LENGTH_SHORT).show(); } }
					 */
				} else {
					if (isGou) {
						showText += "分享失败,分享成功才能购买";
					} else {
						// showText += "取消分享";
					}

					// Toast.makeText(mActivity, showText, Toast.LENGTH_SHORT)
					// .show();
				}
				dismiss();
			}
		});
	}

	private boolean isGou = false;

	// private void recordEveryDayTask(final String id) {
	// new SAsyncTask<Void, Void, Integer>((FragmentActivity) mActivity,
	// R.string.wait) {
	//
	// @Override
	// protected boolean isHandleException() {
	// // TODO Auto-generated method stub
	// return true;
	// }
	//
	// @Override
	// protected Integer doInBackground(FragmentActivity context, Void...
	// params) throws Exception {
	// // TODO Auto-generated method stub
	// return ComModel2.doMission(context, id);
	// }
	//
	// @Override
	// protected void onPostExecute(FragmentActivity context, Integer result,
	// Exception e) {
	// super.onPostExecute(context, result, e);
	// }
	//
	// }.execute();
	// }

	private Bitmap bmBg;

	// private void download(final boolean isF) {
	// // new SAsyncTask<Void, Void, Void>((FragmentActivity) mActivity,
	// // R.string.wait) {
	// //
	// // @Override
	// // protected boolean isHandleException() {
	// // // TODO Auto-generated method stub
	// // return true;
	// // }
	// //
	// // @Override
	// // protected Void doInBackground(FragmentActivity context, Void...
	// // params) throws Exception {
	// // // TODO Auto-generated method stub
	// // String url = four_pic.split(",")[2];
	// // bmBg = downloadPic(
	// // mapInfos.get("shop_code").substring(1, 4) + "/" +
	// // mapInfos.get("shop_code") + "/" + url);
	// // LogYiFu.e("链接顶顶顶顶",
	// // mapInfos.get("shop_code").substring(1, 4) + "/" +
	// // mapInfos.get("shop_code") + "/" + url);
	// //
	// // return super.doInBackground(context, params);
	// // }
	// //
	// // @Override
	// // protected void onPostExecute(FragmentActivity context, Void result,
	// // Exception e) {
	// // // TODO Auto-generated method stub
	// // if (null == e) {
	// // // if(file==null){
	// // // Toast.makeText(context, "您的网络状态不太好哦~~",
	// // // Toast.LENGTH_SHORT).show();
	// // // return;
	// // // }
	// // Bitmap bmQr = QRCreateUtil.createQrImage(link, 160, 160);// 得到二维码图片
	// //
	// // bm = QRCreateUtil.drawNewBitmap1(context, bmBg, bmQr,
	// // mapInfos.get("shop_se_price"), "");
	// // saveBitmap(bm, "/sdcard/share_pic.png");
	// // if (null != bm) {
	// // if (isSecondShare) {// 只分享第二次
	// // onceShare(weixinShareIntent, "微信");
	// // } else {
	// // performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);
	// // }
	// // }
	// // }
	// // super.onPostExecute(context, result, e);
	// // }
	// //
	// // }.execute();
	// }

	private File file;
	public LinearLayout iv_wxin_circle_share;
	public ImageView iv_wxin_share;
	public ImageView iv_qq_share;
	private AlphaAnimation alphaAnimation;
	private TextView img_why;
	private String systime_now;
	private RelativeLayout rl_fw;

	// private Bitmap downloadPic(String picPath) {
	// try {
	// URL url = new URL(YUrl.imgurl + picPath);
	// // 打开连接
	// URLConnection con = url.openConnection();
	// // 获得文件的长度
	// int contentLength = con.getContentLength();
	// // System.out.println("长度 :" + contentLength);
	// // 输入流
	// InputStream is = con.getInputStream();
	// // 1K的数据缓冲
	// byte[] bs = new byte[8192];
	// // 读取到的数据长度
	// int len;
	// BitmapDrawable bmpDraw = new BitmapDrawable(is);
	//
	// // 完毕，关闭所有链接
	// is.close();
	// return bmpDraw.getBitmap();
	// } catch (Exception e) {
	// LogYiFu.e("TAG", "下载失败");
	//
	// e.printStackTrace();
	// return null;
	// }
	// }

	public static void saveBitmap(Bitmap bitmap, String file) {
		FileOutputStream bigOutputStream = null;
		try {
			bigOutputStream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, bigOutputStream);// 把数据写入文件
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				bigOutputStream.flush();
				bigOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 商品详情 分享的 获取分享数据的接口
	 *
	 * @author Administrator
	 */
	public interface ShopDetailsGetShare {
		void getPshareShop();// 特卖分享

		void getPshareSignShop();// 包邮分享

		void share(String code, Shop shop);
	}

	/**
	 * 购物车分享获取数据的接口 ShopCartAdapter
	 *
	 * @author Administrator
	 */
	public interface ShopCartGetShare {
		void getPshareShop();

		void getShopLink(ShopCart shopCart);
	}

	/**
	 * 获取Pic
	 */
	// private void getStartPic() {
	// new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity)
	// mActivity, R.string.wait) {
	//
	// @Override
	// protected HashMap<String, Object> doInBackground(FragmentActivity
	// context, Void... params)
	// throws Exception {
	// return ComModel.ShareLifeGetPic(context);
	// }
	//
	// @Override
	// protected boolean isHandleException() {
	// return true;
	// }
	//
	// @Override
	// protected void onPostExecute(FragmentActivity context, HashMap<String,
	// Object> result, Exception e) {
	// super.onPostExecute(context, result, e);
	//
	// if (null == e && result != null && !("".equals(result))) {
	//
	// String mStartPic = (String) result.get("pic");
	// if (mStartPic == null || mStartPic.equals("null") ||
	// mStartPic.equals("")) {
	// iv_img.setImageResource(R.drawable.putongfengxiang1);
	// } else {
	// SetImageLoader.initImageLoader(null, iv_img, mStartPic, "");
	// }
	//
	// }
	//
	// }
	//
	// }.execute();
	// }

	private Intent weixinShareIntent;

	/**
	 * 分享到微信好友
	 */
	public void shareToWxinNewTitle(String title,String content) {
//		weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());
//		WeiXinShareContent wei = new WeiXinShareContent();
//
//		if (signShopDetail != null && signShopDetail.equals("SignShopDetail")) { // 夺宝或包邮
//
//			if (signType == 0) { // 夺宝
//				wei.setTitle("我正在参与" + ((int) mShop.getShop_se_price() + "") + "元夺宝活动");
//				wei.setShareContent(((int) mShop.getShop_se_price() + "") + "元赢iPhone6，不中还能退钱，居然有这样的好事");
//			} else { // 包邮
//				wei.setTitle("我正在参与" + signType + "元包邮活动");
//				wei.setShareContent(signType + "元带走心爱的商品。首次签到还能领3元现金哦");
//			}
//
//		} else { // 正价或特卖
//			if (isMeal) { // 特卖
//				wei.setTitle("精品超值特卖");
//				wei.setShareContent("买了肯定不后悔，数量不多，快来抢购吧~");
//			} else {// 正价
//				wei.setShareContent(""+content);
//				wei.setTitle(""+title);
//			}
//
//		}


//
//		String four_pic = ShopDetailsActivity.shareFour_pic;
//
//		if(isNewMeal){
//
//
//
//			TongjiShareCount.tongjifenxiangCount();
//			TongjiShareCount.tongjifenxiangwho(mShop.getShop_code());
//
//
//			String shareMIniAPPimgPic = YUrl.imgurl + mShop.getShop_code().substring(1, 4) + "/" + mShop.getShop_code() + "/" + mShop.getDef_pic() + "!280";
//			String wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + mShop.getShop_code() +
//					"&isShareFlag=true&user_id=" + YCache.getCacheUser(mActivity).getUser_id();
//
//
//			String onPrice = GuideActivity.oneShopPrice;
////					onPrice = new java.text.DecimalFormat("#0")
////							.format(Double.parseDouble(onPrice));
//
////			String shareText = "快来" + onPrice + "元抢【" + mShop.getSupp_label() +"正品" + result.get("type2")+ "】，专柜价" + mShop.getShop_se_price() + "元!";
//
//			String shareText = "快来"+onPrice+"元拼【"+mShop.getShop_name()+"】，原价"+mShop.getShop_se_price()+"元！";
//
//
//			//分享到微信统一分享小程序
//			WXminiAPPShareUtil.shareShopToWXminiAPP(mActivity, mShop.getShop_name(),mShop.getApp_shop_group_price(),  shareMIniAPPimgPic, wxMiniPathdUO, false);
//
//			WXEntryActivity.setWXminiShareListener(new WXEntryActivity.WXminiAPPshareListener() {
//				@Override
//				public void wxMiniShareSuccess() {
//					ToastUtil.showShortText(mActivity,"分享成功");
//
//				}
//			});
//
//
//			if (null != shareWaitDialog) {
//				shareWaitDialog.dismiss();
//			}
//			dismiss();
//
//
//		}else{
//			getTyepe2SuppLabel(four_pic);
//
//		}

//		String shareMIniAPPimgPic = YUrl.imgurl + mShop.getShop_code().substring(1, 4) + "/" + mShop.getShop_code() + "/" + four_pic.split(",")[2] + "!280";
//
//
//		String wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + mShop.getShop_code() +
//				"&isShareFlag=true&user_id=" + YCache.getCacheUser(mActivity).getUser_id();
//		//分享到微信统一分享小程序
//		WXminiAPPShareUtil.shareToWXminiAPP(mActivity, shareMIniAPPimgPic, StringUtils.getShareContent(YCache.getCacheUser(mActivity).getNickname()), wxMiniPathdUO,false);
//		dismiss();
//		wei.setTargetUrl(link);
//		wei.setShareMedia(umImage);
//		mController.setShareMedia(wei);
//		performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);


	}



	public void getTyepe2SuppLabel(final String four_pic) {


		String shareMIniAPPimgPic = YUrl.imgurl + mShop.getShop_code().substring(1, 4) + "/" + mShop.getShop_code() + "/" + four_pic.split(",")[2] + "!280";
		String wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + mShop.getShop_code() +
				"&isShareFlag=true&user_id=" + YCache.getCacheUser(mActivity).getUser_id();
		//分享到微信统一分享小程序
		WXminiAPPShareUtil.shareShopToWXminiAPP(mActivity, mShop.getShop_name(),mShop.getApp_shop_group_price(),  shareMIniAPPimgPic, wxMiniPathdUO, false);
		WXEntryActivity.setWXminiShareListener(new WXEntryActivity.WXminiAPPshareListener() {
			@Override
			public void wxMiniShareSuccess() {
				ToastUtil.showShortText(mActivity,"分享成功");

			}
		});
		if (null != shareWaitDialog) {
			shareWaitDialog.dismiss();
		}
		dismiss();

//
//
//		new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mActivity, R.string.wait) {
//
//			@Override
//			protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
//				return ComModelZ.geType2SuppLabe(mActivity, mShop.getShop_code());
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (null == e && result != null) {
//
//					LogYiFu.e("获取","类目获取失败");
//
//
//					//二级类目
//					String label_id = result.get("supp_label_id");
//
//
//					TongjiShareCount.tongjifenxiangCount();
//					TongjiShareCount.tongjifenxiangwho(mShop.getShop_code());
//
//
//					String shareMIniAPPimgPic = YUrl.imgurl + mShop.getShop_code().substring(1, 4) + "/" + mShop.getShop_code() + "/" + four_pic.split(",")[2] + "!280";
//					String wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + mShop.getShop_code() +
//							"&isShareFlag=true&user_id=" + YCache.getCacheUser(mActivity).getUser_id();
//
//
//					String onPrice = GuideActivity.oneShopPrice;
////					onPrice = new java.text.DecimalFormat("#0")
////							.format(Double.parseDouble(onPrice));
//
//					String shareText = "快来" + onPrice + "元拼【" + mShop.getSupp_label() +"正品" + result.get("type2")+ "】，专柜价" + mShop.getShop_se_price() + "元!";
//
//
//
//					//分享到微信统一分享小程序
//
//					WXminiAPPShareUtil.shareShopToWXminiAPP(mActivity, mShop.getShop_name(),mShop.getApp_shop_group_price(),  shareMIniAPPimgPic, wxMiniPathdUO, false);
//
//
//					WXEntryActivity.setWXminiShareListener(new WXEntryActivity.WXminiAPPshareListener() {
//						@Override
//						public void wxMiniShareSuccess() {
//							ToastUtil.showShortText(mActivity,"分享成功");
//
//						}
//					});
//
//
//					if (null != shareWaitDialog) {
//						shareWaitDialog.dismiss();
//					}
//					dismiss();
//
//
//				} else {
//					LogYiFu.e("获取","获取成功");
//					if (null != shareWaitDialog) {
//						shareWaitDialog.dismiss();
//					}
//				}
//			}
//
//		}.execute();
	}


}