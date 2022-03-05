package com.yssj.ui.fragment.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.CheckView;
import com.yssj.custom.view.LineEditText;
import com.yssj.custom.view.NewPDialog;
import com.yssj.entity.MyToggleButton;
import com.yssj.entity.QueryPhoneInfo;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ShopCart;
import com.yssj.entity.UserInfo;
import com.yssj.huanxin.PublicUtil;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.service.ApkDownloadManager;
import com.yssj.ui.activity.MainFragment;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.infos.MyPayPassActivity;
import com.yssj.ui.activity.infos.SetMyPayPassActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.setting.AccountSecureActivity;
import com.yssj.ui.activity.setting.FeedBackActivity;
import com.yssj.ui.activity.setting.SettingActivity;
import com.yssj.ui.activity.setting.SettingCommonFragmentActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.CenterToast;
import com.yssj.utils.CheckStrUtil;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

@SuppressWarnings("WrongConstant")
public class FirstBindPhoneFragmentChanal extends BaseFragment implements OnClickListener {

    private TextView tvTitle_base;
    private LinearLayout img_back;

    private EditText et_phone_num;
    private RadioGroup rg_love, rg_dingjia;
    private RadioButton button_han, button_ou, button_ri, button_age1, button_age2, button_age3, button_age4;
    private RadioButton button_skirt1, button_skirt2, button_skirt3, button_skirt4;
    private RadioButton button_money1, button_money2, button_money3, button_money4;
    private RadioButton button_shihui, button_xiaozi, button_qinshe;
    private TextView btn_next_step, numb3;
    private String exe;
    public static String mThirdParty = "";
    public Activity activity;

    private int age = -1;
    private int skirt = -1;
    private int money = -1;
    private int love = -1;
    private int price = -1;

    public static String phone = "";//用户保存在用户120到及时结束时返回到这个界面的手机号，在替换下个fragmeng时赋值


    //渠道包在此切换AB类
    public static String lei = "A"; // A类 B类
    private ImageView et_auto_xx; //图片验证码--输入框的XX
    private EditText et_auto; //验证码输入的内容
//    private CheckView ck_auto; //图片验证码--图片


    private String[] res = new String[4]; // 获取每次更新的验证码，可用于判断用户输入是否正确
    private ImageView ivGif;


    @Override
    public View initView() {
        view = View.inflate(context, R.layout.activity_setting_first_bind_phone_chanal, null);
        activity = getActivity();
        view.setBackgroundColor(Color.WHITE);
        tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("绑定手机");
        img_back = (LinearLayout) view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        rg_love = (RadioGroup) view.findViewById(R.id.rg_love);
        rg_dingjia = (RadioGroup) view.findViewById(R.id.rg_dingjia);

        et_phone_num = (EditText) view.findViewById(R.id.et_phone_num);
        numb3 = (TextView) view.findViewById(R.id.numb3);


        //验证码相关
        et_auto_xx = (ImageView) view.findViewById(R.id.et_auto_xx);
        et_auto = (EditText) view.findViewById(R.id.et_auto);
//        ck_auto = (com.yssj.custom.view.CheckView) view.findViewById(R.id.ck_auto);
        et_auto_xx.setOnClickListener(this);
//        ck_auto.setOnClickListener(this);


        ivGif = (ImageView) view.findViewById(R.id.iv_gif);


        ivGif.setOnClickListener(this);

        button_han = (RadioButton) view.findViewById(R.id.button_han);
        button_han.setOnClickListener(this);

        button_ou = (RadioButton) view.findViewById(R.id.button_ou);
        button_ou.setOnClickListener(this);

        button_ri = (RadioButton) view.findViewById(R.id.button_ri);
        button_ri.setOnClickListener(this);

        button_shihui = (RadioButton) view.findViewById(R.id.button_shihui);
        button_shihui.setOnClickListener(this);

        button_xiaozi = (RadioButton) view.findViewById(R.id.button_xiaozi);
        button_xiaozi.setOnClickListener(this);

        button_qinshe = (RadioButton) view.findViewById(R.id.button_qinshe);
        button_qinshe.setOnClickListener(this);

        button_age1 = (RadioButton) view.findViewById(R.id.button_age1);
        button_age1.setOnClickListener(this);

        button_age2 = (RadioButton) view.findViewById(R.id.button_age2);
        button_age2.setOnClickListener(this);

        button_age3 = (RadioButton) view.findViewById(R.id.button_age3);
        button_age3.setOnClickListener(this);

        button_age4 = (RadioButton) view.findViewById(R.id.button_age4);
        button_age4.setOnClickListener(this);

        button_skirt1 = (RadioButton) view.findViewById(R.id.button_skirt1);
        button_skirt1.setOnClickListener(this);

        button_skirt2 = (RadioButton) view.findViewById(R.id.button_skirt2);
        button_skirt2.setOnClickListener(this);

        button_skirt3 = (RadioButton) view.findViewById(R.id.button_skirt3);
        button_skirt3.setOnClickListener(this);

        button_skirt4 = (RadioButton) view.findViewById(R.id.button_skirt4);
        button_skirt4.setOnClickListener(this);

        button_money1 = (RadioButton) view.findViewById(R.id.button_money1);
        button_money1.setOnClickListener(this);

        button_money2 = (RadioButton) view.findViewById(R.id.button_money2);
        button_money2.setOnClickListener(this);

        button_money3 = (RadioButton) view.findViewById(R.id.button_money3);
        button_money3.setOnClickListener(this);

        button_money4 = (RadioButton) view.findViewById(R.id.button_money4);
        button_money4.setOnClickListener(this);

        btn_next_step = (TextView) view.findViewById(R.id.btn_next_step);
        btn_next_step.setOnClickListener(this);
        exe = getActivity().getIntent().getStringExtra("wallet");


        ivGif.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void initData() {

        // if(lei == 0 ){ // A类
        // numb3.setVisibility(View.GONE);
        // rg_dingjia.setVisibility(View.GONE);
        // }

        // 初始化验证码
//        res = ck_auto.getValidataAndSetImage();


        et_auto.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String et_auto_text = et_auto.getText().toString();
                if (et_auto_text.length() > 0) {
                    et_auto_xx.setVisibility(View.VISIBLE);
                } else {
                    et_auto_xx.setVisibility(View.GONE);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String code = s.toString();
                if (code != null && code.length() == 4) {

                    String inputCode = "";
                    for (int i = 0; i < res.length; i++) {
                        inputCode += res[i];
                    }

                }

            }
        });


        //手机号输入框的监听
        et_phone_num.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                String code = et_phone_num.getText().toString().trim();
                if (code.length() > 0) {
                    if (code.length() == 11) {
                        PublicUtil.setVCode(context, ivGif, code);
                        ivGif.setVisibility(View.VISIBLE);
                    }
                }


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });


        //如果这个phone有的话，说明是重120秒过了  重新返回的--直接获取图形验证码
        if (null != phone && phone.length() == 11) {
            et_phone_num.setText(phone);
//            String code = et_phone_num.getText().toString().trim();
//            PublicUtil.setVCode(context, ivGif, code);
            ivGif.setVisibility(View.VISIBLE);
        }


    }

    private static AlertDialog dialog;

    private void customDialog(final Activity activity) {
        AlertDialog.Builder builder = new Builder(activity);
        // 自定义一个布局文件
        View view = View.inflate(activity, R.layout.dialog_huodong_finish, null);
        TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
        tv_des.setText("很抱歉，您未满足该活动包的注册条件，点击确定立即下载普通版。");
        // dialog.setCancelable(false);
        Button ok = (Button) view.findViewById(R.id.ok);
        ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setVisibility(View.GONE);
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 把这个对话框取消掉
                dialog.dismiss();
                // activity.finish();
                // AppManager.getAppManager().finishAllActivity();

            }
        });

        ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ToastUtil.showShortText(activity, "已为您开始下载普通版衣蝠,请稍后~");
                checkVersion2();

                YCache.cleanToken(activity);
                YCache.cleanUserInfo(activity);
                ComModel.clearLoginFlag(activity);
                dialog.dismiss();
//				activity.finish();

                // System.exit(0);
                // AppManager.getAppManager().finishAllActivity();
            }
        });

        builder.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {


                dialog.dismiss();
                CommonUtils.finishActivity(MainMenuActivity.instances);

                // 跳至首页
                Intent intent2 = new Intent((Activity) activity, MainMenuActivity.class);
                intent2.putExtra("toHome", "toHome");
                activity.startActivity(intent2);
                ((Activity) activity).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                activity.finish();


            }
        });

        dialog = builder.create();
        dialog.setView(view, 0, 0, 0, 0);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void bindPhone() {

        final String num = et_phone_num.getText().toString();
        if (TextUtils.isEmpty(num)) {
            ToastUtil.showShortText(context, "输入您要绑定的手机号");
            return;
        }


//-------------------------验证码相关----------------------------------------------------------------

        String auto = et_auto.getText().toString().trim();
        if (auto.length() == 0) {
            ToastUtil.showShortText(getActivity(), "请输入验证码");
            return;
        }

//
//        String code = "";
//        for (int i = 0; i < res.length; i++) {
//            code += res[i];
//        }
//        LogYiFu.e("inputCode", "" + code);
//        if (!auto.toLowerCase().equals(code.toLowerCase())) {
//            ToastUtil.showShortText(getActivity(), "请输入正确的验证码");
//            // 初始化验证码
////            res = ck_auto.getValidataAndSetImage();
//            return;
//        }

//-------------------------选择题相关----------------------------------------------------------------
        if (love == -1) {
            ToastUtil.showShortText(context, "请选择您的最爱");
            return;
        }

        if (age == -1) {
            ToastUtil.showShortText(context, "请选择您的年龄段");
            return;
        }
        if (age == -1) {
            ToastUtil.showShortText(context, "请选择您的年龄段");
            return;
        }

        if (skirt == -1) {
            ToastUtil.showShortText(context, "请选择您的职业");
            return;
        }
        if (money == -1) {
            ToastUtil.showShortText(context, "请选择衣服的价位");
            return;
        }

        if (lei.equals("B")) {
            // B类
            if (age == 4 && skirt == 3) {

                customDialog(getActivity());
                return;
            }
        }

        //检查是否绑定过手机
        new SAsyncTask<Void, Void, QueryPhoneInfo>((FragmentActivity) context, R.string.wait) {

            @Override
            protected QueryPhoneInfo doInBackground(FragmentActivity context, Void... params) throws Exception {

                return ComModel.checkPhoneIsBind(context, num);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, QueryPhoneInfo result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result != null && "1".equals(result.getStatus()) && result.isBool() == false) {

                        String num = et_phone_num.getText().toString().trim();

                        //获取手机验证码
                        getCode(num);


                    } else if (result != null && "1".equals(result.getStatus()) && result.isBool() == true) {

                        ToastUtil.showLongText(context, "该手机号已绑定其他账号，请更换其他号码");

                    } else if (result != null && "50".equals(result.getStatus())) {
                        ToastUtil.showLongText(context, "支付密码未初始化，请先设置支付密码才能绑定手机");
                        Intent intent = new Intent(getActivity(), SetMyPayPassActivity.class);
                        startActivity(intent);
                    } else {

                        ToastUtil.showLongText(context, result.getMessage());
                    }
                }
            }
        }.execute();
    }


    private void getCode(final String num) {


        new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) context, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel.sendPhoneVerifyCodeFirstVcode(context, num, 7, et_auto.getText().toString().trim());
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result != null && "1".equals(result.getStatus())) {
                        phone = et_phone_num.getText().toString().trim();


                        // 往下一步跳转
                        Fragment mFragment = new FirstBindPhoneIdentityCodeFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("num", num);
                        bundle.putString("age", age + "");
                        bundle.putString("isChannl", "isChannl");
                        mFragment.setArguments(bundle);
                        context.getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment)
                                .commitAllowingStateLoss();


//                        new CountDownTimer(120000L, 1000) {
//
//                            @Override
//                            public void onTick(long millisUntilFinished) {
////                                btn_get_identify_code.setText(String.valueOf(millisUntilFinished / 1000) + "秒重发");
////                                btn_get_identify_code.setEnabled(false);
//                            }
//
//                            @Override
//                            public void onFinish() {
//
//
//
//
//
//
//                            }
//                        }.start();
//
                    } else {


                    }
                    ToastUtil.showShortText(context, result.getMessage());
                }
            }

        }.execute();
    }


    @Override
    public void onClick(View v) {
        Fragment mFragment;
        switch (v.getId()) {


            case R.id.et_auto_xx:
                //清除验证码
                et_auto.getText().clear();
                break;


            case R.id.ck_auto:
                // 重新初始化验证码
//                res = ck_auto.getValidataAndSetImage();
                et_auto.setText("");
                break;


            case R.id.img_back:
                // Bundle bundle = getArguments();
                // if (bundle != null) {
                // mThirdParty = bundle.getString("thirdparty");
                // }

                if(getActivity().getIntent().getBooleanExtra("bindSelf",false)){
                    getActivity().finish();
                }else{


                LogYiFu.e("ceshic", "1" + SettingCommonFragmentActivity.mThirdParty);
                String phone = YCache.getCacheUser(context).getPhone();
                if ("thirdparty".equals(SettingCommonFragmentActivity.mThirdParty)
                        && ("null".equals(phone) || "".equals(phone) || phone.length() < 11)) {
                    LogYiFu.e("ceshic", "" + SettingCommonFragmentActivity.mThirdParty);
                    UserInfo userInfo = YCache.getCacheUser(getActivity());
                    int usertype = 0;
                    if (userInfo != null) {
                        usertype = userInfo.getUsertype();
                    }
                    String token = YCache.getCacheToken(getActivity());
                    // if (usertype == -1) {// 自拥有账户登陆退出

                    new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) getActivity(), R.string.wait) {

                        @Override
                        protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                            // TODO Auto-generated method stub
                            ReturnInfo retInfo = new ReturnInfo();
                            retInfo = ComModel.Logout(context, YCache.getCacheToken(getActivity()));
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
                                    YCache.cleanToken(getActivity());
                                    YCache.cleanUserInfo(getActivity());
                                    ComModel.clearLoginFlag(getActivity());
                                    YJApplication.APPstartTime = System.currentTimeMillis();
                                    // MainFragment fragment =
                                    // MainMenuActivity.instances
                                    // .getFragment();
                                    // if (fragment.getChildFragmentManager()
                                    // .findFragmentByTag("1") != null) {
                                    // fragment.getChildFragmentManager()
                                    // .beginTransaction()
                                    // .remove(fragment.getChildFragmentManager()
                                    // .findFragmentByTag("1")).commit();
                                    // }

                                    SharedPreferencesUtil.saveBooleanData(getActivity(), "isLoginLogin", false);
                                    // APP启动次数清零
                                    SharedPreferencesUtil.saveStringData(getActivity(), "addNUM", "1");
                                    SharedPreferencesUtil.saveStringData(getActivity(), "AppstartNUM", 0 + "");

                                    SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, false);
                                    SharedPreferencesUtil.saveStringData(context, Pref.TWOFOLDNESS, "-1");
                                    SharedPreferencesUtil.saveStringData(context, Pref.END_DATE, "-1");
                                    SharedPreferencesUtil.saveStringData(context, Pref.IS_OPEN, "-1");
                                    SharedPreferencesUtil.saveBooleanData(context, "ISCHUCHIDNEGLU", false); // 标志改未登录
                                    SharedPreferencesUtil.saveBooleanData(context, Pref.ISKAIDIAN_JUMP_LOGIN, false);
                                    SharedPreferencesUtil.saveBooleanData(context, "isrelogin", true);
                                    SharedPreferencesUtil.saveStringData(context, YConstance.Pref.YIDOU_HALVE_END_TIMES, "0");

                                    if (LoginActivity.instances != null) {
                                        LoginActivity.instances.finish();
                                    }
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    intent.putExtra("login_register", "login");

                                    ((FragmentActivity) getActivity()).startActivityForResult(intent, 239);
                                    getActivity().finish();
                                }
                            }
                            super.onPostExecute(context, result, e);
                        }

                    }.execute((Void[]) null);
                } else {
                    getActivity().finish();
                    if ("account".equals(exe)) {
                        Intent intent = new Intent(getActivity(), AccountSecureActivity.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.activity_from_right_publish,
                                R.anim.activity_from_right_publish_close);
                    }
                }

                }

                break;

            case R.id.btn_next_step:
                bindPhone();
                // mFragment = new SecureIdentifyFragment();
                // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,
                // mFragment).commit();

                break;


            case R.id.iv_gif: //gif图片点击
                String code = et_phone_num.getText().toString().trim();

                if (code.length() != 11) {
                    CenterToast.centerToast(context, "请输入正确的手机号码");
                } else {
                    PublicUtil.setVCode(context, ivGif, code);
                }

                break;


            case R.id.button_shihui:
                price = 100;
                button_shihui.setChecked(true);
                button_xiaozi.setChecked(false);
                button_qinshe.setChecked(false);
                break;
            case R.id.button_xiaozi:
                price = 100;
                button_xiaozi.setChecked(true);
                button_shihui.setChecked(false);
                button_qinshe.setChecked(false);
                break;
            case R.id.button_qinshe:
                price = 100;
                button_qinshe.setChecked(true);
                button_xiaozi.setChecked(false);
                button_shihui.setChecked(false);
                break;

            case R.id.button_han:
                love = 100;
                button_han.setChecked(true);
                button_ri.setChecked(false);
                button_ou.setChecked(false);
                break;
            case R.id.button_ri:
                love = 100;
                button_ri.setChecked(true);
                button_han.setChecked(false);
                button_ou.setChecked(false);
                break;
            case R.id.button_ou:
                love = 100;
                button_ou.setChecked(true);
                button_han.setChecked(false);
                button_ri.setChecked(false);
                break;

            case R.id.button_age1:
                age = 1;
                button_age1.setChecked(true);
                button_age2.setChecked(false);
                button_age3.setChecked(false);
                button_age4.setChecked(false);
                break;
            case R.id.button_age2:
                age = 2;
                button_age1.setChecked(false);
                button_age2.setChecked(true);
                button_age3.setChecked(false);
                button_age4.setChecked(false);
                break;

            case R.id.button_age3:
                age = 3;
                button_age1.setChecked(false);
                button_age2.setChecked(false);
                button_age3.setChecked(true);
                button_age4.setChecked(false);
                break;

            case R.id.button_age4:
                age = 4;
                button_age1.setChecked(false);
                button_age2.setChecked(false);
                button_age3.setChecked(false);
                button_age4.setChecked(true);
                break;

            case R.id.button_skirt1:
                skirt = 1;
                button_skirt1.setChecked(true);
                button_skirt2.setChecked(false);
                button_skirt3.setChecked(false);
                button_skirt4.setChecked(false);
                break;
            case R.id.button_skirt2:
                skirt = 2;
                button_skirt1.setChecked(false);
                button_skirt2.setChecked(true);
                button_skirt3.setChecked(false);
                button_skirt4.setChecked(false);
                break;

            case R.id.button_skirt3:
                skirt = 3;
                button_skirt1.setChecked(false);
                button_skirt2.setChecked(false);
                button_skirt3.setChecked(true);
                button_skirt4.setChecked(false);
                break;

            case R.id.button_skirt4:
                skirt = 4;
                button_skirt1.setChecked(false);
                button_skirt2.setChecked(false);
                button_skirt3.setChecked(false);
                button_skirt4.setChecked(true);
                break;

            case R.id.button_money1:
                money = 1;
                button_money1.setChecked(true);
                button_money2.setChecked(false);
                button_money3.setChecked(false);
                button_money4.setChecked(false);
                break;
            case R.id.button_money2:
                money = 2;
                button_money1.setChecked(false);
                button_money2.setChecked(true);
                button_money3.setChecked(false);
                button_money4.setChecked(false);
                break;

            case R.id.button_money3:
                money = 3;
                button_money1.setChecked(false);
                button_money2.setChecked(false);
                button_money3.setChecked(true);
                button_money4.setChecked(false);
                break;

            case R.id.button_money4:
                money = 4;
                button_money1.setChecked(false);
                button_money2.setChecked(false);
                button_money3.setChecked(false);
                button_money4.setChecked(true);
                break;

        }

    }

    private int checkID = 100;


    public static final String path = "https://yssj668.b0.upaiyun.com/down/YJApp.apk";

    /**
     * 渠道B包 用户不满足条件 强制下载普通包
     *
     * @date 2017年4月28日上午10:52:05
     */
    private void checkVersion2() {

        ApkDownloadManager UpgradeApk = new ApkDownloadManager((FragmentActivity) activity);
        UpgradeApk.downloadUpgradeApk(path);

        String imei = CheckStrUtil.getImei(activity);
        if (imei != null && ComModel2.flag == 0) {
            new Thread() {
                public void run() {

                    try {
                        Thread.sleep(5000);// 5秒
                    } catch (InterruptedException e) {
                        // block
                        e.printStackTrace();
                    }
                    // mContext.sendBroadcast(new
                    // Intent(TaskReceiver.newMemberTask_1));
                    handlerCheckVersion.sendEmptyMessage(0);
                }

                ;
            }.start();

        } else if (YJApplication.instance.isLoginSucess() == false) {
            new Thread() {
                public void run() {

                    try {
                        Thread.sleep(5000);// 5秒
                    } catch (InterruptedException e) {
                        // block
                        e.printStackTrace();
                    }
                    // mContext.sendBroadcast(new
                    // Intent(TaskReceiver.newMemberTask_1));
                    handlerCheckVersion.sendEmptyMessage(1);
                }

                ;
            }.start();
        } else {
            UserInfo userInfo = YCache.getCacheUserSafe(activity);
            if (null == userInfo) {
                return;
            }
            if (null == userInfo.getHobby() || userInfo.getHobby().equals("0")) {

                new Thread() {
                    public void run() {

                        try {
                            Thread.sleep(5000);// 5秒
                        } catch (InterruptedException e) {
                            // catch block
                            e.printStackTrace();
                        }
                        // mContext.sendBroadcast(new
                        // Intent(TaskReceiver.newMemberTask_1));
                        handlerCheckVersion.sendEmptyMessage(3);
                    }

                    ;
                }.start();

            } else {
                new Thread() {
                    public void run() {

                        try {
                            Thread.sleep(5000);// 30秒
                        } catch (InterruptedException e) {
                            // catch block
                            e.printStackTrace();
                        }
                        // mContext.sendBroadcast(new
                        // Intent(TaskReceiver.newMemberTask_1));
                        handlerCheckVersion.sendEmptyMessage(2);
                    }

                    ;
                }.start();
            }
        }

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);


    }

    private int isPause = 0;
    private Boolean isShow = false;
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
                    if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == activity
                            .getSharedPreferences("shareApp", Context.MODE_PRIVATE).getInt("day", 0)) {
                        return;
                    }
                    NewPDialog dialog = new NewPDialog(activity, R.layout.task_dialog1);
                    dialog.setF(new NewPDialog.FinishLintener() {

                        @Override
                        public void onFinishClickLintener() {
                            isShow = false;
                            activity.getSharedPreferences("shareApp", Context.MODE_PRIVATE).edit()
                                    .putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
                        }
                    });
                    dialog.setL(new NewPDialog.TaskLintener() {

                        @Override
                        public void onOKClickLintener() {
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
                        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == activity
                                .getSharedPreferences("EverydayTaskMondayFriday", Context.MODE_PRIVATE).getInt("day", 0)) {
                            return;
                        }

                        UserInfo userInfo;
                        userInfo = YCache.getCacheUserSafe(activity);
                        if (null == userInfo) {
                            return;
                        }
                        if (null == userInfo.getHobby() || userInfo.getHobby().equals("0")) {
                            return;
                        }

                        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                        if (day == 3) {
                        } else if (day == 2) {
                        }
                    }
                }
                case 2: {

                }
                break;
                case 3: {
                    if (isShow) {
                        return;
                    }
//				isNewShop = 2;
                    if (!YJApplication.instance.isLoginSucess()) {
                        return;
                    }
                    if (activity.getSharedPreferences("dian", Context.MODE_PRIVATE).getInt("dian", 0) == Calendar
                            .getInstance().get(Calendar.DAY_OF_MONTH)) {
                        return;
                    }
                    UserInfo userInfo = YCache.getCacheUserSafe(activity);
                    if (null == userInfo) {
                        return;
                    }
                }
                break;
                case 6: {

                    if (activity.getSharedPreferences("tocao_isupdate", Context.MODE_PRIVATE).getBoolean("tocao_isupdate",
                            false)) {
                        return;
                    }
                    if (activity.getSharedPreferences("tocao_isshow", Context.MODE_PRIVATE).getBoolean("tocao_isshow",
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

                            intent.setClass(activity, FeedBackActivity.class);

                            activity.startActivity(intent);

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
                    activity.getSharedPreferences("tocao_isshow", Context.MODE_PRIVATE).edit()
                            .putBoolean("tocao_isshow", true).commit();
                    isShow = true;

                }
                break;
                case 4: {
                    if (isShow) {
                        return;
                    }
                }
                break;
                default:
                    break;
            }

        }

        ;
    };

}
