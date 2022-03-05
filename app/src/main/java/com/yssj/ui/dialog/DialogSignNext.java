//package com.yssj.ui.dialog;
//
//import java.util.Random;
//
//import com.yssj.YJApplication;
//import com.yssj.activity.R;
//import com.yssj.utils.LogYiFu;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//public class DialogSignNext extends Dialog implements OnClickListener {
//
//	private ImageView image, icon_close;
//	private TextView image_text;
//
//	private int type_id_value;
//	private int type;
//	private int type_id;
//
//	public DialogSignNext(Context context, int style, int type,
//			int type_id_value, int type_id) {
//		super(context, style);
//		setCanceledOnTouchOutside(true);
//		this.type_id_value = type_id_value;
//		this.type = type;
//		this.type_id = type_id;
//
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.tomorrow_sign_dialog);
//		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//		
//		
//		 LogYiFu.e("now_type_id_value", type_id_value
//				 + "");
//				 LogYiFu.e("now_type_id", type_id + "");
//		
//		image = (ImageView) findViewById(R.id.image);
//		image_text = (TextView) findViewById(R.id.image_text);
//		icon_close = (ImageView) findViewById(R.id.icon_close);
//		icon_close.setOnClickListener(this);
//		
//		
//		if (type_id == 3) { // 优惠券
//
//			if (type_id_value == 5) {
//				image.setImageResource(R.drawable.yuan_youhuiquan_finish5);
//				image_text.setText("5元优惠券");
//			} else {
//				image.setImageResource(R.drawable.yuan_youhuiquan_finish10);
//				image_text.setText("10元优惠券");
//			}
//		
//
//		} else if (type_id == 4) { // 奖励是积分
//			image.setImageResource(R.drawable.jifen_finish);
//			image_text.setText(type_id_value+"积分");
//			
//		} else if (type_id == 5) { // 奖励是现金
//
//			if (type_id_value == 2) {
//				image.setImageResource(R.drawable.yuanxianjinfinish2);
//				image_text.setText("2元现金");
//			} else if (type_id_value == 3) {
//				image.setImageResource(R.drawable.yuanxianjinfinish3);
//				image_text.setText("3元现金");
//			}
//
//			
//
//		} else if (type_id == 8) { // 奖励是余额翻倍
//
//			image.setImageResource(R.drawable.yuefanbei_finish);
//			image_text.setText("余额翻倍");
//
//			
//
//		}
//		
//		
//		
//		
//		
//		
////		initData();
//
//	}
//
////	private void initData() {
////
////		switch (type) {
////
////		case 1: // 分享特卖
////
////			break;
////
////		case 2: // 分享正价
////
////			if (type_id == 3) { // 优惠券
////
////				if (type_id_value == 5) {
////					image.setImageResource(R.drawable.yuan_youhuiquan_finish5);
////					image_text.setText("5元优惠券");
////				} else {
////					image.setImageResource(R.drawable.yuan_youhuiquan_finish10);
////					image_text.setText("10元优惠券");
////				}
////				break;
////
////			} else if (type_id == 4) { // 奖励是积分
////				image.setImageResource(R.drawable.jifen_finish);
////				image_text.setText(type_id_value+"积分");
////				break;
////			} else if (type_id == 5) { // 奖励是现金
////
////				if (type_id_value == 2) {
////					image.setImageResource(R.drawable.yuanxianjinfinish2);
////					image_text.setText("2元现金");
////				} else if (type_id_value == 3) {
////					image.setImageResource(R.drawable.yuanxianjinfinish3);
////					image_text.setText("3元现金");
////				}
////
////				break;
////
////			} else if (type_id == 8) { // 奖励是余额翻倍
////
////				image.setImageResource(R.drawable.yuefanbei_finish);
////				image_text.setText("余额翻倍");
////
////				break;
////
////			}
////
////			break;
////		case 3: // 分享活动图
////
////			break;
////		case 4: // 夺宝包邮
////
////			break;
////		case 5: // 强制浏览
////			
////			
////			if (type_id == 3) { // 优惠券
////
////				if (type_id_value == 5) {
////					image.setImageResource(R.drawable.yuan_youhuiquan_finish5);
////					image_text.setText("5元优惠券");
////				} else {
////					image.setImageResource(R.drawable.yuan_youhuiquan_finish10);
////					image_text.setText("10元优惠券");
////				}
////				break;
////
////			} else if (type_id == 4) { // 奖励是积分
////				image.setImageResource(R.drawable.jifen_finish);
////				image_text.setText(type_id_value+"积分");
////				break;
////			} else if (type_id == 5) { // 奖励是现金
////
////				if (type_id_value == 2) {
////					image.setImageResource(R.drawable.yuanxianjinfinish2);
////					image_text.setText("2元现金");
////				} else if (type_id_value == 3) {
////					image.setImageResource(R.drawable.yuanxianjinfinish3);
////					image_text.setText("3元现金");
////				}
////
////				break;
////
////			} else if (type_id == 8) { // 奖励是余额翻倍
////
////				image.setImageResource(R.drawable.yuefanbei_finish);
////				image_text.setText("余额翻倍");
////
////				break;
////
////			}
////
////			
////			
////			
////			
////			
////			
////			
////			
////
//////			if (type_id_value == 2) {
//////				image.setImageResource(R.drawable.yuanxianjinfinish2);
//////				image_text.setText("2元现金");
//////			} else if (type_id_value == 3) {
//////				image.setImageResource(R.drawable.yuanxianjinfinish3);
//////				image_text.setText("3元现金");
//////			}
////
////			break;
////			
////			
////		case 6: // 分享搭配
////
////			if (type_id == 3) { // 优惠券
////
////				if (type_id_value == 5) {
////					image.setImageResource(R.drawable.yuan_youhuiquan_finish5);
////					image_text.setText("5元优惠券");
////				} else {
////					image.setImageResource(R.drawable.yuan_youhuiquan_finish10);
////					image_text.setText("10元优惠券");
////				}
////				break;
////
////			} else if (type_id == 4) { // 奖励是积分
////				image.setImageResource(R.drawable.jifen_finish);
////				image_text.setText(type_id_value+"积分");
////				break;
////			} else if (type_id == 5) { // 奖励是现金
////
////				if (type_id_value == 2) {
////					image.setImageResource(R.drawable.yuanxianjinfinish2);
////					image_text.setText("2元现金");
////				} else if (type_id_value == 3) {
////					image.setImageResource(R.drawable.yuanxianjinfinish3);
////					image_text.setText("3元现金");
////				}
////
////				break;
////
////			} else if (type_id == 8) { // 奖励是余额翻倍
////
////				image.setImageResource(R.drawable.yuefanbei_finish);
////				image_text.setText("余额翻倍");
////
////				break;
////
////			}
////
////			break;
////			
////
////		default:
////			break;
////		}
////
////	}
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
//		default:
//			break;
//		}
//
//	}
//
//}
