package com.yssj.ui.adpter;

import java.text.DecimalFormat;
import java.util.HashMap;

import org.apache.commons.lang.time.DateFormatUtils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.base.BaseMainAdapter;
import com.yssj.utils.DateUtil;

public class MyCouponsPagerAdapter extends BaseMainAdapter {
	private java.text.DecimalFormat pFormate;
	public MyCouponsPagerAdapter(Context context) {
		super(context);
		pFormate=new DecimalFormat("0");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
//			convertView = View.inflate(context,R.layout.mycoupons_list_item, null);
			convertView = View.inflate(context,R.layout.useful_list_item, null);

			holder = new ViewHolder();
			holder.img_yiguoqi = (ImageView) convertView.findViewById(R.id.img_yiguoqi); 
			holder.img_yishiyong = (ImageView) convertView.findViewById(R.id.img_yishiyong); 
			
			holder.tv_cprice = (TextView) convertView.findViewById(R.id.tv_cprice); 
			holder.tv_end_date = (TextView) convertView.findViewById(R.id.tv_end_date);
			
			holder.tv_begin_time = (TextView) convertView.findViewById(R.id.tv_begin_time);
	// 满多少钱可用		holder.tv_ccond = (TextView) convertView.findViewById(R.id.tv_ccond); 
			holder.ll_all_bg = (RelativeLayout) convertView.findViewById(R.id.ll_all_bg);
//黄色块背景 现在不用了			holder.rl_part_bg = (LinearLayout) convertView.findViewById(R.id.rl_part_bg);
//满多少减 现在不用			holder.tv_enough  = (TextView) convertView.findViewById(R.id.tv_enough);
//			holder.tv_shop_opnion = (TextView) convertView.findViewById(R.id.tv_shop_opnion);
			holder.tv_manshiyong = (TextView) convertView.findViewById(R.id.tv_manshiyong);
			holder.tv_time_use = (TextView) convertView.findViewById(R.id.tv_time_use);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final HashMap<String, Object> mapObj = result.get(position);
		String is_gold  = (String) mapObj.get("is_gold");
/*		if("2".equals(mapObj.get("is_use").toString()) || (Long.parseLong(mapObj.get("c_last_time").toString()) < System.currentTimeMillis())){ // 已使用和已失效的
			holder.ll_all_bg.setBackgroundResource(R.drawable.youhuiquan_none);
	//.		holder.rl_part_bg.setBackgroundResource(R.drawable.coupont_userless_white);
	//.		holder.tv_enough.setTextColor(context.getResources().getColor(R.color.enough_useless_color));
			holder.tv_shop_opnion.setTextColor(context.getResources().getColor(R.color.white));
			holder.tv_end_date.setTextColor(context.getResources().getColor(R.color.white_white));
			
		}else if("1".equals(mapObj.get("is_use").toString()) && (Long.parseLong(mapObj.get("c_last_time").toString()) > System.currentTimeMillis())){	// 有效的
			holder.ll_all_bg.setBackgroundResource(R.drawable.youhuiquan_new);
//			holder.rl_part_bg.setBackgroundResource(R.drawable.coupont_all_white);
	//.		holder.rl_part_bg.setBackgroundResource(R.drawable.coupont_all_yellow);
			holder.tv_enough.setTextColor(context.getResources().getColor(R.color.shop_opinion_color));
			holder.tv_shop_opnion.setTextColor(context.getResources().getColor(R.color.white));
			holder.tv_end_date.setTextColor(context.getResources().getColor(R.color.pink_color));
		}*/
		if("1".equals(is_gold)){//失效的金券
			if(mapObj != null || !mapObj.isEmpty()){
//				holder.tv_cprice.setText(pFormate.format(mapObj.get("c_price"))+"");

				holder.tv_cprice.setText(
						new java.text.DecimalFormat("#0.00").format(Float.parseFloat(mapObj.get("c_price").toString()))
				);



//				holder.tv_end_date.setText( "-"+DateFormatUtils.format(Long.parseLong(mapObj.get("c_last_time").toString()), "yyyy.MM.dd"));
//				holder.tv_begin_time.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("c_add_time").toString()), "yyyy.MM.dd"));
				
				String sss =pFormate.format(mapObj.get("c_price"))+".01";
				
				holder.tv_manshiyong.setText("（满"+sss+"元可使用）");
				holder.ll_all_bg.setBackgroundResource(R.drawable.jinquan_shixiao_bg);
			}
			if(Long.parseLong(mapObj.get("c_last_time").toString()) < System.currentTimeMillis()){ // 已使用和已失效的
				holder.ll_all_bg.setBackgroundResource(R.drawable.jinquan_shixiao_bg);
				holder.img_yiguoqi.setVisibility(View.VISIBLE);
				holder.img_yishiyong.setVisibility(View.GONE);
				holder.tv_time_use.setVisibility(View.GONE);
				holder.tv_end_date.setVisibility(View.GONE);
				holder.tv_begin_time.setVisibility(View.GONE);
			}
			
			if("2".equals(mapObj.get("is_use").toString()) ){ // 已使用和已失效的
				holder.ll_all_bg.setBackgroundResource(R.drawable.jinquan_shixiao_bg);
				holder.img_yishiyong.setVisibility(View.VISIBLE);
				holder.img_yiguoqi.setVisibility(View.GONE);
				holder.tv_time_use.setVisibility(View.GONE);
				holder.tv_end_date.setVisibility(View.GONE);
				holder.tv_begin_time.setVisibility(View.GONE);
			}	
			
			
		}else{//失效的优惠券
		
			if(mapObj != null || !mapObj.isEmpty()){



//				holder.tv_cprice.setText(pFormate.format(mapObj.get("c_price"))+"");



				holder.tv_cprice.setText(
						new java.text.DecimalFormat("#0.00").format(Float.parseFloat(mapObj.get("c_price").toString()))
				);




				//			holder.tv_end_date.setText("有效期至/ " + DateFormatUtils.format(Long.parseLong(mapObj.get("c_last_time").toString()), "yyyy-MM-dd"));
				holder.tv_end_date.setText( "-"+DateFormatUtils.format(Long.parseLong(mapObj.get("c_last_time").toString()), "yyyy.MM.dd"));
				holder.tv_begin_time.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("c_add_time").toString()), "yyyy.MM.dd"));
		//满多少可用		holder.tv_ccond.setText("【订单满" + mapObj.get("c_cond") + "元可用】");
				
				String sss =mapObj.get("c_cond").toString();
				String s = new java.text.DecimalFormat("#0.00").format(Float.parseFloat(sss));
				holder.tv_manshiyong.setText("（满"+s+"元可使用）");
				holder.ll_all_bg.setBackgroundResource(R.drawable.youhuiquan_shixiao_bg);
			}
			 
	//		if("2".equals(mapObj.get("is_use").toString()) ){ // 已使用和已失效的
	//			holder.ll_all_bg.setBackgroundResource(R.drawable.youhuiquan_none);
	//	//.		holder.rl_part_bg.setBackgroundResource(R.drawable.coupont_userless_white);
	//	//.		holder.tv_enough.setTextColor(context.getResources().getColor(R.color.enough_useless_color));
	//			holder.tv_shop_opnion.setTextColor(context.getResources().getColor(R.color.white));
	//			holder.tv_end_date.setTextColor(context.getResources().getColor(R.color.white_white));
	//			holder.tv_begin_time.setTextColor(context.getResources().getColor(R.color.white_white));
	//			holder.img_yishiyong.setVisibility(View.VISIBLE);
	//			holder.img_yiguoqi.setVisibility(View.GONE);
	//		}
			if(Long.parseLong(mapObj.get("c_last_time").toString()) < System.currentTimeMillis()){ // 已使用和已失效的
				holder.ll_all_bg.setBackgroundResource(R.drawable.youhuiquan_shixiao_bg);
		//.		holder.rl_part_bg.setBackgroundResource(R.drawable.coupont_userless_white);
		//.		holder.tv_enough.setTextColor(context.getResources().getColor(R.color.enough_useless_color));
	//			holder.tv_shop_opnion.setTextColor(context.getResources().getColor(R.color.white));
	//			holder.tv_end_date.setTextColor(context.getResources().getColor(R.color.white_white));
	//			holder.tv_begin_time.setTextColor(context.getResources().getColor(R.color.white_white));
				holder.img_yiguoqi.setVisibility(View.VISIBLE);
				holder.img_yishiyong.setVisibility(View.GONE);
			}
			
			if("2".equals(mapObj.get("is_use").toString()) ){ // 已使用和已失效的
				holder.ll_all_bg.setBackgroundResource(R.drawable.youhuiquan_shixiao_bg);
		//.		holder.rl_part_bg.setBackgroundResource(R.drawable.coupont_userless_white);
		//.		holder.tv_enough.setTextColor(context.getResources().getColor(R.color.enough_useless_color));
	//			holder.tv_shop_opnion.setTextColor(context.getResources().getColor(R.color.white));
	//			holder.tv_end_date.setTextColor(context.getResources().getColor(R.color.white_white));
	//			holder.tv_begin_time.setTextColor(context.getResources().getColor(R.color.white_white));
				holder.img_yishiyong.setVisibility(View.VISIBLE);
				holder.img_yiguoqi.setVisibility(View.GONE);
			}
		}
		return convertView;
	}

	class ViewHolder {

		TextView tv_end_date,tv_cprice,tv_ccond,tv_enough,/*tv_shop_opnion,*/tv_begin_time,tv_manshiyong,tv_time_use;
		RelativeLayout ll_all_bg,
		 rl_part_bg;
		ImageView img_yiguoqi,img_yishiyong;
	}
}
