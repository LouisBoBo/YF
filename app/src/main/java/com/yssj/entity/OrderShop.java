package com.yssj.entity;

import java.io.Serializable;

import org.apache.commons.lang.math.IntRange;

/**
 * @author Administrator 订单详细表
 */
public class OrderShop implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;// 主键id
	private String order_code;// 订单编号
	private String shop_name;// 商品名字
	private String shop_code;// 商品编号
	private Double shop_price;// 商品价格
	private String shop_pic;// 商品图片
	private Integer shop_num;// 商品数量
	private String size;// 商品尺码
	private String color;// 商品颜色
	private String logi_code;// 商家编号
	private String message;// 买家留言
	private String bak;// 保留字段
	private Integer stocktypeid; // 库存表id
	private Integer change;
	private double voucher_money;//使用的抵用券
	private Long last_time;
	private String supp_id;
    private int issue_status;
    private double  original_price = -1;


    public double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(double original_price) {
        this.original_price = original_price;
    }

    public int getIssue_status() {
        return issue_status;
    }

    public void setIssue_status(int issue_status) {
        this.issue_status = issue_status;
    }


	public String getSupp_id() {
		return supp_id;
	}

	public void setSupp_id(String supp_id) {
		this.supp_id = supp_id;
	}


	public double getVoucher_money() {
		return voucher_money;
	}

	public void setVoucher_money(double voucher_money) {
		this.voucher_money = voucher_money;
	}

	public Integer getChange() {
		return change;
	}

	public void setChange(Integer change) {
		this.change = change;
	}

	public Integer getStocktypeid() {
		return stocktypeid;
	}

	public void setStocktypeid(Integer stocktypeid) {
		this.stocktypeid = stocktypeid;
	}

	private int status; //

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrder_code() {
		return order_code;
	}

	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}

//	去掉//由于后台返回数据不同，买单和卖单要分开，买单需要重新截断数据重整，卖单不需要。0为买单，1为买单
	public String getShop_name(int i) {
		if(i == 0)
			return Shop.getShopNameStr(shop_name);
		else
			return shop_name;
	}

	public void setShop_name(String shop_name) {
		if(null==shop_name){
			shop_name="";
		}
		this.shop_name = shop_name;
	}

	public String getShop_code() {
		return shop_code;
	}

	public void setShop_code(String shop_code) {
		if(null==shop_code){
			shop_code="";
		}
		this.shop_code = shop_code;
	}

	public Double getShop_price() {
		return shop_price;
	}

	public void setShop_price(Double shop_price) {
		this.shop_price = shop_price;
	}

	public String getShop_pic() {
		return shop_pic;
	}

	public void setShop_pic(String shop_pic) {
		if(null==shop_pic){
			shop_pic="";
		}
		this.shop_pic = shop_pic;
	}

	public Integer getShop_num() {
		return shop_num;
	}

	public void setShop_num(Integer shop_num) {
		this.shop_num = shop_num;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		if(null==size){
			size="";
		}
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		if(null==color){
			color="";
		}
		this.color = color;
	}

	public String getLogi_code() {
		return logi_code;
	}

	public void setLogi_code(String logi_code) {
		if(null==logi_code){
			logi_code="";
		}
		this.logi_code = logi_code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		if(null==message){
			message="";
		}
		this.message = message;
	}

	public String getBak() {
		return bak;
	}

	public void setBak(String bak) {
		if(null==bak){
			bak="";
		}
		this.bak = bak;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public Long getLast_timess() {
		return last_time;
	}

	public void setLast_timess(Long last_time) {
		this.last_time = last_time;
	}
	@Override
	public String toString() {
		return "OrderShop [id=" + id + ", order_code=" + order_code
				+ ", shop_name=" + shop_name + ", shop_code=" + shop_code
				+ ", shop_price=" + shop_price + ", shop_pic=" + shop_pic
				+ ", shop_num=" + shop_num + ", size=" + size + ", color="
				+ color + ", logi_code=" + logi_code + ", message=" + message
				+ ", bak=" + bak + ", status=" + status + ", getId()="
				+ getId() + ", getOrder_code()=" + getOrder_code()
				+ ", getShop_name()=" + getShop_name(0) + ", getShop_code()="
				+ getShop_code() + ", getShop_price()=" + getShop_price()
				+ ", getShop_pic()=" + getShop_pic() + ", getShop_num()="
				+ getShop_num() + ", getSize()=" + getSize() + ", getColor()="
				+ getColor() + ", getLogi_code()=" + getLogi_code()
				+ ", getMessage()=" + getMessage() + ", getBak()=" + getBak()
				+ ", getStatus()=" + getStatus() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
