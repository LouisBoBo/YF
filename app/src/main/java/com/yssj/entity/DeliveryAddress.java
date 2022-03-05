package com.yssj.entity;

import java.io.Serializable;

public class DeliveryAddress implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;// 地址id
	private int user_id;// 用户id
	private int province;// 省
	private int city;// 市
	private int area;// 区
	private int street;// 街道
	private String address;// 详细地址
	private String consignee;// 收货人
	private String postcode;// 邮编
	private String phone;// 电话
	private int is_default;// 默认收货地址
	private String detailAddress;

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public DeliveryAddress() {
		super();
	}

	// public String getPostcode() {
	// return postcode;
	// }

	// public void setPostcode(String postcode) {
	// this.postcode = "0";
	// }

	public Integer getIs_default() {

		return is_default;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public void setIs_default(String is_default) {

		if (is_default == null || "".equals(is_default) || is_default.equals("null")) {
			this.is_default = 0;
		} else {
			this.is_default = 0;
			try {
				this.is_default = Integer.parseInt(is_default);
			} catch (Exception e) {
			}

		}

		// this.is_default = is_default;
	}

	public DeliveryAddress(String id, String user_id, String province, String city, String area, String street,
			String address) {
		super();

		if (id == null || "".equals(id) || id.equals("null")) {
			this.id = 0;
		} else {
			this.id = 0;
			try {
				this.id = Integer.parseInt(id);
			} catch (Exception e) {
			}

		}

		if (user_id == null) {
			this.user_id = 0;
		}

		if (province == null || "".equals(province) || province.equals("null")) {
			this.province = 0;
		} else {
			this.province = 0;
			try {
				this.province = Integer.parseInt(province);
			} catch (Exception e) {
			}

		}

		if (city == null || "".equals(city) || city.equals("null")) {
			this.city = 0;
		} else {
			this.city = 0;
			try {
				this.city = Integer.parseInt(city);
			} catch (Exception e) {
			}

		}
		if (area == null || "".equals(area) || area.equals("null")) {
			this.area = 0;
		} else {
			this.area = 0;
			try {
				this.area = Integer.parseInt(area)>0?Integer.parseInt(area):0;
			} catch (Exception e) {
			}

		}

		if (street == null || "".equals(street) || street.equals("null")) {
			this.street = 0;
		} else {
			this.street = 0;
			try {
				this.street = Integer.parseInt(street);
			} catch (Exception e) {
			}

		}

		// this.id = id;
		// this.user_id = user_id;
		// this.province = province;
		// this.city = city;
		// this.area = area;
		// this.street = street;
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(String id) {

		if (id == null || "".equals(id) || id.equals("null")) {
			this.id = 0;
		} else {
			this.id = 0;
			try {
				this.id = Integer.parseInt(id);
			} catch (Exception e) {
			}

		}

		// this.id = id;
	}

	public Integer getUser_id() {

		return user_id;
	}

	public void setUser_id(String user_id) {

		if (user_id == null || "".equals(user_id) || user_id.equals("null")) {
			this.user_id = 0;
		} else {
			this.user_id = 0;
			try {
				this.user_id = Integer.parseInt(user_id);
			} catch (Exception e) {
			}

		}

		// this.user_id = user_id;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(String province) {

		if (province == null || "".equals(province) || province.equals("null")) {
			this.province = 0;
		} else {
			this.province = 0;
			try {
				this.province = Integer.parseInt(province);
			} catch (Exception e) {
			}

		}

		// this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(String city) {

		if (city == null || "".equals(city) || city.equals("null")) {
			this.city = 0;
		} else {
			this.city = 0;
			try {
				this.city = Integer.parseInt(city);
			} catch (Exception e) {
			}

		}

		// this.city = city;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(String area) {

		if (area == null || "".equals(area) || area.equals("null")) {
			this.area = 0;
		} else {
			this.area = 0;
			try {
				this.area = Integer.parseInt(area)>0?Integer.parseInt(area):0;
			} catch (Exception e) {
			}

		}

		// this.area = area;
	}

	public Integer getStreet() {
		return street;
	}

	public void setStreet(String street) {

		if (street == null || "".equals(street) || street.equals("null")) {
			this.street = 0;
		} else {
			this.street = 0;
			try {
				this.street = Integer.parseInt(street);
			} catch (Exception e) {
			}

		}

	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {

		if (address == null || "".equals(address) || address.equals("null")) {
			this.address = "";
		} else {
			this.address = address;

		}

	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {

		if (phone == null || "".equals(phone) || phone.equals("null")) {
			this.phone = "";
		} else {
			this.phone = phone;

		}
	}

}
