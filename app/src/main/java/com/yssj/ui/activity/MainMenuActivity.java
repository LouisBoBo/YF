package com.yssj.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.entity.BaseData;
import com.yssj.entity.OneBuyStaus;
import com.yssj.entity.SwitchData;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelL;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.service.LocationService;
import com.yssj.ui.activity.infos.FundDetailsActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.main.FilterResultActivity;
import com.yssj.ui.activity.main.SearchResultActivity;
import com.yssj.ui.activity.shopdetails.MatchDetailsActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.PingTuanDialog;
import com.yssj.ui.fragment.HomePageFragment;
import com.yssj.ui.fragment.MyShopFragment.onSearchListener;
import com.yssj.ui.fragment.setting.FirstBindPhoneFragmentChanal;
import com.yssj.upyun.UpYunException;
import com.yssj.upyun.UpYunUtils;
import com.yssj.upyun.Uploader;
import com.yssj.utils.CheckVersionUtils;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.GetShopCart;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ShareUtil;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.TakePhotoUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;
import com.yssj.utils.YunYingTongJi;

import org.apache.commons.lang.time.DateFormatUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.yssj.ui.activity.GuideActivity.app_every;
import static com.yssj.ui.activity.GuideActivity.oneShopPrice;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
//import com.umeng.analytics.MobclickAgent;
//import com.yssj.ui.dialog.DialogExitThirty;

public class MainMenuActivity extends BasicActivity implements onSearchListener {

    private int mTitleRes;
    // protected LeftFragment mFrag;

    // private static SlidingMenu sm;

    private AppManager appManager;
    public static String kefu;

    // private Dialog dialog;

    private boolean mIsTwoGroup = false;

    public static MainMenuActivity instances;

    public static HashMap<String, String> userGradeTable = new HashMap<String, String>(); // ???????????????????????????

    private Boolean isLogin;
    // ??????????????????

    public static String APP_ID = YUrl.APP_ID;
    public static String MCH_ID = YUrl.MCH_ID;
    public static String API_KEY = YUrl.API_KEY;
    public static String APP_SECRET = YUrl.APP_SECRET;
    public static String specialCart = "";
    public static SwitchData.DataBean mSwitchData;
    private Context mContext;
    private Dialog mDialog;
    private boolean isFromSHYsign;
    private boolean fromGuide;


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();


    }


    private boolean isLogined = false;

    private String toYf;
    private String toFriends = "";
    private String toShop = "";
    private String toHome = "";
    private String mShopCart = "";

    public static boolean noUserRegist = true; // ????????????????????????

    //??????????????????
    public static boolean isNewQudao = true;//?????????????????????????????????????????????????????????(?????????)//????????????????????????????????????


    public static boolean homeIsSign;//??????????????????????????????????????????

    @SuppressWarnings("unused")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appManager = AppManager.getAppManager();
        appManager.addActivity(this);
        instances = this;
        mContext = this;

        setContentView(R.layout.content_frame);

        isLogined = getSharedPreferences(Pref.isLoginFlag, Context.MODE_PRIVATE).getBoolean(Pref.isLoginFlag, false);
        YJApplication.instance.setLoginSucess(isLogined);


        isLogin = YJApplication.instance.isLoginSucess();
        isFromSHYsign = getIntent().getBooleanExtra("isFromSHYsign", false);
        fromGuide = getIntent().getBooleanExtra("fromGuide", false);
        if (isFromSHYsign) {
            ToastUtil.showShortText2("??????????????????????????????????????????");
        }

        registerMessageReceiver();
        HomePageFragment.setOnSearchListener(this);

        getOneYuanStatus();
        getWXpayParam(1);

        //????????????????????????----
        getShareContent();
        if (isLogined) {
            // ???????????????????????????????????????????????????
            GetShopCart.querShopCart(MainMenuActivity.this, 0);
            // ??????????????????
        }

        initFengkong();


        MainFragment fragment = MainFragment.newInstance(this);
        mIsTwoGroup = instances.getIntent().getBooleanExtra("mIsTwoGroup", false);
        String canYunumber = instances.getIntent().getStringExtra("CanYunumber");
        int now_type_id = instances.getIntent().getIntExtra("now_type_id", 0);
        toFriends = instances.getIntent().getStringExtra("toFriends");
        toShop = instances.getIntent().getStringExtra("toShop");
        toHome = instances.getIntent().getStringExtra("toHome");

        mShopCart = getIntent().getStringExtra("shopcart");
        Boolean isExit = false;
        toYf = getIntent().getStringExtra("toYf");


//        getNoPayOrder();


        if (toFriends != null && toFriends.equals("toFriends")) {
            fragment.setCheckID(2);
        } else if ("shopcart".equals(mShopCart)) { // ??????????????????
            // YJApplication.instance.setCheckID(3);
            fragment.setCheckID(3);
        } else if (null != toYf) {
            fragment.setCheckID(0); // ???????????????
        } else if (toShop != null && toShop.equals("toShop")) {
            fragment.setCheckID(1); // ???????????????
        } else if (toHome != null && toHome.equals("toHome")) {
            fragment.setCheckID(0); // ???????????????
        } else {// APP???????????? ??????????????????

            // ???????????? ----???????????????
            initChannel(fragment);

        }

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, "tag").commit();


        // ??????????????????????????????????????????????????? ??????????????????
        if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) {
            if (mIsTwoGroup) {
                new PingTuanDialog(instances, R.style.DialogQuheijiao2, "SendPinTaunSucceed").show();
            }
        }

    }

    private boolean isFirstGetLocationPersimmions;
    public static final int loginToSign = 1;
    public static final int loginToWithdrawal = 2;
    public static final int loginToVip = 3;
    private final int SDK_PERMISSION_REQUEST = 127;
    private LocationService locationService;

    private void initLDY() {
        if (!fromGuide) {
            return;
        }

        YConn.httpPost(this, YUrl.CONFIG_SWITCH, new HashMap<String, String>(), new HttpListener<SwitchData>() {
            @Override
            public void onSuccess(SwitchData result) {

                mSwitchData = result.getData();
                //??????
//                mSwitchData.setApp_landing_page_channel(0);
//                mSwitchData.setIsUserReviewers(0);
//                mSwitchData.setReviewers_channel(0);
//                mSwitchData.setIsLoginToSign(0);
                if (!isLogined && result.getData().getIsUserReviewers() == 1) {
                    getLocationPersimmions();
                    return;
                }


                if (!isLogined && result.getData().getReviewers_channel() == 1) {
                    isFirstGetLocationPersimmions = true;
                    getLocationPersimmions();

                    return;
                }

                if (mSwitchData.getApp_landing_page_channel() == 1) {

                    //?????????????????????????????????????????????????????????
                    if (YJApplication.instance.isLoginSucess() && YCache.getCacheUser(instances).getReviewers() == 1) {
                        //??????????????????????????????
                        long serviceTime = System.currentTimeMillis() + YJApplication.serviceDifferenceTime;
                        String add_date = "";
                        try {
                            add_date = YCache.getCacheUser(instances).getAdd_date().split(" ")[0];
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String currentDate = DateFormatUtils.format(serviceTime, "yyyy-MM-dd");
                        if (add_date.equals(currentDate)) {
                            SharedPreferencesUtil.saveStringData(instances, "commonactivityfrom", "sign");
                            instances.startActivity(new Intent(instances, CommonActivity.class));
                        }
                    } else {
                        SharedPreferencesUtil.saveStringData(instances, "commonactivityfrom", "sign");
                        instances.startActivity(new Intent(instances, CommonActivity.class));
                    }


                } else {

                    if (isLogined) {
                        return;
                    }

                    int homePageSwitchLoginTo = 0;
                    if (mSwitchData.getIsLoginToSign() == 1) {
                        homePageSwitchLoginTo = loginToSign;
                    }

                    if (homePageSwitchLoginTo == 0 && mSwitchData.getIsLoginToWithdrawal() == 1) {
                        homePageSwitchLoginTo = loginToWithdrawal;
                    }

                    if (homePageSwitchLoginTo == 0 && mSwitchData.getIsLoginToVip() == 1) {
                        homePageSwitchLoginTo = loginToVip;
                    }

                    if (homePageSwitchLoginTo > 0) {
                        Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                        intent.putExtra("login_register", "login");
                        intent.putExtra("homePageSwitchLoginTo", homePageSwitchLoginTo);
                        startActivity(intent);
                    }

                }
            }

            @Override
            public void onError() {

            }
        });


    }

    private String permissionInfo;

    @TargetApi(23)
    private void getLocationPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * ??????????????????????????????????????????????????????????????????????????????
             */
            // ??????????????????
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * ????????????????????????????????????????????????(????????????)???????????????????????????????????????????????????????????????
             */
            // ????????????
//            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
//            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            } else {
//                ToastUtil.showShortText2("???????????????????????????");
                if (mSwitchData.getIsUserReviewers() == 1) {
                    Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                    intent.putExtra("login_register", "login");
                    if (mSwitchData.getApp_landing_page_channel() == 1) {
                        intent.putExtra("homePageSwitchLoginTo", MainMenuActivity.loginToSign);
                    }
                    intent.putExtra("reviewers", 1);
                    startActivity(intent);
                    return;
                }
                startGetLocation();

            }
        } else {
            if (mSwitchData.getIsUserReviewers() == 1) {
                Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                intent.putExtra("login_register", "login");
                if (mSwitchData.getApp_landing_page_channel() == 1) {
                    intent.putExtra("homePageSwitchLoginTo", MainMenuActivity.loginToSign);
                }
                intent.putExtra("reviewers", 1);
                startActivity(intent);
                return;
            }
            startGetLocation();
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        // ????????????????????????????????????,?????????????????????,??????????????????
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }
        } else {
            return true;
        }
    }

    private void startGetLocation() {
        // -----------location config ------------
        locationService = ((YJApplication) getApplication()).locationService;
        //??????locationservice????????????????????????????????????1???location???????????????????????????????????????????????????activity?????????????????????????????????locationservice?????????
        locationService.registerListener(mGetLocationListener);
        //????????????
        LocationClientOption locationClientOption = locationService.getDefaultLocationClientOption();
        locationService.setLocationOption(locationClientOption);
        locationService.start();// ??????SDK


    }

    private BDAbstractLocationListener mGetLocationListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(final BDLocation bdLocation) { //????????????
//            showDialog(bdLocation.getCity() + bdLocation.getDistrict());

            if (null != bdLocation && bdLocation.getLocType() != BDLocation.TypeServerError) {
                if (null != locationService) {
                    locationService.unregisterListener(mGetLocationListener); //???????????????
                    locationService.stop(); //??????????????????
                }
                if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {//???????????????
                    showOpenLocationDialog();
                    if (YUrl.debug) {
                        ToastUtil.showShortText2("??????????????????");
                    }
                    return;

                }
                if (mSwitchData.getIsUserReviewers() == 1) {
                    Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                    intent.putExtra("login_register", "login");
                    if (mSwitchData.getApp_landing_page_channel() == 1) {
                        intent.putExtra("homePageSwitchLoginTo", MainMenuActivity.loginToSign);
                    }
                    intent.putExtra("reviewers", 1);
                    startActivity(intent);
                    if (null != mDialog) {
                        mDialog.dismiss();
                    }

                    return;
                }

                if (null == bdLocation.getCity()) {
                    Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                    intent.putExtra("login_register", "login");
                    if (mSwitchData.getApp_landing_page_channel() == 1) {
                        intent.putExtra("homePageSwitchLoginTo", MainMenuActivity.loginToSign);
                    }
                    intent.putExtra("reviewers", 1);
                    startActivity(intent);
                    if (null != mDialog) {
                        mDialog.dismiss();
                    }
                    if (YUrl.debug) {
                        ToastUtil.showMyToast(mContext,
                                "??????????????????" + bdLocation.getProvince() + bdLocation.getCity() + bdLocation.getDistrict()
                                , 5000
                        );
                    }
                    return;
                }
                HashMap map = new HashMap<String, String>();
                map.put("province", bdLocation.getProvince() + "");
                map.put("city", bdLocation.getCity() + "");
                map.put("district", bdLocation.getDistrict() + "");

                YConn.httpPost(mContext, YUrl.CHECK_SHENHE_LOCATION, map, new HttpListener<BaseData>() {
                    @Override
                    public void onSuccess(BaseData result) {
                        //??????
//                        result.setData(0);
//                        mSwitchData.setApp_landing_page_channel(1);
                        if (result.getData() == 1) {
                            Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                            intent.putExtra("login_register", "login");
                            if (mSwitchData.getApp_landing_page_channel() == 1) {
                                intent.putExtra("homePageSwitchLoginTo", MainMenuActivity.loginToSign);
                            }
                            intent.putExtra("reviewers", 1);
                            startActivity(intent);
                            if (YUrl.debug) {
                                ToastUtil.showMyToast(mContext,
                                        "??????????????????" + bdLocation.getProvince() + bdLocation.getCity() + bdLocation.getDistrict()
                                        , 5000
                                );
                            }

                        } else {

                            if (mSwitchData.getReviewers_channel() == 1) {


                                Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                                intent.putExtra("mustLogin", true);
                                if (mSwitchData.getApp_landing_page_channel() == 1) {
                                    intent.putExtra("homePageSwitchLoginTo", MainMenuActivity.loginToSign);
                                }
                                startActivity(intent);

                            } else if (mSwitchData.getApp_landing_page_channel() == 1) {
                                SharedPreferencesUtil.saveStringData(instances, "commonactivityfrom", "sign");
                                instances.startActivity(new Intent(instances, CommonActivity.class));
                            }
                            if (YUrl.debug) {
                                ToastUtil.showMyToast(mContext,
                                        "?????????????????????????????????"
                                        , 5000
                                );
                            }


                        }

                        if (null != mDialog) {
                            mDialog.dismiss();
                        }

                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        }
    };
    private boolean goSetLocationSwitch = false;

    private void showOpenLocationDialog() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);

        View view = View.inflate(mContext, R.layout.dialog_get_location_permission, null);
        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView bt1 = view.findViewById(R.id.bt1);
        tv1.setText("?????????????????????????????????????????????????????????????????????????????????");
        tv2.setVisibility(View.GONE);
        bt1.setText("?????????");

        view.findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //?????????????????????
                try {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                    goSetLocationSwitch = true;
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    LogYiFu.e("??????", "??????????????????????????????");
                }

            }
        });


        // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();

    }


    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == 0 && grantResults[1] == 0) {
//            ToastUtil.showShortText2("??????????????????????????????");

            if (mSwitchData.getIsUserReviewers() == 1) {
                Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                intent.putExtra("login_register", "login");
                if (mSwitchData.getApp_landing_page_channel() == 1) {
                    intent.putExtra("homePageSwitchLoginTo", MainMenuActivity.loginToSign);
                }
                intent.putExtra("reviewers", 1);
                startActivity(intent);

                if (null != mDialog) {
                    mDialog.dismiss();
                }
                return;
            }
            startGetLocation();
        } else { //???????????????

            if (mSwitchData.getIsUserReviewers() == 1) {
                Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                intent.putExtra("login_register", "login");
                if (mSwitchData.getApp_landing_page_channel() == 1) {
                    intent.putExtra("homePageSwitchLoginTo", MainMenuActivity.loginToSign);
                }
                intent.putExtra("reviewers", 1);
                startActivity(intent);

                if (null != mDialog) {
                    mDialog.dismiss();
                }
                return;
            }

            if (isFirstGetLocationPersimmions) {
                isFirstGetLocationPersimmions = false;
                showPerimRefusedDialog();
                return;
            } else {
                Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                String pkg = "com.android.settings";
                String cls = "com.android.settings.applications.InstalledAppDetails";
                i.setComponent(new ComponentName(pkg, cls));
                i.setData(Uri.parse("package:" + getPackageName()));
                startActivity(i);

                ToastUtil.showShortText2("??????????????????????????????????????????????????????");
            }


        }
    }


    private void showPerimRefusedDialog() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.dialog_get_location_permission, null);
        view.findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocationPersimmions();
            }
        });
        // ?????????????????????dialog
        mDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);
        mDialog.show();
    }


    private void initChannel(MainFragment fragment) {

        FirstBindPhoneFragmentChanal.lei = "A";
        LoginActivity.noUserRegist = true;
        LoginActivity.isOldLogin = true;
        LoginActivity.sanFangShow = false;


        if (GuideActivity.hasSign) {
            if (homeIsSign) {
                SharedPreferencesUtil.saveStringData(instances, "commonactivityfrom", "sign");
                instances.startActivity(new Intent(instances, CommonActivity.class));
            }


        }

        // ????????????????????????---??????APP??????
        if (!homeIsSign || !GuideActivity.hasSign) {
            CheckVersionUtils.checkVersion(MainMenuActivity.this);
        }


    }


    private void getShareContent() {

        new SAsyncTask<Void, Void, HashMap<String, HashMap<String, Object>>>((FragmentActivity) this, 0) {

            @Override
            protected HashMap<String, HashMap<String, Object>> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModelL.getSpellGroupsText();
            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, HashMap<String, Object>> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null) {

                    GuideActivity.textMap = result;

                }
            }

        }.execute();
    }


    // private static final int MSG_SET_TAGS = 10099;
    public static boolean isForeground = false;

    public static final String MESSAGE_RECEIVED_ACTION = "com.yssj.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    // for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;


    private void initFengkong() {

        isLogined = getSharedPreferences(Pref.isLoginFlag, Context.MODE_PRIVATE).getBoolean(Pref.isLoginFlag, false);
        //???????????????????????????????????????
        if (isLogined) {
            GuideActivity.needFengKong = SharedPreferencesUtil.getBooleanData(instances, YCache.NEED_FENG_KONG, false);
            initLDY();
            return;
        }
        //??????????????????
        YConn.httpPost(GuideActivity.instance, YUrl.CONFIG_SWITCH, new HashMap<String, String>(), new HttpListener<SwitchData>() {
            @Override
            public void onSuccess(SwitchData result) {

                mSwitchData = result.getData();

                GuideActivity.channelFengkong = result.getData().getMust_risk_management_channel() == 1;
                GuideActivity.needCheckClipboard = result.getData().getConfig_popularize() == 1;


                //???????????????????????????
                GuideActivity.needFengKong = GuideActivity.channelFengkong;
                if (GuideActivity.needFengKong) {
                    initLDY();
                    return;
                }


                //??????????????????
                if (!GuideActivity.needCheckClipboard) {
                    initLDY();
                    return;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //?????????????????????????????????
                        String clipboardContent = CommonUtils.getClipboardContent(MainMenuActivity.this);
                        if (!StringUtils.isEmpty(clipboardContent)) {
                            if (clipboardContent.startsWith("737072656164")) {
                                SharedPreferencesUtil.saveStringData(MainMenuActivity.this, YCache.FENGKONG_CLIPBOARDCONTENT, clipboardContent);
                            } else {
                                GuideActivity.needFengKong = true;

                            }
                        } else {
                            GuideActivity.needFengKong = true;

                        }

                        initLDY();

                    }
                }, 500);
            }

            @Override
            public void onError() {

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        CommonUtils.disableScreenshots(this);

        if (goSetLocationSwitch) {
            goSetLocationSwitch = false;
            getLocationPersimmions();
            return;
        }


        if (YJApplication.instance.isLoginSucess()) {
            HashMap<String, String> pairsMap = new HashMap<>();
            YConn.httpPost(MainMenuActivity.this, YUrl.NEED_JUM_FREE_LING, pairsMap
                    , new HttpListener<BaseData>() {
                        @Override
                        public void onSuccess(BaseData baseData) {
                            if (baseData.getIsJumpPage() == 1) {

                                if (null != HomePageThreeActivity.instance) {
                                    HomePageThreeActivity.instance.finish();
                                }


                                startActivity(new Intent(MainMenuActivity.this, HomePageThreeActivity.class)
                                        .putExtra("buyVipFreeBuy", true)
                                        .putExtra("freeBuyType", 2)
                                        .putExtra("freeOrderPage", baseData.getFreeOrderPage())
                                        .putExtra("freeMoney", baseData.getFreeMoney() + "")

                                );
                                MainMenuActivity.this.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                            }

                        }

                        @Override
                        public void onError() {

                        }
                    });
        }


//        if (YJApplication.instance.isOpen() == 2) {
//            if ("3".equals(YJApplication.instance.getShop_type())) {
//                Intent intent = new Intent(this, MatchDetailsActivity.class);
//                intent.putExtra("collocation_code", YJApplication.instance.getCode());
//                startActivity(intent);
//            } else {
//                Intent intent = new Intent(this, ShopDetailsActivity.class);
//                intent.putExtra("code", YJApplication.instance.getCode());
//                intent.putExtra("isMeal", YJApplication.instance.isMeal());
//                startActivity(intent);
//            }
//            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
//            ;
//            YJApplication.instance.setOpen(0);
//        } else if (YJApplication.instance.isOpen() == 1) {
//            Intent intent = null;
//            if (YJApplication.instance.getCode().contains("type2")) {
//                intent = new Intent(this, SearchResultActivity.class);
//                intent.putExtra("id", YJApplication.instance.getCode().split("=")[1]);
//            } else {
//                intent = new Intent(this, FilterResultActivity.class);
//                intent.putExtra("isTuijian", true);
//                intent.putExtra("title", YJApplication.instance.getCode());
//            }
//            startActivity(intent);
//            YJApplication.instance.setOpen(0);
//        } else if (YJApplication.instance.isOpen() == 4) {
//            Intent intent = new Intent(this, MessageCenterActivity.class);
//            startActivity(intent);
//            YJApplication.instance.setOpen(0);
//        } else if (YJApplication.instance.isOpen() == 5) {
//            Intent intent = new Intent(this, FundDetailsActivity.class);
//            intent.putExtra("index", 3);
//            if ("1".equals(YJApplication.instance.getCode())) {
//                intent.putExtra("isShwo", true);
//            }
//            startActivity(intent);
//            YJApplication.instance.setOpen(0);
//        }

        if (YJApplication.instance.isLoginSucess() == false) {
            getFragment().setCheckID();
        }

    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instances = null;
        if (mMessageReceiver != null)
            unregisterReceiver(mMessageReceiver);
        appManager.finishActivity(this);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!TextUtils.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                // setCostomMsg(showMsg.toString());
            }
        }
    }

    // private void checkIsShow() {
    //
    // }

    private Dialog loadingDialog;

    // private void showShareDialog() {
    //
    // AlertDialog.Builder builder = new Builder(MainMenuActivity.this);
    // View view = View.inflate(MainMenuActivity.this,
    // R.layout.show_share_dialog, null);
    // Button btn_cancel = (Button) view.findViewById(R.id.btn_close_dialog);
    // btn_cancel.setOnClickListener(new OnClickListener() {
    //
    // @Override
    // public void onClick(View arg0) {
    // // TODO Auto-generated method stub
    // loadingDialog.dismiss();
    // }
    // });
    //
    // Button btn_share = (Button) view.findViewById(R.id.btn_share);
    // btn_share.setOnClickListener(new OnClickListener() {
    //
    // @Override
    // public void onClick(View arg0) {
    // // TODO Auto-generated method stub
    // loadingDialog.dismiss();
    //
    // LoadingDialog.show(MainMenuActivity.this);
    // // getSharedPreferences("isFirstH5Login",
    // // Context.MODE_PRIVATE).edit().putBoolean("isFirstH5Login",
    // // false).commit();
    // new AsyncTask<Void, Void, Void>() {
    // @Override
    // protected Void doInBackground(Void... arg0) {
    //
    // downloadPic("http://yssj668.b0.upaiyun.com/share/android/900_900_1_android.jpg");
    // return null;
    // }
    //
    // @Override
    // protected void onPostExecute(Void result) {
    // super.onPostExecute(result);
    // LoadingDialog.hide(MainMenuActivity.this);
    // ShareUtil.shareMainMenu(MainMenuActivity.this, file);
    // };
    //
    // }.execute();
    // }
    // });
    // loadingDialog = new Dialog(this, R.style.dialog_tran);// ?????????????????????dialog
    //
    // loadingDialog.setCancelable(false);// ?????????????????????????????????
    // loadingDialog.setContentView(view, new
    // LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
    // LinearLayout.LayoutParams.MATCH_PARENT));// ????????????
    //
    // loadingDialog.show();
    //
    // /*
    // * dialog2 = builder.create(); dialog2.setView(view, 0, 0, 0, 0);
    // * dialog2.setCancelable(true); dialog2.show();
    // */
    //
    // }

    private File file;

    private void downloadPic(String picPath) {
        try {
            URL url = new URL(picPath);
            // ????????????
            URLConnection con = url.openConnection();
            // ?????????????????????
            int contentLength = con.getContentLength();
            // ?????????
            InputStream is = con.getInputStream();
            // 1K???????????????
            byte[] bs = new byte[8192];
            // ????????????????????????
            int len;
            File fileDirec = new File(YConstance.savePicPath);
            if (!fileDirec.exists()) {
                fileDirec.mkdir();
            }
            // ?????????????????? /sdcard/yssj/
            file = new File(YConstance.savePicPath, "yssjH5Share.jpg");
            if (file.exists()) {
                file.delete();
            }
            OutputStream os = new FileOutputStream(file);
            // ????????????
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            os.close();
            is.close();
        } catch (Exception e) {
            LogYiFu.e("TAG", "????????????");
            e.printStackTrace();
        }
    }

    /*
     * @Override public boolean onOptionsItemSelected(MenuItem item) { switch
     * (item.getItemId()) { case android.R.id.home: toggle(); return true;
     *
     * case R.id.github: Util.goToGitHub(this); return true;
     *
     * } return super.onOptionsItemSelected(item); }
     */
    @Override
    public void onBackPressed() {
        // if (sm.isMenuShowing()) {
        // sm.showContent();
        // } else {
        super.onBackPressed();
        // }
    }

    // private GetSlidingMenu getSlidingMenu;
    //
    // public interface GetSlidingMenu {
    // void getSliding(SlidingMenu menu);
    // }
    //
    // public void setGetSlidingMenu(Fragment mFragment) {
    // getSlidingMenu = (GetSlidingMenu) mFragment;
    // getSlidingMenu.getSliding(sm);
    // }
    //
    // public void setGetSlidingMenu(FrameLayout fl) {
    // getSlidingMenu = (GetSlidingMenu) fl;
    // getSlidingMenu.getSliding(sm);
    // }

    @Override
    public void onSearch() {
        // TODO Auto-generated method stub
        // toggle();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // // ????????????????????????
            // if (sm.isMenuShowing()) {
            // sm.showContent();
            // } else {
            // if (MainFragment.mIntemateFaBuRootView.getVisibility() ==
            // View.VISIBLE) {
            // MainFragment.mIntemateFaBuRootView.setVisibility(View.GONE);
            // return false;
            // }
            exitBy2Click();
            // }
        }
        return false;
    }

    private void loginHxServer(final UserInfo result, final String password) {
//        EMChatManager.getInstance().login(result.getUser_id() + "", password, new EMCallBack() {// ??????
//            @Override
//            public void onSuccess() {
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        EMGroupManager.getInstance().loadAllGroups();
//                        EMChatManager.getInstance().loadAllConversations();
//                        LogYiFu.d("main", "??????????????????????????????");
//                        // ???????????? ??????????????????
//                    }
//                });
//            }
//
//            @Override
//            public void onProgress(int progress, String status) {
//
//            }
//
//            @Override
//            public void onError(int code, String message) {
//                // LogYiFu.d("main", "??????????????????????????????");
//                // ToastUtil.showShortText(getActivity(), "??????????????????????????????");
//                if (YJApplication.instance.isLoginSucess()) {
//                    if (password != null) {
//                        loginHxServer(result, password);
//                    }
//                }
//            }
//        });
    }

    /**
     * ??????????????????
     */
    private Boolean isExit = false;

    private void exitBy2Click() {
        final Timer tExit = new Timer();
        final String type = SharedPreferencesUtil.getStringData(instances, Pref.TONGJI_TYPE, "-1");
        final String tongji_page = SharedPreferencesUtil.getStringData(instances, Pref.TONGJI_PAGE, "-1");
        final String gouwupage_current_pager = SharedPreferencesUtil.getStringData(instances,
                Pref.GOUWUPAGE_CURRENT_PAGER, "-1");

        if (isExit == false) {
            isExit = true; // ????????????

            // String yuancishu30 =
            // SharedPreferencesUtil.getStringData(instances, "yuancishu30",
            // "0");
            ToastUtil.showShortText(MainMenuActivity.this, "????????????????????????");
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // ????????????
                }
            }, 2000); // ??????2?????????????????????????????????????????????????????????????????????????????????

            // if (yuancishu30.equals("3")) {
            //
            // // ?????????????????????3??????---???????????????
            // // Toast.makeText(MainMenuActivity.this, "????????????????????????",
            // // Toast.LENGTH_SHORT).show();
            //
            // ToastUtil.showShortText(instances, "????????????????????????");
            //
            // tExit.schedule(new TimerTask() {
            // @Override
            // public void run() {
            // isExit = false; // ????????????
            // }
            // }, 2000); // ??????2?????????????????????????????????????????????????????????????????????????????????
            //
            // } else { // ?????? ??????????????????3???

            // if (!isLogin) {
            // new DialogExitThirty(MainMenuActivity.this, R.style.DialogStyle1,
            // false, type, tongji_page,
            // gouwupage_current_pager).show();
            // }
            //
            // if (isLogin) {
            //
            // // ??????????????????
            // new SAsyncTask<Void, Void, HashMap<String, Object>>(this,
            // R.string.wait) {
            //
            // @Override
            // protected HashMap<String, Object> doInBackground(FragmentActivity
            // context, Void... params)
            // throws Exception {
            // return ComModel2.checkStore(context);
            // }
            //
            // @Override
            // protected boolean isHandleException() {
            // return true;
            // }
            //
            // @Override
            // protected void onPostExecute(FragmentActivity context,
            // HashMap<String, Object> result,
            // Exception e) {
            // super.onPostExecute(context, result, e);
            // if (e == null && result != null) {
            //
            // String rrr = (String) result.get("is_store");
            // if (rrr.equals("false")) { // ?????????
            //
            // ToastUtil.showShortText(instances, "????????????????????????");
            //
            // tExit.schedule(new TimerTask() {
            // @Override
            // public void run() {
            // isExit = false; // ????????????
            // }
            // }, 2000); // ??????2?????????????????????????????????????????????????????????????????????????????????
            //
            // } else {// ?????????
            // new DialogExitThirty(instances, R.style.DialogStyle1, false,
            // type, tongji_page,
            // gouwupage_current_pager).show();
            // }
            //
            // }
            // }
            //
            //// }.execute();
            // }

            // }

        } else {

            // YunYingTongJi.yunYingTongJi(context, type);

            if (gouwupage_current_pager.equals("gouwu")) {

                YunYingTongJi.yunYingTongJi(instances, Integer.parseInt(type));

                // APP??????????????????
                if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {

                            long endUseTime = System.currentTimeMillis();
                            try {
                                ComModel2.APPuseTime(instances, (endUseTime - GuideActivity.startUserTime) + "");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }

                if (HomePageFragment.cardRootView != null) {
                    if (HomePageFragment.cardRootView.getVisibility() == View.VISIBLE && cardQuitListener != null) {
                        cardQuitListener.delete();
                    }
                }

                YJApplication.instance.isShowUpdateDialog(false);

                if (null != GuideActivity.instance) {
                    GuideActivity.instance.finish();
                }

                YJApplication.tongJiYet = false;

                finish();

                AppManager.getAppManager().finishAllActivity();
                // android.os.Process.killProcess(android.os.Process.myPid()) ;

                return;
            }

            if (tongji_page.equals("??????") && type.equals("1001")) {
                YunYingTongJi.yunYingTongJi(instances, Integer.parseInt(type));
            }
            if (tongji_page.equals("??????") && type.equals("1012")) {
                YunYingTongJi.yunYingTongJi(instances, Integer.parseInt(type));
            }
            if (tongji_page.equals("??????") && type.equals("1013")) {
                YunYingTongJi.yunYingTongJi(instances, Integer.parseInt(type));
            }
            if (tongji_page.equals("??????") && type.equals("1014")) {
                YunYingTongJi.yunYingTongJi(instances, Integer.parseInt(type));
            }
            if (tongji_page.equals("??????") && type.equals("1015")) {
                YunYingTongJi.yunYingTongJi(instances, Integer.parseInt(type));
            }
            if (tongji_page.equals("??????") && type.equals("1016")) {
                YunYingTongJi.yunYingTongJi(instances, Integer.parseInt(type));
            }
            if (tongji_page.equals("??????") && type.equals("1017")) {
                YunYingTongJi.yunYingTongJi(instances, Integer.parseInt(type));
            }

            // APP??????????????????
            if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        long endUseTime = System.currentTimeMillis();
                        try {
                            ComModel2.APPuseTime(instances, (endUseTime - GuideActivity.startUserTime) + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
            try {
                if (HomePageFragment.cardRootView.getVisibility() == View.VISIBLE && cardQuitListener != null) {
                    cardQuitListener.delete();
                }
            } catch (Exception e) {
            }

            YJApplication.instance.isShowUpdateDialog(false);
            if (null != GuideActivity.instance) {
                GuideActivity.instance.finish();
            }

            finish();
            AppManager.getAppManager().finishAllActivity();

            // android.os.Process.killProcess(android.os.Process.myPid()) ;

            // System.exit(0);
        }
    }

    // ??????APP?????? ???????????? ?????????????????????????????????
    private CardQuitListener cardQuitListener;

    public void setCardQuitListener(CardQuitListener cardQuitListener) {
        this.cardQuitListener = cardQuitListener;
    }

    /**
     * ??????APP?????? ???????????? ?????????????????????????????????
     *
     * @date 2017???3???21?????????4:52:26
     */
    public interface CardQuitListener {
        public void delete();
    }

    //int requestCode, int resultCode, Intent data
    @Override
    public void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);


        if (arg1 == 102) {// ?????????????????????????????????????????????
            ((MainFragment) getSupportFragmentManager().findFragmentByTag("tag")).setIndex(3);
        } else if (arg1 == 10001) {
            ((MainFragment) getSupportFragmentManager().findFragmentByTag("tag")).setIndex(1);
        } else if (arg0 == 10002 && arg1 == 1) { // ???????????? ????????????????????????
            ((MainFragment) getSupportFragmentManager().findFragmentByTag("tag")).setIndex(4);
        } else if (arg1 == 1) {
            ((MainFragment) getSupportFragmentManager().findFragmentByTag("tag")).setIndex(3);

        } else if (arg0 == TakePhotoUtil.RESULT_LOAD_PICTURE && arg1 == RESULT_OK) {
            submit("sdcard/temp.jpg");
        } else if (arg0 == 10086 && arg1 == RESULT_OK) {
            TakePhotoUtil.cropImageUri(this, arg2 == null ? null : arg2.getData());
        } else if (arg1 == RESULT_OK && arg0 == 13334) {
            // ((ClassificationFragment) ((MainFragment)
            // getSupportFragmentManager().findFragmentByTag("tag"))
            // .getChildFragmentManager().findFragmentByTag("1")).initData();
        } else if (arg1 == RESULT_OK && arg0 == 236) {// ?????????
            if (YJApplication.instance.isLoginSucess() == false) {
                ((MainFragment) getSupportFragmentManager().findFragmentByTag("tag")).setCheckID();
            } else {
                ((MainFragment) getSupportFragmentManager().findFragmentByTag("tag")).setIndex(0);
            }

        } else if (arg1 == RESULT_OK && arg0 == 237) {// ?????????

            if (YJApplication.instance.isLoginSucess() == false) {
                ((MainFragment) getSupportFragmentManager().findFragmentByTag("tag")).setCheckID();
            } else {
                ((MainFragment) getSupportFragmentManager().findFragmentByTag("tag")).setIndex(3);
            }

        } else if (arg1 == RESULT_OK && arg0 == 238) {// ????????????

            if (YJApplication.instance.isLoginSucess() == false) {
                ((MainFragment) getSupportFragmentManager().findFragmentByTag("tag")).setCheckID();
            } else {

                ((MainFragment) getSupportFragmentManager().findFragmentByTag("tag")).setIndex(4);
            }
        } else if (arg1 == RESULT_OK && arg0 == 239) {
            if (YJApplication.instance.isLoginSucess() == false) {
                ((MainFragment) getSupportFragmentManager().findFragmentByTag("tag")).setIndex(3);
            } else {

                ((MainFragment) getSupportFragmentManager().findFragmentByTag("tag")).setIndex(4);
            }
        } else if (arg1 == RESULT_OK && arg0 == 56) {
            if (YJApplication.instance.isLoginSucess()) {
                // ????????????
                LoadingDialog.show(this);
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... arg0) {

                        downloadPic("http://yssj668.b0.upaiyun.com/share/android/900_900_1_android.jpg");
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        super.onPostExecute(result);
                        LoadingDialog.hide(MainMenuActivity.this);
                        ShareUtil.shareH5(MainMenuActivity.this, file);
                    }

                    ;

                }.execute();
            }
        } else if (arg1 == RESULT_OK && arg0 == 101) {
            HomePageFragment fragment = (HomePageFragment) getFragment().getChildFragmentManager()
                    .findFragmentByTag("2");

            // fragment.getItemFragment().setLikeStatus(
            // arg2.getIntExtra("isLike", 0));
        }
    }

    private void submit(String path) {

        new SAsyncTask<String, Void, String>(this, R.string.wait) {

            @Override
            protected String doInBackground(FragmentActivity context, String... params) throws Exception {
                String string = null;

                try {
                    // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                    String SAVE_KEY = File.separator + "upload/personal" + File.separator + System.currentTimeMillis()
                            + ".jpg";

                    // ??????base64????????????policy
                    String policy = UpYunUtils.makePolicy(SAVE_KEY, Uploader.EXPIRATION, Uploader.BUCKET);

                    // ????????????api???????????????policy????????????
                    // ???????????????????????????????????????????????????????????????????????????http?????????????????????????????????
                    String signature = UpYunUtils.signature(policy + "&" + Uploader.TEST_API_KEY);

                    // ????????????????????????bucket?????????
                    string = Uploader.upload(policy, signature, Uploader.BUCKET, params[0]);

                } catch (UpYunException e) {
                    e.printStackTrace();
                }

                return string;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, String result) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result);
                if (result != null) {
                    LogYiFu.e("result", result);
                    // ?????????????????????
                    submitUserImg(result);
                }

            }

        }.execute(path);
    }

    public MainFragment getFragment() {
        return ((MainFragment) getSupportFragmentManager().findFragmentByTag("tag"));
    }

    /**
     * ??????????????????
     */
    private void submitUserImg(String picPath) {
        new SAsyncTask<String, Void, Void>(this, R.string.wait) {

            @Override
            protected Void doInBackground(FragmentActivity context, String... params) throws Exception {
                // TODO Auto-generated method stub
                ComModel2.resetStorePic(context, params[0]);
                return null;
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, Void result, Exception e) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result, e);
                if (e == null) {
                    if (((MainFragment) getSupportFragmentManager().findFragmentByTag("tag")) != null) {
                        ((MainFragment) getSupportFragmentManager().findFragmentByTag("tag")).setRefresh();
                    }
                } else {
                    Toast.makeText(MainMenuActivity.this, "??????~?????????...", Toast.LENGTH_SHORT).show();
                }
            }

        }.execute(picPath);
    }

    private void getWXpayParam(final int ci) {

        APP_ID = YUrl.APP_ID;
        MCH_ID = YUrl.MCH_ID;
        API_KEY = YUrl.API_KEY;
        APP_SECRET = YUrl.APP_SECRET;

        new SAsyncTask<Void, Void, HashMap<String, String>>(this, R.string.wait) {

            @Override
            protected boolean isHandleException() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                // TODO Auto-generated method stub
                return ComModel2.getWXParam(context);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                // TODO Auto-generated method stub
                if (e == null) {
                    if (result.get("status").equals("1")) {

                        switch (ci) {

                            case 1://???????????????WXAPPKEY

                                if (null == result.get("appID") || "".equals(APP_ID = result.get("appID"))) {
                                    getWXpayParam(2);
                                } else {
                                    APP_ID = result.get("appID");
                                    MCH_ID = result.get("mchID");
                                    APP_SECRET = result.get("AppSecret");
                                    API_KEY = result.get("key");
                                }


                                break;
                            case 2://???????????????WXAPPKEY

                                if (null == result.get("appID") || "".equals(APP_ID = result.get("appID"))) {
                                    getWXpayParam(3);
                                } else {
                                    APP_ID = result.get("appID");
                                    MCH_ID = result.get("mchID");
                                    APP_SECRET = result.get("AppSecret");
                                    API_KEY = result.get("key");
                                }


                                break;
                            case 3://???????????????WXAPPKEY

                                if (null == result.get("appID") || "".equals(APP_ID = result.get("appID"))) {
                                    APP_ID = YUrl.APP_ID;
                                    MCH_ID = result.get("mchID");
                                    APP_SECRET = result.get("AppSecret");
                                    API_KEY = result.get("key");
                                } else {
                                    APP_ID = result.get("appID");
                                    MCH_ID = result.get("mchID");
                                    APP_SECRET = result.get("AppSecret");
                                    API_KEY = result.get("key");
                                }


                                break;

                        }


                    }
                }
                super.onPostExecute(context, result, e);
            }

        }.execute();

    }


    private void getOneYuanStatus() {


        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    OneBuyStaus obs = ComModel2.getOneYuanStatus(MainMenuActivity.this);

                    GuideActivity.show1yuan = "0".equals(obs.getData().getApp_status());
                    app_every = obs.getData().getApp_every();
                    if (null == app_every) {
                        app_every = "2";
                    }
//                    oneShopPrice = new java.text.DecimalFormat("#0.0")
//                            .format(Double.parseDouble(obs.getData().getApp_value()));

                    oneShopPrice = obs.getData().getApp_value();
                    if (null == oneShopPrice) {
                        oneShopPrice = "9.9";
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        if (null != locationService) {
            locationService.unregisterListener(mGetLocationListener); //???????????????
            locationService.stop(); //??????????????????
        }
        super.onStop();
    }
}
