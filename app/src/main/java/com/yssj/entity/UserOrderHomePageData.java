package com.yssj.entity;

/**
 * Created by Administrator on 2020/4/30.
 */

public class UserOrderHomePageData {
    /**
     * nRaffle_Money : 27.2
     * count : 0
     * message : 操作成功
     * status : 1
     */

    private double nRaffle_Money;
    private int count;
    private String message;
    private String status;

    public double getNRaffle_Money() {
        return nRaffle_Money;
    }

    public void setNRaffle_Money(double nRaffle_Money) {
        this.nRaffle_Money = nRaffle_Money;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
