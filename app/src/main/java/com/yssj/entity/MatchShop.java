package com.yssj.entity;

import java.io.Serializable;
import java.util.List;

import org.apache.http.entity.SerializableEntity;

import com.yssj.entity.MatchAttr.StocktypeBean;

public class MatchShop implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	/**
     * collocation_name : 最IN搭配
     * collocation_code : DAPM6121419
     * collocation_pic : oheygwxu_600_900.jpg
     * add_time : 1458817696000
     * collocation_remark : 1111
     * collocation_shop : [{"shop_code":"FAMX16121419","shop_name":"【FAMX16121419】T恤","shop_se_price":67.44,"option_flag":0,"shop_x":56,"shop_y":65,"seq":1},{"shop_code":"FAMX16121419","shop_name":"【FAMX16121419】T恤","shop_se_price":67.44,"option_flag":0,"shop_x":56,"shop_y":65,"seq":1}]
     * type_relation_ids : 2,3
     */
    private String collocation_name;//商品名
    private String collocation_code;//商品编号
    private String collocation_pic;//主图
    private String add_time;
    private String collocation_remark;
    private String type_relation_ids;
    /**
     * shop_code : FAMX16121419
     * shop_name : 【FAMX16121419】T恤
     * shop_se_price : 67.44
     * option_flag : 0
     * shop_x : 56
     * shop_y : 65
     * seq : 1
     */

    private List<CollocationShop> collocation_shop;
    private List<AttrList> attrList;
    
    public List<AttrList> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<AttrList> attrList) {
		this.attrList = attrList;
	}

	public String getCollocation_name() {
        return collocation_name;
    }

    public void setCollocation_name(String collocation_name) {
        this.collocation_name = collocation_name;
    }

    public String getCollocation_code() {
        return collocation_code;
    }

    public void setCollocation_code(String collocation_code) {
        this.collocation_code = collocation_code;
    }

    public String getCollocation_pic() {
        return collocation_pic;
    }

    public void setCollocation_pic(String collocation_pic) {
        this.collocation_pic = collocation_pic;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getCollocation_remark() {
        return collocation_remark;
    }

    public void setCollocation_remark(String collocation_remark) {
        this.collocation_remark = collocation_remark;
    }

    public String getType_relation_ids() {
        return type_relation_ids;
    }

    public void setType_relation_ids(String type_relation_ids) {
        this.type_relation_ids = type_relation_ids;
    }

    public List<CollocationShop> getCollocation_shop() {
        return collocation_shop;
    }

    public void setCollocation_shop(List<CollocationShop> collocation_shop) {
        this.collocation_shop = collocation_shop;
    }
    public static class AttrList implements Serializable{
    	/**
		 * 
		 */
		private static final long serialVersionUID = 3L;
		private String id;
        private String is_show;
        private String ico;
        private String color_type;
        private String attr_name;
        private String parent_id;
        
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getIs_show() {
			return is_show;
		}
		public void setIs_show(String is_show) {
			this.is_show = is_show;
		}
		public String getIco() {
			return ico;
		}
		public void setIco(String ico) {
			this.ico = ico;
		}
		public String getColor_type() {
			return color_type;
		}
		public void setColor_type(String color_type) {
			this.color_type = color_type;
		}
		public String getAttr_name() {
			return attr_name;
		}
		public void setAttr_name(String attr_name) {
			this.attr_name = attr_name;
		}
		public String getParent_id() {
			return parent_id;
		}
		public void setParent_id(String parent_id) {
			this.parent_id = parent_id;
		}
        
    }
    public static class CollocationShop implements Serializable {
        /**
		 * 
		 */
		private static final long serialVersionUID = 2L;
		private String shop_code;
        private String shop_name;
        private double shop_se_price;
        private int option_flag;
        private double shop_x;
        private double shop_y;
        private int seq;
        private double kickback;//回佣
        
        
		@Override
		public String toString() {
			return "CollocationShop [shop_code=" + shop_code + ", shop_name=" + shop_name + ", shop_se_price="
					+ shop_se_price + ", option_flag=" + option_flag + ", shop_x=" + shop_x + ", shop_y=" + shop_y
					+ ", seq=" + seq + ", list_stock_type=" + list_stock_type +", kickback=" + kickback + "]";
		}

		private List<StocktypeBean> list_stock_type;// 商品库存分类 stock_type[]
        
        
        public List<StocktypeBean> getList_stock_type() {
			return list_stock_type;
		}

		public void setList_stock_type(List<StocktypeBean> list_stock_type) {
			this.list_stock_type = list_stock_type;
		}

		public String getShop_code() {
            return shop_code;
        }

        public void setShop_code(String shop_code) {
            this.shop_code = shop_code;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public double getShop_se_price() {
            return shop_se_price;
        }

        public void setShop_se_price(double shop_se_price) {
            this.shop_se_price = shop_se_price;
        }

        public int getOption_flag() {
            return option_flag;
        }

        public void setOption_flag(int option_flag) {
            this.option_flag = option_flag;
        }

        public double getShop_x() {
            return shop_x;
        }

        public void setShop_x(double shop_x) {
            this.shop_x = shop_x;
        }

        public double getShop_y() {
            return shop_y;
        }

        public void setShop_y(double shop_y) {
            this.shop_y = shop_y;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

		public double getKickback() {
			return kickback;
		}

		public void setKickback(double kickback) {
			this.kickback = kickback;
		}
        
    }
}
