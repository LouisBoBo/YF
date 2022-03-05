package com.yssj.ui.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.SwitchButton;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.infos.HelpCenterActivity;
import com.yssj.ui.activity.infos.MyInfoActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.HomePageFragment;
import com.yssj.utils.DeviceUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAppUtil;
import com.yssj.utils.YCache;

public class SettingActivity extends BasicActivity {

    private RelativeLayout rel_my_data, rel_secure, rel_univsersal, rel_about_us, set;

    private TextView tvTitle_base;
    private LinearLayout img_back;
    private SwitchButton sb_clear, sb_start_location;
    private Button btn_loginout;
    private ImageView img_right_icon;

    private AppManager appManager;

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != this.getActionBar()) {
            this.getActionBar().hide();
        }


        appManager = AppManager.getAppManager();
        setContentView(R.layout.setting);

        context = this;
        initView();
    }

    private void initView() {
        tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("设置");
        img_back = (LinearLayout) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        set = (RelativeLayout) findViewById(R.id.set);
        set.setBackgroundColor(Color.WHITE);
        img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
        img_right_icon.setVisibility(View.GONE);
        img_right_icon.setImageResource(R.drawable.mine_message_center);
        img_right_icon.setOnClickListener(this);
        findViewById(R.id.rel_help_center).setOnClickListener(this);

        btn_loginout = (Button) findViewById(R.id.btn_loginout);
        btn_loginout.setOnClickListener(this);

        // sb_clear = (SwitchButton) findViewById(R.id.sb_clear); // 清除缓存
        // // 监听清除缓存按钮
        // sb_clear.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        //
        // @Override
        // public void onCheckedChanged(CompoundButton buttonView, boolean
        // isChecked) {
        //// Toast.makeText(context, "你真的要清除缓存吗", 0).show();
        // }
        // });

        // // 监听开启位置服务按钮
        // sb_start_location = (SwitchButton)
        // findViewById(R.id.sb_start_location); // 开启位置服务
        // sb_start_location.setOnCheckedChangeListener(new
        // OnCheckedChangeListener() {
        //
        // @Override
        // public void onCheckedChanged(CompoundButton buttonView, boolean
        // isChecked) {
        //
        // }
        // });

        rel_my_data = (RelativeLayout) findViewById(R.id.rel_my_data);
        rel_my_data.setOnClickListener(this);
        rel_my_data.setVisibility(View.GONE);
        rel_secure = (RelativeLayout) findViewById(R.id.rel_secure);
        rel_secure.setOnClickListener(this);
        rel_univsersal = (RelativeLayout) findViewById(R.id.rel_univsersal);
        rel_univsersal.setOnClickListener(this);
        rel_about_us = (RelativeLayout) findViewById(R.id.rel_about_us);
        rel_about_us.setOnClickListener(this);

        findViewById(R.id.rel_cheer_us).setOnClickListener(this);
        findViewById(R.id.rel_follow_wx).setOnClickListener(this);
        img_right_icon.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        switch (v.getId()) {
            case R.id.rel_my_data:// 个人资料
                intent = new Intent(this, MyInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_secure:// 账户安全
                intent = new Intent(this, AccountSecureActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_univsersal:// 通用
                intent = new Intent(this, UniversalActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_about_us:// 关于
                intent = new Intent(this, AboutUSActivity.class);
                startActivity(intent);
                break;

            case R.id.img_back:// 返回按钮
                finish();
                break;
            case R.id.btn_loginout:// 退出按钮
                // loginExit(v);
                loginExit();
                break;
            case R.id.img_right_icon:// 消息盒子
                WXminiAppUtil.jumpToWXmini(this);

                break;
            case R.id.rel_cheer_us:// 为衣蝠打分

//                Uri uri = null;
//                try {
//                    uri = Uri.parse("market://details?id=" + getPackageName());
//                    intent = new Intent(Intent.ACTION_VIEW, uri);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                launchAppDetail(getPackageName(), "com.tencent.android.qqdownloader");


                break;
            case R.id.rel_follow_wx:// 关注微信号
                break;
            case R.id.rel_help_center:// 帮助中心
                intent = new Intent(this, HelpCenterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    /**
     * 跳转到app详情界面
     *
     * @param appPkg    App的包名
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public static void launchAppDetail(String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg))
                return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg))
                intent.setPackage(marketPkg);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /****
     * 退出登陆
     */
    private void loginExit() {

        // APP使用時長統計
        if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    long endUseTime = System.currentTimeMillis();
                    try {
                        ComModel2.APPuseTime(SettingActivity.this, (endUseTime - GuideActivity.startUserTime) + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

        UserInfo userInfo = YCache.getCacheUser(SettingActivity.this);
        final int reviewers = userInfo.getReviewers();

        if(reviewers == 1 && !YUrl.debug){

            return;
        }

        int usertype = 0;
        if (userInfo != null) {
            usertype = userInfo.getUsertype();
        }
        String token = YCache.getCacheToken(SettingActivity.this);
        // if (usertype == -1) {// 自拥有账户登陆退出
        LogYiFu.e("STARTTIME", System.currentTimeMillis() + "");

        new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) SettingActivity.this, R.string.wait) {

            protected void onPreExecute() {
                super.onPreExecute();
                LoadingDialog.show(SettingActivity.this);
            }

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                // TODO Auto-generated method stub
                ReturnInfo retInfo = new ReturnInfo();
                retInfo = ComModel.Logout(context, YCache.getCacheToken(SettingActivity.this));
                return retInfo;
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                // TODO Auto-generated method stub

                if (null == e) {
                    LogYiFu.e("ENDTIME", System.currentTimeMillis() + "");
                    if (!result.getStatus().equals("1")) {
                        ToastUtil.showShortText(context, result.getMessage());
                    }


                    if (result.getStatus().equals("1")) {

                        try {
                            HomePageFragment.cardRootView.setVisibility(View.GONE);
                        } catch (Exception e2) {
                        }

                        LogYiFu.e("jizhu", "111");
                        SharedPreferencesUtil.saveBooleanData(context, Pref.SHOWSIGNUPDATA, false);
                        YJApplication.isLogined = false;
                        YCache.cleanToken(SettingActivity.this);
                        YCache.cleanUserInfo(SettingActivity.this);
                        ComModel.clearLoginFlag(SettingActivity.this);

                        YJApplication.APPstartTime = System.currentTimeMillis();
                        // MainFragment fragment = MainMenuActivity.instances
                        // .getFragment();
                        // if (fragment.getChildFragmentManager()
                        // .findFragmentByTag("1") != null) {
                        // fragment.getChildFragmentManager()
                        // .beginTransaction()
                        // .remove(fragment.getChildFragmentManager()
                        // .findFragmentByTag("1")).commit();
                        // }

                        int chan = Integer.parseInt(DeviceUtils.getChannelCode(SettingActivity.this));
                        // if (chan == 47||chan == 48){
//						if(GuideActivity.needShouquan) {
//							//每次登陆都强制微信授权 清除上次微信授权信息
//							final UMSocialService mController =
//									UMServiceFactory.getUMSocialService("");
//							UMWXHandler wxHandler = new
//									UMWXHandler(SettingActivity.this,
//									WxPayUtil.APP_ID, WxPayUtil.APP_SECRET);
//							wxHandler.addToSocialSDK();
//							wxHandler.setRefreshTokenAvailable(false);
//							mController.deleteOauth(context, null, null);
//						}
                        // }

                        SharedPreferencesUtil.saveBooleanData(SettingActivity.this, "isLoginLogin", false);
                        // APP启动次数清零
                        SharedPreferencesUtil.saveStringData(SettingActivity.this, "addNUM", "1");
                        SharedPreferencesUtil.saveStringData(SettingActivity.this, "AppstartNUM", 0 + "");
                        SharedPreferencesUtil.saveBooleanData(context, "kaidiandialogisshowed", false);
                        SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, false);
                        SharedPreferencesUtil.saveStringData(context, Pref.TWOFOLDNESS, "-1");
                        SharedPreferencesUtil.saveStringData(context, Pref.END_DATE, "-1");
                        SharedPreferencesUtil.saveStringData(context, Pref.IS_OPEN, "-1");
                        SharedPreferencesUtil.saveBooleanData(context, "ISCHUCHIDNEGLU", false); // 标志改未登录
                        SharedPreferencesUtil.saveBooleanData(context, Pref.ISKAIDIAN_JUMP_LOGIN, false);
                        SharedPreferencesUtil.saveStringData(context, YConstance.Pref.YIDOU_HALVE_END_TIMES, "0");

                        SharedPreferencesUtil.saveBooleanData(context, "isrelogin", true);

                        if (LoginActivity.instances != null) {
                            LoginActivity.instances.finish();
                        }


                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                        intent.putExtra("login_register", "login");
                        ((FragmentActivity) SettingActivity.this).startActivityForResult(intent, 239);
                        SettingActivity.this.finish();
                    }
                }
                super.onPostExecute(context, result, e);
            }

        }.execute((Void[]) null);

        // } else {// 第三方账户登陆退出
        //
        // new SAsyncTask<String, Void, ReturnInfo>(
        // (FragmentActivity) mContext, R.string.wait) {
        //
        // @Override
        // protected ReturnInfo doInBackground(FragmentActivity context,
        // String... params) throws Exception {
        // // TODO Auto-generated method stub
        // ReturnInfo retInfo = new ReturnInfo();
        // retInfo = ComModel.Logout_Third(mContext, params[0],
        // params[1], params[2]);
        // return retInfo;
        // }
        //
        // @Override
        // protected boolean isHandleException() {
        // return true;
        // }
        //
        // @Override
        // protected void onPostExecute(FragmentActivity context,
        // ReturnInfo result, Exception e) {
        // // TODO Auto-generated method stub
        //
        // if (null == e) {
        //
        // ToastUtil.showShortText(context, result.getMessage());
        // if (result.getStatus().equals("1")) {
        //
        // YCache.cleanToken(mContext);
        // YCache.cleanUserInfo(mContext);
        // ComModel.clearLoginFlag(mContext);
        //
        // MainFragment fragment = MainMenuActivity.instances
        // .getFragment();
        // if (fragment.getChildFragmentManager()
        // .findFragmentByTag("1") != null) {
        // fragment.getChildFragmentManager()
        // .beginTransaction()
        // .remove(fragment
        // .getChildFragmentManager()
        // .findFragmentByTag("1"))
        // .commit();
        // }
        //
        // Intent intent = new Intent(mContext,
        // LoginActivity.class);
        // intent.putExtra("login_register", "login");
        //
        // ((FragmentActivity) mContext)
        // .startActivityForResult(intent, 239);
        // }
        // }
        // super.onPostExecute(context, result, e);
        // }
        //
        // }.execute(userInfo.getUserth_id(), token,
        // "" + userInfo.getUsertype());
        // }

    }

}
