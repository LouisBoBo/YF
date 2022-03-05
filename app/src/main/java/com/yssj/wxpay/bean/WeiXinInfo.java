package com.yssj.wxpay.bean;

import java.util.List;

/**
 */
public class WeiXinInfo {

    /**
     * openid : oOhAjtwd8dXVAWvVe-4hzu_ms3Z0
     * nickname : qingfeng
     * sex : 2
     * language : zh_CN
     * city :
     * province :
     * country :
     * headimgurl : http://thirdwx.qlogo.cn/mmopen/vi_32/lFPknwJcD8TuYqUGrlZORria5vdsoUMpIbIYLOWU5xiakquKpbTN0o2vOqIqibb9nibjUVOauwicQSxHhd4amCVy5jw/132
     * privilege : []
     * unionid : oPQMKv4DhKGKUwevTF3ui94sVKsI
     */

    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String unionid;
    private List<?> privilege;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public List<?> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<?> privilege) {
        this.privilege = privilege;
    }
}
