package com.yssj.entity;

import java.util.List;

public class GoodTuanData {
    /**
     * rnum : 1
     * userPic : ["https://wx.qlogo.cn/mmopen/vi_32/TRdduNGHmiaEU5OZ70DG6mzSofTtvic2aWaE6QTYrdrFe94krTP3d0Gmmvg6hiaAEdRuh0upYgJRYgU3D0CHSPojg/132","https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTItWK4gKVvic6GvmJdgh4wAHicoib7t0C8jkggnGYc3RRSdpqK4qCctywg71enS0grXiaiaWJAGVb9kx0Q/132","https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTItWK4gKVvic6GvmJdgh4wAHicoib7t0C8jkggnGYc3RRSdpqK4qCctywg71enS0grXiaiaWJAGVb9kx0Q/132"]
     * is_vip : 0
     * fight_userid : 945798
     * count : 4
     * time : 14975147
     * message : 操作成功
     * rollNum : 11
     * roll_code : 191228fJzI88f5
     * status : 1
     */

    private int rnum;
    private int is_vip;
    private int fight_userid;
    private int count;
    private int time;
    private String message;
    private int rollNum;
    private String roll_code;
    private String status;
    private List<String> userPic;

    public int getRnum() {
        return rnum;
    }

    public void setRnum(int rnum) {
        this.rnum = rnum;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public int getFight_userid() {
        return fight_userid;
    }

    public void setFight_userid(int fight_userid) {
        this.fight_userid = fight_userid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRollNum() {
        return rollNum;
    }

    public void setRollNum(int rollNum) {
        this.rollNum = rollNum;
    }

    public String getRoll_code() {
        return roll_code;
    }

    public void setRoll_code(String roll_code) {
        this.roll_code = roll_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getUserPic() {
        return userPic;
    }

    public void setUserPic(List<String> userPic) {
        this.userPic = userPic;
    }
}
