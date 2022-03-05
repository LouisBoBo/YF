package com.yssj.ui.fragment.setting;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.NetworkUtils;

public class SecureInfoTipFragment extends BaseFragment implements OnClickListener {
	
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private WebView webview;
	
	private FrameLayout loading_view;
	private String url = YUrl.YSS_URL_ANDROID_H5 + "view/other/accountSafeInfo.html";
	

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_common_webview, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("安全贴士");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		webview = (WebView) view.findViewById(R.id.webview);
		
		loading_view = (FrameLayout) view.findViewById(R.id.loading_view);
		
		return view;
	}

	@Override
	public void initData() {
		if(NetworkUtils.haveNetworkConnection(getActivity())){
			startWebView();
		}else{
			webview.loadUrl("file:///android_asset/error.html");
		}
	}
	
	

	private void startWebView() {
		webview.getSettings().setJavaScriptEnabled(true);
		
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
				LoadingDialog.show(getActivity());
			}
		
			@Override
			public void onPageFinished(WebView view, String url) {
//				loading_view.setVisibility(View.GONE);
				LoadingDialog.hide(getActivity());
				super.onPageFinished(view, url);
			}

		});
		webview.loadUrl(url);
	}
	
	public void onBackPressed() {
        if(webview.canGoBack()) {
        	webview.goBack();
        } else {
          getActivity().onBackPressed();
        }
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			SecureTipsFragment mFragment= new SecureTipsFragment();
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			break;

		}
	}


}
