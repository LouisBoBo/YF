package com.yssj.ui.activity.infos;

import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.MainFragment;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BalanceDoubleSuccessActivity extends BasicActivity implements OnClickListener {
	private LinearLayout img_back;
	private TextView tvTitle_base;
	private TextView mShowBalance, tv1111;
	private TextView mToBuyShop;
	private String flag = "";
	private TextView mExplain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_balance_success);
		flag = getIntent().getStringExtra("where");
		initView();
		SharedPreferencesUtil.saveBooleanData(this, Pref.IS_QULIFICATION, true);
		SharedPreferencesUtil.saveStringData(this, Pref.IS_OPEN, "1");
	}

	private void initView() {
		tv1111 = (TextView) findViewById(R.id.tv1111);
		mExplain = (TextView) findViewById(R.id.tv_explain);
		mExplain.setVisibility(View.VISIBLE);
		mExplain.setOnClickListener(this);
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("开启成功");
		mShowBalance = (TextView) findViewById(R.id.tv_show_banlance);
		mShowBalance.setOnClickListener(this);
		mToBuyShop = (TextView) findViewById(R.id.tv_buy_shop);
		mToBuyShop.setOnClickListener(this);

		if ("WithDrawalFragment".equals(flag)) {
			tv1111.setText("亲，你已成功开启余额x2倍");
		}

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.tv_show_banlance:
			if ("WithDrawalFragment".equals(flag)) {
				finish();
			} else {
				Intent intent2 = new Intent(this, MyWalletActivity.class);
				startActivity(intent2);
				finish();
			}
			break;
		case R.id.tv_buy_shop:
			CommonUtils.finishActivity(MainMenuActivity.instances);

			Intent intent = new Intent(this, MainMenuActivity.class);
			intent.putExtra("toYf", "toYf");
			startActivity(intent);
			finish();
			break;
		case R.id.tv_explain:
			balanceDialog();
			break;
		default:
			break;
		}

	}

	private void balanceDialog() {
		final Dialog dialog = new Dialog(this, R.style.invate_dialog_style);
		View view = View.inflate(this, R.layout.balance_explain_dialog, null);
		TextView btn_close = (TextView) view.findViewById(R.id.balance_dialog_start);
		TextView balance_dialog_title = (TextView) view.findViewById(R.id.balance_dialog_title);

		balance_dialog_title
				.setText("什么是余额X" + SharedPreferencesUtil.getStringData(this, Pref.TWOFOLDNESS, "2") + "倍特权?");
		TextView balance_dialog_tv1 = (TextView) view.findViewById(R.id.balance_dialog_tv1);
		if ("WithDrawalFragment".equals(flag)) {
			balance_dialog_tv1.setText("亲,余额x" + SharedPreferencesUtil.getStringData(this, Pref.TWOFOLDNESS, "2")
					+ "倍特权是开启余额翻倍后24小时之内余额变");
		} else {
			balance_dialog_tv1.setText(
					"亲,余额x" + SharedPreferencesUtil.getStringData(this, Pref.TWOFOLDNESS, "2") + "倍是指在开店成功后24小时之内余额");
		}
		TextView balance_dialog_tv2 = (TextView) view.findViewById(R.id.balance_dialog_tv2);
		balance_dialog_tv2.setText(
				"为原来余额的" + SharedPreferencesUtil.getStringData(this, Pref.TWOFOLDNESS, "2") + "倍,可直接用于购物,24小时之后");
		btn_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 把这个对话框取消掉
				dialog.dismiss();
			}
		});

		// 创建自定义样式dialog
		dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));
		dialog.setCancelable(false);
		dialog.show();
	}
}
