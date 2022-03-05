package com.yssj.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
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
import com.yssj.entity.Choujiang20Data;
import com.yssj.entity.NewPTdetailShop;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelL;
import com.yssj.model.ComModelZ;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.OneBuyGroupsDetailsActivity;
import com.yssj.ui.activity.SignDrawalLimitActivity;
import com.yssj.ui.activity.vip.MyVipListActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongjiShareCount;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;

import java.util.HashMap;

import static com.yssj.ui.activity.OneBuyGroupsDetailsActivity.GO_TO_BUY_MPK;

public class OneBuyGroupsShareHasMPKDialog extends Dialog implements View.OnClickListener {

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
    private boolean isMeal;
    private Activity mActivity;
    private NewPTdetailShop.DataBean shop;
    private int free_Number;
    private View ivWx;

    private ImageView iv_jia2;
    private ImageView iv_jia3;
    private ImageView iv_jia1;
    private TextView bt1;
    private TextView tv1;
    private TextView tv2;


    public OneBuyGroupsShareHasMPKDialog(Context context, int needs, NewPTdetailShop.DataBean shop, String r_code, boolean isMeal, int free_Number) {
        super(context);
        setCanceledOnTouchOutside(false);
        this.mContext = context;
        this.needs = needs;
        this.isMeal = isMeal;
        this.r_code = r_code;
        this.free_Number = free_Number;
        this.shop = shop;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_groups_has_mpk_share);
        mActivity = (Activity) mContext;
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //在0.0f和1.0f之间，0.0f完全不暗，1.0f全暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.75f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        ivWx = findViewById(R.id.ll_wxin);
        View ivFriends = findViewById(R.id.ll_wxin_circle);
        ivWx.setOnClickListener(this);
        ivFriends.setOnClickListener(this);

        if (null == shareWaitDialog) {
            shareWaitDialog = new PublicToastDialog(mContext, R.style.DialogStyle1, "");
        }
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

        iv_jia2 = findViewById(R.id.iv_jia2);
        iv_jia3 = findViewById(R.id.iv_jia3);
        iv_jia1 = findViewById(R.id.iv_jia1);
        bt1 = findViewById(R.id.bt1);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
    }

    private void initData() {
        needTv.setIncludeFontPadding(false);
        String needsNum = needs + "";
//        String needsText = "还差" + needs + "人，赶快邀请好友来参团吧，组团成功后即可参与1折疯抢。";
        String needsText = "还差" + needs + "人，赶快邀请好友来参团吧。参团免费，人满成团后才付款。";

        SpannableString spanString = new SpannableString(needsText);
        spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3F8B")), 2, needsNum.length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(new AbsoluteSizeSpan(DP2SPUtil.sp2px(mContext, 20)), 2, needsNum.length() + 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 2, needsNum.length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        needTv.setText(spanString);
//        needTv.setText(needsText);


        tv1.setText(Html.fromHtml("该商品需要<font color='#FFCF00'>3</font>张免拼卡即可免拼发货"));

        if (free_Number > 0) {
            tv1.setText(Html.fromHtml("商品原价超过<font color='#FFCF00'>119</font>元，需要<font color='#FFCF00'>3</font>张免拼卡即可免拼发货。"));

        }


        tv2.setText(Html.fromHtml("您已有<font color='#FFCF00'>" + free_Number + "</font>张还差<font color='#FFCF00'>" + (3 - free_Number) + "</font>张"));
        iv_jia1.setImageResource(R.drawable.free_fight);
        iv_jia2.setImageResource(R.drawable.free_fight);
        iv_jia3.setImageResource(R.drawable.free_fight);
        bt1.setBackgroundResource(R.drawable.continue_ling_freefightting);

        switch (free_Number) {
            case 1:
                iv_jia2.setImageResource(R.drawable.icon_fahuoka_jia);
                iv_jia3.setImageResource(R.drawable.icon_fahuoka_jia);
                break;
            case 2:
                iv_jia3.setImageResource(R.drawable.icon_fahuoka_jia);
                break;

        }

        findViewById(R.id.rl_jia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();


//                mActivity.startActivityForResult(new Intent(mActivity, MyVipListActivity.class)
//                        .putExtra("isGuideMPK", true), OneBuyGroupsDetailsActivity.GO_TO_BUY_MPK);


                HashMap<String, String> map = new HashMap<>();
                YConn.httpPost(mContext, YUrl.QUERY_TIQIAN_TXCJ, map, new HttpListener<Choujiang20Data>() {
                    @Override
                    public void onSuccess(Choujiang20Data result) {
                        if ("1".equals(result.getStatus())) {
                            if (result.getData().getIs_finish() == 1) {
                                if (null != SignDrawalLimitActivity.instance) {
                                    SignDrawalLimitActivity.instance.finish();
                                }

                                Intent txcjIntent = new Intent(mContext, SignDrawalLimitActivity.class);
                                txcjIntent.putExtra("type", 1)
                                        .putExtra("fromPT", true);

                                mContext.startActivity(txcjIntent);
                                ((FragmentActivity) mContext).overridePendingTransition(
                                        R.anim.slide_left_in, R.anim.slide_match);
                            } else {
                                mActivity.startActivityForResult(new Intent(mActivity, MyVipListActivity.class)
                                        .putExtra("isGuideMPK", true), OneBuyGroupsDetailsActivity.GO_TO_BUY_MPK);

                            }
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });


            }
        });

        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
//                mActivity.startActivityForResult(new Intent(mActivity, MyVipListActivity.class)
//                        .putExtra("isGuideMPK", true), OneBuyGroupsDetailsActivity.GO_TO_BUY_MPK);



                HashMap<String, String> map = new HashMap<>();
                YConn.httpPost(mContext, YUrl.QUERY_TIQIAN_TXCJ, map, new HttpListener<Choujiang20Data>() {
                    @Override
                    public void onSuccess(Choujiang20Data result) {
                        if ("1".equals(result.getStatus())) {
                            if (result.getData().getIs_finish() == 1) {
                                if (null != SignDrawalLimitActivity.instance) {
                                    SignDrawalLimitActivity.instance.finish();
                                }

                                Intent txcjIntent = new Intent(mContext, SignDrawalLimitActivity.class);
                                txcjIntent.putExtra("type", 1)
                                        .putExtra("fromPT", true);

                                mContext.startActivity(txcjIntent);
                                ((FragmentActivity) mContext).overridePendingTransition(
                                        R.anim.slide_left_in, R.anim.slide_match);
                            } else {
                                mActivity.startActivityForResult(new Intent(mActivity, MyVipListActivity.class)
                                        .putExtra("isGuideMPK", true), OneBuyGroupsDetailsActivity.GO_TO_BUY_MPK);

                            }
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_close:
                this.dismiss();
                break;
            case R.id.ll_wxin_circle: // 分享到微信朋友圈

                ivWx.performClick();

//                if (!mWxInstallFlag) {
//                    ToastUtil.showLongText(mContext, "您还未安装微信喔！");
//                    return;
//                }

//                if (null == shareWaitDialog) {
//                    shareWaitDialog = new PublicToastDialog(mContext, R.style.DialogStyle1, "");
//                }
//                shareWaitDialog.show();
//                shareType = 1;
//                ShareUtil.addWXPlatform(mContext);
//                getShareContent();
//                this.dismiss();


                break;
            case R.id.ll_wxin: // 分享到微信好友

                if (!mWxInstallFlag) {
                    ToastUtil.showLongText(mContext, "您还未安装微信喔！");
                    return;
                }
//                shareType = 0;
//                ShareUtil.addWXPlatform(mContext);
//                getShareContent();
//                this.dismiss();

                shareWaitDialog.show();
                new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mActivity, R.string.wait) {

                    @Override
                    protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                            throws Exception {

                        return ComModel2.getShopLink(params[0], context, "true");
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

                                if (isMeal) {

                                    String shareMIniAPPimgPic = YUrl.imgurl + shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/" + result.get("def_pic") + "!280";
                                    XLog.e("sharePic:" + shareMIniAPPimgPic);
//                                    String wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + shop.getShop_code() +
//                                            "&isShareFlag=true&user_id=" + YCache.getCacheUser(mActivity).getUser_id() + "&roll_code=" + r_code;
                                    String onPrice = shop.getAssmble_price();
//                                    OneBuyChouJiangActivity.mealShopName = shop.get("shop_name") + "";
                                    String shareText = "快来" + onPrice + "元拼【" + shop.getShop_name() + "】，原价" + shop.getShop_se_price() + "元！";

                                    //分享到微信统一分享小程序
//                                    WXminiAPPShareUtil.shareToWXminiAPP(mActivity, shareMIniAPPimgPic, shareText, wxMiniPathdUO, false);
//                                    WXminiAPPShareUtil.shareShopToWXminiAPP
//                                            (mActivity, shareText,
//                                                    shop.getAssmble_price(),
//                                                    shareMIniAPPimgPic,
//                                                    wxMiniPathdUO,
//                                                    false);


                                    String wxMiniPathdUO = "/pages/shouye/fightDetail/fightDetail?shop_code=" + shop.getShop_code() +
                                            "&isShareFlag=true&user_id=" + YCache.getCacheUser(mActivity).getUser_id() + "&code=" + r_code + "&isTM=1&isJoinGroup=true";

                                    //分享到微信统一分享小程序
                                    WXminiAPPShareUtil.sharePTtoWXminiAPP
                                            (mActivity, shareText,
                                                    shop.getAssmble_price(),
                                                    shareMIniAPPimgPic,
                                                    wxMiniPathdUO,
                                                    false);

//                                    WXEntryActivity.setWXminiShareListener(new WXEntryActivity.WXminiAPPshareListener() {
//                                        @Override
//                                        public void wxMiniShareSuccess() {
//                                            ToastUtil.showShortText(mActivity, "分享成功");
//
//                                        }
//                                    });


                                    if (null != shareWaitDialog) {
                                        shareWaitDialog.dismiss();
                                    }
                                    dismiss();

                                } else {
                                    getTyepe2SuppLabel(result.get("four_pic").toString());

                                }

                            }
                        } else {
                            if (null != shareWaitDialog) {
                                shareWaitDialog.dismiss();
                            }
                        }
                    }

                }.execute(shop.getShop_code());


                break;

            default:
                break;
        }

    }


    public void getTyepe2SuppLabel(final String four_pic) {
        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mActivity, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModelZ.geType2SuppLabe(mActivity, shop.getShop_code());
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null) {
                    //品牌名称
                    String sub_name = result.get("supp_label_id");


                    TongjiShareCount.tongjifenxiangCount();
                    TongjiShareCount.tongjifenxiangwho(shop.getShop_code());

                    String shareMIniAPPimgPic;
                    String wxMiniPathdUO;
                    String shareText;
                    String onPrice = shop.getAssmble_price();

                    shareMIniAPPimgPic = YUrl.imgurl + shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/" + four_pic.split(",")[2] + "!280";
                    XLog.e("sharePic:" + shareMIniAPPimgPic);
                    wxMiniPathdUO = "/pages/shouye/fightDetail/fightDetail?shop_code=" + shop.getShop_code() +
                            "&isShareFlag=true&user_id=" + YCache.getCacheUser(mActivity).getUser_id() + "&code=" + r_code + "&isTM=0&isJoinGroup=true";


                    shareText = "快来" + onPrice + "元拼【" + sub_name + "正品" + result.get("type2") + "】，专柜价" + shop.getShop_code() + "元!";


                    WXminiAPPShareUtil.sharePTtoWXminiAPP
                            (mActivity, shareText,
                                    shop.getAssmble_price(),
                                    shareMIniAPPimgPic,
                                    wxMiniPathdUO,
                                    false);


                    //分享到微信统一分享小程序
//                    WXminiAPPShareUtil.shareToWXminiAPP(mActivity, shareMIniAPPimgPic, shareText, wxMiniPathdUO, false);

//                    WXminiAPPShareUtil.shareShopToWXminiAPP
//                            (mActivity, shop.getShop_name(),
//                                    shop.getAssmble_price(),
//                                    shareMIniAPPimgPic,
//                                    wxMiniPathdUO,
//                                    false);

//                    WXEntryActivity.setWXminiShareListener(new WXEntryActivity.WXminiAPPshareListener() {
//                        @Override
//                        public void wxMiniShareSuccess() {
//                            ToastUtil.showShortText(mActivity, "分享成功");
//
//                        }
//                    });


                    if (null != shareWaitDialog) {
                        shareWaitDialog.dismiss();
                    }
                    dismiss();


                } else {
                    if (null != shareWaitDialog) {
                        shareWaitDialog.dismiss();
                    }
                }
            }

        }.execute();
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
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HashMap<String, Object> m = new HashMap<String, Object>();
                            if (map != null) {
                                m = (HashMap<String, Object>) map.get(YConstance.KeyJT.KEY_JSONTEXT_PTGFX);
                            }
                            if (m != null && m.size() > 0) {
                                share((String) m.get("title"), (String) m.get("text"), (String) m.get("icon"));
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
        String link = YUrl.YSS_URL_ANDROID_H5 + "view/activity/pack.html?realm=" + YCache.getCacheUser(mContext).getUser_id() + "&r_code=" + r_code;
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
