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


    private List<VipListBean.ViplistBean> vipList; //???????????????
    private List<VipListBean.UserVipListBean> userVipList;//???????????????????????????
    private MyVipListActivity mContext;

    private List<VipListBean.ViplistBean> initialVipList; //????????????????????????????????????????????????

    private int vipListSize;
    public static int buyVipCount = 1;

    private String payVcode;

//    private ArrayList<Spanned> arrvipType4Right;
//    private ArrayList<Spanned> arrvipType5Right;
//    private ArrayList<Spanned> arrvipType6Right;


//    private ArrayList<Spanned> vipShouMingSpdList;


    private int maxVipTypePos = -1;//?????????????????????

    private int fromType;
    public static double balance = 0;//????????????????????????


//    public int viewCurrentPos; //?????????????????????


    private int firstCardShowPostion;//????????????????????????


//    private int tempPayPos = -1;

    private int temPayVipType = -1;

    private VipListBean.ViplistBean currentVip;//???????????????vip

    private VipListBean vipListData;


    public static String shifuPirce;
    public static String yuanjiaPrce;

    private boolean guide_txk;//??????????????????????????????????????????????????????

    private int guide_vipType = 0;//?????????????????????vipType;

    private VipPriceData mVipPriceData;//????????????????????????

    private boolean buyMPKsuc;//???????????????????????????
    private boolean buyFHKsuc;//???????????????????????????
    private boolean isNewUserGuideVIP;
    private boolean isGuideFHK;//??????????????????
    private boolean isGuideMPK;//??????????????????

    private long zuanshiTime = 30 * 60 * 1000;
    private AddVipResult.DataBean paySuccessDialogData;

    private boolean buyTXKsuc;//???????????????????????????


    private int vipUpdateVipType = -1;//??????????????????????????????

    private boolean isFirstCashcard = true; //????????????????????????

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
            tv3.setText("??????6????????????200???");
        } else {
            tv3.setText("??????25????????????200???");
            btn_ok.setText("????????????200?????????");
        }
        tv4.setText(Html.fromHtml("<font color='#FF0000'><strong>????????????????????????</strong></font>???????????????????"));


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
            ToastUtil.showMyToast(mContext, "?????????????????????????????????????????????", 3000);
            finish();
            return;
        }

        if(YCache.getCacheUser(mContext).getReviewers() == 1){
            llKefu.setVisibility(View.GONE);
        }
//????????????
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

        tvTitleBase.setText("?????????????????????");
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        recyclerBanner = findViewById(R.id.recycler);

        if (is20ciBuyVip) {
            ToastUtil.showMyToast(mContext, "??????????????????????????????10??????????????????????????????????????????????????????", 4000);
        }

        if (isFromSign2RoundFirst == 1) {
            showIsFromSign2RoundFisrtDialog();
        }

        if (isFromSign2Round == 1) {
            ToastUtil.showMyToast(mContext, "??????????????????1????????????????????????????????????5????????????", 3000);

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
                vipToastStr = "?????????????????????????????????????????????????????????????????????????????????399??????????????????";
                break;
            case -2:
                vipToastStr = "????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????";
                break;
            case -3:
                vipToastStr = "??????????????????????????????????????????????????????????????????????????????????????????????????????";
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

                //????????????
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


                //??????????????????200-210????????????
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

                //????????????????????????
                for (int i = 0; i < initialVipList.size(); i++) {
                    if (initialVipList.get(i).getVip_type() == 4) {
                        tempIndex = i;
                    }
                }

                //???????????????????????????
                for (int i = 0; i < initialVipList.size(); i++) {
                    if (initialVipList.get(i).getVip_type() == vipListData.getLandPage()) {
                        tempIndex = i;
                    }
                }


                //?????????????????????????????????????????????
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

//                if (isNewUserGuideVIP) { //?????????????????????????????????
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

                //?????????????????????????????????
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


                //??????????????????????????????
//                if (tempPayPos != -1) {//??????????????????
//                    tempIndex = tempPayPos % vipListSize;
//
//                }
                //???????????????????????????
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


//                tvPaySingleMoney.setText("??" + currentVip.getVip_price() + "");//??????

                tvPaySingleMoney.setText("??" + result.getActual_price() + "");//??????


//                tvTotalPayMoneyShifu.setText("?????????????????" + result.getActual_price());


                tvTotalPayMoneyShifuHg.setText(result.getContent1() + "");


//                tvTotalPayMoneyYuanjia.setText("??????" + currentVip.getOriginal_vip_price() * buyVipCount);
                tvBuyYuanjiaBot.setText("????????" + result.getOriginal_price());


                shifuPirce = DateUtils.dePoint("#0.00", result.getActual_price());//??????

                yuanjiaPrce = currentVip.getOriginal_vip_price() * buyVipCount + ""; //?????????


//                tvTotalPayMoneyYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvBuyYuanjiaBot.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);


                tvCount.setText(buyVipCount + "");

                //?????????????????????
                //??????
                if (currentVip.getPurchase_maxNum() == -1 || buyVipCount < currentVip.getPurchase_maxNum()) {
                    ivCountJia.setImageDrawable(getResources().getDrawable(R.drawable.vip_couot_jia_new));
                } else {
                    ivCountJia.setImageDrawable(getResources().getDrawable(R.drawable.vip_couot_jia_new_end));

                }

                //??????
                if (buyVipCount > currentVip.getPurchase_num()) {
                    ivCountJian.setImageDrawable(getResources().getDrawable(R.drawable.vip_couot_jian_new));
                } else {
                    ivCountJian.setImageDrawable(getResources().getDrawable(R.drawable.vip_couot_jian_end_new));

                }


                tvUseJinglijin.setText("-??" + result.getFixMoney());


                if (result.getReMoney() > 0 && currentVip.getVip_type() == 4) {

                    Spanned tempSpdDaoju = Html.fromHtml("????????????<font color='#ff3f8b'>" +
                            result.getReMoney() + "</font>??????????????????????????????<font color='#ff3f8b'>" +
                            result.getActual_price() + "</font>???????????????????????????");
                    tvDaojukaTishi.setText(tempSpdDaoju);


                    tvDaojukaTishi.setVisibility(View.VISIBLE);
                } else {
                    tvDaojukaTishi.setVisibility(View.GONE);

                }

                //?????????????????????????????????????????????????????????
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


        initPayText();//????????????
        setEquity();//??????????????????

    }


    private void setEquity() {

        //????????????
        List<VipEquityBase> equity = currentVip.getEquity();
        //????????????????????????????????????equityYet??????????????????equity
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

            //???????????????????????????????????????????????????
//            if (currentVip.getVip_type() == 4 && vipListData.getFirstDiamondCard() == 1 && i == 0) {
//
//                headerItemView = LayoutInflater.from(mContext).inflate(R.layout.vip_zuanshi_quan_yi_item, null);
//                iv_quanyi_wenhao = headerItemView.findViewById(R.id.iv_quanyi_wenhao);
//                tv_qianyi = headerItemView.findViewById(R.id.tv_qianyi);
//                TextView tv_zuanzshi_quanyi_yi_tv3 = headerItemView.findViewById(R.id.tv_zuanzshi_quanyi_yi_tv3);
//                tv_zuanzshi_quanyi_yi_tv3.setText("??????" + vipListData.getRaffle_money() + "???");
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
        String strUse = ""; //?????????????????????
        String replaceStr = "replace";
        boolean endReStr = false;//????????????????????????

//            String replaceStr = "\\{replace\\}";

        String backStr = vipEquityBase.getEquity_content() + "";
        backStr = backStr.replaceAll("\\{", "");
        backStr = backStr.replaceAll("\\}", "");


        ArrayList<String> replaces = vipEquityBase.getReplaces();


        if (null == replaces || replaces.size() <= 0) {//??????replaces
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

        //?????????????????????????????????
        if (endReStr) {
            strUse = strUse + "<font color='#FF0000'>"
                    + replaces.get(replaces.size() - 1) + "</font>";
        }

        Spanned tv2Str = Html.fromHtml(strUse
                , Html.FROM_HTML_MODE_COMPACT
        );
        tv_qianyi.setText(tv2Str);
    }

    //????????????????????????????????????
    private void setVisibilityCenter() {
        final int currentVipType = currentVip.getVip_type();

        //??????????????? ??????????????????
//        if (currentVipType != 7 && currentVipType != 8 && currentVipType != 9) {
//            rlJiangjin.setVisibility(View.VISIBLE);
//        } else {
//            rlJiangjin.setVisibility(View.GONE);
//
//        }


        //???????????????
        tvCenterPriceShuoming1.setVisibility(View.GONE);
//        tvCenterPriceShuoming2.setVisibility(View.GONE);

        //?????????????????????
        String oneVipPrice = "0";
        oneVipPrice = mVipPriceData.getOne_price();
        switch (currentVipType) {
            case 4://?????????
                tvCenterPriceShuoming1.setText("???????????????????????????????????????");
//                tvCenterPriceShuoming2.setText("??????????????????????????????");
                tvCenterPriceShuoming1.setVisibility(View.VISIBLE);
//                tvCenterPriceShuoming2.setVisibility(View.VISIBLE);
                break;
            case 5://??????
                tvCenterPriceShuoming1.setText("???????????????????????????????????????");
//                tvCenterPriceShuoming2.setText("??????????????????????????????");
                tvCenterPriceShuoming1.setVisibility(View.VISIBLE);
//                tvCenterPriceShuoming2.setVisibility(View.VISIBLE);
                break;
            case 6://??????
                tvCenterPriceShuoming1.setText("???????????????????????????????????????");
//                tvCenterPriceShuoming2.setText("??????????????????????????????");
                tvCenterPriceShuoming1.setVisibility(View.VISIBLE);
//                tvCenterPriceShuoming2.setVisibility(View.VISIBLE);
                break;
            case 7://?????????
                tvCenterPriceShuoming1.setText("??????" + oneVipPrice + "?????????" + mVipPriceData.getTrialNum() + "??????????????????????????????");
                tvCenterPriceShuoming1.setVisibility(View.VISIBLE);
//                tvCenterPriceShuoming2.setVisibility(View.VISIBLE);
                break;
            case 8://?????????
                tvCenterPriceShuoming1.setText("??????" + oneVipPrice + "?????????1??????????????????????????????");
                tvCenterPriceShuoming1.setVisibility(View.VISIBLE);
//                tvCenterPriceShuoming2.setVisibility(View.GONE);
                break;
            case 9://?????????
                tvCenterPriceShuoming1.setText("??????" + oneVipPrice + "?????????1??????????????????????????????");
                tvCenterPriceShuoming1.setVisibility(View.VISIBLE);
//                tvCenterPriceShuoming2.setVisibility(View.GONE);
                break;
            default://????????????
                tvCenterPriceShuoming1.setVisibility(View.GONE);
//                tvCenterPriceShuoming2.setVisibility(View.VISIBLE);

                break;
        }


        if (currentVipType != 7 && currentVipType != 8 && currentVipType != 9) {

            Spanned tempSpd = Html.fromHtml(currentVip.getPunch_days() + "???????????????<strong><font color='#ff3f8b'>??" + (int) currentVip.getReturn_money() + "</font></strong>");
            tvTixianDay.setText(tempSpd);

        } else {
            Spanned tempSpd = Html.fromHtml("??????15?????????<strong><font color='#ff3f8b'>??" + (int) currentVip.getReturn_money() + "</font></strong>");
            tvTixianDay.setText(tempSpd);
        }


        //??????????????????
        ivFanhuanWen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentVipType == 7 || currentVipType == 8 || currentVipType == 9) {
//                    ToastUtil.showShortText2("???????????????????????????????????????????????????????????????15?????????200????????????");

                    ToastUtil.showShortText2("???????????????????????????????????????????????????????????????15?????????" +
                            (int) currentVip.getReturn_money() + "????????????");

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
            case R.id.iv_friend: //???????????????
                break;
            case R.id.iv_friend_close:
                rlFriend.setVisibility(View.GONE);
                break;
            case R.id.iv_count_jia: //??????

                //????????????????????????????????????
                if (currentVip.getVip_type() == 7 && vipListData.getFirstCashcard() == 1) {
                    ToastUtil.showShortText2("??????????????????1???????????????");
                    return;
                }


                //?????????????????????????????????15???
                if (currentVip.getVip_type() == 7 && buyVipCount == 15) {
                    ToastUtil.showShortText2("????????????15???????????????");
                    return;
                }

                //?????????????????????????????????15???
                if (currentVip.getPurchase_maxNum() != -1 && buyVipCount >= currentVip.getPurchase_maxNum()) {
                    ToastUtil.showShortText2("1???????????????" + currentVip.getPurchase_maxNum() + "???" + currentVip.getVip_name() + "???");
                    return;
                }


                ivCountJian.setImageDrawable(getResources().getDrawable(R.drawable.vip_couot_jian_new));
                buyVipCount += currentVip.getPurchase_num();
                tvCount.setText(buyVipCount + "");


//                double chooosePayPice = currentVip.getVip_price() * buyVipCount;
//                double payPrice = chooosePayPice - bouns;
//                payPrice = payPrice < 0 ? 0 : payPrice;
//                tvTotalPayMoneyShifu.setText("?????????????????" + new DecimalFormat("#0.00").format(payPrice));
//                tvTotalPayMoneyYuanjia.setText("??????" + (currentVip.getOriginal_vip_price() * buyVipCount));
//                tvTotalPayMoneyYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//
//
//                shifuPirce = new DecimalFormat("#0.00").format(payPrice);
//                yuanjiaPrce = (currentVip.getOriginal_vip_price() * buyVipCount) + "";
                initPayText();


                break;
            case R.id.iv_count_jian://??????


                if (currentVip.getVip_type() == 7 && vipListData.getFirstCashcard() != 1) {
                    if (buyVipCount > 5) {
                        buyVipCount -= 5;

                    } else {

                        return;
                    }


                } else if (currentVip.getVip_type() == 4) {
                    if (buyVipCount <= currentVip.getPurchase_num()) {
                        ToastUtil.showShortText2("??????????????????????????????2??????");
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
//                tvTotalPayMoneyShifu.setText("?????????????????" + new DecimalFormat("#0.00").format(payPricejian));
//                tvTotalPayMoneyYuanjia.setText("??????" + (currentVip.getOriginal_vip_price() * buyVipCount));
//                tvTotalPayMoneyYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//
//                shifuPirce = new DecimalFormat("#0.00").format(payPricejian);
//                yuanjiaPrce = (currentVip.getOriginal_vip_price() * buyVipCount) + "";

                initPayText();

//                startActivity(new Intent(this, VipSubsidiesActivity.class));


                break;
            case R.id.tv_go_pay://??????
                goToPay();

                break;
            case R.id.tv_go_pay_hg://??????
                goToPay();

                break;
            case R.id.iv_yucun_wen:
                ToastUtil.showShortText2("????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");

                break;
            case R.id.iv_kefu:
                WXminiAppUtil.jumpToWXmini(this);
                break;

        }


    }


    private void goToPay() {

        UserInfo user = YCache.getCacheUser(mContext);
        if (user.getGender() == 1) {
            ToastUtil.showShortText2("????????????????????????????????????");
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

                //??????
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


                // ????????????????????????
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


        tv_vip_price.setText("??" + paySuccessDialogData.getOriginalVipPrice());
        if (paySuccessDialogData.getVip_type() == 4) {
            tv_djk_jian.setText("-??" + paySuccessDialogData.getReduce_extract());
            tv_vip_tx_jian.setText("-??" + paySuccessDialogData.getUnVipRaffleMoney());
            rl_djk.setVisibility(View.GONE);
            rl_vip.setVisibility(View.GONE);
        } else {
            rl_djk.setVisibility(View.GONE);
            rl_vip.setVisibility(View.GONE);
        }
        tv_new_user_jian.setText("-??" + paySuccessDialogData.getFavorablePrice());
        tv_pay_price.setText("??" + paySuccessDialogData.getActual_price());


        final int paySucVipType = paySuccessDialogData.getVip_type();


        if (paySuccessDialogData.getPopupUse() == 1) {

            tv_new_user_str.setText("???????????????");

            tv_zuanshi_bot_text1.setText(Html.fromHtml("????????????????????????????????????????????????" + paySuccessDialogData.getFreeBuyPrice() + "????????????????????????????????????" + paySuccessDialogData.getFreeBuyPrice() + "?????????????????????????????????????????????????????????"));
            tv_zuanshi_bot_text2.setText(Html.fromHtml("<font color='#ff0000'><big>" + paySuccessDialogData.getActual_price() + "???</big></font>?????????????????????????????????????????????????????????????????????????????????"));


            ll_first_zuanshi_bot_text.setVisibility(View.VISIBLE);
            tv_bottom.setVisibility(View.GONE);
            tv_zuanshi_bot_text3.setVisibility(View.GONE);

            TextView btn_ok = view.findViewById(R.id.btn_ok);
            btn_ok.setText("????????????");
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
            case 4://??????
                tv_vip_txdk_text.setText("??????????????????:");
                tv_vip_txdk_text.setTextColor(Color.parseColor("#ff0000"));
                tv_vip_tx_jian.setTextColor(Color.parseColor("#ff0000"));

                if (paySuccessDialogData.getDiamondNum() == 0) {
                    tv_zuanshi_bot_text1.setText(Html.fromHtml("???????????????????????????????????????<font color='#ff0000'><strong>???????????????79????????????</strong></font>"));


                    tv_zuanshi_bot_text2.setText(Html.fromHtml("??????????????????<font color='#ff0000'><strong>" + paySuccessDialogData.getVip_price() + "??????????????????</strong></font>???????????????????????????????????????????????????????????????????????????"));
                    tv_zuanshi_bot_text2.setVisibility(View.VISIBLE);


                    tv_zuanshi_bot_text3.setVisibility(View.GONE);
                    ll_first_zuanshi_bot_text.setVisibility(View.VISIBLE);

                    tv_bottom.setVisibility(View.GONE);
                } else {
                    tv_bottom.setText(Html.fromHtml("???????????????2????????????????????????????????????????????????199????????????<font color='#ff0000'><big>" + paySuccessDialogData.getActual_price() + "???</big></font>????????????????????????????????????????????????????????????????????????????????????"));
                    ll_first_zuanshi_bot_text.setVisibility(View.GONE);
                    tv_bottom.setVisibility(View.VISIBLE);
                }
                break;
            case 5://??????
                rl_djk.setVisibility(View.GONE);
                rl_vip.setVisibility(View.GONE);
                tv_new_user_str.setText("???????????????");
                tv_zuanshi_bot_text1.setText(Html.fromHtml("??????1.?????????????????????????????????????????????????????????????????????????????????<font color='#ff0000'><strong>??????15%??????</strong></font>?????????????????????<font color='#ff0000'><strong>??????15%??????</strong></font>???"));
                tv_zuanshi_bot_text2.setText(Html.fromHtml("<font color='#ffffff'>??????</font>2.?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????<font color='#ff0000'><strong>??????15%??????</strong></font>???<font color='#ff0000'><strong>?????????????????????</strong></font>???"));
                tv_zuanshi_bot_text3.setText(Html.fromHtml("<font color='#ffffff'>??????</font>3.??????????????????<font color='#ff0000'><strong>???????????????199?????????</strong></font>????????????<font color='#ff0000'><strong>" + paySuccessDialogData.getVip_price() + "??????????????????</strong></font>????????????????????????????????????????????????????????????????????????"));
                ll_first_zuanshi_bot_text.setVisibility(View.VISIBLE);
                tv_bottom.setVisibility(View.GONE);
                break;
            default://?????? ???????????????
                tv_bottom.setText(Html.fromHtml("?????????????????????" + paySuccessDialogData.getVip_name() + "????????????????????????<font color='#ff0000'><big>" + paySuccessDialogData.getActual_price() + "???</big></font>??????????????????????????????????????????????????????????????????????????????"));
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

        //???????????????
        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                //???????????????????????????
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

                                //???????????????????????????????????????
                                HashMap<String, String> pairsMap1 = new HashMap<>();
                                pairsMap1.put("v_code", payVcode + "");
                                YConn.httpPost(mContext, YUrl.QUERY_DAOJUKASHENGJI_ZUANSHI_DATA, pairsMap1, new HttpListener<DaoJuKaUpZuanshiData>() {
                                    @Override
                                    public void onSuccess(DaoJuKaUpZuanshiData result) {
                                        if (result.getFreeOrder() == 1) {//??????????????????????????????????????????????????????
                                            ToastUtil.showMyToast(mContext, "?????????????????????????????????????????????????????????????????????????????????", 5000);
                                        } else {

                                            switch (paySucVipType) {
                                                case 4://?????????
                                                    ToastUtil.showMyToast(mContext, "???????????????????????????????????????????????????????????????", 5000);
                                                    break;
                                                case 5://??????
                                                    ToastUtil.showMyToast(mContext, "???????????????????????????????????????????????????????????????", 5000);
                                                    break;
                                                case 6://??????
                                                    ToastUtil.showMyToast(mContext, "???????????????????????????????????????????????????????????????", 5000);
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
                                                default://??????
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

        if (requestCode == 1009) { //????????????
            if (resultCode == PAY_SUCCESS) {//????????????
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
                    ToastUtil.showMyToast(mContext, "???????????????????????????????????????????????????????????????", 5000);
                }

                //???????????????
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
                ToastUtil.showShortText2("????????????");
            }
        }


    }

    @Override
    public void onItemClick(int position) {
        recyclerBanner.smoothScrollToPosition(position);

    }


}
