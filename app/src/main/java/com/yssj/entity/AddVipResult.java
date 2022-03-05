package com.yssj.entity;

public class AddVipResult {


    /**
     * v_code : V200420JEl3PBaC
     * data : {"reduce_extract":0,"unVipRaffleMoney":0,"diamondNum":0,"actual_price":0.5,"vip_type":4,"vip_name":"钻石会员","originalVipPrice":199,"favorablePrice":90,"trailNum":0}
     * actual_price : 0.5
     * message : 操作成功
     * status : 1
     */

    private String v_code;
    private DataBean data;
    private double actual_price;
    private String message;
    private String status;
    private String showBuySucMessage;


    public String getShowBuySucMessage() {
        return showBuySucMessage;
    }

    public void setShowBuySucMessage(String showBuySucMessage) {
        this.showBuySucMessage = showBuySucMessage;
    }


    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    private double discount;

    public String getV_code() {
        return v_code;
    }

    public void setV_code(String v_code) {
        this.v_code = v_code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public double getActual_price() {
        return actual_price;
    }

    public void setActual_price(double actual_price) {
        this.actual_price = actual_price;
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

    public static class DataBean {
        /**
         * reduce_extract : 0
         * unVipRaffleMoney : 0
         * diamondNum : 0
         * actual_price : 0.5
         * vip_type : 4
         * vip_name : 钻石会员
         * originalVipPrice : 199
         * favorablePrice : 90
         * trailNum : 0
         */

        private String reduce_extract;
        private String unVipRaffleMoney;
        private int diamondNum;
        private String actual_price;
        private int vip_type;
        private String vip_name;
        private String originalVipPrice;
        private String favorablePrice;
        private int trailNum;
        private String vip_price;
        private int popupUse;//0  首张以及第二张钻石  首张皇冠弹窗  1其他
        private String freeBuyPrice;



        public String getFreeBuyPrice() {
            return freeBuyPrice;
        }

        public void setFreeBuyPrice(String freeBuyPrice) {
            this.freeBuyPrice = freeBuyPrice;
        }



        public int getPopupUse() {
            return popupUse;
        }

        public void setPopupUse(int popupUse) {
            this.popupUse = popupUse;
        }



        public String getVip_price() {
            return vip_price;
        }

        public void setVip_price(String vip_price) {
            this.vip_price = vip_price;
        }


        public String getReduce_extract() {
            return reduce_extract;
        }

        public void setReduce_extract(String reduce_extract) {
            this.reduce_extract = reduce_extract;
        }

        public String getUnVipRaffleMoney() {
            return unVipRaffleMoney;
        }

        public void setUnVipRaffleMoney(String unVipRaffleMoney) {
            this.unVipRaffleMoney = unVipRaffleMoney;
        }

        public int getDiamondNum() {
            return diamondNum;
        }

        public void setDiamondNum(int diamondNum) {
            this.diamondNum = diamondNum;
        }

        public String getActual_price() {
            return actual_price;
        }

        public void setActual_price(String actual_price) {
            this.actual_price = actual_price;
        }

        public int getVip_type() {
            return vip_type;
        }

        public void setVip_type(int vip_type) {
            this.vip_type = vip_type;
        }

        public String getVip_name() {
            return vip_name;
        }

        public void setVip_name(String vip_name) {
            this.vip_name = vip_name;
        }

        public String getOriginalVipPrice() {
            return originalVipPrice;
        }

        public void setOriginalVipPrice(String originalVipPrice) {
            this.originalVipPrice = originalVipPrice;
        }

        public String getFavorablePrice() {
            return favorablePrice;
        }

        public void setFavorablePrice(String favorablePrice) {
            this.favorablePrice = favorablePrice;
        }

        public int getTrailNum() {
            return trailNum;
        }

        public void setTrailNum(int trailNum) {
            this.trailNum = trailNum;
        }
    }
}
