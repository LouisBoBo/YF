package com.yssj.entity;

/**
 * @author Administrator 密友圈标签
 */
public class FriendCircleTag {
	private Integer id;
	private String  name;// 名称
	private String is_show;
	private Integer sort;
	private Integer type;
	
	public FriendCircleTag() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 * @param is_show
	 * @param sort
	 * @param type
	 */
	public FriendCircleTag(Integer id, String name, String is_show, Integer sort, Integer type) {
		super();
		this.id = id;
		this.name = name;
		this.is_show = is_show;
		this.sort = sort;
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

	public String getIs_show() {
		return is_show;
	}

	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	

}
