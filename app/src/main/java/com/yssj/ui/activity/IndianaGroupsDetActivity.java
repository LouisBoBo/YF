package com.yssj.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;
import com.yssj.Constants;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.IndianaGroupsMemberItem;
import com.yssj.entity.Shop;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModelZ;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

/***
 * 我的夺宝团
 */
public class IndianaGroupsDetActivity extends BasicActivity implements OnClickListener {

	private LinearLayout mGroups;
	private View img_back;
	private ImageView top_image;
	private TextView tvNeeds;
	private int width;
	private Shop shop;


	private int needs ; //还剩几人成团


	private  boolean mWxInstallFlag;
	// 实现对微信好友的分享：
	private Intent weixinShareIntent;
	// 实现对微信朋友圈的分享：
	private Intent wXinShareIntent;
	private int shareType;
	private Context mContext;

	private String  shop_code,issue_code,banner,shop_name,shop_price;
	private int groupCount;//成团人数
	private int is_ago;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indiana_groups_det);
		AppManager.getAppManager().addActivity(this);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		mContext = this;
		Intent intent = this.getIntent();
		if(intent!=null){
			issue_code = intent.getStringExtra("issue_code");
			is_ago = intent.getIntExtra("is_ago",0);
//			banner = intent.getStringExtra("banner");
			groupCount = intent.getIntExtra("group_number",3);
			shop = (Shop) intent.getSerializableExtra("shop");
		}
		if(shop!=null){
			shop_name = shop.getShop_name();
			shop_code = shop.getShop_code();
			banner = shop.getBanner();
//			shop_price = new DecimalFormat("#0.0").format(shop.getShop_se_price());
			shop_price = new DecimalFormat("#0.0").format(Math.round(shop.getShop_se_price()*10) * 0.1d);
		}
		initView();
		initData();
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		img_back = findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		mGroups = (LinearLayout) findViewById(R.id.indiana_groups_container);
		top_image = (ImageView) findViewById(R.id.top_image);
		ViewGroup.LayoutParams lp = top_image.getLayoutParams();
		lp.width =width;
		lp.height =width/2;
		top_image.setLayoutParams(lp);//图片宽高2:1
		tvNeeds = (TextView) findViewById(R.id.indaina_groups_needs);

		View ivWx = findViewById(R.id.ll_wxin);
		View ivFriends = findViewById(R.id.ll_wxin_circle);
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

	/**
	 * 获取数据
	 * 
	 */
	private void initData() {
		PicassoUtils.initImage(mContext, banner, top_image);
		new SAsyncTask<Void, Void, List<HashMap<String,
				Object>>>((FragmentActivity) mContext, null, R.string.wait) {
			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				return ComModelZ.queryGroupIndianaGroupList(context, shop_code,
						issue_code, "" , "",is_ago);

			}

			@Override
			protected void onPostExecute(FragmentActivity context,
										 List<HashMap<String, Object>> result, Exception e) {

				if (e != null) {// 查询异常
					Toast.makeText(context, "连接超时，请重试", Toast.LENGTH_LONG).show();
				} else {
					if (result != null && result.size() > 0) {
						needs = groupCount - ((List<HashMap<String, String>>) result.get(0).get("user")).size();
						tvNeeds.setText(needs+"");

						for (int i = 0; i < result.size() ; i++) {
							HashMap<String,Object> map = result.get(i);
							int num = i+1 ;

							IndianaGroupsMemberItem item = new IndianaGroupsMemberItem(mContext);
							item.setTopData(num+"",(String) map.get("u_code"), (String) map.get("issue_code"));
							item.setData((List<HashMap<String, String>>) map.get("user"),
									groupCount,shop_name,(String) map.get("issue_code"));
							mGroups.addView(item);
						}

					}
				}

			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
		}.execute();

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.img_back:
			onBackPressed();
			break;
		case R.id.ll_wxin_circle: // 分享到微信朋友圈

			if (!mWxInstallFlag) {
				ToastUtil.showLongText(mContext, "您还未安装微信喔！");
				return;
			}

			shareType = 1;
			ShareUtil.addWXPlatform(mContext);
			getShareContent();

			break;
		case R.id.ll_wxin: // 分享到微信好友

			if (!mWxInstallFlag) {
				ToastUtil.showLongText(mContext, "您还未安装微信喔！");
				return;
			}
			shareType = 0;
			ShareUtil.addWXPlatform(mContext);
			getShareContent();

			break;
		default:
			break;
		}
	}


	private void getShareContent() {
		new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {

			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
				return ComModelZ.getShareGroupTitleContent();
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e && result != null) {
					String   mTitle = result.get("title");
					String text=result.get("text");

					UserInfo user = YCache.getCacheUserSafe(context);
					mTitle = mTitle.replaceFirst("\\$\\{replace\\}", ""+needs);
					mTitle = mTitle.replaceFirst("\\$\\{replace\\}", ""+shop_name);

					text = text.replaceFirst("\\$\\{replace\\}", ""+user.getNickname());
					text = text.replaceFirst("\\$\\{replace\\}", ""+needs);
					share(mTitle,text);
				}
			}

		}.execute();

	}


	private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);
	private void share(String shareTitle,String shareContent) {

//        String shareTitle = "衣蝠特惠，"+groupCount+"人成团就有机会，"+shop_price+"元团购"+shop_name;
//        String shareContent = YCache.getCacheUser(mContext).getNickname()+"成功开团，现在报名，马上参团！";
        String sharePic = shop_code.substring(1, 4) + File.separator + shop_code + File.separator + shop.getDef_pic()+"!180";

		wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
		weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());
		String link = YUrl.YSS_URL_ANDROID_H5 + "/view/activity/signDetail.html?r=" + YCache.getCacheUser(mContext).getUser_id()
                +"&i_c=" +issue_code+"&s_c=" +shop_code;
		UMImage umImage = new UMImage(mContext, YUrl.imgurl + sharePic);

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
//			performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);
		}

	}

	public void performShare(SHARE_MEDIA platform, final Intent intent) {

		mController.postShare(mContext, platform, new SocializeListeners.SnsPostListener() {

			@Override
			public void onStart() {
			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
				if (eCode == StatusCode.ST_CODE_SUCCESSED) {

				} else {

				}
				SocializeConfig.getSocializeConfig().cleanListeners();// 清空友盟回调监听器
				// 避免任务接口多次被调用


			}
		});
	}


}
