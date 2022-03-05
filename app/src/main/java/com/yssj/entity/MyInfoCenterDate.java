package com.yssj.entity;

import java.io.Serializable;

public class MyInfoCenterDate {
    /**
     * pay_count : 0
     * like_count : 0
     * balance_show : 0
     * send_count : 0
     * change_count : 0
     * coupon_sum : 0
     * mySteps_count : 3
     * message : 操作成功.
     * furl_count : 0
     * fans_count : 1
     * ass_count : 0
     * balance : 0
     * integral : 0
     * status : 1
     */

    private String pay_count = "0";
    private String like_count = "0";
    private String balance_show = "0";
    private String send_count = "0";
    private String change_count = "0";
    private String coupon_sum = "0";
    private String mySteps_count = "0";
    private String message = "0";
    private String furl_count = "0";
    private String fans_count = "0";
    private String ass_count = "0";
    private String balance = "0";
    private String integral = "0";
    private int status;
    private String refund_count = "0";
    private VipDataBean vipData;

    public String getShowExtMoney() {
        return showExtMoney;
    }

    public void setShowExtMoney(String showExtMoney) {
        this.showExtMoney = showExtMoney;
    }

    private  String showExtMoney;





    public String getStore_shop_count() {
        return store_shop_count;
    }

    public void setStore_shop_count(String store_shop_count) {
        this.store_shop_count = store_shop_count;
    }

    private String store_shop_count = "0";

    public VipDataBean getVipData() {
        return vipData;
    }

    public void setVipData(VipDataBean vipData) {
        this.vipData = vipData;
    }

    public String getRefund_count() {
        return refund_count;
    }

    public void setRefund_count(String refund_count) {
        this.refund_count = refund_count;
    }


    public String getPay_count() {
        return pay_count;
    }

    public void setPay_count(String pay_count) {
        this.pay_count = pay_count;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
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

    public String getCoupon_sum() {
        return coupon_sum;
    }

    public void setCoupon_sum(String coupon_sum) {
        this.coupon_sum = coupon_sum;
    }

    public String getMySteps_count() {
        return mySteps_count;
    }

    public void setMySteps_count(String mySteps_count) {
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

    public String getFans_count() {
        return fans_count;
    }

    public void setFans_count(String fans_count) {
        this.fans_count = fans_count;
    }

    public String getAss_count() {
        return ass_count;
    }

    public void setAss_count(String ass_count) {
        this.ass_count = ass_count;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
