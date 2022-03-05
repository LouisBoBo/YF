package com.yssj.eventbus;

public class MessageEvent {

    private String message;
    private int eventBuyVipSucVipType;


    public String getVipDikou() {
        return vipDikou;
    }

    public void setVipDikou(String vipDikou) {
        this.vipDikou = vipDikou;
    }

    private String vipDikou;

    public  MessageEvent(){
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getEventBuyVipSucVipType() {
        return eventBuyVipSucVipType;
    }

    public void setEventBuyVipSucVipType(int eventBuyVipSucVipType) {
        this.eventBuyVipSucVipType = eventBuyVipSucVipType;
    }


}
