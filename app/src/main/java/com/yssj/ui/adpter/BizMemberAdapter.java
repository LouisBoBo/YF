//package com.yssj.ui.adpter;
//
//import java.util.HashMap;
//
//import org.apache.commons.lang.time.DateFormatUtils;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.data.DBService;
//import com.yssj.ui.activity.league.BizMemberDetailActivity;
//import com.yssj.ui.activity.league.StatusInfoActivity;
//import com.yssj.ui.base.BaseMainAdapter;
//import com.yssj.utils.SetImageLoader;
//
//public class BizMemberAdapter extends BaseMainAdapter {
//	private String tag;
//
//	public BizMemberAdapter(Context context, String tag) {
//		super(context);
//		this.tag = tag;
//	}
//
//	public BizMemberAdapter(Context context) {
//		super(context);
//
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		ViewHolder holder;
//		if (null == convertView) {
//			convertView = View.inflate(context,
//					R.layout.supvip_member_list_item, null);
//
//			holder = new ViewHolder();
//			holder.iv_img_header = (ImageView) convertView
//					.findViewById(R.id.iv_img_header);
//			holder.tv_add_time = (TextView) convertView
//					.findViewById(R.id.tv_add_time);
//			holder.tv_member_num = (TextView) convertView
//					.findViewById(R.id.tv_member_num);
//			holder.tv_title_name = (TextView) convertView
//					.findViewById(R.id.tv_title_name);
//			holder.tv_activation_code = (TextView) convertView
//					.findViewById(R.id.tv_activation_code);
//			holder.tv_member_adress = (TextView) convertView
//					.findViewById(R.id.tv_member_adress);
//			holder.tv_member_phone = (TextView) convertView
//					.findViewById(R.id.tv_member_phone);
//			holder.tv_card_num = (TextView) convertView
//					.findViewById(R.id.tv_card_num);
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//
//		final HashMap<String, Object> mapObj = result.get(position);
//		SetImageLoader.initImageLoader(context, holder.iv_img_header,
//				(String) mapObj.get("pic"), "");
//		if("null".equals(mapObj.get("count"))){
//			holder.tv_member_num.setText( "0");	
//		}
//		else{
//		holder.tv_member_num.setText(mapObj.get("count") + "");
//		}
//		holder.tv_title_name.setText(mapObj.get("nickname") + "");
//		if (null != mapObj.get("start_time")
//				&& !"null".equals(mapObj.get("start_time"))
//				&&!"".equals(mapObj.get("start_time"))) {
//			holder.tv_add_time.setVisibility(View.VISIBLE);
//			holder.tv_add_time.setText(DateFormatUtils.format(
//					Long.parseLong(mapObj.get("start_time").toString()),
//					"yyyy-MM-dd"));
//		}else{
//			holder.tv_add_time.setVisibility(View.GONE);
//		}
//
//		if (!mapObj.get("city").equals("")) {
//			String city = DBService.getIntance().queryAreaNameById(
//					Integer.parseInt((String) mapObj.get("province")))
//					+ " "
//					+ DBService.getIntance().queryAreaNameById(
//							Integer.parseInt((String) mapObj.get("city")));
//			holder.tv_member_adress.setVisibility(View.VISIBLE);
//			holder.tv_member_adress.setText(city);
//		} else {
//			holder.tv_member_adress.setVisibility(View.GONE);
//		}
//		if (mapObj.get("phone").equals("")) {
//			holder.tv_member_phone.setVisibility(View.GONE);
//		} else {
//			holder.tv_member_phone.setVisibility(View.VISIBLE);
//			holder.tv_member_phone.setText(mapObj.get("phone").toString());
//		}
//		holder.tv_card_num.setText("卡号：" + mapObj.get("card_no"));
//		holder.tv_activation_code.setText("激活码：" + mapObj.get("plaintext"));
//		convertView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				 if(tag.equals("huiyuan")){
//				Intent intent = new Intent(context,
//						BizMemberDetailActivity.class);
//				intent.putExtra("user_id", mapObj.get("user_id").toString());
//				 intent.putExtra("flag", "allPager");
//				context.startActivity(intent);
//				
//				
//				
//				 }else if(tag.equals("dingdan")){
//					 
//					 
//				 Intent intent = new Intent(context,
//				 StatusInfoActivity.class);
//				 intent.putExtra("user_id", mapObj.get("user_id").toString());
//				 ((FragmentActivity) context).startActivityForResult(intent,
//				 10002);
//				
//				 }
//			}
//		});
//
//		return convertView;
//	}
//
//	class ViewHolder {
//
//		ImageView iv_img_header;
//		TextView tv_member_num, tv_title_name, tv_add_time, tv_member_adress,
//				tv_member_phone, tv_activation_code, tv_card_num;
//	}
//
//}
