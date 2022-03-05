package com.yssj.entity;

import java.io.Serializable;

/**
 * 商品广告设置
 * 
 * @author Administrator
 *
 */
public class ShopOption implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer type; // 类型 1:顶部广告 2:中间广告
	private String shop_code; // 商品编号
	private String shop_name; // 商品名称
	private String url; // 图片路径
	private String remark;
	private Integer option_type;

	public Integer getOption_type() {
		return option_type;
	}

	public void setOption_type(Integer option_type) {
		this.option_type = option_type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {

		if (null == shop_name) {
			this.shop_name = "";
		} else {
			this.shop_name = shop_name;
		}

	}

	public String getShop_code() {
		return shop_code;
	}

	public void setShop_code(String shop_code) {

		if (null == shop_code) {
			this.shop_code = "";
		} else {
			this.shop_code = shop_code;
		}

	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {

		if (null == url) {
			this.url = "";
		} else {
			this.url = url;
		}

	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {

		if (null == remark) {
			this.remark = "";
		} else {
			this.remark = remark;
		}

	}
}
