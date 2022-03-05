package com.yssj.ui.activity.logins;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.utils.DeviceConfig;
import com.yssj.Constants;
import com.yssj.SmsContent;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.CheckView;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.huanxin.PublicUtil;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.setting.UserProtocolActivity;
import com.yssj.utils.CenterToast;
import com.yssj.utils.DeviceUtils;
import com.yssj.utils.GetShopCart;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.TimeCount;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXcheckUtil;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static com.yssj.Json.JUserInfo.nickname;

/****
 * 注册
 *
 * @author Administrator
 *
 */
public class RegisterFragment extends Fragment implements OnClickListener, OnCheckedChangeListener {

    private static final String TITLE = "register";

    private LinearLayout lay_phoned, lay_emaild, ll_user_protocol;

    private TextView tv_chreg, tvTitle;

    private TextView tv_gquto;

    private TextView tv_acode;

    private TextView tv_regfinish;
    private ToggleButton tv_sbt1;
    private ToggleButton tv_sbt;
    private EditText et_ipwd, et_phoned, et_pauto; //et_nickname; // 手机
    private ImageView et_ipwd_xx, et_phoned_xx, et_pauto_xx, et_nickname_xx;
    private TimeCount timeCount;

    private EditText et_ipwd1, et_auto, et_eml,et_auto2; //et_nickname1;// 邮箱
    private ImageView et_ipwd1_xx, et_auto_xx, et_eml_xx, et_nickname1_xx,et_auto_xx2;
    private CheckView ck_auto;
    private ImageView ck_auto2;
    private CheckBox ckb_agreen;

    private String[] res = new String[4]; // 获取每次更新的验证码，可用于判断用户输入是否正确
//    private String[] res2 = new String[4]; // 获取每次更新的验证码，可用于判断用户输入是否正确
    private int intChoose = 0;
    private boolean emailFlag = false;
    private YJApplication appctx;
    public static LoginActivity instance;

    private boolean isAuthChecked = true;

    private CountDownTimer timeer;

    private String deviceToken;

    public interface FragmentBackListener {

        void onbackForward();
    }

    public static RegisterFragment newInstance(String title) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = (LoginActivity) getActivity();
        appctx = (YJApplication) getActivity().getApplicationContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        initViews(v);
        SmsContent content = new SmsContent(getActivity(), new Handler(), et_pauto);
        // 注册短信变化监听
        getActivity().getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, content);
        timeer = new CountDownTimer(Long.parseLong(getResources().getString(R.string.identify_code)), 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tv_acode.setText(String.valueOf(millisUntilFinished / 1000) + "秒");
                tv_acode.setEnabled(false);
            }

            @Override
            public void onFinish() {
                tv_acode.setText("重新发送");
                tv_acode.setEnabled(true);
            }
        };

        // 初始化验证码
        res = ck_auto.getValidataAndSetImage();
//        res2 = ck_auto2.getValidataAndSetImage();
        addTextChanged();

        return v;
    }

    public void setThirdClickListener(FragmentActivity activity) {
        this.onThirdClickListener = (OnThirdClickListener) activity;
    }

    private OnThirdClickListener onThirdClickListener;

    public interface OnThirdClickListener {
        void qqClick(View v);

        void wxClick(View v);

        void sinaClick(View v);
    }

    private void initViews(View v) {
        tv_chreg = (TextView) instance.findViewById(R.id.tv_chreg);
        tvTitle = (TextView) instance.findViewById(R.id.tvTitle_base);
        // PublicUtil2.openId(SHARE_MEDIA.WEIXIN, 0, getActivity());//(判断微信性别)
        ll_user_protocol = (LinearLayout) v.findViewById(R.id.ll_user_protocol);
        ll_user_protocol.setOnClickListener(this);
        v.findViewById(R.id.registerr).setBackgroundColor(Color.WHITE);
        lay_phoned = (LinearLayout) v.findViewById(R.id.lay_phoned);
        lay_emaild = (LinearLayout) v.findViewById(R.id.lay_emaild);
        tv_sbt = (ToggleButton) v.findViewById(R.id.tv_sbt);
        tv_sbt.setOnCheckedChangeListener(this);
        tv_sbt1 = (ToggleButton) v.findViewById(R.id.tv_sbt1);
        tv_sbt1.setOnCheckedChangeListener(this);
        et_ipwd = (EditText) v.findViewById(R.id.et_ipwd);
        et_ipwd1 = (EditText) v.findViewById(R.id.et_ipwd1);
        tv_gquto = (TextView) v.findViewById(R.id.tv_gquto);
        // tv_chreg = (TextView) v.findViewById(R.id.tv_chreg);
        tv_chreg.setOnClickListener(this);
        tv_acode = (TextView) v.findViewById(R.id.tv_acode);
        tv_acode.setOnClickListener(this);// 获取验证码
        et_auto = (EditText) v.findViewById(R.id.et_auto);
        et_auto2 = (EditText) v.findViewById(R.id.et_auto2);
        ck_auto = (CheckView) v.findViewById(R.id.ck_auto);
        ck_auto.setOnClickListener(this);
        ck_auto2 = (ImageView) v.findViewById(R.id.ck_auto2);
        ck_auto2.setOnClickListener(this);
        tv_regfinish = (TextView) v.findViewById(R.id.tv_regfinish);
        tv_regfinish.setEnabled(true);
        tv_regfinish.setOnClickListener(this);

        et_phoned = (EditText) v.findViewById(R.id.et_phoned);
        et_pauto = (EditText) v.findViewById(R.id.et_pwd);
//        et_nickname = (EditText) v.findViewById(R.id.et_nickname);

        et_eml = (EditText) v.findViewById(R.id.et_eml);
        et_ipwd1 = (EditText) v.findViewById(R.id.et_ipwd1);
//        et_nickname1 = (EditText) v.findViewById(R.id.et_nickname1);
        ckb_agreen = (CheckBox) v.findViewById(R.id.ckb_agreen);
        ckb_agreen.setOnCheckedChangeListener(this);

        et_ipwd_xx = (ImageView) v.findViewById(R.id.et_ipwd_xx);
        et_phoned_xx = (ImageView) v.findViewById(R.id.et_phoned_xx);
        et_pauto_xx = (ImageView) v.findViewById(R.id.et_pwd_xx);
        et_nickname_xx = (ImageView) v.findViewById(R.id.et_nickname_xx);

        et_ipwd1_xx = (ImageView) v.findViewById(R.id.et_ipwd1_xx);
        et_auto_xx = (ImageView) v.findViewById(R.id.et_auto_xx);
        et_auto_xx2 = (ImageView) v.findViewById(R.id.et_auto_xx2);
        et_eml_xx = (ImageView) v.findViewById(R.id.et_eml_xx);
        et_nickname1_xx = (ImageView) v.findViewById(R.id.et_nickname1_xx);
        et_ipwd_xx.setOnClickListener(this);
        et_phoned_xx.setOnClickListener(this);
        et_pauto_xx.setOnClickListener(this);
        et_nickname_xx.setOnClickListener(this);
        et_ipwd1_xx.setOnClickListener(this);
        et_auto_xx.setOnClickListener(this);
        et_auto_xx2.setOnClickListener(this);
        et_eml_xx.setOnClickListener(this);
        et_nickname1_xx.setOnClickListener(this);

        v.findViewById(R.id.img_wx).setOnClickListener(this);
        v.findViewById(R.id.img_qq).setOnClickListener(this);
        v.findViewById(R.id.img_wb).setOnClickListener(this);


        try {
            if (!DeviceConfig.isAppInstalled("com.tencent.mobileqq", getActivity())) {
                v.findViewById(R.id.img_qq_ll).setVisibility(View.GONE);
                hasQQ = false;
            }
        } catch (Exception e) {
        }


        try {
            if (!WXcheckUtil.isWeChatAppInstalled(getActivity())) {
                v.findViewById(R.id.img_wx_ll).setVisibility(View.GONE);
                v.findViewById(R.id.tv_third_ll).setVisibility(View.GONE);
                hasWX = false;
            }
        } catch (Exception e) {
        }


        iphonePWDXianzhi();
        EmailPWDXianzhi();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(et_phoned.getText().toString().trim().length()==11){
            PublicUtil.setVCode(getActivity(),ck_auto2,et_phoned.getText().toString().trim());
        }
        et_auto2.setText("");
    }

    private boolean hasQQ = true;
    private boolean hasWX = true;

    private void EmailPWDXianzhi() {

        et_ipwd1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

                String editable = et_ipwd1.getText().toString();
                String str = stringFilter(editable.toString());
                if (!editable.equals(str)) {
                    et_ipwd1.setText(str);
                    // 设置新的光标所在位置
                    ToastUtil.showShortText(getActivity(), "密码不能包含汉字~");
                    et_ipwd1.setSelection(str.length());
                }
                if (editable.length() > 0) {
                    et_ipwd1_xx.setVisibility(View.VISIBLE);
                } else {
                    et_ipwd1_xx.setVisibility(View.GONE);
                }
                if (et_ipwd1.getText().length() > 0
                        && et_auto.getText().length() > 0
                        && et_eml.getText().length() > 0
//                        && et_nickname1.getText().length() > 0

                        ) {
                    tv_regfinish.setBackgroundResource(R.drawable.btn_back_red);
                } else {
                    tv_regfinish.setBackgroundResource(R.drawable.btn_back);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
//        et_nickname1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                // TODO Auto-generated method stub
//                String editable = et_nickname1.getText().toString();
//                if (editable.length() > 0) {
//                    et_nickname1_xx.setVisibility(View.VISIBLE);
//                } else {
//                    et_nickname1_xx.setVisibility(View.GONE);
//                }
//                if (et_ipwd1.getText().length() > 0
//                        && et_auto.getText().length() > 0
//                        && et_eml.getText().length() > 0
//                        && et_nickname1.getText().length() > 0) {
//                    tv_regfinish.setBackgroundResource(R.drawable.btn_back_red);
//                } else {
//                    tv_regfinish.setBackgroundResource(R.drawable.btn_back);
//                }
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                // TODO Auto-generated method stub
//
//            }
//        });

    }

    private void iphonePWDXianzhi() {

        et_ipwd.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

                String editable = et_ipwd.getText().toString();
                String str = stringFilter(editable.toString());
                if (!editable.equals(str)) {
                    et_ipwd.setText(str);
                    // 设置新的光标所在位置
                    ToastUtil.showShortText(getActivity(), "密码不能包含汉字~");
                    et_ipwd.setSelection(str.length());
                }
                if (editable.length() > 0) {
                    et_ipwd_xx.setVisibility(View.VISIBLE);
                } else {
                    et_ipwd_xx.setVisibility(View.GONE);
                }
                if (et_ipwd.getText().toString().length() > 0
                        && et_phoned.getText().toString().length() > 0
                        && et_pauto.getText().toString().length() > 0
//                        && et_nickname.getText().toString().length() > 0


                        ) {
                    tv_regfinish.setBackgroundResource(R.drawable.btn_back_red);
                } else {
                    tv_regfinish.setBackgroundResource(R.drawable.btn_back);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
        et_pauto.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                String editable = et_pauto.getText().toString();
                if (editable.length() > 0) {
                    et_pauto_xx.setVisibility(View.VISIBLE);
                } else {
                    et_pauto_xx.setVisibility(View.GONE);
                }
                if (et_ipwd.getText().toString().length() > 0
                        && et_phoned.getText().toString().length() > 0
                        && et_pauto.getText().toString().length() > 0
//                        && et_nickname.getText().toString().length() > 0

                        ) {
                    tv_regfinish.setBackgroundResource(R.drawable.btn_back_red);
                } else {
                    tv_regfinish.setBackgroundResource(R.drawable.btn_back);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
//        et_nickname.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                // TODO Auto-generated method stub
//                String editable = et_nickname.getText().toString();
//                if (editable.length() > 0) {
//                    et_nickname_xx.setVisibility(View.VISIBLE);
//                } else {
//                    et_nickname_xx.setVisibility(View.GONE);
//                }
//                if (et_ipwd.getText().toString().length() > 0
//                        && et_phoned.getText().toString().length() > 0
//                        && et_pauto.getText().toString().length() > 0
//                        && et_nickname.getText().toString().length() > 0) {
//                    tv_regfinish.setBackgroundResource(R.drawable.btn_back_red);
//                } else {
//                    tv_regfinish.setBackgroundResource(R.drawable.btn_back);
//                }
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                // TODO Auto-generated method stub
//
//            }
//        });

    }

    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String regEx = "[\u4E00-\u9FA5]"; // "[\u4e00-\u9fa5]"
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    private void addTextChanged() {

        et_auto.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String et_auto_text = et_auto.getText().toString();
                if (et_auto_text.length() > 0) {
                    et_auto_xx.setVisibility(View.VISIBLE);
                } else {
                    et_auto_xx.setVisibility(View.GONE);
                }
                if (et_ipwd1.getText().length() > 0
                        && et_auto.getText().length() > 0
                        && et_eml.getText().length() > 0
//                        && et_nickname1.getText().length() > 0


                        ) {
                    tv_regfinish.setBackgroundResource(R.drawable.btn_back_red);
                } else {
                    tv_regfinish.setBackgroundResource(R.drawable.btn_back);
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
                    LogYiFu.e("inputCode", "" + inputCode);
                    if (code.toLowerCase().equals(inputCode.toLowerCase())) {
                        emailFlag = true;
                    } else {
                        emailFlag = false;

                    }

                }

            }
        });

        et_auto2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String et_auto_text = et_auto2.getText().toString();
                if (et_auto_text.length() > 0) {
                    et_auto_xx2.setVisibility(View.VISIBLE);
                } else {
                    et_auto_xx2.setVisibility(View.GONE);
                }
                if (et_phoned.getText().length() > 0
                        && et_auto2.getText().length() > 0
//                        && et_eml.getText().length() > 0
//                        && et_nickname1.getText().length() > 0


                        ) {
                    tv_regfinish.setBackgroundResource(R.drawable.btn_back_red);
                } else {
                    tv_regfinish.setBackgroundResource(R.drawable.btn_back);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                String code = s.toString();
//                if (code != null && code.length() == 4) {
//                    String inputCode = "";
//                    for (int i = 0; i < res2.length; i++) {
//                        inputCode += res2[i];
//                    }
//                    if (code.toLowerCase().equals(inputCode.toLowerCase())) {
//                        emailFlag = true;
//                    } else {
//                        emailFlag = false;
//
//                    }
//
//                }

            }
        });

        et_phoned.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String et_account_text = et_phoned.getText().toString();
                if (et_account_text.length() > 0) {
                    et_phoned_xx.setVisibility(View.VISIBLE);
                } else {
                    et_phoned_xx.setVisibility(View.GONE);
                }
                if (et_auto2.getText().toString().length() > 0
                        && et_phoned.getText().toString().length() > 0
//                        && et_pauto.getText().toString().length() > 0
//                        && et_nickname.getText().toString().length() > 0

                        ) {
                    tv_regfinish.setBackgroundResource(R.drawable.btn_back_red);
                } else {
                    tv_regfinish.setBackgroundResource(R.drawable.btn_back);
                }

                if (et_account_text.length()==11) {
                    PublicUtil.setVCode(getActivity(),ck_auto2,et_phoned.getText().toString().trim());
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
        et_eml.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String et_account_text = et_eml.getText().toString();
                if (et_account_text.length() > 0) {
                    et_eml_xx.setVisibility(View.VISIBLE);
                } else {
                    et_eml_xx.setVisibility(View.GONE);
                }
                if (et_ipwd1.getText().length() > 0
                        && et_auto.getText().length() > 0
                        && et_eml.getText().length() > 0
//                        && et_nickname1.getText().length() > 0


                        ) {
                    tv_regfinish.setBackgroundResource(R.drawable.btn_back_red);
                } else {
                    tv_regfinish.setBackgroundResource(R.drawable.btn_back);
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_wx:
                onThirdClickListener.wxClick(v);
                break;
            case R.id.img_qq:
                onThirdClickListener.qqClick(v);
                break;
            case R.id.img_wb:
                onThirdClickListener.sinaClick(v);
                break;
            case R.id.tv_chreg:
                if ("邮箱注册".equals(tv_chreg.getText().toString())) {
                    tvTitle.setText("邮箱注册");
                    if (et_ipwd1.getText().length() > 0
                            && et_auto.getText().length() > 0
                            && et_eml.getText().length() > 0
//                            && et_nickname1.getText().length() > 0

                            ) {
                        tv_regfinish.setBackgroundResource(R.drawable.btn_back_red);
                    } else {
                        tv_regfinish.setBackgroundResource(R.drawable.btn_back);
                    }
                } else if ("手机注册".equals(tv_chreg.getText().toString())) {
                    tvTitle.setText("手机注册");
                    if (et_auto2.getText().toString().length() > 0
                            && et_phoned.getText().toString().length() > 0
//                            && et_pauto.getText().toString().length() > 0
//                            && et_nickname.getText().toString().length() > 0


                            ) {
                        tv_regfinish.setBackgroundResource(R.drawable.btn_back_red);
                    } else {
                        tv_regfinish.setBackgroundResource(R.drawable.btn_back);
                    }

                } else {
                    tvTitle.setText("注册");
                }
                chooseRegister();
                break;
            case R.id.tv_acode:// 获取手机验证码
                String str = et_phoned.getText().toString().trim();
                if (str == null || str.equals("")) {
                    CenterToast.centerToast(getActivity(), "请输入手机号码");
                    return;
                }
                if (!StringUtils.isPhoneNumberValid(str)) {
                    CenterToast.centerToast(getActivity(), "请输入正确的手机号!");
                    return;
                }
                // timeCount = new TimeCount(30000, 1000, tv_acode);
                // timeCount.start();
//                getPhoneCode(null);

                break;
            case R.id.ck_auto:
                // 重新初始化验证码
                res = ck_auto.getValidataAndSetImage();
                et_auto.setText("");
                break;
            case R.id.ck_auto2:
                // 重新初始化验证码
//                res2 = ck_auto2.getValidataAndSetImage();
                if(et_phoned.getText().toString().trim().length()==11){
                    PublicUtil.setVCode(getActivity(),ck_auto2,et_phoned.getText().toString().trim());
                }else{
                    CenterToast.centerToast(getActivity(), "请输入正确的手机号码");
                }
                et_auto2.setText("");

                break;
            case R.id.tv_regfinish:// 完成注册
                    sbRegister(null);

                break;
            case R.id.ll_user_protocol:// 衣蝠协议
                Intent intent = new Intent(getActivity(), UserProtocolActivity.class);
                intent.putExtra("value", true);
                startActivity(intent);
                break;
            case R.id.et_ipwd_xx:
                et_ipwd.getText().clear();
                break;
            case R.id.et_phoned_xx:
                et_phoned.getText().clear();
                break;
            case R.id.et_pwd_xx:
                et_pauto.getText().clear();
                break;
//            case R.id.et_nickname_xx:
//                et_nickname.getText().clear();
//                break;
            case R.id.et_ipwd1_xx:
                et_ipwd1.getText().clear();
                break;
            case R.id.et_auto_xx:
                et_auto.getText().clear();
                break;
            case R.id.et_auto_xx2:
                et_auto2.getText().clear();
                break;
            case R.id.et_eml_xx:
                et_eml.getText().clear();
                break;
//            case R.id.et_nickname1_xx:
//                et_nickname1.getText().clear();
//                break;

            default:
                break;
        }

    }

    /***
     * 选择注册方式
     */
    private void chooseRegister() {

        if (intChoose == 0) {
            intChoose++;
            lay_phoned.setVisibility(View.GONE);
            lay_emaild.setVisibility(View.VISIBLE);
            tv_gquto.setText("使用第三方账号登录,3秒完成注册");
            tv_chreg.setText(R.string.tv_phreg);
            tv_regfinish.setText("完成注册");

        } else {
            intChoose--;
            lay_phoned.setVisibility(View.VISIBLE);
            lay_emaild.setVisibility(View.GONE);
//			tv_gquto.setText("没有收到验证码？使用第三方账号一键登录");
            tv_gquto.setText("使用第三方账号登录,3秒完成注册");
            tv_chreg.setText(R.string.tv_erg);
            tv_regfinish.setText("下一步");
        }
    }

    /***
     * 获取手机注册验证码接口
     *
     * @param v
     */
    private void getPhoneCode(View v, final String vCode) {

        String phone_no = et_phoned.getText().toString().trim();
        if (phone_no.length() == 0) {
            ToastUtil.showShortText(getActivity(), "请输入手机号");
            return;

        }


        new SAsyncTask<String, Void, ReturnInfo>(getActivity(), v, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {

                return ComModel.sendPhoneVerifyCodeVCode(getActivity(), params[0], 1,vCode);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                super.onPostExecute(context, result, e);

                // System.out.println("result:" + result.getStatus() +
                // ",message:" + result.getMessage());
                if (null == e) {
                    if (null != result) {// 获取手机验证码成功
                        LogYiFu.e("手机验证码：", "获取手机验证码成功");
                        // timeCount.cancel();
                        // tv_acode.setBackgroundResource(R.color.actionbar_bn);
                        tv_acode.setEnabled(true);
                        // tv_acode.setText("重发(30)");
                        timeer.start();

                        Intent intent=new Intent(instance,PhoneRegisterActivity.class);
                        intent.putExtra("phone",et_phoned.getText().toString().trim());
                        startActivity(intent);
                    } else {
                        ToastUtil.showShortText(context, result.getMessage());
                    }
                }
            }

        }.execute(phone_no);
    }

    // /密码输入明文 或密文显示
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.tv_sbt) {
            setShowPwd(et_ipwd, isChecked);

        } else if (buttonView.getId() == R.id.tv_sbt1) {
            setShowPwd(et_ipwd1, isChecked);
        } else if (buttonView.getId() == R.id.ckb_agreen) {
            if (isChecked) {

                tv_regfinish.setBackgroundResource(R.drawable.u14);
                // tv_regfinish.setEnabled(true);
            } else {
                tv_regfinish.setBackgroundResource(R.drawable.u14);
                // tv_regfinish.setEnabled(false);
            }

            isAuthChecked = isChecked;
        }
    }

    private void setShowPwd(EditText eText, boolean isChecked) {
        if (isChecked) {
            eText.setInputType(InputType.TYPE_CLASS_TEXT);
        } else {
            eText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    // public static int getWordCount(String s)
    // {
    // s = s.replaceAll("[^\\x00-\\xff]", "**");
    // int length = s.length();
    // return length;
    // }

    public static int getWordCount(String s) {
        // if (s == null)
        // return 0;
        // char[] c = s.toCharArray();
        // int len = 0;
        // for (int i = 0; i < c.length; i++) {
        // len++;
        // if (!isLetter(c[i])) {
        // len++;
        // }
        // }
        return (s.getBytes().length - s.length()) / 2 + s.length();
    }

    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /****
     * 提交注册
     */
    private void sbRegister(View v) {


//        if (GuideActivity.needShouquan) {

            //每次都授权
//            final UMSocialService mController =
//                    UMServiceFactory.getUMSocialService("");
//            UMWXHandler wxHandler = new UMWXHandler(getActivity(),
//                    WxPayUtil.APP_ID, WxPayUtil.APP_SECRET);
//            wxHandler.addToSocialSDK();
//            wxHandler.setRefreshTokenAvailable(false);
//            mController.deleteOauth(getActivity(), null, null);


//        }


        if (intChoose == 0) {// 手机注册
            String phoneNo = et_phoned.getText().toString().trim();
            if (phoneNo.length() == 0) {
                CenterToast.centerToast(getActivity(), "请输入手机号码");
                return;
            }
            if (!setETNull(et_phoned, phoneNo))
                return;
            if (!StringUtils.isPhoneNumberValid(phoneNo)) {
                CenterToast.centerToast(getActivity(), "你的手机号格式不正确,请重输");

                return;
            }
            String auto2 = et_auto2.getText().toString().trim();

            if (auto2.length() == 0) {
                CenterToast.centerToast(getActivity(), "请输入验证码");
                return;
            }
//            String code = "";
//            for (int i = 0; i < res2.length; i++) {
//                code += res2[i];
//            }
//            if (!auto2.toLowerCase().equals(code.toLowerCase())) {
//                CenterToast.centerToast(getActivity(), "请输入正确的验证码");
//                res2 = ck_auto2.getValidataAndSetImage();
//                return;
//            }

            getPhoneCode(null,et_auto2.getText().toString().trim());
//            if (!isAuthChecked) {
////				ToastUtil.showShortText(getActivity(), "请同意衣蝠服务协议！");
//                CenterToast.centerToast(getActivity(), "请同意衣蝠服务协议！");
//                return;
//            }

//            timeer.cancel();
//            tv_acode.setText("获取验证码");
//            tv_acode.setEnabled(true);
//
//            if (hasWX && GuideActivity.needShouquan) {
//                //微信授权---拿到unnid
//                openId(SHARE_MEDIA.WEIXIN, 0, instance, v, phoneNo, pwdStr, code, nickname);
////			signup4Phone(v, phoneNo, pwdStr, code, nickname);
//            } else {
//                signup4Phone(v, phoneNo, pwdStr, code, "", "", "");
//            }

        } else {// 邮箱注册
            // 测试

            String email = et_eml.getText().toString().trim();


            if (email.length() == 0) {
                CenterToast.centerToast(getActivity(), "请输入邮箱地址");

                return;
            }

//			if (!setETNull(et_eml, email))
//				return;


            if (!StringUtils.isEmail(email)) {
//				et_eml.setError("你的邮箱格式不正确，请重新输入");


                CenterToast.centerToast(getActivity(), "你的邮箱格式不正确，请重新输入");


                return;
            }
//            String nick_name = et_nickname1.getText().toString().trim();


//            if (nick_name.length() == 0) {
////				ToastUtil.showShortText(getActivity(), "请输入昵称");
//                CenterToast.centerToast(getActivity(), "请输入昵称");
//                return;
//            }


//            if (getWordCount(nick_name) > 8 || getWordCount(nick_name) < 2) {
//                et_nickname1.requestFocus();
//
//                CenterToast.centerToast(getActivity(), "请输入2-8个字符的昵称");
////				ToastUtil.showShortText(getActivity(), "请输入2-8个字符的昵称");
//                return;
//            }
            String pwd = et_ipwd1.getText().toString().trim();

            if (!TextUtils.isEmpty(pwd) && (pwd.length() < 6 || pwd.length() > 16)) {

//				ToastUtil.showShortText(getActivity(), "密码只能6-16个字符");
                CenterToast.centerToast(getActivity(), "密码只能6-16个字符");
                return;
            }
            if (pwd.length() == 0) {
//				ToastUtil.showShortText(getActivity(), "请输入密码");
                CenterToast.centerToast(getActivity(), "请输入密码");
                return;
            }


            String auto = et_auto.getText().toString().trim();

            if (auto.length() == 0) {
                CenterToast.centerToast(getActivity(), "请输入验证码");
//				ToastUtil.showShortText(getActivity(), "请输入验证码");
                return;
            }



            String code = "";
            for (int i = 0; i < res.length; i++) {
                code += res[i];
            }
            LogYiFu.e("inputCode", "" + code);
            if (!auto.toLowerCase().equals(code.toLowerCase())) {

                CenterToast.centerToast(getActivity(), "请输入正确的验证码");

//				et_auto.setError("请输入正确的验证码");
                // 初始化验证码
                res = ck_auto.getValidataAndSetImage();
                return;
            }
            if (!isAuthChecked) {
                CenterToast.centerToast(getActivity(), "请同意衣蝠服务协议！");
//				ToastUtil.showShortText(getActivity(), "请同意衣蝠服务协议！");
                return;
            }
            timeer.cancel();
            tv_acode.setText("获取验证码");


            if (GuideActivity.needShouquan) {

                openIdEmail(SHARE_MEDIA.WEIXIN, getActivity(), v, email, pwd, "");
            } else {
                signup4Email(v, email, pwd, "", "","");
                emailFlag = false;
            }


        }

    }

    /***
     * 邮箱注册
     *
     * @param v
     * @param email
     * @param pwd
     * @param nick_name
     */
    private void signup4Email(View v, final String email, final String pwd, String nick_name, final String unionid,final  String openid) {

        final String channel = DeviceUtils.getChannelCode(getActivity());
        new SAsyncTask<String, Void, UserInfo>(getActivity(), v, R.string.wait) {

            @Override
            protected UserInfo doInBackground(FragmentActivity context, String... params) throws Exception {
                UserInfo userInfo = ComModel.sigup4Email(getActivity(), params[0], params[1], params[2], channel,
                        deviceToken);
                /*
                 * HashMap<String, Object>
				 * map=ComModel2.queryMyIntegral(context); List<String>
				 * list=(List<String>) map.get("fulfill");
				 * if(list!=null&&map.get("everyDayFulfill")!=null){
				 * list.addAll((List<String>)map.get("everyDayFulfill")); }
				 * YJApplication.instance.setTaskMap(list);
				 */
                return userInfo;
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, final UserInfo result, Exception e) {
                if (null == e) {
                    YJApplication.isLogined = true;
                    if (result != null) {
                        SharedPreferencesUtil.saveBooleanData(context, Pref.ISACLASS, false);// 不是A类
                        SharedPreferencesUtil.saveStringData(context, Pref.pd, MD5Tools.MD5(pwd));
                        GetShopCart.querShopCart(context, 1);


                        if (GuideActivity.needShouquan && uuid.length() > 0) {
                            //更新用户UUid
                            updateWXunnid(unionid,openid);
                        }


                        PublicUtil.registerHuanxin(getActivity(), result, false);
                    }
                }
                super.onPostExecute(context, result, e);
                // openId(SHARE_MEDIA.WEIXIN, 0);
            }

        }.execute(email, pwd, nick_name);

    }

    //更新用户微信unnid
    private void updateWXunnid(final String unionid, final String openid ) {

        new Thread() {
            public void run() {
                try {
                    ComModel2.updateUuid(getActivity(), unionid,openid);
                } catch (Exception e) {
                    ToastUtil.showShortText(instance,e.getMessage());

                }
            }
        }.start();


    }


    /***
     * 手机注册
     *
     * @param v
     * @param phoneNo
     * @param pwdStr
     * @param code
     * @param nickname
     */
    private void signup4Phone(View v, String phoneNo, final String pwdStr, String code, String nickname, final String unionid, final String uid) {

        final String channel = DeviceUtils.getChannelCode(getActivity());
        new SAsyncTask<String, Void, UserInfo>(getActivity(), v, R.string.wait) {

            @Override
            protected UserInfo doInBackground(FragmentActivity context, String... params) throws Exception {
                // TODO Auto-generated method stub
                UserInfo user = ComModel.sigup4Phone(getActivity(), params[0], params[1], params[2], params[3], channel,
                        deviceToken, unionid, uid);
                /*
                 * HashMap<String, Object>
				 * map=ComModel2.queryMyIntegral(context); List<String>
				 * list=(List<String>) map.get("fulfill");
				 * if(list!=null&&map.get("everyDayFulfill")!=null){
				 * list.addAll((List<String>)map.get("everyDayFulfill")); }
				 * YJApplication.instance.setTaskMap(list);
				 */
                return user;
            }

            @Override
            protected boolean isHandleException() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, UserInfo result, Exception e) {
                if (null == e) {
                    YJApplication.isLogined = true;
                    if (result != null) {
                        SharedPreferencesUtil.saveBooleanData(context, Pref.ISACLASS, false);// 不是A类

                        SharedPreferencesUtil.saveStringData(context, Pref.pd, MD5Tools.MD5(pwdStr));
                        GetShopCart.querShopCart(context, 1);
                        PublicUtil.registerHuanxin(getActivity(), result, true);
                    }
                    // openId(SHARE_MEDIA.WEIXIN, 0);
                }
                super.onPostExecute(context, result, e);
            }

        }.execute(phoneNo, pwdStr, code, nickname);

    }

    private boolean setETNull(EditText eText, String args) {
        if (args == null || args.equals("")) {
            // eText.setError("不能为空值");
            // eText.requestFocus();
            return false;
        }
        return true;
    }


    private void openId(final SHARE_MEDIA platform, final int type, final Activity activity, final View v, final String phoneNo, final String pwdStr, final String code, final String nickname) {

        final UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);

        mController.doOauthVerify(activity, platform,
                new UMAuthListener() {

                    @Override
                    public void onStart(SHARE_MEDIA platform) {

                    }

                    @Override
                    public void onError(SocializeException e,
                                        SHARE_MEDIA platform) {
                    }

                    @Override
                    public void onComplete(Bundle value, SHARE_MEDIA platform) {

                        mController.getPlatformInfo(activity, platform, new UMDataListener() {

                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onComplete(int status, Map<String, Object> info) {
                                if (info != null) {
                                    if (type == 0) {// 微信
                                        if (status == 200 && info != null) {
                                            String unionid = info.get("unionid").toString();
                                            String uid = info.get("openid").toString();


                                            signup4Phone(v, phoneNo, pwdStr, code, nickname, unionid, uid);


                                        }
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        signup4Phone(v, phoneNo, pwdStr, code, nickname, "", "");
                    }
                });

    }


    private String uuid = "";

    protected void openIdEmail(SHARE_MEDIA weixin, final Context context, final View v, final String email, final String pwd, final String nick_name) {

        final UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);

        mController.doOauthVerify(context, weixin, new UMAuthListener() {

            @Override
            public void onStart(SHARE_MEDIA platform) {

            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {

                final String openid = value.getString("openid");


                mController.getPlatformInfo(context, platform, new UMDataListener() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onComplete(int status, Map<String, Object> info) {
                        if (info != null) {

                            final String unionid = info.get("unionid").toString();
//                            if (TextUtils.isEmpty(uuid)) {
//                                return;
//                            }


                            //
                            signup4Email(v, email, pwd, nick_name, unionid,openid);
                            emailFlag = false;


                        }
                    }
                });
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                signup4Email(v, email, pwd, nick_name, "","");
                emailFlag = false;

//                return;

            }
        });

    }

}