package com.yssj.custom.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.umeng.socialize.utils.Log;
import com.yssj.YConstance.SizeIndex;
import com.yssj.activity.R;
import com.yssj.data.YDBHelper;
import com.yssj.entity.Shop;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SizeView extends LinearLayout {

	private YDBHelper helper;

	private TextView tv_shopconnect;
	private LinearLayout viewContainer;

	private Shop shop;

	private Context context;

	private boolean isRefresh = false;

	private MyGridView sizeTag;

	private ImageView mealIndex;

	private TextView title, content;
	private TextView tv_shop_code;
	private LinearLayout mLlFristRow;
	private TextView mRowLong, mRowShort;
	// private MyGridAdapter adapter1,adapter2,adapter3;

	public SizeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.fragment_size, this);
		helper = new YDBHelper(context);
		mLlFristRow = (LinearLayout) findViewById(R.id.size_ll_frist_row);
		mRowLong = (TextView) findViewById(R.id.size_tag_item_long);
		mRowShort = (TextView) findViewById(R.id.size_tag_item_short);
		viewContainer = (LinearLayout) findViewById(R.id.viewContainer);

		tv_shopconnect = (TextView) findViewById(R.id.tv_shopconnect);

		sizeTag = (MyGridView) findViewById(R.id.size_tag);

		mealIndex = (ImageView) findViewById(R.id.meal_index);

		title = (TextView) findViewById(R.id.title);
		content = (TextView) findViewById(R.id.content);
		tv_shop_code = (TextView) findViewById(R.id.tv_shop_code);
		isRefresh = true;
	}

	public void setContent(Shop shop, boolean isMeal, int position) {

		this.shop = shop;
		if (shop != null) {
			tv_shop_code.setText("商品编号:" + shop.getShop_code());
		}
		if (shop != null && !TextUtils.isEmpty(shop.getContent())) {
			tv_shopconnect.setText(shop.getContent());

		}
		if (isRefresh) {
			isRefresh = false;
			if (isMeal) {
				findViewById(R.id.size_tag_ly).setVisibility(View.GONE);
				mealIndex.setVisibility(View.VISIBLE);
				findViewById(R.id.size_hint_s).setVisibility(View.GONE);
				findViewById(R.id.iv_chimabiao).setVisibility(View.GONE);
				findViewById(R.id.iv_size).setVisibility(View.GONE);
				findViewById(R.id.tv_shop_code).setVisibility(View.GONE);
				switch (position) {
				case 0: {
					mealIndex.setImageResource(R.drawable.shop_tag_one);
					addMealView(viewContainer, shop.getShop_attr());

				}
					break;
				case 1: {
					mealIndex.setImageResource(R.drawable.shop_tag_two);
					addMealView(viewContainer, shop.getShop_attr());
				}
					break;
				case 2: {
					mealIndex.setImageResource(R.drawable.shop_tag_three);
					addMealView(viewContainer, shop.getShop_attr());
				}
					break;

				default:
					break;
				}

			} else {
				findViewById(R.id.size_tag_ly).setVisibility(View.VISIBLE);
				mealIndex.setVisibility(View.GONE);
				List<HashMap<String, String>> list1;
				if (shop.getShop_tag() != null && shop.getShop_tag().length() > 1 && shop.getShop_tag().endsWith(",")) {
					String substring = (shop.getShop_tag()).substring(0, shop.getShop_tag().length() - 1);
					// list1 = helper.query("select * from tag_info where _id
					// in("+substring+") and is_show=1 order by sequence");
					list1 = helper
							.query("select t1.* from tag_info t1 LEFT JOIN tag_info t2 ON(t2._id = t1.p_id) where (t1._id in ("
									+ substring + ") and t1.is_show=1 ) order by t2.sequence");

				} else {
					// list1 = helper.query("select * from tag_info where _id
					// in("+shop.getShop_tag()+") and is_show=1 order by
					// sequence");
					list1 = helper
							.query("select t1.* from tag_info t1 LEFT JOIN tag_info t2 ON(t2._id = t1.p_id) where (t1._id in ("
									+ shop.getShop_tag() + ") and t1.is_show=1 ) order by t2.sequence");
				}
				List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
				List<HashMap<String, String>> listNew = new ArrayList<HashMap<String, String>>();
				if (list1.size() > 0) {
					list.add(list1.get(0));
				}
				if (list1.size() > 7) {
					list.add(list1.get(7));
				}
				if (list1.size() > 3) {
					list.add(list1.get(3));
				}
				if (list1.size() > 6) {
					list.add(list1.get(6));
				}
				if (list1.size() > 4) {
					list.add(list1.get(4));
				}
				if (list1.size() > 1) {
					list.add(list1.get(1));
				}
				if (list1.size() > 2) {
					list.add(list1.get(2));
				}
				if (list1.size() > 5) {
					list.add(list1.get(5));
				}
				if (list1.size() > 8) {
					list.add(list1.get(8));
				}
				if (list1.size() > 9) {
					list.add(list1.get(9));
				}
				if (list1.size() > 10) {
					list.add(list1.get(10));
				}
				if (list1.size() > 11) {
					list.add(list1.get(11));
				}
				if (list1.size() > 12) {
					list.add(list1.get(12));
				}
				if (list1.size() > 13) {
					list.add(list1.get(13));
				}
				if (list1.size() > 14) {
					list.add(list1.get(14));
				}
				// if(list1.size()>15){
				// for (int i = 15; i < list1.size(); i++) {
				// list.add(list1.get(i));
				// }
				// }
				boolean flag1 = false;// 成份含量判断标志
				String str1 = "";
				boolean flag2 = false;// 羽绒服充绒量判断标志
				String str2 = "";
				boolean flag3 = false;// 第一个标签
				String str3 = "";
				if (list.size() == 0) {
					sizeTag.setAdapter(new MyGridAdapter(list));
					mLlFristRow.setVisibility(View.GONE);
				} else if (list.size() == 1) {
					if ("10".equals(list.get(0).get("p_id"))) {// 成份含量
						flag1 = true;
						str1 = list.get(0).get("attr_name");
						mRowLong.setText("主面料成份含量:" + str1);
						mRowShort.setVisibility(View.INVISIBLE);
					} else if ("115".equals(list.get(0).get("p_id"))) {// 羽绒服充绒量
						flag2 = true;
						str2 = list.get(0).get("attr_name");
						mRowLong.setText("羽绒服充绒量:" + str2);
						mRowShort.setVisibility(View.INVISIBLE);
					} else {
						sizeTag.setAdapter(new MyGridAdapter(list));
					}
				} else {
					for (int i = 0; i < list.size(); i++) {
						if ("10".equals(list.get(i).get("p_id"))) {
							flag1 = true;
							str1 = list.get(i).get("attr_name");
						} else if ("115".equals(list.get(i).get("p_id"))) {
							flag2 = true;
							str2 = list.get(i).get("attr_name");
						} else {
							listNew.add(list.get(i));
						}
					}
					if (listNew.size() > 0) {
						flag3 = true;
						str3 = listNew.get(0).get("attr_name");
						listNew.remove(0);
					}
					if (flag2 && flag3) {
						mRowLong.setText("羽绒服充绒量:" + str2);
						mRowShort.setText(str3);
					} else if (flag2) {
						mRowLong.setText("羽绒服充绒量:" + str2);
						mRowShort.setVisibility(View.INVISIBLE);
					} else if (flag1 && flag3) {
						mRowLong.setText("主面料成份含量:" + str1);
						mRowShort.setText(str3);
					} else if (flag1) {
						mRowLong.setText("主面料成份含量:" + str1);
						mRowShort.setVisibility(View.INVISIBLE);
					} else {
						mLlFristRow.setVisibility(View.GONE);
					}
					sizeTag.setAdapter(new MyGridAdapter(listNew));
				}
				// sizeTag.setAdapter(new MyGridAdapter(list));
				refreshSFView();
			}
			/**
			 * switch (position) { case 0: {
			 * findViewById(R.id.size_hint_s).setVisibility(View.VISIBLE);
			 * if(isMeal){ mealIndex.setImageResource(R.drawable.shop_tag_one);
			 * findViewById(R.id.size_tag_ly).setVisibility(View.GONE); }else{
			 * 
			 * }
			 * 
			 * } break; case 1: {
			 * findViewById(R.id.size_tag_ly).setVisibility(View.GONE);
			 * findViewById(R.id.size_hint_s).setVisibility(View.GONE);
			 * mealIndex.setImageResource(R.drawable.shop_tag_two);
			 * sizeTag.setVisibility(View.GONE); } break; case 2: {
			 * findViewById(R.id.size_tag_ly).setVisibility(View.GONE);
			 * findViewById(R.id.size_hint_s).setVisibility(View.GONE);
			 * mealIndex.setImageResource(R.drawable.shop_tag_three);
			 * 
			 * 
			 * 
			 * } break;
			 * 
			 * default: break; }
			 */
		}
	}

	private void addMealView(LinearLayout container, List<String[]> listAll) {
		container.removeAllViews();
		LayoutInflater inflater = LayoutInflater.from(context);
		for (int i = 0; i < listAll.size(); i++) {
			View convertView = inflater.inflate(R.layout.meal_size_view, null);
			convertView.setLayoutParams(
					new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			String[] attrs = listAll.get(i);
			TextView title = (TextView) convertView.findViewById(R.id.title);
			TextView content = (TextView) convertView.findViewById(R.id.content);
			String x = "";
			for (int s = 2; s < attrs.length; s++) {
				x += attrs[s] + ",";
			}
			List<HashMap<String, String>> titlelist = helper
					.query("select * from attr_info where _id in (" + attrs[1] + ")");

			title.setText(titlelist.size() > 0 ? titlelist.get(0).get("attr_name") : "");

			List<HashMap<String, String>> list = helper
					.query("select * from attr_info where _id in (" + x.substring(0, x.length() - 1) + ")");
			if (list != null && list.size() > 0) {
				String txt = "";
				List<String> ss = new ArrayList<String>();
				for (int j = 0; j < list.size(); j++) {
					if (ss.contains(list.get(j).get("attr_name"))) {
						continue;
					}
					ss.add(list.get(j).get("attr_name"));
					txt += list.get(j).get("attr_name") + ",";
				}
				if (!TextUtils.isEmpty(txt)) {
					content.setText(txt.substring(0, txt.length() - 1));
				}
				container.addView(convertView);
			}
		}
	}

	private void addView(LinearLayout container, List<String[]> listAll) {
		container.removeAllViews();
		int countUse = 0;
		LayoutInflater inflater = LayoutInflater.from(context);
		for (int i = 0; i < listAll.size(); i++) {
			View convertView = inflater.inflate(R.layout.listview_size, null);
			if (i == 0) {
				convertView.setBackgroundColor(Color.parseColor("#a8a8a8"));
			} else {
				convertView.setBackgroundColor(Color.parseColor("#ffffff"));
			}
			boolean flags = false;
			TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			TextView tv_size1 = (TextView) convertView.findViewById(R.id.tv_size1);
			TextView tv_size2 = (TextView) convertView.findViewById(R.id.tv_size2);
			TextView tv_size3 = (TextView) convertView.findViewById(R.id.tv_size3);
			TextView tv_size4 = (TextView) convertView.findViewById(R.id.tv_size4);
			TextView tv_size5 = (TextView) convertView.findViewById(R.id.tv_size5);
			if (i == 0) {
				tv_name.setTextColor(Color.parseColor("#ffffff"));
				tv_size1.setTextColor(Color.parseColor("#ffffff"));
				tv_size2.setTextColor(Color.parseColor("#ffffff"));
				tv_size3.setTextColor(Color.parseColor("#ffffff"));
				tv_size4.setTextColor(Color.parseColor("#ffffff"));
				tv_size5.setTextColor(Color.parseColor("#ffffff"));

			}
			String[] str = listAll.get(i);
			if (!TextUtils.isEmpty(str[0])) {
				if (str[0].equals("分割")) {
					convertView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, 30));
				} else {
					for (int j = 0; j < str.length; j++) {
						if (TextUtils.isEmpty(helper.queryAttr_name(str[1])) || "".equals(helper.queryAttr_name(str[1]))
								|| null == helper.queryAttr_name(str[1])) {
							flags = true;
						} else {
							flags = false;
						}
						if (!TextUtils.isEmpty(str[j])) {
							String attr_name = helper.queryAttr_name(str[j]);
							if (!TextUtils.isEmpty(attr_name)) {
								if (j == 0) {
									tv_name.setText(attr_name);
								}
								// if (i == 0 && j == 1 &&
								// (helper.queryAttr_name(str[2])) == null) {
								// tv_size1.setText("均码");
								// } else
								if (j == 1) {
									tv_size1.setText(attr_name);
								}
								if (j == 2) {
									tv_size2.setText(attr_name);
								}
								if (j == 3) {
									tv_size3.setText(attr_name);
								}
								if (j == 4) {
									tv_size4.setText(attr_name);
								}
								if (j == 5) {
									tv_size5.setText(attr_name);
								}
							}
						}

					}
				}
			}
			if (!flags) {
				countUse++;
				if (i == 0) {
					convertView.setBackgroundColor(Color.parseColor("#a8a8a8"));
				} else if (countUse % 2 == 1) {
					convertView.setBackgroundColor(Color.parseColor("#f6f6f6"));
				} else {
					convertView.setBackgroundColor(Color.parseColor("#ffffff"));
				}
				container.addView(convertView);
			}

		}
	}

	private class MyGridAdapter extends BaseAdapter {
		private List<HashMap<String, String>> list;

		public MyGridAdapter(List<HashMap<String, String>> list) {
			super();
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View v, ViewGroup parent) {

			v = LayoutInflater.from(context).inflate(R.layout.size_item, parent, false);

			TextView tv = (TextView) v.findViewById(R.id.size_tag_item);

			tv.setText(list.get(position).get("attr_name"));

			return v;
		}

	}

	private void refreshSFView() {
		int l = 0;

		List<String[]> list = shop.getShop_attr();
		if (list == null || list.size() == 0) {
			return;
		}
		List<String[]> listSize = new ArrayList<String[]>();
		for (int i = 0; i < list.size(); i++) {// 选择尺码
			String str[] = list.get(i);
			if (str[0].equals(SizeIndex.id)) {
				int length = str.length;
				if (length > l) {
					l = length;
				}
				listSize.add(str);
			}
		}
		l = l - 2;
		int lSize = l / 5;
		int mond = l % 5;

		if (mond > 0) {
			lSize++;
		}
		List<String[]> listAll = toList(lSize, listSize);
		addView(viewContainer, listAll);
	}

	private List<String[]> toList(int lSize, List<String[]> list) {
		int p = 1;
		String strFenGe[] = { "分割" };
		List<String[]> arrayList = new ArrayList<String[]>();
		for (int i = 0; i < lSize; i++) {
			for (int j = 0; j < list.size(); j++) {
				String strY[] = list.get(j);
				String str[] = new String[6];
				str[0] = strY[1];
				int l = strY.length;

				if ((5 * p - 3) < l) {
					str[1] = strY[(5 * p - 3)];
				}
				if ((5 * p - 2) < l) {
					str[2] = strY[(5 * p - 2)];
				}
				if ((5 * p - 1) < l) {
					str[3] = strY[(5 * p - 1)];
				}
				if ((5 * p) < l) {
					str[4] = strY[5 * p];
				}
				if ((5 * p + 1) < l) {
					str[5] = strY[5 * p + 1];
				}
				arrayList.add(str);

			}
			p++;
			if (i != lSize - 1) {
				arrayList.add(strFenGe);
			}
		}

		return arrayList;

	}
}
