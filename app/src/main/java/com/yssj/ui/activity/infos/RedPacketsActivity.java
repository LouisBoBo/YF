//package com.yssj.ui.activity.infos;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.controller.UMServiceFactory;
//import com.umeng.socialize.controller.UMSocialService;
//import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
//import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
//import com.umeng.socialize.exception.SocializeException;
//import com.umeng.socialize.weixin.controller.UMWXHandler;
//import com.yssj.Constants;
//import com.yssj.YUrl;
//import com.yssj.activity.R;
//import com.yssj.app.AppManager;
//import com.yssj.app.SAsyncTask;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.entity.Store;
//import com.yssj.entity.UserInfo;
//import com.yssj.huanxin.PublicUtil;
//import com.yssj.model.ComModel;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.MainFragment;
//import com.yssj.ui.activity.MainMenuActivity;
//import com.yssj.ui.activity.MembersGoodsListActivity;
//import com.yssj.ui.activity.myshop.RedPacketsListActivity;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.ui.fragment.MineShopFragment;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.YCache;
//import com.yssj.wxpay.WxPayUtil;
//
//public class RedPacketsActivity extends BasicActivity {
//	private RelativeLayout rel_red_bag, rel_red_bag_mine;
//	private LinearLayout img_back;
//	private TextView tvTitle_base;
//	private Store store;
//	private String token;
//	private String flag;
//	private String uid;
//	private LinearLayout red;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		getActionBar().hide();
//		setContentView(R.layout.activity_red_packets);
//		initView();
//		store = YCache.getCacheStore(this);
//		token = YCache.getCacheToken(this);
//	}
//
//	private void initView() {
//		img_back = (LinearLayout) findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
//		tvTitle_base.setText("语音红包");
//		red= (LinearLayout) findViewById(R.id.redd);
//		red.setBackgroundColor(Color.WHITE);
//		rel_red_bag = (RelativeLayout) findViewById(R.id.rel_red_bag);
//		rel_red_bag_mine = (RelativeLayout) findViewById(R.id.rel_red_bag_mine);
//		rel_red_bag.setOnClickListener(this);
//		rel_red_bag_mine.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View arg0) {
//		// TODO Auto-generated method stub
//		super.onClick(arg0);
//		Intent intent = null;
//		switch (arg0.getId()) {
//		case R.id.img_back:// 返回按钮
//			finish();
//			break;
//		case R.id.rel_red_bag:// 我要发红包
//			checkUid();
//
//			break;
//		case R.id.rel_red_bag_mine:// 已发的红包
//			String ui = YCache.getCacheUser(context).getUuid();
//			if(null==YCache.getCacheUser(context).getUuid()||"".equals(YCache.getCacheUser(context).getUuid())||"null".equals(YCache.getCacheUser(context).getUuid())){
//				ToastUtil.showShortText(context, "你还没有发过红包");
//				return;
//			}
//			
////			toRedPackets("view/hb/mysents_app.html?uid=oj8JHt-fZZRAdTLzY9Nwr-rDznWw");
//			intent = new Intent(this,RedPacketsListActivity.class);
//			startActivity(intent);
//			break;
//		}
//	}
//
//	private void toRedPackets(String url) {
//		AppManager.getAppManager().finishAllActivityOfEveryDayTask();
//		if (MainMenuActivity.instances != null)
//			((MainFragment) MainMenuActivity.instances
//					.getSupportFragmentManager().findFragmentByTag("tag"))
//					.setIndex(0);
//		if (((MineShopFragment) ((MainFragment) MainMenuActivity.instances
//				.getSupportFragmentManager().findFragmentByTag("tag"))
//				.getChildFragmentManager().findFragmentByTag("1")) == null) {
//			MineShopFragment.url = YUrl.YSS_URL_ANDROID_H5 + url
//			// + "?realm="+ store.getRealm() + "&token=" + token +
//			// "&isAndroid=true"
//			;
//		} else {
//			((MineShopFragment) ((MainFragment) MainMenuActivity.instances
//					.getSupportFragmentManager().findFragmentByTag("tag"))
//					.getChildFragmentManager().findFragmentByTag("1"))
//					.getWebview().loadUrl(YUrl.YSS_URL_ANDROID_H5 + url
//					// + "?realm=" + store.getRealm() + "&token="
//					// + token + "&isAndroid=true"
//					);
//		}
//	}
//
//	private void checkUid() {
//
//		new SAsyncTask<Void, Void, HashMap<String, Object>>(this, R.string.wait) {
//
//			@Override
//			protected HashMap<String, Object> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.checkUid(context);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, Object> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (e == null) {
//					flag = result.get("flag") + "";
//					uid = result.get("uid") + "";
//					if (flag.equals("false")) {
//						openId(SHARE_MEDIA.WEIXIN, 0, RedPacketsActivity.this);
//					} else {
//
//						Intent intent = new Intent(RedPacketsActivity.this,
//								SendRedPacketsActivity.class);
//						intent.putExtra("uid", uid);
//						startActivity(intent);
//					}
//				}
//			}
//
//		}.execute();
//
//	}
//	
//	private  void openId(final SHARE_MEDIA platform, final int type,final Activity activity) {
//
//		final UMSocialService mController=UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);
//		UMWXHandler wxHandler = new UMWXHandler(RedPacketsActivity.this,WxPayUtil.APP_ID, WxPayUtil.APP_SECRET);
//		wxHandler.addToSocialSDK(); 
//		mController.doOauthVerify(activity, platform,
//				new UMAuthListener() {
//
//					@Override
//					public void onStart(SHARE_MEDIA platform) {
//						
//					}
//
//					@Override
//					public void onError(SocializeException e,
//							SHARE_MEDIA platform) {
//					}
//
//					@Override
//					public void onComplete(Bundle value, SHARE_MEDIA platform) {
//						
//						mController.getPlatformInfo(activity, platform, new UMDataListener() {
//
//							@Override
//							public void onStart() {
//
//							}
//
//							@Override
//							public void onComplete(int status, Map<String, Object> info) {
//								if (info != null) {
//									if (type == 0) {// 微信
//										if (status == 200 && info != null) {
//											MyLogYiFu.e("微信授权",info.toString());
//											final String uid=info.get("openid").toString();
//											YCache.getCacheUser(activity).setUuid(uid);
//											final String pic=info.get("headimgurl").toString();
//											final String nickname = info.get("nickname").toString();
//											final String unionid  = info.get("unionid").toString();
//											if(TextUtils.isEmpty(uid)){
//												return;
//											}
//											Intent intent = new Intent(RedPacketsActivity.this,
//													SendRedPacketsActivity.class);
//											intent.putExtra("uid", unionid);
//											startActivity(intent);
//											new Thread(){
//												public void run() {
//													try {
//														ComModel2.registerWeiXinToken(unionid,uid,pic,nickname,context);
//													} catch (Exception e) {
//														
//													}
//												};
//											}.start();
//										}
//									}
//								}
//							}
//						});
//					}
//
//					@Override
//					public void onCancel(SHARE_MEDIA platform) {
//						
//					}
//				});
//
//	}
//	
//
//}
