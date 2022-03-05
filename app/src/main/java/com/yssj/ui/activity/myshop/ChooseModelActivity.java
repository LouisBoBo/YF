package com.yssj.ui.activity.myshop;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.yssj.YConstance;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.entity.Store;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.infos.MyInfoActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.upyun.UpYunException;
import com.yssj.upyun.UpYunUtils;
import com.yssj.upyun.Uploader;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.NetworkUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.TakePhotoUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;


public class ChooseModelActivity extends BasicActivity {

	private WebView webview;
	private FrameLayout loading_view;
//	private String url = YUrl.YSS_URL_ANDROID_H5 + "view/decorate_shop/keepHome.jsp";
	private String url = YUrl.YSS_URL_ANDROID_H5 + "view/store/edit.jsp";
	
	// 测试字体
//	private String url = "http://hxgdzyuyi.github.io/blog/chinese-subset.html";

	private Store store;
	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" }) 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getActionBar().hide();

		setContentView(R.layout.choose_model_activity);

		webview = (WebView) findViewById(R.id.webview);
		
		loading_view = (FrameLayout) findViewById(R.id.loading_view);

		webview.getSettings().setJavaScriptEnabled(true);
		
		store = YCache.getCacheStore(this);
		
		if(NetworkUtils.haveNetworkConnection(this)){
			initData();
		}else{
			webview.loadUrl("file:///android_asset/error.html");
		}
		
		webview.addJavascriptInterface(new upLoadImage(ChooseModelActivity.this), "android");
	}

	private void initData() {
		
		if (store != null) {
			url += "?realm=" + store.getRealm(this) + "&token=" + YCache.getCacheToken(this)+"&isAndroid=true";
		}

		webview.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
			
			
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				if(errorCode == -6){
					webview.loadUrl("file:///android_asset/error.html");
				}
				
			}
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
//				LoadingDialog.show("", getSupportFragmentManager());
			}
		
			@Override
			public void onPageFinished(WebView view, String url) {
//				loading_view.setVisibility(View.GONE);
//				LoadingDialog.hide(getSupportFragmentManager());
				super.onPageFinished(view, url);
			}

		});
		webview.loadUrl(url);
	}
	
	private class upLoadImage{
		private Context context;

		public upLoadImage(Context context) {
			super();
			this.context = context;
		}
		@JavascriptInterface
		public void show(){
			TakePhotoUtil.doPickPhotoAction(ChooseModelActivity.this);
		}
		
		@JavascriptInterface
		public void shopDetail(String code){
			Intent intent=new Intent(context, ShopDetailsActivity.class);
			intent.putExtra("code", code);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);;
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == TakePhotoUtil.RESULT_LOAD_PICTURE) {
				submit("sdcard/temp.jpg");
			}else if(requestCode == 10086){
				TakePhotoUtil.cropImageUri(this,data==null?null:data.getData());
			}
		}
	}
	
    public void onBackPressed() {
        if(webview.canGoBack()) {
        	webview.goBack();
        } else {
            super.onBackPressed();
        }
    }
    
    private void submit(String path) {

		new SAsyncTask<String, Void, String>(this, R.string.wait) {

			@Override
			protected String doInBackground(FragmentActivity context,
					String... params) throws Exception {
				String string = null;

				try {
					// 设置服务器上保存文件的目录和文件名，如果服务器上同目录下已经有同名文件会被自动覆盖的。
					String SAVE_KEY = File.separator + "userinfo/head_pic"
							+ File.separator + System.currentTimeMillis()
							+ ".jpg";

					// 取得base64编码后的policy
					String policy = UpYunUtils.makePolicy(SAVE_KEY,
							Uploader.EXPIRATION, Uploader.BUCKET);

					// 根据表单api签名密钥对policy进行签名
					// 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
					String signature = UpYunUtils.signature(policy + "&"
							+ Uploader.TEST_API_KEY);

					// 上传文件到对应的bucket中去。
					string = Uploader.upload(policy, signature,
							Uploader.BUCKET, params[0]);

				} catch (UpYunException e) {
					e.printStackTrace();
				}

				return string;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result);
				if (result != null) {
					LogYiFu.e("result", result);
					//提交服务器请求
					submitUserImg(result);
				}

			}

		}.execute(path);
	}
    
    private void submitUserImg(String picPath){
		new SAsyncTask<String, Void, UserInfo>(this, R.string.wait){

			@Override
			protected UserInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.resetUserPic(context, params[0]);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					UserInfo result, Exception e) {
				// TODO Auto-generated method stub
//				SetImageLoader.initImageLoader(context, img_user,
//						result.getPic());
//				ToastUtil.showShortText(context, "操作成功");
//				userInfoUpdate.update();
				if(null == e){
				webview.loadUrl(url);
				}
				super.onPostExecute(context, result, e);
			}
			
		}.execute(picPath);
	}
}
