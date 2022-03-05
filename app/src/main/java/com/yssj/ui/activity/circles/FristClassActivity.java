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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class FristClassActivity extends BasicActivity implements OnClickListener {
	private LinearLayout mBack;
	private LinearLayout mLlContain;
	public static FristClassActivity instance;
//	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_frist_class);
//		mContext=this;
		instance = this;
		initView();
		initData();
	}

	private void initData() {
		YDBHelper dbHelp = new YDBHelper(this);
		// String sql = "select * from type_tag where type = " + type + " and
		// class_type = 1 order by _id";
		String sql = "select * from sort_info where p_id = 0 and is_show = 1 and sort_name <> '热卖' and sort_name <> '特卖' and sort_name <> '上新' order by sequence ";
		final List<HashMap<String, String>> listSLevel = dbHelp.query(sql);
		if (listSLevel != null) {
			mLlContain.removeAllViews();
			for (int i = 0; i < listSLevel.size(); i++) {
				TextView textView = new TextView(this);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						DP2SPUtil.dp2px(instance, 48));
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
					Intent	intent = new Intent(instance, SecondClassActivity.class);
						intent.putExtra("type", type2);
						intent.putExtra("type1_name", str);
						startActivityForResult(intent, 40001);
					}
				});
			}
		}
		
	}

	private void initView() {
		mBack = (LinearLayout) findViewById(R.id.img_back);
		mBack.setOnClickListener(this);
		mLlContain=(LinearLayout) findViewById(R.id.frist_class_contain);

	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 40001:
			if (resultCode == 30002) {
				String type = data.getStringExtra("type");
				String str = data.getStringExtra("class_name");
				Intent intent = new Intent();
				intent.putExtra("type", type);
				intent.putExtra("type1_name", data.getStringExtra("type1_name"));
				intent.putExtra("class_name", "" + str);
				String type2=data.getStringExtra("type2");
				intent.putExtra("type2", ""+type2);
				setResult(30003, intent);
				FristClassActivity.this.finish();
			}
			break;

		default:
			break;
		}
	}
}
