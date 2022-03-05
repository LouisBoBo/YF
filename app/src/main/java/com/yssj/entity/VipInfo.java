package com.yssj.entity;

import java.io.Serializable;

public class VipInfo implements Serializable {
    /**
     * vip_type : 0
     * vipFreeText :
     * vip_page : 0
     * vip_free : 1
     * message : 需要购买会员.
     * isVip : 0
     * first_group : 1
     * status : 1
     */

    private int vip_type;
    private String vipFreeText;
    private int vip_page;
    private int vip_free;
    private String message;
    private int isVip;
    private int first_group;
    private String status;
    private int maxType;
    private int diamondCount;  //钻石卡张
    private int crownCount;  //皇冠卡张数

    public int getDiamondCount() {
        return diamondCount;
    }

    public void setDiamondCount(int diamondCount) {
        this.diamondCount = diamondCount;
    }

    public int getCrownCount() {
        return crownCount;
    }

    public void setCrownCount(int crownCount) {
        this.crownCount = crownCount;
    }



    public int getMaxType() {
        return maxType;
    }

    public void setMaxType(int maxType) {
        this.maxType = maxType;
    }


    public int getVip_type() {
        return vip_type;
    }

    public void setVip_type(int vip_type) {
        this.vip_type = vip_type;
    }

    public String getVipFreeText() {
        return vipFreeText;
    }

    public void setVipFreeText(String vipFreeText) {
        this.vipFreeText = vipFreeText;
    }

    public int getVip_page() {
        return vip_page;
    }

    public void setVip_page(int vip_page) {
        this.vip_page = vip_page;
    }

//    public int getVip_free() {
//        return vip_free;
//    }

    public void setVip_free(int vip_free) {
        this.vip_free = vip_free;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public int getFirst_group() {
        return first_group;
    }

    public void setFirst_group(int first_group) {
        this.first_group = first_group;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
