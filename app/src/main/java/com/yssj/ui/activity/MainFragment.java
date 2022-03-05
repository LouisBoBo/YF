package com.yssj.ui.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.umeng.socialize.media.UMImage;
import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.MyPopupwindow;
import com.yssj.custom.view.NewPDialog;
import com.yssj.custom.view.ToLoginDialog;
import com.yssj.data.YDBHelper;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel2;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.setting.FeedBackActivity;
import com.yssj.ui.activity.setting.SettingActivity;
import com.yssj.ui.activity.shopdetails.NoShareActivity;
import com.yssj.ui.dialog.DialogExitThirty;
import com.yssj.ui.fragment.ClassficationFragment;
import com.yssj.ui.fragment.FriendsFragment;
import com.yssj.ui.fragment.HomePageFragment;
import com.yssj.ui.fragment.MatchFragment;
import com.yssj.ui.fragment.MineIfoFragment;
import com.yssj.utils.DialogUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.YCache;
import com.yssj.utils.YunYingTongJi;
import com.yssj.utils.sqlite.ShopCartDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
//import com.yssj.ui.dialog.DialogExitThirty;
//import com.yssj.ui.dialog.EightyDialog;
//import com.yssj.ui.dialog.ThirtyDialog;

@SuppressWarnings("WrongConstant")
public class MainFragment extends Fragment implements OnClickListener, OnCheckedChangeListener {
    private RadioGroup rg_change;

    private FragmentTransaction ft;
    private FragmentManager fm;

    /* private boolean isLogin = false; */
    // /------------shopFragment---------------------

    private static String footPrintCount = "0";

    private RadioButton tab_one, tab_two, tab_three, tab_four, tab_five;
    private static Context mContext;

    private TextView undo_view1;
//    private TextView undo_view4;

    private TextView undo_view5;

    public static MainFragment mfragment;

    private boolean flag = false;

    // public MainFragment() {
    // super();
    // // TODO Auto-generated constructor stub
    // }
    private String signShopDetail;
    private String signType;// 代表 几元包邮
    private boolean isDuobao;
    private String CanYunumber;// 夺宝参与号码
    private int now_type_id;
    private int now_type_id_value;
    private int next_type_id;
    private int next_type_id_value;

    private boolean mFirstFlag = false;
    public  ImageView mIntimateFaBuIcon;
    // private Animation mButtonInAnimation;
    // private ImageView intimate_fabu_bg_iv,
    // mIntimateFabuCancle , mIntimateFabuChuanda,mIntimateFabuHuati;
    // private TextView mIntimateFabuChuandaTv,mIntimateFabuHuatiTv;
    // private View mIntimateFabuChuandaView,mIntimateFabuHuatiView;
    // public static View mIntemateFaBuRootView;

    public static MainFragment newInstance(Context context) {
        MainFragment mainFragment = new MainFragment();
        mContext = context;
        return mainFragment;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        // SharedPreferencesUtil.saveBooleanData(mContext, "undo_view4", true);
        View v = inflater.inflate(R.layout.activity_main, container, false);
        fm = getChildFragmentManager();
        rg_change = (RadioGroup) v.findViewById(R.id.main_radio_group);
        rg_change.setOnCheckedChangeListener(this);
        getMineInfoData(); // 获取我的足迹总数
        /* checkData(); */



        tab_one = (RadioButton) v.findViewById(R.id.tab_one);// 首页
        tab_two = (RadioButton) v.findViewById(R.id.tab_two);// 购物
        tab_three = (RadioButton) v.findViewById(R.id.tab_three);// 密友

        tv_tab3 = (TextView) v.findViewById(R.id.tv_tab3);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        systime_new = formatter.format(curDate);

        // tab_three2 = (RadioButton) v.findViewById(R.id.tab_three2);
        tab_four = (RadioButton) v.findViewById(R.id.tab_four);// 购物车
        tab_five = (RadioButton) v.findViewById(R.id.tab_five); // 我的
         mIntimateFaBuIcon = (ImageView)
         v.findViewById(R.id.intimate_fabu_btn_iv);
        // intimate_fabu_bg_iv = (ImageView)
        // v.findViewById(R.id.intimate_fabu_bg_iv);
        // mIntimateFabuCancle = (ImageView)
        // v.findViewById(R.id.intimate_fabu_btn_iv_cancle);
        // mIntimateFabuChuanda = (ImageView)
        // v.findViewById(R.id.intimate_fabu_btn_iv_chuanda);
        // mIntimateFabuChuandaTv = (TextView)
        // v.findViewById(R.id.intimate_fabu_btn_tv_chuanda);
        // mIntimateFabuHuati = (ImageView)
        // v.findViewById(R.id.intimate_fabu_btn_iv_huati);
        // mIntimateFabuHuatiTv = (TextView)
        // v.findViewById(R.id.intimate_fabu_btn_tv_huati);
        // mIntimateFabuChuandaView
        // =v.findViewById(R.id.intimate_fabu_btn_ll_chuanda);
        // mIntimateFabuHuatiView=
        // v.findViewById(R.id.intimate_fabu_btn_ll_huati);
        // mIntemateFaBuRootView = v.findViewById(R.id.intimate_fabu_root_view);
        tab_one.setOnClickListener(this);
        tab_two.setOnClickListener(this);
        tab_three.setOnClickListener(this);
        tab_four.setOnClickListener(this);
        tab_five.setOnClickListener(this);
         mIntimateFaBuIcon.setOnClickListener(this);
        // mIntimateFabuCancle.setOnClickListener(this);
        // mIntimateFabuChuanda.setOnClickListener(this);
        // mIntimateFabuHuati.setOnClickListener(this);
        // mButtonInAnimation = AnimationUtils.loadAnimation(mContext,
        // R.anim.intiamte_fabu_button_in);
        undo_view1 = (TextView) v.findViewById(R.id.undo_view1);
//        undo_view4 = (TextView) v.findViewById(R.id.undo_view4);
        undo_view5 = (TextView) v.findViewById(R.id.undo_view5);
        /**
         * switch (index) { case 0:
         * ((MainMenuActivity)(mContext)).getSlidingMenu
         * ().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
         * tab_one.setChecked(true); ft = fm.beginTransaction();
         * ft.replace(R.id.container,
         * MineShopFragment.newInstance("tab1",mContext));
         * ft.commitAllowingStateLoss(); break; case 1:
         * ((MainMenuActivity)(mContext
         * )).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
         * tab_two.setChecked(true); ft = fm.beginTransaction();
         * ft.replace(R.id.container,
         * MyShopFragment.newInstances("tab2",mContext),"2");
         * ft.commitAllowingStateLoss(); break; case 2:
         * ((MainMenuActivity)(mContext
         * )).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
         * tab_three.setChecked(true); ft = fm.beginTransaction();
         * ft.replace(R.id.container, CircleMainFragment.getIntance(mContext));
         * ft.commitAllowingStateLoss(); break; case 3:
         * tab_four.setChecked(true); // ft = fm.beginTransaction(); //
         * MyLogYiFu.e("TAG", "购物车frag"); // ft.replace(R.id.container,
         * ShopCartFragment.newInstance("tab4")); //
         * ft.commitAllowingStateLoss(); break; case 4:
         * tab_five.setChecked(true); ft = fm.beginTransaction();
         * ft.replace(R.id.container,
         * MineIfoFragment.newInstance("tab5",mContext));
         * ft.commitAllowingStateLoss(); break;
         *
         * default: break; }
         */

        // CheckSignButtonStyle();//签到按钮动画效果
        if (getChildFragmentManager().getFragments() == null || getChildFragmentManager().getFragments().isEmpty()) {
            setIndex(checkID);
        }

        // if (SharedPreferencesUtil.getBooleanData(mContext, "isLoginLogin",
        // false)) {
        // initH5Count();
        // }

        // LogYiFu.e("地址MAC-----安卓6.0.1", CheckStrUtil.getMac(mContext));
        // LogYiFu.e("地址IMEI-----安卓6.0.1", CheckStrUtil.getImei(mContext));

        // LogYiFu.e("currentDate", currentDate);
//        hiddTab();


        return v;
    }


//    private void hiddTab(){
//        rl_tab.setVisibility(View.GONE);
//        mIntimateFaBuIcon.setVisibility(View.GONE);
//        ll_white_back.setVisibility(View.GONE);
//
//        RelativeLayout.LayoutParams paramses= (RelativeLayout.LayoutParams) mContainer.getLayoutParams();
//        paramses.height= WindowManager.LayoutParams.MATCH_PARENT;
//        mContainer.setLayoutParams(paramses);
//    }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
//        initFans();
    }

     private void doSmallAnima() {
//     ScaleAnimation animation = new ScaleAnimation(1, 0.0f, 1, 0.0f,
//     Animation.RELATIVE_TO_SELF, 0.5f,
//     Animation.RELATIVE_TO_SELF, 0.5f);
//
//     animation.setDuration(150);
//
//     AnimationSet animationSet = new AnimationSet(false);
//
//     animationSet.addAnimation(animation);
//     mIntimateFaBuIcon.startAnimation(animationSet);
//
//     animation.setAnimationListener(new AnimationListener() {
//
//     @Override
//     public void onAnimationStart(Animation animation) {
//     // TODO Auto-generated method stub
//
//     }
//
//     @Override
//     public void onAnimationRepeat(Animation animation) {
//     // TODO Auto-generated method stub
//
//     }
//
//     @Override
//     public void onAnimationEnd(Animation animation) {
//     // TODO Auto-generated method stub
//     tab_three.setVisibility(View.VISIBLE);
//         mIntimateFaBuIcon.setVisibility(View.GONE);
//     }
//     });

         ObjectAnimator anim = ObjectAnimator.ofFloat(mIntimateFaBuIcon, "scaleX",  1.0F,0F).setDuration(150);
         anim.start();
         anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
             @Override
             public void onAnimationUpdate(ValueAnimator animation) {
                 float cVal = (Float) animation.getAnimatedValue();
                 mIntimateFaBuIcon.setAlpha(cVal);
                 mIntimateFaBuIcon.setScaleX(cVal);
                 mIntimateFaBuIcon.setScaleY(cVal);
             }
         });
         anim.addListener(new AnimatorListener() {
             @Override
             public void onAnimationStart(Animator animation) {

             }

             @Override
             public void onAnimationEnd(Animator animation) {
                 tab_three.setVisibility(View.VISIBLE);
                 mIntimateFaBuIcon.setVisibility(View.GONE);
             }

             @Override
             public void onAnimationCancel(Animator animation) {

             }

             @Override
             public void onAnimationRepeat(Animator animation) {

             }
         });
     }

     private void doBigAnima() {
//     ScaleAnimation animation = new ScaleAnimation(0, 1, 0, 1,
//     Animation.RELATIVE_TO_SELF, 0.5f,
//     Animation.RELATIVE_TO_SELF, 0.5f);
//
//     animation.setDuration(150);
//
//     AnimationSet animationSet = new AnimationSet(false);
//
//     animationSet.addAnimation(animation);
//     mIntimateFaBuIcon.startAnimation(animationSet);
         ObjectAnimator anim = ObjectAnimator.ofFloat(mIntimateFaBuIcon, "scaleX",  0.1F,0.2F,0.3F,0.4F,0.5F,
                  0.55F,0.6F,0.65F,0.7F,0.75F,0.8F,0.85F,0.9F,0.95F,1.0F,1.05F,1.1F,1.15F,1.1F,1.2F,1.1F,1.05F,1.0F,0.95F,1.0F).setDuration(300);
         anim.start();
         anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
             @Override
             public void onAnimationUpdate(ValueAnimator animation) {
                 float cVal = (Float) animation.getAnimatedValue();
                 mIntimateFaBuIcon.setAlpha(cVal);
                 mIntimateFaBuIcon.setScaleX(cVal);
                 mIntimateFaBuIcon.setScaleY(cVal);
             }
         });
     }

     private void doBigAnimaTabThree() {
//     ScaleAnimation animation = new ScaleAnimation(0, 1, 0, 1,
//     Animation.RELATIVE_TO_SELF, 0.5f,
//     Animation.RELATIVE_TO_SELF, 0.5f);
//
//     animation.setDuration(120);
//
//     AnimationSet animationSet = new AnimationSet(false);
//
//     animationSet.addAnimation(animation);
//     tab_three.startAnimation(animationSet);
         ObjectAnimator anim = ObjectAnimator.ofFloat(tab_three, "scaleX",  0F,1.0F).setDuration(120);
         anim.start();
         anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
             @Override
             public void onAnimationUpdate(ValueAnimator animation) {
                 float cVal = (Float) animation.getAnimatedValue();
                 tab_three.setAlpha(cVal);
                 tab_three.setScaleX(cVal);
                 tab_three.setScaleY(cVal);
             }
         });
     }

    // private void CheckSignButtonStyle() {
    //
    // tab_three2.setOnClickListener(new OnClickListener() {
    //
    // @Override
    // public void onClick(View v) {
    // // TODO Auto-generated method stub
    // // tab_three.setScaleX((float) 1.5);
    // // tab_three.setScaleY((float) 1.5);
    //
    // ScaleAnimation animation = new ScaleAnimation(0, 1, 0, 1,
    // Animation.RELATIVE_TO_SELF, 0.5f,
    // Animation.RELATIVE_TO_SELF, 0.5f);
    //
    // animation.setDuration(300);
    //
    // AnimationSet animationSet = new AnimationSet(false);
    //
    // animationSet.addAnimation(animation);
    // tab_three2.startAnimation(animationSet);
    //
    // }
    // });
    // }

    public void setCheckID(int checkID) {
        this.checkID = checkID;
    }

    private DialogExitThirty dialogCoupon;

    @Override
    public void onResume() {
        super.onResume();
        // HomeWatcherReceiver.registerHomeKeyReceiver(mContext);
            DialogUtils.getDiKouDialogNewTuanFail(mContext,"MainFragment显示");
            tabSignClick = false;

        if (YJApplication.instance.isLoginSucess() == false && YJApplication.isLogined == false) {
            if (checkID == 3) {
                checkID = 1;
                setCheckID();
            }
        }
        mfragment = this;

        // if (YJApplication.isLogined ||
        // YJApplication.instance.isLoginSucess()) {
        // // 拿到用户的类别、
        // GetUserABClass.getUserABclass(mContext);
        // }

        if (SharedPreferencesUtil.getBooleanData(mContext, "undo_view4", true)) {
            // initShopCart();
            setCartCount();
        }

        // if (YJApplication.isLogined ||
        // YJApplication.instance.isLoginSucess()) {
        // // 获取金币金券相关数据
        // GetJinBiJinQuanUtils.getJinBi(mContext);
        // GetJinBiJinQuanUtils.getJinQuan(mContext);
        // }
//		if (YJApplication.instance.isLoginSucess()) {
//			boolean isShow = SharedPreferencesUtil.getBooleanData(mContext,
//					Pref.FIVE_COUPON + YCache.getCacheUser(mContext).getUser_id(), false);
//			if (checkID == 0 && isHome && !isShow) {
//				get500Coupon();
//			}
//		}

    }

    public void get500Coupon() {
        new SAsyncTask<Integer, Void, HashMap<String, String>>((FragmentActivity) mContext, null, 0) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Integer... params)
                    throws Exception {
                // TODO Auto-generated method stub
                return ModQingfeng.getMessageCenterSystem(context);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result);

                if (e != null) {
                    return;
                }
                String coupon = result.get("5");

//				long now = System.currentTimeMillis();
                // 优惠券消息
                if (coupon.length() != 0 && !"".equals(coupon)) {
//					String contet = coupon.split("\\^")[0];
//					long time = Long.parseLong(coupon.split("\\^")[1]);
                    if (dialogCoupon == null) {
                        dialogCoupon = new DialogExitThirty(mContext, R.style.DialogStyle1);
                    }
                    if (!dialogCoupon.isShowing()) {
                        dialogCoupon.show();
                    }
                    SharedPreferencesUtil.saveBooleanData(mContext, Pref.FIVE_COUPON + YCache.getCacheUser(mContext).getUser_id(), true);
                    SharedPreferencesUtil.saveStringData(mContext, Pref.SYSTEM_MESSAGE + YCache.getCacheUser(mContext).getUser_id(), "1");
                }

            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            ;

        }.execute();

    }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.Fragment#onPause()
     */
    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        LogYiFu.e("主周期测试", "onPause");
        // HomeWatcherReceiver.unregisterHomeKeyReceiver(mContext);
    }

    private int CartCount;

    private void setCartCount() {
        ShopCartDao dao = new ShopCartDao(mContext);
        CartCount = dao.queryCartCount(mContext);
        CartCount = CartCount > 99 ? 99 : CartCount;
        handler.sendEmptyMessage(4);
    }

    // private void initShopCart(){
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
    // setCartCount(Integer.valueOf(count));
    // }
    // }.execute();
    // }
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            if (finCount == 0) {
                undo_view5.setVisibility(View.GONE);
            } else {
                if (!(mContext.getSharedPreferences("mine_point", Context.MODE_PRIVATE).getInt("mine_point",
                        0) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))) {
                    undo_view5.setVisibility(View.VISIBLE);
                }
                // undo_view5.setText(finCount + "");
            }

            // 判断是否开通小店 (喜好)
            // if(null==YCache.getCacheUser(context).getHobby()||YCache.getCacheUser(context).getHobby().equals("0")){
            //
            // }
            LogYiFu.e("zlj", null == YCache.getCacheUser(getActivity()) ? "true" : "false");
            boolean showCartVCount = SharedPreferencesUtil.getBooleanData(mContext, "undo_view4", true);
//            if (!showCartVCount || CartCount <= 0 || null == YCache.getCacheUser(getActivity())
//                    || !YJApplication.instance.isLoginSucess()) {
//                undo_view4.setVisibility(View.GONE);
//            } else {
//                undo_view4.setVisibility(View.VISIBLE);
//                undo_view4.setText(CartCount + "");
//            }
            if (msg.what != 1) {
                return;
            }
            if (h5Count == 0 || null == YCache.getCacheUser(getActivity())
                    || null == YCache.getCacheUser(getActivity()).getHobby()
                    || YCache.getCacheUser(getActivity()).getHobby().equals("0")) {
                undo_view1.setVisibility(View.GONE);
            } else {

                Boolean isshowstore = SharedPreferencesUtil.getBooleanData(mContext, Pref.ISSHOWSTORE, false);
                if (isshowstore) {
                    undo_view1.setVisibility(View.VISIBLE);
                    undo_view1.setText(h5Count + "");
                }

            }

        }

        ;
    };
    private int h5Count, finCount;

    public void setUndo(int h5Count, int finCount) {
        this.h5Count = h5Count;
        this.finCount = finCount;
        handler.sendEmptyMessage(1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void setRefresh() {
        ClassficationFragment fragment = (ClassficationFragment) getChildFragmentManager().findFragmentByTag("1");
        if (fragment != null) {
            // fragment.refresh();
        }
    }

    public void setCheckID() {
        setIndex(checkID);
    }

    public void setIndex(int index) {
        switch (index) {
            case 0:
                // SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TYPE,
                // "1");
                tab_one.setChecked(true);
                SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_HOME, "101");// 落地页是首页的
                TongJiUtils.TongJi(mContext, 1 + "");
                LogYiFu.e("TongJiNew", 1 + "");
                SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_MATCH, "104");// 首页专题和搭配是默认选中专题的
                TongJiUtils.TongJi(mContext, 4 + "");
                LogYiFu.e("TongJiNew", 4 + "");
                SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TIMES_HOME, "" + System.currentTimeMillis());
                break;
            case 1:
                // SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TYPE,
                // "2");
                tab_two.setChecked(true);
                break;
            case 2:
                // SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TYPE,
                // "3");
                tab_three.setChecked(true);
                SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_HOME, "108");// 落地页是密友圈的
                SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_MATCH, "104");// 首页专题和搭配是默认选中专题的
                TongJiUtils.TongJi(mContext, 8 + "");
                LogYiFu.e("TongJiNew", 8 + "");
                SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TIMES_INTIMATE, "" + System.currentTimeMillis());
                break;
            case 3:
                // SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TYPE,
                // "4");
                tab_four.setChecked(true);
                break;
            case 4:
                // SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TYPE,
                // "5");
                tab_five.setChecked(true);
                break;
        }
    }

    private void getMineInfoData() {
        YDBHelper help = new YDBHelper(getActivity());
        String sql = "select count(*) as counts from foot_print";
        List<String> list = help.queryToSimpleList(sql);
        if (list != null && list.size() != 0) {
            footPrintCount = list.get(0);
        }
    }

	/*
	 * private void checkData() { YDBHelper help = new YDBHelper(getActivity());
	 * String sql = "select * from sort_info"; MyLogYiFu.e("data.size",
	 * help.query(sql).size() + ""); sql = "select * from attr_info";
	 * MyLogYiFu.e("size", help.query(sql).size() + ""); sql =
	 * "select * from stock_type"; MyLogYiFu.e("size1111",
	 * help.query(sql).size() + ""); }
	 */

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btn_ok:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;

            case R.id.tab_one: // 首页
                if (!"101".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))
                        && !"102".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                    if (!TextUtils.isEmpty(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))
                            && !"200".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {// 其他界面的跳出统计
                        TongJiUtils.TongJi(mContext, SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""));
                        LogYiFu.e("TongJiNew", SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, "") + "");
                    }
                    if ("108".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                        Long nowTimesEnd = System.currentTimeMillis();
                        Long nowTimesStart = Long.parseLong(SharedPreferencesUtil.getStringData(mContext,
                                Pref.TONGJI_TIMES_INTIMATE, nowTimesEnd + ""));
                        Long duration = (nowTimesEnd - nowTimesStart) / 1000;// 以 秒
                        // 为单位
                        TongJiUtils.TongJiDuration(mContext, 1008 + "", duration + "");
                        LogYiFu.e("TongJiNew", duration + "秒 " + 1008);
                    }
                    if (HomePageFragment.cardRootView != null
                            && HomePageFragment.cardRootView.getVisibility() == View.VISIBLE) {
                        SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_HOME, "102");
                        TongJiUtils.TongJi(mContext, 2 + "");
                        LogYiFu.e("TongJiNew", 2 + "");// 首页精选推荐显示
                        Long nowTimes = System.currentTimeMillis();
                        SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TIMES_JINXUAN, "" + nowTimes);
                    } else {
                        SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_HOME, "101");
                        TongJiUtils.TongJi(mContext, 1 + "");
                        LogYiFu.e("TongJiNew", 1 + "");// 首页显示
                        Long nowTimes = System.currentTimeMillis();
                        SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TIMES_HOME, "" + nowTimes);
                        if ("104".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_MATCH, ""))) {
                            SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_MATCH, "104");
                            TongJiUtils.TongJi(mContext, 4 + "");
                            LogYiFu.e("TongJiNew", 4 + "");// 首页专题显示
                        } else if ("105".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_MATCH, ""))) {
                            SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_MATCH, "105");
                            TongJiUtils.TongJi(mContext, 5 + "");
                            LogYiFu.e("TongJiNew", 5 + "");// 首页搭配显示
                        }
                    }
                }
                YunYingTongJi.yunYingTongJi(mContext, 119);

                checked = 1;
                SharedPreferencesUtil.saveStringData(mContext, "TONGJI_SHOPCART", "0");
                // SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TYPE,
                // "-1");
                break;

            case R.id.tab_three: // 密友圈
                if (!"108".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                    if (!TextUtils.isEmpty(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))
                            && !"200".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                        TongJiUtils.TongJi(mContext, SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""));
                        LogYiFu.e("TongJiNew", SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, "") + "");
                    }
                    if ("101".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                        TongJiUtils.TongJi(mContext, SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_MATCH, ""));
                        LogYiFu.e("TongJiNew", SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_MATCH, "") + "");

                        Long nowTimesEnd = System.currentTimeMillis();
                        Long nowTimesStart = Long.parseLong(
                                SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_TIMES_HOME, nowTimesEnd + ""));
                        Long duration = (nowTimesEnd - nowTimesStart) / 1000;// 以 秒
                        // 为单位
                        TongJiUtils.TongJiDuration(mContext, 1001 + "", duration + "");
                        LogYiFu.e("TongJiNew", duration + "秒  " + 1001);
                    } else if ("102".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                        Long nowTimesEnd = System.currentTimeMillis();
                        Long nowTimesStart = Long.parseLong(
                                SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_TIMES_JINXUAN, nowTimesEnd + ""));
                        Long duration = (nowTimesEnd - nowTimesStart) / 1000;// 以 秒
                        // 为单位
                        TongJiUtils.TongJiDuration(mContext, 1002 + "", duration + "");
                        LogYiFu.e("TongJiNew", duration + "秒  " + 1002);
                    }
                    SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_HOME, "108");
                    TongJiUtils.TongJi(mContext, 8 + "");
                    LogYiFu.e("TongJiNew", 8 + "");
                    Long nowTimes = System.currentTimeMillis();
                    SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TIMES_INTIMATE, "" + nowTimes);
                }
                // tv_tab3.setVisibility(View.GONE);
                checked = 3;
                SharedPreferencesUtil.saveStringData(getActivity(), "first_animation", systime_new + "");
                // tab_three.setCompoundDrawablesWithIntrinsicBounds(null,
                // getResources().getDrawable(R.drawable.sign_gray),
                // null, null);
                // Boolean showSignUpdata =
                // SharedPreferencesUtil.getBooleanData(mContext,
                // Pref.SHOWSIGNUPDATA, false);
                // Boolean isshowedsignup =
                // SharedPreferencesUtil.getBooleanData(mContext,
                // Pref.ISSHOWEDSIGNUP, true);
                // if (isshowedsignup) {
                // if (showSignUpdata) {
                // ToastUtil.showLongText(mContext, "签到表已经更新啦~");
                // SharedPreferencesUtil.saveBooleanData(mContext,
                // Pref.SHOWSIGNUPDATA, false);
                // }
                //
                // }

                SharedPreferencesUtil.saveStringData(mContext, "TONGJI_SHOPCART", "0");
                // SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TYPE,
                // "-1");

                YunYingTongJi.yunYingTongJi(mContext, 121);

                // AnimationSet set = new AnimationSet(true);
                // set.addAnimation(alphaAnimation);
                // set.addAnimation(scaleAnimation);
                //
                //
                // this.tab_three.startAnimation(set);

                break;

            case R.id.tab_four: // 购物车
                if (!"110".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                    if (!TextUtils.isEmpty(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))
                            && !"200".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                        TongJiUtils.TongJi(mContext, SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""));
                        LogYiFu.e("TongJiNew", SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, "") + "");
                    }
                    if ("101".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                        TongJiUtils.TongJi(mContext, SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_MATCH, ""));
                        LogYiFu.e("TongJiNew", SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_MATCH, "") + "");

                        Long nowTimesEnd = System.currentTimeMillis();
                        Long nowTimesStart = Long.parseLong(
                                SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_TIMES_HOME, nowTimesEnd + ""));
                        Long duration = (nowTimesEnd - nowTimesStart) / 1000;// 以 秒
                        // 为单位
                        TongJiUtils.TongJiDuration(mContext, 1001 + "", duration + "");
                        LogYiFu.e("TongJiNew", duration + "秒  " + 1001);
                    } else if ("102".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                        Long nowTimesEnd = System.currentTimeMillis();
                        Long nowTimesStart = Long.parseLong(
                                SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_TIMES_JINXUAN, nowTimesEnd + ""));
                        Long duration = (nowTimesEnd - nowTimesStart) / 1000;// 以 秒
                        // 为单位
                        TongJiUtils.TongJiDuration(mContext, 1002 + "", duration + "");
                        LogYiFu.e("TongJiNew", duration + "秒  " + 1002);
                    } else if ("108".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                        Long nowTimesEnd = System.currentTimeMillis();
                        Long nowTimesStart = Long.parseLong(SharedPreferencesUtil.getStringData(mContext,
                                Pref.TONGJI_TIMES_INTIMATE, nowTimesEnd + ""));
                        Long duration = (nowTimesEnd - nowTimesStart) / 1000;// 以 秒
                        // 为单位
                        TongJiUtils.TongJiDuration(mContext, 1008 + "", duration + "");
                        LogYiFu.e("TongJiNew", duration + "秒  " + 1008);
                    }
                    SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_HOME, "110");
                    TongJiUtils.TongJi(mContext, 10 + "");
                    LogYiFu.e("TongJiNew", 10 + "");
                }
                YunYingTongJi.yunYingTongJi(mContext, 122);
                checked = 4;
                SharedPreferencesUtil.saveStringData(mContext, "TONGJI_SHOPCART", "1");
                // SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TYPE,
                // "1052");
                break;

            case R.id.tab_five: // 我的
                if (!"200".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                    // 我的只统计到此界面其他界面的跳出
                    if (!TextUtils.isEmpty(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                        TongJiUtils.TongJi(mContext, SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""));
                        LogYiFu.e("TongJiNew", SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, "") + "");
                    }
                    if ("101".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                        TongJiUtils.TongJi(mContext, SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_MATCH, ""));
                        LogYiFu.e("TongJiNew", SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_MATCH, "") + "");

                        Long nowTimesEnd = System.currentTimeMillis();
                        Long nowTimesStart = Long.parseLong(
                                SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_TIMES_HOME, nowTimesEnd + ""));
                        Long duration = (nowTimesEnd - nowTimesStart) / 1000;// 以 秒
                        // 为单位
                        TongJiUtils.TongJiDuration(mContext, 1001 + "", duration + "");
                        LogYiFu.e("TongJiNew", duration + "秒  " + 1001);
                    } else if ("102".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                        Long nowTimesEnd = System.currentTimeMillis();
                        Long nowTimesStart = Long.parseLong(
                                SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_TIMES_JINXUAN, nowTimesEnd + ""));
                        Long duration = (nowTimesEnd - nowTimesStart) / 1000;// 以 秒
                        // 为单位
                        TongJiUtils.TongJiDuration(mContext, 1002 + "", duration + "");
                        LogYiFu.e("TongJiNew", duration + "秒  " + 1002);
                    } else if ("108".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                        Long nowTimesEnd = System.currentTimeMillis();
                        Long nowTimesStart = Long.parseLong(SharedPreferencesUtil.getStringData(mContext,
                                Pref.TONGJI_TIMES_INTIMATE, nowTimesEnd + ""));
                        Long duration = (nowTimesEnd - nowTimesStart) / 1000;// 以 秒
                        // 为单位
                        TongJiUtils.TongJiDuration(mContext, 1008 + "", duration + "");
                        LogYiFu.e("TongJiNew", duration + "秒  " + 1008);
                    }
                    SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_HOME, "200");
                }
                YunYingTongJi.yunYingTongJi(mContext, 123);

                checked = 5;
                SharedPreferencesUtil.saveStringData(mContext, "TONGJI_SHOPCART", "0");
                // SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TYPE,
                // "-1");

                DialogUtils.firstPTfailDialog(mContext);


                break;

            case R.id.tab_two: // 购物
                if (!"107".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                    if (!TextUtils.isEmpty(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))
                            && !"200".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                        TongJiUtils.TongJi(mContext, SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""));
                        LogYiFu.e("TongJiNew", SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, "") + "");
                    }
                    if ("101".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                        TongJiUtils.TongJi(mContext, SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_MATCH, ""));
                        LogYiFu.e("TongJiNew", SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_MATCH, "") + "");

                        Long nowTimesEnd = System.currentTimeMillis();
                        Long nowTimesStart = Long.parseLong(
                                SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_TIMES_HOME, nowTimesEnd + ""));
                        Long duration = (nowTimesEnd - nowTimesStart) / 1000;// 以 秒
                        // 为单位
                        TongJiUtils.TongJiDuration(mContext, 1001 + "", duration + "");
                        LogYiFu.e("TongJiNew", duration + "秒" + 1001);
                    } else if ("102".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                        Long nowTimesEnd = System.currentTimeMillis();
                        Long nowTimesStart = Long.parseLong(
                                SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_TIMES_JINXUAN, nowTimesEnd + ""));
                        Long duration = (nowTimesEnd - nowTimesStart) / 1000;// 以 秒
                        // 为单位
                        TongJiUtils.TongJiDuration(mContext, 1002 + "", duration + "");
                        LogYiFu.e("TongJiNew", duration + "秒" + 1002);
                    } else if ("108".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
                        Long nowTimesEnd = System.currentTimeMillis();
                        Long nowTimesStart = Long.parseLong(SharedPreferencesUtil.getStringData(mContext,
                                Pref.TONGJI_TIMES_INTIMATE, nowTimesEnd + ""));
                        Long duration = (nowTimesEnd - nowTimesStart) / 1000;// 以 秒
                        // 为单位
                        TongJiUtils.TongJiDuration(mContext, 1008 + "", duration + "");
                        LogYiFu.e("TongJiNew", duration + "秒" + 1008);
                    }
                    SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_HOME, "107");
                    TongJiUtils.TongJi(mContext, 7 + "");
                    LogYiFu.e("TongJiNew", 7 + "");
                }

                YunYingTongJi.yunYingTongJi(mContext, 120);

                checked = 2;
                // gouwu dapei
                String sss = SharedPreferencesUtil.getStringData(mContext, Pref.GOUWUPAGE_CURRENT_PAGER, "-1");
                if (sss.equals("gouwu")) {
                    SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TYPE, "1001");
                    SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_PAGE, "搭配");
                }
                //

                // 如果登录弹出红包
                SharedPreferencesUtil.saveStringData(mContext, "TONGJI_SHOPCART", "");



                SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd");
                Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
                systime_now = formatter.format(curDate);

                if (YJApplication.instance.isLoginSucess()) {
                    String time_pass = SharedPreferencesUtil.getStringData(getActivity(), "first_match_time", "-1");

                    String user_string = SharedPreferencesUtil.getStringData(getActivity(), "user_string", "");

                    // 获取版本
                    try {
                        PackageManager packageManager = getActivity().getPackageManager();
                        PackageInfo packInfo = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
                        int version = packInfo.versionCode;

                        // 取出之前存的版本号
                        String version_num = SharedPreferencesUtil.getStringData(getActivity(), "version_num", "-1");

                        if (!version_num.equals(version + "")) {
                            SharedPreferencesUtil.saveStringData(getActivity(), "version_num", version + "");

                            // 30弹窗
                            // DialogExitThirty dialogExitThirty = new
                            // DialogExitThirty(getActivity(), R.style.DialogStyle1,
                            // true, "", "", "");
                            // dialogExitThirty.show();

                        }

                    } catch (NameNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    MatchFragment.hashSet.clear();
                    if (!user_string.equals("") || user_string != null) {
                        String[] split = user_string.split(",");
                        for (int i = split.length - 1; i >= 0; i--) {
                            MatchFragment.hashSet.add(split[i]);
                        }
                    }
                    UserInfo userInfo = YCache.getCacheUser(mContext);
                    String user_id = userInfo.getUser_id() + "";

                    if (!time_pass.equals(systime_now)) {
                        MatchFragment.hashSet.clear();
                    }

                    if (!MatchFragment.hashSet.contains(user_id)) {
                        // diyongquan();
                        MatchFragment.hashSet.add(user_id);
                        SharedPreferencesUtil.saveStringData(getActivity(), "first_match_time", systime_now);

                        StringBuffer stringBuffer = new StringBuffer();

                        for (int i = MatchFragment.hashSet.size() - 1; i >= 0; i--) {
                            String integer = MatchFragment.hashSet.get(i);

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
                break;
            case R.id.show_fans_ll:
                // 跳至赚钱
                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
                startActivity(new Intent(mContext, CommonActivity.class));
                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                break;
            // case R.id.intimate_fabu_btn_iv: // 发布密友圈()
            //
            //
            //// ObjectAnimator anim0 = ObjectAnimator.ofFloat(mIntimateFabuCancle,
            // "", 0.7F,0.75F,0.8F,0.85F,0.9F,0.95F, 1.0F).setDuration(60);
            //// anim0.start();
            //// anim0.addUpdateListener(new AnimatorUpdateListener(){
            //// @Override
            //// public void onAnimationUpdate(ValueAnimator animation) {
            //// float cVal = (Float) animation.getAnimatedValue();
            //// mIntimateFabuCancle.setAlpha(cVal);
            //// mIntimateFabuCancle.setScaleX(cVal);
            //// mIntimateFabuCancle.setScaleY(cVal);
            //// }
            //// });
            // ObjectAnimator anim=ObjectAnimator.ofFloat(mIntimateFabuCancle,
            // "rotation",
            // -90,-85,-80,-75,-70,-65,-60,-55,-50,-45,-40,-30,-30,-25,-20,-15,-10,0,5,10,20);
            // final ObjectAnimator
            // anim_2=ObjectAnimator.ofFloat(mIntimateFabuCancle,
            // "rotation",15,10,5,0,-10,-15,-10,0);
            // anim.setDuration(130).setStartDelay(10);
            // anim.start();
            // anim_2.setDuration(300);
            // anim.addListener(new AnimatorListener() {
            //
            // @Override
            // public void onAnimationStart(Animator animation) {
            //
            // }
            //
            // @Override
            // public void onAnimationRepeat(Animator animation) {
            //
            // }
            //
            // @Override
            // public void onAnimationEnd(Animator animation) {
            // anim_2.start();
            // }
            //
            // @Override
            // public void onAnimationCancel(Animator animation) {
            //
            // }
            // });
            // mButtonInAnimation.setStartOffset(10);
            // mButtonInAnimation.setStartOffset(10);
            // mIntimateFabuChuandaView.startAnimation(mButtonInAnimation);
            // mIntimateFabuHuatiView.startAnimation(mButtonInAnimation);
            // ((Activity)
            // mContext).getWindow().getDecorView().getRootView().setDrawingCacheEnabled(true);//截屏
            // 并设置缓存
            // Bitmap bitmap_bg = ((Activity)
            // mContext).getWindow().getDecorView().getRootView().getDrawingCache();
            // Bitmap sentBitmap = bitmap_bg.copy(Bitmap.Config.ARGB_8888, true);
            // /**
            // * 加大模糊程度
            // * 增大scaleRatio缩放比，使用一样更小的bitmap去虚化可以得到更好的模糊效果，而且有利于占用内存的减小；
            // * 增大blurRadius，可以得到更高程度的虚化，不过会导致CPU更加intensive
            // */
            //
            // int scaleRatio = 5;
            // int blurRadius = 8;
            // Bitmap scaledBitmap = Bitmap.createScaledBitmap(sentBitmap,
            // sentBitmap.getWidth() / scaleRatio,
            // sentBitmap.getHeight() / scaleRatio, false);
            // Bitmap blurBitmap = FastBlur.doBlur(scaledBitmap, blurRadius, true);
            // intimate_fabu_bg_iv.setImageBitmap(blurBitmap);
            // mIntemateFaBuRootView.setVisibility(View.VISIBLE);
            //// ObjectAnimator
            // anim1=ObjectAnimator.ofFloat(mIntimateFabuChuandaView, "tra",
            // -90,-85,-80,-75,-70,-65,-60,-55,-50,-45,-40,-30,-30,-25,20,15,10,0,10,0,10,0);
            //// ObjectAnimator anim1 = ObjectAnimator.ofFloat(mIntimateFabuChuanda,
            // "", 0.6F,0.65F,0.7F,0.75F,0.8F,0.85F,0.9F,0.95F,
            // 1.0F).setDuration(80);
            //// anim1.start();
            //// anim1.addUpdateListener(new AnimatorUpdateListener(){
            //// @Override
            //// public void onAnimationUpdate(ValueAnimator animation) {
            //// float cVal = (Float) animation.getAnimatedValue();
            //// mIntimateFabuChuanda.setAlpha(cVal);
            //// mIntimateFabuChuanda.setScaleX(cVal);
            //// mIntimateFabuChuanda.setScaleY(cVal);
            //// }
            //// });
            //// ObjectAnimator anim12 =
            // ObjectAnimator.ofFloat(mIntimateFabuChuandaTv, "",
            // 0.6F,0.65F,0.7F,0.75F,0.8F,0.85F,0.9F,0.95F, 1.0F).setDuration(80);
            //// anim12.start();
            //// anim12.addUpdateListener(new AnimatorUpdateListener(){
            //// @Override
            //// public void onAnimationUpdate(ValueAnimator animation) {
            //// float cVal = (Float) animation.getAnimatedValue();
            //// mIntimateFabuChuandaTv.setAlpha(cVal);
            //// mIntimateFabuChuandaTv.setScaleX(cVal);
            //// mIntimateFabuChuandaTv.setScaleY(cVal);
            //// }
            //// });
            //// ObjectAnimator anim2 = ObjectAnimator.ofFloat(mIntimateFabuHuati,
            // "", 0.5F,
            // 0.55F,0.6F,0.65F,0.7F,0.75F,0.8F,0.85F,0.9F,0.95F,1.0F).setDuration(130);
            //// anim2.start();
            //// anim2.addUpdateListener(new AnimatorUpdateListener(){
            //// @Override
            //// public void onAnimationUpdate(ValueAnimator animation) {
            //// float cVal = (Float) animation.getAnimatedValue();
            //// mIntimateFabuHuati.setAlpha(cVal);
            //// mIntimateFabuHuati.setScaleX(cVal);
            //// mIntimateFabuHuati.setScaleY(cVal);
            //// }
            //// });
            //// ObjectAnimator anim22 =
            // ObjectAnimator.ofFloat(mIntimateFabuHuatiTv, "", 0.5F,
            // 0.55F,0.6F,0.65F,0.7F,0.75F,0.8F,0.85F,0.9F,0.95F,1.0F).setDuration(130);
            //// anim22.start();
            //// anim22.addUpdateListener(new AnimatorUpdateListener(){
            //// @Override
            //// public void onAnimationUpdate(ValueAnimator animation) {
            //// float cVal = (Float) animation.getAnimatedValue();
            //// mIntimateFabuHuatiTv.setAlpha(cVal);
            //// mIntimateFabuHuatiTv.setScaleX(cVal);
            //// mIntimateFabuHuatiTv.setScaleY(cVal);
            //// }
            //// });
            // break;
            // case R.id.intimate_fabu_btn_iv_cancle: // 取消发布密友圈
            // ((Activity)
            // mContext).getWindow().getDecorView().getRootView().destroyDrawingCache();//销毁截屏缓存
            // mIntemateFaBuRootView.setVisibility(View.GONE);
            // break;
            // case R.id.intimate_fabu_btn_iv_chuanda: // 发布密友圈穿搭
            // //TODO 发布密友圈穿搭
            // mIntimateFabuCancle.performClick();
            // Intent intnetOutfit = new Intent(mContext,IssueOutfitActivity.class);
            // ((FragmentActivity) mContext).startActivity(intnetOutfit);
            // ((FragmentActivity)mContext).overridePendingTransition(R.anim.slide_left_in,
            // R.anim.slide_match);
            // break;
            // case R.id.intimate_fabu_btn_iv_huati: // 发布密友圈话题
            // //TODO 发布密友圈话题
            // mIntimateFabuCancle.performClick();
            // Intent intnetTopic = new Intent(mContext,IssueTopicActivity.class);
            // ((FragmentActivity) mContext).startActivity(intnetTopic);
            // ((FragmentActivity)mContext).overridePendingTransition(R.anim.slide_left_in,
            // R.anim.slide_match);
            //
            // break;

             case R.id.intimate_fabu_btn_iv: // 发布密友圈()
                    if(FriendsFragment.mIntimateFabuIcon!=null){
                        FriendsFragment.mIntimateFabuIcon.performClick();
                    }
                 break;
            default:
                break;
        }

    }

    // private void diyongquan() {
    //
    // new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity)
    // mContext, R.string.wait) {
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
    // Boolean appear = SharedPreferencesUtil.getBooleanData(getActivity(),
    // "appear", false);
    // if (appear == false) {
    //
    // if (num.equals("0")) { // 第一次弹80抵用券
    // EightyDialog eightyDialog = new EightyDialog(mContext);
    // eightyDialog.show();
    // } else { // 不是第一次弹30抵用券
    // ThirtyDialog thirtyDialog = new ThirtyDialog(mContext);
    // thirtyDialog.show();
    // }
    // }
    // }
    //
    // }
    //
    // }.execute();
    // }

    private int checkID = 0;
    private boolean isHome = true;
    // private boolean isRemove;

    private boolean tabSignClick;

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        // 小米mix重启，这个dialog不需要了

        // if (dialog == null) {
        // dialog = new CheckPressDiaolg(mContext);
        // }
        // dialog.show();
        // Timer timer = new Timer();
        // timer.schedule(new TimerTask() {
        //
        // @Override
        // public void run() {
        // if (dialog.isShowing()) {
        // dialog.dismiss();
        // }
        // }
        // }, 200);

        ft = fm.beginTransaction();
        switch (checkedId) {
            case R.id.tab_one: // 首页

                if(!tabSignClick){
                    DialogUtils.getDiKouDialogNewTuanFail(mContext,"Tab点击");
                    tabSignClick = false;
                }


                // signBTcallBck.signBTcilick();
                if (flag == true) {
                     doSmallAnima();
                    tab_three.setVisibility(View.VISIBLE);
                    // tab_three2.setVisibility(View.GONE);
                     doBigAnimaTabThree();
                    flag = false;
//                    mIntimateFaBuIcon.setVisibility(View.GONE);
                }
                checkID = 0;
                isHome = true;
                // ((MainMenuActivity)
                // (mContext)).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

                if (getChildFragmentManager().findFragmentByTag("2") != null) {
                    ft.show(getChildFragmentManager().findFragmentByTag("2"));
                } else {
                    ft.add(R.id.container, HomePageFragment.newInstances("tab2", mContext), "2");// 购物
                }
                if (getChildFragmentManager().findFragmentByTag("1") != null) {
                    // isRemove = false;
                    ft.hide(getChildFragmentManager().findFragmentByTag("1"));
                }
                if (getChildFragmentManager().findFragmentByTag("4") != null) {
                    ft.hide(getChildFragmentManager().findFragmentByTag("4"));
                }
                if (getChildFragmentManager().findFragmentByTag("3") != null) {
                    ft.hide(getChildFragmentManager().findFragmentByTag("3"));
                    // ft.remove(getChildFragmentManager().findFragmentByTag("3"));
                }
                if (getChildFragmentManager().findFragmentByTag("5") != null) {
                    ft.hide(getChildFragmentManager().findFragmentByTag("5"));
                }
                ft.commitAllowingStateLoss();
//			if (YJApplication.instance.isLoginSucess()) {
//				boolean isShow = SharedPreferencesUtil.getBooleanData(mContext,
//						Pref.FIVE_COUPON + YCache.getCacheUser(mContext).getUser_id(), false);
//				if (checkID == 0 && isHome && !isShow) {
//					get500Coupon();
//				}
//			}
                break;
            case R.id.tab_two:// ------------购物
                DialogUtils.getDiKouDialogNewTuanFail(mContext,"Tab点击");


                if (flag == true) {
                     doSmallAnima();
                    tab_three.setVisibility(View.VISIBLE);
                    // tab_three2.setVisibility(View.GONE);
                     doBigAnimaTabThree();
                    flag = false;
//                    mIntimateFaBuIcon.setVisibility(View.GONE);
                }


                if (flag == true) {
                    // doSmallAnima();
                    tab_three.setVisibility(View.VISIBLE);
                    // tab_three2.setVisibility(View.GONE);
                    // doBigAnimaTabThree();
                    flag = false;
                    mIntimateFaBuIcon.setVisibility(View.GONE);
                }


                checkID = 1;
                // ((MainMenuActivity)
                // (mContext)).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                // if(YJApplication.instance.isLoginSucess()==false){
                // Intent intent =new Intent(getActivity(),LoginActivity.class);
                // intent.putExtra("login_register", "login");
                // getActivity().startActivityForResult(intent, 236);
                // setIndex(checkID);
                // return ;
                // }
                if (null != YCache.getCacheUserSafe(mContext)) {
                    mContext.getSharedPreferences(Pref.CURRENT_DATE, 0).edit()
                            .putString(YCache.getCacheUserSafe(mContext).getUser_id() + "",
                                    new SimpleDateFormat("yyyyMMdd").format(new Date().getTime()))
                            .commit();
                    undo_view1.setVisibility(View.GONE);
                }
                // if (isRemove) {
                // ft.add(R.id.container, ClassficationFragment.newInstance("tab1",
                // mContext), "1");// 分类 （之前小店的位置）
                // } else {
                if (getChildFragmentManager().findFragmentByTag("1") != null) {
                    ft.show(getChildFragmentManager().findFragmentByTag("1"));
                } else {
                    ft.add(R.id.container, ClassficationFragment.newInstance("tab1", mContext), "1");// 购物---老分类
                }
                // }

                if (getChildFragmentManager().findFragmentByTag("4") != null) {
                    ft.hide(getChildFragmentManager().findFragmentByTag("4"));
                }
                if (getChildFragmentManager().findFragmentByTag("2") != null) {
                    ft.hide(getChildFragmentManager().findFragmentByTag("2"));
                }
                if (getChildFragmentManager().findFragmentByTag("3") != null) {
                    ft.hide(getChildFragmentManager().findFragmentByTag("3"));
                    // ft.remove(getChildFragmentManager().findFragmentByTag("3"));
                }
                if (getChildFragmentManager().findFragmentByTag("5") != null) {
                    ft.hide(getChildFragmentManager().findFragmentByTag("5"));
                }
                ft.commitAllowingStateLoss();

                // if ((Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 6)
                // && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == mContext
                // .getSharedPreferences("EverydayTaskMondayFridayAddLike",
                // Context.MODE_PRIVATE).getInt("day",
                // 0)) {
                // if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == mContext
                // .getSharedPreferences("EverydayTaskMondayFridayMyShop",
                // Context.MODE_PRIVATE)
                // .getInt("day", 0)) {
                // return;
                // }
                // if (!YJApplication.instance.isLoginSucess()) {
                // return;
                // }
                // UserInfo userInfo = YCache.getCacheUserSafe(mContext);
                // if (null == userInfo.getHobby() ||
                // userInfo.getHobby().equals("0")) {
                // return;
                // }
                //
                // NewPDialog dialog = new NewPDialog(mContext,
                // R.layout.every_day_task_dialog8_2);
                // dialog.setF(new NewPDialog.FinishLintener() {
                //
                // @Override
                // public void onFinishClickLintener() {
                // // TODO Auto-generated method stub
                // mContext.getSharedPreferences("EverydayTaskMondayFridayMyShop",
                // Context.MODE_PRIVATE).edit()
                // .putInt("day",
                // Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
                // mContext.getSharedPreferences("EverydayTaskMondayFriday",
                // Context.MODE_PRIVATE).edit()
                // .putInt("day",
                // Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
                // }
                // });
                // dialog.setL(new NewPDialog.TaskLintener() {
                //
                // @Override
                // public void onOKClickLintener() {
                // mContext.getSharedPreferences("EverydayTaskMondayFridayMyShop",
                // Context.MODE_PRIVATE).edit()
                // .putInt("day",
                // Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
                // mContext.getSharedPreferences("EverydayTaskMondayFriday",
                // Context.MODE_PRIVATE).edit()
                // .putInt("day",
                // Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
                // }
                //
                // @Override
                // public void onShouZhiClickLintener() {
                //// ClassificationFragment.shareStoreLink(mContext);
                // mContext.getSharedPreferences("EverydayTaskMondayFridayMyShop",
                // Context.MODE_PRIVATE).edit()
                // .putInt("day",
                // Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
                // mContext.getSharedPreferences("EverydayTaskMondayFriday",
                // Context.MODE_PRIVATE).edit()
                // .putInt("day",
                // Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
                // }
                // });
                // dialog.show();
                //
                // }

                break;
            case R.id.tab_three: // 密友圈

                //申请供货
                SharedPreferencesUtil.saveStringData(getActivity(), "commonactivityfrom", "contributions");
                Intent intentSign = new Intent(getActivity(), CommonActivity.class);
                getActivity().startActivity(intentSign);
                getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                setIndex(checkID);

                //申请供款状态
//                SharedPreferencesUtil.saveStringData(getActivity(), "commonactivityfrom", "contributionstatus");
//                Intent intentSign = new Intent(getActivity(), CommonActivity.class);
//                getActivity().startActivity(intentSign);
//                getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//                setIndex(checkID);


//                if(!tabSignClick){
//                    DialogUtils.getDiKouDialogNewTuanFail(mContext,"Tab点击");
//                    tabSignClick = false;
//                }
//
//
//                flag = true;
//                // tab_three.setVisibility(View.GONE);
//                // tab_three2.setScaleX((float) 0.7);
//                // tab_three2.setScaleY((float) 0.9);
//                // tab_three2.setVisibility(View.VISIBLE);
//                if (mFirstFlag) {// 第一次进来不做动画，以后做动画（赚钱图标）
//                    // doBigAnima();
//                }
//                mFirstFlag = true;
//                doBigAnima();
//                mIntimateFaBuIcon.setVisibility(View.VISIBLE);
//                checkID = 2;
//                // ((MainMenuActivity)
//                // (mContext)).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
//
//                if (getChildFragmentManager().findFragmentByTag("3") != null) {
//                    ft.show(getChildFragmentManager().findFragmentByTag("3"));
//                } else {
//                    // ft.add(R.id.container,
//                    // SignFragment.newInstance(mContext),//圈子
//                    // "3");
//                    // ft.add(R.id.container,
//                    // ZeroShopFragment.newInstance("tab3", mContext), "3");
//                    // SignFragment signFragment =
//                    // SignFragment.newInstance(mContext);
//                    FriendsFragment friendsFragment = FriendsFragment.newInstance(mContext);
//                    Bundle bundle = getArguments();
//                    if (bundle != null) {
//                        signShopDetail = bundle.getString("signShopDetail");
//                        signType = bundle.getString("signType");
//                        isDuobao = bundle.getBoolean("isDuobao");
//                        CanYunumber = bundle.getString("CanYunumber");
//                        // id = bundle.getInt("Signidd", 0);
//                        // nextID = bundle.getInt("SignnextID", 0);
//                        now_type_id = bundle.getInt("now_type_id", 0);
//                        now_type_id_value = bundle.getInt("now_type_id_value", 0);
//                        next_type_id = bundle.getInt("next_type_id", 0);
//                        next_type_id_value = bundle.getInt("next_type_id_value", 0);
//                    }
//                    if ("SignShopDetail".equals(signShopDetail) || isDuobao && signType != "0") {
//                        bundle.clear();
//                        bundle.putString("signShopDetail", signShopDetail);
//                        bundle.putString("signType", signType);
//                        bundle.putString("CanYunumber", CanYunumber);
//                        bundle.putBoolean("isDuobao", isDuobao);
//                        bundle.putInt("now_type_id", now_type_id);
//                        bundle.putInt("now_type_id_value", now_type_id_value);
//                        bundle.putInt("next_type_id", next_type_id);
//                        bundle.putInt("next_type_id_value", next_type_id_value);
//                        // signFragment.setArguments(bundle);
//                        friendsFragment.setArguments(bundle);
//                    }
//                    ft.add(R.id.container, friendsFragment, // 密友圈
//                            "3");
//                }
//                if (getChildFragmentManager().findFragmentByTag("1") != null) {
//                    // isRemove = true;
//                    // ft.remove(getChildFragmentManager().findFragmentByTag("1"));
//                    ft.hide(getChildFragmentManager().findFragmentByTag("1"));
//                }
//                if (getChildFragmentManager().findFragmentByTag("2") != null) {
//                    ft.hide(getChildFragmentManager().findFragmentByTag("2"));
//                }
//                if (getChildFragmentManager().findFragmentByTag("4") != null) {
//                    ft.hide(getChildFragmentManager().findFragmentByTag("4"));
//                }
//                if (getChildFragmentManager().findFragmentByTag("5") != null) {
//                    ft.hide(getChildFragmentManager().findFragmentByTag("5"));
//                }
//                ft.commitAllowingStateLoss();
//
//                // if (tab_three2.getVisibility() == View.GONE) {
//                tv_tab3.setVisibility(View.VISIBLE);
//                // } else {
//                // tv_tab3.setVisibility(View.GONE);
//                // }
//                // tab_three.setCompoundDrawablesWithIntrinsicBounds(null,
//                // getResources().getDrawable(R.drawable.sign_gray),
//                // null, null);
                break;
            case R.id.tab_four:// 购物车--调至赚钱
                tabSignClick = true;
                // 跳至赚钱

//                SharedPreferencesUtil.saveStringData(getActivity(), "commonactivityfrom", "sign");
//                Intent intentSign = new Intent(getActivity(), CommonActivity.class);
//                getActivity().startActivity(intentSign);
//                getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//                setIndex(checkID);

                if (flag == true&&YJApplication.instance.isLoginSucess()) {
                     doSmallAnima();
                    tab_three.setVisibility(View.VISIBLE);
                    // tab_three2.setVisibility(View.GONE);
                     doBigAnimaTabThree();
                    flag = false;
//                    mIntimateFaBuIcon.setVisibility(View.GONE);
                }

                if (YJApplication.instance.isLoginSucess() == false && YJApplication.isLogined == false) {
                    if (checkID == 3) {
                        checkID = 1;
                        setCheckID();
                        break;
                    }

                    if (LoginActivity.instances != null) {
                        LoginActivity.instances.finish();
                    }
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra("login_register", "login");
                    intent.putExtra("shopcart", "shopcart");
                    getActivity().startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                     setIndex(checkID);
                    LogYiFu.e("keyi", "yumen");
                    break;
                }
                checkID = 3;
                // ((MainMenuActivity)
                // (mContext)).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                if (null != YCache.getCacheUserSafe(mContext)) {
                    SharedPreferencesUtil.saveBooleanData(mContext, "undo_view4", false);
//                    undo_view4.setVisibility(View.GONE);
                }
                // if (getChildFragmentManager().findFragmentByTag("4") != null) {
                // //是否需要刷新的判断
                // ft.show(getChildFragmentManager().findFragmentByTag("4"));
                // } else {

                // // ft.add(R.id.container,
                // // ZeroShopFragment.newInstance("tab4", mContext), "4");
                //
                // // ft.add(R.id.container,

                // ZeroShopFragment.newInstance("tab4", mContext), "4");// 特卖
                ft.add(R.id.container, ShopCartNewNewFragment.newInstance("tab4", mContext), "4");// 购物车
                // }
                if (getChildFragmentManager().findFragmentByTag("1") != null) {
                    // isRemove = false;
                    ft.hide(getChildFragmentManager().findFragmentByTag("1"));
                }
                if (getChildFragmentManager().findFragmentByTag("2") != null) {
                    ft.hide(getChildFragmentManager().findFragmentByTag("2"));
                }
                if (getChildFragmentManager().findFragmentByTag("3") != null) {
                    ft.hide(getChildFragmentManager().findFragmentByTag("3"));
                    // ft.remove(getChildFragmentManager().findFragmentByTag("3"));
                }
                if (getChildFragmentManager().findFragmentByTag("5") != null) {
                    ft.hide(getChildFragmentManager().findFragmentByTag("5"));
                }
                ft.commitAllowingStateLoss();
                // if (YJApplication.instance.isLoginSucess() == false) {
                // Intent intent = new Intent(mContext, LoginActivity.class);
                // intent.putExtra("login_register", "login");
                // ((FragmentActivity) mContext).startActivityForResult(intent,
                // 236);
                // return;
                // }

                // if (tab_three2.getVisibility() == View.GONE) {
                // tv_tab3.setVisibility(View.VISIBLE);
                // }else {
                // tv_tab3.setVisibility(View.GONE);
                // }
                break;
            case R.id.tab_five:


                if(!tabSignClick){
                    DialogUtils.getDiKouDialogNewTuanFail(mContext,"Tab点击");
                    tabSignClick = false;
                }

                isHome = false;
                if (YJApplication.instance.isLoginSucess() == true) {
                    undo_view5.setVisibility(View.GONE);
                    getActivity().getSharedPreferences("mine_point", Context.MODE_PRIVATE).edit()
                            .putInt("mine_point", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();

                // if(YJApplication.instance
                // .isLoginSucess() == false){
                //
                // }else{
                //
                // }
                if (flag == true) {
                     doSmallAnima();
                    tab_three.setVisibility(View.VISIBLE);
                    // tab_three2.setVisibility(View.GONE);
                     doBigAnimaTabThree();
                    flag = false;
//                    mIntimateFaBuIcon.setVisibility(View.GONE);
                }
                }
                // ((MainMenuActivity)
                // (mContext)).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                if (YJApplication.instance.isLoginSucess() == false) {

                    if (LoginActivity.instances != null) {
                        LoginActivity.instances.finish();
                    }

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra("login_register", "login");
                    getActivity().startActivityForResult(intent, 238);
                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    setIndex(checkID);
                    return;
                }

                if (getChildFragmentManager().findFragmentByTag("5") != null) {
                    ft.show(getChildFragmentManager().findFragmentByTag("5"));
                } else {
                    ft.add(R.id.container, MineIfoFragment.newInstance("tab5", mContext), "5");// 我的
                }
                if (getChildFragmentManager().findFragmentByTag("1") != null) {
                    // isRemove = false;
                    ft.hide(getChildFragmentManager().findFragmentByTag("1"));
                }
                if (getChildFragmentManager().findFragmentByTag("2") != null) {
                    ft.hide(getChildFragmentManager().findFragmentByTag("2"));
                }
                if (getChildFragmentManager().findFragmentByTag("3") != null) {
                    ft.hide(getChildFragmentManager().findFragmentByTag("3"));
                    // ft.remove(getChildFragmentManager().findFragmentByTag("3"));
                }
                if (getChildFragmentManager().findFragmentByTag("4") != null) {
                    ft.hide(getChildFragmentManager().findFragmentByTag("4"));
                }
                ft.commitAllowingStateLoss();

                // if (tab_three2.getVisibility() == View.GONE) {
                // tv_tab3.setVisibility(View.VISIBLE);
                // }else {
                // tv_tab3.setVisibility(View.GONE);
                // }
                break;
        }
    }

    private CheckPressDiaolg dialog;

    private class CheckPressDiaolg extends Dialog {

        public CheckPressDiaolg(Context context) {
            super(context, R.style.my_check_dialog);
            ImageButton l = new ImageButton(context);
            l.setBackgroundResource(R.color.transparenct);
            setContentView(l);
        }

    }

    private File file;

    private void dPic(String picPath) {
        try {
            URL url = new URL(picPath);
            // 打开连接
            URLConnection con = url.openConnection();
            // 获得文件的长度
            int contentLength = con.getContentLength();
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[8192];
            // 读取到的数据长度
            int len;
            File fileDirec = new File(YConstance.savePicPath);
            if (!fileDirec.exists()) {
                fileDirec.mkdir();
            }
            // 输出的文件流 /sdcard/yssj/
            file = new File(YConstance.savePicPath, "yssjH5Share.jpg");
            if (file.exists()) {
                file.delete();
            }
            OutputStream os = new FileOutputStream(file);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            os.close();
            is.close();
        } catch (Exception e) {
            LogYiFu.e("TAG", "下载失败");
            e.printStackTrace();
        }
    }

    private void shareRandom() {

        new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                return ComModel2.getShareShopLink(context, "");
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result.get("status").equals("1")) {
                        LogYiFu.e("pic", result.get("shop_pic"));
                        String[] picList = result.get("shop_pic").split(",");
                        String link = result.get("link");
                        downloadRandom(null, picList, result.get("shop_code"), result.get("shop_name"), result, link);
                    } else if (result.get("status").equals("1050")) {// 表明
                        Intent intent = new Intent(context, NoShareActivity.class);
                        intent.putExtra("isNomal", true);
                        context.startActivity(intent); // 分享已经超过了
                    }
                }
            }

        }.execute();
    }

    private boolean remind = false;

//    private void checkVersion(View v) {
//        try {
//            PackageManager pm = mContext.getPackageManager();
//            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
//            int versionCode = pi.versionCode;
//            final String versionName = "v" + pi.versionName;
//            new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) mContext, v, 0) {
//
//                @Override
//                protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
//                        throws Exception {
//                    HashMap<String, String> mapRet = ComModel2.checkVersion(mContext);
//                    return mapRet;
//                }
//
//                protected boolean isHandleException() {
//                    return true;
//                }
//
//                ;
//
//                protected void onPostExecute(FragmentActivity context, final java.util.HashMap<String, String> result,
//                                             Exception e) {
//                    if (null == e) {
//                        if (StringUtils.isDownload(result.get("version_no"), versionName)) {
//                            mContext.getSharedPreferences("tocao_isupdate", Context.MODE_PRIVATE).edit()
//                                    .putBoolean("tocao_isupdate", true).commit();
//                            AlertDialog.Builder builder = new Builder(mContext);
//
//                            final Dialog dialog = new Dialog(mContext, R.style.invate_dialog_style);
//                            View view = View.inflate(mContext, R.layout.is_force_update_dialog, null);
//                            TextView tv_version = (TextView) view.findViewById(R.id.tv_version);
//                            tv_version.setText("" + result.get("version_no") + "更新了以下内容：");
//                            TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
//                            String content = result.get("msg");
//                            LogYiFu.e("ssss", content);
//                            tv_content.setText(content);
//                            Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
//                            final ImageView iv_remind = (ImageView) view.findViewById(R.id.iv_remind);
//                            LinearLayout ll_notice = (LinearLayout) view.findViewById(R.id.ll_notice);
//                            iv_remind.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View arg0) {
//                                    // TODO Auto-generated method stub
//                                    remind = !remind;
//                                    if (remind) {
//                                        iv_remind.setBackgroundResource(R.drawable.remind_cel);
//                                    } else {
//                                        iv_remind.setBackgroundResource(R.drawable.remind_nor);
//                                    }
//                                }
//                            });
//                            btn_cancel.setOnClickListener(new OnClickListener() {
//
//                                @Override
//                                public void onClick(View arg0) {
//                                    dialog.dismiss();
//                                    if (result.get("is_update").equals("1")) {// 强制更新
//                                        AppManager.getAppManager().finishAllActivity();
//                                    } else {
//                                        SharedPreferencesUtil.saveBooleanData(mContext, Pref.REMIND_IS_SHOW, remind);
//                                        SharedPreferencesUtil.saveStringData(mContext, Pref.RECORD_VERSION,
//                                                "" + result.get("version_no"));
//                                        String imei = CheckStrUtil.getImei(mContext);
//                                        if (imei != null && ComModel2.flag == 0) {
//                                            new Thread() {
//                                                public void run() {
//
//                                                    try {
//                                                        Thread.sleep(5000);// 5秒
//                                                    } catch (InterruptedException e) {
//                                                        // Auto-generated
//                                                        // catch block
//                                                        e.printStackTrace();
//                                                    }
//                                                    // mContext.sendBroadcast(new
//                                                    // Intent(TaskReceiver.newMemberTask_1));
//                                                    isNewMemberTask_1 = 1;
//                                                    handlerCheckVersion.sendEmptyMessage(0);
//                                                }
//
//                                                ;
//                                            }.start();
//
//                                        } else if (YJApplication.instance.isLoginSucess() == false) {
//                                            new Thread() {
//                                                public void run() {
//
//                                                    try {
//                                                        Thread.sleep(5000);// 5秒
//                                                    } catch (InterruptedException e) {
//                                                        // Auto-generated
//                                                        // catch block
//                                                        e.printStackTrace();
//                                                    }
//                                                    // mContext.sendBroadcast(new
//                                                    // Intent(TaskReceiver.newMemberTask_1));
//                                                    isLoginNewTask = 1;
//                                                    handlerCheckVersion.sendEmptyMessage(2);
//                                                }
//
//                                                ;
//                                            }.start();
//                                        } else {
//                                            UserInfo userInfo = YCache.getCacheUserSafe(mContext);
//                                            if (null == userInfo) {
//                                                return;
//                                            }
//                                            if (null == userInfo.getHobby() || userInfo.getHobby().equals("0")) {
//
//                                                new Thread() {
//                                                    public void run() {
//
//                                                        try {
//                                                            Thread.sleep(5000);// 5秒
//                                                        } catch (InterruptedException e) {
//                                                            // Auto-generated
//                                                            // catch
//                                                            // block
//                                                            e.printStackTrace();
//                                                        }
//                                                        // mContext.sendBroadcast(new
//                                                        // Intent(TaskReceiver.newMemberTask_1));
//                                                        isNewShop = 1;
//                                                        handlerCheckVersion.sendEmptyMessage(3);
//                                                    }
//
//                                                    ;
//                                                }.start();
//
//                                            } else {
//                                                new Thread() {
//                                                    public void run() {
//
//                                                        try {
//                                                            Thread.sleep(5000);// 5秒
//                                                        } catch (InterruptedException e) {
//                                                            // Auto-generated
//                                                            // catch
//                                                            // block
//                                                            e.printStackTrace();
//                                                        }
//                                                        // mContext.sendBroadcast(new
//                                                        // Intent(TaskReceiver.newMemberTask_1));
//                                                        isEverydayShareTask = 1;
//                                                        handlerCheckVersion.sendEmptyMessage(2);
//                                                    }
//
//                                                    ;
//                                                }.start();
//                                            }
//                                        }
//                                    }
//                                }
//                            });
//
//                            Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
//                            btn_ok.setOnClickListener(new OnClickListener() {
//
//                                @Override
//                                public void onClick(View arg0) {
//                                    dialog.dismiss();
//                                    ApkDownloadManager UpgradeApk = new ApkDownloadManager((FragmentActivity) mContext);
//                                    UpgradeApk.downloadUpgradeApk(YUrl.apkUrl + result.get("path"));
//
//                                    String imei = CheckStrUtil.getImei(mContext);
//                                    if (imei != null && ComModel2.flag == 0) {
//                                        new Thread() {
//                                            public void run() {
//
//                                                try {
//                                                    Thread.sleep(5000);// 5秒
//                                                } catch (InterruptedException e) {
//                                                    // block
//                                                    e.printStackTrace();
//                                                }
//                                                // mContext.sendBroadcast(new
//                                                // Intent(TaskReceiver.newMemberTask_1));
//                                                isNewMemberTask_1 = 1;
//                                                handlerCheckVersion.sendEmptyMessage(0);
//                                            }
//
//                                            ;
//                                        }.start();
//
//                                    } else if (YJApplication.instance.isLoginSucess() == false) {
//                                        new Thread() {
//                                            public void run() {
//
//                                                try {
//                                                    Thread.sleep(5000);// 5秒
//                                                } catch (InterruptedException e) {
//                                                    // block
//                                                    e.printStackTrace();
//                                                }
//                                                // mContext.sendBroadcast(new
//                                                // Intent(TaskReceiver.newMemberTask_1));
//                                                isLoginNewTask = 1;
//                                                handlerCheckVersion.sendEmptyMessage(1);
//                                            }
//
//                                            ;
//                                        }.start();
//                                    } else {
//                                        UserInfo userInfo = YCache.getCacheUserSafe(mContext);
//                                        if (null == userInfo) {
//                                            return;
//                                        }
//                                        if (null == userInfo.getHobby() || userInfo.getHobby().equals("0")) {
//
//                                            new Thread() {
//                                                public void run() {
//
//                                                    try {
//                                                        Thread.sleep(5000);// 5秒
//                                                    } catch (InterruptedException e) {
//                                                        // catch block
//                                                        e.printStackTrace();
//                                                    }
//                                                    // mContext.sendBroadcast(new
//                                                    // Intent(TaskReceiver.newMemberTask_1));
//                                                    isNewShop = 1;
//                                                    handlerCheckVersion.sendEmptyMessage(3);
//                                                }
//
//                                                ;
//                                            }.start();
//
//                                        } else {
//                                            new Thread() {
//                                                public void run() {
//
//                                                    try {
//                                                        Thread.sleep(5000);// 30秒
//                                                    } catch (InterruptedException e) {
//                                                        // catch block
//                                                        e.printStackTrace();
//                                                    }
//                                                    // mContext.sendBroadcast(new
//                                                    // Intent(TaskReceiver.newMemberTask_1));
//                                                    isEverydayShareTask = 1;
//                                                    handlerCheckVersion.sendEmptyMessage(2);
//                                                }
//
//                                                ;
//                                            }.start();
//                                        }
//                                    }
//
//                                    if (result.get("is_update").equals("1")) {// 强制更新
//
//                                        Intent intent = new Intent(Intent.ACTION_MAIN);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        intent.addCategory(Intent.CATEGORY_HOME);
//                                        startActivity(intent);
//
//                                    }
//                                }
//                            });
//
//                            // 创建自定义样式dialog
//                            dialog.setContentView(view, new LinearLayout.LayoutParams(
//                                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
//                            dialog.setCancelable(false);
//                            // if (result.get("is_update").equals("1")) {// 强制更新
//                            // btn_cancel.setVisibility(View.GONE);
//                            // dialog.setOnCancelListener(new OnCancelListener()
//                            // {
//                            // @Override
//                            // public void onCancel(DialogInterface arg0) {
//                            //
//                            // // mContext.finish();
//                            // AppManager.getAppManager().finishAllActivity();
//                            // }
//                            // });
//                            // }
//                            // if
//                            // (!YJApplication.instance.getIsShowUpdateDialog())
//                            // {
//                            // dialog.show();
//                            // YJApplication.instance.isShowUpdateDialog(true);
//                            // }
//
//                            if (result.get("is_update").equals("1")) {// 强制更新
//                                btn_cancel.setVisibility(View.GONE);
//                                ll_notice.setVisibility(View.GONE);
//                                dialog.setOnCancelListener(new OnCancelListener() {
//                                    @Override
//                                    public void onCancel(DialogInterface arg0) {
//
//                                        // mContext.finish();
//                                        AppManager.getAppManager().finishAllActivity();
//                                    }
//                                });
//                                dialog.show();
//                            } else {// 非强制更新
//                                if (!YJApplication.instance.getIsShowUpdateDialog()) {
//                                    if (!(SharedPreferencesUtil.getStringData(mContext, Pref.RECORD_VERSION, "")
//                                            .equals("" + result.get("version_no")))) {// 版本号再次发生变化（第一次没更新再次更新时弹更新弹窗）
//                                        dialog.show();
//                                        SharedPreferencesUtil.saveStringData(mContext, Pref.UPDATE_TIME,
//                                                "" + (System.currentTimeMillis() + 72 * 60 * 60 * 1000));// 初始化三天计时
//                                        SharedPreferencesUtil.saveBooleanData(mContext, Pref.REMIND_IS_SHOW, false);// 初始化到没有点不再提醒
//                                    } else {// 仍是同一个版本时（根据是否点了不再提醒及三天之内的逻辑判断是否弹）
//                                        if (Long.parseLong(SharedPreferencesUtil.getStringData(mContext,
//                                                Pref.UPDATE_TIME, "" + 0)) < System.currentTimeMillis()
//                                                && !SharedPreferencesUtil.getBooleanData(mContext, Pref.REMIND_IS_SHOW,
//                                                false)) {// 距离上次弹窗已有三天并且上次没有勾选不再提醒
//                                            dialog.show();
//                                            SharedPreferencesUtil.saveStringData(mContext, Pref.UPDATE_TIME,
//                                                    "" + (System.currentTimeMillis() + 72 * 60 * 60 * 1000));
//                                        }
//                                    }
//                                    YJApplication.instance.isShowUpdateDialog(true);
//                                }
//                            }
//
//                        } else {
//                            String imei = CheckStrUtil.getImei(mContext);
//                            if (imei != null && ComModel2.flag == 0) {
//                                new Thread() {
//                                    public void run() {
//
//                                        try {
//                                            Thread.sleep(5000);// 5秒
//                                        } catch (InterruptedException e) {
//                                            e.printStackTrace();
//                                        }
//                                        // mContext.sendBroadcast(new
//                                        // Intent(TaskReceiver.newMemberTask_1));
//                                        isNewMemberTask_1 = 1;
//                                        handlerCheckVersion.sendEmptyMessage(0);
//                                    }
//
//                                    ;
//                                }.start();
//
//                            } else if (YJApplication.instance.isLoginSucess() == false) {
//                                new Thread() {
//                                    public void run() {
//
//                                        try {
//                                            Thread.sleep(5000);// 5秒
//                                        } catch (InterruptedException e) {
//                                            e.printStackTrace();
//                                        }
//                                        // mContext.sendBroadcast(new
//                                        // Intent(TaskReceiver.newMemberTask_1));
//                                        isLoginNewTask = 1;
//                                        handlerCheckVersion.sendEmptyMessage(1);
//                                    }
//
//                                    ;
//                                }.start();
//                            } else {
//                                UserInfo userInfo = YCache.getCacheUserSafe(mContext);
//                                if (null == userInfo) {
//                                    return;
//                                }
//                                if (null == userInfo.getHobby() || userInfo.getHobby().equals("0")) {
//
//                                    new Thread() {
//                                        public void run() {
//
//                                            try {
//                                                Thread.sleep(5000);// 5秒
//                                            } catch (InterruptedException e) {
//                                                // block
//                                                e.printStackTrace();
//                                            }
//                                            // mContext.sendBroadcast(new
//                                            // Intent(TaskReceiver.newMemberTask_1));
//                                            isNewShop = 1;
//                                            handlerCheckVersion.sendEmptyMessage(3);
//                                        }
//
//                                        ;
//                                    }.start();
//
//                                } else {
//                                    new Thread() {
//                                        public void run() {
//
//                                            try {
//                                                Thread.sleep(5000);// 30秒
//                                            } catch (InterruptedException e) {
//                                                // block
//                                                e.printStackTrace();
//                                            }
//                                            // mContext.sendBroadcast(new
//                                            // Intent(TaskReceiver.newMemberTask_1));
//                                            isEverydayShareTask = 1;
//                                            handlerCheckVersion.sendEmptyMessage(2);
//                                        }
//
//                                        ;
//                                    }.start();
//                                }
//                            }
//                        }
//                    }
//                }
//
//                ;
//
//            }.execute((Void[]) null);
//        } catch (NameNotFoundException e) {
//            // e.printStackTrace();
//            String imei = CheckStrUtil.getImei(mContext);
//            if (imei != null && ComModel2.flag == 0) {
//                new Thread() {
//                    public void run() {
//
//                        try {
//                            Thread.sleep(5000);// 5秒
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        // mContext.sendBroadcast(new
//                        // Intent(TaskReceiver.newMemberTask_1));
//                        isNewMemberTask_1 = 1;
//                        handlerCheckVersion.sendEmptyMessage(0);
//                    }
//
//                    ;
//                }.start();
//
//            } else {
//                new Thread() {
//                    public void run() {
//
//                        try {
//                            Thread.sleep(5000);// 5秒
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        // mContext.sendBroadcast(new
//                        // Intent(TaskReceiver.newMemberTask_1));
//                        isEverydayShareTask = 1;
//                        handlerCheckVersion.sendEmptyMessage(2);
//                    }
//
//                    ;
//                }.start();
//            }
//        }
//
//    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        if (!hidden) {

            if (isNewMemberTask_1 == 1) {
                handlerCheckVersion.sendEmptyMessage(0);
            }
            if (isLoginNewTask == 1) {
                handlerCheckVersion.sendEmptyMessage(2);
            }
            if (isNewShop == 1) {
                handlerCheckVersion.sendEmptyMessage(3);
            }
            if (isEveryDayTask3_4 == 1) {
                handlerCheckVersion.sendEmptyMessage(1);
            }
            if (isEverydayShareTask == 1) {
                handlerCheckVersion.sendEmptyMessage(4);
            }
            if (tucaoTask == 1) {
                handlerCheckVersion.sendEmptyMessage(6);
                tucaoTask = 2;
            }
        }
    }

    private int isNewMemberTask_1, isEveryDayTask3_4, isLoginNewTask, isNewShop, isEverydayShareTask, tucaoTask;

    private void downloadRandom(View v, final String[] picList, final String shop_code, final String shop_name,
                                final HashMap<String, String> mapInfos, final String link) {
        new SAsyncTask<Void, Void, Void>((FragmentActivity) mContext, v, R.string.wait) {

            @Override
            protected Void doInBackground(Void... params) {
                File fileDirec = new File(YConstance.savePicPath);
                if (!fileDirec.exists()) {
                    fileDirec.mkdir();
                }
                File[] listFiles = new File(YConstance.savePicPath).listFiles();
                if (listFiles.length != 0) {
                    LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
                    for (File file : listFiles) {
                        file.delete();
                    }
                }
                // LogYiFu.i("TAG", "piclist=" + picList.length);
                List<String> pics = new ArrayList<String>();
                for (int j = 0; j < picList.length; j++) {
                    if (!picList[j].contains("reveal_") && !picList[j].contains("detail_")
                            && !picList[j].contains("real_")) {
                        picList[j] = shop_code.substring(1, 4) + "/" + shop_code + "/" + picList[j];
                        pics.add(picList[j]);
                    }
                }
                int j = pics.size() + 1;
                if (pics.size() > 8) {
                    j = 9;
                }
                for (int i = 0; i < j; i++) {
                    if (i == j - 1) {
						/*
						 * ComModel2.saveQRCode(PaymentSuccessActivity.this,
						 * shop_code);
						 */
                        Bitmap bm = QRCreateUtil.createImage(mapInfos.get("QrLink"), 500, 700,
                                mapInfos.get("shop_se_price"), mContext);// 得到二维码图片
                        QRCreateUtil.saveBitmap(bm, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
                        // downloadPic(mapInfos.get("qr_pic"), 9);
                        break;
                    }
                    downloadPic(pics.get(i) + "!450", i);
                }
                return super.doInBackground(params);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, Void result) {
                // showShareDialog();
                // MyLogYiFu.e("TAG", "宝贝内容=" + shop.getShop_name() + ",宝贝链接=" +
                // result);
                if (null != context && null != context.getWindow().getDecorView()) {
                    ShareUtil.configPlatforms(context);
                    UMImage umImage = new UMImage(context, R.drawable.ic_launcher);
                    ShareUtil.setShareContent(context, umImage, shop_name, link);
                    // ShareUtil.share(ShopDetailsActivity.this);
                    MyPopupwindow myPopupwindow = new MyPopupwindow(context, 0, 1);
                    myPopupwindow.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                }
                super.onPostExecute(context, result);

            }

        }.execute();
    }

    private void downloadPic(String picPath, int i) {
        try {
            URL url = new URL(YUrl.imgurl + picPath);
            // 打开连接
            URLConnection con = url.openConnection();
            // 获得文件的长度
            int contentLength = con.getContentLength();
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[8192];
            // 读取到的数据长度
            int len;
            // 输出的文件流 /sdcard/yssj/
            File file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(i)) + ".jpg");
            if (file.exists()) {
                file.delete();
            }
            LogYiFu.e("TAG", "多分享选择下载的图片。。。。");
            OutputStream os = new FileOutputStream(file);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            LogYiFu.i("TAG", "下载完毕。。。file=" + file.toString());
            // 完毕，关闭所有链接
            os.close();
            is.close();
        } catch (Exception e) {
            LogYiFu.e("TAG", "下载失败");
            e.printStackTrace();
        }
    }

    private ToLoginDialog loginDialog;// 去登陆弹窗
    private Boolean isShow = false;
    private int isPause = 0;
    private Handler handlerCheckVersion = new Handler() {
        public void handleMessage(android.os.Message msg) {

            if (isHidden() || isPause == 1) {
                return;
            }
            switch (msg.what) {
                case 0: {
                    if (isShow) {
                        return;
                    }
                    if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == mContext
                            .getSharedPreferences("shareApp", Context.MODE_PRIVATE).getInt("day", 0)) {
                        return;
                    }
                    isNewMemberTask_1 = 2;
                    NewPDialog dialog = new NewPDialog(mContext, R.layout.task_dialog1);
                    dialog.setF(new NewPDialog.FinishLintener() {

                        @Override
                        public void onFinishClickLintener() {
                            isShow = false;
                            mContext.getSharedPreferences("shareApp", Context.MODE_PRIVATE).edit()
                                    .putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
                        }
                    });
                    dialog.setL(new NewPDialog.TaskLintener() {

                        @Override
                        public void onOKClickLintener() {
                            isShow = false;
                            mContext.getSharedPreferences("shareApp", Context.MODE_PRIVATE).edit()
                                    .putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();

                            if (!YJApplication.instance.isLoginSucess()) {
                                if (loginDialog == null) {
                                    loginDialog = new ToLoginDialog(mContext);
                                    loginDialog.setRequestCode(56);
                                }
                                loginDialog.show();
                                isShow = true;
                                // NewPDialog dialog = new NewPDialog(mContext,
                                // R.layout.task_dialog3);
                                // dialog.setL(new NewPDialog.TaskLintener() {
                                //
                                // @Override
                                // public void onOKClickLintener() {
                                // // 注册
                                //
                                // Intent i = new Intent(mContext,
                                // LoginActivity.class);
                                // i.putExtra("login_register", "login");
                                // ((FragmentActivity) mContext)
                                // .startActivityForResult(i, 56);
                                // }
                                //
                                // @Override
                                // public void onShouZhiClickLintener() {
                                //
                                // }
                                // });
                                // dialog.show();
                            } else {
                                // 点击分享
                                LoadingDialog.show((FragmentActivity) mContext);
                                isShow = true;
                                new AsyncTask<Void, Void, Void>() {
                                    @Override
                                    protected Void doInBackground(Void... arg0) {

                                        dPic("http://yssj668.b0.upaiyun.com/share/android/900_900_1_android.jpg");
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Void result) {
                                        super.onPostExecute(result);
                                        LoadingDialog.hide(getActivity());
                                        isShow = false;
                                        ShareUtil.shareH5(mContext, file);
                                    }

                                    ;

                                }.execute();
                            }
                        }

                        @Override
                        public void onShouZhiClickLintener() {

                        }
                    });
                    // dialog.show();
                    // isShow = true;
                }
                break;
                case 1: {

                    {
                        if (isShow) {
                            return;
                        }
                        if (!YJApplication.instance.isLoginSucess()) {
                            return;
                        }
                        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == mContext
                                .getSharedPreferences("EverydayTaskMondayFriday", Context.MODE_PRIVATE).getInt("day", 0)) {
                            return;
                        }

                        UserInfo userInfo;
                        userInfo = YCache.getCacheUserSafe(mContext);
                        if (null == userInfo) {
                            return;
                        }
                        if (null == userInfo.getHobby() || userInfo.getHobby().equals("0")) {
                            return;
                        }

                        isEveryDayTask3_4 = 2;
                        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                        if (day == 3) {
                        } else if (day == 2) {
                        }
                    }
                }

                break;
                case 2: {

				/*
				 * isLoginNewTask=2;
				 * 
				 * if (YJApplication.instance.isLoginSucess()) { return; }
				 * NewPDialog dialog = new NewPDialog(mContext,
				 * R.layout.task_dialog3); dialog.setF(new
				 * NewPDialog.FinishLintener() {
				 * 
				 * @Override public void onFinishClickLintener() { //
				 * Auto-generated method stub } }); dialog.setL(new
				 * NewPDialog.TaskLintener() {
				 * 
				 * @Override public void onOKClickLintener() { // 注册 Intent i =
				 * new Intent(mContext, LoginActivity.class);
				 * i.putExtra("login_register", "login");
				 * mContext.startActivity(i); }
				 * 
				 * @Override public void onShouZhiClickLintener() { //
				 * Auto-generated method stub
				 * 
				 * } });
				 */
                    // dialog.show();
                    /**
                     * NewPDialog dialog = new NewPDialog(mContext,
                     * R.layout.task_dialog1); dialog.setF(new
                     * NewPDialog.FinishLintener() {
                     *
                     * @Override public void onFinishClickLintener() { //
                     *           Auto-generated method stub new Thread(){ public
                     *           void run() {
                     *
                     *           try { Thread.sleep(25000);//5秒 } catch
                     *           (InterruptedException e) { // catch block
                     *           e.printStackTrace(); } //mContext.sendBroadcast(new
                     *           Intent(TaskReceiver.newMemberTask_1));
                     *           isEveryDayTask3_4=1; handler.sendEmptyMessage(1);
                     *           }; }.start(); } }); dialog.setL(new
                     *           NewPDialog.TaskLintener() {
                     * @Override public void onOKClickLintener() {
                     *
                     *
                     *           new Thread(){ public void run() {
                     *
                     *           try { Thread.sleep(25000);//5秒 } catch
                     *           (InterruptedException e) { // catch block
                     *           e.printStackTrace(); } //mContext.sendBroadcast(new
                     *           Intent(TaskReceiver.newMemberTask_1));
                     *           isEveryDayTask3_4=1; handler.sendEmptyMessage(1);
                     *           }; }.start(); if
                     *           (!YJApplication.instance.isLoginSucess()) {
                     *           NewPDialog dialog = new NewPDialog(mContext,
                     *           R.layout.task_dialog3); dialog.setL(new
                     *           NewPDialog.TaskLintener() {
                     * @Override public void onOKClickLintener() { // 注册 Intent i =
                     *           new Intent(mContext, LoginActivity.class);
                     *           i.putExtra("login_register", "login");
                     *           ((FragmentActivity
                     *           )mContext).startActivityForResult(i,56); }
                     * @Override public void onShouZhiClickLintener() { //
                     *           Auto-generated method stub
                     *
                     *           } }); dialog.show(); }else{ // 点击分享
                     *           LoadingDialog.show((FragmentActivity) mContext);
                     *           new AsyncTask<Void, Void, Void>() {
                     * @Override protected Void doInBackground(Void... arg0) {
                     *
                     *           dPic(
                     *           "http://yssj668.b0.upaiyun.com/share/android/900_900_1_android.jpg"
                     *           ); return null; }
                     * @Override protected void onPostExecute(Void result) {
                     *           super.onPostExecute(result); LoadingDialog.hide();
                     *           ShareUtil.shareH5(mContext, file); };
                     *
                     *           }.execute(); } }
                     * @Override public void onShouZhiClickLintener() { //
                     *           Auto-generated method stub
                     *
                     *           } }); dialog.show();
                     */
                }
                break;
                case 3: {
                    if (isShow) {
                        return;
                    }
                    isNewShop = 2;
                    if (!YJApplication.instance.isLoginSucess()) {
                        return;
                    }
                    if (mContext.getSharedPreferences("dian", Context.MODE_PRIVATE).getInt("dian", 0) == Calendar
                            .getInstance().get(Calendar.DAY_OF_MONTH)) {
                        return;
                    }
                    UserInfo userInfo = YCache.getCacheUserSafe(mContext);
                    if (null == userInfo) {
                        return;
                    }
                    // if (null == userInfo.getHobby() ||
                    // userInfo.getHobby().equals("0")) {
                    // mContext.getSharedPreferences("dian",
                    // Context.MODE_PRIVATE).edit()
                    // .putInt("dian",
                    // Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
                    // int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                    // NewPDialog dialog;
                    // if (day == 2) {
                    // dialog = new NewPDialog(getActivity(),
                    // R.layout.task_dialog4_monday);
                    // } else if (day == 3) {
                    // dialog = new NewPDialog(getActivity(),
                    // R.layout.task_dialog4_tuesday);
                    // } else if (day == 4) {
                    // dialog = new NewPDialog(getActivity(),
                    // R.layout.task_dialog4_wednesday);
                    // } else if (day == 5) {
                    // dialog = new NewPDialog(getActivity(),
                    // R.layout.task_dialog4_thursday);
                    // } else if (day == 6) {
                    // dialog = new NewPDialog(getActivity(),
                    // R.layout.task_dialog4_friday);
                    // } else {
                    // dialog = new NewPDialog(getActivity(),
                    // R.layout.task_dialog4);
                    // }
                    //
                    // dialog.setF(new NewPDialog.FinishLintener() {
                    //
                    // @Override
                    // public void onFinishClickLintener() {
                    // isShow = false;
                    //
                    // }
                    // });
                    //
                    // dialog.setL(new NewPDialog.TaskLintener() {
                    //
                    // @Override
                    // public void onOKClickLintener() {
                    // isShow = false;
                    // // 开店
                    // AppManager.getAppManager().finishAllActivityOfEveryDayTask();
                    // if (MainMenuActivity.instances != null)
                    // ((MainFragment)
                    // MainMenuActivity.instances.getSupportFragmentManager()
                    // .findFragmentByTag("tag")).setIndex(0);
                    // }
                    //
                    // @Override
                    // public void onShouZhiClickLintener() {
                    //
                    // }
                    //
                    // });
                    // try {
                    // dialog.show();
                    // isShow = true;
                    // } catch (Exception e) {
                    //
                    // }
                    // }
                }
                break;
                case 6: {

                    if (mContext.getSharedPreferences("tocao_isupdate", Context.MODE_PRIVATE).getBoolean("tocao_isupdate",
                            false)) {
                        return;
                    }
                    if (mContext.getSharedPreferences("tocao_isshow", Context.MODE_PRIVATE).getBoolean("tocao_isshow",
                            false)) {
                        return;
                    }
                    if (isPause == 1) {
                        return;
                    }
                    if (isShow) {
                        return;
                    }
                    final NewPDialog mDialog = new NewPDialog(getActivity(), R.layout.task_dialog9);

                    mDialog.setL(new NewPDialog.TaskLintener() {

                        @Override
                        public void onOKClickLintener() {
                            // 跳到意见反馈
                            isShow = false;

                            Intent intent = new Intent();

                            intent.setClass(mContext, FeedBackActivity.class);

                            mContext.startActivity(intent);

                        }

                        @Override
                        public void onShouZhiClickLintener() {

                        }

                    });
                    mDialog.setF(new NewPDialog.FinishLintener() {

                        @Override
                        public void onFinishClickLintener() {
                            isShow = false;
                        }
                    });
                    mDialog.show();
                    mContext.getSharedPreferences("tocao_isshow", Context.MODE_PRIVATE).edit()
                            .putBoolean("tocao_isshow", true).commit();
                    isShow = true;

                }
                break;
                case 4: {
                    if (isShow) {
                        return;
                    }
                    if (isEverydayShareTask == 1) {
                        if (!YJApplication.instance.isLoginSucess()) {
                            return;
                        }
                        if (isPause == 1) {
                            return;
                        }
                        List<String> taskMap = YJApplication.instance.getTaskMap();
                        if (taskMap == null) {
                            taskMap = new ArrayList<String>();
                        }
                        if (taskMap.contains("7") || taskMap.contains("8")) {
                            return;
                        }
                        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == mContext
                                .getSharedPreferences("EveryDayShareAm", Context.MODE_PRIVATE).getInt("day", 0)) {
                            return;
                        }
                        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == mContext
                                .getSharedPreferences("EveryDaySharePm", Context.MODE_PRIVATE).getInt("day", 0)) {
                            return;
                        }
                        isEverydayShareTask = 2;
                        // 获取当前时间
                        // String currentTime = DateFormat.format("HH", new Date())
                        // .toString();
                        // int hour = Integer.parseInt(currentTime);
                        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                        // 每日上午分享一次
                        if ((hour > 7 || hour == 7) && hour < 14) {

                            // NewPDialog dialog = new NewPDialog(mContext,
                            // R.layout.every_day_task_dialog2);
                            //
                            // dialog.setF(new NewPDialog.FinishLintener() {
                            //
                            // @Override
                            // public void onFinishClickLintener() {
                            // isShow = false;
                            // mContext.getSharedPreferences("EveryDaySharePause",
                            // Context.MODE_PRIVATE).edit()
                            // .putInt("day", 0).commit();
                            // mContext.getSharedPreferences("EveryDayShareAm",
                            // Context.MODE_PRIVATE).edit()
                            // .putInt("day",
                            // Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
                            // }
                            // });
                            //
                            // dialog.setL(new NewPDialog.TaskLintener() {
                            //
                            // @Override
                            // public void onOKClickLintener() {
                            // isShow = false;
                            // mContext.getSharedPreferences("EveryDaySharePause",
                            // Context.MODE_PRIVATE).edit()
                            // .putInt("day", 0).commit();
                            // mContext.getSharedPreferences("EveryDayShareAm",
                            // Context.MODE_PRIVATE).edit()
                            // .putInt("day",
                            // Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
                            // // 分享商品
                            // shareRandom();
                            //
                            // }
                            //
                            // @Override
                            // public void onShouZhiClickLintener() {
                            //
                            // }
                            // });
                            // try {
                            // dialog.show();
                            // isShow = true;
                            // } catch (Exception e) {
                            // }
                        }

                        // 每日下午分享一次
                        // if ((hour > 14 || hour == 14) && hour < 20) {
                        //
                        // NewPDialog dialog = new NewPDialog(mContext,
                        // R.layout.every_day_task_dialog3);
                        // dialog.setF(new NewPDialog.FinishLintener() {
                        //
                        // @Override
                        // public void onFinishClickLintener() {
                        // isShow = false;
                        // mContext.getSharedPreferences("EveryDaySharePm",
                        // Context.MODE_PRIVATE).edit()
                        // .putInt("day",
                        // Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
                        // }
                        // });
                        // dialog.setL(new NewPDialog.TaskLintener() {
                        //
                        // @Override
                        // public void onOKClickLintener() {
                        // isShow = false;
                        // mContext.getSharedPreferences("EveryDaySharePm",
                        // Context.MODE_PRIVATE).edit()
                        // .putInt("day",
                        // Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
                        // // 分享商品
                        // shareRandom();
                        // }
                        //
                        // @Override
                        // public void onShouZhiClickLintener() {
                        //
                        // }
                        // });
                        // try {
                        // dialog.show();
                        // isShow = true;
                        // } catch (Exception e) {
                        // }
                        // }
                    }
                }
                break;
                default:
                    break;
            }

        }

        ;
    };

    private int checked = -1;

    // private Animation alphaAnimation;

    private TextView tv_tab3;

    private String systime_now;
    private String systime_new;

    /**
     * 显示用户新增粉丝和用户领取奖励弹窗（可以在购物页、小店、签到、购物车、我的页面弹出）
     */
    // private TimerTask task;
    private TimerTask task2;


    private String picHead;
    private String textName;
    private SpannableString ssTextName;


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
            }
        }

    };



    // 5个Fragment
    // // 购物
    // ShopNewFragment shopNewFragment =
    // ShopNewFragment.newInstances("tab2", mContext);
    // // 分类
    // ClassficationFragment classficationFragment =
    // ClassficationFragment.newInstance("tab1", mContext);
    // // 赚钱
    // SignFragment signFragment = SignFragment.newInstance(mContext);
    // // 购物车
    // ShopCartNewNewFragment shopCartNewNewFragment =
    // ShopCartNewNewFragment.newInstance("tab4", mContext);
    // // 我的
    // MineIfoFragment mineIfoFragment = MineIfoFragment.newInstance("tab5",
    // mContext);
    //
    // switch (checkedId) {
    // case R.id.tab_one: // ------------ 购物
    // checkID = 0;
    // if (flag == true) {
    // doSmallAnima();
    // tab_three.setVisibility(View.VISIBLE);
    // tab_three2.setVisibility(View.GONE);
    // doBigAnimaTabThree();
    // flag = false;
    // }
    //
    // if (getChildFragmentManager().findFragmentByTag("1") != null) {
    //
    // if (getChildFragmentManager().findFragmentByTag("2") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("2"));
    // }
    // if (getChildFragmentManager().findFragmentByTag("4") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("4"));
    // }
    // if (getChildFragmentManager().findFragmentByTag("3") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("3"));
    // }
    // if (getChildFragmentManager().findFragmentByTag("5") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("5"));
    // }
    //
    // ft.show(getChildFragmentManager().findFragmentByTag("1"));
    //
    // } else {
    // ft.add(R.id.container, shopNewFragment, "1");
    // }
    //
    // ft.commit();
    //
    // break;
    //
    // case R.id.tab_two: // ------------ 分类
    // checkID = 1;
    // if (flag == true) {
    // doSmallAnima();
    // tab_three.setVisibility(View.VISIBLE);
    // tab_three2.setVisibility(View.GONE);
    // doBigAnimaTabThree();
    // flag = false;
    // }
    //
    // if (getChildFragmentManager().findFragmentByTag("2") != null) {
    //
    // if (getChildFragmentManager().findFragmentByTag("1") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("1"));
    // }
    // if (getChildFragmentManager().findFragmentByTag("4") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("4"));
    // }
    // if (getChildFragmentManager().findFragmentByTag("3") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("3"));
    // }
    // if (getChildFragmentManager().findFragmentByTag("5") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("5"));
    // }
    //
    // ft.show(getChildFragmentManager().findFragmentByTag("2"));
    // } else {
    // ft.add(R.id.container, classficationFragment, "2");
    // }
    // ft.commit();
    // break;
    //
    // case R.id.tab_three: // ------------ 赚钱
    // checkID = 2;
    // flag = true;
    // tab_three.setVisibility(View.GONE);
    // tab_three2.setScaleX((float) 0.7);
    // tab_three2.setScaleY((float) 0.9);
    // tab_three2.setVisibility(View.VISIBLE);
    // if (mFirstFlag) {// 第一次进来不做动画，以后做动画（赚钱图标）
    // doBigAnima();
    // }
    // mFirstFlag = true;
    // if (getChildFragmentManager().findFragmentByTag("3") != null) {
    //
    // if (getChildFragmentManager().findFragmentByTag("1") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("1"));
    // }
    // if (getChildFragmentManager().findFragmentByTag("4") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("4"));
    // }
    // if (getChildFragmentManager().findFragmentByTag("2") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("2"));
    // }
    // if (getChildFragmentManager().findFragmentByTag("5") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("5"));
    // }
    //
    // ft.show(getChildFragmentManager().findFragmentByTag("3"));
    // } else {
    // ft.add(R.id.container, signFragment, "3");
    // }
    //
    // ft.commit();
    // break;
    //
    // case R.id.tab_four: // ------------ 购物车
    // checkID = 3;
    // if (flag == true) {
    // doSmallAnima();
    // tab_three.setVisibility(View.VISIBLE);
    // tab_three2.setVisibility(View.GONE);
    // doBigAnimaTabThree();
    // flag = false;
    // }
    //
    // if (getChildFragmentManager().findFragmentByTag("1") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("1"));
    // }
    // if (getChildFragmentManager().findFragmentByTag("5") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("4"));
    // }
    // if (getChildFragmentManager().findFragmentByTag("2") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("2"));
    // }
    // if (getChildFragmentManager().findFragmentByTag("3") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("3"));
    // }
    // if (getChildFragmentManager().findFragmentByTag("4") != null) {
    // ft.remove(getChildFragmentManager().findFragmentByTag("4"));
    //
    // }
    // ft.add(R.id.container, shopCartNewNewFragment, "4");
    //
    // ft.commit();
    // break;
    //
    // case R.id.tab_five: // ------------ 我的
    // checkID = 4;
    // if (flag == true) {
    // doSmallAnima();
    // tab_three.setVisibility(View.VISIBLE);
    // tab_three2.setVisibility(View.GONE);
    // doBigAnimaTabThree();
    // flag = false;
    // }
    // if (getChildFragmentManager().findFragmentByTag("5") != null) {
    //
    // if (getChildFragmentManager().findFragmentByTag("1") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("1"));
    // }
    // if (getChildFragmentManager().findFragmentByTag("4") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("4"));
    // }
    // if (getChildFragmentManager().findFragmentByTag("2") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("2"));
    // }
    // if (getChildFragmentManager().findFragmentByTag("3") != null) {
    // ft.hide(getChildFragmentManager().findFragmentByTag("3"));
    // }
    //
    // ft.show(getChildFragmentManager().findFragmentByTag("5"));
    // } else {
    // ft.add(R.id.container, mineIfoFragment, "5");
    // }
    //
    // ft.commit();
    // break;
    //
    // }
}
