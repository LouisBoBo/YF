package com.yssj.entity;

public class WithDrawPageData {
    /**
     * lastBname : “123”
     * extract : 89.6
     * balance : 102
     * firstBlood : true
     * minicill : 30
     * grade : 0
     * dm : {"min":"1","max":"100"}
     * freeze_balance : 0
     * kts : 30
     * message : 操作成功.
     * ex_free : 0
     * status : 1
     */

    private String lastBname;
    private double extract;
    private int balance;
    private boolean firstBlood;
    private double minicill;
    private int grade;
    private DmBean dm;
    private int freeze_balance;
    private String kts;
    private String message;
    private int ex_free;
    private String status;
    private int isCanTX;


    public int getIsCanTX() {
        return isCanTX;
    }

    public void setIsCanTX(int isCanTX) {
        this.isCanTX = isCanTX;
    }


    public String getLastBname() {
        return lastBname;
    }

    public void setLastBname(String lastBname) {
        this.lastBname = lastBname;
    }

    public double getExtract() {
        return extract;
    }

    public void setExtract(double extract) {
        this.extract = extract;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isFirstBlood() {
        return firstBlood;
    }

    public void setFirstBlood(boolean firstBlood) {
        this.firstBlood = firstBlood;
    }

    public double getMinicill() {
        return minicill;
    }

    public void setMinicill(double minicill) {
        this.minicill = minicill;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public DmBean getDm() {
        return dm;
    }

    public void setDm(DmBean dm) {
        this.dm = dm;
    }

    public int getFreeze_balance() {
        return freeze_balance;
    }

    public void setFreeze_balance(int freeze_balance) {
        this.freeze_balance = freeze_balance;
    }

    public String getKts() {
        return kts;
    }

    public void setKts(String kts) {
        this.kts = kts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getEx_free() {
        return ex_free;
    }

    public void setEx_free(int ex_free) {
        this.ex_free = ex_free;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DmBean {
        /**
         * min : 1
         * max : 100
         */

        private String min;
        private String max;

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }
    }
}
