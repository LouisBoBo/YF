//package com.yssj.ui.activity.myshop;
//
//import com.yssj.YUrl;
//import com.yssj.activity.R;
//import com.yssj.custom.view.RedPacketsPopupwindow;
//import com.yssj.entity.Store;
//import com.yssj.entity.UserInfo;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.LogYiFu;
//import com.yssj.utils.NetworkUtils;
//import com.yssj.utils.YCache;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.View;
//import android.webkit.JavascriptInterface;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
//public class RedPacketsListActivity extends BasicActivity {
//
//	private WebView webview;
//	private FrameLayout loading_view;
////	private String url = YUrl.YSS_URL_ANDROID_H5
////			+ "view/hb/mysents_app.html?uid=oj8JHt-fZZRAdTLzY9Nwr-rDznWw";
//	private String url = YUrl.YSS_URL_ANDROID_H5+"view/hb/mysents_app.html?uid=oj8JHt-fZZRAdTLzY9Nwr-rDznWw";
//
//	private Store store;
//
//	private ImageView img_to_mine_like;
//	private FrameLayout fl;
//	
//	private TextView tvTitle_base;
//
//	private View title;
//	private UserInfo userInfo;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		getActionBar().hide();
//
//		setContentView(R.layout.choose_model_activity);
//
//		webview = (WebView) findViewById(R.id.webview);
//		loading_view = (FrameLayout) findViewById(R.id.loading_view);
//
//		fl = (FrameLayout)findViewById(R.id.fl);
//		url = YUrl.YSS_URL_ANDROID_H5+"view/hb/mysents_app.html?uid="+YCache.getCacheUser(context).getUuid();
//
//		title = findViewById(R.id.title);
//		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
//		findViewById(R.id.img_back).setOnClickListener(this);
//		tvTitle_base.setText("红包列表");
//		if (NetworkUtils.haveNetworkConnection(this)) {
//			initData();
//		} else {
//			webview.loadUrl("file:///android_asset/error.html");
//		}
//	}
//
//	private class upLoadImage {
//		private Context context;
//
//		public upLoadImage(Context context) {
//			super();
//			this.context = context;
//		}
//
//		@JavascriptInterface
//		public void h5Back() {
//			// onBackPressed();
//			RedPacketsListActivity.this.finish();
//		}
//		@JavascriptInterface
//		public void shareRedPackets(String shareSrc,String name,String word,String wordCircle
//				) {
////			ShareUtil.shareRedPackets(context, shareSrc,name,word);
//			RedPacketsPopupwindow myPopupwindow = new RedPacketsPopupwindow(RedPacketsListActivity.this, shareSrc,name,word,wordCircle);
////			myPopupwindow.setGou(true);
////			if(ShopDetailsActivity.instance!=null){
//				myPopupwindow.showAtLocation(
//						RedPacketsListActivity.this.getWindow().getDecorView(), Gravity.BOTTOM, 0,
//						0);
//			
//		}
//	}
//
//	private void initData(){
//		store = YCache.getCacheStore(this);
//		userInfo = YCache.getCacheUserSafe(this);
//		
////		if(null == userInfo.getHobby()||userInfo.getHobby().equals("0")){
////			img_to_mine_like.setVisibility(View.VISIBLE);
////			fl.setVisibility(View.GONE);
////			title.setVisibility(View.VISIBLE);
////			return;
////		} else {
//////			img_to_mine_like.setVisibility(View.GONE);
//////			fl.setVisibility(View.VISIBLE);
//////			title.setVisibility(View.GONE);
////		}
//		webview.getSettings().setJavaScriptEnabled(true);
//		webview.getSettings().setLoadWithOverviewMode(true);
////		if (store != null) {
////			url += "?realm=" + store.getRealm() + "&isAndroid=true";
////			MyLogYiFu.e("url", url);
////		}
//
//		webview.getSettings().setAllowFileAccess(true);
//
//		webview.setWebViewClient(new WebViewClient() {
//
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				view.loadUrl(url);
//				return true;
//			}
//
//			@Override
//			public void onReceivedError(WebView view, int errorCode,
//					String description, String failingUrl) {
//				super.onReceivedError(view, errorCode, description, failingUrl);
//				if (errorCode == -6) {
//					webview.loadUrl("file:///android_asset/error.html");
//				}
//
//			}
//
//			@Override
//			public void onPageStarted(WebView view, String url, Bitmap favicon) {
//				super.onPageStarted(view, url, favicon);
//				// LoadingDialog.show("", getSupportFragmentManager());
//			}
//
//			@Override
//			public void onPageFinished(WebView view, String url) {
//				// loading_view.setVisibility(View.GONE);
//				// LoadingDialog.hide(getSupportFragmentManager());
//				super.onPageFinished(view, url);
//			}
//
//		});
//		webview.loadUrl(url);
//		LogYiFu.e("url", url);
//		webview.addJavascriptInterface(new upLoadImage(
//				RedPacketsListActivity.this), "android");
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
////		case R.id.img_to_mine_like:
////			Intent intent = new Intent(this, MineLikeActivity.class);
////			startActivityForResult(intent, requestCode);
////			break;
//		case R.id.img_back:
//			finish();
//			break;
//		default:
//			break;
//		}
//	}
//	
//	public static final int requestCode = 1001;
//
//	@Override
//	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(arg0, arg1, arg2);
//		if(arg0 == 0){
//			initData();
//		}
//	}
//	@Override
//	public void onBackPressed() {
//		if (webview.canGoBack()) {
//			webview.goBack();
//		} else {
//			super.onBackPressed();
//		}
//	}
//}
