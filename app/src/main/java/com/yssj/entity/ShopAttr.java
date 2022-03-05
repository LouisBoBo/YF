package com.yssj.entity;

/**
 * @author Administrator 商品属性表
 */
public class ShopAttr {
	private Integer id;
	private String attr_name;// 类型名称
	private String ico;
	private Integer parent_id;// 父类id
	private Integer is_show;

	public Integer getIs_show() {
		return is_show;
	}

	public void setIs_show(Integer is_show) {
		this.is_show = is_show;
	}

	public ShopAttr() {
		super();
	}
	
	

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public ShopAttr(Integer id, String attr_name,String ico, Integer parent_id,
			Integer is_show) {
		super();
		this.id = id;
		this.attr_name = attr_name;
		this.parent_id = parent_id;
		this.is_show = is_show;
		this.ico = ico;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAttr_name() {
		return attr_name;
	}

	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

}
