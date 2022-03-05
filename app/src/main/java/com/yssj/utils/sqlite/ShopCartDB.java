package com.yssj.utils.sqlite;

import java.sql.Date;
import java.util.List;

import com.yssj.ui.activity.shopdetails.StockBean;

/**
 * 购物车数据库
 * 
 * 
 */
public interface ShopCartDB {

	String NAME = "shopcart.db";
	int VERSION = 3;

	/**
	 * 购物车列表
	 * 
	 * 
	 */
	public interface TableBlack {
		// _id, number，type(call,sms,all)
		// integer text

		String shop_code = "shop_code"; // 商品编号 0
		String size = "size"; // 商品尺码 1
		String color = "color"; // 商品颜色 2
		String shop_num = "shop_num"; // 商品的购买数量 integer 3
		String stock_type_id = "stock_type_id";// 库存表id integer 4
		String def_pic = "def_pic"; // 默认图片地址 5
		String user_id = "user_id"; // 用户id integer 6
		String shop_name = "shop_name"; // 商品名称 7
		String store_code = "store_code"; // 店铺编号 8
		String shop_price = "shop_price"; // 商品原价 Double 9
		String shop_se_price = "shop_se_price"; // 商品折后价 Double 10
		String supp_id = "supp_id";// integer 供货商id 11
		String kickback = "kickback";// 返佣Double 12
		String original_price = "original_price";// Double 13
		String valid = "valid"; // 商品是否有效 0有效 1失效integer 14
		String is_del = "is_del";// 商品是否下架，1下架integer 15
		String is_meal_flag = "is_meal_flag";// 表里判断是正价还是特卖integer 0代表正价，1代表特卖
												// 16
		String p_code = "p_code";// 套餐编号 17
		String postage = "postage";// 邮费 Double 18
		String p_s_t_id = "p_s_t_id";// 特卖对应的每件商品库存 id，逗号隔开 19
		// 自己添加
		String p_shop_code = "p_shop_code"; // 套餐商品编号， 以逗号隔开 20
		String p_color = "p_color"; // 套餐商品颜色， 以逗号隔开 21
		String id = "id"; // 主键 ,购物车 integer id 22
		String p_name = "p_name";// 套餐名字    23 
		String supp_label="supp_label";//供应商 24  新加
//		String s_deadline="s_deadline";//正价商品过期时间 long 24
//		String p_deadline="p_deadline";//特卖商品过期时间 long  25
	
		// String p_shop_code2 = "p_shop_code2"; // 商品编号 2
		// String p_shop_code3 = "p_shop_code3"; // 商品编号 3
		// String p_shop_code4 = "p_shop_code3"; // 商品编号 4
		// String p_shop_code5 = "p_shop_code3"; // 商品编号 5
		// 自己添加

		// String[] ids; // id数组

		// Date date_time;// 商品添加时间

		// String like_id=""; // 加心
		String p_type = "p_type";

		// List<StockBean> shop_list;
		String price = "price"; // Double
		// 存储购物车购买商品时，使用的抵用券数量
		String ten = "ten";
		String five = "five";
		String two = "two";
		String one = "one";
		String paired_code = "paired_code";// 搭配编号
		// long s_time;// 系统时间
		String rowCount = "rowCount";

		String TABLE_NAME = "shopcarts";// 表名
		String TABLE_NAME_INVALID = "shopcarts_invalid";// 过期表名
		String COLUMN_ID = "_id";

		/**
		 * 建表的sql
		 */
		// String CREATE_TABLE_SQL = "create table " + TABLE_NAME + "(" +
		// COLUMN_ID + " integer primary key autoincrement,"+id+" integer,"
		// + user_id + " integer," + store_code + " text," + shop_code +
		// " text," + shop_name
		// + " text," + shop_price + " text," + shop_se_price + "
		// text," + size
		// + " text," + shop_num + " integer," + color + " text,"
		// + def_pic + " text," + valid
		// + " integer," + is_del + " integer," + stock_type_id + " integer," +
		// supp_id + " integer," + kickback
		// + " text," + p_type + " integer," + postage + " text,"
		// + p_s_t_id + " text,"
		// + original_price + " integer,"+p_code+" text,"+p_name+" text
		// ," + price + " text," + ten + " integer," + five + "
		// integer," + two
		// + " integer," + one + " integer," + paired_code + " text " +
		// ")";

		String CREATE_TABLE_SQL = "create table " + TABLE_NAME + "(" + COLUMN_ID + " integer primary key autoincrement,"
				+ shop_code + " text," + size + " text," + color + " text," + shop_num + " integer," + stock_type_id
				+ " integer," + def_pic + " text," + user_id + " integer," + shop_name + " text," + store_code
				+ "  text," + shop_price + " text," + shop_se_price + " text," + supp_id + " integer," + kickback
				+ " text," + original_price + " text," + valid + " integer," + is_del + " integer," + is_meal_flag
				+ " integer," + p_code + " text," + postage + " text," + p_s_t_id + " text," + p_shop_code + " text,"
				+ p_color + " text," + id + " integer," +p_name+" text,"+supp_label+" text "+ ")";
		String CREATE_TABLE_SQL_INVALID = "create table " + TABLE_NAME_INVALID + "(" + COLUMN_ID + " integer primary key autoincrement,"
				+ shop_code + " text," + size + " text," + color + " text," + shop_num + " integer," + stock_type_id
				+ " integer," + def_pic + " text," + user_id + " integer," + shop_name + " text," + store_code
				+ "  text," + shop_price + " text," + shop_se_price + " text," + supp_id + " integer," + kickback
				+ " text," + original_price + " text," + valid + " integer," + is_del + " integer," + is_meal_flag
				+ " integer," + p_code + " text," + postage + " text," + p_s_t_id + " text," + p_shop_code + " text,"
				+ p_color + " text," + id + " integer," +p_name+" text "+ ")";

		String QUERY_ALL_SQL = "select " + shop_code + "," + size + "," + color + "," + shop_num + "," + stock_type_id
				+ "," + def_pic + "," + user_id + "," + shop_name + "," + store_code + "," + shop_price + ","
				+ shop_se_price + "," + supp_id + "," + "kickback" + "," + original_price + "," + valid + "," + is_del
				+ "," + is_meal_flag + "," + p_code + "," + postage + "," + p_s_t_id + "," + p_shop_code + "," + p_color
				+ "," + id +","+p_name+","+supp_label+ " from " + TABLE_NAME;
		String QUERY_ALL_SQL_INVALID = "select " + shop_code + "," + size + "," + color + "," + shop_num + "," + stock_type_id
				+ "," + def_pic + "," + user_id + "," + shop_name + "," + store_code + "," + shop_price + ","
				+ shop_se_price + "," + supp_id + "," + "kickback" + "," + original_price + "," + valid + "," + is_del
				+ "," + is_meal_flag + "," + p_code + "," + postage + "," + p_s_t_id + "," + p_shop_code + "," + p_color
				+ "," + id +","+p_name+" from " + TABLE_NAME_INVALID;
		
		String UPGRADE_1_SHOPCARTS = "alter table "+TABLE_NAME+" add column supp_label text;";//增加一列supp_label
	}

	// + valid + " integer," + is_del + " integer," + p_type + " integer,"
	// + postage + " text," + p_s_t_id + " text," + p_code + " text," + p_name
	// + " text," + price + " text," + ten + " integer," + five + " integer," +
	// two + " integer,"
	// + one + " integer," + paired_code + " text "
	public interface TableXXX {

	}

}
