//package com.yssj.ui.fragment.payback;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.support.v4.app.Fragment;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.huanxin.activity.ChatActivity;
//import com.yssj.ui.activity.MainMenuActivity;
//import com.yssj.ui.activity.logins.LoginActivity;
//import com.yssj.ui.base.BaseFragment;
//import com.yssj.ui.fragment.BackHandledFragment;
//import com.yssj.utils.SharedPreferencesUtil;
//import com.yssj.utils.WXminiAppUtil;
//
///**
// * 售后帮助
// * @author roger
// *
// */
//public class SelledHelpFragment extends BackHandledFragment {
//
//	private boolean hadIntercept;
//	private TextView tvTitle_base;
//	private LinearLayout img_back;
//
//	private Button btn_unsell_customer,btn_selled_customer,btn_complaint_advice,btn_service_self;
//	private RelativeLayout rl_call_phone;
//
//	private Button btn_call; // 拨打电话
//
//
//
//	@Override
//	public View initView() {
//		view = View.inflate(context, R.layout.activity_payback_selled_help, null);
//
//		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
//		tvTitle_base.setText("联系客服");
//		img_back = (LinearLayout) view.findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//
//		btn_unsell_customer = (Button) view.findViewById(R.id.btn_unsell_customer);		// 售前服务
//		btn_unsell_customer.setOnClickListener(this);
//
//		btn_selled_customer = (Button) view.findViewById(R.id.btn_selled_customer);		// 售后服务
//		btn_selled_customer.setOnClickListener(this);
//
//		btn_complaint_advice = (Button) view.findViewById(R.id.btn_complaint_advice);	// 投诉建议
//		btn_complaint_advice.setOnClickListener(this);
//
//		btn_service_self = (Button) view.findViewById(R.id.btn_service_self);		// 自助服务
//		btn_service_self.setOnClickListener(this);
//
//		rl_call_phone = (RelativeLayout) view.findViewById(R.id.rl_call_phone);		// 客服电话
//		rl_call_phone.setOnClickListener(this);
//
//		btn_call = (Button) view.findViewById(R.id.btn_call);
//
//
//		return view;
//	}
//
//	@Override
//	public void initData() {
//
//
//	}
//
//
//	@Override
//	public void onClick(View v) {
//		Intent intent = null;
//		switch (v.getId()) {
//		case R.id.img_back:
//			Fragment mFragment = new PayBackListFragment();
//			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
//			break;
//
//		case R.id.btn_unsell_customer:	// 售前服务
////			intent = new Intent(getActivity(), ChatActivity.class);
////			intent.putExtra("userId", SharedPreferencesUtil.getStringData(context, "kefuNB", "0"));
////			startActivity(intent);
//			WXminiAppUtil.jumpToWXService(this);
//
//			break;
//		case R.id.btn_selled_customer:	// 售后服务
////			intent = new Intent(getActivity(), ChatActivity.class);
////			intent.putExtra("userId", SharedPreferencesUtil.getStringData(context, "kefuNB", "0"));
////			startActivity(intent);
//			WXminiAppUtil.jumpToWXService(this);
//
//			break;
//		case R.id.btn_complaint_advice:	// 投诉建议
////			intent = new Intent(getActivity(), ChatActivity.class);
////			intent.putExtra("userId", SharedPreferencesUtil.getStringData(context, "kefuNB", "0"));
////			startActivity(intent);
//			WXminiAppUtil.jumpToWXService(this);
//
//			break;
//		case R.id.btn_service_self:		// 自助服务
////			intent = new Intent(getActivity(), ChatActivity.class);
////			intent.putExtra("userId", SharedPreferencesUtil.getStringData(context, "kefuNB", "0"));
////			startActivity(intent);
//			WXminiAppUtil.jumpToWXService(getActivity());
//
//			break;
//		case R.id.rl_call_phone:		// 客服电话
//			intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + btn_call.getText().toString().split(":")[1].replace("-", "")));
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			startActivity(intent);
//			break;
//
//		}
//	}
//
//	@Override
//	public boolean onBackPressed() {
//		if (hadIntercept) {
//			return false;
//		} else {
//			Fragment mFragment = new PayBackListFragment();
//			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
//			hadIntercept = true;
//			return true;
//		}
//	}
//
//
//}
