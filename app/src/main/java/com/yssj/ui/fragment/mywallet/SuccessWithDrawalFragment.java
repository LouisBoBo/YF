package com.yssj.ui.fragment.mywallet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.infos.FundDetailsActivity;
import com.yssj.ui.activity.infos.MyWalletActivity;
import com.yssj.ui.activity.main.HotSaleActivity;
import com.yssj.ui.activity.shopdetails.SubmitMultiShopActivty;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.GetUserGrade;

public class SuccessWithDrawalFragment extends BaseFragment implements OnClickListener {
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private double SucMoney;// 提现成功的金额
	private double money;// 用户要提现的金额
	private int flag;// 提现成功的标识

	private TextView tv_money, tv_money2;
	private Button btn_view_record;
	private String alliance;

	private ImageView img_icon;
	private LinearLayout textL1, textL2, textL3, textCommon;
	private TextView textTv1, textTv2;
	private DecimalFormat df;
	private boolean mACommonFlag = false;// true时代表为A类普通用户

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_mywallet_success_withdrawa, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("提现");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);

		tv_money = (TextView) view.findViewById(R.id.tv_money);
		tv_money2 = (TextView) view.findViewById(R.id.tv_money2);
		btn_view_record = (Button) view.findViewById(R.id.btn_view_record);
		img_icon = (ImageView) view.findViewById(R.id.img_icon);
		textL1 = (LinearLayout) view.findViewById(R.id.tixianL1);
		textL2 = (LinearLayout) view.findViewById(R.id.tixianL2);
		textL3 = (LinearLayout) view.findViewById(R.id.tixianL3);
		textCommon = (LinearLayout) view.findViewById(R.id.tixian_common);
		textTv1 = (TextView) view.findViewById(R.id.tixianTv1);
		textTv2 = (TextView) view.findViewById(R.id.tixianTv2);

		df = new DecimalFormat("0.00");
		return view;
	}

	@Override
	public void initData() {
		Bundle bundle = getArguments();
		if (bundle != null) {
			SucMoney = Double.parseDouble(bundle.getString("SucMoney"));
			alliance = bundle.getString("alliance");
//			mACommonFlag=bundle.getBoolean("mACommonFlag");//要重新获取用户等级，因为中途可能绑定银行卡，等级会改变
			money = Double.parseDouble(bundle.getString("money"));
			flag = bundle.getInt("flag");
		}
		getUserGradle();
//		if (flag == 0) {// 0提现申请成功
//			if (SucMoney == money) {
//				tv_money.setText(df.format(SucMoney) + "元");
//			} else if (SucMoney < money && SucMoney > 0) {
//				textTv1.setText("申请通过：");
//				tv_money.setText(df.format(SucMoney) + "元(夺宝/回佣)");
//
//				textL2.setVisibility(View.VISIBLE);
//				if (mACommonFlag) {// 是A类普通用户
//					String s = "<html><p><font color=\"#ff8dd8\">(普通用户)</p></html>";
//					textCommon.setVisibility(View.VISIBLE);
//					tv_money2.setText(df.format(money - SucMoney) + "元" + Html.fromHtml(s));
//					btn_view_record.setText("去升级会员");
//				} else {
//					textL3.setVisibility(View.VISIBLE);
//					tv_money2.setText(df.format(money - SucMoney) + "元(签到)");
//				}
//
//			}else if(SucMoney==0){//提取的钱为0
//				img_icon.setImageResource(R.drawable.pay_failed);
//				textL1.setVisibility(View.VISIBLE);
//				tv_money.setVisibility(View.GONE);
//				textL2.setVisibility(View.VISIBLE);
//				tv_money2.setVisibility(View.GONE);
//				textTv1.setTextSize(13);
//				textTv2.setTextSize(13);
//				if (mACommonFlag) {// A类普通会员
//					textTv1.setText("你现在是普通会员，任务现金暂时无法提现");
//					textTv2.setText("哦~升级会员即可享受任务现金提现特权喔。");
//					btn_view_record.setText("去升级会员");
//				} else {
//					textTv1.setText("很抱歉，签到奖励仅可用于平台消费，");
//					textTv2.setText("不可提现，您可以继续使用余额消费噢~");
//					btn_view_record.setText("买 买 买");
//				}
//			}
//		} else if (flag == 4) {// b类用户申请提现的金额不足,非夺宝和回佣的金额
//			img_icon.setImageResource(R.drawable.pay_failed);
//			textL1.setVisibility(View.VISIBLE);
//			tv_money.setVisibility(View.GONE);
//			textL2.setVisibility(View.VISIBLE);
//			tv_money2.setVisibility(View.GONE);
//			textTv1.setTextSize(13);
//			textTv2.setTextSize(13);
//			if (mACommonFlag) {// A类普通会员
//				textTv1.setText("你现在是普通会员，任务现金暂时无法提现");
//				textTv2.setText("哦~升级会员即可享受任务现金提现特权喔。");
//				btn_view_record.setText("去升级会员");
//			} else {
//				textTv1.setText("很抱歉，签到奖励仅可用于平台消费，");
//				textTv2.setText("不可提现，您可以继续使用余额消费噢~");
//				btn_view_record.setText("买 买 买");
//			}
//		}

		btn_view_record.setVisibility(View.VISIBLE);
		btn_view_record.setOnClickListener(this);
	}

	
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.img_back:
			getActivity().finish();
			// if(alliance.equals("alliance")){
			// startActivity(new Intent(getActivity(),
			// LeagueBusinessHomeActivity.class));
			// }else{
			
			
			if( null != MyWalletActivity.instans){
				MyWalletActivity.instans.finish();
			}
			
			startActivity(new Intent(getActivity(), MyWalletActivity.class));
			
			
			
			
			
			
			
			// }
			break;
		case R.id.btn_view_record:
			// ToastUtil.showLongText(context, "查看提现记录");
			getActivity().finish();
			if (mACommonFlag && SucMoney < money) {// A类普通用户
													// （跳转到活动商品热卖落地页,若全部提现成功仍跳转到提现记录）
				intent = new Intent(context, HotSaleActivity.class);
				intent.putExtra("id", "6");
				intent.putExtra("title", "热卖");
				context.startActivity(intent);
				((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
			} else if (flag == 4) { // 买 买 买 跳转到购物界面;
				CommonUtils.finishActivity(MainMenuActivity.instances);

				Intent intent2 = new Intent(context, MainMenuActivity.class);
				intent2.putExtra("toYf", "toYf");
				startActivity(intent2);
			} else {// 提现记录
				intent = new Intent(context, FundDetailsActivity.class);
				intent.putExtra("index", 1);
				startActivity(intent);
			}
			break;

		}
	}
	
	
	/**
	 * 获取用户等级(看是否为A类普通用户，控制页面的显示)
	 */
	private void getUserGradle() {
		new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) context,
				R.string.wait) {

			@Override
			protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				return ComModel2.getUserGradle(context);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
				super.onPostExecute(context, result, e);

				if (null == e && result != null) {
				int	grade = (Integer) result.get("grade");
				int vitality=(Integer) result.get("vitality");
				if(grade==1){
					int a=GetUserGrade.getUserGrade(vitality);
					if(a==0){
						mACommonFlag=true;
					}
				}
				if (flag == 0) {// 0提现申请成功
					if (SucMoney == money) {
						tv_money.setText(df.format(SucMoney) + "元");
					} else if (SucMoney < money && SucMoney > 0) {
						textTv1.setText("申请通过：");
						tv_money.setText(df.format(SucMoney) + "元(夺宝/回佣)");

						textL2.setVisibility(View.VISIBLE);
						if (mACommonFlag) {// 是A类普通用户
//							String s = "<html><p><font color=\"#ff8dd8\">(普通用户)</p></html>";
							textCommon.setVisibility(View.VISIBLE);
							tv_money2.setText(df.format(money - SucMoney) + "元(普通会员)" );
							btn_view_record.setText("去升级会员");
						} else {
							textL3.setVisibility(View.VISIBLE);
							tv_money2.setText(df.format(money - SucMoney) + "元(签到)");
						}

					}else if(SucMoney==0){//提取的钱为0
						img_icon.setImageResource(R.drawable.pay_failed);
						textL1.setVisibility(View.VISIBLE);
						tv_money.setVisibility(View.GONE);
						textL2.setVisibility(View.VISIBLE);
						tv_money2.setVisibility(View.GONE);
						textTv1.setTextSize(13);
						textTv2.setTextSize(13);
						if (mACommonFlag) {// A类普通会员
							textTv1.setText("你现在是普通会员，任务现金暂时无法提现");
							textTv2.setText("哦~升级会员即可享受任务现金提现特权喔。");
							btn_view_record.setText("去升级会员");
						} else {
							textTv1.setText("很抱歉，签到奖励仅可用于平台消费，");
							textTv2.setText("不可提现，您可以继续使用余额消费噢~");
							btn_view_record.setText("买 买 买");
						}
					}
				} else if (flag == 4) {// b类用户申请提现的金额不足,非夺宝和回佣的金额
					img_icon.setImageResource(R.drawable.pay_failed);
					textL1.setVisibility(View.VISIBLE);
					tv_money.setVisibility(View.GONE);
					textL2.setVisibility(View.VISIBLE);
					tv_money2.setVisibility(View.GONE);
					textTv1.setTextSize(13);
					textTv2.setTextSize(13);
					if (mACommonFlag) {// A类普通会员
						textTv1.setText("你现在是普通会员，任务现金暂时无法提现");
						textTv2.setText("哦~升级会员即可享受任务现金提现特权喔。");
						btn_view_record.setText("去升级会员");
					} else {
						textTv1.setText("很抱歉，签到奖励仅可用于平台消费，");
						textTv2.setText("不可提现，您可以继续使用余额消费噢~");
						btn_view_record.setText("买 买 买");
					}
				}

				}
			}

		}.execute();
	}

}
