package com.yssj.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShareShop implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int count;
	private List<HashMap<String, Object>> user_list;

	public int getCount() {
		return count;
	}

	public List<HashMap<String, Object>> getUser_list() {
		return user_list;
	}

	public void setUser_list(List<HashMap<String, Object>> user_list) {
		this.user_list = user_list;
	}

	public void setCount(int count) {
		this.count = count;
	}

//	
//	 private class getPic{
//		 private String pic;
//
//		public String getPic() {
//			return pic;
//		}
//
//		public void setPic(String pic) {
//			this.pic = pic;
//		}
//	 }

	
		
}
