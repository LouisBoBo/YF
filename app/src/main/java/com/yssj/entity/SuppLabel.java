package com.yssj.entity;

/**
 * @author Administrator 品牌
 */
public class SuppLabel {
	private Integer id;
	private String  name;// 名称
	private String icon;
	private String  pic;
	private Integer sort;
	private String remark;
	private String add_time;
	private String type;
	
	public SuppLabel() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 * @param icon
	 * @param pic
	 * @param sort
	 * @param remark
	 * @param add_time
	 */
	public SuppLabel(Integer id, String name, String icon, String pic, Integer sort, String remark, String add_time,String type) {
		super();
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.pic = pic;
		this.sort = sort;
		this.remark = remark;
		this.add_time = add_time;
		this.type = type;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	


}
