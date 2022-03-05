package com.yssj.entity;

import java.io.Serializable;

/***
 * 用户信息
 * 
 * @author Administrator
 * 
 */
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 2296402600988055124L;

	private int user_id;
	private String phone;
	private String email;
	private String nickname;
	private int user_type;
	private String account;
	private String hobby;
	private String imei;
	private String mac;
	private String tag;
	private String parent_id;

	public int getReviewers() {
		return reviewers;
	}

	public void setReviewers(int reviewers) {
		this.reviewers = reviewers;
	}

	private int reviewers;

	// ----------------------------------

	public String getTag() {
		return  tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	private String user_name; // N String 50 真实姓名
	private String user_ident; // Y String 25 身份证

	// private String home_address; // Y String 255 家庭地址
	// private String occupation; // Y String 20 职业
	private int age; // Y Integer 年龄
	private int gender;// Y Int 1 性别 （0=保密，1=男，2=女）
	private String remarks; // Y String 备注

	private String birthday; // Y Date 生日
	private String pic;// Y String 200 用户头像
	private String city; // Y Integer 城市编号 关联areatbl

	private int email_status;
	private String street;// 街道
	private String area;
	
	private String userType = "2";  //用户类别  0未分类，1 A类，2 B类

	private String v_ident ="0"; //用户V  0：为普通用户 ，1：为红V，2：为蓝V

	public String getV_ident() {
		return v_ident;
	}

	public void setV_ident(String v_ident) {
		this.v_ident = v_ident;
	}

	private String province;
	private String uuid;// 微信授权获取的uuid

	private int Is_location;// 是否开启位置服务 Integer 1 必选 0不开启,1开启,默认为开启
	// /////////////////////////////
	private int usertype;// 判断是否是第三方登陆接口
	private String userth_id;

	private String userSign;

	private String is_member;// 0普通用户1已通过验证2会员

	public String getIs_member() {
		return is_member;
	}

	public void setIs_member(String is_member) {

		if (null == is_member) {
			is_member = "";
		}

		this.is_member = is_member;
	}

	public String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if (null == status) {
			status = "";
		}

		this.status = status;
	}

	private String code_type;

	public String getCode_type() {
		return code_type;
	}

	public void setCode_type(String code_type) {
		if (null == code_type) {
			code_type = "";
		}
		this.code_type = code_type;
	}

	public String getUserSign() {
		return userSign;
	}

	public void setUserSign(String userSign) {
		if (null == userSign) {
			userSign = "";
		}
		this.userSign = userSign;
	}

	public String getUserth_id() {
		return userth_id;
	}

	public void setUserth_id(String userth_id) {
		if (null == userth_id) {
			userth_id = "";
		}
		this.userth_id = userth_id;
	}

	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	public int getIs_location() {
		return Is_location;
	}

	public void setIs_location(int is_location) {
		Is_location = is_location;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if (null == phone) {
			phone = "";
		}
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (null == email) {
			email = "";
		}
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		if (null == nickname) {
			nickname = "";
		}
		this.nickname = nickname;
	}
	private String add_date;

	public String getAdd_date() {
		return add_date;
	}

	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}

	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		if (null == user_name) {
			user_name = "";
		}
		this.user_name = user_name;
	}

	public String getUser_ident() {
		return user_ident;
	}

	public void setUser_ident(String user_ident) {
		if (null == user_ident) {
			user_ident = "";
		}
		this.user_ident = user_ident;
	}

	// public String getHome_address() {
	// return home_address;
	// }
	// public void setHome_address(String home_address) {
	// this.home_address = home_address;
	// }
	/*
	 * public String getOccupation() { return occupation; } public void
	 * setOccupation(String occupation) { this.occupation = occupation; }
	 */
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		if (null == remarks) {
			remarks = "";
		}
		this.remarks = remarks;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		if (null == birthday) {
			birthday = "";
		}
		this.birthday = birthday;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		if (null == pic) {
			pic = "";
		}
		this.pic = pic;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		if (null == city) {
			city = "";
		}
		this.city = city;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		if (null == account) {
			account = "";
		}
		this.account = account;
	}

	public int getEmail_status() {
		return email_status;
	}

	public void setEmail_status(int email_status) {
		this.email_status = email_status;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		if (null == street) {
			street = "";
		}
		this.street = street;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		if (null == area) {
			area = "";
		}
		this.area = area;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		if (null == province) {
			province = "";
		}
		this.province = province;
	}



	@Override
	public String toString() {
		return "UserInfo [user_id=" + user_id + ", phone=" + phone + ", email=" + email + ", nickname=" + nickname
				+ ", user_type=" + user_type + ", account=" + account + ", hobby=" + hobby + ", imei=" + imei + ", mac="
				+ mac + ", tag=" + tag + ", user_name=" + user_name + ", user_ident=" + user_ident + ", age=" + age
				+ ", gender=" + gender + ", remarks=" + remarks + ", birthday=" + birthday + ", pic=" + pic + ", city="
				+ city + ", email_status=" + email_status + ", street=" + street + ", area=" + area + ", userType="
				+ userType + ", v_ident=" + v_ident + ", province=" + province + ", uuid=" + uuid + ", Is_location="
				+ Is_location + ", usertype=" + usertype + ", userth_id=" + userth_id + ", userSign=" + userSign
				+ ", is_member=" + is_member + ", status=" + status + ", code_type=" + code_type + "]";
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		if (null == hobby) {
			hobby = "";
		}
		this.hobby = hobby;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		if (null == uuid) {
			uuid = "";
		}
		this.uuid = uuid;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		if (null == imei) {
			imei = "";
		}
		this.imei = imei;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		if (null == mac) {
			mac = "";
		}
		this.mac = mac;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
}
