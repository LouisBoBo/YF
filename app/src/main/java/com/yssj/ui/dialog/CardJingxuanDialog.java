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

public class CardJingxuanDialog extends Dialog implements android.view.View.OnClickListener {
	
	private Context context;
	private ImageView iv;
	private View rootView;
	public CardJingxuanDialog(Context context) {
		super(context);
		setCanceledOnTouchOutside(false);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.card_jingxuan_dialog);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		initView();
	}

	private void initView() {
		iv = (ImageView) findViewById(R.id.but_know);
		iv.setOnClickListener(this);
		rootView = findViewById(R.id.jingxuan_tip_rootview);
		rootView.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.but_know:
		case R.id.jingxuan_tip_rootview:
			this.dismiss();
			break;

		default:
			break;
		}
		
	}
	
}
