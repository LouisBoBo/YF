package com.yssj.custom.view;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;
import com.yssj.Constants;
import com.yssj.YConstance;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelL;
import com.yssj.ui.activity.PointLikeRankingActivity;
import com.yssj.ui.dialog.PublicToastDialog;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.YCache;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;


public class PointSharePopupwindow extends PopupWindow implements View.OnClickListener{
	private Context mContext;
	private  boolean mWxInstallFlag;
	// 实现对微信好友的分享：
	private Intent weixinShareIntent;
	// 实现对微信朋友圈的分享：
	private Intent wXinShareIntent;
	public PublicToastDialog shareWaitDialog;
	private int shareType;

	public PointSharePopupwindow(Activity activity) {
		super(activity);
		this.mContext = activity;
		initView();
	}
	private void initView() {
		View view = LayoutInflater.from(mContext).inflate(R.layout.point_share_window, null);
		setContentView(view);
		setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		setAnimationStyle(R.style.mypopwindow_anim_style);
		setFocusable(true);
		setTouchable(true);
		setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));

		View ivWx = view.findViewById(R.id.ll_wxin);
		View ivFriends = view.findViewById(R.id.ll_wxin_circle);
		ivWx.setOnClickListener(this);
		ivFriends.setOnClickListener(this);


		try {
			// // 是否安装了微信
			if (WXcheckUtil.isWeChatAppInstalled(mContext)) {
				mWxInstallFlag = true;
			} else {
				mWxInstallFlag = false;
			}
		} catch (Exception e) {
		}

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.ll_wxin_circle: // 分享到微信朋友圈

				if (!mWxInstallFlag) {
					ToastUtil.showLongText(mContext, "您还未安装微信喔！");
					return;
				}

				TongJiUtils.yunYunTongJi("point",910,9,mContext);

				shareType = 1;
				ShareUtil.addWXPlatform(mContext);
				getShareContent();

			break;
			case R.id.ll_wxin: // 分享到微信好友

				if (!mWxInstallFlag) {
					ToastUtil.showLongText(mContext, "您还未安装微信喔！");
					return;
				}
				TongJiUtils.yunYunTongJi("point",909,9,mContext);
				shareType = 0;
				ShareUtil.addWXPlatform(mContext);
				getShareContent();

			break;
			default:
				break;
		}
	}
	private void getShareContent(){
//		new SAsyncTask<Void, Void, List<HashMap<String,String>>>((FragmentActivity) mContext, R.string.wait) {
//
//			@Override
//			protected List<HashMap<String,String>> doInBackground(FragmentActivity context, Void... params) throws Exception {
//				return ComModel2.getPointShareContent(context);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, List<HashMap<String,String>> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (null == e&&result!=null&&result.size()>0) {
//
//					share(result.get(0).get("title"),result.get(0).get("txt"),result.get(0).get("png"));
//				}else{
//					share(SHARETITLE,SHARECONTENT,"");
//				}
//			}
//
//		}.execute();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJT.KEY_JSONTEXT_FXYQ);
					((Activity)mContext).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							HashMap<String, Object> m = new HashMap<String, Object>();
							if(map!=null){
								m = (HashMap<String, Object>) map.get(YConstance.KeyJT.KEY_JSONTEXT_FXYQ);
							}
							if(m!=null&&m.size()>0){
								share((String)m.get("title"),(String)m.get("text"),(String)m.get("icon"));
							}

						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();




	}
	private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);
	private  String SHARETITLE = "对方不想和你说话，并且向你扔了10元。";
	private  String SHARECONTENT = "快来为我点赞，即奖10元现金，每月更可赢千元任务奖励。";

	private void share(String shareTitle,String shareContent,String defalutePic) {
		wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
		weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());
		String link = YUrl.YSS_URL_ANDROID_H5+"view/activity/mission.html?realm="+YCache.getCacheUser(mContext).getUser_id();
//		String picPath = YUrl.imgurl+"yaojingjizantu.png";
		String uuId = YCache.getCacheUser(mContext).getUuid();

		UMImage umImage;
		if("null".equals(uuId)|| TextUtils.isEmpty(uuId)){
			umImage = new UMImage(mContext, YUrl.imgurl+defalutePic);
		}else{
			if((YCache.getCacheUser(mContext).getPic()).startsWith("http")){
				umImage = new UMImage(mContext,YCache.getCacheUser(mContext).getPic());
			}else{
				umImage = new UMImage(mContext, YUrl.imgurl+YCache.getCacheUser(mContext).getPic());
			}
		}

		if (shareType == 1) {// 微信好友朋友圈
			SharedPreferencesUtil.saveStringData(mContext, "messageSubSub", shareTitle);
			ShareUtil.setShareContent(mContext, umImage, shareTitle, link);
			performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
		} else if (shareType == 0) {// 微信好友
//			WeiXinShareContent wei = new WeiXinShareContent();
//			wei.setShareContent(shareContent);
//			wei.setTitle(shareTitle);
//			wei.setTargetUrl(link);
//			wei.setShareMedia(umImage);
//			mController.setShareMedia(wei);
//			performShareWXHY(SHARE_MEDIA.WEIXIN, weixinShareIntent);
		}

	}

	public void performShare(SHARE_MEDIA platform, final Intent intent) {
		// UMImage umImage = new UMImage(mActivity, bmNew);
		// ShareUtil.setShareContent(mActivity, umImage,
		// "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~~", link9);

		mController.postShare(mContext, platform, new SocializeListeners.SnsPostListener() {

			@Override
			public void onStart() {
			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
				if (eCode == StatusCode.ST_CODE_SUCCESSED) {
					sign(false);
				} else {

				}
				SocializeConfig.getSocializeConfig().cleanListeners();// 清空友盟回调监听器
				// 避免任务接口多次被调用


			}
		});
	}


	public void performShareWXHY(SHARE_MEDIA platform, final Intent intent) {
		// UMImage umImage = new UMImage(mActivity, bmNew);
		// ShareUtil.setShareContent(mActivity, umImage,
		// "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~~", link9);

		mController.postShare(mContext, platform, new SocializeListeners.SnsPostListener() {

			@Override
			public void onStart() {
				sign(true);
				SocializeConfig.getSocializeConfig().cleanListeners();// 清空友盟回调监听器
				// 避免任务接口多次被调用
			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {


			}

		});
	}


	private void sign(final Boolean isWXHY) {

//		if (isSignComplete) {
//			if (PointLikeRankingActivity.pointlikerankingactivity != null) {
//				PointLikeRankingActivity.pointlikerankingactivity.finish();
//			}
//			dismiss();
//			return;
//		}

		if (SignListAdapter.jizanCoplete) {// 集赞任务的完成状态
			if(!isWXHY){
				ToastUtil.showShortText(mContext, "分享成功");

			}
			if (PointLikeRankingActivity.pointlikerankingactivity != null) {
				PointLikeRankingActivity.pointlikerankingactivity.finish();
			}
			dismiss();
			return;
		}

		//如果是集赞的任务但是集赞任务的index没有拿到无需掉签到接口
		if (null == SignListAdapter.jizanIndex || ("").equals(SignListAdapter.jizanIndex)) {

			if(!isWXHY){
				ToastUtil.showShortText(mContext, "分享成功");

			}
			if (PointLikeRankingActivity.pointlikerankingactivity != null) {
				PointLikeRankingActivity.pointlikerankingactivity.finish();
			}
			dismiss();

			return;
		}



		// 签到
//		new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) mContext, 0) {
//
//			@Override
//			protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
//					throws Exception {
//
//					return ComModel2.getSignIn(mContext, false, true, SignListAdapter.jizanIndex, SignListAdapter.doClass);
//
//			}
//
//			protected boolean isHandleException() {
//				return true;
//			}
//
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (e == null && result != null) {
//
//					if (!isWXHY) {
//						ToastUtil.showShortText(mContext, "分享成功");
//					}
//					if (PointLikeRankingActivity.pointlikerankingactivity != null) {
//						PointLikeRankingActivity.pointlikerankingactivity.finish();
//					}
//					dismiss();
//
//				}
//			}
//		}.execute();
	}
}
