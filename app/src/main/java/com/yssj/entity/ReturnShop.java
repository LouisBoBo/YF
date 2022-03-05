package com.yssj.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**退换货表
 * @author Administrator
 */
public class ReturnShop implements Serializable{
	public Integer id;
	private String return_code;//退款编号
	private String order_code;//订单编号
	private Integer order_shop_status;//货物状态1待发货2已发货3已签收
	private Double money;//退款金额(全部金额)
	private String explain;//说明(150字以内)
	private String cause;//退款原因
	private Integer return_type;//1换货2退货3退款
	private String pic;//退款凭证.最多三张
	private Integer user_id;//用户id 
	//快递单号快递单号,与物流表逻辑关联,若为换货,则有2个快递单号,中间以逗号隔开,
	private String express_id;
	private Integer status;//状态(1.待审核.2,审核通过.3.审核未通过.4.供应商已收到货(退货的时候用)
	private Date add_time;//申请的时间
	private Integer ck_id;//审核人id
	private Date ck_time;//审核的时间,这里只记录初次审核的
	private  String ck_explain;//审核说明
	
	private String store_code; //店铺编号
	private Integer shop_num;//商品数量
	private Double shop_price;//商品价格
	private Date last_time;//商家未处理超时时间
	private String address;//买家地址
	private String consignee;//买家姓名
	private String phone;//买家手机
	private String postcode;//买家邮编
	private String supp_address;//供应商地址
	private String supp_consignee;//供应商姓名
	private String supp_postcode;//供应商邮编
	private String supp_phone;//供应商手机
	private Date end_time;//完结时间
	private String end_explain; // 完结原因0正常完结1超时自动关闭2买家手动关闭3商家超时未处理,自动完结.4换货,买家未及时确认收货,系统自动确认
	private Integer buy_type;	//退款至0我的钱包1支付宝2微信3银行卡
	
	private Integer is_buy;//0我的买1我的卖
	
	private Integer supp_sign_status; //0供货商没有拒绝，1供货商拒签
	private Integer ys_intervene;//平台接入 0没有介入,1申请介入,2已经介入.3卖家赢,4买家赢.5不管了
	private String supp_refuse_msg;//供应商拒绝签收理由
	//下面非表中字段
	private String store_name;//店铺名称
	
	private String shop_name;
	private String shop_color;
	private String shop_size;
	
	private int order_shop_id;
	
	private String shop_code;
	
	
	public String getShop_code() {
		return shop_code;
	}
	public void setShop_code(String shop_code) {
		if (null == shop_code){
			shop_code = "";
		}
		this.shop_code = shop_code;
	}
	public int getOrder_shop_id() {
		return order_shop_id;
	}
	public void setOrder_shop_id(int order_shop_id) {
		this.order_shop_id = order_shop_id;
	}
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		if (null == shop_name){
			shop_name = "";
		}
		this.shop_name = shop_name;
	}
	public String getShop_color() {
		return shop_color;
	}
	public void setShop_color(String shop_color) {
		if (null == shop_color){
			shop_color = "";
		}
		this.shop_color = shop_color;
	}
	public String getShop_size() {
		return shop_size;
	}
	public void setShop_size(String shop_size) {
		if (null == shop_size){
			shop_size = "";
		}
		this.shop_size = shop_size;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		if (null == store_name){
			store_name = "";
		}
		this.store_name = store_name;
	}
	
	
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public String getEnd_explain() {
		return end_explain;
	}
	public void setEnd_explain(String end_explain) {
		if (null == end_explain){
			end_explain = "";
		}
		this.end_explain = end_explain;
	}
	public Integer getBuy_type() {
		return buy_type;
	}
	public void setBuy_type(Integer buy_type) {
		this.buy_type = buy_type;
	}
	public String getAddress() {
		return address;
	}
	public Date getLast_time() {
		return last_time;
	}
	public void setLast_time(Date last_time) {
		this.last_time = last_time;
	}
	public void setAddress(String address) {
		if (null == address){
			address = "";
		}
		this.address = address;
	}
	
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		if (null == consignee){
			consignee = "";
		}
		this.consignee = consignee;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		if (null == phone){
			phone = "";
		}
		this.phone = phone;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		if (null == postcode){
			postcode = "";
		}
		this.postcode = postcode;
	}
	public String getSupp_address() {
		return supp_address;
	}
	public void setSupp_address(String supp_address) {
		if (null == supp_address){
			supp_address = "";
		}
		this.supp_address = supp_address;
	}
	public String getSupp_consignee() {
		return supp_consignee;
	}
	public Integer getIs_buy() {
		return is_buy;
	}
	public void setIs_buy(Integer is_buy) {
		this.is_buy = is_buy;
	}
	public void setSupp_consignee(String supp_consignee) {
		if (null == supp_consignee){
			supp_consignee = "";
		}
		this.supp_consignee = supp_consignee;
	}
	public String getSupp_postcode() {
		return supp_postcode;
	}
	public void setSupp_postcode(String supp_postcode) {
		if (null == supp_postcode){
			supp_postcode = "";
		}
		this.supp_postcode = supp_postcode;
	}
	public String getSupp_phone() {
		return supp_phone;
	}
	public void setSupp_phone(String supp_phone) {
		if (null == supp_phone){
			supp_phone = "";
		}
		this.supp_phone = supp_phone;
	}
	public Date getAdd_time() {
		return add_time;
	}
	public String getStore_code() {
		return store_code;
	}
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		if (null == return_code){
			return_code = "";
		}
		this.return_code = return_code;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		if (null == order_code){
			order_code = "";
		}
		this.order_code = order_code;
	}
	public Integer getOrder_shop_status() {
		return order_shop_status;
	}
	public void setOrder_shop_status(Integer order_shop_status) {
		this.order_shop_status = order_shop_status;
	}
	public Integer getReturn_type() {
		return return_type;
	}
	public void setReturn_type(Integer return_type) {
		this.return_type = return_type;
	}
	public void setStore_code(String store_code) {
		if (null == store_code){
			store_code = "";
		}
		this.store_code = store_code;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getCk_id() {
		return ck_id;
	}
	public void setCk_id(Integer ck_id) {
		this.ck_id = ck_id;
	}
	public Double getShop_price() {
		return shop_price;
	}
	public void setShop_price(Double shop_price) {
		this.shop_price = shop_price;
	}
	public Integer getShop_num() {
		return shop_num;
	}
	public void setShop_num(Integer shop_num) {
		this.shop_num = shop_num;
	}
	public String getCk_explain() {
		return ck_explain;
	}
	public void setCk_explain(String ck_explain) {
		if (null == ck_explain){
			ck_explain = "";
		}
		this.ck_explain = ck_explain;
	}
	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}
	public Date getCk_time() {
		return ck_time;
	}
	public void setCk_time(Date ck_time) {
		this.ck_time = ck_time;
	}
	
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		if (null == explain){
			explain = "";
		}
		this.explain = explain;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		if (null == cause){
			cause = "";
		}
		this.cause = cause;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		if (null == pic){
			pic = "";
		}
		this.pic = pic;
	}

	public String getExpress_id() {
		return express_id;
	}
	public void setExpress_id(String express_id) {
		if (null == express_id){
			express_id = "";
		}
		this.express_id = express_id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	public Integer getSupp_sign_status() {
		return supp_sign_status;
	}
	public void setSupp_sign_status(Integer supp_sign_status) {
		this.supp_sign_status = supp_sign_status;
	}
	
	
	public Integer getYs_intervene() {
		return ys_intervene;
	}
	public void setYs_intervene(Integer ys_intervene) {
		this.ys_intervene = ys_intervene;
	}
	public String getSupp_refuse_msg() {
		return supp_refuse_msg;
	}
	public void setSupp_refuse_msg(String supp_refuse_msg) {
		if (null == supp_refuse_msg){
			supp_refuse_msg = "";
		}
		this.supp_refuse_msg = supp_refuse_msg;
	}
	
	//该订单下面的商品 属于非表中字段
	private List<Map<String	, Object>>orderShops;
	private List<OrderShop> list;
	
	
	public List<OrderShop> getList() {
		return list;
	}


	public void setList(List<OrderShop> list) {
		this.list = list;
	}

	
}
