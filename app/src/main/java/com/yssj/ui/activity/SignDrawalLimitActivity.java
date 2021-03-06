package com.yssj.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cretin.www.wheelsruflibrary.listener.RotateListener;
import com.cretin.www.wheelsruflibrary.view.WheelSurfView;
import com.newcaoguo.easyrollingnumber.view.ScrollingDigitalAnimation;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.activity.wxapi.WXEntryActivity;
import com.yssj.app.AppManager;
import com.yssj.custom.view.CommonLoadingView;
import com.yssj.data.YDBHelper;
import com.yssj.entity.BaseData;
import com.yssj.entity.BaseDataBean;
import com.yssj.entity.CJTXcountData;
import com.yssj.entity.TXCJzhongjiangData;
import com.yssj.entity.UserInfo;
import com.yssj.eventbus.MessageEvent;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.vip.MyVipListActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.DateUtils;
import com.yssj.utils.GlideUtils;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.SimpleCountDownTimer;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.YCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/***
 * ????????????
 */
public class SignDrawalLimitActivity extends BasicActivity implements OnClickListener {
    private CommonLoadingView loading;
    private ListView listView1;
    private View imgBack;
    private boolean yindaoVip;

    private MyAdapter adapter1;

    public static final int LOGIN_SUCCESS = -10;

    private List<HashMap<String, String>> mListData1 = new ArrayList<>();
    private Timer mTimer1;

    private WheelSurfView wheelSurfView;

    private boolean isVirtual;//?????????????????????

    private int type = 2;//?????????????????????1????????????2??????

    private SimpleCountDownTimer countDownTimer;


    private CJTXcountData mCjtXcountData;//??????????????????????????????

    private long txkRecLen;
    private Timer tkxTimer;
    private TextView tv_txk_countdown;
    private Dialog mDialog;
    private Context mContext;
    private boolean isRun;//??????????????????
    public static SignDrawalLimitActivity instance;

    private double redPacketValue;//??????????????????

    private TXCJzhongjiangData mTXCJzhongjiangData;//??????????????????
    private int choujiangCount;//??????????????????
    private int totalchoujiangCount;//?????????????????????????????????
    private int dayall_count;//???????????????????????????
    private String redPacketValue_totaldata;//??????????????????????????????????????????????????????

    public static final int BUG_TXK_SUCCESS = 2000;
    public static final int No_BUG_TXK_SUCCESS = -2000;
    private boolean isFromWallet;//???????????????????????????

    private boolean zhongJiangStatusQueryEd = true;
    //    public static final int BUG_VIP_SUCCESS = 3000;
    private boolean fromSign = false;


    private boolean fromFreeBuy = true;//???????????????????????????????????????20??????
    private boolean fromPT = true;//???????????????????????????????????????20??????
    private int awardIndex = 1;//????????????????????? ??????1?????????????????????
    private String free_url; //?????????????????????????????????

    //    private boolean hiddenTXK = true;
//    private int new_raffle_skipSwitch;
    private BaseData virtualZJdata;
    private boolean userIsVip; //???????????????

    private boolean isFromNewWallet;//????????????????????????????????????????????????????????????????????????????????????

    private int is_finishCome = 0;//20?????????????????? 0??????(??????) 1????????????
    private String showNewWalletTXallMoney;


    /**
     *
     * ????????????20???????????? ??????????????????0????????????????????????????????????????????????
     * ??????????????????????????????????????????????????????????????????????????????????????????
     *              ?????????20?????????????????????????????????????????????
     *
     * ??????????????????????????????????????????????????????
     *
     */


    /**
     * userIsVip
     * ????????????????????????
     * 1.??????????????????????????????
     * 2.??????????????????????????????????????????????????????????????????????????????15??????????????????????????????
     * 3.?????????????????????????????????????????????
     */

    @Override
    public void onBackPressed() {

        boolean mIsVip = false;

        if (choujiangCount > 0) {
            ToastUtil.showShortText2("????????????????????????????????????????????????????????????");
            return;
        }

        if (YJApplication.instance.isLoginSucess() && null != mCjtXcountData) {


            mIsVip = CommonUtils.isVip(mCjtXcountData.getIsVip(), mCjtXcountData.getMaxType());

        }


        if (isRun) {
            return;
        } else if (null != mCjtXcountData && mCjtXcountData.getIs_finish() == 0 && !mIsVip) { //?????????????????????--????????????


            if (fromSign) {
                super.onBackPressed();
                finish();
                return;

            }


            if (null != CommonActivity.instance) {
                CommonActivity.instance.finish();
            }

            SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
            Intent intent = new Intent(mContext, CommonActivity.class);
            startActivity(intent);
            finish();

        } else {
            if (choujiangCount <= 0) {
                SharedPreferencesUtil.saveBooleanData(mContext, "choujiang_not_count_back", true);
            }

            super.onBackPressed();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (GuideActivity.needFengKong) {
            ToastUtil.showMyToast(this, "?????????????????????????????????????????????", 3000);
            finish();
            return;
        }

        setContentView(R.layout.activity_withdrawal_limit_sign);

        AppManager.getAppManager().addActivity(this);
        EventBus.getDefault().register(this);//??????
//        isVirtual = getIntent().getBooleanExtra("isVirtual", false);

        fromPT = getIntent().getBooleanExtra("fromPT", false);
        fromFreeBuy = getIntent().getBooleanExtra("fromFreeBuy", false);
        fromSign = getIntent().getBooleanExtra("fromSign", false);
        isFromNewWallet = getIntent().getBooleanExtra("isFromNewWallet", false);
        if (isFromNewWallet) {
            showNewWalletTXallMoney = getIntent().getStringExtra("showNewWalletTXallMoney");
        }


//        if (null != GuideActivity.switchData) {
//            hiddenTXK = GuideActivity.switchData.getData().getTrial_hidden_switch() == 0;
//            new_raffle_skipSwitch = GuideActivity.switchData.getData().getNew_raffle_skipSwitch();
//        }

//        hiddenTXK = true;//??????


        //????????????????????????????????????
        isVirtual = !YJApplication.instance.isLoginSucess();
        isFromWallet = getIntent().getBooleanExtra("isFromWallet", false);

        type = getIntent().getIntExtra("type", 2);
        mContext = this;
        loading = new CommonLoadingView(this);
        initView();
    }

    /**
     * ?????????View
     */
    private void initView() {
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        listView1 = findViewById(R.id.list_view1);
        initLimitAwardsList();
        wheelSurfView = findViewById(R.id.wheelSurfView);
        //??????????????????
        wheelSurfView.setRotateListener(new RotateListener() {
            @Override//????????????
            public void rotateEnd(int position, String des) {
                isRun = false;


                roteEnd(position);
            }

            @Override
            public void rotating(ValueAnimator valueAnimator) {
                isRun = true;

            }

            @Override //????????????
            public void rotateBefore(ImageView goImg) {
                if (isRun) {
                    return;
                }
                if (null != mDialog && mDialog.isShowing()) {
                    return;
                }

                //?????????
                if (!YJApplication.instance.isLoginSucess()) {
                    if (LoginActivity.instances != null) {
                        LoginActivity.instances.finish();
                    }

                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.putExtra("login_register", "login");
                    intent.putExtra("isVirtual", true);
                    startActivityForResult(intent, 1002);

                    return;
                }


                //???????????????????????????????????????????????????
                if (!zhongJiangStatusQueryEd) {
                    return;
                }
                //???????????????????????????????????????
                if (CommonUtils.isNotFastClick()) {
                    clickStart();

                }

            }
        });


        //???????????????
//        if (true) {
//            showGetFreeBuyDialog();
//            return;
//        }


        //??????????????????????????????
        if (isVirtual) {

            //????????????????????????????????????
            YConn.httpPost(mContext, YUrl.REALRAFFLECHANNEL, new HashMap<String, String>(), new HttpListener<BaseData>() {
                @Override
                public void onSuccess(BaseData result) {

                    virtualZJdata = result;

                    choujiangCount = 1;
                    redPacketValue = result.getRaffle_money();
                    wheelSurfView.startRotate(getPosition());

                }

                @Override
                public void onError() {

                }
            });


        } else {//????????????????????????????????????????????????????????????????????????


//            //??????????????????
//            HashMap<String, String> pairsMap = new HashMap<>();
//            YConn.httpPost(mContext, YUrl.QUERY_VIP_INFO2, pairsMap
//                    , new HttpListener<VipInfo>() {
//                        @Override
//                        public void onSuccess(VipInfo vipInfo) {
//                            isVip = vipInfo.getIsVip();
//
//                            //??????????????????????????????????????????????????????????????????????????????
//                            if (!StringUtils.isEmpty(MyVipListActivity.zuanShiDikou)) {
//                                queryCount(false);
//
//                            } else {
//                                showBuyVipSucDialog();
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onError() {
//
//                        }
//                    });

            //??????????????????

            //????????????????????????
            if (isFromNewWallet) {
                queryCount(true);
                return;

            }
            //????????????????????????
            YConn.httpPost(mContext, YUrl.FIRST_ZUANSHI_ZHUANPAIN_TISHI,
                    new HashMap<String, String>(), new HttpListener<BaseData>() {
                        @Override
                        public void onSuccess(BaseData result) {
                            if (result.getIsPopup() == 1) {//????????????
                                showBuyVipSucDialog(result);
                                return;
                            }

                            if (result.getIsPopup() == 2) {//????????????
                                showBuyVipSucDialogHG(result);
                                return;
                            }


                            queryCount(true);


                        }

                        @Override
                        public void onError() {

                        }
                    });


        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonUtils.disableScreenshots(this);


    }

    private void showGetFreeBuyDialog() {
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }

        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.dialog_withdraw_chouzhong_freebuy, null);
        TextView tv2 = view.findViewById(R.id.tv2);
        ImageView iv_center_img = view.findViewById(R.id.iv_center_img);

        PicassoUtils.initImageNoDefPic2(free_url, iv_center_img);


        Spanned tv2Str = Html.fromHtml("??????????????????<font color='#FDCC21'><strong>" + mTXCJzhongjiangData.getShow_free_money() + "?????????</strong></font>????????????<font color='#FDCC21'><strong>????????????</strong></font>?????????????????????");

//        Spanned tv3Str = Html.fromHtml("??????????????????<font color='#FDCC21'>" + DateUtils.dePoint("#0.00", redPacketValue) +
//                "</font>?????????????????????????????????????????????????????????");

        tv2.setText(tv2Str);


        view.findViewById(R.id.bt_vip).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {//????????????
                mDialog.dismiss();

                if (mTXCJzhongjiangData.getFreeBuyType() == 1) {
                    showGetFreeBuyDialog1();

                } else {
                    showGetFreeBuyDialog2();

                }


            }
        });


        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();


    }

    private void showGetFreeBuyDialog1() {
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }

        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.dialog_withdraw_chouzhong_freebuy2, null);

        TextView tv_money = view.findViewById(R.id.tv_money);
        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView bt1 = view.findViewById(R.id.bt1);
        TextView bt2 = view.findViewById(R.id.bt2);
        TextView tv_time = view.findViewById(R.id.tv_time);
        bt1.setText("??????????????????");


        tv_money.setText(mTXCJzhongjiangData.getYj_money() + "???");


        Spanned sd = Html.fromHtml("?????????????????????????????????<font color='#FDCC21'><strong>" +
                "????????????</strong></font>" +
                "?????????<font color='#FDCC21'><strong>" +
                mTXCJzhongjiangData.getShow_free_money() + "?????????</strong></font>" +
                "???");


        tv1.setText(sd);

        Spanned sd2 = Html.fromHtml("?????????<font color='#FDCC21'><strong>" +
                "??????" + mTXCJzhongjiangData.getYj_money() + "???</strong></font>???????????????");
        tv2.setText(sd2);


        long countTime = 9 * 60 * 1000;
        if (null != mTXCJzhongjiangData) {
            countTime = mTXCJzhongjiangData.getExpireTime();
        }
        countDownTimer = new SimpleCountDownTimer(countTime, tv_time).setOnFinishListener(new SimpleCountDownTimer.OnFinishListener() {
            @Override
            public void onFinish() {
                if (null != mDialog) {
                    mDialog.dismiss();
                }
            }
        });
        countDownTimer.start();


        bt1.setOnClickListener(new OnClickListener() {//????????????
            @Override
            public void onClick(View view) {//????????????
//                mDialog.dismiss();
                startActivityForResult(new Intent(SignDrawalLimitActivity.this, MyVipListActivity.class)
                                .putExtra("vipUpdateVipType", 5)
                        , 1003);

            }
        });


        bt2.setOnClickListener(new OnClickListener() {//????????????laststartLuckBtn
            @Override
            public void onClick(View view) {//????????????
                mDialog.dismiss();
//                continueTXfromDialogClick();


                //20??????????????????????????????????????????20??????????????????--?????????
//                if (new_raffle_skipSwitch == 0
//                        && mCjtXcountData.getIs_finish() == 1
//                        && choujiangCount <= 0
//                        && mCjtXcountData.getReRoundCount() == 0) {
//                    if (null != CommonActivity.instance) {
//                        CommonActivity.instance.finish();
//                    }
//
//                    SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
//                    Intent intent = new Intent(mContext, CommonActivity.class);
//                    intent.putExtra("20choujiang_complete", true);
//                    startActivity(intent);
//                    finish();
//                }


                if (choujiangCount <= 0) {//???????????????


                    //??????????????????0????????????????????????????????????
                    if (mCjtXcountData.getReRoundCount() > 0) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("type", type + "");
                        YConn.httpPost(mContext, YUrl.QUERY_NEW_CJTX_COUNT, map, new HttpListener<CJTXcountData>() {
                            @Override
                            public void onSuccess(CJTXcountData cjtXcountData) {
                                isVirtual = false;
                                mCjtXcountData = cjtXcountData;
                                totalchoujiangCount = mCjtXcountData.getData();
                                dayall_count = mCjtXcountData.getAll_count();
                                redPacketValue_totaldata = mCjtXcountData.getAll_money() + "";
                                choujiangCount = mCjtXcountData.getData();

                                //????????????????????????????????????
                                clickStart();


                            }

                            @Override
                            public void onError() {

                            }
                        });


                    } else {
                        if (Double.parseDouble(redPacketValue_totaldata) > 0) {
                            coujizngCountUseUpdialog();
                            if (mCjtXcountData.getIs_finish() != 0) {//??????20???????????????
                                SharedPreferencesUtil.saveBooleanData(mContext, "20Choujiang_finish", true);
                            }
                        } else {
                            showNoHasCountDialog();

                        }
                    }


                } else {//????????????
                    clickStart();
                }


            }
        });


        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();


    }


    private void showGetFreeBuyDialog2() {
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }

        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.dialog_withdraw_chouzhong_freebuy2, null);

        TextView tv_money = view.findViewById(R.id.tv_money);
        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView bt1 = view.findViewById(R.id.bt1);
        TextView bt2 = view.findViewById(R.id.bt2);
        TextView tv_time = view.findViewById(R.id.tv_time);


        tv_money.setText(redPacketValue_totaldata + "???");


        Spanned sd = Html.fromHtml("???????????????????????????<font color='#FDCC21'><strong>" +
                "????????????</strong></font>" +
                "?????????<font color='#FDCC21'><strong>" +
                mTXCJzhongjiangData.getShow_free_money() + "?????????</strong></font>" +
                "???");


        tv1.setText(sd);

        Spanned sd2 = Html.fromHtml("?????????<font color='#FDCC21'><strong>" +
                "????????????</strong></font>????????????????????????<font color='#FDCC21'><strong>" +
                redPacketValue_totaldata + "???</strong></font>?????????");
        tv2.setText(sd2);


        long countTime = 9 * 60 * 1000;
        if (null != mTXCJzhongjiangData) {
            countTime = mTXCJzhongjiangData.getExpireTime();
        }
        countDownTimer = new SimpleCountDownTimer(countTime, tv_time).setOnFinishListener(new SimpleCountDownTimer.OnFinishListener() {
            @Override
            public void onFinish() {
                if (null != mDialog) {
                    mDialog.dismiss();
                }
            }
        });
        countDownTimer.start();


        bt1.setOnClickListener(new OnClickListener() {//????????????
            @Override
            public void onClick(View view) {//????????????
//                mDialog.dismiss();
                startActivityForResult(new Intent(SignDrawalLimitActivity.this, MyVipListActivity.class), 1003);

            }
        });


        bt2.setOnClickListener(new OnClickListener() {//????????????laststartLuckBtn
            @Override
            public void onClick(View view) {//????????????
                mDialog.dismiss();
//                continueTXfromDialogClick();


                //20??????????????????????????????????????????20??????????????????--?????????
//                if (new_raffle_skipSwitch == 0
//                        && mCjtXcountData.getIs_finish() == 1
//                        && choujiangCount <= 0
//                        && mCjtXcountData.getReRoundCount() == 0) {
//                    if (null != CommonActivity.instance) {
//                        CommonActivity.instance.finish();
//                    }
//
//                    SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
//                    Intent intent = new Intent(mContext, CommonActivity.class);
//                    intent.putExtra("20choujiang_complete", true);
//                    startActivity(intent);
//                    finish();
//                }


                if (choujiangCount <= 0) {//???????????????


                    //??????????????????0????????????????????????????????????
                    if (mCjtXcountData.getReRoundCount() > 0) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("type", type + "");
                        YConn.httpPost(mContext, YUrl.QUERY_NEW_CJTX_COUNT, map, new HttpListener<CJTXcountData>() {
                            @Override
                            public void onSuccess(CJTXcountData cjtXcountData) {
                                isVirtual = false;
                                mCjtXcountData = cjtXcountData;
                                totalchoujiangCount = mCjtXcountData.getData();
                                dayall_count = mCjtXcountData.getAll_count();
                                redPacketValue_totaldata = mCjtXcountData.getAll_money() + "";
                                choujiangCount = mCjtXcountData.getData();

                                //????????????????????????????????????
                                clickStart();


                            }

                            @Override
                            public void onError() {

                            }
                        });


                    } else {
                        if (Double.parseDouble(redPacketValue_totaldata) > 0) {
                            coujizngCountUseUpdialog();
                            if (mCjtXcountData.getIs_finish() != 0) {//??????20???????????????
                                SharedPreferencesUtil.saveBooleanData(mContext, "20Choujiang_finish", true);
                            }
                        } else {
                            showNoHasCountDialog();

                        }
                    }


                } else {//????????????
                    clickStart();
                }


            }
        });


        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();


    }


    //??????????????????????????????
    private void queryZJmoneyAndRotate() {
        zhongJiangStatusQueryEd = false;
        HashMap<String, String> map = new HashMap<>();
        map.put("data", choujiangCount + "");
        YConn.httpPost(mContext, YUrl.QUERY_TIQIAN_TXCJ_MONEY, map, new HttpListener<TXCJzhongjiangData>() {
            @Override
            public void onSuccess(TXCJzhongjiangData txcJzhongjiangData) {
                zhongJiangStatusQueryEd = true;
                choujiangCount = txcJzhongjiangData.getResidual_num();
                free_url = txcJzhongjiangData.getFree_url() + "";
                redPacketValue = txcJzhongjiangData.getRaffle_money();
                mTXCJzhongjiangData = txcJzhongjiangData;
                redPacketValue_totaldata = txcJzhongjiangData.getAll_money();
                wheelSurfView.startRotate(getPosition());

            }

            @Override
            public void onError() {
                zhongJiangStatusQueryEd = true;

            }
        });


    }


    //??????????????????
    private void queryCount(final boolean isFirstQuery) {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", type + "");
        YConn.httpPost(mContext, YUrl.QUERY_NEW_CJTX_COUNT, map, new HttpListener<CJTXcountData>() {
            @Override
            public void onSuccess(CJTXcountData cjtXcountData) {
                isVirtual = false;
                mCjtXcountData = cjtXcountData;
                if (mCjtXcountData.getIs_finish() == 1 && is_finishCome != 1) {
                    is_finishCome = 1;
                }

                userIsVip = CommonUtils.isVip(cjtXcountData.getIsVip(), cjtXcountData.getMaxType());

                totalchoujiangCount = mCjtXcountData.getData();
                dayall_count = mCjtXcountData.getAll_count();
                redPacketValue_totaldata = mCjtXcountData.getAll_money() + "";
                choujiangCount = mCjtXcountData.getData();

                //?????????
//                mCjtXcountData.setData(2);

//                if (isFromWallet && Double.parseDouble(redPacketValue_totaldata) > 0 && cjtXcountData.getReRoundCount() == 0) {//?????????????????????
//                    isFromWallet = false;
//                    showFromWallet();
//                    return;
//                }


                if (choujiangCount > 0) {

                    //?????????????????????????????????
                    if (isFromNewWallet) {
                        queryZJmoneyAndRotate();
                        return;
                    }


                    if (fromFreeBuy || fromPT) {
                        showStartDialog();

                        return;
                    }
                    UserInfo userInfo = YCache.getCacheUser(mContext);
                    boolean qian20_fist_choujiang_tishi_show_ed = SharedPreferencesUtil.getBooleanData(mContext, "qian20_fist_choujiang_tishi_show_ed", false);

                    if (cjtXcountData.getReRoundCount() <= 0) {//???????????????0
                        if (cjtXcountData.getIs_finish() == 0) {//?????????20??????????????????????????????
                            showStartDialog();
                        } else {//?????????????????????????????????????????????????????????
                            if (isFirstQuery) {
                                //???????????????20???????????????????????????????????????????????????????????????
                                if (userInfo.getReviewers() != 1 && !qian20_fist_choujiang_tishi_show_ed) {
                                    SharedPreferencesUtil.saveBooleanData(mContext, "qian20_fist_choujiang_tishi_show_ed", true);
                                    showFirstQaian20Dialog();
                                }else{
                                    ToastUtil.showMyToast(mContext, "????????????????????????????????????????????????????????????", 4000);

                                }
                            } else {
                                queryZJmoneyAndRotate();
                            }
                        }
                    } else { //??????????????????0?????????????????????????????????????????????
                        if (isFirstQuery && userInfo.getReviewers() != 1 && !qian20_fist_choujiang_tishi_show_ed) {
                            SharedPreferencesUtil.saveBooleanData(mContext, "qian20_fist_choujiang_tishi_show_ed", true);
                            showFirstQaian20Dialog();
                        }else{
                            if (isFirstQuery) {
                                ToastUtil.showMyToast(mContext, "????????????????????????????????????????????????????????????", 4000);
                            } else {
                                queryZJmoneyAndRotate();
                            }
                        }


                    }

                } else {
//                    if (buyTXKsuc) {
//                        return;
//                    }


//                    boolean mIsVip = CommonUtils.isVip(mCjtXcountData.getIsVip(), mCjtXcountData.getMaxType());
//
//                    if (mIsVip) {
//                        showBuyVipSucCloseChoujiangDialog();
//                        return;
//                    }


                    if (mCjtXcountData.getAll_money() > 0) {
                        coujizngCountUseUpdialog();//???????????????????????????????????????
                    } else {
                        showNoHasCountDialog();//??????????????????????????????????????????

                    }
                }


//                if (choujiangCount > 0) {
//                    choujiangCount = mCjtXcountData.getData();
//                    showStartDialog();
//                } else {
//                    if (buyTXKsuc) {
//                        return;
//                    }
//                    if (mCjtXcountData.getAll_money() > 0) {
//                        coujizngCountUseUpdialog();//???????????????????????????????????????
//                    } else {
//                        showNoHasCountDialog();//??????????????????????????????????????????
//
//                    }
//
//                }

            }


            @Override
            public void onError() {

            }
        });


    }


    private void showNoHasCountDialog() { //?????????????????????????????????

        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }

        noCountToPage();


//        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
//        View view = View.inflate(mContext, R.layout.dialog_txcj_count, null);
//
//        TextView tv1 = view.findViewById(R.id.tv1);
//        if (mCjtXcountData.getTixian_count() == 1) {
//            tv1.setText(Html.fromHtml("???????????????????????????????????????<strong><font color='#FDCC21'>" + 50 + "???</font></strong>???????????????"));
//        } else {
//            tv1.setText(Html.fromHtml("???????????????????????????????????????<strong><font color='#FDCC21'>" + 50 + "???</font></strong>???????????????"));
//        }
//
//
//        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//            }
//        });
//        view.findViewById(R.id.tv_tx).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//            }
//        });
//
//
//        TextView tv_tx = view.findViewById(R.id.tv_tx);
//        tv_tx.setText("????????????");
//        tv_tx.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//                //????????????????????????
//                if (CommonUtils.isVip(mCjtXcountData.getIsVip(), mCjtXcountData.getMaxType())
//                        || mCjtXcountData.getTixian_twoCount() == 1) {
//                    showVipBackDialog();//??????????????????
//                } else { //???????????????????????????
//                    showTXKdialog();
//                }
//            }
//        });
//
//
//        // // ?????????????????????dialog
//        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
//                LinearLayout.LayoutParams.MATCH_PARENT));
//        mDialog.setCancelable(false);
//        mDialog.show();

    }

//    private void showTXKdialog() {
//        if (null != mDialog) {
//            mDialog.dismiss();
//        }
//
//
//        //20??????????????????????????????????????????20??????????????????--?????????
//        if (mCjtXcountData.getNew_raffle_skipSwitch() == 0
//                && mCjtXcountData.getIs_finish() == 1
//                && choujiangCount <= 0
//                && mCjtXcountData.getReRoundCount() == 0) {
//            if (null != CommonActivity.instance) {
//                CommonActivity.instance.finish();
//            }
//
//            SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
//            Intent intent = new Intent(mContext, CommonActivity.class);
//            intent.putExtra("20choujiang_complete", true);
//            startActivity(intent);
//            finish();
//            return;
//        }
//
//        //???????????????????????????????????????(??????????????????1????????????)
//        if (mCjtXcountData.getTrial_hidden_switch() == 0 || mCjtXcountData.getTixian_count() == 1) {
//            if (null != CommonActivity.instance) {
//                CommonActivity.instance.finish();
//            }
//
//            SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
//            Intent intent = new Intent(mContext, CommonActivity.class);
//            intent.putExtra("isHiddenTXK", true);
//            startActivity(intent);
//            finish();
//            return;
//        }
//
//
//        //????????????????????????
//        if (CommonUtils.isVip(mCjtXcountData.getIsVip(), mCjtXcountData.getMaxType())
//                || mCjtXcountData.getTixian_twoCount() == 1) {
//            showVipBackDialog();//??????????????????
//
//            return;
//        }
//
//
//        txkRecLen = 30 * 60 * 1000;
//
//        LayoutInflater mInflater = LayoutInflater.from(mContext);
//        mDialog = new Dialog(mContext, R.style.invate_dialog_style);
//        View view = mInflater.inflate(R.layout.dialog_tx_choujiang_txk, null);
//        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (tkxTimer != null) {
//                    tkxTimer.cancel();
//                }
//                mDialog.dismiss();
//                showVipBackDialog();
//            }
//        });
//
//
//        ImageView iv_hongbao_bg = view.findViewById(R.id.iv_hongbao_bg);
////        if (mCjtXcountData.getTixian_count() != 1) {
////            iv_hongbao_bg.setImageResource(R.drawable.firstguidetixian_moneycoupon);
////        }
//
//
//        iv_hongbao_bg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (tkxTimer != null) {
//                    tkxTimer.cancel();
//                }
////                mDialog.dismiss();
//
//                startActivityForResult(new Intent(SignDrawalLimitActivity.this, MyVipListActivity.class)
//                                .putExtra("guide_txk", true)
//                        , 1001);
//
//
//            }
//        });
//
//
//        tv_txk_countdown = view.findViewById(R.id.tv_txk_countdown);
//        if (tkxTimer != null) {
//            tkxTimer.cancel();
//        }
//        tkxTimer = new Timer();
//        tkxTimer.schedule(new MyTimerTask(), 0, 1000);
//        mDialog.setCanceledOnTouchOutside(false);
//        mDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT));
//        ToastUtil.showDialog(mDialog);
//    }

    private void showVipBackDialog() {
        txkRecLen = 30 * 60 * 1000;
        if (null != mDialog) {
            mDialog.dismiss();
        }

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        mDialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_tx_choujiang_vip_back, null);
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tkxTimer != null) {
                    tkxTimer.cancel();
                }
                mDialog.dismiss();
            }
        });


        RelativeLayout rl_bg = view.findViewById(R.id.rl_bg);


        rl_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tkxTimer != null) {
                    tkxTimer.cancel();
                }

                String shareText = "\uD83D\uDC47??????????????????90??????????????????";
//                String wxMiniPathdUO = "/pages/sign/sign?isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();
                String wxMiniPathdUO = "/pages/mine/toexamine_test/toexamine_test?isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id() + "&showSignPage=true";


                String shareMIniAPPimgPic = YUrl.imgurl + "small-iconImages/heboImg/taskraward_shareImg.png";


                //????????????????????????????????????
                WXminiAPPShareUtil.shareToWXminiAPP(mContext, shareMIniAPPimgPic, shareText, wxMiniPathdUO, false);
                WXEntryActivity.setWXminiShareListener(new WXEntryActivity.WXminiAPPshareListener() {
                    @Override
                    public void wxMiniShareSuccess() {
                        ToastUtil.showMyToast(mContext, "???????????????????????????????????????????????????????????????", 4000);

                    }
                });


            }
        });


        tv_txk_countdown = view.findViewById(R.id.tv_txk_countdown);
        if (tkxTimer != null) {
            tkxTimer.cancel();
        }
        tkxTimer = new Timer();
        tkxTimer.schedule(new MyTimerTask(), 0, 1000);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ToastUtil.showDialog(mDialog);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1001://????????????????????????

                if (resultCode == BUG_TXK_SUCCESS) {
                    queryCount(false);
                } else {
                    showVipBackDialog();
                }

                break;
            case 1002://????????????
                if (resultCode == LOGIN_SUCCESS && isVirtual) {

                    if (null != countDownTimer) {
                        countDownTimer.cancel();
                    }

                    if (null != mDialog) {
                        mDialog.dismiss();
                    }
                    if (GuideActivity.needFengKong) {
                        ToastUtil.showMyToast(mContext, "?????????????????????????????????????????????", 3000);
                        finish();
                        return;
                    }
                    syncVirtualData();
                }
                break;

            case 1003://???????????????????????????
//                if (resultCode == BUG_VIP_SUCCESS) {

//                    YConn.httpPost(mContext, YUrl.FIRST_ZUANSHI_ZHUANPAIN_TISHI,
//                            new HashMap<String, String>(), new HttpListener<BaseData>() {
//                                @Override
//                                public void onSuccess(BaseData result) {
//
//                                    if (result.getIsPopup() == 1) {//????????????
//                                        showBuyVipSucDialog(result);
//                                        return;
//                                    }
//
//                                    if (result.getIsPopup() == 2) {//????????????
//                                        showBuyVipSucDialogHG(result);
//                                        return;
//                                    }
//                                    queryCount(false, true);
//
//
//                                }
//
//                                @Override
//                                public void onError() {
//
//                                }
//                            });


//                }

                break;


        }


    }

    private void showBuyVipSucDialog(BaseData baseData) {

        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.dialog_txcj_count_use_up, null);

        TextView tv_money = view.findViewById(R.id.tv_money);
        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView bt1 = view.findViewById(R.id.bt1);
        LinearLayout bt2 = view.findViewById(R.id.ll_bt2);
        ImageView icon_close = view.findViewById(R.id.icon_close);

        tv_money.setText("????????????");
        tv_money.getPaint().setFakeBoldText(false);
        bt2.setVisibility(View.GONE);
        bt1.setText("????????????");


        Spanned sd1;
//        sd1 = Html.fromHtml("????????????<font color='#FDCC21'><strong>"
//                + "????????????169???</strong></font>??????????????????????????????????????????????????????????????????<font color='#FDCC21'><strong>"
//                + baseData.getUnVipRaffleMoney() + "????????????</strong></font>??????<font color='#FDCC21'><strong>" + baseData.getVip_price() + "</strong></font>??????????????????"
//        );

        sd1 = Html.fromHtml("????????????????????????????????????<font color='#FDCC21'><strong>" + baseData.getUnVipRaffleMoney() + "??????????????????</strong></font>??????????????????????????????");

        tv1.setText(sd1);


//        Spanned sd2;
//        sd2 = Html.fromHtml("?????????????????????????????????<font color='#FDCC21'><strong>"
//                + "?????????15???????????????</strong></font>?????????????????????????????????"
//        );
//        tv2.setText(sd2);
        tv2.setVisibility(View.GONE);


        bt1.setOnClickListener(new OnClickListener() {//????????????--?????????????????????????????? ???????????????
            @Override
            public void onClick(View view) {
                mDialog.dismiss();

                HashMap<String, String> map = new HashMap<>();
                map.put("type", type + "");
                YConn.httpPost(mContext, YUrl.QUERY_NEW_CJTX_COUNT, map, new HttpListener<CJTXcountData>() {
                    @Override
                    public void onSuccess(CJTXcountData cjtXcountData) {
                        isVirtual = false;
                        mCjtXcountData = cjtXcountData;
                        totalchoujiangCount = mCjtXcountData.getData();
                        dayall_count = mCjtXcountData.getAll_count();
                        redPacketValue_totaldata = mCjtXcountData.getAll_money() + "";
                        choujiangCount = mCjtXcountData.getData();

                        if (choujiangCount > 0) { //??????????????????????????????
                            queryZJmoneyAndRotate();
                        } else { //????????????????????? ?????????????????????
                            showBuyVipSucCloseChoujiangDialog();
                        }
                    }


                    @Override
                    public void onError() {

                    }
                });

            }
        });


        icon_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();

            }
        });


        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();


    }


    private void showBuyVipSucDialogHG(BaseData baseData) {

        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.dialog_txcj_count_use_up, null);

        TextView tv_money = view.findViewById(R.id.tv_money);
        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView bt1 = view.findViewById(R.id.bt1);
        LinearLayout bt2 = view.findViewById(R.id.ll_bt2);
        ImageView icon_close = view.findViewById(R.id.icon_close);

        tv_money.setText("????????????");
        tv_money.getPaint().setFakeBoldText(false);
        bt2.setVisibility(View.GONE);
        bt1.setText("????????????");


        tv1.setText("????????????????????????????????????????????????????????????????????????");


        tv2.setVisibility(View.GONE);


        bt1.setOnClickListener(new OnClickListener() {//????????????--?????????????????????????????? ???????????????
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                startActivity(new Intent(mContext, WithdrawalLimitActivity.class));
                finish();

            }
        });


        icon_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();

            }
        });


        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();


    }


    private void showFirstQaian20Dialog() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.dialog_one_pic, null);
        view.findViewById(R.id.root_view).setOnClickListener(new OnClickListener() {//????????????
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                clickStart();
            }
        });
        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mDialog.setCancelable(false);
        mDialog.show();
    }

    private void showBuyVipSucCloseChoujiangDialog() {
        if (null != mDialog) {
            mDialog.dismiss();
        }

        noCountToPage();


//
//        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
//        View view = View.inflate(mContext, R.layout.dialog_txcj_count_use_up, null);
//
//        TextView tv_money = view.findViewById(R.id.tv_money);
//        TextView tv1 = view.findViewById(R.id.tv1);
//        TextView tv2 = view.findViewById(R.id.tv2);
//        TextView bt1 = view.findViewById(R.id.bt1);
//        LinearLayout bt2 = view.findViewById(R.id.ll_bt2);
//        ImageView icon_close = view.findViewById(R.id.icon_close);
//
////        tv_money.setText(DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getRaffle_money()) + "???");
////        tv_money.getPaint().setFakeBoldText(false);
//
//        tv_money.setText("????????????");
//        tv_money.setTextSize(18);
//
//        bt2.setVisibility(View.GONE);
//        bt1.setText("??????");
//
//
//        Spanned sd1;
//        sd1 = Html.fromHtml("?????????????????????????????????<font color='#FDCC21'><strong>"
//                + "?????????????????????</strong></font>???????????????<font color='#FDCC21'><strong>"
//                + "????????????</strong></font>??????????????????"
//        );
//
//
//        tv1.setText(sd1);
//
//
////        Spanned sd2;
////        sd2 = Html.fromHtml("?????????????????????????????????<font color='#FDCC21'><strong>"
////                + "?????????15???????????????</strong></font>?????????????????????????????????"
////        );
////        tv2.setText(sd2);
//        tv2.setVisibility(View.GONE);
//
//
//        bt1.setOnClickListener(new OnClickListener() {//????????????--?????????????????????????????? ???????????????
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//                finish();
//
//            }
//        });
//
//
//        icon_close.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//                finish();
//
//            }
//        });
//
//
//        // // ?????????????????????dialog
//        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
//                LinearLayout.LayoutParams.MATCH_PARENT));
//        mDialog.setCancelable(false);
//        mDialog.show();


    }


    private void showStartDialog() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.dialog_txcj_start_count_tishi, null);

        TextView tv1 = view.findViewById(R.id.tv1);
        choujiangCount = mCjtXcountData.getData();

        if (fromFreeBuy) {
            tv1.setText(Html.fromHtml("??????<font color='#FDCC21'><strong>"
                    + "????????????????????????"
                    + "</strong></font>??????<font color='#FDCC21'><strong>"
                    + "????????????????????????</strong></font>?????????????????????<font color='#FDCC21'><strong>?????????????????????????????????</strong></font>"
                    + "????????????<font color='#FDCC21'><strong>????????????</strong></font>????????????"

            ));


        } else if (fromPT) {
            tv1.setText(Html.fromHtml("??????<font color='#FDCC21'><strong>"
                    + "?????????????????????"
                    + "</strong></font>????????????<font color='#FDCC21'><strong>????????????????????????</strong></font>?????????????????????<font color='#FDCC21'><strong>?????????????????????????????????</strong></font>"
                    + "????????????<font color='#FDCC21'><strong>????????????</strong></font>????????????"
            ));

        } else {
            tv1.setText(Html.fromHtml("??????<font color='#FDCC21'><strong>"
                    + choujiangCount
                    + "???</strong></font>?????????????????????<font color='#FDCC21'><strong>??????????????????</strong></font>????????????????????????"));
        }


        view.findViewById(R.id.tv_tx).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                queryZJmoneyAndRotate();

            }
        });

        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });


        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();
        fromFreeBuy = false;
        fromPT = false;
    }

    //????????????
    private void clickStart() {//???????????????
        if (null == mCjtXcountData) {
            ToastUtil.showShortText2("?????????");
        }
        if (choujiangCount > 0) {//????????????????????????
            queryZJmoneyAndRotate();
        } else {
            showNoHasCountDialog();
        }

    }

    private void roteEnd(int position) {
        openRedPacket();
    }


    /**
     * ??????????????????(????????????)
     */
    private void openRedPacket() {
        if (null != mDialog) {
            mDialog.dismiss();
        }

        final boolean[] chaiClick = {false};

        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.sign_withdrawal_limit_open_redpacket, null);
        final ImageView open_red_packet = (ImageView) view.findViewById(R.id.open_red_packet);
        TextView open_tv = (TextView) view.findViewById(R.id.open_tv);
        open_tv.setText("???????????????????????????????????????");
        open_red_packet.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (chaiClick[0]) {
                    return;
                }
                chaiClick[0] = true;
                ObjectAnimator
                        .ofFloat(open_red_packet, "rotationY", 0.0F, -790.0F)//
                        .setDuration(1600)//
                        .start();
                open_red_packet.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isVirtual) {
                            mDialog.dismiss();

                            //?????????????????????????????????????????????????????????
//                            redPacketOpenedVirtual();

                            if (virtualZJdata.getNewRaffle_type() == 1) {//???????????????
                                redPacketOpenedUnLoginReal();
                            } else {
                                showAnimNumDialog(true);
                            }

                        } else {

                            //????????????????????????????????????tanc
                            if (awardIndex == 2) {
                                mDialog.dismiss();

                                showGetFreeBuyDialog();
                                return;
                            }

                            //?????????????????????????????????????????????
                            if (mTXCJzhongjiangData.getExtract_money() > 0) {
                                YConn.httpPost(mContext, YUrl.QUERY_TIQIAN_TXCJ_MONEY_FAFANG, new HashMap<String, String>(), new HttpListener<BaseDataBean>() {
                                    @Override
                                    public void onSuccess(BaseDataBean result) {
                                        mDialog.dismiss();
                                        redPacketOpenedReal();
                                    }

                                    @Override
                                    public void onError() {


                                    }
                                });

                            } else {
                                mDialog.dismiss();
                                redPacketOpenedReal();
                            }


                        }
                    }
                }, 1700);
            }
        });
        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();
    }


    private void redPacketOpenedUnLoginReal() {//??????????????????
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.dialog_withdraw_wx_daozhang_real, null);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv4 = view.findViewById(R.id.tv4);
        TextView tv_tx = view.findViewById(R.id.tv_tx);
        tv4.setVisibility(View.VISIBLE);
        tv_tx.setText("????????????????????????");

        tv1.setText(DateUtils.dePoint("#0.00", redPacketValue * 5) + "???");
        tv3.setText("?? " + DateUtils.dePoint("#0.00", redPacketValue));


        double total_extract_money = 0;//?????????????????????
        double surplus_extract_money = 0;//??????????????????
        total_extract_money = virtualZJdata.getRaffle_money() * 5;
        surplus_extract_money = (5 - 1) * virtualZJdata.getRaffle_money();

        Spanned tv2Str = Html.fromHtml("??????????????????<font color='#FDCC21'>" + DateUtils.dePoint("#0.00", total_extract_money) +
                "</font>????????????<font color='#FDCC21'><strong>" + DateUtils.dePoint("#0.00", virtualZJdata.getRaffle_money()) +
                "</strong></font>???<font color='#FDCC21'><strong>???????????????????????????</strong></font>??????????????????");

        tv2.setText(tv2Str);

        Spanned tv4Str = Html.fromHtml("??????<font color='#FDCC21'><strong>" + DateUtils.dePoint("#0.00", surplus_extract_money) +
                "</strong></font>?????????" + "<font color='#FDCC21'>" + 7 + "</font>??????????????????????????????????????????");

        tv4.setText(tv4Str);


        view.findViewById(R.id.tv_tx).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {//????????????

                //?????????
                if (LoginActivity.instances != null) {
                    LoginActivity.instances.finish();
                }
                if (null != countDownTimer) {
                    countDownTimer.cancel();
                }
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.putExtra("login_register", "login");
                intent.putExtra("isVirtual", true);
                startActivityForResult(intent, 1002);


            }
        });


        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();
    }


    private void redPacketOpenedReal() {//??????????????????
        if (null != mDialog) {
            mDialog.dismiss();
        }

        if (mTXCJzhongjiangData.getIsYJin() == 1) {
            redPacketOpenedRealYJ();
            return;
        }


        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.dialog_withdraw_wx_daozhang_real, null);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv_tx = view.findViewById(R.id.tv_tx);
        TextView tv4 = view.findViewById(R.id.tv4);


        tv1.setText(DateUtils.dePoint("#0.00", redPacketValue) + "???");

        if ((mCjtXcountData.getIsVip() == 0 || mCjtXcountData.getIsVip() == 3) && mTXCJzhongjiangData.getExtract_money() > 0) {
            tv3.setText("?? " + DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getExtract_money()));
        } else {
            tv3.setText("?? " + DateUtils.dePoint("#0.00", redPacketValue));

        }


        double total_extract_money = 0;//?????????????????????
        double surplus_extract_money = 0;//??????????????????
        if (mTXCJzhongjiangData.getMultiple() >= 1) {
//            total_extract_money = mTXCJzhongjiangData.getExtract_money() * mTXCJzhongjiangData.getMultiple();
//            surplus_extract_money = (mTXCJzhongjiangData.getMultiple() - 1) * mTXCJzhongjiangData.getExtract_money();

            total_extract_money = mTXCJzhongjiangData.getRaffle_money() * mTXCJzhongjiangData.getMultiple();
            surplus_extract_money = (mTXCJzhongjiangData.getMultiple() - 1) * mTXCJzhongjiangData.getRaffle_money();
        }

        Spanned tv2Str = new SpannableString("??????");


        if ((mCjtXcountData.getIsVip() == 0 || mCjtXcountData.getIsVip() == 3) && mTXCJzhongjiangData.getExtract_money() > 0 && mTXCJzhongjiangData.getMultiple() >= 1) {

            //tv4????????????
            if (mTXCJzhongjiangData.getExtract_money() > 0 && mTXCJzhongjiangData.getExtract_money() < 1) {

                tv2Str = Html.fromHtml("??????????????????<font color='#FDCC21'>" + DateUtils.dePoint("#0.00", total_extract_money) +
                        "</font>????????????<font color='#FDCC21'><strong>" + DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getExtract_money()) +
                        "</strong></font>??????????????????????????????");
                tv_tx.setText("????????????");


                Spanned tv4Str = Html.fromHtml("??????<font color='#FDCC21'><strong>" + DateUtils.dePoint("#0.00", surplus_extract_money) +
                        "</strong></font>?????????" + "<font color='#FDCC21'>" + mTXCJzhongjiangData.getDay() + "</font>??????????????????????????????????????????");
                tv4.setText(tv4Str);
                tv4.setVisibility(View.VISIBLE);

            } else {

                tv2Str = Html.fromHtml("??????????????????<font color='#FDCC21'>" + DateUtils.dePoint("#0.00", total_extract_money) +
                        "</font>????????????<font color='#FDCC21'><strong>" + DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getExtract_money()) +
                        "</strong></font>????????????????????????????????????????????????");

                Spanned tv4Str = Html.fromHtml("??????<font color='#FDCC21'><strong>" + DateUtils.dePoint("#0.00", surplus_extract_money) +
                        "</strong></font>?????????" + "<font color='#FDCC21'>" + mTXCJzhongjiangData.getDay() + "</font>??????????????????????????????????????????");
                tv4.setText(tv4Str);
                tv4.setVisibility(View.VISIBLE);

            }


        } else if (mTXCJzhongjiangData.getLottery_kfMoney() > 0) {
            showAnimNumDialog(false);
            return;

        } else {
            if (redPacketValue >= 1) {

                if (mTXCJzhongjiangData.getExtract_money() > 0) { //???????????????
                    tv2Str = Html.fromHtml("??????????????????<font color='#FDCC21'>" + DateUtils.dePoint("#0.00", redPacketValue) +
                            "</font>?????????????????????????????????????????????????????????");

                } else {
                    tv2Str = Html.fromHtml("????????????<font color='#FDCC21'>" + DateUtils.dePoint("#0.00", redPacketValue) +
                            "</font>???????????????????????????????????????????????????????????????");

                    tv_tx.setText("????????????");


                }

            } else {
                tv2Str = Html.fromHtml("??????????????????<font color='#FDCC21'>" + DateUtils.dePoint("#0.00", redPacketValue) +
                        "</font>?????????????????????????????????1?????????????????????1?????????????????????????????????????????????????????????");
            }
        }

        if (isFromNewWallet && mTXCJzhongjiangData.getExtract_money() > 0) {//??????????????????????????????????????????????????????(?????????????????????)
            tv2Str = Html.fromHtml("??????????????????<font color='#FDCC21'>" + DateUtils.dePoint("#0.00", redPacketValue) +
                    "</font>??????????????????????????????????????????????????????<font color='#FDCC21'>" + showNewWalletTXallMoney +
                    "</font>??????");
            tv4.setText("????????????????????????????????????????????????????????????????????????");
            tv4.setVisibility(View.VISIBLE);

        }


        //?????????????????? ?????????????????????
        if (mTXCJzhongjiangData.getExtract_money() > 0) {

            if (isFromNewWallet) { //?????????????????????????????????????????????
                tv1.setText(showNewWalletTXallMoney + "???");
            } else {
                double titleMoneystr = mTXCJzhongjiangData.getRaffle_money() * mTXCJzhongjiangData.getMultiple();
                tv1.setText(DateUtils.dePoint("#0.00", titleMoneystr) + "???");

            }

        }

        tv2.setText(tv2Str);


        view.findViewById(R.id.tv_tx).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {//????????????
                mDialog.dismiss();


                if (mTXCJzhongjiangData.getExtract_money() > 0 && mTXCJzhongjiangData.getExtract_money() < 1) {


//                    tv_bottom.setText("??????????????????????????????????????????" + redPacketValue + "?????????1?????????????????????1?????????????????????????????????????????????????????????");
//                    tv_bottom.setVisibility(View.VISIBLE);

                    //??????????????????1????????????
                    FaFangLessThan1yuan(false);


                } else {
//                    tv_bottom.setVisibility(View.GONE);


                    if (isFromNewWallet && mTXCJzhongjiangData.getExtract_money() >= 0) {
                        isFromNewWallet = false;
                        Intent intent;
                        if (CommonUtils.isVip(mCjtXcountData.getIsVip(), mCjtXcountData.getMaxType())) {
                            if (mCjtXcountData.getMaxType() == 5 || mCjtXcountData.getMaxType() == 6) {
                                intent = new Intent(mContext, WithdrawalLimitActivity.class);
                            } else {
                                intent = new Intent(mContext, MyYJactivity.class);

                            }
                            startActivity(intent);
                            ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                            finish();
                            return;
                        }
                    }


                    continueTXfromDialogClick();//????????????startLuckBtn


                }


            }
        });


        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();
    }


    private void redPacketOpenedRealYJ() {//????????????????????????
        if (null != mDialog) {
            mDialog.dismiss();
        }


        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.dialog_withdraw_wx_daozhang_real_yj, null);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv_time = view.findViewById(R.id.tv_time);
        TextView tv_tx = view.findViewById(R.id.tv_tx);//????????????
        TextView tv_tx2 = view.findViewById(R.id.tv_tx2);//????????????


//        tv1.setText(DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getYj_money()) + "???");
        tv1.setText(mTXCJzhongjiangData.getYj_money() + "???");


//        if ((mCjtXcountData.getIsVip() == 0 || mCjtXcountData.getIsVip() == 3) && mTXCJzhongjiangData.getExtract_money() > 0) {
//            tv3.setText("?? " + DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getExtract_money()));
//        } else {
//        tv3.setText("?? " + DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getYj_money()));

        tv3.setText("?? " + mTXCJzhongjiangData.getYj_money());


//        }

        Spanned tv2Str = new SpannableString("??????");

        tv2Str = Html.fromHtml("????????????<font color='#FDCC21'><strong>" + mTXCJzhongjiangData.getYj_money() +
                "</strong></font>?????????????????????<font color='#FDCC21'><strong>?????????????????????</strong></font>???");

        tv2.setText(tv2Str);


        long countTime = 9 * 60 * 1000;
        if (null != mTXCJzhongjiangData) {
            countTime = mTXCJzhongjiangData.getExpireTime();
        }
        //????????????????????????????????????????????????----???????????????
        countDownTimer = new SimpleCountDownTimer(countTime, tv_time, true, mTXCJzhongjiangData.getYj_money() + "???").setOnFinishListener(new SimpleCountDownTimer.OnFinishListener() {
            @Override
            public void onFinish() {
                if (null != mDialog) {
                    mDialog.dismiss();
                }
            }
        });
        countDownTimer.start();

        view.findViewById(R.id.tv_tx).setOnClickListener(new OnClickListener() {//????????????
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MyYJactivity.class);
                startActivity(intent);


            }
        });

        view.findViewById(R.id.tv_tx2).

                setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {//????????????
                        mDialog.dismiss();
                        continueTXfromDialogClick();//????????????startLuckBtn
                    }
                });


        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();
    }


    private void FaFangLessThan1yuan(final boolean virtualFinish) {

        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.dialog_txcj_count_use_up, null);

        TextView tv_money = view.findViewById(R.id.tv_money);
        TextView tv1 = view.findViewById(R.id.tv1);
        TextView bt1 = view.findViewById(R.id.bt1);
        LinearLayout bt2 = view.findViewById(R.id.ll_bt2);
        ImageView icon_close = view.findViewById(R.id.icon_close);

        if (virtualFinish) {
            tv_money.setText(redPacketValue * 5 + "???");

        } else {
            double titleMoneystr = mTXCJzhongjiangData.getRaffle_money() * mTXCJzhongjiangData.getMultiple();
            tv_money.setText(DateUtils.dePoint("#0.00", titleMoneystr) + "???");

        }


        bt2.setVisibility(View.GONE);
        bt1.setText("????????????");


        Spanned sd1;
        if (virtualFinish) {


            sd1 = Html.fromHtml("??????????????????????????????<font color='#FDCC21'>"
                    + 1 + "</font>????????????????????????????????????????????????<font color='#FDCC21'><strong>"
                    + redPacketValue + "</strong></font>?????????????????????????????????????????????<font color='#FDCC21'><strong>"
                    + "???1?????????????????????????????????" + "</strong></font>?????????????????????"
            );

        } else {
            sd1 = Html.fromHtml("??????????????????????????????<font color='#FDCC21'>"
                    + 1 + "</font>????????????????????????????????????????????????<font color='#FDCC21'><strong>"
                    + mTXCJzhongjiangData.getExtract_money() + "</strong></font>?????????????????????????????????????????????<font color='#FDCC21'><strong>"
                    + "???1?????????????????????????????????" + "</strong></font>?????????????????????"
            );

        }

        tv1.setText(sd1);


        bt1.setOnClickListener(new OnClickListener() {//????????????
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                if (virtualFinish) {
                    //??????????????????
                    queryCount(false);
                } else {
                    continueTXfromDialogClick();//????????????startLuckBtn

                }


            }
        });


        icon_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });


        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();

    }


    //??????????????????????????????
    private void continueTXfromDialogClick() {

        if (choujiangCount <= 0) {//???????????????


            //??????????????????0????????????????????????????????????
            if (mCjtXcountData.getReRoundCount() > 0) {
                HashMap<String, String> map = new HashMap<>();
                map.put("type", type + "");
                YConn.httpPost(mContext, YUrl.QUERY_NEW_CJTX_COUNT, map, new HttpListener<CJTXcountData>() {
                    @Override
                    public void onSuccess(CJTXcountData cjtXcountData) {
                        isVirtual = false;
                        mCjtXcountData = cjtXcountData;
                        totalchoujiangCount = mCjtXcountData.getData();
                        dayall_count = mCjtXcountData.getAll_count();
                        redPacketValue_totaldata = mCjtXcountData.getAll_money() + "";
                        choujiangCount = mCjtXcountData.getData();

                        //????????????????????????????????????
                        clickStart();


                    }

                    @Override
                    public void onError() {

                    }
                });


            } else { // ????????????????????????

                noCountToPage();


//                boolean mIsVip = CommonUtils.isVip(mCjtXcountData.getIsVip(), mCjtXcountData.getMaxType());
//                if (mIsVip) {
//                    showBuyVipSucCloseChoujiangDialog();
//                    return;
//                }
//
//                if (Double.parseDouble(redPacketValue_totaldata) > 0) {
//                    coujizngCountUseUpdialog();
//                    if (mCjtXcountData.getIs_finish() != 0) {//????????????20?????????????????????
//                        SharedPreferencesUtil.saveBooleanData(mContext, "20Choujiang_finish", true);
//                    }
//                } else {
//                    showNoHasCountDialog();
//
//                }
            }


        } else {//????????????

            if (mTXCJzhongjiangData.getRaffle_money() >= 15) {
                virtualXJdialog();
            } else {
                clickStart();
            }


        }


//        clickStart();


    }

    //??????????????????--???????????????
    private void virtualXJdialog() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.dialog_txcj_virtual_zj_go_vip2, null);

        TextView tv_money = view.findViewById(R.id.tv_money);
        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView bt1 = view.findViewById(R.id.bt1);
        TextView bt2 = view.findViewById(R.id.bt2);

        TextView tv_time = view.findViewById(R.id.tv_time);
        LinearLayout ll_count_time = view.findViewById(R.id.ll_count_time);

        tv_money.setText(redPacketValue_totaldata + "???");

        Spanned sd = new SpannableString("??????");

        if (mCjtXcountData.getIs_finish() == 1) {

            sd = Html.fromHtml("??????????????????<font color='#FDCC21'><strong>"
                    + 15 + "</strong></font>??????<font color='#FDCC21'><strong>"
                    + "?????????????????????????????????</strong></font>???"
            );


        } else {
            sd = Html.fromHtml("??????????????????????????????<font color='#FDCC21'><strong>"
                    + "??????????????????????????????</strong></font>???"
            );
        }


//        }
        tv1.setText(sd);

        Spanned sd2 = new SpannableString("??????");

        if (mCjtXcountData.getIs_finish() == 1) {
            sd2 = Html.fromHtml("??????????????????15?????????????????????<font color='#FDCC21'><strong>"
                    + redPacketValue_totaldata + "</strong></font>???,<font color='#FDCC21'><strong>????????????</strong></font>??????<font color='#FDCC21'><strong>"
                    + "????????????" + "</strong></font>???");
        } else {
            sd2 = Html.fromHtml("??????????????????????????????<font color='#FDCC21'><strong>"
                    + redPacketValue_totaldata + "</strong></font>???,<font color='#FDCC21'><strong>????????????</strong></font>??????<font color='#FDCC21'><strong>"
                    + "????????????" + "</strong></font>???");
        }


//        }
        tv2.setText(sd2);


        long countTime = 9 * 60 * 1000;
        if (null != mTXCJzhongjiangData) {
            countTime = mTXCJzhongjiangData.getExpireTime();
        }
        countDownTimer = new SimpleCountDownTimer(countTime, tv_time).setOnFinishListener(new SimpleCountDownTimer.OnFinishListener() {
            @Override
            public void onFinish() {
                if (null != mDialog) {
                    mDialog.dismiss();
                }
            }
        });
        countDownTimer.start();


        if (choujiangCount <= 0) {
            bt2.setText("??????");
            yindaoVip = true;

        } else {
            bt2.setText("????????????");

        }


        bt1.setOnClickListener(new OnClickListener() {//????????????
            @Override
            public void onClick(View view) {
//                mDialog.dismiss();
                startActivityForResult(new Intent(SignDrawalLimitActivity.this, MyVipListActivity.class), 1003);

            }
        });


        bt2.setOnClickListener(new OnClickListener() {//????????????laststartLuckBtn
            @Override
            public void onClick(View view) {

                if (choujiangCount <= 0) {

                    if (Double.parseDouble(redPacketValue_totaldata) > 0) {
                        mDialog.dismiss();
                        coujizngCountUseUpdialog();


                    } else if (yindaoVip) {
                        finish();
                        SharedPreferencesUtil.saveBooleanData(mContext, "choujiang_not_count_back", false);

                    } else {
                        if (null != countDownTimer) {
                            countDownTimer.cancel();
                        }
                        mDialog.dismiss();
                        showNoHasCountDialog();
                    }


                } else {
                    if (null != countDownTimer) {
                        countDownTimer.cancel();
                    }
                    mDialog.dismiss();
                    clickStart();
                }


            }
        });


        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();

    }

    //?????????????????????dialog
    private void coujizngCountUseUpdialog() {//?????????????????? ????????????
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }

        noCountToPage();
        //?????????????????????????????????20??????????????????????????????????????????????????????
//        boolean mIsVip = CommonUtils.isVip(mCjtXcountData.getIsVip(), mCjtXcountData.getMaxType());
//
//        if ((!mIsVip && mCjtXcountData.getIs_finish() == 0) ||
//                (mIsVip && mCjtXcountData.getMaxType() == 4)
//        ) {
//
//            Intent intent = new Intent(mContext, WithdrawalLimitActivity.class);
//            startActivity(intent);
//            ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//            finish();
//
//            return;
//        }
//
//
//        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
//        View view = View.inflate(mContext, R.layout.dialog_txcj_count_use_up, null);
//
//        TextView tv_money = view.findViewById(R.id.tv_money);
//        TextView tv1 = view.findViewById(R.id.tv1);
//        TextView tv2 = view.findViewById(R.id.tv2);
//        TextView bt1 = view.findViewById(R.id.bt1);
//        TextView tv_time = view.findViewById(R.id.tv_time);
//        LinearLayout bt2 = view.findViewById(R.id.ll_bt2);
//        LinearLayout ll_count_time = view.findViewById(R.id.ll_count_time);
//        ImageView icon_close = view.findViewById(R.id.icon_close);
//        tv_money.setText(redPacketValue_totaldata + "???");
//
//        //??????
////        mCjtXcountData.setTixian_twoCount(1);
////        dayall_count = 20;
//
//        if (mCjtXcountData.getTixian_twoCount() == 1
//                && dayall_count >= 10
//                && (mCjtXcountData.getIsVip() == 0 || mCjtXcountData.getIsVip() == 3)) {
//
//            Spanned sd1;
//
//
////            sd1 = Html.fromHtml("???????????????<font color='#FDCC21'>"
////                    + 10 + "</font>?????????????????????????????????<font color='#FDCC21'>"
////                    + 40 + "</font>??????????????????????????????4???????????????10??????????????????????????????????????????????????????");
//
//            sd1 = Html.fromHtml("?????????<font color='#FDCC21'><strong>"
//
//
//                    + 50 + "</strong></font>???????????????<font color='#FDCC21'><strong>"
//
//                    + 10 + "</strong/></font>?????????????????????????????????<font color='#FDCC21'><strong>"
//
//
//                    + 40 + "</strong></font>????????????????????????4???????????????10??????????????????????????????"
//
//
//            );
//
//
//            tv1.setText(sd1);
//
//
//            Spanned sd2;
//            sd2 = Html.fromHtml("??????????????????<font color='#FDCC21'><strong>"
//                    + redPacketValue_totaldata + "</strong/></font>????????????<font color='#FDCC21'><strong>"
//                    + "79?????????" + "<strong/></font>?????????????????????????????????<font color='#FDCC21'><strong>"
//                    + "?????????????????????" + "</strong></font>???79?????????<font color='#FDCC21'><strong>"
//                    + "????????????" + "</strong></font>???"
//
//
//            );
//
//
//            tv2.setText(sd2);
//            tv2.setVisibility(View.VISIBLE);
//
//        } else {
//            Spanned sd;
//            sd = Html.fromHtml("<font color='#FDCC21'><strong>"
//                    + dayall_count + "</strong></font>???????????????????????????????????????<font color='#FDCC21'><strong>"
//
//                    + redPacketValue_totaldata + "</strong></font>????????????<font color='#FDCC21'><strong>"
//
//                    + "79?????????" + "</strong></font>???");
//
//            ll_count_time.setVisibility(View.VISIBLE);
//            long countTime = 0;
//            if (null != mTXCJzhongjiangData) {
//                countTime = mTXCJzhongjiangData.getExpireTime();
//            }
//            if (countTime <= 0) {
//                countTime = mCjtXcountData.getExpireTime();
//            }
//
//
//            countDownTimer = new SimpleCountDownTimer(countTime, tv_time).setOnFinishListener(new SimpleCountDownTimer.OnFinishListener() {
//                @Override
//                public void onFinish() {
//                    if (null != mDialog) {
//                        mDialog.dismiss();
//                    }
//                }
//            });
//            countDownTimer.start();
//
//            tv1.setText(sd);
//
//            Spanned sd2 = Html.fromHtml("??????????????????????????????<font color='#FDCC21'><strong>"
//
//                    + "????????????????????????" + "</strong></font>79?????????<font color='#FDCC21'><strong>"
//
//                    + "????????????" + "</strong></font>???");
//            tv2.setText(sd2);
//            tv2.setVisibility(View.VISIBLE);
//
//
//        }
//
//
//        //?????????????????????50????????????????????????10???
//        if (mCjtXcountData.getTixian_count() == 1) {
//            bt2.setBackgroundResource(R.drawable.give_fifty_tixian_coupon);
//        }
//
//
//        if (mCjtXcountData.getTixian_twoCount() != 1 || (mCjtXcountData.getIsVip() > 0 && mCjtXcountData.getIsVip() != 3)) {
//            bt2.setVisibility(View.VISIBLE);
//        } else {
//            bt2.setVisibility(View.GONE);
//
//        }
//
//
//        bt1.setOnClickListener(new OnClickListener() {//????????????
//            @Override
//            public void onClick(View view) {
//                startActivityForResult(new Intent(SignDrawalLimitActivity.this, MyVipListActivity.class), 1003);
//
//
//            }
//        });
//
//
//        bt2.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (null != countDownTimer) {
//                    countDownTimer.cancel();
//                }
//                mDialog.dismiss();
//                showTXKdialog();
//
//
//            }
//        });
//
//        icon_close.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (null != countDownTimer) {
//                    countDownTimer.cancel();
//                }
//                mDialog.dismiss();
//                showTXKdialog();
//            }
//        });
//
//
//        // // ?????????????????????dialog
//        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
//                LinearLayout.LayoutParams.MATCH_PARENT));
//        mDialog.setCancelable(false);
//        mDialog.show();

    }


//    private void showFromWallet() {
//        if (null != countDownTimer) {
//            countDownTimer.cancel();
//        }
//        if (null != mDialog) {
//            mDialog.dismiss();
//        }
//        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
//        View view = View.inflate(mContext, R.layout.dialog_txcj_count_use_up, null);
//
//        TextView tv_money = view.findViewById(R.id.tv_money);
//        TextView tv1 = view.findViewById(R.id.tv1);
//        TextView bt1 = view.findViewById(R.id.bt1);
//        LinearLayout bt2 = view.findViewById(R.id.ll_bt2);
//        ImageView icon_close = view.findViewById(R.id.icon_close);
//
//        //??????
////        mCjtXcountData.setTixian_count(1);
////        mCjtXcountData.setTixian_twoCount(1);
//
//
//        //?????????????????????50????????????????????????10???
//        if (mCjtXcountData.getTixian_count() == 1) {
//            bt2.setBackgroundResource(R.drawable.give_fifty_tixian_coupon);
//        }
//        //??????2???????????????????????????
//        if (mCjtXcountData.getTixian_twoCount() == 1) {
//            bt2.setVisibility(View.GONE);
//        }
//
//        tv_money.setText(redPacketValue_totaldata + "???");
//
//
//        Spanned sd;
//        sd = Html.fromHtml("??????????????????<font color='#FDCC21'>"
//                + redPacketValue_totaldata + "</font>???????????????????????????????????????????????????");
//
//
//        tv1.setText(sd);
//
//
//        bt1.setOnClickListener(new OnClickListener() {//????????????
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(SignDrawalLimitActivity.this, MyVipListActivity.class));
//
//            }
//        });
//
//
//        bt2.setOnClickListener(new OnClickListener() {//????????????laststartLuckBtn
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//                showTXKdialog();
//
//
//            }
//        });
//
//        icon_close.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//                showTXKdialog();
//            }
//        });
//
//
//        // // ?????????????????????dialog
//        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
//                LinearLayout.LayoutParams.MATCH_PARENT));
//        mDialog.setCancelable(false);
//        mDialog.show();
//
//    }


    private void syncVirtualData() {
        loading.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("money", redPacketValue + "");
        YConn.httpPost(mContext, YUrl.SYNC_CJ_DATA, map, new HttpListener<BaseDataBean>() {
            @Override
            public void onSuccess(BaseDataBean result) {
//                if (result.getStatus() == 1) {

//                loading.dismiss();
                //?????????
//                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
//                Intent intent = new Intent(mContext, CommonActivity.class);
//                startActivity(intent);
//                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//                finish();

//                }
                //??????????????????
//                queryCount(false, false);
//                FaFangLessThan1yuan(true);


                //????????????????????????
                YConn.httpPost(mContext, YUrl.FIRST_ZUANSHI_ZHUANPAIN_TISHI,
                        new HashMap<String, String>(), new HttpListener<BaseData>() {
                            @Override
                            public void onSuccess(BaseData result) {
                                loading.dismiss();

                                if (result.getIsPopup() == 1) {
                                    showBuyVipSucDialog(result);

                                } else {
                                    //??????????????????
                                    queryCount(false);

                                }

                            }

                            @Override
                            public void onError() {

                            }
                        });


                //??????????????????
//                queryCount(false, false);


            }

            @Override
            public void onError() {
                SharedPreferencesUtil.saveBooleanData(mContext, "choujiang_not_count_back", false);

                finish();

            }
        });

    }

    /**
     * ????????????  ????????????   ??????
     */
    private void initLimitAwardsList() {

        for (int i = 0; i < 50; i++) {
            addToLimitList();
        }

        if (fromFreeBuy || fromPT) { //??????3??????1???????????????


            ArrayList<String> sedLeim = new ArrayList<>();//??????????????????

            ArrayList<String> allSubList = new ArrayList<>();//??????????????????
            YDBHelper dbHelp = new YDBHelper(this);


            //?????????????????????


            String sql = "select * from sort_info where is_show = 1 order by _id";
            List<HashMap<String, String>> sed = dbHelp.query(sql);
            if (sed.size() > 0) {
                for (int i = 0; i < sed.size(); i++) {
                    HashMap<String, String> mMap = sed.get(i);
                    for (int j = 0; j < mMap.size(); j++) {
                        sedLeim.add(mMap.get("sort_name"));
                    }
                }
            }


            String sqlSub = "select * from supp_label where type = 1 order by _id";
            List<HashMap<String, String>> listSub = dbHelp.query(sqlSub);


            if (listSub.size() > 0) {
                for (int i = 0; i < listSub.size(); i++) {
                    HashMap<String, String> mMap = listSub.get(i);
                    for (int j = 0; j < mMap.size(); j++) {
                        allSubList.add(mMap.get("name"));
                    }
                }
            }


            for (int i = 0; i < mListData1.size(); i++) {
                if ((i + 1) % 4 == 0) {

                    HashMap<String, String> map1 = new HashMap<String, String>();
                    map1.put("nname", StringUtils.getVirtualName() + "***" + StringUtils.getVirtualName());
                    String ramSubName = allSubList.get((int) (Math.random() * allSubList.size()));////????????????
                    String ramLeim = sedLeim.get((int) (Math.random() * sedLeim.size()));//??????????????????
                    map1.put("p_name", "???????????????" + ramSubName + ramLeim);
                    map1.put("pic", "defaultcommentimage/" + StringUtils.getDefaultImg());

                    //1-500?????????
                    String ram500 = StringUtils.getRandomInt(100, 400) + ".0";
                    map1.put("num", "??????" + ram500 + "???");

                    mListData1.set(i - 1, map1);


                }
            }
        }

        adapter1 = new MyAdapter(mListData1);
        listView1.setAdapter(adapter1);
        if (mTimer1 != null) {
            mTimer1.cancel();
        }
        mTimer1 = new Timer();
        mTimer1.schedule(task1, 0, 10);

    }

    /**
     * ????????????????????? ??????????????????
     */
    private void addToLimitList() {
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("nname", StringUtils.getVirtualName() + "***" + StringUtils.getVirtualName());
        map1.put("p_name", "?????????????????????");
        map1.put("pic", "defaultcommentimage/" + StringUtils.getDefaultImg());

        map1.put("num", "+" + StringUtils.getRandomInt(8, 16) + StringUtils.getVirtualDecimalAwardsWithdrawal() + "???");// 8 -16

        mListData1.add(map1);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {

            case R.id.img_back:
                onBackPressed();
                break;

            default:
                break;
        }
    }


    /**
     * ????????? ????????????  ??? Adapter
     */
    public class MyAdapter extends BaseAdapter {
        private List<HashMap<String, String>> mListData;

        public MyAdapter(List<HashMap<String, String>> mListData) {
            super();
            this.mListData = mListData;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object getItem(int arg0) {
            return mListData.get(arg0 % (mListData.size()));
        }

        @Override
        public long getItemId(int arg0) {
            return arg0 % (mListData.size());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(SignDrawalLimitActivity.this, R.layout.item_withdrawal_limit, null);
                holder.mNameTv = (TextView) convertView.findViewById(R.id.withdrawal_name_tv);
                holder.tv = (TextView) convertView.findViewById(R.id.withdrawal_exp_tv);
                holder.mAwardsTv = (TextView) convertView.findViewById(R.id.withdrawal_awards_tv);
                holder.headIv = (ImageView) convertView.findViewById(R.id.withdrawal_head_iv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
//			SetImageLoader.initImageLoader(WithdrawalLimitActivity.this, holder.headIv, mListData.get(position%mListData.size()).get("pic").toString(), "");

//			PicassoUtils.initImage(WithdrawalLimitActivity.this,  mListData.get(position%mListData.size()).get("pic").toString(), holder.headIv);

            GlideUtils.initRoundImage(Glide.with(SignDrawalLimitActivity.this), SignDrawalLimitActivity.this, mListData.get(position % mListData.size()).get("pic").toString(), holder.headIv);


            holder.mNameTv.setText(mListData.get(position % mListData.size()).get("nname").toString());
            holder.mAwardsTv.setText(mListData.get(position % mListData.size()).

                    get("num").

                    toString());
            String nameStr = mListData.get(position % mListData.size()).get("p_name").toString();
            if (nameStr.length() > 15) {
                nameStr = nameStr.substring(0, 15) + "...";
            }

            holder.tv.setText(nameStr);
            return convertView;
        }


    }

    public class ViewHolder {
        TextView mNameTv, tv, mAwardsTv;
        ImageView headIv;
    }

    TimerTask task1 = new TimerTask() {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listView1.smoothScrollBy(2, 0);
                }
            });

        }
    };


    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    txkRecLen -= 1000;
                    String days;
                    String hours = "";
                    String minutes = "";
                    String seconds = "";
                    long minute = txkRecLen / 60000;
                    long second = (txkRecLen % 60000) / 1000;
                    if (minute >= 60) {
                        long hour = minute / 60;
                        minute = minute % 60;

                        if (hour < 10) {
                            hours = "0" + hour;
                        } else {
                            hours = "" + hour;
                        }
                        if (minute < 10) {
                            minutes = "0" + minute;
                        } else {
                            minutes = "" + minute;
                        }
                        if (second < 10) {
                            seconds = "0" + second;
                        } else {
                            seconds = "" + second;
                        }

                    } else if (minute >= 10 && second >= 10) {
                        hours = "00";
                        minutes = minute + "";
                        seconds = second + "";
                    } else if (minute >= 10 && second < 10) {
                        hours = "00";
                        minutes = minute + "";
                        seconds = "0" + second;

                    } else if (minute < 10 && second >= 10) {
                        hours = "00";
                        minutes = "0" + minute;
                        seconds = second + "";
                    } else if (minute < 10 && second < 10) {
                        hours = "00";
                        minutes = "0" + minute;
                        seconds = "0" + second + "";
                    }
                    if (txkRecLen <= 0) {
                        tkxTimer.cancel();
                        mDialog.dismiss();
                    } else {
                        tv_txk_countdown.setText(hours + ":" + minutes + ":" + seconds + "?????????");
                    }
                }
            });
        }

    }

    private int getPosition() {

//        int awardIndex = 1;

        if (null != mTXCJzhongjiangData && mTXCJzhongjiangData.getIsYJin() == 1) {
            return 4;
        }

        if (redPacketValue >= 0 && redPacketValue < 10) {
            awardIndex = 1;
        } else if (redPacketValue >= 15 && redPacketValue < 50) {
            awardIndex = 6;
        } else if (redPacketValue >= 10 && redPacketValue < 15) {
            awardIndex = 5;
        } else if (redPacketValue >= 50 && redPacketValue < 70) {
            awardIndex = 4;
        } else if (redPacketValue >= 100 && redPacketValue < 200) {
            awardIndex = 3;
        } else if (redPacketValue >= 500 && redPacketValue <= 1000) {//?????????
            awardIndex = 2;
        }

        return awardIndex;

    }

    @Override
    protected void onDestroy() {

        EventBus.getDefault().unregister(this);//????????????

        if (null != mTimer1) {
            mTimer1.cancel();
        }
        if (mDialog != null) {
            mDialog.dismiss();
        }
        super.onDestroy();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BuyVipSucEvent(MessageEvent messageEvent) {

        if (messageEvent.getEventBuyVipSucVipType() == 0) {
            return;
        }
        YConn.httpPost(mContext, YUrl.FIRST_ZUANSHI_ZHUANPAIN_TISHI,
                new HashMap<String, String>(), new HttpListener<BaseData>() {
                    @Override
                    public void onSuccess(BaseData result) {

                        if (result.getIsPopup() == 1) {//????????????
                            showBuyVipSucDialog(result);
                            return;
                        }

                        if (result.getIsPopup() == 2) {//????????????
                            showBuyVipSucDialogHG(result);
                            return;
                        }
                        queryCount(true);


                    }

                    @Override
                    public void onError() {

                    }
                });


//        int buySucVipType = messageEvent.getEventBuyVipSucVipType();
//        final String vipDikou = messageEvent.getVipDikou() + "";
//        if (buySucVipType == 4 || buySucVipType == 5 || buySucVipType == 6) {
//            HashMap<String, String> map = new HashMap<>();
//            map.put("type", type + "");
//            YConn.httpPost(mContext, YUrl.QUERY_NEW_CJTX_COUNT, map, new HttpListener<CJTXcountData>() {
//                @Override
//                public void onSuccess(CJTXcountData cjtXcountData) {
//                    if (cjtXcountData.getToMakeMoney_page() == 1) {
//
//                        if (null != mDialog) {
//                            mDialog.dismiss();
//                        }
//
//                        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
//                        View view = View.inflate(mContext, R.layout.dialog_txcj_count_use_up, null);
//
//                        TextView tv_money = view.findViewById(R.id.tv_money);
//                        TextView tv1 = view.findViewById(R.id.tv1);
//                        TextView tv2 = view.findViewById(R.id.tv2);
//                        TextView bt1 = view.findViewById(R.id.bt1);
//                        LinearLayout bt2 = view.findViewById(R.id.ll_bt2);
//                        ImageView icon_close = view.findViewById(R.id.icon_close);
//
//                        tv_money.setText(DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getRaffle_money()) + "???");
//                        tv_money.getPaint().setFakeBoldText(false);
//                        bt2.setVisibility(View.GONE);
//                        bt1.setText("??????");
//
//
//                        Spanned sd1;
//                        sd1 = Html.fromHtml("????????????<font color='#FDCC21'><strong>"
//                                + "????????????169???</strong></font>??????????????????????????????????????????????????????????????????<font color='#FDCC21'><strong>"
//                                + vipDikou + "????????????</strong></font>??????<font color='#FDCC21'><strong>79???</strong></font>??????????????????"
//                        );
//
//
//                        tv1.setText(sd1);
//
//
//                        Spanned sd2;
//                        sd2 = Html.fromHtml("?????????????????????????????????<font color='#FDCC21'><strong>"
//                                + "?????????15???????????????</strong></font>?????????????????????????????????"
//                        );
//                        tv2.setText(sd2);
//                        tv2.setVisibility(View.VISIBLE);
//
//
//                        bt1.setOnClickListener(new OnClickListener() {//????????????
//                            @Override
//                            public void onClick(View view) {
//                                mDialog.dismiss();
//                                finish();
//
//                            }
//                        });
//
//
//                        icon_close.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                mDialog.dismiss();
//                                finish();
//
//                            }
//                        });
//
//
//                        // // ?????????????????????dialog
//                        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
//                                LinearLayout.LayoutParams.MATCH_PARENT));
//                        mDialog.setCancelable(false);
//                        mDialog.show();
//
//
//                    }
//                }
//
//                @Override
//                public void onError() {
//
//                }get
//            });
//
//
//        }
    }


    private void showAnimNumDialog(final boolean mIsVirtual) {

        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.dialog_withdraw_wx_daozhang_real_new, null);

        ScrollingDigitalAnimation tv1 = view.findViewById(R.id.tv1);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv_time = view.findViewById(R.id.tv_time);
        TextView tv_tx = view.findViewById(R.id.tv_tx);
        TextView tv_tx10 = view.findViewById(R.id.tv_tx10);


        tv_tx10.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!YJApplication.instance.isLoginSucess()) {

                    if (LoginActivity.instances != null) {
                        LoginActivity.instances.finish();
                    }
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.putExtra("login_register", "login");
                    intent.putExtra("isVirtual", true);
                    startActivityForResult(intent, 1002);

                    return;
                }

                startActivityForResult(new Intent(SignDrawalLimitActivity.this, MyVipListActivity.class)
                                .putExtra("is20ciBuyVip", true)

                        , 1003);

            }
        });


        Spanned tv2Str = Html.fromHtml("?????????<font color='#FDCC21'>" + 10 +
                "</font>????????????<font color='#FDCC21'>?????????????????????</font>??????????????????");
        tv2.setText(tv2Str);
        String startShowMoney;
        String endShowMoney;

        if (mIsVirtual) {
            startShowMoney = "0.00";
            endShowMoney = redPacketValue + "";
        } else {
            startShowMoney = DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getLast_lotteryMoney());
            endShowMoney = DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getKf_allMoney());
        }

        tv1.setDuration(1500);
        tv1.setNumberString(startShowMoney, endShowMoney);

        long countTime = 9 * 60 * 1000;
        if (!mIsVirtual) {
            countTime = mTXCJzhongjiangData.getExpireTime();
        }


        countDownTimer = new SimpleCountDownTimer(countTime, tv_time).setOnFinishListener(new SimpleCountDownTimer.OnFinishListener() {
            @Override
            public void onFinish() {
                if (null != mDialog) {
                    mDialog.dismiss();
                }
            }
        });
        countDownTimer.start();

        tv3.setText("?? " + DateUtils.dePoint("#0.00", 10.00) + "");
        tv_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mIsVirtual) {


                    if (LoginActivity.instances != null) {
                        LoginActivity.instances.finish();
                    }

                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.putExtra("login_register", "login");
                    intent.putExtra("isVirtual", true);
                    startActivityForResult(intent, 1002);


                } else {
                    if (null != countDownTimer) {
                        countDownTimer.cancel();
                    }
                    mDialog.dismiss();
                    continueTXfromDialogClick();
                }

            }
        });
        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();
    }


    private void noCountToPage() {

        Intent intent;
        if (is_finishCome == 1) {//???20????????????????????????--?????????
            if (null != CommonActivity.instance) {
                CommonActivity.instance.finish();
            }
            SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
            intent = new Intent(mContext, CommonActivity.class);
            if (mCjtXcountData.getNew_raffle_skipSwitch() == 0
                    && mCjtXcountData.getIs_finish() == 1
                    && choujiangCount <= 0
                    && mCjtXcountData.getReRoundCount() == 0) {
                if (null != CommonActivity.instance) {
                    CommonActivity.instance.finish();
                }
                intent.putExtra("20choujiang_complete", true);
            }
        } else {//??????
            intent = new Intent(mContext, WithdrawalLimitActivity.class);
        }
        startActivity(intent);
        ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

        finish();


    }
}
