package com.yssj.entity;

public class SignCountData {
    /**
     * topWx : 1
     * dayCount : 0
     * whetherTask : 1
     * roll : 0
     * orderStatus : 1
     * followWx : 1
     * shareCount : null
     * bCount : 3
     * offered : 1
     * fighStatus : 0
     * withdrawal_money : 27.2
     * todayReturnMoney : null
     * count_money : 0
     * cumWitMoney : null
     * isMonday : false
     * yc_task : 0
     * orderCount : 2
     * cumReturnMoney : null
     * today_money : 3
     * message : 操作成功.
     * unrecorded_today_money : 0
     * isVip : 0
     * shareMoneyCount : 0
     * nRaffle_status : 0
     * popup : null
     * current_date : newbie01
     * extract : 0
     * isGratis : true
     * iCount : 0
     * cCount : 0
     * desing : 0
     * today_ref : 0
     * status : 1
     */


    private int current_status_data; // -1(一般不会有) 0 1 -----当前任务是否全部做完
    private int allNumber; // 全部次数-------------------------剩余可以抽奖的次数
    private int hasCashCard; // 0 1-----------------------------是否买过提现卡
    private int is_fast_raffle; // 0 1-----是否抽完过
    private int clockInToday;//当天是否已经打卡
    private int hasDiamondOrVip;//是否 买了钻石卡或者办了会员
    private int maxType;
    private String raffleFixedMoney;
    private String today_money2;


    public String getToday_money2() {
        return today_money2;
    }

    public void setToday_money2(String today_money2) {
        this.today_money2 = today_money2;
    }



    public String getRaffleFixedMoney() {
        return raffleFixedMoney;
    }

    public void setRaffleFixedMoney(String raffleFixedMoney) {
        this.raffleFixedMoney = raffleFixedMoney;
    }



    public int getMaxType() {
        return maxType;
    }

    public void setMaxType(int maxType) {
        this.maxType = maxType;
    }


    public String getSuccMoney() {
        return succMoney;
    }

    public void setSuccMoney(String succMoney) {
        this.succMoney = succMoney;
    }

    private String succMoney;

    public int getHasTrailNum() {
        return hasTrailNum;
    }

    public void setHasTrailNum(int hasTrailNum) {
        this.hasTrailNum = hasTrailNum;
    }

    private int hasTrailNum;

    public String getUnVipRaffleMoney() {
        return unVipRaffleMoney;
    }

    public void setUnVipRaffleMoney(String unVipRaffleMoney) {
        this.unVipRaffleMoney = unVipRaffleMoney;
    }

    private String unVipRaffleMoney;


    public int getHasDiamondOrVip() {
        return hasDiamondOrVip;
    }

    public void setHasDiamondOrVip(int hasDiamondOrVip) {
        this.hasDiamondOrVip = hasDiamondOrVip;
    }


    public int getClockInToday() {
        return clockInToday;
    }

    public void setClockInToday(int clockInToday) {
        this.clockInToday = clockInToday;
    }


    private String topWx = "0";
    private String dayCount = "0";
    private String whetherTask = "0";
    private String roll = "0";
    private String orderStatus = "0";
    private String followWx = "0";
    private String shareCount = "0";
    private String bCount = "0";
    private String offered = "0";
    private String fighStatus = "0";
    private String withdrawal_money = "0";
    private String todayReturnMoney = "0";
    private String count_money = "0";
    private String cumWitMoney = "0";
    private String isMonday = "0";
    private String yc_task = "0";
    private String orderCount = "0";
    private String cumReturnMoney = "0";
    private String today_money = "0";
    private String message = "0";
    private String unrecorded_today_money = "0";
    private int isVip = 0;
    private String shareMoneyCount = "0";
    private String nRaffle_status = "0";
    private String popup = "0";
    private String current_date = "0";
    private String extract = "0";


    private String isGratis = "0";
    private String iCount = "0";
    private String cCount = "0";
    private String desing = "0";
    private String today_ref = "0";
    private String status = "0";
    private String lotterynumber = "0";
    private String point_status = "0";
    private String bro_count = "0";
    private String fans_count = "0";

    public int getCurrent_status_data() {
        return current_status_data;
    }

    public void setCurrent_status_data(int current_status_data) {
        this.current_status_data = current_status_data;
    }

    public int getAllNumber() {
        return allNumber;
    }

    public void setAllNumber(int allNumber) {
        this.allNumber = allNumber;
    }

    public int getHasCashCard() {
        return hasCashCard;
    }

    public void setHasCashCard(int hasCashCard) {
        this.hasCashCard = hasCashCard;
    }

    public int getIs_fast_raffle() {
        return is_fast_raffle;
    }

    public void setIs_fast_raffle(int is_fast_raffle) {
        this.is_fast_raffle = is_fast_raffle;
    }

    public String getbCount() {
        return bCount;
    }

    public void setbCount(String bCount) {
        this.bCount = bCount;
    }

    public String getnRaffle_status() {
        return nRaffle_status;
    }

    public void setnRaffle_status(String nRaffle_status) {
        this.nRaffle_status = nRaffle_status;
    }

    public String getiCount() {
        return iCount;
    }

    public void setiCount(String iCount) {
        this.iCount = iCount;
    }

    public String getcCount() {
        return cCount;
    }

    public void setcCount(String cCount) {
        this.cCount = cCount;
    }


    public String getIsGratis() {
        return isGratis;
    }

    public void setIsGratis(String isGratis) {
        this.isGratis = isGratis;
    }

    public String getFans_count() {
        return fans_count;
    }

    public void setFans_count(String fans_count) {
        this.fans_count = fans_count;
    }


    public String getBro_count() {
        return bro_count;
    }

    public void setBro_count(String bro_count) {
        this.bro_count = bro_count;
    }


    public String getLotterynumber() {
        return lotterynumber;
    }

    public void setLotterynumber(String lotterynumber) {
        this.lotterynumber = lotterynumber;
    }


    public String getPoint_status() {
        return point_status;
    }

    public void setPoint_status(String point_status) {
        this.point_status = point_status;
    }


    public String getTopWx() {
        return topWx;
    }

    public void setTopWx(String topWx) {
        this.topWx = topWx;
    }

    public String getDayCount() {
        return dayCount;
    }

    public void setDayCount(String dayCount) {
        this.dayCount = dayCount;
    }

    public String getWhetherTask() {
        return whetherTask;
    }

    public void setWhetherTask(String whetherTask) {
        this.whetherTask = whetherTask;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getFollowWx() {
        return followWx;
    }

    public void setFollowWx(String followWx) {
        this.followWx = followWx;
    }

    public String getShareCount() {
        return shareCount;
    }

    public void setShareCount(String shareCount) {
        this.shareCount = shareCount;
    }

    public String getBCount() {
        return bCount;
    }

    public void setBCount(String bCount) {
        this.bCount = bCount;
    }

    public String getOffered() {
        return offered;
    }

    public void setOffered(String offered) {
        this.offered = offered;
    }

    public String getFighStatus() {
        return fighStatus;
    }

    public void setFighStatus(String fighStatus) {
        this.fighStatus = fighStatus;
    }

    public String getWithdrawal_money() {
        return withdrawal_money;
    }

    public void setWithdrawal_money(String withdrawal_money) {
        this.withdrawal_money = withdrawal_money;
    }

    public String getTodayReturnMoney() {
        return todayReturnMoney;
    }

    public void setTodayReturnMoney(String todayReturnMoney) {
        this.todayReturnMoney = todayReturnMoney;
    }

    public String getCount_money() {
        return count_money;
    }

    public void setCount_money(String count_money) {
        this.count_money = count_money;
    }

    public String getCumWitMoney() {
        return cumWitMoney;
    }

    public void setCumWitMoney(String cumWitMoney) {
        this.cumWitMoney = cumWitMoney;
    }

    public String getIsMonday() {
        return isMonday;
    }

    public void setIsMonday(String isMonday) {
        this.isMonday = isMonday;
    }

    public String getYc_task() {
        return yc_task;
    }

    public void setYc_task(String yc_task) {
        this.yc_task = yc_task;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getCumReturnMoney() {
        return cumReturnMoney;
    }

    public void setCumReturnMoney(String cumReturnMoney) {
        this.cumReturnMoney = cumReturnMoney;
    }

    public String getToday_money() {
        return today_money;
    }

    public void setToday_money(String today_money) {
        this.today_money = today_money;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUnrecorded_today_money() {
        return unrecorded_today_money;
    }

    public void setUnrecorded_today_money(String unrecorded_today_money) {
        this.unrecorded_today_money = unrecorded_today_money;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public String getShareMoneyCount() {
        return shareMoneyCount;
    }

    public void setShareMoneyCount(String shareMoneyCount) {
        this.shareMoneyCount = shareMoneyCount;
    }

    public String getNRaffle_status() {
        return nRaffle_status;
    }

    public void setNRaffle_status(String nRaffle_status) {
        this.nRaffle_status = nRaffle_status;
    }

    public String getPopup() {
        return popup;
    }

    public void setPopup(String popup) {
        this.popup = popup;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

    public String getExtract() {
        return extract;
    }

    public void setExtract(String extract) {
        this.extract = extract;
    }


    public String getICount() {
        return iCount;
    }

    public void setICount(String iCount) {
        this.iCount = iCount;
    }

    public String getCCount() {
        return cCount;
    }

    public void setCCount(String cCount) {
        this.cCount = cCount;
    }

    public String getDesing() {
        return desing;
    }

    public void setDesing(String desing) {
        this.desing = desing;
    }

    public String getToday_ref() {
        return today_ref;
    }

    public void setToday_ref(String today_ref) {
        this.today_ref = today_ref;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
