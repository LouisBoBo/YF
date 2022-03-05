package com.yssj.activity.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yssj.wxpay.WxPayUtil;
import com.yssj.wxpay.bean.WeiXin;

import org.greenrobot.eventbus.EventBus;

/**
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI wxAPI;
    public interface WXminiAPPshareListener {
        void wxMiniShareSuccess();
    }


    public static WXminiAPPshareListener wXminiAPPshareListener;

    //设置微信小程序分享监听回调，在需要监听的地方调用此方法
    public static void setWXminiShareListener(WXminiAPPshareListener listener) {
        wXminiAPPshareListener = listener;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wxAPI = WXAPIFactory.createWXAPI(this, WxPayUtil.APP_ID, true);
        wxAPI.registerApp(WxPayUtil.APP_ID);
        wxAPI.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        wxAPI.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq arg0) {
        Log.i("ansen", "WXEntryActivity onReq:" + arg0);
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {//分享
            WeiXin weiXin = new WeiXin(2, resp.errCode, "");
//            EventBus.getDefault().post(weiXin);
            wXminiAPPshareListener.wxMiniShareSuccess();
        } else if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {//登陆
            SendAuth.Resp authResp = (SendAuth.Resp) resp;
            WeiXin weiXin = new WeiXin(1, resp.errCode, authResp.code);
            EventBus.getDefault().post(weiXin);
        }
        finish();
    }
}
