package com.yssj.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.wxpay.WxPayUtil;

/**
 * Created by Administrator on 2020/5/16.
 */

public class WXminiAppUtil {

    public static void jumpToWXmini(Context mContext) {


        if(YJApplication.instance.isLoginSucess() && YCache.getCacheUser(mContext).getReviewers() == 1){
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            final Dialog deleteDialog = new Dialog(mContext, R.style.invate_dialog_style);
            View view = mInflater.inflate(R.layout.dialog_one_text_one_button, null);

            TextView tv1 = view.findViewById(R.id.tv1);
            TextView btn_ok = view.findViewById(R.id.btn_ok);
            tv1.setText("请您添加客服QQ：3354501662");
            btn_ok.setText("知道了");
            view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    deleteDialog.dismiss();

                }
            });
            view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    deleteDialog.dismiss();

                }
            });


            deleteDialog.setCanceledOnTouchOutside(false);
            deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            ToastUtil.showDialog(deleteDialog);


            return;
        }


        String appId = WxPayUtil.APP_ID; // 填应用AppId
        if (StringUtils.isEmpty(appId)) {
            appId = YUrl.APP_ID;
        }

        IWXAPI api = WXAPIFactory.createWXAPI(mContext, appId);
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = YUrl.WX_MINIAPP_ORIGINAL_ID; // 填小程序原始id
        req.path = "/pages/mine/AppMessage/AppMessage?fromApp=1"; ////拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
        // 可选打开 开发版，体验版和正式版
        if (YUrl.wxMiniDedug) {
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_TEST;
        } else {
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
        }
        api.sendReq(req);

    }
}
