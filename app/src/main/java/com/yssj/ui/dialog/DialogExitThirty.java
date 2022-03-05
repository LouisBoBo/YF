package com.yssj.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.yssj.activity.R;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.infos.MyCouponsActivity;
import com.yssj.utils.SharedPreferencesUtil;

/****
 * 退出登录30元提示
 * 
 * 
 * @author Administrator
 * 
 */
public class DialogExitThirty extends Dialog implements OnClickListener {
	private ImageView finish, ok;
	private Context context;

	public DialogExitThirty(Context context, int style) {
		super(context, style);
		setCanceledOnTouchOutside(true);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_dialog30);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);

		finish = (ImageView) findViewById(R.id.finish);
		ok = (ImageView) findViewById(R.id.ok);
		finish.setOnClickListener(this);
		ok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.finish:

			dismiss();

			break;

		case R.id.ok: // 跳到优惠券

			Intent intent = new Intent(context, MyCouponsActivity.class);
			context.startActivity(intent);
			dismiss();
			break;

		default:
			break;
		}
	}

}