package com.yssj.entity;

public class BaseData {

    public int getFreeOrderPage() {
        return freeOrderPage;
    }

    public void setFreeOrderPage(int freeOrderPage) {
        this.freeOrderPage = freeOrderPage;
    }

    private int freeOrderPage;

    public int getIsJumpPage() {
        return isJumpPage;
    }

    public void setIsJumpPage(int isJumpPage) {
        this.isJumpPage = isJumpPage;
    }

    public String getFreeMoney() {
        return freeMoney;
    }

    public void setFreeMoney(String freeMoney) {
        this.freeMoney = freeMoney;
    }

    private int isJumpPage;
    private String freeMoney;

    public String getDraw_code() {
        return draw_code;
    }

    public void setDraw_code(String draw_code) {
        this.draw_code = draw_code;
    }

    private String draw_code;

    public int getIsFail() {
        return isFail;
    }

    public void setIsFail(int isFail) {
        this.isFail = isFail;
    }

    private int isFail;

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

    private String message;
    private String status;
    private int newRaffle_type;//是否是真实到账，1是
    private double raffle_money;//金额

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    private String order_code;
    private int vipFreeDrawNum;
    private String vipFreeDrawMoney;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private  double price;

    public int getVipFreeDrawNum() {
        return vipFreeDrawNum;
    }

    public void setVipFreeDrawNum(int vipFreeDrawNum) {
        this.vipFreeDrawNum = vipFreeDrawNum;
    }

    public String getVipFreeDrawMoney() {
        return vipFreeDrawMoney;
    }

    public void setVipFreeDrawMoney(String vipFreeDrawMoney) {
        this.vipFreeDrawMoney = vipFreeDrawMoney;
    }


    public int getNewRaffle_type() {
        return newRaffle_type;
    }

    public void setNewRaffle_type(int newRaffle_type) {
        this.newRaffle_type = newRaffle_type;
    }

    public double getRaffle_money() {
        return raffle_money;
    }

    public void setRaffle_money(double raffle_money) {
        this.raffle_money = raffle_money;
    }


    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    private int data;

    public int getIsExist() {
        return isExist;
    }

    public void setIsExist(int isExist) {
        this.isExist = isExist;
    }

    private int isExist;

    public int getClock_in_status() {
        return clock_in_status;
    }

    public void setClock_in_status(int clock_in_status) {
        this.clock_in_status = clock_in_status;
    }

    private int clock_in_status;
    private int isNewbie01;

    public int getIsPopup() {//0不弹  1弹钻石  2弹皇冠
        return isPopup;
    }

    public void setIsPopup(int isPopup) {
        this.isPopup = isPopup;
    }

    public String getUnVipRaffleMoney() {
        return unVipRaffleMoney;
    }

    public void setUnVipRaffleMoney(String unVipRaffleMoney) {
        this.unVipRaffleMoney = unVipRaffleMoney;
    }

    public String getVip_price() {
        return vip_price;
    }

    public void setVip_price(String vip_price) {
        this.vip_price = vip_price;
    }

    String unVipRaffleMoney;// 非会员抵扣金额
    String vip_price;//  首张钻石卡价格


    private int isPopup;//首张钻石卡购买成功后转盘的弹窗

    public int getIsNewbie01() {
        return isNewbie01;
    }

    public void setIsNewbie01(int isNewbie01) {
        this.isNewbie01 = isNewbie01;
    }
}
