//package com.yssj.ui.dialog;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import com.yssj.activity.R;
//import com.yssj.ui.activity.MainMenuActivity;
//import com.yssj.ui.activity.infos.MyMemberRankActivity;
//import com.yssj.ui.activity.main.HotSaleActivity;
//import com.yssj.utils.GetUserGrade;
//import com.yssj.utils.SharedPreferencesUtil;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.text.Spannable;
//import android.text.SpannableString;
//import android.text.style.ForegroundColorSpan;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
////会员活力只不够提示
//public class MemberPowerNoEnoughDialog extends Dialog implements android.view.View.OnClickListener {
//
//	private Button gobuy2, liebiao;
//	private ImageView icon_close, icon_vip;
//	private Context context;
//	private TextView tv3, tv1, tv4;
//	private String jumpFrom; // 从哪里跳过来的
//	private String powerCount; // 会员活力值
//	private String grade; // 会员等级
//
//	public MemberPowerNoEnoughDialog(Context context, int style, String jumpFrom, String powerCount, String grade) {
//		super(context, style);
//		this.context = context;
//		this.grade = grade;
//		this.jumpFrom = jumpFrom;
//		this.powerCount = powerCount;
//
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.dialog_no_power);
//
//		gobuy2 = (Button) findViewById(R.id.gobuy2);
//		icon_close = (ImageView) findViewById(R.id.icon_close);
//		icon_vip = (ImageView) findViewById(R.id.icon_vip);
//		tv3 = (TextView) findViewById(R.id.tv3);
//		tv4 = (TextView) findViewById(R.id.tv4);
//		tv1 = (TextView) findViewById(R.id.tv1);
//		gobuy2.setOnClickListener(this);
//		icon_close.setOnClickListener(this);
//
//		Date dt = new Date();
//		SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
//		String riqi = matter1.format(dt);
//
//		if (jumpFrom.equals("ceshi") || jumpFrom.equals("power<20")) {
//			tv1.setText("活力值提示");
//			tv4.setVisibility(View.VISIBLE);
//			icon_vip.setVisibility(View.GONE);
//			String ss = "你的活力值不足20点，为了不影响你参与赚钱任务（1个必做任务消耗1点活力值），请及时补充活力值喔~";
//			SpannableString tss = new SpannableString(ss);
//			tss.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), ss.length() - 26, ss.length(),
//					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//			tv3.setText(tss);
//			gobuy2.setText("补充活力值");
//			SharedPreferencesUtil.saveStringData(context, "xiaoyu20", riqi);
//
//		}
//
//		if (jumpFrom.equals("power=0")) {
//			icon_vip.setVisibility(View.GONE);
//			tv1.setText("活力值提示");
//			tv4.setVisibility(View.VISIBLE);
//			String ss = "当前活力值为0点，不可参与赚钱任务（1个必做任务消耗1点活力值），请立即补充活力值喔~";
//			SpannableString tss = new SpannableString(ss);
//			tss.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), ss.length() - 26, ss.length(),
//					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//			tv3.setText(tss);
//			gobuy2.setText("补充活力值");
//			SharedPreferencesUtil.saveStringData(context, "huolizhishi0", riqi);
//
//		}
//
//		if (jumpFrom.equals("signMemberTishi")) {
//			icon_vip.setVisibility(View.VISIBLE);
//			tv1.setText("恭喜你");
//			tv4.setVisibility(View.GONE);
//			gobuy2.setText("查看我的会员");
//			SharedPreferencesUtil.saveBooleanData(context, "isshowhuodehuiyuan", true);
//			int intGrade = 0;
//
//			// try {
//			// intGrade = Integer.parseInt(grade);
//			// } catch (Exception e) {
//			// e.printStackTrace();
//			// }
//
//			String s2 = "，并获得" + powerCount + "点活力值，快来看看都有什么会员权益吧~";
//
//			// 获取用户会员等级
//			int userClass = GetUserGrade.getUserGrade(Integer.parseInt(powerCount));
//
//			switch (userClass) { // 0普通 1青铜 2白银 3黄金
//			// case 0:
//			// s1 = "你免费获得普通会员特权";
//			// icon_vip.setImageResource(R.drawable.icon_vip_goldtishi);
//			// break;
//			case 1:
//
//				tv3.setText("你免费获得青铜会员特权" + s2);
//				icon_vip.setImageResource(R.drawable.icon_vip_qingtongtishi);
//				break;
//			case 2:
//				tv3.setText("你免费获得白银会员特权" + s2);
//				icon_vip.setImageResource(R.drawable.icon_vip_baijingtishi);
//				break;
//			case 3:
//				tv3.setText("你免费获得黄金会员特权" + s2);
//				icon_vip.setImageResource(R.drawable.icon_vip_goldtishi);
//				break;
//
//			default:
//				break;
//
//			}
//
//		}
//
//	}
//
//	@Override
//	public void onClick(View v) {
//
//		switch (v.getId()) {
//
//		case R.id.gobuy2:
//
//			if (jumpFrom.equals("signMemberTishi")) {
//				Intent intent = new Intent(context, MyMemberRankActivity.class);
//				context.startActivity(intent);
//
//			} else {
//				Intent intent = new Intent(context, HotSaleActivity.class);
//				intent.putExtra("id", "6");
//				intent.putExtra("title", "热卖");
//				context.startActivity(intent);
//
//			}
//			((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//			dismiss();
//			break;
//
//		case R.id.icon_close:
//			dismiss();
//			break;
//
//		default:
//			break;
//		}
//	}
//
//}
