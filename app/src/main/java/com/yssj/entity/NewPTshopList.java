package com.yssj.entity;

import java.util.List;

public class NewPTshopList {
    /**
     * data : [{"color":"蓝色","shop_code":"hGES1941069374","user_name":"qingfeng😂 😂 😂 😂","p_price":"null","type":1,"pay_money":0,"shop_original":0,"four_pic":"banner_9ZS1IP9u.jpg,share_9ZS1IP9u.jpg,share_9ZS1IP9u.jpg","shop_url":"9ZS1IP9u_600_900.jpg","id":6716,"n_status":0,"elide_price":"49.5","shop_price":170.7,"shop_se_price":49.5,"r_code":"191226k9QqVdUL","shop_name":"通勤圆领条纹长袖螺纹针织衫","assmble_price":4.2,"order_code":"191226owdJKxIU","q":1,"size":"M","user_portrait":"https://wx.qlogo.cn/mmopen/vi_32/TRdduNGHmiaEU5OZ70DG6mzSofTtvic2aWaE6QTYrdrFe94krTP3d0Gmmvg6hiaAEdRuh0upYgJRYgU3D0CHSPojg/132","user_id":945798,"shop_roll":4.2,"add_time":1577346046000,"status":11},{"color":"黄色","shop_code":"hGES1941069374","user_name":"Louis","p_price":"null","type":2,"pay_money":0,"shop_original":0,"four_pic":"banner_9ZS1IP9u.jpg,share_9ZS1IP9u.jpg,share_9ZS1IP9u.jpg","shop_url":"9ZS1IP9u_600_900.jpg","id":6717,"n_status":0,"elide_price":"49.5","shop_price":170.7,"shop_se_price":49.5,"r_code":"191226k9QqVdUL","shop_name":"通勤圆领条纹长袖螺纹针织衫","assmble_price":4.2,"order_code":"191226SVcBsT0t","q":1,"size":"M","user_portrait":"https://wx.qlogo.cn/mmopen/vi_32/Rl2pxU6dm5v9jZZNQHv4ryfhqZhqM6TXH5KsHUiaOOGtNd9uTtGwAKfIO3Eun9CDic6n1AibuSUiaiaC7H5sxfNiaP1Q/132","user_id":945802,"shop_roll":4.2,"add_time":1577346639000,"status":11}]
     * now : 1577347472058
     * message : 操作成功
     * status : 1
     */

    private long now;
    private String message;
    private String status;
    private List<DataBean> data;

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * color : 蓝色
         * shop_code : hGES1941069374
         * user_name : qingfeng😂 😂 😂 😂
         * p_price : null
         * type : 1
         * pay_money : 0
         * shop_original : 0
         * four_pic : banner_9ZS1IP9u.jpg,share_9ZS1IP9u.jpg,share_9ZS1IP9u.jpg
         * shop_url : 9ZS1IP9u_600_900.jpg
         * id : 6716
         * n_status : 0
         * elide_price : 49.5
         * shop_price : 170.7
         * shop_se_price : 49.5
         * r_code : 191226k9QqVdUL
         * shop_name : 通勤圆领条纹长袖螺纹针织衫
         * assmble_price : 4.2
         * order_code : 191226owdJKxIU
         * q : 1
         * size : M
         * user_portrait : https://wx.qlogo.cn/mmopen/vi_32/TRdduNGHmiaEU5OZ70DG6mzSofTtvic2aWaE6QTYrdrFe94krTP3d0Gmmvg6hiaAEdRuh0upYgJRYgU3D0CHSPojg/132
         * user_id : 945798
         * shop_roll : 4.2
         * add_time : 1577346046000
         * status : 11
         */

        private String color;
        private String shop_code;
        private String user_name;
        private String p_price;
        private int type;
        private int pay_money;
        private int shop_original;
        private String four_pic;
        private String shop_url;
        private int id;
        private int n_status;
        private String elide_price;
        private double shop_price;
        private double shop_se_price;
        private String r_code;
        private String shop_name;
        private double assmble_price;
        private String order_code;
        private int q;
        private String size;
        private String user_portrait;
        private int user_id;
        private double shop_roll;
        private long add_time;
        private int status;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getShop_code() {
            return shop_code;
        }

        public void setShop_code(String shop_code) {
            this.shop_code = shop_code;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getP_price() {
            return p_price;
        }

        public void setP_price(String p_price) {
            this.p_price = p_price;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPay_money() {
            return pay_money;
        }

        public void setPay_money(int pay_money) {
            this.pay_money = pay_money;
        }

        public int getShop_original() {
            return shop_original;
        }

        public void setShop_original(int shop_original) {
            this.shop_original = shop_original;
        }

        public String getFour_pic() {
            return four_pic;
        }

        public void setFour_pic(String four_pic) {
            this.four_pic = four_pic;
        }

        public String getShop_url() {
            return shop_url;
        }

        public void setShop_url(String shop_url) {
            this.shop_url = shop_url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getN_status() {
            return n_status;
        }

        public void setN_status(int n_status) {
            this.n_status = n_status;
        }

        public String getElide_price() {
            return elide_price;
        }

        public void setElide_price(String elide_price) {
            this.elide_price = elide_price;
        }

        public double getShop_price() {
            return shop_price;
        }

        public void setShop_price(double shop_price) {
            this.shop_price = shop_price;
        }

        public double getShop_se_price() {
            return shop_se_price;
        }

        public void setShop_se_price(double shop_se_price) {
            this.shop_se_price = shop_se_price;
        }

        public String getR_code() {
            return r_code;
        }

        public void setR_code(String r_code) {
            this.r_code = r_code;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public double getAssmble_price() {
            return assmble_price;
        }

        public void setAssmble_price(double assmble_price) {
            this.assmble_price = assmble_price;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public int getQ() {
            return q;
        }

        public void setQ(int q) {
            this.q = q;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getUser_portrait() {
            return user_portrait;
        }

        public void setUser_portrait(String user_portrait) {
            this.user_portrait = user_portrait;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public double getShop_roll() {
            return shop_roll;
        }

        public void setShop_roll(double shop_roll) {
            this.shop_roll = shop_roll;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
