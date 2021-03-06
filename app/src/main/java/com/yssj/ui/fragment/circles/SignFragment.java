package com.yssj.ui.fragment.circles;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.yssj.Constants;
import com.yssj.TestActivity;
import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.activity.wxapi.WXEntryActivity;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.MyListView;
import com.yssj.custom.view.SignCalendar.CalendarView;
import com.yssj.custom.view.SignCalendar.DateUtils;
import com.yssj.custom.view.WaitNextTaskDialog;
import com.yssj.entity.BaseData;
import com.yssj.entity.BaseDataBean;
import com.yssj.entity.Choujiang20Data;
import com.yssj.entity.SignCountData;
import com.yssj.entity.SignDaKaInfo;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelZ;
import com.yssj.model.ModQingfeng;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.FriendCommissionActivity;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.HomePageThreeActivity;
import com.yssj.ui.activity.SignDrawalLimitActivity;
import com.yssj.ui.activity.WithdrawalLimitActivity;
import com.yssj.ui.activity.infos.GoldCoinDetailActivity;
import com.yssj.ui.activity.infos.IntergralDetailActivity;
import com.yssj.ui.activity.infos.MyCouponsActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.main.SignGroupShopActivity;
import com.yssj.ui.activity.setting.SettingCommonFragmentActivity;
import com.yssj.ui.activity.vip.MyVipListActivity;
import com.yssj.ui.dialog.BizuoEwaiWanchengTishiDialog;
import com.yssj.ui.dialog.NewSignCommonDiaolg;
import com.yssj.ui.dialog.ShareGetTXdialog;
import com.yssj.ui.dialog.ShareMeiyiChuandaCompleteDiaolg;
import com.yssj.ui.dialog.SignFinishDialogNew;
import com.yssj.ui.dialog.TiXianWanchengTishiDialog;
import com.yssj.ui.fragment.BackHandledFragment;
import com.yssj.utils.CenterToast;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.DateUtil;
import com.yssj.utils.DialogUtils;
import com.yssj.utils.GlideUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.NetworkUtils;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.PinTuanDuoBaoUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.SignCompleteDialogUtil;
import com.yssj.utils.SignUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.YCache;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.yssj.custom.view.SignCalendar.CalendarView.clock_in_start_date;
import static com.yssj.custom.view.SignCalendar.CalendarView.reQuestMon;
import static com.yssj.custom.view.SignCalendar.CalendarView.reQuestYear;
import static com.yssj.custom.view.SignCalendar.CalendarView.signDateList;
import static com.yssj.ui.base.BasicActivity.shareWaitDialog;
import static com.yssj.ui.fragment.circles.SignListAdapter.neeedFenzhongCompleteDiaog;

//import com.yssj.ui.dialog.DialogGofenxiang;


public class SignFragment extends BackHandledFragment implements NewSignCommonDiaolg.SignRefreshDataListener, SignUtil.ShareCompleteCallBack

        , WXEntryActivity.WXminiAPPshareListener {
    private static Context mContext;
    //    @Bind(R.id.rl_bizuo)
    RelativeLayout rlBizuo;
    //    @Bind(R.id.rl_ewai)
    RelativeLayout rlEwai;
    //    @Bind(R.id.lv_duobao)
    MyListView lvDuobao;  //lv_duobao-------------??????????????????????????????
    //    @Bind(R.id.tv_duobao_tou)
    TextView tvDuobaoTou;
    //    @Bind(R.id.rl_duuobao)
    RelativeLayout rlDuuobao;
    public static ScrollView scollView;
    private TextView tv_eyu, tv_jifen, tv_youhuiquan, tv_browse_count, tv_browse_jiangli, tv_tixian;
    // private static TextView tv_buqianka_count;
    private TextView iv_sign_explain, tv_to_more;
    private List<HashMap<String, String>> mListData2;
    private MyAdapter adapter2;
    // private boolean isFirstSetHobbyComplete;
    // private FrameLayout buqiankaAllCount;
    // private MyAdapter adapter;

    public static boolean signIsShow;


//    public  static IWXAPI wXapi;


    public static double h5Moneny = 0.0;//?????????H5?????????
    public static int zidongGundongYBZ = 0;


    private int bizuoCount = 0; //?????????????????????
    private int otherCount = 0; //?????????????????????


    private int bizuoCountComplete = 0; //?????????????????????---?????????
    private int otherCountComplete = 0; //?????????????????????---?????????

    private Activity mActivity;


    /**
     * ??????????????? refreshData()
     */


    public static String whereMon = "";// ??????????????????????????????


    public static String today_ref = "0";//????????????????????????????????????0 ??????1???


    // private MyadapterTastk myadapterTastkTomorrow;
    private SignListAdapter myadapterTastkDay;
    private SignListAdapter myadapterTastkOther;
    private SignListAdapter myadapterTastkSurprise;
    private SignListAdapter myadapterTastkSurpriseTX;
    private SignListAdapter myadapterTastkSurpriseJiZan;
    private SignListAdapter myadapterTastkDuoBao;

    private int roll = 0;//????????????????????? 0???????????????1?????????


    private int fighStatus = -1;//?????????????????????????????????roll == 0?????????????????????1??????????????????


    private TextView tv_to_ot, tv_to_ms;
    private TextView tv_spu, tv_spu_tx;


    public LinearLayout sl_sign_fragment, ll_sl, rl_maomi_center_no_money;

    private TextView tv_tixianzhong;
    private TextView tv_ketixian, tv_yitixian, textView8, tv_maomi_center_no_money_tou;
    private TextView tv_jinrizhuan;


    private MyListView lv_mustdu, lv_mustoder, lv_surprise, lv_jizan; // ????????????----????????????----???????????????????????????--????????????
    private ListView listView1;

    /**
     * lv_surprise ---??????????????????????????????
     */
    // public static String aaaa = ""; // ??????????????????????????????

    public static int bangDingPhoneType;

    private LinearLayout lv_kaiqifanbei, ll_youhuiquan, ll_yugao;

    public static RelativeLayout rl_surprise_tx;

    private RelativeLayout rl_yuefanbei;


    private boolean isCrazyMon = false; // ????????????????????????
    private boolean haslingyuangou = false;
    private boolean hasFriendTicheng = false;



    private int to_ot_count; // ????????????????????????????????????
    private int to_ms_count; // ????????????????????????????????????


    private int to_suprise_count; // ????????????????????????????????????
    private int to_suprise_tx_count; // ????????????????????????????????????


    public static SignFragment signFragment;


    public String signIn_status = "";// ???????????? -1????????????0???????????????1???????????????2?????????

    public static int type;// ?????????????????? ----????????????????????????????????????


    public static boolean hasTXtask;//???????????????????????????


    /**
     * 0??????-1????????????-2??????-3???X?????????????????????-4??????????????????-5?????????????????? 6??????X?????????-7??????????????????-8???????????????
     * 701??????????????????--?????????????????? 702??????????????????--???????????? 703??????????????????--????????? 801??????????????????--??????????????????
     * 802??????????????????--???????????? 803??????????????????--???????????????
     */
    // ??????????????????
    private List<HashMap<String, Object>> taskList = new ArrayList<HashMap<String, Object>>();// ????????????????????????????????????????????????????????????
    // ??????????????????????????????
    // ???????????????????????????
    private List<HashMap<String, Object>> taskiconList = new ArrayList<HashMap<String, Object>>();// ??????????????????
    // ??????????????????
    private List<HashMap<String, Object>> tomorrowTaskList = new ArrayList<HashMap<String, Object>>();// ????????????

    // ????????????????????????
    private List<HashMap<String, Object>> daytaskList = new ArrayList<HashMap<String, Object>>();// ???????????? taskClass???1
    private List<HashMap<String, Object>> othertaskList = new ArrayList<HashMap<String, Object>>();// ???????????? taskClass???2
    private List<HashMap<String, Object>> surpriseTaskList = new ArrayList<HashMap<String, Object>>();// ????????????-----??????????????????????????? taskClass???3
    private List<HashMap<String, Object>> tiXianSurpriseTaskList = new ArrayList<HashMap<String, Object>>();// ????????????--?????? taskClass???4
    private List<HashMap<String, Object>> jiZanTaskList = new ArrayList<HashMap<String, Object>>();// ????????????  taskClass???5  //?????????

    private List<HashMap<String, Object>> duoBaoTaskList = new ArrayList<HashMap<String, Object>>();// ????????????-------???????????????????????????   taskClass???6

    // ?????????????????????????????????
    public static List<HashMap<String, Object>> daytaskListYet = new ArrayList<HashMap<String, Object>>(); // ?????????????????????(??????)
    private List<HashMap<String, Object>> othertaskListYet = new ArrayList<HashMap<String, Object>>(); // ?????????????????????(??????)
    private List<HashMap<String, Object>> surpriseTaskListYet = new ArrayList<HashMap<String, Object>>(); // ?????????????????????(??????)
    private List<HashMap<String, Object>> tiXiansurpriseTaskListYet = new ArrayList<HashMap<String, Object>>(); // ???????????????????????????
    private List<HashMap<String, Object>> jiZanTaskListYet = new ArrayList<HashMap<String, Object>>(); // ???????????????????????????
    private List<HashMap<String, Object>> duoBaoTaskListYet = new ArrayList<HashMap<String, Object>>(); // ?????????????????????


    private LinearLayout ll_jifen;
    private boolean isXiala;//????????????---??????
    private View ll_eyu;
    private Timer mTimer2;
    boolean sharemeiyichuandaback;


    private LinearLayout img_back;
    private LinearLayout ll_tixian;
    private MyListView lv_surprise_tx;
    private ImageView headIv;
    private TextView nameTv;

    private boolean isTXListScroll;
    private boolean fromMianOrFaClick;
    private boolean isHiddenTXK;


    private boolean signHintShow = false;//???????????????????????????


    //?????????????????????
    private String point_status = "-1"; //????????????0-?????? 1-??????????????? 2-???????????????0???????????????????????????????????????

    public static String isGratis = "false";    //isGratis ?????????????????????????????????????????????????????????????????????????????????isGratis://???????????? true?????????false??????

    private LinearLayout ll_wallet_count;
    public static boolean mWxInstallFlag;

    private boolean eWaiTaskComplete = false;//??????????????????????????????
    private boolean biZuoTaskCoumlete = false;//??????????????????????????????
    private String current_date = "";// ?????????????????????


    private boolean isTastComplete = false; //?????????????????????????????????

    //    private int pagerShow = 0;//????????????????????????????????? - 1?????????1??????????????????2???????????????(??????)???3???????????????????????????????????????????????????4??????0?????????5???????????????
    private boolean hasMeiyuejingxi;
    private boolean hasQianuyuanhongbao;
    public static int whetherTask = 1; // ????????????????????? ???1 ?????????????????????????????????????????????
    private TextView mTxtDate;
    private CalendarView mCalendarView;
    private TextView calendar_next;
    private TextView calendar_last;
    public static SignCountData mSignCountData;
    private Choujiang20Data choujiang20Data;


    private void questCalender(final int click) {

        if (click == 0) {
            initLimitAwardsList();
        }


        if (click == 0) {
            boolean mIsVip = CommonUtils.isVip(SignFragment.mSignCountData.getIsVip(), SignFragment.mSignCountData.getMaxType());
            boolean mGuideVip2Day2TaskClick = SharedPreferencesUtil.getBooleanData(mContext, "mGuideVip2Day2TaskClick", false);

            if (!mGuideVip2Day2TaskClick
                    && !mIsVip
                    && (SignFragment.mSignCountData.getCurrent_date() + "").equals("newbie02")
                    && SignFragment.daytaskListYet.size() == 1) {
                mContext.startActivity(new Intent(mContext, MyVipListActivity.class)
                        .putExtra("guide_vipType", 4)
                        .putExtra("isFromSign2RoundFirst", 1)

                );
                SharedPreferencesUtil.saveBooleanData(mContext, "mGuideVip2Day2TaskClick", true);
            }
        }


        if (!YJApplication.instance.isLoginSucess()) {
            initCalendar(click);
            return;
        }

        String dateMonStr = reQuestMon + "";
        if (reQuestMon < 10) {
            dateMonStr = "0" + reQuestMon;
        }
        String dateStr = reQuestYear + "" + dateMonStr;

        HashMap<String, String> pairsMap = new HashMap<>();
        pairsMap.put("date", dateStr);

        //??????????????????
        YConn.httpPost(mContext, YUrl.QUERY_SIGN_DAKA, pairsMap
                , new HttpListener<SignDaKaInfo>() {
                    @Override
                    public void onSuccess(SignDaKaInfo signDaKaInfo) {
                        signDateList = signDaKaInfo.getData().getList();
                        clock_in_start_date = signDaKaInfo.getData().getClock_in_start_date();
                        CalendarView.requestEd = true;


                        initCalendar(click);


                    }

                    @Override
                    public void onError() {

                    }
                });


    }

    private void initCalendar(int click) {


        //......???????????????????????????????????????????????????????????????..

//        signDateList = new ArrayList<>();
//
//        if (reQuestMon == 9) {
//            signDateList = new ArrayList<>();
//            signDateList.add(5);
//            signDateList.add(6);
//            signDateList.add(11);
//
//
//        }
//        clock_in_start_date = 1567382880000L;

        //........?????????????????????????????????????????????????????????...........


        //??????????????????
        int rowCount = 5;
        // ?????????????????????????????????
        int week = DateUtils.getFirstDayWeek(reQuestYear, reQuestMon);
        int m_days = DateUtils.getMonthDays(reQuestYear, reQuestMon);  // ???????????????

        if (week == 5) {
            rowCount = m_days <= 30 ? 5 : 6;
        } else if (week == 6) {
            rowCount = m_days <= 29 ? 5 : 6;
        }

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mCalendarView.getLayoutParams();
        if (rowCount == 5) {
            params.height = DP2SPUtil.dp2px(mContext, 260);
        } else {
            params.height = DP2SPUtil.dp2px(mContext, 300);

        }

        mCalendarView.setLayoutParams(params);


        switch (click) {
            case 0: //????????????
                Calendar calendar = mCalendarView.getCalendar();
                calendar.set(reQuestYear, reQuestMon - 1, 1);
                mCalendarView.refreshMonth();
                break;
            case -1: //?????????
                mCalendarView.lastMonth();
                break;
            case 1: //?????????
                mCalendarView.nextMonth();
                break;
        }

        String enMonth = "";
        switch (mCalendarView.getMonth() + 1) {
            case 1:
                enMonth = "January";
                break;
            case 2:
                enMonth = "February";
                break;
            case 3:
                enMonth = "March";
                break;
            case 4:
                enMonth = "April";
                break;
            case 5:
                enMonth = "May";
                break;
            case 6:
                enMonth = "June";
                break;
            case 7:
                enMonth = "July";
                break;
            case 8:
                enMonth = "August";
                break;
            case 9:
                enMonth = "September";
                break;
            case 10:
                ;
                enMonth = "October";
                break;
            case 11:
                enMonth = "November";
                break;
            case 12:
                enMonth = "December";
                break;
        }
        mTxtDate.setText(enMonth + "    " + mCalendarView.getYear());
    }


    public SignFragment() {
        super();

    }

    public static SignFragment newInstance(Context context) {
        SignFragment fragment = new SignFragment();

        mContext = context;
        return fragment;
    }

    @SuppressLint("NewApi")
    @Override
    public View initView() {
        mActivity = getActivity();

        if (GuideActivity.needFengKong) {
            ToastUtil.showMyToast(mContext, "?????????????????????????????????????????????", 3000);
            mActivity.finish();
        }
        mSignCountData = new SignCountData();
        isCrazyMon = SharedPreferencesUtil.getBooleanData(mContext, Pref.ISMADMONDAY, false);//??????????????????
        hasFriendTicheng = SharedPreferencesUtil.getBooleanData(mContext, Pref.FRIENDTICHENGPAGE, false);//?????????????????????
        hasMeiyuejingxi = SharedPreferencesUtil.getBooleanData(mContext, Pref.HASMEIYUEJINGXI, false);//???????????????????????????
        haslingyuangou = SharedPreferencesUtil.getBooleanData(mContext, Pref.ISHASLINGYUANGOU, false);//?????????0??????
        hasQianuyuanhongbao = SharedPreferencesUtil.getBooleanData(mContext, Pref.HAISQIANYAUNHBONGBAO_SIGN, false);//?????????????????????

//        DialogUtils.newUserRefreshSignTaskDialog(mActivity);

//        isCrazyMon = false;
//        haslingyuangou = true;

//
//        if (isCrazyMon) {//?????????
//            pagerShow = 1;
//        } else {
//            if (hasFriendTicheng) {//????????????
//                pagerShow = 2;
//            } else {
//                if (hasMeiyuejingxi) { //????????????
//                    pagerShow = 3;
//                } else {
//                    if (haslingyuangou) {//0??????
//                        pagerShow = 4;
//                    } else {
//                        if (hasQianuyuanhongbao) { //????????????
//                            pagerShow = 5;
//                        } else {
//                            pagerShow = -1;
//
//                        }
//
//                    }
//                }
//            }
//        }
//        isCrazyMon = true;
//        pagerShow = 1;


        view = View.inflate(mContext, R.layout.sign_fragment, null);

        show_fans_ll = view.findViewById(R.id.show_fans_ll);
        rl_jiangli_list = view.findViewById(R.id.rl_jiangli_list);
        headIv = (ImageView) view.findViewById(R.id.show_fans_head_iv);
        textView8 = (TextView) view.findViewById(R.id.textView8);
        tv_maomi_center_no_money_tou = (TextView) view.findViewById(R.id.tv_maomi_center_no_money_tou);
        nameTv = (TextView) view.findViewById(R.id.show_fans_name_tv);
        ll_tixian = (LinearLayout) view.findViewById(R.id.ll_tixian);
        ll_tixian.setOnClickListener(this);

        ll_wallet_count = (LinearLayout) view.findViewById(R.id.ll_wallet_count);

        tv_tixianzhong = (TextView) view.findViewById(R.id.tv_tixianzhong);


        mTxtDate = (TextView) view.findViewById(R.id.txt_date);
        mCalendarView = (CalendarView) view.findViewById(R.id.calendarView);

        calendar_next = (TextView) view.findViewById(R.id.calendar_next);
        calendar_last = (TextView) view.findViewById(R.id.calendar_last);

        calendar_next.setOnClickListener(SignFragment.this);
        calendar_last.setOnClickListener(SignFragment.this);


        isTastComplete = CommonActivity.instance.getIntent().getBooleanExtra("isTastComplete", false);
        isTXListScroll = CommonActivity.instance.getIntent().getBooleanExtra("isTXListScroll", false);
        fromMianOrFaClick = CommonActivity.instance.getIntent().getBooleanExtra("fromMianOrFaClick", false);
        isHiddenTXK = CommonActivity.instance.getIntent().getBooleanExtra("isHiddenTXK", false);
        boolean ershichoujiang_complete = CommonActivity.instance.getIntent().getBooleanExtra("20choujiang_complete", false);
        if (ershichoujiang_complete) {
//            ToastUtil.showMyToast(mContext, "?????????????????????????????????10??????", 3000);
        }

//        ButterKnife.bind(this, view);


        signFragment = this;

        WXEntryActivity.setWXminiShareListener(this);
        WXEntryActivity.setWXminiShareListener(this);


        rlBizuo = (RelativeLayout) view.findViewById(R.id.rl_bizuo);


        rlEwai = (RelativeLayout) view.findViewById(R.id.rl_ewai);

        lvDuobao = (MyListView) view.findViewById(R.id.lv_duobao);


        tvDuobaoTou = (TextView) view.findViewById(R.id.tv_duobao_tou);


        rlDuuobao = (RelativeLayout) view.findViewById(R.id.rl_duuobao);




        view.setBackgroundColor(Color.WHITE);
        sl_sign_fragment = (LinearLayout) view.findViewById(R.id.sl_sign_fragment);
        scollView = view.findViewById(R.id.sign_scoll);

        ll_sl = (LinearLayout) view.findViewById(R.id.ll_sl);
        rl_maomi_center_no_money = (LinearLayout) view.findViewById(R.id.rl_maomi_center_no_money);
        img_back = (LinearLayout) view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

//        scollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                isXiala = true;
//                refreshData();
//            }
//        });

        lv_mustdu = (MyListView) view.findViewById(R.id.lv_mustdu);
        lv_mustoder = (MyListView) view.findViewById(R.id.lv_mustoder);
        listView1 = view.findViewById(R.id.list_view1);
        lv_surprise = (MyListView) view.findViewById(R.id.lv_surprise);
        // ??????????????????
        lv_surprise_tx = (MyListView) view.findViewById(R.id.lv_surprise_tx);

        lv_jizan = (MyListView) view.findViewById(R.id.lv_jizan);

        //
        // iv_jifen = (ImageView) view.findViewById(R.id.iv_jifen);
        // iv_youhuiquan = (ImageView) view.findViewById(R.id.iv_youhuiquan);

        tv_ketixian = (TextView) view.findViewById(R.id.tv_ketixian);
        tv_yitixian = (TextView) view.findViewById(R.id.tv_yitixian);


        tv_jifen = (TextView) view.findViewById(R.id.tv_jifenall);
        ll_jifen = (LinearLayout) view.findViewById(R.id.ll_jifen);
        ll_jifen.setOnClickListener(this);

        tv_eyu = (TextView) view.findViewById(R.id.tv_eyu);
        ll_eyu = (LinearLayout) view.findViewById(R.id.ll_eyu);

        tv_to_ot = (TextView) view.findViewById(R.id.tv_to_ot);
        tv_to_ms = (TextView) view.findViewById(R.id.tv_to_ms);


        tv_spu = (TextView) view.findViewById(R.id.tv_spu);
        tv_spu_tx = (TextView) view.findViewById(R.id.tv_spu_tx);

//        tv_addtixianedu = (TextView) view.findViewById(R.id.tv_addtixianedu);
//        tv_addtixianedu.setOnClickListener(this);
        tv_youhuiquan = (TextView) view.findViewById(R.id.tv_youhuiquan);
        ll_youhuiquan = (LinearLayout) view.findViewById(R.id.ll_youhuiquan);
        ll_youhuiquan.setOnClickListener(this);

        tv_browse_count = (TextView) view.findViewById(R.id.tv_browse_count);
        tv_browse_jiangli = (TextView) view.findViewById(R.id.tv_browse_jiangli);
        // tv_tixian = (TextView) view.findViewById(R.id.tv_tixian);
        // tv_power = (TextView) view.findViewById(R.id.tv_power); // ?????????

        // tv_tixian.setOnClickListener(this);

        ll_yugao = (LinearLayout) view.findViewById(R.id.ll_yugao);

        // tv_fans_count = (TextView) view.findViewById(R.id.tv_fans_count);

        // kaiqi = (TextView) view.findViewById(R.id.kaiqi);

        lv_kaiqifanbei = (LinearLayout) view.findViewById(R.id.lv_kaiqifanbei);

        lv_kaiqifanbei.setOnClickListener(this);

        rl_yuefanbei = (RelativeLayout) view.findViewById(R.id.rl_yuefanbei);
        rl_surprise_tx = (RelativeLayout) view.findViewById(R.id.rl_surprise_tx);

        tv_to_more = (TextView) view.findViewById(R.id.tv_to_more);
        tv_jinrizhuan = (TextView) view.findViewById(R.id.tv_jinrizhuan);

        iv_sign_explain = (TextView) view.findViewById(R.id.iv_sign_explain);
        iv_sign_explain.setOnClickListener(this);


        try {
            // // ?????????????????????

            if (WXcheckUtil.isWeChatAppInstalled(mContext)) {
                mWxInstallFlag = true;
            } else {
                mWxInstallFlag = false;
            }
        } catch (Exception e) {
        }


        String textAward = "????????????50???";
        SpannableString ssTextAward = new SpannableString(textAward);
        ssTextAward.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 4, 6,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_to_more.setText(ssTextAward);



        SignListAdapter.setRefreshListener(new SignListAdapter.RefreshListener() {
            @Override
            public void signRefresh() {
                //?????????????????????
                refreshData();
            }
        });


        return view;
    }

    @Override
    public void initData() {
        if (YJApplication.instance.isLoginSucess()) {

            if (SharedPreferencesUtil.getBooleanData(mContext, "isSignSetLikeComplete", false)) {
                SignFinishDialogNew dialog = new SignFinishDialogNew(mContext, R.style.DialogQuheijiao2,
                        "setLikeCoplete", this);
                dialog.show();
            }

        }

        lv_mustdu.setFocusable(false);
        lv_mustoder.setFocusable(false);

        lv_surprise.setFocusable(false);
        lv_jizan.setFocusable(false);
        lv_surprise_tx.setFocusable(false);

        //???????????????
//        if (YJApplication.instance.isLoginSucess()) {
//            CreateDiaogSignShopEnd();
//
//            //??????????????????
//            DialogUtils.IndianaResultDialog(mContext);
//        }

        allId = "";

    }




    @Override
    public void onStart() {
        super.onStart();
        LogYiFu.e("????????????????????????", "onStart");

    }

    /**
     * @date 2017???5???27??? ??????3:37:39 @Description: ????????????????????? @param ???????????? @return void
     * ???????????? @author lifeng @throws
     */
    private void intitAllTask() {
        //
        // taskList.clear();
        // daytaskList.clear();
        // othertaskList.clear();
        // tomorrowTaskList.clear();
        // surpriseTaskList.clear();

        to_ms_count = 0;
        to_ot_count = 0;

        to_suprise_count = 0;
        to_suprise_tx_count = 0;

        if (YJApplication.instance.isLoginSucess()) {
            querySignData();
//            querySignData();// ??????????????????????????????????????????
//            querySignListYet();// ???????????????????????????;????????????
            // queryLoginSignListLogined();// ??????????????????---?????????

        } else {

            querySignListUnLogin();// ??????????????????(?????????)
            tv_eyu.setText("0");
            tv_jifen.setText("0");
            tv_youhuiquan.setText("0");
            initLimitAwardsList();
            if (!isCrazyMon) {
                tv_tixianzhong.setText("?????????0.00");
            }
            tv_ketixian.setText(Html.fromHtml("????????? <b>0.00</b>"));


        }

        // adapter = new MyAdapter(getActivity(), taskList, motaskList,
        // taskListAlready, bool);
        // gv_qiandao.setAdapter(adapter);
        // adapter.notifyDataSetChanged();

        // gv_qiandao.setBackgroundResource(R.drawable.qiandaobg3);
    }

    // ?????????????????????????????????????????????????????????
    private void queryLoginSignListLogined() {
        new SAsyncTask<Void, Void, HashMap<String, List<HashMap<String, Object>>>>((FragmentActivity) mContext, 0) {

            @Override
            protected HashMap<String, List<HashMap<String, Object>>> doInBackground(FragmentActivity context,
                                                                                    Void... params) throws Exception {
                return ComModel2.getLoginSignList(mContext);
            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         HashMap<String, List<HashMap<String, Object>>> result, Exception e) {
                super.onPostExecute(context, result, e);
//                scollView.onRefreshComplete();

                if (isXiala) {
                    isXiala = false;
                    return;
                }


                if (e == null && result != null) {

                    // ???????????????????????????????????????
                    whereMon = result.get("monday_info").get(0).get("value") + "";

                    long endTime = System.currentTimeMillis();
                    // Log.e("????????????????????????", (endTime - statrtTime) +
                    // "---signIn2_0/siLogTaskList");

                    if (result.size() != 0) {

                        /**
                         * daytaskListYet.addAll(result.get("daytaskListYet"));
                         * // ?????????????????????????????????????????????
                         * othertaskListYet.addAll(result.get("othertaskListYet"
                         * ));// ?????????????????????????????????????????????
                         */

                        // ???????????????4????????? ??????-??????-????????????????????????-????????????????????????????????????---???????????????

                        taskList.clear();
                        daytaskList.clear();
                        othertaskList.clear();
                        tomorrowTaskList.clear();
                        surpriseTaskList.clear();
                        tiXianSurpriseTaskList.clear();
                        jiZanTaskList.clear();
                        taskiconList.clear();
                        duoBaoTaskList.clear();

                        taskList.addAll(result.get("taskList")); // ??????????????????????????????
                        daytaskList.addAll(result.get("daytaskList")); // ??????????????????????????????
                        othertaskList.addAll(result.get("othertaskList")); // ??????????????????????????????
                        tomorrowTaskList.addAll(result.get("tomorrowTaskList")); // ??????????????????????????????
                        surpriseTaskList.addAll(result.get("surpriseTaskList")); // ??????????????????????????????????????????
                        tiXianSurpriseTaskList.addAll(result.get("txsurprisetasklist")); // ??????????????????????????????????????????---??????
                        jiZanTaskList.addAll(result.get("jizantasklist")); // ??????????????????????????????????????????
                        duoBaoTaskList.addAll(result.get("duoBaoTaskList")); // ??????????????????????????????????????????

                        taskiconList.addAll(result.get("taskiconList")); // ????????????????????????????????????


                        bizuoCount = daytaskList.size();
                        otherCount = othertaskList.size();

                        LogYiFu.e("tomorrowTaskList", taskiconList + "");

                        // ?????????????????????????????????????????????????????????????????????daytaskList???????????????????????????

                        if (daytaskListYet.size() > 0) {

                            List<HashMap<String, Object>> daytaskListTemp = new ArrayList<HashMap<String, Object>>();
                            for (int i = 0; i < daytaskList.size(); i++) {
                                boolean isHave = false;
                                String allID = daytaskList.get(i).get("index").toString();
                                for (int j = 0; j < daytaskListYet.size(); j++) {
                                    String completeID = daytaskListYet.get(j).get("index_id").toString();
                                    if ((allID.equals(completeID))
                                            && ((daytaskListYet.get(j).get("status") + "").equals("0"))) {
                                        isHave = true;
                                        daytaskListYet.get(j).put("value", daytaskList.get(i).get("value"));// ???value??????????????????????????????????????????
                                        daytaskListYet.get(j).put("num", daytaskList.get(i).get("num"));// ???num??????????????????????????????????????????
                                        daytaskListYet.get(j).put("icon", daytaskList.get(i).get("icon"));// ???icon??????????????????????????????????????????
                                        daytaskListYet.get(j).put("app_name", daytaskList.get(i).get("app_name"));// ???app_name??????????????????????????????????????????
                                        break;
                                    }
                                }
                                if (!isHave) {
                                    daytaskListTemp.add(daytaskList.get(i));
                                }
                            }


                            for (int i = 0; i < daytaskListYet.size(); i++) {
                                String completeID = daytaskListYet.get(i).get("index_id").toString();
                                for (int j = 0; j < daytaskListTemp.size(); j++) {
                                    String allID = daytaskListTemp.get(j).get("index").toString();
                                    if (allID.equals(completeID)) {
                                        daytaskListTemp.get(j).put("status", daytaskListYet.get(i).get("status"));
                                        daytaskListYet.remove(i);
                                        i--;
                                    }
                                }

                            }


                            daytaskList.clear();
                            daytaskList = daytaskListTemp;
                            // ???????????????????????????????????????????????????????????????????????????????????????????????????signStatus ???1
                            daytaskList.addAll(daytaskListYet);

                        }

                        // ?????????????????????????????????????????????????????????????????????daytaskList???????????????????????????
                        if (othertaskListYet.size() > 0) {

                            List<HashMap<String, Object>> daytaskListTemp = new ArrayList<HashMap<String, Object>>();
                            for (int i = 0; i < othertaskList.size(); i++) {
                                boolean isHave = false;
                                String allID = othertaskList.get(i).get("index").toString();
                                for (int j = 0; j < othertaskListYet.size(); j++) {
                                    String completeID = othertaskListYet.get(j).get("index_id").toString();
                                    if ((allID.equals(completeID))
                                            && ((othertaskListYet.get(j).get("status") + "").equals("0"))) {
                                        isHave = true;
                                        othertaskListYet.get(j).put("value", othertaskList.get(i).get("value"));// ???value??????????????????????????????????????????
                                        othertaskListYet.get(j).put("num", othertaskList.get(i).get("num"));// ???num??????????????????????????????????????????
                                        othertaskListYet.get(j).put("icon", othertaskList.get(i).get("icon"));// ???icon??????????????????????????????????????????
                                        othertaskListYet.get(j).put("app_name", othertaskList.get(i).get("app_name"));// ???app_name??????????????????????????????????????????
                                        break;
                                    }
                                }
                                if (!isHave) {
                                    daytaskListTemp.add(othertaskList.get(i));
                                }
                            }

                            for (int i = 0; i < othertaskListYet.size(); i++) {
                                String completeID = othertaskListYet.get(i).get("index_id").toString();
                                for (int j = 0; j < daytaskListTemp.size(); j++) {
                                    String allID = daytaskListTemp.get(j).get("index").toString();
                                    if (allID.equals(completeID)) {
                                        daytaskListTemp.get(j).put("status", othertaskListYet.get(i).get("status"));
                                        othertaskListYet.remove(i);
                                        i--;
                                    }
                                }

                            }


                            othertaskList.clear();

                            //daytaskListTemp???????????????????????????????????????
                            othertaskList = daytaskListTemp;
                            // ???????????????????????????????????????????????????????????????????????????????????????????????????signStatus ???1
                            othertaskList.addAll(othertaskListYet);

                        }
                        LogYiFu.e("othertaskListYet", othertaskList + "");
                        // ?????????????????????????????????????????????????????????????????????daytaskList???????????????????????????
                        if (surpriseTaskListYet.size() > 0) {

                            List<HashMap<String, Object>> daytaskListTemp = new ArrayList<HashMap<String, Object>>();
                            for (int i = 0; i < surpriseTaskList.size(); i++) {
                                boolean isHave = false;
                                String allID = surpriseTaskList.get(i).get("index").toString();
                                for (int j = 0; j < surpriseTaskListYet.size(); j++) {
                                    String completeID = surpriseTaskListYet.get(j).get("index_id").toString();
                                    if ((allID.equals(completeID))
                                            && ((surpriseTaskListYet.get(j).get("status") + "").equals("0"))) {
                                        isHave = true;
                                        surpriseTaskListYet.get(j).put("value", surpriseTaskList.get(i).get("value"));// ???value??????????????????????????????????????????
                                        surpriseTaskListYet.get(j).put("num", surpriseTaskList.get(i).get("num"));// ???num??????????????????????????????????????????
                                        surpriseTaskListYet.get(j).put("icon", surpriseTaskList.get(i).get("icon"));// ???icon??????????????????????????????????????????
                                        surpriseTaskListYet.get(j).put("app_name",
                                                surpriseTaskList.get(i).get("app_name"));// ???app_name??????????????????????????????????????????

                                        break;
                                    }
                                }
                                if (!isHave) {
                                    daytaskListTemp.add(surpriseTaskList.get(i));
                                }
                            }

                            for (int i = 0; i < surpriseTaskListYet.size(); i++) {
                                String completeID = surpriseTaskListYet.get(i).get("index_id").toString();
                                for (int j = 0; j < daytaskListTemp.size(); j++) {
                                    String allID = daytaskListTemp.get(j).get("index").toString();
                                    if (allID.equals(completeID)) {
                                        daytaskListTemp.get(j).put("status", surpriseTaskListYet.get(i).get("status"));
                                        surpriseTaskListYet.remove(i);
                                        i--;
                                    }
                                }

                            }

                            surpriseTaskList.clear();
                            surpriseTaskList = daytaskListTemp;
                            // ???????????????????????????????????????????????????????????????????????????????????????????????????signStatus ???1
                            surpriseTaskList.addAll(surpriseTaskListYet);

                        }

                        // ?????????????????????????????????????????????????????????????????????daytaskList???????????????????????????-------------??????
                        if (tiXiansurpriseTaskListYet.size() > 0) {

                            List<HashMap<String, Object>> daytaskListTemp = new ArrayList<HashMap<String, Object>>();
                            for (int i = 0; i < tiXianSurpriseTaskList.size(); i++) {
                                boolean isHave = false;
                                String allID = tiXianSurpriseTaskList.get(i).get("index").toString();
                                for (int j = 0; j < tiXiansurpriseTaskListYet.size(); j++) {
                                    String completeID = tiXiansurpriseTaskListYet.get(j).get("index_id").toString();
                                    if ((allID.equals(completeID))
                                            && ((tiXiansurpriseTaskListYet.get(j).get("status") + "").equals("0"))) {
                                        isHave = true;
                                        tiXiansurpriseTaskListYet.get(j).put("value",
                                                tiXianSurpriseTaskList.get(i).get("value"));// ???value??????????????????????????????????????????
                                        tiXiansurpriseTaskListYet.get(j).put("num",
                                                tiXianSurpriseTaskList.get(i).get("num"));// ???num??????????????????????????????????????????

                                        tiXiansurpriseTaskListYet.get(j).put("icon",
                                                tiXianSurpriseTaskList.get(i).get("icon"));// ???icon??????????????????????????????????????????
                                        tiXiansurpriseTaskListYet.get(j).put("app_name",
                                                tiXianSurpriseTaskList.get(i).get("app_name"));// ???app_name??????????????????????????????????????????

                                        break;
                                    }
                                }
                                if (!isHave) {
                                    daytaskListTemp.add(tiXianSurpriseTaskList.get(i));
                                }
                            }

                            for (int i = 0; i < tiXiansurpriseTaskListYet.size(); i++) {
                                String completeID = tiXiansurpriseTaskListYet.get(i).get("index_id").toString();
                                for (int j = 0; j < daytaskListTemp.size(); j++) {
                                    String allID = daytaskListTemp.get(j).get("index").toString();
                                    if (allID.equals(completeID)) {
                                        daytaskListTemp.get(j).put("status", tiXiansurpriseTaskListYet.get(i).get("status"));
                                        tiXiansurpriseTaskListYet.remove(i);
                                        i--;
                                    }
                                }

                            }

                            tiXianSurpriseTaskList.clear();
                            tiXianSurpriseTaskList = daytaskListTemp;
                            // ???????????????????????????????????????????????????????????????????????????????????????????????????signStatus ???1
                            tiXianSurpriseTaskList.addAll(tiXiansurpriseTaskListYet);

                        }

                        // ?????????????????????????????????????????????????????????????????????daytaskList???????????????????????????-------------??????
                        if (jiZanTaskListYet.size() > 0) {

                            List<HashMap<String, Object>> daytaskListTemp = new ArrayList<HashMap<String, Object>>();
                            for (int i = 0; i < jiZanTaskList.size(); i++) {
                                boolean isHave = false;
                                String allID = jiZanTaskList.get(i).get("index").toString();
                                for (int j = 0; j < jiZanTaskListYet.size(); j++) {
                                    String completeID = jiZanTaskListYet.get(j).get("index_id").toString();
                                    if ((allID.equals(completeID))
                                            && ((jiZanTaskListYet.get(j).get("status") + "").equals("0"))) {
                                        isHave = true;
                                        jiZanTaskListYet.get(j).put("value", jiZanTaskList.get(i).get("value"));// ???value??????????????????????????????????????????
                                        jiZanTaskListYet.get(j).put("num", jiZanTaskList.get(i).get("num"));// ???num??????????????????????????????????????????
                                        jiZanTaskListYet.get(j).put("icon", jiZanTaskList.get(i).get("icon"));// ???icon??????????????????????????????????????????
                                        jiZanTaskListYet.get(j).put("app_name", jiZanTaskList.get(i).get("app_name"));// ???app_name??????????????????????????????????????????

                                        break;
                                    }
                                }
                                if (!isHave) {
                                    daytaskListTemp.add(jiZanTaskList.get(i));
                                }
                            }

                            for (int i = 0; i < jiZanTaskListYet.size(); i++) {
                                String completeID = jiZanTaskListYet.get(i).get("index_id").toString();
                                for (int j = 0; j < daytaskListTemp.size(); j++) {
                                    String allID = daytaskListTemp.get(j).get("index").toString();
                                    if (allID.equals(completeID)) {
                                        daytaskListTemp.get(j).put("status", jiZanTaskListYet.get(i).get("status"));
                                        jiZanTaskListYet.remove(i);
                                        i--;
                                    }
                                }

                            }

                            jiZanTaskList.clear();
                            jiZanTaskList = daytaskListTemp;
                            // ???????????????????????????????????????????????????????????????????????????????????????????????????signStatus ???1
                            jiZanTaskList.addAll(jiZanTaskListYet);

                        }


                        // ?????????????????????????????????????????????????????????????????????daytaskList???????????????????????????-------------??????------------???????????????????????????
                        if (duoBaoTaskListYet.size() > 0) {

                            List<HashMap<String, Object>> daytaskListTemp = new ArrayList<HashMap<String, Object>>();
                            for (int i = 0; i < duoBaoTaskList.size(); i++) {
                                boolean isHave = false;
                                String allID = duoBaoTaskList.get(i).get("index").toString();
                                for (int j = 0; j < duoBaoTaskListYet.size(); j++) {
                                    String completeID = duoBaoTaskListYet.get(j).get("index_id").toString();
                                    if ((allID.equals(completeID))
                                            && ((duoBaoTaskListYet.get(j).get("status") + "").equals("0"))) {
                                        isHave = true;
                                        duoBaoTaskListYet.get(j).put("value", duoBaoTaskList.get(i).get("value"));// ???value??????????????????????????????????????????

                                        String num = duoBaoTaskList.get(i).get("num") + "";

                                        duoBaoTaskListYet.get(j).put("num", num);// ???num??????????????????????????????????????????
                                        duoBaoTaskListYet.get(j).put("icon", duoBaoTaskList.get(i).get("icon"));// ???icon??????????????????????????????????????????
                                        duoBaoTaskListYet.get(j).put("app_name", duoBaoTaskList.get(i).get("app_name"));// ???app_name??????????????????????????????????????????

                                        break;
                                    }
                                }
                                if (!isHave) {
                                    daytaskListTemp.add(duoBaoTaskList.get(i));
                                }
                            }

                            for (int i = 0; i < duoBaoTaskListYet.size(); i++) {
                                String completeID = duoBaoTaskListYet.get(i).get("index_id").toString();
                                for (int j = 0; j < daytaskListTemp.size(); j++) {
                                    String allID = daytaskListTemp.get(j).get("index").toString();
                                    if (allID.equals(completeID)) {
                                        daytaskListTemp.get(j).put("status", duoBaoTaskListYet.get(i).get("status"));
                                        duoBaoTaskListYet.remove(i);
                                        i--;
                                    }
                                }

                            }

                            duoBaoTaskList.clear();
                            duoBaoTaskList = daytaskListTemp;
                            // ???????????????????????????????????????????????????????????????????????????????????????????????????signStatus ???1
                            duoBaoTaskList.addAll(duoBaoTaskListYet);

                        }


                        // ???????????????????????????????????????
                        if (isCrazyMon) {
                            HashMap<String, Object> crazyMon = new HashMap<String, Object>();
                            crazyMon.put("index", "-999");
                            crazyMon.put("t_id", "-999");
                            crazyMon.put("num", "-999");
                            crazyMon.put("task_type", "999");
                            crazyMon.put("value", "-999");
                            crazyMon.put("task_class", "3");
//                            surpriseTaskList.add(crazyMon,0);
                            //???????????????????????????????????????
                            duoBaoTaskList.add(0, crazyMon);
                        }


                        // ????????????????????????????????????????????????
                        if (othertaskList.size() > 0) {
                            for (HashMap<String, Object> hashMap : othertaskList) {
                                if (hashMap.get("task_type").equals("6")) {
                                    SignListAdapter.hasGoumai = true;
                                }

                            }
                        }


                        //??????????????????????????????????????????????????????????????????????????????
                        if (surpriseTaskList.size() > 0) {
                            for (int i = 0; i < surpriseTaskList.size(); i++) {
                                if (surpriseTaskList.get(i).get("task_type").equals("24")) {
                                    HashMap<String, Object> hashMap = surpriseTaskList.get(i);
                                    duoBaoTaskList.add(0, hashMap);
                                    surpriseTaskList.remove(i);
                                    i--;
                                }
                            }
                        }


                        // ???????????????????????????---??????????????????????????????
                        removeMonTaskComplet(daytaskList);
                        removeMonTaskComplet(othertaskList);
                        removeMonTaskComplet(tiXianSurpriseTaskList);
                        removeMonTaskComplet(jiZanTaskList);
                        removeMonTaskComplet(duoBaoTaskList);


                        //??????????????????????????????-1???????????????????????????
                        if (point_status.equals("-1") || point_status.equals("0")) {
                            if (jiZanTaskList.size() > 0) {

                                for (int i = 0; i < jiZanTaskList.size(); i++) {
                                    if (jiZanTaskList.get(i).get("task_type").equals("15")) {
                                        jiZanTaskList.remove(i);
                                        i--;
                                    }
                                }

                            }

                        }
                        if (point_status.equals("-1") || point_status.equals("0")) {
                            if (daytaskList.size() > 0) {

                                for (int i = 0; i < daytaskList.size(); i++) {
                                    if (daytaskList.get(i).get("task_type").equals("15")) {
                                        daytaskList.remove(i);
                                        i--;
                                    }
                                }

                            }

                        }
                        if (point_status.equals("-1") || point_status.equals("0")) {
                            if (tiXianSurpriseTaskList.size() > 0) {

                                for (int i = 0; i < tiXianSurpriseTaskList.size(); i++) {
                                    if (tiXianSurpriseTaskList.get(i).get("task_type").equals("15")) {
                                        tiXianSurpriseTaskList.remove(i);
                                        i--;
                                    }
                                }

                            }

                        }

                        if (point_status.equals("-1") || point_status.equals("0")) {
                            if (othertaskList.size() > 0) {

                                for (int i = 0; i < othertaskList.size(); i++) {
                                    if (othertaskList.get(i).get("task_type").equals("15")) {
                                        othertaskList.remove(i);
                                        i--;
                                    }
                                }

                            }

                        }


                        if (tiXianSurpriseTaskList.size() > 0) {
                            hasTXtask = true;
                        }


                        //????????????????????????---???????????????????????????????????????  ??? ?????????????????????????????????------??????
//                        if (roll == 0 && (fighStatus == 0 || fighStatus == 3)) {
//                            if (duoBaoTaskList.size() > 0) {
//                                for (int i = 0; i < duoBaoTaskList.size(); i++) {
//                                    if (duoBaoTaskList.get(i).get("task_type").equals("17")) {
//                                        duoBaoTaskList.remove(i);
//                                        i--;
//                                    }
//                                }
//
//                            }
//                        }

                        //??????????????????????????????????????????????????????????????????????????????????????????---??????-----??????

                        //???????????????????????????????????????????????????????????? 1????????????????????? 2???????????????????????? 3???????????????????????????????????????????????????????????????????????????

                        // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????

                        if (duoBaoTaskList.size() > 0) {
                            for (int i = 0; i < duoBaoTaskList.size(); i++) {
                                if (duoBaoTaskList.get(i).get("task_type").equals("17")) {

                                    boolean b1 = false;
                                    boolean b2 = false;
                                    boolean b3 = false;
                                    boolean b4 = false;

                                    b1 = fighStatus == 0;//????????????

                                    b2 = fighStatus == 3;//?????????????????????

                                    if (!"1".equals(duoBaoTaskList.get(i).get("signStatus"))) {//???????????????
                                        if (fighStatus == 1) {
                                            b3 = true;//???????????????????????????????????????????????????????????????????????????
                                        }
                                    }


                                    b4 = SignListAdapter.orderCount != 0 && !"1".equals(duoBaoTaskList.get(i).get("signStatus")); //??????????????????????????? ???  ??????????????????(?????????)

                                    if (b1 || b2 || b3 || b4) {
                                        duoBaoTaskList.remove(i);
                                        i--;
                                    }
                                }
                            }

                        }


                        //???????????? + orderStatus=1  +   orderCount =2 //??????????????????????????????????????????---???????????????????????????
                        if (SignListAdapter.orderStatus == 1 && (SignListAdapter.orderCount == 2 || SignListAdapter.orderCount == 1)) {
                            if (duoBaoTaskList.size() > 0) {
                                for (int i = 0; i < duoBaoTaskList.size(); i++) {
                                    if (duoBaoTaskList.get(i).get("task_type").equals("18")) {
                                        if (!"1".equals(duoBaoTaskList.get(i).get("signStatus"))) {//???????????????
                                            duoBaoTaskList.remove(i);
                                            i--;
                                        }
                                    }
                                }

                            }
                        }


                        //??????????????????????????????
                        if ("0".equals(today_ref)) {
                            //???????????????????????????
                            removeHongBao(daytaskList);
                            removeHongBao(othertaskList);
                            removeHongBao(tiXianSurpriseTaskList);
                            removeHongBao(jiZanTaskList);
                            removeHongBao(duoBaoTaskList);


                        }

                        //?????????????????????????????????  ??????"??????????????????"
                        if (SharedPreferencesUtil.getBooleanData(mContext, Pref.IS_AREADY_BUY + YCache.getCacheUser(context).getUser_id(), false)) {
                            //???????????????
                            removeChouJiang(daytaskList);
                            removeChouJiang(othertaskList);
                            removeChouJiang(tiXianSurpriseTaskList);
                            removeChouJiang(jiZanTaskList);
                            removeChouJiang(duoBaoTaskList);
                        }


                        /**
                         * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                         */

                        //1?????????????????????????????????  -----????????????????????????????????????,???????????????????????????????????????,??????????????????
                        boolean hasMon = isCrazyMon; // ??????????????????????????????
                        if (hasMon) {

                            //????????????????????????
//                            if (surpriseTaskList.size() > 0) {
//                                surpriseTaskList.clear();
//                            }

//                            if (duoBaoTaskList.size() > 0) {    //??????????????????????????????????????????????????????
//                                for (int i = 0; i < duoBaoTaskList.size(); i++) {
//                                    if (duoBaoTaskList.get(i).get("task_type").equals("6")) {
//                                        duoBaoTaskList.remove(i);
//                                        i--;
//                                    }
//                                }
//
//                            }


                            //????????????????????????
                            removeMonTask(daytaskList);
                            removeMonTask(othertaskList);
                            removeMonTask(tiXianSurpriseTaskList);
                            removeMonTask(jiZanTaskList);
                            removeMonTask(duoBaoTaskList);


                            //???????????????--??????
                            removeKaiTuan(daytaskList);
                            removeKaiTuan(othertaskList);
                            removeKaiTuan(tiXianSurpriseTaskList);
                            removeKaiTuan(jiZanTaskList);
                            removeKaiTuan(duoBaoTaskList);


                            //????????????????????????
                            removeHongBao(daytaskList);
                            removeHongBao(othertaskList);
                            removeHongBao(tiXianSurpriseTaskList);
                            removeHongBao(jiZanTaskList);
                            removeHongBao(duoBaoTaskList);


                            //?????????????????????
                            removeGoumaiYingTX(tiXianSurpriseTaskList);


                        }
                        //2???????????????????????????????????????
                        boolean kt1 = checkKaituanTask(daytaskList);
                        boolean kt2 = checkKaituanTask(othertaskList);
                        boolean kt3 = checkKaituanTask(tiXianSurpriseTaskList);
                        boolean kt4 = checkKaituanTask(jiZanTaskList);
                        boolean kt5 = checkKaituanTask(duoBaoTaskList);
                        if (kt1 || kt2 || kt3 || kt4 || kt5) {//???????????????
                            //????????????????????????
//                            if (surpriseTaskList.size() > 0) {
//                                surpriseTaskList.clear();
//                            }

//                            if (duoBaoTaskList.size() > 0) {    //??????????????????????????????????????????????????????
//                                for (int i = 0; i < duoBaoTaskList.size(); i++) {
//                                    if (duoBaoTaskList.get(i).get("task_type").equals("6")) {
//                                        duoBaoTaskList.remove(i);
//                                        i--;
//                                    }
//                                }
//
//                            }


                            removeMonTask(daytaskList);
                            removeMonTask(othertaskList);
                            removeMonTask(tiXianSurpriseTaskList);
                            removeMonTask(jiZanTaskList);
                            removeMonTask(duoBaoTaskList);


                            //?????????????????????
                            if (duoBaoTaskList.size() > 0) {
                                for (int i = 0; i < duoBaoTaskList.size(); i++) {
                                    if (duoBaoTaskList.get(i).get("task_type").equals("999")) {
                                        duoBaoTaskList.remove(i);
                                        i--;
                                    }
                                }

                            }


                        }

                        //3?????????????????????????????????????????????????????????
                        boolean mon1 = checkHasMonthTask(daytaskList);
                        boolean mon2 = checkHasMonthTask(othertaskList);
                        boolean mon3 = checkHasMonthTask(tiXianSurpriseTaskList);
                        boolean mon4 = checkHasMonthTask(jiZanTaskList);
                        boolean mon5 = checkHasMonthTask(duoBaoTaskList);
                        if (mon1 || mon2 || mon3 || mon4 || mon5) {//?????????????????????
                            //?????????????????????-----????????????
//                            removeGoumaiYingTX(tiXianSurpriseTaskList);


                            //???????????????--??????
                            removeKaiTuan(daytaskList);
                            removeKaiTuan(othertaskList);
                            removeKaiTuan(tiXianSurpriseTaskList);
                            removeKaiTuan(jiZanTaskList);
                            removeKaiTuan(duoBaoTaskList);


                            //?????????????????????
//                            if (duoBaoTaskList.size() > 0) {
//                                for (int i = 0; i < duoBaoTaskList.size(); i++) {
//                                    if (duoBaoTaskList.get(i).get("task_type").equals("999")) {
//                                        duoBaoTaskList.remove(i);
//                                        i--;
//                                    }
//                                }
//
//                            }

                        }

                        //???????????????APP???????????????----------????????????9257
                        removeH5task(daytaskList);
                        removeH5task(othertaskList);
                        removeH5task(tiXianSurpriseTaskList);
                        removeH5task(jiZanTaskList);
                        removeH5task(duoBaoTaskList);

                        //??????????????????????????????0??????
                        remove0yuanTask(daytaskList);
                        remove0yuanTask(othertaskList);
                        remove0yuanTask(tiXianSurpriseTaskList);
                        remove0yuanTask(jiZanTaskList);
                        remove0yuanTask(duoBaoTaskList);

                        //????????????????????????????????????????????????????????????????????????type = 43 (?????????????????????)
                        for (int i = 0; i < daytaskList.size(); i++) {
                            if (daytaskList.get(i).get("task_type").equals("43")) {
                                if (!mSignCountData.getYc_task().equals("1") || !YJApplication.instance.isLoginSucess()) {
                                    daytaskList.remove(i);
                                    i--;
                                }
                            }
                        }


                        //???????????????????????????
                        //????????????
                        if (!YJApplication.instance.isLoginSucess() || mSignCountData.getCurrent_date().indexOf("newbie") != -1) {
                            for (int i = 0; i < daytaskList.size(); i++) {
                                if (daytaskList.get(i).get("task_type").equals("46")) {
                                    daytaskList.remove(i);
                                    i--;
                                }
                            }
                        }

                        boolean mIsVip = CommonUtils.isVip(mSignCountData.getIsVip(), mSignCountData.getMaxType());
                        if (mIsVip) {//???????????????????????????????????????20???
                            mSignCountData.setIs_fast_raffle(1);
                        }


                        //???????????????????????? ????????????
                        for (int i = 0; i < daytaskList.size(); i++) {
                            if (daytaskList.get(i).get("task_type").equals("40")) {
                                daytaskList.remove(i);
                                i--;
                            }

                        }

//                        if (!YJApplication.instance.isLoginSucess()) {//1
//                            for (int i = 0; i < daytaskList.size(); i++) {
//                                if (daytaskList.get(i).get("task_type").equals("40")) {
//                                    daytaskList.remove(i);
//                                    i--;
//                                }
//
//                            }
//
//                        }
//                        //??????
//                        if (mIsVip) {//2
//                            for (int i = 0; i < daytaskList.size(); i++) {
//                                if (daytaskList.get(i).get("task_type").equals("40")) {
//                                    daytaskList.remove(i);
//                                    i--;
//                                }
//                            }
//                        }
//
//
//                        //??????????????????3
//                        if (mSignCountData.getCurrent_date().equals("newbie01")) {//4
//                            for (int i = 0; i < daytaskList.size(); i++) {
//                                if (daytaskList.get(i).get("task_type").equals("40")) {
//                                    daytaskList.remove(i);
//                                    i--;
//                                }
//                            }
//                        }


                        //62???????????????2????????????????????????????????????????????????????????????????????????????????????????????????
                        HashMap<String, Object> task43 = null;
                        for (int i = 0; i < daytaskList.size(); i++) {
                            if (daytaskList.get(i).get("task_type").equals("43")) {
                                task43 = daytaskList.get(i);
                                daytaskList.remove(i);
                                i--;
                            }
                        }
                        if (null != task43) {
                            daytaskList.add(task43);
                        }


                        //??????????????????????????????????????????????????????????????????????????? type=46
                        HashMap<String, Object> task46 = null;
                        for (int i = 0; i < daytaskList.size(); i++) {
                            if (daytaskList.get(i).get("task_type").equals("46")) {
                                task46 = daytaskList.get(i);
                                daytaskList.remove(i);
                                i--;
                            }
                        }
                        if (null != task46) {
                            daytaskList.add(task46);
                        }


                        //???????????????????????????????????????????????????????????????1??? type=40
                        HashMap<String, Object> task40 = null;
                        for (int i = 0; i < daytaskList.size(); i++) {
                            if (daytaskList.get(i).get("task_type").equals("40")) {
                                task40 = daytaskList.get(i);
                                daytaskList.remove(i);
                                i--;
                            }
                        }
                        if (null != task40) {
                            daytaskList.add(task40);
                        }

                        //?????????????????????index?????????????????????????????????
                        for (int i = 0; i < daytaskList.size(); i++) {
                            if (daytaskList.get(i).get("task_type").equals("43")) {
                                SignListAdapter.task43Index = i;

//                                daytaskList.get(position).get("signStatus") + "").equals("1")
                            } else {
                                SignListAdapter.task43Index = -1;
                            }
                        }


                        myadapterTastkDay = new SignListAdapter(1, taskList, daytaskList, taskiconList, mContext, mActivity);
                        lv_mustdu.setAdapter(myadapterTastkDay); // ??????????????????


                        myadapterTastkOther = new SignListAdapter(2, taskList, othertaskList, taskiconList, mContext, mActivity);
                        lv_mustoder.setAdapter(myadapterTastkOther); // ????????????


                        myadapterTastkSurprise = new SignListAdapter(4, taskList, surpriseTaskList, taskiconList, mContext, mActivity);
                        lv_surprise.setAdapter(myadapterTastkSurprise); // ??????----??????????????????????????????


                        myadapterTastkSurpriseTX = new SignListAdapter(5, taskList, tiXianSurpriseTaskList, taskiconList, mContext, mActivity);
                        lv_surprise_tx.setAdapter(myadapterTastkSurpriseTX); // ??????---??????


                        myadapterTastkSurpriseJiZan = new SignListAdapter(6, taskList, jiZanTaskList, taskiconList, mContext, mActivity);
                        lv_jizan.setAdapter(myadapterTastkSurpriseJiZan); // ??????

                        myadapterTastkDuoBao = new SignListAdapter(7, taskList, duoBaoTaskList, taskiconList, mContext, mActivity);
                        lvDuobao.setAdapter(myadapterTastkDuoBao); // ??????

                        //?????????????????????????????????
                        if (SharedPreferencesUtil.getBooleanData(mContext, "yaoQingCanTaunGo", false)) {
                            SharedPreferencesUtil.saveBooleanData(mContext, "yaoQingCanTaunGo", false);


                            LayoutInflater mInflater = LayoutInflater.from(mContext);
                            final Dialog deleteDialog = new Dialog(mContext, R.style.invate_dialog_style);
                            final View view = mInflater.inflate(R.layout.dialog_new_pt_ct, null);
                            ImageView iv_close = view.findViewById(R.id.iv_close);
                            final TextView tv = view.findViewById(R.id.tv);
                            final Button btn_ok = view.findViewById(R.id.btn_ok);
                            tv.setText("???????????????2???????????????????????????????????????????????????1??????????????????");
                            iv_close.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    deleteDialog.dismiss();

                                }
                            });
                            btn_ok.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    SignListAdapter.goPinTuanDetail(true, daytaskList);

                                    deleteDialog.dismiss();

                                }
                            });

                            deleteDialog.setCanceledOnTouchOutside(false);
                            deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT));

                            ToastUtil.showDialog(deleteDialog);

                        }


                        // ??????????????????????????????

                        for (int i = 0; i < tomorrowTaskList.size(); i++) {

                            if (tomorrowTaskList.get(i).get("task_class").equals("1")) { // ????????????
                                to_ms_count++;

                            }


                            if (tomorrowTaskList.get(i).get("task_class").equals("2")) { // ????????????
                                to_ot_count++;

                            }


                            if (tomorrowTaskList.get(i).get("task_class").equals("6")) { // ????????????????????????
                                to_suprise_count++;

                            }

                            if (tomorrowTaskList.get(i).get("task_class").equals("4")) { // ??????????????????
                                to_suprise_tx_count++;

                            }


                        }

                        String textAwardD = "????????????" + to_ms_count + "???";
                        SpannableString ssTextAwardD = new SpannableString(textAwardD);
                        ssTextAwardD.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 4,
                                textAwardD.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        tv_to_ms.setText(ssTextAwardD);

                        String textAward = "????????????" + to_ot_count + "???";
                        SpannableString ssTextAward = new SpannableString(textAward);
                        ssTextAward.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 4,
                                textAward.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                        String textAwardjx = "??????????????????" + to_suprise_count + "???";
                        SpannableString ssTextAwardjx = new SpannableString(textAwardjx);
                        ssTextAwardjx.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 6,
                                textAwardjx.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        tv_spu.setText(ssTextAwardjx);


                        String textAwardtx = "??????????????????" + to_suprise_tx_count + "???";
                        SpannableString ssTextAwardtx = new SpannableString(textAwardtx);
                        ssTextAwardtx.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 6,
                                textAwardtx.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        tv_spu_tx.setText(ssTextAwardtx);


                        tv_to_ot.setText(ssTextAward);

                        SharedPreferencesUtil.saveStringData(context, "IDallId", allId);
                        allId = "";


                        // gv_qiandao.setVisibility(View.VISIBLE);

                        String berforIds = SharedPreferencesUtil.getStringData(context, "IDallId", "");

                        if (!berforIds.contains(allId)) {

                            Boolean isSignLoginBack = SharedPreferencesUtil.getBooleanData(mContext,
                                    Pref.ISKAIDIAN_JUMP_LOGIN, false);

                            if (!isSignLoginBack) {
                                CenterToast.centerToast(context, "????????????????????????~");
                                // SharedPreferencesUtil.saveBooleanData(mContext,
                                // Pref.ISKAIDIAN_JUMP_LOGIN, true);
                            }

                            // ??????????????????????????????????????????????????????????????????????????????????????????true
                            SharedPreferencesUtil.saveBooleanData(mContext, Pref.ISKAIDIAN_JUMP_LOGIN, false);

                            // CenterToast.centerToast(context, "????????????????????????~");
                            SharedPreferencesUtil.saveStringData(context, "IDallId", allId);

                        }

                        //???????????? ??? ?????????????????????????????????---?????????????????????????????????????????????
                        if (roll == 1) { //?????????
                            if (duoBaoTaskList.size() > 0) {
                                for (int i = 0; i < duoBaoTaskList.size(); i++) {
                                    if (duoBaoTaskList.get(i).get("task_type").equals("17")) {

                                        //???????????????????????????
                                        if (!"1".equals(duoBaoTaskList.get(i).get("signStatus"))) {
                                            //???????????????????????????????????????
                                            if (!SharedPreferencesUtil.getBooleanData(mContext, "CANTUANYINDAO" + YCache.getCacheUserSafe(mContext), false)) {

                                                //???????????????????????????
                                                SharedPreferencesUtil.saveBooleanData(mContext, "CANTUANYINDAO" + YCache.getCacheUserSafe(mContext), true);

                                                if (SignListAdapter.offered == 2 && SignListAdapter.orderCount == 0) {//????????????????????????
                                                    SignListAdapter.tuanClass = 2;
                                                    Intent intent = new Intent(mContext, SignGroupShopActivity.class);
                                                    startActivity(intent);
                                                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                                                }


                                            }

                                        }

                                    }
                                }

                            }
                        }
                        //??????????????????????????????

                        LogYiFu.e("otherCount-------------", otherCount + "");
                        if ((otherCount - otherCountComplete) <= 0) {   //??????????????????????????????
                            eWaiTaskComplete = true;//?????????1???????????????????????????????????????????????????

                        }


//                        for (HashMap<String, Object> hashMap : othertaskList) {
//                            if (!"1".equals(hashMap.get("signStatus"))) {
//                                eWaiTaskComplete = false;//?????????1???????????????????????????????????????????????????
//                                break;
//                            }
//                        }


                        //??????????????????????????????
                        if ((bizuoCount - bizuoCountComplete) <= 0) {   //??????????????????????????????
                            biZuoTaskCoumlete = true;//?????????1???????????????????????????????????????????????????

                        }


//                        for (HashMap<String, Object> hashMap : daytaskList) {
//                            if (!"1".equals(hashMap.get("signStatus"))) {
//                                biZuoTaskCoumlete = false;//?????????1???????????????????????????????????????????????????
//                                break;
//                            }
//                        }


//                        scollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                            @Override
//                            public void onGlobalLayout() {
//                                if (!isFlagZIDONGGUNDONG) {
////                                    int[] location = new int[2];
////                                    rl_surprise_tx.getLocationOnScreen(location);
////                                    final int x = location[0];
////                                    final int y = location[1];
////                                    if (YJApplication.instance.isLoginSucess()) {
////                                        scollView.getRefreshableView().smoothScrollTo(x, y - DP2SPUtil.dp2px(mContext, 80) - finalStatusBarHeight);
////
////                                    } else {
////                                        scollView.getRefreshableView().smoothScrollTo(x, y - DP2SPUtil.dp2px(mContext, 50) - finalStatusBarHeight);
////
////                                    }
//
//
//                                    int[] location = new int[2];
//                                    rlBizuo.getLocationOnScreen(location);
//                                    zidongGundongYBZ = location[1];
//
//
//                                    int[] locationEW = new int[2];
//                                    rlEwai.getLocationOnScreen(locationEW);
//                                    zidongGundongYEW = locationEW[1];
//
//
//                                    isFlagZIDONGGUNDONG = true;
//                                }
//                            }
//                        });


                        /**??????????????????????????????????????????  -- ?????????????????????????????????????????????
                         *  ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                         */
                        if (current_date.contains("newbie")) {//????????????newbie?????????????????????newbie01????????????????????????????????????10???


                            List<HashMap<String, Object>> tempTxList = new ArrayList<HashMap<String, Object>>();
                            tempTxList.addAll(tiXianSurpriseTaskList);
                            for (int i = 0; i < tempTxList.size(); i++) {
                                int type = 0;
                                try {
                                    type = Integer.parseInt(tempTxList.get(i).get("task_type") + "");
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                                if (type == 21 || type == 25 || type == 15 || type == 25 || type == 27) {
                                    tempTxList.remove(i);
                                    i--;
                                }

                            }
                            boolean txIsComplete = true;
                            if (tempTxList.size() > 0) {
                                for (HashMap<String, Object> hashMap : tempTxList) {
                                    if (!"1".equals(hashMap.get("signStatus"))) {//????????????
                                        txIsComplete = false;
                                        break;
                                    }
                                }
                            } else {
                                txIsComplete = false;
                            }

                            if (txIsComplete) {
                                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                String first_times = SharedPreferencesUtil.getStringData(mContext, YCache.getCacheUser(mContext) + "tianwanchengtishi", "0");
                                // ????????????
                                String date = sdf.format(new Date());
                                if (!first_times.equals(date)) {
                                    ToastUtil.showDialog(new TiXianWanchengTishiDialog(mContext, R.style.DialogStyle1, signFragment));
                                    SharedPreferencesUtil.saveStringData(mContext, YCache.getCacheUser(mContext) + "tianwanchengtishi", sdf.format(new Date()));
                                }
                            }

                        }

                        /**
                         * ??????????????????????????????????????????????????????????????????????????????????????????????????????
                         */

                        if (biZuoTaskCoumlete && eWaiTaskComplete) {
                            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String first_times = SharedPreferencesUtil.getStringData(mContext, YCache.getCacheUser(mContext) + "bizuoheewairenwuwancchengtishi", "0");
                            // ????????????
                            String date = sdf.format(new Date());
                            if (!first_times.equals(date)) {
//                                ToastUtil.showDialog(new BizuoEwaiWanchengTishiDialog(mContext, R.style.DialogStyle1));
                                showBIZUOEWIcompleteTishi();
                                SharedPreferencesUtil.saveStringData(mContext, YCache.getCacheUser(mContext) + "bizuoheewairenwuwancchengtishi", sdf.format(new Date()));
                            }
                        }


//                        if(zidongGundongYBZ == 0){
//                            zidongGundongYBZ = y;
//                        }
//


//                        int[] locationEW = new int[2];
//                        rlEwai.getLocationOnScreen(locationEW);
//                        EWtaskTishiDialog.zidongGundongYeW = locationEW[1];


//                        showsignhint();

                        //????????????????????????
//                        tixianlistscrolltop();


                        if (daytaskList.size() == 0) {
                            rlBizuo.setVisibility(View.GONE);
                        } else {
                            rlBizuo.setVisibility(View.VISIBLE);
                        }

//                        if (othertaskList.size() == 0) {
//                            rlEwai.setVisibility(View.GONE);
//                        } else {
//                            rlEwai.setVisibility(View.VISIBLE);
//                        }
//
//
//                        if (surpriseTaskList.size() == 0) {
//                            rl_surprise.setVisibility(View.GONE);
//                        } else {
//                            rl_surprise.setVisibility(View.VISIBLE);
//                        }
//
//                        if (tiXianSurpriseTaskList.size() == 0) {
//                            rl_surprise_tx.setVisibility(View.GONE);
//                        } else {
//                            rl_surprise_tx.setVisibility(View.VISIBLE);
//                        }
//
//                        //?????????????????????----???????????????????????????
//                        if (jiZanTaskList.size() == 0) {
//                            rl_jizan.setVisibility(View.GONE);
//                        } else {
//                            rl_jizan.setVisibility(View.GONE);
//                        }
//
//                        if (duoBaoTaskList.size() == 0) {
//                            rlDuuobao.setVisibility(View.GONE);
//                        } else {
//                            rlDuuobao.setVisibility(View.VISIBLE);
//                        }


                        signIsShow = true;


                        if (SignListAdapter.showFirstClickInSuccseeDialog) {
                            SignListAdapter.showFirstClickInSuccseeDialog = false;
                            SignCompleteDialogUtil.firstClickInGoToZP(mContext);
                        } else {
                            if (neeedFenzhongCompleteDiaog) {
                                neeedFenzhongCompleteDiaog = false;
//                            ToastUtil.showDialog(new SignFengZhongCompleteDialog(mContext, R.style.DialogQuheijiao, "bankuailiulanwancheng", SignFragment.signFragment));
                                String jianglivalue = SignListAdapter.jiangliValueMap.get(YConstance.SCAN_SHOP_TIME);
                                SignCompleteDialogUtil.showSignComplete(mContext, jianglivalue);
                            }
                        }


                        //????????????????????????????????????????????????????????????????????????????????????
                        if (isTastComplete) {
                            zidongCiickNextTask();
                        }


                        allId = "";

                        // adapter.notifyDataSetChanged();

                    }

                    ll_yugao.setVisibility(View.VISIBLE);

                } else {


                    daytaskList.clear();
                    if (null != myadapterTastkDay) {
                        myadapterTastkDay.notifyDataSetChanged();
                    }


                    othertaskList.clear();
                    if (null != myadapterTastkOther) {
                        myadapterTastkOther.notifyDataSetChanged();
                    }


                    tiXianSurpriseTaskList.clear();
                    if (null != myadapterTastkSurpriseTX) {
                        myadapterTastkSurpriseTX.notifyDataSetChanged();
                    }


                    jiZanTaskList.clear();
                    if (null != myadapterTastkSurpriseJiZan) {
                        myadapterTastkSurpriseJiZan.notifyDataSetChanged();
                    }


                    duoBaoTaskList.clear();
                    if (null != myadapterTastkDuoBao) {
                        myadapterTastkDuoBao.notifyDataSetChanged();
                    }


                }

                if (fromMianOrFaClick) {
                    fromMianOrFaClick = false;
                    DialogUtils.showSignDialogFromMianOrFa(mContext);
                }

//                if (SharedPreferencesUtil.getBooleanData(mContext, "20Choujiang_finish", false)) {
//                    SharedPreferencesUtil.saveBooleanData(mContext, "20Choujiang_finish", false);
//                    ToastUtil.showMyToast(mContext, "????????????????????????????????????????????????", 3000);
//                }

                questCalender(0);


            }


        }.execute();

    }


    private void zidongCiickNextTask() {
        completeWaitDialog.dismiss();

        //????????????????????????????????????

//                        lv_mustdu.performItemClick(lv_mustdu.getAdapter().getView(1, null, null), 1, lv_mustdu.getItemIdAtPosition(1));


        //???????????????????????????????????????????????????????????????????????????
        boolean biZuoUnfinished = false;//?????????????????????????????????
        int mIndex = -1;

        if (daytaskList.size() > 0) {
            for (int i = 0; i < daytaskList.size(); i++) {
                if (!"1".equals(daytaskList.get(i).get("signStatus"))) {//???????????????
                    biZuoUnfinished = true;
                    mIndex = i;
                    SignUtil.ZiDongClickNextTask(mContext, mIndex, daytaskList, taskList, taskiconList);
                    isTastComplete = false;
                    break;

                }
            }
        }


        if (!biZuoUnfinished && othertaskList.size() > 0) {
            for (int j = 0; j < othertaskList.size(); j++) {
                if (!"1".equals(othertaskList.get(j).get("signStatus"))) {//???????????????
                    mIndex = j;
                    SignUtil.ZiDongClickNextTask(mContext, mIndex, othertaskList, taskList, taskiconList);
                    isTastComplete = false;
                    break;


                }
            }
        }


        if (mIndex == -1) {//?????????????????????????????????????????????

//            showBIZUOEWIcompleteTishi();

            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String first_times = SharedPreferencesUtil.getStringData(mContext, YCache.getCacheUser(mContext) + "bizuoheewairenwuwancchengtishi", "0");
            // ????????????
            String date = sdf.format(new Date());
            if (!first_times.equals(date)) {
//                                ToastUtil.showDialog(new BizuoEwaiWanchengTishiDialog(mContext, R.style.DialogStyle1));
                showBIZUOEWIcompleteTishi();
                SharedPreferencesUtil.saveStringData(mContext, YCache.getCacheUser(mContext) + "bizuoheewairenwuwancchengtishi", sdf.format(new Date()));
            }


        }

    }

    private void removeMonTaskComplet(List<HashMap<String, Object>> list) {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if ("1".equals(list.get(i).get("signStatus")) && list.get(i).get("task_type").equals("24")) {
                    list.remove(i);


//                    boolean isshowjingxiwancheng = SharedPreferencesUtil.getBooleanData(mContext,
//                            "isSHOWjingxiWANCHENG", false);
//                    if (!isshowjingxiwancheng) {
//                        SharedPreferencesUtil.saveBooleanData(context, "isSHOWjingxiWANCHENG",
//                                true);
//                        NewSignJingxiWanchengDiaolg jingxiWanchengDiaolg =
//                                new NewSignJingxiWanchengDiaolg(context, R.style.DialogQuheijiao);
//                        jingxiWanchengDiaolg.getWindow().setWindowAnimations(R.style.common_dialog_style);
//                        jingxiWanchengDiaolg.show();
//
//                    }


                    i--;
                }
            }
        }

    }


    //??????????????????????????????
    private void removeGoumaiYingTX(List<HashMap<String, Object>> list) {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).get("task_type").equals("6")) {
                    list.remove(i);
                    i--;
                }
            }

        }


    }

    //???????????????????????????
    private boolean checkKaituanTask(List<HashMap<String, Object>> list) {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (!"1".equals(list.get(i).get("signStatus")) && list.get(i).get("task_type").equals("18")) {
                    return true;
                }
            }
        }
        return false;
    }


    //?????????????????????????????????
    private boolean checkHasMonthTask(List<HashMap<String, Object>> list) {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).get("task_type").equals("24")) {
                    return true;
                }
            }
        }
        return false;
    }


    //??????????????????
    private void removeKaiTuan(List<HashMap<String, Object>> list) {

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (!"1".equals(list.get(i).get("signStatus")) && list.get(i).get("task_type").equals("18")) {
                    list.remove(i);
                    i--;
                }
            }

        }

    }


    //???????????????APP???????????????
    private void removeH5task(List<HashMap<String, Object>> list) {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                int h5 = 1;
                try {
                    h5 = Integer.parseInt(list.get(i).get("task_h5") + "");
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                if (h5 >= 5) {
                    list.remove(i);
                    i--;
                }
            }

        }

    }


    //??????0????????????
    private void remove0yuanTask(List<HashMap<String, Object>> list) {
        if (list.size() > 0 && isCrazyMon) {
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).get("task_type").equals("28")) {
                        list.remove(i);
                        i--;
                    }
                }

            }
        }

    }


    //??????????????????
    private void removeMonTask(List<HashMap<String, Object>> list) {

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).get("task_type").equals("24")) {
                    list.remove(i);
                    i--;
                }
            }

        }

    }


    //?????????????????????
    private void removeHongBao(List<HashMap<String, Object>> list) {

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).get("task_type").equals("23")) {
                    list.remove(i);
                    i--;
                }
            }

        }

    }


    //???????????????
    private void removeChouJiang(List<HashMap<String, Object>> list) {

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).get("task_type").equals("26")) {
                    list.remove(i);
                    i--;
                }
            }

        }

    }

    private void tiXianListScrollTop() {
        if (isTXListScroll && hasTXtask) {
            //?????????????????????
            int statusBarHeight2 = -1;
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                int height = Integer.parseInt(clazz.getField("status_bar_height")
                        .get(object).toString());
                statusBarHeight2 = getResources().getDimensionPixelSize(height);
            } catch (Exception ee) {
                ee.printStackTrace();
            }

            //??????????????????????????????????????????
//            final int finalStatusBarHeight = statusBarHeight2;
//            scollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    if (!isFlag) {
//                        int[] location = new int[2];
//                        rl_surprise_tx.getLocationOnScreen(location);
//                        final int x = location[0];
//                        final int y = location[1];
//                        if (YJApplication.instance.isLoginSucess()) {
//                            scollView.getRefreshableView().smoothScrollTo(x, y - DP2SPUtil.dp2px(mContext, 80) - finalStatusBarHeight);
//
//                        } else {
//                            scollView.getRefreshableView().smoothScrollTo(x, y - DP2SPUtil.dp2px(mContext, 50) - finalStatusBarHeight);
//
//                        }
//                        isFlag = true;
//                    }
//                }
//            });
        }
    }

    //???????????????
    private boolean isFlag;


    private boolean isFlagZIDONGGUNDONG;


    private boolean isFlagSignHint;
    private boolean isFeWSignHint;

    /**
     * ?????????????????????????????????
     */
    private void querySignListYet() {

        final long statrtTime = System.currentTimeMillis();
        new SAsyncTask<Void, Void, HashMap<String, List<HashMap<String, Object>>>>((FragmentActivity) mContext, 0) {

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                LoadingDialog.show((FragmentActivity) mContext);
            }

            @Override
            protected HashMap<String, List<HashMap<String, Object>>> doInBackground(FragmentActivity context,
                                                                                    Void... params) throws Exception {
                return ComModel2.getSignYetList(mContext);
            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         HashMap<String, List<HashMap<String, Object>>> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null) {

                    // Log.e("????????????????????????", (endTime - statrtTime) +
                    // "---signIn2_0/userTaskList");

                    if (result.size() != 0) {

                        SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", false);
                        SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", false);

                        daytaskListYet.clear();
                        othertaskListYet.clear();
                        surpriseTaskListYet.clear();
                        tiXiansurpriseTaskListYet.clear();
                        jiZanTaskListYet.clear();
                        duoBaoTaskListYet.clear();
                        daytaskListYet.addAll(result.get("daytaskListYet")); // ?????????????????????????????????????????????
                        othertaskListYet.addAll(result.get("othertaskListYet"));// ?????????????????????????????????????????????
                        surpriseTaskListYet.addAll(result.get("surpriseTaskListYet"));// ???????????????????????????????????????
                        tiXiansurpriseTaskListYet.addAll(result.get("txsurprisetasklistyet"));// ?????????????????????????????????
                        jiZanTaskListYet.addAll(result.get("jizantasklistyet"));// ???????????????????????????
                        duoBaoTaskListYet.addAll(result.get("duoBaoTaskListYet"));// ???????????????????????????


                        bizuoCountComplete = daytaskListYet.size();
                        otherCountComplete = othertaskListYet.size();


                        LogYiFu.e("othertaskListYet", othertaskListYet + "");

                        queryLoginSignListLogined();// ??????????????????---?????????

                    }

                }
            }

        }.execute();
    }

    /**
     * ????????????????????????(?????????)
     */
    private void querySignListUnLogin() {

        new SAsyncTask<Void, Void, HashMap<String, List<HashMap<String, Object>>>>((FragmentActivity) mContext, 0) {

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();

                try {
                    LoadingDialog.show((FragmentActivity) mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected HashMap<String, List<HashMap<String, Object>>> doInBackground(FragmentActivity context,
                                                                                    Void... params) throws Exception {
                return ComModel2.getSignList(mContext);
            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         HashMap<String, List<HashMap<String, Object>>> result, Exception e) {
                super.onPostExecute(context, result, e);
//                scollView.onRefreshComplete();
                if (isXiala) {
                    isXiala = false;
                    return;
                }

                if (e == null && result != null) {

                    // Log.e("????????????????????????", (endTime - statrtTime) +
                    // "---signIn2_0/siTaskList");

                    // LogYiFu.e("NNNN", result + "");

                    if (result.size() != 0) {

                        // ???????????????4????????? ??????-??????-????????????????????????-????????????????????????????????????---???????????????

                        taskList.clear();
                        daytaskList.clear();
                        othertaskList.clear();
                        tomorrowTaskList.clear();
                        surpriseTaskList.clear();
                        tiXianSurpriseTaskList.clear();
                        jiZanTaskList.clear();
                        duoBaoTaskList.clear();

                        taskiconList.clear();
                        taskiconList.addAll(result.get("taskiconList")); // ????????????????????????????????????

                        taskList.addAll(result.get("taskList")); // ??????????????????????????????
                        daytaskList.addAll(result.get("daytaskList")); // ??????????????????????????????
                        othertaskList.addAll(result.get("othertaskList")); // ??????????????????????????????

                        tomorrowTaskList.addAll(result.get("tomorrowTaskList")); // ??????????????????????????????
                        surpriseTaskList.addAll(result.get("surpriseTaskList")); // ????????????
                        tiXianSurpriseTaskList.addAll(result.get("txsurprisetasklist")); // ??????????????????????????????????????????
                        jiZanTaskList.addAll(result.get("jizantasklist")); // ??????????????????????????????????????????
                        duoBaoTaskList.addAll(result.get("duoBaoTaskList")); // ??????????????????????????????????????????

                        if (isCrazyMon) {
                            HashMap<String, Object> crazyMon = new HashMap<String, Object>();
                            crazyMon.put("index", "-999");
                            crazyMon.put("t_id", "-999");
                            crazyMon.put("num", "-999");
                            crazyMon.put("task_type", "999");
                            crazyMon.put("value", "-999");
                            crazyMon.put("task_class", "3");
                            duoBaoTaskList.add(0, crazyMon);
                        }


//                        // ?????????????????? ???????????????????????????????????????????????????????????? ???????????????
//                        // ????????????????????????1?????????????????????????????????
//
//                        boolean hasMon = false; // ??????????????????????????????
//                        boolean hasTX = false;// ????????? ??????????????????
//                        boolean hasJX = false;// ????????? ??????????????????
//
//                        for (HashMap<String, Object> hashMap : surpriseTaskList) {
//                            if (hashMap.get("task_type").equals("999")) {
//                                hasMon = true;
//                            }
//
//                            if (hashMap.get("task_type").equals("6")) {
//                                hasJX = true;
//                                hasGoumai = true;
//
//                            }
//
//                        }
//
//                        if (tiXianSurpriseTaskList.size() > 0) {
//                            hasTX = true;
//                            hasGoumai = true;
//
//                        }
//
//                        // ??????-----???????????????????????????
//
//                        if (hasMon && hasTX && hasJX) {// ??????3?????????--????????????????????????
//
//
//                            for (int i = 0; i < surpriseTaskList.size(); i++) {
//
//                                if (surpriseTaskList.get(i).get("task_type").equals("6")) {
//                                    surpriseTaskList.remove(i);
//                                    i--;
//                                }
//
//                            }
//
//                            tiXianSurpriseTaskList.clear();
//
//                        }
//
//                        if (hasMon && hasTX && !hasJX) {// ???????????????????????????-----????????????
//
//                            tiXianSurpriseTaskList.clear();
//
//                        }
//
//                        if (!hasMon && hasTX && hasJX) {// ????????????????????????-----??????????????????
//
//                            for (int i = 0; i < surpriseTaskList.size(); i++) {
//
//                                if (surpriseTaskList.get(i).get("task_type").equals("6")) {
//                                    surpriseTaskList.remove(i);
//                                    i--;
//                                }
//
//                            }
//
//                        }
//
//                        if (hasMon && !hasTX && hasJX) {// ????????????????????????????????? ---
//                            // ??????????????????
//
//                            for (int i = 0; i < surpriseTaskList.size(); i++) {
//
//                                if (surpriseTaskList.get(i).get("task_type").equals("6")) {
//                                    surpriseTaskList.remove(i);
//                                    i--;
//                                }
//
//                            }
//
//                        }
//
//


                        // ????????????????????????????????????????????????

                        for (HashMap<String, Object> hashMap : othertaskList) {
                            if (hashMap.get("task_type").equals("6")) {
                                SignListAdapter.hasGoumai = true;
                            }

                        }
                        if (tiXianSurpriseTaskList.size() > 0) {
                            hasTXtask = true;
                        }

                        //????????????????????????????????????????????????
                        for (int i = 0; i < daytaskList.size(); i++) {
                            if (daytaskList.get(i).get("task_type").equals("46")) {
                                daytaskList.remove(i);
                                i--;
                            }
                        }


                        //??????????????????????????????????????????????????????????????????????????????
                        if (surpriseTaskList.size() > 0) {
                            for (int i = 0; i < surpriseTaskList.size(); i++) {
                                if (surpriseTaskList.get(i).get("task_type").equals("24")) {
                                    HashMap<String, Object> hashMap = surpriseTaskList.get(i);
                                    duoBaoTaskList.add(0, hashMap);
                                    surpriseTaskList.remove(i);
                                    i--;
                                }
                            }
                        }


                        //??????????????????????????????
                        if ("0".equals(today_ref)) {
                            //???????????????????????????
                            removeHongBao(daytaskList);
                            removeHongBao(othertaskList);
                            removeHongBao(tiXianSurpriseTaskList);
                            removeHongBao(jiZanTaskList);
                            removeHongBao(duoBaoTaskList);


                        }


                        /**
                         * ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                         */

                        //1?????????????????????????????????  -----???????????????????????????????????????????????????
                        boolean hasMon = isCrazyMon; // ??????????????????????????????
                        if (hasMon) {

                            //????????????????????????
//                            if (surpriseTaskList.size() > 0) {
//                                surpriseTaskList.clear();
//                            }


//                            if (duoBaoTaskList.size() > 0) {    //??????????????????????????????????????????????????????
//                                for (int i = 0; i < duoBaoTaskList.size(); i++) {
//                                    if (duoBaoTaskList.get(i).get("task_type").equals("6")) {
//                                        duoBaoTaskList.remove(i);
//                                        i--;
//                                    }
//                                }
//
//                            }


                            removeMonTask(daytaskList);
                            removeMonTask(othertaskList);
                            removeMonTask(tiXianSurpriseTaskList);
                            removeMonTask(jiZanTaskList);
                            removeMonTask(duoBaoTaskList);


                            //???????????????--??????
                            removeKaiTuan(daytaskList);
                            removeKaiTuan(othertaskList);
                            removeKaiTuan(tiXianSurpriseTaskList);
                            removeKaiTuan(jiZanTaskList);
                            removeKaiTuan(duoBaoTaskList);


                            //????????????????????????
                            removeHongBao(daytaskList);
                            removeHongBao(othertaskList);
                            removeHongBao(tiXianSurpriseTaskList);
                            removeHongBao(jiZanTaskList);
                            removeHongBao(duoBaoTaskList);


                            //?????????????????????
                            removeGoumaiYingTX(tiXianSurpriseTaskList);


                        }
                        //2???????????????????????????????????????
                        boolean kt1 = checkKaituanTask(daytaskList);
                        boolean kt2 = checkKaituanTask(othertaskList);
                        boolean kt3 = checkKaituanTask(tiXianSurpriseTaskList);
                        boolean kt4 = checkKaituanTask(jiZanTaskList);
                        boolean kt5 = checkKaituanTask(duoBaoTaskList);
                        if (kt1 || kt2 || kt3 || kt4 || kt5) {//???????????????
                            //????????????????????????
//                            if (surpriseTaskList.size() > 0) {
//                                surpriseTaskList.clear();
//                            }


//                            if (duoBaoTaskList.size() > 0) {    //??????????????????????????????????????????????????????
//                                for (int i = 0; i < duoBaoTaskList.size(); i++) {
//                                    if (duoBaoTaskList.get(i).get("task_type").equals("6")) {
//                                        duoBaoTaskList.remove(i);
//                                        i--;
//                                    }
//                                }
//
//                            }


                            removeMonTask(daytaskList);
                            removeMonTask(othertaskList);
                            removeMonTask(tiXianSurpriseTaskList);
                            removeMonTask(jiZanTaskList);
                            removeMonTask(duoBaoTaskList);


                            //?????????????????????
                            if (duoBaoTaskList.size() > 0) {
                                for (int i = 0; i < duoBaoTaskList.size(); i++) {
                                    if (duoBaoTaskList.get(i).get("task_type").equals("999")) {
                                        duoBaoTaskList.remove(i);
                                        i--;
                                    }
                                }

                            }


                        }

                        //3?????????????????????????????????????????????????????????

//                        boolean hasMonthTask = false;
//                        if (duoBaoTaskList.size() > 0) {    //??????????????????????????????????????????????????????
//                            for (int i = 0; i < duoBaoTaskList.size(); i++) {
//                                if (duoBaoTaskList.get(i).get("task_type").equals("6")) {
//                                    hasMonthTask = true;
//                                }
//                            }
//
//                        }

                        boolean mon1 = checkHasMonthTask(daytaskList);
                        boolean mon2 = checkHasMonthTask(othertaskList);
                        boolean mon3 = checkHasMonthTask(tiXianSurpriseTaskList);
                        boolean mon4 = checkHasMonthTask(jiZanTaskList);
                        boolean mon5 = checkHasMonthTask(duoBaoTaskList);
                        if (mon1 || mon2 || mon3 || mon4 || mon5) {//?????????????????????


                            //?????????????????????
//                            removeGoumaiYingTX(tiXianSurpriseTaskList);


                            //???????????????--??????
                            removeKaiTuan(daytaskList);
                            removeKaiTuan(othertaskList);
                            removeKaiTuan(tiXianSurpriseTaskList);
                            removeKaiTuan(jiZanTaskList);
                            removeKaiTuan(duoBaoTaskList);


                            //?????????????????????
                            if (duoBaoTaskList.size() > 0) {
                                for (int i = 0; i < duoBaoTaskList.size(); i++) {
                                    if (duoBaoTaskList.get(i).get("task_type").equals("999")) {
                                        duoBaoTaskList.remove(i);
                                        i--;
                                    }
                                }

                            }

                        }


                        //???????????????????????????????????????????????????????????????1??? type=40
                        HashMap<String, Object> task40 = null;
                        for (int i = 0; i < daytaskList.size(); i++) {
                            if (daytaskList.get(i).get("task_type").equals("40")) {
                                task40 = daytaskList.get(i);
                                daytaskList.remove(i);
                                i--;
                            }
                        }
                        if (null != task40) {
                            daytaskList.add(task40);
                        }


                        //???????????????APP???????????????--????????????---9257
                        removeH5task(daytaskList);
                        removeH5task(othertaskList);
                        removeH5task(tiXianSurpriseTaskList);
                        removeH5task(jiZanTaskList);
                        removeH5task(duoBaoTaskList);


                        //??????????????????????????????0??????
                        remove0yuanTask(daytaskList);
                        remove0yuanTask(othertaskList);
                        remove0yuanTask(tiXianSurpriseTaskList);
                        remove0yuanTask(jiZanTaskList);
                        remove0yuanTask(duoBaoTaskList);


                        myadapterTastkDay = new SignListAdapter(1, taskList, daytaskList, taskiconList, mContext, mActivity);
                        lv_mustdu.setAdapter(myadapterTastkDay); // ??????????????????


                        myadapterTastkOther = new SignListAdapter(2, taskList, othertaskList, taskiconList, mContext, mActivity);
                        lv_mustoder.setAdapter(myadapterTastkOther); // ????????????


                        myadapterTastkSurprise = new SignListAdapter(4, taskList, surpriseTaskList, taskiconList, mContext, mActivity);
                        lv_surprise.setAdapter(myadapterTastkSurprise); // ??????


                        myadapterTastkSurpriseTX = new SignListAdapter(5, taskList, tiXianSurpriseTaskList, taskiconList, mContext, mActivity);
                        lv_surprise_tx.setAdapter(myadapterTastkSurpriseTX); // ??????---??????


                        myadapterTastkSurpriseJiZan = new SignListAdapter(6, taskList, jiZanTaskList, taskiconList, mContext, mActivity);
                        lv_jizan.setAdapter(myadapterTastkSurpriseJiZan); // ??????


                        myadapterTastkDuoBao = new SignListAdapter(7, taskList, duoBaoTaskList, taskiconList, mContext, mActivity);
                        lvDuobao.setAdapter(myadapterTastkDuoBao); // ??????--???????????????????????????

                        // ??????????????????????????????

                        for (int i = 0; i < tomorrowTaskList.size(); i++) {

                            // ????????????

                            if (tomorrowTaskList.get(i).get("task_class").equals("1")) { // ????????????
                                to_ms_count++;

                            }
                            if (tomorrowTaskList.get(i).get("task_class").equals("2")) { // ????????????
                                to_ot_count++;

                            }


                            if (tomorrowTaskList.get(i).get("task_class").equals("6")) { // ????????????????????????
                                to_suprise_count++;

                            }

                            if (tomorrowTaskList.get(i).get("task_class").equals("4")) { // ??????????????????
                                to_suprise_tx_count++;

                            }


                        }


                        if (daytaskList.size() == 0) {
                            rlBizuo.setVisibility(View.GONE);
                        } else {
                            rlBizuo.setVisibility(View.VISIBLE);
                        }

//                        if (othertaskList.size() == 0) {
//                            rlEwai.setVisibility(View.GONE);
//                        } else {
//                            rlEwai.setVisibility(View.VISIBLE);
//                        }
//
//
//                        if (surpriseTaskList.size() == 0) {
//                            rl_surprise.setVisibility(View.GONE);
//                        } else {
//                            rl_surprise.setVisibility(View.VISIBLE);
//                        }
//
//                        if (tiXianSurpriseTaskList.size() == 0) {
//                            rl_surprise_tx.setVisibility(View.GONE);
//                        } else {
//                            rl_surprise_tx.setVisibility(View.VISIBLE);
//                        }
//
//                        //?????????????????????
//                        if (jiZanTaskList.size() == 0) {
//                            rl_jizan.setVisibility(View.GONE);
//                        } else {
//                            rl_jizan.setVisibility(View.GONE);
//                        }
//                        if (duoBaoTaskList.size() == 0) {
//                            rlDuuobao.setVisibility(View.GONE);
//                        } else {
//                            rlDuuobao.setVisibility(View.VISIBLE);
//                        }


                        String textAwardD = "????????????" + to_ms_count + "???";
                        SpannableString ssTextAwardD = new SpannableString(textAwardD);
                        ssTextAwardD.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 4,
                                textAwardD.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        tv_to_ms.setText(ssTextAwardD);


                        String textAward = "????????????" + to_ot_count + "???";
                        SpannableString ssTextAward = new SpannableString(textAward);
                        ssTextAward.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 4,
                                textAward.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        tv_to_ot.setText(ssTextAward);


                        String textAwardjx = "??????????????????" + to_suprise_count + "???";
                        SpannableString ssTextAwardjx = new SpannableString(textAwardjx);
                        ssTextAwardjx.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 6,
                                textAwardjx.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        tv_spu.setText(ssTextAwardjx);


                        String textAwardtx = "??????????????????" + to_suprise_tx_count + "???";
                        SpannableString ssTextAwardtx = new SpannableString(textAwardtx);
                        ssTextAwardtx.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 6,
                                textAwardtx.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        tv_spu_tx.setText(ssTextAwardtx);


                        SharedPreferencesUtil.saveStringData(context, "IDallId", allId);
                        allId = "";


//                        showSignHint();


                        ll_yugao.setVisibility(View.VISIBLE);

                        initCalendar(0);


                    }

                }
            }

        }.execute();

    }




    // ????????????????????????
    private void querySignData() {


        YConn.httpPost(mContext, YUrl.SIGN_DATA, new HashMap<String, String>(), new HttpListener<SignCountData>() {
            @Override
            public void onSuccess(SignCountData signCountData) {


                mSignCountData = signCountData;
                current_date = signCountData.getCurrent_date() + "";
                boolean mIsVip = CommonUtils.isVip(signCountData.getIsVip(), signCountData.getMaxType());

                //?????????????????????????????????????????????????????????????????????????????????
                if (signCountData.getCurrent_status_data() == 1 && signCountData.getAllNumber() > 0) {
//                    if (SharedPreferencesUtil.getBooleanData(mContext, Pref.JUMP_XCX_SIGN, false)) {
//                        SharedPreferencesUtil.saveBooleanData(mContext, Pref.JUMP_XCX_SIGN, false);


                    Intent intent = new Intent(mContext, SignDrawalLimitActivity.class)
                            .putExtra("type", 1);
                    intent.putExtra("fromSign", true);
                    startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);


//                    }
                }
                String yue = signCountData.getBCount() + "";
                String desing = signCountData.getDesing() + "";

//                whetherTask = signCountData.getWhetherTask() + "";

                if (current_date.indexOf("newbie") == -1 && !mIsVip) { //???????????????()
                    whetherTask = 0;
                } else {
                    whetherTask = 1;
                }


                int yueLen = yue.length();

                if (5 >= yueLen) {
                    tv_eyu.setTextSize(13);
                }

                if (yueLen >= 6) {
                    tv_eyu.setTextSize(13);
                }

                if (yueLen >= 7) {
                    tv_eyu.setTextSize(11);
                }
                // ?????????????????????????????? ?????????????????????
                SignListAdapter.lotterynumber = Integer.parseInt(signCountData.getLotterynumber() + "");

                //????????????????????????
//                        fxqd = result.get("fxqd") + "";


                int mTwofoldness = Integer
                        .parseInt(SharedPreferencesUtil.getStringData(context, Pref.TWOFOLDNESS, -1 + ""));
                int mIsOpen = Integer
                        .parseInt(SharedPreferencesUtil.getStringData(context, Pref.IS_OPEN, -1 + ""));
                // ??????------------

                tv_eyu.setText(new DecimalFormat("#0.00").format(Double.parseDouble(yue)));

                tv_jifen.setText(signCountData.getICount() + "");
                tv_youhuiquan.setText(signCountData.getCCount() + "");



                //???????????????????????? ??? ??????????????????
                if (YJApplication.instance.isLoginSucess() && YCache.getCacheUser(mContext).getReviewers() == 1
                        ||  signCountData.getCurrent_date().indexOf("newbie") != -1) {
                    ll_wallet_count.setVisibility(View.GONE);
                    LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParam.setMargins(0, 0, 0, DP2SPUtil.dp2px(mContext, 7));
                    tv_jinrizhuan.setLayoutParams(layoutParam);
                    tv_jinrizhuan.setGravity(Gravity.CENTER);

                } else {

                    ll_wallet_count.setVisibility(View.VISIBLE);
                }



                tv_tixianzhong
                        .setText(
                                Html.fromHtml("????????? <b>"
                                        + new DecimalFormat("#0.00")
                                        .format(Double.parseDouble(signCountData.getDesing() + ""))
                                        + "</b>"));


                tv_ketixian
                        .setText(
                                Html.fromHtml("????????? <b>"
                                        + new DecimalFormat("#0.00")
                                        .format(Double.parseDouble(signCountData.getWithdrawal_money() + ""))
                                        + "</b>"));
                tv_yitixian
                        .setText(
                                Html.fromHtml("????????? <b>"
                                        + new DecimalFormat("#0.00")
                                        .format(Double.parseDouble(signCountData.getSuccMoney() + ""))
                                        + "</b>"));


                //????????????????????????
//                signCountData.setCurrent_date("111");//??????
                if (YCache.getCacheUser(mContext).getReviewers() == 1
                        && !signCountData.getCurrent_date().equals("newbie01")) {
                    if(null == signCountData.getToday_money2()){
                        signCountData.setToday_money2("1.00");
                    }
                    signCountData.setToday_money(signCountData.getToday_money2());
                }


                // ??????
                if (Double.parseDouble(signCountData.getToday_money() + "") == 0) {
                    tv_maomi_center_no_money_tou.setVisibility(View.VISIBLE);
                    rl_maomi_center_no_money.setVisibility(View.VISIBLE);
                    tv_jinrizhuan.setVisibility(View.GONE);
                    textView8.setVisibility(View.GONE);


                } else {
                    tv_maomi_center_no_money_tou.setVisibility(View.GONE);
                    rl_maomi_center_no_money.setVisibility(View.GONE);
                    tv_jinrizhuan.setText(new DecimalFormat("#0.00")
                            .format(Double.parseDouble(signCountData.getToday_money() + "")));
                    tv_jinrizhuan.setVisibility(View.VISIBLE);
                    textView8.setVisibility(View.VISIBLE);

                }


//                if (!isCrazyMon) {
//                    // ??????
//                    if (Double.parseDouble(signCountData.getToday_money() + "") == 0) {
//                        tv_jinrizhuan.setText("????????????");
//                    } else {
//
//                        tv_jinrizhuan.setText(new DecimalFormat("#0.00")
//                                .format(Double.parseDouble(signCountData.getToday_money() + "")));
//
//                    }
//                }


                String bro_count = signCountData.getBro_count() + "";

                double dd = Double.parseDouble(bro_count);

                bro_count = (int) dd + "";

                String fans_count = signCountData.getFans_count() + "";

                if (Integer.parseInt(bro_count) > 100000) {
                    bro_count = "1000000+";
                }

                if (Integer.parseInt(fans_count) > 100000) {
                    fans_count = "1000000+";
                }

                // tv_fans_count.setText("??????????????????" + bro_count);


//                        tv_browse_count.setText("???????????????:" + result.get("point_count"));
//
//                        DecimalFormat df = new DecimalFormat("0.0#");
//                        tv_browse_jiangli.setText("??????????????????:" + df.format(Double.parseDouble(result.get("total_rewards") + "")) + "???");
//


                tv_browse_count.setText("???????????????:" + signCountData.getShareCount() + "");

                DecimalFormat df = new DecimalFormat("0.0#");
                tv_browse_jiangli.setText("????????????:" + df.format(Double.parseDouble(signCountData.getShareMoneyCount() + "")) + "???");


//------------------------------------------------??????????????????---------------------------------------------------------------------
                //????????????-1 0 1 2
//                point_status = signCountData.getPoint_status() + "";
//                if (point_status.equals("0")) {
//                    boolean zidong = SharedPreferencesUtil.getBooleanData(mContext, YCache.getCacheUser(mContext).getUser_id() + "zdongdianzan", false);
//                    if (!zidong) {
//                        //??????????????????
//                        SignListAdapter.dianZan(false, false);
//                    }
//                }
//                //???????????????????????????????????????
//                isGratis = signCountData.getIsGratis() + "";
//
//
//                //popup //?????????H5????????????APP   0??????  1 ???
//                String popup = signCountData.getPopup() + "";
//                if (popup.equals("1") && isGratis.equals("false")) { //?????????????????????????????????????????????????????????????????????????????????????????????isGratis--false
//                    new JiZanCommonDialog(getActivity(), R.style.DialogStyle1, "jixujizandianji").show();
//
//                }
//                //???????????????????????????????????????APP??????????????????????????????????????????????????????????????????----????????????
//                try {
//                    if (popup.equals("1") && isGratis.equals("true") && Integer.parseInt(point_status) > 0) {
//                        //??????????????????
//                        SignListAdapter.dianZan(false, true);
//                    }
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//-------------------------------------------------------------------------------------------------------------------------------------------------
// ------------------------------------------------??????????????????---------------------------------------------------------------------------------------


                        /*
                         /signIn2_0/getCount
                        ?????????????????????
                        roll ???//????????????

                                0???????????????1?????????
                        --------------------------------------------------------------------------------
                        fighStatus???//

                                -1???????????????????????????????????????2?????????????????????-1???????????????0????????????????????????????????????
                                0??????????????????????????????---???????????? ?????????
                                1??????????????????---?????????
                                2??????????????????????????????	 ---????????????	????????????????????????
                                3???????????????????????????????????????	---????????????  ?????? ????????????????????????

                                ??????roll=0????????????5?????????
                                ??????roll = 1 ???????????????????????????fighStatus???????????? ---  ?????????????????????len>=8???
                                ??????2??????????????????-1???3??????????????????0  ---?????????

                        ---------------------------------------------------------------------------------
                        orderStatus???//?????????????????????
                                 0?????????
                                 1?????????
                        -------------------------

                        orderCount ??? //????????????

                                   0???????????????
                                   1??????????????????
                                   2??????????????????

                        -------------------------

                        //??????????????????
                        offered???


                            offered //?????? 0?????????  1????????? 2?????????

                        */


                //???????????????
                //  roll  0???????????????1?????????
                try {
                    roll = Integer.parseInt(signCountData.getRoll() + "");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


                try {
                    fighStatus = Integer.parseInt(signCountData.getFighStatus() + "");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


                if (roll == 1) {
                    SignListAdapter.pingTuanNum = signCountData.getFighStatus() + "";
                }


                //???????????? ???0????????? 1?????????

                try {
                    SignListAdapter.orderStatus = Integer.parseInt(signCountData.getOrderStatus() + "");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                //????????????
                try {
                    SignListAdapter.orderCount = Integer.parseInt(signCountData.getOrderCount() + "");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


                if (roll == 0) {
                    if (fighStatus == 2) {
                        ToastUtil.showMyToast(mContext, "???????????????????????????~", 3 * 1000);
                        restPinTuanToast();//????????????
                    }

                    if (fighStatus == 3) {    //????????????H5????????????????????????????????????????????????----????????????  ???H5???????????????

                        ToastUtil.showMyToast(mContext, "????????????????????????????????????~", 3 * 1000);
                        restPinTuanToast();//????????????
                    }
                }


                //?????????????????????


                SignListAdapter.offered = 0;

                try {
                    SignListAdapter.offered = Integer.parseInt(signCountData.getOffered() + "");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


//-------------------------------------------------------------------------------------------------------------------------------------------------

                //???????????????
                initDialogAndTishi();
                querySignListYet();// ???????????????????????????;????????????


            }

            @Override
            public void onError() {

            }
        });


    }

    /**
     * ??????????????????
     */

    private void restPinTuanToast() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ModQingfeng.restPintuanTishi(mContext);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }).start();


    }


    public static int zidongGundongYEW = 0;

    private String allId = "";


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {


            case R.id.calendar_last://?????????

                reQuestMon--;
                if (reQuestMon == 0) {
                    reQuestMon = 12;
                    reQuestYear--;
                }

                questCalender(-1);

                break;


            case R.id.calendar_next://?????????

                reQuestMon++;
                if (reQuestMon == 13) {
                    reQuestMon = 1;
                    reQuestYear++;
                }
                questCalender(1);

                break;


            case R.id.iv_sign_explain://??????????????????;


//                SignCompleteDialogUtil.firstClickInGoToZP(mContext);

//                SignCompleteDialogUtil.showSignComplete(mContext,100+"");
//            DialogUtils.signGuideVip(mContext)


//                ToastUtil.showDialog(new SignShuomingDialog(mContext, R.style.DialogStyle1));

//                ToastUtil.showDialog(new DialogGofenxiang((Activity)context, context, R.style.DialogStyle1));

//
//                if (!YUrl.debug) {
//                    intent = new Intent(mContext, SignDrawalLimitActivity.class)
//                            .putExtra("type", 1);
//
//
//                    intent.putExtra("fromSign", true);
//                    startActivity(intent);
//                    ((FragmentActivity) mContext).overridePendingTransition(
//                            R.anim.slide_left_in, R.anim.slide_match);
//
//                    return;
//                }

//                ToastUtil.showDialog(new SignShuomingDialog(mContext, R.style.DialogStyle1));


                mContext.startActivity(new Intent(mContext, TestActivity.class));


//
//                shareWaitDialog.show();
//
//
//                Picasso.get()
//                        .load("https://www.measures.wang/small-iconImages/qingfengpic/share_pyq_text.jpg")
//                        .into(new Target() {
//                            @Override
//                            public void onBitmapLoaded(Bitmap bmp, Picasso.LoadedFrom from) {
//
//
//                                String appId = WxPayUtil.APP_ID; // ?????????AppId
//                                if (StringUtils.isEmpty(appId)) {
//                                    appId = YUrl.APP_ID;
//                                }
//                                IWXAPI api = WXAPIFactory.createWXAPI(mContext, appId);
//
//
////                                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.yuanxianjin30);
//
////????????? WXImageObject ??? WXMediaMessage ??????
//                                WXImageObject imgObj = new WXImageObject(bmp);
//                                WXMediaMessage msg = new WXMediaMessage();
//                                msg.mediaObject = imgObj;
//
////???????????????
//                                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 200, true);
//                                msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
//
////????????????Req
//                                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                                req.transaction = buildTransaction("img");
//                                req.message = msg;
//                                req.scene = SendMessageToWX.Req.WXSceneTimeline ;
////                req.userOpenId = getOpenId();
////??????api??????????????????????????????
//                                api.sendReq(req);
//
//
//                                shareWaitDialog.dismiss();
//
//                            }
//
//
//                            @Override
//                            public void onBitmapFailed(Exception e, Drawable drawable) {
//
//
//
//                                shareWaitDialog.dismiss();
//
//
//                            }
//
//                            @Override
//                            public void onPrepareLoad(Drawable drawable) {
//
//                            }
//
//
//                        });


//                startActivity(new Intent(mContext, NewWalletActivity.class));


                //????????????
//                Intent intent6 = new Intent(mContext, OneBuyGroupsDetailsActivity.class);//?????????????????????
//                intent6.putExtra("completeStatus", 4);
//                startActivity(intent6);

//                if (YUrl.kaiguan) {
//                    intent = new Intent(mContext, SignDrawalLimitActivity.class);
//                    startActivity(intent);
//                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//                } else {


//                SignShareShopDialog signshareshopdialog =
//                        new SignShareShopDialog(getActivity(), mContext, R.style.DialogStyle1,
//                                jiangliID);
//                signshareshopdialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
//                signshareshopdialog.show();


//                DialogUtils.newRedHongBaoDialog(mContext);


//                ToastUtil.showDialog(new NoTixianEduDialog(mContext));

//                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "zero");
//                mContext.startActivity(new Intent(mContext, IndianaListActivity.class));
//                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


//                }
//                ToastUtil.showDialog(new NoTixianEduDialog(mContext));

//                DialogUtils.guideToZhuanqianDialog(mContext);


//                String imageUrl = "https://www.measures.wang/small-iconImages/ad_pic/signlingyuangou_bg.png!280";
//                String shareTitle = "????????????????????????";
//                String wxMiniPath = "pages/sign/sign";
//                WXminiAPPShareUtil.shareToWXminiAPP(mContext, imageUrl, shareTitle, wxMiniPath, false);


//                WXMiniProgramObject miniProgram = new WXMiniProgramObject();
//                miniProgram.webpageUrl = "http://www.qq.com";
//                miniProgram.userName = "gh_01f3abb24f0b";
////                miniProgram.path = "pages/play/index?cid=fvue88y1fsnk4w2&ptag=vicyao&seek=3219";
//
//                miniProgram.path = "pages/sign/sign";
//
//                WXMediaMessage msg = new WXMediaMessage(miniProgram);
//                msg.title = "???????????????Title";
//                msg.description = "???????????????????????????";
//                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon_wechat);
//                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
//                bmp.recycle();
//                msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
//
//                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = BasicActivity.buildTransaction("webpage");
//                req.message = msg;
//                req.scene = SendMessageToWX.Req.WXSceneSession;
//                wXapi.sendReq(req);


//                BasicActivity.wXapi = WXAPIFactory.createWXAPI(mContext, WxPayUtil.APP_ID);
//                BasicActivity.wXapi.registerApp(WxPayUtil.APP_ID);
//                String imageUrl = "https://www.measures.wang/small-iconImages/ad_pic/bg_mad_monday.png";
//
//                Picasso.with(mContext)
//                        .load(imageUrl)
//                        .resize(200, 300)
//                        .centerCrop()
//                        .into(new Target() {
//                            @Override
//                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//
//                                WXMiniProgramObject miniProgram = new WXMiniProgramObject();
//                                miniProgram.webpageUrl = "http://www.qq.com";
//                                miniProgram.userName = "gh_01f3abb24f0b";
////                miniProgram.path = "pages/play/index?cid=fvue88y1fsnk4w2&ptag=vicyao&seek=3219";
//
//                                miniProgram.path = "pages/sign/sign";
//
//                                WXMediaMessage msg = new WXMediaMessage(miniProgram);
//                                msg.title = "???????????????Title";
//                                msg.description = "???????????????????????????";
////                                      Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon_wechat);
////                                      Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
////                                      bmp.recycle();
//                                msg.thumbData = Util.bmpToByteArray(bitmap, false);
//
//                                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                                req.transaction = BasicActivity.buildTransaction("webpage");
//                                req.message = msg;
//                                req.scene = SendMessageToWX.Req.WXSceneTimeline;
//
//                                BasicActivity.wXapi.sendReq(req);
//
//
//                            }
//
//                            @Override
//                            public void onBitmapFailed(Drawable drawable) {
//
//                            }
//
//                            @Override
//                            public void onPrepareLoad(Drawable drawable) {
//
//                            }
//
//
//                        });


                break;


            case R.id.img_back:
//                getActivity().finish();
//                ((Activity) mContext).overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
//                ToastUtil.showDialog(new EWtaskTishiDialog(mContext, R.style.DialogStyle1));


                onBackPressed();

                break;


            case R.id.ll_tixian:

                // MobclickAgent.onEvent(mContext, "sign_coupous");
                if (!YJApplication.instance.isLoginSucess()) {

                    if (LoginActivity.instances != null) {
                        LoginActivity.instances.finish();
                    }

                    Intent intentd = new Intent(context, LoginActivity.class);
                    intentd.putExtra("login_register", "login");
                    ((FragmentActivity) context).startActivity(intentd);
                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    return;
                }

                if (YCache.getCacheUser(mContext).getReviewers() == 1) {
                    intent = new Intent(mContext, MyCouponsActivity.class);

                } else if (current_date.indexOf("newbie") != -1) {
                    intent = new Intent(mContext, SignDrawalLimitActivity.class);

                } else {
                    intent = new Intent(mContext, WithdrawalLimitActivity.class);

                }

                startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                break;


            case R.id.ll_youhuiquan:

                // MobclickAgent.onEvent(mContext, "sign_coupous");
                if (!YJApplication.instance.isLoginSucess()) {

                    if (LoginActivity.instances != null) {
                        LoginActivity.instances.finish();
                    }

                    Intent intentd = new Intent(context, LoginActivity.class);
                    intentd.putExtra("login_register", "login");
                    ((FragmentActivity) context).startActivity(intentd);
                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    return;
                }
                intent = new Intent(mContext, MyCouponsActivity.class);
                startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                break;
            case R.id.ll_jifen:
                // MobclickAgent.onEvent(mContext, "sign_intergral");
                if (YJApplication.instance.isLoginSucess()) { // ?????????????????????

                    if (!"-1".equals(SharedPreferencesUtil.getStringData(mContext, Pref.JINBI_END_DATE, "-1"))) {
                        Intent intentd = new Intent(context, GoldCoinDetailActivity.class);
                        ((FragmentActivity) context).startActivity(intentd);
                        ((FragmentActivity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    } else {
                        Intent intent1 = new Intent(context, IntergralDetailActivity.class);
                        intent1.putExtra("page", 0);
                        startActivity(intent1);
                        ((FragmentActivity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    }

                } else {
                    if (LoginActivity.instances != null) {
                        LoginActivity.instances.finish();
                    }

                    Intent intentd = new Intent(context, LoginActivity.class);
                    intentd.putExtra("login_register", "login");
                    ((FragmentActivity) context).startActivity(intentd);
                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    return;
                }

                break;

            // case R.id.buqiankaAllCount: // ????????????
            // if (!YJApplication.instance.isLoginSucess()) {
            //
            // if (LoginActivity.instances != null) {
            // LoginActivity.instances.finish();
            // }
            //
            // Intent intentd = new Intent(context, LoginActivity.class);
            // intentd.putExtra("login_register", "login");
            // ((FragmentActivity) context).startActivity(intentd);
            // ((FragmentActivity)
            // mContext).overridePendingTransition(R.anim.slide_left_in,
            // R.anim.slide_match);
            // return;
            // } else {
            //
            // Intent intent2 = new Intent(mContext, SnatchActivity.class);
            //
            // startActivity(intent2);
            // ((FragmentActivity)
            // mContext).overridePendingTransition(R.anim.slide_left_in,
            // R.anim.slide_match);
            //
            // }

            // break;

            case R.id.lv_kaiqifanbei: // ????????????

                if (!YJApplication.instance.isLoginSucess()) {

                    if (LoginActivity.instances != null) {
                        LoginActivity.instances.finish();
                    }

                    Intent intentd = new Intent(context, LoginActivity.class);
                    intentd.putExtra("login_register", "login");
                    ((FragmentActivity) context).startActivity(intentd);
                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    return;
                }

                intent = new Intent(mContext, FriendCommissionActivity.class);
                startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                // mContext.startActivity(new Intent(mContext,
                // ShareDetailsActivity.class));
                // ((FragmentActivity)
                // mContext).overridePendingTransition(R.anim.slide_left_in,
                // R.anim.slide_match);
                // startDoubleBanlance();

                break;

            // case R.id.rl_jingxi:
            // new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
            // "jingxitishi").show();
            //
            // break;

            default:
                break;
        }
    }


    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        signIsShow = false;

        if (mJLlistTimer != null) {
            mJLlistTimer.cancel();
        }


        //
        LogYiFu.e("????????????????????????", "onPause");
        // MobclickAgent.onPageEnd("SignFragment");

    }

    WaitNextTaskDialog completeWaitDialog;


    @Override
    public void onResume() {
        super.onResume();

        try {
            CommonUtils.disableScreenshotsSign(getActivity());
        } catch (ParseException e) {
            e.printStackTrace();
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
                                startActivity(new Intent(mContext, HomePageThreeActivity.class)
                                        .putExtra("buyVipFreeBuy", true)
                                        .putExtra("freeBuyType", 2)
                                        .putExtra("freeOrderPage", baseData.getFreeOrderPage())
                                        .putExtra("freeMoney", baseData.getFreeMoney() + "")
                                );
                                mActivity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                            }

                        }

                        @Override
                        public void onError() {

                        }
                    });
        }


        completeWaitDialog = WaitNextTaskDialog.createDialog(mContext);


        //?????????????????????????????????(????????????)?????????????????????????????????
        final Intent txcjIntent = new Intent(mContext, SignDrawalLimitActivity.class);
        txcjIntent.putExtra("type", 1)
                .putExtra("fromSign", true)
                .putExtra("isVirtual", true);
        if (YJApplication.instance.isLoginSucess()) {
            HashMap<String, String> map = new HashMap<>();
            YConn.httpPost(context, YUrl.QUERY_TIQIAN_TXCJ, map, new HttpListener<Choujiang20Data>() {
                @Override
                public void onSuccess(Choujiang20Data result) {
                    if ("1".equals(result.getStatus())) {
                        choujiang20Data = result;

                        if (result.getData().getIs_finish() == 1) {//???????????????????????????????????????
                            if (null != SignDrawalLimitActivity.instance) {
                                SignDrawalLimitActivity.instance.finish();
                            }
                            startActivity(txcjIntent);
                            ((FragmentActivity) mContext).overridePendingTransition(
                                    R.anim.slide_left_in, R.anim.slide_match);
                            mActivity.finish();
                        } else {
                            if (SignListAdapter.doSignGo) {
                                SignListAdapter.doSignGo = false;
                                SignCompleteDialogUtil.SignIng(mContext, new SignCompleteDialogUtil.DoSingBackToSignListener() {
                                    @Override
                                    public void signCompleteRefresh() {
                                        setOnResumData();
                                    }
                                });
                            } else {
                                setOnResumData();
                            }
                        }
                    }
                }

                @Override
                public void onError() {

                }
            });

        } else {
            HashMap<String, String> map = new HashMap<>();
            YConn.httpPost(context, YUrl.QUERY_TIQIAN_TXCJ_EXIST, map, new HttpListener<BaseData>() {
                @Override
                public void onSuccess(BaseData result) {
                    if (result.getIsExist() == 1) {
                        if (null != SignDrawalLimitActivity.instance) {
                            SignDrawalLimitActivity.instance.finish();
                        }
                        startActivity(txcjIntent);
                        ((FragmentActivity) mContext).overridePendingTransition(
                                R.anim.slide_left_in, R.anim.slide_match);
                        mActivity.finish();
                    } else {
                        setOnResumData();
                    }
                }

                @Override
                public void onError() {
                }
            });
        }




    }

    private void setOnResumData() {

        clock_in_start_date = 0;


        Calendar cale = Calendar.getInstance();
        reQuestYear = cale.get(Calendar.YEAR);
        reQuestMon = cale.get(Calendar.MONTH) + 1;

        CalendarView.requestEd = false;
        signDateList = new ArrayList<>();

        if (isTastComplete) {
            ToastUtil.showDialog(completeWaitDialog);
        }

//        if (isCrazyMon || pagerShow == 2) {
//            initYiDouAwardsList();
//        }


        if (ShareGetTXdialog.wxClick) {
            try {
                ShareGetTXdialog.submitShareTXCountRecord();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //?????????
//        if (YJApplication.instance.isLoginSucess()) {
//            showSignHint(h5Moneny);
//        }


        if (YJApplication.instance.isLoginSucess()) {


            PinTuanDuoBaoUtil.getDuobaoH5(mContext);


            sharemeiyichuandaback = SharedPreferencesUtil.getBooleanData(context, "sharemeiyichuandaback", false);
            if (sharemeiyichuandaback) {
                final String SHARENUMKEY = SignListAdapter.signIndex + "share_num"
                        + YCache.getCacheUser(mContext).getUser_id();
                String ss = "";
                switch (SignListAdapter.jiangliID) {
                    case 3:
                        ss = "????????????";
                        ShareMeiyiChuandaCompleteDiaolg dialog3 = new ShareMeiyiChuandaCompleteDiaolg(context, R.style.DialogQuheijiao2,
                                "share_sign_finish", new DecimalFormat("0.##")
                                .format(Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum) + ss, this);
                        dialog3.getWindow().setWindowAnimations(R.style.common_dialog_style);
                        dialog3.show();
                        SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                        SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                        break;
                    case 4:
                        ss = "??????";
                        ShareMeiyiChuandaCompleteDiaolg dialog4 = new ShareMeiyiChuandaCompleteDiaolg(context, R.style.DialogQuheijiao2,
                                "share_sign_finish", new DecimalFormat("0.##")
                                .format(Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum) + ss, this);

                        dialog4.getWindow().setWindowAnimations(R.style.common_dialog_style);

                        dialog4.show();

                        SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                        SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                        break;
                    case 5:
                        ss = "???";
                        ShareMeiyiChuandaCompleteDiaolg dialog5 = new ShareMeiyiChuandaCompleteDiaolg(context, R.style.DialogQuheijiao2,
                                "share_sign_finish", new DecimalFormat("0.##")
                                .format(Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum) + ss, this);

                        dialog5.getWindow().setWindowAnimations(R.style.common_dialog_style);

                        dialog5.show();
                        SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                        SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                        break;
                    case 11:// ??????
                        ss = "?????????";
                        ShareMeiyiChuandaCompleteDiaolg dialo11 = new ShareMeiyiChuandaCompleteDiaolg(context, R.style.DialogQuheijiao2,
                                "share_sign_finish", new DecimalFormat("0.##")
                                .format(Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum) + ss, this);
                        dialo11.getWindow().setWindowAnimations(R.style.common_dialog_style);

                        dialo11.show();
                        SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                        SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                        break;
                    case 8:// ????????????
                        ShareMeiyiChuandaCompleteDiaolg dialog8 = new ShareMeiyiChuandaCompleteDiaolg(context, R.style.DialogQuheijiao2,
                                "share_sign_fanbei_finish", this);
                        dialog8.getWindow().setWindowAnimations(R.style.common_dialog_style);

                        dialog8.show();
                        SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                        SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                        break;
                    case 9:// ????????????
                        ShareMeiyiChuandaCompleteDiaolg dialog9 = new ShareMeiyiChuandaCompleteDiaolg(context, R.style.DialogQuheijiao2,
                                "share_sign_jinbi_finish", this);
                        dialog9.getWindow().setWindowAnimations(R.style.common_dialog_style);

                        dialog9.show();
                        SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                        SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                        break;
                    case 10:// ????????????
                        ShareMeiyiChuandaCompleteDiaolg dialog10 = new ShareMeiyiChuandaCompleteDiaolg(context, R.style.DialogQuheijiao2,
                                "share_sign_jinquan_finish", this);
                        dialog10.getWindow().setWindowAnimations(R.style.common_dialog_style);

                        dialog10.show();
                        SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                        SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                        break;

                    default:
                        break;
                }

            }
        }

        refreshData();
    }


    /**
     * \
     *
     * @date 2017???5???27??? ??????3:36:19 @Description: ????????????????????? @param ???????????? @return void
     * ???????????? @author lifeng @throws
     */
    public void refreshData() {


//        ToastUtil.showShortText(mContext,"??????");
        if (!isXiala) {
            stopTimer();
        }


        if (!YJApplication.instance.isLoginSucess() || !YJApplication.isLogined) {

            // tv_jifenall tv_youhuiquan

            // iv_jifen.setImageResource(R.drawable.iconjifen);
            // iv_jifen.clearAnimation();
            // iv_youhuiquan.setImageResource(R.drawable.icon_youhuijuan);
            // iv_youhuiquan.clearAnimation();

            tv_jifen.setBackgroundResource(R.drawable.jifen_bg);
            tv_youhuiquan.setBackgroundResource(R.drawable.youhuijuan_bg);

        }

        // if (YJApplication.instance.isLoginSucess() ||
        // YJApplication.isLogined) {
        // // ????????????????????????
        // getNewMoney();
        // }


        if (YJApplication.instance.isLoginSucess()) {
            //????????????
            YConn.httpPost(mContext, YUrl.REFRESH_SIGN_LIST, new HashMap<String, String>(), new HttpListener<BaseDataBean>() {
                @Override
                public void onSuccess(BaseDataBean result) {
                    //????????????????????????
                    intitAllTask();
                }

                @Override
                public void onError() {
                    //????????????????????????
                    intitAllTask();
                }
            });


        } else {
            //????????????????????????
            intitAllTask();
        }


    }

    public UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);

    private static AlertDialog dialog;

    public static void customDialog(final int id) {
        Builder builder = new Builder(mContext, R.style.Theme_Transparent);
        // ???????????????????????????
        View view = View.inflate(mContext, R.layout.payback_esc_apply_dialog, null);
        view.setBackgroundResource(R.drawable.redquanbg);
        TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
        Button ok = (Button) view.findViewById(R.id.ok);
        ok.setTextColor(Color.parseColor("#ffffff"));
        ok.setBackgroundResource(R.drawable.bg_red_ok);
        ok.setWidth(DP2SPUtil.dp2px(mContext, 90));
        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setWidth(DP2SPUtil.dp2px(mContext, 90));
        cancel.setTextColor(Color.parseColor("#ff3f8b"));
        cancel.setBackgroundResource(R.drawable.bg_tans_cancel);


        if (id == -1) {//??????????????????
            tv_des.setText("?????????APP????????????");
            cancel.setText("??????");
            cancel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    SharedPreferencesUtil.saveBooleanData(mContext, "change_change", false);
                    ToastUtil.showShortText(mContext, "????????????????????????????????????APP???????????????????????????~");
                    // ???????????????????????????
                    dialog.dismiss();

                }
            });
            ok.setText("??????");
            ok.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    SharedPreferencesUtil.saveBooleanData(mContext, "change_change", true);
                    ToastUtil.showShortText(mContext, "????????????????????????????????????APP???????????????????????????~");
                    dialog.dismiss();
                }
            });


        } else {//??????????????????

            tv_des.setText("?????????????????????????????????????????????");
            cancel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // ???????????????????????????
                    dialog.dismiss();

                }
            });

            ok.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    bangDingPhoneType = id;
//                    Intent intent = new Intent(mContext, BindPhoneActivity.class);
//                    startActivityForResult(intent, 1305);


                    Intent intent = new Intent(mContext, SettingCommonFragmentActivity.class);
                    intent.putExtra("flag", "bindPhoneFragment");
                    intent.putExtra("bool", false);
                    intent.putExtra("isChanal", false); // ????????????true,?????????false
                    // ???????????????????????????true
                    intent.putExtra("phoneNum", "");
                    intent.putExtra("wallet", "account");
                    intent.putExtra("thirdparty", "thirdparty");
                    intent.putExtra("tishiBind", true);

                    mContext.startActivity(intent);

                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                    dialog.dismiss();
                }
            });


        }


        dialog = builder.create();
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1305) {
            if (bangDingPhoneType == 0) {
                // Intent intent = new Intent(mContext, MineLikeActivity.class);
                // ((MainMenuActivity) mContext).startActivityForResult(intent,
                // 13334);
            } else {
                if ("2".equals(signIn_status)) {
                    boolean bool = context.getSharedPreferences("buqianka", Context.MODE_PRIVATE).getBoolean("bool",

                            false);
                    if (bool) {
                        // share(taskId);

                        return;
                    } else {
                        // ToastUtil.showShortText(mContext, "??????????????????");// ??????????????????
                        // ???????????????
                        // new NoBuqiankaDialog(getActivity(),
                        // R.style.DialogStyle1).show(); // ----???????????????APP
                        return;
                    }
                } else if ("1".equals(signIn_status)) {
                    // share(taskId);
                }
            }
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        ButterKnife.bind(this, rootView);
//        return rootView;
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }

    @Override
    public void clickNext() {
        zidongCiickNextTask();
    }


    @Override
    public void wxMiniShareSuccess() {
//        ToastUtil.showShortText(mContext,"?????????????????????");
    }


    // ????????????????????????
    public void getJinBi(Context context) {

        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) context, 0) {
            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getTwoFoldnessGold(context);
            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null) {

                    try {

                        String str = result.get("twofoldnessGold");
                        if (!"".equals(str)) {

                            SharedPreferencesUtil.saveStringData(context, Pref.JINBI_END_DATE, result.get("end_date"));
                            SharedPreferencesUtil.saveStringData(context, Pref.JINBU_ID, result.get("id"));

                        } else {
                            SharedPreferencesUtil.saveStringData(context, Pref.JINBI_END_DATE, "-1");
                            SharedPreferencesUtil.saveStringData(context, Pref.JINBU_ID, "");
                        }

                        getJinQuan(mContext);
                    } catch (Exception e2) {

                    }

                }
            }
        }.execute();

    }

    // ????????????????????????
    public void getJinQuan(Context context) {

        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) context, 0) {
            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getCpgold(context);
            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null) {

                    try {

                        if (result.get("is_open").equals("1")) {
                            SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_END_DATE,
                                    result.get("end_date"));
                            SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_IS_OPEN, result.get("is_open"));
                            SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_C_LAST_TIME,
                                    result.get("c_last_time"));
                            SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_C_PRICE, result.get("c_price"));
                            SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_IS_USE, result.get("is_use"));
                            SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_C_ID, result.get("c_id"));
                        } else {
                            SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_END_DATE, "-1");
                            SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_IS_OPEN, "0");
                            SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_C_LAST_TIME, "0");
                            SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_C_PRICE, "0.0");
                            SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_IS_USE, "");
                            SharedPreferencesUtil.saveStringData(context, Pref.JINQUAN_C_ID, "0");
                        }

                        // ?????????????????????
                        if (SharedPreferencesUtil.getStringData(mContext, Pref.JINBI_END_DATE, "-1").equals("-1")) {

                            // tv_jifenall tv_youhuiquan

                            tv_jifen.setBackgroundResource(R.drawable.jifen_bg);
                            // iv_jifen.setImageResource(R.drawable.iconjifen);
                            // iv_jifen.clearAnimation();

                        } else {

                            tv_jifen.setBackgroundResource(R.drawable.jingb_bg);

                            // ??????
                            // iv_jifen.startAnimation(AnimationUtils.loadAnimation(mContext,
                            // R.anim.signanim_cion));
                        }

                        if (SharedPreferencesUtil.getStringData(mContext, Pref.JINQUAN_END_DATE, "-1").equals("-1")) {
                            tv_youhuiquan.setBackgroundResource(R.drawable.youhuijuan_bg);

                            // iv_youhuiquan.setImageResource(R.drawable.icon_youhuijuan);
                            // iv_youhuiquan.clearAnimation();
                        } else {
                            tv_youhuiquan.setBackgroundResource(R.drawable.jingquan_bg);
                            // iv_youhuiquan.clearAnimation();
                            // // ??????
                            // iv_youhuiquan.startAnimation(AnimationUtils.loadAnimation(mContext,
                            // R.anim.signanim_cion));
                        }

                    } catch (Exception e2) {
                        // TODO: handle exception
                    }

                }
            }
        }.execute();

    }

    @Override
    public void timeOut() {
        refreshData();
    }

    // private static class AnimateFirstDisplayListener extends
    // SimpleImageLoadingListener {
    //
    // static final List<String> displayedImages =
    // Collections.synchronizedList(new LinkedList<String>());
    //
    // @Override
    // public void onLoadingComplete(String imageUri, View view, Bitmap
    // loadedImage) {
    // if (loadedImage != null) {
    // ImageView imageView = (ImageView) view;
    // boolean firstDisplay = !displayedImages.contains(imageUri);
    // if (firstDisplay) {
    // FadeInBitmapDisplayer.animate(imageView, 500);
    // displayedImages.add(imageUri);
    // }
    // }
    // }
    // }




    private void stopTimer() {
        if (!isCrazyMon) {
            return;
        }
        if (mTimer2 != null) {
            mTimer2.cancel();
            mTimer2 = null;
        }

        if (task2 != null) {
            task2.cancel();
            task2 = null;
        }

    }


    TimerTask task2;

    public class MyAdapter extends BaseAdapter {
        private List<HashMap<String, String>> mListData;
        private Context context;

        public MyAdapter(Context context, List<HashMap<String, String>> mListData) {
            super();
            this.mListData = mListData;
            this.context = context;
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
                convertView = View.inflate(mContext, R.layout.item_sign_choujiang, null);
                holder.mNameTv = (TextView) convertView.findViewById(R.id.withdrawal_name_tv);
                holder.mAwardsTv = (TextView) convertView.findViewById(R.id.withdrawal_awards_tv);
                holder.tv_danwei = (TextView) convertView.findViewById(R.id.tv_danwei);
                holder.headIv = (ImageView) convertView.findViewById(R.id.withdrawal_head_iv);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position % 2 == 0) {
                // ?????????
                convertView.setAlpha(0.7f);
            } else {
                // ?????????
                convertView.setAlpha(1.0f);
            }
            // SetImageLoader.initImageLoader(mContext, holder.headIv,
            // mListData.get(position % mListData.size()).get("pic").toString(),
            // "");

//            PicassoUtils.initImage(mContext, mListData.get(position % mListData.size()).get("pic").toString(),
//                    holder.headIv);

            GlideUtils.initRoundImage(Glide.with(((Activity) mContext).getApplicationContext()), ((Activity) mContext).getApplicationContext(), mListData.get(position % mListData.size()).get("pic").toString(),
                    holder.headIv);

            // xx??????????????????xx???

            if (isCrazyMon) {
                holder.mNameTv.setText(mListData.get(position % mListData.size()).get("nname").toString() + " ??????");
                holder.mAwardsTv.setText(mListData.get(position % mListData.size()).get("num").toString());
                holder.tv_danwei.setText("???????????????");

            } else {
                holder.mNameTv.setText(mListData.get(position % mListData.size()).get("nname").toString() + " ???????????????");
                holder.mAwardsTv.setText(mListData.get(position % mListData.size()).get("num").toString());
                holder.tv_danwei.setText("???");
            }

//
//            else {
//                holder.mNameTv.setText(mListData.get(position % mListData.size()).get("nname").toString() + "??????");
//
//                // String textAwardD = "????????????" + to_ms_count + "???";
//                String textAwardD = "????????????" + mListData.get(position % mListData.size()).get("num").toString();
//                SpannableString ssTextAwardD = new SpannableString(textAwardD);
//                ssTextAwardD.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), 0, 4,
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                holder.mAwardsTv.setText(ssTextAwardD);
//
//                holder.tv_danwei.setText("???");
//
//            }

            return convertView;
        }

    }

    public class ViewHolder {
        TextView mNameTv, tv, mAwardsTv, tv_danwei;
        ImageView headIv;
    }

    /**
     * ????????????????????? ??????????????????
     */
    private void addToYiDouList() {
        HashMap<String, String> map2 = new HashMap<String, String>();
        map2.put("nname", StringUtils.getVirtualName() + "***" + StringUtils.getVirtualName());
        // map2.put("p_name", "????????????????????????");
        map2.put("pic", "defaultcommentimage/" + StringUtils.getDefaultImg());
        map2.put("num", (int) (Math.random() * (1000 - 100) + 10) + "");// 100-999
        mListData2.add(map2);
    }


    @Override
    public void onDestroy() {
//        if (UIutil.isOnMainThread()) {
//            Glide.with(mContext).pauseRequests();
//        }

//        if (null != mTimer2) {
//            mTimer2.cancel();
//        }


        super.onDestroy();


    }

    private View show_fans_ll;
    private LinearLayout rl_jiangli_list;

    private TimerTask task3;

    private void initFans() {
        if (show_fans_ll.getVisibility() == View.VISIBLE) {
            return;
        }
        if (task3 != null) {
            task3.cancel();
            task3 = null;
        }
        // if(task!=null||task2!=null){
        // return;
        // }
        //
        // task = new TimerTask(){
        // public void run() {
        // if(task!=null){
        // task.cancel();
        // }
        // if(task2!=null){
        // task2.cancel();
        // }
        // Message message = new Message();
        // message.what = 0;
        // mHandler.sendMessage(message);
        // }
        // };
        // Timer timer = new Timer();
        // timer.schedule(task,5000, 60*60*1000);
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                // if (YJApplication.instance.isLoginSucess() ||
                // YJApplication.isLogined) {
                // getShowData();
                // } else {
                getVirtualShareAwardsData();// ???????????????????????? ????????????????????????
                // }
            }
        }, 2000);

    }

    private String picHead;
    private String textName;
    private SpannableString ssTextName;

    private void showView() {
        if (show_fans_ll.getVisibility() == View.GONE && NetworkUtils.haveNetworkConnection(mContext)) {
            show_fans_ll.setAlpha(0f);
            // SetImageLoader.initImageLoader(mContext, headIv, picHead,"");
            nameTv.setText(ssTextName);
            show_fans_ll.setVisibility(View.VISIBLE);
            show_fans_ll.animate().alpha(1f).setDuration(500).setListener(null);
        }
    }

    private void hideView() {
        if (show_fans_ll.getVisibility() == View.VISIBLE) {
            show_fans_ll.animate().alpha(0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    show_fans_ll.setVisibility(View.GONE);
                }
            });
        }
    }

    Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            if (msg.what == 0) {// ??????1???????????????????????? ?????????
                // TODO ???????????? ????????????
                // if(YJApplication.instance.isLoginSucess()||
                // YJApplication.isLogined){
                // getShowData();
                // }else{
                // getVirtualShareAwardsData();//???????????????????????? ????????????????????????
                // }
            } else if (msg.what == 1) {// View?????????
                hideView();
            }
        }

    };

    private void getShowData() {
        new SAsyncTask<Void, Void, List<String>>((FragmentActivity) mContext, 0) {

            @Override
            protected List<String> doInBackground(FragmentActivity mContext, Void... params) throws Exception {
                // slb/queryBarr ??????????????????
                // ???"??????,??????,type,??????"?????????type 1??? ?????????????????????2??? ???????????????????????????3???????????????????????????????????????
                // //4????????????????????????
                // ??????????????????type=3???4?????????
                return ComModel2.getShareAwardsData(mContext);
            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity mContext, List<String> result, Exception e) {
                super.onPostExecute(mContext, result, e);
                if (e == null && result != null) {// ?????????
                    getShareAwardsData(result);

                } else {// ???????????? ??????????????????
                    getVirtualShareAwardsData();
                }
            }

        }.execute();
    }

    private DecimalFormat pFormate;
    private Runnable runable1 = new Runnable() {
        @Override
        public void run() { //--?????????

            if (position < realDatalist.size()) {
                // int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                // if(hour>=12||hour<2){//??????30???????????????
                // delay = 30*1000;
                // }else if(hour>=2&&hour<12){//??????5??????????????????
                // delay = 5*60*1000;
                // }
                String str[] = realDatalist.get(position).split(",");
                picHead = str[0];
                // SetImageLoader.initImageLoader(mContext, headIv, picHead,
                // "");
                PicassoUtils.initImage(mContext, picHead, headIv);
                String type = str.length > 2 ? str[2] : "2";// ?????????????????????????????????
                String fans = "";
                if ("1".equals(type)) {
                    fans = "??????????????????";
                    show_fans_ll.setClickable(false);
                } else if ("3".equals(type)) {
                    // ??????????????????type=3???4?????????
                    String award = str.length > 3 ? str[3]
                            : StringUtils.getVirtualIntegerAwards() + StringUtils.getVirtualDecimalAwards();// ???????????????????????????????????????,??????????????????????????????
                    pFormate = new DecimalFormat("#0.00");
                    fans = "????????????????????????" + pFormate.format(Double.parseDouble(award)) + "???";
                    show_fans_ll.setClickable(true);
                } else if ("2".equals(type)) {
                    fans = "????????????????????????";
                    show_fans_ll.setClickable(true);
                } else if ("4".equals(type)) {// ????????????????????????
                    // ??????????????????type=3???4?????????
                    String award = str.length > 3 ? str[3]
                            : StringUtils.getVirtualIntegerAwards() + StringUtils.getVirtualDecimalAwards();// ???????????????????????????????????????,??????????????????????????????
                    pFormate = new DecimalFormat("#0.00");
                    fans = "??????????????????" + pFormate.format(Double.parseDouble(award)) + "???";
                    show_fans_ll.setClickable(true);
                }
                String nickName = "";
                if (str.length > 1) {
                    nickName = str[1];
                    if (nickName.length() > 7) {
                        nickName = nickName.substring(0, 7) + "...";
                    }
                }
                // textName = str.length > 1 ? nickName + fans : "" + fans;
                textName = nickName + fans;
                ssTextName = new SpannableString(textName);
                ssTextName.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0,
                        textName.length() - fans.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                if ("3".equals(type)) {// ?????????????????????????????????
                    ssTextName.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), nickName.length() + 8,
                            textName.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else if ("4".equals(type)) {// ????????????????????????
                    ssTextName.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), nickName.length() + 6,
                            textName.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                showView();
                task3 = new TimerTask() {
                    public void run() {
                        Message message = Message.obtain();
                        message.what = 1;
                        mHandler.sendMessage(message);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task3, 4000);
                position++;
                mHandler.postDelayed(this, 15 * 1000);
            } else {
                getVirtualShareAwardsData(); // ??????????????????????????????????????????
            }
        }
    };
    private int position = 0;
    private List<String> realDatalist;// ???????????????List??????

    private void getShareAwardsData(List<String> result) {
        realDatalist = result;
        if (runable1 != null) {
            mHandler.removeCallbacks(runable1);//
        }
        mHandler.post(runable1);
    }

    private int shareFlag = 0;
    private Runnable runable2 = new Runnable() {
        @Override
        public void run() {//?????????
            // SetImageLoader.initImageLoader(mContext, headIv,
            // "defaultcommentimage/" + StringUtils.getDefaultImg(), "");
            PicassoUtils.initImage(mContext, "defaultcommentimage/" + StringUtils.getDefaultImg(), headIv);
            // int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            // if (hour >= 12 || hour < 2) {// ??????30???????????????
            // delay = 15 * 1000;
            // } else if (hour >= 2 && hour < 12) {// ??????2??????????????????
            // delay = 1 * 60 * 1000;
            // // delay = 15*1000;
            // }

            delay = 4000;//????????????

            // textName =
            // StringUtils.getVirtualName()+"***"+StringUtils.getVirtualName()+"???????????????????????????";
            // boolean showShareFlag =
            // SharedPreferencesUtil.getBooleanData(mContext,
            // Pref.SHOW_SHARE_AWARDS, true);
            if (shareFlag == 0) {
                // textName = StringUtils.getVirtualName() + "***" +
                // StringUtils.getVirtualName() + "????????????????????????2?????????";
                textName = StringUtils.getVirtualName() + "***" + StringUtils.getVirtualName() + "????????????????????????"
                        + StringUtils.getVirtualIntegerAwards() + StringUtils.getVirtualDecimalAwards() + "???";
                ssTextName = new SpannableString(textName);
                ssTextName.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0, 5,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssTextName.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 13, textName.length() - 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                shareFlag = 1;
            } else if (shareFlag == 1) {
                textName = StringUtils.getVirtualName() + "***" + StringUtils.getVirtualName() + "??????????????????"
                        + StringUtils.getVirtualAwards() + "???????????????";
                ssTextName = new SpannableString(textName);
                ssTextName.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0, 5,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssTextName.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 11, textName.length() - 5,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                shareFlag = 2;
            } else if (shareFlag == 2) {
                // textName = StringUtils.getVirtualName() + "***" +
                // StringUtils.getVirtualName() + "??????????????????"
                // + StringUtils.getVirtualAwards() + "???";

                // 20-60??????????????????
                pFormate = new DecimalFormat("#0.00");
                textName = StringUtils.getVirtualName() + "***" + StringUtils.getVirtualName() + "??????????????????"
                        + pFormate.format(StringUtils.getVirtualDoubleEduAwards()) + "???";
                ssTextName = new SpannableString(textName);
                ssTextName.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0, 5,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssTextName.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 11, textName.length() - 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                shareFlag = 0;
            } else {
                shareFlag = 0;
            }

            // shareFlag = !shareFlag;
            // SharedPreferencesUtil.saveBooleanData(mContext,
            // Pref.SHOW_SHARE_AWARDS, shareFlag);
            showView();
            task3 = new TimerTask() {
                public void run() {
                    Message message = Message.obtain();
                    message.what = 1;
                    mHandler.sendMessage(message);
                }
            };
            Timer timer = new Timer();
            timer.schedule(task3, 3000);//????????????
            mHandler.postDelayed(this, delay);
        }
    };

    private int delay;

    private void getVirtualShareAwardsData() {
        if (task3 != null) {
            task3.cancel();
            task3 = null;
        }
        if (runable2 != null) {
            mHandler.removeCallbacks(runable2);
        }
        if (runable1 != null) {
            mHandler.removeCallbacks(runable1);
        }
        mHandler.post(runable2);
    }


    //true??????  false?????????
    @Override
    public boolean onBackPressed() {
//        if (YJApplication.instance.isLoginSucess() && !eWaiTaskComplete) {
//            final SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
//            String first_times = SharedPreferencesUtil.getStringData(mContext, "ewairenwutishitishi", "0");
//            // ????????????
//            String date = sdf.format(new Date());
//            if (!first_times.equals(date)) {
//
//
//                //?????????????????????
//                int statusBarHeight2 = -1;
//                try {
//                    Class<?> clazz = Class.forName("com.android.internal.R$dimen");
//                    Object object = clazz.newInstance();
//                    int height = Integer.parseInt(clazz.getField("status_bar_height")
//                            .get(object).toString());
//                    statusBarHeight2 = context.getResources().getDimensionPixelSize(height);
//                } catch (Exception ee) {
//                    ee.printStackTrace();
//                }
//
//                int finalStatusBarHeight = statusBarHeight2;
//
//
////                int[] location = new int[2];
////                SignFragment.rlEwai.getLocationOnScreen(location);
////                int x = location[0];
////                zidongGundongYEW = location[1];
//
//
////
////                if(zidongGundongY == 0){
////                    zidongGundongY = y;
////                }
//
//                int mY;
//
//                if (YJApplication.instance.isLoginSucess()) {
//                    mY = zidongGundongYEW - DP2SPUtil.dp2px(context, 80) - finalStatusBarHeight;
//                } else {
//                    mY = zidongGundongYEW - DP2SPUtil.dp2px(context, 50) - finalStatusBarHeight;
//                }
//                SignFragment.scollView.getRefreshableView().smoothScrollTo(0, mY);
//
//                ToastUtil.showDialog(new EWtaskTishiDialog(mContext, R.style.DialogStyle1, signFragment));
//                SharedPreferencesUtil.saveStringData(mContext, "ewairenwutishitishi", sdf.format(new Date()));
//
//
//                return true;
//            }
//
//        }

        if (null != shareWaitDialog) {
            shareWaitDialog.dismiss();
        }
        getActivity().finish();
        ((Activity) mContext).overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
        return false;

    }


    String mTitle = "";
    private String count = "";
    private String money = "";

    //???????????????????????????????????????
    private void showBIZUOEWIcompleteTishi() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final HashMap<String, String> m = ComModelZ.getNextDayTaskContent();
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            mTitle = " ??????????????????????????????????????????????????????200??????????????????200???????????????????????????????????????";

                            if (m != null && m.size() > 0) {
                                mTitle = m.get("title") + "";

                                try {
                                    mTitle = mTitle.replaceFirst("\\$\\{replace\\}", "" + StringUtils.getCiriCount());
                                    mTitle = mTitle.replaceFirst("\\$\\{replace\\}", "" + StringUtils.getCiriMoney());

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            try {

                                if (YJApplication.instance.isLoginSucess()) {
                                    ToastUtil.showDialog(new BizuoEwaiWanchengTishiDialog(mContext, R.style.DialogStyle1, mTitle));

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    //????????????????????????
    private void initDialogAndTishi() {
        //???????????? ??????1 ???????????? ????????? ?????????????????????2

        YConn.httpPost(mContext, YUrl.QUERY_SIGN_LIST_REFRESH, new HashMap<String, String>(), new HttpListener<BaseData>() {//????????????????????????
            @Override
            public void onSuccess(BaseData mRefreshData) {


                boolean mIsVip = CommonUtils.isVip(mSignCountData.getIsVip(), mSignCountData.getMaxType());
                boolean choujiang20TishiShowEd = SharedPreferencesUtil.getBooleanData(mContext, "choujiang20TishiShowEd", false);
                if (!mIsVip
                        && mSignCountData.getCurrent_date().equals("newbie01")
                        && choujiang20Data.getData().getIs_finish() == 0
                        && !choujiang20TishiShowEd

                ) {
                    SharedPreferencesUtil.saveBooleanData(mContext, "choujiang20TishiShowEd", true);
//                    ToastUtil.showMyToast(mContext, "????????????????????????????????????????????????", 3000);
//                    ToastUtil.showDialog(new SignShuomingDialog(mContext, R.style.DialogStyle1));
                    ToastUtil.showMyToast(mContext, "????????????????????????????????????10??????????????????5-50????????????????????????", 3000);


                    return;
                } else if (isHiddenTXK) {
                    ToastUtil.showMyToast(mContext, "?????????????????????????????????50???", 3000);
                    isHiddenTXK = false;
                    return;
                }


                //?????????(??????????????????)
                //?????????????????? ??????????????????????????????????????????????????????
                String nowDay = DateUtil.getDay();
                int needDay = -1;
                if (!mSignCountData.getCurrent_date().equals("newbie01")
                        && mSignCountData.getClockInToday() != 1
                        && (mSignCountData.getCurrent_date() + "").indexOf("newbie") != -1
                        && !SharedPreferencesUtil.getStringData(mContext, "SING_EVERY_TISHI_DAY_STR", "").equals(nowDay)
                ) {

                    try {
                        needDay = Integer.parseInt((mSignCountData.getCurrent_date() + "").substring(mSignCountData.getCurrent_date().length() - 2, mSignCountData.getCurrent_date().length()));
                        needDay = 15 - (needDay - 1);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (needDay > 0) {
                    LayoutInflater mInflater = LayoutInflater.from(mContext);
                    final Dialog deleteDialog = new Dialog(mContext, R.style.invate_dialog_style);
                    final View view = mInflater.inflate(R.layout.dialog_new_pt_ct, null);
                    ImageView iv_close = view.findViewById(R.id.iv_close);
                    final TextView tv = view.findViewById(R.id.tv);
                    final Button btn_ok = view.findViewById(R.id.btn_ok);


                    tv.setText("??????????????????????????????????????????????????????3?????????66????????????????????????");
                    btn_ok.setText("?????????");

                    iv_close.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            deleteDialog.dismiss();

                        }
                    });
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
                    SharedPreferencesUtil.saveStringData(mContext, "SING_EVERY_TISHI_DAY_STR", nowDay);
                    SharedPreferencesUtil.saveBooleanData(mContext, "choujiang_not_count_back", false);

                    return;
                }

                //??????????????????????????????????????????????????????
                if (mRefreshData.getData() == 1 && (mSignCountData.getCurrent_date() + "").indexOf("newbie") != -1) {

                    if ((mSignCountData.getCurrent_date() + "").equals("newbie02")) {
                        DialogUtils.newUserRefreshSignTaskDialog(mContext);
                    }

//                    ToastUtil.showShortText2("???????????????????????????????????????????????????");
                    SharedPreferencesUtil.saveBooleanData(mContext, "choujiang_not_count_back", false);

                    return;
                }

                //??????2
//                if (SharedPreferencesUtil.getBooleanData(mContext, "choujiang_not_count_back", false)) {
//
//                    if (mSignCountData.getCurrent_status_data() != 1) { //??????2????????????????????????????????????????????????
//                        SharedPreferencesUtil.saveBooleanData(mContext, "choujiang_not_count_back", false);
//                        return;
//                    }
//
//                    LayoutInflater mInflater = LayoutInflater.from(mContext);
//                    final Dialog deleteDialog = new Dialog(mContext, R.style.invate_dialog_style);
//                    final View view = mInflater.inflate(R.layout.dialog_new_pt_ct, null);
//                    ImageView iv_close = view.findViewById(R.id.iv_close);
//                    final TextView tv = view.findViewById(R.id.tv);
//                    final Button btn_ok = view.findViewById(R.id.btn_ok);
//                    tv.setText(Html.fromHtml("?????????????????????<strong><<font color='#FF3F8B'>20???</strong></font>???????????????????????????????????????"));
//
//                    btn_ok.setText("????????????");
//
//                    iv_close.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//                            deleteDialog.dismiss();
//
//                        }
//                    });
//                    btn_ok.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//                            startActivity(new Intent(mContext, KeFuActivity.class));
//                            deleteDialog.dismiss();
//
//                        }
//                    });
//
//                    deleteDialog.setCanceledOnTouchOutside(false);
//                    deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                            LinearLayout.LayoutParams.MATCH_PARENT));
//
//                    ToastUtil.showDialog(deleteDialog);
//                    SharedPreferencesUtil.saveBooleanData(mContext, "choujiang_not_count_back", false);
//
//                }


            }

            @Override
            public void onError() {

            }
        });


    }

    private List<HashMap<String, String>> mListData1 = new ArrayList<>();
    private JLlistAdapter jLlistAdapter;

    private void initLimitAwardsList() {

        if (YJApplication.instance.isLoginSucess()
                && mSignCountData.getCurrent_date().indexOf("newbie") == -1) {
            rl_jiangli_list.setVisibility(View.GONE);
            return;
        }


        String[] taskItemCountList = StringUtils.getSignJLlistItemTaskCount();
        String[] taskItemMoneyList = StringUtils.getSignJLlistItemTaskMoney();

        for (int i = 0; i < taskItemCountList.length; i++) {
            HashMap<String, String> map1 = new HashMap<>();
            map1.put("nname", StringUtils.getVirtualName() + "***" + StringUtils.getVirtualName());
            map1.put("p_name", "????????????" + taskItemCountList[i] + "?????????");
            map1.put("pic", "defaultcommentimage/" + StringUtils.getDefaultImg());
            map1.put("num", "+" + taskItemMoneyList[i] + "???");
            mListData1.add(map1);
        }


        jLlistAdapter = new JLlistAdapter(mListData1);
        listView1.setAdapter(jLlistAdapter);
        if (mJLlistTimer != null) {
            mJLlistTimer.cancel();
        }


        mJLlistTimer = new Timer();

        try {
            mJLlistTimer.schedule(new TimerTask() {
                @Override
                public void run() {

                    if (!CommonUtils.isActivityDestroy(getActivity())) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listView1.smoothScrollBy(2, 0);
                            }
                        });
                    }


                }
            }, 0, 10);
        } catch (Exception e) {
            e.printStackTrace();

        }

        rl_jiangli_list.setVisibility(View.VISIBLE);

    }

    private Timer mJLlistTimer;

//    TimerTask JLlistTask = new TimerTask() {
//
//        @Override
//        public void run() {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    listView1.smoothScrollBy(2, 0);
//                }
//            });
//
//        }
//    };


    public class JLlistAdapter extends BaseAdapter {
        private List<HashMap<String, String>> mListData;

        public JLlistAdapter(List<HashMap<String, String>> mListData) {
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
            JLlistViewHolder holder = null;
            if (convertView == null) {
                holder = new JLlistViewHolder();
                convertView = View.inflate(mContext, R.layout.item_withdrawal_limit_sign, null);
                holder.mNameTv = (TextView) convertView.findViewById(R.id.withdrawal_name_tv);
                holder.tv = (TextView) convertView.findViewById(R.id.withdrawal_exp_tv);
                holder.mAwardsTv = (TextView) convertView.findViewById(R.id.withdrawal_awards_tv);
                holder.headIv = (ImageView) convertView.findViewById(R.id.withdrawal_head_iv);
                convertView.setTag(holder);
            } else {
                holder = (JLlistViewHolder) convertView.getTag();
            }
//			SetImageLoader.initImageLoader(WithdrawalLimitActivity.this, holder.headIv, mListData.get(position%mListData.size()).get("pic").toString(), "");

//			PicassoUtils.initImage(WithdrawalLimitActivity.this,  mListData.get(position%mListData.size()).get("pic").toString(), holder.headIv);

            GlideUtils.initRoundImage(Glide.with(mContext), mContext, mListData.get(position % mListData.size()).get("pic").toString(), holder.headIv);


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

    public class JLlistViewHolder {
        TextView mNameTv, tv, mAwardsTv;
        ImageView headIv;
    }


}