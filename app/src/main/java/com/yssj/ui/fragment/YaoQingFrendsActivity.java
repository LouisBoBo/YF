package com.yssj.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.DeviceConfig;
import com.yssj.Constants;
import com.yssj.YConstance;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.activity.wxapi.WXEntryActivity;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelZ;
import com.yssj.pubu.PubuFragment;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.PointLikeRankingActivity;
import com.yssj.ui.activity.shopdetails.NoShareActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.PublicToastDialog;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * ??????????????????----------?????????????????????????????????????????????-----------????????????????????????-----3????????????????????????
 */
public class YaoQingFrendsActivity extends BasicActivity implements OnClickListener, WXEntryActivity.WXminiAPPshareListener {
    private static Context mContext;
    private LinearLayout duobaojilu, ll_title_two, ll_title_one;

    private TextView tV_title_price_one;
    private TextView tV_title_price_two;
    private ImageButton imgbtn_left_icon, bt_back;
    private FrameLayout content_frame;
    private int type = 1; // 1=???????????????2= ???????????? (???????????????)
    private LinearLayout ll_wxin, ll_wxin_circle, ll_qq, ll_head, ll_share; // ????????????-----?????????----QQ
    public PublicToastDialog shareWaitDialog;
    private int shareType;
    private String tieZiLink;
    // ?????????????????????
    public static String tieziID = "";
    public static String tieziPIC = "";
    public static String tieziContent = "";

    // ?????????????????????
    public static String shopPIC = "";
    public static String shopCode = "";

    private String newShareTitle = "????????????????????????????????????39????????????????????????";
    private String newShareText = "";

    private boolean mQqInstallFlag = true;// true???????????????qq
    private boolean mWxInstallFlag = true;// true?????????????????????
    private boolean mQqZone = true;// true???????????????QQ??????

    public static boolean isComplete;// ???????????????????????????????????????

    public static boolean isFabu; // ????????????????????????

    public static boolean isAddLike;// ??????????????????

    private String jumpFrom = "";
    private LinearLayout ll_tou;// ?????????
    private String str21;
    private String four_pic;

    public YaoQingFrendsActivity() {
        super();
    }

    int randNum;//0???1??????1??? 0??????1???

    private Context context;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_yaoqing_frends);
        mContext = this;
        context = this;
        tieziID = "";
        tieziPIC = "";
        tieziContent = "";
        shopPIC = "";
        shopCode = "";
        jumpFrom = getIntent().getStringExtra("jumpFrom");
        isComplete = getIntent().getBooleanExtra("isComplete", false);
        isFabu = getIntent().getBooleanExtra("isFabu", false);
        isAddLike = getIntent().getBooleanExtra("isAddLike", false);


        WXEntryActivity.setWXminiShareListener(this);


        try {
            // ???????????????QQ
            if (DeviceConfig.isAppInstalled("com.tencent.mobileqq", mContext)) {
                mQqInstallFlag = true;
            } else {
                mQqInstallFlag = false;
            }
        } catch (Exception e) {
        }

        try {
            // // ?????????????????????
            if (WXcheckUtil.isWeChatAppInstalled(mContext)) {
                mWxInstallFlag = true;
            } else {
                mWxInstallFlag = false;
            }
        } catch (Exception e) {
        }
        try {
            // // ???????????????QQ???????????????
            if (DeviceConfig.isAppInstalled("com.tencent.sc.activity.SplashActivity", mContext)) {
                mQqZone = true;
            } else {
                mQqZone = false;
            }
        } catch (Exception e) {
        }

        String[] value = SignListAdapter.doValue.split(",");
        try {
            shareNum = Integer.parseInt(value[0]);
        } catch (Exception e) {
            shareNum = SignListAdapter.doNum;
        }

        initView();
    }

    private void initView() {
        duobaojilu = (LinearLayout) findViewById(R.id.root);
        duobaojilu.setBackgroundColor(Color.WHITE);
        ll_title_one = (LinearLayout) findViewById(R.id.ll_title_one);// ????????????
        ll_title_two = (LinearLayout) findViewById(R.id.ll_title_two);// ????????????
        tV_title_price_one = (TextView) findViewById(R.id.TV_title_price_one);
        tV_title_price_two = (TextView) findViewById(R.id.TV_title_price_two);
        TextView tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        imgbtn_left_icon = (ImageButton) findViewById(R.id.imgbtn_left_icon);
        ll_tou = (LinearLayout) findViewById(R.id.ll_tou);
        imgbtn_left_icon.setOnClickListener(this);
        content_frame = (FrameLayout) findViewById(R.id.content_frame);

        shareWaitDialog = new PublicToastDialog(mContext, R.style.DialogStyle1, "");

        ll_wxin = (LinearLayout) findViewById(R.id.ll_wxin);
        ll_wxin_circle = (LinearLayout) findViewById(R.id.ll_wxin_circle);

        //???????????????????????????
        ll_wxin_circle.setVisibility(View.GONE);

        ll_qq = (LinearLayout) findViewById(R.id.ll_qq);

        bt_back = (ImageButton) findViewById(R.id.bt_back);

        ll_head = (LinearLayout) findViewById(R.id.ll_head);
        ll_share = (LinearLayout) findViewById(R.id.ll_share);


        if (isAddLike) {//???????????????????????????QQ????????????
            ll_qq.setVisibility(View.GONE);
//            ll_wxin.setVisibility(View.GONE);
        }


        ll_wxin.setOnClickListener(this);
        ll_wxin_circle.setOnClickListener(this);
        ll_qq.setOnClickListener(this);
        bt_back.setOnClickListener(this);


        getShareTitleText();

        if (isFabu) {// ????????????
            tV_title_price_one.setText("????????????");
            tV_title_price_two.setText("????????????");
            ll_head.setVisibility(View.GONE);
            ll_share.setVisibility(View.GONE);
            bt_back.setVisibility(View.VISIBLE);

            // ????????????----????????????????????????
            InviteHotFragment fragment2 = InviteHotFragment.newInstances(context, isFabu, 1);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment2).commit();

        } else {

            tV_title_price_one.setText("??????????????????");
            tV_title_price_two.setText("??????????????????");

            ll_head.setVisibility(View.VISIBLE);
            ll_share.setVisibility(View.VISIBLE);
            bt_back.setVisibility(View.GONE);

            if (("shareShop").equals(jumpFrom)) {// ????????????
                tvTitle_base.setText("??????????????????");
                ll_tou.setVisibility(View.GONE);
                type = 1;

                // ????????????
                InviteHotFragment fragment2 = InviteHotFragment.newInstances(context, isFabu, 0);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment2).commit();

            } else if (("shareTieZi").equals(jumpFrom)) {// ????????????
                tvTitle_base.setText("??????????????????");
                ll_tou.setVisibility(View.GONE);
                type = 2;
                // ????????????
                PubuFragment fragment = PubuFragment.newInstances(context, "intivate");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            } else {
                // ????????????

                if (isAddLike) {// ??????????????????
                    tvTitle_base.setText("??????????????????");

                } else {// ????????????
                    tvTitle_base.setText("??????????????????");

                }

                ll_tou.setVisibility(View.VISIBLE);
                InviteHotFragment fragment2 = InviteHotFragment.newInstances(context, isFabu, 0);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment2).commit();

            }

        }

        ll_title_one.setOnClickListener(this);
        ll_title_two.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_title_one:// ??????
                type = 1;
                tieziID = "";
                tieziPIC = "";
                tieziContent = "";
                ll_title_one.setBackgroundResource(R.drawable.jx_left_red);
                ll_title_two.setBackgroundResource(R.drawable.jx_right_white);
                tV_title_price_one.setTextColor(getResources().getColor(R.color.white));
                tV_title_price_two.setTextColor(getResources().getColor(R.color.zero_shop_choice));

                // ?????????????????????????????????

                InviteHotFragment fragment2 = InviteHotFragment.newInstances(context, isFabu, 1);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment2).commit();

                break;

            case R.id.ll_title_two:// ??????

                ll_title_one.setBackgroundResource(R.drawable.jx_left_white);
                ll_title_two.setBackgroundResource(R.drawable.jx_right_red);
                tV_title_price_one.setTextColor(getResources().getColor(R.color.zero_shop_choice));
                tV_title_price_two.setTextColor(getResources().getColor(R.color.white));

                if (isFabu) {

                    // ??????????????????
                    InviteHotFragment fragmentShop = InviteHotFragment.newInstances(context, isFabu, 2);
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentShop).commit();
                } else {
                    type = 2;
                    shopPIC = "";
                    shopCode = "";
                    // ????????????
                    PubuFragment fragment = PubuFragment.newInstances(context, "intivate");
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                }

                break;
            case R.id.imgbtn_left_icon:
                onBackPressed();
                break;
            case R.id.bt_back:
                onBackPressed();
                break;

            case R.id.ll_wxin_circle: // ????????????????????????

                if (!mWxInstallFlag) {
                    ToastUtil.showLongText(mContext, "???????????????????????????");
                    return;
                }

                if (type == 1) {// ????????????
                    if (TextUtils.isEmpty(shopCode)) {
                        ToastUtil.showShortText(mContext, "????????????????????????????????????");
                        return;
                    }
                } else if (type == 2) {// ????????????
                    if (TextUtils.isEmpty(tieziID)) {
                        ToastUtil.showShortText(mContext, "????????????????????????????????????");
                        return;
                    }
                }
                if (!WXcheckUtil.isWeChatAppInstalled(mContext)) {
                    ToastUtil.showShortText(mContext, "???????????????????????????~");
                } else {
                    shareWaitDialog.show();

                    shareType = 1;
                    ShareUtil.addWXPlatform(mContext);
                    if (type == 1) {// ????????????
                        SharedPreferencesUtil.saveStringData(context, "messageSubSub", StringUtils.getShareContentNew());
                        share(shopCode);
                    } else if (type == 2) {// ????????????
                        tieZiLink = YUrl.YSS_URL_ANDROID_H5 + "/views/topic/detail.html?theme_id=" + tieziID + "&realm="
                                + YCache.getCacheUser(mContext).getUser_id();
                        SharedPreferencesUtil.saveStringData(context, "messageSubSub", tieziContent);
                        createSharePic(tieZiLink, tieziPIC, "");
                    }
                }
                break;
            case R.id.ll_wxin: // ?????????????????????

                if (!mWxInstallFlag) {
                    ToastUtil.showLongText(mContext, "???????????????????????????");
                    return;
                }

                if (type == 1) {// ????????????
                    if (TextUtils.isEmpty(shopCode)) {
                        ToastUtil.showShortText(mContext, "????????????????????????????????????");

                        return;
                    }
                } else if (type == 2) {// ????????????
                    if (TextUtils.isEmpty(tieziID)) {
                        ToastUtil.showShortText(mContext, "????????????????????????????????????");

                        return;
                    }
                }
                if (!WXcheckUtil.isWeChatAppInstalled(mContext)) {
                    ToastUtil.showShortText(mContext, "???????????????????????????~");
                } else {
                    shareWaitDialog.show();
                    shareType = 0;

                    ShareUtil.addWXPlatform(mContext);
                    if (type == 1) {// ????????????
                        share(shopCode);
                    } else if (type == 2) {// ????????????
                        tieZiLink = YUrl.YSS_URL_ANDROID_H5 + "/views/topic/detail.html?theme_id=" + tieziID + "&realm="
                                + YCache.getCacheUser(mContext).getUser_id();
                        if (tieziPIC.equals("")) {
//                            weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());
//                            WeiXinShareContent wei = new WeiXinShareContent();
//                            // wei.setTitle("??????????????????????????????????????????");
//                            wei.setShareContent("??????APP");
//                            wei.setTitle(tieziContent);
//                            wei.setTargetUrl(tieZiLink);
//                            wei.setShareMedia(new UMImage(mContext, R.drawable.gerenzhongxin_morentouxiang_bg));
//                            mController.setShareMedia(wei);
//                            performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);

                        } else {
                            createSharePic(tieZiLink, tieziPIC, "");
                        }
                    }
                }
                break;
            case R.id.ll_qq: // ?????????QQ

                if (!mQqInstallFlag && !mQqZone) {
                    ToastUtil.showShortText(mContext, "????????????QQ???QQ?????????????????????");
                    return;
                }

                if (type == 1) {// ????????????
                    if (TextUtils.isEmpty(shopCode)) {
                        ToastUtil.showShortText(mContext, "????????????????????????????????????");

                        return;
                    }
                } else if (type == 2) {// ????????????
                    if (TextUtils.isEmpty(tieziID)) {
                        ToastUtil.showShortText(mContext, "????????????????????????????????????");

                        return;
                    }
                }
                if (!DeviceConfig.isAppInstalled("com.tencent.mobileqq", mContext)) {
                    ToastUtil.showShortText(mContext, "??????????????????qq???~");

                } else {
                    shareType = 2;
                    shareWaitDialog.show();
                    ShareUtil.addQQQZonePlatform(mContext);
                    if (type == 1) {// ????????????
                        share(shopCode);
                    } else if (type == 2) {// ????????????
                        tieZiLink = YUrl.YSS_URL_ANDROID_H5 + "/views/topic/detail.html?theme_id=" + tieziID + "&realm="
                                + YCache.getCacheUser(mContext).getUser_id();
                        createSharePic(tieZiLink, tieziPIC, "");
                    }
                }

                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
    }

    private Bitmap bmBg;
    private File file;
    private Intent qqShareIntent;
    private Intent wXinShareIntent;// ?????????????????????
    private Intent weixinShareIntent;// ??????????????????
    private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);

    private void createSharePic(final String link, final String picPath, final String shop_code) {
        new SAsyncTask<Void, Void, Void>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            protected Void doInBackground(FragmentActivity context, Void... params) throws Exception {
                // TODO Auto-generated method stub
                if (type == 1) {
                    String pic = shop_code.substring(1, 4) + "/" + shop_code + "/" + picPath;
                    bmBg = downloadPic(pic);
                } else if (type == 2) {
                    bmBg = downloadPic(picPath);
                }

                if (bmBg != null) {
                    QRCreateUtil.saveBitmap(bmBg, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// ????????????
                } else {
                    // ??????????????????
                    QRCreateUtil.saveBitmap(
                            BitmapFactory.decodeResource(getResources(), R.drawable.gerenzhongxin_morentouxiang_bg),
                            YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// ????????????
                }
                return super.doInBackground(context, params);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, Void result, Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result, e);
                if (null == e) {
                    file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");
                    // share(file); ????????????????????????????????????
                    if (null != shareWaitDialog) {
                        shareWaitDialog.dismiss();
                    }
                    share(file, shop_code, link);

                } else {
                    if (null != shareWaitDialog) {
                        shareWaitDialog.dismiss();
                    }
                }
            }

        }.execute();
    }

    private Bitmap downloadPic(String picPath) {
        try {
            URL url = new URL(YUrl.imgurl + picPath);
            // ????????????
            URLConnection con = url.openConnection();
            // ?????????????????????
            int contentLength = con.getContentLength();
            // System.out.println("?????? :" + contentLength);
            // ?????????
            InputStream is = con.getInputStream();
            // 1K???????????????
            byte[] bs = new byte[8192];
            // ????????????????????????
            int len;
            BitmapDrawable bmpDraw = new BitmapDrawable(is);

            // ???????????????????????????
            is.close();
            return bmpDraw.getBitmap();
        } catch (Exception e) {
            LogYiFu.e("TAG", "????????????");

            e.printStackTrace();
            return null;
        }

    }

    private void share(File file, String shop_code, String link) {
        wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
        weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());
        qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
        if (shareType == 2) {// QQ??????
            if (file == null) {
                ToastUtil.showShortText(this, "??????????????????????????????~~");
                return;
            }
            UMImage umImage = new UMImage(mContext, file);
            if (type == 1) {
                ShareUtil.setShareContent(mContext, umImage, StringUtils.getShareContentNew(), link);
            } else if (type == 2) {
                ShareUtil.setShareContentFriend(mContext, umImage, tieziContent, link, "");
            }
            performShare(SHARE_MEDIA.QZONE, qqShareIntent);
        } else if (shareType == 1) {// ?????????????????????
            if (file == null) {
                ToastUtil.showShortText(this, "??????????????????????????????~~");
                return;
            }
            UMImage umImage = new UMImage(mContext, file);
            if (type == 1) {
                if (jumpFrom.equals("YaoQingHaoyou")) {


//                    Random rand = new Random();
//                    randNum = rand.nextInt(2);
//
//                    if (randNum == 0) {
                    ShareUtil.setShareNewTitleContent(mContext, umImage, newShareText, link, str4);
//                    } else {
//                        ShareUtil.setShareContent(mContext, umImage, StringUtils.getShareContentNew(), link);
//                    }


                } else {
                    ShareUtil.setShareContent(mContext, umImage, StringUtils.getShareContentNew(), link);

                }

            } else if (type == 2) {
                ShareUtil.setShareContentFriend(mContext, umImage, tieziContent, link, "");
            }
            performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);
        } else if (shareType == 0) {// ????????????

            if (jumpFrom.equals("YaoQingHaoyou")) { //??????????????????


//                WeiXinShareContent wei = new WeiXinShareContent();
//                wei.setShareContent(newShareText);
//                wei.setTitle(str4);
//                wei.setTargetUrl(link);
//                wei.setShareMedia(new UMImage(mContext, file));
//                mController.setShareMedia(wei);
//                performShareWXHY(SHARE_MEDIA.WEIXIN, weixinShareIntent);


                if (type == 1) { //??????
                    String shareMIniAPPimgPic = YUrl.imgurl + shop_code.substring(1, 4) + "/" + shop_code + "/" + four_pic.split(",")[2] + "!280";
                    String wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + shop_code +
                            "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();

                    String shareText = "??????" + GuideActivity.oneShopPrice + "?????????" + supp_label + "??????" + type2 + "???????????????" + shop_se_price + "???!";


                    //????????????????????????????????????
                    WXminiAPPShareUtil.shareToWXminiAPP(mContext, shareMIniAPPimgPic, shareText, wxMiniPathdUO, false);
                } else { //??????
                    String shareMIniAPPimgPic = YUrl.imgurl + tieziPIC + "!280";
                    String wxMiniPathdUO = "/pages/shouye/detail/sweetFriendsDetail/friendsDetail?theme_id=" + tieziID +
                            "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();
                    //????????????????????????????????????
                    WXminiAPPShareUtil.shareToWXminiAPP(mContext, shareMIniAPPimgPic, tieziContent, wxMiniPathdUO, false);
                }


            } else {


                if (type == 1) { //??????
                    String shareMIniAPPimgPic = YUrl.imgurl + shop_code.substring(1, 4) + "/" + shop_code + "/" + four_pic.split(",")[2] + "!280";
                    String wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + shop_code +
                            "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();

                    String shareText = "??????" + GuideActivity.oneShopPrice + "?????????" + supp_label + "??????" + type2 + "???????????????" + shop_se_price + "???!";

                    //????????????????????????????????????
                    WXminiAPPShareUtil.shareToWXminiAPP(mContext, shareMIniAPPimgPic, shareText, wxMiniPathdUO, false);
                } else { //??????
                    String shareMIniAPPimgPic = YUrl.imgurl + tieziPIC + "!280";
                    String wxMiniPathdUO = "/pages/shouye/detail/sweetFriendsDetail/friendsDetail?theme_id=" + tieziID +
                            "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();
                    //????????????????????????????????????
                    WXminiAPPShareUtil.shareToWXminiAPP(mContext, shareMIniAPPimgPic, tieziContent, wxMiniPathdUO, false);
                }


//                WeiXinShareContent wei = new WeiXinShareContent();
//
//                wei.setShareContent("??????APP");
//                if (type == 1) {
//                    wei.setTitle(StringUtils.getShareContentNew() + "");
//                } else if (type == 2) {
//                    wei.setTitle(tieziContent + "");
//                }
//                wei.setTargetUrl(link);
//                wei.setShareMedia(new UMImage(mContext, file));
//                mController.setShareMedia(wei);
//                performShareWXHY(SHARE_MEDIA.WEIXIN, weixinShareIntent);

            }


        }

    }

    public void performShare(SHARE_MEDIA platform, final Intent intent) {
        // UMImage umImage = new UMImage(mActivity, bmNew);
        // ShareUtil.setShareContent(mActivity, umImage,
        // "????????????????????????????????????????????????????????????????????????~~", link9);

        mController.postShare(mContext, platform, new SnsPostListener() {

            @Override
            public void onStart() {
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                if (eCode == StatusCode.ST_CODE_SUCCESSED) {
                    sign(false);
                } else {

                }
                SocializeConfig.getSocializeConfig().cleanListeners();// ???????????????????????????
                // ?????????????????????????????????


            }
        });
    }


    public void performShareWXHY(SHARE_MEDIA platform, final Intent intent) {
        // UMImage umImage = new UMImage(mActivity, bmNew);
        // ShareUtil.setShareContent(mActivity, umImage,
        // "????????????????????????????????????????????????????????????????????????~~", link9);

        mController.postShare(mContext, platform, new SnsPostListener() {

            @Override
            public void onStart() {
                sign(true);
                SocializeConfig.getSocializeConfig().cleanListeners();// ???????????????????????????
                // ?????????????????????????????????
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {


            }
        });
    }

    String str4 = ""; //???????????? ?????????????????????title
    String shop_name = "??????";
    String type2 = "";
    String shop_se_price = "0";
    String supp_label = "??????";

    /**
     * ???????????????????????????
     */
    public void share(final String code) {
        new SAsyncTask<String, Void, HashMap<String, String>>(this, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                HashMap<String, String> map = ComModelZ.geType2SuppLabe(YaoQingFrendsActivity.this, code);
                if (map != null && map.size() > 0) {
                    type2 = map.get("type2");
                }
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
                        String shoplink = result.get("link");
                        supp_label = result.get("supp_label");
                        shop_se_price = result.get("shop_se_price");
                        shop_name = result.get("shop_name");


                        four_pic = result.get("four_pic");
                        if ("".equals(newShareText)) {
                            newShareText = shop_name;
                        }
                        if ("".equals(type2)) {
                            type2 = shop_name;
                        }

                        String str1;
                        String str2;
                        String str3;
                        if ("".equals(supp_label)) {
                            str1 = newShareTitle.replaceFirst("\\$\\{replace\\}", "??????");
                        } else {
                            str1 = newShareTitle.replaceFirst("\\$\\{replace\\}", supp_label);
                        }
                        str2 = str1.replaceFirst("\\$\\{replace\\}", type2);
                        str3 = str2.replaceFirst("\\$\\{replace\\}", "" + new DecimalFormat("#0.0").format(Math.round(Double.parseDouble(shop_se_price) * 0.5 * 10) * 0.1d));
                        str4 = str3.replaceFirst("\\$\\{replace\\}", "" + new DecimalFormat("#0.0").format(Math.round(Double.parseDouble(shop_se_price) * 0.5 * 10) * 0.1d));
//                        str2 = str1.replaceFirst("\\$\\{replace\\}", "" +new DecimalFormat("#0.0").format( Math.round(Double.parseDouble(shop_se_price) * 0.5*10)*0.1d));

                        createSharePic(shoplink, shopPIC, shopCode);
                    } else if (result.get("status").equals("1050")) {// ??????
                        if (null != shareWaitDialog) {
                            shareWaitDialog.dismiss();

                        }
                        Intent intent = new Intent(context, NoShareActivity.class);
                        intent.putExtra("isNomal", true);
                        context.startActivity(intent); // ?????????????????????

                    } else {// Dialog??????
                        if (null != shareWaitDialog) {
                            shareWaitDialog.dismiss();
                        }
                    }
                } else {
                    if (null != shareWaitDialog) {
                        shareWaitDialog.dismiss();
                    }
                }
            }

        }.execute(code);
    }

    private int num;
    private int shareNum;

    private void sign(final Boolean isWXHY) {

        if (isComplete) {// ???????????????????????????
            return;
        }

        if (isAddLike && SignListAdapter.jizanCoplete) {// ???????????????????????????
            if (!isWXHY) {
                ToastUtil.showShortText(mContext, "????????????");

            }
            if (PointLikeRankingActivity.pointlikerankingactivity != null) {
                PointLikeRankingActivity.pointlikerankingactivity.finish();
            }
            finish();
            return;
        }

        //?????????????????????????????????????????????index?????????????????????????????????
        if (isAddLike && (null == SignListAdapter.jizanIndex || ("").equals(SignListAdapter.jizanIndex))) {

            if (!isWXHY) {
                ToastUtil.showShortText(mContext, "????????????");

            }
            if (PointLikeRankingActivity.pointlikerankingactivity != null) {
                PointLikeRankingActivity.pointlikerankingactivity.finish();
            }
            finish();

            return;
        }

        if (!isAddLike) {

            final String SHARENUMKEY = SignListAdapter.signIndex + "share_num"
                    + YCache.getCacheUser(mContext).getUser_id();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String now_time = sdf.format(date);
            String nowTime = SharedPreferencesUtil.getStringData(mContext,
                    "share_now_time" + YCache.getCacheUser(mContext).getUser_id(), "");
            num = Integer.valueOf(SharedPreferencesUtil.getStringData(mContext, SHARENUMKEY, "0"));

            if (!now_time.equals(nowTime)) {
                num = 0;
            }
            num++;
            SharedPreferencesUtil.saveStringData(mContext,
                    "share_now_time" + YCache.getCacheUser(mContext).getUser_id(), now_time);
            SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "" + num);

            // ??????????????????????????????????????????
            if (num < shareNum) {// ???????????????????????????
                if (SignListAdapter.doNum == 1) {// ??????????????????????????? ??????????????? ???????????????????????? ????????????
                    ToastUtil.showShortText(mContext, "?????????" + (shareNum - num) + "?????????????????????~");
                    return;
                }
            }

        }

        // ??????
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) mContext, 0) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                if (isAddLike) {
                    return ComModel2.getSignIn(mContext, false, true, SignListAdapter.jizanIndex, SignListAdapter.doClass);

                } else {
                    return ComModel2.getSignIn(mContext, false, true, SignListAdapter.signIndex, SignListAdapter.doClass);

                }

            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null) {

                    if (isAddLike) {
                        if (!isWXHY) {
                            ToastUtil.showShortText(mContext, "????????????");
                        }
                        if (PointLikeRankingActivity.pointlikerankingactivity != null) {
                            PointLikeRankingActivity.pointlikerankingactivity.finish();
                        }
                        finish();
                    } else {

                        // ???????????????????????????
                        if (num < shareNum) {
                            String ss = "";
                            switch (SignListAdapter.jiangliID) {
                                case 3:
                                    ss = "????????????";
                                    break;
                                case 4:
                                    ss = "??????";
                                    break;
                                case 5:
                                    ss = "???";
                                    break;
                                case 11:
                                    ss = "?????????";
                                    break;
                                default:
                                    break;
                            }
                            ToastUtil.showShortText(mContext, "??????????????????" +
                                    // new
                                    // java.text.DecimalFormat("#0.00").format(Double.valueOf(SignListAdapter.jiangliValue)/shareNum)
                                    SignListAdapter.jiangliValue + ss + "?????????" + (shareNum - num) + "????????????~");
                        } else if (num >= shareNum) {
                            if (jumpFrom.equals("YaoQingHaoyou")) {// ???????????????????????????
                                finish();
                                return;
                            }
                            SharedPreferencesUtil.saveBooleanData(mContext, "sharemeiyichuandaback", true);
                            finish();

                        }
                    }


                } else {
                    // ToastUtil.showLongText(mActivity, "????????????");
                }

            }

        }.execute();
    }


    public void getShareTitleText() {
        new SAsyncTask<Void, Void, HashMap<String, String>>(YaoQingFrendsActivity.this, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModelZ.getShareTitleContent();
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null) {
                    newShareTitle = result.get("title");
                    newShareText = result.get("text");


                }
            }

        }.execute();
    }

    @Override
    public void wxMiniShareSuccess() {

        if (jumpFrom.equals("YaoQingHaoyou")) { //??????????????????
            sign(false);
        } else {
            sign(true);
        }

    }
}
