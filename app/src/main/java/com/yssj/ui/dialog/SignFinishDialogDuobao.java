//package com.yssj.ui.dialog;
//
//import com.yssj.activity.R;
//import com.yssj.ui.activity.MainMenuActivity;
//import com.yssj.ui.activity.infos.IntergralDetailActivity;
//import com.yssj.ui.activity.infos.MyCouponsActivity;
//import com.yssj.ui.activity.infos.MyWalletActivity;
//import com.yssj.utils.LogYiFu;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
///****
// * 签到完成弹窗
// * 
// * @author Administrator
// * 
// */
//public class SignFinishDialogDuobao extends Dialog implements OnClickListener {
//	private Context context;
//
//	private Button close;// 知道了按钮
//
//	private ImageView img_now; // 当天任务图片
//	private ImageView img_next; // 下一天任务图片
//
//	private TextView tv_now; // 当天任务名称
//	private TextView tv_next; // 下一天任务名称
//
//	private TextView mingshilingqu;
//
//	private Boolean duobao; // 夺宝-包邮
//
//	private String CanYunumber; // 第盼盼参与号
//
//	private int now_type_id;
//	private int now_type_id_value;
//	private int next_type_id;
//	private int next_type_id_value;
//
//	// 参入号 （默认隐藏）
//	private TextView tv_canyuhao1; // 夺宝参与号上半部分 您的参与号码为：20155200336
//	private TextView tv_canyuhao2; // 夺宝参与号下半部分
//
//	private TextView tv_finish_status; // 参入状态
//
//	private Button bt_look; // 去查看 ---跳到一下界面
//
//	private LinearLayout lv_2button; // 两个按钮（默认隐藏）
//
//	private Button bt_close;// 明天来按钮，--关闭 一个按钮
//
//	public SignFinishDialogDuobao(Context context, int now_type_id,
//			int now_type_id_value, int next_type_id, int next_type_id_value,
//			Boolean duobao, String CanYunumber) {
//		super(context);
//		setCanceledOnTouchOutside(true);
//		this.context = context;
//		this.duobao = duobao;
//		this.CanYunumber = CanYunumber;
//		this.now_type_id = now_type_id;
//		this.now_type_id_value = now_type_id_value;
//		this.next_type_id =next_type_id;
//		this.next_type_id_value =next_type_id_value;
//		
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.dialog_sign_finish_duobao);
//		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//		
////		now_type_id = Integer.valueOf(SharedPreferencesUtil.getStringData(context, "now_type_id", "0"));
////		now_type_id_value = Integer.valueOf(SharedPreferencesUtil.getStringData(context, "now_type_id_value", "0"));
////		next_type_id = Integer.valueOf(SharedPreferencesUtil.getStringData(context, "next_type_id", "0"));
////		next_type_id_value = Integer.valueOf(SharedPreferencesUtil.getStringData(context, "next_type_id_value", "0"));
//
//		
//		
//
//		initView();
//		initData();
//
//	}
//
//	private void initView() {
//		
//		
//
//		bt_close = (Button) findViewById(R.id.bt_close);
//		close = (Button) findViewById(R.id.close);
//
//		img_now = (ImageView) findViewById(R.id.img_now);
//		img_next = (ImageView) findViewById(R.id.img_next);
//
//		tv_now = (TextView) findViewById(R.id.tv_now);
//		tv_next = (TextView) findViewById(R.id.tv_next);
//
//		tv_canyuhao1 = (TextView) findViewById(R.id.tv_canyuhao1);
//		tv_canyuhao2 = (TextView) findViewById(R.id.tv_canyuhao2);
//		mingshilingqu = (TextView) findViewById(R.id.mingshilingqu);
//
//		tv_finish_status = (TextView) findViewById(R.id.tv_finish_status);
//		bt_look = (Button) findViewById(R.id.bt_look);
//		lv_2button = (LinearLayout) findViewById(R.id.lv_2button);
//
//		bt_close.setOnClickListener(this);
//		bt_look.setOnClickListener(this);
//		close.setOnClickListener(this);
//
//	}
//
//	private void initData() {
//		
//	
//		// 填充当天任务
//		initNoWData();
//
//		// 填充下一天任务
//		initNextData();
//
//		// 填充按钮
//		initButton();
//
//	}
//
//	private void initButton() {
//		// 如果现金、余额翻倍
//		if (now_type_id == 5 || now_type_id == 8) {
//			lv_2button.setVisibility(View.VISIBLE);
//			bt_close.setVisibility(View.GONE);
//			bt_look.setText("查看余额");
//
//		} else if (now_type_id == 4) {
//			lv_2button.setVisibility(View.VISIBLE);
//			bt_close.setVisibility(View.GONE);
//			bt_look.setText("查积分");
//		} else if (now_type_id == 3) {
//			lv_2button.setVisibility(View.VISIBLE);
//			bt_close.setVisibility(View.GONE);
//			bt_look.setText("查看卡券");
//		} else if (now_type_id == 7) { // 夺宝包邮
//			// 其他的显示一个按钮
//			lv_2button.setVisibility(View.GONE);
//			bt_close.setVisibility(View.VISIBLE);
//		}
//
//	}
//
//	private void initNoWData() {
//
//		switch (now_type_id) {
//		case 3:  //优惠券
//			
//			
//			tv_canyuhao1.setVisibility(View.GONE);
//			tv_canyuhao2.setVisibility(View.GONE);
//			
//			if(now_type_id_value == 5){
//				img_now.setImageResource(R.drawable.yuan_youhuiquan_finish5);
//			}else{
//				img_now.setImageResource(R.drawable.yuan_youhuiquan_finish10);
//			}
//			
//			
//			tv_finish_status.setText("已领取");
//			tv_now.setText(now_type_id_value + "元优惠券");
//			
//			
//			
//			break;
//		case 4:	//积分
//			tv_canyuhao1.setVisibility(View.GONE);
//			tv_canyuhao2.setVisibility(View.GONE);
//			img_now.setImageResource(R.drawable.jifen_finish);
//			tv_finish_status.setText("已领取");
//			tv_now.setText(now_type_id_value + "积分");
//			
//			
//			break;
//		case 5: //现金
//			tv_canyuhao1.setVisibility(View.GONE);
//			tv_canyuhao2.setVisibility(View.GONE);
//			if (now_type_id_value  == 2){
//				img_now.setImageResource(R.drawable.yuanxianjinfinish2);
//			}else if (now_type_id_value  == 3){
//				img_now.setImageResource(R.drawable.yuanxianjinfinish3);
//			}
//			tv_finish_status.setText("已领取");
//			tv_now.setText(now_type_id_value + "元现金");
//			
//			break;
//		
//		case 7: //夺宝包邮
//
//			tv_finish_status.setVisibility(View.VISIBLE);
//			tv_canyuhao1.setText("您的参与号码为：" + CanYunumber);
//
//			if (duobao) { // 夺宝签到完成
//
//				tv_canyuhao1.setVisibility(View.VISIBLE);
//				tv_canyuhao2.setVisibility(View.VISIBLE);
//
//				if(now_type_id_value == 3){
//					img_now.setImageResource(R.drawable.yuanduobao3);
//					tv_now.setText("3元夺宝");
//				}else if(now_type_id_value ==5){
//					img_now.setImageResource(R.drawable.yuanduobao5);
//					tv_now.setText("5元夺宝");
//				}
//				
//				
//				
//				
//
//			} else {  //包邮完成任务
//
//				tv_canyuhao1.setVisibility(View.GONE);
//				tv_canyuhao2.setVisibility(View.GONE);
//
//				if(now_type_id_value == 3){
//					img_now.setImageResource(R.drawable.yuanbaoyou33);
//					tv_now.setText("3元包邮");
//				}else if(now_type_id_value ==5){
//					img_now.setImageResource(R.drawable.yuanbaoyou55);
//					tv_now.setText("5元包邮");
//				}
//				
//			}
//
//			tv_finish_status.setText("已参与");
//
//			break;
//		case 8: //余额翻倍
//			tv_canyuhao1.setVisibility(View.GONE);
//			tv_canyuhao2.setVisibility(View.GONE);
//			img_now.setImageResource(R.drawable.yuefanbei_finish);
//			tv_now.setText("余额翻倍");
//			tv_finish_status.setText("已领取");
//			
//			break;
//
//		default:
//			break;
//		}
//
//
//	}
//
//	private void initNextData() {
//		
//		LogYiFu.e("LLLid",next_type_id+"");
//		LogYiFu.e("TTTID",next_type_id_value+"");
//
//		
//		
//
//		switch (next_type_id) {
//
//		case -1: // 明日是下月任务
//			img_next.setImageResource(R.drawable.jingxi);
//			tv_next.setText("?");
//			mingshilingqu.setText("明日更多惊喜");
//			break;
//			
//			
//			
//			
//		case 3:  //优惠券
//			tv_next.setText(next_type_id_value + "元优惠券");
//			
//			if(next_type_id_value == 5){
//				img_next.setImageResource(R.drawable.yuan_youhuiquan_finish5);
//			}else{
//				img_next.setImageResource(R.drawable.yuan_youhuiquan_finish10);
//			}
//		
//			
//			
//			
//			break;
//		case 4:	//积分
//			
//			img_next.setImageResource(R.drawable.jifen_finish);
//			tv_next.setText(next_type_id_value + "积分");
//			
//			
//			break;
//		case 5: //现金
//			
//			if (next_type_id_value  == 2){
//				img_next.setImageResource(R.drawable.yuanxianjinfinish2);
//			}else if (next_type_id_value  == 3){
//				img_next.setImageResource(R.drawable.yuanxianjinfinish3);
//			}
//			tv_next.setText(next_type_id_value + "元现金");
//			
//			break;
//		
//		case 7: //夺宝包邮
//			
//			
//			if (next_type_id_value == 3){
//				img_next.setImageResource(R.drawable.yuanduobao3);
//				tv_next.setText("3夺宝");
//			}else{
//				img_next.setImageResource(R.drawable.yuanduobao5);
//				tv_next.setText("5夺宝");
//			}
//			
//			break;
//		case 8: //余额翻倍
//			
//			img_next.setImageResource(R.drawable.yuefanbei_finish);
//			tv_next.setText("余额翻倍");
//			
//			break;
//			
//			
//			
//			
//		default:
//			break;
//		}
//
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.bt_close:
//			
//			
//			Intent intent2 = new Intent((Activity) context, MainMenuActivity.class);
//			intent2.putExtra("toYf", "toYf");
//			context.startActivity(intent2);
//			
//			this.dismiss();
//
//			break;
//		case R.id.close:
//			this.dismiss();
//			break;
//		case R.id.bt_look:
//			// 查余额
//			if (now_type_id == 5 || now_type_id == 8) {
//				Intent intent = new Intent(context, MyWalletActivity.class);
//				context.startActivity(intent);
//				dismiss();
//			} else if (now_type_id == 4) {
//				// 查积分
//				Intent intent = new Intent(context,
//						IntergralDetailActivity.class);
//				intent.putExtra("page", 0);
//				context.startActivity(intent);
//				dismiss();
//
//			} else if (now_type_id == 3) {
//				// 查卡券
//				Intent intent = new Intent(context, MyCouponsActivity.class);
//				context.startActivity(intent);
//				dismiss();
//			} else {
//
//			}
//			break;
//		default:
//			break;
//		}
//
//	}
//
//}