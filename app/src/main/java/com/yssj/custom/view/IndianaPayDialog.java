package com.yssj.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
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
import com.yssj.model.ComModelZ;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.ShopImageActivity;
import com.yssj.ui.activity.shopdetails.NoShareActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsIndianaActivity;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.TongjiShareCount;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/29.
 */

public class IndianaPayDialog extends Dialog implements IndianaPayPopupWinDow.IndianaInterFace {
    public static Context mContext;
    private IndianaPayDialog dialog;
    private IndianaDialogInterface dialogInterface;
    private String sharePic = "";//分享图片链接
    public static String code = "";//商品编号
    private double shopMoney = 0;//商品价格
    public static TextView tvShareCount;
    public static TextView tvBuyCount;
    public static int currentDayMaxShareCount = 200;//当天最大分享次数
    public static int flagMax = 5;//分享满多少次给一次机会
    public static int shareCount = 5;//分享图标上显示的次数
    public static int branchCount = 0;//1分钱夺宝次数
    public static int dayOreadyShareCount = 0;//今天已经分享的次数
    public static int needPeoPleCount = 0;//本次夺宝还需要的人数
    private String shareTo = "link";//默认随机商品


    //小程序相关
    private String duoBaoSharePic = ""; //夺宝分享的图
    private String DuobaoShop_code; //夺宝分享商品编号
    private String indiana_name;
    private String def_pic;

    public IndianaPayDialog(ShopDetailsIndianaActivity context, int theme, String sharePic, String code, double shopMoney, int needPeopleCount, String indiana_name, String def_pic) {
        super(context, theme);
        this.dialog = this;
        mContext = context;
        getIndianaShareData();
        dialogInterface = context;
        this.sharePic = sharePic;
        this.code = code;
        this.indiana_name = indiana_name;
        this.def_pic = def_pic;
        this.shopMoney = shopMoney;
        this.needPeoPleCount = needPeopleCount;
        currentDayMaxShareCount = Integer.parseInt(SharedPreferencesUtil.getStringData(mContext, YConstance.Pref.SHARETODAYNUMDUOBAO, "200"));
        flagMax = Integer.parseInt(SharedPreferencesUtil.getStringData(mContext, YConstance.Pref.SHARETONUMDUOBAO, "5"));
        initView();
    }

    public IndianaPayDialog(ShopImageActivity context, int theme, String sharePic, String code, double shopMoney, int needPeopleCount, String indiana_name, String def_pic) {
        super(context, theme);
        this.dialog = this;
        mContext = context;
        getIndianaShareData();
        dialogInterface = context;
        this.sharePic = sharePic;
        this.code = code;
        this.indiana_name = indiana_name;
        this.def_pic = def_pic;
        this.shopMoney = shopMoney;
        this.needPeoPleCount = needPeopleCount;
        currentDayMaxShareCount = Integer.parseInt(SharedPreferencesUtil.getStringData(mContext, YConstance.Pref.SHARETODAYNUMDUOBAO, "200"));
        flagMax = Integer.parseInt(SharedPreferencesUtil.getStringData(mContext, YConstance.Pref.SHARETONUMDUOBAO, "5"));
        initView();
    }
//    public IndianaPayDialog(Context context) {
//        mContext=context;
//        initView();
//        dialog=this;
//    }

    private IndianaPayPopupWinDow payWinDow;

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_indiana_details, null);
        setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        setCancelable(false);
        TextView tvMaxNum = (TextView) view.findViewById(R.id.indiana_tv_line1);
        tvMaxNum.setText("1.每分享" + flagMax + "次给微信群或好友（只可分享给不同群或好友，否则只记1次），即可以1分钱参与原价2元的抽奖。分享次数越多，参与次数越多，你的中奖率就越高。");
        TextView dialog_close = (TextView) view.findViewById(R.id.dialog_colse);
        LinearLayout indiana_ll_share = (LinearLayout) view.findViewById(R.id.indiana_ll_share);//分享微信好友
        LinearLayout indiana_ll_branch = (LinearLayout) view.findViewById(R.id.indiana_ll_branch);//1分钱夺宝
        LinearLayout indiana_ll_normal = (LinearLayout) view.findViewById(R.id.indiana_ll_normal);//2元夺宝
        tvShareCount = (TextView) view.findViewById(R.id.tv_share_count);
        tvBuyCount = (TextView) view.findViewById(R.id.tv_branch_count);
        dialog_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 把这个对话框取消掉
                dialog.dismiss();
            }
        });
        indiana_ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TongJiUtils.yunYunTongJi("duobao", 1004, 10, mContext);
                boolean mWxInstallFlag = true;
                try {
                    // // 是否安装了微信
                    if (WXcheckUtil.isWeChatAppInstalled(mContext)) {
                        mWxInstallFlag = true;
                    } else {
                        mWxInstallFlag = false;
                    }
                } catch (Exception e) {
                }
                if (!mWxInstallFlag) {
                    ToastUtil.showShortText(mContext, "您还未安装微信哦~");
                    return;
                }

                if (dayOreadyShareCount > currentDayMaxShareCount) {
                    ToastUtil.showMyToast(mContext, "你当日的分享次数过于频繁，请48小时后再来分享。", 3000);
                    return;
                }
                getShareTitle();

            }
        });
        indiana_ll_branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TongJiUtils.yunYunTongJi("duobao", 1006, 10, mContext);
                if (branchCount <= 0) {
                    ToastUtil.showMyToast(mContext, "你的1分抽奖次数不足哦。再分享" + shareCount + "次即可赢得一次1分抽奖的机会，现在就去分享吧。", 3000);
                    return;
                }
                dialog.dismiss();
//                float bgAlpha=0.7f;
//                WindowManager.LayoutParams lp = getWindow().getAttributes();
//                lp.alpha = bgAlpha; //0.0-1.0
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                getWindow().setAttributes(lp);
                payWinDow = new IndianaPayPopupWinDow(mContext, IndianaPayDialog.this, 1);
                payWinDow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        if (!isToBuy) {
                            show();
                        }
                    }
                });
                payWinDow.show();
            }
        });
        indiana_ll_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TongJiUtils.yunYunTongJi("duobao", 1003, 10, mContext);
                dialog.dismiss();
                payWinDow = new IndianaPayPopupWinDow(mContext, IndianaPayDialog.this, 2);
                payWinDow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        show();
                    }
                });
                payWinDow.show();
            }
        });
        initShareLink();//
        getShareTitleText();

    }

    private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);
    private Intent weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());// 分享到微信好友
    //    String link = YUrl.YSS_URL_ANDROID_H5 + "view/download/6.html?realm=" + YCache.getCacheUser(mContext).getUser_id();
    private String link = "";
    private String shareTitle = "对方不想和你说话，并向你扔了一部IPHONE7";
    private String shareContent = "比一元抽奖更牛的1分抽奖来了，1分钱抽IPHONE7，马上开奖了。快来！";

    //    /**
//     * 得到分享的链接
//     */
//    public void getShareTitle() {
//
//
//        new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {
//
//            @Override
//            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
//                    throws Exception {
//                return ComModel2.getIndianashopLink(params[0], context, "true");
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (null == e) {
//                    if (result.get("status").equals("1")) {
//                        TongjiShareCount.tongjifenxiangCount();
//                        TongjiShareCount.tongjifenxiangwho((String) result.get("shop_code"));
//                        if ("".equals(link)) {
//                            link = result.get("link");
//                        }
//                        if (!"".equals(result.get("duobaoTitle"))) {
//                            shareTitle = result.get("duobaoTitle");
//                        }
//                        if (!"".equals(result.get("duobaoTxt"))) {
//                            shareContent = result.get("duobaoTxt");
//                        }
//                        ShareUtil.addWXPlatform(mContext);
////                        shareToWeChat(link, new UMImage(mContext, YUrl.imgurl + sharePic));//R.drawable.inviting_single
//                        share();
//                    } else if (result.get("status").equals("1050")) {// 表明
//                        Intent intent = new Intent(context, NoShareActivity.class);
//                        intent.putExtra("isNomal", true);
//                        context.startActivity(intent); // 分享已经超过了
//
//                    }
//                }
//            }
//
//        }.execute(code);
//    }
    private void getShareTitle() {

        new SAsyncTask<Void, Void, HashMap<String, HashMap<String, Object>>>((FragmentActivity) mContext, 0) {

            @Override
            protected HashMap<String, HashMap<String, Object>> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModelZ.getIndianaShareText();
            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, HashMap<String, Object>> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null) {

                    TongjiShareCount.tongjifenxiangCount();
//                    TongjiShareCount.tongjifenxiangwho((String) result.get("shop_code"));

                    if (shareTo.equals("spellGroup")) {//拼团
                        shareTitle = "" + result.get("spellGroup_yf").get("title");
                        shareContent = "" + result.get("spellGroup_yf").get("text");
                    } else if (shareTo.equals("indiana")) {//夺宝
                        shareTitle = "" + result.get("indiana_yf").get("title");
                        shareContent = "" + result.get("indiana_yf").get("text");
                        shareTitle = shareTitle.replaceFirst("\\$\\{replace\\}", "" + indiana_name);
                        shareContent = shareContent.replaceFirst("\\$\\{replace\\}", "" + indianaShop_name);
                    } else if (shareTo.equals("h5money")) {//H5赚钱页
                        shareTitle = "" + result.get("h5money_yf").get("title");
                        shareContent = "" + result.get("h5money_yf").get("text");
                    }

                    ShareUtil.addWXPlatform(mContext);
//                        shareToWeChat(link, new UMImage(mContext, YUrl.imgurl + sharePic));//R.drawable.inviting_single
                    share();

                } else if (result.get("status").equals("1050")) {// 表明
                    Intent intent = new Intent(context, NoShareActivity.class);
                    intent.putExtra("isNomal", true);
                    context.startActivity(intent); // 分享已经超过了

                }
            }

        }.execute();
    }

    public static boolean wxClick = false;

    public static boolean isCommit = false;

    /**
     * 分享到微信好友
     */
//    private void shareToWeChat(String link, UMImage umImage) {
//        WeiXinShareContent wei = new WeiXinShareContent();
//        wei.setShareContent("" + shareContent);
//        wei.setTitle("" + shareTitle);
//        wei.setTargetUrl(link);
//        wei.setShareMedia(umImage);
//        mController.setShareMedia(wei);
//        performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);
//    }

    public void performShare(SHARE_MEDIA platform, final Intent intent) {
        UMImage umImage;

        // File bmg = null;
        // Drawable img_invite_friend =
        // getResources().getDrawable(R.drawable.invite_friend);
        // BitmapDrawable bd = (BitmapDrawable) img_invite_friend;
        umImage = new UMImage(mContext, "" + YUrl.imgurl + sharePic);

        ShareUtil.setShareContent(mContext, umImage, "" + shareContent, link);

        mController.getConfig().closeToast();
        mController.postShare(mContext, platform, new SocializeListeners.SnsPostListener() {


            @Override
            public void onStart() {
                LogYiFu.e("showText", "asdsafdsf");

                wxClick = true;

                // chooseDialog();
            }


            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                String showText = platform.toString();
                LogYiFu.e("showText", showText);

                if (eCode == StatusCode.ST_CODE_SUCCESSED) {

//                    ToastUtil.showShortText(mContext, "分享成功");
//                    shareCount--;
//                    if(shareCount==0){
//                        shareCount=5;
//                        branchCount++;
//                    }
//                    if(!isCommit) {
//                        isCommit=true;
//                        submitIndianaShareRecord();
//                    }

                } else {

                }

            }


        });
    }

    private boolean isToBuy = false;

    @Override
    public void confirm(int takeCount, double countMoney, int flag) {
        if (dialogInterface != null) {
            isToBuy = true;
            dialogInterface.intentToConfirm(takeCount, countMoney, flag);
            if (payWinDow != null) {
                payWinDow.dismiss();
            }
        }
    }

    public interface IndianaDialogInterface {
        void intentToConfirm(int takeCount, double countMoney, int flag);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {//用来控制popupWindow外面不可点击
        if (payWinDow != null && payWinDow.isShowing()) {
            return false;
        }
        return super.dispatchTouchEvent(event);
    }

    public double getShopMoney() {
        return shopMoney;
    }

    public int getBranchCount() {
        return branchCount;
    }

    public int getNeedPeoPleCount() {
        return needPeoPleCount;
    }

    public static void submitIndianaShareRecord() {
        new SAsyncTask<Void, Void, HashMap<String, Object>>(
                (FragmentActivity) mContext, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(
                    FragmentActivity context, Void... params) throws Exception {
                return ComModel2.IndianashareRecordCount(mContext, "" + code);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                isCommit = false;
                if (null == e && result != null) {//分享成功
                    shareCount--;
                    if (shareCount <= 0) {
                        shareCount = flagMax;
                        branchCount++;
                    }
                    dayOreadyShareCount++;
                    tvShareCount.setText("" + shareCount);
                    tvBuyCount.setText("" + branchCount);
                    if (branchCount > 99) {
                        tvBuyCount.setTextSize(8);
                    } else {
                        tvBuyCount.setTextSize(12);
                    }
                    wxClick = false;
                    ToastUtil.showMyToast(mContext, "分享成功，再分享" + shareCount + "次即可赢得一次1分抽奖的机会，继续努力。", 3000);

                }
            }

        }.execute();
    }


    private void getIndianaShareData() {
        new SAsyncTask<Void, Void, HashMap<String, Object>>(
                (FragmentActivity) mContext, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(
                    FragmentActivity context, Void... params) throws Exception {
                return ComModel2.IndianaShareData(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);

                if (null == e && result != null) {
                    branchCount = Integer.parseInt((String) result.get("oc"));
                    shareCount = flagMax - Integer.parseInt((String) result.get("sc"));
                    dayOreadyShareCount = Integer.parseInt((String) result.get("scDay"));
                    tvShareCount.setText("" + shareCount);
                    tvBuyCount.setText("" + branchCount);
                    if (branchCount > 99) {
                        tvBuyCount.setTextSize(8);
                    } else {
                        tvBuyCount.setTextSize(12);
                    }
                }
            }

        }.execute();
    }


    /**
     * 获取分享链接
     */
    private void initShareLink() {
        try {
            shareTo = SignListAdapter.doValue.split(",")[0].split("=")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (shareTo.equals("link")) {//分享随机商品
            shareTo = "link";
        } else {
            if (shareTo.equals("spellGroup")) {//拼团
                link = YUrl.YSS_URL_ANDROID_H5 + "/view/activity/pack.html?realm=" + YCache.getCacheUser(mContext).getUser_id();
            } else if (shareTo.equals("indiana")) {//夺宝
                //获取夺宝
                getDuoBaoShareLink();

            } else if (shareTo.equals("h5money")) {//H5赚钱页
                link = YUrl.YSS_URL_ANDROID_H5 + "/view/activity/mission.html?realm=" + YCache.getCacheUser(mContext).getUser_id();
            }

        }
    }

    private void getDuoBaoShareLink() {
        duoBaoSharePic = YUrl.imgurl + code.substring(1, 4) + "/" + code + "/" + def_pic + "!280";
//        new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {
//
//            @Override
//            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
//                    throws Exception {
//                return ComModel2.getsuijishopLink(context);
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (null == e) {
//                    link = result.get("link");
//                    indianaShop_name=result.get("shop_name");
//
//
//                    DuobaoShop_code = result.get("shop_code");
//                    String def_pic = result.get("def_pic").toString(); //微信分享全部改为分享小程序
//                    duoBaoSharePic = YUrl.imgurl + DuobaoShop_code.substring(1, 4) + "/" + DuobaoShop_code + "/" + def_pic + "!280";
//
//
//                }
//            }
//
//        }.execute("");

    }


    /**
     * 得到分享的链接
     */
    public void share() {


        //全部改未分享小程序


        if (shareTo.equals("link")) {//分享随机商品
            //分享随机商品到微信好友
            shareShop();
        } else {

            if (shareTo.equals("spellGroup")) { //拼团暂不修改
                //获取随机拼团编号并开始分享
                getPintuanCode();
            } else {
//
//                ShareUtil.addWXPlatform(mContext);
//                //设置分享内容并且开始分享
//                WeiXinShareContent wei = new WeiXinShareContent();
//                wei.setShareContent("" + shareContent);
//                wei.setTitle("" + shareTitle);
//                wei.setTargetUrl(link);
//                wei.setShareMedia(new UMImage(mContext, YUrl.imgurl + sharePic));
//                mController.setShareMedia(wei);
//                performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);


                String shopPiC = "https://www.measures.wang/small-iconImages/ad_pic/ic_launcher.png";
                String wxMiniPath = "/pages/shouye/shouye?isShareFlag=true&goto=sign&user_id=" + YCache.getCacheUser(mContext);


                if (shareTo.equals("indiana")) { //夺宝的要取商品编号
                    //获取夺宝
//                    getDuoBaoShareLink();


                    String wxMiniPathdUO = "/pages/shouye/detail/centsIndianaDetail/centsDetail?shop_code=" + code +
                            "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();
                    wxClick = true;
                    //分享到微信统一分享小程序
                    WXminiAPPShareUtil.shareToWXminiAPP(mContext, duoBaoSharePic, shareTitle, wxMiniPathdUO, false);

                } else {
                    //其他的直接取
                    HashMap<String, Object> map = GuideActivity.textMap.get(shareTo);
                    if (null != map && map.size() > 0) {//找到了
                        //确定分享内容
                        shareTitle = map.get("title") + "";
                        shareContent = map.get("text") + "";
                        shopPiC = YUrl.imgurl + map.get("icon");
//                        umImage = new UMImage(mContext, YUrl.imgurl + map.get("icon"));

                        if (shareTo.equals("h5money")) {//H5赚钱页
                            wxMiniPath = "/pages/shouye/shouye?isShareFlag=true&goto=sign&user_id=" + YCache.getCacheUser(mContext).getUser_id();
                        }

                    }

                    //分享到微信统一分享小程序
                    wxClick = true;
                    WXminiAPPShareUtil.shareToWXminiAPP(mContext, shopPiC, shareTitle, wxMiniPath, true);

                }

            }


        }


    }

    private void getPintuanCode() {//拼团分享

        new SAsyncTask<String, Void, String>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected String doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                return ModQingfeng.getRandRoll(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, String result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
//                    ShareUtil.addWXPlatform(mContext);
                    //设置分享内容并且开始分享
//                    WeiXinShareContent wei = new WeiXinShareContent();
//                    wei.setShareContent("" + shareContent);
//                    wei.setTitle("" + shareTitle);
//                    wei.setTargetUrl(link + "&r_code=" + result);
//                    wei.setShareMedia(new UMImage(mContext, YUrl.imgurl + sharePic));
//                    mController.setShareMedia(wei);
//                    performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);

                }
            }

        }.execute(code);


    }

    private String shopLink = "";

//    /**
//     * 分享随机商品
//     */
//    private void shareShop() {
//        new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {
//
//            @Override
//            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
//                    throws Exception {
//                // TODO Auto-generated method stub
//                return ComModel2.getShareShopLinkHobby(context, "true");
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result,
//                                         Exception e) {
//                // TODO Auto-generated method stub
//                super.onPostExecute(context, result, e);
//                if (null == e) {
//                    if (result.get("status").equals("1")) {
//
//                        String[] picList = result.get("four_pic").split(",");
//                        String shop_code = result.get("shop_code");
//                        if (shop_code.equals("null") || shop_code == null || shop_code.equals("")) {
//                            ToastUtil.showShortText(context, "未获取到商品！");
//                            return;
//                        }
//
//                        shopLink = (String) result.get("link") + "&share=true";//商品链接
////                        String shopPiC = YUrl.imgurl + shop_code.substring(1, 4) + "/" + shop_code + "/" + result.get("def_pic");
//                        ShareUtil.addWXPlatform(mContext);
//                        weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());
//                        WeiXinShareContent wei = new WeiXinShareContent();
//                        wei.setShareContent("衣蝠APP");
//                        wei.setTitle(StringUtils.getShareContentNew() + "");
////                        wei.setShareContent("" + shareContent);
////                        wei.setTitle("" + shareTitle);
//                        wei.setTargetUrl(shopLink);
////                        wei.setShareMedia(new UMImage(mContext, shopPiC));
//                        wei.setShareMedia(new UMImage(mContext, "" + YUrl.imgurl + sharePic));
//                        mController.setShareMedia(wei);
////                            performShareWXHY(SHARE_MEDIA.WEIXIN, weixinShareIntent);
//
//                        performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);
//                    }
//
//
//                }
//
//
//            }
//
//
//        }.execute();
//
//
//    }

    private String newShareTitle = "衣蝠品牌商出品，拼团仅售39元，快来一起拼！";
    private String newShareText = "";

    String str4 = ""; //替换后的 用于放新的分享title
    String shop_name = "衣蝠";
    String type2 = "";
    String indianaShop_name = "iphone7";

    public void getTyepe2SuppLabel(final String shop_code, final String link, final String shop_se_price, final String supp_label, final String def_pic, final String shop_name) {
        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModelZ.geType2SuppLabe(mContext, shop_code);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null) {
                    type2 = result.get("type2");
//                    String label_id = result.get("supp_label_id");
                    if ("".equals(type2)) {
                        type2 = shop_name;
                    }
//                    String str1;
//                    String str2;
//                    String str3;
//                    if ("".equals(supp_label)) {
//                        str1 = newShareTitle.replaceFirst("\\$\\{replace\\}", "衣蝠");
//                    } else {
//                        str1 = newShareTitle.replaceFirst("\\$\\{replace\\}", supp_label);
//                    }
//                    str2= str1.replaceFirst("\\$\\{replace\\}",type2);
//                    str3=str2.replaceFirst("\\$\\{replace\\}","" +new DecimalFormat("#0.0").format( Math.round(Double.parseDouble(shop_se_price) * 0.5*10)*0.1d));
//                    str4=str3.replaceFirst("\\$\\{replace\\}","" +new DecimalFormat("#0.0").format( Math.round(Double.parseDouble(shop_se_price) * 0.5*10)*0.1d));


//                    String shopPiC = YUrl.imgurl + shop_code.substring(1, 4) + "/" + shop_code + "/" + def_pic;

                    str4 = "快来" +
                            GuideActivity.oneShopPrice + "元拼【" +
                            supp_label + "正品" + type2 + "】，专柜价" + shop_se_price + "元！";

                    String shopPiC = YUrl.imgurl + shop_code.substring(1, 4) + "/" + shop_code + "/" + def_pic.split(",")[2] + "!280";

                    String wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + shop_code +
                            "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();

                    //改为分享小程序
                    wxClick = true;
                    WXminiAPPShareUtil.shareToWXminiAPP(mContext, shopPiC, str4, wxMiniPathdUO, false);


//                    ShareUtil.addWXPlatform(mContext);
//                    weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());
//                    WeiXinShareContent wei = new WeiXinShareContent();
//
//                    wei.setShareContent(newShareText);
//                    wei.setTitle(str4);
//
////                        wei.setShareContent("衣蝠APP");
////                        wei.setTitle(StringUtils.getShareContentNew() + "");
//                    wei.setTargetUrl(shopLink+"&share=true");
//                    wei.setShareMedia(new UMImage(mContext, shopPiC));
//                    mController.setShareMedia(wei);
////                            performShareWXHY(SHARE_MEDIA.WEIXIN, weixinShareIntent);
//                    performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);
                }
            }

        }.execute();
    }


    /**
     * 分享随机商品
     */
    private void shareShop() {
        new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                // TODO Auto-generated method stub
                return ComModel2.getShareShopLinkHobby(context, "true");
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result,
                                         Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result.get("status").equals("1")) {

                        String[] picList = result.get("four_pic").split(",");
                        String shop_code = result.get("shop_code");
                        if (shop_code.equals("null") || shop_code == null || shop_code.equals("")) {
                            ToastUtil.showShortText(context, "未获取到商品！");
                            return;
                        }


                        String supp_label = result.get("supp_label");
                        String shop_se_price = result.get("shop_se_price");
                        shop_name = result.get("shop_name");
                        shopLink = (String) result.get("link");//商品链接
//                      String def_pic=  result.get("def_pic");

                        String def_pic = result.get("four_pic").toString(); //微信分享全部改为分享小程序--用four_pic中第三张


                        if ("".equals(newShareText)) {
                            newShareText = shop_name;
                        }


                        getTyepe2SuppLabel(shop_code, shopLink, shop_se_price, supp_label, def_pic, shop_name);

                    }


                }


            }


        }.execute();


    }

    public void getShareTitleText() {
        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {

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
}
