package com.yssj.ui.dialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.YConstance.Pref;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ShopCart;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.network.YConn;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.WithdrawalLimitActivity;
import com.yssj.ui.activity.infos.MyWalletCommonFragmentActivity;
import com.yssj.ui.fragment.mywallet.WithDrawWeiXinCheckActivity;
import com.yssj.utils.GetShopCart;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

/****
 * 商品详情页 套餐对话框
 * 
 * @author Administrator
 * 
 */
public class TixianYinDaoDialog extends Dialog implements OnClickListener, TextWatcher {
	private ImageView icon_close;
	private Context context;
	private Button mNextStep;
	private TextView tv, title, tv_yidoucount;
	private int jumpFrom; // 1引导第一个提示 ---在签到页面 2 引导第二个---在提现页面 3引导第三个
							// ------签到页面（获得20衣豆）
	private EditText mName;
	private EditText mCard;
	private WithDrawListener listener;

	private LinearLayout ll_yidou;

	public TixianYinDaoDialog(Context context, int style, int jumpFrom, WithDrawListener listener) {
		super(context, style);
		setCanceledOnTouchOutside(true);
		this.context = context;
		this.jumpFrom = jumpFrom;
		this.listener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_tixian_yindao);
		// GetShopCart.querShopCart(context,1);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		icon_close = (ImageView) findViewById(R.id.icon_close);

		mNextStep = (Button) findViewById(R.id.bt_next);
		ll_yidou = (LinearLayout) findViewById(R.id.ll_yidou);

		icon_close.setOnClickListener(this);
		mNextStep.setOnClickListener(this);

		mName = (EditText) findViewById(R.id.explain_name);
		mName.addTextChangedListener(this);
		mCard = (EditText) findViewById(R.id.expalin_card);
		mCard.addTextChangedListener(this);

		tv = (TextView) findViewById(R.id.tv);
		title = (TextView) findViewById(R.id.title);
		// tv_yidoucount = (TextView) findViewById(R.id.tv_yidoucount);
		switch (jumpFrom) {
		case 1:
			title.setVisibility(View.VISIBLE);
			ll_yidou.setVisibility(View.GONE);
			mNextStep.setBackgroundResource(R.drawable.btn_back_red);
			mName.setVisibility(View.GONE);
			mCard.setVisibility(View.GONE);
			mNextStep.setText("下一步");
			tv.setText("欢迎加入衣蝠，为了让新用户能快速体验提现功能，我们特意在你的可提现额度中存入1.00元现金，现在可以立即提现了哦~");
			SharedPreferencesUtil.saveBooleanData(context, "isYindaoToast", true);

			new Thread() {
				public void run() {
					try {
						ComModel2.delUserType(context);
					} catch (Exception e) {

					}
				};
			}.start();

			break;

		case 2:
			title.setVisibility(View.VISIBLE);
			ll_yidou.setVisibility(View.GONE);
			mNextStep.setClickable(false);
			mName.setVisibility(View.VISIBLE);
			mCard.setVisibility(View.VISIBLE);
			tv.setText("为了你的账户信息安全，身份证号必须正确填写，平台将用于微信或银行卡信息进行验证，错误信息将会导致提现失败，身份证信息进行严格的加密处理，提现验证。");
			SpannableStringBuilder builder = new SpannableStringBuilder(tv.getText().toString());
			ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#ff3f8b"));
			builder.setSpan(redSpan, 11, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv.setText(builder);
			mNextStep.setText("立即提现");
			mNextStep.setBackgroundResource(R.drawable.submit_money_next_shape);
			String digists = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			mCard.setKeyListener(DigitsKeyListener.getInstance(digists));
			break;

		case 3:
			title.setVisibility(View.GONE);
			// tv.setText("幸运女神降临，你已免费获得" +
			// SharedPreferencesUtil.getStringData(context, Pref.SGUIDANCE, "0")
			// + "个衣豆，使用衣豆抽奖，最高赢取1000元提现额度哦~");
			tv.setText("幸运女神降临，你已免费获得20个衣豆，使用衣豆抽奖，最高赢取1000元提现额度哦~");
			mName.setVisibility(View.GONE);
			mCard.setVisibility(View.GONE);
			// tv_yidoucount.setText("+"+SharedPreferencesUtil.getStringData(context,
			// Pref.SGUIDANCE, "0"));
			mNextStep.setText("立即抽取提现额度");
			ll_yidou.setVisibility(View.VISIBLE);
			SharedPreferencesUtil.saveStringData(context, Pref.SGUIDANCE, "");

			break;
		default:
			break;

		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.icon_close:
			if (listener != null) {
				listener.close();
			}
			this.dismiss();
			break;
		case R.id.bt_next: // 下一步

			switch (jumpFrom) {
			case 1:
				Intent intent = new Intent(context, MyWalletCommonFragmentActivity.class);
				intent.putExtra("flag", "withDrawalFragment");
				// intent.putExtra("balance", balance);
				intent.putExtra("alliance", "wallet");
				intent.putExtra("jumpFromSign", true);
				context.startActivity(intent);
				this.dismiss();
				break;

			case 2:
				if (personIdValidation(mCard.getText().toString())) {// 匹配身份证
					this.dismiss();
					submitCard(mCard.getText().toString(), context);
					// Intent intent2 = new Intent(context,
					// WithDrawWeiXinCheckActivity.class);
					// // intent.putExtra("money", money);
					// intent2.putExtra("name", mName.getText().toString());
					// intent2.putExtra("identity", mCard.getText().toString());
					// context.startActivity(intent2);
				} else {
					ToastUtil.showShortText(context, "请输入正确的身份证号");
				}
				break;

			case 3:

				intent = new Intent(context, WithdrawalLimitActivity.class);
				context.startActivity(intent);
				((FragmentActivity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

				dismiss();
				break;
			default:
				break;

			}
			break;

		default:
			break;
		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (mCard.getText().toString().length() > 0 && mName.getText().toString().length() > 0) {
			mNextStep.setBackgroundResource(R.drawable.btn_back_red);
			mNextStep.setClickable(true);
		} else {
			mNextStep.setBackgroundResource(R.drawable.submit_money_next_shape);
			mNextStep.setClickable(false);
		}

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	/**
	 * 验证身份证号是否符合规则
	 * 
	 * @param text
	 *            身份证号
	 * @return
	 */
	public boolean personIdValidation(String text) {
		String regx = "[0-9]{17}X";
		String reg1 = "[0-9]{15}";
		String regex = "[0-9]{18}";
		return text.matches(regx) || text.matches(reg1) || text.matches(regex);
	}

	private void submitCard(final String idCard, final Context mContext) {

		new SAsyncTask<Integer, Void, HashMap<String, String>>((FragmentActivity) context, R.string.wait) {
			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context, Integer... params)
					throws Exception {

				return ComModel2.submit_card_guid(context, idCard);

			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
				super.onPostExecute(context, result, e);

				if (e != null) {// 查询异常
				} else {
					if (listener != null) {
						listener.close();
					}
					if (result != null) {
						String sguidance = result.get("sguidance");
						SharedPreferencesUtil.saveStringData(mContext, Pref.SGUIDANCE, "" + sguidance);
					}
				}
			}

		}.execute();
	}

	public interface WithDrawListener {
		public void close();
		// public void submit();
	}
}