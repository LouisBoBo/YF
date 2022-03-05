package com.yssj.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelL;
import com.yssj.ui.activity.shopdetails.NoShareActivity;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXcheckUtil;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class YiYuanDuoShareDialog extends Dialog implements View.OnClickListener {

    @Bind(R.id.tv_number)
    TextView tvNumber;
    @Bind(R.id.tv_shuoming_top)
    TextView tvShuomingTop;
    @Bind(R.id.tv_shuoming_bot)
    TextView tvShuomingBot;
    private Context mContext;
    private View tvClose;

    private boolean mWxInstallFlag;

    private Intent weixinShareIntent;// 实现对微信好友的分享：
    private Intent wXinShareIntent;// 实现对微信朋友圈的分享：
    public PublicToastDialog shareWaitDialog;
    private int shareType;

    //一元夺宝相关
    private boolean isYiYuan;
    private String shop_code = "";
    private String duobaoNumber = "";

    private String link = ""; //分享链接

    private String title1 = "";//大标题

    private String title2 = "";//小标题


    private String iconLink = "";//小图链接
    private String shop_price ="";
    private String d_pic ="";


    //一元夺宝构造  -----------赢提现额度
    public YiYuanDuoShareDialog(Context context, boolean isYiYuan, String shop_code, String duobaoNumber,String shop_price,String d_pic) {
        super(context);
        setCanceledOnTouchOutside(false);
        this.mContext = context;
        this.isYiYuan = isYiYuan;
        this.shop_code = shop_code;
        this.duobaoNumber = duobaoNumber;
        this.shop_price = shop_price;
        this.d_pic = d_pic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_yiyuan_duobao_share);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //在0.0f和1.0f之间，0.0f完全不暗，1.0f全暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.75f;
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

    private void initData() {
        //夺宝号只取前两个
        if (null != duobaoNumber && duobaoNumber.length() > 0) {
            String[] duo = duobaoNumber.split(",");
            if (duo.length > 2) {
                duobaoNumber = duo[0] + "," + duo[1];
            }
        }
        tvNumber.setText("你的抽奖号为："+duobaoNumber);


        //设置上下文案
        setText();

    }

    private void setText() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJT.KEY_JSONTEXT_YYDB_TISHITEXT);
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            try {
                                HashMap<String, Object> m = (HashMap<String, Object>) map.get(YConstance.KeyJT.KEY_JSONTEXT_YYDB_TISHITEXT);
                                if (m != null && m.size() > 0) {
                                    //处理数据
                                    tvShuomingTop.setText(m.get("text1") + "");
                                    tvShuomingBot.setText(m.get("text2") + "");

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private void initView() {
        tvClose = findViewById(R.id.icon_close);
        tvClose.setOnClickListener(this);
    }


    /**
     * 得到分享的链接
     */
    public void share() {


        new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                return ComModel2.getIndianashopLink(params[0], context, "true");
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result.get("status").equals("1")) {


                        //拿到分享链接
                        link = result.get("link");

                        setShareTitle();


                    } else if (result.get("status").equals("1050")) {// 表明
                        Intent intent = new Intent(context, NoShareActivity.class);
                        intent.putExtra("isNomal", true);
                        context.startActivity(intent); // 分享已经超过了

                    }
                }
            }

        }.execute(shop_code);
    }


    private void setShareTitle() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJT.KEY_JSONTEXT_YYDB);
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            try {
                                HashMap<String, Object> m = (HashMap<String, Object>) map.get(YConstance.KeyJT.KEY_JSONTEXT_YYDB);
                                if (m != null && m.size() > 0) {
                                    //处理数据





                                    title1 = m.get("title") + "";

                                    try {
                                        title1 =  title1.replace("${replace}", ((int)Double.parseDouble(shop_price))+"");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                    title2 = m.get("text") + "";
                                    iconLink = m.get("icon") + "";

                                    share(title1, title2,iconLink);


                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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


                shareType = 1;
                ShareUtil.addWXPlatform(mContext);
//                getShareContent();

                share();


                this.dismiss();
                break;
            case R.id.ll_wxin: // 分享到微信好友

                if (!mWxInstallFlag) {
                    ToastUtil.showLongText(mContext, "您还未安装微信喔！");
                    return;
                }
                shareType = 0;
                ShareUtil.addWXPlatform(mContext);
//                getShareContent();
                share();
                this.dismiss();
                break;

            default:
                break;
        }

    }

    private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);

//    private void getShareContent() {
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
//
//            }
//
//        }.execute();
//
//
//    }

    private void share(String shareTitle, String shareContent, String sharePic) {
        wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
        weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());


//        String link = YUrl.YSS_URL_ANDROID_H5 + "view/activity/pack.html?realm=" + YCache.getCacheUser(mContext).getUser_id() + "&r_code=";


        UMImage umImage = new UMImage(mContext, YUrl.imgurl + d_pic);

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
