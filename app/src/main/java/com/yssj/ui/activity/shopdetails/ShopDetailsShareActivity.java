package com.yssj.ui.activity.shopdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.DeviceConfig;
import com.yssj.Constants;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.activity.wxapi.WXEntryActivity;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelZ;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.PublicToastDialog;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongjiShareCount;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/9/13.
 */

public class ShopDetailsShareActivity extends BasicActivity {
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.tv4)
    TextView tv4;
    private Context mContext;
    public static PublicToastDialog shareWaitDialog = null;
    private boolean mWxInstallFlag;
    private String shop_code = "";

    private boolean isNewMeal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_share_details);
        ButterKnife.bind(this);
        mContext = this;
        isNewMeal = getIntent().getBooleanExtra("isNewMeal", false);
        shop_code = getIntent().getStringExtra("shop_code");
        if (WXcheckUtil.isWeChatAppInstalled(this)) {
            mWxInstallFlag = true;
        } else {
            mWxInstallFlag = false;
        }
        shareWaitDialog = new PublicToastDialog(this, R.style.DialogStyle1, "");
        StringUtils.initShareText(tv1, tv2, tv3, tv4);
        initView();
    }

    private void initView() {
        LinearLayout img_back = (LinearLayout) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        ImageView iv_wxin_share = (ImageView) findViewById(R.id.iv_wxin_share);
        iv_wxin_share.setOnClickListener(this);
        LinearLayout iv_qq_share = (LinearLayout) findViewById(R.id.iv_qq_share);
        iv_qq_share.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.iv_wxin_share://分享到朋友圈
                if (!mWxInstallFlag) {
                    ToastUtil.showShortText(mContext, "您还未安装微信哦~");
                    return;
                }
//                if (ShareUtil.intentIsAvailable(mContext, wXinShareIntent)) {

                ShareUtil.addWXPlatform(mContext);
                share(shop_code, 2);
//                }

                break;
            case R.id.iv_qq_share://分享到微信好友
                if (!mWxInstallFlag) {
                    ToastUtil.showShortText(mContext, "您还未安装微信哦~");
                    return;
                }
                ShareUtil.addWXPlatform(mContext);
                share(shop_code, 1);
                break;
            default:
                break;
        }
    }

    private Intent wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
    private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);
    private Intent weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());// 分享到微信好友

    /**
     * 得到分享的链接
     */
    public void share(final String code, final int flag) {

        try {
            shareWaitDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new SAsyncTask<String, Void, HashMap<String, String>>(this, R.string.wait) {

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
                if (null != shareWaitDialog) {
                    shareWaitDialog.dismiss();

                }
                if (null == e) {

                    if (result.get("status").equals("1")) {
                        TongjiShareCount.tongjifenxiangCount();
                        TongjiShareCount.tongjifenxiangwho(code);
                        String link = result.get("link");
                        String four_pic = result.get("four_pic").toString();
                        String bg_url = "";
                        if (four_pic != null && four_pic.split(",").length > 2) {
                            bg_url = "" + shop_code.substring(1, 4) + "/" + shop_code + "/" + four_pic.split(",")[2] + "!450";
                        }


                        ShareUtil.addWXPlatform(mContext);
                        UMImage umImage = new UMImage(context, YUrl.imgurl + bg_url);

                        if (flag == 1) { //分享到微信
//                            WeiXinShareContent wei = new WeiXinShareContent();
//                            wei.setTargetUrl(link);
//                            wei.setShareContent("我挺喜欢的美衣，分享给你进来看看还能领现金红包哦~");
//                            wei.setTitle(StringUtils.getShareContent(YCache.getCacheUser(mContext).getNickname()));
//                            wei.setShareMedia(umImage);
//                            mController.setShareMedia(wei);
//                            performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);


                            if (isNewMeal) {


                                String shareString = "快来" + GuideActivity.oneShopPrice + "元拼【" + result.get("shop_name") + "】，原价" + result.get("shop_se_price") + "元！";


                                String shareMIniAPPimgPic = YUrl.imgurl + shop_code.substring(1, 4) + "/" + shop_code + "/" + result.get("def_pic") + "!280";
                                String wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + shop_code +
                                        "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();
                                //分享到微信统一分享小程序
                                WXminiAPPShareUtil.shareShopToWXminiAPP(mContext, result.get("shop_name")+"",result.get("app_shop_group_price")+"",shareMIniAPPimgPic, wxMiniPathdUO, false);


                            } else {
                                getTyepe2SuppLabel(four_pic, result.get("shop_se_price"));
                            }


                        } else {

                            ShareUtil.setShareContent(mContext, umImage, "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link + "&share=true");
                            performShare(SHARE_MEDIA.WEIXIN_CIRCLE, weixinShareIntent);
                        }


                    } else if (result.get("status").equals("1050")) {// 表明
                        Intent intent = new Intent(context, NoShareActivity.class);
                        intent.putExtra("isNomal", true);
                        context.startActivity(intent); // 分享已经超过了
                    }
                }
            }

        }.execute(code);
    }


    public void getTyepe2SuppLabel(final String four_pic, final String shop_se_price) {
        new SAsyncTask<Void, Void, HashMap<String, String>>(this, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModelZ.geType2SuppLabe(ShopDetailsShareActivity.this, shop_code);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null) {
                    //品牌
                    String label_id = result.get("supp_label_id");


                    String shareMIniAPPimgPic = YUrl.imgurl + shop_code.substring(1, 4) + "/" + shop_code + "/" + four_pic.split(",")[2] + "!280";
                    String wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + shop_code +
                            "&isShareFlag=true&user_id=" + YCache.getCacheUser(ShopDetailsShareActivity.this).getUser_id();


                    String onPrice = GuideActivity.oneShopPrice;
//					onPrice = new java.text.DecimalFormat("#0")
//							.format(Double.parseDouble(onPrice));

                    String shareText = "快来" + onPrice + "元拼【" + label_id + "正品" + result.get("type2") + "】，专柜价" + shop_se_price + "元!";


                    //分享到微信统一分享小程序
                    WXminiAPPShareUtil.shareShopToWXminiAPP(mContext, result.get("shop_name")+"",result.get("app_shop_group_price")+"",shareMIniAPPimgPic, wxMiniPathdUO, false);

                    WXEntryActivity.setWXminiShareListener(new WXEntryActivity.WXminiAPPshareListener() {
                        @Override
                        public void wxMiniShareSuccess() {
                            ToastUtil.showShortText(ShopDetailsShareActivity.this, "分享成功");

                        }
                    });


                    if (null != shareWaitDialog) {
                        shareWaitDialog.dismiss();
                    }


                } else {
                    if (null != shareWaitDialog) {
                        shareWaitDialog.dismiss();
                    }
                }
            }

        }.execute();
    }


    public void performShare(SHARE_MEDIA platform, final Intent intent) {
        mController.postShare(mContext, platform, new SocializeListeners.SnsPostListener() {


            @Override
            public void onStart() {
            }


            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {

                String showText = platform.toString();
                if (eCode == StatusCode.ST_CODE_SUCCESSED) {
                    showText += "平台分享成功";
                    Toast.makeText(mContext, showText, Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
