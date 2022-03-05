//package com.yssj.ui.adpter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.app.ArrayAdapterCompat;
//import com.yssj.entity.MyCoupon;
//
//public class CouponsListAdapter extends ArrayAdapterCompat<MyCoupon> {
//
//	private Context context;
//	private LayoutInflater inflater;
//
//	public CouponsListAdapter(Context context) {
//		super(context);
//		this.context = context;
//		inflater = LayoutInflater.from(context);
//	}
//
//	class Holder {
//		TextView tv_deal_no, tv_type, tv_money, tv_status;
//
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		Holder holder = null;
//		if (convertView == null) {
//			holder = new Holder();
//			LayoutInflater mInflater = LayoutInflater.from(context);
//			convertView = mInflater.inflate(R.layout.my_coupons_item, null);
//			holder.tv_deal_no = (TextView) convertView
//					.findViewById(R.id.tv_deal_no);
//			holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
//			holder.tv_money = (TextView) convertView
//					.findViewById(R.id.tv_money);
//			holder.tv_status = (TextView) convertView
//					.findViewById(R.id.tv_status);
//
//			convertView.setTag(holder);
//
//		} else {
//			holder = (Holder) convertView.getTag();
//		}
//
//		MyCoupon coupon = getItem(position);
//		holder.tv_deal_no.setText(coupon.getC_id()+"");
//		holder.tv_type.setText(coupon.getNum().toString());
//		holder.tv_money.setText(coupon.getAdd_time() + "");
//		holder.tv_status.setText(coupon.getLast_time().toString());
//
//		return convertView;
//	}
//
//}
