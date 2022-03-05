/*
 *  Copyright 2010 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.yssj.wheelview;

import java.util.HashMap;
import java.util.List;


/**
 * Numeric Wheel adapter.
 */
public class StrericWheelAdapter implements WheelAdapter {
	
	/** The default min value */
//	private String[] strContents;
	private List<HashMap<String, String>> listContent;
	/**
	 * 构造方法
	 * @param strContents
	 */
	public StrericWheelAdapter(List<HashMap<String, String>> listContent){
		this.listContent=listContent;
	}
	
	
	public List<HashMap<String, String>> getStrContents() {
		return listContent;
	}


	public void setStrContents(List<HashMap<String, String>> strContents) {
		this.listContent = strContents;
	}


	public HashMap<String, String> getItem(int index) {
		if (index >= 0 && index < getItemsCount()) {
			return listContent.get(index);
		}
		return null;
	}
	
	public int getItemsCount() {
		return listContent.size();
	}
	/**
	 * 设置最大的宽度
	 */
	public int getMaximumLength() {
		int maxLen=7;
		return maxLen;
	}


	@Override
	public String getCurrentId(int index) {
		// TODO Auto-generated method stub
		return null;
	}
}
