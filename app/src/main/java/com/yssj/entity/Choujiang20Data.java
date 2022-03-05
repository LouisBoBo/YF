package com.yssj.entity;

/**
 * Created by Administrator on 2020/5/29.
 */

public class Choujiang20Data {
    /**
     * data : {"is_finish":0,"reRoundCount":0}
     * message : 操作成功.
     * status : 1
     */

    private DataBean data;
    private String message;
    private String status;



    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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
         * is_finish : 0
         * reRoundCount : 0
         */

        private int is_finish;//20次有没有抽完 0抽完 1没有抽完
        private int reRoundCount;

        private int isVip;
        private int maxType;


        public int getMaxType() {
            return maxType;
        }

        public void setMaxType(int maxType) {
            this.maxType = maxType;
        }




        public int getIsVip() {
            return isVip;
        }

        public void setIsVip(int isVip) {
            this.isVip = isVip;
        }

        public int getIs_finish() {
            return is_finish;
        }

        public void setIs_finish(int is_finish) {
            this.is_finish = is_finish;
        }

        public int getReRoundCount() {
            return reRoundCount;
        }

        public void setReRoundCount(int reRoundCount) {
            this.reRoundCount = reRoundCount;
        }
    }
}
