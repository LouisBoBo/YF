package com.yssj.entity;

/**
 * @author Administrator 商品表
 */
public class ShopType {
	private Integer id;
	private String type_name;// 类型名称
	private String ico;
	private Integer parent_id;// 父类id
	private Integer is_show;
	private Integer sequence;
	private String group_flag;
	
	

	public String getGroup_flag() {
		return group_flag;
	}

	public void setGroup_flag(String group_flag) {
		this.group_flag = group_flag;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public ShopType() {
		super();
	}

	public ShopType(Integer id, String type_name,String ico, Integer parent_id, Integer is_show) {
		super();
		this.id = id;
		this.type_name = type_name;
		this.parent_id = parent_id;
		this.ico = ico;
		this.is_show = is_show;
	}

	public Integer getIs_show() {
		return is_show;
	}

	public void setIs_show(Integer is_show) {
		this.is_show = is_show;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

}
