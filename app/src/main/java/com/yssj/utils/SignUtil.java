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


        SignListAdapter.doClass = Integer.parseInt(initList.get(position) // ??????????????????????????????
                .get("task_class").toString());

        int tast = SignListAdapter.doClass;


        if (tast == 3) {// ??????????????????????????????
            return;
        }

        if (initList.size() == 0 || position > initList.size() - 1) {
            return;
        }


        try {
            SignListAdapter.doType = Integer.parseInt(initList.get(position) // ??????????????????????????????
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
        // ??????????????????????????????????????????0?????????????????????????????????????????????
        // new
        // MemberPowerNoEnoughDialog(mContext,
        // R.style.DialogQuheijiao2,
        // "power=0", 0 + "", "").show();
        //
        // return;
        // }
        //
        // }

        // ????????????


        String status = "-1";
        status = initList.get(position).get("status") + "";
        boolean s_complete = status.equals("0");
        boolean isComplete = (initList.get(position).get("signStatus") + "").equals("1") && s_complete; // ??????????????????????????????

        SignListAdapter.doValue = initList.get(position).get("value") + ""; // ????????????value
        SignListAdapter.doNeedCount = initList.get(position).get("status") + ""; // ????????????value




        SignListAdapter.doIconId = initList.get(position).get("icon") + "";


        try {
            SignListAdapter.doNum = Integer.parseInt(initList.get(position) // ??????????????????????????????
                    .get("num").toString()); // ??????????????????
        } catch (Exception eee) {
            eee.printStackTrace();
        }

        if (null == initList.get(position).get("num")
                || initList.get(position).get("num").equals("")) {
            if (!isComplete) {
                ToastUtil.showShortText(mContext, "??????????????????,???????????????");
                return;
            }

        }

        SignListAdapter.signIndex = initList.get(position).get("index") + "";

        // ??????????????????????????????
        for (int j = 0; j < taskList.size(); j++) {

            String id = initList.get(position).get("t_id").toString(); // ?????????????????????ID----tasklist??????t_id????????????
            String taskID = (String) taskList.get(j).get("t_id"); // ????????????ID

            if (id.equals(taskID)) {
                SignListAdapter.jiangliValue = taskList.get(j).get("value") + "";
                SignListAdapter.jiangliID = Integer.parseInt(taskList.get(j).get("type_id").toString());
            }

        }
        // ??????
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

            case 0: // ?????? --???????????????

                Intent intentKaidian = new Intent(mContext, MineLikeActivity.class);
                intentKaidian.putExtra("isSign", true);
                ((MainMenuActivity) mContext).startActivityForResult(intentKaidian, 13334);

                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);

                break;
            case 1:// ????????????

                double getValue = 0;

                for (int i = 0; i < taskList.size(); i++) { // ??????????????????

                    // ????????????

                    String id = initList.get(position).get("t_id").toString();
                    String taskID = (String) taskList.get(i).get("t_id"); // ????????????ID

                    if (id.equals(taskID)) { // ???????????????????????????????????????????????????
                        // ???????????????
                        getValue = Double.parseDouble(taskList.get(i) // ??????????????????????????????
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
            case 2: // ??????

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
            case 3: // ???X?????????????????????
                // indexMap.put(YConstance.ADD_TO_SHOPCART,
                // signIndex);
                SharedPreferencesUtil.saveStringData(mContext, YConstance.ADD_TO_SHOPCART,
                        SignListAdapter.signIndex);

                SignListAdapter.classMap.put(YConstance.ADD_TO_SHOPCART, SignListAdapter.doClass);

                // ???????????????????????????

                SignListAdapter.doValueShopCart = initList.get(position).get("value").toString(); // ????????????value

                try {
                    SignListAdapter.doNumShopCart = Integer.parseInt(initList.get(position) // ??????????????????????????????
                            .get("num").toString()); // ??????????????????
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                // ??????????????????????????????
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // ?????????????????????ID----tasklist??????t_id????????????
                    String taskID = (String) taskList.get(j).get("t_id"); // ????????????ID

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
            case 4: // ??????X??????????????? //????????????-----??????

                String lei = "";

                lei = null; // ??????
                try {
                    lei = SignListAdapter.doValue.split(",")[0];
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                if (lei.equals("collection=collocation_shop")) {// ??????
                    intent = new Intent(mContext, ForceLookMatchActivity.class);
                    intent.putExtra("type", "1");
                    intent.putExtra("doIconId", SignListAdapter.doIconId);
                    intent.putExtra("isSignLiulan", true);
                    mContext.startActivity(intent);
                    SharedPreferencesUtil.saveStringData(mContext, YConstance.Pref.SINGVALUE, SignListAdapter.doValue);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);
                } else if (lei.equals("collection=shop_activity")) { // ????????????

                    intent = new Intent(mContext, SignActiveShopActivity.class);

                    intent.putExtra("doIconId", SignListAdapter.doIconId);
                    intent.putExtra("isSignLiulan", true);

                    mContext.startActivity(intent);


                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);


                } else if (lei.equals("collection=browse_shop")) { // ????????????
                    // ----??????????????????????????????
                    intent = new Intent(mContext, ForceLookActivity.class);

                    intent.putExtra("doIconId", SignListAdapter.doIconId);
                    intent.putExtra("isSignLiulan", true);
                    intent.putExtra("pinJievalue", lei);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);
                } else {
                    // ?????????????????????????????????
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
                    leiTX = SignListAdapter.doValue.split(",")[0]; // ??????
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


//                                            intent = new Intent(context, ForceLookActivity.class);
//                                            intent.putExtra("isFilterConditionActivity", true);
//                                            intent.putExtra("title", "??????");
//                                            intent.putExtra("pinJievalue", "collection=collocation_shop");
//
//                                            intent.putExtra("doIconId", doIconId);
//                                            intent.putExtra("isSignLiulan", true);
//
//
//                                            context.startActivity(intent);
//                                            ((Activity) context).overridePendingTransition(
//                                                    R.anim.slide_left_in, R.anim.slide_match);


                if (leiTX.equals("collection=collocation_shop")) {// ??????
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
                    intent.putExtra("title", "??????");
                    intent.putExtra("pinJievalue", leiTX);

                    intent.putExtra("doIconId", SignListAdapter.doIconId);
                    intent.putExtra("isSignLiulan", true);


                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);


                } else if (leiTX.equals("collection=shop_activity")) { // ????????????
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
                    intent.putExtra("title", "??????");
                    intent.putExtra("pinJievalue", leiTX);

                    intent.putExtra("doIconId", SignListAdapter.doIconId);
                    intent.putExtra("isSignLiulan", true);


                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);


                } else if (leiTX.equals("collection=browse_shop")) { // ????????????
                    // ----??????????????????????????????
                    intent = new Intent(mContext, ForceLookActivity.class);

                    intent.putExtra("doIconId", SignListAdapter.doIconId);
                    intent.putExtra("isSignLiulan", true);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);
                } else {
                    // ?????????????????????????????????
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
                    intent.putExtra("title", "??????");
                    intent.putExtra("pinJievalue", leiTX);

                    intent.putExtra("doIconId", SignListAdapter.doIconId);
                    intent.putExtra("isSignLiulan", true);


                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);

                }

                break;


            case 20://?????????????????????

                TongJiUtils.yunYunTongJi("fxytx", 1101, 11, mContext);

                ToastUtil.showDialog(new ShareGetTXdialog(mContext, R.style.invate_dialog_style));

//                                                getIndianaRuleContent();


                //                                                ToastUtil.showDialog(new ShareGetTXdialog(mContext, R.style.invate_dialog_style));

                break;


            case 21://????????????????????????????????????


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

            case 5:// ????????????????????????????????? X??????
                // ?????????------?????????
                String leii = "";


                try {
                    SignListAdapter.doNumShopLiulan = Integer.parseInt(initList.get(position) // ??????????????????????????????
                            .get("num").toString()); // ??????????????????

                    leii = SignListAdapter.doValue.split(",")[0]; // ??????

                    SignListAdapter.doValueLiulan = initList.get(position).get("value").toString(); // ????????????value
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                // ??????????????????????????????
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // ?????????????????????ID----tasklist??????t_id????????????
                    String taskID = (String) taskList.get(j).get("t_id"); // ????????????ID

                    if (id.equals(taskID)) {
                        SignListAdapter.jiangliValueLiulan = taskList.get(j).get("value") + "";
                        SignListAdapter.jiangliIDLiulan = Integer
                                .parseInt(taskList.get(j).get("type_id").toString());
                    }

                }

                String fenzhongDoValue = initList.get(position).get("value").toString(); // ????????????value
                // (??????????????????--??????????????????)

                String gotoLiuLan = "";
                try {
                    gotoLiuLan = SignListAdapter.doValue.split(",")[0];
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                // ???????????????index?????????????????????????????????????????????
                boolean sameIndex = SignListAdapter.signIndex
                        .equals(SignListAdapter.indexMap.get(YConstance.SCAN_SHOP_TIME));

                // ??????????????????????????????????????????????????????????????????????????????
                if (SignListAdapter.minuteMap.size() != 0 || sameIndex) {

                }

                /**
                 * ?????????????????????????????????????????????????????????????????? ------
                 * -----??????????????????????????????????????????????????????????????????
                 * ????????????????????????????????????
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
                        || gotoLiuLan.equals("type1=0")) {// ??????

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
                } else if (gotoLiuLan.equals("collection=csss_shop")) {// ????????????
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
                } else if (gotoLiuLan.equals("collection=shopping_page")) {// ????????????
                    if (isComplete) {

                        // ????????????
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
                } else if (gotoLiuLan.equals("collection=shop_activity")) { // ????????????

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
            case 6: // ??????X????????? -------

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
            case 7: // ??????X???????????????
                break;
            case 8: // ??????X????????????
                break;
            case 9: // ??????

                intent = new Intent(mContext, BuyFreeActivity.class);

                int valuevv = 1;

                try {
                    valuevv = Integer.parseInt(SignListAdapter.doValue);
                } catch (Exception e2) {
                }

                if (valuevv == 1) { // 100??????
                    intent.putExtra("cashBack", 1);
                }

                if (valuevv == 2) { // 50??????
                    intent.putExtra("cashBack", 0);
                }

                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);

                break;

            // 701-803????????????1??? ?????????????????????
            // 701-703?????????????????????
            // 801-803??????????????????
            case 701: // ?????????????????? --- ????????????
                break;
            case 702:// ?????????????????? ----????????????
                break;

            case 703:// ??????????????????-----???????????????
                break;

            case 801:// ??????????????? ---????????????
                break;

            case 802:// ??????????????? ----????????????
                break;

            case 803:// ??????????????? ---???????????????
                break;

            case 999:// ???????????????
//                if (lotterynumber > 0) {
//                    intent = new Intent(mContext, WithdrawalLimitActivity.class);
//                    startActivity(intent);
//                    ((FragmentActivity) mContext).overridePendingTransition(
//                            R.anim.slide_left_in, R.anim.slide_match);
//                } else {
//                    showFreeFormDialog(-1);
//                }
                break;
            case 10:// ????????????

                // ??????????????????????????????
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // ?????????????????????ID----tasklist??????t_id????????????
                    String taskID = (String) taskList.get(j).get("t_id"); // ????????????ID

                    if (id.equals(taskID)) {
                        String jiangliValue = taskList.get(j).get("value") + "";
                        int jiangliID = Integer
                                .parseInt(taskList.get(j).get("type_id").toString());
                        // ???????????????
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

            case 11: // ????????????????????????
                if (!YCache.getCacheUser(mContext).getHobby().contains("_")) {
                    ToastUtil.showShortText(mContext, "?????????????????????~");


                    intent = new Intent(mContext, MyLikeActivity.class);
                    intent.putExtra("isJingxuanJump", true);
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);


                    return;
                }

                if (SharedPreferencesUtil.getBooleanData(mContext,
                        YConstance.Pref.JINGXUAN_SCAN_FINISH, false)) {
                    ToastUtil.showShortText(mContext, "???????????????????????????????????????~");
                } else {

                    SignListAdapter.doClass_jx = Integer.parseInt(initList.get(position) // ??????????????????????????????
                            .get("task_class").toString());
                    // ??????????????????????????????---?????????????????????????????????
                    for (int j = 0; j < taskList.size(); j++) {
                        String id = initList.get(position).get("t_id").toString(); // ?????????????????????ID----tasklist??????t_id????????????
                        String taskID = (String) taskList.get(j).get("t_id"); // ????????????ID

                        if (id.equals(taskID)) {
                            SignListAdapter.jiangliValueJX = taskList.get(j).get("value") + "";
                            SignListAdapter.jiangliIDJX = Integer
                                    .parseInt(taskList.get(j).get("type_id").toString());
                        }

                    }

                    // ??????index
                    SharedPreferencesUtil.saveStringData(mContext,
                            YConstance.LIULANJINGXUANTUJIANINDEX, SignListAdapter.signIndex);
                    // ?????????????????????????????????
                    SharedPreferencesUtil.saveBooleanData(mContext, "JXFROMSIGNGETSIGN",
                            true);

                    // ?????????????????????????????????????????????
                    SharedPreferencesUtil.saveBooleanData(mContext, "JXFROMSIGN", true);

                    // ?????????????????????????????????
                    SharedPreferencesUtil.saveBooleanData(mContext, "openJingxuan", true);
                    Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
                    intent2.putExtra("toYf", "toYf");
                    mContext.startActivity(intent2);
                }
                break;
            case 12:

                // ???????????????index
                SharedPreferencesUtil.saveStringData(mContext, YConstance.LIULANCHUANDAINDEX,
                        SignListAdapter.signIndex);

                // ???????????????????????????

                try {
                    SignListAdapter.doValueCD = initList.get(position).get("value").toString(); // ????????????value
                    SignListAdapter.doNumCD = Integer.parseInt(initList.get(position) // ??????????????????????????????
                            .get("num").toString()); // ??????????????????
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


                // ??????????????????????????????
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // ?????????????????????ID----tasklist??????t_id????????????
                    String taskID = (String) taskList.get(j).get("t_id"); // ????????????ID

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
            case 13:// ??????XX???????????????
                intent = new Intent(mContext, YaoQingFrendsActivity.class);
                intent.putExtra("jumpFrom", "shareShop");
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);
                break;
            case 14:// ??????XX?????????????????????
                intent = new Intent(mContext, YaoQingFrendsActivity.class);
                intent.putExtra("jumpFrom", "shareTieZi");

                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);

                break;
            case 15:// ????????????

                if (SignFragment.isGratis.equals("false")) {//????????????5??????????????????
                    JiZanCommonDialog dialog = new JiZanCommonDialog(mContext, R.style.DialogStyle1, "jixujizandianji");
                    dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
                    dialog.show();


                } else {//?????????????????????????????????????????????
//                    dianZan(true, false);
                }

                break;

            case 16://??????????????????
                TongJiUtils.yunYunTongJi("point", 907, 9, mContext);
                intent = new Intent(mContext, PointLikeRankingActivity.class);
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);

                break;

            case 17: //??????jkm

                SignListAdapter.tuanClass = 2;


                if (SignListAdapter.offered == 1) { //??????????????????---??????????????????
                    intent = new Intent(mContext, GroupsDetailsActivity.class);
                    intent.putExtra("isTuanEnd", true);
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);
                } else {//?????????-----???????????????????????????--????????????


                    if (SignListAdapter.offered == 2) {//??????offered = 2?????????????????????????????????????????????????????????????????????????????????
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
            case 18://??????
                SignListAdapter.tuanClass = 1;

                if (SignListAdapter.orderCount > 0) {//?????????
                    if (SignListAdapter.orderStatus == 1) { //?????????
                        intent = new Intent(mContext, GroupsDetailsActivity.class);
                        mContext.startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(
                                R.anim.slide_left_in, R.anim.slide_match);
                    } else {//?????????????????????
                        intent = new Intent(mContext, GroupsDetailsActivity.class);
                        intent.putExtra("completeStatus", 3);
                        mContext.startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(
                                R.anim.slide_left_in, R.anim.slide_match);
                    }

                } else { //?????????
                    intent = new Intent(mContext, SignGroupShopActivity.class);
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);
                }


//                                            if (isSignComplete) {
//                                                if (orderStatus == 1) { //?????????
//
//                                                    intent = new Intent(mContext, GroupsDetailsActivity.class);
//                                                    startActivity(intent);
//                                                    ((FragmentActivity) mContext).overridePendingTransition(
//                                                            R.anim.slide_left_in, R.anim.slide_match);
//
//                                                } else {//?????????????????????
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

            case 22://????????????
                intent = new Intent(mContext, IndianaListActivity.class);
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in,
                        R.anim.slide_match);
                break;

            case 23://???????????????
//                DialogUtils.redPacketDownDialog(mContext);
                break;


            case 24://??????X?????????//??????????????????--------------//


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


            case 25://  ??????????????????

                ToastUtil.showDialog(new NewShareGetTXDialog((Activity) mContext, mContext, R.style.DialogStyle1,
                        SignListAdapter.jiangliID));

                break;
            case 26://  ?????????
                PublicUtil.getBalanceNum(mContext, null, true);
                break;

            case 27: //???????????????


                intent = new Intent(mContext, WithdrawalLimitActivity.class);
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                break;
            case 28: //??????0??????
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