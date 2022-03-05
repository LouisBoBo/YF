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
    MyListView lvDuobao;  //lv_duobao-------------已经改成超级惊喜任务
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


    public static double h5Moneny = 0.0;//用户在H5赚的钱
    public static int zidongGundongYBZ = 0;


    private int bizuoCount = 0; //必做任务的数量
    private int otherCount = 0; //额外任务的数量


    private int bizuoCountComplete = 0; //必做任务的数量---已完成
    private int otherCountComplete = 0; //额外任务的数量---已完成

    private Activity mActivity;


    /**
     * 数据刷新在 refreshData()
     */


    public static String whereMon = "";// 疯狂星期一跳转落地页


    public static String today_ref = "0";//千元红包雨任务是否保留，0 否，1是


    // private MyadapterTastk myadapterTastkTomorrow;
    private SignListAdapter myadapterTastkDay;
    private SignListAdapter myadapterTastkOther;
    private SignListAdapter myadapterTastkSurprise;
    private SignListAdapter myadapterTastkSurpriseTX;
    private SignListAdapter myadapterTastkSurpriseJiZan;
    private SignListAdapter myadapterTastkDuoBao;

    private int roll = 0;//参团任务资格： 0已无资格，1有资格


    private int fighStatus = -1;//参团状态和拼团编号。当roll == 0时为参团状态，1时为拼团编号


    private TextView tv_to_ot, tv_to_ms;
    private TextView tv_spu, tv_spu_tx;


    public LinearLayout sl_sign_fragment, ll_sl, rl_maomi_center_no_money;

    private TextView tv_tixianzhong;
    private TextView tv_ketixian, tv_yitixian, textView8, tv_maomi_center_no_money_tou;
    private TextView tv_jinrizhuan;


    private MyListView lv_mustdu, lv_mustoder, lv_surprise, lv_jizan; // 每日必做----额外任务----明日任务（仅展示）--惊喜任务
    private ListView listView1;

    /**
     * lv_surprise ---已经改成每月惊喜任务
     */
    // public static String aaaa = ""; // 朋友圈分享图片或链接

    public static int bangDingPhoneType;

    private LinearLayout lv_kaiqifanbei, ll_youhuiquan, ll_yugao;

    public static RelativeLayout rl_surprise_tx;

    private RelativeLayout rl_yuefanbei;


    private boolean isCrazyMon = false; // 是否是疯狂星期一
    private boolean haslingyuangou = false;
    private boolean hasFriendTicheng = false;



    private int to_ot_count; // 明日任务中的额外任务个数
    private int to_ms_count; // 明日任务中的必做任务个数


    private int to_suprise_count; // 明日任务中的超级惊喜任务
    private int to_suprise_tx_count; // 明日任务惊喜提现任务个数


    public static SignFragment signFragment;


    public String signIn_status = "";// 签到状态 -1：未知，0：未签到，1：已签到，2：补签

    public static int type;// 签到任务类型 ----填充时和点击时都要使用。


    public static boolean hasTXtask;//有没有惊喜提现任务


    /**
     * 0开店-1邀请好友-2夺宝-3加X件商品到购物车-4浏览普通商品-5浏览商品集合 6购买X件商品-7分享普通商品-8分享搭配购
     * 701分享普通商品--得到余额翻倍 702分享普通商品--得到积分 703分享普通商品--优惠券 801分享搭配商品--得到余额翻倍
     * 802分享搭配商品--得到积分 803搭配搭配商品--得到优惠券
     */
    // 任务类型列表
    private List<HashMap<String, Object>> taskList = new ArrayList<HashMap<String, Object>>();// 任务类型列表（总列表包括未完成和已完成，
    // 未登录时只有未完成）
    // 任务完成时图标列表
    private List<HashMap<String, Object>> taskiconList = new ArrayList<HashMap<String, Object>>();// 任务图标列表
    // 明日任务列表
    private List<HashMap<String, Object>> tomorrowTaskList = new ArrayList<HashMap<String, Object>>();// 明日任务

    // 各大板块任务列表
    private List<HashMap<String, Object>> daytaskList = new ArrayList<HashMap<String, Object>>();// 必做任务 taskClass：1
    private List<HashMap<String, Object>> othertaskList = new ArrayList<HashMap<String, Object>>();// 额外任务 taskClass：2
    private List<HashMap<String, Object>> surpriseTaskList = new ArrayList<HashMap<String, Object>>();// 惊喜任务-----头上是每月惊喜任务 taskClass：3
    private List<HashMap<String, Object>> tiXianSurpriseTaskList = new ArrayList<HashMap<String, Object>>();// 惊喜任务--提现 taskClass：4
    private List<HashMap<String, Object>> jiZanTaskList = new ArrayList<HashMap<String, Object>>();// 集赞任务  taskClass：5  //没有了

    private List<HashMap<String, Object>> duoBaoTaskList = new ArrayList<HashMap<String, Object>>();// 夺宝任务-------头上是超级惊喜任务   taskClass：6

    // 各大版本已完成任务列表
    public static List<HashMap<String, Object>> daytaskListYet = new ArrayList<HashMap<String, Object>>(); // 已完成任务列表(必做)
    private List<HashMap<String, Object>> othertaskListYet = new ArrayList<HashMap<String, Object>>(); // 已完成任务列表(额外)
    private List<HashMap<String, Object>> surpriseTaskListYet = new ArrayList<HashMap<String, Object>>(); // 已完成任务列表(额外)
    private List<HashMap<String, Object>> tiXiansurpriseTaskListYet = new ArrayList<HashMap<String, Object>>(); // 已完成惊喜提现任务
    private List<HashMap<String, Object>> jiZanTaskListYet = new ArrayList<HashMap<String, Object>>(); // 已完成集赞任务任务
    private List<HashMap<String, Object>> duoBaoTaskListYet = new ArrayList<HashMap<String, Object>>(); // 已完成夺宝任务


    private LinearLayout ll_jifen;
    private boolean isXiala;//下拉刷新---假的
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


    private boolean signHintShow = false;//赚钱提示是否弹出过


    //用户的点赞状态
    private String point_status = "-1"; //点赞状态0-默认 1-第一次点赞 2-多次点赞（0时掉接口弹出获得奖励弹窗）

    public static String isGratis = "false";    //isGratis 如果用户点击继续点赞使用的是免费的还是收费的（扣衣豆）isGratis://免费状态 true免费，false收费

    private LinearLayout ll_wallet_count;
    public static boolean mWxInstallFlag;

    private boolean eWaiTaskComplete = false;//额外任务是否已经做完
    private boolean biZuoTaskCoumlete = false;//必做任务是否已经做完
    private String current_date = "";// 新手任务的判断


    private boolean isTastComplete = false; //是否是完成任务后过来的

    //    private int pagerShow = 0;//所有背景页面权重顺序： - 1默认，1疯狂星期一，2超级分享日(提成)，3购买余额奖励翻倍（每月惊喜任务），4超级0元购，5千元红包雨
    private boolean hasMeiyuejingxi;
    private boolean hasQianuyuanhongbao;
    public static int whetherTask = 1; // 是否可以做任务 ，1 可以做，其他不能做（会员相关）
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

        //查询会员情况
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


        //......模拟的数据↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓..

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

        //........模拟的数据↑↑↑↑↑↑↑↑↑↑↑↑↑↑...........


        //计算当月行数
        int rowCount = 5;
        // 获取当月第一天位于周几
        int week = DateUtils.getFirstDayWeek(reQuestYear, reQuestMon);
        int m_days = DateUtils.getMonthDays(reQuestYear, reQuestMon);  // 当月的天数

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
            case 0: //进来刷新
                Calendar calendar = mCalendarView.getCalendar();
                calendar.set(reQuestYear, reQuestMon - 1, 1);
                mCalendarView.refreshMonth();
                break;
            case -1: //上个月
                mCalendarView.lastMonth();
                break;
            case 1: //下个月
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
            ToastUtil.showMyToast(mContext, "奖励已进入账户，祝您购物愉快。", 3000);
            mActivity.finish();
        }
        mSignCountData = new SignCountData();
        isCrazyMon = SharedPreferencesUtil.getBooleanData(mContext, Pref.ISMADMONDAY, false);//是否是新衣节
        hasFriendTicheng = SharedPreferencesUtil.getBooleanData(mContext, Pref.FRIENDTICHENGPAGE, false);//是否是好友提成
        hasMeiyuejingxi = SharedPreferencesUtil.getBooleanData(mContext, Pref.HASMEIYUEJINGXI, false);//是否有每月惊喜任务
        haslingyuangou = SharedPreferencesUtil.getBooleanData(mContext, Pref.ISHASLINGYUANGOU, false);//是否是0元购
        hasQianuyuanhongbao = SharedPreferencesUtil.getBooleanData(mContext, Pref.HAISQIANYAUNHBONGBAO_SIGN, false);//是有千元包任务

//        DialogUtils.newUserRefreshSignTaskDialog(mActivity);

//        isCrazyMon = false;
//        haslingyuangou = true;

//
//        if (isCrazyMon) {//新衣节
//            pagerShow = 1;
//        } else {
//            if (hasFriendTicheng) {//好友提成
//                pagerShow = 2;
//            } else {
//                if (hasMeiyuejingxi) { //每月惊喜
//                    pagerShow = 3;
//                } else {
//                    if (haslingyuangou) {//0元购
//                        pagerShow = 4;
//                    } else {
//                        if (hasQianuyuanhongbao) { //千元红包
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
//            ToastUtil.showMyToast(mContext, "完成任务后，可再去提现10次！", 3000);
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
        // 惊喜提现任务
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
        // tv_power = (TextView) view.findViewById(R.id.tv_power); // 活力值

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
            // // 是否安装了微信

            if (WXcheckUtil.isWeChatAppInstalled(mContext)) {
                mWxInstallFlag = true;
            } else {
                mWxInstallFlag = false;
            }
        } catch (Exception e) {
        }


        String textAward = "最高奖励50元";
        SpannableString ssTextAward = new SpannableString(textAward);
        ssTextAward.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 4, 6,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_to_more.setText(ssTextAward);



        SignListAdapter.setRefreshListener(new SignListAdapter.RefreshListener() {
            @Override
            public void signRefresh() {
                //刷新赚钱页数据
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

        //获取夺宝号
//        if (YJApplication.instance.isLoginSucess()) {
//            CreateDiaogSignShopEnd();
//
//            //夺宝结果弹窗
//            DialogUtils.IndianaResultDialog(mContext);
//        }

        allId = "";

    }




    @Override
    public void onStart() {
        super.onStart();
        LogYiFu.e("生命周期签到测试", "onStart");

    }

    /**
     * @date 2017年5月27日 下午3:37:39 @Description: 填充所有的任务 @param 设定文件 @return void
     * 返回类型 @author lifeng @throws
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
//            querySignData();// 获取赚钱页面用户账户相关数据
//            querySignListYet();// 获取已完成任务列表;到页数据
            // queryLoginSignListLogined();// 获取任务列表---已登录

        } else {

            querySignListUnLogin();// 获取任务列表(未登录)
            tv_eyu.setText("0");
            tv_jifen.setText("0");
            tv_youhuiquan.setText("0");
            initLimitAwardsList();
            if (!isCrazyMon) {
                tv_tixianzhong.setText("提现中0.00");
            }
            tv_ketixian.setText(Html.fromHtml("可提现 <b>0.00</b>"));


        }

        // adapter = new MyAdapter(getActivity(), taskList, motaskList,
        // taskListAlready, bool);
        // gv_qiandao.setAdapter(adapter);
        // adapter.notifyDataSetChanged();

        // gv_qiandao.setBackgroundResource(R.drawable.qiandaobg3);
    }

    // 获取已登录任务列表（包括完成和未完成）
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

                    // 取出疯狂星期一跳转的落地页
                    whereMon = result.get("monday_info").get(0).get("value") + "";

                    long endTime = System.currentTimeMillis();
                    // Log.e("签到界面获取时间", (endTime - statrtTime) +
                    // "---signIn2_0/siLogTaskList");

                    if (result.size() != 0) {

                        /**
                         * daytaskListYet.addAll(result.get("daytaskListYet"));
                         * // 每日必做任务已完成列表添加完毕
                         * othertaskListYet.addAll(result.get("othertaskListYet"
                         * ));// 每日额外任务已完成列表添加完毕
                         */

                        // 此处返回了4个列表 必做-额外-对照用的类型列表-明日列表（包括必做和额外---一起展示）

                        taskList.clear();
                        daytaskList.clear();
                        othertaskList.clear();
                        tomorrowTaskList.clear();
                        surpriseTaskList.clear();
                        tiXianSurpriseTaskList.clear();
                        jiZanTaskList.clear();
                        taskiconList.clear();
                        duoBaoTaskList.clear();

                        taskList.addAll(result.get("taskList")); // 任务类型列表填充完毕
                        daytaskList.addAll(result.get("daytaskList")); // 必做任务列表填充完毕
                        othertaskList.addAll(result.get("othertaskList")); // 额外任务列表填充完毕
                        tomorrowTaskList.addAll(result.get("tomorrowTaskList")); // 明日任务列表填充完毕
                        surpriseTaskList.addAll(result.get("surpriseTaskList")); // 惊喜任务列表任务列表填充完毕
                        tiXianSurpriseTaskList.addAll(result.get("txsurprisetasklist")); // 惊喜任务列表任务列表填充完毕---提现
                        jiZanTaskList.addAll(result.get("jizantasklist")); // 集赞任务列表任务列表填充完毕
                        duoBaoTaskList.addAll(result.get("duoBaoTaskList")); // 夺宝任务列表任务列表填充完毕

                        taskiconList.addAll(result.get("taskiconList")); // 任务图标和浏览任务的图标


                        bizuoCount = daytaskList.size();
                        otherCount = othertaskList.size();

                        LogYiFu.e("tomorrowTaskList", taskiconList + "");

                        // 删掉必做全部任务中已完成的任务，经过这个操作后daytaskList中只有未完成的任务

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
                                        daytaskListYet.get(j).put("value", daytaskList.get(i).get("value"));// 把value的值加入到已完成列表的元素中
                                        daytaskListYet.get(j).put("num", daytaskList.get(i).get("num"));// 把num的值加入到已完成列表的元素中
                                        daytaskListYet.get(j).put("icon", daytaskList.get(i).get("icon"));// 把icon的值加入到已完成列表的元素中
                                        daytaskListYet.get(j).put("app_name", daytaskList.get(i).get("app_name"));// 把app_name的值加入到已完成列表的元素中
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
                            // 将两个必做列表合成一个完整的必做列表，完成的放在后面切已经加上字段signStatus ：1
                            daytaskList.addAll(daytaskListYet);

                        }

                        // 删掉额外全部任务中已完成的任务，经过这个操作后daytaskList中只有未完成的任务
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
                                        othertaskListYet.get(j).put("value", othertaskList.get(i).get("value"));// 把value的值加入到已完成列表的元素中
                                        othertaskListYet.get(j).put("num", othertaskList.get(i).get("num"));// 把num的值加入到已完成列表的元素中
                                        othertaskListYet.get(j).put("icon", othertaskList.get(i).get("icon"));// 把icon的值加入到已完成列表的元素中
                                        othertaskListYet.get(j).put("app_name", othertaskList.get(i).get("app_name"));// 把app_name的值加入到已完成列表的元素中
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

                            //daytaskListTemp是已经去掉已完成剩下的列表
                            othertaskList = daytaskListTemp;
                            // 将两个额外列表合成一个完整的必做列表，完成的放在后面切已经加上字段signStatus ：1
                            othertaskList.addAll(othertaskListYet);

                        }
                        LogYiFu.e("othertaskListYet", othertaskList + "");
                        // 删掉惊喜全部任务中已完成的任务，经过这个操作后daytaskList中只有未完成的任务
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
                                        surpriseTaskListYet.get(j).put("value", surpriseTaskList.get(i).get("value"));// 把value的值加入到已完成列表的元素中
                                        surpriseTaskListYet.get(j).put("num", surpriseTaskList.get(i).get("num"));// 把num的值加入到已完成列表的元素中
                                        surpriseTaskListYet.get(j).put("icon", surpriseTaskList.get(i).get("icon"));// 把icon的值加入到已完成列表的元素中
                                        surpriseTaskListYet.get(j).put("app_name",
                                                surpriseTaskList.get(i).get("app_name"));// 把app_name的值加入到已完成列表的元素中

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
                            // 将两个惊喜列表合成一个完整的必做列表，完成的放在后面切已经加上字段signStatus ：1
                            surpriseTaskList.addAll(surpriseTaskListYet);

                        }

                        // 删掉惊喜全部任务中已完成的任务，经过这个操作后daytaskList中只有未完成的任务-------------提现
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
                                                tiXianSurpriseTaskList.get(i).get("value"));// 把value的值加入到已完成列表的元素中
                                        tiXiansurpriseTaskListYet.get(j).put("num",
                                                tiXianSurpriseTaskList.get(i).get("num"));// 把num的值加入到已完成列表的元素中

                                        tiXiansurpriseTaskListYet.get(j).put("icon",
                                                tiXianSurpriseTaskList.get(i).get("icon"));// 把icon的值加入到已完成列表的元素中
                                        tiXiansurpriseTaskListYet.get(j).put("app_name",
                                                tiXianSurpriseTaskList.get(i).get("app_name"));// 把app_name的值加入到已完成列表的元素中

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
                            // 将两个惊喜列表合成一个完整的必做列表，完成的放在后面切已经加上字段signStatus ：1
                            tiXianSurpriseTaskList.addAll(tiXiansurpriseTaskListYet);

                        }

                        // 删掉惊喜全部任务中已完成的任务，经过这个操作后daytaskList中只有未完成的任务-------------提现
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
                                        jiZanTaskListYet.get(j).put("value", jiZanTaskList.get(i).get("value"));// 把value的值加入到已完成列表的元素中
                                        jiZanTaskListYet.get(j).put("num", jiZanTaskList.get(i).get("num"));// 把num的值加入到已完成列表的元素中
                                        jiZanTaskListYet.get(j).put("icon", jiZanTaskList.get(i).get("icon"));// 把icon的值加入到已完成列表的元素中
                                        jiZanTaskListYet.get(j).put("app_name", jiZanTaskList.get(i).get("app_name"));// 把app_name的值加入到已完成列表的元素中

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
                            // 将两个惊喜列表合成一个完整的必做列表，完成的放在后面切已经加上字段signStatus ：1
                            jiZanTaskList.addAll(jiZanTaskListYet);

                        }


                        // 删掉惊喜全部任务中已完成的任务，经过这个操作后daytaskList中只有未完成的任务-------------夺宝------------现在叫超级惊喜任务
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
                                        duoBaoTaskListYet.get(j).put("value", duoBaoTaskList.get(i).get("value"));// 把value的值加入到已完成列表的元素中

                                        String num = duoBaoTaskList.get(i).get("num") + "";

                                        duoBaoTaskListYet.get(j).put("num", num);// 把num的值加入到已完成列表的元素中
                                        duoBaoTaskListYet.get(j).put("icon", duoBaoTaskList.get(i).get("icon"));// 把icon的值加入到已完成列表的元素中
                                        duoBaoTaskListYet.get(j).put("app_name", duoBaoTaskList.get(i).get("app_name"));// 把app_name的值加入到已完成列表的元素中

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
                            // 将两个惊喜列表合成一个完整的必做列表，完成的放在后面切已经加上字段signStatus ：1
                            duoBaoTaskList.addAll(duoBaoTaskListYet);

                        }


                        // 如果有星期一的任务手动加上
                        if (isCrazyMon) {
                            HashMap<String, Object> crazyMon = new HashMap<String, Object>();
                            crazyMon.put("index", "-999");
                            crazyMon.put("t_id", "-999");
                            crazyMon.put("num", "-999");
                            crazyMon.put("task_type", "999");
                            crazyMon.put("value", "-999");
                            crazyMon.put("task_class", "3");
//                            surpriseTaskList.add(crazyMon,0);
                            //疯狂星期一放到超级惊喜任务
                            duoBaoTaskList.add(0, crazyMon);
                        }


                        // 判断额外任务里面有没有购买的任务
                        if (othertaskList.size() > 0) {
                            for (HashMap<String, Object> hashMap : othertaskList) {
                                if (hashMap.get("task_type").equals("6")) {
                                    SignListAdapter.hasGoumai = true;
                                }

                            }
                        }


                        //将每月惊喜任务列表中的惊喜任务挪至超级惊喜任务列表中
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


                        // 去掉每月惊喜任务中---已完成的每月惊喜任务
                        removeMonTaskComplet(daytaskList);
                        removeMonTaskComplet(othertaskList);
                        removeMonTaskComplet(tiXianSurpriseTaskList);
                        removeMonTaskComplet(jiZanTaskList);
                        removeMonTaskComplet(duoBaoTaskList);


                        //如果用户的点赞状态是-1，去掉继续点赞任务
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


                        //如果没有参团资格---其中包括：没有被引导新用户  和 老用户：去掉参团的任务------错的
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

                        //如果当天参团任务没有签，且已参过团或者是老用户被引导的时候，---去掉-----对的

                        //去掉参团任务的条件（三个满足一个即可）： 1未被引导的用户 2：被引导的老用户 3参团任未签到，且参团状态是已参与（第二个参团任务）

                        // 新增：所有用户只能开一次团，新用户注册进来可以参团也可以开团，如未参团，可以直接开团，如已开团，就不能参团了

                        if (duoBaoTaskList.size() > 0) {
                            for (int i = 0; i < duoBaoTaskList.size(); i++) {
                                if (duoBaoTaskList.get(i).get("task_type").equals("17")) {

                                    boolean b1 = false;
                                    boolean b2 = false;
                                    boolean b3 = false;
                                    boolean b4 = false;

                                    b1 = fighStatus == 0;//未被引导

                                    b2 = fighStatus == 3;//被引导的老用户

                                    if (!"1".equals(duoBaoTaskList.get(i).get("signStatus"))) {//签到未完成
                                        if (fighStatus == 1) {
                                            b3 = true;//参团任未签到，且参团状态是已参与（第二个参团任务）
                                        }
                                    }


                                    b4 = SignListAdapter.orderCount != 0 && !"1".equals(duoBaoTaskList.get(i).get("signStatus")); //已开过团就不能参团 ，  去掉参团任务(未签的)

                                    if (b1 || b2 || b3 || b4) {
                                        duoBaoTaskList.remove(i);
                                        i--;
                                    }
                                }
                            }

                        }


                        //签到未签 + orderStatus=1  +   orderCount =2 //代表第三次开团，去掉开团任务---已改为只能开一次团
                        if (SignListAdapter.orderStatus == 1 && (SignListAdapter.orderCount == 2 || SignListAdapter.orderCount == 1)) {
                            if (duoBaoTaskList.size() > 0) {
                                for (int i = 0; i < duoBaoTaskList.size(); i++) {
                                    if (duoBaoTaskList.get(i).get("task_type").equals("18")) {
                                        if (!"1".equals(duoBaoTaskList.get(i).get("signStatus"))) {//签到未完成
                                            duoBaoTaskList.remove(i);
                                            i--;
                                        }
                                    }
                                }

                            }
                        }


                        //千元红包雨的任务去留
                        if ("0".equals(today_ref)) {
                            //去掉千元红包雨任务
                            removeHongBao(daytaskList);
                            removeHongBao(othertaskList);
                            removeHongBao(tiXianSurpriseTaskList);
                            removeHongBao(jiZanTaskList);
                            removeHongBao(duoBaoTaskList);


                        }

                        //如果用户已经购买过商品  去掉"去抽奖的任务"
                        if (SharedPreferencesUtil.getBooleanData(mContext, Pref.IS_AREADY_BUY + YCache.getCacheUser(context).getUser_id(), false)) {
                            //去掉去抽奖
                            removeChouJiang(daytaskList);
                            removeChouJiang(othertaskList);
                            removeChouJiang(tiXianSurpriseTaskList);
                            removeChouJiang(jiZanTaskList);
                            removeChouJiang(duoBaoTaskList);
                        }


                        /**
                         * 任务之间的覆盖并存关系：疯狂星期一覆盖拼团购及每月惊喜，拼团购覆盖每月惊喜和星期一，每月惊喜覆盖购买赢提现和拼团购和星期一
                         */

                        //1如果有疯狂星期一的任务  -----去掉拼团购和每月惊喜任务,及千元红包雨，和千元红包雨,及购买赢提现
                        boolean hasMon = isCrazyMon; // 是否有疯狂星期一任务
                        if (hasMon) {

                            //去掉每月惊喜任务
//                            if (surpriseTaskList.size() > 0) {
//                                surpriseTaskList.clear();
//                            }

//                            if (duoBaoTaskList.size() > 0) {    //惊喜任务中的购买任务就是每月惊喜任务
//                                for (int i = 0; i < duoBaoTaskList.size(); i++) {
//                                    if (duoBaoTaskList.get(i).get("task_type").equals("6")) {
//                                        duoBaoTaskList.remove(i);
//                                        i--;
//                                    }
//                                }
//
//                            }


                            //去掉每月惊喜任务
                            removeMonTask(daytaskList);
                            removeMonTask(othertaskList);
                            removeMonTask(tiXianSurpriseTaskList);
                            removeMonTask(jiZanTaskList);
                            removeMonTask(duoBaoTaskList);


                            //去掉拼团购--开团
                            removeKaiTuan(daytaskList);
                            removeKaiTuan(othertaskList);
                            removeKaiTuan(tiXianSurpriseTaskList);
                            removeKaiTuan(jiZanTaskList);
                            removeKaiTuan(duoBaoTaskList);


                            //去掉红包雨的任务
                            removeHongBao(daytaskList);
                            removeHongBao(othertaskList);
                            removeHongBao(tiXianSurpriseTaskList);
                            removeHongBao(jiZanTaskList);
                            removeHongBao(duoBaoTaskList);


                            //去掉购买赢提现
                            removeGoumaiYingTX(tiXianSurpriseTaskList);


                        }
                        //2拼团购覆盖每月惊喜和星期一
                        boolean kt1 = checkKaituanTask(daytaskList);
                        boolean kt2 = checkKaituanTask(othertaskList);
                        boolean kt3 = checkKaituanTask(tiXianSurpriseTaskList);
                        boolean kt4 = checkKaituanTask(jiZanTaskList);
                        boolean kt5 = checkKaituanTask(duoBaoTaskList);
                        if (kt1 || kt2 || kt3 || kt4 || kt5) {//有开团任务
                            //去掉每月惊喜任务
//                            if (surpriseTaskList.size() > 0) {
//                                surpriseTaskList.clear();
//                            }

//                            if (duoBaoTaskList.size() > 0) {    //惊喜任务中的购买任务就是每月惊喜任务
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


                            //去掉疯狂星期一
                            if (duoBaoTaskList.size() > 0) {
                                for (int i = 0; i < duoBaoTaskList.size(); i++) {
                                    if (duoBaoTaskList.get(i).get("task_type").equals("999")) {
                                        duoBaoTaskList.remove(i);
                                        i--;
                                    }
                                }

                            }


                        }

                        //3每月惊喜覆盖购买赢提现和拼团购和星期一
                        boolean mon1 = checkHasMonthTask(daytaskList);
                        boolean mon2 = checkHasMonthTask(othertaskList);
                        boolean mon3 = checkHasMonthTask(tiXianSurpriseTaskList);
                        boolean mon4 = checkHasMonthTask(jiZanTaskList);
                        boolean mon5 = checkHasMonthTask(duoBaoTaskList);
                        if (mon1 || mon2 || mon3 || mon4 || mon5) {//有每月惊喜任务
                            //去掉购买赢提现-----不再覆盖
//                            removeGoumaiYingTX(tiXianSurpriseTaskList);


                            //去掉拼团购--开团
                            removeKaiTuan(daytaskList);
                            removeKaiTuan(othertaskList);
                            removeKaiTuan(tiXianSurpriseTaskList);
                            removeKaiTuan(jiZanTaskList);
                            removeKaiTuan(duoBaoTaskList);


                            //去掉疯狂星期一
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

                        //去掉不能在APP展示的任务----------相关任务9257
                        removeH5task(daytaskList);
                        removeH5task(othertaskList);
                        removeH5task(tiXianSurpriseTaskList);
                        removeH5task(jiZanTaskList);
                        removeH5task(duoBaoTaskList);

                        //当是新衣节的时候去掉0元购
                        remove0yuanTask(daytaskList);
                        remove0yuanTask(othertaskList);
                        remove0yuanTask(tiXianSurpriseTaskList);
                        remove0yuanTask(jiZanTaskList);
                        remove0yuanTask(duoBaoTaskList);

                        //如果用户没有点击转盘接通客服按钮就去掉参团的任务type = 43 (未登录也不显示)
                        for (int i = 0; i < daytaskList.size(); i++) {
                            if (daytaskList.get(i).get("task_type").equals("43")) {
                                if (!mSignCountData.getYc_task().equals("1") || !YJApplication.instance.isLoginSucess()) {
                                    daytaskList.remove(i);
                                    i--;
                                }
                            }
                        }


                        //不会变化的删除条件
                        //新手任务
                        if (!YJApplication.instance.isLoginSucess() || mSignCountData.getCurrent_date().indexOf("newbie") != -1) {
                            for (int i = 0; i < daytaskList.size(); i++) {
                                if (daytaskList.get(i).get("task_type").equals("46")) {
                                    daytaskList.remove(i);
                                    i--;
                                }
                            }
                        }

                        boolean mIsVip = CommonUtils.isVip(mSignCountData.getIsVip(), mSignCountData.getMaxType());
                        if (mIsVip) {//如果已经是会员就默认抽完了20次
                            mSignCountData.setIs_fast_raffle(1);
                        }


                        //会变化的删除条件 直接删掉
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
//                        //会员
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
//                        //首日首批任务3
//                        if (mSignCountData.getCurrent_date().equals("newbie01")) {//4
//                            for (int i = 0; i < daytaskList.size(); i++) {
//                                if (daytaskList.get(i).get("task_type").equals("40")) {
//                                    daytaskList.remove(i);
//                                    i--;
//                                }
//                            }
//                        }


                        //62、将“邀请2位好友参团”任务位置固定在“完成全部任务，立即提现到微信”上面。
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


                        //将“新完成全部任务，立即提现到微信”放到倒数第二个 type=46
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


                        //将“完成全部任务，立即提现到微信”放到最后1个 type=40
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

                        //找到参团任务的index和参团任务是否已经完成
                        for (int i = 0; i < daytaskList.size(); i++) {
                            if (daytaskList.get(i).get("task_type").equals("43")) {
                                SignListAdapter.task43Index = i;

//                                daytaskList.get(position).get("signStatus") + "").equals("1")
                            } else {
                                SignListAdapter.task43Index = -1;
                            }
                        }


                        myadapterTastkDay = new SignListAdapter(1, taskList, daytaskList, taskiconList, mContext, mActivity);
                        lv_mustdu.setAdapter(myadapterTastkDay); // 必做任务列表


                        myadapterTastkOther = new SignListAdapter(2, taskList, othertaskList, taskiconList, mContext, mActivity);
                        lv_mustoder.setAdapter(myadapterTastkOther); // 额外任务


                        myadapterTastkSurprise = new SignListAdapter(4, taskList, surpriseTaskList, taskiconList, mContext, mActivity);
                        lv_surprise.setAdapter(myadapterTastkSurprise); // 惊喜----已经改成每月惊喜任务


                        myadapterTastkSurpriseTX = new SignListAdapter(5, taskList, tiXianSurpriseTaskList, taskiconList, mContext, mActivity);
                        lv_surprise_tx.setAdapter(myadapterTastkSurpriseTX); // 惊喜---提现


                        myadapterTastkSurpriseJiZan = new SignListAdapter(6, taskList, jiZanTaskList, taskiconList, mContext, mActivity);
                        lv_jizan.setAdapter(myadapterTastkSurpriseJiZan); // 集赞

                        myadapterTastkDuoBao = new SignListAdapter(7, taskList, duoBaoTaskList, taskiconList, mContext, mActivity);
                        lvDuobao.setAdapter(myadapterTastkDuoBao); // 夺宝

                        //邀请参团任务回来的处理
                        if (SharedPreferencesUtil.getBooleanData(mContext, "yaoQingCanTaunGo", false)) {
                            SharedPreferencesUtil.saveBooleanData(mContext, "yaoQingCanTaunGo", false);


                            LayoutInflater mInflater = LayoutInflater.from(mContext);
                            final Dialog deleteDialog = new Dialog(mContext, R.style.invate_dialog_style);
                            final View view = mInflater.inflate(R.layout.dialog_new_pt_ct, null);
                            ImageView iv_close = view.findViewById(R.id.iv_close);
                            final TextView tv = view.findViewById(R.id.tv);
                            final Button btn_ok = view.findViewById(R.id.btn_ok);
                            tv.setText("如您已邀请2位好友参团，请点完成任务。并赠送您1次提现机会。");
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


                        // 拿到明日预告任务个数

                        for (int i = 0; i < tomorrowTaskList.size(); i++) {

                            if (tomorrowTaskList.get(i).get("task_class").equals("1")) { // 必做任务
                                to_ms_count++;

                            }


                            if (tomorrowTaskList.get(i).get("task_class").equals("2")) { // 必做任务
                                to_ot_count++;

                            }


                            if (tomorrowTaskList.get(i).get("task_class").equals("6")) { // 超级惊喜任务个数
                                to_suprise_count++;

                            }

                            if (tomorrowTaskList.get(i).get("task_class").equals("4")) { // 惊喜提现任务
                                to_suprise_tx_count++;

                            }


                        }

                        String textAwardD = "必做任务" + to_ms_count + "个";
                        SpannableString ssTextAwardD = new SpannableString(textAwardD);
                        ssTextAwardD.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 4,
                                textAwardD.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        tv_to_ms.setText(ssTextAwardD);

                        String textAward = "额外任务" + to_ot_count + "个";
                        SpannableString ssTextAward = new SpannableString(textAward);
                        ssTextAward.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 4,
                                textAward.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                        String textAwardjx = "超级惊喜任务" + to_suprise_count + "个";
                        SpannableString ssTextAwardjx = new SpannableString(textAwardjx);
                        ssTextAwardjx.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 6,
                                textAwardjx.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        tv_spu.setText(ssTextAwardjx);


                        String textAwardtx = "惊喜提现任务" + to_suprise_tx_count + "个";
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
                                CenterToast.centerToast(context, "签到表已经更新啦~");
                                // SharedPreferencesUtil.saveBooleanData(mContext,
                                // Pref.ISKAIDIAN_JUMP_LOGIN, true);
                            }

                            // 只有在未登录的情况下载签到那里点击开店领取，跳转过去的登录存true
                            SharedPreferencesUtil.saveBooleanData(mContext, Pref.ISKAIDIAN_JUMP_LOGIN, false);

                            // CenterToast.centerToast(context, "签到表已经更新啦~");
                            SharedPreferencesUtil.saveStringData(context, "IDallId", allId);

                        }

                        //当有资格 且 有参团的任务时自动跳到---拼团商品列表，把拼团编号带过去
                        if (roll == 1) { //有资格
                            if (duoBaoTaskList.size() > 0) {
                                for (int i = 0; i < duoBaoTaskList.size(); i++) {
                                    if (duoBaoTaskList.get(i).get("task_type").equals("17")) {

                                        //有未完成的参团任务
                                        if (!"1".equals(duoBaoTaskList.get(i).get("signStatus"))) {
                                            //如果以前没有被引导过就引导
                                            if (!SharedPreferencesUtil.getBooleanData(mContext, "CANTUANYINDAO" + YCache.getCacheUserSafe(mContext), false)) {

                                                //保存已引导过的标志
                                                SharedPreferencesUtil.saveBooleanData(mContext, "CANTUANYINDAO" + YCache.getCacheUserSafe(mContext), true);

                                                if (SignListAdapter.offered == 2 && SignListAdapter.orderCount == 0) {//未开过团才能参团
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
                        //额外任务是否全部做完

                        LogYiFu.e("otherCount-------------", otherCount + "");
                        if ((otherCount - otherCountComplete) <= 0) {   //额外任务已经全部做完
                            eWaiTaskComplete = true;//只要有1个未做完那么额外任务就没有全部做完

                        }


//                        for (HashMap<String, Object> hashMap : othertaskList) {
//                            if (!"1".equals(hashMap.get("signStatus"))) {
//                                eWaiTaskComplete = false;//只要有1个未做完那么额外任务就没有全部做完
//                                break;
//                            }
//                        }


                        //必做任务是否全部做完
                        if ((bizuoCount - bizuoCountComplete) <= 0) {   //额外任务已经全部做完
                            biZuoTaskCoumlete = true;//只要有1个未做完那么额外任务就没有全部做完

                        }


//                        for (HashMap<String, Object> hashMap : daytaskList) {
//                            if (!"1".equals(hashMap.get("signStatus"))) {
//                                biZuoTaskCoumlete = false;//只要有1个未做完那么必做任务就没有全部做完
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


                        /**提现任务完成后引导到额外任务  -- 该弹窗仅在新手任务时间内出现。
                         *  无完成状态的提现任务：夺宝赢提现、新分享赢提现（分享商品给好友，好友购买后获得返现）、为好友点赞、余额抽提现
                         */
                        if (current_date.contains("newbie")) {//如果包含newbie，就是新手任务newbie01：第一天；依次类推，最多10天


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
                                    if (!"1".equals(hashMap.get("signStatus"))) {//没有完成
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
                                // 当前日期
                                String date = sdf.format(new Date());
                                if (!first_times.equals(date)) {
                                    ToastUtil.showDialog(new TiXianWanchengTishiDialog(mContext, R.style.DialogStyle1, signFragment));
                                    SharedPreferencesUtil.saveStringData(mContext, YCache.getCacheUser(mContext) + "tianwanchengtishi", sdf.format(new Date()));
                                }
                            }

                        }

                        /**
                         * 完成当天全部有已完成状态的必做任务与额外任务后，回到赚钱任务页出弹窗
                         */

                        if (biZuoTaskCoumlete && eWaiTaskComplete) {
                            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String first_times = SharedPreferencesUtil.getStringData(mContext, YCache.getCacheUser(mContext) + "bizuoheewairenwuwancchengtishi", "0");
                            // 当前日期
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

                        //惊喜提现任务置顶
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
//                        //隐藏集赞任务栏----积攒任务已经被干掉
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


                        //如果是已完成任务过来的，自动开始必做和额外中的下一个任务
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
//                    ToastUtil.showMyToast(mContext, "完成赚钱小任务，可再去提现现金哦", 3000);
//                }

                questCalender(0);


            }


        }.execute();

    }


    private void zidongCiickNextTask() {
        completeWaitDialog.dismiss();

        //自动点击下个未完成的任务

//                        lv_mustdu.performItemClick(lv_mustdu.getAdapter().getView(1, null, null), 1, lv_mustdu.getItemIdAtPosition(1));


        //获取必做任务和额外任务里面第一个未完成的任务的序号
        boolean biZuoUnfinished = false;//必做任务是否有未完成的
        int mIndex = -1;

        if (daytaskList.size() > 0) {
            for (int i = 0; i < daytaskList.size(); i++) {
                if (!"1".equals(daytaskList.get(i).get("signStatus"))) {//签到未完成
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
                if (!"1".equals(othertaskList.get(j).get("signStatus"))) {//签到未完成
                    mIndex = j;
                    SignUtil.ZiDongClickNextTask(mContext, mIndex, othertaskList, taskList, taskiconList);
                    isTastComplete = false;
                    break;


                }
            }
        }


        if (mIndex == -1) {//必做任务和额外任务已经全部做完

//            showBIZUOEWIcompleteTishi();

            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String first_times = SharedPreferencesUtil.getStringData(mContext, YCache.getCacheUser(mContext) + "bizuoheewairenwuwancchengtishi", "0");
            // 当前日期
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


    //去掉购买赢提现的任务
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

    //检查是否有开团任务
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


    //检查是否有每月惊喜任务
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


    //去掉开团任务
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


    //去掉不能在APP展示的任务
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


    //去掉0元购任务
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


    //每月惊喜任务
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


    //去掉千元红包雨
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


    //去掉去抽奖
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
            //获取状态栏高度
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

            //将惊喜提现任务自动滚动到顶步
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

    //是否监听过
    private boolean isFlag;


    private boolean isFlagZIDONGGUNDONG;


    private boolean isFlagSignHint;
    private boolean isFeWSignHint;

    /**
     * 获取签到已完成任务列表
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

                    // Log.e("签到界面获取时间", (endTime - statrtTime) +
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
                        daytaskListYet.addAll(result.get("daytaskListYet")); // 每日必做任务已完成列表添加完毕
                        othertaskListYet.addAll(result.get("othertaskListYet"));// 每日额外任务已完成列表添加完毕
                        surpriseTaskListYet.addAll(result.get("surpriseTaskListYet"));// 惊喜任务已完成列表添加完毕
                        tiXiansurpriseTaskListYet.addAll(result.get("txsurprisetasklistyet"));// 惊喜提现任务已完成列表
                        jiZanTaskListYet.addAll(result.get("jizantasklistyet"));// 集赞任务已完成列表
                        duoBaoTaskListYet.addAll(result.get("duoBaoTaskListYet"));// 夺宝任务已完成列表


                        bizuoCountComplete = daytaskListYet.size();
                        otherCountComplete = othertaskListYet.size();


                        LogYiFu.e("othertaskListYet", othertaskListYet + "");

                        queryLoginSignListLogined();// 获取任务列表---已登录

                    }

                }
            }

        }.execute();
    }

    /**
     * 获取签到任务列表(未登录)
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

                    // Log.e("签到界面获取时间", (endTime - statrtTime) +
                    // "---signIn2_0/siTaskList");

                    // LogYiFu.e("NNNN", result + "");

                    if (result.size() != 0) {

                        // 此处返回了4个列表 必做-额外-对照用的类型列表-明日列表（包括必做和额外---一起展示）

                        taskList.clear();
                        daytaskList.clear();
                        othertaskList.clear();
                        tomorrowTaskList.clear();
                        surpriseTaskList.clear();
                        tiXianSurpriseTaskList.clear();
                        jiZanTaskList.clear();
                        duoBaoTaskList.clear();

                        taskiconList.clear();
                        taskiconList.addAll(result.get("taskiconList")); // 任务图标和浏览任务的图标

                        taskList.addAll(result.get("taskList")); // 任务类型列表填充完毕
                        daytaskList.addAll(result.get("daytaskList")); // 必做任务列表填充完毕
                        othertaskList.addAll(result.get("othertaskList")); // 额外任务列表填充完毕

                        tomorrowTaskList.addAll(result.get("tomorrowTaskList")); // 明日任务列表填充完毕
                        surpriseTaskList.addAll(result.get("surpriseTaskList")); // 惊喜任务
                        tiXianSurpriseTaskList.addAll(result.get("txsurprisetasklist")); // 惊喜任务列表任务列表填充完毕
                        jiZanTaskList.addAll(result.get("jizantasklist")); // 集赞任务列表任务列表填充完毕
                        duoBaoTaskList.addAll(result.get("duoBaoTaskList")); // 夺宝任务列表任务列表填充完毕

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


//                        // 疯狂星期一， 惊喜提现任务，每月惊喜任务。只能显示一个 优先级依次
//                        // 只有三种任务只有1个和全都没有时不用判断
//
//                        boolean hasMon = false; // 是否有疯狂星期一任务
//                        boolean hasTX = false;// 是否有 惊喜提现任务
//                        boolean hasJX = false;// 是否有 每月惊喜任务
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
//                        // 判断-----去掉优先级低的任务
//
//                        if (hasMon && hasTX && hasJX) {// 如果3个都有--只保留疯狂星期一
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
//                        if (hasMon && hasTX && !hasJX) {// 有疯狂星期一和提现-----干掉提现
//
//                            tiXianSurpriseTaskList.clear();
//
//                        }
//
//                        if (!hasMon && hasTX && hasJX) {// 有提现和每月惊喜-----干掉每月惊喜
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
//                        if (hasMon && !hasTX && hasJX) {// 有疯狂星期一和每月惊喜 ---
//                            // 干掉每月惊喜
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


                        // 判断额外任务里面有没有购买的任务

                        for (HashMap<String, Object> hashMap : othertaskList) {
                            if (hashMap.get("task_type").equals("6")) {
                                SignListAdapter.hasGoumai = true;
                            }

                        }
                        if (tiXianSurpriseTaskList.size() > 0) {
                            hasTXtask = true;
                        }

                        //未登录的去掉新完成全部任务去提现
                        for (int i = 0; i < daytaskList.size(); i++) {
                            if (daytaskList.get(i).get("task_type").equals("46")) {
                                daytaskList.remove(i);
                                i--;
                            }
                        }


                        //将每月惊喜任务列表中的惊喜任务挪至超级惊喜任务列表中
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


                        //千元红包雨的任务去留
                        if ("0".equals(today_ref)) {
                            //去掉千元红包雨任务
                            removeHongBao(daytaskList);
                            removeHongBao(othertaskList);
                            removeHongBao(tiXianSurpriseTaskList);
                            removeHongBao(jiZanTaskList);
                            removeHongBao(duoBaoTaskList);


                        }


                        /**
                         * 任务之间的覆盖并存关系：疯狂星期一覆盖拼团购及每月惊喜，拼团购覆盖每月惊喜和星期一，每月惊喜覆盖购买赢提现和拼团购和星期一，及购买赢提现
                         */

                        //1如果有疯狂星期一的任务  -----去掉拼团购和每月惊喜任务。和红包雨
                        boolean hasMon = isCrazyMon; // 是否有疯狂星期一任务
                        if (hasMon) {

                            //去掉每月惊喜任务
//                            if (surpriseTaskList.size() > 0) {
//                                surpriseTaskList.clear();
//                            }


//                            if (duoBaoTaskList.size() > 0) {    //惊喜任务中的购买任务就是每月惊喜任务
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


                            //去掉拼团购--开团
                            removeKaiTuan(daytaskList);
                            removeKaiTuan(othertaskList);
                            removeKaiTuan(tiXianSurpriseTaskList);
                            removeKaiTuan(jiZanTaskList);
                            removeKaiTuan(duoBaoTaskList);


                            //去掉红包雨的任务
                            removeHongBao(daytaskList);
                            removeHongBao(othertaskList);
                            removeHongBao(tiXianSurpriseTaskList);
                            removeHongBao(jiZanTaskList);
                            removeHongBao(duoBaoTaskList);


                            //去掉购买赢提现
                            removeGoumaiYingTX(tiXianSurpriseTaskList);


                        }
                        //2拼团购覆盖每月惊喜和星期一
                        boolean kt1 = checkKaituanTask(daytaskList);
                        boolean kt2 = checkKaituanTask(othertaskList);
                        boolean kt3 = checkKaituanTask(tiXianSurpriseTaskList);
                        boolean kt4 = checkKaituanTask(jiZanTaskList);
                        boolean kt5 = checkKaituanTask(duoBaoTaskList);
                        if (kt1 || kt2 || kt3 || kt4 || kt5) {//有开团任务
                            //去掉每月惊喜任务
//                            if (surpriseTaskList.size() > 0) {
//                                surpriseTaskList.clear();
//                            }


//                            if (duoBaoTaskList.size() > 0) {    //惊喜任务中的购买任务就是每月惊喜任务
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


                            //去掉疯狂星期一
                            if (duoBaoTaskList.size() > 0) {
                                for (int i = 0; i < duoBaoTaskList.size(); i++) {
                                    if (duoBaoTaskList.get(i).get("task_type").equals("999")) {
                                        duoBaoTaskList.remove(i);
                                        i--;
                                    }
                                }

                            }


                        }

                        //3每月惊喜覆盖购买赢提现和拼团购和星期一

//                        boolean hasMonthTask = false;
//                        if (duoBaoTaskList.size() > 0) {    //惊喜任务中的购买任务就是每月惊喜任务
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
                        if (mon1 || mon2 || mon3 || mon4 || mon5) {//有每月惊喜任务


                            //去掉购买赢提现
//                            removeGoumaiYingTX(tiXianSurpriseTaskList);


                            //去掉拼团购--开团
                            removeKaiTuan(daytaskList);
                            removeKaiTuan(othertaskList);
                            removeKaiTuan(tiXianSurpriseTaskList);
                            removeKaiTuan(jiZanTaskList);
                            removeKaiTuan(duoBaoTaskList);


                            //去掉疯狂星期一
                            if (duoBaoTaskList.size() > 0) {
                                for (int i = 0; i < duoBaoTaskList.size(); i++) {
                                    if (duoBaoTaskList.get(i).get("task_type").equals("999")) {
                                        duoBaoTaskList.remove(i);
                                        i--;
                                    }
                                }

                            }

                        }


                        //将“完成全部任务，立即提现到微信”放到最后1个 type=40
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


                        //去掉不能在APP展示的任务--相关任务---9257
                        removeH5task(daytaskList);
                        removeH5task(othertaskList);
                        removeH5task(tiXianSurpriseTaskList);
                        removeH5task(jiZanTaskList);
                        removeH5task(duoBaoTaskList);


                        //当是新衣节的时候去掉0元购
                        remove0yuanTask(daytaskList);
                        remove0yuanTask(othertaskList);
                        remove0yuanTask(tiXianSurpriseTaskList);
                        remove0yuanTask(jiZanTaskList);
                        remove0yuanTask(duoBaoTaskList);


                        myadapterTastkDay = new SignListAdapter(1, taskList, daytaskList, taskiconList, mContext, mActivity);
                        lv_mustdu.setAdapter(myadapterTastkDay); // 必做任务列表


                        myadapterTastkOther = new SignListAdapter(2, taskList, othertaskList, taskiconList, mContext, mActivity);
                        lv_mustoder.setAdapter(myadapterTastkOther); // 额外任务


                        myadapterTastkSurprise = new SignListAdapter(4, taskList, surpriseTaskList, taskiconList, mContext, mActivity);
                        lv_surprise.setAdapter(myadapterTastkSurprise); // 惊喜


                        myadapterTastkSurpriseTX = new SignListAdapter(5, taskList, tiXianSurpriseTaskList, taskiconList, mContext, mActivity);
                        lv_surprise_tx.setAdapter(myadapterTastkSurpriseTX); // 惊喜---提现


                        myadapterTastkSurpriseJiZan = new SignListAdapter(6, taskList, jiZanTaskList, taskiconList, mContext, mActivity);
                        lv_jizan.setAdapter(myadapterTastkSurpriseJiZan); // 集赞


                        myadapterTastkDuoBao = new SignListAdapter(7, taskList, duoBaoTaskList, taskiconList, mContext, mActivity);
                        lvDuobao.setAdapter(myadapterTastkDuoBao); // 夺宝--已改为超级惊喜任务

                        // 拿到明日任务两个个数

                        for (int i = 0; i < tomorrowTaskList.size(); i++) {

                            // 你做任务

                            if (tomorrowTaskList.get(i).get("task_class").equals("1")) { // 必做任务
                                to_ms_count++;

                            }
                            if (tomorrowTaskList.get(i).get("task_class").equals("2")) { // 必做任务
                                to_ot_count++;

                            }


                            if (tomorrowTaskList.get(i).get("task_class").equals("6")) { // 超级惊喜任务个数
                                to_suprise_count++;

                            }

                            if (tomorrowTaskList.get(i).get("task_class").equals("4")) { // 惊喜提现任务
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
//                        //隐藏集赞任务栏
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


                        String textAwardD = "必做任务" + to_ms_count + "个";
                        SpannableString ssTextAwardD = new SpannableString(textAwardD);
                        ssTextAwardD.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 4,
                                textAwardD.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        tv_to_ms.setText(ssTextAwardD);


                        String textAward = "额外任务" + to_ot_count + "个";
                        SpannableString ssTextAward = new SpannableString(textAward);
                        ssTextAward.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 4,
                                textAward.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        tv_to_ot.setText(ssTextAward);


                        String textAwardjx = "超级惊喜任务" + to_suprise_count + "个";
                        SpannableString ssTextAwardjx = new SpannableString(textAwardjx);
                        ssTextAwardjx.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 6,
                                textAwardjx.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        tv_spu.setText(ssTextAwardjx);


                        String textAwardtx = "惊喜提现任务" + to_suprise_tx_count + "个";
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




    // 获取签到页面数据
    private void querySignData() {


        YConn.httpPost(mContext, YUrl.SIGN_DATA, new HashMap<String, String>(), new HttpListener<SignCountData>() {
            @Override
            public void onSuccess(SignCountData signCountData) {


                mSignCountData = signCountData;
                current_date = signCountData.getCurrent_date() + "";
                boolean mIsVip = CommonUtils.isVip(signCountData.getIsVip(), signCountData.getMaxType());

                //当前任务已经全部做完且抽奖次数没有用完就自动跳过去抽奖
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

                if (current_date.indexOf("newbie") == -1 && !mIsVip) { //不能做任务()
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
                // 疯狂星期一剩余抽奖数 测试时在此修改
                SignListAdapter.lotterynumber = Integer.parseInt(signCountData.getLotterynumber() + "");

                //分享集赞任务奖励
//                        fxqd = result.get("fxqd") + "";


                int mTwofoldness = Integer
                        .parseInt(SharedPreferencesUtil.getStringData(context, Pref.TWOFOLDNESS, -1 + ""));
                int mIsOpen = Integer
                        .parseInt(SharedPreferencesUtil.getStringData(context, Pref.IS_OPEN, -1 + ""));
                // 余额------------

                tv_eyu.setText(new DecimalFormat("#0.00").format(Double.parseDouble(yue)));

                tv_jifen.setText(signCountData.getICount() + "");
                tv_youhuiquan.setText(signCountData.getCCount() + "");



                //处理任务说明按钮 和 猫咪底部一排
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
                                Html.fromHtml("提现中 <b>"
                                        + new DecimalFormat("#0.00")
                                        .format(Double.parseDouble(signCountData.getDesing() + ""))
                                        + "</b>"));


                tv_ketixian
                        .setText(
                                Html.fromHtml("可提现 <b>"
                                        + new DecimalFormat("#0.00")
                                        .format(Double.parseDouble(signCountData.getWithdrawal_money() + ""))
                                        + "</b>"));
                tv_yitixian
                        .setText(
                                Html.fromHtml("已提现 <b>"
                                        + new DecimalFormat("#0.00")
                                        .format(Double.parseDouble(signCountData.getSuccMoney() + ""))
                                        + "</b>"));


                //处理猫咪中间部分
//                signCountData.setCurrent_date("111");//测试
                if (YCache.getCacheUser(mContext).getReviewers() == 1
                        && !signCountData.getCurrent_date().equals("newbie01")) {
                    if(null == signCountData.getToday_money2()){
                        signCountData.setToday_money2("1.00");
                    }
                    signCountData.setToday_money(signCountData.getToday_money2());
                }


                // 已赚
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
//                    // 已赚
//                    if (Double.parseDouble(signCountData.getToday_money() + "") == 0) {
//                        tv_jinrizhuan.setText("竟然没有");
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

                // tv_fans_count.setText("分享浏览数：" + bro_count);


//                        tv_browse_count.setText("累计点赞数:" + result.get("point_count"));
//
//                        DecimalFormat df = new DecimalFormat("0.0#");
//                        tv_browse_jiangli.setText("累计获得奖励:" + df.format(Double.parseDouble(result.get("total_rewards") + "")) + "元");
//


                tv_browse_count.setText("累计分享数:" + signCountData.getShareCount() + "");

                DecimalFormat df = new DecimalFormat("0.0#");
                tv_browse_jiangli.setText("累计奖励:" + df.format(Double.parseDouble(signCountData.getShareMoneyCount() + "")) + "元");


//------------------------------------------------积攒任务相关---------------------------------------------------------------------
                //点赞状态-1 0 1 2
//                point_status = signCountData.getPoint_status() + "";
//                if (point_status.equals("0")) {
//                    boolean zidong = SharedPreferencesUtil.getBooleanData(mContext, YCache.getCacheUser(mContext).getUser_id() + "zdongdianzan", false);
//                    if (!zidong) {
//                        //调用点赞接口
//                        SignListAdapter.dianZan(false, false);
//                    }
//                }
//                //用户的免费点赞机会有没有用
//                isGratis = signCountData.getIsGratis() + "";
//
//
//                //popup //是否是H5引导进入APP   0不是  1 是
//                String popup = signCountData.getPopup() + "";
//                if (popup.equals("1") && isGratis.equals("false")) { //后台让弹出免费机会已经用完的弹窗，这里要保证确实是免费的用完了isGratis--false
//                    new JiZanCommonDialog(getActivity(), R.style.DialogStyle1, "jixujizandianji").show();
//
//                }
//                //如果是第二次或者多次引导到APP，如果用户免费点赞次数没有用，帮用户自动点赞----需要重置
//                try {
//                    if (popup.equals("1") && isGratis.equals("true") && Integer.parseInt(point_status) > 0) {
//                        //调用点赞接口
//                        SignListAdapter.dianZan(false, true);
//                    }
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//-------------------------------------------------------------------------------------------------------------------------------------------------
// ------------------------------------------------拼团任务相关---------------------------------------------------------------------------------------


                        /*
                         /signIn2_0/getCount
                        新增返回参数：
                        roll ：//参团资格

                                0已无资格，1有资格
                        --------------------------------------------------------------------------------
                        fighStatus：//

                                -1、已参团新用户二次被引导为2，重置以后变成-1（不能变成0，否则会把参团任务删掉）
                                0、没有被引导新用户，---不能参团 默认值
                                1、新用户参团---已参与
                                2、已参团新用户被引导	 ---不能参团	提示只能参与一次
                                3、老用户（活动时间前注册）	---不能参团  提示 老用户不能参与、

                                注：roll=0时有以上5种情况
                                注：roll = 1 可以参团时为团号，fighStatus是团号） ---  必备条件团号（len>=8）
                                注：2重置之后变成-1、3重置之后变为0  ---无提示

                        ---------------------------------------------------------------------------------
                        orderStatus：//当前已开团状态
                                 0未完成
                                 1已完成
                        -------------------------

                        orderCount ： //开团次数

                                   0：未开过团
                                   1：开了一次团
                                   2：开了两次团

                        -------------------------

                        //参团任务状态
                        offered：


                            offered //状态 0待成团  1已完结 2可参与

                        */


                //参团相关：
                //  roll  0已无资格，1有资格
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


                //开团相关 ：0未完成 1已完成

                try {
                    SignListAdapter.orderStatus = Integer.parseInt(signCountData.getOrderStatus() + "");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                //开团次数
                try {
                    SignListAdapter.orderCount = Integer.parseInt(signCountData.getOrderCount() + "");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


                if (roll == 0) {
                    if (fighStatus == 2) {
                        ToastUtil.showMyToast(mContext, "拼团只能参与一次哦~", 3 * 1000);
                        restPinTuanToast();//重置提示
                    }

                    if (fighStatus == 3) {    //仅仅是在H5端被引导且未开团的老用户才会提示----如已开团  在H5端就会提示

                        ToastUtil.showMyToast(mContext, "超级拼团仅限新用户参与哦~", 3 * 1000);
                        restPinTuanToast();//重置提示
                    }
                }


                //要参的团的状态


                SignListAdapter.offered = 0;

                try {
                    SignListAdapter.offered = Integer.parseInt(signCountData.getOffered() + "");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


//-------------------------------------------------------------------------------------------------------------------------------------------------

                //提示和弹窗
                initDialogAndTishi();
                querySignListYet();// 获取已完成任务列表;到页数据


            }

            @Override
            public void onError() {

            }
        });


    }

    /**
     * 重置拼团提示
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


            case R.id.calendar_last://上个月

                reQuestMon--;
                if (reQuestMon == 0) {
                    reQuestMon = 12;
                    reQuestYear--;
                }

                questCalender(-1);

                break;


            case R.id.calendar_next://下个月

                reQuestMon++;
                if (reQuestMon == 13) {
                    reQuestMon = 1;
                    reQuestYear++;
                }
                questCalender(1);

                break;


            case R.id.iv_sign_explain://签到任务说明;


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
//                                String appId = WxPayUtil.APP_ID; // 填应用AppId
//                                if (StringUtils.isEmpty(appId)) {
//                                    appId = YUrl.APP_ID;
//                                }
//                                IWXAPI api = WXAPIFactory.createWXAPI(mContext, appId);
//
//
////                                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.yuanxianjin30);
//
////初始化 WXImageObject 和 WXMediaMessage 对象
//                                WXImageObject imgObj = new WXImageObject(bmp);
//                                WXMediaMessage msg = new WXMediaMessage();
//                                msg.mediaObject = imgObj;
//
////设置缩略图
//                                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 200, true);
//                                msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
//
////构造一个Req
//                                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                                req.transaction = buildTransaction("img");
//                                req.message = msg;
//                                req.scene = SendMessageToWX.Req.WXSceneTimeline ;
////                req.userOpenId = getOpenId();
////调用api接口，发送数据到微信
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


                //拼团详情
//                Intent intent6 = new Intent(mContext, OneBuyGroupsDetailsActivity.class);//跳转到拼团详情
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
//                String shareTitle = "分享到小程序测试";
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
//                msg.title = "分享小程序Title";
//                msg.description = "分享小程序描述信息";
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
//                                msg.title = "分享小程序Title";
//                                msg.description = "分享小程序描述信息";
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
                if (YJApplication.instance.isLoginSucess()) { // 查看是否有金币

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

            // case R.id.buqiankaAllCount: // 夺宝记录
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

            case R.id.lv_kaiqifanbei: // 查看详情

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
        LogYiFu.e("生命周期签到测试", "onPause");
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


        //没登陆的和可以提前抽奖(提现抽奖)的直接跳到提现抽奖界面
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

                        if (result.getData().getIs_finish() == 1) {//没有抽完过且不是会跳到转盘
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

        //已去掉
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
                        ss = "元优惠券";
                        ShareMeiyiChuandaCompleteDiaolg dialog3 = new ShareMeiyiChuandaCompleteDiaolg(context, R.style.DialogQuheijiao2,
                                "share_sign_finish", new DecimalFormat("0.##")
                                .format(Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum) + ss, this);
                        dialog3.getWindow().setWindowAnimations(R.style.common_dialog_style);
                        dialog3.show();
                        SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                        SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                        break;
                    case 4:
                        ss = "积分";
                        ShareMeiyiChuandaCompleteDiaolg dialog4 = new ShareMeiyiChuandaCompleteDiaolg(context, R.style.DialogQuheijiao2,
                                "share_sign_finish", new DecimalFormat("0.##")
                                .format(Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum) + ss, this);

                        dialog4.getWindow().setWindowAnimations(R.style.common_dialog_style);

                        dialog4.show();

                        SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                        SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                        break;
                    case 5:
                        ss = "元";
                        ShareMeiyiChuandaCompleteDiaolg dialog5 = new ShareMeiyiChuandaCompleteDiaolg(context, R.style.DialogQuheijiao2,
                                "share_sign_finish", new DecimalFormat("0.##")
                                .format(Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum) + ss, this);

                        dialog5.getWindow().setWindowAnimations(R.style.common_dialog_style);

                        dialog5.show();
                        SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                        SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                        break;
                    case 11:// 衣豆
                        ss = "个衣豆";
                        ShareMeiyiChuandaCompleteDiaolg dialo11 = new ShareMeiyiChuandaCompleteDiaolg(context, R.style.DialogQuheijiao2,
                                "share_sign_finish", new DecimalFormat("0.##")
                                .format(Double.parseDouble(SignListAdapter.jiangliValue) * SignListAdapter.doNum) + ss, this);
                        dialo11.getWindow().setWindowAnimations(R.style.common_dialog_style);

                        dialo11.show();
                        SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                        SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                        break;
                    case 8:// 余额翻倍
                        ShareMeiyiChuandaCompleteDiaolg dialog8 = new ShareMeiyiChuandaCompleteDiaolg(context, R.style.DialogQuheijiao2,
                                "share_sign_fanbei_finish", this);
                        dialog8.getWindow().setWindowAnimations(R.style.common_dialog_style);

                        dialog8.show();
                        SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                        SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                        break;
                    case 9:// 升级金币
                        ShareMeiyiChuandaCompleteDiaolg dialog9 = new ShareMeiyiChuandaCompleteDiaolg(context, R.style.DialogQuheijiao2,
                                "share_sign_jinbi_finish", this);
                        dialog9.getWindow().setWindowAnimations(R.style.common_dialog_style);

                        dialog9.show();
                        SharedPreferencesUtil.saveStringData(mContext, SHARENUMKEY, "0");
                        SharedPreferencesUtil.removeData(mContext, SHARENUMKEY);
                        break;
                    case 10:// 升级金券
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
     * @date 2017年5月27日 下午3:36:19 @Description: 刷新签到页数据 @param 设定文件 @return void
     * 返回类型 @author lifeng @throws
     */
    public void refreshData() {


//        ToastUtil.showShortText(mContext,"赚钱");
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
        // // 最新获奖弹窗通知
        // getNewMoney();
        // }


        if (YJApplication.instance.isLoginSucess()) {
            //刷新任务
            YConn.httpPost(mContext, YUrl.REFRESH_SIGN_LIST, new HashMap<String, String>(), new HttpListener<BaseDataBean>() {
                @Override
                public void onSuccess(BaseDataBean result) {
                    //查询填充个个任务
                    intitAllTask();
                }

                @Override
                public void onError() {
                    //查询填充个个任务
                    intitAllTask();
                }
            });


        } else {
            //查询填充个个任务
            intitAllTask();
        }


    }

    public UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE);

    private static AlertDialog dialog;

    public static void customDialog(final int id) {
        Builder builder = new Builder(mContext, R.style.Theme_Transparent);
        // 自定义一个布局文件
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


        if (id == -1) {//切换运行环境
            tv_des.setText("请选择APP运行环境");
            cancel.setText("正式");
            cancel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    SharedPreferencesUtil.saveBooleanData(mContext, "change_change", false);
                    ToastUtil.showShortText(mContext, "已经切到正式环境，请关掉APP清理后台，重新打开~");
                    // 把这个对话框取消掉
                    dialog.dismiss();

                }
            });
            ok.setText("测试");
            ok.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    SharedPreferencesUtil.saveBooleanData(mContext, "change_change", true);
                    ToastUtil.showShortText(mContext, "已经切到测试环境，请关掉APP清理后台，重新打开~");
                    dialog.dismiss();
                }
            });


        } else {//提醒绑定手机

            tv_des.setText("为了您的账户安全，请先绑定手机");
            cancel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 把这个对话框取消掉
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
                    intent.putExtra("isChanal", false); // 渠道包用true,其他用false
                    // 为了测试方便暂时用true
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
                        // ToastUtil.showShortText(mContext, "你没有补签卡");// 弹去收集弹窗
                        // 没有补签卡
                        // new NoBuqiankaDialog(getActivity(),
                        // R.style.DialogStyle1).show(); // ----分享或下载APP
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
//        ToastUtil.showShortText(mContext,"小程序分享成功");
    }


    // 获取金币相关数据
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

    // 获取金券相关数据
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

                        // 金币金券的显示
                        if (SharedPreferencesUtil.getStringData(mContext, Pref.JINBI_END_DATE, "-1").equals("-1")) {

                            // tv_jifenall tv_youhuiquan

                            tv_jifen.setBackgroundResource(R.drawable.jifen_bg);
                            // iv_jifen.setImageResource(R.drawable.iconjifen);
                            // iv_jifen.clearAnimation();

                        } else {

                            tv_jifen.setBackgroundResource(R.drawable.jingb_bg);

                            // 动画
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
                            // // 动画
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
                // 为偶数
                convertView.setAlpha(0.7f);
            } else {
                // 为基数
                convertView.setAlpha(1.0f);
            }
            // SetImageLoader.initImageLoader(mContext, holder.headIv,
            // mListData.get(position % mListData.size()).get("pic").toString(),
            // "");

//            PicassoUtils.initImage(mContext, mListData.get(position % mListData.size()).get("pic").toString(),
//                    holder.headIv);

            GlideUtils.initRoundImage(Glide.with(((Activity) mContext).getApplicationContext()), ((Activity) mContext).getApplicationContext(), mListData.get(position % mListData.size()).get("pic").toString(),
                    holder.headIv);

            // xx获得提现额度xx元

            if (isCrazyMon) {
                holder.mNameTv.setText(mListData.get(position % mListData.size()).get("nname").toString() + " 抽中");
                holder.mAwardsTv.setText(mListData.get(position % mListData.size()).get("num").toString());
                holder.tv_danwei.setText("元提现额度");

            } else {
                holder.mNameTv.setText(mListData.get(position % mListData.size()).get("nname").toString() + " 邀请好友得");
                holder.mAwardsTv.setText(mListData.get(position % mListData.size()).get("num").toString());
                holder.tv_danwei.setText("元");
            }

//
//            else {
//                holder.mNameTv.setText(mListData.get(position % mListData.size()).get("nname").toString() + "获得");
//
//                // String textAwardD = "必做任务" + to_ms_count + "个";
//                String textAwardD = "提现额度" + mListData.get(position % mListData.size()).get("num").toString();
//                SpannableString ssTextAwardD = new SpannableString(textAwardD);
//                ssTextAwardD.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), 0, 4,
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                holder.mAwardsTv.setText(ssTextAwardD);
//
//                holder.tv_danwei.setText("元");
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
     * 添加虚拟数据到 衣豆奖励集合
     */
    private void addToYiDouList() {
        HashMap<String, String> map2 = new HashMap<String, String>();
        map2.put("nname", StringUtils.getVirtualName() + "***" + StringUtils.getVirtualName());
        // map2.put("p_name", "完成订单获得衣豆");
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
                getVirtualShareAwardsData();// 没有登录的情况下 显示虚拟用户数据
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
            if (msg.what == 0) {// 每个1小时请求一次接口 并显示
                // TODO 请求接口 查询数据
                // if(YJApplication.instance.isLoginSucess()||
                // YJApplication.isLogined){
                // getShowData();
                // }else{
                // getVirtualShareAwardsData();//没有登录的情况下 显示虚拟用户数据
                // }
            } else if (msg.what == 1) {// View不显示
                hideView();
            }
        }

    };

    private void getShowData() {
        new SAsyncTask<Void, Void, List<String>>((FragmentActivity) mContext, 0) {

            @Override
            protected List<String> doInBackground(FragmentActivity mContext, Void... params) throws Exception {
                // slb/queryBarr （悬浮弹框）
                // （"头像,昵称,type,金额"）其中type 1为 成为你的粉丝，2为 获得分享额外奖励，3为粉丝通过赚钱任务赚了奖励
                // //4获得提现额度文案
                // 金额暂时只在type=3和4时才有
                return ComModel2.getShareAwardsData(mContext);
            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity mContext, List<String> result, Exception e) {
                super.onPostExecute(mContext, result, e);
                if (e == null && result != null) {// 有数据
                    getShareAwardsData(result);

                } else {// 没有数据 使用虚拟奖励
                    getVirtualShareAwardsData();
                }
            }

        }.execute();
    }

    private DecimalFormat pFormate;
    private Runnable runable1 = new Runnable() {
        @Override
        public void run() { //--真数据

            if (position < realDatalist.size()) {
                // int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                // if(hour>=12||hour<2){//每隔30秒显示一次
                // delay = 30*1000;
                // }else if(hour>=2&&hour<12){//每隔5分钟显示一次
                // delay = 5*60*1000;
                // }
                String str[] = realDatalist.get(position).split(",");
                picHead = str[0];
                // SetImageLoader.initImageLoader(mContext, headIv, picHead,
                // "");
                PicassoUtils.initImage(mContext, picHead, headIv);
                String type = str.length > 2 ? str[2] : "2";// 默认是获得分享额外奖励
                String fans = "";
                if ("1".equals(type)) {
                    fans = "成为你的粉丝";
                    show_fans_ll.setClickable(false);
                } else if ("3".equals(type)) {
                    // 金额暂时只在type=3和4时才有
                    String award = str.length > 3 ? str[3]
                            : StringUtils.getVirtualIntegerAwards() + StringUtils.getVirtualDecimalAwards();// 真实数据赚钱任务获得的奖励,取不到就是用虚拟数据
                    pFormate = new DecimalFormat("#0.00");
                    fans = "通过赚钱任务赚了" + pFormate.format(Double.parseDouble(award)) + "元";
                    show_fans_ll.setClickable(true);
                } else if ("2".equals(type)) {
                    fans = "获得分享额外奖励";
                    show_fans_ll.setClickable(true);
                } else if ("4".equals(type)) {// 获得提现额度文案
                    // 金额暂时只在type=3和4时才有
                    String award = str.length > 3 ? str[3]
                            : StringUtils.getVirtualIntegerAwards() + StringUtils.getVirtualDecimalAwards();// 真实数据赚钱任务获得的奖励,取不到就是用虚拟数据
                    pFormate = new DecimalFormat("#0.00");
                    fans = "获得提现额度" + pFormate.format(Double.parseDouble(award)) + "元";
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
                if ("3".equals(type)) {// 赚钱任务赚了多少的文案
                    ssTextName.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), nickName.length() + 8,
                            textName.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else if ("4".equals(type)) {// 获得提现额度文案
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
                getVirtualShareAwardsData(); // 真实数据结束之后显示虚拟用户
            }
        }
    };
    private int position = 0;
    private List<String> realDatalist;// 真实数据的List集合

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
        public void run() {//假数据
            // SetImageLoader.initImageLoader(mContext, headIv,
            // "defaultcommentimage/" + StringUtils.getDefaultImg(), "");
            PicassoUtils.initImage(mContext, "defaultcommentimage/" + StringUtils.getDefaultImg(), headIv);
            // int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            // if (hour >= 12 || hour < 2) {// 每隔30秒显示一次
            // delay = 15 * 1000;
            // } else if (hour >= 2 && hour < 12) {// 每隔2分钟显示一次
            // delay = 1 * 60 * 1000;
            // // delay = 15*1000;
            // }

            delay = 4000;//间隔时间

            // textName =
            // StringUtils.getVirtualName()+"***"+StringUtils.getVirtualName()+"获得元分享额外奖励";
            // boolean showShareFlag =
            // SharedPreferencesUtil.getBooleanData(mContext,
            // Pref.SHOW_SHARE_AWARDS, true);
            if (shareFlag == 0) {
                // textName = StringUtils.getVirtualName() + "***" +
                // StringUtils.getVirtualName() + "完成签到任务获得2元奖励";
                textName = StringUtils.getVirtualName() + "***" + StringUtils.getVirtualName() + "通过赚钱任务赚了"
                        + StringUtils.getVirtualIntegerAwards() + StringUtils.getVirtualDecimalAwards() + "元";
                ssTextName = new SpannableString(textName);
                ssTextName.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0, 5,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssTextName.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 13, textName.length() - 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                shareFlag = 1;
            } else if (shareFlag == 1) {
                textName = StringUtils.getVirtualName() + "***" + StringUtils.getVirtualName() + "分享美衣获得"
                        + StringUtils.getVirtualAwards() + "元额外奖励";
                ssTextName = new SpannableString(textName);
                ssTextName.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0, 5,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssTextName.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 11, textName.length() - 5,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                shareFlag = 2;
            } else if (shareFlag == 2) {
                // textName = StringUtils.getVirtualName() + "***" +
                // StringUtils.getVirtualName() + "获得提现额度"
                // + StringUtils.getVirtualAwards() + "元";

                // 20-60随机两位小数
                pFormate = new DecimalFormat("#0.00");
                textName = StringUtils.getVirtualName() + "***" + StringUtils.getVirtualName() + "获得提现额度"
                        + pFormate.format(StringUtils.getVirtualDoubleEduAwards()) + "元";
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
            timer.schedule(task3, 3000);//停留时间
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


    //true拦截  false不拦截
    @Override
    public boolean onBackPressed() {
//        if (YJApplication.instance.isLoginSucess() && !eWaiTaskComplete) {
//            final SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
//            String first_times = SharedPreferencesUtil.getStringData(mContext, "ewairenwutishitishi", "0");
//            // 当前日期
//            String date = sdf.format(new Date());
//            if (!first_times.equals(date)) {
//
//
//                //获取状态栏高度
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

    //必做任务和额外任务完成提示
    private void showBIZUOEWIcompleteTishi() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final HashMap<String, String> m = ComModelZ.getNextDayTaskContent();
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            mTitle = " 恭喜你今天完成了全部任务，明天会更新200个任务，共计200元奖励。记得明天继续来哦。";

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

    //提示和弹窗的处理
    private void initDialogAndTishi() {
        //优先级： 弹窗1 ———— 轻提示 —————弹窗2

        YConn.httpPost(mContext, YUrl.QUERY_SIGN_LIST_REFRESH, new HashMap<String, String>(), new HttpListener<BaseData>() {//查询任务刷新情况
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
//                    ToastUtil.showMyToast(mContext, "完成赚钱小任务，可再去提现现金哦", 3000);
//                    ToastUtil.showDialog(new SignShuomingDialog(mContext, R.style.DialogStyle1));
                    ToastUtil.showMyToast(mContext, "完成赚钱小任务可再去提现10次，保底提现5-50元秒到微信零钱！", 3000);


                    return;
                } else if (isHiddenTXK) {
                    ToastUtil.showMyToast(mContext, "完成任务后，可再去提现50次", 3000);
                    isHiddenTXK = false;
                    return;
                }


                //弹窗一(每天只弹一次)
                //不是第一天， 且当天没有打卡，说明是从第二天开始的
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


                    tv.setText("完成今天的全部任务即打卡成功。再打卡3天可领66元可提现。加油！");
                    btn_ok.setText("知道了");

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

                //轻提示（任务刷新了，并且是新手任务）
                if (mRefreshData.getData() == 1 && (mSignCountData.getCurrent_date() + "").indexOf("newbie") != -1) {

                    if ((mSignCountData.getCurrent_date() + "").equals("newbie02")) {
                        DialogUtils.newUserRefreshSignTaskDialog(mContext);
                    }

//                    ToastUtil.showShortText2("解锁新任务啦，完成后可以再去提现！");
                    SharedPreferencesUtil.saveBooleanData(mContext, "choujiang_not_count_back", false);

                    return;
                }

                //弹窗2
//                if (SharedPreferencesUtil.getBooleanData(mContext, "choujiang_not_count_back", false)) {
//
//                    if (mSignCountData.getCurrent_status_data() != 1) { //弹窗2只有在完成了当前的全部任务才会弹
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
//                    tv.setText(Html.fromHtml("惊喜，得到赠送<strong><<font color='#FF3F8B'>20次</strong></font>提现机会，请联系客服领取。"));
//
//                    btn_ok.setText("接通客服");
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
            map1.put("p_name", "累计领取" + taskItemCountList[i] + "个任务");
            map1.put("pic", "defaultcommentimage/" + StringUtils.getDefaultImg());
            map1.put("num", "+" + taskItemMoneyList[i] + "元");
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