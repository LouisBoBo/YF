package com.yssj.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.Constants;
import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.activity.wxapi.WXEntryActivity;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.entity.BaseData;
import com.yssj.entity.Choujiang20Data;
import com.yssj.entity.HotShop;
import com.yssj.entity.JangLiJinData;
import com.yssj.entity.Order;
import com.yssj.entity.UserOrderHomePageData;
import com.yssj.entity.VipPriceData;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelL;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.BootPayDetails;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.OneBuyChouJiangActivity;
import com.yssj.ui.activity.ShopPageActivity;
import com.yssj.ui.activity.SignDrawalLimitActivity;
import com.yssj.ui.activity.infos.FundDetailsActivity;
import com.yssj.ui.activity.infos.MyWalletActivity;
import com.yssj.ui.activity.infos.MyWalletCommonFragmentActivity;
import com.yssj.ui.activity.infos.NewWalletActivity;
import com.yssj.ui.activity.infos.YJdetailActivity;
import com.yssj.ui.activity.main.ForceLookActivity;
import com.yssj.ui.activity.main.ForceLookMatchActivity;
import com.yssj.ui.activity.main.NewPThotsaleActivity;
import com.yssj.ui.activity.main.SignActiveShopActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsGroupIndianaActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsIndianaActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsMoneyIndianaActivity;
import com.yssj.ui.activity.vip.MyVipListActivity;
import com.yssj.ui.activity.vip.VipListBean;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.PublicToastDialog;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.ui.fragment.orderinfo.OrderInfoFragment;
import com.yssj.ui.fragment.orderinfo.OrderListAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.yssj.ui.activity.OneBuyGroupsDetailsActivity.GO_TO_BUY_MPK;


/**
 *
 */

public class DialogUtils {

    public static boolean homePageDefDialogShowEd = false;//?????????????????????????????????
    public static boolean homePageNotNewUserDialog = false;//??????????????????????????????????????????

    public static Dialog homeDialog = null; //?????????????????????dialog

    //??????????????????
    public static void initShouYeDialog(final Context mContext) {


        if (null != homeDialog && homeDialog.isShowing()) {//?????????????????????????????????
            return;
        }

        /**
         ????????????
         ?????????
         ????????????????????????????????????
         ????????????????????????????????????
         ?????????
         ?????????
         */


        //?????????
        if (YJApplication.instance.isLoginSucess()) {

            if(YCache.getCacheUser(mContext).getReviewers() ==1 ){
                return;
            }


            YConn.httpPost(mContext, YUrl.QUERY_PT_INFO, new HashMap<String, String>(), new HttpListener<BaseData>() {
                @Override
                public void onSuccess(BaseData result) {
                    if (result.getIsFail() == 1) { //????????????
                        showPTFailDialog(mContext);
                        return;
                    }

                    //??????????????????
                    if (!SharedPreferencesUtil.getBooleanData(mContext, YCache.USER_FISRT_LOGIN, false)) {

                        if (homePageNotNewUserDialog) {
                            return;
                        }

                        YConn.httpPost(mContext, YUrl.QUERY_HAS_JYJL, new HashMap<String, String>(), new HttpListener<UserOrderHomePageData>() {
                            @Override
                            public void onSuccess(UserOrderHomePageData result) {

                                if (result.getNRaffle_Money() > 0) {
                                    showNotNewUserHasRmoney(mContext, result.getNRaffle_Money() + "");
                                } else {
                                    showNotNewUserNoRmoney(mContext);
                                }

                                homePageNotNewUserDialog = true;
                            }

                            @Override
                            public void onError() {

                            }
                        });


                        return;
                    }

                    //?????????
                    showHomePageDefDialog(mContext);


                }

                @Override
                public void onError() {

                }
            });


        }


        //?????????
        //????????????????????????????????????
        //????????????????????????????????????
        //?????????
        //?????????


    }


    public static void showNotNewUserHasRmoney(final Context mContext, String tv_r_money) {
        final Timer mpkTimer = new Timer();
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        homeDialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_not_new_user_has_r_money, null);
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeDialog.dismiss();
                mpkTimer.cancel();
            }
        });

        TextView mTv_r_money = view.findViewById(R.id.tv_r_money);

        ImageView iv_hongbao_bg = view.findViewById(R.id.iv_hongbao_bg);
        iv_hongbao_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (null != CommonActivity.instance) {
                    CommonActivity.instance.finish();
                }
                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
                mContext.startActivity(new Intent(mContext, CommonActivity.class));
                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                mpkTimer.cancel();
                homeDialog.dismiss();

            }
        });

        mTv_r_money.setText(tv_r_money);
        final TextView tv_txk_countdown = view.findViewById(R.id.tv_txk_countdown);
        long txkRecLen = 24 * 60 * 60 * 1000;
        final long[] reTime = {txkRecLen};
        tv_txk_countdown.setText(DateUtil.FormatMilliseondToEndTime2(reTime[0]) + "?????????");
        mpkTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (reTime[0] <= 0) {
                                    mpkTimer.cancel();
                                    homeDialog.dismiss();
                                } else {
                                    reTime[0] -= 1000;
                                    tv_txk_countdown.setText(DateUtil.FormatMilliseondToEndTime2(reTime[0]) + "?????????");
                                }
                            }
                        });
                    }
                },

                0, 1000
        );

        homeDialog.setCanceledOnTouchOutside(true);
        homeDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ToastUtil.showDialog(homeDialog);

    }


//    public static void showPTfailDialog(final Context mContext) {
//        final Timer mpkTimer = new Timer();
//        LayoutInflater mInflater = LayoutInflater.from(mContext);
//        homeDialog = new Dialog(mContext, R.style.invate_dialog_style);
//        View view = mInflater.inflate(R.layout.dialog_pt_mpk, null);
//        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mpkTimer.cancel();
//                homeDialog.dismiss();
//            }
//        });
//
//        ImageView iv_hongbao_bg = view.findViewById(R.id.iv_hongbao_bg);
//        iv_hongbao_bg.setImageResource(R.drawable.free_ling_fight_fail_mpk);
//        iv_hongbao_bg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                HashMap<String, String> map = new HashMap<>();
//                YConn.httpPost(mContext, YUrl.QUERY_TIQIAN_TXCJ, map, new HttpListener<Choujiang20Data>() {
//                    @Override
//                    public void onSuccess(Choujiang20Data result) {
//                        if ("1".equals(result.getStatus())) {
//                            if (result.getData().getIs_finish() == 1) {
//                                if (null != SignDrawalLimitActivity.instance) {
//                                    SignDrawalLimitActivity.instance.finish();
//                                }
//
//                                Intent txcjIntent = new Intent(mContext, SignDrawalLimitActivity.class);
//                                txcjIntent.putExtra("type", 1)
//                                        .putExtra("fromPT", true);
//
//                                mContext.startActivity(txcjIntent);
//                                ((FragmentActivity) mContext).overridePendingTransition(
//                                        R.anim.slide_left_in, R.anim.slide_match);
//                            } else {
//                                mContext.startActivity(new Intent(mContext, MyVipListActivity.class)
//                                        .putExtra("isGuideMPK", true));
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });
//
//
////
////                mContext.startActivity(new Intent(mContext, MyVipListActivity.class)
////                        .putExtra("isGuideMPK", true));
////                mpkTimer.cancel();
////                homeDialog.dismiss();
//
//            }
//        });
//
//
//        final TextView tv_txk_countdown = view.findViewById(R.id.tv_txk_countdown);
//        long txkRecLen = 30 * 60 * 1000;
//        final long[] reTime = {txkRecLen};
//        tv_txk_countdown.setText(DateUtil.FormatMilliseondToEndTime2(reTime[0]) + "?????????");
//        mpkTimer.schedule(
//                new TimerTask() {
//                    @Override
//                    public void run() {
//                        ((Activity) mContext).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (reTime[0] <= 0) {
//                                    mpkTimer.cancel();
//                                    homeDialog.dismiss();
//                                } else {
//                                    reTime[0] -= 1000;
//                                    tv_txk_countdown.setText(DateUtil.FormatMilliseondToEndTime2(reTime[0]) + "?????????");
//                                }
//                            }
//                        });
//                    }
//                },
//
//                0, 1000
//        );
//
//        homeDialog.setCanceledOnTouchOutside(false);
//        homeDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT));
//        ToastUtil.showDialog(homeDialog);
//
//    }


    public static void showNotNewUserNoRmoney(final Context mContext) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        homeDialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_not_new_user_no_has_r_money, null);
        ImageView ivLing = view.findViewById(R.id.iv_ling);
        ImageView iv_hongbao_bg = view.findViewById(R.id.iv_hongbao_bg);
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeDialog.dismiss();
            }
        });
        setHomeDialogLingAnim(ivLing);
        ivLing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
                mContext.startActivity(new Intent(mContext, CommonActivity.class));
                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                homeDialog.dismiss();


            }
        });

        homeDialog.setCanceledOnTouchOutside(false);
        homeDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ToastUtil.showDialog(homeDialog);

    }

    public static void showHomePageDefDialog(final Context mContext) { //??????90?????????????????????
//        if (homePageDefDialogShowEd) {
//            return;
//        }
//        LayoutInflater mInflater = LayoutInflater.from(mContext);
//        homeDialog = new Dialog(mContext, R.style.invate_dialog_style);
//        View view = mInflater.inflate(R.layout.dialog_new_red_hongbao, null);
//        ImageView ivLing = view.findViewById(R.id.iv_ling);
//        ImageView iv_hongbao_bg = view.findViewById(R.id.iv_hongbao_bg);
//        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                homeDialog.dismiss();
//            }
//        });
//        setHomeDialogLingAnim(ivLing);
//        ivLing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
//                mContext.startActivity(new Intent(mContext, CommonActivity.class));
//                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//                homeDialog.dismiss();
//
//
//            }
//        });
//
//        homeDialog.setCanceledOnTouchOutside(false);
//        homeDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT));
//        ToastUtil.showDialog(homeDialog);
//        homePageDefDialogShowEd = true;

    }


    public static void setHomeDialogLingAnim(ImageView ivLing) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(ivLing, "scaleX", 1, 1.2f, 1);
        animatorX.setRepeatCount(-1);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(ivLing, "scaleY", 1, 1.2f, 1);
        animatorY.setRepeatCount(-1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.setDuration(1100);
        animatorSet.start();
    }


    /**
     * ?????????????????????????????????
     */
    public static void showOnePicDialog(final Context context, int from) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_show_notixianedu, null);
        ImageView iv_tosign = view.findViewById(R.id.iv_tosign);
        switch (from) {
            case 1:
                iv_tosign.setImageDrawable(context.getResources().getDrawable(R.drawable.tixian_no_tixian));

                break;
        }

        view.findViewById(R.id.iv_tosign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ????????????
                SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
                context.startActivity(new Intent(context, CommonActivity.class));
                ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                deleteDialog.dismiss();

            }
        });

        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(deleteDialog);


    }

    //?????????????????????????????????--????????????
    public static void freelLingNeedVip(final Context context) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_freeling_error, null);
        ImageView iv_tosign = view.findViewById(R.id.iv_tosign);
//        iv_tosign.setImageDrawable(context.getResources().getDrawable(R.drawable.become_member_gobuy));

        view.findViewById(R.id.iv_tosign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //????????????
                BasicActivity.goToGuideVipOrToMyVipList(context, 0);
                deleteDialog.dismiss();


            }
        });
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
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


    /**
     * ?????????????????????vip_free=0
     */
    public static void freelLingError(final Context context) {

        //????????????????????????
        HashMap<String, String> pairsMap = new HashMap<>();
        YConn.httpPost(context, YUrl.QUERY_DAKA, pairsMap
                , new HttpListener<BaseData>() {
                    @Override
                    public void onSuccess(BaseData baseData) {
                        if (baseData.getData() == 1) {//????????????????????????
                            freelLingNeedVip(context);
                        } else {//?????????????????????
                            LayoutInflater mInflater = LayoutInflater.from(context);
                            final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
                            View view = mInflater.inflate(R.layout.dialog_freeling_error, null);


                            view.findViewById(R.id.iv_tosign).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    // ????????????
                                    SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
                                    context.startActivity(new Intent(context, CommonActivity.class));
                                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                                    deleteDialog.dismiss();

                                }
                            });
                            view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
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
                    }

                    @Override
                    public void onError() {

                    }
                });


    }

    public static void showYQMtishiDialog(Context context) {


        LayoutInflater mInflater = LayoutInflater.from(context);
        final Dialog dialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_common_one_button, null);


        final TextView btn_ok = view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btn_ok.performClick();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(dialog);

    }

    public static void showSignDialogFromMianOrFa(Context context) {


        LayoutInflater mInflater = LayoutInflater.from(context);
        final Dialog dialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_common_one_button, null);


        TextView tag_name = view.findViewById(R.id.tag_name);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView btn_ok = view.findViewById(R.id.btn_ok);
        btn_ok.setText("????????????");
        tv_title.setText("????????????");
        tag_name.setText(Html.fromHtml("???????????????????????????????????????????????????<font color='#ff0000'><b><big>????????????????????????????????????</big></b></font>???????????????????????????"));


        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(dialog);

    }


    /**
     * ??????????????????NEW
     */
    public static void getDiKouDialogNew(final Context context, final String title, final boolean isZhuanpan, final boolean isShopDetail) {

        if (!YJApplication.instance.isLoginSucess()) {

            LayoutInflater mInflater = LayoutInflater.from(context);
            final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
            View view = mInflater.inflate(R.layout.dialog_dikou_explian_new, null);
            ImageView iv_close = view.findViewById(R.id.iv_close);
            TextView tv_fanhuan = view.findViewById(R.id.tv_fanhuan);
            TextView tag_name = view.findViewById(R.id.tag_name);
            TextView tv_title = view.findViewById(R.id.tv_title);
            tv_title.setText(title);

            String onPrice = GuideActivity.oneShopPrice;
//            if(isShopDetail){
//                tag_name.setText("????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
//
//            }else{
//                tag_name.setText("??????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
//
//            }

            if (isShopDetail) {
                tag_name.setText("?????????????????????????????????????????????????????????????????????????????????????????????");

            } else {
//                if (isZhuanpan) {
                tag_name.setText("??????????????????????????????????????????????????????????????????????????????????????????????????????????????????");

//                } else {
//                    tag_name.setText("???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");

//                }

            }


            tv_fanhuan.setText("????????????0.0???");
            iv_close.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    deleteDialog.dismiss();

                }
            });
            Button btn_cancel = view.findViewById(R.id.btn_cancel);
            btn_cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    deleteDialog.dismiss();

                }
            });
            deleteDialog.setCanceledOnTouchOutside(false);
            deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            ToastUtil.showDialog(deleteDialog);


            return;
        }


        new SAsyncTask<Void, Void, String>((FragmentActivity) context, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected String doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel2.getALLDikou(context);
            }

            @Override
            protected void onPostExecute(final FragmentActivity context, String result, Exception e) {
                if (null == e) {
                    LayoutInflater mInflater = LayoutInflater.from(context);
                    final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
                    View view = mInflater.inflate(R.layout.dialog_dikou_explian_new, null);
                    ImageView iv_close = view.findViewById(R.id.iv_close);
                    TextView tv_fanhuan = view.findViewById(R.id.tv_fanhuan);
                    TextView tag_name = view.findViewById(R.id.tag_name);
                    TextView tv_title = view.findViewById(R.id.tv_title);
                    tv_title.setText(title);

                    String onPrice = GuideActivity.oneShopPrice;
//                    tag_name.setText("????????????" + onPrice + "???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????0????????????");

//                    if(isShopDetail){
//                        tag_name.setText("????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
//
//                    }else{
//                        tag_name.setText("??????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
//
//                    }

                    if (isShopDetail) {
                        tag_name.setText("?????????????????????????????????????????????????????????????????????????????????????????????");

                    } else {
//                        if (isZhuanpan) {
                        tag_name.setText("??????????????????????????????????????????????????????????????????????????????????????????????????????????????????");

//                        } else {
//                            tag_name.setText("???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
//
//                        }

                    }

                    tv_fanhuan.setText("????????????" + result + "???");
                    iv_close.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            deleteDialog.dismiss();
                            if (isZhuanpan) {
                                context.finish();
                            }

                        }
                    });
                    Button btn_cancel = view.findViewById(R.id.btn_cancel);
                    btn_cancel.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            deleteDialog.dismiss();
                            if (isZhuanpan) {
                                context.finish();
                            }
                        }
                    });
                    Button btn_ok = view.findViewById(R.id.btn_ok);
                    btn_ok.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            deleteDialog.dismiss();

                            Intent intent = new Intent(context, MyWalletActivity.class);
                            context.startActivity(intent);
                            context.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                        }
                    });
                    deleteDialog.setCanceledOnTouchOutside(false);
                    deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT));

                    ToastUtil.showDialog(deleteDialog);
                }

                super.onPostExecute(context, result, e);
            }

        }.execute();


    }


    /**
     * ??????????????????NEW
     */
    public static void getDiKouDialogNewOrder(final Context mContext) {

        if (YCache.getCacheUser(mContext).getReviewers() == 1) {
            return;
        }

        final PublicToastDialog shareWaitDialog = new PublicToastDialog(mContext, R.style.DialogStyle1, "");
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        final Dialog deleteDialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_dikou_explian_order, null);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("????????????????????????");
        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.getVip(mContext, new CommonUtils.GetVipListener() {
                    @Override
                    public void callBack(boolean isVip, int maxType) {
                        if (isVip && (maxType == 5 || maxType == 6)) {
                            try {
                                shareWaitDialog.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //????????????????????????
                            HashMap<String, String> map = new HashMap<>();
                            map.put("getShop", "true");
                            YConn.httpPost(mContext, YUrl.GET_SHARE_SHOP_LINK_HOBBY, map, new HttpListener<HotShop>() {
                                @Override
                                public void onSuccess(HotShop shop) {
                                    String sharePath = "/pages/mine/toexamine_test/toexamine_test?shouYePage=ThreePage" + "&isShareFlag=true" + "&user_id=" + YCache.getCacheUser(mContext).getUser_id();
                                    String shareMIniAPPimgPic = YUrl.imgurl + shop.getShop().getShop_code().substring(1, 4) + "/" + shop.getShop().getShop_code() + "/" + shop.getShop().getFour_pic().split(",")[2] + "!280";
                                    WXminiAPPShareUtil.shareShopToWXminiAPP(mContext,
                                            shop.getShop().getShop_name(),
                                            shop.getShop().getAssmble_price() + "",

                                            shareMIniAPPimgPic, sharePath, false);

                                    WXEntryActivity.setWXminiShareListener(new WXEntryActivity.WXminiAPPshareListener() {
                                        @Override
                                        public void wxMiniShareSuccess() {
                                            ToastUtil.showShortText(mContext, "????????????");

                                        }
                                    });

                                    if (null != shareWaitDialog) {
                                        shareWaitDialog.dismiss();
                                    }
                                }

                                @Override
                                public void onError() {
                                    if (null != shareWaitDialog) {
                                        shareWaitDialog.dismiss();
                                    }
                                }
                            });


                        } else {
                            mContext.startActivity(new Intent(mContext, MyVipListActivity.class)
                                    .putExtra("guide_vipType", 5)
                            );
                        }
                    }
                });


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


    /**
     *
     */
    public static void newTKtishi(final Context context) {


        LayoutInflater mInflater = LayoutInflater.from(context);
        final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_tishi_common, null);

        ImageView iv_close = view.findViewById(R.id.iv_close);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tag_name = view.findViewById(R.id.tag_name);
        Button btn_ok = view.findViewById(R.id.btn_ok);
        tv_title.setText("????????????");
        tag_name.setText("??????????????????????????????????????????????????????????????????????????????");
        btn_ok.setText("?????????");

        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context, NewPThotsaleActivity.class));
                ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
                        R.anim.slide_match);
                SignListAdapter.doSignGo = true;
                deleteDialog.dismiss();

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


    public static void callJiQiTiShi(final Context context, final Order order, final OrderListAdapter.notifyDatas notifydatas) {


        LayoutInflater mInflater = LayoutInflater.from(context);
        final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_call_jiqi_tishi, null);
        ImageView iv_close = view.findViewById(R.id.iv_close);


        iv_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();

            }
        });
        //????????????
        Button btn_ok = view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();

            }
        });


        //??????????????????
        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
                ToastUtil.showMyToastProgress(context, "?????????~", 2000);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


//                        ToastUtil.showShortText2("?????????????????????");
                        jiqiCanTuanSuccess(context, order, notifydatas);


                    }
                }, 2100);

            }
        });

        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(deleteDialog);


    }


    public static void vipListUpdateZuanTishi(final Context context, long zuanshiTime) {


        final Timer timer = new Timer();


        LayoutInflater mInflater = LayoutInflater.from(context);
        final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_zuanshi_update, null);
        ImageView iv_close = view.findViewById(R.id.iv_close);


        final TextView tv_time = view.findViewById(R.id.tv_time);

        final long[] reTime = {zuanshiTime};
        tv_time.setText(DateUtil.FormatMilliseondToEndTime2(reTime[0]) + "?????????");


        timer.schedule(

                new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (reTime[0] <= 0) {
                                    timer.cancel();
                                    deleteDialog.dismiss();

                                } else {
                                    reTime[0] -= 1000;
                                    tv_time.setText(DateUtil.FormatMilliseondToEndTime2(reTime[0]) + "?????????");
                                }

                            }
                        });

                    }
                },

                0, 1000
        );


        iv_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                timer.cancel();
                deleteDialog.dismiss();

            }
        });
        view.findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                timer.cancel();
                deleteDialog.dismiss();

            }
        });


        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(deleteDialog);


    }

    public static void newUserRefreshSignTaskDialog(final Context mContext) {



        LayoutInflater mInflater = LayoutInflater.from(mContext);
        final Dialog dialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_common_one_button, null);


        TextView tag_name = view.findViewById(R.id.tag_name);
        TextView tag_name1 = view.findViewById(R.id.tag_name1);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView btn_ok = view.findViewById(R.id.btn_ok);
        tv_title.setText("????????????");
        btn_ok.setText("???????????????");
        tag_name.setText(Html.fromHtml("????????????????????????????????????????????????????????????????????????"));
        tag_name1.setText(Html.fromHtml("??????<font color='#ff3f8b'><b><big>5????????????</big></b></font>????????????????????????????????????"));
        tag_name1.setVisibility(View.VISIBLE);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();


            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(dialog);

    }


    public static void vipTixianShuomingDialog(Context context, VipListBean.ViplistBean viplistBean, VipPriceData mVipPriceData) {


        LayoutInflater mInflater = LayoutInflater.from(context);
        final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_vip_tixian_shuoming, null);

        Button btn_ok = view.findViewById(R.id.btn_ok);


//        ((TextView) (view.findViewById(R.id.tv2))).setText(
//                "2???????????????" + viplistBean.getPunch_days() + "?????????????????????????????????" + viplistBean.getPunch_days() + "????????????????????????" + viplistBean.getReturn_money() + "??????"
//        );

        ((TextView) (view.findViewById(R.id.tv2))).setText(
                "2????????????" + viplistBean.getPunch_days() + "???????????????" + viplistBean.getReturn_money() + "?????????????????????"
        );

//        ((TextView) (view.findViewById(R.id.tv3))).setText(
//                "3????????????" + viplistBean.getReturn_money() + "??????????????????" + mVipPriceData.getOne_price() + "??????????????????????????????????????????????????????????????????" + (viplistBean.getReturn_money() - viplistBean.getVip_price()) + "??????"
//        );
//        ((TextView) (view.findViewById(R.id.tv4))).setText(
//                "4??????" + viplistBean.getPunch_days() + "???????????????????????????????????????" + mVipPriceData.getOne_price() + "??????????????????????????????????????????"
//        );


        btn_ok.setOnClickListener(new View.OnClickListener() {

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


    public static void showWithDrawalHGdialog(final Context context) {


        LayoutInflater mInflater = LayoutInflater.from(context);
        final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_jianglijin_tishi, null);

        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();

            }
        });
        //?????????????????? ????????????
        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyWalletCommonFragmentActivity.class);
                intent.putExtra("flag", "withDrawalFragment");
                intent.putExtra("alliance", "wallet");
                context.startActivity(intent);
                deleteDialog.dismiss();

            }
        });


        //?????????????????????  ????????????
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, NewWalletActivity.class);
//                context.startActivity(intent);
                deleteDialog.dismiss();


            }
        });


        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(deleteDialog);


    }


//    public static void initJiangLiJin(final Context mContext, final boolean isShopdetial) {//???????????????
//
//        if (YJApplication.instance.isLoginSucess()) {
//
//            YConn.httpPost(mContext, YUrl.GET_REWARD_DRAW_POP, new HashMap<String, String>(), new HttpListener<JangLiJinData>() {
//                @Override
//                public void onSuccess(JangLiJinData jiangLiJinData) {
//                    if (jiangLiJinData.getIs_pop() > 0) {
//                        DialogUtils.jiangliJinTishi(mContext, jiangLiJinData);
//                        HomePageFragment.isShowLingLiJingEnd = true;
//                    } else {
//                        if (!isShopdetial) {
//
//
//                            if (lingHongbaoShowEnd) {
//                                return;
//                            }
//
//
//                            //??????????????????
//                            new SAsyncTask<String, Void, Boolean>((FragmentActivity) mContext, R.string.wait) {
//
//                                @Override
//                                protected Boolean doInBackground(FragmentActivity context, String... params)
//                                        throws Exception {
//                                    return ComModel2.queryHasJYJL(mContext);
//                                }
//
//                                @Override
//                                protected boolean isHandleException() {
//                                    return true;
//                                }
//
//                                @Override
//                                protected void onPostExecute(FragmentActivity context, Boolean result, Exception e) {
//                                    super.onPostExecute(context, result, e);
//                                    if (null == e) {
//                                        DialogUtils.newRedHongBaoDialog(mContext, result);
//
//
//                                    }
//                                }
//
//                            }.execute();
//                        }
//
//                    }
//                }
//
//                @Override
//                public void onError() {
//
//                }
//            });
//        }


//    }

    public static void shareGroupShareCompleteDialog(final Context context) {


        LayoutInflater mInflater = LayoutInflater.from(context);
        final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_share_group, null);
        ImageView iv = view.findViewById(R.id.iv);


        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                context.startActivity(new Intent(context, KeFuActivity.class));
//                ((Activity) context).finish();

                WXminiAppUtil.jumpToWXmini(context);

            }
        });
        deleteDialog.setCancelable(false);
        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(deleteDialog);


    }


    public static void signGuideVip(final Context context) {


        LayoutInflater mInflater = LayoutInflater.from(context);
        final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_sign_guidevip, null);
        ImageView iv_close = view.findViewById(R.id.iv_close);


        iv_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();

            }
        });
        //????????????
        Button btn_ok = view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                BasicActivity.goToGuideVipOrToMyVipList(context, 0);


                deleteDialog.dismiss();

            }
        });


        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(deleteDialog);


    }

    public static void newUserFirstOrderDialog(final Context context) {


        LayoutInflater mInflater = LayoutInflater.from(context);
        final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_first_order, null);
        ImageView iv_close = view.findViewById(R.id.iv_close);


        iv_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();

            }
        });
        //????????????
        Button btn_ok = view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                ToastUtil.showShortText2("????????????");
//                context.startActivity(new Intent(context, KeFuActivity.class));
//                ((Activity) context).finish();

                WXminiAppUtil.jumpToWXmini(context);


                deleteDialog.dismiss();

            }
        });


        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(deleteDialog);


    }


    public static boolean lingHongbaoShowEnd = false;

    public static void newRedHongBaoDialog(final Context context, final boolean hasJYJL) {

        if (lingHongbaoShowEnd) {
            return;
        }

        LayoutInflater mInflater = LayoutInflater.from(context);
        final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_new_red_hongbao, null);
        ImageView ivLing = view.findViewById(R.id.iv_ling);
        ImageView iv_hongbao_bg = view.findViewById(R.id.iv_hongbao_bg);
        if (hasJYJL) {
            iv_hongbao_bg.setImageDrawable(context.getResources().getDrawable(R.drawable.new_fifty_redhongbao));

        }

        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialog.dismiss();
            }
        });
        ivLing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (hasJYJL) {
                // ????????????
                SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
//                } else {


//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJT.KEY_JSONTEXT_NEW_HONGBAO_TEXT);
//                                final HashMap<String, Object> m = (HashMap<String, Object>) map.get(YConstance.KeyJT.KEY_JSONTEXT_NEW_HONGBAO_TEXT);
//                                ((Activity) context).runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//
//                                        String shareMIniAPPimgPic = YUrl.imgurl + m.get("icon");
//                                        String shareText = m.get("title") + "";
//                                        String path = "/pages/shouye/redHongBao?shouYePage=ThreePage";
//
//                                        //????????????????????????????????????
//                                        WXminiAPPShareUtil.shareToWXminiAPP(context, shareMIniAPPimgPic, shareText, path, false);
//
//
//                                    }
//                                });
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }).start();


//
//
//                }
                context.startActivity(new Intent(context, CommonActivity.class));
                ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                deleteDialog.dismiss();


            }
        });

        deleteDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
            }
        });


        ObjectAnimator animatorX = ObjectAnimator.ofFloat(ivLing, "scaleX", 1, 1.2f, 1);

        animatorX.setRepeatCount(-1);
//        animatorX.setRepeatMode(ValueAnimator.RESTART);


        ObjectAnimator animatorY = ObjectAnimator.ofFloat(ivLing, "scaleY", 1, 1.2f, 1);
        animatorY.setRepeatCount(-1);
//        animatorY.setRepeatMode(ValueAnimator.RESTART);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.setDuration(1100);
        animatorSet.start();


        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(deleteDialog);
        lingHongbaoShowEnd = true;


    }


    public static String macthineName = "??????";

    public static void jiqiCanTuanSuccess(final Context context, final Order order, final OrderListAdapter.notifyDatas notifydatas) {


        new SAsyncTask<Void, Void, Boolean>((FragmentActivity) context, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected Boolean doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel2.callMachineCanTuan(context, order.getOrder_code());
            }

            @Override
            protected void onPostExecute(final FragmentActivity context, Boolean result, Exception e) {
                if (null == e) {

                    if (result) {//?????????????????????

                        notifydatas.refresh();

                        LayoutInflater mInflater = LayoutInflater.from(context);
                        final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
                        View view = mInflater.inflate(R.layout.dialog_jiqi_cantuan_success, null);
                        ImageView iv_close = view.findViewById(R.id.iv_close);
                        TextView tv_desc = view.findViewById(R.id.tv_desc);

                        String desc = macthineName + "????????????????????????!?????????????????????,1??????????????????";

                        SpannableStringBuilder ssb = new SpannableStringBuilder(desc);
                        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0, macthineName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                        tv_desc.setText(ssb);

                        iv_close.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                deleteDialog.dismiss();

                            }
                        });
                        //?????????
                        Button btn_ok = view.findViewById(R.id.btn_ok);
                        btn_ok.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                deleteDialog.dismiss();


                                Intent OneBuyintent = new Intent(context, OneBuyChouJiangActivity.class);
                                OneBuyintent.putExtra("isMeal", "1".equals(order.getIsTM()));

                                Bundle bundle = new Bundle();
                                bundle.putSerializable("order", order);
                                OneBuyintent.putExtras(bundle);

                                context.startActivity(OneBuyintent);
                                context.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                            }
                        });


                        deleteDialog.setCanceledOnTouchOutside(false);
                        deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT));

                        ToastUtil.showDialog(deleteDialog);


                    }

                }

                super.onPostExecute(context, result, e);
            }

        }.execute();


    }


    public static void tuiKaunFailDialog(final Context context) {


        LayoutInflater mInflater = LayoutInflater.from(context);
        final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_tuikuan_fail, null);
        ImageView iv_close = view.findViewById(R.id.iv_close);

        iv_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();

            }
        });
        Button btn_ok = view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, MyWalletCommonFragmentActivity.class)
                        .putExtra("flag", "withDrawalFragment")
                        .putExtra("alliance", "wallet")

                );
                ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                deleteDialog.dismiss();

            }
        });

        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(deleteDialog);


    }


    public interface DiKouDialogNewOnCountChouListener {
        void click();
    }

    public static void getDiKouDialogNewOnCount(final Context context, boolean isZhiZun, final DiKouDialogNewOnCountChouListener diKouDialogNewOnCountChouListener) {


//        new SAsyncTask<Void, Void, String>((FragmentActivity) context, R.string.wait) {
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected String doInBackground(FragmentActivity context, Void... params) throws Exception {
//                return ComModel2.getALLDikou(context);
//            }
//
//            @Override
//            protected void onPostExecute(final FragmentActivity context, String result, Exception e) {
//                if (null == e) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_dikou_explian_new_onecount, null);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        TextView tag_name = view.findViewById(R.id.tag_name);
        TextView btn_cancel = view.findViewById(R.id.btn_cancel);//????????????

        if (isZhiZun) {
            tag_name.setText("????????????????????????????????????????????????????????????????????????????????????");
            btn_cancel.setText("??????????????????");
        } else {
            tag_name.setText("?????????????????????????????????????????????????????????????????????????????????????????????????????????");
            btn_cancel.setText("????????????");
        }


        iv_close.setVisibility(View.INVISIBLE);

//        iv_close.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                deleteDialog.dismiss();
//
//            }
//        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();


                diKouDialogNewOnCountChouListener.click();


            }
        });
        TextView btn_ok = view.findViewById(R.id.btn_ok);//??????
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();

                ((FragmentActivity) context).finish();


//                            Intent intent = new Intent(context, MyWalletActivity.class);
//                            context.startActivity(intent);
//                            ((FragmentActivity)context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


            }
        });
        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(deleteDialog);
//                }
//
//                super.onPostExecute(context, result, e);
//            }
//
//        }.execute();


    }


    /**
     * ???????????? ??????????????????
     *
     * @param context
     */
    public static void redPacketDownDialog(final Context context) {
        final Dialog dialog = new Dialog(context, R.style.DialogQuheijiao2);
        dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        View view = View.inflate(context, R.layout.redpadket_down_dialog, null);

        ImageView open_red_packet = view.findViewById(R.id.open_red_packet);
        open_red_packet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                //????????????????????????????????????????????????
                checkWXshouquan(context, dialog);


            }
        });
        view.findViewById(R.id.icon_close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // // ?????????????????????dialog
        dialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(context, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.show();
        TongJiUtils.yunYunTongJi("duobao", 1007, 10, context);
    }


    public static void paySuccessDialog(final Context context, double money) {
        final Dialog dialog = new Dialog(context, R.style.invate_dialog_style);
        dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        View view = View.inflate(context, R.layout.pay_success_dalog, null);

        TextView tv_go_sign = view.findViewById(R.id.tv_go_sign);
        TextView tv_money = view.findViewById(R.id.tv_money);
        tv_money.setText((ComputeUtil.div(money, 2, 1) > 50.0 ? 50.0 : ComputeUtil.div(money, 2, 1)) + "");

        tv_go_sign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();

                // ????????????
                SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
                context.startActivity(new Intent(context, CommonActivity.class));
                ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

            }
        });

        // // ?????????????????????dialog
        dialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(context, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.show();
    }


    public static void newHongbaoNoJYJL(final Context context, String jianmMoney, int hongbaoMoney) {
        final Dialog dialog = new Dialog(context, R.style.invate_dialog_style);
        dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        View view = View.inflate(context, R.layout.no_jyjl_dalog, null);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        tv1.setText("20??????????????????");
        tv2.setText(jianmMoney);
        tv3.setText(hongbaoMoney - Integer.parseInt(jianmMoney) + "");

        //????????????
        view.findViewById(R.id.tv_go_sign).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ((Activity) context).finish();

            }
        });

        // // ?????????????????????dialog
        dialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(context, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    private static void checkWXshouquan(final Context mContext, final Dialog dialog) {
        boolean mWxInstallFlag = false;

        try {
            // // ?????????????????????

            if (WXcheckUtil.isWeChatAppInstalled(mContext)) {
                mWxInstallFlag = true;
            } else {
                mWxInstallFlag = false;
            }
        } catch (Exception e) {
        }

        String uuid = YCache.getCacheUserSafe(mContext).getUuid();

        if (null == uuid || uuid.equals("null")
                || uuid.equals("")) {
            //??????????????????-----?????????

            if (!mWxInstallFlag) {

                ToastUtil.showShortText(mContext, "??????????????????????????????????????????~");

            } else {

//                ToastUtil.showShortText(mContext, "?????????????????????~");


//
//                UMWXHandler wxHandler = new UMWXHandler(mContext, WxPayUtil.APP_ID,
//                        WxPayUtil.APP_SECRET);
//                wxHandler.addToSocialSDK();
//
//
//                //????????????
//                final UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);
//
//                mController.doOauthVerify(mContext, SHARE_MEDIA.WEIXIN, new SocializeListeners.UMAuthListener() {
//
//                    @Override
//                    public void onStart(SHARE_MEDIA platform) {
//                    }
//
//                    @Override
//                    public void onError(SocializeException e, SHARE_MEDIA platform) {
//                        ToastUtil.showShortText(mContext, "??????????????????");
//
//                    }
//
//                    @Override
//                    public void onComplete(Bundle value, SHARE_MEDIA platform) {
//
//                        final String openid = value.getString("openid");
//
//
//                        mController.getPlatformInfo(mContext, platform, new SocializeListeners.UMDataListener() {
//
//                            @Override
//                            public void onStart() {
//
//                            }
//
//                            @Override
//                            public void onComplete(int status, Map<String, Object> info) {
//                                if (info != null) {
//
//
//                                    final String unionid = info.get("unionid").toString();
//                                    if (TextUtils.isEmpty(unionid)) {
//                                        return;
//                                    }
//
//                                    //??????unionid
//                                    new SAsyncTask<String, Void, UserInfo>((FragmentActivity) mContext, R.string.wait) {
//
//                                        @Override
//                                        protected UserInfo doInBackground(FragmentActivity context, String... params)
//                                                throws Exception {
//                                            return ComModel2.updateUuid(mContext, unionid, openid);
//                                        }
//
//                                        @Override
//                                        protected boolean isHandleException() {
//                                            return true;
//                                        }
//
//                                        @Override
//                                        protected void onPostExecute(FragmentActivity context, UserInfo result, Exception e) {
//                                            super.onPostExecute(context, result, e);
//                                            if (null == e) {
//
//
//                                            }
//                                        }
//
//                                    }.execute();
//
//
//                                }
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onCancel(SHARE_MEDIA platform) {
//                        ToastUtil.showShortText(mContext, "?????????????????????~");
//
//                    }
//                });

            }

            return;
        }


        getHalveAgo(mContext, dialog);


    }

    public static boolean dNTF;//??????????????????????????????

    public static void getDiKouDialogNewTuanFail(final Context context, final String money) {
//
//        if (!YJApplication.instance.isLoginSucess()) {
//            return;
//        }
//
//        if (dNTF) {
//            return;
//        }
//
//
//        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) context, R.string.wait) {
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
//                return ComModel2.getALLDikouTongzhi(context);
//            }
//
//            @Override
//            protected void onPostExecute(final FragmentActivity context, HashMap<String, String> result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (null == e) {
//
//                    if (result.get("isFail").equals("1")) {
//                        String order_price = "????????????" + result.get("order_price") + "???";
//
//
//                        LayoutInflater mInflater = LayoutInflater.from(context);
//                        final Dialog deleteDialog = new Dialog(context, R.style.invate_dialog_style);
//                        View view = mInflater.inflate(R.layout.dialog_dikou_explian_new, null);
//                        ImageView iv_close = view.findViewById(R.id.iv_close);
//                        TextView tv_fanhuan = view.findViewById(R.id.tv_fanhuan);
//                        TextView tag_name = view.findViewById(R.id.tag_name);
//                        TextView tv_title = view.findViewById(R.id.tv_title);
//                        Button btn_cancel = view.findViewById(R.id.btn_cancel);
//                        tv_title.setText("????????????????????????");
//                        btn_cancel.setText("????????????");
//
//                        tag_name.setText("??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
//
//
//                        tv_fanhuan.setText(order_price);
//                        iv_close.setOnClickListener(new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                deleteDialog.dismiss();
//                                dNTF = false;
//
//                            }
//                        });
//                        btn_cancel.setOnClickListener(new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                deleteDialog.dismiss();
//                                dNTF = false;
//
//
//                                Intent intent = new Intent(context, StatusInfoActivity.class);
//                                intent.putExtra("index", 0);
//                                context.startActivity(intent);
//                                context.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//
//                            }
//                        });
//
//
//                        Button btn_ok = view.findViewById(R.id.btn_ok);
//                        btn_ok.setOnClickListener(new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                deleteDialog.dismiss();
//                                dNTF = false;
//                                Intent intent = new Intent(context, MyWalletActivity.class);
//                                context.startActivity(intent);
//                                context.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//
//                            }
//                        });
//                        deleteDialog.setCanceledOnTouchOutside(false);
//                        deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                                LinearLayout.LayoutParams.MATCH_PARENT));
//
//                        ToastUtil.showDialog(deleteDialog);
//                        dNTF = true;
//                    }
//
//
//                }
//
//            }
//
//        }.execute();


    }

    public static void showPTFailDialog(final Context mContext) {

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        final Dialog mDialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_common_pt_fail, null);
        view.findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, MyVipListActivity.class));
                mDialog.dismiss();
            }
        });
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });


        mDialog.setCanceledOnTouchOutside(false);
        mDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ToastUtil.showDialog(mDialog);

    }


    public static void showNewUserFirstOrderDialog(final Context context, final Order order, final int send_num, final int isVip) {
        final Dialog mDialog;
//        send_num = 0;
        //????????????
//        if (send_num >= 1) {//????????????????????????
//
//
//            LayoutInflater mInflater = LayoutInflater.from(context);
//            mDialog = new Dialog(context, R.style.invate_dialog_style);
//            View view = mInflater.inflate(R.layout.dialog_order_list_has_fhk, null);
//            ImageView iv_jia2 = view.findViewById(R.id.iv_jia2);
//            ImageView iv_jia3 = view.findViewById(R.id.iv_jia3);
//
//            TextView tv1 = view.findViewById(R.id.tv1);
//            TextView tv2 = view.findViewById(R.id.tv2);
//
//            view.findViewById(R.id.tv8).setVisibility(View.VISIBLE);
//            view.findViewById(R.id.tv9).setVisibility(View.VISIBLE);
//
//            tv1.setText(Html.fromHtml("???????????????<font color='#FFCF00'>3</font>????????????????????????"));
//
//            if (send_num > 0) {
//                tv1.setText(Html.fromHtml("??????????????????<font color='#FFCF00'>119</font>????????????<font color='#FFCF00'>3</font>???????????????????????????"));
//
//            }
//
//
//            tv2.setText(Html.fromHtml("?????????<font color='#FFCF00'>" + send_num + "</font>?????????<font color='#FFCF00'>" + (3 - send_num) + "</font>???"));
//
//
//            switch (send_num) {
//
//                case 1:
//                    iv_jia2.setImageResource(R.drawable.icon_fahuoka_jia);
//                    iv_jia3.setImageResource(R.drawable.icon_fahuoka_jia);
//
//                    break;
//
//                case 2:
//                    iv_jia3.setImageResource(R.drawable.icon_fahuoka_jia);
//
//                    break;
//
//            }
//
//
//            view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mDialog.dismiss();
//
//
//                }
//            });
//
//
//            view.findViewById(R.id.rl_jia).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mDialog.dismiss();
//
//                    if (send_num == 2) {
//                        SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
//                        Intent intent = new Intent(context, CommonActivity.class);
//                        intent.putExtra("fromMianOrFaClick", true);
//                        context.startActivity(intent);
//                        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//                        return;
//                    }
//
//
//                    context.startActivity(new Intent(context, MyVipListActivity.class)
//                            .putExtra("isGuideFHK", true)
//
//                    );
//                }
//            });
//
//
//            view.findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mDialog.dismiss();
//
//                    if (send_num == 2) {
//                        SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
//                        Intent intent = new Intent(context, CommonActivity.class);
//                        intent.putExtra("fromMianOrFaClick", true);
//                        context.startActivity(intent);
//                        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//                        return;
//                    }
//                    context.startActivity(new Intent(context, MyVipListActivity.class)
//                            .putExtra("isGuideFHK", true)
//
//                    );
//                }
//            });
//            view.findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
////                    mDialog.dismiss();
//
//                    context.startActivity(new Intent(context, MyVipListActivity.class)
//                            .putExtra("isNewUserGuideVIP", true)
//
//                    );
//
//                }
//            });
//
//            mDialog.setCanceledOnTouchOutside(false);
//            mDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT));
//            ToastUtil.showDialog(mDialog);
//
//
//            return;
//        }


        //???????????????---?????????????????????
        LayoutInflater mInflater = LayoutInflater.from(context);
        mDialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_order_list_new_user, null);

        TextView tv2 = view.findViewById(R.id.tv2);
        ImageView bt1 = view.findViewById(R.id.bt1);
        tv2.setText("????????????????????????");

//        if (CommonUtils.isVip(OrderInfoFragment.isVip, OrderInfoFragment.maxType)) {
        bt1.setImageResource(R.drawable.direct_deliver_become_member);


//        } else {
//            bt1.setImageResource(R.drawable.first_ling_delivergoods);
//
//        }


        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();

//                if (OrderInfoFragment.send_num < 1 && OrderInfoFragment.free_num < 1) {
//                    if (!CommonUtils.isVip(OrderInfoFragment.isVip, OrderInfoFragment.maxType)) {
//                        showNewUserFirstOrderToSignDialog(context);
//
//                    }
//
//                }


            }
        });


        view.findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                if (CommonUtils.isVip(OrderInfoFragment.isVip, OrderInfoFragment.maxType)) {
//                    context.startActivity(new Intent(context, MyVipListActivity.class)
//                            .putExtra("isNewUserGuideVIP", true)
//
//                    );

                    context.startActivity(new Intent(context, MyVipListActivity.class)
                            .putExtra("vipUpdateVipType", order.getVip_roll_type())

                    );

                } else {
                    context.startActivity(new Intent(context, MyVipListActivity.class));
                }


            }
        });

        mDialog.setCanceledOnTouchOutside(false);
        mDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ToastUtil.showDialog(mDialog);


    }

    public static void showNewUserFirstOrderToSignDialog(final Context context) {
        final Dialog mDialog;

        LayoutInflater mInflater = LayoutInflater.from(context);
        mDialog = new Dialog(context, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_new_user_order_guide_sign, null);
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();

            }
        });


        view.findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();


                SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
                context.startActivity(new Intent(context, CommonActivity.class));
                mDialog.dismiss();
            }
        });


        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(context, 275),
                LinearLayout.LayoutParams.MATCH_PARENT));
        ToastUtil.showDialog(mDialog);


    }


    private static void getHalveAgo(final Context context, final Dialog dialog) {
        new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) context, 0) {
            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                LoadingDialog.show((FragmentActivity) context);
            }

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {

                return ComModel2.getYiDouHalveAgo(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null) {
                    String end_times = result.get("d");
                    SharedPreferencesUtil.saveStringData(context, YConstance.Pref.YIDOU_HALVE_END_TIMES, end_times);
                }
                Intent intent = new Intent(context, BootPayDetails.class);
                context.startActivity(intent);
                dialog.dismiss();
            }

        }.execute();
    }


    /**
     * ???????????? ???????????????????????? ????????????????????????
     *
     * @param context
     */
    public static void redPacketDownSignTCDialog(final Context context) {
        final Dialog dialog = new Dialog(context, R.style.DialogQuheijiao2);
        dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        View view = View.inflate(context, R.layout.dialog_redpacket_down_sign_tc, null);

        Button btn_red = view.findViewById(R.id.btn_red);
        btn_red.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                getHalveAgo(context, dialog);

            }
        });
        view.findViewById(R.id.icon_close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // // ?????????????????????dialog
        dialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(context, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.show();
    }


    /**
     * ???????????????????????? ????????????
     */
    public static void meuyueJingxiDialog(final Context mContext, final String doValue) {
        final Dialog dialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        View view = View.inflate(mContext, R.layout.dialog_meiyie_tishi, null);

        Button btn_red = view.findViewById(R.id.btn_red);
        btn_red.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                getHalveAgo(context, dialog);


                String where = "type_name=??????&notType=true";
                try {
                    where = doValue.split(",")[0];
                } catch (Exception e) {
                }

                if (where.equals("collection=shopping_page")) {// ??????
                    // ????????????
//                    Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
//                    intent2.putExtra("toShop", "toShop");
//                    mContext.startActivity(intent2);
//                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


//                    mContext.startActivity(new Intent(mContext, ShopPageActivity.class));
//                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    //???????????????????????????--????????????
                    Intent intent = new Intent(mContext, ShopPageActivity.class);
                    intent.putExtra("isAddShopcart", true);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                } else if (where.equals("collection=collocation_shop")) {// ??????

                    Intent intent = new Intent(mContext, ForceLookMatchActivity.class);
                    intent.putExtra("type", "1");


                    intent.putExtra("isSignLiulan", true);
                    intent.putExtra("isGaoMai", true);


                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                } else if (where.equals("collection=shop_activity")) {// ??????

                    // ?????????????????????

                    Intent intent = new Intent(mContext, SignActiveShopActivity.class);

                    intent.putExtra("isSignLiulan", true);
                    intent.putExtra("isGaoMai", true);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                } else if (where.equals("collection=csss_shop")) {// ??????
                    Intent intent = new Intent(mContext, ForceLookMatchActivity.class);

                    intent.putExtra("isSignLiulan", true);
                    intent.putExtra("isGaoMai", true);
                    intent.putExtra("type", "2");
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                } else if (where.equals("type_name=??????&notType=true")) {// ??????

                    Intent intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("id", "6");
                    intent.putExtra("title", "??????");

                    intent.putExtra("isSignLiulan", true);
                    intent.putExtra("isGaoMai", true);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                } else if (where.equals("collection=shop_home")) {// ??????

                    // ????????????
                    Intent intent2 = new Intent(mContext, MainMenuActivity.class);
                    intent2.putExtra("toHome", "toHome");
                    mContext.startActivity(intent2);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                } else { // ??????????????????????????????

                    Intent intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("id", "6");
                    intent.putExtra("title", "??????");

                    intent.putExtra("isSignLiulan", true);
                    intent.putExtra("isGaoMai", true);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                }

                dialog.dismiss();


            }
        });
        view.findViewById(R.id.icon_close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // // ?????????????????????dialog
        dialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.show();
    }


    /**
     * ??????????????? ????????????
     *
     * @param context
     */
//    public static void guideToZhuanqianDialog(final Context context) {
//
//
//        final Dialog dialog = new Dialog(context, R.style.DialogQuheijiao2);
//        dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
//        View view = View.inflate(context, R.layout.dialog_guide_to_zhuanqian, null);
//
//
//        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
//        TextView tv_content_1 = (TextView) view.findViewById(R.id.tv_content_1);
//
//        view.findViewById(R.id.icon_close).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                DialogUtils.guideToZhuanqianTipsDialog(context);
//                dialog.dismiss();
//            }
//        });
//        view.findViewById(R.id.btn_red).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if ("0".equals(SharedPreferencesUtil.getStringData(context, "LingYUANGOUTishiDialogHome", "0"))) {
//                    ToastUtil.showDialog(new LingYUANGOUTishiDialog(context, R.style.DialogStyle1));
//                } else {
//                    DialogUtils.guideToZhuanqianTipsDialog(context);
//                }
//                SharedPreferencesUtil.saveStringData(context, "LingYUANGOUTishiDialogHome", "1");
//                dialog.dismiss();
//            }
//        });
//        view.findViewById(R.id.btn_white).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//
//
//                if (GuideActivity.hasSign) {
//
//                    // ????????????
//                    SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
//                    context.startActivity(new Intent(context, CommonActivity.class));
//                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//
//                } else {
////                    // ????????????
////                    Intent intent2 = new Intent((Activity) context, MainMenuActivity.class);
////                    intent2.putExtra("toHome", "toHome");
////                    context.startActivity(intent2);
////                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//                }
//
//
//            }
//        });
//
//        // // ?????????????????????dialog
//        dialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(context, 300),
//                LinearLayout.LayoutParams.MATCH_PARENT));
////        dialog.show();//??????????????????show
//        HomePageFragment.homeIsShow = false;
//        getXSYDString(context, tv_content, tv_content_1, dialog);
//    }

    /**
     * ??????????????? ???????????? ????????????
     *
     * @param context
     */
//    public static void guideToZhuanqianTipsDialog(final Context context) {
//        final Dialog dialog = new Dialog(context, android.R.style.Theme);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        final View view = View.inflate(context, R.layout.dialog_guide_to_zhuanqian_tips, null);
//
//
//        view.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        final ImageView imgView = (ImageView) view.findViewById(R.id.shoushi_match);
//        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "Y", 0.0F, 0.1F, 0.2F, 0.3F, 0.4F, 0.5F, 0.6F, 0.7F, 0.8F, 0.9F, 1.0F).setDuration(300);
//        anim.start();
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float cVal = (Float) animation.getAnimatedValue();
//                imgView.setAlpha(cVal);
//                imgView.setScaleX(cVal);
//                imgView.setScaleY(cVal);
//            }
//        });
//        // // ?????????????????????dialog
//        dialog.setContentView(view);
//        dialog.show();
//    }

    public static String yContent;

    public static void getXSYDString(final Context context, final TextView tv_content, final TextView tv_content_1, final Dialog dialog) {
//        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) context, null) {
//
//            @Override
//            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
//                return ComModel2.getPointGuiZeContent(context);
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
//                if (!TextUtils.isEmpty(yContent)) {
//                    tv_content.setText(yContent);
//                }
//            }
//
//        }.execute();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJT.KEY_JSONTEXT_QDRWYD);
                    final HashMap<String, Object> m = (HashMap<String, Object>) map.get(YConstance.KeyJT.KEY_JSONTEXT_QDRWYD);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String title1 = "???????????????X??????????????????XX????????????";
                            String title = "XX???????????????????????????????????????????????????????????????????????????????????????";
                            if (m != null && m.size() > 0) {
                                String text = (String) m.get("text");
                                title1 = (String) m.get("title1");
                                title = (String) m.get("title");
                                String[] texts = text.split(",");
                                try {
//                                    textContent = "??????????????????" + texts[0] + "????????????\n??????" + texts[1] + "????????????????????????";
                                    title = title.replaceFirst("\\$\\{replace\\}", texts[0]);

                                    title1 = title1.replaceFirst("\\$\\{replace\\}", texts[1]);
                                    title1 = title1.replaceFirst("\\$\\{replace\\}", texts[2]);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            tv_content.setText(title);
                            tv_content_1.setText(title1);
                            dialog.show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    /**
     * ??????????????????
     *
     * @param context
     */
    public static void IndianaResultDialog(final Context context) {
//                    0 ???????????? true??????
//                    1.??????????????????
//                    2.??????????????????
//                    3.??????
//                    4.???????????? ??? 2 ??????2?????????
//                    5.????????????
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<String> list = ComModel2.getIndianaDialogMessage(context);
                    if (list != null && list.size() > 0) {//
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < list.size(); i++) {
                                    String isWin = "";//????????????
                                    String takeNumber = "";//????????????
                                    String winName = "";//??????????????????
                                    String issue = "";//??????
                                    String money = "";//????????????
                                    String winNumber = "";//????????????
                                    String type = "0";
//                        final  String shop_code;//??????????????????
//                        shop_code=strings[6];
                                    try {
                                        String str = list.get(i);
                                        String[] strings = str.split("\\^");
                                        isWin = strings[0];
                                        takeNumber = strings[1];
                                        winName = strings[2];
                                        issue = strings[3];
                                        money = strings[4];
                                        winNumber = strings[5];
                                        type = strings[7];
                                    } catch (Exception e2) {
                                        e2.printStackTrace();
                                    }
                                    final Dialog dialog = new Dialog(context, R.style.DialogQuheijiao2);
                                    dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    View view;
                                    if ("true".equals(isWin)) {//?????????
                                        view = View.inflate(context, R.layout.dialog_indiana_win, null);
                                    } else {//?????????
                                        view = View.inflate(context, R.layout.dialog_indiana_no_win, null);
                                        TextView tv_name = view.findViewById(R.id.tv_name);
                                        TextView tv_count = view.findViewById(R.id.tv_count);//????????????
                                        tv_name.setText("?????????:" + winName);
                                        tv_count.setText("????????????:" + takeNumber);
                                        if ("0".equals(type)) {
                                            tv_name.setText("?????????:" + winName);
                                            tv_count.setText("????????????:" + takeNumber);
                                        } else if ("1".equals(type)) {
                                            tv_name.setText("?????????:" + winName);
                                            tv_count.setText("????????????:" + takeNumber);
                                        } else if ("2".equals(type)) {
                                            tv_name.setText("?????????:" + winName + "??????");
                                            tv_count.setText("????????????:" + takeNumber);
                                        }

                                    }
                                    TextView tv_title = view.findViewById(R.id.tv_title);
                                    TextView tv_win_number = view.findViewById(R.id.tv_number);
                                    tv_title.setText("???????????????" + issue + "???" + money + "?????????????????????");
                                    tv_win_number.setText("???????????????:" + winNumber);
//                                    tv_title.setText("???????????????" + issue + "???" + money + "?????????????????????");
                                    if ("0".equals(type)) {
                                        tv_title.setText("???????????????" + issue + "???" + "????????????????????????");
                                        tv_win_number.setText("???????????????:" + winNumber);
                                    } else if ("1".equals(type)) {
                                        tv_title.setText("???????????????" + issue + "???" + money + "??????????????????????????????");
                                        tv_win_number.setText("???????????????:" + winNumber);
                                    } else if ("2".equals(type)) {
                                        tv_title.setText("???????????????" + issue + "???" + "????????????????????????");
                                        tv_win_number.setText("???????????????:" + winNumber);
                                    }
                                    Button to_look = view.findViewById(R.id.to_look);
                                    ImageView icon_close = view.findViewById(R.id.icon_close);
                                    icon_close.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                    final int finalI = i;
                                    final String finalType = type;
                                    final String finalIssue = issue;
                                    to_look.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String str = list.get(finalI);
                                            String[] strings = str.split("\\^");
                                            String shop_code = "";
                                            if (strings.length > 6) {
                                                shop_code = strings[6];
                                            }
                                            if ("0".equals(finalType)) {
                                                if (!(context instanceof ShopDetailsIndianaActivity)) {
                                                    Intent intent;
                                                    intent = new Intent(context, ShopDetailsIndianaActivity.class);
                                                    intent.putExtra("shop_code", shop_code);
                                                    context.startActivity(intent);
                                                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
                                                            R.anim.slide_match);
                                                    dialog.dismiss();
                                                } else {
                                                    dialog.dismiss();
                                                }
                                            } else if ("1".equals(finalType)) {
                                                if (!(context instanceof ShopDetailsMoneyIndianaActivity)) {
                                                    Intent intent;
                                                    intent = new Intent(context, ShopDetailsMoneyIndianaActivity.class);
                                                    intent.putExtra("shop_code", shop_code);
                                                    context.startActivity(intent);
                                                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
                                                            R.anim.slide_match);
                                                    dialog.dismiss();
                                                } else {
                                                    dialog.dismiss();
                                                }
                                            } else if ("2".equals(finalType)) {
                                                if (!(context instanceof ShopDetailsGroupIndianaActivity)) {
                                                    Intent intent;
                                                    intent = new Intent(context, ShopDetailsGroupIndianaActivity.class);
                                                    intent.putExtra("shop_code", shop_code);
                                                    intent.putExtra("old_issue_code", "" + finalIssue);
                                                    context.startActivity(intent);
                                                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
                                                            R.anim.slide_match);
                                                    dialog.dismiss();
                                                } else {
                                                    dialog.dismiss();
                                                }
                                            }
                                        }
                                    });
                                    dialog.setContentView(view);
                                    dialog.show();
                                }

                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public static void firstPTfailDialog(final Context context) {


        final String showCount = SharedPreferencesUtil.getStringData(context, Constants.PT_FAIL_SHOW, "0");

        //????????????
        if ("2".equals(showCount) || !YJApplication.instance.isLoginSucess()) {
            return;
        }


        new SAsyncTask<Void, Void, Boolean>((FragmentActivity) context, R.string.wait) {

            @Override
            protected Boolean doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel.getPingtuanFirstFail(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, Boolean result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result) {


                        new SAsyncTask<Void, Void, HashMap<String, String>>(context, R.string.wait) {

                            @Override
                            protected boolean isHandleException() {
                                return true;
                            }

                            @Override
                            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
                                return ComModel2.getOneBuyYHQ(context);
                            }

                            @Override
                            protected void onPostExecute(final FragmentActivity context, HashMap<String, String> map, Exception e) {
                                if (null == e) {

                                    if (null == map) {
                                        return;
                                    }
                                    String condPrice = map.get("price") + "";
                                    String showMoney = Constants.NEW_HONGBAO - Integer.parseInt(condPrice) + "";


                                    final Dialog dialog = new Dialog(context, R.style.invate_dialog_style);
                                    dialog.getWindow().

                                            setWindowAnimations(R.style.common_dialog_style);

                                    View view = View.inflate(context, R.layout.dialog_first_pt_fail, null);


                                    //????????????
                                    ((TextView) view.findViewById(R.id.tx_money)).setText(showMoney);
                                    view.findViewById(R.id.tv_go_sign).

                                            setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View v) {
                                                    // ????????????
                                                    SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
                                                    context.startActivity(new Intent(context, CommonActivity.class));
                                                    context.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                                                    dialog.dismiss();

                                                }
                                            });

                                    // // ?????????????????????dialog
                                    dialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(context, 270),
                                            LinearLayout.LayoutParams.MATCH_PARENT));
                                    dialog.setCancelable(true);
                                    ToastUtil.showDialog(dialog);
                                    if ("0".equals(showCount)) {
                                        SharedPreferencesUtil.saveStringData(context, Constants.PT_FAIL_SHOW, "1");
                                    }
                                    if ("1".equals(showCount)) {
                                        SharedPreferencesUtil.saveStringData(context, Constants.PT_FAIL_SHOW, "2");
                                    }


                                }

                                super.onPostExecute(context, map, e);
                            }

                        }.execute();


                    }
                }
            }

        }.execute();


    }
}
