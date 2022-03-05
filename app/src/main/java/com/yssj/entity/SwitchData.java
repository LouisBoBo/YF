package com.yssj.entity;

/**
 * Created by Administrator on 2020/6/19.
 */

public class SwitchData {
    /**
     * data : {"homePage3toPage":"1","trial_hidden_switch":"1","miniappDisplayOrHideBrand":"1","homePage3ElasticFrame":"0","cashOnDelivery":"1","noMemberHomePage3ElasticFrame":"1","special_reg_out_page":"0","homePage3FirstTime":"5","special_reg_out_reward":"0","homePage2to3":"1","must_bind_phone_channel":"6998,6899,6823"}
     * status : 1
     */

    private DataBean data;
    private String status;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * homePage3toPage : 1
         * trial_hidden_switch : 1
         * miniappDisplayOrHideBrand : 1
         * homePage3ElasticFrame : 0
         * cashOnDelivery : 1
         * noMemberHomePage3ElasticFrame : 1
         * special_reg_out_page : 0
         * homePage3FirstTime : 5
         * special_reg_out_reward : 0
         * homePage2to3 : 1
         * must_bind_phone_channel : 6998,6899,6823
         */

        private String homePage3toPage;
        private int trial_hidden_switch;//提现卡的隐藏显示0 隐藏  1显示
        private String miniappDisplayOrHideBrand;
        private String homePage3ElasticFrame;
        private String cashOnDelivery;
        private String noMemberHomePage3ElasticFrame;
        private String special_reg_out_page;
        private String homePage3FirstTime;
        private String special_reg_out_reward;
        private String homePage2to3;
        private String must_bind_phone_channel;
        private int must_risk_management_channel;//0 不风控  1 风控
        private int new_raffle_skipSwitch; //新用户抽完赠送的20次后是否引导提现卡是否跳到赚钱  0 跳转赚钱任务     1 正常 跳转提现卡
        private int config_popularize;//是否要验证剪切板 0不需要
        private int app_landing_page_channel;//启动后的落地页（1赚钱）
        private int isLoginToSign;
        private int isLoginToWithdrawal;
        private int isLoginToVip;
        private int reviewers_channel;//过滤审核位置开关  （必须要定位权限，后才能去登录）
        private int isUserReviewers;//需要定位单不需要定位结果，登录后固定是审核员


        public int getIsUserReviewers() {
            return isUserReviewers;
        }

        public void setIsUserReviewers(int isUserReviewers) {
            this.isUserReviewers = isUserReviewers;
        }



        public int getReviewers_channel() {
            return reviewers_channel;
        }

        public void setReviewers_channel(int reviewers_channel) {
            this.reviewers_channel = reviewers_channel;
        }



        public int getIsLoginToWithdrawal() {
            return isLoginToWithdrawal;
        }

        public void setIsLoginToWithdrawal(int isLoginToWithdrawal) {
            this.isLoginToWithdrawal = isLoginToWithdrawal;
        }

        public int getIsLoginToVip() {
            return isLoginToVip;
        }

        public void setIsLoginToVip(int isLoginToVip) {
            this.isLoginToVip = isLoginToVip;
        }



        public int getIsLoginToSign() {
            return isLoginToSign;
        }

        public void setIsLoginToSign(int isLoginToSign) {
            this.isLoginToSign = isLoginToSign;
        }




        public int getApp_landing_page_channel() {
            return app_landing_page_channel;
        }

        public void setApp_landing_page_channel(int app_landing_page_channel) {
            this.app_landing_page_channel = app_landing_page_channel;
        }





        public int getConfig_popularize() {
            return config_popularize;
        }

        public void setConfig_popularize(int config_popularize) {
            this.config_popularize = config_popularize;
        }

        public int getNew_raffle_skipSwitch() {
            return new_raffle_skipSwitch;
        }

        public void setNew_raffle_skipSwitch(int new_raffle_skipSwitch) {
            this.new_raffle_skipSwitch = new_raffle_skipSwitch;
        }




        public int getMust_risk_management_channel() {
            return must_risk_management_channel;
        }

        public void setMust_risk_management_channel(int must_risk_management_channel) {
            this.must_risk_management_channel = must_risk_management_channel;
        }

        public String getHomePage3toPage() {
            return homePage3toPage;
        }

        public void setHomePage3toPage(String homePage3toPage) {
            this.homePage3toPage = homePage3toPage;
        }

        public int getTrial_hidden_switch() {
            return trial_hidden_switch;
        }

        public void setTrial_hidden_switch(int trial_hidden_switch) {
            this.trial_hidden_switch = trial_hidden_switch;
        }

        public String getMiniappDisplayOrHideBrand() {
            return miniappDisplayOrHideBrand;
        }

        public void setMiniappDisplayOrHideBrand(String miniappDisplayOrHideBrand) {
            this.miniappDisplayOrHideBrand = miniappDisplayOrHideBrand;
        }

        public String getHomePage3ElasticFrame() {
            return homePage3ElasticFrame;
        }

        public void setHomePage3ElasticFrame(String homePage3ElasticFrame) {
            this.homePage3ElasticFrame = homePage3ElasticFrame;
        }

        public String getCashOnDelivery() {
            return cashOnDelivery;
        }

        public void setCashOnDelivery(String cashOnDelivery) {
            this.cashOnDelivery = cashOnDelivery;
        }

        public String getNoMemberHomePage3ElasticFrame() {
            return noMemberHomePage3ElasticFrame;
        }

        public void setNoMemberHomePage3ElasticFrame(String noMemberHomePage3ElasticFrame) {
            this.noMemberHomePage3ElasticFrame = noMemberHomePage3ElasticFrame;
        }

        public String getSpecial_reg_out_page() {
            return special_reg_out_page;
        }

        public void setSpecial_reg_out_page(String special_reg_out_page) {
            this.special_reg_out_page = special_reg_out_page;
        }

        public String getHomePage3FirstTime() {
            return homePage3FirstTime;
        }

        public void setHomePage3FirstTime(String homePage3FirstTime) {
            this.homePage3FirstTime = homePage3FirstTime;
        }

        public String getSpecial_reg_out_reward() {
            return special_reg_out_reward;
        }

        public void setSpecial_reg_out_reward(String special_reg_out_reward) {
            this.special_reg_out_reward = special_reg_out_reward;
        }

        public String getHomePage2to3() {
            return homePage2to3;
        }

        public void setHomePage2to3(String homePage2to3) {
            this.homePage2to3 = homePage2to3;
        }

        public String getMust_bind_phone_channel() {
            return must_bind_phone_channel;
        }

        public void setMust_bind_phone_channel(String must_bind_phone_channel) {
            this.must_bind_phone_channel = must_bind_phone_channel;
        }
    }
}
