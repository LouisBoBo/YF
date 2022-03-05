package com.yssj.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;

import com.yssj.ui.activity.shopdetails.StockBean;

/**
 * @author Administrator 购物车表
 */
public class ShopCart implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id; // 主键
	private Integer user_id; // 用户id
	private String store_code; // 店铺编号
	private String shop_code; // 商品编号
	private String shop_name; // 商品名称
	private Double shop_price; // 商品原价
	private Double shop_se_price; // 商品折后价
	private String size; // 商品尺码
	private Integer shop_num; // 商品的购买数量
	private String color; // 商品颜色
	private String def_pic; // 默认图片地址
	private Integer valid; // 商品是否有效 0有效 1失效
	private Integer is_del;// 商品是否下架，1下架
	private String[] ids; // id数组
	private Integer stock_type_id;// 库存表id
	private Date date_time;// 商品添加时间
	private Integer supp_id;// 供货商id
	private String like_id; // 加心
	private Double kickback;// 返佣
	private String core;//当前商品可用积分
	private String supp_label;//供应商名字
	private Double shop_group_price; // 商品拼团价

	
	public String getSupp_label() {
		return supp_label;
	}

	public void setSupp_label(String supp_label) {
		this.supp_label = supp_label;
	}
	public String getCore() {
		return core;
	}

	public void setCore(String core) {
		this.core = core;
	}

	private int p_type;

	private Double postage;

	private String p_s_t_id;

	private Double original_price;

	private String p_code;
	private String p_name;
	private List<StockBean> shop_list;
	private Double price;
	// 存储购物车购买商品时，使用的抵用券数量
	private int ten;
	private int five;
	private int two;
	private int one;
	private String paired_code;// 搭配编号
	private long s_time;// 系统时间
	private long s_deadline;// 正价购物车过期时间
	private long p_deadline;// 特卖购物车过期时间
	private int rowCount;
	private int is_meal_flag;// 判断是正价商品还是特卖商品 0正价，1特卖
	private String p_shop_code;// 套餐商品编号，
	private String p_color;// 套餐颜色
//	private String is_out_date;
	// private int p_count;
	// private int s_count;
	//
	// public int getP_count() {
	// return p_count;
	// }
	//
	// public void setP_count(int p_count) {
	// this.p_count = p_count;
	// }
	// public int getS_count() {
	// return s_count;
	// }
	//
	// public void setS_count(int s_count) {
	// this.s_count = s_count;
	// }
//	public String getIs_out_date() {
//		return is_out_date;
//	}
//
//	public void setIs_out_date(String is_out_date) {
//		this.is_out_date = is_out_date;
//	}
	public String getP_shop_code() {
		return p_shop_code;
	}

	public void setP_shop_code(String p_shop_code) {
		if(null==p_shop_code||"null".equals(p_shop_code)){
			p_shop_code="";
		}
		this.p_shop_code = p_shop_code;
	}

	public String getP_color() {
		return p_color;
	}

	public void setP_color(String p_color) {
		if(null==p_color||"null".equals(p_color)){
			p_color="";
		}
		this.p_color = p_color;
	}

	public int getIs_meal_flag() {
		return is_meal_flag;
	}

	public void setIs_meal_flag(int is_meal_flag) {
		this.is_meal_flag = is_meal_flag;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public long getS_time() {
		return s_time;
	}

	public void setS_time(long s_time) {
		this.s_time = s_time;
	}

	public long getS_deadline() {
		return s_deadline;
	}

	public void setS_deadline(long s_deadline) {
		this.s_deadline = s_deadline;
	}

	public long getP_deadline() {
		return p_deadline;
	}

	public void setP_deadline(long p_deadline) {
		this.p_deadline = p_deadline;
	}

	public String getPaired_code() {
		return paired_code;
	}

	public void setPaired_code(String paired_code) {
		if(null==paired_code||"null".equals(paired_code)){
			paired_code="";
		}
		this.paired_code = paired_code;
	}

	public int getTen() {
		return ten;
	}

	public void setTen(int ten) {
		this.ten = ten;
	}

	public int getFive() {
		return five;
	}

	public void setFive(int five) {
		this.five = five;
	}

	public int getTwo() {
		return two;
	}

	public void setTwo(int two) {
		this.two = two;
	}

	public int getOne() {
		return one;
	}

	public void setOne(int one) {
		this.one = one;
	}

	public String getP_code() {
		return p_code;
	}

	public void setP_code(String p_code) {
		if(null==p_code||"null".equals(p_code)){
			p_code="";
		}
		this.p_code = p_code;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<StockBean> getShop_list() {
		return shop_list;
	}

	public void setShop_list(List<StockBean> shop_list) {
		this.shop_list = shop_list;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		if(null==p_name){
			p_name="";
		}
		this.p_name = p_name;
	}

	public Double getOriginal_price() {
		return original_price;
	}

	public void setOriginal_price(Double original_price) {
		this.original_price = original_price;
	}

	public String getP_s_t_id() {
		return p_s_t_id;
	}

	public void setP_s_t_id(String p_s_t_id) {
		if(null==p_s_t_id||"null".equals(p_s_t_id)){
			p_s_t_id="";
		}
		this.p_s_t_id = p_s_t_id;
	}

	public Double getPostage() {
		return postage;
	}

	public void setPostage(Double postage) {
		this.postage = postage;
	}

	public int getP_type() {
		return p_type;
	}

	public void setP_type(int p_type) {
		this.p_type = p_type;
	}

	public String getLike_id() {
		return like_id;
	}

	public void setLike_id(String like_id) {
		if(null==like_id||"null".equals(like_id)){
			like_id="";
		}
		this.like_id = like_id;
	}

	private String[] shop_codes; // 商品编号

	public ShopCart() {
		super();
	}

	public Double getKickback() {
		return kickback;
	}

	public void setKickback(Double kickback) {
		this.kickback = kickback;
	}

	public ShopCart(Integer user_id, String[] shop_codes) {
		this.shop_codes = shop_codes;
		this.user_id = user_id;
	}

	/**
	 * 根据用户id查询
	 * 
	 * @param user_id
	 */
	public ShopCart(Integer user_id) {
		super();
		this.user_id = user_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getShop_code() {
		return shop_code;
	}

	public void setShop_code(String shop_code) {
		if(null==shop_code||"null".equals(shop_code)){
			shop_code="";
		}
		this.shop_code = shop_code;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		if(null==shop_name||"null".equals(shop_name)){
			shop_name="";
		}
		this.shop_name = shop_name;
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

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		if(null==size||"null".equals(size)){
			size="";
		}
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		if(null==color||"null".equals(color)){
			color="";
		}
		this.color = color;
	}

	public Integer getShop_num() {
		return shop_num;
	}

	public void setShop_num(Integer shop_num) {
		this.shop_num = shop_num;
	}

	public String getDef_pic() {
		return def_pic;
	}

	public void setDef_pic(String def_pic) {
		if(null==def_pic||"null".equals(def_pic)){
			def_pic="";
		}
		this.def_pic = def_pic;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public Integer getStock_type_id() {
		return stock_type_id;
	}

	public void setStock_type_id(Integer stock_type_id) {
		this.stock_type_id = stock_type_id;
	}

	public Date getDate_time() {
		return date_time;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		if(null==store_code||"null".equals(store_code)){
			store_code="";
		}
		this.store_code = store_code;
	}

	public String[] getShop_codes() {
		return shop_codes;
	}

	public void setShop_codes(String[] shop_codes) {
		this.shop_codes = shop_codes;
	}

	public Integer getSupp_id() {
		return supp_id;
	}

	public void setSupp_id(Integer supp_id) {
		this.supp_id = supp_id;
	}

	public Double getShop_group_price() {
		return shop_group_price;
	}

	public void setShop_group_price(Double shop_group_price) {
		this.shop_group_price = shop_group_price;
	}
	public class SpecialBean {
		private String shop_code;
		private int supp_id;
	}

}
