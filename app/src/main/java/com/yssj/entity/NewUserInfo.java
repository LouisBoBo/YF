package com.yssj.entity;

public class NewUserInfo {
    /**
     * userinfo : {"user_id":945465,"nickname":"qingfeng😂 😂 😂 😂","gender":0,"add_date":"2019-09-27 18:00:39","is_del":0,"user_type":11,"pic":"http://thirdwx.qlogo.cn/mmopen/vi_32/lFPknwJcD8TuYqUGrlZORria5vdsoUMpIbIYLOWU5xiakquKpbTN0o2vOqIqibb9nibjUVOauwicQSxHhd4amCVy5jw/132","email_status":0,"hobby":"0","bg_pic":"userinfo/bg_pic/default.jpg","code_type":1,"type":68,"is_member":0,"sup_id":0,"wx_openid":"oOhAjtwd8dXVAWvVe-4hzu_ms3Z0","unionid":"oPQMKv4DhKGKUwevTF3ui94sVKsI","wxcx_openid":"olSYH0ZROUtBLAPptKjXsAzTfPnI","value":11,"merge":0,"user_mark":0,"virtual_type":68,"wx_sex":0,"v_ident":0}
     * status : 1
     */

    private UserinfoBean userinfo;
    private String status;

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class UserinfoBean {
        /**
         * user_id : 945465
         * nickname : qingfeng😂 😂 😂 😂
         * gender : 0
         * add_date : 2019-09-27 18:00:39
         * is_del : 0
         * user_type : 11
         * pic : http://thirdwx.qlogo.cn/mmopen/vi_32/lFPknwJcD8TuYqUGrlZORria5vdsoUMpIbIYLOWU5xiakquKpbTN0o2vOqIqibb9nibjUVOauwicQSxHhd4amCVy5jw/132
         * email_status : 0
         * hobby : 0
         * bg_pic : userinfo/bg_pic/default.jpg
         * code_type : 1
         * type : 68
         * is_member : 0
         * sup_id : 0
         * wx_openid : oOhAjtwd8dXVAWvVe-4hzu_ms3Z0
         * unionid : oPQMKv4DhKGKUwevTF3ui94sVKsI
         * wxcx_openid : olSYH0ZROUtBLAPptKjXsAzTfPnI
         * value : 11
         * merge : 0
         * user_mark : 0
         * virtual_type : 68
         * wx_sex : 0
         * v_ident : 0
         */
        private  String parent_id;
        private String user_id;
        private String nickname;
        private int gender;
        private String add_date;
        private int is_del;
        private int user_type;
        private String pic;
        private int email_status;
        private String hobby;
        private String bg_pic;
        private int code_type;
        private int type;
        private int is_member;
        private int sup_id;
        private String wx_openid;
        private String unionid;
        private String wxcx_openid;
        private String value;
        private String merge;
        private String user_mark;
        private String virtual_type;
        private String wx_sex;
        private String v_ident;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getAdd_date() {
            return add_date;
        }

        public void setAdd_date(String add_date) {
            this.add_date = add_date;
        }

        public int getIs_del() {
            return is_del;
        }

        public void setIs_del(int is_del) {
            this.is_del = is_del;
        }

        public int getUser_type() {
            return user_type;
        }

        public void setUser_type(int user_type) {
            this.user_type = user_type;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getEmail_status() {
            return email_status;
        }

        public void setEmail_status(int email_status) {
            this.email_status = email_status;
        }

        public String getHobby() {
            return hobby;
        }

        public void setHobby(String hobby) {
            this.hobby = hobby;
        }

        public String getBg_pic() {
            return bg_pic;
        }

        public void setBg_pic(String bg_pic) {
            this.bg_pic = bg_pic;
        }

        public int getCode_type() {
            return code_type;
        }

        public void setCode_type(int code_type) {
            this.code_type = code_type;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getIs_member() {
            return is_member;
        }

        public void setIs_member(int is_member) {
            this.is_member = is_member;
        }

        public int getSup_id() {
            return sup_id;
        }

        public void setSup_id(int sup_id) {
            this.sup_id = sup_id;
        }

        public String getWx_openid() {
            return wx_openid;
        }

        public void setWx_openid(String wx_openid) {
            this.wx_openid = wx_openid;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getWxcx_openid() {
            return wxcx_openid;
        }

        public void setWxcx_openid(String wxcx_openid) {
            this.wxcx_openid = wxcx_openid;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getMerge() {
            return merge;
        }

        public void setMerge(String merge) {
            this.merge = merge;
        }

        public String getUser_mark() {
            return user_mark;
        }

        public void setUser_mark(String user_mark) {
            this.user_mark = user_mark;
        }

        public String getVirtual_type() {
            return virtual_type;
        }

        public void setVirtual_type(String virtual_type) {
            this.virtual_type = virtual_type;
        }

        public String getWx_sex() {
            return wx_sex;
        }

        public void setWx_sex(String wx_sex) {
            this.wx_sex = wx_sex;
        }

        public String getV_ident() {
            return v_ident;
        }

        public void setV_ident(String v_ident) {
            this.v_ident = v_ident;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }
    }
}
