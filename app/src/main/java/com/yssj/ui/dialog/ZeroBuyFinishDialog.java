package com.yssj.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yssj.YConstance;
import com.yssj.activity.R;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.utils.SharedPreferencesUtil;

public class ZeroBuyFinishDialog extends Dialog implements View.OnClickListener {

	private Context context;
	private View iconClose,whiteBtn,redBtn;
	private TextView tvContent;

	public ZeroBuyFinishDialog(Context context, int style) {
		super(context, style);
		setCanceledOnTouchOutside(false);
		this.context = context;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_zero_buy_finish);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		initView();
	}

	private void initView() {
		iconClose = findViewById(R.id.icon_close);
		tvContent = (TextView) findViewById(R.id.tv_content);
		tvContent.setText("抽中的提现额度与"
				+SharedPreferencesUtil.getStringData(context, YConstance.Pref.PAYSUCCESSDIALOG_SHOW_DIALOG,"-1")
				+"元购衣款已返现至账户余额，处于冻结状态，交易成功后即可解冻。");
		whiteBtn = findViewById(R.id.btn_white);
		redBtn =  findViewById(R.id.btn_red);
		whiteBtn.setOnClickListener(this);
		redBtn.setOnClickListener(this);
		iconClose.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.icon_close:
			this.dismiss();
			break;
			case R.id.btn_red:
				SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
				context.startActivity(new Intent(context, CommonActivity.class));
				((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
				this.dismiss();
				break;
			case R.id.btn_white:
				this.dismiss();
				break;

		default:
			break;
		}
		
	}
	
}
