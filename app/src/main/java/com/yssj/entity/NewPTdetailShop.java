package com.yssj.entity;

import java.util.List;

/**
 * Created by Administrator on 2020/4/16.
 */

public class NewPTdetailShop {
    /**
     * data : [{"color":"白色","shop_code":"hGES1942069364","user_name":"qingfeng😂 😂 😂 😂","p_price":"null","type":1,"pay_money":0,"shop_original":0,"four_pic":"banner_hC19ZJ2g.jpg,share_hC19ZJ2g.jpg,share_hC19ZJ2g.jpg","shop_url":"hC19ZJ2g_600_900.jpg","id":6901,"n_status":0,"elide_price":"207.0","shop_price":2070,"end_time":1587007164000,"shop_se_price":579.6,"r_code":"2004169qUkxaEm","shop_name":"通勤半高领人物长袖印染卫衣","assmble_price":14.7,"order_code":"200416OdMCaRIn","q":1,"size":"S","user_portrait":"http://thirdwx.qlogo.cn/mmopen/vi_32/lFPknwJcD8TuYqUGrlZORria5vdsoUMpIbIYLOWU5xiakquKpbTN0o2vOqIqibb9nibjUVOauwicQSxHhd4amCVy5jw/132","user_id":946368,"shop_roll":7.5,"add_time":1587006804000,"status":13}]
     * now : 1587028549821
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
         * color : 白色
         * shop_code : hGES1942069364
         * user_name : qingfeng😂 😂 😂 😂
         * p_price : null
         * type : 1
         * pay_money : 0
         * shop_original : 0
         * four_pic : banner_hC19ZJ2g.jpg,share_hC19ZJ2g.jpg,share_hC19ZJ2g.jpg
         * shop_url : hC19ZJ2g_600_900.jpg
         * id : 6901
         * n_status : 0
         * elide_price : 207.0
         * shop_price : 2070
         * end_time : 1587007164000
         * shop_se_price : 579.6
         * r_code : 2004169qUkxaEm
         * shop_name : 通勤半高领人物长袖印染卫衣
         * assmble_price : 14.7
         * order_code : 200416OdMCaRIn
         * q : 1
         * size : S
         * user_portrait : http://thirdwx.qlogo.cn/mmopen/vi_32/lFPknwJcD8TuYqUGrlZORria5vdsoUMpIbIYLOWU5xiakquKpbTN0o2vOqIqibb9nibjUVOauwicQSxHhd4amCVy5jw/132
         * user_id : 946368
         * shop_roll : 7.5
         * add_time : 1587006804000
         * status : 13
         *
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
        private int shop_price;
        private long end_time;
        private double shop_se_price;
        private String r_code;
        private String shop_name;
        private String assmble_price;
        private String order_code;
        private int q;
        private String size;
        private String user_portrait;
        private int user_id;
        private double shop_roll;
        private long add_time;
        private int status;
        private int noPay;


        public int getNoPay() {
            return noPay;
        }

        public void setNoPay(int noPay) {
            this.noPay = noPay;
        }


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

        public int getShop_price() {
            return shop_price;
        }

        public void setShop_price(int shop_price) {
            this.shop_price = shop_price;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
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

        public String getAssmble_price() {
            return assmble_price;
        }

        public void setAssmble_price(String assmble_price) {
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
