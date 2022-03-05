package com.yssj.ui.activity.setting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.NetworkUtils;
import com.yssj.utils.WXminiAppUtil;

public class UserProtocolActivity extends BasicActivity implements OnClickListener {
    private LinearLayout img_back, fankui;
    private ImageView img_right_icon;

    private FrameLayout loading_view;
    String url = YUrl.YSS_URL_ANDROID_H5 + "view/other/userPortocol.jsp";
    private WebView wv_user_protocol;
    private boolean value;
    private boolean isYSQX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();

        setContentView(R.layout.activity_user_protocol);

        TextView tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("用户服务协议");


        isYSQX = getIntent().getBooleanExtra("isYSQX", false);
        if (isYSQX) {
            tvTitle_base.setText("隐私政策");
            url = "http://pass.52yifu.wang/yssj-h5/page/Privacy/privacy.html";
        }


        fankui = (LinearLayout) findViewById(R.id.fankui);
        fankui.setBackgroundColor(Color.WHITE);
        img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
        img_right_icon.setVisibility(View.GONE);
        img_right_icon.setImageResource(R.drawable.mine_message_center);
        img_right_icon.setOnClickListener(this);

        img_back = (LinearLayout) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        wv_user_protocol = (WebView) findViewById(R.id.wv_user_protocol);

        loading_view = (FrameLayout) findViewById(R.id.loading_view);
        value = getIntent().getBooleanExtra("value", false);
        if (value) {
            img_right_icon.setVisibility(View.GONE);
        }

        initData();

        img_right_icon.setVisibility(View.GONE);
    }


    private void initData() {
        wv_user_protocol.getSettings().setJavaScriptEnabled(true);

        wv_user_protocol.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (errorCode == -6) {
                    wv_user_protocol.loadUrl("file:///android_asset/error.html");
                }

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LoadingDialog.show(UserProtocolActivity.this);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//				loading_view.setVisibility(View.GONE);
                super.onPageFinished(view, url);
                LoadingDialog.hide(UserProtocolActivity.this);
            }

        });
        wv_user_protocol.loadUrl(url);
    }

    public void onBackPressed() {
        if (wv_user_protocol.canGoBack()) {
            wv_user_protocol.goBack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;

            case R.id.img_right_icon:
                WXminiAppUtil.jumpToWXmini(this);

                break;

            default:
                break;
        }
    }
}
