package com.yssj.ui.fragment.mywallet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.infos.MyWalletCommonFragmentActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ToastUtil;
import com.yssj.wxpay.WxPayUtil;

import java.util.HashMap;
import java.util.Map;

public class WithDrawWeiXinExplainNameActivity extends BasicActivity implements OnClickListener, TextWatcher {
	private Context mContext;
	private EditText mName;
	private TextView mNextStep, mText;
	private LinearLayout img_back;
	private String money = "";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_withdraw_weixin_explain_name);
		mContext = this;
		money = getIntent().getStringExtra("money");
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	private void initView() {
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		mName = (EditText) findViewById(R.id.explain_name);
		mName.addTextChangedListener(this);
		mNextStep = (TextView) findViewById(R.id.explain_btn_next_step);
		mText = (TextView) findViewById(R.id.tv_text);
		SpannableStringBuilder builder = new SpannableStringBuilder(mText.getText().toString());
		ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#ff3f8b"));
		builder.setSpan(redSpan, 37, 49, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		mText.setText(builder);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.explain_btn_next_step:
			openId(SHARE_MEDIA.WEIXIN, 0, WithDrawWeiXinExplainNameActivity.this);
			break;

		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (mName.getText().toString().length() > 0) {
			mNextStep.setOnClickListener(this);
			mNextStep.setBackgroundResource(R.drawable.btn_back_red);
			mNextStep.setClickable(true);
		} else {
			mNextStep.setBackgroundResource(R.drawable.submit_money_next_shape);
			mNextStep.setClickable(false);
		}

	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	public void submit(final String openid) {// 微信验证

		new SAsyncTask<String, Void, HashMap<String, Object>>((FragmentActivity) WithDrawWeiXinExplainNameActivity.this,
				R.string.wait) {

			@Override
			protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
					throws Exception {
				return ComModel.WXDepositAdd(mContext, money, "", mName.getText().toString(), "", openid, false);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e) {
					String SucMoney = result.get("money").toString();
					int flag = Integer.parseInt(result.get("flag").toString());
					if (flag == 0) { // 提现成功
						Intent intent = new Intent(mContext, MyWalletCommonFragmentActivity.class);
						intent.putExtra("flag", "withDrawalFragment");
						intent.putExtra("allMoney", money);
						intent.putExtra("SucMoney", SucMoney);
						intent.putExtra("WXFlag", "WXFlag");
						intent.putExtra("alliance", "wallet");
						startActivity(intent);
						WithDrawWeiXinExplainNameActivity.this.finish();
					} else if (flag == 1 || flag == 2 || flag == 3 || flag == 4 || flag == 5||flag==6) {// flag==5提现金额不能大于最高提现金额或者小于最低提现金额
						// String message = result.get("message").toString();
						// ToastUtil.showShortText(context, message);
						Intent intent = new Intent(mContext, MyWalletCommonFragmentActivity.class);
						intent.putExtra("flag", "withDrawalFragment");
						intent.putExtra("allMoney", money);
						intent.putExtra("SucMoney", "0.0");
						intent.putExtra("WXFlag", "WXFlag");
						intent.putExtra("alliance", "wallet");
						startActivity(intent);
						WithDrawWeiXinExplainNameActivity.this.finish();
					}

				}
			}

		}.execute();
	}

	public void openId(final SHARE_MEDIA platform, final int type, final Activity activity) {

		ToastUtil.showShortText2("授权");

//
//		final UMSocialService mController = UMServiceFactory.getUMSocialService("");
//		UMWXHandler wxHandler = new UMWXHandler(WithDrawWeiXinExplainNameActivity.this, WxPayUtil.APP_ID,
//				WxPayUtil.APP_SECRET);
//		wxHandler.addToSocialSDK();
//		wxHandler.setRefreshTokenAvailable(false);
//		mController.deleteOauth(WithDrawWeiXinExplainNameActivity.this, null, null);
//		mController.doOauthVerify(activity, platform, new UMAuthListener() {
//
//			@Override
//			public void onStart(SHARE_MEDIA platform) {
//				Toast.makeText(WithDrawWeiXinExplainNameActivity.this, "授权开始", Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onError(SocializeException e, SHARE_MEDIA platform) {
//				Toast.makeText(WithDrawWeiXinExplainNameActivity.this, "授权失败" + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onComplete(Bundle value, SHARE_MEDIA platform) {
//				LogYiFu.e("微信授权三方", "11" + value.toString());
//				mController.getPlatformInfo(activity, platform, new UMDataListener() {
//
//					@Override
//					public void onStart() {
//
//					}
//
//					@Override
//					public void onComplete(int status, Map<String, Object> info) {
//						if (info != null) {
//							if (type == 0) {// 微信
//								if (status == 200 && info != null) {
//
//									final String openid = info.get("openid").toString();// openid
//									submit("" + openid);
//								}
//							}
//						}
//					}
//				});
//			}
//
//			@Override
//			public void onCancel(SHARE_MEDIA platform) {
//
//			}
//		});
//
	}

}
