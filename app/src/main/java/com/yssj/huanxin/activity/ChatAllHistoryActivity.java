package com.yssj.huanxin.activity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.entity.KeFuWXH;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXcheckUtil;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ChatAllHistoryActivity extends BasicActivity  {



    @Bind(R.id.tvTitle_base)
    TextView tvTitleBase;
    @Bind(R.id.tv_wxh)
    TextView tvWxh;
    @Bind(R.id.ll_wxin)
    LinearLayout llWxin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustomer_service);
        ButterKnife.bind(this);
        tvTitleBase.setText("联系客服");

        YConn.httpPost(this, YUrl.GET_WXH, new HashMap<String, String>(), new HttpListener<KeFuWXH>() {
            @Override
            public void onSuccess(KeFuWXH result) {
                tvWxh.setText(result.getWeChat() + "");
                llWxin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        StringUtils.copyString(ChatAllHistoryActivity.this, tvWxh.getText().toString().trim());
                        ToastUtil.showShortText2("客服微信号已复制");

                        boolean mWxInstallFlag = false;
                        try {
                            // // 是否安装了微信
                            if (WXcheckUtil.isWeChatAppInstalled(ChatAllHistoryActivity.this)) {
                                mWxInstallFlag = true;
                            } else {
                                mWxInstallFlag = false;
                            }
                        } catch (Exception e) {
                        }
                        if (mWxInstallFlag) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setComponent(cmp);
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                            }
                        }


                    }
                });
            }

            @Override
            public void onError() {

            }
        });

    }

    @OnClick({R.id.img_back, R.id.ll_wxin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;

        }
    }


}
