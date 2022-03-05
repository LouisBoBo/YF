package com.yssj.entity;

import java.util.HashMap;
import java.util.List;

public class MatchAttr {

    /**
     * status : 1
     * shop_attr : {"SAPO16122902":"0,500,636_501,502,699,700,701_501,503,854,897,948_501,760,891_501,761,891_501,762,891_501,504,913,860,944_501,505,896,781,920_501,763,891_501,764,891_501,731,849,854,857","SAPO16115927":"0,500,520,539,656_501,502,700_501,503,777_501,760,769_501,761,891_501,762,898_501,504,891_501,505,891_501,763,891_501,764,891_501,731,891"}
     * stocktype : [{"shop_code":"SAPO16115927","original_price":"30.0","pic":"stock_H7doNSzh_0.jpg","kickback":"3.9","shop_name":"��SAPO16115927������","id":"13481","stock":"0","color_size":"539:700","price":"43.9","shop_price":"118.5","supp_id":"1061","three_kickback":"4.4","two_kickback":"0.3"},{"shop_code":"SAPO16115927","original_price":"30.0","pic":"stock_H7doNSzh_2.jpg","kickback":"3.9","shop_name":"��SAPO16115927������","id":"13485","color_size":"656:700","price":"43.9","shop_price":"118.5","stock":"100","supp_id":"1061","three_kickback":"4.4","two_kickback":"0.3"},{"shop_code":"SAPO16115927","original_price":"30.0","pic":"stock_H7doNSzh_1.jpg","kickback":"3.9","shop_name":"��SAPO16115927������","id":"13483","stock":"120","price":"43.9","color_size":"520:700","shop_price":"118.5","supp_id":"1061","three_kickback":"4.4","two_kickback":"0.3"},{"shop_code":"SAPO16122902","original_price":"129.35","pic":"stock_ZEAL48rY_0.jpg","kickback":"16.82","shop_name":"��SAPO16122902����������ȹ","id":"4933","color_size":"636:699","price":"171.1","shop_price":"479.0","stock":"154","supp_id":"1061","three_kickback":"17.1","two_kickback":"1.29"},{"shop_code":"SAPO16122902","original_price":"129.35","pic":"stock_ZEAL48rY_0.jpg","kickback":"19.4","shop_name":"��SAPO16122902����������ȹ","id":"4935","shop_price":"479.0","stock":"154","price":"171.1","color_size":"636:700","supp_id":"1061","three_kickback":"17.1","two_kickback":"1.29"},{"shop_code":"SAPO16122902","original_price":"129.35","pic":"stock_ZEAL48rY_0.jpg","kickback":"16.82","shop_name":"��SAPO16122902����������ȹ","id":"4937","color_size":"636:701","price":"171.1","shop_price":"479.0","stock":"154","supp_id":"1061","three_kickback":"17.1","two_kickback":"1.29"}]
     */

    private String status;
    /**
     * SAPO16122902 : 0,500,636_501,502,699,700,701_501,503,854,897,948_501,760,891_501,761,891_501,762,891_501,504,913,860,944_501,505,896,781,920_501,763,891_501,764,891_501,731,849,854,857
     * SAPO16115927 : 0,500,520,539,656_501,502,700_501,503,777_501,760,769_501,761,891_501,762,898_501,504,891_501,505,891_501,763,891_501,764,891_501,731,891
     */

    private ShopAttrBean shop_attr;
    /**
     * shop_code : SAPO16115927
     * original_price : 30.0
     * pic : stock_H7doNSzh_0.jpg
     * kickback : 3.9
     * shop_name : SAPO16115927
     * id : 13481
     * stock : 0
     * color_size : 539:700
     * price : 43.9
     * shop_price : 118.5
     * supp_id : 1061
     * three_kickback : 4.4
     * two_kickback : 0.3
     */

    private List<StocktypeBean> stocktype;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ShopAttrBean getShop_attr() {
        return shop_attr;
    }

    public void setShop_attr(ShopAttrBean shop_attr) {
        this.shop_attr = shop_attr;
    }

    public List<StocktypeBean> getStocktype() {
        return stocktype;
    }

    public void setStocktype(List<StocktypeBean> stocktype) {
        this.stocktype = stocktype;
    }

    
    public static class ShopAttrBean {
        private HashMap<String,String> shopAttrHashMap;
        

        public HashMap<String,String> getShopAttrHashMap() {
            return shopAttrHashMap;
        }

        public void setShopAttrHashMap( HashMap<String,String> shopAttrHashMap) {
            this.shopAttrHashMap = shopAttrHashMap;
        }
    }

    public static class StocktypeBean {
        private String shop_code;
        private String original_price;
        private String pic;
        private String kickback;
        private String shop_name;
        private String id;
        private String stock;
        private String color_size;
        private String price;
        private String shop_price;
        private String supp_id;
        private String three_kickback;
        private String two_kickback;
        private String cores;
        
        @Override
		public String toString() {
			return "StocktypeBean [shop_code=" + shop_code + ", original_price=" + original_price + ", pic=" + pic
					+ ", kickback=" + kickback + ", shop_name=" + shop_name + ", id=" + id + ", stock=" + stock
					+ ", color_size=" + color_size + ", price=" + price + ", shop_price=" + shop_price + ", supp_id="
					+ supp_id + ", three_kickback=" + three_kickback + ", two_kickback=" + two_kickback + "]";
		}

        
		public String getCores() {
			return cores;
		}

		public void setCores(String cores) {
			this.cores = cores;
		}


		public String getShop_code() {
            return shop_code;
        }

        public void setShop_code(String shop_code) {
            this.shop_code = shop_code;
        }

        public String getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getKickback() {
            return kickback;
        }

        public void setKickback(String kickback) {
            this.kickback = kickback;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getColor_size() {
            return color_size;
        }

        public void setColor_size(String color_size) {
            this.color_size = color_size;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getSupp_id() {
            return supp_id;
        }

        public void setSupp_id(String supp_id) {
            this.supp_id = supp_id;
        }

        public String getThree_kickback() {
            return three_kickback;
        }

        public void setThree_kickback(String three_kickback) {
            this.three_kickback = three_kickback;
        }

        public String getTwo_kickback() {
            return two_kickback;
        }

        public void setTwo_kickback(String two_kickback) {
            this.two_kickback = two_kickback;
        }
    }
}