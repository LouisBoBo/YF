package com.yssj.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.data.YDBHelper;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.SwitchData;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelL;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.setting.UserProtocolActivity;
import com.yssj.ui.base.BasicGuideActivity;
import com.yssj.utils.CheckStrUtil;
import com.yssj.utils.CheckVersionUtils;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DeviceUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;
import com.yssj.utils.YunYingTongJi;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

//import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
//import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
//import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/***
 * 引导页
 *
 * @author Administrator
 *
 */
public class GuideActivity extends BasicGuideActivity implements OnPageChangeListener, OnClickListener {
    private RelativeLayout root;
    private String sortDateStr;// 类目时间戳
    private String attrDateStr;// 属性时间戳
    private String tagDateStr;// 标签时间戳
    private String busTagDateStr;// 联盟商家时间戳
    private String typeTagDateStr;// 新的分类数据时间戳
    private String suppLabelDataStr;// 品牌时间戳
    private String friendCircleTagDataStr;// 密友圈热门标签时间戳

//    public static int currentChannel = 9;

    //    private boolean isFirst = true;
//    private static final int[] pics = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};

    private List<View> views;
    private ViewPager vp;
    private PagerAdapter vpAdapter;
    public static boolean needShouquan = false;//是否需要授权
    private boolean isCheckLast = false, isSyncFinish = false;
    private ImageView mIvLogo;
    // private ImageView[] dots;
    private int currentIndex;

    // private LinearLayout ll;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");

    private int addNUM;
    private ImageView mAddPic;
    private TextView mNextStep;

    public static boolean hasSign = true;

    //元购相关
    public static boolean show1yuan = true;
    public static String app_every = "2";//1元购每次抽奖需要的金额


    public static String oneShopPrice = "1.0";
//    public static String oneShopPrice = "9.9";

    public static long startUserTime; // APP开始使用时间
    // // 微信支付参数
    //
    // public static String APP_ID;
    // public static String MCH_ID;
    // public static String API_KEY;
    // public static String APP_SECRET;

    private boolean isGuide = false;

    public static GuideActivity instance;

    private int sycDataNum = 0;

    private boolean isAready = false;

    private boolean isLogined;

    public static HashMap<String, HashMap<String, Object>> textMap = new HashMap<String, HashMap<String, Object>>();//分享文案
    public static SwitchData switchData;

    public static boolean needFengKong = false;
    public static boolean channelFengkong = true;
    public static boolean needCheckClipboard = true;//是否获取剪切板内容


//    public static boolean


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (!isTaskRoot()
                && intent != null
                && intent.hasCategory(Intent.CATEGORY_LAUNCHER)
                && intent.getAction() != null
                && intent.getAction().equals(Intent.ACTION_MAIN)) {
            finish();
            return;
        }

        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        AppManager.getAppManager().addActivity(this);

        instance = this;
//        try {
//            currentChannel = Integer.parseInt(DeviceUtils.getChannelCode(this));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        if ("1".equals(DeviceUtils.getChannelCode(this))) {//1号渠道需要统一隐私权限
//            if (!SharedPreferencesUtil.getBooleanData(this, YCache.START_APP_YSQX, false)) {
//                showYSQX();
//            } else {
//                initStartAPPdata();
//
//            }
//        } else {
//            initStartAPPdata();
//
//        }


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                //测试用
                startActivity(new Intent(GuideActivity.this, MainMenuActivity.class).putExtra("fromGuide",true));
                // }
                GuideActivity.this.finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

        }, 3000);//3秒后执行Runnable中的run方法


    }

    private void showYSQX() {

        final Dialog mDialog = new Dialog(instance, R.style.DialogQuheijiao2);
        View view = View.inflate(instance, R.layout.dialog_start_app_ysqx, null);

        TextView tv_bot = view.findViewById(R.id.tv_bot);

        view.findViewById(R.id.tv_yhxy).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(instance, UserProtocolActivity.class));
            }
        });
        view.findViewById(R.id.tv_ysqx).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(instance, UserProtocolActivity.class)
                        .putExtra("isYSQX", true)

                );

            }
        });

        view.findViewById(R.id.bt1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                finish();
                AppManager.getAppManager().finishAllActivity();
            }
        });
        view.findViewById(R.id.bt2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                SharedPreferencesUtil.saveBooleanData(instance, YCache.START_APP_YSQX, true);
                initStartAPPdata();

            }
        });

        tv_bot.setText("请点击\"同意”开始使用我们的产品和服务,我们尽全力保护您的个人信息安全。");
        // // 创建自定义样式dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();

    }

    private void initStartAPPdata() {
        //先检测更新
        CheckVersionUtils.checkVersionAppStart(this, new CheckVersionUtils.OnNoUpdateListener() {
            @Override
            public void cancel() {
                initStartAPPdata2();

            }
        });

    }

    private void initStartAPPdata2() {
        getImageRadio();// 获取图片压缩比


        isLogined = getSharedPreferences(Pref.isLoginFlag, Context.MODE_PRIVATE).getBoolean(Pref.isLoginFlag,
                false);
        LogYiFu.e("登录测试", "状态---" + isLogined);
        startUserTime = System.currentTimeMillis();

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        LogYiFu.e("hour", hour + "");

        SharedPreferencesUtil.saveBooleanData(instance, Pref.ISSHOWSTORE, true);

        YunYingTongJi.yunYingTongJi(this, 1); // 运营统计APP启动

        Boolean sp = SharedPreferencesUtil.getBooleanData(getApplicationContext(), "isLoginLogin", false);
        LogYiFu.e("sp", sp + "");
        if (sp) {
            appStartCount(); // 以天为单位 统计APP启动次数
        }



        YDBHelper helper = new YDBHelper(this);
        helper.query("select count(*) from sort_info");
        // aBar.hide();
        root = (RelativeLayout) findViewById(R.id.root);
        mAddPic = (ImageView) findViewById(R.id.new_add_pic);
        mNextStep = (TextView) findViewById(R.id.next_step);
        mIvLogo = (ImageView) findViewById(R.id.iv_logo);
        sortDateStr = getSharedPreferences(Pref.sync, Context.MODE_PRIVATE).getString(Pref.sync_sort_date,
                "8813A493A311F4D640B8184000F4B49F");
        attrDateStr = getSharedPreferences(Pref.sync, Context.MODE_PRIVATE).getString(Pref.sync_attr_date, "1");
        tagDateStr = getSharedPreferences(Pref.sync, Context.MODE_PRIVATE).getString(Pref.sync_tag_date,
                "9666D29AFA66DD843782158D3DC52942");
        busTagDateStr = getSharedPreferences(Pref.sync, Context.MODE_PRIVATE).getString(Pref.sync_bus_tag_date, "null");

        typeTagDateStr = getSharedPreferences(Pref.sync, Context.MODE_PRIVATE).getString(Pref.sync_type_tag_data,
                "null");
        // 品牌
        suppLabelDataStr = getSharedPreferences(Pref.sync, Context.MODE_PRIVATE).getString(Pref.sync_supp_label_data,
                "null");
        // 密友圈标签
        friendCircleTagDataStr = getSharedPreferences(Pref.sync, Context.MODE_PRIVATE)
                .getString(Pref.sync_friend_circle_tag_data, "null");

//        isFirst = getSharedPreferences(Pref.is_show_guide, Context.MODE_PRIVATE).getBoolean(Pref.is_show_guide, true);
        this.getSharedPreferences("EveryDayTaskTimer", Context.MODE_PRIVATE).edit().putInt("day", 0).commit();
        // 激活APP
        activate();

        String currettime2 = df.format(new Date());
        SharedPreferencesUtil.saveStringData(getApplicationContext(), "APPStartcountTime", currettime2);

        updateData(); // 同步数据


        File fileDirec = new File(YConstance.savePicPath);
        if (!fileDirec.exists()) {
            fileDirec.mkdir();
        }
    }

    private void initFengkong() {

        //如果已经登录就用用户的风控
        if (isLogined) {
            needFengKong = SharedPreferencesUtil.getBooleanData(instance, YCache.NEED_FENG_KONG, false);
            return;
        }
        //查询渠道风控
        YConn.httpPost(GuideActivity.instance, YUrl.CONFIG_SWITCH, new HashMap<String, String>(), new HttpListener<SwitchData>() {
            @Override
            public void onSuccess(SwitchData result) {
                channelFengkong = result.getData().getMust_risk_management_channel() == 1;
                GuideActivity.needCheckClipboard = result.getData().getConfig_popularize() == 1;
                //渠道的风控优先级最高
                needFengKong = channelFengkong;
                if (GuideActivity.needFengKong) {
                    return;
                }

                //其次是剪切板的
                if (!needCheckClipboard) {
                    return;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //获取剪切板内容并存本地
                        String clipboardContent = CommonUtils.getClipboardContent(instance);
                        if (!StringUtils.isEmpty(clipboardContent)) {
                            if (YUrl.debug) {
                                ToastUtil.showShortText2("剪切板内容是:" + clipboardContent);
                            }
                            if (clipboardContent.startsWith("737072656164")) {
                                SharedPreferencesUtil.saveStringData(instance, YCache.FENGKONG_CLIPBOARDCONTENT, clipboardContent);
                            } else {
                                GuideActivity.needFengKong = true;

                            }
                        } else {
                            GuideActivity.needFengKong = true;
                        }
                    }
                }, 500);


            }

            @Override
            public void onError() {

            }
        });


    }


    private void appStartCount() {
        // TODO Auto-generated method stub

        String currettime1 = df.format(new Date());
        if (SharedPreferencesUtil.getStringData(getApplicationContext(), "APPStartcountTime", "0").equals("0")) {
            // 保存当前时间
            SharedPreferencesUtil.saveStringData(getApplicationContext(), "APPStartcountTime", currettime1);
        }

        // 获取保存的次数
        String getCount = SharedPreferencesUtil.getStringData(getApplicationContext(), "AppstartNUM", "0");
        LogYiFu.e("getCount", getCount);

        addNUM = Integer.parseInt(SharedPreferencesUtil.getStringData(getApplicationContext(), "addNUM", "0")); // 加的数量

        if (getCount.equals("0") || getCount == null) { // 以前没有记录过

            SharedPreferencesUtil.saveStringData(getApplicationContext(), "AppstartNUM", addNUM + "");
            SharedPreferencesUtil.saveStringData(getApplicationContext(), "addNUM", 1 + "");

        } else { // 已经记录过

            String currettime3 = SharedPreferencesUtil.getStringData(getApplicationContext(), "APPStartcountTime", "0");

            LogYiFu.e("3currettime", currettime3);
            LogYiFu.e("1currettime", currettime1);

            if (currettime3.equals(currettime1)) {
                // 如果是同一天就记录

                addNUM += 1;
                // 保存次数
                SharedPreferencesUtil.saveStringData(getApplicationContext(), "AppstartNUM", addNUM + "");
                SharedPreferencesUtil.saveStringData(getApplicationContext(), "addNUM", addNUM + "");

            } else {
                // 如果不是同一天就清零
                SharedPreferencesUtil.saveStringData(getApplicationContext(), "addNUM", "1");
                SharedPreferencesUtil.saveStringData(getApplicationContext(), "AppstartNUM", 0 + "");
            }

        }

    }

    public Handler handler = new Handler();





    @Override
    protected void onResume() {
        super.onResume();
        instance = this;
        isLogined = getSharedPreferences(Pref.isLoginFlag, Context.MODE_PRIVATE).getBoolean(Pref.isLoginFlag,
                false);
//        initFengkong();

        CheckVersionUtils.updateDialogShowEd = false;
//        getSwitch();


        // if (YJApplication.isLogined ||
        // YJApplication.instance.isLoginSucess()) {
        // // 拿到用户的类别、
        // GetUserABClass.getUserABclass(this);
        // }
        //
        // MobclickAgent.onResume(this);
    }

    private void getSwitch() {

        YConn.httpPost(GuideActivity.instance, YUrl.CONFIG_SWITCH, new HashMap<String, String>(), new HttpListener<SwitchData>() {
            @Override
            public void onSuccess(SwitchData result) {
                switchData = result;
            }

            @Override
            public void onError() {

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        // MobclickAgent.onPause(this);
    }

    private List<ImageView> imgList = new ArrayList<ImageView>();


    private void activate() {
        final String channel = DeviceUtils.getChannelCode(this);
        final String imei = CheckStrUtil.getImei(GuideActivity.this);

        LogYiFu.e("AppstartNUM", SharedPreferencesUtil.getStringData(getApplicationContext(), "AppstartNUM", "0"));

        LogYiFu.e("channel", channel);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    ComModel2.startActive(GuideActivity.this, imei, channel, Integer.parseInt(
                            SharedPreferencesUtil.getStringData(getApplicationContext(), "AppstartNUM", "0")));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                // intentTo();
//                if (isFirst) {
//                    if (isCheckLast && isSyncFinish) {
//                        // TODO: 11111此处
//                        intentTo();
//                        // getStartPic();
//                        // ImageUtils.releaseImg(imgList);
//                    }
//                } else {
//                    // TODO: 11111此处
//                    // intentTo();
////                    getStartPic();
//
//                    intentTo();
//                }

                intentTo();

            } else if (msg.what == 0) {
                // 试三次 最后提示 同步异常
                if (sycDataNum > 2) {
                    // ToastUtil.showShortText(GuideActivity.this,
                    // "网络开小差啦，请退出重进");
//                    intentTo();
                } else {
                    updateData();
                }
            }
        }

        ;
    };

    private ReturnInfo ret;

    private void updateData() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {

                    ret = ComModel2.syncDatas(GuideActivity.this, sortDateStr, tagDateStr, typeTagDateStr,
                            suppLabelDataStr, friendCircleTagDataStr, true);
                    LogYiFu.e("自个的时间戳", sortDateStr + "tagDateStr" + tagDateStr + "typeTagDateStr" + typeTagDateStr);
                    isSyncFinish = true;
                    mHandler.sendEmptyMessage(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    sycDataNum++;
                    mHandler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    private void intentTo() {
        startActivity(new Intent(GuideActivity.this, MainMenuActivity.class)
        .putExtra("fromGuide",true)


        );
    }


    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub
//        setCurDot(arg0);
//        if (arg0 == pics.length - 1) {
//
//            // 循环取得小点图片
//            /*
//             * for (int i = 0; i < dots.length; i++) {
//             * dots[i].setVisibility(View.GONE); }
//             */
//            // ll.setVisibility(View.GONE);
//
//            isCheckLast = true;
//            // intentTo();
//        } else {
//            // 循环取得小点图片
//            /*
//             * for (int i = 0; i < dots.length; i++) {
//             * dots[i].setVisibility(View.VISIBLE); }
//             */
//            // ll.setVisibility(View.VISIBLE);
//            isCheckLast = false;
//        }
//        mHandler.sendEmptyMessage(1);
//        LogYiFu.e("TAG", "结束引导界面");
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }


    /**
     * 获取Pic
     */
    private void getStartPic() {
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) GuideActivity.this, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel.startGetPic(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);

                if (null == e && result != null && !("".equals(result))) {

                    String mTime = (String) result.get("time");
                    final String mStartPic = (String) result.get("pic") + "!450";
                    // SetImageLoader.initImageLoader(null, mAddPic, mStartPic,
                    // "");


                    LogYiFu.e("第一张图", mStartPic);

                    PicassoUtils.initImage(GuideActivity.this, mStartPic, mAddPic);

                }

                AlphaAnimation am = new AlphaAnimation(1.0f, 1.0f);
                am.setDuration(1000); // 延时2s
                root.startAnimation(am);

                am.setAnimationListener(new AnimationListener() {

                    public void onAnimationEnd(Animation animation) {
                        // root.setBackgroundResource(R.drawable.guid_in_1);
                        mIvLogo.setVisibility(View.GONE);
                        mAddPic.setVisibility(View.VISIBLE);
                        mNextStep.setVisibility(View.VISIBLE);

                        // final AlphaAnimation am2 = new AlphaAnimation(1.0f,
                        // 1.0f);
                        // am2.setDuration(3000); // 延时2s

                        final ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f,
                                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        scaleAnim.setFillAfter(true);
                        scaleAnim.setDuration(3000);
                        mAddPic.setAnimation(scaleAnim);
                        time = new TimeCount(4000, 1000);
                        time.start();

                         final Runnable runnable = new Runnable() {
                         @Override
                         public void run() {
                         if (!isAready) {
                         startActivity(new Intent(GuideActivity.this,
                         MainMenuActivity.class));
                         overridePendingTransition(android.R.anim.fade_in,
                         android.R.anim.fade_out);
                         }
                         }
                         };
                        mNextStep.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // scaleAnim.cancel();
                                // mAddPic.removeCallbacks(runnable);
                                // am2.cancel();
                                isAready = true;
                                startActivity(new Intent(GuideActivity.this, MainMenuActivity.class)
                                        .putExtra("fromGuide",true)
                                );
                                // }
                                 GuideActivity.this.finish();
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                        });
                        // scaleAnim.setAnimationListener(new
                        // AnimationListener() {
                        //
                        // @Override
                        // public void onAnimationStart(Animation arg0) {
                        // // TODO Auto-generated method stub
                        //
                        // }
                        //
                        // @Override
                        // public void onAnimationRepeat(Animation arg0) {
                        // // TODO Auto-generated method stub
                        //
                        // }
                        //
                        // @Override
                        // public void onAnimationEnd(Animation arg0) {
                        //// mAddPic.postDelayed(new Runnable() {
                        //// @Override
                        //// public void run() {
                        ////// if (!isAready) {
                        ////// startActivity(new Intent(GuideActivity.this,
                        // MainMenuActivity.class));
                        ////// // }
                        ////// // GuideActivity.this.finish();
                        ////// overridePendingTransition(android.R.anim.fade_in,
                        // android.R.anim.fade_out);
                        //////
                        ////// }
                        //// }
                        //// }, 2000);
                        // // mAddPic.postDelayed(runnable, 2000);
                        // }
                        // });

                        // overridePendingTransition(android.R.anim.fade_in,
                        // android.R.anim.fade_out);
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationStart(Animation animation) {
                    }
                });
                //
                // recLen = deadTime - nowTime;
                // mTvTime.setVisibility(View.VISIBLE);-
            }

        }.execute();
    }

    private TimeCount time;

    // 计时器
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔

        }

        @Override
        public void onFinish() {// 计时完毕时触发
            if (!isAready) {
                startActivity(new Intent(GuideActivity.this, MainMenuActivity.class).putExtra("fromGuide",true));
                // }
                 GuideActivity.this.finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            try {
                int i = Integer.valueOf(millisUntilFinished / 1000 + "");
                mNextStep.setText("跳过 " + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getImageRadio() {
        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) GuideActivity.this, 0) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModel2.getImageRadio(GuideActivity.this);

            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null && result.size() > 0) {
                    SharedPreferencesUtil.saveStringData(GuideActivity.this, Pref.IMAGE_RADIO,
                            "" + result.get("img_rate"));
                } else {
                    SharedPreferencesUtil.saveStringData(GuideActivity.this, Pref.IMAGE_RADIO, "450");
                }
            }

        }.execute();

    }


    private void haiWXshouquan() {

        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity)
                this, 0) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity
                                                                     context, Void... params)
                    throws Exception {
                return ComModel2.get1QudaoLoginStyle(getApplicationContext());
            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String,
                    String> result, Exception e) {
                super.onPostExecute(context, result, e);

                if (e == null && result != null) {

                    int style = 1; // 1需要微信授权 2//不需要微信授权

                    try {
                        style = Integer.parseInt(result.get("data") + "");
                    } catch (Exception e2) {
                        // TODO: handle exception
                    }


                    if (style == 1) {
                        needShouquan = true;
                    } else {
                        needShouquan = false;
                    }


                }
            }

        }.execute();
    }


    /**
     * 动态获取分享链接H5域名
     */
    public void getAndroidH5Url() {
        new SAsyncTask<Void, Void, String>(GuideActivity.this, 0) {

            @Override
            protected String doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModelL.getAndroidH5Url(GuideActivity.this);

            }

            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, String result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null) {
                    if (!result.startsWith("http://")) {
                        result = "http://" + result;
                    }

                    if (result.endsWith("/")) {
                        YUrl.YSS_URL_ANDROID_H5 = result;
                    } else {
                        YUrl.YSS_URL_ANDROID_H5 = result + "/";
                    }

                }
            }

        }.execute();

    }
}
