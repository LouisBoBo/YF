//package com.yssj.ui.dialog;
//
//import java.util.Random;
//
//import com.yssj.activity.R;
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//public class DialogSignNext2Duobao extends Dialog implements OnClickListener {
//
//	private ImageView image_duobao,icon_close,image_baoyou;
//	private TextView image_baoyou_text,image_duobao_text;
//	
//	private int type_id_value ;
//
//	
//
//	public DialogSignNext2Duobao(Context context,int style ,int type_id_value) {
//		super(context,style);
//		setCanceledOnTouchOutside(true);
//		this.type_id_value = type_id_value;
//		
//	}
//		
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.tomorrow_sign_duobao_dialog);
//		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//		
//		image_duobao = (ImageView) findViewById(R.id.image_duobao);
//		image_baoyou = (ImageView) findViewById(R.id.image_baoyou);
//		
//		image_baoyou_text = (TextView) findViewById(R.id.image_baoyou_text);
//		image_duobao_text = (TextView) findViewById(R.id.image_duobao_text);
//		icon_close = (ImageView) findViewById(R.id.icon_close);
//		icon_close.setOnClickListener(this);
//		initData();
//		
//	}
//
//
//
//
//	private void initData() {
//
//		
//
//
//		switch (type_id_value) {
//		case 3: // 3元包邮夺宝
//			
//			image_duobao.setImageResource(R.drawable.yuanduobao3);
//			image_baoyou.setImageResource(R.drawable.yuanbaoyou33);
//			image_baoyou_text.setText("3元包邮");
//			image_duobao_text.setText("3元夺宝");
//
//
//			break;
//
//		
//
//		case 5: //5元包邮夺宝
//			image_duobao.setImageResource(R.drawable.yuanduobao5);    
//			image_baoyou.setImageResource(R.drawable.yuanbaoyou55);
//			image_baoyou_text.setText("5元包邮");
//			image_duobao_text.setText("5元夺宝");
//			break;
//		
//		
//		}
//
//	
//	
//	}
//
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.icon_close: // 关闭
//
//			this.dismiss();
//
//			break;
//
//			
//		default:
//			break;
//		}
//
//	}
//
//}
