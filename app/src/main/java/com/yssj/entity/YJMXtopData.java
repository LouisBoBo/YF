package com.yssj.entity;

public class YJMXtopData {
    /**
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
        public String getCount5Money() {
            return count5Money;
        }

        public void setCount5Money(String count5Money) {
            this.count5Money = count5Money;
        }

        public double getCleanMoney() {
            return cleanMoney;
        }

        public void setCleanMoney(double cleanMoney) {
            this.cleanMoney = cleanMoney;
        }

        public String getWithdrawMoney() {
            return withdrawMoney;
        }

        public void setWithdrawMoney(String withdrawMoney) {
            this.withdrawMoney = withdrawMoney;
        }

        private String count5Money;//我的佣金
        private double cleanMoney;//清除的佣金
        private String withdrawMoney;//已提现


    }
}
