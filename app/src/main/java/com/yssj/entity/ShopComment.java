package com.yssj.entity;

import java.util.Date;
import java.util.List;

public class ShopComment {

	private Integer id;
	private String shop_code;// 商品编号
	private String store_code;// 店铺编号
	private Integer user_id;// 用户id
	private String user_name;// 用户昵称
	private String user_url;//用户头像
	private String content;// 评论内容
	private long add_date;// 评论时间
	private String pic;// 评论图片
	private Integer comment_type;// 评价类型(1好评,2,中评,3差评)
	private String shop_color;// 商品颜色
	private String shop_size;// 商品尺码
	private Integer is_del;// 是否删除 0否 1是
	private Integer verify_user;// 审核人
	private Integer verify_status;// 审核状态0不通过1通过
	private long verify_time;// 审核时间
	private String verify_info;// 审核信息
	private int supp_id;//供应商id
	private  String shop_name;//商品名称
	private String shop_price;//商品价格 
	
	private Integer cost;//性价比
	private Integer work;//做工
	private Integer color;//色差
	private Integer type;//版型
	private Integer star;//评星数
	
	private String supp_content ;
	private long supp_add_date;
	private String supp_pic;
	private Date supp_verify_status;
	
	private List<Comment> comment;
	private List<SuppComment> suppComment;
	private List<SuppComment> suppEndComment;
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
	public String getStore_code() {
		return store_code;
	}
	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_url() {
		return user_url;
	}
	public void setUser_url(String user_url) {
		this.user_url = user_url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getAdd_date() {
		return add_date;
	}
	public void setAdd_date(long add_date) {
		this.add_date = add_date;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Integer getComment_type() {
		return comment_type;
	}
	public void setComment_type(Integer comment_type) {
		this.comment_type = comment_type;
	}
	public String getShop_color() {
		return shop_color;
	}
	public void setShop_color(String shop_color) {
		this.shop_color = shop_color;
	}
	public String getShop_size() {
		return shop_size;
	}
	public void setShop_size(String shop_size) {
		this.shop_size = shop_size;
	}
	public Integer getIs_del() {
		return is_del;
	}
	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}
	public Integer getVerify_user() {
		return verify_user;
	}
	public void setVerify_user(Integer verify_user) {
		this.verify_user = verify_user;
	}
	public Integer getVerify_status() {
		return verify_status;
	}
	public void setVerify_status(Integer verify_status) {
		this.verify_status = verify_status;
	}
	public long getVerify_time() {
		return verify_time;
	}
	public void setVerify_time(long verify_time) {
		this.verify_time = verify_time;
	}
	public String getVerify_info() {
		return verify_info;
	}
	public void setVerify_info(String verify_info) {
		this.verify_info = verify_info;
	}
	public int getSupp_id() {
		return supp_id;
	}
	public void setSupp_id(int supp_id) {
		this.supp_id = supp_id;
	}
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	public String getShop_price() {
		return shop_price;
	}
	public void setShop_price(String shop_price) {
		this.shop_price = shop_price;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	public Integer getWork() {
		return work;
	}
	public void setWork(Integer work) {
		this.work = work;
	}
	public Integer getColor() {
		return color;
	}
	public void setColor(Integer color) {
		this.color = color;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStar() {
		return star;
	}
	public void setStar(Integer star) {
		this.star = star;
	}
	public String getSupp_content() {
		return supp_content;
	}
	public void setSupp_content(String supp_content) {
		this.supp_content = supp_content;
	}
	public long getSupp_add_date() {
		return supp_add_date;
	}
	public void setSupp_add_date(long supp_add_date) {
		this.supp_add_date = supp_add_date;
	}
	public String getSupp_pic() {
		return supp_pic;
	}
	public void setSupp_pic(String supp_pic) {
		this.supp_pic = supp_pic;
	}
	public Date getSupp_verify_status() {
		return supp_verify_status;
	}
	public void setSupp_verify_status(Date supp_verify_status) {
		this.supp_verify_status = supp_verify_status;
	}
	public List<Comment> getComment() {
		return comment;
	}
	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}
	public List<SuppComment> getSuppComment() {
		return suppComment;
	}
	public void setSuppComment(List<SuppComment> suppComment) {
		this.suppComment = suppComment;
	}
	public List<SuppComment> getSuppEndComment() {
		return suppEndComment;
	}
	public void setSuppEndComment(List<SuppComment> suppEndComment) {
		this.suppEndComment = suppEndComment;
	}
	
	
	

}
