package com.yssj.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;
import com.yssj.Constants;
import com.yssj.YConstance;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.model.ComModelL;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import java.util.HashMap;

public class GroupsShareDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private View tvClose;
    private int needs;
    private TextView needTv;

    private boolean mWxInstallFlag;

    private Intent weixinShareIntent;// 实现对微信好友的分享：
    private Intent wXinShareIntent;// 实现对微信朋友圈的分享：
    public PublicToastDialog shareWaitDialog;
    private int shareType;
    private String r_code;

    public GroupsShareDialog(Context context, int needs, String r_code) {
        super(context);
        setCanceledOnTouchOutside(false);
        this.mContext = context;
        this.needs = needs;
        this.r_code = r_code;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_groups_share);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //在0.0f和1.0f之间，0.0f完全不暗，1.0f全暗
        WindowManager.LayoutParams lp=getWindow().getAttributes();
        lp.dimAmount=0.75f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        View ivWx = findViewById(R.id.ll_wxin);
        View ivFriends = findViewById(R.id.ll_wxin_circle);
        ivWx.setOnClickListener(this);
        ivFriends.setOnClickListener(this);


        try {
            // // 是否安装了微信
            if (WXcheckUtil.isWeChatAppInstalled(mContext)) {
                mWxInstallFlag = true;
            } else {
                mWxInstallFlag = false;
            }
        } catch (Exception e) {
        }


        initView();
        initData();
    }


    private void initView() {
        tvClose = findViewById(R.id.icon_close);
        tvClose.setOnClickListener(this);
        needTv = (TextView) findViewById(R.id.need_people);
    }

    private void initData() {
        needTv.setIncludeFontPadding(false);
        String needsNum = needs + "";
        String needsText = "还差"+needs+"人，赶紧邀请好友来参团吧！开团与参团都无需付款，拼团成功后记得要在规定时间内完成付款哦。";
        SpannableString spanString = new SpannableString(needsText);
        spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3F8B")), 2, needsNum.length()+2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(new AbsoluteSizeSpan(DP2SPUtil.sp2px(mContext, 20)), 2, needsNum.length()+2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 2, needsNum.length()+2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        needTv.setText(spanString);
//        needTv.setText(needsText);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_close:
                this.dismiss();
                break;
            case R.id.ll_wxin_circle: // 分享到微信朋友圈

                if (!mWxInstallFlag) {
                    ToastUtil.showLongText(mContext, "您还未安装微信喔！");
                    return;
                }

//                if (null == shareWaitDialog) {
//                    shareWaitDialog = new PublicToastDialog(mContext, R.style.DialogStyle1, "");
//                }
//                shareWaitDialog.show();
                shareType = 1;
                ShareUtil.addWXPlatform(mContext);
                getShareContent();
                this.dismiss();
                break;
            case R.id.ll_wxin: // 分享到微信好友

                if (!mWxInstallFlag) {
                    ToastUtil.showLongText(mContext, "您还未安装微信喔！");
                    return;
                }
                shareType = 0;
                ShareUtil.addWXPlatform(mContext);
                getShareContent();
                this.dismiss();
                break;

            default:
                break;
        }

    }

    private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);

    private void getShareContent() {
//        new SAsyncTask<Void, Void, List<HashMap<String, String>>>((FragmentActivity) mContext, R.string.wait) {
//
//            @Override
//            protected List<HashMap<String, String>> doInBackground(FragmentActivity context, Void... params) throws Exception {
//                return ComModelL.getGroupsShareContent(context);
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, List<HashMap<String, String>> result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (null == e && result != null && result.size() > 0) {
//                    share(result.get(0).get("title"), result.get(0).get("txt"), result.get(0).get("png"));
//                }
////                if (null != shareWaitDialog) {
////                    shareWaitDialog.dismiss();
////                }
//            }
//
//        }.execute();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJT.KEY_JSONTEXT_PTGFX);
                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HashMap<String, Object> m = new HashMap<String, Object>();
                            if(map!=null){
                                m = (HashMap<String, Object>) map.get(YConstance.KeyJT.KEY_JSONTEXT_PTGFX);
                            }
                            if(m!=null&&m.size()>0){
                                share((String)m.get("title"),(String)m.get("text"),(String)m.get("icon"));
                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void share(String shareTitle, String shareContent, String sharePic) {
        wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
        weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());
        String link = YUrl.YSS_URL_ANDROID_H5 + "view/activity/pack.html?realm=" + YCache.getCacheUser(mContext).getUser_id()+"&r_code=" + r_code;
        UMImage umImage = new UMImage(mContext, YUrl.imgurl + sharePic);

        if (shareType == 1) {// 微信好友朋友圈
            SharedPreferencesUtil.saveStringData(mContext, "messageSubSub", shareTitle);
            ShareUtil.setShareContent(mContext, umImage, shareTitle, link);
            performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
        } else if (shareType == 0) {// 微信好友
//            WeiXinShareContent wei = new WeiXinShareContent();
//            wei.setShareContent(shareContent);
//            wei.setTitle(shareTitle);
//            wei.setTargetUrl(link);
//            wei.setShareMedia(umImage);
//            mController.setShareMedia(wei);
//            performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);
        }

    }

    public void performShare(SHARE_MEDIA platform, final Intent intent) {

        mController.postShare(mContext, platform, new SocializeListeners.SnsPostListener() {

            @Override
            public void onStart() {
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                if (eCode == StatusCode.ST_CODE_SUCCESSED) {

                } else {

                }
                SocializeConfig.getSocializeConfig().cleanListeners();// 清空友盟回调监听器
                // 避免任务接口多次被调用


            }
        });
    }

}
