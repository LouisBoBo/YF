package com.yssj.data;

public final class YDBConstance {

	private YDBConstance() {
	}

	public static final String TABLE_MESSAGE_CREATE_1 = "create table if not exists message ("
			+ "_id integer primary key autoincrement, "
			+ "msgid text, "
			+ "senderuid text, "
			+ "nickname text, "
			+ "avatar text, "
			+ "receiveruid text, "
			+ "message text, "
			+ "date text, "
			+ "read integer, "
			+ "type integer, "
			+ "size integer, "
			+ "duration integer," + "state integer, " + "useruid text);";

	public static final String TABLE_CREATE_SORT = "create table if not exists sort_info("
			+ "_id integer,"
			+ "sort_name text,"
			+ "icon text,"
			+ "p_id integer, is_show integer,sequence integer, group_flag text)";
	public static final String TABLE_CREATE_ATTR = "create table if not exists attr_info("
			+ "_id integer,"
			+ "attr_name text,"
			+ "icon text,"
			+ "p_id integer, is_show integer)";
	public static final String TABLE_CREATE_TAG = "create table if not exists tag_info("
			+ "_id integer,"
			+ "attr_name text,"
			+ "icon text,"
			+ "p_id integer, is_show integer,sequence integer)";
	
	public static final String TABLE_CREATE_BUS_TAG = "create table if not exists bus_tag("
			+ "_id integer,"
			+ "attr_name text,"
			+ "icon text,"
			+ "p_id integer, is_show integer,sequence integer)";
	public static final String TABLE_CREATE_TYPE_TAG = "create table if not exists type_tag("
			+ "_id integer,"
			+ "class_name text,"
			+ "pic text,"
			+ "is_new integer, "
			+ "is_hot integer,"
			+ "sort integer,"
			+ "type integer,"
			+ "class_type integer)";
	public static final String TABLE_CREATE_SUPP_LABEL = "create table if not exists supp_label("
			+ "_id integer,"
			+ "name text,"
			+ "pic text,"
			+ "icon text, "
			+ "sort integer,"
			+ "remark text,"
			+ "add_time text)";
	public static final String TABLE_CREATE_FRIEND_CIRCLE_TAG = "create table if not exists friend_circle_tag("
			+ "_id integer,"
			+ "name text,"
			+ "is_show text,"
			+ "sort integer,"
			+ "type integer)";
	public static final String TABLE_CREATE_SHOP_GROUP_LIST = "create table if not exists shop_group_list("
			+ "_id integer,"
			+ "value text,"
			+ "icon text,"
			+ "app_name text,"
			+ "banner text)";

	public static final String TABLE_CREATE_STOCK_TYPE = "create table if not exists stock_type("
			+ "_id integer ,color text, size text , pic text,"
			+ " stock integer , price double )";

	public static final String TABLE_CREATE_FOOT_PRINT = "create table if not exists foot_print("
			+ "_id integer ,id text, def_pic text , shop_code text,"
			+ " shop_se_price text )";

	public static final String UPGRADE_SORT_INFO = "alter table sort_info add column sequence INTEGER;";
	
	public static final String UPGRADE_TAG_INFO = "alter table tag_info add column sequence INTEGER;";
	
	public static final String UPGRADE_2_SORT_INFO = "alter table sort_info add column group_flag text;";

	public static final String UPGRADE_GROUP_MESSAGE_6_1 = "alter table group_message add column group_avatar text;";
	public static final String UPGRADE_GROUP_MESSAGE_6_2 = "alter table group_message add column group_name text;";

	public static final String UPGRADE_TAG_INFO2 = "alter table tag_info add column e_name text";
	public static final String UPGRADE_SUPP_LABEL = "alter table supp_label add column type text";//增加品牌类型 1：后台定义 2 ：用户自定义的品牌
	public static String getCreateMessageTableComman(int version) {
		switch (version) {
		case 1:
			return TABLE_MESSAGE_CREATE_1;
		case 2:
		case 3:
			// return TABLE_MESSAGE_CREATE_2;
		case 4:
		case 5:
		case 6:
			// return TABLE_MESSAGE_CREATE_4;
		default:
			throw new IllegalArgumentException("unknow version: " + version);
		}
	}

	public static String getCreateSortInfoTableComman(int version) {
		switch (version) {
		case 1:
			
		case 2:
			return TABLE_CREATE_SORT;
		case 3:
			// return TABLE_MESSAGE_CREATE_2;
		case 4:
		case 5:
		case 6:
			// return TABLE_MESSAGE_CREATE_4;
		default:
			throw new IllegalArgumentException("unknow version: " + version);
		}
	}

	public static String getCreateAttrInfoTableComman(int version) {
		switch (version) {
		case 1:
			
		case 2:
			return TABLE_CREATE_ATTR;
		case 3:
			// return TABLE_MESSAGE_CREATE_2;
		case 4:
		case 5:
		case 6:
			// return TABLE_MESSAGE_CREATE_4;
		default:
			throw new IllegalArgumentException("unknow version: " + version);
		}
	}

	public static String getCreateTagInfoTableComman(int version) {
		switch (version) {
		case 1:
			
		case 2:
			return TABLE_CREATE_TAG;
		case 3:
			// return TABLE_MESSAGE_CREATE_2;
		case 4:
		case 5:
		case 6:
			// return TABLE_MESSAGE_CREATE_4;
		default:
			throw new IllegalArgumentException("unknow version: " + version);
		}
	}
	
	/**
	 * 创建公司停车场位置表 暂未统一
	 */
	// public static String getCompanyParkLotLocation(int version) {
	// switch (version) {
	// case 1:
	// case 2:
	// case 3:
	// case 4:
	// case 5:
	// case 6:
	// case 7:
	// case 8:
	// return CREATE_COMPANY_PARK_LOT_LOCATION;
	// default:
	// throw new IllegalArgumentException("unknow version: " + version);
	// }
	// }
}
