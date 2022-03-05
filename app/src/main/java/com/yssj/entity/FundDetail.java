package com.yssj.entity;

import java.io.Serializable;
import java.util.Date;

/** 资金明细表(供应商和用户的)
 * @author Administrator
 */
public class FundDetail implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer user_id;//用户ID(发起方
	private Integer s_user_id;//用户ID(接受方)
	private String deal_no;//交易号
	private String type;//事件类型1购物2转账3提现4充值5回佣6二级回佣
	private Double money;//金额
	private long add_time;//发生时间
	private Character status;//状态1未到账2已到账
	private String name;//名称
	private String order_code;//订单编号
	private Integer pay_type;//支付方式分类0我的钱包,1支付宝,2微信支付
	private String pay_user;//支付账号
	public Integer getId() {
		return id;
	}
	public FundDetail(Integer user_id, String deal_no, String type,
			Double money, Character status, String order_code,Integer pay_type,String pay_user) {
		this.user_id = user_id;
		this.deal_no = deal_no;
		this.type = type;
		this.money = money;
		this.status = status;
		this.order_code = order_code;
		this.pay_type=pay_type;
		this.pay_user=pay_user;
	}
	public FundDetail(Integer user_id, Integer s_user_id, String deal_no,
			String type, Double money, Character status,String name,
			String order_code,Integer pay_type,String pay_user) {
		super();
		this.user_id = user_id;
		this.s_user_id = s_user_id;
		this.deal_no = deal_no;
		this.type = type;
		this.money = money;
		this.status = status;
		this.name=name;
		this.order_code = order_code;
		this.pay_type=pay_type;
		this.pay_user=pay_user;
	}
	public FundDetail() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		
		if(name == null){
			name = "";
		}
		
		this.name = name;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		
		if(order_code == null){
			order_code = "";
		}
		
		this.order_code = order_code;
	}
	public Integer getPay_type() {
		return pay_type;
	}
	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
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
	public Integer getS_user_id() {
		return s_user_id;
	}
	public void setS_user_id(Integer s_user_id) {
		this.s_user_id = s_user_id;
	}
	public String getPay_user() {
		return pay_user;
	}
	public void setPay_user(String pay_user) {
		
		if(pay_user == null){
			pay_user = "";
		}
		
		this.pay_user = pay_user;
	}
	public String getDeal_no() {
		return deal_no;
	}
	public void setDeal_no(String deal_no) {
		
		if(deal_no == null){
			deal_no = "";
		}
		
		
		this.deal_no = deal_no;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		
		if(type == null){
			type = "0";
		}
		
		this.type = type;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public long getAdd_time() {
		return add_time;
	}
	public void setAdd_time(long add_time) {
		this.add_time = add_time;
	}
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "FundDetail [id=" + id + ", user_id=" + user_id + ", s_user_id="
				+ s_user_id + ", deal_no=" + deal_no + ", type=" + type
				+ ", money=" + money + ", add_time=" + add_time + ", status="
				+ status + ", name=" + name + ", order_code=" + order_code
				+ ", pay_type=" + pay_type + ", pay_user=" + pay_user + "]";
	}
	
	
}
