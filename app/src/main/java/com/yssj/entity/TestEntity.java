package com.yssj.entity;

import java.io.Serializable;

public class TestEntity {
    /**
     * pay_count : 1
     * like_count : 0
     * balance_show : 1
     * send_count : 0
     * change_count : 0
     * coupon_sum : 10.0
     * mySteps_count : 3
     * message : 操作成功.
     * furl_count : 0
     * vipData : {"head_url":"vip/vip_head/crownhg.png","vip_type":5,"vip_name":"皇冠卡"}
     * fans_count : 0
     * ass_count : 0
     * balance : 0.1
     * integral : 0
     * status : 1
     */

    private String pay_count;
    private int like_count;
    private String balance_show;
    private String send_count;
    private String change_count;
    private double coupon_sum;
    private int mySteps_count;
    private String message;
    private String furl_count;
    private VipDataBean vipData;
    private int fans_count;
    private String ass_count;
    private double balance;
    private int integral;
    private String status;

    public String getPay_count() {
        return pay_count;
    }

    public void setPay_count(String pay_count) {
        this.pay_count = pay_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public String getBalance_show() {
        return balance_show;
    }

    public void setBalance_show(String balance_show) {
        this.balance_show = balance_show;
    }

    public String getSend_count() {
        return send_count;
    }

    public void setSend_count(String send_count) {
        this.send_count = send_count;
    }

    public String getChange_count() {
        return change_count;
    }

    public void setChange_count(String change_count) {
        this.change_count = change_count;
    }

    public double getCoupon_sum() {
        return coupon_sum;
    }

    public void setCoupon_sum(double coupon_sum) {
        this.coupon_sum = coupon_sum;
    }

    public int getMySteps_count() {
        return mySteps_count;
    }

    public void setMySteps_count(int mySteps_count) {
        this.mySteps_count = mySteps_count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFurl_count() {
        return furl_count;
    }

    public void setFurl_count(String furl_count) {
        this.furl_count = furl_count;
    }

    public VipDataBean getVipData() {
        return vipData;
    }

    public void setVipData(VipDataBean vipData) {
        this.vipData = vipData;
    }

    public int getFans_count() {
        return fans_count;
    }

    public void setFans_count(int fans_count) {
        this.fans_count = fans_count;
    }

    public String getAss_count() {
        return ass_count;
    }

    public void setAss_count(String ass_count) {
        this.ass_count = ass_count;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class VipDataBean implements Serializable {
        /**
         * head_url : vip/vip_head/crownhg.png
         * vip_type : 5
         * vip_name : 皇冠卡
         */

        private String head_url;
        private int vip_type;
        private String vip_name;

        public String getHead_url() {
            return head_url;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }

        public int getVip_type() {
            return vip_type;
        }

        public void setVip_type(int vip_type) {
            this.vip_type = vip_type;
        }

        public String getVip_name() {
            return vip_name;
        }

        public void setVip_name(String vip_name) {
            this.vip_name = vip_name;
        }
    }
}
