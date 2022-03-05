//package com.yssj.ui.activity.infos;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.text.Html;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.ui.base.BasicActivity;
//
//public class SubmitCodeStatusActivity extends BasicActivity{
//
//	private boolean isSucess = false;
//	
//	private ImageView img_status;
//	private LinearLayout root;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		aBar.hide();
//		setContentView(R.layout.submit_code_status_activity);
//		initView();
//	}
//	
//	private TextView tv_status;
//	private TextView tv_result;
//	private void initView(){
//		findViewById(R.id.img_back).setOnClickListener(this);
//		findViewById(R.id.btn_close).setOnClickListener(this);
//		root = (LinearLayout) findViewById(R.id.root);
//		root.setBackgroundColor(Color.WHITE);
//		img_status = (ImageView) findViewById(R.id.img_status);
//		tv_result = (TextView) findViewById(R.id.tv_result);
//		tv_status = (TextView) findViewById(R.id.tv_status);
//		isSucess = getIntent().getBooleanExtra("isSuccess", false);
//		if(isSucess){
//			img_status.setImageResource(R.drawable.submit_code_status_sucess);
//			tv_status.setTextColor(getResources().getColor(R.color.common_red));
//			tv_status.setText("兑换成功");
//			tv_result.setText(Html.fromHtml(getString(R.string.tv_invite_code)));
//		}else{
//			img_status.setImageResource(R.drawable.submit_code_status_failed);
//			tv_status.setTextColor(getResources().getColor(R.color.all2));
//			tv_status.setText("兑换失败");
//			tv_result.setText("请确认邀请码是否正确");
//		}
//		
//	}
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.img_back:
//		case R.id.btn_close:
//			finish();
//			break;
//		default:
//			break;
//		}
//		super.onClick(v);
//	}
//}
