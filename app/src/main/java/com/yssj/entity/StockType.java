package com.yssj.entity;

import java.io.Serializable;

/**
 * @author Administrator 商品库存分类表
 */
public class StockType implements Serializable {
	private Integer id;// 库存分类id
	private String color_size;// 颜色和尺码id
	private Integer stock;// 库存
	private Double price;// 价格
	private String shop_code;// 商品编号
	private String pic; // 默认图片地址
	private Integer supp_id; // 供应商
	private String shop_name; // 商品名称
	private Double kickback; // 回佣,不大于1为提比,大于1为值
	private Double two_kickback; // 回佣,不大于1为提比,大于1为值

	private Double original_price;// 供应商
	private Integer color_id;// 颜色id
	private Integer size_id;// 尺码id
	private String core;// 可用积分

	public String getAttr_pic() {
		return attr_pic;
	}

	public void setAttr_pic(String attr_pic) {
		this.attr_pic = attr_pic;
	}

	private String attr_pic;

	private Double group_price;//拼团价格
	
	public Double getGroup_price() {
		return group_price;
	}

	public void setGroup_price(Double group_price) {
		this.group_price = group_price;
	}
	
	public StockType() {
		super();
	}

	public String getCore() {
		return core;
	}

	public void setCore(String core) {
		this.core = core;
	}

	public Double getOriginal_price() {
		return original_price;
	}

	public void setOriginal_price(Double original_price) {
		this.original_price = original_price;
	}

	/**
	 * 根据颜色id和尺码id拼接颜色和尺码
	 * 
	 * @param color_id
	 * @param size_id
	 */
	public void setColor_size(Integer color_id, Integer size_id) {
		this.color_size = color_id + ":" + size_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getColor_size() {
		return color_size;
	}

	public void setColor_size(String color_size) {

		if (null == color_size || color_size.equals("null")) {
			color_size = "0";
		}

		this.color_size = color_size;
	}

	public Integer getColor_id() {
		return color_id;
	}

	public void setColor_id(Integer color_id) {
		this.color_id = color_id;
	}

	public Integer getSize_id() {
		return size_id;
	}

	public void setSize_id(Integer size_id) {
		this.size_id = size_id;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getShop_code() {
		return shop_code;
	}

	public void setShop_code(String shop_code) {
		if (null == shop_code || shop_code.equals("null")) {
			shop_code = "0";
		}

		this.shop_code = shop_code;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		if (null == pic || pic.equals("null")) {
			pic = "0";
		}
		this.pic = pic;
	}

	public Integer getSupp_id() {
		return supp_id;
	}

	public void setSupp_id(Integer supp_id) {
		this.supp_id = supp_id;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		if (null == shop_name) {
			shop_name = "0";
		}
		this.shop_name = shop_name;
	}

	public Double getKickback() {
		return kickback;
	}

	public void setKickback(Double kickback) {
		this.kickback = kickback;
	}

	public Double getTwo_kickback() {
		return two_kickback;
	}

	public void setTwo_kickback(Double two_kickback) {
		this.two_kickback = two_kickback;
	}

}
