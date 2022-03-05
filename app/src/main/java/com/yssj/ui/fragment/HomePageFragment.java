package com.yssj.ui.fragment;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.yssj.Constants;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.ToLoginDialog;
import com.yssj.data.YDBHelper;
import com.yssj.entity.BaseData;
import com.yssj.entity.QueryPhoneInfo;
import com.yssj.entity.UserInfo;
import com.yssj.entity.UserOrderHomePageData;
import com.yssj.entity.VipInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.BuyFreeActivity;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.MainFragment;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.MyLikeActivity;
import com.yssj.ui.activity.classfication.ClassficationActivity;
import com.yssj.ui.activity.classfication.ClassficationSearchActivity;
import com.yssj.ui.activity.infos.StatusInfoActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.main.CrazyShopListActivity;
import com.yssj.ui.activity.setting.SettingCommonFragmentActivity;
import com.yssj.ui.fragment.MyShopFragment.onSearchListener;
import com.yssj.ui.fragment.cardselect.CardDataItem;
import com.yssj.ui.fragment.cardselect.CardFragment;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.DialogUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.YCache;
import com.yssj.wxpay.WxPayUtil;

import org.apache.commons.lang.time.DateFormatUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

//import com.yssj.ui.dialog.EightyDialog;
//import com.yssj.ui.dialog.ThirtyDialog;

public class HomePageFragment extends Fragment implements OnClickListener {
    // 首页
    private boolean tongJiFirst = true;
    private String isWhere = "";
    private Boolean first_the_day;
    public static ArrayList<String> hashSet = new ArrayList<String>();
    public static View cardRootView;// 精选
    // private List<HashMap<String, String>> listTitle;
    // private TextView mTvCommon;// 正价商品按钮
    // private TextView mTvSpecial;// 特卖商品切换按钮
    // private ImageButton mShopCart;// 购物车图标
    // private TextView mShopCartCount;// 购物车数量
    // private RelativeLayout mShaiXuan;// 筛选
    // private RelativeLayout mRLShopCrat;
    // private android.support.v4.app.Fragment mSpecialFragment;// 特卖
    private android.support.v4.app.Fragment mCommonFragment;// 购物
    private List<Fragment> pageLists;
    private FragmentTransaction ft;
    private android.support.v4.app.FragmentManager fm;
    private static onSearchListener searchListener;// 搜索监听
    private String _id;// 购筛选时传的值
    private String TAG = "ShopNewFragment";
    private ToLoginDialog loginDialog;// 去登陆弹窗
    public static int width;// 该frgment总宽度
    private int intShaiXuanX;
    private int intShopCartX;
    private static Context mContext;
    private static int mCrruentIndex = 0;// 0，正价页；1，特卖页
    public static HomePageFragment instance;
    // private RelativeLayout my_shop_rl_show_time;
    // private TextView mTimeNotice;
    public static TextView et_search, tv_zhuanqian, tv_fenlei;
    // private ImageView mNowStart;
    // private long recLen = 24 * 60 * 60 * 1000;
    // Timer timer = new Timer();
    // private long nowTime = 0;// 系统时间
    // private long deadTime = 0;// 过期时间
    // private int mIsOpen = 0;// 是否开启

    public static LinearLayout ll_search;

    public static boolean isShowFQtishiEnd;//是否已经弹出过疯抢提示
    public static boolean isShowFQtishiClose;


    public static boolean isShowLingLiJingEnd;//是否弹出过奖励金提示


    public static boolean homeIsShow;

    private Timer timerJingxuan;
    private int jingxuanTime = Integer.MAX_VALUE;
    public static ImageView iv_xuanfurugou;
    private LinearLayout redShare;
    private ImageView moneyShare, iv_hongbao;
    private YDBHelper dbHelp;

    private RelativeLayout rl_sign;

    boolean mWxInstallFlag = false;

    private boolean hongbaoAnimStart = false;
    private boolean hasJYZL;


    // Pref.JINGXUAN_SCAN_FINISH

    private boolean isJinxuanScanFinish;// 精选推荐200块是否浏览完成


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = View.inflate(getActivity(), R.layout.fragment_shop_new, null);
        mContext = getActivity();
        view.setBackgroundColor(Color.WHITE);
        instance = this;
        et_search = (TextView) view.findViewById(R.id.et_search);
        et_search.setOnClickListener(this);
        tv_zhuanqian = (TextView) view.findViewById(R.id.tv_zhuanqian);
        tv_fenlei = (TextView) view.findViewById(R.id.tv_fenlei);
        tv_fenlei.setOnClickListener(this);
        ll_search = (LinearLayout) view.findViewById(R.id.ll_search);
        ll_search.setBackgroundResource(R.drawable.zhezhao2x);
        iv_xuanfurugou = (ImageView) view.findViewById(R.id.iv_xuanfurugou);
        iv_xuanfurugou.setOnClickListener(this);

        iv_hongbao = (ImageView) view.findViewById(R.id.iv_hongbao);
        iv_hongbao.setOnClickListener(this);

        cardRootView = view.findViewById(R.id.container_jingixuan);
        cardRootView.setVisibility(View.GONE);
        redShare = (LinearLayout) view.findViewById(R.id.red_share_ll);
        moneyShare = (ImageView) view.findViewById(R.id.money_share_iv);
        rl_sign = (RelativeLayout) view.findViewById(R.id.rl_sign);
        rl_sign.setOnClickListener(this);


        if (!GuideActivity.hasSign) {
            rl_sign.setVisibility(View.INVISIBLE);
        }


        if (null != mContext) {
            dbHelp = new YDBHelper(mContext);
        } else {
            dbHelp = new YDBHelper(getActivity());
        }


        try {
            // // 是否安装了微信

            if (WXcheckUtil.isWeChatAppInstalled(mContext)) {
                mWxInstallFlag = true;
            } else {
                mWxInstallFlag = false;
            }
        } catch (Exception e) {
        }


        getHotTag();

        return view;

    }

    AnimatorSet animatorSet = new AnimatorSet();

    private void setHongbaoAnim() {

        if (YJApplication.instance.isLoginSucess()) {


            if(YCache.getCacheUser(mContext).getReviewers() == 1){
                iv_hongbao.setVisibility(View.GONE);
            }else{
//                iv_hongbao.setVisibility(View.VISIBLE);

            }



            HashMap<String, String> pairsMap = new HashMap<>();
            YConn.httpPost(mContext, YUrl.QUERY_VIP_INFO2, pairsMap
                    , new HttpListener<VipInfo>() {
                        @Override
                        public void onSuccess(VipInfo vipInfo) {
                            if (vipInfo.getIsVip() > 0) {
                                iv_hongbao.setImageResource(R.drawable.small_redhongbao_nintymoney_600);
                            }else{
                                iv_hongbao.setImageResource(R.drawable.small_redhongbao_nintymoney_90);
                            }
                            setHongBaoAnim();
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }else{
//            iv_hongbao.setVisibility(View.VISIBLE);
            iv_hongbao.setImageResource(R.drawable.small_redhongbao_nintymoney_90);
            setHongBaoAnim();
        }


    }

    private void setHongBaoAnim() {
        if (hongbaoAnimStart) {
            return;
        }

        ObjectAnimator animatorBigX = ObjectAnimator.ofFloat(iv_hongbao, "scaleX", 1, 1.4f);

        animatorBigX.setDuration(800);

        ObjectAnimator animatorBigY = ObjectAnimator.ofFloat(iv_hongbao, "scaleY", 1, 1.4f);

        animatorBigY.setDuration(800);


        ObjectAnimator animRot = ObjectAnimator.ofFloat(iv_hongbao, "rotation", 0f, -30f, 0f, 30f, 0);
        animRot.setDuration(800);


        ObjectAnimator animatorSmallX = ObjectAnimator.ofFloat(iv_hongbao, "scaleX", 1.4f, 1);
        animatorSmallX.setDuration(800);

        ObjectAnimator animatorSmallY = ObjectAnimator.ofFloat(iv_hongbao, "scaleY", 1.4f, 1);
        animatorSmallY.setDuration(800);

        AnimatorSet.Builder buildBigX = animatorSet.play(animatorBigX);
        buildBigX.with(animatorBigY);//一起放大


        AnimatorSet.Builder buildSmallX = animatorSet.play(animatorSmallX);
        buildSmallX.with(animatorSmallY);//一起缩小


        //放大后旋转
        buildBigX.before(animRot);


        //旋转后缩小
        buildSmallX.after(animRot);

        hongbaoAnimStart = true;
        //循环播放
        handler.postDelayed(runnable, 100);
    }


    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            animatorSet.start();

            handler.postDelayed(this, 2600);
        }
    };


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String jingxuan = SharedPreferencesUtil.getStringData(mContext, Pref.JINGXUAN, "0");
        boolean jingxuanSelcet = SharedPreferencesUtil.getBooleanData(mContext, Pref.JINGXUAN_SELECT, false);
        long jingxuanTime = Long.valueOf(jingxuan);
        // if (cardRootView.getVisibility() == View.VISIBLE) {
        // getFragmentManager().beginTransaction()
        // .add(R.id.container_jingixuan,
        // CardFragment.newInstances("CardFragment", mContext), "CardFragment")
        // .commitAllowingStateLoss();
        // }
        mCrruentIndex = 0;// 默认购物
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        // int[] location = new int[2];
        // mShopCart.getLocationOnScreen(location);
        // intShopCartX = location[0];
        int[] location1 = new int[2];
        // mShaiXuan.getLocationOnScreen(location1);
        intShaiXuanX = location1[0];
        YDBHelper helper = new YDBHelper(getActivity());
        String sql = "select * from sort_info where p_id = 0 and is_show = 1 order by sequence";
        // listTitle = helper.query(sql);
        fm = getChildFragmentManager();
        ft = fm.beginTransaction();
        // mSpecialFragment = ZeroShopFragment.newInstance("tab4",
        // getActivity());
        mCommonFragment = MyShopFragment.newInstances("tab2", getActivity());
        // _id=((MyShopFragment) mCommonFragment).getIdNew();
        LogYiFu.e(TAG, "mCrruentIndex" + mCrruentIndex);
        LogYiFu.e(TAG, "验证" + MainMenuActivity.specialCart);
        // if ("specialCart".equals(MainMenuActivity.specialCart)) {
        // mCrruentIndex = 1;
        // }
        if (mCrruentIndex == 1) {
            // mShaiXuan.setVisibility(View.GONE);
            // getChildFragmentManager().beginTransaction().add(R.id.shop_new_content,
            // mSpecialFragment, "1").commit();
            // my_shop_rl_show_time.setVisibility(View.GONE);
            // mTvSpecial.setTextColor(getResources().getColor(R.color.white));
            // mTvSpecial.setBackgroundResource(R.drawable.title_red_right);
            // mTvCommon.setTextColor(getResources().getColor(R.color.red_new));
            // mTvCommon.setBackgroundResource(R.drawable.title_white_left);

        } else {
            // mShaiXuan.setVisibility(View.VISIBLE);
            getChildFragmentManager().beginTransaction().add(R.id.shop_new_content, mCommonFragment, "0").commit();
            // my_shop_rl_show_time.setVisibility(View.VISIBLE);
            // mTvCommon.setTextColor(getResources().getColor(R.color.white));
            // mTvCommon.setBackgroundResource(R.drawable.title_red_left);
            // mTvSpecial.setTextColor(getResources().getColor(R.color.red_new));
            // mTvSpecial.setBackgroundResource(R.drawable.title_white_right);
        }
        // 获取控价的位置
        Window window = getActivity().getWindow();
        // 倒计时
        // timer.schedule(task, 0, 1000); // timeTask
        // 动画
        // TranslateAnimation ta=T
        // 查询购物车数量
        // if (YJApplication.instance.isLoginSucess()) {
        // queryCartCount();
        // }else{
        // mShopCartCount.setVisibility(View.GONE);
        // }
        // getActivity().getSupportFragmentManager().beginTransaction()
        // .add(R.id.shop_new_content, mCommonFragment,"0").commit();
        // initFrament();
        // initTextView();
        setZhuanIconAnim();// 赚钱图标动画效果
        // 下面的是红包的弹窗
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String systime_now = formatter.format(curDate);
        String time_pass = SharedPreferencesUtil.getStringData(getActivity(), "first_match_time", "-1");
        String is_first = SharedPreferencesUtil.getStringData(getActivity(), "is_first", "-2");

        if (!is_first.equals(systime_now)) {
            SharedPreferencesUtil.saveBooleanData(getActivity(), "first_the_day", true);
        } else {
            SharedPreferencesUtil.saveBooleanData(getActivity(), "first_the_day", false);
        }
        first_the_day = SharedPreferencesUtil.getBooleanData(getActivity(), "first_the_day", false);
        if (first_the_day == true) {
            // if (!YJApplication.instance.isLoginSucess()) {
            // EightyDialog eightydialog = new
            // EightyDialog(HomePageFragment.this.getActivity());
            // eightydialog.show();
            // SharedPreferencesUtil.saveStringData(getActivity(), "is_first",
            // systime_now);
            // SharedPreferencesUtil.saveBooleanData(getActivity(), "appear",
            // true);
            //
            // } else {
            // SharedPreferencesUtil.saveBooleanData(getActivity(), "appear",
            // false);
            // }

        }

        if (YJApplication.instance.isLoginSucess()) {
            // String time_pass =
            // SharedPreferencesUtil.getStringData(getActivity(),
            // "first_match_time", "-1");

            String user_string = SharedPreferencesUtil.getStringData(getActivity(), "user_string", "");
            appear = SharedPreferencesUtil.getBooleanData(getActivity(), "appear", false);

            hashSet.clear();
            if (!user_string.equals("") || user_string != null) {
                String[] split = user_string.split(",");
                for (int i = split.length - 1; i >= 0; i--) {
                    hashSet.add(split[i]);
                }
            }

            if (null == YCache.getCacheUser(mContext)) {

                // 退出登录
                SharedPreferencesUtil.saveBooleanData(mContext, "ISCHUCHIDNEGLU", false); // 标志改为未登录
                YJApplication.instance.setLoginSucess(false);
                YCache.cleanToken(mContext);
                YCache.cleanUserInfo(mContext);
                ComModel.clearLoginFlag(mContext);
                AppManager.getAppManager().finishAllActivityOfEveryDayTask();
                YJApplication.isLogined = false;

                MainFragment fragment = MainMenuActivity.instances.getFragment();

                if (fragment.getChildFragmentManager().findFragmentByTag("1") != null) {
                    fragment.getChildFragmentManager().beginTransaction()
                            .remove(fragment.getChildFragmentManager().findFragmentByTag("1")).commit();
                }

                // Intent intent = new Intent(mContext, LoginActivity.class);
                // intent.putExtra("login_register", "login");
                //
                // ((FragmentActivity) mContext).startActivity(intent);

            } else {
                getMineLike();
                UserInfo userInfo = YCache.getCacheUser(mContext);
                String user_id = userInfo.getUser_id() + "";

                if (!time_pass.equals(systime_now)) {
                    hashSet.clear();
                }

                if (!hashSet.contains(user_id) /* && appear == true */) {
                    // diyongquan();
                    hashSet.add(user_id);
                    SharedPreferencesUtil.saveStringData(getActivity(), "first_match_time", systime_now);

                    StringBuffer stringBuffer = new StringBuffer();

                    for (int i = hashSet.size() - 1; i >= 0; i--) {
                        String integer = hashSet.get(i);

                        if (i != 0) {
                            stringBuffer.append(integer + ",");
                        } else {
                            stringBuffer.append(integer);
                        }

                    }
                    String string_last = stringBuffer.toString();
                    SharedPreferencesUtil.saveStringData(getActivity(), "user_string", string_last);

                }
            }

        }
        // 每天第一次打开APP自动弹出精选推荐---已去掉
        // if (("0".equals(jingxuan) || !df.format(new
        // Date()).equals(df.format(new Date(jingxuanTime))))
        // && !jingxuanSelcet && YJApplication.instance.isLoginSucess()// 登录
        // // 并且设置过喜好
        // && !YCache.getCacheUser(mContext).getHobby().equals("null")
        // && !YCache.getCacheUser(mContext).getHobby().equals("")
        // && null != YCache.getCacheUser(mContext).getHobby()
        // && YCache.getCacheUser(mContext).getHobby().contains("_")) {
        // // cardRootView.setVisibility(View.VISIBLE);
        // getJingxuanData(true, false,false);
        //
        // }


//        if (GuideActivity.hasSign) {
//            showGuideZhuanqianDialog();
//            if (MainMenuActivity.noUserRegist && MainMenuActivity.homeIsSign) {
//                if (!SharedPreferencesUtil.getBooleanData(mContext, "qudaozhuanqinayindao_show", false)) {
//                    DialogUtils.guideToZhuanqianTipsDialog(mContext);
//                    SharedPreferencesUtil.saveBooleanData(mContext, "qudaozhuanqinayindao_show", true);
//                }
//            }
//
//        }


//        ToastUtil.showShortText(mContext, "首页");


    }

    // 右移动画
    // public void startRightAnimation() {
    // LogYiFu.e(TAG, "x" + intShaiXuanX);
    // LogYiFu.e(TAG, "x2" + intShopCartX);
    //// ObjectAnimator oa2 = ObjectAnimator.ofFloat(mRLShopCrat,
    // "translationX", DP2SPUtil.dp2px(getActivity(), 35));
    //// ObjectAnimator oa = ObjectAnimator.ofFloat(mShaiXuan, "translationX",
    // DP2SPUtil.dp2px(getActivity(), 40));
    //// oa2.setDuration(100);
    //// oa.setDuration(114);
    //// oa.start();
    //// oa2.start();
    // // oa2.addListener(new AnimatorListener() {
    // //
    // // @Override
    // // public void onAnimationStart(Animator arg0) {
    // // // TODO Auto-generated method stub
    // //
    // // }
    // //
    // // @Override
    // // public void onAnimationRepeat(Animator arg0) {
    // // // TODO Auto-generated method stub
    // //
    // // }
    // //
    // // @Override
    // // public void onAnimationEnd(Animator arg0) {
    // // // TODO Auto-generated method stub
    // // ObjectAnimator oa = ObjectAnimator.ofFloat(mShaiXuan, "translationX",
    // // DP2SPUtil.dp2px(getActivity(), 40));
    // // oa.setDuration(100);
    // // oa.start();
    // // }
    // //
    // // @Override
    // // public void onAnimationCancel(Animator arg0) {
    // // // TODO Auto-generated method stub
    // //
    // // }
    // // });
    //
    // }

    // public void startLeftAnimation() {
    // LogYiFu.e(TAG, "x" + intShaiXuanX);
    // LogYiFu.e(TAG, "x2" + intShopCartX);
    //// ObjectAnimator oa = ObjectAnimator.ofFloat(mShaiXuan, "translationX",
    // 0);
    //// ObjectAnimator oa2 = ObjectAnimator.ofFloat(mRLShopCrat,
    // "translationX", 0);
    //// oa2.setDuration(100);
    //// oa2.start();
    // oa.setDuration(114);
    // oa.start();
    // // oa.addListener(new AnimatorListener() {
    // //
    // // @Override
    // // public void onAnimationStart(Animator arg0) {
    // // // TODO Auto-generated method stub
    // //
    // // }
    // //
    // // @Override
    // // public void onAnimationRepeat(Animator arg0) {
    // // // TODO Auto-generated method stub
    // //
    // // }
    // //
    // // @Override
    // // public void onAnimationEnd(Animator arg0) {
    // // // TODO Auto-generated method stub
    // // ObjectAnimator oa2 = ObjectAnimator.ofFloat(mRLShopCrat,
    // // "translationX", 0);
    // // oa2.setDuration(100);
    // // oa2.start();
    // // }
    // //
    // // @Override
    // // public void onAnimationCancel(Animator arg0) {
    // // // TODO Auto-generated method stub
    // //
    // // }
    // // });
    // }

    // 右移动画
    public void leftAnimation() {
        // final TranslateAnimation animation = new TranslateAnimation(0,
        // -intShaiXuanX, 0, 0);
        // animation.setDuration(2000);// 设置动画持续时间
        // animation.setFillAfter(true);
        // mShaiXuan.startAnimation(animation);
        // final TranslateAnimation animation2 = new TranslateAnimation(
        // intShaiXuanX, -intShopCartX, 0, 0); // 购物车
        // animation2.setDuration(2000);// 设置动画持续时间
        // animation2.setFillAfter(true);
        // mShopCart.startAnimation(animation2);
        // animation.setRepeatCount(2);//设置重复次数
        // animation.setRepeatMode(Animation.REVERSE);//设置反方向执行
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onResume() {
        LogYiFu.e("2020-2020", "首页onResume");
        homeIsShow = true;
        hasJYZL = false;
        setHongbaoAnim();
        //处理首页弹窗
//        DialogUtils.initShouYeDialog(mContext);

//        initRightBottomHongbao();


        //奖励金优先级最高
//        if (!isShowLingLiJingEnd) {
//            DialogUtils.initJiangLiJin(mContext, false);
//
//        } else {
//
//
//            if (YJApplication.instance.isLoginSucess()) {
//                if (!isShowFQtishiEnd) { //没有显示过疯抢提示
//                    //疯抢提示
//                    getFQtishi();
//                }
//
//            } else {
//
//                if(DialogUtils.lingHongbaoShowEnd){
//                    return;
//                }
//
//                //查询交易记录
//                new SAsyncTask<String, Void, Boolean>((FragmentActivity) mContext, R.string.wait) {
//
//                    @Override
//                    protected Boolean doInBackground(FragmentActivity context, String... params)
//                            throws Exception {
//                        return ComModel2.queryHasJYJL(mContext);
//                    }
//
//                    @Override
//                    protected boolean isHandleException() {
//                        return true;
//                    }
//
//                    @Override
//                    protected void onPostExecute(FragmentActivity context, Boolean result, Exception e) {
//                        super.onPostExecute(context, result, e);
//                        if (null == e) {
//                            DialogUtils.newRedHongBaoDialog(mContext, result);
//
//
//                        }
//                    }
//
//                }.execute();
//
//            }
//        }

//        if (YJApplication.needShowQianyuanHongBao && YJApplication.instance.isLoginSucess()) {
//            SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
//            DialogUtils.redPacketDownDialog(mContext);
//            //弹出后保存当前日期
//            String dateeNow = sdff.format(new Date());
//            SharedPreferencesUtil.saveStringData(mContext, "FUCHUANYINDAODIALOG", dateeNow);
//            YJApplication.needShowQianyuanHongBao = false;
//        }


        if ("101".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, "101")) && !tongJiFirst) {
            SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_HOME, "101");
            TongJiUtils.TongJi(mContext, 1 + "");
            LogYiFu.e("TongJiNew", 1 + "");
            Long nowTimes = System.currentTimeMillis();
            SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TIMES_HOME, "" + nowTimes);
        }
        tongJiFirst = false;
        super.onResume();
        if (cardRootView.getVisibility() != View.VISIBLE
                && getFragmentManager().findFragmentByTag("CardFragment") != null) {
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("CardFragment"))
                    .commitAllowingStateLoss();
        }
        LogYiFu.e(TAG, "测试测试！");

        /**
         * 精选推荐入口的显示与影藏
         */
        // 精选200款是否已经浏览完成
        isJinxuanScanFinish = SharedPreferencesUtil.getBooleanData(mContext, Pref.JINGXUAN_SCAN_FINISH, false);
        // 精选200款浏览完成时的日期
        String jingxuan_scan_finish_date = SharedPreferencesUtil.getStringData(mContext, Pref.JINGXUAN_SCAN_FINISH_DATE,
                "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 当前日期
        String date = sdf.format(new Date());
        if (date.equals(jingxuan_scan_finish_date)) {// 如果是同一天

            if (isJinxuanScanFinish) {
//                iv_xuanfurugou.setVisibility(View.GONE);
            } else {
//                iv_xuanfurugou.setVisibility(View.VISIBLE);
            }

        } else { // 不是同一天就直接显示
//            iv_xuanfurugou.setVisibility(View.VISIBLE);
            SharedPreferencesUtil.saveBooleanData(mContext, Pref.JINGXUAN_SCAN_FINISH, false);
        }

        boolean openJingxuan = SharedPreferencesUtil.getBooleanData(mContext, "openJingxuan", false);
        if (openJingxuan) {
            if (YCache.getCacheUser(mContext).getHobby().equals("null")
                    || YCache.getCacheUser(mContext).getHobby().equals("")
                    || null == YCache.getCacheUser(mContext).getHobby()) {
                ToastUtil.showShortText(mContext, "请先设置喜好喔~");
                Intent intentLike = new Intent(mContext, MyLikeActivity.class);
                intentLike.putExtra("isJingxuanJump", true);
                getRootFragment().startActivityForResult(intentLike, 10008);
                return;
            }
            if (!YCache.getCacheUser(mContext).getHobby().contains("_")) {
                ToastUtil.showShortText(mContext, "请先设置喜好喔~");
                Intent intentLike = new Intent(mContext, MyLikeActivity.class);
                intentLike.putExtra("isJingxuanJump", true);
                getRootFragment().startActivityForResult(intentLike, 10008);
                return;
            }

            getJingxuanData(false, false, true);

        }

        /**
         * 疯狂星期一的提示自动弹出
         */
        if (SharedPreferencesUtil.getBooleanData(mContext, Pref.ISMADMONDAY, false)) {
            String grazymeonishoudata = SharedPreferencesUtil.getStringData(mContext, "GRAZYMEONISHOUdata", "");

            SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
            // 当前日期
            String datee = sdff.format(new Date());
            if (datee.equals(grazymeonishoudata)) {// 如果是同一天就不弹

            } else { // 不是同一天就弹出
                showFreeFormDialog(-1);
            }

        }

        // if (YJApplication.instance.isLoginSucess()) {
        // getJingxuanData(false, true);
        // }

        timerJingxuan = new Timer();
        timerJingxuan.schedule(new TimerTask() {

            @Override
            public void run() {

                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (jingxuanTime % 2 == 0) {
                            iv_xuanfurugou.setImageDrawable(getResources().getDrawable(R.drawable.kuan200));
                            jingxuanTime--;
                        } else {
                            iv_xuanfurugou.setImageDrawable(getResources().getDrawable(R.drawable.jingxuan));
                            jingxuanTime--;
                        }

                    }
                });

            }
        }, 1000, 1000);
    }


//    private void getRedHongbaoDialog() {
//
//
//
//
//        if (YJApplication.instance.isLoginSucess()) {
//            //查询交易记录
//            new SAsyncTask<String, Void, Boolean>((FragmentActivity) mContext, R.string.wait) {
//
//                @Override
//                protected Boolean doInBackground(FragmentActivity context, String... params)
//                        throws Exception {
//                    return ComModel2.queryHasJYJL(mContext);
//                }
//
//                @Override
//                protected boolean isHandleException() {
//                    return true;
//                }
//
//                @Override
//                protected void onPostExecute(FragmentActivity context, Boolean result, Exception e) {
//                    super.onPostExecute(context, result, e);
//                    if (null == e) {
//                        DialogUtils.newRedHongBaoDialog(mContext, result);
//
//
//                    }
//                }
//
//            }.execute();
//
//
//        } else {
//            DialogUtils.newRedHongBaoDialog(mContext, false);
//        }
//
//
//    }

    public static void setOnSearchListener(Activity mActivity) {
        searchListener = (onSearchListener) mActivity;
    }

    // private static Boolean isExit = false;
    // private void recoverBy2Click() {
    // Timer tExit = null;
    // if (isExit == false) {
    // isExit = true;
    //
    // tExit = new Timer();
    // tExit.schedule(new TimerTask() {
    // @Override
    // public void run() {
    // isExit = false;
    // }
    // }, 1000); // 如果1秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
    //
    // } else {
    //
    // ((MyShopFragment) getChildFragmentManager()
    // .findFragmentByTag("0")).mView.scrollTo(0, 0);
    // ((ItemFragment) mAdapter.getItem(currentPosition)).getmList()
    // .setSelection(0);
    //
    // }
    // }
    @Override
    public void onClick(View arg0) {

        switch (arg0.getId()) {
            case R.id.tv_fenlei:// 分类
                searchListener.onSearch();
                Intent intentClassfication = new Intent(getActivity(), ClassficationActivity.class);
                startActivity(intentClassfication);

                // -----------------------------测试-------------------------------------------------
                // new ChoicenessDialog(getActivity(), R.style.DialogStyle1).show();

                break;

            case R.id.et_search:// 跳至搜索页
                Intent intentSearch = new Intent(getActivity(), ClassficationSearchActivity.class);
                ((Activity) mContext).getWindow().getDecorView().getRootView().setDrawingCacheEnabled(true);
                Bitmap bitmap_bg = ((Activity) mContext).getWindow().getDecorView().getRootView().getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap_bg.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                byte[] bitmapDatas = baos.toByteArray();
                intentSearch.putExtra("bitmapDatas", bitmapDatas);
                startActivity(intentSearch);
                try {
                    baos.close();
                    ((Activity) mContext).getWindow().getDecorView().getRootView().destroyDrawingCache();
                } catch (IOException e) {
                }
                break;

            case R.id.rl_sign:

                //如果是审核员，不在注册当天就不能点
                if(YJApplication.instance.isLoginSucess() && YCache.getCacheUser(mContext).getReviewers() ==1 ){
                    //判断是否是在注册当天
                    long serviceTime = System.currentTimeMillis()+ YJApplication.serviceDifferenceTime;
                    String add_date = "";
                    try {
                        add_date = YCache.getCacheUser(mContext).getAdd_date().split(" ")[0];
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String currentDate = DateFormatUtils.format(serviceTime, "yyyy-MM-dd");
                    if (!add_date.equals(currentDate)) {
                        ToastUtil.showShortText2("您已领取奖励，祝您购物愉快。");
                        return;
                    }
                }





                // 跳至赚钱
                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
                startActivity(new Intent(mContext, CommonActivity.class));
                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                redShare.clearAnimation();// 点击赚钱图标之后 清除动画效果 并保存当前时间值
                moneyShare.clearAnimation();
                SharedPreferencesUtil.saveStringData(mContext, Pref.SHAREANIMZHUNA, System.currentTimeMillis() + "");
                break;

            case R.id.iv_hongbao:
//                if (hasJYZL) {
                // 跳至赚钱
                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");

                startActivity(new Intent(mContext, CommonActivity.class));
                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                break;

            case R.id.img_btn_filter:// 筛选
                // Intent intent = new Intent(mContext,
                // FilterConditionActivity.class);
                // Intent intent = new Intent(getActivity(),
                // FilterConditionActivity.class);
                // intent.putExtra("isWhere", isWhere);
                // intent.putExtra("sort_name",
                // ((MyShopFragment)
                // getChildFragmentManager().findFragmentByTag("0")).getSortNameNew());
                // _id = ((MyShopFragment)
                // getChildFragmentManager().findFragmentByTag("0")).getIdNew();
                // intent.putExtra("_id", _id);
                // LogYiFu.e(TAG, _id);
                // getActivity().startActivity(intent);
                // ((FragmentActivity)
                // getActivity()).overridePendingTransition(R.anim.activity_filter_open,
                // R.anim.activity_filter_close);
                break;
            // case R.id.img_shop_cart_common:// 购物车/
            // if (mCrruentIndex == 0) {
            // if (YJApplication.instance.isLoginSucess() == false) {
            //
            // if (LoginActivity.instances != null) {
            // LoginActivity.instances.finish();
            // }
            //
            // Intent intent1 = new Intent(getActivity(), LoginActivity.class);
            // intent1.putExtra("login_register", "login");
            // intent1.putExtra("shopcart", "shopcart");
            // startActivity(intent1);
            // return;
            // }
            // Intent intent2 = new Intent(getActivity(),
            // ShopCartNewNewActivity.class);
            // intent2.putExtra("where", "0");
            // startActivityForResult(intent2, 235);
            // } else {
            // if (YJApplication.instance.isLoginSucess() == false) {
            //
            // if (LoginActivity.instances != null) {
            // LoginActivity.instances.finish();
            // }
            //
            // Intent intent1 = new Intent(getActivity(), LoginActivity.class);
            // intent1.putExtra("login_register", "login");
            // intent1.putExtra("shopcart", "shopcart");
            //
            // startActivity(intent1);
            // return;
            // }
            // Intent intent2 = new Intent(getActivity(),
            // ShopCartNewNewActivity.class);
            // intent2.putExtra("where", "1");
            // startActivityForResult(intent2, 235);
            // }
            // /*
            // * Intent intent = new Intent(mContext, ShopCartActivity.class);
            // * startActivity(intent);
            // */
            // /*
            // * ((FragmentActivity)mContext).overridePendingTransition(R.anim.
            // * activity_filter_open,R.anim.activity_filter_close);
            // */
            // break;
            // case R.id.tv_title:
            // recoverBy2Click();
            // case R.id.new_tv_common:// 切换正价商品
            // SharedPreferencesUtil.saveStringData(mContext,
            // Pref.GOUWUPAGE_CURRENT_PAGER, "gouwu");
            //
            // SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TYPE,
            // "1001");
            // SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_PAGE,
            // "搭配");
            //
            // YunYingTongJi.yunYingTongJi(mContext, 1);
            //
            // if (mCrruentIndex == 0) {
            // break;
            // } else {
            // mShaiXuan.setVisibility(View.VISIBLE);
            // indexChange(0);
            //
            // startLeftAnimation();
            // ft = fm.beginTransaction();
            // if (getChildFragmentManager().findFragmentByTag("0") != null) {
            // ft.show(getChildFragmentManager().findFragmentByTag("0"));
            // //
            // _id=((MyShopFragment)getChildFragmentManager().findFragmentByTag("0")).getIdNew();
            // } else {
            // ft.add(R.id.shop_new_content, MyShopFragment.newInstances("tab2",
            // getActivity()), "0");// 购物
            // //
            // _id=((MyShopFragment)getChildFragmentManager().findFragmentByTag("0")).getIdNew();
            // }
            // if (getChildFragmentManager().findFragmentByTag("1") != null) {
            // ft.hide(getChildFragmentManager().findFragmentByTag("1"));
            // }
            // ft.commitAllowingStateLoss();
            //
            // mCrruentIndex = 0;
            // // getActivity().getSupportFragmentManager().beginTransaction()
            // // .replace(R.id.shop_new_content, mCommonFragment).commit();
            // break;
            // }
            // case R.id.new_tv_speical:// 切换特卖商品
            // SharedPreferencesUtil.saveStringData(mContext,
            // Pref.GOUWUPAGE_CURRENT_PAGER, "temai");
            //
            // SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TYPE,
            // "-1");
            // SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_PAGE,
            // "特卖首页");
            // if (mCrruentIndex == 1) {
            // break;
            // } else {
            // mShaiXuan.setVisibility(View.VISIBLE);
            // indexChange(1);
            // startRightAnimation();
            // ft = fm.beginTransaction();
            //// if (getChildFragmentManager().findFragmentByTag("1") != null) {
            //// ft.show(getChildFragmentManager().findFragmentByTag("1"));
            //// } else {
            //// ft.add(R.id.shop_new_content, ZeroShopFragment.newInstance("tab4",
            // getActivity()), "1");// 购物
            //// }
            //// if (getChildFragmentManager().findFragmentByTag("0") != null) {
            //// ft.hide(getChildFragmentManager().findFragmentByTag("0"));
            //// }
            //// ft.commitAllowingStateLoss();
            //
            // mCrruentIndex = 1;
            // // if(mSpecialFragment==null){
            // // mSpecialFragment = ZeroShopFragment.newInstance("tab4",
            // // getActivity());
            // // }
            // // getActivity().getSupportFragmentManager().beginTransaction()
            // // .replace(R.id.shop_new_content, mSpecialFragment).commit();
            //
            // YunYingTongJi.yunYingTongJi(mContext, 118);
            //
            // break;
            // }
            // case R.id.my_shop_button:// 余额翻倍
            // new SAsyncTask<Void, Void, HashMap<String,
            // Object>>((FragmentActivity) getActivity(), R.string.wait) {
            //
            // @Override
            // protected HashMap<String, Object> doInBackground(FragmentActivity
            // context, Void... params)
            // throws Exception {
            // HashMap<String, Object> returnInfo = new HashMap<String, Object>();
            // returnInfo = ComModel.startDoubleBalance(context,
            // YCache.getCacheToken(context), 2);
            // return returnInfo;
            // }
            //
            // @Override
            // protected boolean isHandleException() {
            // return true;
            // }
            //
            // @Override
            // protected void onPostExecute(FragmentActivity context,
            // HashMap<String, Object> result, Exception e) {
            // super.onPostExecute(context, result, e);
            // if (e == null && result != null) {
            // Intent intent = new Intent(getActivity(),
            // BalanceDoubleSuccessActivity.class);
            // startActivity(intent);
            // SharedPreferencesUtil.saveStringData(context, Pref.IS_OPEN, 1 + "");
            // }
            //
            // }
            //
            // }.execute();
            //
            // break;

            case R.id.iv_xuanfurugou:// 精选推荐


                // 去登陆
                if (!YJApplication.instance.isLoginSucess()) {

                    if (LoginActivity.instances != null) {
                        LoginActivity.instances.finish();
                    }
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.putExtra("login_register", "login");
                    ((FragmentActivity) mContext).startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    break;
                } else {
                    new SAsyncTask<Void, Void, QueryPhoneInfo>((FragmentActivity) mContext) {

                        @Override
                        protected QueryPhoneInfo doInBackground(FragmentActivity context, Void... params) throws Exception {

                            return ComModel.bindPhone(context);
                        }

                        @Override
                        protected boolean isHandleException() {
                            return true;
                        }

                        @Override
                        protected void onPostExecute(FragmentActivity context, QueryPhoneInfo result, Exception e) {
                            super.onPostExecute(context, result, e);
                            if (null == e) {
                                if (result != null && "1".equals(result.getStatus())) {
                                    phoneInfo = result;

                                    if (phoneInfo.isBool()) {
                                        if (YCache.getCacheUser(mContext).getHobby().equals("null")
                                                || YCache.getCacheUser(mContext).getHobby().equals("")
                                                || null == YCache.getCacheUser(mContext).getHobby()
                                                || !YCache.getCacheUser(mContext).getHobby().contains("_")) {


                                            ToastUtil.showShortText(mContext, "请先设置喜好喔~");
                                            Intent intentLike = new Intent(mContext, MyLikeActivity.class);
                                            intentLike.putExtra("isJingxuanJump", true);
                                            getRootFragment().startActivityForResult(intentLike, 10008);
                                            return;
                                        }


//                                        if (!YCache.getCacheUser(mContext).getHobby().contains("_")) {
//                                            ToastUtil.showShortText(mContext, "请先设置喜好喔~");
//                                            Intent intentLike = new Intent(mContext, MyLikeActivity.class);
//                                            intentLike.putExtra("isJingxuanJump", true);
//                                            getRootFragment().startActivityForResult(intentLike, 10008);
//                                            return;
//                                        }

                                        getJingxuanData(false, false, false);
                                    } else {
                                        ToastUtil.showShortText(mContext, "请先设绑定手机并置喜好喔~");
//									Intent intent = new Intent(mContext, BindPhoneActivity.class);
//									intent.putExtra("isjingxuan", true);
//									getRootFragment().startActivityForResult(intent, 10008);


                                        Intent intent = new Intent(mContext, SettingCommonFragmentActivity.class);
                                        intent.putExtra("flag", "bindPhoneFragment");
                                        intent.putExtra("bool", false);
                                        intent.putExtra("isChanal", MainMenuActivity.noUserRegist); // 渠道包用true,其他用false
                                        // 为了测试方便暂时用true
                                        intent.putExtra("phoneNum", "");

                                        intent.putExtra("tishiBind", true);

                                        intent.putExtra("bindSelf", true);

                                        intent.putExtra("wallet", "account");
                                        intent.putExtra("thirdparty", "thirdparty");
                                        startActivity(intent);
                                        ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                                    }

                                } else {
                                    ToastUtil.showLongText(context, "糟糕，出错了~~~");
                                }
                            }
                        }

                    }.execute();
                }

                // String phone = YCache.getCacheUser(mContext).getPhone();
                // if (phone == null || phone.equals("null") || phone.equals("")) {
                // ToastUtil.showShortText(mContext, "请先设绑定手机并置喜好喔~");
                // Intent intent = new Intent(mContext, BindPhoneActivity.class);
                // intent.putExtra("isjingxuan", true);
                // getRootFragment().startActivityForResult(intent, 10008);
                // break;
                // }
                //
                // if (YCache.getCacheUser(mContext).getHobby().equals("null")
                // || YCache.getCacheUser(mContext).getHobby().equals("")
                // || null == YCache.getCacheUser(mContext).getHobby()) {
                // ToastUtil.showShortText(mContext, "请先设置喜好喔~");
                // Intent intentLike = new Intent(mContext, MyLikeActivity.class);
                // intentLike.putExtra("isJingxuanJump", true);
                // getRootFragment().startActivityForResult(intentLike, 10008);
                // break;
                // }
                // if (!YCache.getCacheUser(mContext).getHobby().contains("_")) {
                // ToastUtil.showShortText(mContext, "请先设置喜好喔~");
                // Intent intentLike = new Intent(mContext, MyLikeActivity.class);
                // intentLike.putExtra("isJingxuanJump", true);
                // getRootFragment().startActivityForResult(intentLike, 10008);
                // break;
                // }
                //
                // getJingxuanData(false, false);

                // cardRootView.setVisibility(View.VISIBLE);
                // getFragmentManager().beginTransaction()
                // .add(R.id.container_jingixuan,
                // CardFragment.newInstances("CardFragment", mContext),
                // "CardFragment")
                // .commitAllowingStateLoss();
                break;
            default:
                break;
        }

    }

    private QueryPhoneInfo phoneInfo = new QueryPhoneInfo();

    private void getJingxuanData(final boolean isFrst, final boolean isShow, final boolean isSign) {

        new SAsyncTask<String, Void, List<CardDataItem>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected void onPostExecute(FragmentActivity mContext, List<CardDataItem> result, Exception e) {
                super.onPostExecute(mContext, result, e);
                if (null == e && result != null && result.size() > 0) {
                    // if
                    // (getChildFragmentManager().findFragmentByTag("CardFragment")
                    // != null) {
                    // ft.show(getChildFragmentManager().findFragmentByTag("CardFragment"));
                    // } else {
                    // ft.add(R.id.container_jingixuan,
                    // CardFragment.newInstances("CardFragment", mContext),
                    // "CardFragment");
                    // ft.commitAllowingStateLoss();
                    // }
                    getFragmentManager()
                            .beginTransaction().add(R.id.container_jingixuan,
                            CardFragment.newInstances("CardFragment", mContext), "CardFragment")
                            .commitAllowingStateLoss();

                    if (isSign) {
                        SharedPreferencesUtil.saveBooleanData(mContext, "openJingxuan", false);
                    }

                    // if (isFrst) {
                    // new CardJingxuanDialog(mContext).show();
                    // SharedPreferencesUtil.saveStringData(mContext,
                    // Pref.JINGXUAN, System.currentTimeMillis() + "");
                    // }
                    //

                    // cardRootView.setVisibility(View.VISIBLE);
                    showCardView();
//                    iv_xuanfurugou.setVisibility(View.VISIBLE);

                } else {
                    cardRootView.setVisibility(View.GONE);
                    if (!isFrst) {
                        SharedPreferencesUtil.saveBooleanData(mContext, "openJingxuan", false);
                        ToastUtil.showShortText(mContext, "没有精选推荐商品喔~");
                        if (isShow) {
//                            iv_xuanfurugou.setVisibility(View.GONE);
                        }

                    }

                }

            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected List<CardDataItem> doInBackground(FragmentActivity mContext, String... params) throws Exception {

                return ComModel2.getLikeQua(mContext, 20);

            }

        }.execute();

    }

    /**
     * 得到根Fragment
     *
     * @return
     */
    private Fragment getRootFragment() {
        Fragment fragment = getParentFragment();
        while (fragment.getParentFragment() != null) {
            fragment = fragment.getParentFragment();
        }
        return fragment;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10008) {
            if (resultCode == -1001) { // 设置喜好回来
                // cardRootView.setVisibility(View.VISIBLE);
                // if
                // (getChildFragmentManager().findFragmentByTag("CardFragment")
                // != null) {
                // ft.show(getChildFragmentManager().findFragmentByTag("CardFragment"));
                // } else {
                // ft.add(R.id.container_jingixuan,
                // CardFragment.newInstances("CardFragment", mContext),
                // "CardFragment");
                // ft.commitAllowingStateLoss();
                // }
                getFragmentManager()
                        .beginTransaction().add(R.id.container_jingixuan,
                        CardFragment.newInstances("CardFragment", mContext), "CardFragment")
                        .commitAllowingStateLoss();
                showCardView();
            } else if (resultCode == -10002) { // 绑定手机回来
                ToastUtil.showShortText(mContext, "绑定成功");

                // 设置喜好
                Intent intentLike = new Intent(mContext, MyLikeActivity.class);
                intentLike.putExtra("isJingxuanJump", true);
                getRootFragment().startActivityForResult(intentLike, 10008);
            }

        }

    }

    /**
     * 切换点击的回调
     */
    public void indexChange(int position) {
        if (position == 0) {// 当前页是正价
            // showBanlanceTime(position);
            // mTvCommon.setTextColor(getResources().getColor(R.color.white));
            // mTvCommon.setBackgroundResource(R.drawable.title_red_left);
            // mTvSpecial.setTextColor(getResources().getColor(R.color.red_new));
            // mTvSpecial.setBackgroundResource(R.drawable.title_white_right);
        } else {// 当前页是特卖
            // showBanlanceTime(position);
            // mTvSpecial.setTextColor(getResources().getColor(R.color.white));
            // mTvSpecial.setBackgroundResource(R.drawable.title_red_right);
            // mTvCommon.setTextColor(getResources().getColor(R.color.red_new));
            // mTvCommon.setBackgroundResource(R.drawable.title_white_left);
        }
    }

    /**
     * 余额翻倍倒计时的显示与隐藏
     */
    // public void showBanlanceTime(int position) {
    // if (YJApplication.instance.isLoginSucess()) {
    // if (position == 0 && deadTime > 0) {// &&
    // // SharedPreferencesUtil.getBooleanData(getActivity(),
    // // Pref.IS_QULIFICATION, false)
    // LogYiFu.e(TAG, "错了吗");
    // my_shop_rl_show_time.setVisibility(View.VISIBLE);
    // if (mIsOpen == 1) {
    // mNowStart.setBackgroundResource(R.drawable.button_yikaiqi);
    // mNowStart.setClickable(false);
    // } else {
    // mNowStart.setBackgroundResource(R.drawable.button_lijikaiqi);
    // mNowStart.setClickable(true);
    // mNowStart.setOnClickListener(this);
    // }
    // } else {
    // my_shop_rl_show_time.setVisibility(View.GONE);
    // }
    // } else {
    // my_shop_rl_show_time.setVisibility(View.GONE);
    // }
    // }

    // 查询购物车数量
    // private void queryCartCount() {
    //
    // new SAsyncTask<Void, Void, String>(getActivity(), R.string.wait) {
    //
    // @Override
    // protected String doInBackground(FragmentActivity context, Void... params)
    // throws Exception {
    // return ComModel2.getShopCartCount(context);
    // }
    //
    // @Override
    // protected boolean isHandleException() {
    // return true;
    // }
    //
    // @Override
    // protected void onPostExecute(FragmentActivity context, String count,
    // Exception e) {
    // super.onPostExecute(context, count, e);
    // if (e != null || count == null) {
    // return;
    // }
    // if (Integer.parseInt(count) > 0) {
    // // mShopCartCount.setVisibility(View.VISIBLE);
    // // mShopCartCount.setText(count);
    // } else {
    // // mShopCartCount.setVisibility(View.GONE);
    // }
    //
    // }
    // }.execute();
    //
    // }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // if (task != null) {
        // task.cancel();
        // task = null;
        // }
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        homeIsShow = false;
        if (timerJingxuan != null) {
            timerJingxuan.cancel();
        }
        // jingxuanTask.cancel();
        timerJingxuan = null;
        if ("101".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, "101"))) {
            TongJiUtils.TongJi(mContext, 101 + "");
            LogYiFu.e("TongJiNew", 101 + "");

            Long nowTimesEnd = System.currentTimeMillis();
            Long nowTimesStart = Long
                    .parseLong(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_TIMES_HOME, nowTimesEnd + ""));
            Long duration = (nowTimesEnd - nowTimesStart) / 1000;// 以 秒 为单位
            TongJiUtils.TongJiDuration(mContext, 1001 + "", duration + "");
            LogYiFu.e("TongJiNew", duration + "秒" + 1001);
        }
    }

    // public static void setCurrentIndex(int currentIndext) {
    // mCrruentIndex = currentIndext;
    // }
    //
    // public static int getCurrentIndex() {
    // return mCrruentIndex;
    //
    // }
    public static HomePageFragment newInstances(String title, Context context) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putString("tag", title);
//        mContext = context;
        fragment.setArguments(args);
        return fragment;
    }

    // 倒计时
    // TimerTask task = new TimerTask() {
    // @Override
    // public void run() {
    //
    // getActivity().runOnUiThread(new Runnable() { // UI thread
    // @Override
    // public void run() {
    // recLen -= 1000;
    // String days;
    // String hours;
    // String minutes;
    // String seconds;
    // long minute = recLen / 60000;
    // long second = (recLen % 60000) / 1000;
    // if (minute >= 60) {
    // long hour = minute / 60;
    // minute = minute % 60;
    // if (hour >= 24) {
    // long day = hour / 24;
    // hour = hour % 24;
    // if (day < 10) {
    // days = "0" + day;
    // } else {
    // days = "" + day;
    // }
    // if (hour < 10) {
    // hours = "0" + hour;
    // } else {
    // hours = "" + hour;
    // }
    // if (minute < 10) {
    // minutes = "0" + minute;
    // } else {
    // minutes = "" + minute;
    // }
    // if (second < 10) {
    // seconds = "0" + second;
    // } else {
    // seconds = "" + second;
    // }
    // mTvTime.setText("" + days + "天" + hours + "时" + minutes + "分" + seconds +
    // "秒");
    // } else {
    // if (hour < 10) {
    // hours = "0" + hour;
    // } else {
    // hours = "" + hour;
    // }
    // if (minute < 10) {
    // minutes = "0" + minute;
    // } else {
    // minutes = "" + minute;
    // }
    // if (second < 10) {
    // seconds = "0" + second;
    // } else {
    // seconds = "" + second;
    // }
    // mTvTime.setText("" + hours + "时" + minutes + "分" + seconds + "秒");
    // }
    // } else if (minute >= 10 && second >= 10) {
    // mTvTime.setText("" + minute + "分" + second + "秒");
    // } else if (minute >= 10 && second < 10) {
    // mTvTime.setText("" + minute + "分0" + second + "秒");
    // } else if (minute < 10 && second >= 10) {
    // mTvTime.setText("0" + minute + "分" + second + "秒");
    // } else {
    // mTvTime.setText("0" + minute + "分0" + second + "秒");
    // }
    // // mTvTime.setText("" + recLen);
    // if (recLen <= 0) {
    // timer.cancel();
    // my_shop_rl_show_time.setVisibility(View.GONE);
    //
    // SharedPreferencesUtil.saveStringData(getActivity(), Pref.TWOFOLDNESS,
    // "-1"); // 余额翻倍倍数
    // SharedPreferencesUtil.saveBooleanData(getActivity(),
    // Pref.IS_QULIFICATION, false); // 余额翻倍是否有开启资格
    // SharedPreferencesUtil.saveStringData(getActivity(), Pref.IS_OPEN, "0");//
    // 是否已开启
    // // 0：未开启
    // // 1：已开启
    // // mTvTime.setVisibility(View.GONE);
    // }
    // }
    // });
    // }
    // };
    public static Boolean appear;

    /**
     * 获取系统时间
     */
    // private void getSystemTime() {
    // new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity)
    // getActivity(), R.string.wait) {
    //
    // @Override
    // protected HashMap<String, Object> doInBackground(FragmentActivity
    // context, Void... params)
    // throws Exception {
    // return ComModel2.getSystemTime(context);
    // }
    //
    // @Override
    // protected boolean isHandleException() {
    // return true;
    // }
    //
    // @Override
    // protected void onPostExecute(FragmentActivity context, HashMap<String,
    // Object> result, Exception e) {
    // super.onPostExecute(context, result, e);
    //
    // if (null == e && result != null) {
    // nowTime = (Long) result.get("now");
    //
    // recLen = deadTime - nowTime;
    // if (recLen > 0) {
    // my_shop_rl_show_time.setVisibility(View.VISIBLE);
    // mTvTime.setVisibility(View.VISIBLE);
    // }
    // }
    // }
    //
    // }.execute();
    // }

    // 获取抵用券
    // private void diyongquan() {
    //
    // new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity)
    // HomePageFragment.this.getActivity(),
    // R.string.wait) {
    // @Override
    // protected HashMap<String, Object> doInBackground(FragmentActivity
    // context, Void... params)
    // throws Exception {
    // return (HashMap<String, Object>) ComModel2.DiYongQuan(context);
    // }
    //
    // @Override
    // protected boolean isHandleException() {
    // return true;
    // }
    //
    // protected void onPostExecute(FragmentActivity context, HashMap<String,
    // Object> result, Exception e) {
    //
    // if (result != null) {
    // String num = result.get("num").toString();
    // if (appear == false) {
    // if (num.equals("0")) { // 第一次弹80抵用券
    // EightyDialog eightydialog = new
    // EightyDialog(HomePageFragment.this.getActivity());
    // eightydialog.show();
    // } else { // 不是第一次弹30抵用券
    // ThirtyDialog thirtyDialog = new
    // ThirtyDialog(HomePageFragment.this.getActivity());
    // thirtyDialog.show();
    // }
    // }
    // } else {
    // return;
    // }
    //
    // }
    //
    // }.execute();
    // }

    /**
     * 获取我的喜好
     */
    private void getMineLike() {
        new SAsyncTask<Void, Void, String>((FragmentActivity) getActivity(), R.string.wait) {

            @Override
            protected String doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel2.getMineLike(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, String result, Exception e) {
                super.onPostExecute(context, result, e);

                if (null == e && result != null) {
                    // SharedPreferences sp =
                    // context.getSharedPreferences("data",
                    // Context.MODE_PRIVATE);
                    // Editor et = sp.edit();
                    // et.putStringSet(""+YCache.getCacheUser(context).getUser_id(),
                    // result);
                    SharedPreferencesUtil.saveStringData(mContext, "" + YCache.getCacheUser(mContext).getUser_id(),
                            result);
                }
            }

        }.execute();
    }

    //

    /**
     * 赚钱图标动画效果
     */
    private void setZhuanIconAnim() {

        SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String shareAnim = SharedPreferencesUtil.getStringData(mContext, Pref.SHAREANIMZHUNA, "0");
        long shareAnimTime = Long.valueOf(shareAnim);
        boolean isRoate = "0".equals(shareAnim) || !df.format(new Date()).equals(df.format(new Date(shareAnimTime)));
        if (!isRoate) {
            return;
        }
        RotateAnimation ani1 = new RotateAnimation(0f, 35f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        ScaleAnimation ani2 = new ScaleAnimation(1.0f, 0.85f, 1.0f, 0.85f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        final AnimationSet set = new AnimationSet(mContext, null);
        ani1.setDuration(270);
        ani1.setRepeatMode(Animation.REVERSE);
        // ani1.setRepeatCount(1);
        ani1.setFillAfter(false);
        // ani1.setStartOffset(1500);
        ani2.setDuration(270);
        ani2.setRepeatMode(Animation.RESTART);
        // ani2.setRepeatCount(Integer.MAX_VALUE);
        ani2.setFillAfter(false);
        // ani2.setStartOffset(1500);

        set.addAnimation(ani1);
        set.addAnimation(ani2);
        set.setStartOffset(600);
        // redShare.setAnimation(set);
        redShare.startAnimation(set);

        final RotateAnimation ani3 = new RotateAnimation(-12f, 10f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        ani3.setDuration(55);
        ani3.setRepeatMode(Animation.REVERSE);
        ani3.setRepeatCount(2);
        ani3.setFillAfter(true);
        final RotateAnimation ani4 = new RotateAnimation(-6f, 6f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        ani4.setDuration(45);
        ani4.setRepeatMode(Animation.REVERSE);
        ani4.setRepeatCount(1);
        ani4.setFillAfter(false);

        ani1.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moneyShare.startAnimation(ani3);
                set.setStartOffset(1300);
            }
        });
        ani3.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moneyShare.startAnimation(ani4);
            }
        });
        ani4.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                redShare.startAnimation(set);
            }
        });

    }

    /**
     * 获取热门搜索标签
     */
    private void getHotTag() {

        new SAsyncTask<Void, Void, List<HashMap<String, String>>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected List<HashMap<String, String>> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getHotTag(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<HashMap<String, String>> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null && result.size() > 0) {
                    // for (int i = 0; i < result.size(); i++) {
                    // String tag_id = result.get(i).get("tag_id");
                    // String hotSql = "select * from tag_info where _id = " +
                    // tag_id;
                    // List<HashMap<String, String>> datasHot =
                    // dbHelp.query(hotSql);
                    // if (i == 0 && datasHot.size() > 0) {
                    // et_search.setText(datasHot.get(0).get("attr_name"));
                    // }
                    // mDatasHot.addAll(dbHelp.query(hotSql));
                    // }
                    // initHotChildViews(mDatasHot, 1);// type_tag 和 tag_info
                    // // name键值 不同

                    String tag_id = result.get(0).get("tag_id");
                    String hotSql = "select * from tag_info where _id = " + tag_id;
                    List<HashMap<String, String>> datasHot = dbHelp.query(hotSql);
                    if (datasHot.size() > 0) {
                        et_search.setText(datasHot.get(0).get("attr_name"));
                    }

                }
                // else {
                // String hotSql = "select * from type_tag where type = " +
                // types[0] + " order by _id";// 没有数据时候
                // // 使用流行趋势填充
                // mDatasHot = dbHelp.query(hotSql);
                // initHotChildViews(mDatasHot, 0);// type_tag 和 tag_info
                // // name键值 不同
                // }

            }
        }.execute();
    }

    public void showFreeFormDialog(final int i) {// 只能传0和1，0代表返还50，1代表返还100
        // -1：疯狂星期一；

        SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
        // 当前日期
        String datee = sdff.format(new Date());
        SharedPreferencesUtil.saveStringData(mContext, "GRAZYMEONISHOUdata", datee);
        final Dialog freeDialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = View.inflate(mContext, R.layout.free_form_dialog, null);
        freeDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        ImageView free_iv_close = (ImageView) view.findViewById(R.id.free_iv_close);
        ImageView to_hotsale = (ImageView) view.findViewById(R.id.to_hotsale);
        free_iv_close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                freeDialog.dismiss();

            }
        });
        to_hotsale.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(mContext, HotSaleActivity.class);
//                intent.putExtra("id", "6");
//                intent.putExtra("title", "热卖");
//                startActivity(intent);
//                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                if (!YJApplication.instance.isLoginSucess()) {

                    if (LoginActivity.instances != null) {
                        LoginActivity.instances.finish();
                    }

                    Intent intentd = new Intent(mContext, LoginActivity.class);
                    intentd.putExtra("login_register", "login");
                    ((FragmentActivity) mContext).startActivity(intentd);
                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    return;
                }


                //检查绑定手机--------------------------------------------------------------------------------------------------------------------------------------
                new SAsyncTask<Void, Void, QueryPhoneInfo>((FragmentActivity) mContext) {

                    @Override
                    protected QueryPhoneInfo doInBackground(FragmentActivity context,
                                                            Void... params) throws Exception {

                        return ComModel.bindPhone(context);
                    }

                    @Override
                    protected boolean isHandleException() {
                        return true;
                    }

                    @Override
                    protected void onPostExecute(FragmentActivity context,
                                                 QueryPhoneInfo result, Exception e) {
                        super.onPostExecute(context, result, e);
                        if (null == e) {
                            if (result != null && "1".equals(result.getStatus())) {
                                Boolean phoneboolll = result.isBool();
                                if (phoneboolll == false) {// 未绑定手机
                                    customDialog(mContext);


                                    return;
                                } else {
//检查微信授权-----------------------------------------------------------------------------------------------------------------
                                    String uuid = YCache.getCacheUserSafe(mContext).getUuid();

                                    if (null == uuid || uuid.equals("null")
                                            || uuid.equals("")) {
                                        //没有授权微信-----去授权

                                        boolean mWxInstallFlag = false;

                                        try {
                                            // // 是否安装了微信

                                            if (WXcheckUtil.isWeChatAppInstalled(mContext)) {
                                                mWxInstallFlag = true;
                                            } else {
                                                mWxInstallFlag = false;
                                            }
                                        } catch (Exception ee) {
                                        }

                                        if (!mWxInstallFlag) {

                                            ToastUtil.showShortText(mContext, "您没有安装微信，需要微信授权~");

                                        } else {
                                            ToastUtil.showShortText(mContext, "您没有授权微信，请授权");

//
//                                            UMWXHandler wxHandler = new UMWXHandler(mContext, WxPayUtil.APP_ID,
//                                                    WxPayUtil.APP_SECRET);
//                                            wxHandler.addToSocialSDK();
//
//
//                                            //授权微信
//                                            final UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);
//
//                                            mController.doOauthVerify(context, SHARE_MEDIA.WEIXIN, new SocializeListeners.UMAuthListener() {
//
//                                                @Override
//                                                public void onStart(SHARE_MEDIA platform) {
//                                                }
//
//                                                @Override
//                                                public void onError(SocializeException e, SHARE_MEDIA platform) {
//                                                    ToastUtil.showShortText(mContext, "微信授权失败");
//
//                                                }
//
//                                                @Override
//                                                public void onComplete(Bundle value, SHARE_MEDIA platform) {
//
//                                                    final String openid = value.getString("openid");
//
//
//                                                    mController.getPlatformInfo(mContext, platform, new SocializeListeners.UMDataListener() {
//
//                                                        @Override
//                                                        public void onStart() {
//
//                                                        }
//
//                                                        @Override
//                                                        public void onComplete(int status, Map<String, Object> info) {
//                                                            if (info != null) {
//
//
//                                                                final String unionid = info.get("unionid").toString();
//
//
//                                                                if (TextUtils.isEmpty(unionid)) {
//                                                                    return;
//                                                                }
//
//                                                                //更新unionid
//                                                                new SAsyncTask<String, Void, UserInfo>((FragmentActivity) mContext, R.string.wait) {
//
//                                                                    @Override
//                                                                    protected UserInfo doInBackground(FragmentActivity context, String... params)
//                                                                            throws Exception {
//                                                                        return ComModel2.updateUuid(mContext, unionid, openid);
//                                                                    }
//
//                                                                    @Override
//                                                                    protected boolean isHandleException() {
//                                                                        return true;
//                                                                    }
//
//                                                                    @Override
//                                                                    protected void onPostExecute(FragmentActivity context, UserInfo result, Exception e) {
//                                                                        super.onPostExecute(context, result, e);
//                                                                        if (null == e) {
//
//
//                                                                        }
//                                                                    }
//
//                                                                }.execute();
//
//
//                                                            }
//                                                        }
//                                                    });
//                                                }
//
//                                                @Override
//                                                public void onCancel(SHARE_MEDIA platform) {
//                                                    ToastUtil.showShortText(mContext, "需要微信授权哦~");
//
//                                                }
//                                            });

                                        }

                                        return;
                                    }


                                }


                                Intent intent2 = new Intent((Activity) mContext, CrazyShopListActivity.class);
                                mContext.startActivity(intent2);
                                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                                freeDialog.dismiss();


                            } else {
                                ToastUtil.showLongText(context, "糟糕，出错了~~~");
                            }
                        }
                    }

                }.execute();


            }
        });
        ImageView free_iv = (ImageView) view.findViewById(R.id.free_iv);
        free_iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, BuyFreeActivity.class);
                if (i == -1) {
                    intent.putExtra("isCrazyMon", true);
                }
                intent.putExtra("cashBack", i);
                startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                freeDialog.dismiss();
            }
        });
        if (i == 1) {
            to_hotsale.setVisibility(View.GONE);
//            free_iv.setImageResource(R.drawable.free_100);
        } else if (i == 0) {
            to_hotsale.setVisibility(View.GONE);
//            free_iv.setImageResource(R.drawable.free_50);
        } else if (i == -1) {
            to_hotsale.setVisibility(View.VISIBLE);
//            free_iv.setImageResource(R.drawable.bg_hdxq);
        }
        freeDialog.setContentView(view);
        freeDialog.setCancelable(false);
        freeDialog.show();

    }

    // 卡片布局的显示动画效果
    private void showCardView() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(cardRootView, "Y", 0.3F, 0.4F, 0.5F, 0.6F, 0.7F, 0.8F, 0.9F, 1.0F)
                .setDuration(260);
        anim.start();
        anim.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                cardRootView.setAlpha(cVal);
                cardRootView.setScaleX(cVal);
                cardRootView.setScaleY(cVal);
            }
        });
        cardRootView.setVisibility(View.VISIBLE);
        SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_HOME, "102");
    }

    public static void hideCardView() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(cardRootView, "Y", 1.0F, 0.9F, 0.8F, 0.7F, 0.6F, 0.5F, 0.4F, 0.3F)
                .setDuration(260);
        anim.start();
        anim.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                cardRootView.setAlpha(cVal);
                cardRootView.setScaleX(cVal);
                cardRootView.setScaleY(cVal);
            }
        });
        anim.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                cardRootView.setVisibility(View.GONE);
                if (HomePageFragment.instance.getFragmentManager().findFragmentByTag("CardFragment") != null) {
                    HomePageFragment.instance.getFragmentManager().beginTransaction()
                            .remove(HomePageFragment.instance.getFragmentManager().findFragmentByTag("CardFragment"))
                            .commitAllowingStateLoss();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
        // getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("CardFragment")).commit();
        SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_HOME, "101");
        SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TIMES_HOME, System.currentTimeMillis() + "");
        TongJiUtils.TongJi(mContext, 102 + "");
        LogYiFu.e("TongJiNew", 102 + "");

        Long nowTimesEnd = System.currentTimeMillis();
        Long nowTimesStart = Long
                .parseLong(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_TIMES_JINXUAN, nowTimesEnd + ""));
        Long duration = (nowTimesEnd - nowTimesStart) / 1000;// 以 秒 为单位
        TongJiUtils.TongJiDuration(mContext, 1002 + "", duration + "");
        LogYiFu.e("TongJiNew", duration + "秒" + 1002);
    }


    /**
     * 首页赚钱页面引导弹框
     */
    private void showGuideZhuanqianDialog() {

//
//        if (MainMenuActivity.homeIsSign || !YJApplication.instance.isLoginSucess()) {
//            return;
//        }
//        SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
//        String first_times = SharedPreferencesUtil.getStringData(mContext, Pref.GUIDE_TO_ZQ_FIRST_TIMES, "0");
//        if ("0".equals(first_times)) {
//            //保存第一次弹出时间（记录为第一周的时间）
//            SharedPreferencesUtil.saveStringData(mContext, Pref.GUIDE_TO_ZQ_FIRST_TIMES, System.currentTimeMillis() + "");
//        }
//        long aLongFirstTimes = Long.parseLong(first_times);
//
//        if ("0".equals(first_times) || DateUtil.isSameWeek(new Date(), new Date(aLongFirstTimes))) {
//            //第一周第一次 或者是  和第一周时间是同一周   一天一次 并且弹两次(进入APP前两天一天一次)
//
//            String times = SharedPreferencesUtil.getStringData(mContext, Pref.GUIDE_TO_ZQ_TIMES, "0");
//            String counts = SharedPreferencesUtil.getStringData(mContext, Pref.GUIDE_TO_ZQ_FIRST_COUNT, "0");
//            long aLongTimes = Long.parseLong(times);
//            int aLongCounts = Integer.parseInt(counts);
//
//            if (("0".equals(times) || !df.format(new Date()).equals(df.format(new Date(aLongTimes))))
//                    && aLongCounts < 2) {
//                DialogUtils.guideToZhuanqianDialog(mContext);
//                SharedPreferencesUtil.saveStringData(mContext, Pref.GUIDE_TO_ZQ_TIMES, System.currentTimeMillis() + "");
//                SharedPreferencesUtil.saveStringData(mContext, Pref.GUIDE_TO_ZQ_FIRST_COUNT, ++aLongCounts + "");
//            }
//
//
//        } else {
//            //第一周之后 一周一次
//            String times = SharedPreferencesUtil.getStringData(mContext, Pref.GUIDE_TO_ZQ_TIMES, "0");
//            long aLongTimes = Long.parseLong(times);
//            if (!DateUtil.isSameWeek(new Date(), new Date(aLongTimes))) {//和上次弹出时间不是同一周
//                DialogUtils.guideToZhuanqianDialog(mContext);
//                SharedPreferencesUtil.saveStringData(mContext, Pref.GUIDE_TO_ZQ_TIMES, System.currentTimeMillis() + "");
//            }
//
//        }

    }

    public static AlertDialog dialog;

    public static void customDialog(final Context mContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.Theme_Transparent);
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


        tv_des.setText("为了您的账户安全，请先绑定手机");
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 把这个对话框取消掉
                dialog.dismiss();

            }
        });

    }

//    public void getFQtishi() {
//
//        new SAsyncTask<Void, Void, Boolean>((FragmentActivity) mContext, R.string.wait) {
//
//            @Override
//            protected Boolean doInBackground(FragmentActivity context, Void... params)
//                    throws Exception {
//
//                return ComModel.getPingtuanTongzhi(context);
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, Boolean result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (null == e) {
//
//                    if (result) {
//                        isShowFQtishiEnd = true;
//
//
//                        final Dialog dialog = new Dialog(context, R.style.invate_dialog_style);
//                        View view = View.inflate(context, R.layout.dialog_fqts, null);
//
//                        TextView btn_ok = (TextView) view.findViewById(R.id.btn_ok);
//                        btn_ok.setOnClickListener(new OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//
//                                dialog.dismiss();
//                                Intent intent = new Intent(mContext, StatusInfoActivity.class);
//                                intent.putExtra("index", 2);
//                                mContext.startActivity(intent);
//
//
//                            }
//                        });
//                        ImageView balance_dialog_close = (ImageView) view.findViewById(R.id.balance_dialog_close);
//                        balance_dialog_close.setOnClickListener(new OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//
//                                dialog.dismiss();
//
//
//                            }
//                        });
//
//                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                            @Override
//                            public void onDismiss(DialogInterface dialogInterface) {
//                                isShowFQtishiClose = true;
//                            }
//                        });
//
//                        // 创建自定义样式dialog
//                        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
//                                LinearLayout.LayoutParams.FILL_PARENT));
//                        dialog.setCancelable(false);
//                        dialog.show();
//
//
//                    } else {
//                        if(DialogUtils.lingHongbaoShowEnd){
//                            return;
//                        }
//                        getRedHongbaoDialog();
//                    }
//
//                }
//            }
//
//        }.execute();
//    }
}