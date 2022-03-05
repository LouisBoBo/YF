package com.yssj.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单表
 *
 * @author 欧阳思蓝
 */
public class Order implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer id;// 主键id
    private String order_code;// 订单编号
    private String order_name;// 订单名称
    private Double order_price;// 订单金额
    private Integer user_id;// 用户id
    private Integer shop_num;// 商品数量
    private String order_pic;// 订单图片
    private Long add_time;// 下单时间
    private Integer change;// 退换货0默认1换货2退货
    private String status;// 订单状态1待付款2代发货3待收货4待评价5退款中6已完结7延长收货
    private String message;// 买家留言
    private String store_code;// 店铺编号
    private Date last_time;// 过期时间
    private String bak;// 保留字段
    private Double free;// 免付
    private String address;// 地址
    private String consignee;// 收货人
    private String phone;// 联系手机
    private String postcode;// 邮编
    private int is_del;// 是否删除0否,1是
    private long pay_time = 0;// 付款时间
    private long end_time;// 完结时间
    private long shipments_time;// 发货时间
    private int check = -1; //自动退款状态（默认没有自动退款）
    private String business_code;
    private int new_free;
    private int is_freeDelivery;  //  <!-- 免费领订单倒计时 新用户第一天完成任务后显示申请发货 第二天直接显示申请发货-->
    private int is_FightShow;
    private int vip_roll_type;//需要升级到的vipType


    public int getVip_roll_type() {
        return vip_roll_type;
    }

    public void setVip_roll_type(int vip_roll_type) {
        this.vip_roll_type = vip_roll_type;
    }


    public int getAdvance_sale_days() {
        return advance_sale_days;
    }

    public void setAdvance_sale_days(int advance_sale_days) {
        this.advance_sale_days = advance_sale_days;
    }

    private int advance_sale_days;




    public int getIs_FightShow() {
        return is_FightShow;
    }

    public void setIs_FightShow(int is_FightShow) {
        this.is_FightShow = is_FightShow;
    }



    public int getIs_freeDelivery() {
        return is_freeDelivery;
    }

    public void setIs_freeDelivery(int is_freeDelivery) {
        this.is_freeDelivery = is_freeDelivery;
    }




    public int getNew_free() {
        return new_free;
    }

    public void setNew_free(int new_free) {
        this.new_free = new_free;
    }



    public String getBusiness_code() {
        return business_code;
    }



    public void setBusiness_code(String business_code) {
        this.business_code = business_code;
    }


    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }




    private String isTM;//是否是特卖商品 （"1"=特卖）

    public int getWhether_prize() {
        return whether_prize;
    }

    public void setWhether_prize(int whether_prize) {
        this.whether_prize = whether_prize;
    }

    private int whether_prize = -1;// 1=显示订单金额已返


    public String getIsTM() {
        return isTM;
    }

    public void setIsTM(String isTM) {
        this.isTM = isTM;
    }

    public double getOne_deductible() {
        return one_deductible;
    }

    public void setOne_deductible(double one_deductible) {
        this.one_deductible = one_deductible;
    }

    private double one_deductible;//抵扣金额


    private String logi_code;// 物流编号
    private String logi_name;// 物流公司名称(编号,如圆通是yuantong)
    private String supp_address;// 发货地址
    private String supp_consignee;// 发货人姓名
    private String supp_postcode;// 发货地址邮编
    private String supp_phone;// 发货手机号码
    private Integer supp_id;// 供应商编号
    private Double supp_money;// 供应商应得总金额
    private Double kickBack;// 一级回佣总金额
    private Double two_kickback;// 二级回佣的总金额
    private Integer is_buy;// 是否是自己在自己订单里面购买的订单0是1否
    private long issue_endtime;//夺宝中奖号码 揭晓时间
    private String issue_status;

    private double pay_money;


    public double getPay_money() {
        return pay_money;
    }

    public void setPay_money(double pay_money) {
            this.pay_money = pay_money;
    }

    private String issue_code;


    private String participation_code;

    private Double remain_money;// 该订单需要支付的金额
    private int is_wx;// 是否跳过微信的支付界面 0否1是
    private double wx_price;// 第一次跳微信支付界面时需要支付的金额
    private int twofoldness;//翻倍倍数
    private double tfn_money;//翻倍使用金额


    private int is_free;//0否1是2参与但取消3后台取消
    private int is_roll;//是否参与拼团,0否1我发起2我参与3我发起成功4机器人参与
    private String roll_code;//拼团编号

    public Integer getIs_free() {
        return is_free;
    }

    public void setIs_free(Integer is_free) {
        try {
            this.is_free = is_free;
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public Integer getIs_roll() {
        return is_roll;
    }

    public void setIs_roll(Integer is_roll) {
        try {
            this.is_roll = is_roll;
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public String getRoll_code() {
        return roll_code;
    }

    public void setRoll_code(String roll_code) {
        try {
            this.roll_code = roll_code;
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public Double getRemain_money() {
        return remain_money;
    }

    public void setRemain_money(Double remain_money) {
        if (null == remain_money) {
            this.remain_money = 0.0;
        } else {
            this.remain_money = remain_money;
        }

    }

    public Double getWx_price() {
        return wx_price;
    }

    public void setWx_price(double wx_price) {
        this.wx_price = wx_price;
    }

    public int getTwofoldness() {
        return twofoldness;
    }

    public void setTwofoldness(int twofoldness) {
        this.twofoldness = twofoldness;
    }

    public int getIs_wx() {
        return is_wx;
    }

    public void setIs_wx(int is_wx) {
        this.is_wx = is_wx;
    }

    public double getTfn_money() {
        return tfn_money;
    }

    public void setTfn_money(double tfn_money) {
        this.tfn_money = tfn_money;
    }


    public String getIssue_code() {
        return issue_code;
    }

    public void setIssue_code(String issue_code) {
        if (null == issue_code) {
            issue_code = "";
        }
        this.issue_code = issue_code;
    }


    public long getIssue_endtime() {
        return issue_endtime;
    }

    public void setIssue_endtime(long issue_endtime) {
        this.issue_endtime = issue_endtime;
    }

    public String getParticipation_code() {
        return participation_code;
    }

    public void setParticipation_code(String participation_code) {
        if (null == participation_code) {
            participation_code = "";
        }
        this.participation_code = participation_code;
    }

    public String getIssue_status() {
        return issue_status;
    }

    public void setIssue_status(String issue_status) {
        if (null == issue_status) {
            issue_status = "";
        }
        this.issue_status = issue_status;
    }

    private int is_kick; // 订单是否过期

    private int pay_status;

    private Long now;

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    private Integer shop_from;//0,正价商品 1,特卖商品，2，会员商品 3 标识签到包邮购买的商品 4 签到夺宝购买的商品
    public static boolean signCompleteFlag;// (非表中字段)签到购买是否完成的标识 所有的签到订单公用
    private int signType;//(非表中字段) 签到 商品的类型 代表 几元包邮

    public int getSignType() {
        return signType;
    }

    public void setSignType(int signType) {
        this.signType = signType;
    }

    private Double postage;

    public Double getPostage() {
        return postage;
    }

    public void setPostage(Double postage) {
        this.postage = postage;
    }

    public Integer getShop_from() {
        return shop_from;
    }

    public void setShop_from(Integer shop_from) {
        this.shop_from = shop_from;
    }

    public int getIs_kick() {
        return is_kick;
    }

    public void setIs_kick(int is_kick) {
        this.is_kick = is_kick;
    }

    // 该订单下面的商品 属于非表中字段
    private List<Map<String, Object>> orderShops;
    private List<OrderShop> list;

    public List<OrderShop> getList() {
        return list;
    }

    public void setList(List<OrderShop> list) {
        this.list = list;
    }

    private List<Order> orderlist;

    public List<Order> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<Order> orderlist) {
        this.orderlist = orderlist;
    }

    public List<Map<String, Object>> getOrderShops() {
        if (orderShops == null)
            orderShops = new ArrayList<Map<String, Object>>();
        return orderShops;
    }

    public Double getKickBack() {
        return kickBack;
    }

    public void setKickBack(Double kickBack) {
        this.kickBack = kickBack;
    }

    public Double getTwo_kickback() {
        return two_kickback;
    }

    public void setTwo_kickback(Double two_kickback) {
        this.two_kickback = two_kickback;
    }

    public Integer getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(Integer is_buy) {
        this.is_buy = is_buy;
    }

    public int getIs_del() {
        return is_del;
    }

    public long getShipments_time() {
        return shipments_time;
    }

    public Integer getChange() {
        return change;
    }

    public void setChange(Integer change) {
        this.change = change;
    }

    public void setShipments_time(long shipments_time) {
        this.shipments_time = shipments_time;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public void setOrderShops(List<Map<String, Object>> orderShops) {
        this.orderShops = orderShops;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        if (null == postcode) {
            postcode = "";
        }
        this.postcode = postcode;
    }

    public long getPay_time() {
        return pay_time;
    }

    public void setPay_time(long pay_time) {
//		try {
        this.pay_time = pay_time;
//		}catch (Exception e){
//			this.pay_time=0;
//		}

    }

    public String getLogi_code() {
        return logi_code;
    }

    public void setLogi_code(String logi_code) {
        this.logi_code = logi_code;
    }

    public String getLogi_name() {
        return logi_name;
    }

    public void setLogi_name(String logi_name) {
        if (null == logi_name) {
            logi_name = "";
        }
        this.logi_name = logi_name;
    }

    public String getSupp_address() {
        return supp_address;
    }

    public void setSupp_address(String supp_address) {
        if (null == supp_address) {
            supp_address = "";
        }
        this.supp_address = supp_address;
    }

    public String getSupp_consignee() {
        return supp_consignee;
    }

    public void setSupp_consignee(String supp_consignee) {
        if (null == supp_consignee) {
            supp_consignee = "";
        }
        this.supp_consignee = supp_consignee;
    }

    public String getSupp_postcode() {
        return supp_postcode;
    }

    public void setSupp_postcode(String supp_postcode) {
        if (null == supp_postcode) {
            supp_postcode = "";
        }
        this.supp_postcode = supp_postcode;
    }

    public String getSupp_phone() {
        return supp_phone;
    }

    public void setSupp_phone(String supp_phone) {
        if (null == supp_phone) {
            supp_phone = "";
        }
        this.supp_phone = supp_phone;
    }

    public Integer getSupp_id() {
        return supp_id;
    }

    public void setSupp_id(Integer supp_id) {
        this.supp_id = supp_id;
    }

    public Double getSupp_money() {
        return supp_money;
    }

    public void setSupp_money(Double supp_money) {
        this.supp_money = supp_money;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public Double getFree() {
        free = free != null ? free : 0;
        return free;
    }

    public void setFree(Double free) {
        this.free = free;
    }

    public String getOrder_name() {
        return order_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (null == address) {
            address = "";
        }
        this.address = address;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        if (null == consignee) {
            consignee = "";
        }
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOrder_name(String order_name) {
        if (null == order_name) {
            order_name = "";
        }
        this.order_name = order_name;
    }

    public String getOrder_pic() {
        return order_pic;
    }

    public void setOrder_pic(String order_pic) {
        if (null == order_pic) {
            order_pic = "";
        }
        this.order_pic = order_pic;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLast_time() {
        return last_time;
    }

    public void setLast_time(Date last_time) {
        this.last_time = last_time;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        if (null == order_code) {
            order_code = "";
        }
        this.order_code = order_code;
    }

    public Double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(Double order_price) {
        this.order_price = order_price;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getShop_num() {
        return shop_num;
    }

    public void setShop_num(Integer shop_num) {
        this.shop_num = shop_num;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if (null == message) {
            message = "";
        }
        this.message = message;
    }

    public String getStore_code() {
        return store_code;
    }

    public void setStore_code(String store_code) {
        if (null == store_code) {
            store_code = "";
        }
        this.store_code = store_code;
    }

    public String getBak() {
        return bak;
    }

    public void setBak(String bak) {
        if (null == bak) {
            bak = "";
        }
        this.bak = bak;
    }

    public Long getNowss() {
        return now;
    }

    public void setNowss(Long now) {
        this.now = now;
    }

    public Order(Integer id, String order_code, Double order_price, Integer user_id, Integer shop_num, long add_time,
                 long add_date, String status, String message, String store_code, String bak) {
        super();
        this.id = id;
        this.order_code = order_code;
        this.order_price = order_price;
        this.user_id = user_id;
        this.shop_num = shop_num;
        this.add_time = add_time;
        this.status = status;
        this.message = message;
        this.store_code = store_code;
        this.bak = bak;
    }

    public Order() {
        super();
    }

    public Order(String order_code, String status) {
        super();
        this.order_code = order_code;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", order_code=" + order_code + ", order_name=" + order_name + ", order_price="
                + order_price + ", user_id=" + user_id + ", shop_num=" + shop_num + ", order_pic=" + order_pic
                + ", add_time=" + add_time + ", change=" + change + ", status=" + status + ", message=" + message
                + ", store_code=" + store_code + ", last_time=" + last_time + ", bak=" + bak + ", free=" + free
                + ", address=" + address + ", consignee=" + consignee + ", phone=" + phone + ", postcode=" + postcode
                + ", is_del=" + is_del + ", pay_time=" + pay_time + ", end_time=" + end_time + ", shipments_time="
                + shipments_time + ", logi_code=" + logi_code + ", logi_name=" + logi_name + ", supp_address="
                + supp_address + ", supp_consignee=" + supp_consignee + ", supp_postcode=" + supp_postcode
                + ", supp_phone=" + supp_phone + ", supp_id=" + supp_id + ", supp_money=" + supp_money + ", kickBack="
                + kickBack + ", two_kickback=" + two_kickback + ", is_buy=" + is_buy + ", orderShops=" + orderShops
                + ", list=" + list + "]";
    }

}
