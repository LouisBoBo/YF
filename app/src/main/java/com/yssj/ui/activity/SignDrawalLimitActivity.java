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
 * 新抽提现
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

    private boolean isVirtual;//是否是虚拟抽奖

    private int type = 2;//从哪里进来的。1：赚钱，2其他

    private SimpleCountDownTimer countDownTimer;


    private CJTXcountData mCjtXcountData;//当前抽奖次数相关数据

    private long txkRecLen;
    private Timer tkxTimer;
    private TextView tv_txk_countdown;
    private Dialog mDialog;
    private Context mContext;
    private boolean isRun;//是否正在旋转
    public static SignDrawalLimitActivity instance;

    private double redPacketValue;//单次中奖金额

    private TXCJzhongjiangData mTXCJzhongjiangData;//单次中奖情况
    private int choujiangCount;//当前剩余次数
    private int totalchoujiangCount;//用户当前可以抽的总次数
    private int dayall_count;//当天可以抽的总次数
    private String redPacketValue_totaldata;//当天累计的总抽奖金额（虚拟不可提现）

    public static final int BUG_TXK_SUCCESS = 2000;
    public static final int No_BUG_TXK_SUCCESS = -2000;
    private boolean isFromWallet;//钱包提现按钮进来的

    private boolean zhongJiangStatusQueryEd = true;
    //    public static final int BUG_VIP_SUCCESS = 3000;
    private boolean fromSign = false;


    private boolean fromFreeBuy = true;//从免费领进来的（没有抽完过20次）
    private boolean fromPT = true;//从拼团领进来的（没有抽完过20次）
    private int awardIndex = 1;//抽中的中奖区间 （从1开始，逆时针）
    private String free_url; //抽中免费领弹窗上的图片

    //    private boolean hiddenTXK = true;
//    private int new_raffle_skipSwitch;
    private BaseData virtualZJdata;
    private boolean userIsVip; //是否是会员

    private boolean isFromNewWallet;//新钱包界面调过来的要查询后要自动抽一次，并弹出单独的弹窗

    private int is_finishCome = 0;//20次有没有抽完 0抽完(默认) 1没有抽完
    private String showNewWalletTXallMoney;


    /**
     *
     * 提前抽的20次的时候 查询到次数为0时跳赚钱。其他的一律跳余额抽提现
     * 没有次数后点击立即提现不在弹出次数一用完的弹窗，直接跳出去：
     *              来抽前20次的去赚钱，其他的跳余额抽提现
     *
     * 进来就没有次数的也是直接去余额抽提现
     *
     */


    /**
     * userIsVip
     * 当用户是会员后：
     * 1.抽到的虚拟金额为佣金
     * 2.虚拟金额中奖的弹窗（单独的弹窗）点击继续提现不再弹出15元的弹窗，而是查次数
     * 3.次数抽完后点继续提现跳好友奖励
     */

    @Override
    public void onBackPressed() {

        boolean mIsVip = false;

        if (choujiangCount > 0) {
            ToastUtil.showShortText2("请点转盘中央的“马上提现”，先完成提现。");
            return;
        }

        if (YJApplication.instance.isLoginSucess() && null != mCjtXcountData) {


            mIsVip = CommonUtils.isVip(mCjtXcountData.getIsVip(), mCjtXcountData.getMaxType());

        }


        if (isRun) {
            return;
        } else if (null != mCjtXcountData && mCjtXcountData.getIs_finish() == 0 && !mIsVip) { //抽完了不是会员--跳到赚钱


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
            ToastUtil.showMyToast(this, "奖励已进入账户，祝您购物愉快。", 3000);
            finish();
            return;
        }

        setContentView(R.layout.activity_withdrawal_limit_sign);

        AppManager.getAppManager().addActivity(this);
        EventBus.getDefault().register(this);//注册
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

//        hiddenTXK = true;//测试


        //虚拟抽奖只有未登录的情况
        isVirtual = !YJApplication.instance.isLoginSucess();
        isFromWallet = getIntent().getBooleanExtra("isFromWallet", false);

        type = getIntent().getIntExtra("type", 2);
        mContext = this;
        loading = new CommonLoadingView(this);
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        listView1 = findViewById(R.id.list_view1);
        initLimitAwardsList();
        wheelSurfView = findViewById(R.id.wheelSurfView);
        //添加滚动监听
        wheelSurfView.setRotateListener(new RotateListener() {
            @Override//旋转结束
            public void rotateEnd(int position, String des) {
                isRun = false;


                roteEnd(position);
            }

            @Override
            public void rotating(ValueAnimator valueAnimator) {
                isRun = true;

            }

            @Override //点击开始
            public void rotateBefore(ImageView goImg) {
                if (isRun) {
                    return;
                }
                if (null != mDialog && mDialog.isShowing()) {
                    return;
                }

                //去登录
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


                //查询中奖情况接口没掉完之前不能点击
                if (!zhongJiangStatusQueryEd) {
                    return;
                }
                //抽中未点拆之前不能再次点击
                if (CommonUtils.isNotFastClick()) {
                    clickStart();

                }

            }
        });


        //免费领测试
//        if (true) {
//            showGetFreeBuyDialog();
//            return;
//        }


        //虚拟抽奖的自动开始抽
        if (isVirtual) {

            //获取未登录第一次中奖情况
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


        } else {//非虚拟的就要实时查询抽奖次数（先查询是否是会员）


//            //查询会员情况
//            HashMap<String, String> pairsMap = new HashMap<>();
//            YConn.httpPost(mContext, YUrl.QUERY_VIP_INFO2, pairsMap
//                    , new HttpListener<VipInfo>() {
//                        @Override
//                        public void onSuccess(VipInfo vipInfo) {
//                            isVip = vipInfo.getIsVip();
//
//                            //如果购买了会员且，购买会员成功的弹窗没有弹出过就弹出
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

            //刚进来的处理

            //新钱包提现的处理
            if (isFromNewWallet) {
                queryCount(true);
                return;

            }
            //首张钻石卡的处理
            YConn.httpPost(mContext, YUrl.FIRST_ZUANSHI_ZHUANPAIN_TISHI,
                    new HashMap<String, String>(), new HttpListener<BaseData>() {
                        @Override
                        public void onSuccess(BaseData result) {
                            if (result.getIsPopup() == 1) {//钻石弹窗
                                showBuyVipSucDialog(result);
                                return;
                            }

                            if (result.getIsPopup() == 2) {//皇冠弹窗
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


        Spanned tv2Str = Html.fromHtml("恭喜成功领到<font color='#FDCC21'><strong>" + mTXCJzhongjiangData.getShow_free_money() + "元美衣</strong></font>，可直接<font color='#FDCC21'><strong>免费发货</strong></font>。请注意查收。");

//        Spanned tv3Str = Html.fromHtml("恭喜成功领到<font color='#FDCC21'>" + DateUtils.dePoint("#0.00", redPacketValue) +
//                "</font>元现金。已提现至微信零钱。请注意查收。");

        tv2.setText(tv2Str);


        view.findViewById(R.id.bt_vip).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {//继续提现
                mDialog.dismiss();

                if (mTXCJzhongjiangData.getFreeBuyType() == 1) {
                    showGetFreeBuyDialog1();

                } else {
                    showGetFreeBuyDialog2();

                }


            }
        });


        // // 创建自定义样式dialog
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
        bt1.setText("成为皇冠会员");


        tv_money.setText(mTXCJzhongjiangData.getYj_money() + "元");


        Spanned sd = Html.fromHtml("成为皇冠会员后即可立即<font color='#FDCC21'><strong>" +
                "免费发货</strong></font>" +
                "领到的<font color='#FDCC21'><strong>" +
                mTXCJzhongjiangData.getShow_free_money() + "元美衣</strong></font>" +
                "。");


        tv1.setText(sd);

        Spanned sd2 = Html.fromHtml("同时可<font color='#FDCC21'><strong>" +
                "提现" + mTXCJzhongjiangData.getYj_money() + "元</strong></font>粉丝佣金。");
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


        bt1.setOnClickListener(new OnClickListener() {//成为会员
            @Override
            public void onClick(View view) {//成为会员
//                mDialog.dismiss();
                startActivityForResult(new Intent(SignDrawalLimitActivity.this, MyVipListActivity.class)
                                .putExtra("vipUpdateVipType", 5)
                        , 1003);

            }
        });


        bt2.setOnClickListener(new OnClickListener() {//小程序中laststartLuckBtn
            @Override
            public void onClick(View view) {//继续提现
                mDialog.dismiss();
//                continueTXfromDialogClick();


                //20次抽完跳赚钱的开关打开的，且20次抽完的时候--去赚钱
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


                if (choujiangCount <= 0) {//次数用完了


                    //如果轮数大于0要重新查询次数并开始抽奖
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

                                //重新查中奖情况并开始抽奖
                                clickStart();


                            }

                            @Override
                            public void onError() {

                            }
                        });


                    } else {
                        if (Double.parseDouble(redPacketValue_totaldata) > 0) {
                            coujizngCountUseUpdialog();
                            if (mCjtXcountData.getIs_finish() != 0) {//此时20次全部抽完
                                SharedPreferencesUtil.saveBooleanData(mContext, "20Choujiang_finish", true);
                            }
                        } else {
                            showNoHasCountDialog();

                        }
                    }


                } else {//还有次数
                    clickStart();
                }


            }
        });


        // // 创建自定义样式dialog
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


        tv_money.setText(redPacketValue_totaldata + "元");


        Spanned sd = Html.fromHtml("成为会员后即可立即<font color='#FDCC21'><strong>" +
                "免费发货</strong></font>" +
                "领到的<font color='#FDCC21'><strong>" +
                mTXCJzhongjiangData.getShow_free_money() + "元美衣</strong></font>" +
                "。");


        tv1.setText(sd);

        Spanned sd2 = Html.fromHtml("同时可<font color='#FDCC21'><strong>" +
                "立即提现</strong></font>本日已累计提现的<font color='#FDCC21'><strong>" +
                redPacketValue_totaldata + "元</strong></font>现金。");
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


        bt1.setOnClickListener(new OnClickListener() {//成为会员
            @Override
            public void onClick(View view) {//成为会员
//                mDialog.dismiss();
                startActivityForResult(new Intent(SignDrawalLimitActivity.this, MyVipListActivity.class), 1003);

            }
        });


        bt2.setOnClickListener(new OnClickListener() {//小程序中laststartLuckBtn
            @Override
            public void onClick(View view) {//继续提现
                mDialog.dismiss();
//                continueTXfromDialogClick();


                //20次抽完跳赚钱的开关打开的，且20次抽完的时候--去赚钱
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


                if (choujiangCount <= 0) {//次数用完了


                    //如果轮数大于0要重新查询次数并开始抽奖
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

                                //重新查中奖情况并开始抽奖
                                clickStart();


                            }

                            @Override
                            public void onError() {

                            }
                        });


                    } else {
                        if (Double.parseDouble(redPacketValue_totaldata) > 0) {
                            coujizngCountUseUpdialog();
                            if (mCjtXcountData.getIs_finish() != 0) {//此时20次全部抽完
                                SharedPreferencesUtil.saveBooleanData(mContext, "20Choujiang_finish", true);
                            }
                        } else {
                            showNoHasCountDialog();

                        }
                    }


                } else {//还有次数
                    clickStart();
                }


            }
        });


        // // 创建自定义样式dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();


    }


    //获取中奖情况并开始转
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


    //查询抽奖次数
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

                //测试用
//                mCjtXcountData.setData(2);

//                if (isFromWallet && Double.parseDouble(redPacketValue_totaldata) > 0 && cjtXcountData.getReRoundCount() == 0) {//钱包进来的单做
//                    isFromWallet = false;
//                    showFromWallet();
//                    return;
//                }


                if (choujiangCount > 0) {

                    //新钱包过来的自动抽一次
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

                    if (cjtXcountData.getReRoundCount() <= 0) {//剩余轮数为0
                        if (cjtXcountData.getIs_finish() == 0) {//抽完过20次（弹次数提示弹窗）
                            showStartDialog();
                        } else {//未抽完过（查询到抽奖结果并直接开始转）
                            if (isFirstQuery) {
                                //非审核员前20次没抽完第一次进转盘弹窗，只弹一次，本地计
                                if (userInfo.getReviewers() != 1 && !qian20_fist_choujiang_tishi_show_ed) {
                                    SharedPreferencesUtil.saveBooleanData(mContext, "qian20_fist_choujiang_tishi_show_ed", true);
                                    showFirstQaian20Dialog();
                                }else{
                                    ToastUtil.showMyToast(mContext, "请点转盘中央的“马上提现”，先完成提现。", 4000);

                                }
                            } else {
                                queryZJmoneyAndRotate();
                            }
                        }
                    } else { //剩余轮数大于0（查询到抽奖结果并直接开始转）
                        if (isFirstQuery && userInfo.getReviewers() != 1 && !qian20_fist_choujiang_tishi_show_ed) {
                            SharedPreferencesUtil.saveBooleanData(mContext, "qian20_fist_choujiang_tishi_show_ed", true);
                            showFirstQaian20Dialog();
                        }else{
                            if (isFirstQuery) {
                                ToastUtil.showMyToast(mContext, "请点转盘中央的“马上提现”，先完成提现。", 4000);
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
                        coujizngCountUseUpdialog();//有中奖金额，抽奖次数用完了
                    } else {
                        showNoHasCountDialog();//没有中奖金额，也没有抽奖次数

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
//                        coujizngCountUseUpdialog();//有中奖金额，抽奖次数用完了
//                    } else {
//                        showNoHasCountDialog();//没有中奖金额，也没有抽奖次数
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


    private void showNoHasCountDialog() { //没有次数的情况固定跳转

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
//            tv1.setText(Html.fromHtml("您目前没有提现机会，赠送您<strong><font color='#FDCC21'>" + 50 + "次</font></strong>提现机会。"));
//        } else {
//            tv1.setText(Html.fromHtml("您目前没有提现机会，赠送您<strong><font color='#FDCC21'>" + 50 + "次</font></strong>提现机会。"));
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
//        tv_tx.setText("继续提现");
//        tv_tx.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//                //会员的是邀请好友
//                if (CommonUtils.isVip(mCjtXcountData.getIsVip(), mCjtXcountData.getMaxType())
//                        || mCjtXcountData.getTixian_twoCount() == 1) {
//                    showVipBackDialog();//邀请好友弹窗
//                } else { //非会员是引导提现卡
//                    showTXKdialog();
//                }
//            }
//        });
//
//
//        // // 创建自定义样式dialog
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
//        //20次抽完跳赚钱的开关打开的，且20次抽完的时候--去赚钱
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
//        //如果提现卡被隐藏就跳往赚钱(或者已经买了1张提现卡)
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
//        //会员的是邀请好友
//        if (CommonUtils.isVip(mCjtXcountData.getIsVip(), mCjtXcountData.getMaxType())
//                || mCjtXcountData.getTixian_twoCount() == 1) {
//            showVipBackDialog();//邀请好友弹窗
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

                String shareText = "\uD83D\uDC47点击领取您的90元任务奖金！";
//                String wxMiniPathdUO = "/pages/sign/sign?isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id();
                String wxMiniPathdUO = "/pages/mine/toexamine_test/toexamine_test?isShareFlag=true&user_id=" + YCache.getCacheUser(mContext).getUser_id() + "&showSignPage=true";


                String shareMIniAPPimgPic = YUrl.imgurl + "small-iconImages/heboImg/taskraward_shareImg.png";


                //分享到微信统一分享小程序
                WXminiAPPShareUtil.shareToWXminiAPP(mContext, shareMIniAPPimgPic, shareText, wxMiniPathdUO, false);
                WXEntryActivity.setWXminiShareListener(new WXEntryActivity.WXminiAPPshareListener() {
                    @Override
                    public void wxMiniShareSuccess() {
                        ToastUtil.showMyToast(mContext, "分享成功，别忘记让好友点进来并微信授权哦。", 4000);

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
            case 1001://从引导提现卡回来

                if (resultCode == BUG_TXK_SUCCESS) {
                    queryCount(false);
                } else {
                    showVipBackDialog();
                }

                break;
            case 1002://同步数据
                if (resultCode == LOGIN_SUCCESS && isVirtual) {

                    if (null != countDownTimer) {
                        countDownTimer.cancel();
                    }

                    if (null != mDialog) {
                        mDialog.dismiss();
                    }
                    if (GuideActivity.needFengKong) {
                        ToastUtil.showMyToast(mContext, "奖励已进入账户，祝您购物愉快。", 3000);
                        finish();
                        return;
                    }
                    syncVirtualData();
                }
                break;

            case 1003://引导购买会员卡回来
//                if (resultCode == BUG_VIP_SUCCESS) {

//                    YConn.httpPost(mContext, YUrl.FIRST_ZUANSHI_ZHUANPAIN_TISHI,
//                            new HashMap<String, String>(), new HttpListener<BaseData>() {
//                                @Override
//                                public void onSuccess(BaseData result) {
//
//                                    if (result.getIsPopup() == 1) {//钻石弹窗
//                                        showBuyVipSucDialog(result);
//                                        return;
//                                    }
//
//                                    if (result.getIsPopup() == 2) {//皇冠弹窗
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

        tv_money.setText("温馨提示");
        tv_money.getPaint().setFakeBoldText(false);
        bt2.setVisibility(View.GONE);
        bt1.setText("继续提现");


        Spanned sd1;
//        sd1 = Html.fromHtml("钻石会员<font color='#FDCC21'><strong>"
//                + "原价预存169元</strong></font>赠送，首次成为会员会优先抵扣已有的提现。您有<font color='#FDCC21'><strong>"
//                + baseData.getUnVipRaffleMoney() + "元被抵扣</strong></font>，仅<font color='#FDCC21'><strong>" + baseData.getVip_price() + "</strong></font>即成为会员。"
//        );

        sd1 = Html.fromHtml("您已成为钻石会员，累积的<font color='#FDCC21'><strong>" + baseData.getUnVipRaffleMoney() + "元可提现余额</strong></font>已在您的衣蝠钱包里。");

        tv1.setText(sd1);


//        Spanned sd2;
//        sd2 = Html.fromHtml("您已是会员，接下来提现<font color='#FDCC21'><strong>"
//                + "不再受15元金额限制</strong></font>，直接发放入微信零钱。"
//        );
//        tv2.setText(sd2);
        tv2.setVisibility(View.GONE);


        bt1.setOnClickListener(new OnClickListener() {//继续提现--查询次数，如果有次数 直接开始抽
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

                        if (choujiangCount > 0) { //有次数就直接开始抽奖
                            queryZJmoneyAndRotate();
                        } else { //没有次数就弹出 关掉转盘的弹窗
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


        // // 创建自定义样式dialog
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

        tv_money.setText("温馨提示");
        tv_money.getPaint().setFakeBoldText(false);
        bt2.setVisibility(View.GONE);
        bt1.setText("继续提现");


        tv1.setText("您已成为皇冠会员，可以去我的佣金页提现佣金了哦。");


        tv2.setVisibility(View.GONE);


        bt1.setOnClickListener(new OnClickListener() {//继续提现--查询次数，如果有次数 直接开始抽
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


        // // 创建自定义样式dialog
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
        view.findViewById(R.id.root_view).setOnClickListener(new OnClickListener() {//继续提现
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                clickStart();
            }
        });
        // // 创建自定义样式dialog
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
////        tv_money.setText(DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getRaffle_money()) + "元");
////        tv_money.getPaint().setFakeBoldText(false);
//
//        tv_money.setText("温馨提示");
//        tv_money.setTextSize(18);
//
//        bt2.setVisibility(View.GONE);
//        bt1.setText("离开");
//
//
//        Spanned sd1;
//        sd1 = Html.fromHtml("今天的提现次数已用完，<font color='#FDCC21'><strong>"
//                + "明天任务会更新</strong></font>，完成后可<font color='#FDCC21'><strong>"
//                + "继续提现</strong></font>，祝您好运。"
//        );
//
//
//        tv1.setText(sd1);
//
//
////        Spanned sd2;
////        sd2 = Html.fromHtml("您已是会员，接下来提现<font color='#FDCC21'><strong>"
////                + "不再受15元金额限制</strong></font>，直接发放入微信零钱。"
////        );
////        tv2.setText(sd2);
//        tv2.setVisibility(View.GONE);
//
//
//        bt1.setOnClickListener(new OnClickListener() {//继续提现--查询次数，如果有次数 直接开始抽
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
//        // // 创建自定义样式dialog
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
            tv1.setText(Html.fromHtml("转盘<font color='#FDCC21'><strong>"
                    + "转到免费领走美衣"
                    + "</strong></font>，可<font color='#FDCC21'><strong>"
                    + "免费发货本件美衣</strong></font>。转到金额，可<font color='#FDCC21'><strong>立即提现现金到微信零钱</strong></font>"
                    + "。请点“<font color='#FDCC21'><strong>马上提现</strong></font>”开始。"

            ));


        } else if (fromPT) {
            tv1.setText(Html.fromHtml("转盘<font color='#FDCC21'><strong>"
                    + "转到免拼团发货"
                    + "</strong></font>，可立即<font color='#FDCC21'><strong>免拼发货本件美衣</strong></font>。转到金额，可<font color='#FDCC21'><strong>立即提现现金到微信零钱</strong></font>"
                    + "。请点“<font color='#FDCC21'><strong>马上提现</strong></font>”开始。"
            ));

        } else {
            tv1.setText(Html.fromHtml("您有<font color='#FDCC21'><strong>"
                    + choujiangCount
                    + "次</strong></font>提现机会，请点<font color='#FDCC21'><strong>“马上提现”</strong></font>。开始马上提现。"));
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


        // // 创建自定义样式dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();
        fromFreeBuy = false;
        fromPT = false;
    }

    //点击开始
    private void clickStart() {//非虚拟抽奖
        if (null == mCjtXcountData) {
            ToastUtil.showShortText2("请稍后");
        }
        if (choujiangCount > 0) {//有次数可以开始抽
            queryZJmoneyAndRotate();
        } else {
            showNoHasCountDialog();
        }

    }

    private void roteEnd(int position) {
        openRedPacket();
    }


    /**
     * 点击拆开红包(余额抽奖)
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
        open_tv.setText("哇喔，恭喜您，快拆开看看！");
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

                            //未登录的虚拟抽奖已经更改为带动画的弹窗
//                            redPacketOpenedVirtual();

                            if (virtualZJdata.getNewRaffle_type() == 1) {//真实到账的
                                redPacketOpenedUnLoginReal();
                            } else {
                                showAnimNumDialog(true);
                            }

                        } else {

                            //抽中了免费领就弹免费领的tanc
                            if (awardIndex == 2) {
                                mDialog.dismiss();

                                showGetFreeBuyDialog();
                                return;
                            }

                            //如果有发放中奖需要调用发放接口
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
        // // 创建自定义样式dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();
    }


    private void redPacketOpenedUnLoginReal() {//真实抽奖点拆
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
        tv_tx.setText("立即微信授权提现");

        tv1.setText(DateUtils.dePoint("#0.00", redPacketValue * 5) + "元");
        tv3.setText("¥ " + DateUtils.dePoint("#0.00", redPacketValue));


        double total_extract_money = 0;//虚拟发放总金额
        double surplus_extract_money = 0;//剩余发放金额
        total_extract_money = virtualZJdata.getRaffle_money() * 5;
        surplus_extract_money = (5 - 1) * virtualZJdata.getRaffle_money();

        Spanned tv2Str = Html.fromHtml("恭喜成功提现<font color='#FDCC21'>" + DateUtils.dePoint("#0.00", total_extract_money) +
                "</font>元，其中<font color='#FDCC21'><strong>" + DateUtils.dePoint("#0.00", virtualZJdata.getRaffle_money()) +
                "</strong></font>元<font color='#FDCC21'><strong>微信授权后立即发放</strong></font>至微信零钱。");

        tv2.setText(tv2Str);

        Spanned tv4Str = Html.fromHtml("剩余<font color='#FDCC21'><strong>" + DateUtils.dePoint("#0.00", surplus_extract_money) +
                "</strong></font>元将分" + "<font color='#FDCC21'>" + 7 + "</font>天到账，记得每日来衣蝠领取。");

        tv4.setText(tv4Str);


        view.findViewById(R.id.tv_tx).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {//继续提现

                //去登录
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


        // // 创建自定义样式dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();
    }


    private void redPacketOpenedReal() {//真实抽奖点拆
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


        tv1.setText(DateUtils.dePoint("#0.00", redPacketValue) + "元");

        if ((mCjtXcountData.getIsVip() == 0 || mCjtXcountData.getIsVip() == 3) && mTXCJzhongjiangData.getExtract_money() > 0) {
            tv3.setText("¥ " + DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getExtract_money()));
        } else {
            tv3.setText("¥ " + DateUtils.dePoint("#0.00", redPacketValue));

        }


        double total_extract_money = 0;//虚拟发放总金额
        double surplus_extract_money = 0;//剩余发放金额
        if (mTXCJzhongjiangData.getMultiple() >= 1) {
//            total_extract_money = mTXCJzhongjiangData.getExtract_money() * mTXCJzhongjiangData.getMultiple();
//            surplus_extract_money = (mTXCJzhongjiangData.getMultiple() - 1) * mTXCJzhongjiangData.getExtract_money();

            total_extract_money = mTXCJzhongjiangData.getRaffle_money() * mTXCJzhongjiangData.getMultiple();
            surplus_extract_money = (mTXCJzhongjiangData.getMultiple() - 1) * mTXCJzhongjiangData.getRaffle_money();
        }

        Spanned tv2Str = new SpannableString("测试");


        if ((mCjtXcountData.getIsVip() == 0 || mCjtXcountData.getIsVip() == 3) && mTXCJzhongjiangData.getExtract_money() > 0 && mTXCJzhongjiangData.getMultiple() >= 1) {

            //tv4是第二行
            if (mTXCJzhongjiangData.getExtract_money() > 0 && mTXCJzhongjiangData.getExtract_money() < 1) {

                tv2Str = Html.fromHtml("恭喜成功提现<font color='#FDCC21'>" + DateUtils.dePoint("#0.00", total_extract_money) +
                        "</font>元，其中<font color='#FDCC21'><strong>" + DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getExtract_money()) +
                        "</strong></font>元可发放至微信零钱。");
                tv_tx.setText("立即提现");


                Spanned tv4Str = Html.fromHtml("剩余<font color='#FDCC21'><strong>" + DateUtils.dePoint("#0.00", surplus_extract_money) +
                        "</strong></font>元将分" + "<font color='#FDCC21'>" + mTXCJzhongjiangData.getDay() + "</font>天到账，记得每日来衣蝠领取。");
                tv4.setText(tv4Str);
                tv4.setVisibility(View.VISIBLE);

            } else {

                tv2Str = Html.fromHtml("恭喜成功提现<font color='#FDCC21'>" + DateUtils.dePoint("#0.00", total_extract_money) +
                        "</font>元，其中<font color='#FDCC21'><strong>" + DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getExtract_money()) +
                        "</strong></font>元已发放至微信零钱，请注意查收。");

                Spanned tv4Str = Html.fromHtml("剩余<font color='#FDCC21'><strong>" + DateUtils.dePoint("#0.00", surplus_extract_money) +
                        "</strong></font>元将分" + "<font color='#FDCC21'>" + mTXCJzhongjiangData.getDay() + "</font>天到账，记得每日来衣蝠领取。");
                tv4.setText(tv4Str);
                tv4.setVisibility(View.VISIBLE);

            }


        } else if (mTXCJzhongjiangData.getLottery_kfMoney() > 0) {
            showAnimNumDialog(false);
            return;

        } else {
            if (redPacketValue >= 1) {

                if (mTXCJzhongjiangData.getExtract_money() > 0) { //真实到账的
                    tv2Str = Html.fromHtml("恭喜成功领到<font color='#FDCC21'>" + DateUtils.dePoint("#0.00", redPacketValue) +
                            "</font>元现金。已提现至微信零钱。请注意查收。");

                } else {
                    tv2Str = Html.fromHtml("恭喜提现<font color='#FDCC21'>" + DateUtils.dePoint("#0.00", redPacketValue) +
                            "</font>元现金。可立即发放至微信零钱。请注意查收。");

                    tv_tx.setText("立即提现");


                }

            } else {
                tv2Str = Html.fromHtml("恭喜成功领到<font color='#FDCC21'>" + DateUtils.dePoint("#0.00", redPacketValue) +
                        "</font>元已提现，本次提现未满1元，会在累计满1元时直接打入您的微信零钱，请注意查收！");
            }
        }

        if (isFromNewWallet && mTXCJzhongjiangData.getExtract_money() > 0) {//有发放金额并且是从新钱包进来的单独做(进来的首次抽奖)
            tv2Str = Html.fromHtml("恭喜成功提现<font color='#FDCC21'>" + DateUtils.dePoint("#0.00", redPacketValue) +
                    "</font>元，已到账，清注意查收。您已累计提现<font color='#FDCC21'>" + showNewWalletTXallMoney +
                    "</font>元。");
            tv4.setText("每天来做赚钱小任务完成打卡进度，可以持续提现哦！");
            tv4.setVisibility(View.VISIBLE);

        }


        //有发放金额的 标题显示翻倍的
        if (mTXCJzhongjiangData.getExtract_money() > 0) {

            if (isFromNewWallet) { //新钱包进来的首次抽奖不显示翻倍
                tv1.setText(showNewWalletTXallMoney + "元");
            } else {
                double titleMoneystr = mTXCJzhongjiangData.getRaffle_money() * mTXCJzhongjiangData.getMultiple();
                tv1.setText(DateUtils.dePoint("#0.00", titleMoneystr) + "元");

            }

        }

        tv2.setText(tv2Str);


        view.findViewById(R.id.tv_tx).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {//继续提现
                mDialog.dismiss();


                if (mTXCJzhongjiangData.getExtract_money() > 0 && mTXCJzhongjiangData.getExtract_money() < 1) {


//                    tv_bottom.setText("注：因微信规定，本日已提现的" + redPacketValue + "元不满1元，将在积累满1元后直接打入您的微信零钱，请注意查收。");
//                    tv_bottom.setVisibility(View.VISIBLE);

                    //发放金额小于1元的弹窗
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


                    continueTXfromDialogClick();//小程序的startLuckBtn


                }


            }
        });


        // // 创建自定义样式dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();
    }


    private void redPacketOpenedRealYJ() {//显示佣金中奖弹窗
        if (null != mDialog) {
            mDialog.dismiss();
        }


        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.dialog_withdraw_wx_daozhang_real_yj, null);

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv_time = view.findViewById(R.id.tv_time);
        TextView tv_tx = view.findViewById(R.id.tv_tx);//立即提现
        TextView tv_tx2 = view.findViewById(R.id.tv_tx2);//继续提现


//        tv1.setText(DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getYj_money()) + "元");
        tv1.setText(mTXCJzhongjiangData.getYj_money() + "元");


//        if ((mCjtXcountData.getIsVip() == 0 || mCjtXcountData.getIsVip() == 3) && mTXCJzhongjiangData.getExtract_money() > 0) {
//            tv3.setText("¥ " + DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getExtract_money()));
//        } else {
//        tv3.setText("¥ " + DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getYj_money()));

        tv3.setText("¥ " + mTXCJzhongjiangData.getYj_money());


//        }

        Spanned tv2Str = new SpannableString("测试");

        tv2Str = Html.fromHtml("您已累积<font color='#FDCC21'><strong>" + mTXCJzhongjiangData.getYj_money() +
                "</strong></font>元佣金，可立即<font color='#FDCC21'><strong>提现至微信零钱</strong></font>。");

        tv2.setText(tv2Str);


        long countTime = 9 * 60 * 1000;
        if (null != mTXCJzhongjiangData) {
            countTime = mTXCJzhongjiangData.getExpireTime();
        }
        //这里的佣金金额暂时写死，后面再改----直接用抽到
        countDownTimer = new SimpleCountDownTimer(countTime, tv_time, true, mTXCJzhongjiangData.getYj_money() + "元").setOnFinishListener(new SimpleCountDownTimer.OnFinishListener() {
            @Override
            public void onFinish() {
                if (null != mDialog) {
                    mDialog.dismiss();
                }
            }
        });
        countDownTimer.start();

        view.findViewById(R.id.tv_tx).setOnClickListener(new OnClickListener() {//立即提现
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MyYJactivity.class);
                startActivity(intent);


            }
        });

        view.findViewById(R.id.tv_tx2).

                setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {//继续提现
                        mDialog.dismiss();
                        continueTXfromDialogClick();//小程序的startLuckBtn
                    }
                });


        // // 创建自定义样式dialog
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
            tv_money.setText(redPacketValue * 5 + "元");

        } else {
            double titleMoneystr = mTXCJzhongjiangData.getRaffle_money() * mTXCJzhongjiangData.getMultiple();
            tv_money.setText(DateUtils.dePoint("#0.00", titleMoneystr) + "元");

        }


        bt2.setVisibility(View.GONE);
        bt1.setText("继续提现");


        Spanned sd1;
        if (virtualFinish) {


            sd1 = Html.fromHtml("注：因微信规定，不满<font color='#FDCC21'>"
                    + 1 + "</font>元无法发放至微信零钱，本次发放的<font color='#FDCC21'><strong>"
                    + redPacketValue + "</strong></font>元已存入您的衣蝠钱包。将在累积<font color='#FDCC21'><strong>"
                    + "满1元后直接发放入微信零钱" + "</strong></font>，请继续提现。"
            );

        } else {
            sd1 = Html.fromHtml("注：因微信规定，不满<font color='#FDCC21'>"
                    + 1 + "</font>元无法发放至微信零钱，本次发放的<font color='#FDCC21'><strong>"
                    + mTXCJzhongjiangData.getExtract_money() + "</strong></font>元已存入您的衣蝠钱包。将在累积<font color='#FDCC21'><strong>"
                    + "满1元后直接发放入微信零钱" + "</strong></font>，请继续提现。"
            );

        }

        tv1.setText(sd1);


        bt1.setOnClickListener(new OnClickListener() {//成为会员
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                if (virtualFinish) {
                    //查询剩余次数
                    queryCount(false);
                } else {
                    continueTXfromDialogClick();//小程序的startLuckBtn

                }


            }
        });


        icon_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });


        // // 创建自定义样式dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();

    }


    //弹窗上继续提现的点击
    private void continueTXfromDialogClick() {

        if (choujiangCount <= 0) {//次数用完了


            //如果轮数大于0要重新查询次数并开始抽奖
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

                        //重新查中奖情况并开始抽奖
                        clickStart();


                    }

                    @Override
                    public void onError() {

                    }
                });


            } else { // 次数轮数都没有了

                noCountToPage();


//                boolean mIsVip = CommonUtils.isVip(mCjtXcountData.getIsVip(), mCjtXcountData.getMaxType());
//                if (mIsVip) {
//                    showBuyVipSucCloseChoujiangDialog();
//                    return;
//                }
//
//                if (Double.parseDouble(redPacketValue_totaldata) > 0) {
//                    coujizngCountUseUpdialog();
//                    if (mCjtXcountData.getIs_finish() != 0) {//此时当前20次刚好全部抽完
//                        SharedPreferencesUtil.saveBooleanData(mContext, "20Choujiang_finish", true);
//                    }
//                } else {
//                    showNoHasCountDialog();
//
//                }
            }


        } else {//还有次数

            if (mTXCJzhongjiangData.getRaffle_money() >= 15) {
                virtualXJdialog();
            } else {
                clickStart();
            }


        }


//        clickStart();


    }

    //虚拟中奖弹窗--引导开会员
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

        tv_money.setText(redPacketValue_totaldata + "元");

        Spanned sd = new SpannableString("测试");

        if (mCjtXcountData.getIs_finish() == 1) {

            sd = Html.fromHtml("本次发放超过<font color='#FDCC21'><strong>"
                    + 15 + "</strong></font>元，<font color='#FDCC21'><strong>"
                    + "成为会员才能发放到零钱</strong></font>。"
            );


        } else {
            sd = Html.fromHtml("非会员只能提现一次，<font color='#FDCC21'><strong>"
                    + "成为会员才能继续提现</strong></font>。"
            );
        }


//        }
        tv1.setText(sd);

        Spanned sd2 = new SpannableString("测试");

        if (mCjtXcountData.getIs_finish() == 1) {
            sd2 = Html.fromHtml("本日单次超过15元的提现已累计<font color='#FDCC21'><strong>"
                    + redPacketValue_totaldata + "</strong></font>元,<font color='#FDCC21'><strong>成为会员</strong></font>后可<font color='#FDCC21'><strong>"
                    + "立即提现" + "</strong></font>！");
        } else {
            sd2 = Html.fromHtml("本日可提现金额已累计<font color='#FDCC21'><strong>"
                    + redPacketValue_totaldata + "</strong></font>元,<font color='#FDCC21'><strong>成为会员</strong></font>后可<font color='#FDCC21'><strong>"
                    + "全部提现" + "</strong></font>！");
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
            bt2.setText("离开");
            yindaoVip = true;

        } else {
            bt2.setText("继续提现");

        }


        bt1.setOnClickListener(new OnClickListener() {//成为会员
            @Override
            public void onClick(View view) {
//                mDialog.dismiss();
                startActivityForResult(new Intent(SignDrawalLimitActivity.this, MyVipListActivity.class), 1003);

            }
        });


        bt2.setOnClickListener(new OnClickListener() {//小程序中laststartLuckBtn
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


        // // 创建自定义样式dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();

    }

    //抽奖次数用完的dialog
    private void coujizngCountUseUpdialog() {//这里不弹窗了 直接跳走
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }

        noCountToPage();
        //如果是非会员并且不是前20次，或者是钻石会员就去余额抽提现界面
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
//        tv_money.setText(redPacketValue_totaldata + "元");
//
//        //测试
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
////            sd1 = Html.fromHtml("今日赠送的<font color='#FDCC21'>"
////                    + 10 + "</font>次提现机会已用完，剩余<font color='#FDCC21'>"
////                    + 40 + "</font>次提现明日开始，连续4天每日赠送10次。记得明天一定要来完成赚钱任务哦。");
//
//            sd1 = Html.fromHtml("赠送的<font color='#FDCC21'><strong>"
//
//
//                    + 50 + "</strong></font>次提现里，<font color='#FDCC21'><strong>"
//
//                    + 10 + "</strong/></font>次提现机会已用完，剩余<font color='#FDCC21'><strong>"
//
//
//                    + 40 + "</strong></font>次明天开始，连续4天每日赠送10次。记得一定要来哦。"
//
//
//            );
//
//
//            tv1.setText(sd1);
//
//
//            Spanned sd2;
//            sd2 = Html.fromHtml("你已累计提现<font color='#FDCC21'><strong>"
//                    + redPacketValue_totaldata + "</strong/></font>元及领到<font color='#FDCC21'><strong>"
//                    + "79元美衣" + "<strong/></font>。成为会员后提现可立即<font color='#FDCC21'><strong>"
//                    + "发放到微信零钱" + "</strong></font>，79元美衣<font color='#FDCC21'><strong>"
//                    + "免费发货" + "</strong></font>。"
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
//                    + dayall_count + "</strong></font>次提现机会已用完，累计提现<font color='#FDCC21'><strong>"
//
//                    + redPacketValue_totaldata + "</strong></font>元及领到<font color='#FDCC21'><strong>"
//
//                    + "79元美衣" + "</strong></font>。");
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
//            Spanned sd2 = Html.fromHtml("成为会员后提现可立即<font color='#FDCC21'><strong>"
//
//                    + "发放到微信零钱，" + "</strong></font>79元美衣<font color='#FDCC21'><strong>"
//
//                    + "免费发货" + "</strong></font>。");
//            tv2.setText(sd2);
//            tv2.setVisibility(View.VISIBLE);
//
//
//        }
//
//
//        //买过提现卡显示50次，没买过就显示10次
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
//        bt1.setOnClickListener(new OnClickListener() {//成为会员
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
//        // // 创建自定义样式dialog
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
//        //测试
////        mCjtXcountData.setTixian_count(1);
////        mCjtXcountData.setTixian_twoCount(1);
//
//
//        //买过提现卡显示50次，没买过就显示10次
//        if (mCjtXcountData.getTixian_count() == 1) {
//            bt2.setBackgroundResource(R.drawable.give_fifty_tixian_coupon);
//        }
//        //买过2次提现卡就隐藏按钮
//        if (mCjtXcountData.getTixian_twoCount() == 1) {
//            bt2.setVisibility(View.GONE);
//        }
//
//        tv_money.setText(redPacketValue_totaldata + "元");
//
//
//        Spanned sd;
//        sd = Html.fromHtml("今日累计提现<font color='#FDCC21'>"
//                + redPacketValue_totaldata + "</font>元，成为会员可立即发放至微信零钱。");
//
//
//        tv1.setText(sd);
//
//
//        bt1.setOnClickListener(new OnClickListener() {//成为会员
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(SignDrawalLimitActivity.this, MyVipListActivity.class));
//
//            }
//        });
//
//
//        bt2.setOnClickListener(new OnClickListener() {//小程序中laststartLuckBtn
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
//        // // 创建自定义样式dialog
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
                //去赚钱
//                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
//                Intent intent = new Intent(mContext, CommonActivity.class);
//                startActivity(intent);
//                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//                finish();

//                }
                //查询剩余次数
//                queryCount(false, false);
//                FaFangLessThan1yuan(true);


                //首张钻石卡的处理
                YConn.httpPost(mContext, YUrl.FIRST_ZUANSHI_ZHUANPAIN_TISHI,
                        new HashMap<String, String>(), new HttpListener<BaseData>() {
                            @Override
                            public void onSuccess(BaseData result) {
                                loading.dismiss();

                                if (result.getIsPopup() == 1) {
                                    showBuyVipSucDialog(result);

                                } else {
                                    //查询剩余次数
                                    queryCount(false);

                                }

                            }

                            @Override
                            public void onError() {

                            }
                        });


                //查询剩余次数
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
     * 获取数据  奖励列表   额度
     */
    private void initLimitAwardsList() {

        for (int i = 0; i < 50; i++) {
            addToLimitList();
        }

        if (fromFreeBuy || fromPT) { //每隔3条插1条免费领的


            ArrayList<String> sedLeim = new ArrayList<>();//二级类目集合

            ArrayList<String> allSubList = new ArrayList<>();//所有品牌集合
            YDBHelper dbHelp = new YDBHelper(this);


            //所有的二级类目


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
                    String ramSubName = allSubList.get((int) (Math.random() * allSubList.size()));////随机品牌
                    String ramLeim = sedLeim.get((int) (Math.random() * sedLeim.size()));//随机二级类目
                    map1.put("p_name", "免费领走了" + ramSubName + ramLeim);
                    map1.put("pic", "defaultcommentimage/" + StringUtils.getDefaultImg());

                    //1-500随机数
                    String ram500 = StringUtils.getRandomInt(100, 400) + ".0";
                    map1.put("num", "原价" + ram500 + "元");

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
     * 添加虚拟数据到 额度奖励集合
     */
    private void addToLimitList() {
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("nname", StringUtils.getVirtualName() + "***" + StringUtils.getVirtualName());
        map1.put("p_name", "获得了提现现金");
        map1.put("pic", "defaultcommentimage/" + StringUtils.getDefaultImg());

        map1.put("num", "+" + StringUtils.getRandomInt(8, 16) + StringUtils.getVirtualDecimalAwardsWithdrawal() + "元");// 8 -16

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
     * 滚动的 奖励列表  的 Adapter
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
                        tv_txk_countdown.setText(hours + ":" + minutes + ":" + seconds + "后失效");
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
        } else if (redPacketValue >= 500 && redPacketValue <= 1000) {//免费领
            awardIndex = 2;
        }

        return awardIndex;

    }

    @Override
    protected void onDestroy() {

        EventBus.getDefault().unregister(this);//取消注册

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

                        if (result.getIsPopup() == 1) {//钻石弹窗
                            showBuyVipSucDialog(result);
                            return;
                        }

                        if (result.getIsPopup() == 2) {//皇冠弹窗
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
//                        tv_money.setText(DateUtils.dePoint("#0.00", mTXCJzhongjiangData.getRaffle_money()) + "元");
//                        tv_money.getPaint().setFakeBoldText(false);
//                        bt2.setVisibility(View.GONE);
//                        bt1.setText("离开");
//
//
//                        Spanned sd1;
//                        sd1 = Html.fromHtml("钻石会员<font color='#FDCC21'><strong>"
//                                + "原价预存169元</strong></font>赠送，首次成为会员会优先抵扣已有的提现。您有<font color='#FDCC21'><strong>"
//                                + vipDikou + "元被抵扣</strong></font>，仅<font color='#FDCC21'><strong>79元</strong></font>即成为会员。"
//                        );
//
//
//                        tv1.setText(sd1);
//
//
//                        Spanned sd2;
//                        sd2 = Html.fromHtml("您已是会员，接下来提现<font color='#FDCC21'><strong>"
//                                + "不再受15元金额限制</strong></font>，直接发放入微信零钱。"
//                        );
//                        tv2.setText(sd2);
//                        tv2.setVisibility(View.VISIBLE);
//
//
//                        bt1.setOnClickListener(new OnClickListener() {//成为会员
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
//                        // // 创建自定义样式dialog
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


        Spanned tv2Str = Html.fromHtml("累计满<font color='#FDCC21'>" + 10 +
                "</font>元，立即<font color='#FDCC21'>发放到微信零钱</font>！请继续提现");
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

        tv3.setText("¥ " + DateUtils.dePoint("#0.00", 10.00) + "");
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
        // // 创建自定义样式dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();
    }


    private void noCountToPage() {

        Intent intent;
        if (is_finishCome == 1) {//前20次没有抽完进来的--去赚钱
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
        } else {//其他
            intent = new Intent(mContext, WithdrawalLimitActivity.class);
        }
        startActivity(intent);
        ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

        finish();


    }
}
