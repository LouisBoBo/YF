package com.yssj.ui.dialog;

import com.yssj.activity.R;
import com.yssj.ui.activity.main.ForceLookActivity;
import com.yssj.utils.SharedPreferencesUtil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LeaveDialog extends Dialog implements android.view.View.OnClickListener {

	
	private Context context;
	private Button btn_left;
	private Button btn_right;
	private TextView tvTitie,tvContent;
	public LeaveDialog(Context context) {
		super(context);
		setCanceledOnTouchOutside(true);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.leave_forcelooke);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		initView();
	}

	private void initView() {
		btn_left = (Button) findViewById(R.id.btn_left);
		btn_right = (Button) findViewById(R.id.btn_right);
		btn_left.setOnClickListener(this);
		btn_right.setOnClickListener(this);
		tvTitie = (TextView) findViewById(R.id.id_tv_titie);
		tvContent = (TextView) findViewById(R.id.id_tv_content);
		
	}
	public void setContentText(String content){
		tvContent.setText(content);
	}
	public void setButtonText(String btnLeft,String btnRigth){
		btn_left.setText(btnLeft);
		btn_right.setText(btnRigth);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			
//			SharedPreferencesUtil.saveBooleanData(context, "close", true);
			
			
			
			this.dismiss();
			break;
			
		case R.id.btn_right:
			this.dismiss();
			break;

		default:
			break;
		}
		
	}
}
