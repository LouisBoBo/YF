package com.yssj.ui.fragment.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import com.yssj.YJApplication;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.setting.SettingCommonFragmentActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

public class FirstBindPhoneIdentityCodeFragment extends BaseFragment implements OnClickListener {

    private TextView tvTitle_base;
    private LinearLayout img_back;

    private EditText et_identify_code;
    private Button btn_next_step, btn_get_identify_code;
    private Context mContext;
    private EditText et_pwd;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.activity_setting_first_bind_phone_code, null);
        mContext = context;
        view.setBackgroundColor(Color.WHITE);
        tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("绑定手机");
        img_back = (LinearLayout) view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        et_identify_code = (EditText) view.findViewById(R.id.et_identify_code);

        btn_next_step = (Button) view.findViewById(R.id.btn_next_step);
        btn_next_step.setOnClickListener(this);


        et_pwd  = (EditText) view.findViewById(R.id.et_pwd);

        btn_get_identify_code = (Button) view.findViewById(R.id.btn_get_identify_code);
        btn_get_identify_code.setOnClickListener(this);

        return view;
    }

    @Override
    public void initData() {

        age = getArguments().getString("age");
        isChannl = getArguments().getString("isChannl");

        //进来直接开始倒计时 ---时间结束跳回上一步
        new CountDownTimer(120000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btn_get_identify_code.setText(String.valueOf(millisUntilFinished / 1000) + "秒");
                btn_get_identify_code.setEnabled(false);
            }

            @Override
            public void onFinish() {

                btn_get_identify_code.setText("重新发送");
                btn_get_identify_code.setEnabled(true);


                btn_get_identify_code.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //跳至渠道的绑定手机界面
                        FirstBindPhoneFragmentChanal mFragment = new FirstBindPhoneFragmentChanal();
                        Bundle bundle = new Bundle();
                        bundle.putString("thirdparty", "thirdparty");
                        mFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
                    }
                });


            }
        }.start();


    }

    private String isChannl = "";
    private String age = "";

//    private void getCode() {
//
//        final String phone = getArguments().getString("num");
//        if (TextUtils.isEmpty(phone)) {
//            ToastUtil.showLongText(context, "手机号不能为空");
//        }
//        new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) context, R.string.wait) {
//
//            @Override
//            protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
//
//                if (isChannl.equals("isChannl")) {
//                    return ComModel.sendPhoneVerifyCodeFirst(context, phone, 7);
//                } else {
//                    return ComModel.sendPhoneVerifyCode(context, phone, 7);
//                }
//
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (null == e) {
//                    if (result != null && "1".equals(result.getStatus())) {
//                        new CountDownTimer(120000L, 1000) {
//
//                            @Override
//                            public void onTick(long millisUntilFinished) {
//                                btn_get_identify_code.setText(String.valueOf(millisUntilFinished / 1000) + "秒重发");
//                                btn_get_identify_code.setEnabled(false);
//                            }
//
//                            @Override
//                            public void onFinish() {
//
//
//                                //跳至渠道的绑定手机界面
//                                FirstBindPhoneFragmentChanal mFragment = new FirstBindPhoneFragmentChanal();
//                                Bundle bundle = new Bundle();
//                                bundle.putString("thirdparty", "thirdparty");
//                                mFragment.setArguments(bundle);
//                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
//
//
//                                try {
//                                    btn_get_identify_code.setText("重新发送");
//                                    btn_get_identify_code.setEnabled(true);
//
//                                } catch (Exception e1) {
//                                    e1.printStackTrace();
//                                }
//
//
//                            }
//                        }.start();
//
//                    } else {
//                        //跳至渠道的绑定手机界面
//                        FirstBindPhoneFragmentChanal mFragment = new FirstBindPhoneFragmentChanal();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("thirdparty", "thirdparty");
//                        mFragment.setArguments(bundle);
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
//
//
//                        try {
//                            btn_get_identify_code.setText("重新发送");
//                            btn_get_identify_code.setEnabled(true);
//
//                        } catch (Exception e1) {
//                            e1.printStackTrace();
//                        }
//
//                    }
//                    ToastUtil.showShortText(context, result.getMessage());
//                }
//            }
//
//        }.execute();
//    }

    private void checkCode(final String age, final String isChannl) {
        final String code = et_identify_code.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showShortText(context, "请输入验证码");
            return;
        }


        final String pwd = et_pwd.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showShortText(context, "请输入登录密码");
            return;
        }

        if(pwd.length() < 6 ){
            ToastUtil.showShortText(context, "登录密码不能少于6位");
            return;

        }

        new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) context, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {

                return ComModel.checkCodePwd(context, code, age, isChannl,pwd);
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
                        InputMethodManager inputMethodManager = (InputMethodManager) context
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);



//                        Fragment mFragment = new SuccessBindPhoneActivity();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("buy0", "buy0");
//                        mFragment.setArguments(bundle);
//                        context.getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment)
//                                .commit();
//

                        Intent in = new Intent(getActivity(),SuccessBindPhoneActivity.class);

                        in.putExtra("buy0","buy0");
                        getActivity().startActivity(in);
                        ((Activity) getActivity()).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                        if ("thirdparty".equals(SettingCommonFragmentActivity.mThirdParty)) {
                            getHuanXinPassword(YCache.getCacheUser(context));
                        }
                    } else {
                        ToastUtil.showLongText(context, result.getMessage());
                    }
                }
            }

        }.execute();
    }

    @Override
    public void onClick(View v) {
        Fragment mFragment;
        switch (v.getId()) {
            case R.id.img_back:

                YJApplication.instance.setLoginSucess(false);
                YJApplication.isLogined = false;

                if (isChannl.equals("isChannl")) {

                    YCache.cleanToken(getActivity());
                    YCache.cleanUserInfo(getActivity());
                    ComModel.clearLoginFlag(getActivity());

                    if (LoginActivity.instances != null) {
                        LoginActivity.instances.finish();
                    }

                    Intent intentd = new Intent(context, LoginActivity.class);
                    intentd.putExtra("login_register", "login");
                    ((FragmentActivity) context).startActivity(intentd);

                    getActivity().finish();

                } else {
                    mFragment = new FirstBindPhoneFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment)
                            .commit();
                }

                break;

            case R.id.btn_next_step:
                checkCode(age, isChannl);
                break;
            case R.id.btn_get_identify_code: // 获取验证码
//			getCode();
                break;

        }
    }

    /**
     * 获取环信password
     */
    private void getHuanXinPassword(final UserInfo user) {
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
                    // PublicUtil.registerHuanxin((Activity) mContext, user,
                    // ""+code);
                    SharedPreferencesUtil.saveStringData(context, Pref.pd, code);
                }
            }

        }.execute();
    }
}
