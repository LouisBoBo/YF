package com.yssj.entity;

/**
 * Created by Administrator on 2020/4/3 0003.
 */

public class HotShop {
    /**
     * shop : {"shop_tag":"2002,2046,2027,2054,2059,2119,2086,2072,2180,2272,2314,2326,2373,2454,2133,2190,2201,2445,2217,2227,2252","def_pic":"m4gVBaET_600_900.jpg","shop_code":"hGDY1941812345","shop_attr":"0,500,524,581,757_501,502,699,700,701_501,503,979,987,1005_501,761,913,883,945_501,762,873,1015,1016","shop_se_price":"162.0","supp_id":"2212","supp_label":"ZARA制造商","shop_name":"甜美POLO领纯色长袖口袋衬衫","type1":"2","content":"","assmble_price":6.5,"app_shop_group_price":25.8,"shop_pic":"reveal_m4gVBaET_3.jpg,reveal_m4gVBaET_4.jpg,reveal_m4gVBaET_5.jpg,reveal_m4gVBaET_6.jpg,reveal_m4gVBaET_7.jpg,reveal_m4gVBaET_8.jpg,reveal_m4gVBaET_9.jpg,reveal_m4gVBaET_10.jpg,reveal_m4gVBaET_11.jpg,reveal_m4gVBaET_12.jpg,reveal_m4gVBaET_13.jpg,reveal_m4gVBaET_14.jpg,reveal_m4gVBaET_15.jpg,reveal_m4gVBaET_16.jpg,reveal_m4gVBaET_17.jpg,reveal_m4gVBaET_18.jpg,reveal_m4gVBaET_19.jpg,reveal_m4gVBaET_20.jpg,reveal_m4gVBaET_21.jpg,reveal_m4gVBaET_22.jpg,reveal_m4gVBaET_23.jpg,reveal_m4gVBaET_24.jpg,reveal_m4gVBaET_25.jpg,reveal_m4gVBaET_26.jpg,reveal_m4gVBaET_27.jpg,reveal_m4gVBaET_28.jpg,reveal_m4gVBaET_29.jpg,reveal_m4gVBaET_30.jpg,reveal_m4gVBaET_31.jpg,reveal_m4gVBaET_32.jpg,reveal_m4gVBaET_33.jpg,reveal_m4gVBaET_34.jpg,reveal_m4gVBaET_35.jpg,reveal_m4gVBaET_36.jpg,reveal_m4gVBaET_37.jpg,reveal_m4gVBaET_38.jpg,real_m4gVBaET_39.jpg,detail_m4gVBaET_40.jpg,style_m4gVBaET.jpg,work_m4gVBaET.jpg,realShot_m4gVBaET.jpg,accessories_m4gVBaET.jpg,rear_m4gVBaET.jpg,side_m4gVBaET.jpg,positive_m4gVBaET.jpg,m4gVBaET_600_900.jpg","four_pic":"banner_m4gVBaET.jpg,share_m4gVBaET.jpg,share_m4gVBaET.jpg","wxcx_shop_group_price":25.8}
     * link : http://www.52yifu.com/view/store/d.html?r=13&s=hGDY1941812345
     * QrLink : http://www.52yifu.com/view/store/d.html?s=hGDY1941812345&r=13&k=331FE132104625DF&Ii0o=XzXnHuUnXzSnXNM2XtZOHq==
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
         * shop_tag : 2002,2046,2027,2054,2059,2119,2086,2072,2180,2272,2314,2326,2373,2454,2133,2190,2201,2445,2217,2227,2252
         * def_pic : m4gVBaET_600_900.jpg
         * shop_code : hGDY1941812345
         * shop_attr : 0,500,524,581,757_501,502,699,700,701_501,503,979,987,1005_501,761,913,883,945_501,762,873,1015,1016
         * shop_se_price : 162.0
         * supp_id : 2212
         * supp_label : ZARA制造商
         * shop_name : 甜美POLO领纯色长袖口袋衬衫
         * type1 : 2
         * content :
         * assmble_price : 6.5
         * app_shop_group_price : 25.8
         * shop_pic : reveal_m4gVBaET_3.jpg,reveal_m4gVBaET_4.jpg,reveal_m4gVBaET_5.jpg,reveal_m4gVBaET_6.jpg,reveal_m4gVBaET_7.jpg,reveal_m4gVBaET_8.jpg,reveal_m4gVBaET_9.jpg,reveal_m4gVBaET_10.jpg,reveal_m4gVBaET_11.jpg,reveal_m4gVBaET_12.jpg,reveal_m4gVBaET_13.jpg,reveal_m4gVBaET_14.jpg,reveal_m4gVBaET_15.jpg,reveal_m4gVBaET_16.jpg,reveal_m4gVBaET_17.jpg,reveal_m4gVBaET_18.jpg,reveal_m4gVBaET_19.jpg,reveal_m4gVBaET_20.jpg,reveal_m4gVBaET_21.jpg,reveal_m4gVBaET_22.jpg,reveal_m4gVBaET_23.jpg,reveal_m4gVBaET_24.jpg,reveal_m4gVBaET_25.jpg,reveal_m4gVBaET_26.jpg,reveal_m4gVBaET_27.jpg,reveal_m4gVBaET_28.jpg,reveal_m4gVBaET_29.jpg,reveal_m4gVBaET_30.jpg,reveal_m4gVBaET_31.jpg,reveal_m4gVBaET_32.jpg,reveal_m4gVBaET_33.jpg,reveal_m4gVBaET_34.jpg,reveal_m4gVBaET_35.jpg,reveal_m4gVBaET_36.jpg,reveal_m4gVBaET_37.jpg,reveal_m4gVBaET_38.jpg,real_m4gVBaET_39.jpg,detail_m4gVBaET_40.jpg,style_m4gVBaET.jpg,work_m4gVBaET.jpg,realShot_m4gVBaET.jpg,accessories_m4gVBaET.jpg,rear_m4gVBaET.jpg,side_m4gVBaET.jpg,positive_m4gVBaET.jpg,m4gVBaET_600_900.jpg
         * four_pic : banner_m4gVBaET.jpg,share_m4gVBaET.jpg,share_m4gVBaET.jpg
         * wxcx_shop_group_price : 25.8
         */

        private String shop_tag;
        private String def_pic;
        private String shop_code;
        private String shop_attr;
        private String shop_se_price;
        private String supp_id;
        private String supp_label;
        private String shop_name;
        private String type1;
        private String content;
        private double assmble_price;
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

        public String getSupp_label() {
            return supp_label;
        }

        public void setSupp_label(String supp_label) {
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

        public double getAssmble_price() {
            return assmble_price;
        }

        public void setAssmble_price(double assmble_price) {
            this.assmble_price = assmble_price;
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
