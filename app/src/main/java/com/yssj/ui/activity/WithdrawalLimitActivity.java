package com.yssj.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yssj.YConstance.Pref;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LuckyPanView;
import com.yssj.custom.view.LuckyPanView.OnStopListening;
import com.yssj.entity.BalanceLuckyDrawData;
import com.yssj.entity.MyWalletData;
import com.yssj.entity.VipInfo;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelL;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.infos.ClothesBeanDetailActivity;
import com.yssj.ui.activity.infos.IntergralDetailActivity;
import com.yssj.ui.activity.infos.MyWalletActivity;
import com.yssj.ui.activity.infos.MyWalletCommonFragmentActivity;
import com.yssj.ui.activity.infos.NewWalletActivity;
import com.yssj.ui.activity.vip.MyVipListActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.GlideUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.SimpleCountDownTimer;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/***
 * ???????????? ??????
 */
public class WithdrawalLimitActivity extends BasicActivity implements OnClickListener {
    private TextView to_obtion_yidou_btn;
    private ListView listView1;//???????????? ??? ???????????????ListView
    private List<HashMap<String, String>> mListData1;
    private MyAdapter adapter1;
    private Timer mTimer1;
    private LuckyPanView mLuckyPanView;
    private ImageView mStartBtn;
    private View explain_limit;//????????????
    private View to_withdrawal_btn;//????????????
    private View limit_det_ll;//????????????
    private double mSumBalance, mLimit;//????????????  ??? ???????????????
    private int usedYidou, unUsedYidou;//????????????  ???????????????
    private double usedBalance, unUsedBalance;//???????????? ????????????
    private TextView balanceTv, limitTv, used_yidou_tv, un_used_yidou_tv;
    private View imgBack;
    private double redPacketValue;//????????????
    private boolean isRunning;
    private boolean isMad;//????????????????????????
    private int lotterynumber;//??????????????? ??????????????????
    private int payLotteryNumber;//????????????  ??????????????? ??????????????????
    private TextView tvRemaindTimes;
    private boolean isFromPaySuccess;//??????????????????????????? ture:?????????????????????????????????????????? ??????????????????
    private int payYiDouNumber;//???????????? ?????????????????????
    private boolean is_guidPay = false;

    private boolean isBalanceLottery;//????????????????????? ???????????????
    private int balanceLottery;//???????????????

    private boolean isFromSignBalanceLottery;//???????????? ?????????????????????????????????
    private String raffleType;//??????????????????????????? ??????????????????????????? 0 ?????? 1????????????
    private Context mContext;
    private boolean mIsVip;
    private int mMaxType;
    private SimpleCountDownTimer countDownTimer;
    private Dialog mDialog;
    private BalanceLuckyDrawData mBalanceLuckyDrawData;
    public static WithdrawalLimitActivity instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal_limit);
        mContext = this;
        instance = this;
        AppManager.getAppManager().addActivity(this);
        isMad = SharedPreferencesUtil.getBooleanData(this, Pref.ISMADMONDAY, false);
        isFromPaySuccess = getIntent().getBooleanExtra("isFromPaySuccess", false);
        payLotteryNumber = getIntent().getIntExtra("payLotteryNumber", 0);
        payYiDouNumber = getIntent().getIntExtra("payYiDouNumber", 0);
        is_guidPay = getIntent().getBooleanExtra("is_guidPay", false);
        isBalanceLottery = getIntent().getBooleanExtra("isBalanceLottery", false);
        isFromSignBalanceLottery = getIntent().getBooleanExtra("isFromSignBalanceLottery", false);
//		isFromPaySuccess = true;
//		payYiDouNumber = 100;
//		isMad = true;
//		isBalanceLottery = true;

        initView();
    }

    /**
     * ?????????View
     */
    private void initView() {
        to_obtion_yidou_btn = (TextView) findViewById(R.id.to_obtion_yidou_btn);
        to_obtion_yidou_btn.setOnClickListener(this);
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        explain_limit = findViewById(R.id.explain_limit);
        explain_limit.setOnClickListener(this);
        to_withdrawal_btn = findViewById(R.id.to_withdrawal_btn);
        to_withdrawal_btn.setOnClickListener(this);
        limit_det_ll = findViewById(R.id.limit_det_ll);
        limit_det_ll.setOnClickListener(this);
        balanceTv = (TextView) findViewById(R.id.sum_balance_tv);
        balanceTv.getPaint().setFakeBoldText(true);//??????????????????
        limitTv = (TextView) findViewById(R.id.limit_tv);
        limitTv.getPaint().setFakeBoldText(true);//??????????????????
        used_yidou_tv = (TextView) findViewById(R.id.used_yidou_tv);
        un_used_yidou_tv = (TextView) findViewById(R.id.un_used_yidou_tv);
        used_yidou_tv.setOnClickListener(this);
        un_used_yidou_tv.setOnClickListener(this);
        mLuckyPanView = (LuckyPanView) findViewById(R.id.id_luckypan);
        mLuckyPanView.setMad(isMad);
//		if(isBalanceLottery){
//			mLuckyPanView.setmStrsBottom("??????");
//		}else{
//			mLuckyPanView.setmStrsBottom("??????");
//		}
        mLuckyPanView.setBalanceLottery(isBalanceLottery);

        listView1 = (ListView) findViewById(R.id.list_view1);
//        listView2 = (ListView) findViewById(R.id.list_view2);
        /**8888888888888888888888888888888888888888888888888888*/
//		findViewById(R.id.ll_title).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				openRedPacket();
//			}
//		});
//		obtainMadDialog();
//		madRedPacketOpenedDialog();
//		madOpenRedPacketDialog();
//		openRedPacket();
//		if(isMad&&remaindTimes>0&&isFromPaySuccess){
//			obtainMadDialog();
//		}
        /**8888888888888888888888888888888888888888888888888888*/
        View topView = findViewById(R.id.scoll_top_ll);
        View scoll_root_view = findViewById(R.id.scoll_root);
        tvRemaindTimes = (TextView) findViewById(R.id.crazy_remain_times_tv);
//		if(isBalanceLottery){
//			to_obtion_yidou_btn.setText("????????????");
//			topView.setBackgroundResource(R.drawable.withdrawal_main);
//			scoll_root_view.setBackgroundColor(Color.parseColor("#A377FF"));
//			tvRemaindTimes.setVisibility(View.GONE);
//			findViewById(R.id.list_view2_ll).setVisibility(View.VISIBLE);
//			findViewById(R.id.list_view2_iv).setVisibility(View.VISIBLE);
//			listView2.setVisibility(View.VISIBLE);
//		}else if(isMad){
//			to_obtion_yidou_btn.setText("??????????????????");
//			topView.setBackgroundResource(R.drawable.bg_choujiang_mad);
//			scoll_root_view.setBackgroundColor(Color.parseColor("#8813E2"));
//			tvRemaindTimes.setVisibility(View.VISIBLE);
//			setRemaindTimes();
//			findViewById(R.id.list_view2_ll).setVisibility(View.GONE);
//			findViewById(R.id.list_view2_iv).setVisibility(View.GONE);
//			listView2.setVisibility(View.GONE);
//		}else{
        to_obtion_yidou_btn.setText("????????????");
//        topView.setBackgroundResource(R.drawable.withdrawal_main);
        scoll_root_view.setBackgroundColor(Color.parseColor("#A377FF"));
        tvRemaindTimes.setVisibility(View.GONE);
//        findViewById(R.id.list_view2_ll).setVisibility(View.VISIBLE);
//        listView2.setVisibility(View.VISIBLE);
//		}
        initData(false);

//        if (isBalanceLottery) {
//            setBalanceInit();
//            balanceLottery = Integer.parseInt(SharedPreferencesUtil.getStringData(mContext, Pref.BALANCE_LOTTERY, "0"));
//            if (balanceLottery > 0) {
//                getBalanceLottery(1);
//            } else {
//                getBalanceLottery(2);
//            }
//
//        } else if (isMad) {
//            //???????????? ???????????????????????? ??????
//            if (isFromPaySuccess && payLotteryNumber > 0) {
//                obtainMadDialog();
//            }
//            querySignData();
//        } else {
//            //???????????? ????????????????????? ??????
//            if (isFromPaySuccess && payYiDouNumber > 0) {
//                if (is_guidPay) {//??????????????? ???????????????????????????
//                    getTwofoldness(true);
//                } else {
//                }
//            }
//        }
//
//        if (!isBalanceLottery) {
//            getWxIsBind();
//        }
        mStartBtn = (ImageView) findViewById(R.id.id_start_btn);
        mLuckyPanView.setOnStopListening(new OnStopListening() {

            @Override
            public void stopListening(int luckyIndex) {
                //???????????? ??????????????????
                mLuckyPanView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (redPacketValue > 0) {
                            if (isBalanceLottery) {
                                openRedPacketBalance();
                            } else {
                                openRedPacket();
                            }
                        } else {
                            if (isBalanceLottery) {
                                noRedPacketBalance();
                            } else {
                                noRedPacket();
                            }

                        }
                        mStartBtn.setClickable(true);
                        isRunning = false;
                    }
                }, 500);
            }
        });

        mStartBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//				boolean isWran = SharedPreferencesUtil.getBooleanData(mContext, Pref.USEDYIDOUWRAN, false);//????????????????????????


                if (!mLuckyPanView.isStart()) {//??????????????????????????????????????????????????????
                    if (isBalanceLottery) {
                        if (balanceLottery > 0) {
                            getBalanceValue();
                        } else {
                            getBalanceLottery(2);//????????????????????????
                        }
                    } else if (isMad && lotterynumber > 0) {//???????????????
                        getLimitValue(1);
                    } else {
                        //????????????????????????????????? ??????????????????????????????
                        if (usedYidou >= 10) {
                            //?????????????????? ?????????????????????????????????????????????
                            usedYiDouAwards(1);

                        } else if (unUsedYidou > 0) {
                            // ?????????????????? ?????????????????? ?????????????????????????????????
                            //false ????????? ?????????????????????????????????
                            getTwofoldness(false);
                        } else if (usedBalance >= 10) {//??????????????????????????????????????????(?????????????????????)
                            usedYiDouAwards(1);
                        } else {
                            notEnoughYidou();
                        }


                    }
                }

            }
        });

        initLimitAwardsList();
//		if(!isMad){
//			initYiDouAwardsList();
//		}
//        if (isBalanceLottery) {
//            initYiDouAwardsList();
//        } else if (isMad) {
//
//        } else {
//            initYiDouAwardsList();
//        }
    }


    /**
     * ????????????????????????????????? ?????????????????????????????? ????????????????????????
     *
     * @param twofoldness ??????????????????
     */
    private void usedYiDouAwards(int twofoldness) {
        ////???????????????????????? ???????????? ?????????????????????
        SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String isWarnDate = SharedPreferencesUtil.getStringData(mContext, Pref.ISWARNDATETIME, "0");
        Long isWarnDateTime = Long.valueOf(isWarnDate);
        boolean isWran = !df.format(new Date()).equals(df.format(new Date(isWarnDateTime)));//??????????????? ????????????????????????
        //??????????????????????????????????????????????????? ????????????????????????   ????????? ????????????????????????????????????
        if (usedYidou >= 10 || unUsedYidou >= (10 / twofoldness) || usedBalance >= 10) {
            if (isWran) {
                //????????????
                LuckWarn(twofoldness);
            } else {
                //????????? ??????????????????
                getLimitValue(twofoldness);
            }
        } else {

        }
    }

    /**
     * @param is_guidPay ???????????????????????????????????? ??????????????????????????????
     */
    private void getTwofoldness(final boolean is_guidPay) {
        new SAsyncTask<String, Void, HashMap<String, String>>(WithdrawalLimitActivity.this, 0) {
//			@Override
//			protected void onPreExecute() {
//				// TODO Auto-generated method stub
//				super.onPreExecute();
//				if(!is_guidPay){
//					LoadingDialog.show((FragmentActivity) mContext);
//				}
//			}

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity mContext, String... params)
                    throws Exception {

                return ComModel2.getYiDouHalve(mContext);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity mContext,
                                         HashMap<String, String> result, Exception e) {
                super.onPostExecute(mContext, result, e);
                if (e == null && result != null && "1".equals(result.get("is_open")) &&
                        Long.parseLong(result.get("end_date")) > Long.parseLong(result.get("now"))) {

                    int twofoldness = 1;
                    try {
                        twofoldness = Integer.parseInt(result.get("twofoldness"));
                    } catch (NumberFormatException e1) {

                    }
                    if (is_guidPay) {
                    } else {
                        usedYiDouAwards(twofoldness);
                    }
                } else {
                    if (is_guidPay) {
                    } else {
                        usedYiDouAwards(1);
                    }
                }
            }

        }.execute();
    }


    /**
     * ???????????? ????????????
     */
    private void getLimitValue(final int twofoldness) {
        redPacketValue = 0;//??????????????????????????????
        raffleType = "0";//?????????????????? ??????
        isRunning = true;
        mStartBtn.setClickable(false);
        mLuckyPanView.luckyStart(0);//?????????????????????


        YConn.httpPost(mContext, YUrl.GET_LUCK_DRAW, new HashMap<String, String>(), new HttpListener<BalanceLuckyDrawData>() {
            @Override
            public void onSuccess(BalanceLuckyDrawData result) {


                String status = result.getStatus();
                // 0?????? 1??????
                raffleType = result.getT();
                if ("1".equals(status)) {
                    mBalanceLuckyDrawData = result;

                    if (isMad && lotterynumber > 0) {//?????????????????? ????????????????????????0  ??????????????????
                        lotterynumber--;
                        setRemaindTimes();
                    } else if (usedYidou >= 10) {//???????????????????????? ??????????????????
                        usedYidou = usedYidou - 10;
                        used_yidou_tv.setText("????????????:" + usedYidou);

                    } else if (unUsedYidou >= (10 / twofoldness)) {// ?????????????????? ??????????????????
                        unUsedYidou = unUsedYidou - (10 / twofoldness);
                        un_used_yidou_tv.setText("????????????:" + unUsedYidou);

                    } else if (usedBalance >= 10) {//???????????????????????????
                        usedBalance = usedBalance - 10;//?????????????????? ??????????????????????????????

                        mSumBalance = mSumBalance - 10;//?????????????????? ????????????????????????
                        balanceTv.setText(new DecimalFormat("#0.00").format(mSumBalance));
                    }

                    redPacketValue = result.getRaffle();
                    //???????????? ??????????????????
                    if (redPacketValue <= 0) { //?????????
                        mLuckyPanView.luckyStart(0);
                        mLuckyPanView.luckyEnd();
                        return;
                    }

                    if (raffleType.equals("1")) {
                        mLuckyPanView.luckyStart(1);

                    } else {
                        if (redPacketValue >= 0.01 && redPacketValue < 1) {
                            mLuckyPanView.luckyStart(7);
                        } else if (redPacketValue >= 1 && redPacketValue < 3) {
                            mLuckyPanView.luckyStart(6);
                        } else if (redPacketValue >= 3 && redPacketValue < 5) {
                            mLuckyPanView.luckyStart(5);
                        } else if (redPacketValue >= 5 && redPacketValue < 10) {
                            mLuckyPanView.luckyStart(4);
                        } else if (redPacketValue >= 10 && redPacketValue < 20) {
                            mLuckyPanView.luckyStart(3);
                        } else if (redPacketValue >= 20 && redPacketValue < 50) {
                            mLuckyPanView.luckyStart(2);
                        } else {
                            mLuckyPanView.luckyStart(0);
                        }
                    }


                } else {
                    notEnoughYidou();
                }
                mLuckyPanView.luckyEnd();


            }

            @Override
            public void onError() {
                mLuckyPanView.luckyEnd();

            }
        });


    }


    private void notEnoughYidou() {
        final Dialog dialog = new Dialog(mContext, R.style.invate_dialog_style);
        dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        dialog.setCanceledOnTouchOutside(false);
        View view = View.inflate(mContext, R.layout.withdrawal_luck_warn1, null);
        view.findViewById(R.id.select_ll).setVisibility(View.GONE);
        ((TextView) view.findViewById(R.id.title)).setText("????????????");
        ((TextView) view.findViewById(R.id.yidou_wran_content_tv)).setText("???????????????????????????????????????????????????????????????");

        // ??????????????????
        TextView btn_white = (TextView) view.findViewById(R.id.btn_white);
        TextView btn_yellow_2 = (TextView) view.findViewById(R.id.btn_yellow_2);

        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // ????????????
        TextView btn_yellow = (TextView) view.findViewById(R.id.btn_yellow);

        btn_white.setVisibility(View.GONE);
        btn_yellow.setVisibility(View.GONE);
        btn_yellow_2.setVisibility(View.VISIBLE);
        btn_yellow_2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (null != CommonActivity.instance) {
                    CommonActivity.instance.finish();
                }
                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
                Intent intent = new Intent(mContext, CommonActivity.class);
                startActivity(intent);
            }
        });


        // // ?????????????????????dialog
        dialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.show();
    }

    /**
     * ????????????
     * isRefresh ???????????????????????????
     */
    private void initData(final boolean isRefresh) {


        HashMap<String, String> pairsMap = new HashMap<>();

        YConn.httpPost(this, YUrl.QUERY_VIP_INFO_FL, pairsMap
                , new HttpListener<VipInfo>() {
                    @Override
                    public void onSuccess(VipInfo vipInfo) {


                        mIsVip = CommonUtils.isVip(vipInfo.getIsVip(), vipInfo.getMaxType());
                        mMaxType = vipInfo.getMaxType();


                        YConn.httpPost(mContext, YUrl.MY_WALLET_INFO, new HashMap<String, String>(), new HttpListener<MyWalletData>() {
                            @Override
                            public void onSuccess(MyWalletData myWalletData) {


                                usedBalance = myWalletData.getBalance();
                                unUsedBalance = myWalletData.getFreeze_balance();
                                mSumBalance = usedBalance + unUsedBalance;//?????????????????????????????????????????????

                                limitTv.setText("" + new DecimalFormat("#0.00").format(myWalletData.getRaffleMoney()));


                                setBalanceTvText(WithdrawalLimitActivity.this);

                                if (!isRefresh) {
                                    autoLimitDialog();
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

    /**
     * ???????????? ??????????????????
     *
     * @param mContext
     */
    private void setBalanceTvText(FragmentActivity mContext) {
        int mTwofoldness = Integer
                .parseInt(SharedPreferencesUtil.getStringData(mContext, Pref.TWOFOLDNESS, 1 + ""));
        int mIsOpen = Integer
                .parseInt(SharedPreferencesUtil.getStringData(mContext, Pref.IS_OPEN, -1 + ""));
        if (mIsOpen == 1) {//?????????????????????
            balanceTv.setText("" + new DecimalFormat("#0.00").format(mSumBalance * mTwofoldness));
        } else {
            balanceTv.setText("" + new DecimalFormat("#0.00").format(mSumBalance));
        }
    }

    /**
     * ????????????  ????????????   ??????
     */
    private void initLimitAwardsList() {
        new SAsyncTask<Void, Void, List<HashMap<String, String>>>(WithdrawalLimitActivity.this, R.string.wait) {

            @Override
            protected List<HashMap<String, String>> doInBackground(FragmentActivity mContext, Void... params) throws Exception {
                return ComModel2.getExtractNewData(mContext);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity mContext, List<HashMap<String, String>> result, Exception e) {
                super.onPostExecute(mContext, result, e);

                mListData1 = new ArrayList<HashMap<String, String>>();
                if (null == e && result != null) {
                    List<HashMap<String, String>> result1 = result;//??????????????????????????? ????????????
                    //????????????????????????
//					if(result1.size()<25){
                    for (int i = 0; i < 50; i++) {
                        if (i < result1.size() * 2) { //??????result1.size()*2???????????????????????????
                            if (i % 2 == 0) {
                                mListData1.add(result1.get(i / 2));//??????????????????  ??????????????????????????????????????????
                            } else {
                                addToLimitList();
                            }
                        } else {
                            addToLimitList();
                        }
                    }
//					}else{
//						for (int i = 0; i <50; i++) {
//							if(i%2==0){
//								mListData1.add(result1.get(i/2));//??????????????????  ??????????????????????????????????????????
//							}else{
//								addToLimitList();
//							}
//						}
//					}

                } else {//???????????? ??????????????????
                    for (int i = 0; i < 50; i++) {
                        addToLimitList();
                    }
                }
                adapter1 = new MyAdapter(WithdrawalLimitActivity.this, mListData1);
                listView1.setAdapter(adapter1);
                if (mTimer1 != null) {
                    mTimer1.cancel();
                }
                mTimer1 = new Timer();
                mTimer1.schedule(task1, 20, 20);
            }
        }.execute();
    }

    /**
     * ????????????????????? ??????????????????
     */
    private void addToLimitList() {
        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("nname", StringUtils.getVirtualName() + "***" + StringUtils.getVirtualName());
        map1.put("p_name", "???????????????????????????");
        map1.put("pic", "defaultcommentimage/" + StringUtils.getDefaultImg());
        map1.put("num", "+" + (int) (Math.random() * (21 - 10) + 10) + StringUtils.getVirtualDecimalAwardsWithdrawal() + "???");//10-900
        mListData1.add(map1);
    }


    /**
     * ????????????  ????????????   ??????
     */
//    private void initYiDouAwardsList() {
//        new SAsyncTask<Void, Void, List<HashMap<String, String>>>(WithdrawalLimitActivity.this, R.string.wait) {
//
//            @Override
//            protected List<HashMap<String, String>> doInBackground(FragmentActivity mContext, Void... params) throws Exception {
//                return ComModel2.getYiDouNewData(mContext);
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity mContext, List<HashMap<String, String>> result, Exception e) {
//                super.onPostExecute(mContext, result, e);
//                mListData2 = new ArrayList<HashMap<String, String>>();
//                if (null == e && result != null) {
//                    List<HashMap<String, String>> result2 = result;//??????????????????????????? ????????????
//                    //??????????????????????????????
////					if(result2.size()<25){
//                    for (int i = 0; i < 50; i++) {
//                        if (i < result2.size() * 2) {
//                            if (i % 2 == 0) {
//                                mListData2.add(result2.get(i / 2));//??????????????????  ??????????????????????????????????????????
//                            } else {
//                                addToYiDouList();
//                            }
//                        } else {
//                            addToYiDouList();
//                        }
//                    }
////					}else{
////						for (int i = 0; i <50; i++) {
////							if(i%2==0){
////								mListData2.add(result2.get(i/2));
////							}else{
////								addToYiDouList();
////							}
////						}
////					}
//
//                } else {
//                    for (int i = 0; i < 50; i++) {
//                        addToYiDouList();
//                    }
//                }
//                adapter2 = new MyAdapter(WithdrawalLimitActivity.this, mListData2);
//                listView2.setAdapter(adapter2);
//                if (mTimer2 != null) {
//                    mTimer2.cancel();
//                }
//                mTimer2 = new Timer();
//                mTimer2.schedule(task2, 20, 20);
//            }
//        }.execute();
//    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
//		if(isRunning){
//			return;
//		}
        switch (v.getId()) {
            case R.id.to_obtion_yidou_btn:
//                if (isBalanceLottery) {
//                    toGetYiDou(); //?????????????????????????????? ??????????????????????????????
//                } else if (isMad) {
//                    Intent intent = new Intent(mContext, HotSaleActivity.class);
//                    intent.putExtra("id", "6");
//                    intent.putExtra("title", "??????");
//                    mContext.startActivity(intent);
//                } else {
//                    toGetYiDou();
//                }

                if (null != CommonActivity.instance) {
                    CommonActivity.instance.finish();
                }

                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
                Intent intent = new Intent(mContext, CommonActivity.class);
                startActivity(intent);
                break;
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.explain_limit:
                showLimitShuoming();
                break;
            case R.id.to_withdrawal_btn://??????


                YConn.httpPost(this, YUrl.QUERY_VIP_INFO_FL, new HashMap<String, String>()
                        , new HttpListener<VipInfo>() {
                            @Override
                            public void onSuccess(VipInfo vipInfo) {
                                if (CommonUtils.isVip(vipInfo.getIsVip(), vipInfo.getMaxType())) {

                                    mContext.startActivity(new Intent(mContext, NewWalletActivity.class)
                                    );
                                } else {
                                    mContext.startActivity(new Intent(mContext, MyVipListActivity.class)
                                            .putExtra("guide_vipType", 4)
                                    );
                                }
                            }

                            @Override
                            public void onError() {
                            }
                        });

//                Intent to_withdrawal_btn_intent = new Intent(mContext, MyWalletCommonFragmentActivity.class);
//                to_withdrawal_btn_intent.putExtra("flag", "withDrawalFragment");
//                to_withdrawal_btn_intent.putExtra("alliance", "wallet");
//                startActivity(to_withdrawal_btn_intent);
                break;
            case R.id.limit_det_ll://????????????
                toLimitDet();
                break;
            case R.id.un_used_yidou_tv://????????????
            case R.id.used_yidou_tv://????????????
                Intent yidou_intent = new Intent(mContext, ClothesBeanDetailActivity.class);
                yidou_intent.putExtra("pearsCount", usedYidou + "");
                yidou_intent.putExtra("freezeCount", unUsedYidou + "");
                startActivity(yidou_intent);
//			overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                break;
            default:
                break;
        }
    }

    /**
     * ??????????????????
     */
    private void toLimitDet() {
        Intent limit_det_ll_intent = new Intent(mContext, IntergralDetailActivity.class);
        limit_det_ll_intent.putExtra("page", 0);
        limit_det_ll_intent.putExtra("isTiXianMingXi", true);
        startActivity(limit_det_ll_intent);
//		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
    }

    @Override
    public void onBackPressed() {
//		if(isRunning){
//			return;
//		}
//		if(wxIsNotBind&&!isBalanceLottery){
//			notBindDialog();
//			return;
//		}

//        super.onBackPressed();
        if (mTimer1 != null) {
            mTimer1.cancel();
        }
        if (task1 != null) {
            task1.cancel();
        }

        if (mLuckyPanView.mBgBitmap != null && !mLuckyPanView.mBgBitmap.isRecycled()) {
            mLuckyPanView.mBgBitmap.recycle();
        }

        if (null != CommonActivity.instance) {
            CommonActivity.instance.finish();
        }
        SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
        Intent intent = new Intent(mContext, CommonActivity.class);
        startActivity(intent);
        finish();


    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mLuckyPanView.mBgBitmap != null && !mLuckyPanView.mBgBitmap.isRecycled()) {
            mLuckyPanView.mBgBitmap.recycle();
        }
        if (isFromPaySuccess) {
            String totalAccount = SharedPreferencesUtil.getStringData(mContext, Pref.PAYSUCCESSDIALOG, "-1");//????????????
            SharedPreferencesUtil.saveStringData(mContext, Pref.PAYSUCCESSDIALOG_SHOW_DIALOG
                    , "" + totalAccount);
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        CommonUtils.disableScreenshots(this);

        mLuckyPanView.mBgBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.zhuanpan);
        if (mSumBalance != 0) {
            setBalanceTvText(this);
        }

    }

    /**
     * ??????????????????
     */
    private void showLimitShuoming() {
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = View.inflate(mContext, R.layout.withdrawal_limit_shuoming, null);
        mDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        mDialog.setCanceledOnTouchOutside(false);
//		if(isMad){
//			TextView tv7 = (TextView) view.findViewById(R.id.tv7);
//			tv7.setVisibility(View.VISIBLE);
//		}
        // ??????????????????
        view.findViewById(R.id.liebiao).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                mDialog.dismiss();
            }
        });
        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        // ????????????????????????
        view.findViewById(R.id.gobuy2).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//				getActivity().finish();
                mDialog.dismiss();
                mStartBtn.performClick();
            }
        });

        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.show();
    }


    /**
     * ????????????
     */
    private boolean isSelect = true;

    private void LuckWarn(final int twofoldness) {
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.invate_dialog_style);
        mDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        mDialog.setCanceledOnTouchOutside(false);
        View view = View.inflate(mContext, R.layout.withdrawal_luck_warn, null);
        // ??????????????????
        view.findViewById(R.id.btn_white).setVisibility(View.GONE);
        TextView tvWarn = (TextView) view.findViewById(R.id.yidou_wran_content_tv);
        tvWarn.setText("????????????" + (10 / twofoldness) + "???????????????????????????");
//		view.findViewById(R.id.btn_white).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				dialog.dismiss();
//			}
//		});
        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        //
        View btn_yellow = view.findViewById(R.id.btn_yellow);
        btn_yellow.setVisibility(View.GONE);
        View btn_yellow_2 = view.findViewById(R.id.btn_yellow_2);
        btn_yellow_2.setVisibility(View.VISIBLE);
        btn_yellow_2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//				getActivity().finish();
                mDialog.dismiss();

                getLimitValue(twofoldness);

            }
        });
//		final ImageView ivSelect = (ImageView) view.findViewById(R.id.icon_select_withdrawal);
//		SharedPreferencesUtil.saveBooleanData(mContext, Pref.USEDYIDOUWRAN, true);//????????????
//		ivSelect.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				isSelect = !isSelect;
//				if(isSelect){
//					ivSelect.setImageResource(R.drawable.withdrawal_select);
//				}else{
//					ivSelect.setImageResource(R.drawable.withdrawal_normal);
//				}
//				SharedPreferencesUtil.saveBooleanData(mContext, Pref.USEDYIDOUWRAN, isSelect);
//			}
//		});
        SharedPreferencesUtil.saveStringData(mContext, Pref.ISWARNDATETIME, System.currentTimeMillis() + "");

        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.show();
    }


    /**
     * ????????????????????????
     */
    private void getRaffleNumDialog(int num) {
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.invate_dialog_style);
        mDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        mDialog.setCanceledOnTouchOutside(false);
        View view = View.inflate(mContext, R.layout.withdrawal_balance_lottery, null);
        TextView titleTv = ((TextView) view.findViewById(R.id.title));
        TextView textView = ((TextView) view.findViewById(R.id.vitual_lottery_content_tv));
        TextView btn_yellow = (TextView) view.findViewById(R.id.btn_yellow);


        titleTv.setText("????????????");
        textView.setText("??????" + num + "?????????????????????");
        btn_yellow.setText("???????????????");


        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });


        view.findViewById(R.id.btn_yellow).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mStartBtn.performClick();


            }
        });
        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.show();
    }


    /**
     * ????????????????????????
     * ????????????????????????????????????
     */
    private void getRaffleNum() {
        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mContext, 0) {
            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity mContext, Void... params)
                    throws Exception {
                return ComModelL.getRaffleNum(mContext);
            }

            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity mContext, HashMap<String, String> result, Exception e) {
                super.onPostExecute(mContext, result, e);
                if (e == null && result != null && "1".equals(result.get("status"))) {
                    int num = 0;
                    try {
                        num = Integer.parseInt(result.get("data"));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    if (num > 0) {
                        getRaffleNumDialog(num);
                    }

                }

            }
        }.execute();
    }


    /**
     * ??????????????????
     */
    private void openRedPacket() {
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.withdrawal_limit_open_redpacket, null);
        TextView open_tv = (TextView) view.findViewById(R.id.open_tv);
        open_tv.getPaint().setFakeBoldText(true);//??????????????????

        final ImageView open_red_packet = (ImageView) view.findViewById(R.id.open_red_packet);
        open_red_packet.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ObjectAnimator
                        .ofFloat(open_red_packet, "rotationY", 0.0F, -790.0F)//
                        .setDuration(1600)//
                        .start();
                open_red_packet.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                        redPacketOpened();
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
        mDialog.show();
    }


    /**
     * ??????????????????
     */
    private void noRedPacket() {
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.invate_dialog_style);
        mDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        mDialog.setCanceledOnTouchOutside(false);
        View view = View.inflate(mContext, R.layout.withdrawal_limit_no_redpacket, null);
        TextView no_packet_tv = (TextView) view.findViewById(R.id.no_packet_tv);
        no_packet_tv.getPaint().setFakeBoldText(true);//??????????????????
        //??????
        view.findViewById(R.id.btn_white).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        // ????????????
        view.findViewById(R.id.btn_yellow).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();

                mStartBtn.performClick();

            }
        });

        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.show();
    }

    /**
     * ????????????????????????
     */
    private void redPacketOpened() {

        initData(true);


        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }

        if (!"1".equals(raffleType) && mBalanceLuckyDrawData.getSumExtract() > 0) {//????????????????????????????????????
            showRedPacketOpenedTXED();
            return;
        }


        mDialog = new Dialog(mContext, R.style.invate_dialog_style);
        final View view = View.inflate(mContext, R.layout.withdrawal_limit_redpacket_value, null);
        mDialog.setCanceledOnTouchOutside(false);
        TextView red_packet_value = (TextView) view.findViewById(R.id.red_packet_value);
        TextView red_packet_value_content = (TextView) view.findViewById(R.id.red_packet_value_content);
        red_packet_value.getPaint().setFakeBoldText(true);//??????????????????
        red_packet_value.setText(new DecimalFormat("#0.00").format(redPacketValue) + "???");
        red_packet_value_content.setText("???????????????" + new DecimalFormat("#0.00").format(redPacketValue) + "??????????????????????????????????????????");


//		mLimit += redPacketValue;
//		limitTv.setText(new DecimalFormat("#0.00").format(mLimit));

        //??????????????????
        view.findViewById(R.id.btn_white).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                toLimitDet();
            }
        });
        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        // ????????????
        view.findViewById(R.id.btn_yellow).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();

                mStartBtn.performClick();

            }
        });

        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));

        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "Y", 0.1F, 1.0F).setDuration(500);
        anim.start();
        anim.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                view.setAlpha(cVal);
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            }
        });
        mDialog.show();
    }

    private void showRedPacketOpenedTXED() {


        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }

        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.dialog_withdraw_chouzhong_txed, null);

        TextView tv_money = view.findViewById(R.id.tv_money);
        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView bt1 = view.findViewById(R.id.bt1);
        TextView bt2 = view.findViewById(R.id.bt2);
        TextView tv_time = view.findViewById(R.id.tv_time);
        LinearLayout ll_count_time = view.findViewById(R.id.ll_count_time);

        tv_money.setText(mBalanceLuckyDrawData.getRaffle() + "???");

        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != countDownTimer) {
                    countDownTimer.cancel();
                }
                mDialog.dismiss();
            }
        });

        if (!mIsVip) {

            Spanned sd = Html.fromHtml("<font color='#FDCC21'><strong>" +
                    mBalanceLuckyDrawData.getRaffle() + "???"
                    + "</strong></font>???????????????????????????????????????????????????");
            tv1.setText(sd);


            Spanned sd2 = Html.fromHtml("??????????????????<font color='#FDCC21'><strong>"
                    + mBalanceLuckyDrawData.getSumExtract() + "???</strong></font>?????????????????????????????????????????????");

            tv2.setText(sd2);

            long countTime = 9 * 60 * 1000;
            if (null != mBalanceLuckyDrawData && mBalanceLuckyDrawData.getExpireTime() > 0) {
                countTime = mBalanceLuckyDrawData.getExpireTime();
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

            bt1.setText("??????????????????");
            bt2.setText("???????????????????????????");


            bt1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent(mContext, MyVipListActivity.class)
                            .putExtra("guide_vipType", 4)
                    );

                }
            });


            bt2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.dismiss();
                    mStartBtn.performClick();


                }
            });

        } else {

            if (mMaxType == 4) {//??????


                Spanned sd = Html.fromHtml("<font color='#FDCC21'><strong>" +
                        mBalanceLuckyDrawData.getRaffle() + "???"
                        + "</strong></font>???????????????????????????????????????????????????"
                );
                tv1.setText(sd);


                Spanned sd2 = Html.fromHtml("??????????????????<font color='#FDCC21'><strong>"
                        + mBalanceLuckyDrawData.getSumExtract() + "???</strong></font>????????????????????????????????????????????????");

                tv2.setText(sd2);

                long countTime = 9 * 60 * 1000;
                if (null != mBalanceLuckyDrawData && mBalanceLuckyDrawData.getExpireTime() > 0) {
                    countTime = mBalanceLuckyDrawData.getExpireTime();
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

                bt1.setText("????????????");
                bt2.setText("???????????????????????????");


                bt1.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent intent = new Intent(mContext, MyYJactivity.class);
                        startActivity(intent);


                    }
                });


                bt2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                        mStartBtn.performClick();


                    }
                });


            } else {//??????????????????

                Spanned sd = Html.fromHtml("<font color='#FDCC21'><strong>" +
                        mBalanceLuckyDrawData.getRaffle() + "???"
                        + "</strong></font>???????????????????????????????????????????????????"
                );
                tv1.setText(sd);


                Spanned sd2 = Html.fromHtml("????????????<font color='#FDCC21'><strong>"
                        + mBalanceLuckyDrawData.getSumExtract() + "???</strong></font>??????????????????");

                tv2.setText(sd2);


                ll_count_time.setVisibility(View.GONE);

                bt1.setText("?????????????????????");
                bt2.setText("???????????????????????????");

                bt1.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext, MyWalletActivity.class));
                    }
                });

                bt2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                        mStartBtn.performClick();
                    }
                });

            }
        }

//
//        Spanned sd = Html.fromHtml("???????????????????????????<font color='#FDCC21'><strong>" +
//                "????????????</strong></font>" +
//                "?????????<font color='#FDCC21'><strong>" +
//                "79?????????</strong></font>" +
//                "???");
//
//
//        tv1.setText(sd);
//
//        Spanned sd2 = Html.fromHtml("?????????<font color='#FDCC21'><strong>" +
//                "????????????</strong></font>????????????????????????<font color='#FDCC21'><strong>" +
//                mBalanceLuckyDrawData.getRaffle() + "???</strong></font>?????????");
//        tv2.setText(sd2);
//
//
//        long countTime = 9 * 60 * 1000;
//        if (null != mBalanceLuckyDrawData && mBalanceLuckyDrawData.getExpireTime() > 0) {
//            countTime = mBalanceLuckyDrawData.getExpireTime();
//        }
//        countDownTimer = new SimpleCountDownTimer(countTime, tv_time).setOnFinishListener(new SimpleCountDownTimer.OnFinishListener() {
//            @Override
//            public void onFinish() {
//                if (null != mDialog) {
//                    mDialog.dismiss();
//                }
//            }
//        });
//        countDownTimer.start();
//
//
//        bt1.setOnClickListener(new OnClickListener() {//????????????
//            @Override
//            public void onClick(View view) {//????????????
//
//            }
//        });
//
//
//        bt2.setOnClickListener(new OnClickListener() {//????????????laststartLuckBtn
//            @Override
//            public void onClick(View view) {//????????????
//                mDialog.dismiss();
//                mStartBtn.performClick();
//
//
//            }
//        });


        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();

    }

    /**
     * ????????? ????????????  ??? Adapter
     */
    public class MyAdapter extends BaseAdapter {
        private List<HashMap<String, String>> mListData;
        private Context mContext;

        public MyAdapter(Context mContext, List<HashMap<String, String>> mListData) {
            super();
            this.mListData = mListData;
            this.mContext = mContext;
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
                convertView = View.inflate(WithdrawalLimitActivity.this, R.layout.item_withdrawal_limit, null);
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

            GlideUtils.initRoundImage(Glide.with(WithdrawalLimitActivity.this), WithdrawalLimitActivity.this, mListData.get(position % mListData.size()).get("pic").toString(), holder.headIv);

            holder.mNameTv.setText(mListData.get(position % mListData.size()).get("nname").toString());
            holder.mAwardsTv.setText(mListData.get(position % mListData.size()).get("num").toString());
            holder.tv.setText(mListData.get(position % mListData.size()).get("p_name").toString());
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
                    listView1.smoothScrollBy(1, 0);
                }
            });

        }
    };
//    TimerTask task2 = new TimerTask() {
//
//        @Override
//        public void run() {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    listView2.smoothScrollBy(1, 0);
//
//                }
//            });
//
//        }
//    };

    /*
     *********************************************** ???????????????**************************************************
     **/
    /**
     * ?????????????????????????????? ???????????? ??????????????????
     */
//	private void madTimesOverDialog(){
//		final Dialog dialog = new Dialog(mContext, R.style.invate_dialog_style);
//		View view = View.inflate(mContext, R.layout.withdrawal_limit_yidou_exp, null);
//		view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
//		TextView title = (TextView)view.findViewById(R.id.title);
//		title.setText("????????????");
//		TextView tv1 = (TextView)view.findViewById(R.id.tv1);
//		tv1.setText("?????????????????????????????????????????????????????????????????????????????????????????? 100%?????????");
//		view.findViewById(R.id.tv2).setVisibility(View.GONE);
//		view.findViewById(R.id.tv3).setVisibility(View.GONE);
//		Button btn_yellow = (Button)view.findViewById(R.id.btn_yellow);
//		btn_yellow.setText("??????????????????");
//		view.findViewById(R.id.btn_yellow).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//				Intent intent = new Intent(mContext, HotSaleActivity.class);
//				intent.putExtra("id", "6");
//				intent.putExtra("title", "??????");
//				mContext.startActivity(intent);
//			}
//		});
//
//		// // ?????????????????????dialog
//		dialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
//				LinearLayout.LayoutParams.MATCH_PARENT));
//		dialog.show();
//	}

    /**
     * ??????20?????????????????????
     */
    private void obtainMadDialog() {
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = View.inflate(mContext, R.layout.withdrawal_limit_obtain_mad_dialog, null);
        mDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        mDialog.setCanceledOnTouchOutside(false);
        TextView remaindtimesTv = (TextView) view.findViewById(R.id.obtain_mad_remaindtimes_tv);
        remaindtimesTv.setText("??????" + payLotteryNumber + "?????????????????????");
        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_yellow).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mStartBtn.performClick();
            }
        });

        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.show();
    }

    /**
     * ???????????????  ??????????????????
     */


    private void setRemaindTimes() {
        if (lotterynumber > 0) {
            String text = "??????" + lotterynumber + "?????????????????????";
            SpannableString textSpan = new SpannableString(text);
            textSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 2, text.length() - 6,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvRemaindTimes.setText(textSpan);
        } else {
            String text = "0?????????????????????";
            SpannableString textSpan = new SpannableString(text);
            textSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF400")), 0, 2,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvRemaindTimes.setText(textSpan);
        }
    }

    /**
     * ????????????????????????(?????????????????????????????????)
     */
    private void querySignData() {

        new SAsyncTask<Void, Void, HashMap<String, Object>>(WithdrawalLimitActivity.this, 0) {
            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity mContext, Void... params)
                    throws Exception {
                return ComModel2.getSignData(WithdrawalLimitActivity.this);
            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity mContext, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(mContext, result, e);
                if (e == null && result != null) {
                    try {
                        lotterynumber = Integer.parseInt((String) result.get("LotteryNumber"));
                    } catch (Exception e2) {

                    }
                }
                setRemaindTimes();
            }
        }.execute();

    }
    /*
     *********************************************** ???????????????**************************************************
     **/


    /**
     * ????????????  ???????????????????????????
     *
     */
    private void autoLimitDialog() {
//        if (isBalanceLottery || isFromPaySuccess) {
//            return;
//        }
//        String withdrawLimitDialogTimes = SharedPreferencesUtil.getStringData(mContext, Pref.WITHDRAWLIMITDIALOG, "0");
//        Long longDialogTimes = Long.valueOf(withdrawLimitDialogTimes);
//        if (longDialogTimes < 2) {
//            longDialogTimes++;
////			showLimitShuoming();
//            SharedPreferencesUtil.saveStringData(mContext, Pref.WITHDRAWLIMITDIALOG, longDialogTimes + "");
//        } else {
            getRaffleNum();
//        }
    }

    /*
     *********************************************** ??????????????????**************************************************
     **/

    /**
     * ????????????????????????????????? ??????
     */
    private void getBalanceValue() {
        redPacketValue = 0;//??????????????????????????????
        isRunning = true;
        mStartBtn.setClickable(false);
        mLuckyPanView.luckyStart(0);//?????????????????????
        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mContext, 0) {
            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity mContext, Void... params)
                    throws Exception {

                return ComModelL.getBalanceLuckResult(mContext);
            }

            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity mContext, HashMap<String, String> result, Exception e) {
                super.onPostExecute(mContext, result, e);
                if (e == null && result != null) {
                    String status = result.get("status");
                    if ("1".equals(status)) {
                        if (balanceLottery > 0) {//???????????????  ??????????????????
                            balanceLottery--;
                            SharedPreferencesUtil.saveStringData(mContext, Pref.BALANCE_LOTTERY,
                                    balanceLottery + "");
                        }

                        redPacketValue = Double.parseDouble(result.get("data"));

                        //??????????????????????????? ???????????????
                        double balanceLotterySumValue =
                                Double.parseDouble(SharedPreferencesUtil.getStringData(mContext, Pref.BALANCE_LOTTERY_SUM_VALUE
                                        + YCache.getCacheUser(mContext).getUser_id(), "0"));
                        balanceLotterySumValue += redPacketValue;
                        SharedPreferencesUtil.saveStringData(mContext, Pref.BALANCE_LOTTERY_SUM_VALUE
                                        + YCache.getCacheUser(mContext).getUser_id(),
                                new java.text.DecimalFormat("#0.00").format(balanceLotterySumValue));

                        //???????????? ????????????
                        if (redPacketValue >= 0.01 && redPacketValue < 5) {
                            mLuckyPanView.luckyStart(7);
                        } else if (redPacketValue >= 5 && redPacketValue < 10) {
                            mLuckyPanView.luckyStart(6);
                        } else if (redPacketValue >= 10 && redPacketValue < 50) {
                            mLuckyPanView.luckyStart(5);
                        } else if (redPacketValue >= 50 && redPacketValue < 100) {
                            mLuckyPanView.luckyStart(4);
                        } else if (redPacketValue >= 100 && redPacketValue < 200) {
                            mLuckyPanView.luckyStart(3);
                        } else if (redPacketValue >= 200 && redPacketValue < 500) {
                            mLuckyPanView.luckyStart(2);
                        } else if (redPacketValue >= 500 && redPacketValue <= 1000) {
                            mLuckyPanView.luckyStart(1);
                        } else {
                            mLuckyPanView.luckyStart(0);
                        }

                    }
                    mLuckyPanView.luckyEnd();
                } else {
                    mLuckyPanView.luckyEnd();
                }

            }
        }.execute();
    }

    /**
     * ???????????????????????????????????? ?????????????????????
     *
     * @param type =1 ??????5????????????.
     *             type  =2 5??????????????? ??????
     */
    private void getBalanceLottery(final int type) {
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.invate_dialog_style);
        mDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        mDialog.setCanceledOnTouchOutside(false);
        View view = View.inflate(mContext, R.layout.withdrawal_balance_lottery, null);
        TextView titleTv = ((TextView) view.findViewById(R.id.title));
        TextView textView = ((TextView) view.findViewById(R.id.vitual_lottery_content_tv));
        TextView btn_yellow = (TextView) view.findViewById(R.id.btn_yellow);


        if (type == 1) {
            titleTv.setText("????????????");
            textView.setText("??????" + balanceLottery + "?????????????????????");
            btn_yellow.setText("???????????????");
        } else if (type == 2) {

            String balanceLotterySumCount =
                    SharedPreferencesUtil.getStringData(mContext, Pref.BALANCE_LOTTERY_SUM_COUNT, "0");
            double balanceLotterySumValue =
                    Double.parseDouble(SharedPreferencesUtil.getStringData(mContext, Pref.BALANCE_LOTTERY_SUM_VALUE
                            + YCache.getCacheUser(mContext).getUser_id(), "0"));
            String text = "";
            if (balanceLotterySumValue <= 0) {
                text = "??????" + balanceLotterySumCount + "??????????????????????????????????????????????????????????????????????????????????????????????????????????????????1000??????????????????~";
            } else {
                text = "??????" + balanceLotterySumCount + "????????????????????????????????????????????????????????????" +
                        new java.text.DecimalFormat("#0.00").format(balanceLotterySumValue)
                        + "??????????????????????????????????????????????????????????????????????????????????????????????????????1000??????????????????~";

            }

            SpannableString textSpan = new SpannableString(text);
            textSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), text.length() - 21, text.length() - 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //??????
            textSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#FDCC21")), text.length() - 21, text.length() - 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(textSpan);
            titleTv.setText("????????????");
            btn_yellow.setText("??????????????????");

            textView.setGravity(Gravity.LEFT);
        }

        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });


        view.findViewById(R.id.btn_yellow).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                if (type == 1) {
                    mStartBtn.performClick();
                } else if (type == 2) {
                    if (isFromSignBalanceLottery) {
                        Intent intent = new Intent(mContext, MainMenuActivity.class);
                        intent.putExtra("toYf", "toYf");
                        startActivity(intent);
                    } else {
                        onBackPressed();
                    }
                }

            }
        });
        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.show();
    }


    /**
     * ??????????????????(????????????)
     */
    private void openRedPacketBalance() {
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.withdrawal_limit_open_redpacket, null);
        TextView open_tv = (TextView) view.findViewById(R.id.open_tv);
        open_tv.getPaint().setFakeBoldText(true);//??????????????????

        final ImageView open_red_packet = (ImageView) view.findViewById(R.id.open_red_packet);
        ((TextView) view.findViewById(R.id.open_bottom_tv)).setText("?????????????????????????????????????????????");

        open_red_packet.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ObjectAnimator
                        .ofFloat(open_red_packet, "rotationY", 0.0F, -790.0F)//
                        .setDuration(1600)//
                        .start();
                open_red_packet.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                        redPacketOpenedBalance();
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
        mDialog.show();
    }


    /**
     * ??????????????????(????????????)
     */
    private void noRedPacketBalance() {
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.invate_dialog_style);
        mDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        mDialog.setCanceledOnTouchOutside(false);
        View view = View.inflate(mContext, R.layout.withdrawal_limit_no_redpacket, null);
        TextView no_packet_tv = (TextView) view.findViewById(R.id.no_packet_tv);
        no_packet_tv.getPaint().setFakeBoldText(true);//??????????????????
        view.findViewById(R.id.btn_white).setVisibility(View.GONE);
//		//??????
//		view.findViewById(R.id.btn_white).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        // ????????????
        view.findViewById(R.id.btn_yellow).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();

                mStartBtn.performClick();

            }
        });

        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.show();
    }

    /**
     * ????????????????????????(????????????)
     */
    private void redPacketOpenedBalance() {
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.invate_dialog_style);
        final View view = View.inflate(mContext, R.layout.withdrawal_limit_redpacket_value, null);
        mDialog.setCanceledOnTouchOutside(false);
        TextView red_packet_value = (TextView) view.findViewById(R.id.red_packet_value);
        TextView red_packet_value_content = (TextView) view.findViewById(R.id.red_packet_value_content);
        red_packet_value.getPaint().setFakeBoldText(true);//??????????????????
        red_packet_value.setText(new DecimalFormat("#0.00").format(redPacketValue) + "???");
//		red_packet_value_content.setText(new DecimalFormat("#0.00").format(redPacketValue)+"???????????????????????????????????????????????????");
        red_packet_value_content.setText("???????????????" + new DecimalFormat("#0.00").format(redPacketValue) + "??????????????????????????????????????????");
        red_packet_value_content.setGravity(Gravity.LEFT);

        mSumBalance += redPacketValue;
        balanceTv.setText(new DecimalFormat("#0.00").format(mSumBalance));

        view.findViewById(R.id.btn_white).setVisibility(View.GONE);
//		//??????????????????
//		view.findViewById(R.id.btn_white).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//				toLimitDet();
//			}
//		});
        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        // ????????????
        Button btn_yellow = (Button) view.findViewById(R.id.btn_yellow);
        btn_yellow.setText("????????????");
        btn_yellow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();

                mStartBtn.performClick();

            }
        });

        // // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));

        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "Y", 0.1F, 1.0F).setDuration(500);
        anim.start();
        anim.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                view.setAlpha(cVal);
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            }
        });
        mDialog.show();
    }


    private void setBalanceInit() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String balanceLotteryTimes = SharedPreferencesUtil.getStringData(mContext, Pref.BALANCE_LOTTERY_DATE, "0");
        long balanceLotteryTimesLong = Long.valueOf(balanceLotteryTimes);
        if ("0".equals(balanceLotteryTimes) || !df.format(new Date()).equals(df.format(new Date(balanceLotteryTimesLong)))) {
            SharedPreferencesUtil.saveStringData(mContext, Pref.BALANCE_LOTTERY_SUM_COUNT, "0");
            SharedPreferencesUtil.saveStringData(mContext, Pref.BALANCE_LOTTERY, "0");
            SharedPreferencesUtil.saveStringData(mContext, Pref.BALANCE_LOTTERY_SUM_VALUE
                    + YCache.getCacheUser(mContext).getUser_id(), "0");
        }
    }

    /*
     *********************************************** ??????????????????**************************************************
     **/


    private boolean wxIsNotBind;

    /**
     * ????????????????????????????????????
     */
    private void getWxIsBind() {
        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mContext, 0) {
            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity mContext, Void... params)
                    throws Exception {

                return ComModelL.getWxIsBind(mContext);
            }

            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity mContext, HashMap<String, String> result, Exception e) {
                super.onPostExecute(mContext, result, e);
                if (e == null && result != null && "1".equals(result.get("status"))) {
                    if ("1".equals(result.get("data"))) {//1???????????? 0 ?????????
                        wxIsNotBind = false;
                    } else if ("0".equals(result.get("data"))) {
                        wxIsNotBind = true;
                    }
                }

            }
        }.execute();

    }


    @Override
    public void finish() {
        super.finish();
        if (mTimer1 != null) {
            mTimer1.cancel();
        }
        if (task1 != null) {
            task1.cancel();
        }
//        if (mTimer2 != null) {
//            mTimer2.cancel();
//        }
//        if (task2 != null) {
//            task2.cancel();
//        }
        if (mLuckyPanView.mBgBitmap != null && !mLuckyPanView.mBgBitmap.isRecycled()) {
            mLuckyPanView.mBgBitmap.recycle();
        }
    }

    @Override
    protected void onDestroy() {
        if (mTimer1 != null) {
            mTimer1.cancel();
        }
        if (task1 != null) {
            task1.cancel();
        }

        if (mLuckyPanView.mBgBitmap != null && !mLuckyPanView.mBgBitmap.isRecycled()) {
            mLuckyPanView.mBgBitmap.recycle();
        }
        super.onDestroy();
    }
}
