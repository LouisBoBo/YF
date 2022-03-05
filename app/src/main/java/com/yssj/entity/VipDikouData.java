package com.yssj.entity;

public class VipDikouData {
    /**
     * order_deduction : 30.0
     * deduction : 0.1
     * one_not_use_price : 0.2
     * now : 1559718460346
     * order_price : 30.0
     * message : 操作成功.
     * shop_deduction : 0.9
     * status : 1
     * is_open:“为他人分享” 功能开关 0关闭 1打开
     */

    private double order_deduction;
    private String deduction;
    private double one_not_use_price;
    private long now;
    private double order_price;
    private String message;
    private double shop_deduction;
    private String status;
    private int  isVip;
    private int maxType;
    private int is_open;

    public int getIs_open() {
        return is_open;
    }

    public void setIs_open(int is_open) {
        this.is_open = is_open;
    }



    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public int getMaxType() {
        return maxType;
    }

    public void setMaxType(int maxType) {
        this.maxType = maxType;
    }



    public double getOrder_deduction() {
        return order_deduction;
    }

    public void setOrder_deduction(double order_deduction) {
        this.order_deduction = order_deduction;
    }

    public String getDeduction() {
        return deduction;
    }

    public void setDeduction(String deduction) {
        this.deduction = deduction;
    }

    public double getOne_not_use_price() {
        return one_not_use_price;
    }

    public void setOne_not_use_price(double one_not_use_price) {
        this.one_not_use_price = one_not_use_price;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(double order_price) {
        this.order_price = order_price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getShop_deduction() {
        return shop_deduction;
    }

    public void setShop_deduction(double shop_deduction) {
        this.shop_deduction = shop_deduction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
