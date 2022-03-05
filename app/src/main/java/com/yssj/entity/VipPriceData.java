package com.yssj.entity;

import java.io.Serializable;

public class VipPriceData implements Serializable {


    /**
     * actual_price : 0.2
     * isSupply : 0
     * fixMoney : 90
     * reMoney : 0.1
     * message : 操作成功.
     * status : 1
     */

    private double actual_price;
    private int isSupply;
    private int fixMoney;
    private double reMoney;
    private String message;
    private String status;
    private String one_price;
    private int trialNum;
    private String content1;
    private String original_price;


    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }



    public int getTrialNum() {
        return trialNum;
    }

    public void setTrialNum(int trialNum) {
        this.trialNum = trialNum;
    }


    public String getOne_price() {
        return one_price;
    }

    public void setOne_price(String one_price) {
        this.one_price = one_price;
    }

    public double getActual_price() {
        return actual_price;
    }

    public void setActual_price(double actual_price) {
        this.actual_price = actual_price;
    }

    public int getIsSupply() {
        return isSupply;
    }

    public void setIsSupply(int isSupply) {
        this.isSupply = isSupply;
    }

    public int getFixMoney() {
        return fixMoney;
    }

    public void setFixMoney(int fixMoney) {
        this.fixMoney = fixMoney;
    }

    public double getReMoney() {
        return reMoney;
    }

    public void setReMoney(double reMoney) {
        this.reMoney = reMoney;
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
