package com.yssj.entity;

public class RondomShop {
    /**
     * shop : {"shop_tag":"2001,2015,2019,2054,2059,2126,2272,2320,2330,2376,2453,2167,2210,2252,2446","def_pic":"b5FV2TvY_600_900.jpg","shop_code":"BGDA1833231596","shop_attr":"0,500,636_501,502,699,700,701_501,504,913,955,860_501,505,946,948,965_501,764,910,922,911_501,731,932,897,946","shop_se_price":"73.5","supp_id":"2177","supp_label":null,"shop_name":"欧美纯色直筒裤长裤破洞牛仔裤","type1":"4","content":"","app_shop_group_price":19.9,"shop_pic":"reveal_b5FV2TvY_1.jpg,reveal_b5FV2TvY_2.jpg,reveal_b5FV2TvY_3.jpg,reveal_b5FV2TvY_4.jpg,real_b5FV2TvY_5.jpg,detail_b5FV2TvY_6.jpg,realShot_b5FV2TvY.jpg,accessories_b5FV2TvY.jpg,rear_b5FV2TvY.jpg,side_b5FV2TvY.jpg,positive_b5FV2TvY.jpg,b5FV2TvY_600_900.jpg","four_pic":"banner_b5FV2TvY.jpg,share_b5FV2TvY.jpg,share_b5FV2TvY.jpg","wxcx_shop_group_price":19.9}
     * link : http://www.52yifu.com/view/store/d.html?r=13&s=BGDA1833231596
     * QrLink : http://www.52yifu.com/view/store/d.html?s=BGDA1833231596&r=13&k=913C033626F4AB86&Ii0o=EJOzMzKzXzYyDuY0MUS4Dq==
     * message : 操作成功.
     * status : 1
     */

    private ShopBean shop;
    private String link;
    private String QrLink;
    private String message;
    private String status;

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getQrLink() {
        return QrLink;
    }

    public void setQrLink(String QrLink) {
        this.QrLink = QrLink;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ShopBean {
        /**
         * shop_tag : 2001,2015,2019,2054,2059,2126,2272,2320,2330,2376,2453,2167,2210,2252,2446
         * def_pic : b5FV2TvY_600_900.jpg
         * shop_code : BGDA1833231596
         * shop_attr : 0,500,636_501,502,699,700,701_501,504,913,955,860_501,505,946,948,965_501,764,910,922,911_501,731,932,897,946
         * shop_se_price : 73.5
         * supp_id : 2177
         * supp_label : null
         * shop_name : 欧美纯色直筒裤长裤破洞牛仔裤
         * type1 : 4
         * content :
         * app_shop_group_price : 19.9
         * shop_pic : reveal_b5FV2TvY_1.jpg,reveal_b5FV2TvY_2.jpg,reveal_b5FV2TvY_3.jpg,reveal_b5FV2TvY_4.jpg,real_b5FV2TvY_5.jpg,detail_b5FV2TvY_6.jpg,realShot_b5FV2TvY.jpg,accessories_b5FV2TvY.jpg,rear_b5FV2TvY.jpg,side_b5FV2TvY.jpg,positive_b5FV2TvY.jpg,b5FV2TvY_600_900.jpg
         * four_pic : banner_b5FV2TvY.jpg,share_b5FV2TvY.jpg,share_b5FV2TvY.jpg
         * wxcx_shop_group_price : 19.9
         */

        private String shop_tag;
        private String def_pic;
        private String shop_code;
        private String shop_attr;
        private String shop_se_price;
        private String supp_id;
        private Object supp_label;
        private String shop_name;
        private String type1;
        private String content;
        private double app_shop_group_price;
        private String shop_pic;
        private String four_pic;
        private double wxcx_shop_group_price;

        public String getShop_tag() {
            return shop_tag;
        }

        public void setShop_tag(String shop_tag) {
            this.shop_tag = shop_tag;
        }

        public String getDef_pic() {
            return def_pic;
        }

        public void setDef_pic(String def_pic) {
            this.def_pic = def_pic;
        }

        public String getShop_code() {
            return shop_code;
        }

        public void setShop_code(String shop_code) {
            this.shop_code = shop_code;
        }

        public String getShop_attr() {
            return shop_attr;
        }

        public void setShop_attr(String shop_attr) {
            this.shop_attr = shop_attr;
        }

        public String getShop_se_price() {
            return shop_se_price;
        }

        public void setShop_se_price(String shop_se_price) {
            this.shop_se_price = shop_se_price;
        }

        public String getSupp_id() {
            return supp_id;
        }

        public void setSupp_id(String supp_id) {
            this.supp_id = supp_id;
        }

        public Object getSupp_label() {
            return supp_label;
        }

        public void setSupp_label(Object supp_label) {
            this.supp_label = supp_label;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getType1() {
            return type1;
        }

        public void setType1(String type1) {
            this.type1 = type1;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public double getApp_shop_group_price() {
            return app_shop_group_price;
        }

        public void setApp_shop_group_price(double app_shop_group_price) {
            this.app_shop_group_price = app_shop_group_price;
        }

        public String getShop_pic() {
            return shop_pic;
        }

        public void setShop_pic(String shop_pic) {
            this.shop_pic = shop_pic;
        }

        public String getFour_pic() {
            return four_pic;
        }

        public void setFour_pic(String four_pic) {
            this.four_pic = four_pic;
        }

        public double getWxcx_shop_group_price() {
            return wxcx_shop_group_price;
        }

        public void setWxcx_shop_group_price(double wxcx_shop_group_price) {
            this.wxcx_shop_group_price = wxcx_shop_group_price;
        }
    }
}
