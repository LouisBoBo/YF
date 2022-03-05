package com.yssj.entity;

/**
 * Created by Administrator on 2020/6/3.
 */

public class AppUpdateBean {
    /**
     * msg : 哈哈
     * version_no : v3.7.1
     * path : 20180401FganmIa8.apk
     * is_update : 0
     * message : 操作成功
     * status : 1
     */

    private String msg;
    private String version_no;
    private String path;
    private String is_update;//是否是强更
    private String message;
    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIs_update() {
        return is_update;
    }

    public void setIs_update(String is_update) {
        this.is_update = is_update;
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
