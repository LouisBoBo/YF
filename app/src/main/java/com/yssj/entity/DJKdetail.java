package com.yssj.entity;

/**
 * Created by Administrator on 2020/4/16.
 */

public class DJKdetail {
    /**
     * deliveryCardNum : 0
     * freeCardNum : 0
     * message : 操作成功.
     * status : 1
     */

    private int deliveryCardNum;//发货卡张数
    private int freeCardNum;//免拼卡张数
    private String message;
    private String status;
    private int isDeliver;//当前订单是否已经发货
    private int vipMaxType;
    private int isVip;

    public int getVipMaxType() {
        return vipMaxType;
    }

    public void setVipMaxType(int vipMaxType) {
        this.vipMaxType = vipMaxType;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }




    public int getIsDeliver() {
        return isDeliver;
    }

    public void setIsDeliver(int isDeliver) {
        this.isDeliver = isDeliver;
    }


    public int getDeliveryCardNum() {
        return deliveryCardNum;
    }

    public void setDeliveryCardNum(int deliveryCardNum) {
        this.deliveryCardNum = deliveryCardNum;
    }

    public int getFreeCardNum() {
        return freeCardNum;
    }

    public void setFreeCardNum(int freeCardNum) {
        this.freeCardNum = freeCardNum;
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
}
