package com.yssj.entity;

import java.util.List;

public class FriendsRewardList {
    /**
     * data : [{"money":"30.0","nickName":"飞飞","pic":"/userinfo/sys_head_pic/eda4fca1b5ba35568a988a2fb095ce89.jpg","time":"2019-09-25"},{"nickName":"梦里花落知多少","pic":"/userinfo/sys_head_pic/72da0c7dcb5de7689ce232fc2eb22b9f.jpg","time":"2019-09-25"},{"nickName":"哪够i","pic":"/userinfo/sys_head_pic/70016802a00f86da9c8aa1dc3350af4f.jpg","time":"2019-09-25"},{"nickName":"把幸福晒得高调。","pic":"/userinfo/sys_head_pic/aa8247701644a1a11c39b1a2e53293fa.jpg","time":"2019-09-25"},{"money":"30.0","nickName":"爱不随人愿","pic":"/userinfo/sys_head_pic/d27c7c537fd0ecd1dc76e34974eee79f.jpg","time":"2019-09-25"},{"nickName":"我已绝版","pic":"/userinfo/sys_head_pic/24b5e219b9ecb8b31094be114187c7be.jpg","time":"2019-09-25"},{"nickName":"人生","pic":"/userinfo/sys_head_pic/1e5bd2ca3ec029cbacc40ee5379fd2a6.jpg","time":"2019-09-25"}]
     * message : 操作成功.
     * status : 1
     */

    private String message;
    private String status;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * money : 30.0
         * nickName : 飞飞
         * pic : /userinfo/sys_head_pic/eda4fca1b5ba35568a988a2fb095ce89.jpg
         * time : 2019-09-25
         */

        private String money;
        private String nickName;
        private String pic;
        private String time;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
