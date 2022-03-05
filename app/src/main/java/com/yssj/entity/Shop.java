package com.yssj.entity;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/***
 * 商品详细信息
 *
 * @author Administrator
 *
 */
public class Shop implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String audit_time; //七天倒计时
    private long s_time;//倒计时系统时间
    private long c_time;//购物车时间

    public String getReturnOneText() {
        return returnOneText;
    }

    public void setReturnOneText(String returnOneText) {
        this.returnOneText = returnOneText;
    }

    private String returnOneText;

    private Double random;

    public Double getRandom() {
        return random;
    }

    public void setRandom(Double random) {
        this.random = random;
    }

    public long getC_time() {
        return c_time;
    }

    public void setC_time(long c_time) {
        this.c_time = c_time;
    }

    public long getS_time() {
        return s_time;
    }

    public void setS_time(long s_time) {
        this.s_time = s_time;
    }

    private int id;// n Integer 11 商品id，主键
    private String shop_code;// y String 50 商品编号
    private String collocation_code;//搭配商品的 搭配编号
    private String shop_name;// String 50 商品名称
    private double Shop_price;// n Double 商品价格 原价
    private String[] shop_type_id;// y String 200 商品类型
    private List<String[]> shop_attr;// n String 100 商品属性 包含颜色，尺寸等
    private double shop_se_price; // n Double 商品出售价格 折后价
    private String def_pic;// String 默认图片地址
    private String shop_pic; // String 图片ID列表
    private long shop_up_time; // Date 商品上架时间
    private int invertory_num; // Integer 库存
    private String is_new; // char 1 是否新品 0否 ，1是
    private String is_hot; // char 1 是否热门 0否 ，1是
    private int actual_sales; // Integer 实际销量
    private int virtual_sales; // Integer 虚拟销量
    private String content; // /String 商品简介
    private int clicks; // Integer 点击量
    private String remark; // String 备注
    private int shop_number; // Integer 购买的数量
    private int love_num; // Integer 喜欢人数
    private Pager pager; // /Pager
    // /分页对象 条件查询时返回
    private double minshop_se_price; // Double 价格最小值 不返回数据
    private double maxshop_se_price; // Double 价格最大值 不返回数据
    private List<StockType> stocktype;
    private long shop_discount_time;
    private List<StockType> list_stock_type;// 商品库存分类 stock_type[]
    private double kickback;// 返现

    private int praise_count; // 好评数 Integer
    private int med_count; // 中评数 Intege
    private int bad_count; // 差评数 Integer
    private float eva_count; // 评价总数 Integer
    private int like_id; // 加心
    private int cart_count;// 购物车上的商品数量

    private float color_count;// 色差

    public int getAdvance_sale_days() {
        return advance_sale_days;
    }

    public void setAdvance_sale_days(int advance_sale_days) {
        this.advance_sale_days = advance_sale_days;
    }

    private int advance_sale_days;

    public String getApp_shop_group_price() {
        return app_shop_group_price;
    }

    public String getAssmble_price() {
        return assmble_price;
    }

    public void setAssmble_price(String assmble_price) {
        this.assmble_price = assmble_price;
    }

    private String assmble_price;


    public void setApp_shop_group_price(String app_shop_group_price) {
        this.app_shop_group_price = app_shop_group_price;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "audit_time='" + audit_time + '\'' +
                ", s_time=" + s_time +
                ", c_time=" + c_time +
                ", random=" + random +
                ", id=" + id +
                ", shop_code='" + shop_code + '\'' +
                ", collocation_code='" + collocation_code + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", Shop_price=" + Shop_price +
                ", shop_type_id=" + Arrays.toString(shop_type_id) +
                ", shop_attr=" + shop_attr +
                ", shop_se_price=" + shop_se_price +
                ", def_pic='" + def_pic + '\'' +
                ", shop_pic='" + shop_pic + '\'' +
                ", shop_up_time=" + shop_up_time +
                ", invertory_num=" + invertory_num +
                ", is_new='" + is_new + '\'' +
                ", is_hot='" + is_hot + '\'' +
                ", actual_sales=" + actual_sales +
                ", virtual_sales=" + virtual_sales +
                ", content='" + content + '\'' +
                ", clicks=" + clicks +
                ", remark='" + remark + '\'' +
                ", shop_number=" + shop_number +
                ", love_num=" + love_num +
                ", pager=" + pager +
                ", minshop_se_price=" + minshop_se_price +
                ", maxshop_se_price=" + maxshop_se_price +
                ", stocktype=" + stocktype +
                ", shop_discount_time=" + shop_discount_time +
                ", list_stock_type=" + list_stock_type +
                ", kickback=" + kickback +
                ", praise_count=" + praise_count +
                ", med_count=" + med_count +
                ", bad_count=" + bad_count +
                ", eva_count=" + eva_count +
                ", like_id=" + like_id +
                ", cart_count=" + cart_count +
                ", color_count=" + color_count +
                ", app_shop_group_price='" + app_shop_group_price + '\'' +
                ", wxcx_shop_group_price='" + wxcx_shop_group_price + '\'' +
                ", zeroOrderNum=" + zeroOrderNum +
                ", type_count=" + type_count +
                ", work_count=" + work_count +
                ", cost_count=" + cost_count +
                ", supp_id=" + supp_id +
                ", star_count=" + star_count +
                ", type1=" + type1 +
                ", shop_tag='" + shop_tag + '\'' +
                ", core=" + core +
                ", four_pic=" + four_pic +
                ", active_rule_pic='" + active_rule_pic + '\'' +
                ", active_people_num=" + active_people_num +
                ", involved_people_num=" + involved_people_num +
                ", active_start_time=" + active_start_time +
                ", active_end_time=" + active_end_time +
                ", shop_batch_num='" + shop_batch_num + '\'' +
                ", otime=" + otime +
                ", sys_time=" + sys_time +
                ", num=" + num +
                ", shop_group_price=" + shop_group_price +
                ", supp_label='" + supp_label + '\'' +
                ", supp_label_id='" + supp_label_id + '\'' +
                ", roll_price=" + roll_price +
                ", banner='" + banner + '\'' +
                ", group_number=" + group_number +
                ", trait='" + trait + '\'' +
                '}';
    }

    private String app_shop_group_price = "1.5";

    public String getWxcx_shop_group_price() {
        return wxcx_shop_group_price;
    }

    public void setWxcx_shop_group_price(String wxcx_shop_group_price) {
        this.wxcx_shop_group_price = wxcx_shop_group_price;
    }

    private String wxcx_shop_group_price;


    public float getZeroOrderNum() {
        return zeroOrderNum;
    }

    public void setZeroOrderNum(float zeroOrderNum) {
        this.zeroOrderNum = zeroOrderNum;
    }

    private float zeroOrderNum;//  下单数
    private float type_count;// 版型
    private float work_count;// 做工
    private float cost_count;// 性价比

    private Integer supp_id;// 供货商id
    private double star_count;

    private int type1;

    private String shop_tag;
    private int core;
    private List<String> four_pic;

    private String active_rule_pic;// 夺宝详情规则图
    private int active_people_num;// 夺宝商品开售人数
    private int involved_people_num;//拼团夺宝参与人数
    private long active_start_time;// 夺宝商品开售起始时间
    private long active_end_time;// 夺宝商品开售结束时间
    private String shop_batch_num;// 夺宝批次号
    private long otime;// 开奖时间
    private long sys_time;// 当前系统时间
    private int num;
    private double shop_group_price;//组团价格
    private String supp_label;//品牌名称
    private String supp_label_id;//品牌id（与数据库对应的id）
    private double roll_price;//新拼团价格
    private String banner;
    private int group_number;

    public int getGroup_number() {
        return group_number;
    }

    public void setGroup_number(int group_number) {
        this.group_number = group_number;
    }


    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getSupp_label() {
        return supp_label;
    }

    public void setSupp_label(String supp_label) {
        this.supp_label = supp_label;
    }

    public String getSupp_label_id() {
        return supp_label_id;
    }

    public void setSupp_label_id(String supp_label_id) {
        this.supp_label_id = supp_label_id;
    }

    public double getShop_group_price() {
        return shop_group_price;
    }

    public void setShop_group_price(double shop_group_price) {
        this.shop_group_price = shop_group_price;
    }

    public double getRoll_price() {
        return roll_price;
    }

    public void setRoll_price(double roll_price) {
        this.roll_price = roll_price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getOtime() {
        return otime;
    }

    public void setOtime(long otime) {
        this.otime = otime;
    }

    public long getSys_time() {
        return sys_time;
    }

    public void setSys_time(long sys_time) {
        this.sys_time = sys_time;
    }

    public List<String> getFour_pic() {
        return four_pic;
    }

    public void setFour_pic(List<String> four_pic) {
        this.four_pic = four_pic;
    }

    public String getActive_rule_pic() {
        return active_rule_pic;
    }

    public void setActive_rule_pic(String active_rule_pic) {
        this.active_rule_pic = active_rule_pic;
    }

    public int getActive_people_num() {
        return active_people_num;
    }

    public void setActive_people_num(int active_people_num) {
        this.active_people_num = active_people_num;
    }

    public int getInvolved_people_num() {
        return involved_people_num;
    }

    public void setInvolved_people_num(int involved_people_num) {
        this.involved_people_num = involved_people_num;
    }

    public long getActive_start_time() {
        return active_start_time;
    }

    public void setActive_start_time(long active_start_time) {
        this.active_start_time = active_start_time;
    }

    public long getActive_end_time() {
        return active_end_time;
    }

    public void setActive_end_time(long active_end_time) {
        this.active_end_time = active_end_time;
    }

    public String getShop_batch_num() {
        return shop_batch_num;
    }

    public void setShop_batch_num(String shop_batch_num) {
        this.shop_batch_num = shop_batch_num;
    }

    public int getCore() {
        return core;
    }

    public void setCore(int core) {
        this.core = core;
//		System.out.println("********************ertt" + core);
    }

    public String getShop_tag() {
        return shop_tag;
    }

    public void setShop_tag(String shop_tag) {
        this.shop_tag = shop_tag;
    }

    /**
     * private int fix_price;//定价 private int occasion;//场合 private int
     * favorite;//最爱
     * <p>
     * private int stuff;//材质 private int stuff2;//材质 private int stuff3;//材质
     * private int stuff4;//材质 private int sys_color;//色系 private int
     * pattern;//图案 private int trait;//特点 private int trait2;//特点 private int
     * trait3;//特点 private int style; private int age;
     */

    private String trait;// 商品标签集合

    public String getTrait() {
        return trait;
    }

    public void setTrait(String trait) {
        this.trait = trait;
    }

    public float getEva_count() {
        return eva_count;
    }

    public void setEva_count(float eva_count) {
        this.eva_count = eva_count;
    }

    public float getColor_count() {
        return color_count;
    }

    public void setColor_count(float color_count) {
        this.color_count = color_count;
    }

    public float getType_count() {
        return type_count;
    }

    public void setType_count(float type_count) {
        this.type_count = type_count;
    }

    public float getWork_count() {
        return work_count;
    }

    public void setWork_count(float work_count) {
        this.work_count = work_count;
    }

    public float getCost_count() {
        return cost_count;
    }

    public void setCost_count(float cost_count) {
        this.cost_count = cost_count;
    }

    public int getType1() {
        return type1;
    }

    public void setType1(int type1) {
        this.type1 = type1;
    }

    public Integer getSupp_id() {
        return supp_id;
    }

    public void setSupp_id(Integer supp_id) {
        this.supp_id = supp_id;
    }

    public double getStar_count() {
        return star_count;
    }

    public void setStar_count(double star_count) {
        this.star_count = star_count;
    }

    public int getLike_id() {
        return like_id;
    }

    public void setLike_id(int like_id) {
        this.like_id = like_id;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public List<StockType> getList_stock_type() {
        return list_stock_type;
    }

    public double getKickback() {
        return kickback;
    }

    public void setKickback(double kickback) {
        this.kickback = kickback;
    }

    public void setList_stock_type(List<StockType> list_stock_type) {
        this.list_stock_type = list_stock_type;
    }

    public int getPraise_count() {
        return praise_count;
    }

    public void setPraise_count(int praise_count) {
        this.praise_count = praise_count;
    }

    public int getMed_count() {
        return med_count;
    }

    public void setMed_count(int med_count) {
        this.med_count = med_count;
    }

    public int getBad_count() {
        return bad_count;
    }

    public void setBad_count(int bad_count) {
        this.bad_count = bad_count;
    }

    public List<String[]> getShop_attr() {
        return shop_attr;
    }

    public void setShop_attr(List<String[]> shop_attr) {
        this.shop_attr = shop_attr;
    }

    public List<StockType> getStocktype() {
        return stocktype;
    }

    public void setStocktype(List<StockType> stocktype) {
        this.stocktype = stocktype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShop_code() {
        return shop_code;
    }

    public void setShop_code(String shop_code) {
        this.shop_code = shop_code;
    }

    public String getShop_name() {
        return getShopNameStrNew(shop_name);
    }

    public String getShop_name(int x) {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public double getShop_price() {
        return Shop_price;
    }

    public void setShop_price(double shop_price) {
        Shop_price = shop_price;
    }

    public String[] getShop_type_id() {
        return shop_type_id;
    }

    public void setShop_type_id(String[] shop_type_id) {
        this.shop_type_id = shop_type_id;
    }

    public double getShop_se_price() {
        return shop_se_price;
    }

    public void setShop_se_price(double shop_se_price) {
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

    public int getInvertory_num() {
        return invertory_num;
    }

    public void setInvertory_num(int invertory_num) {
        this.invertory_num = invertory_num;
    }

    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public String getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(String is_hot) {
        this.is_hot = is_hot;
    }

    public int getActual_sales() {
        return actual_sales;
    }

    public void setActual_sales(int actual_sales) {
        this.actual_sales = actual_sales;
    }

    public int getVirtual_sales() {
        return virtual_sales;
    }

    public void setVirtual_sales(int virtual_sales) {
        this.virtual_sales = virtual_sales;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getShop_number() {
        return shop_number;
    }

    public void setShop_number(int shop_number) {
        this.shop_number = shop_number;
    }

    public int getLove_num() {
        return love_num;
    }

    public void setLove_num(int love_num) {
        this.love_num = love_num;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public double getMinshop_se_price() {
        return minshop_se_price;
    }

    public void setMinshop_se_price(double minshop_se_price) {
        this.minshop_se_price = minshop_se_price;
    }

    public double getMaxshop_se_price() {
        return maxshop_se_price;
    }

    public void setMaxshop_se_price(double maxshop_se_price) {
        this.maxshop_se_price = maxshop_se_price;
    }

    public long getShop_up_time() {
        return shop_up_time;
    }

    public void setShop_up_time(long shop_up_time) {
        this.shop_up_time = shop_up_time;
    }

    public long getShop_discount_time() {
        return shop_discount_time;
    }

    public void setShop_discount_time(long shop_discount_time) {
        this.shop_discount_time = shop_discount_time;
    }

    public int getCart_count() {
        return cart_count;
    }

    public void setCart_count(int cart_count) {
        this.cart_count = cart_count;
    }

    public String getCollocation_code() {
        return collocation_code;
    }


    public void setCollocation_code(String collocation_code) {
        this.collocation_code = collocation_code;
    }

    public static String getShopNameStr(String str) {
//		if (str.length() > 14) {
//			return str.substring(14, str.length()) + " " + str.substring(0, 14);
//		}
        return str;
    }

    // 分隔字符串
    public static String getShopNameStrNew(String str) {
        if (str.contains("】")) {
            String[] strs = str.split("】");
            String str0 = strs[0];
            if (str.length() == (str0.length() + 1)) {
                return str;
            } else {
                str = strs[1] + strs[0] + "】";
            }
        }
        return str;
    }

    public String getAudit_time() {
        return audit_time;
    }

    public void setAudit_time(String audit_time) {
        this.audit_time = audit_time;
    }
}
