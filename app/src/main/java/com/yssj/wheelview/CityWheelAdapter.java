package com.yssj.wheelview;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.yssj.data.DBService;
import com.yssj.utils.LogYiFu;

import android.content.Context;
import android.util.Log;

public class CityWheelAdapter implements WheelAdapter {
	
//	private List<String> cities;
	private Context context;
	private List<HashMap<String, String>> cityList;
	
	public CityWheelAdapter(Context context, List<HashMap<String, String>> cityList){
		this.context = context;
		this.cityList = cityList;
		LogYiFu.e("cityList", cityList.size()+"");
	}

	@Override
	public int getItemsCount() {
		return cityList == null ? 0 : cityList.size();
	}

	@Override
	public HashMap<String, String> getItem(int index) {
		return index <= cityList.size() - 1 ? cityList.get(index) : null;
	}

	@Override
	public int getMaximumLength() {
		return 7;
	}
	
	public void setCityList(List<HashMap<String, String>> cityList){
//		this.cityList = Arrays.asList(context.getResources().getStringArray(provinceId));
		this.cityList = cityList;
	}

	@Override
	public String getCurrentId(int index) {
		return cityList.get(index).get("id");
	}
}
