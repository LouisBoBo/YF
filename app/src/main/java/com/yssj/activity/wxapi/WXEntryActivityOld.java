//package com.yssj.activity.wxapi;
//
//import android.os.Bundle;
//import android.widget.Toast;
//
//import com.tencent.mm.opensdk.modelbase.BaseReq;
//import com.tencent.mm.opensdk.modelbase.BaseResp;
//import com.tencent.mm.opensdk.modelmsg.SendAuth;
//import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.bean.SocializeEntity;
//import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
//import com.umeng.socialize.weixin.view.WXCallbackActivity;
//import com.yssj.activity.R;
//import com.yssj.utils.LogYiFu;
//import com.yssj.utils.WXminiAPPShareUtil;
//
//public class WXEntryActivityOld extends WXCallbackActivity implements IWXAPIEventHandler {
//
//
//
//    public static WXminiAPPshareListener wXminiAPPshareListener;
//
//    //设置微信小程序分享监听回调，在需要监听的地方调用此方法
//    public static void setWXminiShareListener(WXminiAPPshareListener listener) {
//        wXminiAPPshareListener = listener;
//    }
//
//
//
//    SnsPostListener mSnsPostListener = new SnsPostListener() {
//
//        @Override
//        public void onStart() {
//
//        }
//
//        @Override
//        public void onComplete(SHARE_MEDIA platform, int stCode,
//                               SocializeEntity entity) {
//            if (stCode == 200) {
////					Toast.makeText(WXEntryActivity.this, "分享成功", Toast.LENGTH_SHORT)
////							.show();
//            } else {
//                Toast.makeText(WXEntryActivityOld.this,
//                        "分享失败 : error code : " + stCode, Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }
//    };
//
//
//
//    @Override
//    protected void onCreate(Bundle arg0) {
//        super.onCreate(arg0);
//
//
////		wXminiAPPshareListener = (WXminiAPPshareListener) this;
//        //注意：
//        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
//        try {
//            boolean wxIstrue = WXminiAPPShareUtil.wXapi.handleIntent(getIntent(), this);
//            LogYiFu.e("微信官方API分享wxIstrue:", "11111111111111" + wxIstrue);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onReq(BaseReq baseReq) {
//
//    }
//
//
//    @Override
//    public void onResp(BaseResp resp) {
//        LogYiFu.e("微信官方API分享", "11111111111111" + resp);
//        int result = 0;
//
//        switch (resp.errCode) {
//            // 用户同意
//            case BaseResp.ErrCode.ERR_OK:
//                if(resp.getType() == 1){
//                    SendAuth.Resp respAll = (SendAuth.Resp)resp;
//                    if(respAll.state.equals("wechat_login")){
//
//
//                    }else if(respAll.state.equals("wechat_bind")){
//
//
//                    }
//                }else{
//                    wXminiAPPshareListener.wxMiniShareSuccess();
//                }
//                finish();
//                break;
//
//            case BaseResp.ErrCode.ERR_USER_CANCEL://发送取消
//                result = R.string.errcode_cancel;
//                break;
//            case BaseResp.ErrCode.ERR_AUTH_DENIED://发送被拒绝
//                result = R.string.errcode_deny;
//                break;
//            case BaseResp.ErrCode.ERR_UNSUPPORT://不支持错误
//                result = R.string.errcode_unsupported;
//                break;
//            default:
//                result = R.string.errcode_unknown;//发送返回默认给返回
//                break;
//        }
//
////        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
//        finish();
//    }
//
//	/*@Override
//    protected void onResume() {
//		super.onResume();
//		JPushInterface.onResume(this);
//	}
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//		JPushInterface.onPause(this);
//
//	}*/
//
//    public interface WXminiAPPshareListener {
//        void wxMiniShareSuccess();
//    }
//
//
//}