package com.yssj.ui.pager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.activity.WithdrawalLimitActivity;
import com.yssj.ui.activity.infos.ClothesBeanDetailActivity;
import com.yssj.ui.activity.infos.MyCouponsActivity;
import com.yssj.ui.activity.infos.MyWalletActivity;
import com.yssj.ui.activity.infos.StatusInfoActivity;
import com.yssj.ui.base.BasePager;
import com.yssj.utils.DateUtil;

//x消息中心--系统
public class MyMessageSystemPager extends BasePager implements OnClickListener {
	private HashMap<String, Object> map;

	private TextView tv_dingdan_time, tv_haoyou_time, tv_zhanghu_time, tv_yidou_time,tv_coupon_time, tv_dingdang_neirong,
			tv_haoyou_neirong, tv_zhanghu_neirong, tv_yidou_neirong,tv_coupon_content;
	private View orderView, haoyouView, zhanghuView, yidouView,couponView;
	private Context mContext;

	public MyMessageSystemPager(Context context) {
		super(context);
		mContext = context;
	}

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.pager_my_center_messy_system, null);
		tv_dingdang_neirong = (TextView) view.findViewById(R.id.tv_dingdangneirong);
		tv_haoyou_neirong = (TextView) view.findViewById(R.id.tv_haoyouneirong);
		tv_zhanghu_neirong = (TextView) view.findViewById(R.id.tv_zhanghuneirong);
		tv_yidou_neirong = (TextView) view.findViewById(R.id.tv_yidouneirong);
		tv_coupon_content=(TextView)view.findViewById(R.id.tv_coupon_content);

		tv_dingdan_time = (TextView) view.findViewById(R.id.tv_dingdan_time);
		tv_haoyou_time = (TextView) view.findViewById(R.id.tv_haoyou_time);
		tv_zhanghu_time = (TextView) view.findViewById(R.id.tv_zhanghu_time);
		tv_yidou_time = (TextView) view.findViewById(R.id.tv_yidou_time);
		tv_coupon_time=(TextView) view.findViewById(R.id.tv_coupon_time);
		orderView = view.findViewById(R.id.order_view);
		haoyouView = view.findViewById(R.id.haoyou_view);
		zhanghuView = view.findViewById(R.id.zhanghu_view);
		yidouView = view.findViewById(R.id.yidou_view);
		couponView=view.findViewById(R.id.coupon_view);
		orderView.setOnClickListener(this);
		haoyouView.setOnClickListener(this);
		zhanghuView.setOnClickListener(this);
		yidouView.setOnClickListener(this);
		couponView.setOnClickListener(this);

		return view;

	}

	@Override
	public void initData() {
		new SAsyncTask<Integer, Void, HashMap<String, String>>((FragmentActivity) context, null, 0) {

			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, Integer... params)
					throws Exception {
				// TODO Auto-generated method stub
				return ModQingfeng.getMessageCenterSystem(context);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result);

				if (e != null) {
					return;
				}
				String dingdan = result.get("1");
				String friend = result.get("2");
				String zhanghu = result.get("3");
				String yidou = result.get("4");
				String coupon=result.get("5");

				// 分别填充
				long now = System.currentTimeMillis();
				// 订单
				if (dingdan.length() != 0) {
					// 填充描述内容
					String contet = dingdan.split("\\^")[0];
					tv_dingdang_neirong.setText(contet);
					long time = Long.parseLong(dingdan.split("\\^")[1]);

					if ((now - time) > 24 * 60 * 60 * 1000) {
						// 24小时之前
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String riqi = sdf.format(time);
						tv_dingdan_time.setText(
								riqi.split("-")[0] + "年" + riqi.split("-")[1] + "月" + riqi.split("-")[2] + "日");
					} else {
						// 当天
						tv_dingdan_time.setText(DateUtil.formatTimeDangTian(time));

					}

				}
				// 好友消息
				if (friend.length() != 0) {
					// 填充描述内容
					String contet = friend.split("\\^")[0];
					tv_haoyou_neirong.setText(contet);
					long time = Long.parseLong(friend.split("\\^")[1]);

					if ((now - time) > 24 * 60 * 60 * 1000) {
						// 24小时之前
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String riqi = sdf.format(time);
						tv_haoyou_time.setText(
								riqi.split("-")[0] + "年" + riqi.split("-")[1] + "月" + riqi.split("-")[2] + "日");
					} else {
						// 当天
						tv_haoyou_time.setText(DateUtil.formatTimeDangTian(time));
					}

				}
				// 账户消息
				if (zhanghu.length() != 0) {
					// 填充描述内容
					String contet = zhanghu.split("\\^")[0];
					tv_zhanghu_neirong.setText(contet);
					long time = Long.parseLong(zhanghu.split("\\^")[1]);

					if ((now - time) > 24 * 60 * 60 * 1000) {
						// 24小时之前
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String riqi = sdf.format(time);
						tv_zhanghu_time.setText(
								riqi.split("-")[0] + "年" + riqi.split("-")[1] + "月" + riqi.split("-")[2] + "日");
					} else {
						// 当天
						tv_zhanghu_time.setText(DateUtil.formatTimeDangTian(time));
					}

				}
				// 衣豆消息
				if (yidou.length() != 0) {
					// 填充描述内容
					String contet = yidou.split("\\^")[0];
					tv_yidou_neirong.setText(contet);
					long time = Long.parseLong(yidou.split("\\^")[1]);

					if ((now - time) > 24 * 60 * 60 * 1000) {
						// 24小时之前
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String riqi = sdf.format(time);
						tv_yidou_time.setText(
								riqi.split("-")[0] + "年" + riqi.split("-")[1] + "月" + riqi.split("-")[2] + "日");
					} else {
						// 当天
						tv_yidou_time.setText(DateUtil.formatTimeDangTian(time));
					}

				}
				
				// 优惠券消息
//				if (coupon.length() != 0) {
//					// 填充描述内容
//					String contet = coupon.split("\\^")[0];
//					tv_coupon_content.setText(contet);
//					long time = Long.parseLong(coupon.split("\\^")[1]);
//
//					if ((now - time) > 24 * 60 * 60 * 1000) {
//						// 24小时之前
//						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//						String riqi = sdf.format(time);
//						tv_coupon_time.setText(
//								riqi.split("-")[0] + "年" + riqi.split("-")[1] + "月" + riqi.split("-")[2] + "日");
//					} else {
//						// 当天
//						tv_coupon_time.setText(DateUtil.formatTimeDangTian(time));
//					}
//
//				}

			}

			@Override
			protected boolean isHandleException() {
				return true;
			};

		}.execute();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.order_view:
			Intent intentOrder = new Intent(mContext, StatusInfoActivity.class);
			intentOrder.putExtra("index", 0);
			mContext.startActivity(intentOrder);
			((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
			break;
		case R.id.haoyou_view:

			break;
		case R.id.zhanghu_view:
			Intent intentZhanghu = new Intent(mContext, MyWalletActivity.class);
			mContext.startActivity(intentZhanghu);
			((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
			break;
		case R.id.yidou_view:
			final Intent intentYidou = new Intent(context, ClothesBeanDetailActivity.class);
			// yidou_intent.putExtra("pearsCount", usedYidou+"");
			// yidou_intent.putExtra("freezeCount", unUsedYidou+"");
			if (YJApplication.instance.isLoginSucess()) {
				new SAsyncTask<Void, Void, String[]>((FragmentActivity) mContext, R.string.wait) {

					@Override
					protected String[] doInBackground(FragmentActivity mContext, Void... params) throws Exception {
						return ComModel2.myWalletInfo(mContext);
					}

					@Override
					protected boolean isHandleException() {
						return true;
					}

					@Override
					protected void onPostExecute(FragmentActivity mContext, String[] result, Exception e) {
						super.onPostExecute(mContext, result, e);
						if (null == e) {
							if (result != null && result.length > 0) {
								intentYidou.putExtra("pearsCount", result[4]);
								intentYidou.putExtra("freezeCount", result[5]);
								mContext.startActivity(intentYidou);
								((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in,
										R.anim.slide_match);
							}
						}
					}

				}.execute();
			}
			break;
//		case R.id.coupon_view:
//			Intent	intent = new Intent(mContext, MyCouponsActivity.class);
//			mContext.startActivity(intent);
//			((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//			break;
		default:
			break;
		}

	}

}
