package com.yssj.ui.activity.myshop;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;

public class Model1Activity extends BasicActivity {

	private WebView web_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.model_one);
		web_view = (WebView) findViewById(R.id.web_view);
		web_view.getSettings()
				.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//		web_view.loadUrl("http://192.168.1.119:8080/test/hello.jsp");
		web_view.loadUrl("http://192.168.10.10:8081/springmvc001/btest1.jsp");
		// 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		web_view.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}
		});
	}
}
