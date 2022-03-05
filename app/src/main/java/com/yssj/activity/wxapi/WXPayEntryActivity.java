package com.yssj.activity.wxapi;


import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yssj.activity.R;
import com.yssj.ui.activity.shopdetails.ShowShareActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ShareUtil;
import com.yssj.wxpay.WxPayUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class WXPayEntryActivity extends BasicActivity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;

	private Intent intent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil
			.getImage());
	private AlertDialog dialog2 = null;
	
	private Button btn_choose_other;
	
	public static WXPayEntryActivity instance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		aBar.hide();
		setContentView(R.layout.pay_result);

		api = WXAPIFactory.createWXAPI(this, WxPayUtil.APP_ID);

		api.handleIntent(getIntent(), this);
		
		instance = this;
		initView();
	}

	private void initView(){
		((TextView)findViewById(R.id.tvTitle_base)).setText("收银台");
		findViewById(R.id.img_back).setVisibility(View.GONE);
		findViewById(R.id.btn_choose_other).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				WXPayEntryActivity.this.finish();
			}
		});
	}
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		LogYiFu.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		// if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
		// AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// builder.setTitle(R.string.app_tip);
		// builder.setMessage(getString(R.string.pay_result_callback_msg,
		// resp.errStr +";code=" + String.valueOf(resp.errCode)));
		// builder.show();
		// }
		if (resp.errCode == 0) {
			WXPayEntryActivity.this.finish();
			onwxpayFinish.onWxpaySuccess();
		}else{
			WXPayEntryActivity.this.finish();
			onwxpayFinish.onWxpayFailed();
		}

	}
	public static OnWxpayFinish onwxpayFinish;
	
	public static void setOnWxpayFinish(Activity activity){
		onwxpayFinish = (OnWxpayFinish) activity;
	}
	
	public interface OnWxpayFinish{
		void onWxpaySuccess();
		void onWxpayFailed();
	}

	private void showShareDialog() {
	
		/** 弹出自定义对话框 */
		AlertDialog.Builder builder = null;
		View view = LayoutInflater.from(this).inflate(
				R.layout.choose_share_platform, null);
		RadioGroup rg = (RadioGroup) view.findViewById(R.id.rg);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				switch (arg1) {
				case R.id.rb_share_timeline:
					intent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil
							.getImage());
					break;
				case R.id.rb_share_qqzone:
					intent = ShareUtil.shareMultiplePictureToQZone(ShareUtil
							.getImage());
					break;
				case R.id.rb_share_sina:
					intent = ShareUtil.shareMultiplePictureToSina(ShareUtil
							.getImage());
					break;
				}
			}
		});

		Button btn_change = (Button) view.findViewById(R.id.btn_change);
		final TimeCount timeCount = new TimeCount(10000, 1000, btn_change);
		timeCount.start();
		btn_change.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog2.dismiss();
				timeCount.cancel();
				startActivityForResult(intent, 101);
				WXPayEntryActivity.this.finish();
			}
		});

		builder = new AlertDialog.Builder(this);
		builder.setTitle("选择分享平台");
		builder.setView(view);
		builder.setCancelable(false);
		dialog2 = builder.create();

		// dialog2.show();

		startActivity(new Intent(this, ShowShareActivity.class));

		finish();

	}

	// 计时器
	class TimeCount extends CountDownTimer {
		private Button btn = null;

		public TimeCount(long millisInFuture, long countDownInterval, Button btn) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
			this.btn = btn;

		}

		@Override
		public void onFinish() {// 计时完毕时触发
			try {
				// btn.setText("重发(30)");
				// btn.setEnabled(true);
				dialog2.dismiss();
				startActivityForResult(intent, 101);
				WXPayEntryActivity.this.finish();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			try {
				// btn.setEnabled(false);
				// btn.setBackgroundResource(R.color.time_count);
				btn.setText("" + millisUntilFinished / 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}