package com.yssj.ui.activity.setting;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.huanxin.PublicUtil;
import com.yssj.model.ComModel;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.fragment.setting.SuccessBindPhoneActivity;
import com.yssj.utils.CenterToast;
import com.yssj.utils.ToastUtil;

public class ChangePhoneBindFragment extends BaseFragment implements
        OnClickListener {

    private TextView tvTitle_base, tv_current_phone, tv_send;
    private LinearLayout img_back;

    private EditText ev_quondam_phone_num, ev_phone_num,et_auto;
    private Button btn_send_code;

    private String phone = "";
    private String code;
    private ImageView ivGif;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.activity_change_phone_bind, null);
        view.setBackgroundColor(Color.WHITE);
        tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("更换手机号");
        img_back = (LinearLayout) view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        phone = getArguments().getString("phone");
        tv_current_phone = (TextView) view.findViewById(R.id.tv_current_phone);
        tv_current_phone.setText("当前手机号："
                + phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        ev_phone_num = (EditText) view.findViewById(R.id.ev_phone_num);
        et_auto = (EditText) view.findViewById(R.id.et_auto); //图形验证码
//		tv_phone_num.setText(phone.replaceAll("(\\d{3})\\d{4}(\\d{4})",
//				"$1****$2"));

        ev_quondam_phone_num = (EditText) view
                .findViewById(R.id.ev_quondam_phone_num);
        btn_send_code = (Button) view.findViewById(R.id.btn_send_code);
        btn_send_code.setOnClickListener(this);

        tv_send = (TextView) view.findViewById(R.id.tv_send);
        tv_send.setOnClickListener(this);


        ivGif = (ImageView) view.findViewById(R.id.iv_gif);
        ivGif.setOnClickListener(this);


        //手机号输入框的监听
        ev_phone_num.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                String code = ev_phone_num.getText().toString().trim();
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


        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment mFragment;
        switch (v.getId()) {
            case R.id.img_back:
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ev_quondam_phone_num.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(ev_phone_num.getWindowToken(), 0);
                mFragment = new VerifyQuondamPhoneFragment();
                Bundle bundle = new Bundle();
                bundle.putString("phone", phone);
                mFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
                break;
            case R.id.btn_send_code: // 检查验证码
                submitCode();
                break;
            case R.id.tv_send:
                tv_send.setEnabled(false);
                getCode();//获取手机验证码

                break;


            case R.id.iv_gif: //gif图片点击
                String code = ev_phone_num.getText().toString().trim();
                if (code.length() != 11) {
                    CenterToast.centerToast(context, "请输入正确的手机号码");
                } else {
                    PublicUtil.setVCode(context, ivGif, code);
                }

                break;
        }


    }

    private void submitCode() {
        code = ev_quondam_phone_num.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showLongText(context, "验证码不能为空");
            return;
        }

        new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) context, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context,
                                                Void... params) throws Exception {

                return ComModel.checkCode(context, code, "", "");
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         ReturnInfo result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result != null && "1".equals(result.getStatus())) {

                        // 往下一步跳转
                    /*Fragment mFragment = new SecureIdentifyFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();*/
                        InputMethodManager inputMethodManager =
                                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                        Fragment mFragment = new SuccessBindPhoneActivity();
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();





                        Intent in = new Intent(getActivity(),SuccessBindPhoneActivity.class);

//                        in.putExtra("buy0","buy0");
                        getActivity().startActivity(in);
                        ((Activity) getActivity()).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);



                    } else {
                        ToastUtil.showLongText(context, result.getMessage());
                    }
                }
            }

        }.execute();
    }

    private void getCode() {

        final String phone = ev_phone_num.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showLongText(context, "手机号不能为空");
            return;
        }

        final String txCode = et_auto.getText().toString();
        if (TextUtils.isEmpty(txCode)) {
            ToastUtil.showLongText(context, "图形验证码不能为空");
            return;
        }



        new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) context, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context,
                                                Void... params) throws Exception {

                return ComModel.sendPhoneVerifyCode(context, phone, 8, "true", txCode);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(final FragmentActivity context,
                                         ReturnInfo result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result != null && "1".equals(result.getStatus())) {



                        ev_quondam_phone_num.setFocusable(true);
                        ev_quondam_phone_num.requestFocus();


                        //获取成功

                        new CountDownTimer(Long.parseLong(getResources().getString(R.string.identify_code)), 1000) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                tv_send.setText(String.valueOf(millisUntilFinished / 1000) + "秒重发");

                                tv_send.setBackgroundResource(R.drawable.bg_square_bind_btn_default);
                            }

                            @Override
                            public void onFinish() {

                                //清空图形验证码输入框，刷新图形验证码
                                et_auto.getText().clear();
                                String code = ev_phone_num.getText().toString().trim();
                                if (code.length() == 11) {
                                    PublicUtil.setVCode(context, ivGif, code);
                                }




                                tv_send.setText("重新发送");
                                tv_send.setEnabled(true);
                                tv_send.setBackgroundResource(R.drawable.bg_square_choice_btn_checked);
                            }
                        }.start();

                    } else {


                        //清空图形验证码输入框，刷新图形验证码
                        et_auto.getText().clear();
                        String code = ev_phone_num.getText().toString().trim();
                        if (code.length() == 11) {
                            PublicUtil.setVCode(context, ivGif, code);
                        }



                        tv_send.setText("重新发送");
                        tv_send.setEnabled(true);
                        tv_send.setBackgroundResource(R.drawable.bg_square_choice_btn_checked);
                    }
                    ToastUtil.showShortText(context, result.getMessage());
                } else {
                    tv_send.setEnabled(true);
                }
            }

        }.execute();
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub

    }
}
