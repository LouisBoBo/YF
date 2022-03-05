package com.yssj.entity;

public class CJTXcountData {
    /**
     * all_count : 10//当天可以抽奖的总次数
     * data : 10  //剩余抽奖次数
     * all_money : 0.0 //当天已抽中累计的总抽奖金额（虚拟不可提现）
     * message : 操作成功.
     * tixian_count : 1 //提现卡张数
     * status : 1
     */

    private int all_count;
    private int data;
    private int tixian_twoCount;//是否买了2次提现卡

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    private int isVip;

    public int getTrial_hidden_switch() {
        return trial_hidden_switch;
    }

    public void setTrial_hidden_switch(int trial_hidden_switch) {
        this.trial_hidden_switch = trial_hidden_switch;
    }

    private int trial_hidden_switch;


    public int getNew_raffle_skipSwitch() {
        return new_raffle_skipSwitch;
    }

    public void setNew_raffle_skipSwitch(int new_raffle_skipSwitch) {
        this.new_raffle_skipSwitch = new_raffle_skipSwitch;
    }

    private int new_raffle_skipSwitch;



    private int maxType;
    private double all_money;
    private String message;
    private int tixian_count; //是否买过提现卡

    private int toMakeMoney_page; //是否因买会员卡后抽奖次数被清0
    private String status;
    private int reRoundCount;//剩余轮数

    private int is_finish;//20次有没有抽完 0抽完 1没有抽完

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    private long expireTime;


    public int getIs_finish() {
        return is_finish;
    }

    public void setIs_finish(int is_finish) {
        this.is_finish = is_finish;
    }




    public int getReRoundCount() {
        return reRoundCount;
    }

    public void setReRoundCount(int reRoundCount) {
        this.reRoundCount = reRoundCount;
    }


    public int getToMakeMoney_page() {
        return toMakeMoney_page;
    }

    public void setToMakeMoney_page(int toMakeMoney_page) {
        this.toMakeMoney_page = toMakeMoney_page;
    }


    public int getTixian_twoCount() {
        return tixian_twoCount;
    }

    public void setTixian_twoCount(int tixian_twoCount) {
        this.tixian_twoCount = tixian_twoCount;
    }




    public int getMaxType() {
        return maxType;
    }

    public void setMaxType(int maxType) {
        this.maxType = maxType;
    }


    public int getAll_count() {
        return all_count;
    }

    public void setAll_count(int all_count) {
        this.all_count = all_count;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public double getAll_money() {
        return all_money;
    }

    public void setAll_money(double all_money) {
        this.all_money = all_money;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTixian_count() {
        return tixian_count;
    }

    public void setTixian_count(int tixian_count) {
        this.tixian_count = tixian_count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
