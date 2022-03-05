package com.yssj.entity;

public class FriendsRewardCount {
    /**
     * data : {"ext_yet":90,"ext_num":17,"ext_money":82.9,"ext_now":0,"time":0}
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
         * ext_yet : 90.0
         * ext_num : 17
         * ext_money : 82.9
         * ext_now : 0.0
         * time : 0
         */

        private double ext_yet;
        private int ext_num;
        private double ext_money;
        private double ext_now;
        private long time;

        public int getHastips() {
            return hastips;
        }

        public void setHastips(int hastips) {
            this.hastips = hastips;
        }

        private int hastips;

        private int ext_time;

        public double getExt_yet() {
            return ext_yet;
        }

        public void setExt_yet(double ext_yet) {
            this.ext_yet = ext_yet;
        }

        public int getExt_num() {
            return ext_num;
        }

        public void setExt_num(int ext_num) {
            this.ext_num = ext_num;
        }

        public double getExt_money() {
            return ext_money;
        }

        public void setExt_money(double ext_money) {
            this.ext_money = ext_money;
        }

        public double getExt_now() {
            return ext_now;
        }

        public void setExt_now(double ext_now) {
            this.ext_now = ext_now;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getExt_time() {
            return ext_time;
        }

        public void setExt_time(int ext_time) {
            this.ext_time = ext_time;
        }
    }
}
