package com.yssj.entity;

/**
 * 喜欢表<br/>
 * 
 * @author Administrator
 * 
 */
public class Like {
	private String shop_code;// 商品编号
	private Double shop_price;// 商品价格
	private Double shop_se_price;// 商品价格
	private String show_pic;// 商品图片
	private String shop_name;// 商品名称
	private Double kickback  = 0.00;
	private String isCart; // 是否在购物车中存在
	private String is_show; // 是否置顶
	private String is_del;
	private String isLike; //是否喜爱

	private String app_shop_group_price = "1.5";//1元购价格


	public String getSupp_label_id() {
		return supp_label_id;
	}

	public void setSupp_label_id(String supp_label_id) {
		this.supp_label_id = supp_label_id;
	}

	private String supp_label_id; //供应商ID


	public String getIsLike() {
		return isLike;
	}

	public void setIsLike(String isLike) {
		this.isLike = isLike;
	}

	private String pageCount; // 页码总数

	public String getIs_del() {
		return is_del;
	}

	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}

	public String getIs_show() {
		return is_show;
	}

	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}

	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	public String getShop_code() {
		return shop_code;
	}

	public void setShop_code(String shop_code) {
		this.shop_code = shop_code;
	}


	public String getApp_shop_group_price() {
		return app_shop_group_price;
	}

	public void setApp_shop_group_price(String app_shop_group_price) {
		this.app_shop_group_price = app_shop_group_price;
	}



	public Double getShop_price() {
		return shop_price;
	}

	public void setShop_price(Double shop_price) {
		this.shop_price = shop_price;
	}

	public Double getShop_se_price() {
		return shop_se_price;
	}

	public void setShop_se_price(Double shop_se_price) {
		this.shop_se_price = shop_se_price;
	}

	public String getShow_pic() {
		return show_pic;
	}

	public void setShow_pic(String show_pic) {
		this.show_pic = show_pic;
	}

	public String getShop_name() {
		return Shop.getShopNameStrNew(shop_name);
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public Double getKickback() {
		return kickback;
	}

	public void setKickback(Double kickback) {
		this.kickback = kickback;
	}

	public String getIsCart() {
		return isCart;
	}

	public void setIsCart(String isCart) {
		this.isCart = isCart;
	}

}
