package com.yssj.ui.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.ArrayAdapterCompat;
import com.yssj.entity.FundDetail;

public class FundListAdapter extends ArrayAdapterCompat<FundDetail> {

	private Context context;
	private LayoutInflater inflater;

	public FundListAdapter(Context context) {
		super(context);
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	class Holder {
		TextView tv_deal_no, tv_type, tv_money, tv_status;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			LayoutInflater mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.fund_detail_item, null);
			holder.tv_deal_no = (TextView) convertView
					.findViewById(R.id.tv_deal_no);
			holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
			holder.tv_money = (TextView) convertView
					.findViewById(R.id.tv_money);
			holder.tv_status = (TextView) convertView
					.findViewById(R.id.tv_status);

			convertView.setTag(holder);

		} else {
			holder = (Holder) convertView.getTag();
		}

		FundDetail fundDetail = getItem(position);
		holder.tv_deal_no.setText(fundDetail.getDeal_no());
		holder.tv_type.setText(fundDetail.getType().toString());
		holder.tv_money.setText(fundDetail.getMoney() + "");
		holder.tv_status.setText(fundDetail.getStatus().toString());

		return convertView;
	}

}
