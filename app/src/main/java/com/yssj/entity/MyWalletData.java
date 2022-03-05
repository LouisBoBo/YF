package com.yssj.entity;

import java.util.List;

/**
 * Created by Administrator on 2020/4/21.
 */

public class MyWalletData {
    /**
     * wechat : 18011906325
     * vip_balance : 0
     * freeze_balance : 0
     * pic : https://wx.qlogo.cn/mmopen/vi_32/TRdduNGHmiaEU5OZ70DG6mzSofTtvic2aWaE6QTYrdrFe94krTP3d0Gmmvg6hiaAEdRuh0upYgJRYgU3D0CHSPojg/132
     * message : 操作成功.
     * peas_free : 0
     * point_count : 0
     * point_user_count : 0
     * peas : 0
     * extract : 87.53
     * balance : 13
     * user_id : 946429
     * point_user_list : []
     * nickname : qingfeng😂 😂 😂 😂
     * location : null
     * ex_free : 0
     * status : 1
     */

    private String wechat;
    private double vip_balance;

    public double getRaffleMoney() {
        return raffleMoney;
    }

    public void setRaffleMoney(double raffleMoney) {
        this.raffleMoney = raffleMoney;
    }

    private double raffleMoney;
    private int freeze_balance;
    private String pic;
    private String message;
    private int peas_free;
    private int point_count;
    private int point_user_count;
    private int peas;
    private double extract;
    private int balance;
    private int user_id;
    private String nickname;
    private Object location;
    private int ex_free;
    private String status;
    private List<?> point_user_list;
    private int conponCount;
    private double ext_money;


    public double getExt_money() {
        return ext_money;
    }

    public void setExt_money(double ext_money) {
        this.ext_money = ext_money;
    }



    public int getConponCount() {
        return conponCount;
    }

    public void setConponCount(int conponCount) {
        this.conponCount = conponCount;
    }


    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public double getVip_balance() {
        return vip_balance;
    }

    public void setVip_balance(double vip_balance) {
        this.vip_balance = vip_balance;
    }

    public int getFreeze_balance() {
        return freeze_balance;
    }

    public void setFreeze_balance(int freeze_balance) {
        this.freeze_balance = freeze_balance;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPeas_free() {
        return peas_free;
    }

    public void setPeas_free(int peas_free) {
        this.peas_free = peas_free;
    }

    public int getPoint_count() {
        return point_count;
    }

    public void setPoint_count(int point_count) {
        this.point_count = point_count;
    }

    public int getPoint_user_count() {
        return point_user_count;
    }

    public void setPoint_user_count(int point_user_count) {
        this.point_user_count = point_user_count;
    }

    public int getPeas() {
        return peas;
    }

    public void setPeas(int peas) {
        this.peas = peas;
    }

    public double getExtract() {
        return extract;
    }

    public void setExtract(double extract) {
        this.extract = extract;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public int getEx_free() {
        return ex_free;
    }

    public void setEx_free(int ex_free) {
        this.ex_free = ex_free;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<?> getPoint_user_list() {
        return point_user_list;
    }

    public void setPoint_user_list(List<?> point_user_list) {
        this.point_user_list = point_user_list;
    }
}
