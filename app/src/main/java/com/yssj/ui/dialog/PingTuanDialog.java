package com.yssj.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.utils.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

public class PingTuanDialog extends Dialog implements android.view.View.OnClickListener {
	private TextView tv1, tv2, tv3, tv4;
	private Button gobuy1, gobuy2, liebiao;
	private RelativeLayout rl_twobt;
	private ImageView icon_close;
	private Context context;

	private String fen = "";
	private String miao = "";
	private String other = "";
	private String jumpFrom;

	public PingTuanDialog(Context context, int theme, String jumpFrom) {
		super(context, theme);
		this.context = context;
		this.jumpFrom = jumpFrom;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_pingtuan);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);

		gobuy1 = (Button) findViewById(R.id.gobuy1); // 一个按钮时的按钮
		gobuy2 = (Button) findViewById(R.id.gobuy2);
		liebiao = (Button) findViewById(R.id.liebiao);
		icon_close = (ImageView) findViewById(R.id.icon_close);

		rl_twobt = (RelativeLayout) findViewById(R.id.rl_twobt); // 两个按钮

		gobuy1.setOnClickListener(this);
		gobuy2.setOnClickListener(this);
		liebiao.setOnClickListener(this);
		icon_close.setOnClickListener(this);

		// timer = new Timer();
		// timer.schedule(task, 0, 100); // timeTask

		if (jumpFrom.equals("SendPinTaunSucceed")) {
			tv1.setText("拼团申请已发出");
			tv2.setText("拼团申请已经发送到衣蝠平台，如果有其它平台用户加入你的拼团，此团才会生效哦~");
			tv3.setVisibility(View.GONE);
			gobuy2.setText("参与其它拼团");
		}

	}

	Timer timer;

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.gobuy1:

			break;
		case R.id.gobuy2: // 去拼团广场
//			context.startActivity(new Intent(context, GroupsSquareActivity.class));
//			((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
			dismiss();
			break;

		case R.id.liebiao:
			dismiss();
			break;

		case R.id.icon_close:
			dismiss();
			break;

		default:
			break;
		}

	}

	private long time = 30 * 60 * 1000;
	// 倒计时---用于实时显示时间
	TimerTask task = new TimerTask() {
		@Override
		public void run() {

			((Activity) context).runOnUiThread(new Runnable() { // UI thread
				@Override
				public void run() {
					time -= 100;

					String showFen = "";
					String showMiao = "";
					String showOtherTime = "";

					long minute = 0; // 分
					long second = 0; // 秒
					long otherTime = 0; // 后边的

					// 分割成分和秒
					minute = time / 60000;
					second = (time % 60000) / 1000;
					otherTime = time - second / 100;

					if (minute >= 10) {
						showFen = minute + "";

					} else {
						showFen = "0" + minute;
					}

					if (second >= 10) {
						// tv_miao.setText(second + "");
						showMiao = second + "";
					} else {
						showMiao = "0" + second;
					}

					otherTime = (time - minute * 60 * 1000 - second * 1000) / 100;
					showOtherTime = otherTime + "";

					if (second == 0 && minute == 0 && otherTime == 0) {
						showFen = "00";
						showMiao = "00";
						showOtherTime = "0";

						ToastUtil.showShortText(context, "拼团订单已取消！");
						timer.cancel();
					}
					// 倒计时：28分58秒8
					tv4.setText("倒计时：" + showFen + "分" + showMiao + "秒" + showOtherTime);

				}
			});
		}
	};

}
