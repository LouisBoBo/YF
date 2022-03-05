package com.yssj.ui.activity.payback;

import java.io.Serializable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnShop;
import com.yssj.model.ComModel2;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.payback.ApplyPlatformActivity;
import com.yssj.ui.fragment.payback.ApplyPlatformItemDetailsFragment;
import com.yssj.ui.fragment.payback.ApplyPlatformItemFragment;
import com.yssj.ui.fragment.payback.hh.HHDetailAuditPassFragment;
import com.yssj.ui.fragment.payback.hh.HHDetailBuyerSendGoodsFragment;
import com.yssj.ui.fragment.payback.hh.HHDetailSellerConfirmReceiveFragment;
import com.yssj.ui.fragment.payback.hh.HHDetailSellerRefuseFragment;
import com.yssj.ui.fragment.payback.hh.HHDetailSuccessFragment;
import com.yssj.ui.fragment.payback.hh.HHDetailUncheckFragment;
import com.yssj.ui.fragment.payback.thtk.THDetailAuditPassFragment;
import com.yssj.ui.fragment.payback.thtk.THDetailBuyerSendGoodsFragment;
import com.yssj.ui.fragment.payback.thtk.THDetailPlatformAuditTKFragment;
import com.yssj.ui.fragment.payback.thtk.THDetailSellerConfirmReceiverFragment;
import com.yssj.ui.fragment.payback.thtk.THDetailSellerRefuseFragment;
import com.yssj.ui.fragment.payback.thtk.THDetailSuccessFragment;
import com.yssj.ui.fragment.payback.thtk.THDetailUncheckFragment;
import com.yssj.ui.fragment.payback.tk.TKDetailAliFailedFragment;
import com.yssj.ui.fragment.payback.tk.TKDetailAuditPassFragment;
import com.yssj.ui.fragment.payback.tk.TKDetailCloseFragment;
import com.yssj.ui.fragment.payback.tk.TKDetailCommit9Fragment;
import com.yssj.ui.fragment.payback.tk.TKDetailCommitAliFragment;
import com.yssj.ui.fragment.payback.tk.TKDetailPlatformAuditTkFragment;
import com.yssj.ui.fragment.payback.tk.TKDetailSellerRefuseFragment;
import com.yssj.ui.fragment.payback.tk.TKDetailSuccessFragment;
import com.yssj.ui.fragment.payback.tk.TKDetailUncheckFragment;
import com.yssj.ui.fragment.payback.tk.UserRefuseFragment;

public class PayBackDetailsActivity extends BasicActivity {
	private String order_code;
	private ReturnShop result;
private String isIndiana;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getActionBar().hide();
		// initData();
		isIndiana=getIntent().getStringExtra("isIndiana");
		handleResult();
		initView();
	}

	private void initView() {
		// setContentView(R.layout.pay_back_details);
		setContentView(R.layout.activity_circle_common);
	}

	private void handleResult() {
		result = (ReturnShop) getIntent().getSerializableExtra("returnShop");
		if (result == null) {
			return;
		}
		Fragment mFragment;
		Bundle bundle = new Bundle();
		bundle.putSerializable("returnShop", result);
		// 平台介入后的跳转
		if (Integer.parseInt(result.getSupp_sign_status().toString()) == 1) {
			if (result.getYs_intervene() != 0) {
				mFragment = new ApplyPlatformItemDetailsFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
				return;
			} else {
				mFragment = new ApplyPlatformItemFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			}

		} else if (result.getReturn_type() == 1) { // 换货
			if (result.getStatus() == 1) { // 待审核 ---已完成
				mFragment = new HHDetailUncheckFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 2) { // 审核通过 ---已完成
				mFragment = new HHDetailAuditPassFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 3) { // 审核未通过 ---已完成
				mFragment = new HHDetailSellerRefuseFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 4) { // 供应商已收到货并发货 ---已完成
				mFragment = new HHDetailSellerConfirmReceiveFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 5) { // 买家取消 ---已经在退款售后列表中不显示了
				mFragment = new UserRefuseFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 6) { // 退款成功----换货不需要

			} else if (result.getStatus() == 7) { // 退款关闭----换货不需要

			} else if (result.getStatus() == 8) { // 换货成功 ---已完成
				mFragment = new HHDetailSuccessFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 9) { // 平台审核退款 ----换货不需要

			} else if (result.getStatus() == 10) { // 买家已寄快递 ---已完成
				mFragment = new HHDetailBuyerSendGoodsFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			}

		} else if (result.getReturn_type() == 2) { // 退货
			if (result.getStatus() == 1) { // 待审核---已完成
				mFragment = new THDetailUncheckFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 2) { // 审核通过 ---已完成
				mFragment = new THDetailAuditPassFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 3) { // 审核未通过 ---已完成
				mFragment = new THDetailSellerRefuseFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 4) { // 供应商已收到货 ---已完成
				mFragment = new THDetailSellerConfirmReceiverFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 5) { // 买家取消 ---已经在退款售后列表中不显示了
				mFragment = new UserRefuseFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 6) { // 退款成功 ---已完成
				mFragment = new THDetailSuccessFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 7) { // 退款关闭 ---退货不需要

			} else if (result.getStatus() == 8) { // 换货成功---退货不需要

			} else if (result.getStatus() == 9) { // 平台审核退款 ---已完成
				/*
				 * mFragment = new THDetailPlatformAuditTKFragment();
				 * mFragment.setArguments(bundle);
				 * getSupportFragmentManager().beginTransaction
				 * ().replace(R.id.fl_content, mFragment).commit();
				 */
				mFragment = new TKDetailCommit9Fragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 10) { // 买家已寄快递 ---已完成
				mFragment = new THDetailBuyerSendGoodsFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 11) {// 退款已提交支付宝审核
				mFragment = new TKDetailCommitAliFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 12) {// 支付宝审核退款失败
				mFragment = new TKDetailAliFailedFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			}

		} else if (result.getReturn_type() == 3) { // 退款
			if (result.getStatus() == 1) { // 退款待审核----已完成
				mFragment = new TKDetailUncheckFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 2) { // 审核通过----已完成
				// mFragment = new PaybackTKDetailSuccessFragment();
				mFragment = new TKDetailAuditPassFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 3) { // 审核未通过 ----已完成
				mFragment = new TKDetailSellerRefuseFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 4) { // // 供应商已收到货 ---退款不需要

			} else if (result.getStatus() == 5) { // 买家取消 ---已经在退款售后列表中不显示了
				mFragment = new UserRefuseFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 6) { // 退款成功
				mFragment = new TKDetailSuccessFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 7) { // 退款关闭
				mFragment = new TKDetailCloseFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 8) { // 换货成功---退款不需要

			} else if (result.getStatus() == 9) { // 平台审核退款
				/*
				 * mFragment = new TKDetailPlatformAuditTkFragment();
				 * mFragment.setArguments(bundle);
				 * getSupportFragmentManager().beginTransaction
				 * ().replace(R.id.fl_content, mFragment).commit();
				 */
				mFragment = new TKDetailCommit9Fragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 10) { // 买家已寄快递 ---退款不需要

			} else if (result.getStatus() == 11) {// 退款已提交支付宝审核
				mFragment = new TKDetailCommitAliFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			} else if (result.getStatus() == 12) {// 支付宝审核退款失败
				mFragment = new TKDetailAliFailedFragment();
				mFragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fl_content, mFragment).commit();
			}

		}
	}

	private void initData() {
		order_code = getIntent().getStringExtra("order_code");

		new SAsyncTask<Void, Void, ReturnShop>(this, R.string.wait) {

			@Override
			protected ReturnShop doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel2.checkPayback(context, order_code);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnShop result, Exception e) {
				super.onPostExecute(context, result, e);
				// System.out.println("type:" + result.getReturn_type() +
				// ",status:" + result.getStatus());
				if (null == e) {
					Fragment mFragment;
					Bundle bundle = new Bundle();
					bundle.putSerializable("returnShop", result);
bundle.putString("isIndiana", isIndiana);
					if (result.getReturn_type() == 1) { // 换货
						if (result.getStatus() == 1) { // 待审核 ---已完成
							mFragment = new HHDetailUncheckFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						} else if (result.getStatus() == 2) { // 审核通过 ---已完成
							mFragment = new HHDetailAuditPassFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						} else if (result.getStatus() == 3) { // 审核未通过 ---已完成
							mFragment = new HHDetailSellerRefuseFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						} else if (result.getStatus() == 4) { // 供应商已收到货并发货
																// ---已完成
							mFragment = new HHDetailSellerConfirmReceiveFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						} else if (result.getStatus() == 5) { // 买家取消
																// ---已经在退款售后列表中不显示了
							// mFragment = new THDetailUncheckFragment();
							// mFragment.setArguments(bundle);
							// getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,
							// mFragment).commit();
						} else if (result.getStatus() == 6) { // 退款成功----换货不需要

						} else if (result.getStatus() == 7) { // 退款关闭----换货不需要

						} else if (result.getStatus() == 8) { // 换货成功 ---已完成
							mFragment = new HHDetailSuccessFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						} else if (result.getStatus() == 9) { // 平台审核退款
																// ----换货不需要

						} else if (result.getStatus() == 10) { // 买家已寄快递 ---已完成
							mFragment = new HHDetailBuyerSendGoodsFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						}

					} else if (result.getReturn_type() == 2) { // 退货
						if (result.getStatus() == 1) { // 待审核---已完成
							mFragment = new THDetailUncheckFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						} else if (result.getStatus() == 2) { // 审核通过 ---已完成
							mFragment = new THDetailAuditPassFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						} else if (result.getStatus() == 3) { // 审核未通过 ---已完成
							mFragment = new THDetailSellerRefuseFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						} else if (result.getStatus() == 4) { // 供应商已收到货 ---已完成
							mFragment = new THDetailSellerConfirmReceiverFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						} else if (result.getStatus() == 5) { // 买家取消
																// ---已经在退款售后列表中不显示了
							// mFragment = new THDetailUncheckFragment();
							// mFragment.setArguments(bundle);
							// getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,
							// mFragment).commit();
						} else if (result.getStatus() == 6) { // 退款成功 ---已完成
							mFragment = new THDetailSuccessFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						} else if (result.getStatus() == 7) { // 退款关闭 ---退货不需要

						} else if (result.getStatus() == 8) { // 换货成功---退货不需要

						} else if (result.getStatus() == 9) { // 平台审核退款 ---已完成
							mFragment = new THDetailPlatformAuditTKFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						} else if (result.getStatus() == 10) { // 买家已寄快递 ---已完成
							mFragment = new THDetailBuyerSendGoodsFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						}

					} else if (result.getReturn_type() == 3) { // 退款
						if (result.getStatus() == 1) { // 退款待审核----已完成
							mFragment = new TKDetailUncheckFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						} else if (result.getStatus() == 2) { // 审核通过----已完成
							// mFragment = new PaybackTKDetailSuccessFragment();
							mFragment = new TKDetailAuditPassFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						} else if (result.getStatus() == 3) { // 审核未通过 ----已完成
							mFragment = new TKDetailSellerRefuseFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						} else if (result.getStatus() == 4) { // // 供应商已收到货
																// ---退款不需要

						} else if (result.getStatus() == 5) { // 买家取消
																// ---已经在退款售后列表中不显示了
							// mFragment = new PaybackTKDetailFailFragment();
							// mFragment.setArguments(bundle);
							// getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,
							// mFragment).commit();
						} else if (result.getStatus() == 6) { // 退款成功
							mFragment = new TKDetailSuccessFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						} else if (result.getStatus() == 7) { // 退款关闭
							mFragment = new TKDetailCloseFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						} else if (result.getStatus() == 8) { // 换货成功---退款不需要

						} else if (result.getStatus() == 9) { // 平台审核退款
							mFragment = new TKDetailPlatformAuditTkFragment();
							mFragment.setArguments(bundle);
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.fl_content, mFragment)
									.commit();
						} else if (result.getStatus() == 10) { // 买家已寄快递
																// ---退款不需要

						}

					}
				}

			}

		}.execute();
	}

}
