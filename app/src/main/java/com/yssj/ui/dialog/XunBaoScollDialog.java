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

public class XunBaoScollDialog extends Dialog implements android.view.View.OnClickListener {
	
	private Context context;
	private ImageView iv;
	private ImageView xunBaoIv;
	public XunBaoScollDialog(Context context,ImageView xunBaoIv) {
		super(context);
		setCanceledOnTouchOutside(false);
		this.context = context;
		this.xunBaoIv = xunBaoIv;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xunbao_scoll_dialog);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		initView();
	}

	private void initView() {
		iv = (ImageView) findViewById(R.id.but_know);
		iv.setOnClickListener(this);
	}
	
	@Override
	public void dismiss() {
		super.dismiss();
		if(xunBaoIv!=null&&xunBaoIv.getVisibility() != View.VISIBLE){
			xunBaoIv.setVisibility(View.VISIBLE);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.but_know:
			this.dismiss();
			break;

		default:
			break;
		}
		
	}
	
}
