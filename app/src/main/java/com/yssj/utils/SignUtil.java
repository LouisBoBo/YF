package com.yssj.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.huanxin.PublicUtil;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.activity.BuyFreeActivity;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.GroupsDetailsActivity;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.MineLikeActivity;
import com.yssj.ui.activity.MyLikeActivity;
import com.yssj.ui.activity.PointLikeRankingActivity;
import com.yssj.ui.activity.WithdrawalLimitActivity;
import com.yssj.ui.activity.main.ForceLookActivity;
import com.yssj.ui.activity.main.ForceLookMatchActivity;
import com.yssj.ui.activity.main.IndianaListActivity;
import com.yssj.ui.activity.main.SignActiveShopActivity;
import com.yssj.ui.activity.main.SignGroupShopActivity;
import com.yssj.ui.dialog.DialogSignFenzhongTishi;
import com.yssj.ui.dialog.DianZanSucceedDiaolg;
import com.yssj.ui.dialog.JiZanCommonDialog;
import com.yssj.ui.dialog.LingYUANGOUTishiRedDialog;
import com.yssj.ui.dialog.NewShareGetTXDialog;
import com.yssj.ui.dialog.NewSignCommonDiaolg;
import com.yssj.ui.dialog.ShareGetTXdialog;
import com.yssj.ui.dialog.SignShareShopDialog;
import com.yssj.ui.fragment.YaoQingFrendsActivity;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.ui.fragment.circles.SignListAdapter;

import java.util.HashMap;
import java.util.List;


/**
 * Created by qingfeng on 2017/10/21.
 */

public class SignUtil {


    public static void ZiDongClickNextTask(Context mContext, int position, List<HashMap<String, Object>> initList, List<HashMap<String, Object>> taskList, List<HashMap<String, Object>> taskiconList) {


        SignListAdapter.doClass = Integer.parseInt(initList.get(position) // 任务类型（区分用的）
                .get("task_class").toString());

        int tast = SignListAdapter.doClass;


        if (tast == 3) {// 明日预告没有点击事件
            return;
        }

        if (initList.size() == 0 || position > initList.size() - 1) {
            return;
        }


        try {
            SignListAdapter.doType = Integer.parseInt(initList.get(position) // 任务类型（去做什么）
                    .get("task_type").toString());
        } catch (Exception e1) {
            e1.printStackTrace();
        }


        // boolean isA =
        // SharedPreferencesUtil.getBooleanData(mContext,
        // Pref.ISACLASS,
        // false);
        // if (isA) {
        // if (rankValue <= 0 && tast == 1) { //
        // 如果会员用户的活力值小于等于0且点击了必做任务提示活力值不足
        // new
        // MemberPowerNoEnoughDialog(mContext,
        // R.style.DialogQuheijiao2,
        // "power=0", 0 + "", "").show();
        //
        // return;
        // }
        //
        // }

        // 完成状态


        String status = "-1";
        status = initList.get(position).get("status") + "";
        boolean s_complete = status.equals("0");
        boolean isComplete = (initList.get(position).get("signStatus") + "").equals("1") && s_complete; // 任务时当时的完成状态

        SignListAdapter.doValue = initList.get(position).get("value") + ""; // 做什么的value
        SignListAdapter.doNeedCount = initList.get(position).get("status") + ""; // 做什么的value




        SignListAdapter.doIconId = initList.get(position).get("icon") + "";


        try {
            SignListAdapter.doNum = Integer.parseInt(initList.get(position) // 任务类型（去做什么）
                    .get("num").toString()); // 奖励分几次发
        } catch (Exception eee) {
            eee.printStackTrace();
        }

        if (null == initList.get(position).get("num")
                || initList.get(position).get("num").equals("")) {
            if (!isComplete) {
                ToastUtil.showShortText(mContext, "此任务不存在,或配置错误");
                return;
            }

        }

        SignListAdapter.signIndex = initList.get(position).get("index") + "";

        // 拿到点击的条目的奖励
        for (int j = 0; j < taskList.size(); j++) {

            String id = initList.get(position).get("t_id").toString(); // 需要做的任务的ID----tasklist中的t_id做对比，
            String taskID = (String) taskList.get(j).get("t_id"); // 任务类型ID

            if (id.equals(taskID)) {
                SignListAdapter.jiangliValue = taskList.get(j).get("value") + "";
                SignListAdapter.jiangliID = Integer.parseInt(taskList.get(j).get("type_id").toString());
            }

        }
        // 分享
        if (SignListAdapter.doType == 7 || SignListAdapter.doType == 8 || SignListAdapter.doType == 701 || SignListAdapter.doType == 702
                || SignListAdapter.doType == 703 || SignListAdapter.doType == 801 || SignListAdapter.doType == 802 || SignListAdapter.doType == 803) {

            if (!SignShareShopDialog.isShow) {

                try {
                    SignListAdapter.gotoShareValue = SignListAdapter.doValue.split(",")[0];
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


                SignShareShopDialog signshareshopdialog =
                        new SignShareShopDialog((Activity) mContext, mContext, R.style.DialogStyle1,
                                SignListAdapter.jiangliID);
                signshareshopdialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
                signshareshopdialog.show();

            }

        }


        Intent intent = null;
        switch (SignListAdapter.doType) {

            case 0: // 开店 --没有开店了

                Intent intentKaidian = new Intent(mContext, MineLikeActivity.class);
                intentKaidian.putExtra("isSign", true);
                ((MainMenuActivity) mContext).startActivityForResult(intentKaidian, 13334);

                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);

                break;
            case 1:// 邀请好友

                double getValue = 0;

                for (int i = 0; i < taskList.size(); i++) { // 遍历任务类型

                    // 去做对比

                    String id = initList.get(position).get("t_id").toString();
                    String taskID = (String) taskList.get(i).get("t_id"); // 任务类型ID

                    if (id.equals(taskID)) { // 如果对上，就能找到该条目对应的信息
                        // ，填充即可
                        getValue = Double.parseDouble(taskList.get(i) // 奖励数量（得到什么）
                                .get("value").toString());
                    }

                }

                Intent intentYao = new Intent(mContext, YaoQingFrendsActivity.class);
                intentYao.putExtra("jumpFrom", "YaoQingHaoyou");

                mContext.startActivity(intentYao);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);

                // Intent intentYao = new
                // Intent(mContext,
                // InvitingFriendsDetails.class);
                // intentYao.putExtra("signMoney",
                // getValue + "");
                // intentYao.putExtra("isComplete",
                // isComplete);
                // mContext.startActivity(intentYao);
                //
                // ((FragmentActivity)
                // mContext).overridePendingTransition(
                // R.anim.slide_left_in,
                // R.anim.slide_match);

                break;
            case 2: // 夺宝

//                                                 indexMap.put(YConstance.DUOBAO_INDEX,
//                                                 signIndex);
                TongJiUtils.yunYunTongJi("duobao", 1001, 10, mContext);

                Intent intentDuo = new
                        Intent(mContext,
                        IndianaListActivity.class);
//                                                intentDuo.putExtra("SignShopDetail",
//                                                        "SignShopDetail");
//                                                intentDuo.putExtra("valueDuo",
//                                                        doValue);
                mContext.startActivity(intentDuo);
                ((FragmentActivity)
                        mContext).overridePendingTransition(
                        R.anim.slide_left_in,
                        R.anim.slide_match);

                break;
            case 3: // 加X件商品到购物车
                // indexMap.put(YConstance.ADD_TO_SHOPCART,
                // signIndex);
                SharedPreferencesUtil.saveStringData(mContext, YConstance.ADD_TO_SHOPCART,
                        SignListAdapter.signIndex);

                SignListAdapter.classMap.put(YConstance.ADD_TO_SHOPCART, SignListAdapter.doClass);

                // 加购物车的单独赋值

                SignListAdapter.doValueShopCart = initList.get(position).get("value").toString(); // 做什么的value

                try {
                    SignListAdapter.doNumShopCart = Integer.parseInt(initList.get(position) // 任务类型（去做什么）
                            .get("num").toString()); // 奖励分几次发
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                // 拿到点击的条目的奖励
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // 需要做的任务的ID----tasklist中的t_id做对比，
                    String taskID = (String) taskList.get(j).get("t_id"); // 任务类型ID

                    if (id.equals(taskID)) {
                        SignListAdapter.jiangliValueShopCart = taskList.get(j).get("value") + "";
                        SignListAdapter.jiangliIDShopCart = Integer
                                .parseInt(taskList.get(j).get("type_id").toString());
                    }

                }

                NewSignCommonDiaolg addshopcartDiaolg = new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                        "addshopcarttishi", SignFragment.signFragment, "");

                addshopcartDiaolg.getWindow().setWindowAnimations(R.style.common_dialog_style);
                addshopcartDiaolg.show();


                break;
            case 4: // 浏览X件普通商品 //要求的是-----个数

                String lei = "";

                lei = null; // 类型
                try {
                    lei = SignListAdapter.doValue.split(",")[0];
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                if (lei.equals("collection=collocation_shop")) {// 搭配
                    intent = new Intent(mContext, ForceLookMatchActivity.class);
                    intent.putExtra("type", "1");
                    intent.putExtra("doIconId", SignListAdapter.doIconId);
                    intent.putExtra("isSignLiulan", true);
                    mContext.startActivity(intent);
                    SharedPreferencesUtil.saveStringData(mContext, YConstance.Pref.SINGVALUE, SignListAdapter.doValue);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);
                } else if (lei.equals("collection=shop_activity")) { // 活动商品

                    intent = new Intent(mContext, SignActiveShopActivity.class);

                    intent.putExtra("doIconId", SignListAdapter.doIconId);
                    intent.putExtra("isSignLiulan", true);

                    mContext.startActivity(intent);


                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);


                } else if (lei.equals("collection=browse_shop")) { // 热门推首
                    // ----完全用以前的强制浏览
                    intent = new Intent(mContext, ForceLookActivity.class);

                    intent.putExtra("doIconId", SignListAdapter.doIconId);
                    intent.putExtra("isSignLiulan", true);
                    intent.putExtra("pinJievalue", lei);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);
                } else {
                    // 其他的用以前的强制浏览
                    SharedPreferencesUtil.saveStringData(mContext, YConstance.Pref.SINGVALUE, SignListAdapter.doValue);
                    for (int j = 0; j < taskiconList.size(); j++) {
                        if ((taskiconList.get(j).get("id") + "")
                                .equals(initList.get(position).get("icon") + "")) {
                            intent = new Intent(mContext, ForceLookActivity.class);
                            intent.putExtra("isFilterConditionActivity", true);
                            intent.putExtra("title",
                                    taskiconList.get(j).get("app_name") + "");
                            intent.putExtra("pinJievalue", lei);


                            intent.putExtra("doIconId", SignListAdapter.doIconId);
                            intent.putExtra("isSignLiulan", true);


                            mContext.startActivity(intent);
                            ((Activity) mContext).overridePendingTransition(
                                    R.anim.slide_left_in, R.anim.slide_match);

                        }

                    }

                }

                break;


            case 19://
                String leiTX = "";

                try {
                    leiTX = SignListAdapter.doValue.split(",")[0]; // 类型
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


//                                            intent = new Intent(context, ForceLookActivity.class);
//                                            intent.putExtra("isFilterConditionActivity", true);
//                                            intent.putExtra("title", "热卖");
//                                            intent.putExtra("pinJievalue", "collection=collocation_shop");
//
//                                            intent.putExtra("doIconId", doIconId);
//                                            intent.putExtra("isSignLiulan", true);
//
//
//                                            context.startActivity(intent);
//                                            ((Activity) context).overridePendingTransition(
//                                                    R.anim.slide_left_in, R.anim.slide_match);


                if (leiTX.equals("collection=collocation_shop")) {// 搭配
//                                                intent = new Intent(context, ForceLookMatchActivity.class);
//                                                intent.putExtra("type", "1");
//                                                intent.putExtra("doIconId", doIconId);
//                                                intent.putExtra("isSignLiulan", true);
//                                                startActivity(intent);
//                                                SharedPreferencesUtil.saveStringData(mContext, Pref.SINGVALUE, doValue);
//                                                ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
//                                                        R.anim.slide_match);


                    intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("title", "热卖");
                    intent.putExtra("pinJievalue", leiTX);

                    intent.putExtra("doIconId", SignListAdapter.doIconId);
                    intent.putExtra("isSignLiulan", true);


                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);


                } else if (leiTX.equals("collection=shop_activity")) { // 活动商品
//                                                intent = new Intent(mContext, SignActiveShopActivity.class);
//
//                                                intent.putExtra("doIconId", doIconId);
//                                                intent.putExtra("isSignLiulan", true);
//
//
//                                                startActivity(intent);
//                                                ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
//                                                        R.anim.slide_match);
//

                    intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("title", "热卖");
                    intent.putExtra("pinJievalue", leiTX);

                    intent.putExtra("doIconId", SignListAdapter.doIconId);
                    intent.putExtra("isSignLiulan", true);


                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);


                } else if (leiTX.equals("collection=browse_shop")) { // 热门推首
                    // ----完全用以前的强制浏览
                    intent = new Intent(mContext, ForceLookActivity.class);

                    intent.putExtra("doIconId", SignListAdapter.doIconId);
                    intent.putExtra("isSignLiulan", true);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);
                } else {
                    // 其他的用以前的强制浏览
//                                                    SharedPreferencesUtil.saveStringData(mContext, Pref.SINGVALUE, doValue);
//                                                    for (int j = 0; j < taskiconList.size(); j++) {
//                                                        if ((taskiconList.get(j).get("id") + "")
//                                                                .equals(initList.get(position).get("icon") + "")) {
//                                                            Intent intent = new Intent(context, ForceLookActivity.class);
//                                                            intent.putExtra("isFilterConditionActivity", true);
//                                                            intent.putExtra("title",
//                                                                    taskiconList.get(j).get("app_name") + "");
//                                                            intent.putExtra("pinJievalue", leiTX);
//                                                            context.startActivity(intent);
//                                                            ((Activity) context).overridePendingTransition(
//                                                                    R.anim.slide_left_in, R.anim.slide_match);
//
//                                                        }
//
//                                                    }


                    intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("title", "热卖");
                    intent.putExtra("pinJievalue", leiTX);

                    intent.putExtra("doIconId", SignListAdapter.doIconId);
                    intent.putExtra("isSignLiulan", true);


                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);

                }

                break;


            case 20://分享赢提现额度

                TongJiUtils.yunYunTongJi("fxytx", 1101, 11, mContext);

                ToastUtil.showDialog(new ShareGetTXdialog(mContext, R.style.invate_dialog_style));

//                                                getIndianaRuleContent();


                //                                                ToastUtil.showDialog(new ShareGetTXdialog(mContext, R.style.invate_dialog_style));

                break;


            case 21://一元夺宝（提现额度夺宝）


                Intent intentDuoTX = new
                        Intent(mContext,
                        IndianaListActivity.class);
//                                                intentDuo.putExtra("SignShopDetail",
//                                                        "SignShopDetail");
//                                                intentDuo.putExtra("valueDuo",
//                                                        doValue);
                mContext.startActivity(intentDuoTX);
                ((FragmentActivity)
                        mContext).overridePendingTransition(
                        R.anim.slide_left_in,
                        R.anim.slide_match);


                break;

            case 5:// 浏览商品集合和活动商品 X分钟
                // 要求的------是分钟
                String leii = "";


                try {
                    SignListAdapter.doNumShopLiulan = Integer.parseInt(initList.get(position) // 任务类型（去做什么）
                            .get("num").toString()); // 奖励分几次发

                    leii = SignListAdapter.doValue.split(",")[0]; // 类型

                    SignListAdapter.doValueLiulan = initList.get(position).get("value").toString(); // 做什么的value
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                // 拿到点击的条目的奖励
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // 需要做的任务的ID----tasklist中的t_id做对比，
                    String taskID = (String) taskList.get(j).get("t_id"); // 任务类型ID

                    if (id.equals(taskID)) {
                        SignListAdapter.jiangliValueLiulan = taskList.get(j).get("value") + "";
                        SignListAdapter.jiangliIDLiulan = Integer
                                .parseInt(taskList.get(j).get("type_id").toString());
                    }

                }

                String fenzhongDoValue = initList.get(position).get("value").toString(); // 做什么的value
                // (分钟数单独存--需要重新赋值)

                String gotoLiuLan = "";
                try {
                    gotoLiuLan = SignListAdapter.doValue.split(",")[0];
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                // 通过当前的index判断点击是是否是刚才点击的任务
                boolean sameIndex = SignListAdapter.signIndex
                        .equals(SignListAdapter.indexMap.get(YConstance.SCAN_SHOP_TIME));

                // 已经开始过分钟数的任务，点击的是刚才的任务（同一个）
                if (SignListAdapter.minuteMap.size() != 0 || sameIndex) {

                }

                /**
                 * 已经开始过分钟数的任务，点击的不是同一个任务 ------
                 * -----提示还有分钟数的任务没完成，并显示剩余时间，
                 * 且有上个分钟数任务的跳转
                 */

                if (SignListAdapter.minuteMap.size() != 0 && !sameIndex) {
                    new DialogSignFenzhongTishi(mContext, R.style.DialogQuheijiao).show();
                    return;

                }

                SignListAdapter.indexMap.put(YConstance.SCAN_SHOP_TIME, SignListAdapter.signIndex);
                SignListAdapter.classMap.put(YConstance.SCAN_SHOP_TIME, SignListAdapter.doClass);
                SignListAdapter.jiangliIDmap.put(YConstance.SCAN_SHOP_TIME, SignListAdapter.jiangliIDLiulan);
                SignListAdapter.jiangliValueMap.put(YConstance.SCAN_SHOP_TIME, SignListAdapter.jiangliValueLiulan);


                try {
                    fenzhongDoValue = fenzhongDoValue.split(",")[0];
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


                SignListAdapter.fenzhongDoValueMap.put(YConstance.SCAN_SHOP_TIME, fenzhongDoValue);


                SignListAdapter.fenzhongIconID.put(YConstance.SCAN_SHOP_TIME, SignListAdapter.doIconId);

                if (gotoLiuLan.equals("collection=collocation_shop")
                        || gotoLiuLan.equals("type1=0")) {// 搭配

                    if (isComplete) {
                        intent = new Intent(mContext, ForceLookMatchActivity.class);
                        intent.putExtra("type", "1");
                        intent.putExtra("doIconId", SignListAdapter.doIconId);
                        intent.putExtra("isSignLiulan", true);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                                R.anim.slide_match);

                    } else {
                        new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                "liulandapeitishi", SignFragment.signFragment, "").show();
                    }
                } else if (gotoLiuLan.equals("collection=csss_shop")) {// 浏览专题
                    if (isComplete) {
                        intent = new Intent(mContext, ForceLookMatchActivity.class);
                        intent.putExtra("type", "2");
                        intent.putExtra("doIconId", SignListAdapter.doIconId);
                        intent.putExtra("isSignLiulan", true);
                        mContext.startActivity(intent);

                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                                R.anim.slide_match);

                    } else {

                        for (int j = 0; j < taskiconList.size(); j++) {
                            if ((taskiconList.get(j).get("id") + "")
                                    .equals(initList.get(position).get("icon") + "")) {
                                new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                        "liulanzhuantitishi", SignFragment.signFragment,
                                        taskiconList.get(j).get("app_name") + "").show();

                            }

                        }

                    }
                } else if (gotoLiuLan.equals("collection=shopping_page")) {// 购物页面
                    if (isComplete) {

                        // 跳至购物
                        Intent intent2 = new Intent((Activity) mContext,
                                MainMenuActivity.class);
                        intent2.putExtra("toShop", "toShop");
                        mContext.startActivity(intent2);


                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                                R.anim.slide_match);

                    } else {

                        for (int j = 0; j < taskiconList.size(); j++) {
                            if ((taskiconList.get(j).get("id") + "")
                                    .equals(initList.get(position).get("icon") + "")) {
                                new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                        "liulangouwuyemian",SignFragment.signFragment,
                                        taskiconList.get(j).get("app_name") + "").show();

                            }

                        }

                    }
                } else if (gotoLiuLan.equals("collection=shop_activity")) { // 活动商品

                    if (isComplete) {

                        intent = new Intent(mContext, SignActiveShopActivity.class);

                        intent.putExtra("doIconId", SignListAdapter.doIconId);
                        intent.putExtra("isSignLiulan", true);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                                R.anim.slide_match);
                    } else {

                        NewSignCommonDiaolg huodongDialog =

                                new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                        "liulanhuodongjishitishi", SignFragment.signFragment, "");
                        huodongDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
                        huodongDialog.show();

                    }
                } else {
                    if (isComplete) {

                        for (int j = 0; j < taskiconList.size(); j++) {
                            if ((taskiconList.get(j).get("id") + "")
                                    .equals(initList.get(position).get("icon") + "")) {
                                intent = new Intent(mContext,
                                        ForceLookActivity.class);


                                intent.putExtra("doIconId", SignListAdapter.doIconId);
                                intent.putExtra("isSignLiulan", true);

                                intent.putExtra("isFilterConditionActivity", true);
                                intent.putExtra("title",
                                        taskiconList.get(j).get("app_name") + "");
                                intent.putExtra("pinJievalue", leii);
                                mContext.startActivity(intent);
                                ((Activity) mContext).overridePendingTransition(
                                        R.anim.slide_left_in, R.anim.slide_match);

                            }

                        }

                    } else {

                        for (int j = 0; j < taskiconList.size(); j++) {
                            if ((taskiconList.get(j).get("id") + "")
                                    .equals(initList.get(position).get("icon") + "")) {


                                NewSignCommonDiaolg liulanDiaolg = new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                        "liulanfenzhongtishi", SignFragment.signFragment,
                                        taskiconList.get(j).get("app_name") + "");

                                liulanDiaolg.getWindow().setWindowAnimations(R.style.common_dialog_style);

                                liulanDiaolg.show();


                            }

                        }

                    }

                }

                break;
            case 6: // 购买X件商品 -------

                String name = "";

                for (int j = 0; j < taskiconList.size(); j++) {
                    if ((taskiconList.get(j).get("id") + "")
                            .equals(initList.get(position).get("icon") + "")) {
                        name = taskiconList.get(j).get("app_name") + "";
                    }

                }


                NewSignCommonDiaolg goumaiDiaog =
                        new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                "goumaishuoming", SignFragment.signFragment, SignListAdapter.doValue + "-" + name);

                goumaiDiaog.getWindow().setWindowAnimations(R.style.common_dialog_style);

                goumaiDiaog.show();


                break;
            case 7: // 分享X件普通商品
                break;
            case 8: // 分享X套搭配购
                break;
            case 9: // 免单

                intent = new Intent(mContext, BuyFreeActivity.class);

                int valuevv = 1;

                try {
                    valuevv = Integer.parseInt(SignListAdapter.doValue);
                } catch (Exception e2) {
                }

                if (valuevv == 1) { // 100以上
                    intent.putExtra("cashBack", 1);
                }

                if (valuevv == 2) { // 50以上
                    intent.putExtra("cashBack", 0);
                }

                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);

                break;

            // 701-803都是分享1件 奖励写在描述上
            // 701-703是分享普通商品
            // 801-803是分享搭配购
            case 701: // 分享普通商品 --- 余额翻倍
                break;
            case 702:// 分享普通商品 ----积分升级
                break;

            case 703:// 分享普通商品-----优惠券升级
                break;

            case 801:// 分享搭配购 ---余额翻倍
                break;

            case 802:// 分享搭配购 ----积分升级
                break;

            case 803:// 分享搭配购 ---优惠券升级
                break;

            case 999:// 疯狂星期一
//                if (lotterynumber > 0) {
//                    intent = new Intent(mContext, WithdrawalLimitActivity.class);
//                    startActivity(intent);
//                    ((FragmentActivity) mContext).overridePendingTransition(
//                            R.anim.slide_left_in, R.anim.slide_match);
//                } else {
//                    showFreeFormDialog(-1);
//                }
                break;
            case 10:// 设置喜好

                // 拿到点击的条目的奖励
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // 需要做的任务的ID----tasklist中的t_id做对比，
                    String taskID = (String) taskList.get(j).get("t_id"); // 任务类型ID

                    if (id.equals(taskID)) {
                        String jiangliValue = taskList.get(j).get("value") + "";
                        int jiangliID = Integer
                                .parseInt(taskList.get(j).get("type_id").toString());
                        // 保存在本地
                        SharedPreferencesUtil.saveStringData(mContext,
                                "jianglivaluesetlike", jiangliValue);
                        SharedPreferencesUtil.saveStringData(mContext, "jiangliidsetlike",
                                jiangliID + "");

                    }

                }

                Intent intentLike = new Intent(mContext, MyLikeActivity.class);
                intentLike.putExtra("isSignJump", true);
                mContext.startActivity(intentLike);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);
                break;

            case 11: // 去精选推荐挑美衣
                if (!YCache.getCacheUser(mContext).getHobby().contains("_")) {
                    ToastUtil.showShortText(mContext, "请先设置喜好喔~");


                    intent = new Intent(mContext, MyLikeActivity.class);
                    intent.putExtra("isJingxuanJump", true);
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);


                    return;
                }

                if (SharedPreferencesUtil.getBooleanData(mContext,
                        YConstance.Pref.JINGXUAN_SCAN_FINISH, false)) {
                    ToastUtil.showShortText(mContext, "已经完成今天的精选推荐了哦~");
                } else {

                    SignListAdapter.doClass_jx = Integer.parseInt(initList.get(position) // 任务类型（区分用的）
                            .get("task_class").toString());
                    // 拿到点击的条目的奖励---保存去精选推荐的是奖励
                    for (int j = 0; j < taskList.size(); j++) {
                        String id = initList.get(position).get("t_id").toString(); // 需要做的任务的ID----tasklist中的t_id做对比，
                        String taskID = (String) taskList.get(j).get("t_id"); // 任务类型ID

                        if (id.equals(taskID)) {
                            SignListAdapter.jiangliValueJX = taskList.get(j).get("value") + "";
                            SignListAdapter.jiangliIDJX = Integer
                                    .parseInt(taskList.get(j).get("type_id").toString());
                        }

                    }

                    // 保存index
                    SharedPreferencesUtil.saveStringData(mContext,
                            YConstance.LIULANJINGXUANTUJIANINDEX, SignListAdapter.signIndex);
                    // 是否没有调过签到的接口
                    SharedPreferencesUtil.saveBooleanData(mContext, "JXFROMSIGNGETSIGN",
                            true);

                    // 保存是从签到跳入精选推荐的标志
                    SharedPreferencesUtil.saveBooleanData(mContext, "JXFROMSIGN", true);

                    // 进入首页并打开精选推荐
                    SharedPreferencesUtil.saveBooleanData(mContext, "openJingxuan", true);
                    Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
                    intent2.putExtra("toYf", "toYf");
                    mContext.startActivity(intent2);
                }
                break;
            case 12:

                // 浏览穿搭的index
                SharedPreferencesUtil.saveStringData(mContext, YConstance.LIULANCHUANDAINDEX,
                        SignListAdapter.signIndex);

                // 浏览穿搭的单独赋值

                try {
                    SignListAdapter.doValueCD = initList.get(position).get("value").toString(); // 做什么的value
                    SignListAdapter.doNumCD = Integer.parseInt(initList.get(position) // 任务类型（去做什么）
                            .get("num").toString()); // 奖励分几次发
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


                // 拿到点击的条目的奖励
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // 需要做的任务的ID----tasklist中的t_id做对比，
                    String taskID = (String) taskList.get(j).get("t_id"); // 任务类型ID

                    if (id.equals(taskID)) {
                        SignListAdapter.jiangliValueCD = taskList.get(j).get("value") + "";
                        SignListAdapter.jiangliIDCD = Integer
                                .parseInt(taskList.get(j).get("type_id").toString());
                    }

                }

                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom",
                        "pubuliu_sign");
                mContext.startActivity(new Intent(mContext, CommonActivity.class));

                break;
            case 13:// 分享XX件品质美衣
                intent = new Intent(mContext, YaoQingFrendsActivity.class);
                intent.putExtra("jumpFrom", "shareShop");
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);
                break;
            case 14:// 分享XX条热门穿搭话题
                intent = new Intent(mContext, YaoQingFrendsActivity.class);
                intent.putExtra("jumpFrom", "shareTieZi");

                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);

                break;
            case 15:// 继续点赞

                if (SignFragment.isGratis.equals("false")) {//需要花费5衣豆继续点赞
                    JiZanCommonDialog dialog = new JiZanCommonDialog(mContext, R.style.DialogStyle1, "jixujizandianji");
                    dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
                    dialog.show();


                } else {//有免费点赞的机会，直接点赞即可
//                    dianZan(true, false);
                }

                break;

            case 16://邀请好友集赞
                TongJiUtils.yunYunTongJi("point", 907, 9, mContext);
                intent = new Intent(mContext, PointLikeRankingActivity.class);
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);

                break;

            case 17: //参团jkm

                SignListAdapter.tuanClass = 2;


                if (SignListAdapter.offered == 1) { //当前团已结束---跳到拼团详情
                    intent = new Intent(mContext, GroupsDetailsActivity.class);
                    intent.putExtra("isTuanEnd", true);
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);
                } else {//未结束-----跳到拼团商品选择，--正常参与


                    if (SignListAdapter.offered == 2) {//只有offered = 2：可参与的时候，跳进拼团商品列表，其他的都跳至拼团详情
                        intent = new Intent(mContext, SignGroupShopActivity.class);
                        mContext.startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(
                                R.anim.slide_left_in, R.anim.slide_match);
                    } else {
                        intent = new Intent(mContext, GroupsDetailsActivity.class);
                        mContext.startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(
                                R.anim.slide_left_in, R.anim.slide_match);

                    }


                }


                break;
            case 18://开团
                SignListAdapter.tuanClass = 1;

                if (SignListAdapter.orderCount > 0) {//开过团
                    if (SignListAdapter.orderStatus == 1) { //已完成
                        intent = new Intent(mContext, GroupsDetailsActivity.class);
                        mContext.startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(
                                R.anim.slide_left_in, R.anim.slide_match);
                    } else {//已开团但未完成
                        intent = new Intent(mContext, GroupsDetailsActivity.class);
                        intent.putExtra("completeStatus", 3);
                        mContext.startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(
                                R.anim.slide_left_in, R.anim.slide_match);
                    }

                } else { //未开过
                    intent = new Intent(mContext, SignGroupShopActivity.class);
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);
                }


//                                            if (isSignComplete) {
//                                                if (orderStatus == 1) { //已完成
//
//                                                    intent = new Intent(mContext, GroupsDetailsActivity.class);
//                                                    startActivity(intent);
//                                                    ((FragmentActivity) mContext).overridePendingTransition(
//                                                            R.anim.slide_left_in, R.anim.slide_match);
//
//                                                } else {//已开团但未完成
//                                                    intent = new Intent(mContext, GroupsDetailsActivity.class);
//                                                    intent.putExtra("completeStatus", 3);
//                                                    startActivity(intent);
//                                                    ((FragmentActivity) mContext).overridePendingTransition(
//                                                            R.anim.slide_left_in, R.anim.slide_match);
//                                                }
//                                            } else {
//                                                intent = new Intent(mContext, SignGroupShopActivity.class);
//                                                startActivity(intent);
//                                                ((FragmentActivity) mContext).overridePendingTransition(
//                                                        R.anim.slide_left_in, R.anim.slide_match);
//                                            }


                break;

            case 22://拼团夺宝
                intent = new Intent(mContext, IndianaListActivity.class);
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in,
                        R.anim.slide_match);
                break;

            case 23://千元红包雨
//                DialogUtils.redPacketDownDialog(mContext);
                break;


            case 24://购买X件商品//每月惊喜任务--------------//


                String namena = "";

                for (int j = 0; j < taskiconList.size(); j++) {
                    if ((taskiconList.get(j).get("id") + "")
                            .equals(initList.get(position).get("icon") + "")) {
                        namena = taskiconList.get(j).get("app_name") + "";
                    }

                }

                NewSignCommonDiaolg jingxiDialog =
                        new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                "jingxirenwushuoming", SignFragment.signFragment,SignListAdapter. doValue + "-" + namena);

                jingxiDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);

                jingxiDialog.show();
                break;


            case 25://  新分享赢提现

                ToastUtil.showDialog(new NewShareGetTXDialog((Activity) mContext, mContext, R.style.DialogStyle1,
                        SignListAdapter.jiangliID));

                break;
            case 26://  去抽奖
                PublicUtil.getBalanceNum(mContext, null, true);
                break;

            case 27: //余额抽提现


                intent = new Intent(mContext, WithdrawalLimitActivity.class);
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                break;
            case 28: //超级0元购
                ToastUtil.showDialog(new LingYUANGOUTishiRedDialog(mContext, R.style.DialogStyle1, SignListAdapter.doValue.split(",")[0]));
                break;

            default:
                break;


        }


    }





    public interface ShareCompleteCallBack {

        void clickNext();

    }



}