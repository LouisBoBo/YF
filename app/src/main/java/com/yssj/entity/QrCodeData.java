package com.yssj.entity;

public class QrCodeData {
    /**
     * imgUrl : /user/QRCode/createQRCode_1563297.jpg
     * message : 操作成功.
     * status : 1
     */

    private String imgUrl;
    private String message;
    private String status;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
