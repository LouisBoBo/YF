package com.yssj.ui.dialog;

import com.yssj.activity.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class XunBaoDialog extends Dialog implements android.view.View.OnClickListener {
	
	private Context context;
	private TextView tv;
	private TextView tvContent;

	public XunBaoDialog(Context context) {
		super(context);
		setCanceledOnTouchOutside(false);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xunbao_dialog);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		getWindow().setWindowAnimations(R.style.common_dialog_style);
		initView();
	}

	private void initView() {
		tv = (TextView) findViewById(R.id.tv_xunbao);
		tvContent = (TextView) findViewById(R.id.tv_xunbao_content);
		tv.setOnClickListener(this);
		
	}
	public void setContentText(String content){
		tvContent.setText(content);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_xunbao:
			this.dismiss();
			break;

		default:
			break;
		}
		
	}
	
}
