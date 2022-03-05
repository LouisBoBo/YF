package com.yssj.ui.activity;

import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.data.YDBHelper;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.SelectStyleClassDialog;
import com.yssj.ui.dialog.SelectStyleClassDialog.StyleClassListener;
import com.yssj.ui.dialog.SelectSuppleDialog;
import com.yssj.ui.dialog.SelectSuppleDialog.SuppleListener;
import com.yssj.ui.fragment.YaoQingFrendsActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 发布密友圈 品牌和风格类目选择
 * 
 * @date 2017年4月5日下午5:20:32
 */
public class SuppAndClassActivity extends BasicActivity implements OnClickListener, SuppleListener, StyleClassListener {
	private View rel_shop, rel_supple, rel_classfication,v_line;
	private TextView shopNameTv,suppleNameTv, styleClassNameTv, backStepTv, finishStepTv;
	private HashMap<String, String> SuppleHashMap, hashMapStyle, hashMapClass1, hashMapClass2;
	private String label_type = "", label_name = "", label_id = "", style = "", type1 = "", type2 = "", flagShop = "0";
	private YDBHelper dbHelp;
	private static int REQUEST_CHOICE_SHOP = 1000;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_supp_and_class);
		AppManager.getAppManager().addActivity(this);
		context = this;
		dbHelp = new YDBHelper(context);
		initView();
	}

	private void initView() {
		v_line=findViewById(R.id.v_line);
		rel_shop = findViewById(R.id.rel_shop);
		rel_shop.setOnClickListener(this);
		rel_supple = findViewById(R.id.rel_supple);
		rel_classfication = findViewById(R.id.rel_classfication);
		shopNameTv=(TextView) findViewById(R.id.tv_shop_name);
		suppleNameTv = (TextView) findViewById(R.id.tv_supple_name);
		styleClassNameTv = (TextView) findViewById(R.id.tv_classfication_name);
		backStepTv = (TextView) findViewById(R.id.back_step_tv);
		finishStepTv = (TextView) findViewById(R.id.finish_step_tv);
		rel_supple.setOnClickListener(this);
		rel_classfication.setOnClickListener(this);
		backStepTv.setOnClickListener(this);
		finishStepTv.setOnClickListener(this);
		finishStepTv.setClickable(false);
		setDatas();
	}

	SelectSuppleDialog dialog;
	SelectStyleClassDialog dialog2;

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.rel_shop:
			Intent intentYao = new Intent(context, YaoQingFrendsActivity.class);
			intentYao.putExtra("isFabu", true);
			startActivityForResult(intentYao, REQUEST_CHOICE_SHOP);
			((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

			break;
		case R.id.rel_supple:
			if (dialog == null) {
				dialog = new SelectSuppleDialog(context);
			}
			dialog.setSuppleListener(this);
			dialog.show();
			break;
		case R.id.rel_classfication:
			if (dialog2 == null) {
				dialog2 = new SelectStyleClassDialog(context);
			}
			dialog2.setStyleClassListener(this);
			dialog2.show();
			break;
		case R.id.back_step_tv:
			onBackPressed();
			break;
		case R.id.finish_step_tv:
			if (SuppleHashMap != null && "1".equals(label_type)) {
				label_id = SuppleHashMap.get("_id");
			}
			if (hashMapStyle != null) {
				style = hashMapStyle.get("like_id");
			}
			if (hashMapClass1 != null) {
				type1 = hashMapClass1.get("_id");
			}
			if (hashMapClass2 != null) {
				type2 = hashMapClass2.get("_id");
			}
			Intent intent = new Intent();
			intent.putExtra("label_id", label_id);
			intent.putExtra("label_name", label_name);
			intent.putExtra("label_type", label_type);
			intent.putExtra("style", style);
			intent.putExtra("type1", type1);
			intent.putExtra("type2", type2);
			setResult(10000, intent);
			onBackPressed();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1000:
			if (resultCode == 10000) {
				String label_id = data.getStringExtra("supp_label_id");
				String label_name = data.getStringExtra("label_name");
				// String type1 = data.getStringExtra("type1");
				// String type2 = data.getStringExtra("type2");
				// String style = data.getStringExtra("style");
				// String label_type = data.getStringExtra("label_type");
				String shop_code = data.getStringExtra("shop_code");

				Intent intent = new Intent();
				intent.putExtra("label_id", label_id);
				intent.putExtra("label_name", label_name);
				intent.putExtra("label_type", "1");
//				intent.putExtra("style", "0");
//				intent.putExtra("type1", "0");
//				intent.putExtra("type2", "0");
				intent.putExtra("shop_code", shop_code);
				setResult(10001, intent);
				SuppAndClassActivity.this.finish();
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void setSuppleListener(String suppleName, HashMap<String, String> SuppleHashMap, String label_type) {
		if (!TextUtils.isEmpty(suppleName)) {
			suppleNameTv.setText(suppleName);
			suppleNameTv.setVisibility(View.VISIBLE);
			this.SuppleHashMap = SuppleHashMap;
			this.label_type = label_type;
			this.label_name = suppleName;
		} else {
			suppleNameTv.setVisibility(View.GONE);
		}
		if (suppleNameTv.getVisibility() == View.VISIBLE && styleClassNameTv.getVisibility() == View.VISIBLE) {
			finishStepTv.setClickable(true);
			finishStepTv.setTextColor(Color.parseColor("#FF3F8B"));
		} else {
			finishStepTv.setClickable(false);
			finishStepTv.setTextColor(Color.parseColor("#C5C5C5"));
		}
	}

	/*
	 * 风格:{like_pic=/myPreferences/DBLCivOv.png, like_id=2009, t_name=2,
	 * attr_name=韩系, isChecked=1} 类目：_id integer,sort_name text,icon text,p_id
	 * integer, is_show integer,sequence integer, group_flag text
	 *
	 */
	@Override
	public void setStyleClassListener(HashMap<String, String> hashMapStyle, HashMap<String, String> hashMapClass1,
			HashMap<String, String> hashMapClass2) {
		String styleName = "";
		String className1 = "";
		String className2 = "";
		if (hashMapStyle != null) {
			styleName = hashMapStyle.get("attr_name") == null ? "" : hashMapStyle.get("attr_name");
			this.hashMapStyle = hashMapStyle;
		}
		if (hashMapClass1 != null) {
			className1 = hashMapClass1.get("sort_name") == null ? "" : hashMapClass1.get("sort_name");
			this.hashMapClass1 = hashMapClass1;
		}
		if (hashMapClass2 != null) {
			className2 = hashMapClass2.get("sort_name") == null ? "" : hashMapClass2.get("sort_name");
			this.hashMapClass2 = hashMapClass2;
		}
		String styleClassName = (styleName + " " + className1 + "-" + className2).trim();
		if (!TextUtils.isEmpty(styleClassName)) {
			styleClassNameTv.setText(styleClassName);
			styleClassNameTv.setVisibility(View.VISIBLE);
		} else {
			styleClassNameTv.setVisibility(View.GONE);
		}
		if (suppleNameTv.getVisibility() == View.VISIBLE && styleClassNameTv.getVisibility() == View.VISIBLE) {
			finishStepTv.setClickable(true);
			finishStepTv.setTextColor(Color.parseColor("#FF3F8B"));
		} else {
			finishStepTv.setClickable(false);
			finishStepTv.setTextColor(Color.parseColor("#C5C5C5"));
		}
	}

	/**
	 * 初始数据
	 */
	private void setDatas() {
		Intent intent = getIntent();
		if (intent != null) {
			label_id = intent.getStringExtra("label_id");
			label_name = intent.getStringExtra("label_name");
			label_type = intent.getStringExtra("label_type");
			style = intent.getStringExtra("style");
			type1 = intent.getStringExtra("type1");
			type2 = intent.getStringExtra("type2");
			flagShop = intent.getStringExtra("flagShop");
		}
		if ("1".equals(flagShop)) {//商品标签
			if (!TextUtils.isEmpty(label_name)) {
				v_line.setVisibility(View.GONE);
				rel_shop.setVisibility(View.VISIBLE);
				rel_supple.setVisibility(View.GONE);
				rel_classfication.setVisibility(View.GONE);
				shopNameTv.setText(label_name);
				shopNameTv.setVisibility(View.VISIBLE);
			} else {
				shopNameTv.setVisibility(View.GONE);
			}
		} else {
			
			if (!TextUtils.isEmpty(label_name)) {
				v_line.setVisibility(View.VISIBLE);
				rel_shop.setVisibility(View.GONE);
				rel_supple.setVisibility(View.VISIBLE);
				rel_classfication.setVisibility(View.VISIBLE);
				suppleNameTv.setText(label_name);
				suppleNameTv.setVisibility(View.VISIBLE);
			} else {
				suppleNameTv.setVisibility(View.GONE);
			}

			if (!TextUtils.isEmpty(style)) {
				String styleName = "";
				String className1 = "";
				String className2 = "";
				List<HashMap<String, String>> listStyle = dbHelp
						.query("select * from tag_info where p_id = 2 and is_show = 1 and _id = " + style
								+ " order by sequence");// p_id
														// =
														// 2
														// 风格
				if (listStyle != null && listStyle.size() > 0) {
					styleName = listStyle.get(0).get("attr_name");
				}
				String sqlClass1 = "select * from sort_info where p_id = 0 and is_show = 1 and sort_name <> '热卖' and sort_name <> '特卖' and sort_name <> '上新' order by sequence ";
				final List<HashMap<String, String>> firstListSLevel = dbHelp.query(sqlClass1);
				if (firstListSLevel != null && firstListSLevel.size() > 0) {
					className1 = firstListSLevel.get(0).get("sort_name");
				}
				String sql = "select * from sort_info where _id = " + type2 + " order by _id";
				final List<HashMap<String, String>> SecondListSLevel = dbHelp.query(sql);
				if (SecondListSLevel != null && SecondListSLevel.size() > 0) {
					className2 = SecondListSLevel.get(0).get("sort_name");
				}
				String styleClassName = (styleName + " " + className1 + "-" + className2).trim();
				if (!TextUtils.isEmpty(styleClassName)) {
					styleClassNameTv.setText(styleClassName);
					styleClassNameTv.setVisibility(View.VISIBLE);
				} else {
					styleClassNameTv.setVisibility(View.GONE);
				}
				if (suppleNameTv.getVisibility() == View.VISIBLE && styleClassNameTv.getVisibility() == View.VISIBLE) {
					finishStepTv.setClickable(true);
					finishStepTv.setTextColor(Color.parseColor("#FF3F8B"));
				} else {
					finishStepTv.setClickable(false);
					finishStepTv.setTextColor(Color.parseColor("#C5C5C5"));
				}
			}
		}
	}

}
