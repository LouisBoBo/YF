package com.yssj.ui.adpter;

import java.util.HashMap;

import org.apache.commons.lang.time.DateFormatUtils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.base.BaseMainAdapter;
import com.yssj.ui.pager.IntergralIncomePage;
import com.yssj.utils.DateUtil;

public class MyPearsPageAdapter extends BaseMainAdapter {
	private int indext;

	public MyPearsPageAdapter(Context context, int indext) {
		super(context);
		this.indext = indext;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
			convertView = View.inflate(context, R.layout.intergral_detail_item, null);

			holder = new ViewHolder();
			holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
			holder.tv_add_time = (TextView) convertView.findViewById(R.id.tv_add_time);
			holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (indext == 0) {
			holder.tv_num.setTextColor(Color.parseColor("#F83F3F"));
		}

		final HashMap<String, Object> mapObj = result.get(position);

		// type:分类 1任务 2下单 3官方奖励 4抽奖 5官方减少
		if ("1".equals(mapObj.get("type").toString())) {
			holder.tv_type.setText("任务奖励");
			holder.tv_add_time.setText(
					DateFormatUtils.format(Long.parseLong(mapObj.get("add_date").toString()), "yyyy-MM-dd  HH:mm:ss"));
			holder.tv_num.setText("+" + mapObj.get("num").toString());
		} else if ("2".equals(mapObj.get("type").toString())) {
			if (indext == 1) {
				holder.tv_type.setText(mapObj.get("p_name").toString() + "解冻");
				holder.tv_num.setText("+" + mapObj.get("num").toString());
			} else if (indext == 2) {
				holder.tv_type.setText(mapObj.get("p_name").toString() + "冻结");
				holder.tv_num.setText(mapObj.get("num").toString());
			}else if(indext == 0){
				holder.tv_type.setText(mapObj.get("p_name").toString());
				holder.tv_num.setText("-"+mapObj.get("num").toString());
			}

			holder.tv_add_time.setText(
					DateFormatUtils.format(Long.parseLong(mapObj.get("add_date").toString()), "yyyy-MM-dd  HH:mm:ss"));
		} else if ("3".equals(mapObj.get("type").toString())) {
			holder.tv_type.setText("衣蝠官方奖励");
			holder.tv_add_time.setText(
					DateFormatUtils.format(Long.parseLong(mapObj.get("add_date").toString()), "yyyy-MM-dd  HH:mm:ss"));
			holder.tv_num.setText("+" + mapObj.get("num").toString());
		} else if ("4".equals(mapObj.get("type").toString())) {
			holder.tv_type.setText("抽奖消耗");
			holder.tv_add_time.setText(
					DateFormatUtils.format(Long.parseLong(mapObj.get("add_date").toString()), "yyyy-MM-dd  HH:mm:ss"));
			holder.tv_num.setText( mapObj.get("num").toString());
		} else if ("5".equals(mapObj.get("type").toString())) {
			holder.tv_type.setText("衣蝠官方扣除");
			holder.tv_add_time.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("add_date").toString()),
					"yyyy-MM-dd  HH:mm:ss  "));
			holder.tv_num.setText(mapObj.get("num").toString());
		}else if ("6".equals(mapObj.get("type").toString())) {
			holder.tv_type.setText("点赞消耗");
			holder.tv_add_time.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("add_date").toString()),
					"yyyy-MM-dd  HH:mm:ss  "));
			holder.tv_num.setText(mapObj.get("num").toString());
		}else if("7".equals(mapObj.get("type").toString())){
			holder.tv_type.setText("抽奖消耗冻结衣豆");
			holder.tv_add_time.setText(
					DateFormatUtils.format(Long.parseLong(mapObj.get("add_date").toString()), "yyyy-MM-dd  HH:mm:ss"));
			holder.tv_num.setText( mapObj.get("num").toString());
		}else{
			try {
				holder.tv_type.setText(mapObj.get("p_name").toString());
				holder.tv_add_time.setText(
						DateFormatUtils.format(Long.parseLong(mapObj.get("add_date").toString()), "yyyy-MM-dd  HH:mm:ss"));
				holder.tv_num.setText( mapObj.get("num").toString());
			}catch (Exception e){

			}

		}		//
		// if (IntergralIncomePage.isTiXianMingXi) {
		// holder.tv_num.append("元");
		// }

		return convertView;
	}

	class ViewHolder {

		TextView tv_add_time, tv_type, tv_num;
	}
}
