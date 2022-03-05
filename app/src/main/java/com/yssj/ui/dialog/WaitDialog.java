package com.yssj.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;

import java.util.Timer;
import java.util.TimerTask;

public class WaitDialog extends Dialog {
	private LinearLayout root;


	private Context context;
	private TextView id_tv_loadingmsg;

	public WaitDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	public WaitDialog(Context context) {
		super(context);
	}

	@SuppressLint("Range")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dialog_share_wait);
		root = (LinearLayout) findViewById(R.id.root);
		root.setAlpha(180);
		id_tv_loadingmsg = findViewById(R.id.id_tv_loadingmsg);
		id_tv_loadingmsg.setText("请稍后~");


		getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		AnimationDrawable anim = (AnimationDrawable) findViewById(R.id.loadingImageView).getBackground();
		anim.start();
		getWindow().setBackgroundDrawableResource(R.drawable.bg_toast_share);
		getWindow().setDimAmount(0f);
		setCancelable(false);

	}

}
