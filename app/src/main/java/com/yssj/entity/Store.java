package com.yssj.entity;

import android.content.Context;

import com.yssj.YJApplication;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.utils.YCache;

import java.io.Serializable;

public class Store implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer user_id;// 店主ID
	private String s_code;// 店铺编号
	private String s_name;// 店铺名称
	private String s_pic;// 店铺图片
	private String s_bg_pic;//模板编号
	private String s_sign;//店招图片
	private String s_content;// 店铺介绍
	private String notice;// 店铺公告
	private Integer s_clicks;// 店铺点击数
	private Integer s_fans;// 店铺粉丝数
	private String coupon_list;// 优惠券
	private String remark;// 备注
	private String realm;// 店铺域名
	private Integer is_up;// 是否可以修改域名,0不可以,1可以,默认为可以
	private String templet_code;//模板编号
	private String circle_sys_pic;//系统推送轮播商品编号及大图
	private String circle_user_pic;//用户选择轮播商品编号及大图
	private Integer circle_status;//0为系统推送，1为用户设定
	
	public String getRealm(Context context) {
		return YCache.getCacheUser(context).getUser_id()+"";
	}

	public void setRealm(String realm) {
		if(null==realm||"null".equals(realm)){
			realm="";
		}
		this.realm = realm;
	}

	public Integer getIs_up() {
		return is_up;
	}

	public void setIs_up(Integer is_up) {
		this.is_up = is_up;
	}

	public Store() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Store( Integer user_id,String s_code, String s_name, String s_pic,String s_bg_pic,String s_sign,
			 String realm) {
		super();
		this.user_id = user_id;
		this.s_code = s_code;
		this.s_name = s_name;
		this.s_pic = s_pic;
		this.s_bg_pic = s_bg_pic;
		this.s_sign = s_sign;
		this.realm=realm;
	}

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

	public String getS_code() {
		return s_code;
	}

	public void setS_code(String s_code) {
		if(null==s_code||"null".equals(s_code)){
			s_code="";
		}
		this.s_code = s_code;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		if(null==s_name||"null".equals(s_name)){
			s_code="";
		}
		this.s_name = s_name;
	}

	public String getS_pic() {
		return s_pic;
	}

	public void setS_pic(String s_pic) {
		if(null==s_pic||"null".equals(s_pic)){
			s_pic="";
		}
		this.s_pic = s_pic;
	}

	public String getS_bg_pic() {
		return s_bg_pic;
	}

	public void setS_bg_pic(String s_bg_pic) {
		if(null==s_bg_pic||"null".equals(s_bg_pic)){
			s_bg_pic="";
		}
		this.s_bg_pic = s_bg_pic;
	}

	public String getS_sign() {
		return s_sign;
	}

	public void setS_sign(String s_sign) {
		if(null==s_sign||"null".equals(s_sign)){
			s_sign="";
		}
		this.s_sign = s_sign;
	}

	public String getS_content() {
		return s_content;
	}

	public void setS_content(String s_content) {
		if(null==s_content||"null".equals(s_content)){
			s_content="";
		}
		this.s_content = s_content;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		if(null==notice||"null".equals(notice)){
			notice="";
		}
		this.notice = notice;
	}

	public Integer getS_clicks() {
		return s_clicks;
	}

	public void setS_clicks(Integer s_clicks) {
	
		this.s_clicks = s_clicks;
	}

	public Integer getS_fans() {
		return s_fans;
	}

	public void setS_fans(Integer s_fans) {
		this.s_fans = s_fans;
	}

	public String getCoupon_list() {
		return coupon_list;
	}

	public void setCoupon_list(String coupon_list) {
		if(null==coupon_list||"null".equals(coupon_list)){
			coupon_list="";
		}
		this.coupon_list = coupon_list;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		if(null==remark||"null".equals(remark)){
			remark="";
		}
		this.remark = remark;
	}

	public String getTemplet_code() {
		return templet_code;
	}

	public void setTemplet_code(String templet_code) {
		if(null==templet_code||"null".equals(templet_code)){
			templet_code="";
		}
		this.templet_code = templet_code;
	}

	public String getCircle_sys_pic() {
		return circle_sys_pic;
	}

	public void setCircle_sys_pic(String circle_sys_pic) {
		if(null==circle_sys_pic||"null".equals(circle_sys_pic)){
			circle_sys_pic="";
		}
		this.circle_sys_pic = circle_sys_pic;
	}

	public String getCircle_user_pic() {
		return circle_user_pic;
	}

	public void setCircle_user_pic(String circle_user_pic) {
		if(null==circle_user_pic||"null".equals(circle_user_pic)){
			circle_user_pic="";
		}
		this.circle_user_pic = circle_user_pic;
	}

	public Integer getCircle_status() {
		return circle_status;
	}

	public void setCircle_status(Integer circle_status) {
		this.circle_status = circle_status;
	}

	
}
