package com.yssj.ui.dialog;

import com.yssj.activity.R;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.utils.CommonUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class NewSignJingxiWanchengDiaolg extends Dialog implements android.view.View.OnClickListener {

	private Button gobuy2, liebiao;
	private ImageView icon_close;
	private Context context;

	public NewSignJingxiWanchengDiaolg(Context context, int style) {
		super(context, style);
		this.context = context;
		

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_sign_common_jingxi);

		gobuy2 = (Button) findViewById(R.id.gobuy2);
		liebiao = (Button) findViewById(R.id.liebiao);
		icon_close = (ImageView) findViewById(R.id.icon_close);

		gobuy2.setOnClickListener(this);
		liebiao.setOnClickListener(this);
		icon_close.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.gobuy2:
			CommonUtils.finishActivity(MainMenuActivity.instances);

			Intent intent2 = new Intent((Activity) context, MainMenuActivity.class);
			intent2.putExtra("toYf", "toYf");
			context.startActivity(intent2);

			dismiss();
			break;
		case R.id.liebiao:
			dismiss();

			break;
		case R.id.icon_close:
			dismiss();
			break;

		default:
			break;
		}
	}

}
