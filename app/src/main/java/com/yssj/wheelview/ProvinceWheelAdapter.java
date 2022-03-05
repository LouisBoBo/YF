package com.yssj.wheelview;

import java.util.HashMap;
import java.util.List;

import android.content.Context;



public class ProvinceWheelAdapter implements WheelAdapter {
	
//	private List<String> provinces;
	private List<HashMap<String, String>> provinceList;
	
	public ProvinceWheelAdapter(Context context, List<HashMap<String, String>> provinceList){
//		provinces = Arrays.asList(context.getResources().getStringArray(R.array.province_item));
		this.provinceList = provinceList;
	}

	@Override
	public int getItemsCount() {
		return provinceList == null ? 0 : provinceList.size();
	}

	@Override
	public HashMap<String, String> getItem(int index) {
		return index <= provinceList.size() - 1 ? provinceList.get(index) : null;
	}

	@Override
	public int getMaximumLength() {
		return 7;
	}
	
	@Override
	public String getCurrentId(int index) {
		return provinceList.get(index).get("id");
	}

}
