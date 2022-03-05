package com.yssj.entity;

/**
 * Created by Administrator on 2020/3/20 0020.
 */

public class LastPTdata {
    /**
     * data : {"status":"1"}
     * message : 操作成功
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
         * status : 1
         */

        private int status;
        private String roll_code;

        public String getRoll_code() {
            return roll_code;
        }

        public void setRoll_code(String roll_code) {
            this.roll_code = roll_code;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
