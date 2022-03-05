package com.yssj.ui.adpter;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.ArrayAdapterCompat;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;


public class AllCircleAdapter extends ArrayAdapterCompat<HashMap<String, Object>> {
	private Context context;
	private LayoutInflater inflater;
	

	
	public AllCircleAdapter(Context context ) {
		super(context);
		this.context = context;

		inflater = LayoutInflater.from(context);
		
	}

	

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
	
		ViewHolder holder;
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.all_circle_item, null);
			
			holder = new ViewHolder();
			holder.img_title =(ImageView) convertView.findViewById(R.id.img_title);
			holder.tv_title =(TextView) convertView.findViewById(R.id.tv_title);
			holder.tv_desc =(TextView) convertView.findViewById(R.id.tv_desc);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		HashMap<String, Object> mapObj = getItem(position);
//		SetImageLoader.initImageLoader(context, holder.img_title, (String) mapObj.get("pic"),"");
		
		PicassoUtils.initImage(context, (String) mapObj.get("pic"), holder.img_title);
		
		holder.tv_title.setText((CharSequence) mapObj.get("title"));
		holder.tv_desc.setText((CharSequence) mapObj.get("content"));
		
		return convertView;
	}
	
	

	

	class ViewHolder {
		ImageView img_title;
		TextView tv_title, tv_desc;
	}

}
