package com.yssj.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**我的优惠券
 * @author Administrator
 *
 */
public class MyCoupon {
	private Integer id;
	private Integer user_id;//用户id
	private Integer c_id;//优惠券id
	private Integer num;//张数
	private String add_time;//获取时间
	private String last_time;//过期时间
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getC_id() {
		return c_id;
	}
	public void setC_id(Integer c_id) {
		this.c_id = c_id;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getAdd_time() {
		return sdf.format(Long.valueOf(add_time));
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	public String getLast_time() {
		return sdf.format(Long.valueOf(last_time));
	}
	public void setLast_time(String last_time) {
		this.last_time = last_time;
	}
	
}
