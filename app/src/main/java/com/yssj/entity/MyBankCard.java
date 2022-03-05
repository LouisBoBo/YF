package com.yssj.entity;

/**
 * 我的银行卡
 * 
 * @author Administrator
 * 
 */
public class MyBankCard {
	private Integer id;
	private String bank_name;// 银行名称
	private String bank_no;// 银行卡号
	private Integer user_id;// 用户id
	private String name;// 用户真实姓名
	private String identity;// 用户身份证号
	private String branch_name;// 支行名称
	private String phone;// 手机号码
	private int province_id;
	private String province;
	private int city_id;
	private String city;

	public Integer getId() {
		return id;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {

		if (null == branch_name) {

			branch_name = "";
		}

		this.branch_name = branch_name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {

		if (null == bank_name) {

			bank_name = "";
		}

		this.bank_name = bank_name;
	}

	public String getBank_no() {
		return bank_no;
	}

	public void setBank_no(String bank_no) {
		
		if (null == bank_no) {

			bank_no = "";
		}
		
		this.bank_no = bank_no;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		
		if (null == name) {

			name = "";
		}
		
		this.name = name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		if (null == identity) {

			identity = "";
		}
		
		this.identity = identity;
	}

	public MyBankCard(Integer user_id) {
		super();
		this.user_id = user_id;
	}

	public MyBankCard() {
		super();
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

	public int getProvince_id() {
		return province_id;
	}

	public void setProvince_id(int province_id) {
		this.province_id = province_id;
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

	public int getCity_id() {
		return city_id;
	}

	public void setCity_id(int city_id) {
		this.city_id = city_id;
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

}
