package com.yssj.ui.dialog;

import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.custom.view.MatchNavLeft;
import com.yssj.custom.view.MatchNavLeftWhite;
import com.yssj.entity.Shop;
import com.yssj.ui.activity.main.ForceLookMatchActivity;
import com.yssj.utils.DP2SPUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class XunBaoMatchDialog extends Dialog implements android.view.View.OnClickListener {
	
	private Context context;
	private ImageView iv;
//	private List<HashMap<String, Object>> dataList;
	private String shop_name ;
	private String shop_x;
	private String shop_y ;
	private int from;//0 强制浏览 
	private RelativeLayout containsRl;
	private ImageView gesIv;
	public XunBaoMatchDialog(Context context,String shop_name,String shop_x,String shop_y,int from) {
		 super(context, android.R.style.Theme);
		 setOwnerActivity((Activity)context);
		setCanceledOnTouchOutside(false);
		this.context = context;
		this.shop_name = shop_name;
		this.shop_x = shop_x;
		this.shop_y = shop_y;
		this.from = from;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xunbao_match_dialog);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		initView();
	}

	private void initView() {
		iv = (ImageView) findViewById(R.id.but_know);
		iv.setOnClickListener(this);
		containsRl = (RelativeLayout) findViewById(R.id.Match_contains_rl);
		gesIv = (ImageView) findViewById(R.id.shoushi_match);
		showGuide();
	}
	
	private void showGuide() {
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		if(from == 0){//标签top间距 要计算上 浏览有奖 和签到说明 
			LinearLayout.LayoutParams p =(LinearLayout.LayoutParams) containsRl.getLayoutParams();
			p.setMargins(0, DP2SPUtil.dp2px(context, 80), 0, 0);
			containsRl.setLayoutParams(p);
		}
//		List<HashMap<String, Object>> collocation_shop_list = 
//				(List<HashMap<String, Object>>) dataList.get(0).get("collocation_shop");
//		String shop_name = (String) collocation_shop_list.get(0).get("shop_name");
//		String shop_x = (String) collocation_shop_list.get(0).get("shop_x");
//		String shop_y = (String) collocation_shop_list.get(0).get("shop_y");
		double X = 0.0;
		double Y = 0.0;
		if (!TextUtils.isEmpty(shop_y) && !TextUtils.isEmpty(shop_x)) {
			X = Double.valueOf(shop_x);
			Y = Double.valueOf(shop_y);
		}

		X = X == 0 ? 0.45 : X;
		Y = Y == 0 ? 0.35 : Y;
		MatchNavLeftWhite matchNavLeft = new MatchNavLeftWhite(context);
		matchNavLeft.setTextView(Shop.getShopNameStrNew(shop_name));
		matchNavLeft.measure(0, 0);
		param.leftMargin = (int) (ForceLookMatchActivity.width * X - matchNavLeft.getMeasuredWidth() - 8);
		param.topMargin = (int) (ForceLookMatchActivity.width / 2 + (Y - 0.5) * ForceLookMatchActivity.width  - matchNavLeft.getMeasuredHeight() / 2 + 8);
		matchNavLeft.setLayoutParams(param);// 设置布局参数
		containsRl.addView(matchNavLeft);// RelativeLayout添加子View
		gesIv.measure(0, 0);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); 
		lp.setMargins((int) (ForceLookMatchActivity.width * X-gesIv.getMeasuredWidth()), 0, 0, 0); 
		gesIv.setLayoutParams(lp);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.but_know:
			this.dismiss();
			break;

		default:
			break;
		}
		
	}
	
}
