package com.yssj.ui.fragment.circles;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yssj.YConstance;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.activity.wxapi.WXEntryActivity;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.dialog.PublicToastDialog;
import com.yssj.ui.dialog.ShopShareCompletDiaolg;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.SignCompleteDialogUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongjiShareCount;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class SignShareUtil implements WXEntryActivity.WXminiAPPshareListener {

    private Context mContext;
    private int shareNum;
    String shareTo = "link";//

    private String shareTitle = "对方不想和你说话，并向你扔了一部IPHONE7"; //大标题


    //小程序相关
    private String DuobaoShop_code = ""; //分享夺宝到小程序的夺宝编号
    private String shareMIniAPPimgPic;//普通商品分享到小程序的分享图
    private String duoBaoSharePic = ""; //夺宝分享的图
    private String H5moneyPic = ""; //赚钱页分享的图


    private PublicToastDialog loadDialog;


    public void share(Context context, int now_type_id) {


        this.mContext = context;
        this.now_type_id = now_type_id;

        WXEntryActivity.setWXminiShareListener(this);


        LayoutInflater mInflater = LayoutInflater.from(context);
        final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_sign_share_nom_shop_tishi, null);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        LinearLayout ll_share = view.findViewById(R.id.ll_share);


        ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteDialog.dismiss();


                if (SignListAdapter.doType == 32) {//分享赚钱任务页的任务
                    String shareText = "\uD83D\uDC47点击领取您的90元任务奖金！";

                    String shareMIniAPPimgPic = YUrl.imgurl + "small-iconImages/heboImg/taskraward_shareImg.png";

                    String wxMiniPathdUO = "/pages/mine/toexamine_test/toexamine_test?isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id() + "&showSignPage=true";


                    //分享到微信统一分享小程序
                    WXminiAPPShareUtil.shareToWXminiAPP(mContext, shareMIniAPPimgPic, shareText, wxMiniPathdUO, false);
                    return;

                }


                loadDialog = new PublicToastDialog(mContext, R.style.DialogStyle1, "");
                loadDialog.show();

                String[] value = SignListAdapter.doValue.split(",");
                try {
                    shareNum = Integer.parseInt(value[1]);
                } catch (Exception e) {
                    shareNum = SignListAdapter.doNum;
                }

                try {
                    shareTo = SignListAdapter.doValue.split(",")[0].split("=")[1];
                } catch (Exception e) {
                    e.printStackTrace();
                }


                //是否是特殊陆落地页的分享
                if (shareTo.equals("indiana") || shareTo.equals("h5money")) {
                    //确定分享内容然后分享
                    initShareContent();
                } else {
                    //分享普通商品
                    shareShop();
                }


            }
        });


        iv_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();

            }
        });

        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(deleteDialog);


    }

    private void shareShop() {
        //获取随机商品
        new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                // TODO Auto-generated method stub
                return ComModel2.getShareShopLinkHobbySign(context);
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

                        String shop_code = result.get("shop_code");
                        if (shop_code.equals("null") || shop_code == null || shop_code.equals("")) {

                            ToastUtil.showShortText(context, "未获取到商品！");
                            return;
                        }

                        TongjiShareCount.tongjifenxiangCount();
                        TongjiShareCount.tongjifenxiangwho(shop_code);


                        String four_pic = result.get("four_pic"); //微信分享全部改为分享小程序--用four_pic中第三张
                        String app_shop_group_price = result.get("app_shop_group_price");


                        shareMIniAPPimgPic = YUrl.imgurl + shop_code.substring(1, 4) + "/" + shop_code + "/" + four_pic.split(",")[2] + "!280";


                        //获取二级类目
                        getShopSup2(result.get("shop_name"), result.get("shop_code"), app_shop_group_price, four_pic);

                    }


                }


            }

        }.execute();


    }

    private void getShopSup2(final String shop_name, final String shop_code, final String app_shop_group_price, final String four_pic) {


        String shareText = "点击购买👆【" + shop_name + "】今日特价" + app_shop_group_price + "元！";
        String wxMiniPathdUO;

        if (SignListAdapter.doType == 7) {

            wxMiniPathdUO = "/pages/mine/toexamine_test/toexamine_test?shop_code=" + shop_code + "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id() +
                    "&isSign=false&shareFrom=signPage";


        } else {
            wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + shop_code +
                    "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();
        }


        if (null != loadDialog) {
            loadDialog.dismiss();
        }
        //分享到微信统一分享小程序
//        WXminiAPPShareUtil.shareShopToWXminiAPP(mContext, shareMIniAPPimgPic,"", shareText, wxMiniPathdUO, false);
        String sharePic = YUrl.imgurl + shop_code.substring(1, 4) + "/" + shop_code + "/" + four_pic.split(",")[2] + "!280";

        WXminiAPPShareUtil.shareShopToWXminiAPP(mContext, shop_name, app_shop_group_price, sharePic, wxMiniPathdUO, false);


//        new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {
//
//            @Override
//            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
//                    throws Exception {
//                return ComModelZ.geType2SuppLabe(mContext, shop_code);
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
//                    if (null != result) {
//                        String type2 = result.get("type2");
//                        if ("".equals(type2)) {
//                            type2 = "衣蝠";
//                        }
//
//                        String supp_label = result.get("supp_label_id");//品牌
//
//
//
//
////                        String shareText = "快来" + GuideActivity.oneShopPrice + "元拼【" + supp_label + "正品" + type2 + "】，专柜价" + shop_se_price + "元!";
//
//
//                        String shareText = "点击购买👆【" + shop_name +"】今日特价" + app_shop_group_price + "元！";
//
//                        String wxMiniPathdUO = "/pages/shouye/detail/detail?shop_code=" + shop_code +
//                                "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();
//                        if (null != loadDialog) {
//                            loadDialog.dismiss();
//                        }
//                        //分享到微信统一分享小程序
//                        WXminiAPPShareUtil.shareToWXminiAPP(mContext, shareMIniAPPimgPic, shareText, wxMiniPathdUO, false);
//
//
//                    }
//
//                }
//
//            }
//        }.execute(shop_code);
    }

    private void initShareContent() {


        //其他的直接取
        HashMap<String, Object> map = GuideActivity.textMap.get(shareTo);
        if (null != map && map.size() > 0) {//找到了
            //确定分享内容
            shareTitle = map.get("title") + "";

            H5moneyPic = YUrl.imgurl + map.get("icon");


            //确定分享链接
            if (shareTo.equals("indiana")) {//夺宝单独处理
                //获取夺宝后分享
                getDuoBaoShareLink();
                return;
            }
            //分享赚钱页
            String wxMiniPath = "/pages/shouye/shouye?isShareFlag=true&goto=sign&user_id=" + YCache.getCacheUser(mContext);
            if (null != loadDialog) {
                loadDialog.dismiss();
            }
            WXminiAPPShareUtil.shareToWXminiAPP(mContext, H5moneyPic, shareTitle, wxMiniPath, true);


        } else {//没有去到的话就去分享商品
            //分享普通商品
            shareShop();
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

                    DuobaoShop_code = result.get("shop_code");
                    String def_pic = result.get("def_pic").toString(); //微信分享全部改为分享小程序
                    duoBaoSharePic = YUrl.imgurl + DuobaoShop_code.substring(1, 4) + "/" + DuobaoShop_code + "/" + def_pic + "!280";

                    String wxMiniPath = "/pages/shouye/detail/centsIndianaDetail/centsDetail?shop_code=" + DuobaoShop_code +
                            "&isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();
                    if (null != loadDialog) {
                        loadDialog.dismiss();
                    }
                    //分享夺宝
                    WXminiAPPShareUtil.shareToWXminiAPP(mContext, duoBaoSharePic, shareTitle, wxMiniPath, false);
                }
            }

        }.execute();

    }

    private int num;
    private int now_type_id;

    @Override
    public void wxMiniShareSuccess() {


        final String SHARENUMKEY = SignListAdapter.signIndex + "share_num" + YCache.getCacheUser(mContext).getUser_id();
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
        SharedPreferencesUtil.saveStringData(mContext, "share_now_time" + YCache.getCacheUser(mContext).getUser_id(),
                now_time);
        SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "" + num);
        if (num < shareNum) {// 小于要求的分享次数
            if (SignListAdapter.doNum == 1) {// 就是一次性发放奖励 是现金奖励 就需要每次调接口 分次奖励
                ToastUtil.showShortText(mContext, "再分享" + (shareNum - num) + "件可完成任务喔~");
                return;
            }
        }
        // 签到
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) mContext, 0) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                if (now_type_id == 8) { // 如果是余额翻倍任务需要拿到余额翻倍剩余时间

                    return ComModel2.getSignIn(mContext, true, true, SignListAdapter.signIndex, SignListAdapter.doClass);
                } else {
                    // 如果不是余额翻倍任务就不用管

                    return ComModel2.getSignIn(mContext, false, true, SignListAdapter.signIndex, SignListAdapter.doClass);
                }

            }

            protected boolean isHandleException() {
                return true;
            }


            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null) {


                    if (Integer.valueOf(result.get("isNewbie01") + "") == 1) {
                        SignCompleteDialogUtil.firstClickInGoToZP(context);
                        return;
                    }


                    // 签到完成弹出成功框
                    // chooseDialog();
                    // SharedPreferencesUtil.saveBooleanData(context,
                    // "isFirstFenXiang", true);
                    if (now_type_id == 8) {
                        String t_time = (String) result.get("t_time");

                        SharedPreferencesUtil.saveStringData(context, YConstance.Pref.TWOFOLDNESS, "2"); // 余额翻倍倍数
                        SharedPreferencesUtil.saveStringData(context, YConstance.Pref.END_DATE, t_time + ""); // 余额翻倍剩余时间
                        SharedPreferencesUtil.saveBooleanData(context, YConstance.Pref.IS_QULIFICATION, true); // 余额翻倍是否有开启资格
                        SharedPreferencesUtil.saveStringData(context, YConstance.Pref.IS_OPEN, "1");// 是否已开启
                        // 0：未开启
                        // 1：已开启

                    }

                    // new SignFinishDialog(mActivity, now_type_id,
                    // now_type_id_value, next_type_id, next_type_id_value,
                    // false, "", false).show();
                    if (num < shareNum) {
                        String ss = "";
                        switch (SignListAdapter.jiangliID) {
                            case 3:
                                ss = "元优惠券";
                                break;
                            case 4:
                                ss = "积分";
                                break;
                            case 5:
                                ss = "元";
                                break;
                            case 11:
                                ss = "个衣豆";
                                break;
                            default:
                                break;
                        }
                        ToastUtil.showShortText(mContext, "分享成功奖励" +
                                // new
                                // java.text.DecimalFormat("#0.00").format(Double.valueOf(SignListAdapter.jiangliValue)/shareNum)
                                SignListAdapter.jiangliValue + ss + "，还有" + (shareNum - num) + "次机会喔~");
                    } else if (num >= shareNum) {
                        String ss = "";
                        switch (SignListAdapter.jiangliID) {
                            case 3:
                                ss = "元优惠券";
                                ShopShareCompletDiaolg dialog3 = new ShopShareCompletDiaolg(context, R.style.DialogQuheijiao2,
                                        "share_sign_finish", new java.text.DecimalFormat("0.##").format(
                                        Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum) + ss, SignFragment.signFragment);
                                dialog3.getWindow().setWindowAnimations(R.style.common_dialog_style);
                                dialog3.show();
                                SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                                SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                                break;
                            case 4:
                                ss = "积分";
                                ShopShareCompletDiaolg dialog4 = new ShopShareCompletDiaolg(context, R.style.DialogQuheijiao2,
                                        "share_sign_finish", new java.text.DecimalFormat("0.##").format(
                                        Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum) + ss, SignFragment.signFragment);
                                dialog4.getWindow().setWindowAnimations(R.style.common_dialog_style);

                                dialog4.show();
                                SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                                SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                                break;
                            case 5:
                                ss = "元";
                                ShopShareCompletDiaolg dialog5 = new ShopShareCompletDiaolg(context, R.style.DialogQuheijiao2,
                                        "share_sign_finish", new java.text.DecimalFormat("0.##").format(
                                        Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum) + ss, SignFragment.signFragment);
                                dialog5.getWindow().setWindowAnimations(R.style.common_dialog_style);

                                dialog5.show();
                                SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                                SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                                break;
                            case 11:// 衣豆
                                ss = "个衣豆";
                                ShopShareCompletDiaolg dialo11 = new ShopShareCompletDiaolg(context, R.style.DialogQuheijiao2,
                                        "share_sign_finish", new java.text.DecimalFormat("0.##").format(
                                        Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum) + ss, SignFragment.signFragment);
                                dialo11.getWindow().setWindowAnimations(R.style.common_dialog_style);

                                dialo11.show();
                                SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                                SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                                break;
                            case 8:// 余额翻倍
                                ShopShareCompletDiaolg dialog8 = new ShopShareCompletDiaolg(context, R.style.DialogQuheijiao2,
                                        "share_sign_fanbei_finish", SignFragment.signFragment);
                                dialog8.getWindow().setWindowAnimations(R.style.common_dialog_style);

                                dialog8.show();
                                SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                                SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                                break;
                            case 9:// 升级金币
                                ShopShareCompletDiaolg dialog9 = new ShopShareCompletDiaolg(context, R.style.DialogQuheijiao2,
                                        "share_sign_jinbi_finish", SignFragment.signFragment);
                                dialog9.getWindow().setWindowAnimations(R.style.common_dialog_style);

                                dialog9.show();
                                SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                                SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                                break;
                            case 10:// 升级金券
                                ShopShareCompletDiaolg dialog10 = new ShopShareCompletDiaolg(context, R.style.DialogQuheijiao2,
                                        "share_sign_jinquan_finish", SignFragment.signFragment);
                                dialog10.getWindow().setWindowAnimations(R.style.common_dialog_style);

                                dialog10.show();
                                SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                                SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                                break;

                            default:
                                break;
                        }
                    }

                } else {
                    // ToastUtil.showLongText(mActivity, "未知错误");
                }

            }

        }.execute();
    }
}
