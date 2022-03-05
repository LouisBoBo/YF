package com.yssj.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * @author Administrator 商品表
 */
public class IntegralShop implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id; // 主键
	private String shop_code; // 商品编号
	private String shop_name; // 商品名称
	private Double shop_price; // 商品价格
	private String shop_attr; // 商品属性
	private Double shop_se_price; // 商品出售价格
	private String def_pic; // 默认图片地址
	private String shop_pic; // 图片id列表
	private Integer shelf; // 1下架2上架
	private Date shop_up_time; // 商品上架时间
	private Date shop_down_time; // 商品下架时间
	private Integer invertory_num; // 库存
	private char is_new; // 是否新品
	private char is_hot; // 是否热门
	private Date add_time; // 商品添加时间
	private Date audit_time; // 商品审核时间
	private Integer actual_sales; // 实际销量
	private Integer virtual_sales; // 虚拟销量
	private Integer adm_id; // 审核人
	private Integer supp_id; // 供应商
	private String content; // 商品简介
	private Integer clicks; // 点击量
	private Integer shop_status; // 商品状态1待审核2审核通过3退回
	private String remark; // 备注
	private Integer love_num; // 喜欢人数
	private Double kickback; // 回佣,不大于1为提比,大于1为值
	private Date shop_discount_time; // 打折结束时间
	private Integer type1;// 一级类目
	private Integer type2;// 二级类目
	private Integer type3;// 三级类目
	private Integer type4;// 四级类目
	private Integer age;// 年龄属性
	private Integer size;// 尺码属性
	private Integer fix_price;// 定价属性
	private Integer occasion;// 场合属性
	private Integer favorite;// 最爱属性
	private Integer sys_color;// 色系属性
	private Integer pattern;// 图案属性
	private Integer stuff;// 材质属性
	private Integer trait;// 特点属性
	private Integer style;// 风格属性

	private List<StockType> stocktype;
	private List<StockType> list_stock_type;// 商品库存分类 stock_type[]

	private Double minshop_se_price; // 最低价格
	private Double maxshop_se_price; // 最高价格

	public IntegralShop() {
		super();
	}

	public IntegralShop(String shop_code, Integer love_num) {
		super();
		this.shop_code = shop_code;
		this.love_num = love_num;
	}

	public List<StockType> getStocktype() {
		return stocktype;
	}

	public void setStocktype(List<StockType> stocktype) {
		this.stocktype = stocktype;
	}

	public List<StockType> getList_stock_type() {
		return list_stock_type;
	}

	public void setList_stock_type(List<StockType> list_stock_type) {
		this.list_stock_type = list_stock_type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShop_code() {
		return shop_code;
	}

	public void setShop_code(String shop_code) {
		this.shop_code = shop_code;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public Double getShop_price() {
		return shop_price;
	}

	public void setShop_price(Double shop_price) {
		this.shop_price = shop_price;
	}

	public String getShop_attr() {
		return shop_attr;
	}

	public void setShop_attr(String shop_attr) {
		this.shop_attr = shop_attr;
	}

	public Double getShop_se_price() {
		return shop_se_price;
	}

	public void setShop_se_price(Double shop_se_price) {
		this.shop_se_price = shop_se_price;
	}

	public String getDef_pic() {
		return def_pic;
	}

	public void setDef_pic(String def_pic) {
		this.def_pic = def_pic;
	}

	public String getShop_pic() {
		return shop_pic;
	}

	public void setShop_pic(String shop_pic) {
		this.shop_pic = shop_pic;
	}

	public Integer getShelf() {
		return shelf;
	}

	public void setShelf(Integer shelf) {
		this.shelf = shelf;
	}

	public Date getShop_up_time() {
		return shop_up_time;
	}

	public void setShop_up_time(Date shop_up_time) {
		this.shop_up_time = shop_up_time;
	}

	public Date getShop_down_time() {
		return shop_down_time;
	}

	public void setShop_down_time(Date shop_down_time) {
		this.shop_down_time = shop_down_time;
	}

	public Integer getInvertory_num() {
		return invertory_num;
	}

	public void setInvertory_num(Integer invertory_num) {
		this.invertory_num = invertory_num;
	}

	public char getIs_new() {
		return is_new;
	}

	public void setIs_new(char is_new) {
		this.is_new = is_new;
	}

	public char getIs_hot() {
		return is_hot;
	}

	public void setIs_hot(char is_hot) {
		this.is_hot = is_hot;
	}

	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}

	public Date getAudit_time() {
		return audit_time;
	}

	public void setAudit_time(Date audit_time) {
		this.audit_time = audit_time;
	}

	public Integer getActual_sales() {
		return actual_sales;
	}

	public void setActual_sales(Integer actual_sales) {
		this.actual_sales = actual_sales;
	}

	public Integer getVirtual_sales() {
		return virtual_sales;
	}

	public void setVirtual_sales(Integer virtual_sales) {
		this.virtual_sales = virtual_sales;
	}

	public Integer getAdm_id() {
		return adm_id;
	}

	public void setAdm_id(Integer adm_id) {
		this.adm_id = adm_id;
	}

	public Integer getSupp_id() {
		return supp_id;
	}

	public void setSupp_id(Integer supp_id) {
		this.supp_id = supp_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getClicks() {
		return clicks;
	}

	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}

	public Integer getShop_status() {
		return shop_status;
	}

	public void setShop_status(Integer shop_status) {
		this.shop_status = shop_status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getLove_num() {
		return love_num;
	}

	public void setLove_num(Integer love_num) {
		this.love_num = love_num;
	}

	public Double getKickback() {
		return kickback;
	}

	public void setKickback(Double kickback) {
		this.kickback = kickback;
	}

	public Date getShop_discount_time() {
		return shop_discount_time;
	}

	public void setShop_discount_time(Date shop_discount_time) {
		this.shop_discount_time = shop_discount_time;
	}

	public Integer getType1() {
		return type1;
	}

	public void setType1(Integer type1) {
		this.type1 = type1;
	}

	public Integer getType2() {
		return type2;
	}

	public void setType2(Integer type2) {
		this.type2 = type2;
	}

	public Integer getType3() {
		return type3;
	}

	public void setType3(Integer type3) {
		this.type3 = type3;
	}

	public Integer getType4() {
		return type4;
	}

	public void setType4(Integer type4) {
		this.type4 = type4;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getFix_price() {
		return fix_price;
	}

	public void setFix_price(Integer fix_price) {
		this.fix_price = fix_price;
	}

	public Integer getOccasion() {
		return occasion;
	}

	public void setOccasion(Integer occasion) {
		this.occasion = occasion;
	}

	public Integer getFavorite() {
		return favorite;
	}

	public void setFavorite(Integer favorite) {
		this.favorite = favorite;
	}

	public Integer getSys_color() {
		return sys_color;
	}

	public void setSys_color(Integer sys_color) {
		this.sys_color = sys_color;
	}

	public Integer getPattern() {
		return pattern;
	}

	public void setPattern(Integer pattern) {
		this.pattern = pattern;
	}

	public Integer getStuff() {
		return stuff;
	}

	public void setStuff(Integer stuff) {
		this.stuff = stuff;
	}

	public Integer getTrait() {
		return trait;
	}

	public void setTrait(Integer trait) {
		this.trait = trait;
	}

	public Integer getStyle() {
		return style;
	}

	public void setStyle(Integer style) {
		this.style = style;
	}

	public Double getMinshop_se_price() {
		return minshop_se_price;
	}

	public void setMinshop_se_price(Double minshop_se_price) {
		this.minshop_se_price = minshop_se_price;
	}

	public Double getMaxshop_se_price() {
		return maxshop_se_price;
	}

	public void setMaxshop_se_price(Double maxshop_se_price) {
		this.maxshop_se_price = maxshop_se_price;
	}


}
