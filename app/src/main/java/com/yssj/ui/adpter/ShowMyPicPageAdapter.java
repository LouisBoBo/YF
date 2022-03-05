package com.yssj.ui.adpter;

import java.util.HashMap;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.huanxin.widget.photoview.PhotoView;
import com.yssj.ui.activity.circles.SweetFriendsDetails;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.utils.PicassoUtils;
//import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowMyPicPageAdapter extends PagerAdapter {
	private List<HashMap<String, Object>> list;
	private Context context;

	public ShowMyPicPageAdapter(Context context, List<HashMap<String, Object>> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		View view = (View) object;
		container.removeView(view);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		/**
		 * 话题单用，穿搭和精品推荐共用 // theme_type: 1 精选推荐，2 穿搭，3 普通话题
		 */
		View view;
		ImageView img_bigger;
		final int theme_type;
		TextView tv_tiezineirong_huati; // 话题帖子内容
		TextView tv_tiezineirong_chuanda; // 穿搭帖子内容
		TextView tv_chuanda_title; // 穿搭标题
		LinearLayout ll_jingxuan;// 精选推荐专用
		TextView tv_count, tv_yuanlai_price, tv_real_price, tv_look_detail, tv_shop_name;

		view = LayoutInflater.from(context).inflate(R.layout.image_layout, null);
		img_bigger = (ImageView) view.findViewById(R.id.img_bigger);
		tv_count = (TextView) view.findViewById(R.id.tv_count);
		tv_tiezineirong_huati = (TextView) view.findViewById(R.id.tv_tiezineirong_huati);
		tv_yuanlai_price = (TextView) view.findViewById(R.id.tv_yuanlai_price);
		tv_real_price = (TextView) view.findViewById(R.id.tv_real_price);
		tv_shop_name = (TextView) view.findViewById(R.id.tv_shop_name);
		tv_chuanda_title = (TextView) view.findViewById(R.id.tv_chuanda_title);
		tv_tiezineirong_chuanda = (TextView) view.findViewById(R.id.tv_tiezineirong_chuanda);
		// 查看详情和购买按钮
		tv_look_detail = (TextView) view.findViewById(R.id.tv_look_detail);
		theme_type = Integer.parseInt(list.get(position).get("theme_type") + "");
		ll_jingxuan = (LinearLayout) view.findViewById(R.id.ll_jingxuan);

		// 填充数据
		switch (theme_type) {
		case 1:// 1 精选推荐
			tv_tiezineirong_huati.setVisibility(View.GONE);
			tv_chuanda_title.setVisibility(View.GONE);
			tv_tiezineirong_chuanda.setVisibility(View.GONE);
			ll_jingxuan.setVisibility(View.VISIBLE);
			// ToastUtil.addStrikeSpan(tv_yuanlai_price, "100.10");
			tv_look_detail.setText("立即购买");



            String pc = ((List<HashMap<String, Object>>) list.get(position).get("shop_list")).get(0).get("shop_se_price") + "";

            double price = 0.0;
            try {
                price = Double.parseDouble(pc) *0.9;
            } catch (Exception e) {
                e.printStackTrace();
            }
            tv_real_price.setText("¥" + new java.text.DecimalFormat("#0.0").format(price));



//            tv_real_price.setText("¥"
//					+ ((List<HashMap<String, Object>>) list.get(position).get("shop_list")).get(0).get("shop_se_price")
//					+ "");




			ToastUtil.addStrikeSpan(tv_yuanlai_price,
					"¥" + ((List<HashMap<String, Object>>) list.get(position).get("shop_list")).get(0).get("shop_price")
							+ "");
			tv_shop_name.setText(
					((List<HashMap<String, Object>>) list.get(position).get("shop_list")).get(0).get("shop_name") + "");

			List<HashMap<String, Object>> shopList = (List<HashMap<String, Object>>) list.get(position)
					.get("shop_list");
			String shop_status = shopList.get(0).get("shop_status") + "";
			if (shop_status.equals("1")) { // 没有库存
				tv_look_detail.setBackgroundResource(R.drawable.bg_not_ok);
			} else {
				tv_look_detail.setBackgroundResource(R.drawable.bg_red_ok);

			}

			break;
		case 2:// 穿搭

			tv_tiezineirong_huati.setVisibility(View.GONE);
			tv_chuanda_title.setVisibility(View.VISIBLE);
			tv_tiezineirong_chuanda.setVisibility(View.VISIBLE);
			ll_jingxuan.setVisibility(View.GONE);
			tv_chuanda_title.setText("#" + list.get(position).get("title") + "" + "#");
			tv_tiezineirong_chuanda.setText(list.get(position).get("content") + "");
			tv_look_detail.setText("查看详情");

			break;
		case 3:// 话题

			tv_tiezineirong_huati.setVisibility(View.VISIBLE);
			tv_chuanda_title.setVisibility(View.GONE);
			tv_tiezineirong_chuanda.setVisibility(View.GONE);
			ll_jingxuan.setVisibility(View.GONE);

			tv_tiezineirong_huati.setText(list.get(position).get("content") + "");
			tv_look_detail.setText("查看详情");

			break;
		case 4:// 新穿搭

			tv_tiezineirong_huati.setVisibility(View.GONE);
			tv_chuanda_title.setVisibility(View.GONE);
			tv_tiezineirong_chuanda.setVisibility(View.VISIBLE);
			ll_jingxuan.setVisibility(View.GONE);
			tv_chuanda_title.setText("#" + list.get(position).get("title") + "" + "#");
			tv_tiezineirong_chuanda.setText(list.get(position).get("content") + "");
			tv_look_detail.setText("查看详情");

			break;

		default:
			break;
		}

		tv_look_detail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (theme_type == 2 || theme_type == 3 || theme_type == 4) {
					Intent intent = new Intent(context, SweetFriendsDetails.class);
					intent.putExtra("theme_id", list.get(position).get("theme_id") + "");
					((FragmentActivity) context).startActivity(intent);
					((FragmentActivity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
				}

				if (theme_type == 1) {
					String code = ((List<HashMap<String, Object>>) list.get(position).get("shop_list")).get(0)
							.get("shop_code") + "";
					Intent intent = new Intent(context, ShopDetailsActivity.class);
					intent.putExtra("code", code);
					((FragmentActivity) context).startActivity(intent);
					((FragmentActivity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

				}

			}
		});

		img_bigger.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((FragmentActivity) context).finish();
			}
		});

		String pic = "";
		if ((list.get(position).get("theme_type") + "").equals("1")) {
			HashMap<String, Object> shop;
			shop = ((List<HashMap<String, Object>>) list.get(position).get("shop_list")).get(0);
			pic = list.get(position).get("pic") + "";
			pic = (shop.get("shop_code") + "").substring(1, 4) + "/" + shop.get("shop_code") + "/" + pic;

		} else {
			pic = ("myq/theme/" + (list.get(position).get("user_id") + "") + "/" + list.get(position).get("pic") + "")
					.split(":")[0];
		}

		// String pic = ("myq/theme/" +
		// YCache.getCacheUser(context).getUser_id() + "/" +
		// list.get(position).get("pic")
		// + "").split(":")[0];
		// PicassoUtils.initImage(context, pic, img_bigger);

//		SetImageLoader.initImageLoader3(context, img_bigger, pic, "");
		// SetImageLoader.initImageLoader3(context, img_bigger, pic, "");

		PicassoUtils.initImage(context, pic+"!450", img_bigger);
		tv_count.setText(position + 1 + "/" + list.size());
		container.addView(view);
		return view;

	}
}
