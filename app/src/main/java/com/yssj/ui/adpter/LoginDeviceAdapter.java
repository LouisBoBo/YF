package com.yssj.ui.adpter;

import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.base.BaseMainAdapter;
import com.yssj.utils.DateUtil;

public class LoginDeviceAdapter extends BaseMainAdapter {

	public LoginDeviceAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.setting_logindevice_item, null);
			holder.tv_devicename = (TextView) convertView.findViewById(R.id.tv_devicename);
			holder.tv_access_time = (TextView) convertView.findViewById(R.id.tv_access_time);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		HashMap<String, Object> map = result.get(position);
		String deviceName = "";
		if("1".equals(map.get("device").toString())){
			deviceName = "安卓设备";
		}else if("2".equals(map.get("device").toString())){
			deviceName = "iOS设备";
		}
		/*else if("2".equals(map.get("device").toString())){
			deviceName = "电脑登陆";
		}*/
		holder.tv_devicename.setText(deviceName);
		holder.tv_access_time.setText(DateUtil.twoDateDistance(new Date((Long)map.get("login_time")),new Date(System.currentTimeMillis())));
		
		return convertView;
	}
	
	class ViewHolder{
		TextView tv_devicename,tv_access_time;
	}

}
