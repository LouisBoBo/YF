package com.yssj.ui.activity.logins;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.CheckView;
import com.yssj.entity.ReturnInfo;
import com.yssj.huanxin.PublicUtil;
import com.yssj.model.ComModel;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.CenterToast;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.TimeCount;

/***
 * 重发获取验证码
 *
 * @author Administrator
 *
 */
public class FillCodeActivity extends BasicActivity implements OnClickListener {
    private AppManager appManager;
    private View firstView, secondView;
    private TextView tv_commit_first, tv_commit, tv_rsend;
    private EditText et_fpwd, et_phone, et_auto;

    private String type;
    private TimeCount timeCount;

    private LinearLayout img_back;
    private TextView tvTitle_base;
    private int phoneLen;
    private int pwdLen;
    private int autoLen;
    private ImageButton iv_xphone;
    private ImageButton iv_xpwd;
    private ImageButton iv_xauto;
    //	private CheckView ck_auto;
    private ImageView ck_auto;
    private Context context;
//	private String[] res = new String[4]; // 获取每次更新的验证码，可用于判断用户输入是否正确


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_code);
        context = this;
        appManager = AppManager.getAppManager();
        // aBar.setTitle("输入验证码");
//		aBar.hide();
        Bundle bundle = getIntent().getExtras();
        // et_phone.getText().toString() =
        // bundle.getString("et_phone.getText().toString()");
        // type = bundle.getString("type");

        type = "phone";

        initViews();
        // timeCount = new TimeCount(120000, 1000, tv_rsend);
        // timeCount.start();
    }

    private void initViews() {
        firstView = findViewById(R.id.forget_pwd_first);
        secondView = findViewById(R.id.forget_pwd_second);
        firstView.setVisibility(View.VISIBLE);
        secondView.setVisibility(View.GONE);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        tv_commit.setOnClickListener(this);
        tv_commit_first = (TextView) findViewById(R.id.tv_commit_first);
        tv_commit_first.setOnClickListener(this);
        tv_rsend = (TextView) findViewById(R.id.tv_rsend);
        tv_rsend.setOnClickListener(this);
        et_fpwd = (EditText) findViewById(R.id.et_fpwd);
        et_fpwd.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        img_back = (LinearLayout) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("找回密码");
        et_phone = (EditText) findViewById(R.id.et_phone);
        tv_rsend.setText("获取验证码");
        et_auto = (EditText) findViewById(R.id.et_auto);//图片验证码
        iv_xphone = (ImageButton) findViewById(R.id.iv_xphone);
        iv_xpwd = (ImageButton) findViewById(R.id.iv_xpwd);
        iv_xauto = (ImageButton) findViewById(R.id.iv_xauto);
        iv_xphone.setOnClickListener(this);
        iv_xpwd.setOnClickListener(this);
        iv_xauto.setOnClickListener(this);
        iv_xpwd.setVisibility(View.GONE);
        iv_xphone.setVisibility(View.GONE);
        iv_xauto.setVisibility(View.GONE);

        ck_auto = (ImageView) findViewById(R.id.ck_auto);
        ck_auto.setOnClickListener(this);
//		res = ck_auto.getValidataAndSetImage();
        et_phone.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                phoneLen = et_phone.getText().toString().trim().length();

                if (autoLen > 0 && phoneLen == 11) {
                    tv_commit_first.setBackgroundResource(R.drawable.btn_back_red);
                } else {
                    tv_commit_first.setBackgroundResource(R.drawable.btn_back);
                }

                if (phoneLen == 0) {
                    iv_xphone.setVisibility(View.GONE);
                } else {
                    iv_xphone.setVisibility(View.VISIBLE);
                }
                if (phoneLen == 11) {
                    PublicUtil.setVCode(context, ck_auto, et_phone.getText().toString().trim());
                }


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        et_fpwd.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                int pwdLen = et_fpwd.getText().toString().trim().length();


                if (pwdLen == 0) {
                    iv_xpwd.setVisibility(View.GONE);
                    tv_commit.setBackgroundResource(R.drawable.btn_back);
                } else {
                    iv_xpwd.setVisibility(View.VISIBLE);
                    tv_commit.setBackgroundResource(R.drawable.btn_back_red);
                }


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_auto.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                autoLen = et_auto.getText().toString().trim().length();


                if (autoLen == 0) {
                    iv_xauto.setVisibility(View.GONE);
                } else {
                    iv_xauto.setVisibility(View.VISIBLE);
                }

                if (autoLen > 0 && phoneLen == 11) {
                    tv_commit_first.setBackgroundResource(R.drawable.btn_back_red);
                } else {
                    tv_commit_first.setBackgroundResource(R.drawable.btn_back);
                }


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit_first:// 下一步(获取图形验证码之后)

                phoneLen = et_phone.getText().toString().trim().length();
                autoLen = et_auto.getText().toString().trim().length();

                if (phoneLen != 11) {
                    CenterToast.centerToast(context, "请输入正确的手机号码");
                    return;
                }

                if (autoLen <= 0) {
                    CenterToast.centerToast(context, "请输入正确的验证码");
                    return;
                }
                String auto = et_auto.getText().toString().trim();

                if (auto.length() == 0) {
                    CenterToast.centerToast(context, "请输入验证码");
                    return;
                }


//			String code = "";
//			for (int i = 0; i < res.length; i++) {
//				code += res[i];
//			}
//			LogYiFu.e("inputCode", "" + code);
//			if (!auto.toLowerCase().equals(code.toLowerCase())) {
//
//				CenterToast.centerToast(context, "请输入正确的验证码");
//
//				// 初始化验证码
//				res = ck_auto.getValidataAndSetImage();
//				return;
//			}
//			topView.setVisibility(View.GONE);
//			bottomView.setVisibility(View.VISIBLE);
//			phoneLen = et_phone.getText().toString().trim().length();
//			pwdLen = et_fpwd.getText().toString().trim().length();

//			if (phoneLen != 11) {
//				CenterToast.centerToast(context, "请输入正确的手机号码");
//				return;
//			}

                getPhoneCode(v, et_phone.getText().toString().trim(), et_auto.getText().toString().trim());
                break;
            case R.id.tv_commit:// 下一步(提交验证码)

                phoneLen = et_phone.getText().toString().trim().length();
                pwdLen = et_fpwd.getText().toString().trim().length();

                if (phoneLen != 11) {
                    CenterToast.centerToast(context, "请输入正确的手机号码");
                    return;
                }

                if (pwdLen <= 0) {
                    CenterToast.centerToast(context, "请输入正确的验证码");
                    return;
                }

                submit();
                break;
            case R.id.img_back:// 返回上一层
                onBackPressed();
                break;
            case R.id.tv_rsend:// 获取验证码

//			phoneLen = et_phone.getText().toString().trim().length();
//			pwdLen = et_fpwd.getText().toString().trim().length();
//
//			if (phoneLen != 11) {
//				CenterToast.centerToast(context, "请输入正确的手机号码");
//				return;
//			}
//
//			getPhoneCode(v,et_phone.getText().toString().trim(),et_auto.getText().toString().trim());

//			timeCount = new TimeCount(120000, 1000, tv_rsend);
//			timeCount.start();

                // timeCount.cancel();
                // getPhoneCode(v);
                //返回输入图形验证码 并重新刷新
                et_auto.setText("");
                if (et_phone.getText().toString().trim().length() == 11) {
                    PublicUtil.setVCode(context, ck_auto, et_phone.getText().toString().trim());
                }
                firstView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.view_rigth_in));
                secondView.setVisibility(View.GONE);
                firstView.setVisibility(View.VISIBLE);
                break;

            case R.id.iv_xpwd:// 密码×
                et_fpwd.setText("");

                break;

            case R.id.iv_xphone:// 手机号x

                et_phone.setText("");

                break;
            case R.id.iv_xauto:// 图片验证码x

                et_auto.setText("");

                break;
            case R.id.ck_auto:
                // 重新初始化验证码
//			res = ck_auto.getValidataAndSetImage();
                if (et_phone.getText().toString().trim().length() == 11) {
                    PublicUtil.setVCode(context, ck_auto, et_phone.getText().toString().trim());
                } else {
                    CenterToast.centerToast(context, "请输入正确的手机号码");
                }

                et_auto.setText("");
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            appManager.finishActivity();
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        appManager.finishActivity();

    }

    /***
     * 邮箱获取验证码
     *
     * @param v
     */
    private void getEmailCode(View v) {
        new SAsyncTask<String, Void, ReturnInfo>(this, v, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {

                return ComModel.sendEmailVerifyCode(FillCodeActivity.this, params[0], 2);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {

                if (null == e) {
                    if (null != result) {
                        timeCount.start();
                        tv_rsend.setBackgroundResource(R.color.white);
                        // tv_rsend.setText("重发(60s)");
                        Toast.makeText(FillCodeActivity.this, "邮箱重新获取验证码成功", Toast.LENGTH_SHORT).show();
                    }
                }
                super.onPostExecute(context, result, e);
            }

        }.execute(et_phone.getText().toString());

    }

    /***
     * 重发手机获取验证码
     *
     * @param v
     */
    private void getPhoneCode(View v) {

        new SAsyncTask<String, Void, ReturnInfo>(FillCodeActivity.this, v, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {
                return ComModel.sendPhoneVerifyCode(FillCodeActivity.this, params[0], 2);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                if (null == e) {
                    if (null != result) {// 获取手机验证码成功
                        timeCount.start();
                        tv_rsend.setBackgroundResource(R.color.white);
                        // tv_rsend.setText("重发(60s)");

                        Toast.makeText(FillCodeActivity.this, "手机重新获取验证码成功", Toast.LENGTH_SHORT).show();
                    }
                }
                super.onPostExecute(context, result, e);
            }

        }.execute(et_phone.getText().toString());
    }

    /***
     * 提交
     */
    private void submit() {
        String code = et_fpwd.getText().toString().trim();
        // if (code == null || code.equals("")) {
        // et_fpwd.setError("验证码不能为空");
        // return;
        // }
        // /*
        // * if (code.length() != 4) { et_fpwd.setError("你输入的验证码长度必须为4");
        // return;
        // * }
        // */

        checkPhoneCode(code);

    }

    /** 检测邮箱验证码是否正确 */
    // private void checkEmaiCode(final String code) {
    // new SAsyncTask<String, Void, ReturnInfo>(FillCodeActivity.this,
    // R.string.wait) {
    //
    // @Override
    // protected ReturnInfo doInBackground(FragmentActivity context, String...
    // params) throws Exception {
    //
    // return ComModel.checkEmailCode(FillCodeActivity.this,
    // et_phone.getText().toString(), code, "2");
    // }
    //
    // protected void onPostExecute(FragmentActivity context, ReturnInfo result,
    // Exception e) {
    //
    // if (null != result && "1".equals(result.getStatus())) {
    // Intent intent = new Intent(FillCodeActivity.this,
    // ResetPassActivity.class);
    // Bundle bundle = new Bundle();
    // bundle.putString("code", code);
    // bundle.putString("et_phone.getText().toString()",
    // et_phone.getText().toString());
    // bundle.putString("type", type);
    // intent.putExtras(bundle);
    // startActivity(intent);
    // }
    // };
    //
    // @Override
    // protected boolean isHandleException() {
    // return true;
    // }
    //
    // }.execute();
    // }

    /**
     * 检测手机验证码是否正确
     */
    private void checkPhoneCode(final String code) {
        new SAsyncTask<String, Void, ReturnInfo>(FillCodeActivity.this, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {

                return ComModel.checkPhoneCode(context, et_phone.getText().toString(), code, "2");
            }

            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {

                if (null != result && "1".equals(result.getStatus())) {
                    Intent intent = new Intent(FillCodeActivity.this, ResetPassActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("code", code);
                    bundle.putString("account", et_phone.getText().toString().trim());
                    bundle.putString("type", type);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

            ;

            @Override
            protected boolean isHandleException() {
                return true;
            }

        }.execute();
    }

    /***
     * 手机获取验证码
     *
     * @param v
     */
    private void getPhoneCode(View v, String phone_email, final String vCode) {

        new SAsyncTask<String, Void, ReturnInfo>(this, v, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {

                return ComModel.sendPhoneVerifyCodeVCode(FillCodeActivity.this, params[0], 2, vCode);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                LogYiFu.e("TAG", "result====");
                if (null == e && null != result) {
//					if (null != result) {// 获取验证码成功

                    // Intent intent = new Intent(FillCodeActivity.this,
                    // FillCodeActivity.class);
                    // Bundle bundle = new Bundle();
                    // bundle.putString("type", "phone");
                    // bundle.putString("et_phone.getText().toString()",
                    // et_phone.getText().toString());
                    // intent.putExtras(bundle);
                    // startActivity(intent);
                    secondView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.view_rigth_in));
                    secondView.setVisibility(View.VISIBLE);
                    firstView.setVisibility(View.GONE);

                    timeCount = new TimeCount(120000, 1000, tv_rsend);
                    timeCount.start();

//					}
                }
                super.onPostExecute(context, result, e);
            }

        }.execute(phone_email);

    }

}
