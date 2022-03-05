package com.yssj.entity;

/**
 *
 */
public class ShopGroupList {
	private Integer id;
	private String  value;
	private String  icon;
	private String app_name;
	private String banner;

	public ShopGroupList() {
		super();
	}

	public ShopGroupList(Integer id, String value, String icon, String app_name, String banner) {
		this.id = id;
		this.value = value;
		this.icon = icon;
		this.app_name = app_name;
		this.banner = banner;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}
}
