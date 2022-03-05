package com.yssj.entity;

public class DaoJuKaUpZuanshiData {


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getReMoney() {
        return reMoney;
    }

    public void setReMoney(double reMoney) {
        this.reMoney = reMoney;
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

    private String message;
    private double reMoney;
    private String status;
    private double raffle_money;

    public int getFreeOrder() {
        return freeOrder;
    }

    public void setFreeOrder(int freeOrder) {
        this.freeOrder = freeOrder;
    }

    private int freeOrder;
}
