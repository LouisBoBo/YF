package com.yssj.entity;

public class BaseDataBean {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private String message;
    private int status;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    private int data;
}
