//package com.yssj.ui.adpter;
//
//import java.util.HashMap;
//
//import org.apache.commons.lang.time.DateFormatUtils;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.data.DBService;
//import com.yssj.ui.base.BaseMainAdapter;
//import com.yssj.utils.DateUtil;
//import com.yssj.utils.SetImageLoader;
//
//public class BizMemberDetailAdapter extends BaseMainAdapter {
//	public BizMemberDetailAdapter(Context context) {
//		super(context);
//	}
//
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		ViewHolder holder;
//		if (null == convertView) {
//			convertView = View.inflate(context,R.layout.biz_member_list_item, null);
//
//			holder = new ViewHolder();
//			holder.iv_img_header =(ImageView) convertView.findViewById(R.id.iv_img_header);
//			holder.tv_title_name =  (TextView) convertView.findViewById(R.id.tv_title_name);
//			holder.tv_add_time =  (TextView) convertView.findViewById(R.id.tv_add_time);
//			holder.tv_member_adress =  (TextView) convertView.findViewById(R.id.tv_member_adress);
//			holder.tv_member_phone =  (TextView) convertView.findViewById(R.id.tv_member_phone);
//			holder.tv_member_money2 =  (TextView) convertView.findViewById(R.id.tv_member_money2);
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//
//		final HashMap<String, Object> mapObj = result.get(position);
//		SetImageLoader.initImageLoader(context, holder.iv_img_header, mapObj.get("pic")+"", "");
//		holder.tv_title_name.setText(""+mapObj.get("nickname"));
//		holder.tv_member_money2.setText("+"+mapObj.get("sumMoney"));
//		if (null != mapObj.get("time")
//				&& !"null".equals(mapObj.get("time"))
//				&&!"".equals(mapObj.get("time"))) {
//			holder.tv_add_time.setVisibility(View.VISIBLE);
//			holder.tv_add_time.setText(DateFormatUtils.format(
//					Long.parseLong(mapObj.get("time").toString()),
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
//		
//		
//		
//		
//		
//		
//		
//		return convertView;
//	}
//
//	class ViewHolder {
//
//		ImageView iv_img_header;
//		TextView tv_title_name,tv_add_time,tv_member_adress,tv_member_phone,tv_member_money2;
//		
//	}
//	
//
//}
