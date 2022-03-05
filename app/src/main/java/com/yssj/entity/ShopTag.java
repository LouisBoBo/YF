package com.yssj.entity;

/**
 * @author Administrator 商品属性表
 */
public class ShopTag {
	private Integer id;
	private String tag_name;// 类型名称
	private String ico;//图标
	private Integer parent_id;// 父类id
	private Integer is_show;
	private Integer sequence;
	private String e_name;

	public String getE_name() {
		return e_name;
	}

	public void setE_name(String e_name) {
		this.e_name = e_name;
	}

	public ShopTag() {
		super();
	}

	public ShopTag(Integer id, String tag_name,String ico, Integer parent_id, Integer is_show) {
		super();
		this.id = id;
		this.tag_name = tag_name;
		this.parent_id = parent_id;
		this.is_show = is_show;
		this.ico = ico;
	}
	
	
	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public Integer getIs_show() {
		return is_show;
	}

	public void setIs_show(Integer is_show) {
		this.is_show = is_show;
	}

	public Integer getId() {
		return id;
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	
}
