package com.yssj.ui.fragment.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.QueryPhoneInfo;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.huanxin.PublicUtil;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.infos.SetMyPayPassActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.setting.AccountSecureActivity;
import com.yssj.ui.activity.setting.SettingCommonFragmentActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.CenterToast;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import java.util.HashMap;

import butterknife.ButterKnife;

public class FirstBindPhoneFragment extends BaseFragment implements OnClickListener {

    private ImageView ivGif;  //gif验证码
    private TextView tvTitle_base;
    private LinearLayout img_back;

    private EditText et_phone_num;

    //立即绑定
    private Button btn_next_step;


    //下一步
    private Button btn_next_step_next;

    //手机号区域
    private RelativeLayout rel_phone_num;


    private String exe;
    public static String mThirdParty = "";
    private ImageButton img_clean;
    private TextView btn_get_identify_code;
    private EditText et_identify_code;
    private ImageButton clean_yan;

    private ImageView pwd_clean;
    private RelativeLayout et_pwd_rl;
    private EditText et_pwd;
    private boolean sanFangFirstBind;

    //图片验证码部分
    private LinearLayout ll_get_piccode;


    private ImageView et_auto_xx; //图片验证码--输入框的XX
    private EditText et_auto; //验证码输入的内容
    //    private CheckView ck_auto; //图片验证码--图片
    private String[] res = new String[4]; // 获取每次更新的验证码，可用于判断用户输入是否正确


    //手机验证码部分
    private RelativeLayout rel_phone_code;


    public static FirstBindPhoneFragment instans;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.activity_setting_first_bind_phone, null);


        ivGif = (ImageView) view.findViewById(R.id.iv_gif);


        ivGif.setOnClickListener(this);

        // view.setBackgroundColor(Color.WHITE);
        tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("绑定手机");
        img_back = (LinearLayout) view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        img_clean = (ImageButton) view.findViewById(R.id.img_clean);
        img_clean.setOnClickListener(this);


        clean_yan = (ImageButton) view.findViewById(R.id.clean_yan);
        clean_yan.setOnClickListener(this);

        pwd_clean = (ImageView) view.findViewById(R.id.pwd_clean);
        pwd_clean.setOnClickListener(this);

        btn_next_step = (Button) view.findViewById(R.id.btn_next_step);
        btn_next_step.setOnClickListener(this);


        btn_next_step_next = (Button) view.findViewById(R.id.btn_next_step_next);
        btn_next_step_next.setOnClickListener(this);


        exe = getActivity().getIntent().getStringExtra("wallet");

        btn_get_identify_code = (TextView) view.findViewById(R.id.btn_get_identify_code);
        btn_get_identify_code.setOnClickListener(this);

        et_identify_code = (EditText) view.findViewById(R.id.et_identify_code);


        rel_phone_num = (RelativeLayout) view.findViewById(R.id.rel_phone_num);


        //图片验证码
        ll_get_piccode = (LinearLayout) view.findViewById(R.id.ll_get_piccode);

        et_auto_xx = (ImageView) view.findViewById(R.id.et_auto_xx);
        et_auto = (EditText) view.findViewById(R.id.et_auto);
//        ck_auto = (com.yssj.custom.view.CheckView) view.findViewById(R.id.ck_auto);
        et_auto_xx.setOnClickListener(this);
//        ck_auto.setOnClickListener(this);

        //手机验证码部分
        rel_phone_code = (RelativeLayout) view.findViewById(R.id.rel_phone_code);


        et_phone_num = (EditText) view.findViewById(R.id.et_phone_num);


        ivGif.setVisibility(View.GONE);

        sanFangFirstBind = getActivity().getIntent().getBooleanExtra("sanFangFirstBind", false);
        et_pwd_rl = (RelativeLayout) view.findViewById(R.id.et_pwd_rl);
        et_pwd = (EditText) view.findViewById(R.id.et_pwd);


        //手机号输入框的监听
        et_phone_num.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                String code = et_phone_num.getText().toString().trim();
                if (code.length() > 0) {
                    img_clean.setVisibility(View.VISIBLE);
                    btn_next_step_next.setBackgroundResource(R.drawable.btn_back_red);


                    if (code.length() == 11) {
                        PublicUtil.setVCode(context, ivGif, code);
                        ivGif.setVisibility(View.VISIBLE);
                    }


                } else {
                    img_clean.setVisibility(View.GONE);
                    btn_next_step_next.setBackgroundResource(R.drawable.btn_back);
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


//手机验证码输入框的监听
        et_identify_code.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String et_identify_code_lenth = et_identify_code.getText().toString();
                if (et_identify_code_lenth.length() > 0) {

                    btn_next_step.setBackgroundResource(R.drawable.btn_back_red);


                    clean_yan.setVisibility(View.VISIBLE);
                } else {

                    btn_next_step.setBackgroundResource(R.drawable.btn_back);

                    clean_yan.setVisibility(View.GONE);
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


        // 初始化验证码
//        res = ck_auto.getValidataAndSetImage();

        //图形验证码输入框的监听
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

        et_pwd.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                String pwd = et_pwd.getText().toString().trim();
                if (pwd.length() > 0) {
                    pwd_clean.setVisibility(View.VISIBLE);

                } else {
                    pwd_clean.setVisibility(View.GONE);
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

        return view;
    }

    @Override
    public void initData() {


        ll_get_piccode.setVisibility(View.VISIBLE);
        rel_phone_code.setVisibility(View.GONE);

        btn_next_step_next.setVisibility(View.VISIBLE);
        btn_next_step.setVisibility(View.GONE);

        rel_phone_num.setVisibility(View.VISIBLE);
        et_pwd_rl.setVisibility(View.GONE);


//        PublicUtil.setVCode(context, ivGif, "13621191209");


    }

    private void bindPhone() {
        final String pwd = et_pwd.getText().toString().trim();
        final String num = et_phone_num.getText().toString();
        if (TextUtils.isEmpty(num)) {
            CenterToast.centerToast(context, "输入您要绑定的手机号");
            return;
        }
        if (sanFangFirstBind) {
            if (TextUtils.isEmpty(pwd)) {
                CenterToast.centerToast(context, "请设置登录密码");
                return;
            } else if (pwd.length() < 6) {
                CenterToast.centerToast(context, "登录密码不能小于六位");
                return;
            }
        }

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
                        // 往下一步跳转
                        // Fragment mFragment = new
                        // FirstBindPhoneIdentityCodeFragment();
                        // Bundle bundle = new Bundle();
                        // bundle.putString("num", num);
                        // mFragment.setArguments(bundle);
                        // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,
                        // mFragment)
                        // .commit();
                        checkCode();

                    } else if (result != null && "1".equals(result.getStatus()) && result.isBool() == true) {
                        CenterToast.centerToast(context, "该手机号已绑定其他账号，请更换其他号码");

                    } else if (result != null && "50".equals(result.getStatus())) {
                        CenterToast.centerToast(context, "支付密码未初始化，请先设置支付密码才能绑定手机");
                        Intent intent = new Intent(getActivity(), SetMyPayPassActivity.class);
                        startActivity(intent);
                    } else {
                        // ToastUtil.showLongText(context, result.getMessage());
                        CenterToast.centerToast(context, result.getMessage());
                    }
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

            case R.id.iv_gif: //gif图片点击
                String code = et_phone_num.getText().toString().trim();

                if (code.length() != 11) {
                    CenterToast.centerToast(context, "请输入正确的手机号码");
                } else {
                    PublicUtil.setVCode(context, ivGif, code);
                }

                break;


//            case R.id.ck_auto:
            // 重新初始化验证码
//                res = ck_auto.getValidataAndSetImage();
//                et_auto.setText("");
//                break;


            case R.id.img_back:


                if (getActivity().getIntent().getBooleanExtra("tishiBind", false)) {
                    getActivity().finish();
                    getActivity().overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);


                    return;
                }

                // Bundle bundle = getArguments();
                // if (bundle != null) {
                // mThirdParty = bundle.getString("thirdparty");
                // }
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
//								MainFragment fragment = MainMenuActivity.instances
//										.getFragment();
//								if (fragment.getChildFragmentManager()
//										.findFragmentByTag("1") != null) {
//									fragment.getChildFragmentManager()
//											.beginTransaction()
//											.remove(fragment.getChildFragmentManager()
//													.findFragmentByTag("1")).commit();
//								}


                                    SharedPreferencesUtil.saveBooleanData(getActivity(), "isLoginLogin", false);
                                    //APP启动次数清零
                                    SharedPreferencesUtil.saveStringData(getActivity(), "addNUM", "1");
                                    SharedPreferencesUtil.saveStringData(getActivity(), "AppstartNUM", 0 + "");

                                    SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, false);
                                    SharedPreferencesUtil.saveStringData(context, Pref.TWOFOLDNESS, "-1");
                                    SharedPreferencesUtil.saveStringData(context, Pref.END_DATE, "-1");
                                    SharedPreferencesUtil.saveStringData(context, Pref.IS_OPEN, "-1");
                                    SharedPreferencesUtil.saveBooleanData(context, "ISCHUCHIDNEGLU", false);  //标志改未登录
                                    SharedPreferencesUtil.saveBooleanData(context, Pref.ISKAIDIAN_JUMP_LOGIN, false);
                                    SharedPreferencesUtil.saveBooleanData(context, "isrelogin", true);
                                    SharedPreferencesUtil.saveStringData(context, Pref.YIDOU_HALVE_END_TIMES, "0");


                                    if (LoginActivity.instances != null) {
                                        LoginActivity.instances.finish();
                                    }
                                    Intent intent = new Intent(getActivity(),
                                            LoginActivity.class);
                                    intent.putExtra("login_register", "login");

                                    ((FragmentActivity) getActivity()).startActivityForResult(
                                            intent, 239);
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
                break;

            case R.id.btn_next_step://绑定手机


                bindPhone();
                // mFragment = new SecureIdentifyFragment();
                // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,
                // mFragment).commit();

                break;

            case R.id.btn_next_step_next://下一步


                //验证图形验证码并发送手机验证码


                final String num = et_phone_num.getText().toString();
                if (TextUtils.isEmpty(num)) {
                    CenterToast.centerToast(context, "输入您要绑定的手机号");
                    return;
                }


                String auto = et_auto.getText().toString().trim();
                if (auto.length() == 0) {
                    ToastUtil.showShortText(getActivity(), "请输入验证码");
                    return;
                }


//                String code = "";
//                for (int i = 0; i < res.length; i++) {
//                    code += res[i];
//                }
//                LogYiFu.e("inputCode", "" + code);
//                if (!auto.toLowerCase().equals(code.toLowerCase())) {
//                    ToastUtil.showShortText(getActivity(), "请输入正确的验证码");
//                    // 初始化验证码
//                    res = ck_auto.getValidataAndSetImage();
//                    return;
//                }


                getCode();


//                ll_get_piccode.setVisibility(View.GONE);
//                rel_phone_code.setVisibility(View.VISIBLE);
//                btn_next_step_next.setVisibility(View.GONE);
//                btn_next_step.setVisibility(View.VISIBLE);
//                rel_phone_num.setVisibility(View.GONE);


                break;
            case R.id.img_clean:
                et_phone_num.getText().clear();
                break;
            case R.id.clean_yan:
                et_identify_code.getText().clear();
                break;

            case R.id.btn_get_identify_code:

                getCode();
                break;
            case R.id.pwd_clean:
                //清除密码
                et_pwd.getText().clear();
                break;
            default:
                break;
        }
    }

    private void getCode() {

        // final String phone = getArguments().getString("num");5
        final String phone = et_phone_num.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            CenterToast.centerToast(context, "手机号不能为空");
            return;
        }
        new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) context, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {

                return ComModel.sendPhoneVerifyCodeFirstVcode(context, phone, 7, et_auto.getText().toString().trim());
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(final FragmentActivity context, ReturnInfo result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result != null && "1".equals(result.getStatus())) {

                        btn_next_step_next.setVisibility(View.GONE);
                        rel_phone_num.setVisibility(View.GONE);
                        ll_get_piccode.setVisibility(View.GONE);
                        ivGif.setVisibility(View.GONE);

                        rel_phone_code.setVisibility(View.VISIBLE);
                        btn_next_step.setVisibility(View.VISIBLE);
                        if (sanFangFirstBind) {
                            et_pwd_rl.setVisibility(View.VISIBLE);
                        } else {
                            et_pwd_rl.setVisibility(View.GONE);
                        }


                        new CountDownTimer(Long.parseLong(getResources().getString(R.string.identify_code)), 1000) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                btn_get_identify_code.setTextColor(Color.parseColor("#c7c7c7"));
                                btn_get_identify_code.setText(String.valueOf(millisUntilFinished / 1000) + "秒");
                                btn_get_identify_code.setEnabled(false);
                            }

                            @Override
                            public void onFinish() {


                                btn_get_identify_code.setTextColor(Color.parseColor("#FF3F8B"));
                                btn_get_identify_code.setText("重新获取");
                                btn_get_identify_code.setEnabled(true);
                                btn_get_identify_code.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //重新显示图片验证码
                                        btn_next_step_next.setVisibility(View.VISIBLE);
                                        rel_phone_num.setVisibility(View.VISIBLE);
                                        ll_get_piccode.setVisibility(View.VISIBLE);
                                        ivGif.setVisibility(View.VISIBLE);


                                        et_pwd_rl.setVisibility(View.GONE);
                                        rel_phone_code.setVisibility(View.GONE);
                                        btn_next_step.setVisibility(View.GONE);

                                    }
                                });


                                String code = et_phone_num.getText().toString().trim();
                                if (code.length() == 11) {
                                    et_auto.getText().clear();
                                    PublicUtil.setVCode(context, ivGif, code);
                                }


                            }
                        }.start();

                        ToastUtil.showShortText(context, "手机验证码获取成功！");

                    } else {
                        ToastUtil.showShortText(context, result.getMessage());

                        btn_get_identify_code.setTextColor(Color.parseColor("#FF3F8B"));
                        btn_get_identify_code.setText("重新获取");
                        btn_get_identify_code.setEnabled(true);
                    }
                    // ToastUtil.showShortText(context, result.getMessage());
//                    CenterToast.centerToast(context, result.getMessage());
                }
            }

        }.execute();
    }

    private void checkCode() {
        final String code = et_identify_code.getText().toString();
        final String pwd = et_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            // ToastUtil.showShortText(context, "请输入验证码");
            CenterToast.centerToast(context, "请输入验证码");
            return;
        }
        if (sanFangFirstBind) {
            if (TextUtils.isEmpty(pwd)) {
                CenterToast.centerToast(context, "请设置登录密码");
                return;
            } else if (pwd.length() < 6) {
                CenterToast.centerToast(context, "登录密码不能小于六位");
                return;
            }
        }

        new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) context, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                if (sanFangFirstBind) {
                    return ComModel.checkCodePwd(context, code, "", "", pwd);
                } else {
                    return ComModel.checkCode(context, code, "", "");
                }

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
                        /*
                         * // 往下一步跳转 Fragment mFragment = new
						 * SecureIdentifyFragment(); Bundle bundle = new
						 * Bundle(); bundle.putString("firstBind",
						 * "firstBindPhone"); mFragment.setArguments(bundle);
						 * getActivity().getSupportFragmentManager().
						 * beginTransaction().replace(R.id.fl_content,
						 * mFragment).commit();
						 */

                        SharedPreferencesUtil.saveBooleanData(context, "signDATAneedRefresh", true);

                        InputMethodManager inputMethodManager =
                                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                        Fragment mFragment = new SuccessBindPhoneActivity();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("buy0", "buy0");
//                        mFragment.setArguments(bundle);
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment)
//                                .commit();


                        Intent in = new Intent(getActivity(), SuccessBindPhoneActivity.class);

                        in.putExtra("buy0", "buy0");
                        getActivity().startActivity(in);
                        ((Activity) getActivity()).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                        if ("thirdparty".equals(SettingCommonFragmentActivity.mThirdParty)) {
                            SharedPreferencesUtil.saveBooleanData(context, "bindPhoneNumber", true);
                            getHuanXinPassword(YCache.getCacheUser(context), getActivity());
                        }
                    } else {
                        // ToastUtil.showLongText(context, result.getMessage());
                        CenterToast.centerToast(context, result.getMessage());
                    }
                }
            }

        }.execute();
    }

    private void getHuanXinPassword(final UserInfo user, final Activity activity) {
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) getActivity(), R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getHuanXinPassword(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);

                if (null == e && result != null) {
                    String code = (String) result.get("code");
                    LogYiFu.e("jjj", "222" + code);
                    PublicUtil.registerHuanxin(activity, user,  false);
                    SharedPreferencesUtil.saveStringData(context, Pref.pd, code);

                }
            }

        }.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}