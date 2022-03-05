//package com.yssj.ui.activity.setting;
//
//import java.util.HashMap;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.entity.UserInfo;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.logins.RegisterFragment;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.DateUtil;
//import com.yssj.utils.DeviceUtils;
//import com.yssj.utils.StringUtils;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.YCache;
//
//public class MemberCardDetailActivity extends BasicActivity {
//
//	private LinearLayout img_back;
//	private TextView tv_card_number, tv_activation_code, tv_time;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		getActionBar().hide();
//		setContentView(R.layout.member_card_activity);
//
//		TextView tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
//		tvTitle_base.setText("会员卡");
//		tvTitle_base.setTextSize(18.0f);
//
//		img_back = (LinearLayout) findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//		tv_card_number = (TextView) findViewById(R.id.tv_card_number);
//		tv_activation_code = (TextView) findViewById(R.id.tv_activation_code);
//		tv_time = (TextView) findViewById(R.id.tv_time);
//
//		initData();
//	}
//
//	@Override
//	public void onClick(View arg0) {
//		// TODO Auto-generated method stub
//		super.onClick(arg0);
//		switch (arg0.getId()) {
//
//		case R.id.img_back:
//			finish();
//			break;
//		}
//	}
//
//	private void initData() {
//		new SAsyncTask<Void, Void, HashMap<String, Object>>(
//				MemberCardDetailActivity.this, R.string.wait) {
//
//			@Override
//			protected HashMap<String, Object> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.queryVipCard(context);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, Object> result, Exception e) {
//				if (null == e) {
//					if (result != null && result.get("status").equals("1")) {
//						tv_card_number.setText("卡号：" + result.get("card_no"));
//						tv_activation_code.setText("激活码："
//								+ result.get("plaintext") + "");
//						if (null == result.get("time")
//								|| "null".equals(result.get("time").toString())
//								|| "".equals(result.get("time").toString())
//								|| "0".equals(result.get("time").toString())) {
//							tv_time.setText("");
//						} else {
//							tv_time.setText("会员有效期至："
//									+ DateUtil.FormatMillisecondForDay(Long
//											.parseLong(result.get("time") + "")));
//						}
//					}
//				}
//			}
//
//		}.execute((Void[]) null);
//	}
//
//}
