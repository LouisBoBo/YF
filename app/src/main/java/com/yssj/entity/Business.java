package com.yssj.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 商家
 * 
 * @author 戴厚坤
 * 
 */
public class Business implements Serializable {
//	/**
//	 * 
//	 */
//	private Integer id; // 主键
//	private Integer user_id; // 用户id
//	private String bus_code; // 商家编号
//	private String bus_name; // 商家名称
//	private Integer bus_type; // 商家一级类别
//	private Integer bus_type_two; // 商家二级类别
//	private String def_pic; // 默认图片
//	private String phone; // 商家联系人手机
//	private String telephone; // 座机
//	private String salesman_name; // 业务员名字
//	private String real_name; // 机构负责人名字
//	private String bus_telephone; // 机构联系人电话
//	private Character sex; // 联系人性别0男1女
//	private String idcard; // 身份证号
//	private Integer age; // 年龄
//	private String user_service; // 会员服务
//	private String content; // 商家介绍
//	private Double svg_price; // 平均消费
//	private Integer province; // 省
//	private Integer city; // 市
//	private Integer area; // 区
//	private Integer street; // 街道
//	private String addr; // 地址
//	private Double lng; // 经度
//	private Double lat; // 纬度
//	private String bus_pic; // 图片列表15张以内
//	private Long add_time; // 入驻时间
//	private Long audit_time; // 审核时间
//	private Integer audit_admin; // 审核人
//	private Character status; // 状态，1待审核2审核通过3退回
//	private String qr_pic; // 二维码
//	private Double star_level; // 星级，共5星
//	private String tag; // 标签
//	private String remark; // 备注
//	private String intact_addr;
//	private String vip_dis;
//	private String dis_note;
//	
////	public String getVip_dis() {
////		return vip_dis;
////	}
////
//	public void setVip_dis(String vip_dis) {
//		if(vip_dis==null){
//			vip_dis="";
//		}
//		this.vip_dis = vip_dis;
//	}
////
////	public String getDis_note() {
////		return dis_note;
////	}
////
//	public void setDis_note(String dis_note) {
//		if(dis_note==null){
//			dis_note="";
//		}
//		this.dis_note = dis_note;
//	}
////
////	public String getIntact_addr() {
////		return intact_addr;
////	}
////
////	public void setIntact_addr(String intact_addr) {
////		this.intact_addr = intact_addr;
////	}
////
////	public Integer getId() {
////		return id;
////	}
////
////	public void setId(Integer id) {
////		this.id = id;
////	}
////
////	public Integer getUser_id() {
////		return user_id;
////	}
////
////	public void setUser_id(Integer user_id) {
////		this.user_id = user_id;
////	}
////
////	public String getBus_code() {
////		return bus_code;
////	}
////
////	public void setBus_code(String bus_code) {
////		this.bus_code = bus_code;
////	}
////
////	public String getBus_name() {
////		return bus_name;
////	}
////
////	public void setBus_name(String bus_name) {
////		this.bus_name = bus_name;
////	}
////
////	public Integer getBus_type() {
////		return bus_type;
////	}
////
//	public void setBus_type(Integer bus_type) {
//		this.bus_type = bus_type;
//	}
////
////	public Integer getBus_type_two() {
////		return bus_type_two;
////	}
////
//	public void setBus_type_two(Integer bus_type_two) {
//		this.bus_type_two = bus_type_two;
//	}
////
////	public String getDef_pic() {
////		return def_pic;
////	}
////
//	public void setDef_pic(String def_pic) {
//		if(def_pic==null){
//			def_pic="";
//		}
//		this.def_pic = def_pic;
//	}
////
////	public String getPhone() {
////		return phone;
////	}
////
////	public void setPhone(String phone) {
////		this.phone = phone;
////	}
////
////	public String getTelephone() {
////		return telephone;
////	}
////
////	public void setTelephone(String telephone) {
////		this.telephone = telephone;
////	}
////
////	public String getSalesman_name() {
////		return salesman_name;
////	}
////
////	public void setSalesman_name(String salesman_name) {
////		this.salesman_name = salesman_name;
////	}
////
////	public String getReal_name() {
////		return real_name;
////	}
////
////	public void setReal_name(String real_name) {
////		this.real_name = real_name;
////	}
////
////	public String getBus_telephone() {
////		return bus_telephone;
////	}
////
////	public void setBus_telephone(String bus_telephone) {
////		this.bus_telephone = bus_telephone;
////	}
////
////	public Character getSex() {
////		return sex;
////	}
////
////	public void setSex(Character sex) {
////		this.sex = sex;
////	}
////
////	public String getIdcard() {
////		return idcard;
////	}
////
////	public void setIdcard(String idcard) {
////		this.idcard = idcard;
////	}
////
////	public Integer getAge() {
////		return age;
////	}
////
////	public void setAge(Integer age) {
////		this.age = age;
////	}
////
////	public String getUser_service() {
////		return user_service;
////	}
////
//	public void setUser_service(String user_service) {
//		if(user_service==null){
//			user_service="";
//		}
//		this.user_service = user_service;
//	}
////
////	public String getContent() {
////		return content;
////	}
////
////	public void setContent(String content) {
////		this.content = content;
////	}
////
////	public Double getSvg_price() {
////		return svg_price;
////	}
////
////	public void setSvg_price(Double svg_price) {
////		this.svg_price = svg_price;
////	}
////
////	public Integer getProvince() {
////		return province;
////	}
////
//	public void setProvince(Integer province) {
//		this.province = province;
//	}
////
////	public Integer getCity() {
////		return city;
////	}
////
//	public void setCity(Integer city) {
//		this.city = city;
//	}
////
////	public Integer getArea() {
////		return area;
////	}
////
//	public void setArea(Integer area) {
//		this.area = area;
//	}
////
////	public Integer getStreet() {
////		return street;
////	}
////
//	public void setStreet(Integer street) {
//		this.street = street;
//	}
////
////	public String getAddr() {
////		return addr;
////	}
////
////	public void setAddr(String addr) {
////		this.addr = addr;
////	}
////
////	public Double getLng() {
////		return lng;
////	}
////
////	public void setLng(Double lng) {
////		this.lng = lng;
////	}
////
////	public Double getLat() {
////		return lat;
////	}
////
////	public void setLat(Double lat) {
////		this.lat = lat;
////	}
////
////	public String getBus_pic() {
////		return bus_pic;
////	}
////
////	public void setBus_pic(String bus_pic) {
////		this.bus_pic = bus_pic;
////	}
////
////	public Long getAdd_time() {
////		return add_time;
////	}
////
////	public void setAdd_time(Long add_time) {
////		this.add_time = add_time;
////	}
////
////	public Long getAudit_time() {
////		return audit_time;
////	}
////
////	public void setAudit_time(Long audit_time) {
////		this.audit_time = audit_time;
////	}
////
////	public Integer getAudit_admin() {
////		return audit_admin;
////	}
////
////	public void setAudit_admin(Integer audit_admin) {
////		this.audit_admin = audit_admin;
////	}
////
////	public Character getStatus() {
////		return status;
////	}
////
////	public void setStatus(Character status) {
////		this.status = status;
////	}
////
////	public String getQr_pic() {
////		return qr_pic;
////	}
////
////	public void setQr_pic(String qr_pic) {
////		this.qr_pic = qr_pic;
////	}
////
////	public Double getStar_level() {
////		return star_level;
////	}
////
////	public void setStar_level(Double star_level) {
////		this.star_level = star_level;
////	}
////
////	public String getTag() {
////		return tag;
////	}
////
////	public void setTag(String tag) {
////		this.tag = tag;
////	}
////
////	public String getRemark() {
////		return remark;
////	}
////
////	public void setRemark(String remark) {
////		this.remark = remark;
////	}
////
////	public String toJSon(){
////		return "id="+id+"addr="+addr+"bus_code="+bus_code+"bus_name="+bus_name+"bus_pic="+bus_pic+"bus_telephone="+bus_telephone;
////	}
}
