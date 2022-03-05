package com.yssj.ui.activity.circles;

import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.data.YDBHelper;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DP2SPUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class SecondClassActivity extends BasicActivity implements OnClickListener {
	private LinearLayout mBack;
	private LinearLayout mLlContain;
	private String type;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_second_class);
		mContext = this;
		type = getIntent().getStringExtra("type");
		initView();
		initData();
	}

	private void initData() {
		YDBHelper dbHelp = new YDBHelper(this);
		// String sql = "select * from type_tag where type = " + type + " and
		// class_type = 1 order by _id";
		String sql = "select * from sort_info where p_id = " + type + " order by _id";
		final List<HashMap<String, String>> listSLevel = dbHelp.query(sql);
		if (listSLevel != null) {
			mLlContain.removeAllViews();
			for (int i = 0; i < listSLevel.size(); i++) {
				TextView textView = new TextView(this);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						DP2SPUtil.dp2px(mContext, 48));
				textView.setTextSize(15);
				final String str = listSLevel.get(i).get("sort_name");
				final String type2 = ""+listSLevel.get(i).get("_id");
				textView.setText(str);
				textView.setGravity(Gravity.CENTER_VERTICAL);
				textView.setTextColor(Color.parseColor("#3e3e3e"));
				mLlContain.addView(textView, params);
				View view = new View(this);
				LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						DP2SPUtil.dp2px(this, 1));
				view.setBackgroundColor(Color.parseColor("#e5e5e5"));
				mLlContain.addView(view, params2);
				textView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.putExtra("type", type);
						intent.putExtra("class_name", "" + str);
						intent.putExtra("type2", ""+type2);
						intent.putExtra("type1_name", SecondClassActivity.this.getIntent().getStringExtra("type1_name"));
						setResult(30002, intent);
//						if (FristClassActivity.instance != null) {
//							FristClassActivity.instance.finish();
//							FristClassActivity.instance = null;
//						}
						SecondClassActivity.this.finish();
					}
				});
			}
		}

	}

	private void initView() {
		mBack = (LinearLayout) findViewById(R.id.img_back);
		mBack.setOnClickListener(this);
		mLlContain = (LinearLayout) findViewById(R.id.second_class_contain);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		default:
			break;
		}

	}
}
