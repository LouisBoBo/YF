package com.yssj.entity;

public class TXCJzhongjiangData {
    private String message;
    private String status;

    private String free_url;
    private double raffle_money;//单次中奖金额(客服除外)
    private  int freeBuyType;



    public int getFreeBuyType() {
        return freeBuyType;
    }

    public void setFreeBuyType(int freeBuyType) {
        this.freeBuyType = freeBuyType;
    }



    public String getYj_money() {
        return yj_money;
    }

    public void setYj_money(String yj_money) {
        this.yj_money = yj_money;
    }

    private String yj_money;//佣金
    public int getIsYJin() {
        return isYJin;
    }

    public void setIsYJin(int isYJin) {
        this.isYJin = isYJin;
    }

    private int isYJin;
    private double lottery_kfMoney; //本轮客服抽奖金额
    private double last_lotteryMoney;  //上一轮客服抽奖金额
    private double kf_allMoney;    //客服抽奖总金额

    private int residual_num;

    private String all_money;//累计抽中金额
    private long expireTime;

    public String getShow_free_money() {
        return show_free_money;
    }

    public void setShow_free_money(String show_free_money) {
        this.show_free_money = show_free_money;
    }

    private String show_free_money;



    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }


    //真实中奖相关
    private double extract_money;//发放金额
    private int multiple;//真实抽奖金额放大多少倍
    private int day;//分几天发放


    public double getLottery_kfMoney() {
        return lottery_kfMoney;
    }

    public void setLottery_kfMoney(double lottery_kfMoney) {
        this.lottery_kfMoney = lottery_kfMoney;
    }

    public double getLast_lotteryMoney() {
        return last_lotteryMoney;
    }

    public void setLast_lotteryMoney(double last_lotteryMoney) {
        this.last_lotteryMoney = last_lotteryMoney;
    }

    public double getKf_allMoney() {
        return kf_allMoney;
    }

    public void setKf_allMoney(double kf_allMoney) {
        this.kf_allMoney = kf_allMoney;
    }


    public int getResidual_num() {
        return residual_num;
    }

    public void setResidual_num(int residual_num) {
        this.residual_num = residual_num;
    }


    public String getFree_url() {
        return free_url;
    }

    public void setFree_url(String free_url) {
        this.free_url = free_url;
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

    public double getRaffle_money() {
        return raffle_money;
    }

    public void setRaffle_money(double raffle_money) {
        this.raffle_money = raffle_money;
    }

    public String getAll_money() {
        return all_money;
    }

    public void setAll_money(String all_money) {
        this.all_money = all_money;
    }

    public double getExtract_money() {
        return extract_money;
    }

    public void setExtract_money(double extract_money) {
        this.extract_money = extract_money;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
