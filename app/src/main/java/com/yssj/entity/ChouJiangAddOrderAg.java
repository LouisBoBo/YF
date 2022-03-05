package com.yssj.entity;

public class ChouJiangAddOrderAg {
    /**
     * vip_type : -2
     * tri : 1
     * message : 操作成功.
     * url : 2
     * status : 1
     */

    private int vip_type;
    private int tri;
    private String message;
    private int url;
    private String status;
    private String order_code;
    private String price;

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public int getVip_type() {
        return vip_type;
    }

    public void setVip_type(int vip_type) {
        this.vip_type = vip_type;
    }

    public int getTri() {
        return tri;
    }

    public void setTri(int tri) {
        this.tri = tri;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
