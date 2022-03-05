package com.yssj.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
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
import com.yssj.ui.activity.infos.MyWalletCommonFragmentActivity;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.SignCompleteDialogUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/6/29. 老分享赢提现
 */

public class ShareGetTXdialog extends Dialog {
    public static Context mContext;
    private String sharePic = "";//分享图片链接
    public static String code = "";//商品编号
    private double shopMoney = 0;//商品价格
    public static TextView tvShareCount;//微信分享角标
    public static TextView tvBuyCount;//提现角标
    public static int flagMax = 10;//分享满多少次给一次机会
    public static int shareCount = 10;//分享图标上显示的次数
    public static int branchCount = 0;//获得到1元提现的次数
    public static int dayOreadyShareCount = 0;//今天已经分享的次数

    public static ShareGetTXdialog xdialog;
    private static int shareTotalCount;//分享上限

    private static int forcelookLimitNum;// 浏览奖励提现额度 强制浏览的计数

    private static SimpleDateFormat df;


    private static String singvalue;// 强制浏览数量
    public static boolean wxClick = false;

    String shareTo = "link";//默认随机商品

    private LinearLayout mLlRuleContainer;

//    private TextView tv_tv;

    //小程序相关
    private String duoBaoSharePic = ""; //夺宝分享的图
    private String DuobaoShop_code; //夺宝分享商品编号


    //分享相关
    private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);
    private Intent weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());// 分享到微信好友
    //    String link = YUrl.YSS_URL_ANDROID_H5 + "view/download/6.html?realm=" + YCache.getCacheUser(mContext).getUser_id();
    private String link = "";//分享链接
    private String shareTitle = "对方不想和你说话，并向你扔了一部IPHONE7"; //大标题
    private String shareContent = "比一元夺宝更牛的1分夺宝来了，1分钱抽IPHONE7，马上开奖了。快来！";//小标题
    private UMImage umImage; //小图标

    private List<String> result = new ArrayList<String>();
    private String newShareTitle = "衣蝠品牌商出品，拼团仅售39元，快来一起拼！";
    private String newShareText = "";
    String str4 = ""; //替换后的 用于放新的分享title
    String shop_name = "衣蝠";
    String type2 = "";


    public ShareGetTXdialog(Context context, int style) {
        super(context, style);
        setCanceledOnTouchOutside(true);
        this.mContext = context;
//        this.result = r;

        df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String value = SignListAdapter.doValue;
        String values[] = value.split(",");
        if (values.length > 1) {
            singvalue = values[1];
            if (!Pattern.compile("^\\+?[1-9][0-9]*$").matcher(singvalue).find()) {
                singvalue = "10";
            }

        } else {
            singvalue = "10";
        }

        try {
            flagMax = Integer.parseInt(singvalue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null == SignListAdapter.doNeedCount || "".equals(SignListAdapter.doNeedCount) || "null".equals(SignListAdapter.doNeedCount)) {//任务还没开始做
            shareTotalCount = SignListAdapter.doNum * Integer.parseInt(singvalue);
        } else {//任务已经做过一部分
            int doNeedCount = Integer.parseInt(SignListAdapter.doNeedCount);
            shareTotalCount = doNeedCount * Integer.parseInt(singvalue);
        }

        forcelookLimitNum = Integer
                .parseInt(
                        SharedPreferencesUtil
                                .getStringData(mContext,
                                        SignListAdapter.signIndex + YConstance.Pref.ISFORCELOOKLIMITNUMSHARE
                                                + YCache.getCacheUser(mContext).getUser_id(),
                                        "0"));

        //如果已经隔天，将当次任务的分享数置0
        String nowTimeForcelookLimit = SharedPreferencesUtil.getStringData(
                mContext,
                "nowTimeForcelookLimit_SHARE" + YCache.getCacheUser(mContext).getUser_id(), "");

        if (!df.format(new Date()).equals(nowTimeForcelookLimit)) {
            forcelookLimitNum = 0;// 不是同一天点击分享任务
            // 或者不是同一个用户 就
            // 或者取出的数据大于浏览次数
            // 重新开始计数分享的次数
        }

        SharedPreferencesUtil.saveStringData(mContext,
                "nowTimeForcelookLimit_SHARE" + YCache.getCacheUser(mContext).getUser_id(),
                df.format(new Date()));


        initView();
    }


    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_tx_details, null);
        setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        setCancelable(false);
        xdialog = this;


        StringUtils.initShareText(
                (TextView) view.findViewById(R.id.tv1),
                (TextView) view.findViewById(R.id.tv2),
                (TextView) view.findViewById(R.id.tv3),
                (TextView) view.findViewById(R.id.tv4)
        );

        TextView dialog_close = (TextView) view.findViewById(R.id.dialog_colse);
        LinearLayout indiana_ll_share = (LinearLayout) view.findViewById(R.id.indiana_ll_share);//分享微信好友
        LinearLayout indiana_ll_branch = (LinearLayout) view.findViewById(R.id.indiana_ll_branch);//1分钱夺宝
        LinearLayout indiana_ll_normal = (LinearLayout) view.findViewById(R.id.indiana_ll_normal);//2元夺宝
        tvShareCount = (TextView) view.findViewById(R.id.tv_share_count);
        tvBuyCount = (TextView) view.findViewById(R.id.tv_branch_count);
        mLlRuleContainer = (LinearLayout) view.findViewById(R.id.indiana_ll_rule_container);


//        tv_tv = (TextView) view.findViewById(R.id.tv_tv);

        shareCount = Integer.parseInt(singvalue) - (forcelookLimitNum % Integer.parseInt(singvalue));

        tvShareCount.setText(shareCount + "");

        tvBuyCount.setText("" + branchCount);


        dialog_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 把这个对话框取消掉
                dismiss();
            }
        });
        indiana_ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                if (forcelookLimitNum >= shareTotalCount) {
                    ToastUtil.showMyToast(mContext, "今日的分享次数已到达上限，明天再来吧~", 3000);
                    dismiss();
                    return;
                }

                TongJiUtils.yunYunTongJi("fxytx", 1102, 11, mContext);

                share();

            }
        });
        indiana_ll_branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //去提现
                Intent to_withdrawal_btn_intent = new Intent(mContext, MyWalletCommonFragmentActivity.class);
                to_withdrawal_btn_intent.putExtra("flag", "withDrawalFragment");
                to_withdrawal_btn_intent.putExtra("alliance", "wallet");
                mContext.startActivity(to_withdrawal_btn_intent);


            }
        });
        indiana_ll_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });


//        String s = "2.每分享" + shareCount + "次给微信好友（只可分享给不同群或好友，否则只记1次），即可得到" + SignListAdapter.jiangliValue + "元钱提现额度。";

        //2、每分享10次微信群或好友（只可分享给不同群或好友，否则只记1次），即可得1元提现。

//        String s = "2.每分享" + flagMax + "次微信群或好友（只可分享给不同群或好友，否则只记1次），即可得到" + SignListAdapter.jiangliValue + "元提现。";

        //任务奖励上限

        int tCount = 10;
        try {
            tCount = SignListAdapter.doNum * Integer.parseInt(SignListAdapter.jiangliValue);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


//        String s = "点下方分享按钮，分享" + flagMax + "次美衣或赚钱小任务到微信群。任意从未来过衣蝠的新朋友点击后，每" + flagMax + "人点击，你可得" + SignListAdapter.jiangliValue + "元提现现金。今日" + tCount + "元封顶。";
//
//
//        tv_tv.setText(s);


        //填充说明
//        mLlRuleContainer.removeAllViews();
//        for (int i = 0; i < result.size(); i++) {
//
//            TextView tvRule = new TextView(mContext);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
//                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            tvRule.setTextSize(14);
//            tvRule.setTextColor(Color.parseColor("#7d7d7d"));
//            tvRule.setText((i + 1) + "." + result.get(i));
//            params.setMargins(0, DP2SPUtil.dip2px(mContext, 16), 0, 0);
//            mLlRuleContainer.addView(tvRule, params);
//        }

        getShareTitleText();

        //确定分享内容
        initShareContent();


    }

    private void initShareContent() {

        try {
            shareTo = SignListAdapter.doValue.split(",")[0].split("=")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }

//        shareTo = "h5money";

        if (shareTo.equals("link")) {//分享随机商品

            shareTo = "link";

        } else {
            //其他的直接取
            HashMap<String, Object> map = GuideActivity.textMap.get(shareTo);
            if (null != map && map.size() > 0) {//找到了
                //确定分享内容
                shareTitle = map.get("title") + "";
                shareContent = map.get("text") + "";
                umImage = new UMImage(mContext, YUrl.imgurl + map.get("icon"));
                //确定分享链接

                if (shareTo.equals("spellGroup")) {//拼团
                    link = YUrl.YSS_URL_ANDROID_H5 + "/view/activity/pack.html?realm=" + YCache.getCacheUser(mContext).getUser_id();
                } else if (shareTo.equals("indiana")) {//夺宝
                    //获取夺宝
                    getDuoBaoShareLink();

                } else if (shareTo.equals("h5money")) {//H5赚钱页
                    link = YUrl.YSS_URL_ANDROID_H5 + "/view/activity/mission.html?realm=" + YCache.getCacheUser(mContext).getUser_id();
                }

            } else {//没有去到的话就去分享商品
                shareTo = "link";
            }


        }


    }


    private void getDuoBaoShareLink() {

        new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                return ComModel2.getsuijishopLink(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    link = result.get("link");

                    String shop_code = result.get("shop_code");

                    DuobaoShop_code = result.get("shop_code");

                    String def_pic = result.get("def_pic").toString(); //微信分享全部改为分享小程序

                    duoBaoSharePic = YUrl.imgurl + shop_code.substring(1, 4) + "/" + shop_code + "/" + def_pic + "!280";
                    LogYiFu.e("duoBaoSharePic", duoBaoSharePic);


//
//                    String shopPiC = "https://www.measures.wang/small-iconImages/ad_pic/ic_launcher.png";
//
//
//                    //其他的直接取
//                    HashMap<String, Object> map = GuideActivity.textMap.get(shareTo);
//                    if (null != map && map.size() > 0) {//找到了
//                        //确定分享内容
//                        shareTitle = map.get("title") + "";
//                        shareContent = map.get("text") + "";
//                        shopPiC = YUrl.imgurl + map.get("icon");
//                        umImage = new UMImage(mContext, YUrl.imgurl + map.get("icon"));
//                    }
//                    String wxMiniPath =   "/pages/shouye/detail/centsIndianaDetail/centsDetail?shop_code=" + shop_code +
//                            "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext);
//                    //分享到微信统一分享小程序
//                    wxClick = true;
//                    WXminiAPPShareUtil.shareToWXminiAPP(mContext, shopPiC, shareTitle, wxMiniPath);
                }
            }

        }.execute(code);

    }


    /**
     * 得到分享的链接
     */
    public void share() {


        if (shareTo.equals("link")) {//分享随机商品
            //分享随机商品到微信好友
            shareShop();
        } else {

            if (shareTo.equals("spellGroup")) {
                //获取随机拼团编号并开始分享
                getPintuanCode();
            } else {  //夺宝和H5赚钱页


//                ShareUtil.addWXPlatform(mContext);
//                //设置分享内容并且开始分享
//                WeiXinShareContent wei = new WeiXinShareContent();
//                wei.setShareContent("" + shareContent);
//                wei.setTitle("" + shareTitle);
//                wei.setTargetUrl(link);
//                wei.setShareMedia(umImage);
//                mController.setShareMedia(wei);
//                performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);


                String shopPiC = "https://www.measures.wang/small-iconImages/ad_pic/ic_launcher.png";
                String wxMiniPath = "/pages/shouye/shouye?isShareFlag=true&goto=sign&user_id=" + YCache.getCacheUser(mContext);


                if (shareTo.equals("indiana")) { //夺宝的要取商品编号
                    //获取夺宝
//                    getDuoBaoShareLink();


                    String wxMiniPathdUO = "/pages/shouye/detail/centsIndianaDetail/centsDetail?shop_code=" + DuobaoShop_code +
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
                        umImage = new UMImage(mContext, YUrl.imgurl + map.get("icon"));

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

    private void getPintuanCode() {

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
                    ShareUtil.addWXPlatform(mContext);
                    //设置分享内容并且开始分享
//                    WeiXinShareContent wei = new WeiXinShareContent();
//                    wei.setShareContent("" + shareContent);
//                    wei.setTitle("" + shareTitle);
//                    wei.setTargetUrl(link + "&r_code=" + result);
//                    wei.setShareMedia(umImage);
//                    mController.setShareMedia(wei);
//                    performShare(SHARE_MEDIA.WEIXIN, weixinShareIntent);

                }
            }

        }.execute(code);


    }

    private String shopLink = "";


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
//                    str2 = str1.replaceFirst("\\$\\{replace\\}", type2);
//                    str3 = str2.replaceFirst("\\$\\{replace\\}", "" + new DecimalFormat("#0.0").format(Math.round(Double.parseDouble(shop_se_price) * 0.5 * 10) * 0.1d));
//                    str4 = str3.replaceFirst("\\$\\{replace\\}", "" + new DecimalFormat("#0.0").format(Math.round(Double.parseDouble(shop_se_price) * 0.5 * 10) * 0.1d));


//                    String shopPiC = YUrl.imgurl + shop_code.substring(1, 4) + "/" + shop_code + "/" + def_pic;
                    //def_pic已经改为four_pic
                    String shopPiC = YUrl.imgurl + shop_code.substring(1, 4) + "/" + shop_code + "/" + def_pic.split(",")[2] + "!280";


                    str4 = "快来" +
                            GuideActivity.oneShopPrice + "元拼【" +
                            supp_label + "正品" + type2 + "】，专柜价" + shop_se_price + "元！";


                    String wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + shop_code +
                            "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();


                    //分享到微信统一分享小程序
                    wxClick = true;
                    WXminiAPPShareUtil.shareToWXminiAPP(mContext, shopPiC, str4, wxMiniPathdUO, false);

//                    ShareUtil.addWXPlatform(mContext);
//                    weixinShareIntent = ShareUtil.shareToWechat(ShareUtil.getImage());
//                    WeiXinShareContent wei = new WeiXinShareContent();

//                    wei.setShareContent(newShareText);
//                    wei.setTitle(str4);

//                        wei.setShareContent("衣蝠APP");
//                        wei.setTitle(StringUtils.getShareContentNew() + "");
//                    wei.setTargetUrl(shopLink+"&share=true");
//                    wei.setShareMedia(new UMImage(mContext, shopPiC));
//                    mController.setShareMedia(wei);
//                            performShareWXHY(SHARE_MEDIA.WEIXIN, weixinShareIntent);
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
//                        String def_pic=  result.get("def_pic");
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


    public void performShare(SHARE_MEDIA platform, final Intent intent) {
        UMImage umImage;

        // File bmg = null;
        // Drawable img_invite_friend =
        // getResources().getDrawable(R.drawable.invite_friend);
        // BitmapDrawable bd = (BitmapDrawable) img_invite_friend;
//        umImage = new UMImage(mContext, "" + YUrl.imgurl + sharePic);
//
//        ShareUtil.setShareContent(mContext, umImage, "" + shareContent, link);

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


    public static void submitShareTXCountRecord() {

        if (forcelookLimitNum / Integer.parseInt(singvalue) + 1 > SignListAdapter.doNum
                || SignListAdapter.isSignComplete) {
            //浏览 奖励额度 达到上限
            NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(mContext,
                    R.style.DialogQuheijiao2, YConstance.Pref.LIULAN_SIGN_UPPER_LIMIT);
            dialog.show();
            forcelookLimitNum++;
            SharedPreferencesUtil
                    .saveStringData(mContext,
                            SignListAdapter.signIndex + YConstance.Pref.ISFORCELOOKLIMITNUMSHARE
                                    + YCache.getCacheUser(mContext).getUser_id(),
                            "" + forcelookLimitNum);

        } else {

            if (forcelookLimitNum % Integer.parseInt(singvalue) + 1 < Integer.parseInt(singvalue)) {//1-9次
//                ToastUtil.showMyToast(mContext,
//                        "分享成功，再分享" + (Integer.parseInt(singvalue) - (forcelookLimitNum % Integer.parseInt(singvalue) + 1)) + "次即可赢得"
//                                + SignListAdapter.jiangliValue
//                                + "元提现额度,继续努力~", 5000);


                ToastUtil.showMyToast(mContext,
                        "再分享" + (Integer.parseInt(singvalue) - (forcelookLimitNum % Integer.parseInt(singvalue) + 1)) + "次即可赢得"
                                + SignListAdapter.jiangliValue
                                + "元提现额度,继续努力~", 3000);

                ToastUtil.showMyToast(mContext,
                        "再分享" + (Integer.parseInt(singvalue) - (forcelookLimitNum % Integer.parseInt(singvalue) + 1)) + "次即可赢得"
                                + SignListAdapter.jiangliValue
                                + "元提现额度,继续努力~", 3000);


                forcelookLimitNum++;
                SharedPreferencesUtil
                        .saveStringData(mContext,
                                SignListAdapter.signIndex + YConstance.Pref.ISFORCELOOKLIMITNUMSHARE
                                        + YCache.getCacheUser(mContext).getUser_id(),
                                "" + forcelookLimitNum);


                shareCount--;
                if (shareCount <= 0) {//分享数


                    shareCount = flagMax;
                    branchCount++;//提现数


                }
//                dayOreadyShareCount++;
                tvShareCount.setText("" + shareCount);
//                tvBuyCount.setText("" + branchCount);


//                if (branchCount > 99) {
//                    tvBuyCount.setTextSize(8);
//                } else {
//                    tvBuyCount.setTextSize(12);
//                }


                wxClick = false;
//                ToastUtil.showMyToast(mContext, "分享成功，再分享" + shareCount + "次即可赢得一次1分夺宝的机会，继续努力。", 3000);


            } else if (forcelookLimitNum % Integer.parseInt(singvalue) + 1 == Integer.parseInt(singvalue)) {//第10次
                signLimit();
            }

        }


    }


    public static void signLimit() {
        final String ss = "元提现额度";
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) mContext, 0) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel2.getSignIn(mContext, false, false, SignListAdapter.signIndex,
                        SignListAdapter.doClass);

            }

            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null) {

                    if (Integer.valueOf(result.get("isNewbie01") + "") == 1) {
                        xdialog.dismiss();
                        SignCompleteDialogUtil.firstClickInGoToZP(mContext);
                        return;
                    }

                    SharedPreferencesUtil.saveStringData(mContext,
                            "nowTimeForcelookLimit_SHARE" + YCache.getCacheUser(context).getUser_id(),
                            df.format(new Date()));

//                    if (forcelookLimitNum / Integer.parseInt(singvalue) + 1 <= SignListAdapter.doNum) {// 小于要求的浏览次数

                    wxClick = false;
                    forcelookLimitNum++;

                    if (forcelookLimitNum < shareTotalCount) {
                        ToastUtil.showMyToast(mContext,
                                SignListAdapter.jiangliValue + ss + "提现额度已经存入您的余额，再分享"
                                        + (Integer.parseInt(singvalue) + "次可再赢得" + SignListAdapter.jiangliValue + ss
                                        + ",继续努力~"), 6000);
                    } else {//已经拿到全部奖励
                        xdialog.dismiss();
                    }


                    SharedPreferencesUtil
                            .saveStringData(mContext,
                                    SignListAdapter.signIndex + YConstance.Pref.ISFORCELOOKLIMITNUMSHARE
                                            + YCache.getCacheUser(context).getUser_id(),
                                    "" + forcelookLimitNum);

                    //分享数重置
                    shareCount = Integer.parseInt(singvalue);
                    tvShareCount.setText(shareCount + "");

                    //提现数加1
                    branchCount++;
                    tvBuyCount.setText("" + branchCount);
                    if (branchCount > 99) {
                        tvBuyCount.setTextSize(8);
                    } else {
                        tvBuyCount.setTextSize(12);
                    }


                }

            }


        }.

                execute();
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
