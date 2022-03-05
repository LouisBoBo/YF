package com.yssj.ui.activity.vip;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.entity.AddVipResult;
import com.yssj.entity.BaseData;
import com.yssj.entity.DaoJuKaUpZuanshiData;
import com.yssj.entity.UserInfo;
import com.yssj.entity.VipPriceData;
import com.yssj.eventbus.MessageEvent;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.HomePageThreeActivity;
import com.yssj.ui.activity.OneBuyGroupsDetailsActivity;
import com.yssj.ui.activity.SignDrawalLimitActivity;
import com.yssj.ui.activity.WithdrawalLimitActivity;
import com.yssj.ui.activity.shopdetails.PaymentActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.WaitDialog;
import com.yssj.ui.fragment.orderinfo.OrderInfoFragment;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DateUtils;
import com.yssj.utils.DialogUtils;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAppUtil;
import com.yssj.utils.YCache;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MyVipListActivity extends BasicActivity implements BannerLayout.OnBannerItemClickListener, BannerLayout.OnBannerScrollChangeListener {
    BannerLayout recyclerBanner;

    @Bind(R.id.iv_friend)
    ImageView ivFriend;
    @Bind(R.id.iv_friend_close)
    ImageView ivFriendClose;
    @Bind(R.id.rl_friend)
    RelativeLayout rlFriend;
    @Bind(R.id.tvTitle_base)
    TextView tvTitleBase;
    @Bind(R.id.tv_pay_single_money)
    TextView tvPaySingleMoney;
    @Bind(R.id.tv_count)
    TextView tvCount;
    @Bind(R.id.iv_count_jian)
    ImageView ivCountJian;
    //    @Bind(R.id.rl_count)
//    RelativeLayout rlCount;
    @Bind(R.id.tv_pay_name)
    TextView tvPayName;

    @Bind(R.id.tv_go_pay)
    TextView tvGoPay;

    @Bind(R.id.ll_vip_right)
    LinearLayout llVipRight;
    //    @Bind(R.id.tv_re_count_str)
//    TextView tvReCountStr;
//    @Bind(R.id.tv_re_count_text)
//    TextView tvReCountText;
    @Bind(R.id.tv_tixian_day)
    TextView tvTixianDay;
    @Bind(R.id.tv_use_jinglijin)
    TextView tvUseJinglijin;
    @Bind(R.id.tv_total_pay_money_shifu)
    TextView tvTotalPayMoneyShifu;
    @Bind(R.id.tv_total_pay_money_yuanjia)
    TextView tvTotalPayMoneyYuanjia;
    @Bind(R.id.ll_wenhao)
    LinearLayout llWenhao;
    @Bind(R.id.tv_daojuka_tishi)
    TextView tvDaojukaTishi;
    @Bind(R.id.iv_count_jia)
    ImageView ivCountJia;
    @Bind(R.id.rl_jiangjin)
    RelativeLayout rlJiangjin;
    @Bind(R.id.tv_center_price_shuoming1)
    TextView tvCenterPriceShuoming1;
    //    @Bind(R.id.tv_center_price_shuoming2)
//    TextView tvCenterPriceShuoming2;
    @Bind(R.id.iv_fanhuan_wen)
    ImageView ivFanhuanWen;
    @Bind(R.id.iv_kefu)
    ImageView ivKefu;
    @Bind(R.id.iv_kefu_text)
    ImageView ivKefuText;


    @Bind(R.id.ll_bot_to_pay)
    LinearLayout llBotToPay;
    @Bind(R.id.tv_total_pay_money_shifu_hg)
    TextView tvTotalPayMoneyShifuHg;
    @Bind(R.id.tv_go_pay_hg)
    TextView tvGoPayHg;
    @Bind(R.id.rl_bot_to_pay_hg)
    RelativeLayout rlBotToPayHg;
    @Bind(R.id.tv_total_pay_money_shifu_hg_bot)
    TextView tvTotalPayMoneyShifuHgBot;
    @Bind(R.id.tv_buy_yuanjia_bot)
    TextView tvBuyYuanjiaBot;
    @Bind(R.id.ll_kefu)
    LinearLayout llKefu;


    private List<VipListBean.ViplistBean> vipList; //会员卡列表
    private List<VipListBean.UserVipListBean> userVipList;//用户已办会员卡列表
    private MyVipListActivity mContext;

    private List<VipListBean.ViplistBean> initialVipList; //用户初始的列表（用来确定偏移量）

    private int vipListSize;
    public static int buyVipCount = 1;

    private String payVcode;

//    private ArrayList<Spanned> arrvipType4Right;
//    private ArrayList<Spanned> arrvipType5Right;
//    private ArrayList<Spanned> arrvipType6Right;


//    private ArrayList<Spanned> vipShouMingSpdList;


    private int maxVipTypePos = -1;//最高级别的角标

    private int fromType;
    public static double balance = 0;//用户已存的总金额


//    public int viewCurrentPos; //显示的中间位置


    private int firstCardShowPostion;//首张卡的显示位置


//    private int tempPayPos = -1;

    private int temPayVipType = -1;

    private VipListBean.ViplistBean currentVip;//当前选中的vip

    private VipListBean vipListData;


    public static String shifuPirce;
    public static String yuanjiaPrce;

    private boolean guide_txk;//是否是抽提现引导用户办理体现卡进来的

    private int guide_vipType = 0;//引导购买会员的vipType;

    private VipPriceData mVipPriceData;//当前卡的价格信息

    private boolean buyMPKsuc;//是否成功买到免拼卡
    private boolean buyFHKsuc;//是否成功买到发货卡
    private boolean isNewUserGuideVIP;
    private boolean isGuideFHK;//发货卡已去掉
    private boolean isGuideMPK;//免拼卡已去掉

    private long zuanshiTime = 30 * 60 * 1000;
    private AddVipResult.DataBean paySuccessDialogData;

    private boolean buyTXKsuc;//是否成功买到提现卡


    private int vipUpdateVipType = -1;//会员升会员需要的等级

    private boolean isFirstCashcard = true; //是否是首张提现卡

    public static String zuanShiDikou;
    private boolean is20ciBuyVip;
    public int isFromSign2Round;
    public int isFromSign2RoundFirst;
    private boolean buyVipSuccess;

    public static String showBuySucMessage;
    private WaitDialog waitDialog;


    @Override
    public void onBackPressed() {

        if (!StringUtils.isEmpty(zuanShiDikou) || buyVipSuccess) {
//            setResult(SignDrawalLimitActivity.BUG_VIP_SUCCESS);

            MessageEvent messageEvent = new MessageEvent();
            messageEvent.setEventBuyVipSucVipType(currentVip.getVip_type());
            EventBus.getDefault().post(messageEvent);

            finish();

        }

        if (isGuideFHK && buyFHKsuc) {
            OrderInfoFragment.mBuyFHKsuc = true;
            finish();
            return;
        }

        if (isGuideMPK && buyMPKsuc) {
            setResult(OneBuyGroupsDetailsActivity.BUG_MPK_SUCCESS);
            finish();
            return;
        }

        if (guide_txk && !buyTXKsuc) {

            showGuideTXKnoBuyDailog(isFirstCashcard);

            return;

        }

        super.onBackPressed();
    }

    private void showGuideTXKnoBuyDailog(boolean isFirstCashcard) {


        LayoutInflater mInflater = LayoutInflater.from(mContext);
        final Dialog deleteDialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_guide_txk_no_buy, null);

        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv4 = view.findViewById(R.id.tv4);
        Button btn_ok = view.findViewById(R.id.btn_ok);

        if (isFirstCashcard) {
            tv3.setText("保底6元，最高200元");
        } else {
            tv3.setText("保底25元，最高200元");
            btn_ok.setText("立即领取200元提现");
        }
        tv4.setText(Html.fromHtml("<font color='#FF0000'><strong>立即打入微信零钱</strong></font>的提现机会吗?"));


        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (null != CommonActivity.instance) {
                    CommonActivity.instance.finish();
                }

                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
                Intent intent = new Intent(mContext, CommonActivity.class);
                startActivity(intent);

                deleteDialog.dismiss();
                finish();
            }
        });


        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
                guide_txk = true;
                getVipData();

            }
        });


        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ToastUtil.showDialog(deleteDialog);


    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonUtils.disableScreenshots(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        showBuySucMessage = null;
        waitDialog = new WaitDialog(this, R.style.DialogStyle1);

        if (GuideActivity.needFengKong) {
            ToastUtil.showMyToast(mContext, "奖励已进入账户，祝您购物愉快。", 3000);
            finish();
            return;
        }

        if(YCache.getCacheUser(mContext).getReviewers() == 1){
            llKefu.setVisibility(View.GONE);
        }
//测试代码
//        paySuccessDialogData = new AddVipResult.DataBean();
//        paySuccessDialogData.setVip_type(5);
//        showPaySuccessDialog();


        setContentView(R.layout.activity_myviplist);
        ButterKnife.bind(this);
//        viewCurrentPos = 50 * 3;
        buyVipCount = 1;
        fromType = getIntent().getIntExtra("fromType", 0);
        isFromSign2Round = getIntent().getIntExtra("isFromSign2Round", 0);
        isFromSign2RoundFirst = getIntent().getIntExtra("isFromSign2RoundFirst", 0);

        vipUpdateVipType = getIntent().getIntExtra("vipUpdateVipType", -1);
        guide_vipType = getIntent().getIntExtra("guide_vipType", -1);
        guide_txk = getIntent().getBooleanExtra("guide_txk", false);
        is20ciBuyVip = getIntent().getBooleanExtra("is20ciBuyVip", false);
        isNewUserGuideVIP = getIntent().getBooleanExtra("isNewUserGuideVIP", false);
//        isGuideFHK = getIntent().getBooleanExtra("isGuideFHK", false);
//        isGuideMPK = getIntent().getBooleanExtra("isGuideMPK", false);

        tvTitleBase.setText("选择会员卡类型");
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        recyclerBanner = findViewById(R.id.recycler);

        if (is20ciBuyVip) {
            ToastUtil.showMyToast(mContext, "成为会员后，立即提现10元！且同时享受钻石会员的四大特权！！", 4000);
        }

        if (isFromSign2RoundFirst == 1) {
            showIsFromSign2RoundFisrtDialog();
        }

        if (isFromSign2Round == 1) {
            ToastUtil.showMyToast(mContext, "非会员只能领1个任务，钻石会员每日可领5个任务。", 3000);

        }

        if (YJApplication.instance.isLoginSucess()) {
            HashMap<String, String> pairsMap = new HashMap<>();
            YConn.httpPost(mContext, YUrl.NEED_JUM_FREE_LING, pairsMap
                    , new HttpListener<BaseData>() {
                        @Override
                        public void onSuccess(BaseData baseData) {
                            if (baseData.getIsJumpPage() == 1) {
                                if(null != HomePageThreeActivity.instance){
                                    HomePageThreeActivity.instance.finish();
                                }
                                startActivity(new Intent(MyVipListActivity.this, HomePageThreeActivity.class)
                                        .putExtra("buyVipFreeBuy", true)
                                        .putExtra("freeBuyType", 2)
                                        .putExtra("freeOrderPage", baseData.getFreeOrderPage())
                                        .putExtra("freeMoney", baseData.getFreeMoney() + "")

                                );
                                mContext.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                            } else {

                                showVipToast();
                                getVipData();
                            }

                        }

                        @Override
                        public void onError() {

                        }
                    });
        }

        initKefu();


    }

    private void showIsFromSign2RoundFisrtDialog() {


        LayoutInflater mInflater = LayoutInflater.from(this);
        final Dialog deleteDialog = new Dialog(this, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_one_text_one_button, null);


        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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

    private void initKefu() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ivKefuText.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ivKefuText.setVisibility(View.GONE);

                    }
                }, 3000);
            }
        }, 2000);


    }

    private void showVipToast() {

        String vipToastStr = null;

        switch (fromType) {
            case -1:
                vipToastStr = "因会员卡费不足会员资格已失效，现在开通新卡立即赠送一件399元的美衣哦。";
                break;
            case -2:
                vipToastStr = "尊敬的衣蝠会员，您今日的会员卡免费领次数已使用完，购买新的会员卡后即可继续免费领";
                break;
            case -3:
                vipToastStr = "尊敬的衣蝠会员，您的会员卡费已被全额用于购买商品，请重新购买会员卡。";
                break;
        }
        if (null != vipToastStr) {
            ToastUtil.showMyToastProgress(this, vipToastStr, 4000);

        }


    }


    private void getVipData() {

        HashMap<String, String> pairsMap = new HashMap<>();
        pairsMap.put("jump", "1");
        if (isFromSign2RoundFirst == 1 || isFromSign2Round == 1) {
            pairsMap.put("isFromSign2Round", "1");
        }

        YConn.httpPost(this, YUrl.VIP_LIST, pairsMap, new HttpListener<VipListBean>() {

            @Override
            public void onSuccess(VipListBean mResult) {
                vipListData = mResult;
                if (mResult.getFirstCashcard() == 1) {
                    isFirstCashcard = true;
                } else {
                    isFirstCashcard = false;
                }

                vipList = mResult.getViplist();
                userVipList = mResult.getUserVipList();

                if (!StringUtils.isEmpty(mResult.getBalance())) {
                    balance = Double.parseDouble(mResult.getBalance());
                }

                //合并数据
                for (VipListBean.ViplistBean vipListBean : vipList) {
                    for (VipListBean.UserVipListBean userVipListBean : userVipList) {
                        if (vipListBean.getVip_type() == userVipListBean.getVip_type()) {
                            vipListBean.setArrears_price(userVipListBean.getArrears_price());
                            vipListBean.setVip_balance(userVipListBean.getVip_balance());
                            vipListBean.setVip_num(userVipListBean.getVip_num());
                            vipListBean.setVip_code(userVipListBean.getVip_code());
                            vipListBean.setNum(userVipListBean.getNum());
                            vipListBean.setCount(userVipListBean.getCount());
                            vipListBean.setContext(userVipListBean.getContext());
                            vipListBean.setSubstance(userVipListBean.getSubstance());


                        }
                    }
                    vipListBean.setInfo_url(vipListBean.getInfo_url() + "?" + new Random().nextInt(100000));
                }
                vipListSize = vipList.size();


                initialVipList = vipList;
                List<VipListBean.ViplistBean> tempList = new ArrayList<>();
                for (int i = 0; i < 100; i++) {
                    tempList.addAll(vipList);
                }


                //找到首张卡在200-210中的位置
                int firstCardVipType = -1;
                for (int i = 0; i < initialVipList.size(); i++) {
                    firstCardVipType = initialVipList.get(0).getVip_type();
                }
                for (int i = 0; i < tempList.size(); i++) {
                    if (i > 200 && i < 210) {
                        if (tempList.get(i).getVip_type() == firstCardVipType) {
                            firstCardShowPostion = i;
                            break;
                        }
                    }
                }


                vipList = tempList;

                VipCardListAdapter vipCardListAdapter = new VipCardListAdapter(mContext, vipList);
                recyclerBanner.setAdapter(vipCardListAdapter);

                vipCardListAdapter.setOnBannerItemClickListener(mContext);
                recyclerBanner.setBannerScrollChangeListener(mContext);
                recyclerBanner.setVisibility(View.VISIBLE);


                int tempIndex = 0;

                //默认停留在钻石卡
                for (int i = 0; i < initialVipList.size(); i++) {
                    if (initialVipList.get(i).getVip_type() == 4) {
                        tempIndex = i;
                    }
                }

                //确定后台给的落地页
                for (int i = 0; i < initialVipList.size(); i++) {
                    if (initialVipList.get(i).getVip_type() == vipListData.getLandPage()) {
                        tempIndex = i;
                    }
                }


                //找出已办的最高级别会员卡的角标
//                for (int i = 0; i < initialVipList.size(); i++) {
//                    if (!StringUtils.isEmpty(vipList.get(i).getVip_code())) {
//                        if (initialVipList.get(i).getVip_type() > maxVipTypePos) {
//                            maxVipTypePos = i;
//                        }
//                    }
//                }


//                if (maxVipTypePos != -1) {
//                    tempIndex = maxVipTypePos;
//                }

//                if (isNewUserGuideVIP) { //新用户引导会员停钻石卡
//                    for (int i = 0; i < initialVipList.size(); i++) {
//                        if (initialVipList.get(i).getVip_type() == 4) {
//                            tempIndex = i;
//
//                        }
//                    }
//
//                }


                if (guide_txk) {
                    for (int i = 0; i < initialVipList.size(); i++) {
                        if (initialVipList.get(i).getVip_type() == 7) {
                            tempIndex = i;

                        }
                    }
                }

                //升级会员卡到对应的等级
                if (vipUpdateVipType != -1) {
                    for (int i = 0; i < initialVipList.size(); i++) {
                        if (initialVipList.get(i).getVip_type() == vipUpdateVipType) {
                            tempIndex = i;

                        }
                    }
                }

                if (guide_vipType > 0) {
                    for (int i = 0; i < initialVipList.size(); i++) {
                        if (initialVipList.get(i).getVip_type() == guide_vipType) {
                            tempIndex = i;
                        }
                    }
                }


                if (isGuideFHK) {
                    for (int i = 0; i < initialVipList.size(); i++) {
                        if (initialVipList.get(i).getVip_type() == 8) {
                            tempIndex = i;

                        }
                    }
                }
                if (isGuideMPK) {
                    for (int i = 0; i < initialVipList.size(); i++) {
                        if (initialVipList.get(i).getVip_type() == 9) {
                            tempIndex = i;

                        }
                    }
                }


                //初始展示支付成功展示
//                if (tempPayPos != -1) {//支付回来停留
//                    tempIndex = tempPayPos % vipListSize;
//
//                }
                //支付成功展示的位置
                if (temPayVipType > 0) {
                    for (int i = 0; i < initialVipList.size(); i++) {
                        if (initialVipList.get(i).getVip_type() == temPayVipType) {
                            tempIndex = i;

                        }
                    }
                }

//                int relIndex = viewCurrentPos + (viewCurrentPos % initialVipList.size() + tempIndex);

                int relIndex = firstCardShowPostion + tempIndex;

                recyclerBanner.scrollToPosition(relIndex);


                initOtherData(relIndex);


            }

            @Override
            public void onError() {

            }
        });


    }


    @Override
    public void onScrollChange(int position) {
        initOtherData(position);

    }

    private void initPayText() {


        HashMap<String, String> pairsMap = new HashMap<>();
        pairsMap.put("vip_type", currentVip.getVip_type() + "");
        pairsMap.put("vip_count", buyVipCount + "");

        YConn.httpPost(this, YUrl.QUERY_CURRENT_VIP_PRICE_DATA, pairsMap, new HttpListener<VipPriceData>() {
            @Override
            public void onSuccess(VipPriceData result) {


                mVipPriceData = result;


//                tvPaySingleMoney.setText("¥" + currentVip.getVip_price() + "");//预存

                tvPaySingleMoney.setText("¥" + result.getActual_price() + "");//预存


//                tvTotalPayMoneyShifu.setText("限时特惠¥：" + result.getActual_price());


                tvTotalPayMoneyShifuHg.setText(result.getContent1() + "");


//                tvTotalPayMoneyYuanjia.setText("原价" + currentVip.getOriginal_vip_price() * buyVipCount);
                tvBuyYuanjiaBot.setText("原价¥" + result.getOriginal_price());


                shifuPirce = DateUtils.dePoint("#0.00", result.getActual_price());//原价

                yuanjiaPrce = currentVip.getOriginal_vip_price() * buyVipCount + ""; //实付价


//                tvTotalPayMoneyYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvBuyYuanjiaBot.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);


                tvCount.setText(buyVipCount + "");

                //确定加减号颜色
                //加号
                if (currentVip.getPurchase_maxNum() == -1 || buyVipCount < currentVip.getPurchase_maxNum()) {
                    ivCountJia.setImageDrawable(getResources().getDrawable(R.drawable.vip_couot_jia_new));
                } else {
                    ivCountJia.setImageDrawable(getResources().getDrawable(R.drawable.vip_couot_jia_new_end));

                }

                //减号
                if (buyVipCount > currentVip.getPurchase_num()) {
                    ivCountJian.setImageDrawable(getResources().getDrawable(R.drawable.vip_couot_jian_new));
                } else {
                    ivCountJian.setImageDrawable(getResources().getDrawable(R.drawable.vip_couot_jian_end_new));

                }


                tvUseJinglijin.setText("-¥" + result.getFixMoney());


                if (result.getReMoney() > 0 && currentVip.getVip_type() == 4) {

                    Spanned tempSpdDaoju = Html.fromHtml("您已预存<font color='#ff3f8b'>" +
                            result.getReMoney() + "</font>元道具卡，仅需再预存<font color='#ff3f8b'>" +
                            result.getActual_price() + "</font>元即可成为钻石会员");
                    tvDaojukaTishi.setText(tempSpdDaoju);


                    tvDaojukaTishi.setVisibility(View.VISIBLE);
                } else {
                    tvDaojukaTishi.setVisibility(View.GONE);

                }

                //中间部分的内容显示和隐藏（加减号附近）
                setVisibilityCenter();

            }

            @Override
            public void onError() {
            }
        });


    }

    private Timer timer;

    private void initOtherData(int position) {

        currentVip = vipList.get(position);

        if (temPayVipType == -1 && vipListData.getTwoDiamondCard() == 1 && currentVip.getVip_type() == 4) {


            if (zuanshiTime <= 1000) {
                if (null != timer) {
                    timer.cancel();
                }
                return;
            }


            if (null == timer) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (zuanshiTime >= 1000) {
                            zuanshiTime -= 1000;

                        }

//                        DialogUtils.vipListUpdateZuanTishi(context,zuanshiTime);

                    }
                }, 0, 1000);
            }
            DialogUtils.vipListUpdateZuanTishi(mContext, zuanshiTime);


        }

        buyVipCount = currentVip.getPurchase_num();
        temPayVipType = -1;
        maxVipTypePos = -1;
        fromType = 0;


        initPayText();//填充价格
        setEquity();//填充会员权益

    }


    private void setEquity() {

        //权益相关
        List<VipEquityBase> equity = currentVip.getEquity();
        //如果当前会员卡已办，就用equityYet，没有办就用equity
        if (!StringUtils.isEmpty(currentVip.getVip_code())) {
            equity = currentVip.getEquityYet();
        }
        llVipRight.removeAllViewsInLayout();
        llVipRight.removeAllViews();


        for (int i = 0; i < equity.size(); i++) {
            final VipEquityBase vipEquityBase = equity.get(i);

            ImageView iv_quanyi_wenhao;
            View headerItemView;
            TextView tv_qianyi;

            //钻石会员没有办之前的特权一单独处理
//            if (currentVip.getVip_type() == 4 && vipListData.getFirstDiamondCard() == 1 && i == 0) {
//
//                headerItemView = LayoutInflater.from(mContext).inflate(R.layout.vip_zuanshi_quan_yi_item, null);
//                iv_quanyi_wenhao = headerItemView.findViewById(R.id.iv_quanyi_wenhao);
//                tv_qianyi = headerItemView.findViewById(R.id.tv_qianyi);
//                TextView tv_zuanzshi_quanyi_yi_tv3 = headerItemView.findViewById(R.id.tv_zuanzshi_quanyi_yi_tv3);
//                tv_zuanzshi_quanyi_yi_tv3.setText("提现" + vipListData.getRaffle_money() + "元");
//
//                ImageView iv_quanyi = headerItemView.findViewById(R.id.iv_quanyi);
//                PicassoUtils.initImage(mContext, vipEquityBase.getEquity_url(), iv_quanyi);
//
//
//            } else {
            headerItemView = LayoutInflater.from(mContext).inflate(R.layout.vip_quan_item, null);
            iv_quanyi_wenhao = headerItemView.findViewById(R.id.iv_quanyi_wenhao);
            ImageView iv_quanyi = headerItemView.findViewById(R.id.iv_quanyi);
            tv_qianyi = headerItemView.findViewById(R.id.tv_qianyi);
            PicassoUtils.initImage(mContext, vipEquityBase.getEquity_url(), iv_quanyi);


//            }

            initTQtopStr(vipEquityBase, tv_qianyi);

            if (vipEquityBase.getShowWen() == 1) {
                iv_quanyi_wenhao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!StringUtils.isEmpty(currentVip.getVip_code())) {
                            ToastUtil.showShortText2(vipEquityBase.getWenContent());
                        }
                    }
                });
                iv_quanyi_wenhao.setVisibility(View.VISIBLE);
            } else {
                iv_quanyi_wenhao.setVisibility(View.GONE);

            }
            llVipRight.addView(headerItemView);

        }


    }

    private void initTQtopStr(VipEquityBase vipEquityBase, TextView tv_qianyi) {
        String strUse = ""; //最后用的字符串
        String replaceStr = "replace";
        boolean endReStr = false;//是否以替换符结尾

//            String replaceStr = "\\{replace\\}";

        String backStr = vipEquityBase.getEquity_content() + "";
        backStr = backStr.replaceAll("\\{", "");
        backStr = backStr.replaceAll("\\}", "");


        ArrayList<String> replaces = vipEquityBase.getReplaces();


        if (null == replaces || replaces.size() <= 0) {//没有replaces
            strUse = backStr + "";
            Spanned tv2Str = Html.fromHtml(strUse
                    , Html.FROM_HTML_MODE_COMPACT
            );
            tv_qianyi.setText(tv2Str);
            return;
        }

        if (backStr.endsWith(replaceStr)) {
            backStr = backStr.substring(0, backStr.length() - replaceStr.length());
            endReStr = true;
        }

        String[] spStrs = backStr.split(replaceStr);
        for (int j = 0; j < spStrs.length; j++) {
            if (j < spStrs.length - 1) {
                try {
                    strUse = strUse + spStrs[j] + "<font color='#FF0000'>" + replaces.get(j) + "</font>";
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }
        strUse = strUse + spStrs[spStrs.length - 1];

        //处理结尾是替换符的情况
        if (endReStr) {
            strUse = strUse + "<font color='#FF0000'>"
                    + replaces.get(replaces.size() - 1) + "</font>";
        }

        Spanned tv2Str = Html.fromHtml(strUse
                , Html.FROM_HTML_MODE_COMPACT
        );
        tv_qianyi.setText(tv2Str);
    }

    //数量附近的内容显示和隐藏
    private void setVisibilityCenter() {
        final int currentVipType = currentVip.getVip_type();

        //奖金的一行 奖金不再显示
//        if (currentVipType != 7 && currentVipType != 8 && currentVipType != 9) {
//            rlJiangjin.setVisibility(View.VISIBLE);
//        } else {
//            rlJiangjin.setVisibility(View.GONE);
//
//        }


        //默认都隐藏
        tvCenterPriceShuoming1.setVisibility(View.GONE);
//        tvCenterPriceShuoming2.setVisibility(View.GONE);

        //单张会员卡价格
        String oneVipPrice = "0";
        oneVipPrice = mVipPriceData.getOne_price();
        switch (currentVipType) {
            case 4://钻石卡
                tvCenterPriceShuoming1.setText("预存钻石会员卡，享如下特权");
//                tvCenterPriceShuoming2.setText("可预存多张，权益叠加");
                tvCenterPriceShuoming1.setVisibility(View.VISIBLE);
//                tvCenterPriceShuoming2.setVisibility(View.VISIBLE);
                break;
            case 5://皇冠
                tvCenterPriceShuoming1.setText("预存皇冠会员卡，享如下特权");
//                tvCenterPriceShuoming2.setText("可预存多张，权益叠加");
                tvCenterPriceShuoming1.setVisibility(View.VISIBLE);
//                tvCenterPriceShuoming2.setVisibility(View.VISIBLE);
                break;
            case 6://至尊
                tvCenterPriceShuoming1.setText("预存至尊会员卡，享如下特权");
//                tvCenterPriceShuoming2.setText("可预存多张，权益叠加");
                tvCenterPriceShuoming1.setVisibility(View.VISIBLE);
//                tvCenterPriceShuoming2.setVisibility(View.VISIBLE);
                break;
            case 7://提现卡
                tvCenterPriceShuoming1.setText("预存" + oneVipPrice + "元赠送" + mVipPriceData.getTrialNum() + "张提现卡，享如下特权");
                tvCenterPriceShuoming1.setVisibility(View.VISIBLE);
//                tvCenterPriceShuoming2.setVisibility(View.VISIBLE);
                break;
            case 8://发货卡
                tvCenterPriceShuoming1.setText("预存" + oneVipPrice + "元赠送1张发货卡，享如下特权");
                tvCenterPriceShuoming1.setVisibility(View.VISIBLE);
//                tvCenterPriceShuoming2.setVisibility(View.GONE);
                break;
            case 9://免拼卡
                tvCenterPriceShuoming1.setText("预存" + oneVipPrice + "元赠送1张免拼卡，享如下特权");
                tvCenterPriceShuoming1.setVisibility(View.VISIBLE);
//                tvCenterPriceShuoming2.setVisibility(View.GONE);
                break;
            default://其他会员
                tvCenterPriceShuoming1.setVisibility(View.GONE);
//                tvCenterPriceShuoming2.setVisibility(View.VISIBLE);

                break;
        }


        if (currentVipType != 7 && currentVipType != 8 && currentVipType != 9) {

            Spanned tempSpd = Html.fromHtml(currentVip.getPunch_days() + "日后可返还<strong><font color='#ff3f8b'>¥" + (int) currentVip.getReturn_money() + "</font></strong>");
            tvTixianDay.setText(tempSpd);

        } else {
            Spanned tempSpd = Html.fromHtml("打卡15日可返<strong><font color='#ff3f8b'>¥" + (int) currentVip.getReturn_money() + "</font></strong>");
            tvTixianDay.setText(tempSpd);
        }


        //返还问号点击
        ivFanhuanWen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentVipType == 7 || currentVipType == 8 || currentVipType == 9) {
//                    ToastUtil.showShortText2("完成每日的赚钱任务即打卡成功。新人累计打卡15日可领200元现金。");

                    ToastUtil.showShortText2("完成每日的赚钱任务即打卡成功。新人累计打卡15日可领" +
                            (int) currentVip.getReturn_money() + "元现金。");

                    return;
                }

                if (!StringUtils.isEmpty(currentVip.getVip_code())) {
                    DialogUtils.vipTixianShuomingDialog(mContext, currentVip, mVipPriceData);
                    return;
                }


                Intent intent = new Intent(mContext,
                        VipSubsidiesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("currentVip", currentVip);
                bundle.putSerializable("mVipPriceData", mVipPriceData);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1009);
            }
        });


    }


    public void jumpOverFlying(View view) {
        recyclerBanner.smoothScrollToPosition(5);
    }

    public void jump(View view) {
        recyclerBanner.setAutoPlaying(true);

    }

    @OnClick({R.id.iv_friend, R.id.iv_friend_close, R.id.iv_count_jia,
            R.id.iv_count_jian, R.id.tv_go_pay, R.id.iv_yucun_wen, R.id.iv_kefu
            , R.id.tv_go_pay_hg

    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_friend: //去邀请好友
                break;
            case R.id.iv_friend_close:
                rlFriend.setVisibility(View.GONE);
                break;
            case R.id.iv_count_jia: //加号

                //首次购买提现卡只能买一张
                if (currentVip.getVip_type() == 7 && vipListData.getFirstCashcard() == 1) {
                    ToastUtil.showShortText2("首次只能购买1张提现卡哦");
                    return;
                }


                //非首次买提现卡最多能买15张
                if (currentVip.getVip_type() == 7 && buyVipCount == 15) {
                    ToastUtil.showShortText2("只能购买15张提现卡。");
                    return;
                }

                //非首次买提现卡最多能买15张
                if (currentVip.getPurchase_maxNum() != -1 && buyVipCount >= currentVip.getPurchase_maxNum()) {
                    ToastUtil.showShortText2("1次只能购买" + currentVip.getPurchase_maxNum() + "张" + currentVip.getVip_name() + "哦");
                    return;
                }


                ivCountJian.setImageDrawable(getResources().getDrawable(R.drawable.vip_couot_jian_new));
                buyVipCount += currentVip.getPurchase_num();
                tvCount.setText(buyVipCount + "");


//                double chooosePayPice = currentVip.getVip_price() * buyVipCount;
//                double payPrice = chooosePayPice - bouns;
//                payPrice = payPrice < 0 ? 0 : payPrice;
//                tvTotalPayMoneyShifu.setText("限时特惠¥：" + new DecimalFormat("#0.00").format(payPrice));
//                tvTotalPayMoneyYuanjia.setText("原价" + (currentVip.getOriginal_vip_price() * buyVipCount));
//                tvTotalPayMoneyYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//
//
//                shifuPirce = new DecimalFormat("#0.00").format(payPrice);
//                yuanjiaPrce = (currentVip.getOriginal_vip_price() * buyVipCount) + "";
                initPayText();


                break;
            case R.id.iv_count_jian://减号


                if (currentVip.getVip_type() == 7 && vipListData.getFirstCashcard() != 1) {
                    if (buyVipCount > 5) {
                        buyVipCount -= 5;

                    } else {

                        return;
                    }


                } else if (currentVip.getVip_type() == 4) {
                    if (buyVipCount <= currentVip.getPurchase_num()) {
                        ToastUtil.showShortText2("第三次预存钻石卡必须2张起");
                        return;
                    }
                    buyVipCount -= currentVip.getPurchase_num();
                } else if (buyVipCount > 1) {
                    buyVipCount -= currentVip.getPurchase_num();
                }

//                if (buyVipCount > 1) {
//                    buyVipCount--;
//                }
//
//
//                if (buyVipCount == 1) {
//                    ivCountJian.setImageDrawable(getResources().getDrawable(R.drawable.vip_couot_jian_end_new));
//                } else {
//                    ivCountJian.setImageDrawable(getResources().getDrawable(R.drawable.vip_couot_jian_new));
//                }


                tvCount.setText(buyVipCount + "");


//                double chooosePayPiceJian = currentVip.getVip_price() * buyVipCount;
//                double payPricejian = chooosePayPiceJian - bouns;
//                payPricejian = payPricejian < 0 ? 0 : payPricejian;
//                tvTotalPayMoneyShifu.setText("限时特惠¥：" + new DecimalFormat("#0.00").format(payPricejian));
//                tvTotalPayMoneyYuanjia.setText("原价" + (currentVip.getOriginal_vip_price() * buyVipCount));
//                tvTotalPayMoneyYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//
//                shifuPirce = new DecimalFormat("#0.00").format(payPricejian);
//                yuanjiaPrce = (currentVip.getOriginal_vip_price() * buyVipCount) + "";

                initPayText();

//                startActivity(new Intent(this, VipSubsidiesActivity.class));


                break;
            case R.id.tv_go_pay://支付
                goToPay();

                break;
            case R.id.tv_go_pay_hg://支付
                goToPay();

                break;
            case R.id.iv_yucun_wen:
                ToastUtil.showShortText2("预存款将全额返还给您至您的衣蝠钱包，可用来以会员价购买任意商品。不再支持退款哦。");

                break;
            case R.id.iv_kefu:
                WXminiAppUtil.jumpToWXmini(this);
                break;

        }


    }


    private void goToPay() {

        UserInfo user = YCache.getCacheUser(mContext);
        if (user.getGender() == 1) {
            ToastUtil.showShortText2("系统维护中，暂不支持支付");
            return;
        }

        if (null == vipList || vipList.size() == 0) {
            return;
        }

        HashMap<String, String> pairsMap = new HashMap<>();
        pairsMap.put("vip_type", currentVip.getVip_type() + "");
        pairsMap.put("vip_count", buyVipCount + "");

        waitDialog.show();
        YConn.httpPost(this, YUrl.VIP_PAY_URL, pairsMap, new HttpListener<AddVipResult>() {


            @Override
            public void onSuccess(AddVipResult result) {
                showBuySucMessage = result.getShowBuySucMessage();
                payVcode = result.getV_code();
                paySuccessDialogData = result.getData();

                //测试
//                if (null != paySuccessDialogData) {
//                    showPaySuccessDialog();
//                    return;
//                }
                temPayVipType = currentVip.getVip_type();


                if (result.getActual_price() <= 0) {
//                    tempPayPos = viewCurrentPos;
                    temPayVipType = currentVip.getVip_type();

                    if (null != paySuccessDialogData) {
                        showPaySuccessDialog();
                    }

//                    MessageEvent messageEvent = new MessageEvent();
//                    messageEvent.setEventBuyVipSucVipType(currentVip.getVip_type());
//                    EventBus.getDefault().post(messageEvent);


                    getVipData();
                    waitDialog.dismiss();
                    return;
                }


                // 跳转到收银台界面
                Intent intent = new Intent(MyVipListActivity.this, PaymentActivity.class);
                intent.putExtra("order_code", result.getV_code());
                double price;
//                if (currentVip.getArrears_price() > 0) {
//                    price = currentVip.getArrears_price();
//                } else {
//                    price = currentVip.getVip_price();
//                }


                intent.putExtra("isVIPpay", true);
                intent.putExtra("vipDiscount", result.getDiscount());

                if (temPayVipType == 7) {
                    intent.putExtra("isBuyTXK", true);

                }
                intent.putExtra("isVIPpay", true);
                intent.putExtra("totlaAccount", mVipPriceData.getActual_price());
                intent.putExtra("isMulti", true);
                startActivityForResult(intent, 1009);
                waitDialog.dismiss();



            }

            @Override
            public void onError() {
                waitDialog.dismiss();
            }
        });
    }


    public void showPaySuccessDialog() {


        LayoutInflater mInflater = LayoutInflater.from(mContext);
        final Dialog dialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = mInflater.inflate(R.layout.dialog_sign_buy_sucess, null);


        TextView tv_vip_price = view.findViewById(R.id.tv_vip_price);
        TextView tv_djk_jian = view.findViewById(R.id.tv_djk_jian);
        RelativeLayout rl_djk = view.findViewById(R.id.rl_djk);
        TextView tv_vip_tx_jian = view.findViewById(R.id.tv_vip_tx_jian);
        RelativeLayout rl_vip = view.findViewById(R.id.rl_vip);
        TextView tv_new_user_jian = view.findViewById(R.id.tv_new_user_jian);
        TextView tv_pay_price = view.findViewById(R.id.tv_pay_price);
        TextView tv_bottom = view.findViewById(R.id.tv_bottom);
        ImageView iv_bot_img = view.findViewById(R.id.iv_bot_img);
        LinearLayout ll_first_zuanshi_bot_text = view.findViewById(R.id.ll_first_zuanshi_bot_text);
        TextView tv_zuanshi_bot_text1 = view.findViewById(R.id.tv_zuanshi_bot_text1);
        TextView tv_zuanshi_bot_text2 = view.findViewById(R.id.tv_zuanshi_bot_text2);
        TextView tv_zuanshi_bot_text3 = view.findViewById(R.id.tv_zuanshi_bot_text3);
        TextView tv_vip_txdk_text = view.findViewById(R.id.tv_vip_txdk_text);
        TextView tv_new_user_str = view.findViewById(R.id.tv_new_user_str);


        tv_vip_price.setText("¥" + paySuccessDialogData.getOriginalVipPrice());
        if (paySuccessDialogData.getVip_type() == 4) {
            tv_djk_jian.setText("-¥" + paySuccessDialogData.getReduce_extract());
            tv_vip_tx_jian.setText("-¥" + paySuccessDialogData.getUnVipRaffleMoney());
            rl_djk.setVisibility(View.GONE);
            rl_vip.setVisibility(View.GONE);
        } else {
            rl_djk.setVisibility(View.GONE);
            rl_vip.setVisibility(View.GONE);
        }
        tv_new_user_jian.setText("-¥" + paySuccessDialogData.getFavorablePrice());
        tv_pay_price.setText("¥" + paySuccessDialogData.getActual_price());


        final int paySucVipType = paySuccessDialogData.getVip_type();


        if (paySuccessDialogData.getPopupUse() == 1) {

            tv_new_user_str.setText("活动优惠：");

            tv_zuanshi_bot_text1.setText(Html.fromHtml("恭喜您，预存会员后可免费领取一件" + paySuccessDialogData.getFreeBuyPrice() + "元美衣！您可自由选择原价" + paySuccessDialogData.getFreeBuyPrice() + "元以下商品，点免费领下单后，直接发货。"));
            tv_zuanshi_bot_text2.setText(Html.fromHtml("<font color='#ff0000'><big>" + paySuccessDialogData.getActual_price() + "元</big></font>已返还至您衣蝠钱包的购物余额，可全额用来购买任意商品。"));


            ll_first_zuanshi_bot_text.setVisibility(View.VISIBLE);
            tv_bottom.setVisibility(View.GONE);
            tv_zuanshi_bot_text3.setVisibility(View.GONE);

            TextView btn_ok = view.findViewById(R.id.btn_ok);
            btn_ok.setText("我知道了");
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });


            dialog.setCanceledOnTouchOutside(false);
            dialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            ToastUtil.showDialog(dialog);

            return;


        }


        switch (paySucVipType) {
            case 4://钻石
                tv_vip_txdk_text.setText("会员提现抵扣:");
                tv_vip_txdk_text.setTextColor(Color.parseColor("#ff0000"));
                tv_vip_tx_jian.setTextColor(Color.parseColor("#ff0000"));

                if (paySuccessDialogData.getDiamondNum() == 0) {
                    tv_zuanshi_bot_text1.setText(Html.fromHtml("注：您已是会员，现在可以去<font color='#ff0000'><strong>免费领一件79元美衣。</strong></font>"));


                    tv_zuanshi_bot_text2.setText(Html.fromHtml("领完后实付的<font color='#ff0000'><strong>" + paySuccessDialogData.getVip_price() + "元会全额返还</strong></font>到您衣蝠钱包的购物余额里，可用来再购买任意商品哦。"));
                    tv_zuanshi_bot_text2.setVisibility(View.VISIBLE);


                    tv_zuanshi_bot_text3.setVisibility(View.GONE);
                    ll_first_zuanshi_bot_text.setVisibility(View.VISIBLE);

                    tv_bottom.setVisibility(View.GONE);
                } else {
                    tv_bottom.setText(Html.fromHtml("恭喜您拥有2张钻石会员卡，可免费领取一件原价199元美衣。<font color='#ff0000'><big>" + paySuccessDialogData.getActual_price() + "元</big></font>已返还至您衣蝠钱包的购物余额，可全额用来购买任意美衣哦。"));
                    ll_first_zuanshi_bot_text.setVisibility(View.GONE);
                    tv_bottom.setVisibility(View.VISIBLE);
                }
                break;
            case 5://皇冠
                rl_djk.setVisibility(View.GONE);
                rl_vip.setVisibility(View.GONE);
                tv_new_user_str.setText("限时优惠：");
                tv_zuanshi_bot_text1.setText(Html.fromHtml("注：1.您已成为皇冠会员，接下来您的粉丝好友预存会员或消费，您<font color='#ff0000'><strong>立得15%佣金</strong></font>。您自购美衣，<font color='#ff0000'><strong>立返15%佣金</strong></font>。"));
                tv_zuanshi_bot_text2.setText(Html.fromHtml("<font color='#ffffff'>空白</font>2.您可建立粉丝群，并申请智能助理进群。助理每日自动在群里推荐爆款美衣。群友消费您<font color='#ff0000'><strong>即得15%佣金</strong></font>。<font color='#ff0000'><strong>轻松月赚数千元</strong></font>。"));
                tv_zuanshi_bot_text3.setText(Html.fromHtml("<font color='#ffffff'>空白</font>3.您现在可以去<font color='#ff0000'><strong>免费领一件199元美衣</strong></font>，领完后<font color='#ff0000'><strong>" + paySuccessDialogData.getVip_price() + "元会全额返还</strong></font>至您衣蝠钱包的购物余额。可再用来购买任意商品哦。"));
                ll_first_zuanshi_bot_text.setVisibility(View.VISIBLE);
                tv_bottom.setVisibility(View.GONE);
                break;
            default://其他 （道具卡）
                tv_bottom.setText(Html.fromHtml("恭喜您得到一张" + paySuccessDialogData.getVip_name() + "，尊享两大特权。<font color='#ff0000'><big>" + paySuccessDialogData.getActual_price() + "元</big></font>已返还至您衣蝠钱包的余额，可全额用来购买任意美衣哦。"));
                ll_first_zuanshi_bot_text.setVisibility(View.GONE);
                tv_bottom.setVisibility(View.VISIBLE);
                break;
        }


        if (paySuccessDialogData.getVip_type() == 8) {
            iv_bot_img.setImageResource(R.drawable.fhk_pay_suc_str);
            iv_bot_img.setVisibility(View.VISIBLE);
        } else if (paySuccessDialogData.getVip_type() == 9) {
            iv_bot_img.setImageResource(R.drawable.mpk_pay_suc_str);
            iv_bot_img.setVisibility(View.VISIBLE);

        } else {
            iv_bot_img.setVisibility(View.GONE);
        }

        //知道了点击
        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                //查询是否要去免费领
                HashMap<String, String> pairsMap = new HashMap<>();
                YConn.httpPost(mContext, YUrl.NEED_JUM_FREE_LING, pairsMap
                        , new HttpListener<BaseData>() {
                            @Override
                            public void onSuccess(BaseData baseData) {
                                if (baseData.getIsJumpPage() == 1) {
                                    if(null != HomePageThreeActivity.instance){
                                        HomePageThreeActivity.instance.finish();
                                    }
                                    startActivity(new Intent(MyVipListActivity.this, HomePageThreeActivity.class)
                                            .putExtra("freeBuyType", 2)
                                            .putExtra("buyVipFreeBuy", true)
                                            .putExtra("freeOrderPage", baseData.getFreeOrderPage())
                                            .putExtra("freeMoney", baseData.getFreeMoney() + "")

                                    );
                                    mContext.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                                    return;
                                }

                                //查询是否有免费领订单发货了
                                HashMap<String, String> pairsMap1 = new HashMap<>();
                                pairsMap1.put("v_code", payVcode + "");
                                YConn.httpPost(mContext, YUrl.QUERY_DAOJUKASHENGJI_ZUANSHI_DATA, pairsMap1, new HttpListener<DaoJuKaUpZuanshiData>() {
                                    @Override
                                    public void onSuccess(DaoJuKaUpZuanshiData result) {
                                        if (result.getFreeOrder() == 1) {//如果有免费领的订单发货了就弹出轻提示
                                            ToastUtil.showMyToast(mContext, "预存成功，免费领订单已进入待发货状态，请注意物流信息。", 5000);
                                        } else {

                                            switch (paySucVipType) {
                                                case 4://钻石卡
                                                    ToastUtil.showMyToast(mContext, "购买成功，会员卡权益已开通，祝您购物愉快。", 5000);
                                                    break;
                                                case 5://皇冠
                                                    ToastUtil.showMyToast(mContext, "购买成功，会员卡权益已开通，祝您购物愉快。", 5000);
                                                    break;
                                                case 6://至尊
                                                    ToastUtil.showMyToast(mContext, "购买成功，会员卡权益已开通，祝您购物愉快。", 5000);
                                                    break;

                                                case 7:
                                                    if (guide_txk) {
                                                        setResult(SignDrawalLimitActivity.BUG_TXK_SUCCESS);
                                                        finish();
                                                    } else {
                                                        if (null != SignDrawalLimitActivity.instance) {
                                                            SignDrawalLimitActivity.instance.finish();
                                                        }
                                                        Intent intent = new Intent(mContext, SignDrawalLimitActivity.class).putExtra("type", 1);
                                                        mContext.startActivity(intent);
                                                        mContext.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                                                    }
                                                    break;
                                                case 8:
                                                    buyFHKsuc = true;
                                                    break;
                                                case 9:
                                                    buyMPKsuc = true;
                                                    break;
                                                default://其他
                                                    break;

                                            }


                                        }
                                    }

                                    @Override
                                    public void onError() {
                                    }
                                });


                            }

                            @Override
                            public void onError() {

                            }
                        });


            }
        });


        dialog.setCanceledOnTouchOutside(false);
        dialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ToastUtil.showDialog(dialog);


    }


    public static final int PAY_SUCCESS = 1;
    public static final int PAY_FAIL = 2;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1009) { //支付回来
            if (resultCode == PAY_SUCCESS) {//支付成功
//                tempPayPos = viewCurrentPos;


                temPayVipType = currentVip.getVip_type();
                if (temPayVipType == 7) {
                    buyTXKsuc = true;
                }

                if (null != WithdrawalLimitActivity.instance) {
                    WithdrawalLimitActivity.instance.finish();
                }

                if (null != paySuccessDialogData) {
                    showPaySuccessDialog();
                } else {
                    ToastUtil.showMyToast(mContext, "购买成功，会员卡权益已开通，祝您购物愉快。", 5000);
                }

                //钻石卡抵扣
                if (temPayVipType == 4) {
                    zuanShiDikou = paySuccessDialogData.getUnVipRaffleMoney() + "";
                }
                buyVipSuccess = true;


//                MessageEvent messageEvent = new MessageEvent();
//                messageEvent.setEventBuyVipSucVipType(currentVip.getVip_type());
//                EventBus.getDefault().post(messageEvent);


                getVipData();

                if (null != showBuySucMessage) {
                    ToastUtil.showMyToast(mContext, showBuySucMessage, 4000);
                }

            } else if (resultCode == PAY_FAIL) {
                ToastUtil.showShortText2("支付失败");
            }
        }


    }

    @Override
    public void onItemClick(int position) {
        recyclerBanner.smoothScrollToPosition(position);

    }


}
