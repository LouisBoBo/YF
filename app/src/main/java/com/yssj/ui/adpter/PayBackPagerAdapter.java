package com.yssj.ui.adpter;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.entity.ReturnShop;
import com.yssj.ui.activity.infos.EvaluateOrderNewActivity;
import com.yssj.ui.fragment.payback.ApplyPlatformActivity;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;

public class PayBackPagerAdapter extends BaseAdapter {
	private DecimalFormat format;
	private LayoutInflater inflater;
	private Context mContext;
	private List<ReturnShop> rShops;

	public PayBackPagerAdapter(Context context, List<ReturnShop> rShops) {
		this.rShops = rShops;
		format = new DecimalFormat("#0.00");
		inflater = LayoutInflater.from(context);
		this.mContext = context;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext,
					R.layout.listview_orderlist_son, null);
			holder.img_product = (ImageView) convertView
					.findViewById(R.id.img_product1);
			holder.tv_product_name = (TextView) convertView
					.findViewById(R.id.tv_product_name);

			holder.tv_shop_num = (TextView) convertView
					.findViewById(R.id.tv_shop_num);
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			holder.tv_product_color = (TextView) convertView
					.findViewById(R.id.tv_product_color);
			holder.tv_product_size = (TextView) convertView
					.findViewById(R.id.tv_product_size);
			holder.tv_status = (TextView) convertView
					.findViewById(R.id.tv_status);

			holder.meal = (TextView) convertView.findViewById(R.id.meal);
			holder.mPlatform = (TextView) convertView
					.findViewById(R.id.platform_jieru);
			holder.mPlatform.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent intent = new Intent(mContext,
							ApplyPlatformActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("order",
							(Serializable) getItem(position));
					intent.putExtras(bundle);
					mContext.startActivity(intent);

				}
			});
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final ReturnShop returnShop = (ReturnShop) getItem(position);

		String shop_name = returnShop.getShop_name();
		if (!TextUtils.isEmpty(shop_name)) {
			holder.tv_product_name.setText(shop_name);
		}
		String pic = returnShop.getShop_code().substring(1, 4) + "/"
				+ returnShop.getShop_code() + "/" + returnShop.getPic();
		if (-1 == returnShop.getOrder_shop_id()) {
			holder.meal.setVisibility(View.VISIBLE);
			holder.tv_product_color.setVisibility(View.GONE);
			holder.tv_product_size.setVisibility(View.GONE);
			if (returnShop.getShop_num() > 1) {
				holder.meal.setText("????????????");
			} else {
				holder.meal.setText("????????????");
			}
			pic = returnShop.getPic();
		} else {
			holder.meal.setVisibility(View.GONE);
			holder.tv_product_color.setVisibility(View.VISIBLE);
			holder.tv_product_size.setVisibility(View.VISIBLE);
		}

		if (!TextUtils.isEmpty(pic)) {
//			SetImageLoader.initImageLoader(mContext, holder.img_product, pic,
//					"");
//			
			PicassoUtils.initImage(mContext, pic, holder.img_product);
		}

		if (null == returnShop.getShop_color()) {
			holder.tv_product_color.setVisibility(View.GONE);
		}

		if (null == returnShop.getShop_size()) {
			holder.tv_product_size.setVisibility(View.GONE);
		}

		holder.tv_product_color.setText("?????? : " + returnShop.getShop_color());
		holder.tv_product_size.setText("?????? : " + returnShop.getShop_size());
		String price = new java.text.DecimalFormat("#0.00").format(returnShop
				.getShop_price());
		holder.tv_price.setText("??" + price);
		int num = returnShop.getShop_num();
		holder.tv_shop_num.setText("x" + num);

		int returnShopType = returnShop.getReturn_type();
		int returnShopStatus = returnShop.getStatus();

		StringBuffer sb = new StringBuffer();
		switch (returnShopType) {
		case 1:
			sb.append("??????");
			break;
		case 2:
			sb.append("??????");
			break;
		case 3:
			sb.append("??????");
			break;
		}
		switch (returnShopStatus) {

		case 1:
			sb.append("?????????");
			break;
		case 2:
			sb.append("???????????????");
			break;
		case 3:
			sb.append("???????????????");
			break;
		case 4:
			sb.append("-?????????????????????");
			break;
		case 5:
			sb.append("-???????????????");
			break;
		case 6:
			sb.append("?????????");
			break;
		case 7:
			sb.append("?????????");
			break;
		case 8:
			sb.append("??????");
			break;
		case 9:
			sb.append("?????????");
			break;
		case 10:
			sb.append("?????????");
			break;
		case 11:
			sb.append("-???????????????????????????");
			break;
		case 12:
			sb.append("-???????????????????????????");
			break;
		default:
			break;
		}
		// TODO
		if (returnShop.getSupp_sign_status() == 1) {
			switch (returnShopType) {
			case 1:
				if (returnShop.getYs_intervene() == 2||returnShop.getYs_intervene() == 1) {
					holder.tv_status.setText("???????????????");
					holder.mPlatform.setVisibility(View.GONE);
				} else if (returnShop.getYs_intervene() == 3) {
					holder.tv_status.setText("????????????");
					holder.mPlatform.setVisibility(View.GONE);
				} else if (returnShop.getYs_intervene() == 4) {
					holder.tv_status.setText("????????????");
					holder.mPlatform.setVisibility(View.GONE);
				} else if (returnShop.getYs_intervene() == 5||returnShop.getYs_intervene() == 6) {
					holder.tv_status.setText("????????????");
					holder.mPlatform.setVisibility(View.GONE);
				} else {
					holder.mPlatform.setVisibility(View.VISIBLE);
					holder.tv_status.setText("??????????????????");
				}
				break;
			case 2:
				if (returnShop.getYs_intervene() == 2||returnShop.getYs_intervene() == 1) {
					holder.tv_status.setText("???????????????");
					holder.mPlatform.setVisibility(View.GONE);
				} else if (returnShop.getYs_intervene() == 3) {
					holder.tv_status.setText("????????????");
					holder.mPlatform.setVisibility(View.GONE);
				} else if (returnShop.getYs_intervene() == 4) {
					holder.tv_status.setText("????????????");
					holder.mPlatform.setVisibility(View.GONE);
				} else if (returnShop.getYs_intervene() == 5) {
					holder.tv_status.setText("????????????");
					holder.mPlatform.setVisibility(View.GONE);
				} else {
					holder.mPlatform.setVisibility(View.VISIBLE);
					holder.tv_status.setText("??????????????????");
				}
				break;
			case 3:
				if (returnShop.getYs_intervene() == 2||returnShop.getYs_intervene() == 1) {
					holder.tv_status.setText("???????????????");
					holder.mPlatform.setVisibility(View.GONE);
				} else if (returnShop.getYs_intervene() == 3) {
					holder.tv_status.setText("????????????");
					holder.mPlatform.setVisibility(View.GONE);
				} else if (returnShop.getYs_intervene() == 4) {
					holder.tv_status.setText("????????????");
					holder.mPlatform.setVisibility(View.GONE);
				} else if (returnShop.getYs_intervene() == 5) {
					holder.tv_status.setText("????????????");
					holder.mPlatform.setVisibility(View.GONE);
				} else {
					holder.mPlatform.setVisibility(View.VISIBLE);
					holder.tv_status.setText("??????????????????");
				}
				break;
			}

		} else {
			holder.tv_status.setText(sb.toString());// ????????????????????????????????????status
													// ???change???????????????s
			holder.mPlatform.setVisibility(View.GONE);
		}
		return convertView;
	}

	class ViewHolder {
		TextView mPlatform;
		ImageView img_product;
		TextView tv_product_name, tv_shop_num, tv_price, tv_product_color,
				tv_product_size, tv_status, meal;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stubT
		return rShops.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return rShops.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
