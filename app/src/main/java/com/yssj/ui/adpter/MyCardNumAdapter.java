//package com.yssj.ui.adpter;
//
//import java.util.HashMap;
//import java.util.List;
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
//public class MyCardNumAdapter extends BaseMainAdapter {
//	 private List<HashMap<String, Object>> list;
//
//	public MyCardNumAdapter(Context context) {
//		super(context);
//
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		ViewHolder holder;
//		if (null == convertView) {
//			convertView = View.inflate(context, R.layout.my_card_num_list_item,
//					null);
//
//			holder = new ViewHolder();
//			holder.iv_activation_status = (ImageView) convertView
//					.findViewById(R.id.iv_activation_status);
//			holder.tv_card_num = (TextView) convertView
//					.findViewById(R.id.tv_card_num);
//			holder.tv_card_activation = (TextView) convertView
//					.findViewById(R.id.tv_card_activation);
//			holder.tv_card_status_z = (TextView) convertView
//					.findViewById(R.id.tv_card_status_z);
//			holder.tv_card_activation_id = (TextView) convertView
//					.findViewById(R.id.tv_card_activation_id);
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
////
//		final HashMap<String, Object> mapObj = result.get(position);
//		holder.tv_card_num.setText("卡号"+mapObj.get("card_no"));
//		holder.tv_card_activation.setText("激活码:"+mapObj.get("plaintext"));
//		String status = mapObj.get("is_use").toString();
//		if(null==status||"0".equals(status)){
//		holder.tv_card_status_z.setText("未激活");
//		holder.tv_card_status_z.setTextColor(context.getResources().getColor(R.color.background_gray));
//		holder.iv_activation_status.setVisibility(View.GONE);
//		}else{
//			holder.tv_card_status_z.setText("已激活");
//			holder.tv_card_status_z.setTextColor(context.getResources().getColor(R.color.pink_color));
//			holder.iv_activation_status.setVisibility(View.VISIBLE);
//		}
//		if(null==mapObj.get("nickname")||"".equals(mapObj.get("nickname"))){
//			holder.tv_card_activation_id .setText("激活ID:无");
//		}else{
//		holder.tv_card_activation_id .setText("激活ID:"+mapObj.get("nickname"));
//		}
//
//		return convertView;
//	}
//
//	class ViewHolder {
//
//		ImageView iv_activation_status;
//		TextView tv_card_num, tv_card_activation, tv_card_status_z, tv_card_activation_id;
//	}
//
//}
