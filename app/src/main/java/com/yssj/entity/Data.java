/**
 * Copyright 2018 bejson.com
 */
package com.yssj.entity;

/**
 * Auto-generated: 2018-03-29 12:38:28
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Data {

    private String wxcx_status;
    private String app_value;
    private String wxcx_value;
    private String app_status;
    private String app_every;//每抽一次需要的金额
    private String app_zero;//新用户0元次数（大于等于1说明是可以0元参与）

    public String getApp_every() {
        return app_every;
    }

    public void setApp_every(String app_every) {
        this.app_every = app_every;
    }


    public void setWxcx_status(String wxcx_status) {
        this.wxcx_status = wxcx_status;
    }

    public String getWxcx_status() {
        return wxcx_status;
    }

    public void setApp_value(String app_value) {
        this.app_value = app_value;
    }

    public String getApp_value() {
        return app_value;
    }

    public void setWxcx_value(String wxcx_value) {
        this.wxcx_value = wxcx_value;
    }

    public String getWxcx_value() {
        return wxcx_value;
    }

    public void setApp_status(String app_status) {
        this.app_status = app_status;
    }

    public String getApp_status() {
        return app_status;
    }

    @Override
    public String toString() {
        return "Data{" +
                "wxcx_status='" + wxcx_status + '\'' +
                ", app_value='" + app_value + '\'' +
                ", wxcx_value='" + wxcx_value + '\'' +
                ", app_status='" + app_status + '\'' +
                '}';
    }
}