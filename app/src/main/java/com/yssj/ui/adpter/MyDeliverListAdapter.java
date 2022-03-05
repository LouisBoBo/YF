package com.yssj.ui.adpter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.data.DBService;
import com.yssj.entity.DeliveryAddress;

public class MyDeliverListAdapter extends BaseAdapter {
	private Context context;
	private List<DeliveryAddress> listData;
	private DBService db;

	/** 所有任务都一次性开始的线程池 */
	private static ExecutorService allTaskExecutor = null;
	/** 每次执行限定个数个任务的线程池 */
	private static ExecutorService limitedTaskExecutor = null;
	private static final int count = Runtime.getRuntime().availableProcessors() * 3 + 2;

	public MyDeliverListAdapter(Context context, List<DeliveryAddress> listData) {
		this.context = context;
		this.listData = listData;
		db = new DBService(context);
		allTaskExecutor = Executors.newCachedThreadPool(); // 一个没有限制最大线程数的线程池
		limitedTaskExecutor = Executors.newFixedThreadPool(3);// 限制线程池大小为7的线程池
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listData.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			LayoutInflater mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.deliver_address_list_item,
					null);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_phone = (TextView) convertView
					.findViewById(R.id.tv_phone);
			holder.tv_detail_address = (TextView) convertView
					.findViewById(R.id.tv_detail_address);
			holder.is_default_addr = (ImageView) convertView.findViewById(R.id.is_default_addr);
			convertView.setTag(holder);

		} else {
			holder = (Holder) convertView.getTag();
		}
		
		holder.tv_name.setText(listData.get(position).getConsignee());
		holder.tv_phone.setText(listData.get(position).getPhone());
		if(0 == listData.get(position).getIs_default()){
			holder.is_default_addr.setBackgroundResource(R.drawable.tvchooseno_normal);
			holder.tv_detail_address.setText(listData.get(position).getDetailAddress());
			holder.tv_detail_address.setTextColor(context.getResources().getColor(R.color.title_color));
			holder.tv_name.setTextColor(context.getResources().getColor(R.color.title_color));
			holder.tv_phone.setTextColor(context.getResources().getColor(R.color.title_color));
		}else{
			holder.is_default_addr.setBackgroundResource(R.drawable.tvchooseno_selected);
			holder.tv_detail_address.setText("[默认]" + listData.get(position).getDetailAddress());
			holder.tv_detail_address.setTextColor(context.getResources().getColor(R.color.pink_color));
			holder.tv_name.setTextColor(context.getResources().getColor(R.color.pink_color));
			holder.tv_phone.setTextColor(context.getResources().getColor(R.color.pink_color));
		}
		
//		StringBuffer sb = new StringBuffer();

		/**
		 * 按我们指定的个数来执行任务的线程池
		 * */
//		AsyncTaskTest atQuery = new AsyncTaskTest(holder.tv_detail_address,position,listData.get(position).getIs_default());
//		atQuery.executeOnExecutor(limitedTaskExecutor);

//		sb.append(db
//				.query("select * from areatbl where id = '"
//						+ listData.get(position).getProvince() + "'").get(0)
//				.get("AreaName"));
//		sb.append(db
//				.query("select * from areatbl where id = '"
//						+ listData.get(position).getCity() + "'").get(0)
//				.get("AreaName"));
//		sb.append(db
//				.query("select * from areatbl where id = '"
//						+ listData.get(position).getArea() + "'").get(0)
//				.get("AreaName"));
//		sb.append(listData.get(position).getAddress());
//
//		holder.tv_detail_address.setText(sb.toString());
		

		return convertView;
	}

	class Holder {
		TextView tv_name, tv_phone, tv_detail_address;
		ImageView is_default_addr;
	}

	class AsyncTaskTest extends AsyncTask<String, Void, StringBuffer> {
		private TextView tv_address;
		private String id;
		private int position;
		private int is_default;

		private AsyncTaskTest(TextView tv_address, int position,int is_default) {
			this.tv_address = tv_address;
			this.position = position;
			this.is_default = is_default;
			// if (order < count || order == count) {
			// id = "执行:" + String.valueOf(++order);
			// } else {
			// order = 0;
			// id = "执行:" + String.valueOf(++order);
			// }
		}

		@Override
		protected StringBuffer doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			StringBuffer sb = new StringBuffer();
			sb.append(db
					.query("select * from areatbl where id = '"
							+ listData.get(position).getProvince() + "'")
					.get(0).get("AreaName"));
			sb.append(db
					.query("select * from areatbl where id = '"
							+ listData.get(position).getCity() + "'").get(0)
					.get("AreaName"));
			if(null != listData.get(position).getArea() && 0 != listData.get(position).getArea()){
				sb.append(db
						.query("select * from areatbl where id = '"
								+ listData.get(position).getArea() + "'").get(0)
						.get("AreaName"));
			}
			if(null != listData.get(position).getStreet() && 0 != listData.get(position).getStreet()){
				sb.append(db
						.query("select * from areatbl where id = '"
								+ listData.get(position).getStreet() + "'").get(0)
						.get("AreaName"));
			}
			sb.append(listData.get(position).getAddress());
			return sb;
		}

		@Override
		protected void onPostExecute(StringBuffer result) {
			super.onPostExecute(result);
			if(0 == is_default){
				tv_address.setText(result.toString());
			}else{
				tv_address.setText("[默认]" + result.toString());
				tv_address.setTextColor(context.getResources().getColor(R.color.pink_color));
			}
			
		}

	}

}