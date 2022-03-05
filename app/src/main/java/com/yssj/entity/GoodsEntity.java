package com.yssj.entity;

import java.io.Serializable;


/**
 * 
 * 商品购买实体类
 * @author lbp
 *
 */

public class GoodsEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7162388307695131818L;

	private String pic,size,color;
	
	private int shop_num,stock_type_id,stock,supp_id,type,id;
	
	private double price,kickback, original_price;
	
	

	public GoodsEntity(String pic, String size, String color, int shop_num,
			int stock_type_id, int stock, int supp_id, int type, double price,
			double kickback, double original_price) {
		super();
		this.pic = pic;
		this.size = size;
		this.color = color;
		this.shop_num = shop_num;
		this.stock_type_id = stock_type_id;
		this.stock = stock;
		this.supp_id = supp_id;
		this.type = type;
		this.price = price;
		this.kickback = kickback;
		this.original_price = original_price;
		this.id=id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	public double getOriginal_price() {
		return original_price;
	}



	public void setOriginal_price(double original_price) {
		this.original_price = original_price;
	}



	public int getSupp_id() {
		return supp_id;
	}

	public void setSupp_id(int supp_id) {
		this.supp_id = supp_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getKickback() {
		return kickback;
	}

	public void setKickback(double kickback) {
		this.kickback = kickback;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getShop_num() {
		return shop_num;
	}

	public void setShop_num(int shop_num) {
		this.shop_num = shop_num;
	}

	public int getStock_type_id() {
		return stock_type_id;
	}

	public void setStock_type_id(int stock_type_id) {
		this.stock_type_id = stock_type_id;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
}
