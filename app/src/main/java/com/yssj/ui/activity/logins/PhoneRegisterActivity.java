package com.yssj.ui.activity.logins;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.yssj.Constants;
import com.yssj.SmsContent;
import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.huanxin.PublicUtil;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.setting.UserProtocolActivity;
import com.yssj.utils.CenterToast;
import com.yssj.utils.CheckStrUtil;
import com.yssj.utils.DeviceUtils;
import com.yssj.utils.GetShopCart;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXcheckUtil;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static com.yssj.Json.JUserInfo.nickname;


/**
 * Created by Administrator on 2017/8/9.
 */

public class PhoneRegisterActivity extends FragmentActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private CountDownTimer timeer;
    private TextView tv_acode;//获取验证码按钮
    private Context mContext;
    private String phone_no;
    private EditText et_pauto;//验证码输入框
    private ImageView et_pauto_xx;//验证码清空按钮
    private ToggleButton tv_sbt;//密码查看按钮
    private EditText et_ipwd;//密码输入框
    private ImageView et_ipwd_xx;//密码删除按钮
    private TextView tv_regfinish;//完成注册
    private String deviceToken;
    private LinearLayout  ll_user_protocol;//用户协议
    private LinearLayout imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_phone);
        mContext = this;
        phone_no = getIntent().getStringExtra("phone");
        initView();

        SmsContent content = new SmsContent((Activity) mContext, new Handler(), et_pauto);
        // 注册短信变化监听
        mContext.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, content);
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
        timeer.start();

    }

    private void initView() {
        imgBack = (LinearLayout) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        ll_user_protocol = (LinearLayout) findViewById(R.id.ll_user_protocol);
        ll_user_protocol.setOnClickListener(this);
        tv_regfinish = (TextView) findViewById(R.id.tv_regfinish);
        tv_regfinish.setEnabled(true);
        tv_regfinish.setOnClickListener(this);
        et_ipwd_xx = (ImageView) findViewById(R.id.et_ipwd_xx);
        et_ipwd_xx.setOnClickListener(this);
        et_ipwd = (EditText) findViewById(R.id.et_ipwd);
        tv_sbt = (ToggleButton) findViewById(R.id.tv_sbt);
        tv_sbt.setOnCheckedChangeListener(this);
        et_pauto_xx = (ImageView) findViewById(R.id.et_pwd_xx);
        et_pauto_xx.setOnClickListener(this);
        TextView tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        tv_acode = (TextView) findViewById(R.id.tv_acode);
        tv_acode.setOnClickListener(this);// 获取验证码
        tvTitle_base.setText("手机注册");
        et_pauto = (EditText) findViewById(R.id.et_pwd);
        try {
            if (!WXcheckUtil.isWeChatAppInstalled(mContext)) {
//                v.findViewById(R.id.img_wx_ll).setVisibility(View.GONE);
//                v.findViewById(R.id.tv_third_ll).setVisibility(View.GONE);
                hasWX = false;
            }
        } catch (Exception e) {
        }
        iphonePWDXianzhi();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_acode:// 获取手机验证码
//                getPhoneCode(null);
                onBackPressed();//返回上个界面重新填写新的图片验证码
                break;
            case R.id.et_pwd_xx://输入框验证码清空
                et_pauto.getText().clear();
                break;
            case R.id.et_ipwd_xx://密码删除按钮
                et_ipwd.getText().clear();
                break;
            case R.id.tv_regfinish:// 完成注册
                sbRegister(null);

                break;
            case R.id.img_back:
                // appManager.finishActivity();
             String imei=   CheckStrUtil.getImei(mContext);
                LogYiFu.e("imei",imei);
                onBackPressed();
                break;
            case R.id.ll_user_protocol:// 衣蝠协议
                Intent intent = new Intent(mContext, UserProtocolActivity.class);
                intent.putExtra("value", true);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private boolean hasWX = true;

    private void sbRegister(View v) {//完成注册
        String code = et_pauto.getText().toString().trim();

        if (code.length() == 0) {
            CenterToast.centerToast(mContext, "请输入验证码");
            return;
        }
        String pwdStr = et_ipwd.getText().toString().trim();

        if (pwdStr.length() == 0) {
            CenterToast.centerToast(mContext, "请输入密码");
            return;
        }

        if (!TextUtils.isEmpty(pwdStr) && (pwdStr.length() < 6 || pwdStr.length() > 16)) {
            CenterToast.centerToast(mContext, "密码只能6-16个字符");
            return;
        }


//        if (!isAuthChecked) {
//            CenterToast.centerToast(getActivity(), "请同意衣蝠服务协议！");
//            return;
//        }

//        timeer.cancel();
//        tv_acode.setText("获取验证码");
//        tv_acode.setEnabled(true);

        if (hasWX && GuideActivity.needShouquan) {
            //微信授权---拿到unnid
            openId(SHARE_MEDIA.WEIXIN, 0, (Activity) mContext, v, phone_no, pwdStr, code, nickname);
        } else {
            signup4Phone(v, phone_no, pwdStr, code, "", "", "");
        }
    }

    private void openId(final SHARE_MEDIA platform, final int type, final Activity activity, final View v, final String phoneNo, final String pwdStr, final String code, final String nickname) {

        final UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);

        mController.doOauthVerify(activity, platform,
                new SocializeListeners.UMAuthListener() {

                    @Override
                    public void onStart(SHARE_MEDIA platform) {

                    }

                    @Override
                    public void onError(SocializeException e,
                                        SHARE_MEDIA platform) {
                    }

                    @Override
                    public void onComplete(Bundle value, SHARE_MEDIA platform) {

                        final String openid = value.getString("openid");


                        mController.getPlatformInfo(activity, platform, new SocializeListeners.UMDataListener() {

                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onComplete(int status, Map<String, Object> info) {
                                if (info != null) {
                                    if (type == 0) {// 微信
                                        if (status == 200 && info != null) {

                                            String unionid = info.get("unionid").toString();

                                            signup4Phone(v, phoneNo, pwdStr, code, nickname, unionid, openid);

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

    /***
     * 手机注册
     *
     * @param v
     * @param phoneNo
     * @param pwdStr
     * @param code
     * @param nickname
     */
    private void signup4Phone(View v, String phoneNo, final String pwdStr, String code, String nickname, final String unionid, final String openid) {

        final String channel = DeviceUtils.getChannelCode(mContext);
        new SAsyncTask<String, Void, UserInfo>((FragmentActivity) mContext, v, R.string.wait) {

            @Override
            protected UserInfo doInBackground(FragmentActivity context, String... params) throws Exception {
                // TODO Auto-generated method stub
                UserInfo user = ComModel.sigup4Phone(mContext, params[0], params[1], params[2], params[3], channel,
                        deviceToken, unionid, openid);
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
                        SharedPreferencesUtil.saveBooleanData(context, YConstance.Pref.ISACLASS, false);// 不是A类

                        SharedPreferencesUtil.saveStringData(context, YConstance.Pref.pd, MD5Tools.MD5(pwdStr));
                        GetShopCart.querShopCart(context, 1);
                        PublicUtil.registerHuanxin((Activity) mContext, result , true);
                    }
                    // openId(SHARE_MEDIA.WEIXIN, 0);
                }
                super.onPostExecute(context, result, e);
            }

        }.execute(phoneNo, pwdStr, code, nickname);

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
                    ToastUtil.showShortText(mContext, "密码不能包含汉字~");
                    et_ipwd.setSelection(str.length());
                }
                if (editable.length() > 0) {
                    et_ipwd_xx.setVisibility(View.VISIBLE);
                } else {
                    et_ipwd_xx.setVisibility(View.GONE);
                }
                if (et_ipwd.getText().toString().length() > 0
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
    }

    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String regEx = "[\u4E00-\u9FA5]"; // "[\u4e00-\u9fa5]"
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /***
     * 获取手机注册验证码接口
     *
     * @param v
     */
    private void getPhoneCode(View v) {


        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, v, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {

                return ComModel.sendPhoneVerifyCode(mContext, params[0], 1);
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
                        tv_acode.setEnabled(true);
                        // tv_acode.setText("重发(30)");
                        timeer.start();


                    } else {
                        ToastUtil.showShortText(context, result.getMessage());
                    }
                }
            }

        }.execute(phone_no);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.tv_sbt) {
            setShowPwd(et_ipwd, isChecked);

        }
    }

    private void setShowPwd(EditText eText, boolean isChecked) {
        if (isChecked) {
            eText.setInputType(InputType.TYPE_CLASS_TEXT);
        } else {
            eText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
}
