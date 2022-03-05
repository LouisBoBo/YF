//package com.yssj.ui.activity.league;
//
//import java.io.Serializable;
//import java.util.HashMap;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.ui.base.BasicActivity;
//
//public class VipDiscountActivity extends BasicActivity{
//
//	private EditText et_vip_discount;
//	private EditText et_vip_discount_explain;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.vip_discount);
//		findViewById(R.id.img_back).setOnClickListener(this);
//		((TextView)findViewById(R.id.tvTitle_base)).setText("会员折扣");
//
//		et_vip_discount = (EditText) findViewById(R.id.et_vip_discount);
//		et_vip_discount_explain = (EditText) findViewById(R.id.et_vip_discount_explain);
//	}
//
//	@Override
//	public void finish() {
//		String vipDiscount = et_vip_discount.getText().toString().trim();
//		String vipDiscountExplain = et_vip_discount_explain.getText().toString().trim();
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("vipDiscount", vipDiscount);
//		map.put("vipDiscountExplain", vipDiscountExplain);
//		Intent intent = getIntent();
//		Bundle bundle = new Bundle();
//		bundle.putSerializable("map", (Serializable) map);
//		intent.putExtras(bundle);
//		setResult(RESULT_OK, intent);
//		super.finish();
//	}
//
//	@Override
//	public void onClick(View v) {
//		super.onClick(v);
//		switch (v.getId()) {
//		case R.id.img_back:
//			this.finish();
//			break;
//
//		default:
//			break;
//		}
//	}
//}
