package com.yssj.ui.dialog;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.yssj.YConstance;
import com.yssj.activity.R;
import com.yssj.data.YDBHelper;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.ShopPageActivity;
import com.yssj.ui.activity.main.FilterResultActivity;
import com.yssj.ui.activity.main.ForceLookActivity;
import com.yssj.ui.activity.main.ForceLookMatchActivity;
import com.yssj.ui.activity.main.SearchResultActivity;
import com.yssj.ui.activity.main.SignActiveShopActivity;
import com.yssj.ui.fragment.circles.SignListAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogSignFenzhongTishi extends Dialog implements OnClickListener {

	private ImageView icon_close;
	private Button gobuy1;
	private long doValuefenzhongOver;
	Timer overtimershow;
	private Context context;

	public long minute; // 剩余分钟
	public long second; // 剩余秒数

	private String jumpFrom = "";
	private String jump = "";

	private TextView tv_fenzhong;
	private TextView tv_miao, text1;

	public DialogSignFenzhongTishi(Context context, int style) {
		super(context, style);
		this.context = context;
		setCanceledOnTouchOutside(true);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_fengzhong_tishi_dialog);

		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		icon_close = (ImageView) findViewById(R.id.icon_close);
		icon_close.setOnClickListener(this);
		gobuy1 = (Button) findViewById(R.id.gobuy1);
		gobuy1.setOnClickListener(this);

		tv_fenzhong = (TextView) findViewById(R.id.tv_fenzhong);
		tv_miao = (TextView) findViewById(R.id.tv_miao);
		text1 = (TextView) findViewById(R.id.text1);


		String id = SignListAdapter.fenzhongIconID.get(YConstance.SCAN_SHOP_TIME);




		String sql = "select * from shop_group_list where _id = " + id;
		List<HashMap<String, String>> listData = new YDBHelper(context).query(sql);


		String app_name = "衣蝠";
		if (listData.size() > 0) {
			app_name = listData.get(0).get("app_name");

		}


		jumpFrom = SignListAdapter.minuteMap.get("jumpFrom");
		jump = app_name;

		// 上个分钟数任务的分钟
		String liulanfeizhong = SignListAdapter.minuteMap.get("liulanfeizhong");
//
//		if (jumpFrom.equals("liulanremaitishi")) { // 浏热卖集合
//			jump = "最热销单品";
//		}
//
//		else if (jumpFrom.equals("liulandapeitishi")) { // 浏览搭配集合
//			jump = "时尚搭配";
//		}
//
//		else if (jumpFrom.equals("liulanzhuantitishi")) { // 浏览专题集合
//			jump = "专题";
//		} else if (jumpFrom.equals("liulangouwuyemian")) { // 购物页面
//			jump = "购物页面";
//		} else if(jumpFrom.equals("liulanhuodongjishitishi")){
//			jump = "特价商品";
//
//		}







//
//		else {
//			jump = SignListAdapter.minuteMap.get("jumpFrom");
//		}





		String textortherjifen = "正在进行浏览" + jump + liulanfeizhong + "分钟任务，只有完成了该任务，才可以开始其他同类型任务哦~";
		SpannableString tttTextjifen = new SpannableString(textortherjifen);
		tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")),
				textortherjifen.length() - 29 - jump.length(), textortherjifen.length() - 29,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);



		text1.setText(tttTextjifen);

		// 上个任务的剩余时间
		doValuefenzhongOver = NewSignCommonDiaolg.doValuefenzhongOver;

		if (null != overtimershow) {
			overtimershow.cancel();
		}
		overtimershow = new Timer();
		overtimershow.schedule(overtaskShow, 0, 1000); // timeTask

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.icon_close: // 关闭
			this.dismiss();
			break;
		case R.id.gobuy1: // 去浏览上次的任务

			if (jumpFrom.equals("liulandapeitishi")) { // 浏览搭配集合
				Intent intent = new Intent(context, ForceLookMatchActivity.class);
				intent.putExtra("type", "1");
				context.startActivity(intent);
				((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

			} else if (jumpFrom.equals("liulanzhuantitishi")) {//浏览专题集合









				Intent intent = new Intent(context, ForceLookMatchActivity.class);
				intent.putExtra("type", "2");
				context.startActivity(intent);
				((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);







			}

			else if (jumpFrom.equals("liulanhuodongjishitishi")) { // 浏览活动商品几分钟
				// 跳转到活动商品
				context.startActivity(new Intent(context, SignActiveShopActivity.class));
				((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

			} else if (jumpFrom.equals("liulangouwuyemian")) { // 浏览购物页面
				// 购物
				// 跳至购物
//				Intent intent2 = new Intent((Activity) context, MainMenuActivity.class);
//				intent2.putExtra("toShop", "toShop");
//				context.startActivity(intent2);
//				((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);



				context.startActivity(new Intent(context, ShopPageActivity.class));
				((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

			}

			else {
				Intent intent = new Intent(context, ForceLookActivity.class);
				intent.putExtra("isFilterConditionActivity", true);
				intent.putExtra("title", SignListAdapter.minuteMap.get("jumpFrom"));
				intent.putExtra("pinJievalue", SignListAdapter.doValueLiulan.split(",")[0]);
				context.startActivity(intent);
				((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

			}

			// if (jumpFrom.equals("liulanwaitaotishi")) { // 浏览外套集合
			// Intent intent = new Intent(context, ForceLookActivity.class);
			//
			// intent.putExtra("title", "甜心的外套");
			// intent.putExtra("id", "1");
			//
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.slide_left_in,
			// R.anim.slide_match);
			//
			// }
			//
			// if (jumpFrom.equals("liulanshangyitishi")) { // 浏上衣集合
			// Intent intent = new Intent(context, ForceLookActivity.class);
			//
			// intent.putExtra("title", "宝宝的上衣");
			// intent.putExtra("id", "2");
			//
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.slide_left_in,
			// R.anim.slide_match);
			//
			// }
			//
			// if (jumpFrom.equals("liulanqunzitishi")) { // 浏览裙子集合
			// Intent intent = new Intent(context, ForceLookActivity.class);
			//
			// intent.putExtra("title", "仙女的裙子");
			// intent.putExtra("id", "3");
			//
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.slide_left_in,
			// R.anim.slide_match);
			//
			// }
			//
			// if (jumpFrom.equals("liulankuzitishi")) { // 浏览裤子集合
			// Intent intent = new Intent(context, ForceLookActivity.class);
			//
			// intent.putExtra("title", "萌妹的裤子");
			// intent.putExtra("id", "4");
			//
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.slide_left_in,
			// R.anim.slide_match);
			//
			// }
			//
			// if (jumpFrom.equals("liulanremaitishi")) { // 浏热卖集合
			// Intent intent = new Intent(context, ForceLookActivity.class);
			//
			// intent.putExtra("title", "最热销单品");
			// intent.putExtra("id", "6");
			//
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.slide_left_in,
			// R.anim.slide_match);
			//
			// }
			// if (jumpFrom.equals("liulantaozhuangtishi")) { // 浏览套装集合
			// Intent intent = new Intent(context, ForceLookActivity.class);
			//
			// intent.putExtra("title", "女王的套装");
			// intent.putExtra("id", "7");
			//
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.slide_left_in,
			// R.anim.slide_match);
			//
			// }
			//
			// if (jumpFrom.equals("liulanxiaowaitao")) { // 浏览小外套几分钟
			//
			// // 跳转到小外套搜索结果
			//
			// HashMap<String, String> item = new HashMap<String, String>();
			//
			// item.put("s_show", "1");
			// item.put("p_id", "1");
			// item.put("icon", "shop/type/xiaowaitao.png");
			// item.put("_id", "11");
			// item.put("group_flag", "");
			// item.put("sequence", "2");
			// item.put("sort_name", "帅气外套");
			//
			// Intent intent = new Intent();
			// intent.setClass(context, SearchResultActivity.class);
			//
			// intent.putExtra("isSign", true);
			//
			// Bundle bundle = new Bundle();
			// bundle.putSerializable("item", item);
			// intent.putExtras(bundle);
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.activity_from_right,
			// R.anim.activity_search_close);
			// }
			//
			// if (jumpFrom.equals("liulanlianyiqun")) { // 浏览连衣裙几分钟
			// // 跳转到小外套搜索结果
			// HashMap<String, String> item = new HashMap<String, String>();
			// item.put("s_show", "1");
			// item.put("p_id", "1");
			// item.put("icon", "shop/type/lianyiqun.png");
			// item.put("_id", "23");
			// item.put("group_flag", "");
			// item.put("sequence", "1");
			// item.put("sort_name", "气质美裙");
			//
			// Intent intent = new Intent();
			// intent.setClass(context, SearchResultActivity.class);
			// intent.putExtra("isSign", true);
			// Bundle bundle = new Bundle();
			// bundle.putSerializable("item", item);
			// intent.putExtras(bundle);
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.activity_from_right,
			// R.anim.activity_search_close);
			// }
			// if (jumpFrom.equals("liulanhanxi")) { // 浏览韩系几分钟
			//
			// HashMap<String, String> map = new HashMap<String, String>();
			// HashMap<String, Object> mapRequest = new HashMap<String,
			// Object>();
			// map.put("aa", "favorite");
			// map.put("is_show", "1");
			// map.put("p_id", "5");
			// map.put("icon", "");
			// map.put("isChecked", "1");
			// map.put("e_name", "");
			// map.put("_id", "29");
			// map.put("sequence", "29");
			// map.put("attr_name", "韩系");
			// mapRequest.put(map.get("aa"), map);
			// Intent intent = new Intent(context, FilterResultActivity.class);
			// intent.putExtra("isSign", true);
			// intent.putExtra("isFilterConditionActivity", true);
			// intent.putExtra("shop_name", "甜美韩系");
			// intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
			// Bundle bundle = new Bundle();
			// bundle.putSerializable("condition", mapRequest);
			// bundle.putString("id", 6 + "");// 默认筛选热卖
			// bundle.putString("title", "热卖");
			// intent.putExtras(bundle);
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.slide_left_in,
			// R.anim.slide_match);
			// // {favorite={aa=favorite, is_show=1, p_id=5, icon=,
			// // isChecked=1, e_name=, _id=29, sequence=29,
			// // attr_name=韩系}}
			// }
			// if (jumpFrom.equals("liulanoouxi")) { // 浏览欧系几分钟
			//
			// HashMap<String, String> map = new HashMap<String, String>();
			// HashMap<String, Object> mapRequest = new HashMap<String,
			// Object>();
			// map.put("aa", "favorite");
			// map.put("is_show", "1");
			// map.put("p_id", "5");
			// map.put("icon", "");
			// map.put("isChecked", "1");
			// map.put("e_name", "");
			// map.put("_id", "30");
			// map.put("sequence", "30");
			// map.put("attr_name", "欧系");
			// mapRequest.put(map.get("aa"), map);
			// Intent intent = new Intent(context, FilterResultActivity.class);
			// intent.putExtra("isFilterConditionActivity", true);
			// intent.putExtra("shop_name", "欧美潮范");
			// intent.putExtra("isSign", true);
			// intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
			// Bundle bundle = new Bundle();
			// bundle.putSerializable("condition", mapRequest);
			// bundle.putString("id", 6 + "");// 默认筛选热卖
			// bundle.putString("title", "热卖");
			// intent.putExtras(bundle);
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.slide_left_in,
			// R.anim.slide_match);
			// // {favorite={aa=favorite, is_show=1, p_id=5, icon=,
			// // isChecked=1, e_name=, _id=30, sequence=30,
			// // attr_name=欧系}}
			// }
			// if (jumpFrom.equals("liulanshihui")) { // 浏览实惠几分钟
			//
			// HashMap<String, String> map = new HashMap<String, String>();
			// HashMap<String, Object> mapRequest = new HashMap<String,
			// Object>();
			// map.put("aa", "fix_price");
			// map.put("is_show", "1");
			// map.put("p_id", "3");
			// map.put("icon", "");
			// map.put("isChecked", "1");
			// map.put("e_name", "");
			// map.put("_id", "20");
			// map.put("sequence", "20");
			// map.put("attr_name", "实惠");
			// mapRequest.put(map.get("aa"), map);
			// Intent intent = new Intent(context, FilterResultActivity.class);
			// intent.putExtra("isFilterConditionActivity", true);
			// intent.putExtra("shop_name", "超值特惠");
			// intent.putExtra("isSign", true);
			// intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
			// Bundle bundle = new Bundle();
			// bundle.putSerializable("condition", mapRequest);
			// bundle.putString("id", 6 + "");// 默认筛选热卖
			// bundle.putString("title", "热卖");
			// intent.putExtras(bundle);
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.slide_left_in,
			// R.anim.slide_match);
			// // {fix_price={aa=fix_price, is_show=1, p_id=3,
			// // icon=, isChecked=1, e_name=, _id=20, sequence=20,
			// // attr_name=实惠}}
			// }
			// if (jumpFrom.equals("liullanzhonggaoduan")) { // 浏览中高端几分钟
			//
			// HashMap<String, String> map = new HashMap<String, String>();
			// HashMap<String, Object> mapRequest = new HashMap<String,
			// Object>();
			// map.put("aa", "fix_price");
			// map.put("is_show", "1");
			// map.put("p_id", "3");
			// map.put("icon", "");
			// map.put("isChecked", "1");
			// map.put("e_name", "");
			// map.put("_id", "22");
			// map.put("sequence", "22");
			// map.put("attr_name", "轻奢");
			// mapRequest.put(map.get("aa"), map);
			// Intent intent = new Intent(context, FilterResultActivity.class);
			// intent.putExtra("isFilterConditionActivity", true);
			// intent.putExtra("shop_name", "流行趋势");
			// intent.putExtra("isSign", true);
			// intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
			// Bundle bundle = new Bundle();
			// bundle.putSerializable("condition", mapRequest);
			// bundle.putString("id", 6 + "");// 默认筛选热卖
			// bundle.putString("title", "热卖");
			// intent.putExtras(bundle);
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.slide_left_in,
			// R.anim.slide_match);
			// // map={fix_price={aa=fix_price, is_show=1, p_id=3,
			// // icon=, isChecked=1, e_name=, _id=22, sequence=22,
			// // attr_name=轻奢}}
			// }
			// if (jumpFrom.equals("liulantianmeikeai")) { // 浏览甜美可爱几分钟
			// HashMap<String, String> map = new HashMap<String, String>();
			// HashMap<String, Object> mapRequest = new HashMap<String,
			// Object>();
			// map.put("aa", "style");
			// map.put("is_show", "1");
			// map.put("p_id", "101");
			// map.put("icon", "");
			// map.put("isChecked", "1");
			// map.put("e_name", "");
			// map.put("_id", "105");
			// map.put("sequence", "3");
			// map.put("attr_name", "甜美可爱");
			// mapRequest.put(map.get("aa"), map);
			// Intent intent = new Intent(context, FilterResultActivity.class);
			// intent.putExtra("isFilterConditionActivity", true);
			// intent.putExtra("shop_name", "萌系可爱风");
			// intent.putExtra("isSign", true);
			// intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
			// Bundle bundle = new Bundle();
			// bundle.putSerializable("condition", mapRequest);
			// bundle.putString("id", 6 + "");// 默认筛选热卖
			// bundle.putString("title", "热卖");
			// intent.putExtras(bundle);
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.slide_left_in,
			// R.anim.slide_match);
			// // map={style={aa=style, is_show=1, p_id=101, icon=,
			// // isChecked=1, e_name=, _id=105, sequence=3,
			// // attr_name=甜美可爱}}
			// }
			// if (jumpFrom.equals("liulantongqingmingyuan")) { // 浏览通勤名媛几分钟
			//
			// HashMap<String, String> map = new HashMap<String, String>();
			// HashMap<String, Object> mapRequest = new HashMap<String,
			// Object>();
			// map.put("aa", "style");
			// map.put("is_show", "1");
			// map.put("p_id", "101");
			// map.put("icon", "");
			// map.put("isChecked", "1");
			// map.put("e_name", "");
			// map.put("_id", "103");
			// map.put("sequence", "1");
			// map.put("attr_name", "通勤名媛");
			// mapRequest.put(map.get("aa"), map);
			// Intent intent = new Intent(context, FilterResultActivity.class);
			// intent.putExtra("isFilterConditionActivity", true);
			// intent.putExtra("shop_name", "简约通勤");
			// intent.putExtra("isSign", true);
			// intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
			// Bundle bundle = new Bundle();
			// bundle.putSerializable("condition", mapRequest);
			// bundle.putString("id", 6 + "");// 默认筛选热卖
			// bundle.putString("title", "热卖");
			// intent.putExtras(bundle);
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.slide_left_in,
			// R.anim.slide_match);
			// // map={style={aa=style, is_show=1, p_id=101, icon=,
			// // isChecked=1, e_name=, _id=103, sequence=1,
			// // attr_name=通勤名媛}}
			// }
			// if (jumpFrom.equals("liulanyundongxiuxian")) { // 浏览运动休闲几分钟
			//
			// HashMap<String, String> map = new HashMap<String, String>();
			// HashMap<String, Object> mapRequest = new HashMap<String,
			// Object>();
			// map.put("aa", "style");
			// map.put("is_show", "1");
			// map.put("p_id", "101");
			// map.put("icon", "");
			// map.put("isChecked", "1");
			// map.put("e_name", "");
			// map.put("_id", "112");
			// map.put("sequence", "4");
			// map.put("attr_name", "运动休闲");
			// mapRequest.put(map.get("aa"), map);
			// Intent intent = new Intent(context, FilterResultActivity.class);
			// intent.putExtra("isFilterConditionActivity", true);
			// intent.putExtra("shop_name", "运动休闲");
			// intent.putExtra("isSign", true);
			// intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
			// Bundle bundle = new Bundle();
			// bundle.putSerializable("condition", mapRequest);
			// bundle.putString("id", 6 + "");// 默认筛选热卖
			// bundle.putString("title", "热卖");
			// intent.putExtras(bundle);
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.slide_left_in,
			// R.anim.slide_match);
			// // map={style={aa=style, is_show=1, p_id=101, icon=,
			// // isChecked=1, e_name=, _id=112, sequence=4,
			// // attr_name=运动休闲}}
			// }
			// if (jumpFrom.equals("liulanjianyuebaida")) { // 浏览简约百搭几分钟
			//
			// HashMap<String, String> map = new HashMap<String, String>();
			// HashMap<String, Object> mapRequest = new HashMap<String,
			// Object>();
			// map.put("aa", "style");
			// map.put("is_show", "1");
			// map.put("p_id", "101");
			// map.put("icon", "");
			// map.put("isChecked", "1");
			// map.put("e_name", "");
			// map.put("_id", "750");
			// map.put("sequence", "5");
			// map.put("attr_name", "简约百搭");
			// mapRequest.put(map.get("aa"), map);
			// Intent intent = new Intent(context, FilterResultActivity.class);
			// intent.putExtra("isFilterConditionActivity", true);
			// intent.putExtra("shop_name", "经典百搭");
			// intent.putExtra("isSign", true);
			// intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
			// Bundle bundle = new Bundle();
			// bundle.putSerializable("condition", mapRequest);
			// bundle.putString("id", 6 + "");// 默认筛选热卖
			// bundle.putString("title", "热卖");
			// intent.putExtras(bundle);
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.slide_left_in,
			// R.anim.slide_match);
			// // map={style={aa=style, is_show=1, p_id=101, icon=,
			// // isChecked=1, e_name=, _id=750, sequence=5,
			// // attr_name=简约百搭}}
			//
			// }
			// if (jumpFrom.equals("liulanwenyifugu")) { // 浏览文艺复古几分钟
			//
			// HashMap<String, String> map = new HashMap<String, String>();
			// HashMap<String, Object> mapRequest = new HashMap<String,
			// Object>();
			// map.put("aa", "style");
			// map.put("is_show", "1");
			// map.put("p_id", "101");
			// map.put("icon", "");
			// map.put("isChecked", "1");
			// map.put("e_name", "");
			// map.put("_id", "102");
			// map.put("sequence", "0");
			// map.put("attr_name", "文艺复古");
			// mapRequest.put(map.get("aa"), map);
			// Intent intent = new Intent(context, FilterResultActivity.class);
			// intent.putExtra("isFilterConditionActivity", true);
			// intent.putExtra("shop_name", "文艺复古");
			// intent.putExtra("isSign", true);
			// intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
			// Bundle bundle = new Bundle();
			// bundle.putSerializable("condition", mapRequest);
			// bundle.putString("id", 6 + "");// 默认筛选热卖
			// bundle.putString("title", "热卖");
			// intent.putExtras(bundle);
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.slide_left_in,
			// R.anim.slide_match);
			// // map={style={aa=style, is_show=1, p_id=101, icon=,
			// // isChecked=1, e_name=, _id=102, sequence=0,
			// // attr_name=文艺复古}}
			// }
			// if (jumpFrom.equals("liulanqingtongzhuang")) { // 浏览通勤装几分钟
			//
			// HashMap<String, String> map = new HashMap<String, String>();
			// HashMap<String, Object> mapRequest = new HashMap<String,
			// Object>();
			// map.put("aa", "occasion");
			// map.put("is_show", "1");
			// map.put("p_id", "4");
			// map.put("icon", "");
			// map.put("isChecked", "1");
			// map.put("e_name", "");
			// map.put("_id", "24");
			// map.put("sequence", "24");
			// map.put("attr_name", "通勤装");
			// mapRequest.put(map.get("aa"), map);
			// Intent intent = new Intent(context, FilterResultActivity.class);
			// intent.putExtra("isFilterConditionActivity", true);
			// intent.putExtra("shop_name", "上班族必备");
			// intent.putExtra("isSign", true);
			// intent.putExtra("isWhere", "");// 运营统计使用 暂时传空字符串
			// Bundle bundle = new Bundle();
			// bundle.putSerializable("condition", mapRequest);
			// bundle.putString("id", 6 + "");// 默认筛选热卖
			// bundle.putString("title", "热卖");
			// intent.putExtras(bundle);
			// context.startActivity(intent);
			// ((Activity)
			// context).overridePendingTransition(R.anim.slide_left_in,
			// R.anim.slide_match);
			// // map={occasion={aa=occasion, is_show=1, p_id=4,
			// // icon=, isChecked=1, e_name=, _id=24, sequence=24,
			// // attr_name=通勤装}}
			// }

			dismiss();

			break;

		default:
			break;
		}

	}

	// 倒计时---用于实时显示时间
	TimerTask overtaskShow = new TimerTask() {
		@Override
		public void run() {

			((Activity) context).runOnUiThread(new Runnable() { // UI thread

				@Override
				public void run() {
					doValuefenzhongOver -= 1000;

					if (doValuefenzhongOver <= 0) {
						tv_miao.setText("00");
						tv_fenzhong.setText("00");
						overtimershow.cancel();
						dismiss();
						return;
					}

					// 分割成分和秒
					minute = doValuefenzhongOver / 60000;
					second = (doValuefenzhongOver % 60000) / 1000;
					// minute剩余分钟 second剩余秒
					// ToastUtil.showShortText(context, minute+"分钟"+
					// second+"秒");

					if (minute >= 10) {
						tv_fenzhong.setText(minute + "");

					} else {
						tv_fenzhong.setText("0" + minute);

					}

					if (second >= 10) {
						tv_miao.setText(second + "");
					} else {
						tv_miao.setText("0" + second);
					}

					if (second == 0) {
						tv_miao.setText("00");
						tv_fenzhong.setText("00");
						overtimershow.cancel();
					}

					if (doValuefenzhongOver == 0) {
						overtimershow.cancel();
					}

				}
			});
		}
	};

	// SignRefreshDataListener refreshData;
	// public interface SignRefreshDataListener {
	// public void timeOut();
	// }

}
