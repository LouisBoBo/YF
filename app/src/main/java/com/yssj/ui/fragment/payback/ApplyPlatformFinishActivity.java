package com.yssj.ui.fragment.payback;

import com.yssj.activity.R;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.ui.activity.payback.PaybackCommonFragmentActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.WXminiAppUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ApplyPlatformFinishActivity extends BasicActivity implements
		OnClickListener {
	private LinearLayout img_back;
	private TextView tvTitle_base;
	private ImageView img_right_icon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.apply_platform_finish);
		initView();
	}

	private void initView() {
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("申请平台介入");
		img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		img_right_icon.setVisibility(View.GONE);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back: // 返回
			// finish();
//			Intent intent2 = new Intent(this,
//					PaybackCommonFragmentActivity.class);
//			intent2.putExtra("flag", "payBackListFragment");
//			startActivity(intent2);
			finish();
			break;

		case R.id.img_right_icon:// 消息盒子
			WXminiAppUtil.jumpToWXmini(this);

			break;

		default:
			break;
		}

	}
}
