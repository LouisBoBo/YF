package com.yssj.entity;

/**
 * Created by Administrator on 2020/3/30 0030.
 */

public class ShouSuChouJiangCountData {
    public int getFirstGroup() {
        return firstGroup;
    }

    public void setFirstGroup(int firstGroup) {
        this.firstGroup = firstGroup;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRemainder() {
        return remainder;
    }

    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int  firstGroup;
   private String message;
   private int remainder;
   private int status;

}
