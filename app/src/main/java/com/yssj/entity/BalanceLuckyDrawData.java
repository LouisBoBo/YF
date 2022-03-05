package com.yssj.entity;

/**
 * Created by Administrator on 2020/8/13.
 */

public class BalanceLuckyDrawData {


    /**
     * t : 1
     * raffle : 4.88
     * message : 操作成功.
     * status : 1
     */

    private String t;
    private double raffle;
    private String message;
    private String status;
    private double sumExtract;


    public double getSumExtract() {
        return sumExtract;
    }

    public void setSumExtract(double sumExtract) {
        this.sumExtract = sumExtract;
    }


    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    private long expireTime;

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public double getRaffle() {
        return raffle;
    }

    public void setRaffle(double raffle) {
        this.raffle = raffle;
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
