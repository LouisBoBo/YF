package com.yssj.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.QueryPhoneInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.main.CrazyShopListActivity;
import com.yssj.ui.activity.main.HotSaleActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXcheckUtil;
import com.yssj.utils.YCache;

import java.util.HashMap;

/***
 * 你时尚我买单 买单资格说明---- //疯狂星期一和免单共用
 */
public class BuyFreeActivity extends BasicActivity implements OnClickListener {
	private TextView buy_free_btn, mQualifications, mUsedTv, mUsedOrder, mExpTv1, mExpTv2_50, mExpTv2_100, mExpTv3_50,
			mExpTv3_100, mExpTv4, mExpTv5, mExpTv6;
	private View img_back, mQualificationsUsed;
	private ImageView buy_free_image;
	private int cashBack;// 0：免单50 1： 免单50或者100

	private boolean isCrazyMon; // 疯狂星期一和免单公用
	private String whereMon = "";

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		context = this;
		Intent intent = getIntent();
		if (intent != null) {
			cashBack = intent.getIntExtra("cashBack", 0);
			isCrazyMon = intent.getBooleanExtra("isCrazyMon", false);
		}

		if (isCrazyMon) {
			setContentView(R.layout.activity_crazymon);
			try {
				whereMon = intent.getStringExtra("whereMon");
			} catch (Exception e) {
			}

		} else {
			setContentView(R.layout.activity_buy_free);
		}

		initView();
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		buy_free_btn = (TextView) findViewById(R.id.buy_free_btn);
		img_back = findViewById(R.id.img_back);
		buy_free_btn.setOnClickListener(this);
		img_back.setOnClickListener(this);

		mQualifications = (TextView) findViewById(R.id.buy_free_qualifications);// 剩余一次资格
		mQualifications.setVisibility(View.GONE);
		mQualifications.getPaint().setFakeBoldText(true);// 设置中文字体加粗
		mQualificationsUsed = findViewById(R.id.buy_free_qualifications_used);
		mQualificationsUsed.setVisibility(View.VISIBLE);
		mUsedOrder = (TextView) findViewById(R.id.buy_free_used_order_tv);// 已使用
																			// 订单和价格
		mUsedOrder.setVisibility(View.GONE);
		mUsedTv = (TextView) findViewById(R.id.buy_free_qualifications_used_tv);// 资格已使用
		mUsedTv.getPaint().setFakeBoldText(true);// 设置中文字体加粗
		mUsedTv.setVisibility(View.GONE);

		mExpTv1 = (TextView) findViewById(R.id.bu_free_exp1);
		mExpTv2_50 = (TextView) findViewById(R.id.bu_free_exp2_50);
		mExpTv2_100 = (TextView) findViewById(R.id.bu_free_exp2_100);
		mExpTv3_50 = (TextView) findViewById(R.id.bu_free_exp3_50);
		mExpTv3_100 = (TextView) findViewById(R.id.bu_free_exp3_100);
		mExpTv4 = (TextView) findViewById(R.id.bu_free_exp4);
		mExpTv5 = (TextView) findViewById(R.id.bu_free_exp5);
		mExpTv6 = (TextView) findViewById(R.id.bu_free_exp6);
		buy_free_image = (ImageView) findViewById(R.id.buy_free_image);
		changeTextSpan(mExpTv1, 0);
		changeTextSpan(mExpTv2_100, 2);
		changeTextSpan(mExpTv3_100, 0);
		if (isCrazyMon) {
			changeTextSpan(mExpTv2_50, 101);
			changeTextSpan(mExpTv3_50, 102);
		} else {
			changeTextSpan(mExpTv2_50, 1);
			changeTextSpan(mExpTv3_50, 0);
		}

		changeTextSpan(mExpTv4, 0);
		changeTextSpan(mExpTv5, 0);
		changeTextSpan(mExpTv6, 0);
		if (cashBack == 1) {
			mExpTv2_50.setVisibility(View.GONE);
			mExpTv2_100.setVisibility(View.VISIBLE);
			mExpTv3_50.setVisibility(View.GONE);
			mExpTv3_100.setVisibility(View.VISIBLE);
			buy_free_image.setImageResource(R.drawable.tab_back_cash_time_100);
		} else {
			mExpTv2_50.setVisibility(View.VISIBLE);
			mExpTv2_100.setVisibility(View.GONE);
			mExpTv3_50.setVisibility(View.VISIBLE);
			mExpTv3_100.setVisibility(View.GONE);
			buy_free_image.setImageResource(R.drawable.tab_back_cash_time_50);
		}

		if (isCrazyMon) {
			mQualifications.setVisibility(View.GONE);
			mExpTv2_100.setVisibility(View.VISIBLE);
			mExpTv3_100.setVisibility(View.VISIBLE);
		} else {
			initData();
		}

	}

	/**
	 * 获取数据
	 * 
	 */
	private void initData() {
		new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) context, 0) {
			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				return ComModel2.getFreeUse(context);
			}

			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null && result != null) {
					int num = Integer.parseInt(result.get("num"));
					if (num > 0) {
						mQualifications.setVisibility(View.VISIBLE);
						mQualificationsUsed.setVisibility(View.GONE);
						mQualifications.setText("剩余" + num + "次资格");
					} else if (num == 0) {
						mQualifications.setVisibility(View.GONE);
						mQualificationsUsed.setVisibility(View.VISIBLE);
						mUsedOrder.setVisibility(View.VISIBLE);
						mUsedTv.setVisibility(View.VISIBLE);
						mUsedOrder.setText("订单:" + result.get("order_code") + "    ¥"
								+ new java.text.DecimalFormat("#0.00").format(Double.parseDouble(result.get("op")))
								+ "元");
					}
				}
			}
		}.execute();

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.img_back:
			onBackPressed();
			break;
		case R.id.buy_free_btn:// 去免单

			if (isCrazyMon) {

//				if (whereMon != null) {
					// 疯狂星期一落地页











					if (!YJApplication.instance.isLoginSucess()) {

						if (LoginActivity.instances != null) {
							LoginActivity.instances.finish();
						}

						Intent intentd = new Intent(BuyFreeActivity.this, LoginActivity.class);
						intentd.putExtra("login_register", "login");
						((FragmentActivity) BuyFreeActivity.this).startActivity(intentd);
						((FragmentActivity) BuyFreeActivity.this).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
						return;
					}


					//检查绑定手机--------------------------------------------------------------------------------------------------------------------------------------
					new SAsyncTask<Void, Void, QueryPhoneInfo>((FragmentActivity) BuyFreeActivity.this) {

						@Override
						protected QueryPhoneInfo doInBackground(FragmentActivity context,
																Void... params) throws Exception {

							return ComModel.bindPhone(context);
						}

						@Override
						protected boolean isHandleException() {
							return true;
						}

						@Override
						protected void onPostExecute(FragmentActivity context,
													 QueryPhoneInfo result, Exception e) {
							super.onPostExecute(context, result, e);
							if (null == e) {
								if (result != null && "1".equals(result.getStatus())) {
									Boolean phoneboolll = result.isBool();
									if (phoneboolll == false) {// 未绑定手机
										customDialog(BuyFreeActivity.this);


										return;
									} else {
//检查微信授权-----------------------------------------------------------------------------------------------------------------
										String uuid = YCache.getCacheUserSafe(BuyFreeActivity.this).getUuid();

										if (null == uuid || uuid.equals("null")
												|| uuid.equals("")) {
											//没有授权微信-----去授权

											boolean mWxInstallFlag = false;

											try {
												// // 是否安装了微信

												if (WXcheckUtil.isWeChatAppInstalled(BuyFreeActivity.this)) {
													mWxInstallFlag = true;
												} else {
													mWxInstallFlag = false;
												}
											} catch (Exception ee) {
											}

											if (!mWxInstallFlag) {

												ToastUtil.showShortText(BuyFreeActivity.this, "您没有安装微信，需要微信授权~");

											} else {

												ToastUtil.showShortText(BuyFreeActivity.this, "需要微信授权哦~");



//												UMWXHandler wxHandler = new UMWXHandler(BuyFreeActivity.this, WxPayUtil.APP_ID,
//														WxPayUtil.APP_SECRET);
//												wxHandler.addToSocialSDK();
//
//
//												//授权微信
//												final UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);
//
//												mController.doOauthVerify(context, SHARE_MEDIA.WEIXIN, new SocializeListeners.UMAuthListener() {
//
//													@Override
//													public void onStart(SHARE_MEDIA platform) {
//													}
//
//													@Override
//													public void onError(SocializeException e, SHARE_MEDIA platform) {
//														ToastUtil.showShortText(BuyFreeActivity.this, "微信授权失败");
//
//													}
//
//													@Override
//													public void onComplete(Bundle value, SHARE_MEDIA platform) {
//
//														final String openid = value.getString("openid");
//
//
//														mController.getPlatformInfo(BuyFreeActivity.this, platform, new SocializeListeners.UMDataListener() {
//
//															@Override
//															public void onStart() {
//
//															}
//
//															@Override
//															public void onComplete(int status, Map<String, Object> info) {
//																if (info != null) {
//
//
//																	final String unionid = info.get("unionid").toString();
//																	if (TextUtils.isEmpty(unionid)) {
//																		return;
//																	}
//
//																	//更新unionid
//																	new SAsyncTask<String, Void, UserInfo>((FragmentActivity) BuyFreeActivity.this, R.string.wait) {
//
//																		@Override
//																		protected UserInfo doInBackground(FragmentActivity context, String... params)
//																				throws Exception {
//																			return ComModel2.updateUuid(BuyFreeActivity.this, unionid,openid);
//																		}
//
//																		@Override
//																		protected boolean isHandleException() {
//																			return true;
//																		}
//
//																		@Override
//																		protected void onPostExecute(FragmentActivity context, UserInfo result, Exception e) {
//																			super.onPostExecute(context, result, e);
//																			if (null == e) {
//
//
//																			}
//																		}
//
//																	}.execute();
//
//
//																}
//															}
//														});
//													}
//
//													@Override
//													public void onCancel(SHARE_MEDIA platform) {
//														ToastUtil.showShortText(BuyFreeActivity.this, "需要微信授权哦~");
//
//													}
//												});

											}

											return;
										}


									}


									Intent intent2 = new Intent((Activity) context, CrazyShopListActivity.class);
									context.startActivity(intent2);
									((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);




								} else {
									ToastUtil.showLongText(context, "糟糕，出错了~~~");
								}
							}
						}

					}.execute();





//
//					Intent intent2 = new Intent((Activity) context, CrazyShopListActivity.class);
//					context.startActivity(intent2);
//					((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);














//					if (whereMon.equals("collection=shopping_page")) {// 购物
//						// 跳至购物
//						Intent intent2 = new Intent((Activity) context, MainMenuActivity.class);
//						intent2.putExtra("toShop", "toShop");
//						context.startActivity(intent2);
//						((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//					} else if (whereMon.equals("collection=collocation_shop")) {// 搭配
//
//						Intent intent = new Intent(context, ForceLookMatchActivity.class);
//						intent.putExtra("type", "1");
//						context.startActivity(intent);
//						((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//					} else if (whereMon.equals("collection=shop_activity")) {// 活动
//
//						// 跳转到活动商品
//						context.startActivity(new Intent(context, SignActiveShopActivity.class));
//						((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//					} else if (whereMon.equals("collection=csss_shop")) {// 专题
//						Intent intent = new Intent(context, ForceLookMatchActivity.class);
//						intent.putExtra("type", "2");
//						context.startActivity(intent);
//						((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//					}
//
//					else if (whereMon.equals("type_name=热卖&notType=true")) {// 热卖
//
//						Intent intent = new Intent(context, ForceLookActivity.class);
//						intent.putExtra("id", "6");
//						intent.putExtra("title", "热卖");
//						context.startActivity(intent);
//						((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//					}
//
//					else if (whereMon.equals("collection=shop_home")) {// 首页
//
//						// 跳至首页
//						Intent intent2 = new Intent((Activity) context, MainMenuActivity.class);
//						intent2.putExtra("toHome", "toHome");
//						context.startActivity(intent2);
//						((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//					}
//
//					else { // 找不到的话还是用热卖
//
//						Intent intent = new Intent(context, ForceLookActivity.class);
//						intent.putExtra("id", "6");
//						intent.putExtra("title", "热卖");
//						context.startActivity(intent);
//						((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//					}
//
//
//





















//				} else {
//
//					Intent intent = new Intent(context, HotSaleActivity.class);
//					intent.putExtra("id", "6");
//					intent.putExtra("title", "热卖");
//					context.startActivity(intent);
//					overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//				}
			} else {

				Intent intent = new Intent(context, HotSaleActivity.class);
				intent.putExtra("id", "6");
				intent.putExtra("title", "热卖");
				context.startActivity(intent);
				overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
			}

			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * @param tv
	 * @param type
	 *            0 只设置改变第一个字字体大小 1--改变第一个字字体大小 改变mExpTv2_50部分字体颜色 2--改变第一个字字体大小
	 *            改变mExpTv2_100部分字体颜色
	 */
	private void changeTextSpan(TextView tv, int type) {
		String text = tv.getText().toString();
		SpannableString textSpan = new SpannableString(text);
		textSpan.setSpan(new AbsoluteSizeSpan(DP2SPUtil.sp2px(context, 18)), 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		if (!isCrazyMon) {
			if (type == 1 || type == 2) {
				textSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#FFD800")), 16, 25,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			if (type == 2) {
				textSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#FFD800")), 26, 32,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}

		if (isCrazyMon) {
			if (type == 101) {
				textSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#fff400")), 28, 36,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			if (type == 102) {
				try {
					textSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#fff400")), 14, 21,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		tv.setText(textSpan);
	}

	public static AlertDialog dialog;

	public static void customDialog(final Context mContext) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.Theme_Transparent);
		// 自定义一个布局文件
		View view = View.inflate(mContext, R.layout.payback_esc_apply_dialog, null);
		view.setBackgroundResource(R.drawable.redquanbg);
		TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
		Button ok = (Button) view.findViewById(R.id.ok);
		ok.setTextColor(Color.parseColor("#ffffff"));
		ok.setBackgroundResource(R.drawable.bg_red_ok);
		ok.setWidth(DP2SPUtil.dp2px(mContext, 90));
		Button cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setWidth(DP2SPUtil.dp2px(mContext, 90));
		cancel.setTextColor(Color.parseColor("#ff3f8b"));
		cancel.setBackgroundResource(R.drawable.bg_tans_cancel);


		tv_des.setText("为了您的账户安全，请先绑定手机");
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 把这个对话框取消掉
				dialog.dismiss();

			}
		});

	}
}
