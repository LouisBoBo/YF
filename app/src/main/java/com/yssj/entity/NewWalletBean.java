package com.yssj.entity;

import java.util.List;

public class NewWalletBean {
    /**
     * status : 1
     * data : {"ucipv":12,"money":5,"plist":[{"money":"60","percentage":5},{"money":"50","percentage":10},{"money":"450","percentage":50}],"uplist":[{"money":60,"percentage":5}]}
     */

    private double status;
    private DataBean data;

    public double getStatus() {
        return status;
    }

    public void setStatus(double status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ucipv : 12
         * money : 5
         * plist : [{"money":"60","percentage":5},{"money":"50","percentage":10},{"money":"450","percentage":50}]
         * uplist : [{"money":60,"percentage":5}]
         */

        private double ucipv;
        private double money;
        private List<PlistBean> plist;
        private List<UplistBean> uplist;

        public double getUcipv() {
            return ucipv;
        }

        public void setUcipv(double ucipv) {
            this.ucipv = ucipv;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public List<PlistBean> getPlist() {
            return plist;
        }

        public void setPlist(List<PlistBean> plist) {
            this.plist = plist;
        }

        public List<UplistBean> getUplist() {
            return uplist;
        }

        public void setUplist(List<UplistBean> uplist) {
            this.uplist = uplist;
        }

        public static class PlistBean {
            /**
             * money : 60
             * percentage : 5
             */

            private String money;
            private double percentage;

            public double getCheck() {
                return check;
            }

            public void setCheck(double check) {
                this.check = check;
            }

            private double check;

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public double getPercentage() {
                return percentage;
            }

            public void setPercentage(double percentage) {
                this.percentage = percentage;
            }
        }

        public static class UplistBean {
            /**
             * money : 60
             * percentage : 5
             */

            private double money;
            private double percentage;

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public double getPercentage() {
                return percentage;
            }

            public void setPercentage(double percentage) {
                this.percentage = percentage;
            }
        }
    }
}
