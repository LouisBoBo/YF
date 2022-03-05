package com.yssj.ui.dialog;

import com.yssj.activity.R;
import com.yssj.ui.activity.MainMenuActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class SignActiveShopExplainDialog extends Dialog implements android.view.View.OnClickListener {
	
	private Context context;
	private TextView bt;
	private ImageView btnClose;
	private TextView tv_one;

	public SignActiveShopExplainDialog(Context context) {
		super(context);
		setCanceledOnTouchOutside(false);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sign_active_shop_task_explain);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		getWindow().setWindowAnimations(R.style.common_dialog_style);
		initView();
	}

	private void initView() {
		bt = (TextView) findViewById(R.id.bt);
		tv_one = (TextView) findViewById(R.id.tv_one);
		bt.setOnClickListener(this);
		btnClose = (ImageView) findViewById(R.id.icon_close);
		btnClose.setOnClickListener(this);
		
	}

	public void setText(String tv_one_text){
		tv_one.setText(tv_one_text);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.icon_close:
			this.dismiss();
			break;
		case R.id.bt:
			this.dismiss();
//			Intent intent2 = new Intent(context, MainMenuActivity.class);
//			intent2.putExtra("toYf", "toYf");
//			context.startActivity(intent2);
			break;

		default:
			break;
		}
		
	}
	
}
