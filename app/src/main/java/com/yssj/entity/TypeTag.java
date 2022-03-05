package com.yssj.entity;

/**
 * @author Administrator 商品表
 */
public class TypeTag {
	private Integer id;
	private String  class_name;// 类型名称
	private Integer sort;
	private String  pic;
	private Integer is_new;
	private Integer is_hot;
	private Integer type;//父级的id 属于哪个一级类目
	private Integer class_type;//购物首页和分类页的数据区分  1：购物首页 2：分类页
	
	public TypeTag() {
		super();
	}

	/**
	 * @param id
	 * @param class_name
	 * @param sort
	 * @param pic
	 * @param is_new
	 * @param is_hot
	 * @param type
	 * @param class_type
	 */
	public TypeTag(Integer id, String class_name, Integer sort, String pic, Integer is_new, Integer is_hot,
			Integer type, Integer class_type) {
		super();
		this.id = id;
		this.class_name = class_name;
		this.sort = sort;
		this.pic = pic;
		this.is_new = is_new;
		this.is_hot = is_hot;
		this.type = type;
		this.class_type = class_type;
	}
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Integer getIs_new() {
		return is_new;
	}
	public void setIs_new(Integer is_new) {
		this.is_new = is_new;
	}
	public Integer getIs_hot() {
		return is_hot;
	}
	public void setIs_hot(Integer is_hot) {
		this.is_hot = is_hot;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getClass_type() {
		return class_type;
	}

	public void setClass_type(Integer class_type) {
		this.class_type = class_type;
	}

}
