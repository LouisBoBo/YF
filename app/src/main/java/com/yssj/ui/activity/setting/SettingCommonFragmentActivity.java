package com.yssj.ui.activity.setting;

import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.MainFragment;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.setting.BindEmailFragment;
import com.yssj.ui.fragment.setting.BindPhoneFragment;
import com.yssj.ui.fragment.setting.FirstBindEmailFragment;
import com.yssj.ui.fragment.setting.FirstBindPhoneFragment;
import com.yssj.ui.fragment.setting.FirstBindPhoneFragmentChanal;
import com.yssj.ui.fragment.setting.LoginDevicesFragment;
import com.yssj.ui.fragment.setting.LoginDevicesFragment.CloseLoginDevicesFragment;
import com.yssj.ui.fragment.setting.PayPasswordFragment;
import com.yssj.ui.fragment.setting.SecureTipsFragment;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

public class SettingCommonFragmentActivity extends BasicActivity implements CloseLoginDevicesFragment {

    private String flag;
    public static SettingCommonFragmentActivity instanes;
    public static String mThirdParty = "";// 第三方授权登录绑定手机

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_circle_common);

        instanes = this;

        FirstBindPhoneFragmentChanal.phone = "";

        flag = getIntent().getStringExtra("flag");
        mThirdParty = getIntent().getStringExtra("thirdparty");
        LogYiFu.e("ceshic", "2" + SettingCommonFragmentActivity.mThirdParty);
        initFragment();

    }

    /*
     * @Override protected void onResume() { super.onResume();
     * JPushInterface.onResume(this); }
     *
     * @Override protected void onPause() { super.onPause();
     * JPushInterface.onPause(this);
     *
     * }
     */
    @Override
    protected void onResume() {
        super.onResume();
        // MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
//		YJApplication.getLoader().stop();
        // MobclickAgent.onPause(this);
    }

    private void initFragment() {
        if ("loginDevicesFragment".equals(flag)) {
            LoginDevicesFragment mFragment = new LoginDevicesFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
            mFragment.setCloseLoginDevicesFragment(this);
        }
        if ("secureTipsFragment".equals(flag)) {
            SecureTipsFragment mFragment = new SecureTipsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
        }

        if ("payPasswordFragment".equals(flag)) {
            PayPasswordFragment mFragment = new PayPasswordFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
        }
        if ("bindEmailFragment".equals(flag)) {
            boolean isBool = getIntent().getBooleanExtra("bool", false);
            String emailNum = getIntent().getStringExtra("emailNum");
            if (isBool) {
                BindEmailFragment mFragment = new BindEmailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("emailNum", emailNum);
                mFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();

            } else {
                FirstBindEmailFragment mFragment = new FirstBindEmailFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
            }
        }
        if ("bindPhoneFragment".equals(flag)) {
            boolean isChanal = getIntent().getBooleanExtra("isChanal", false);
            boolean isBool = getIntent().getBooleanExtra("bool", false);
            String phoneNum = getIntent().getStringExtra("phoneNum");


            if (isBool) {
                BindPhoneFragment mFragment = new BindPhoneFragment();
                Bundle bundle = new Bundle();
                bundle.putString("phoneNum", phoneNum);
                mFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
            } else {

                if (isChanal && !MainMenuActivity.isNewQudao) {   //说明是渠道包


                    if (LoginActivity.isOldLogin && !LoginActivity.sanFangShow) {  //渠道的第一次綁定手機
                        //跳至渠道的绑定手机界面
                        FirstBindPhoneFragmentChanal mFragment = new FirstBindPhoneFragmentChanal();
                        Bundle bundle = new Bundle();
                        bundle.putString("thirdparty", mThirdParty);
                        mFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
                    }

                    // 跳至普通的绑定手机界面
                    if (!LoginActivity.isOldLogin && LoginActivity.sanFangShow) {  //渠道的第一次綁定手機   显示正常的登录界面
                        FirstBindPhoneFragment mFragment = new FirstBindPhoneFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("thirdparty", mThirdParty);
                        mFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
                    }


                } else {
                    FirstBindPhoneFragment mFragment = new FirstBindPhoneFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("thirdparty", mThirdParty);
                    mFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
                }


            }
        }

		/*
         * if("updateNickNameFragment".equals(flag)){ String nickName =
		 * getIntent().getStringExtra("nickName"); UpdateNickNameFragment
		 * mFragment= new UpdateNickNameFragment(); Bundle bundle = new
		 * Bundle(); bundle.putString("nickName", nickName);
		 * mFragment.setArguments(bundle);
		 * getSupportFragmentManager().beginTransaction
		 * ().replace(R.id.fl_content, mFragment).commit(); }
		 */

    }

    @Override
    public void closeFragment() {
        if ("thirdparty".equals(mThirdParty)
                && YCache.getCacheUser(SettingCommonFragmentActivity.this).getPhone().length() < 11) {
            UserInfo userInfo = YCache.getCacheUser(SettingCommonFragmentActivity.this);
            int usertype = 0;
            if (userInfo != null) {
                usertype = userInfo.getUsertype();
            }
            String token = YCache.getCacheToken(SettingCommonFragmentActivity.this);


            //APP使用時長統計
            if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        long endUseTime = System.currentTimeMillis();
                        try {
                            ComModel2.APPuseTime(SettingCommonFragmentActivity.this, (endUseTime - GuideActivity.startUserTime) + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }


            // if (usertype == -1) {// 自拥有账户登陆退出

            new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) SettingCommonFragmentActivity.this,
                    R.string.wait) {

                @Override
                protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                    // TODO Auto-generated method stub
                    ReturnInfo retInfo = new ReturnInfo();
                    retInfo = ComModel.Logout(context, YCache.getCacheToken(SettingCommonFragmentActivity.this));
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
                        if (!result.getStatus().equals("1")) {
                            ToastUtil.showShortText(context, result.getMessage());
                        }
                        if (result.getStatus().equals("1")) {

                            SharedPreferencesUtil.saveBooleanData(context, Pref.SHOWSIGNUPDATA, false);
                            YCache.cleanToken(SettingCommonFragmentActivity.this);
                            YCache.cleanUserInfo(SettingCommonFragmentActivity.this);
                            ComModel.clearLoginFlag(SettingCommonFragmentActivity.this);
                            MainFragment fragment = MainMenuActivity.instances.getFragment();
                            if (fragment.getChildFragmentManager().findFragmentByTag("1") != null) {
                                fragment.getChildFragmentManager().beginTransaction()
                                        .remove(fragment.getChildFragmentManager().findFragmentByTag("1")).commit();
                            }

                            SharedPreferencesUtil.saveBooleanData(SettingCommonFragmentActivity.this, "isLoginLogin",
                                    false);
                            // APP启动次数清零
                            SharedPreferencesUtil.saveStringData(SettingCommonFragmentActivity.this, "addNUM", "1");
                            SharedPreferencesUtil.saveStringData(SettingCommonFragmentActivity.this, "AppstartNUM",
                                    0 + "");
                            SharedPreferencesUtil.saveBooleanData(context, "isrelogin", true);
                            SharedPreferencesUtil.saveBooleanData(context, "ISCHUCHIDNEGLU", false);  //标志改为未登录
                            SharedPreferencesUtil.saveBooleanData(context, Pref.ISKAIDIAN_JUMP_LOGIN, false);
                            SharedPreferencesUtil.saveStringData(context, YConstance.Pref.YIDOU_HALVE_END_TIMES, "0");

                            YJApplication.APPstartTime = System.currentTimeMillis();

                            if (LoginActivity.instances != null) {
                                LoginActivity.instances.finish();
                            }

                            Intent intent = new Intent(SettingCommonFragmentActivity.this, LoginActivity.class);
                            intent.putExtra("login_register", "login");

                            ((FragmentActivity) SettingCommonFragmentActivity.this).startActivityForResult(intent, 239);
                            finish();
                        }
                    }
                    super.onPostExecute(context, result, e);
                }

            }.execute((Void[]) null);
        } else {
            this.finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0
                && ("account").equals(getIntent().getStringExtra("wallet"))) {


            if (getIntent().getBooleanExtra("tishiBind", false)) {
                finish();
                overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);

            } else {


                // 这里重写返回键
                if ("thirdparty".equals(mThirdParty)
                        && YCache.getCacheUser(SettingCommonFragmentActivity.this).getPhone().length() < 11) {
                    UserInfo userInfo = YCache.getCacheUser(SettingCommonFragmentActivity.this);
                    int usertype = 0;
                    if (userInfo != null) {
                        usertype = userInfo.getUsertype();
                    }
                    String token = YCache.getCacheToken(SettingCommonFragmentActivity.this);


                    //APP使用時長統計
                    if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                long endUseTime = System.currentTimeMillis();
                                try {
                                    ComModel2.APPuseTime(SettingCommonFragmentActivity.this, (endUseTime - GuideActivity.startUserTime) + "");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();


                    }


                    // if (usertype == -1) {// 自拥有账户登陆退出

                    new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) SettingCommonFragmentActivity.this,
                            R.string.wait) {

                        @Override
                        protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                            // TODO Auto-generated method stub
                            ReturnInfo retInfo = new ReturnInfo();
                            retInfo = ComModel.Logout(context, YCache.getCacheToken(SettingCommonFragmentActivity.this));
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
                                    LogYiFu.e("jizhu", "111");
                                    SharedPreferencesUtil.saveBooleanData(context, Pref.SHOWSIGNUPDATA, false);
                                    YJApplication.isLogined = false;
                                    YCache.cleanToken(SettingCommonFragmentActivity.this);
                                    YCache.cleanUserInfo(SettingCommonFragmentActivity.this);
                                    ComModel.clearLoginFlag(SettingCommonFragmentActivity.this);
//								MainFragment fragment = MainMenuActivity.instances
//										.getFragment();
//								if (fragment.getChildFragmentManager()
//										.findFragmentByTag("1") != null) {
//									fragment.getChildFragmentManager()
//											.beginTransaction()
//											.remove(fragment.getChildFragmentManager()
//													.findFragmentByTag("1")).commit();
//								}


                                    SharedPreferencesUtil.saveBooleanData(SettingCommonFragmentActivity.this, "isLoginLogin", false);
                                    //APP启动次数清零
                                    SharedPreferencesUtil.saveStringData(SettingCommonFragmentActivity.this, "addNUM", "1");
                                    SharedPreferencesUtil.saveStringData(SettingCommonFragmentActivity.this, "AppstartNUM", 0 + "");

                                    SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, false);
                                    SharedPreferencesUtil.saveStringData(context, Pref.TWOFOLDNESS, "-1");
                                    SharedPreferencesUtil.saveStringData(context, Pref.END_DATE, "-1");
                                    SharedPreferencesUtil.saveStringData(context, Pref.IS_OPEN, "-1");
                                    SharedPreferencesUtil.saveBooleanData(context, "ISCHUCHIDNEGLU", false);  //标志改未登录
                                    SharedPreferencesUtil.saveBooleanData(context, Pref.ISKAIDIAN_JUMP_LOGIN, false);
                                    SharedPreferencesUtil.saveBooleanData(context, "isrelogin", true);
                                    SharedPreferencesUtil.saveStringData(context, YConstance.Pref.YIDOU_HALVE_END_TIMES, "0");

                                    if (LoginActivity.instances != null) {
                                        LoginActivity.instances.finish();
                                    }
                                    Intent intent = new Intent(SettingCommonFragmentActivity.this,
                                            LoginActivity.class);
                                    intent.putExtra("login_register", "login");

                                    ((FragmentActivity) SettingCommonFragmentActivity.this).startActivityForResult(
                                            intent, 239);
                                    SettingCommonFragmentActivity.this.finish();
                                }
                            }
                            super.onPostExecute(context, result, e);
                        }

                    }.execute((Void[]) null);

                } else {

                    Intent intent = new Intent(this, AccountSecureActivity.class);
                    startActivity(intent);
                    this.overridePendingTransition(R.anim.activity_from_right_publish,
                            R.anim.activity_from_right_publish_close);
                    finish();
                }

            }
            return true;

        }

        return super.onKeyDown(keyCode, event);

    }

}
