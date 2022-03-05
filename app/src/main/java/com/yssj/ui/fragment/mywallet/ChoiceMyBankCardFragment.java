package com.yssj.ui.fragment.mywallet;

import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.PayPasswordCustomDialog;
import com.yssj.custom.view.PayPasswordCustomDialog.InputDialogListener;
import com.yssj.entity.CheckPwdInfo;
import com.yssj.entity.MyBankCard;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.infos.SetMyPayPassActivity;
import com.yssj.ui.activity.infos.StatusInfoActivity;
import com.yssj.ui.activity.shopdetails.MealSubmitOrderActivity;
import com.yssj.ui.activity.shopdetails.PaymentActivity;
import com.yssj.ui.activity.shopdetails.SubmitMultiShopActivty;
import com.yssj.ui.activity.shopdetails.SubmitOrderActivity;
import com.yssj.ui.adpter.ChoiceMyBankCardListAdapter;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.dialog.PayErrorDialog;
import com.yssj.utils.ToastUtil;

public class ChoiceMyBankCardFragment extends BaseFragment implements
		OnClickListener {

	private TextView tvTitle_base;
	private LinearLayout img_back;

	private RelativeLayout rel_support_bank_card, rel_add_bank_card,
			rl_listview;
	private ListView lv_card;

	private TextView tv_des_info;

	private ChoiceMyBankCardListAdapter adapter;

	private PayPasswordCustomDialog customDialog;
	private InputDialogListener inputDialogListener;
	private List<MyBankCard> lists;

	private String alliance;
	private boolean mACommonFlag=false;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_mywallet_mycard, null);
		view.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("我的银行卡");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);

		rel_support_bank_card = (RelativeLayout) view
				.findViewById(R.id.rel_support_bank_card);
		rel_support_bank_card.setVisibility(View.VISIBLE);

		tv_des_info = (TextView) view.findViewById(R.id.tv_des_info);
		tv_des_info.setText("选择提现到哪张银行卡");
		tv_des_info.setTextSize(16.0f);
		tv_des_info.setTextColor(getResources().getColor(R.color.text1_color));

		rel_add_bank_card = (RelativeLayout) view
				.findViewById(R.id.rel_add_bank_card);
		rel_add_bank_card.setOnClickListener(this);

		rl_listview = (RelativeLayout) view.findViewById(R.id.rl_listview);

		lv_card = (ListView) view.findViewById(R.id.lv_card);

		return view;
	}

	@Override
	public void initData() {

		Bundle bundle = getArguments();
		if (bundle != null) {
			money = bundle.getString("money");
			alliance = bundle.getString("alliance");
			mACommonFlag=bundle.getBoolean("mACommonFlag");
		}

		new SAsyncTask<Void, Void, List<MyBankCard>>(
				(FragmentActivity) context, R.string.wait) {

			@Override
			protected List<MyBankCard> doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel.findMyBankCard(context);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					List<MyBankCard> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e) {
					if (result != null && !result.isEmpty()) {
						rl_listview.setVisibility(View.VISIBLE);

						lists = result;

						adapter = new ChoiceMyBankCardListAdapter(context,
								result);
						lv_card.setAdapter(adapter);
					} else {
						rl_listview.setVisibility(View.GONE);
					}
				}
			}

		}.execute();

		lv_card.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (lists != null) {
					MyBankCard myBankCard = lists.get(position);
//					if ((myBankCard.getBranch_name() == null)
//							|| myBankCard.getBranch_name().equals("null")) {
//						ToastUtil
//								.showLongText(context, "您这张银行卡有未完善的信息，请完善后再提现");
//						Fragment mFragment = new ModifyMyBankCardFragment(
//								myBankCard);
//						Bundle bundle = new Bundle();
//						bundle.putString("flag", "choiceMyBankCardFragment");
//						bundle.putString("money", money);
//						mFragment.setArguments(bundle);
//						getActivity().getSupportFragmentManager()
//								.beginTransaction()
//								.replace(R.id.fl_content, mFragment).commit();
//					} else {
						checkMyWalletPayPass(null, myBankCard.getId() + "");
						// initDialog(myBankCard.getId() + "");
//					}
				}
			}
		});

		lv_card.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	private void checkMyWalletPayPass(View v, final String id) {
		// TODO Auto-generated method stub
		new SAsyncTask<Void, Void, CheckPwdInfo>(getActivity(), v,
				R.string.wait) {

			@Override
			protected CheckPwdInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel.checkPWD(context);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					CheckPwdInfo result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e) {
					if (result != null && "1".equals(result.getStatus())
							&& "1".equals(result.getFlag())) {
						customDialog();
					} else if (result != null && "1".equals(result.getStatus())
							&& "2".equals(result.getFlag())) {
						initDialog(id);
					} else {
						ToastUtil.showLongText(context, "糟糕，出错了~~~");
					}
				}
			}

		}.execute((Void[]) null);
	}

	private AlertDialog dialog;

	private void customDialog() {
		AlertDialog.Builder builder = new Builder(getActivity());
		// 自定义一个布局文件
		View view = View.inflate(getActivity(),
				R.layout.payback_esc_apply_dialog, null);
		TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
		tv_des.setText("您还没有设置支付密码，立即去设置？");

		Button ok = (Button) view.findViewById(R.id.ok);
		ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
		Button cancel = (Button) view.findViewById(R.id.cancel);

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 把这个对话框取消掉
				dialog.dismiss();
			}
		});

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						SetMyPayPassActivity.class);
				startActivity(intent);
				dialog.dismiss();
			}
		});

		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}

	/**
	 * onFocusListener
	 */
	OnFocusChangeListener FocusListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// initDialog();
		}
	};

	/**
	 * onclicklistener
	 */
	OnClickListener ClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// initDialog();
		}
	};
	private String money;

	/**
	 * init Dialog
	 */
	private void initDialog(final String id) {
		customDialog = new PayPasswordCustomDialog(getActivity(),
				R.style.mystyle, R.layout.pay_customdialog, "请输入密码", null);
		inputDialogListener = new InputDialogListener() {

			@Override
			public void onOK(String text) {
				// 给密码文本框设置密码
				// ToastUtil.showLongText(context, "你的密码是：" + text);
				submit(id, text);
			}

			@Override
			public void onCancel() {
				// TODO Auto-generated method stub

			}

		};
		customDialog.setListener(inputDialogListener);
		customDialog.show();
	}

	private void submit(final String bank_id, final String pwd) {

		new SAsyncTask<String, Void, HashMap<String, Object>>(
				(FragmentActivity) context, R.string.wait) {

			@Override
			protected HashMap<String, Object> doInBackground(
					FragmentActivity context, String... params)
					throws Exception {

				if (null == alliance || (!alliance.equals("alliance"))) {
					return ComModel.bankDepositAdd(context, money, pwd,
							bank_id, "");
				} else {

					return ComModel.bankDepositAdd2(context, money, pwd,
							bank_id, "");// 联盟商家提现
				}
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, Object> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e) {

					
					int pwdflag = Integer.parseInt(result.get("pwdflag").toString());
					String SucMoney  = result.get("money").toString();
					int flag =Integer.parseInt(result.get("flag").toString());
					if (pwdflag == 0&&(flag==0||flag==4)) { // 支付成功
//						if(flag!=4){
//							ToastUtil.showShortText(context, "支付成功");
//						}
					// MealSubmitOrderActivity.instance.finish();
					// MealPaymentActivity.this.finish();
					// paySuccessTo();
					// timeCount.start();
					// rel_pay_success.setVisibility(View.VISIBLE);
					// rel_nomal.setVisibility(View.GONE);
					 
					 
					 
					 
					 
						Fragment mFragment = new SuccessWithDrawalFragment();
						Bundle bundle = new Bundle();
						bundle.putString("SucMoney", SucMoney);//提现成功的金额
						bundle.putString("money", money);//要提现的金额
						bundle.putBoolean("mACommonFlag", mACommonFlag);
						bundle.putInt("flag", flag);
						bundle.putString("alliance", alliance);
						mFragment.setArguments(bundle);
						getActivity().getSupportFragmentManager()
								.beginTransaction()
								.replace(R.id.fl_content, mFragment).commit();

					} else if (pwdflag == 1 || pwdflag == 2 || pwdflag == 3) {
						String message = result.get("message").toString();
						// 支付密码错误
//						customDialog.dismiss();
						PayErrorDialog dialog = new PayErrorDialog(context,
								R.style.DialogStyle1, pwdflag,message);
						dialog.show();

					}else if(flag==1||flag==2||flag==3||flag==5||flag==6){//flag==5提现金额不能大于最高提现金额或者小于最低提现金额,flag==6时为未找到该银行卡
						String message = result.get("message").toString();
						ToastUtil.showShortText(context, message);
					}

					//
					// if (result != null && "1".equals(result.get("status"))) {
					// ToastUtil.showLongText(context,
					// result.get("message")+"");
					// Fragment mFragment = new SuccessWithDrawalFragment();
					// Bundle bundle = new Bundle();
					// bundle.putString("money", money);
					// bundle.putString("alliance", alliance);
					// mFragment.setArguments(bundle);
					// getActivity().getSupportFragmentManager()
					// .beginTransaction()
					// .replace(R.id.fl_content, mFragment).commit();
					// }
				}
			}

		}.execute();
	}

	@Override
	public void onClick(View v) {
		Fragment mFragment;
		switch (v.getId()) {
		case R.id.img_back:
			getActivity().finish();
			break;

		case R.id.rel_add_bank_card:
			mFragment = new AddMyBankCardFragment();
			Bundle bundle = new Bundle();
			bundle.putString("flag", "choiceMyBankCardFragment");
			bundle.putString("money", money);
			bundle.putString("alliance", alliance);
			mFragment.setArguments(bundle);
			getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.fl_content, mFragment).commit();
			break;

		}
	}

}
