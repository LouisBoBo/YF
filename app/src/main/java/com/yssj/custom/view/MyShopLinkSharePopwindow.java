package com.yssj.custom.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yssj.activity.R;
import com.yssj.utils.ShareUtil;

public class MyShopLinkSharePopwindow extends Dialog implements OnClickListener {
//	private UMSocialService mController = UMServiceFactory
//			.getUMSocialService(Constants.DESCRIPTOR_SHARE);
	private Activity mActivity;



	public MyShopLinkSharePopwindow(Activity activity) {
		super(activity,R.style.my_dialog);
		this.mActivity = activity;
		initView(activity);
	}


	@SuppressWarnings("deprecation")
	private void initView(Context context) {
		setContentView(R.layout.share_invite_code_popupwindow);
//		View rootView = LayoutInflater.from(context).inflate(
//				R.layout.share_invite_code_popupwindow, null);
		findViewById(R.id.iv_qq_share).setOnClickListener(this);
		findViewById(R.id.iv_wxin_share).setOnClickListener(this);
		findViewById(R.id.iv_sina_share).setOnClickListener(this);
		// rootView.findViewById(R.id.btn_cancle).setOnClickListener(this);
//		setWidth(LayoutParams.MATCH_PARENT);
//		setHeight(LayoutParams.WRAP_CONTENT);
//		setAnimationStyle(R.style.mypopwindow_anim_style);
//		setFocusable(true);
//		setTouchable(true);
//		setBackgroundDrawable(new ColorDrawable(R.color.white));
//		params = mActivity.getWindow().getAttributes();
//		params.alpha = 0.5f;
		getWindow().setWindowAnimations(R.style.mypopwindow_anim_style);
		WindowManager.LayoutParams params=getWindow().getAttributes();
		getWindow().getDecorView().setPadding(0, 0, 0, 0);
		params.width=WindowManager.LayoutParams.MATCH_PARENT;
		params.height=WindowManager.LayoutParams.WRAP_CONTENT;
		params.gravity=Gravity.BOTTOM;
		getWindow().setAttributes(params);
		setCanceledOnTouchOutside(true);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		dismiss();
		switch (id) {
		case R.id.iv_qq_share:
			ShareUtil.performShare(SHARE_MEDIA.QZONE,mActivity);
			break;
		case R.id.iv_wxin_share:
			ShareUtil.shareShopLink(SHARE_MEDIA.WEIXIN_CIRCLE,mActivity);
			break;
		case R.id.iv_sina_share:
			ShareUtil.performShare(SHARE_MEDIA.SINA,mActivity);
			break;
		// case R.id.btn_cancel:
		// dismiss();
		// break;
		default:
			break;
		}
	}

	private void onceShare(Intent intent, String perform) {
		if (ShareUtil.intentIsAvailable(mActivity, intent)) {
			mActivity.startActivity(intent);
		} else {
			Toast.makeText(mActivity, "没有安装" + perform + "客户端",
					Toast.LENGTH_SHORT).show();
		}
	}
	/**
	private void performShare(SHARE_MEDIA platform) {
		mController.postShare(mActivity, platform, new SnsPostListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode,
					SocializeEntity entity) {
				String showText = platform.toString();
				if (eCode == StatusCode.ST_CODE_SUCCESSED) {
					showText += "平台分享成功";
					Toast.makeText(mActivity, showText, Toast.LENGTH_SHORT)
							.show();
				} else {
					showText += "平台分享失败";
					Toast.makeText(mActivity, showText, Toast.LENGTH_SHORT)
							.show();
				}
				dismiss();
			}
		});
	}*/

}
