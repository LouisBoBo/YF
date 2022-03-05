package com.yssj.ui.activity.circles;

public class IssueBean {
	private int label_id;
	private String label_name;
	private int type1;
	private int type2;
	private int style;
	private int label_type;
	private String label_x;
	private String label_y;
	private String direction;
	private String flagShop;//"0"一般标签，"1"商品标签
	private String shop_code;

	public String getShop_code() {
		return shop_code;
	}

	public void setShop_code(String shop_code) {
		this.shop_code = shop_code;
	}

	public String getFlagShop() {
		return flagShop;
	}

	public void setFlagShop(String flagShop) {
		this.flagShop = flagShop;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public IssueBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getLabel_id() {
		return label_id;
	}

	public void setLabel_id(int label_id) {
		this.label_id = label_id;
	}

	public String getLabel_name() {
		return label_name;
	}

	public void setLabel_name(String label_name) {
		this.label_name = label_name;
	}

	public int getType1() {
		return type1;
	}

	public void setType1(int type1) {
		this.type1 = type1;
	}

	public int getType2() {
		return type2;
	}

	public void setType2(int type2) {
		this.type2 = type2;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public int getLabel_type() {
		return label_type;
	}

	public void setLabel_type(int label_type) {
		this.label_type = label_type;
	}

	public String getLabel_x() {
		return label_x;
	}

	public void setLabel_x(String label_x) {
		this.label_x = label_x;
	}

	public String getLabel_y() {
		return label_y;
	}

	public void setLabel_y(String label_y) {
		this.label_y = label_y;
	}

	

}
