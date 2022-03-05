package com.yssj.ui.fragment.mywallet;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.GetUserABClass;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.style.ForegroundColorSpan;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WithDrawWeiXinExplainActivity extends BasicActivity implements OnClickListener, TextWatcher {
	public static Context mContext;
	private EditText mName;
	private TextView mNextStep;
	private LinearLayout img_back;
	private String money = "";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_withdraw_weixin_explain);
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
//		mCard = (EditText) findViewById(R.id.expalin_card);
//		mCard.addTextChangedListener(this);
		mNextStep = (TextView) findViewById(R.id.explain_btn_next_step);
		mNextStep.setOnClickListener(this);
//		mText = (TextView) findViewById(R.id.explain_text);
//		SpannableStringBuilder builder = new SpannableStringBuilder(mText.getText().toString());
//		ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#ff3f8b"));
//		builder.setSpan(redSpan, 11, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		mText.setText(builder);
		String digists = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
//		mCard.setKeyListener(DigitsKeyListener.getInstance(digists));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.explain_btn_next_step:

			if(StringUtils.isEmpty( mName.getText().toString().trim())){
				ToastUtil.showShortText2("请输入姓名");
				return;
			}
//			if (personIdValidation(mCard.getText().toString())) {// 匹配身份证
				Intent intent = new Intent(mContext, WithDrawWeiXinCheckActivity.class);
				intent.putExtra("money", money);
				intent.putExtra("name", mName.getText().toString());
//				intent.putExtra("identity", mCard.getText().toString());
				startActivity(intent);
//			} else {
//				ToastUtil.showShortText(mContext, "请输入正确的身份证号");
//			}
			break;

		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (mName.getText().toString().length() > 0) {
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

	/**
	 * 验证身份证号是否符合规则
	 * 
	 * @param text
	 *            身份证号
	 * @return
	 */
	public boolean personIdValidation(String text) {
		String regx = "[0-9]{17}X";
		String reg1 = "[0-9]{15}";
		String regex = "[0-9]{18}";
		return text.matches(regx) || text.matches(reg1) || text.matches(regex);
	}
}
