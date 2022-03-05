package com.yssj.entity;

import java.util.List;

public class NewPTcountData {
    /**
     * data : {"free_num":0,"rnum":2,"userPicData":["https://wx.qlogo.cn/mmopen/vi_32/TRdduNGHmiaEU5OZ70DG6mzSofTtvic2aWaE6QTYrdrFe94krTP3d0Gmmvg6hiaAEdRuh0upYgJRYgU3D0CHSPojg/132","https://wx.qlogo.cn/mmopen/vi_32/Rl2pxU6dm5v9jZZNQHv4ryfhqZhqM6TXH5KsHUiaOOGtNd9uTtGwAKfIO3Eun9CDic6n1AibuSUiaiaC7H5sxfNiaP1Q/132"],"fight_userid":945798,"count":4,"isNew":0,"validMin":17038002,"order":{"id":133357,"order_code":"191226owdJKxIU","order_name":"通勤圆领条纹长袖螺纹针织衫","order_price":4.2,"remain_money":4.2,"pay_money":4.2,"user_id":945798,"user_name":"qingfeng���� ���� ���� ����","shop_num":1,"order_pic":"9ZS1IP9u_600_900.jpg","add_time":1577346045000,"change":0,"status":11,"store_code":"945798","last_time":1577432446000,"bak":"hGES1941069374","free":0,"address":" 河北省唐山市丰南区111","consignee":"111","phone":"13695875877","postcode":"","is_del":0,"supp_id":2230,"supp_money":0,"kickBack":0,"two_kickback":0,"three_kickback":0,"four_kickback":0,"is_buy":0,"pay_from":0,"coupon_id":"0","coupon_price":0,"c_merge":1,"c_token":"191226C9Xb1pbz","is_kick":0,"integral_num":0,"remain_integral_num":0,"terrace_type":1,"shop_from":11,"postage":0,"pay_status":0,"user_member":1,"zero_form":0,"channel":1,"voucher_money":0,"issue_code":"0","participation_code":"0","issue_status":0,"issue_endtime":0,"dpzkm":0,"orderShops":[],"orderShopList":[],"twofoldness":1,"tfn_money":0,"tfn_money_ago":0,"is_wx":0,"wx_price":0,"is_bro":1,"user_mark":0,"is_virtual":0,"is_gold":0,"is_gold_coupon":0,"is_free":0,"is_roll":1,"roll_code":"191226k9QqVdUL","raffle":0,"integral_convert":1,"sign":"","channel_":0,"supp_order":"0","tr_num":0,"one_from":1,"one_deductible":0,"is_draw":0,"whether_prize":1,"first_group":0,"page4_shop":0,"vip_roll_type":0,"new_free":0}}
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
        public int getIsVip() {
            return isVip;
        }

        public void setIsVip(int isVip) {
            this.isVip = isVip;
        }

        /**
         * free_num : 0
         * rnum : 2
         * userPicData : ["https://wx.qlogo.cn/mmopen/vi_32/TRdduNGHmiaEU5OZ70DG6mzSofTtvic2aWaE6QTYrdrFe94krTP3d0Gmmvg6hiaAEdRuh0upYgJRYgU3D0CHSPojg/132","https://wx.qlogo.cn/mmopen/vi_32/Rl2pxU6dm5v9jZZNQHv4ryfhqZhqM6TXH5KsHUiaOOGtNd9uTtGwAKfIO3Eun9CDic6n1AibuSUiaiaC7H5sxfNiaP1Q/132"]
         * fight_userid : 945798
         * count : 4
         * isNew : 0
         * validMin : 17038002
         * order : {"id":133357,"order_code":"191226owdJKxIU","order_name":"通勤圆领条纹长袖螺纹针织衫","order_price":4.2,"remain_money":4.2,"pay_money":4.2,"user_id":945798,"user_name":"qingfeng���� ���� ���� ����","shop_num":1,"order_pic":"9ZS1IP9u_600_900.jpg","add_time":1577346045000,"change":0,"status":11,"store_code":"945798","last_time":1577432446000,"bak":"hGES1941069374","free":0,"address":" 河北省唐山市丰南区111","consignee":"111","phone":"13695875877","postcode":"","is_del":0,"supp_id":2230,"supp_money":0,"kickBack":0,"two_kickback":0,"three_kickback":0,"four_kickback":0,"is_buy":0,"pay_from":0,"coupon_id":"0","coupon_price":0,"c_merge":1,"c_token":"191226C9Xb1pbz","is_kick":0,"integral_num":0,"remain_integral_num":0,"terrace_type":1,"shop_from":11,"postage":0,"pay_status":0,"user_member":1,"zero_form":0,"channel":1,"voucher_money":0,"issue_code":"0","participation_code":"0","issue_status":0,"issue_endtime":0,"dpzkm":0,"orderShops":[],"orderShopList":[],"twofoldness":1,"tfn_money":0,"tfn_money_ago":0,"is_wx":0,"wx_price":0,"is_bro":1,"user_mark":0,"is_virtual":0,"is_gold":0,"is_gold_coupon":0,"is_free":0,"is_roll":1,"roll_code":"191226k9QqVdUL","raffle":0,"integral_convert":1,"sign":"","channel_":0,"supp_order":"0","tr_num":0,"one_from":1,"one_deductible":0,"is_draw":0,"whether_prize":1,"first_group":0,"page4_shop":0,"vip_roll_type":0,"new_free":0}
         */
        private int isVip;

        public int getMaxType() {
            return maxType;
        }

        public void setMaxType(int maxType) {
            this.maxType = maxType;
        }

        private int maxType;
        private int free_num;
        private int rnum;
        private int fight_userid;
        private int count;
        private int isNew;
        private int validMin;
        private OrderBean order;
        private List<String> userPicData;

        public int getFree_num() {
            return free_num;
        }

        public void setFree_num(int free_num) {
            this.free_num = free_num;
        }

        public int getRnum() {
            return rnum;
        }

        public void setRnum(int rnum) {
            this.rnum = rnum;
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

        public int getIsNew() {
            return isNew;
        }

        public void setIsNew(int isNew) {
            this.isNew = isNew;
        }

        public int getValidMin() {
            return validMin;
        }

        public void setValidMin(int validMin) {
            this.validMin = validMin;
        }

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public List<String> getUserPicData() {
            return userPicData;
        }

        public void setUserPicData(List<String> userPicData) {
            this.userPicData = userPicData;
        }

        public static class OrderBean {
            /**
             * id : 133357
             * order_code : 191226owdJKxIU
             * order_name : 通勤圆领条纹长袖螺纹针织衫
             * order_price : 4.2
             * remain_money : 4.2
             * pay_money : 4.2
             * user_id : 945798
             * user_name : qingfeng���� ���� ���� ����
             * shop_num : 1
             * order_pic : 9ZS1IP9u_600_900.jpg
             * add_time : 1577346045000
             * change : 0
             * status : 11
             * store_code : 945798
             * last_time : 1577432446000
             * bak : hGES1941069374
             * free : 0
             * address :  河北省唐山市丰南区111
             * consignee : 111
             * phone : 13695875877
             * postcode :
             * is_del : 0
             * supp_id : 2230
             * supp_money : 0
             * kickBack : 0
             * two_kickback : 0
             * three_kickback : 0
             * four_kickback : 0
             * is_buy : 0
             * pay_from : 0
             * coupon_id : 0
             * coupon_price : 0
             * c_merge : 1
             * c_token : 191226C9Xb1pbz
             * is_kick : 0
             * integral_num : 0
             * remain_integral_num : 0
             * terrace_type : 1
             * shop_from : 11
             * postage : 0
             * pay_status : 0
             * user_member : 1
             * zero_form : 0
             * channel : 1
             * voucher_money : 0
             * issue_code : 0
             * participation_code : 0
             * issue_status : 0
             * issue_endtime : 0
             * dpzkm : 0
             * orderShops : []
             * orderShopList : []
             * twofoldness : 1
             * tfn_money : 0
             * tfn_money_ago : 0
             * is_wx : 0
             * wx_price : 0
             * is_bro : 1
             * user_mark : 0
             * is_virtual : 0
             * is_gold : 0
             * is_gold_coupon : 0
             * is_free : 0
             * is_roll : 1
             * roll_code : 191226k9QqVdUL
             * raffle : 0
             * integral_convert : 1
             * sign :
             * channel_ : 0
             * supp_order : 0
             * tr_num : 0
             * one_from : 1
             * one_deductible : 0
             * is_draw : 0
             * whether_prize : 1
             * first_group : 0
             * page4_shop : 0
             * vip_roll_type : 0
             * new_free : 0
             */

            private int id;
            private String order_code;
            private String order_name;
            private double order_price;
            private double remain_money;
            private double pay_money;
            private int user_id;
            private String user_name;
            private int shop_num;
            private String order_pic;
            private long add_time;
            private int change;
            private int status;
            private String store_code;
            private long last_time;
            private String bak;
            private int free;
            private String address;
            private String consignee;
            private String phone;
            private String postcode;
            private int is_del;
            private int supp_id;
            private int supp_money;
            private int kickBack;
            private int two_kickback;
            private int three_kickback;
            private int four_kickback;
            private int is_buy;
            private int pay_from;
            private String coupon_id;
            private int coupon_price;
            private int c_merge;
            private String c_token;
            private int is_kick;
            private int integral_num;
            private int remain_integral_num;
            private int terrace_type;
            private int shop_from;
            private int postage;
            private int pay_status;
            private int user_member;
            private int zero_form;
            private int channel;
            private int voucher_money;
            private String issue_code;
            private String participation_code;
            private int issue_status;
            private int issue_endtime;
            private int dpzkm;
            private int twofoldness;
            private int tfn_money;
            private int tfn_money_ago;
            private int is_wx;
            private int wx_price;
            private int is_bro;
            private int user_mark;
            private int is_virtual;
            private int is_gold;
            private int is_gold_coupon;
            private int is_free;
            private int is_roll;
            private String roll_code;
            private int raffle;
            private int integral_convert;
            private String sign;
            private int channel_;
            private String supp_order;
            private int tr_num;
            private int one_from;
            private int one_deductible;
            private int is_draw;
            private int whether_prize;
            private int first_group;
            private int page4_shop;
            private int vip_roll_type;
            private int new_free;
            private List<?> orderShops;
            private List<?> orderShopList;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOrder_code() {
                return order_code;
            }

            public void setOrder_code(String order_code) {
                this.order_code = order_code;
            }

            public String getOrder_name() {
                return order_name;
            }

            public void setOrder_name(String order_name) {
                this.order_name = order_name;
            }

            public double getOrder_price() {
                return order_price;
            }

            public void setOrder_price(double order_price) {
                this.order_price = order_price;
            }

            public double getRemain_money() {
                return remain_money;
            }

            public void setRemain_money(double remain_money) {
                this.remain_money = remain_money;
            }

            public double getPay_money() {
                return pay_money;
            }

            public void setPay_money(double pay_money) {
                this.pay_money = pay_money;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public int getShop_num() {
                return shop_num;
            }

            public void setShop_num(int shop_num) {
                this.shop_num = shop_num;
            }

            public String getOrder_pic() {
                return order_pic;
            }

            public void setOrder_pic(String order_pic) {
                this.order_pic = order_pic;
            }

            public long getAdd_time() {
                return add_time;
            }

            public void setAdd_time(long add_time) {
                this.add_time = add_time;
            }

            public int getChange() {
                return change;
            }

            public void setChange(int change) {
                this.change = change;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getStore_code() {
                return store_code;
            }

            public void setStore_code(String store_code) {
                this.store_code = store_code;
            }

            public long getLast_time() {
                return last_time;
            }

            public void setLast_time(long last_time) {
                this.last_time = last_time;
            }

            public String getBak() {
                return bak;
            }

            public void setBak(String bak) {
                this.bak = bak;
            }

            public int getFree() {
                return free;
            }

            public void setFree(int free) {
                this.free = free;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getConsignee() {
                return consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPostcode() {
                return postcode;
            }

            public void setPostcode(String postcode) {
                this.postcode = postcode;
            }

            public int getIs_del() {
                return is_del;
            }

            public void setIs_del(int is_del) {
                this.is_del = is_del;
            }

            public int getSupp_id() {
                return supp_id;
            }

            public void setSupp_id(int supp_id) {
                this.supp_id = supp_id;
            }

            public int getSupp_money() {
                return supp_money;
            }

            public void setSupp_money(int supp_money) {
                this.supp_money = supp_money;
            }

            public int getKickBack() {
                return kickBack;
            }

            public void setKickBack(int kickBack) {
                this.kickBack = kickBack;
            }

            public int getTwo_kickback() {
                return two_kickback;
            }

            public void setTwo_kickback(int two_kickback) {
                this.two_kickback = two_kickback;
            }

            public int getThree_kickback() {
                return three_kickback;
            }

            public void setThree_kickback(int three_kickback) {
                this.three_kickback = three_kickback;
            }

            public int getFour_kickback() {
                return four_kickback;
            }

            public void setFour_kickback(int four_kickback) {
                this.four_kickback = four_kickback;
            }

            public int getIs_buy() {
                return is_buy;
            }

            public void setIs_buy(int is_buy) {
                this.is_buy = is_buy;
            }

            public int getPay_from() {
                return pay_from;
            }

            public void setPay_from(int pay_from) {
                this.pay_from = pay_from;
            }

            public String getCoupon_id() {
                return coupon_id;
            }

            public void setCoupon_id(String coupon_id) {
                this.coupon_id = coupon_id;
            }

            public int getCoupon_price() {
                return coupon_price;
            }

            public void setCoupon_price(int coupon_price) {
                this.coupon_price = coupon_price;
            }

            public int getC_merge() {
                return c_merge;
            }

            public void setC_merge(int c_merge) {
                this.c_merge = c_merge;
            }

            public String getC_token() {
                return c_token;
            }

            public void setC_token(String c_token) {
                this.c_token = c_token;
            }

            public int getIs_kick() {
                return is_kick;
            }

            public void setIs_kick(int is_kick) {
                this.is_kick = is_kick;
            }

            public int getIntegral_num() {
                return integral_num;
            }

            public void setIntegral_num(int integral_num) {
                this.integral_num = integral_num;
            }

            public int getRemain_integral_num() {
                return remain_integral_num;
            }

            public void setRemain_integral_num(int remain_integral_num) {
                this.remain_integral_num = remain_integral_num;
            }

            public int getTerrace_type() {
                return terrace_type;
            }

            public void setTerrace_type(int terrace_type) {
                this.terrace_type = terrace_type;
            }

            public int getShop_from() {
                return shop_from;
            }

            public void setShop_from(int shop_from) {
                this.shop_from = shop_from;
            }

            public int getPostage() {
                return postage;
            }

            public void setPostage(int postage) {
                this.postage = postage;
            }

            public int getPay_status() {
                return pay_status;
            }

            public void setPay_status(int pay_status) {
                this.pay_status = pay_status;
            }

            public int getUser_member() {
                return user_member;
            }

            public void setUser_member(int user_member) {
                this.user_member = user_member;
            }

            public int getZero_form() {
                return zero_form;
            }

            public void setZero_form(int zero_form) {
                this.zero_form = zero_form;
            }

            public int getChannel() {
                return channel;
            }

            public void setChannel(int channel) {
                this.channel = channel;
            }

            public int getVoucher_money() {
                return voucher_money;
            }

            public void setVoucher_money(int voucher_money) {
                this.voucher_money = voucher_money;
            }

            public String getIssue_code() {
                return issue_code;
            }

            public void setIssue_code(String issue_code) {
                this.issue_code = issue_code;
            }

            public String getParticipation_code() {
                return participation_code;
            }

            public void setParticipation_code(String participation_code) {
                this.participation_code = participation_code;
            }

            public int getIssue_status() {
                return issue_status;
            }

            public void setIssue_status(int issue_status) {
                this.issue_status = issue_status;
            }

            public int getIssue_endtime() {
                return issue_endtime;
            }

            public void setIssue_endtime(int issue_endtime) {
                this.issue_endtime = issue_endtime;
            }

            public int getDpzkm() {
                return dpzkm;
            }

            public void setDpzkm(int dpzkm) {
                this.dpzkm = dpzkm;
            }

            public int getTwofoldness() {
                return twofoldness;
            }

            public void setTwofoldness(int twofoldness) {
                this.twofoldness = twofoldness;
            }

            public int getTfn_money() {
                return tfn_money;
            }

            public void setTfn_money(int tfn_money) {
                this.tfn_money = tfn_money;
            }

            public int getTfn_money_ago() {
                return tfn_money_ago;
            }

            public void setTfn_money_ago(int tfn_money_ago) {
                this.tfn_money_ago = tfn_money_ago;
            }

            public int getIs_wx() {
                return is_wx;
            }

            public void setIs_wx(int is_wx) {
                this.is_wx = is_wx;
            }

            public int getWx_price() {
                return wx_price;
            }

            public void setWx_price(int wx_price) {
                this.wx_price = wx_price;
            }

            public int getIs_bro() {
                return is_bro;
            }

            public void setIs_bro(int is_bro) {
                this.is_bro = is_bro;
            }

            public int getUser_mark() {
                return user_mark;
            }

            public void setUser_mark(int user_mark) {
                this.user_mark = user_mark;
            }

            public int getIs_virtual() {
                return is_virtual;
            }

            public void setIs_virtual(int is_virtual) {
                this.is_virtual = is_virtual;
            }

            public int getIs_gold() {
                return is_gold;
            }

            public void setIs_gold(int is_gold) {
                this.is_gold = is_gold;
            }

            public int getIs_gold_coupon() {
                return is_gold_coupon;
            }

            public void setIs_gold_coupon(int is_gold_coupon) {
                this.is_gold_coupon = is_gold_coupon;
            }

            public int getIs_free() {
                return is_free;
            }

            public void setIs_free(int is_free) {
                this.is_free = is_free;
            }

            public int getIs_roll() {
                return is_roll;
            }

            public void setIs_roll(int is_roll) {
                this.is_roll = is_roll;
            }

            public String getRoll_code() {
                return roll_code;
            }

            public void setRoll_code(String roll_code) {
                this.roll_code = roll_code;
            }

            public int getRaffle() {
                return raffle;
            }

            public void setRaffle(int raffle) {
                this.raffle = raffle;
            }

            public int getIntegral_convert() {
                return integral_convert;
            }

            public void setIntegral_convert(int integral_convert) {
                this.integral_convert = integral_convert;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public int getChannel_() {
                return channel_;
            }

            public void setChannel_(int channel_) {
                this.channel_ = channel_;
            }

            public String getSupp_order() {
                return supp_order;
            }

            public void setSupp_order(String supp_order) {
                this.supp_order = supp_order;
            }

            public int getTr_num() {
                return tr_num;
            }

            public void setTr_num(int tr_num) {
                this.tr_num = tr_num;
            }

            public int getOne_from() {
                return one_from;
            }

            public void setOne_from(int one_from) {
                this.one_from = one_from;
            }

            public int getOne_deductible() {
                return one_deductible;
            }

            public void setOne_deductible(int one_deductible) {
                this.one_deductible = one_deductible;
            }

            public int getIs_draw() {
                return is_draw;
            }

            public void setIs_draw(int is_draw) {
                this.is_draw = is_draw;
            }

            public int getWhether_prize() {
                return whether_prize;
            }

            public void setWhether_prize(int whether_prize) {
                this.whether_prize = whether_prize;
            }

            public int getFirst_group() {
                return first_group;
            }

            public void setFirst_group(int first_group) {
                this.first_group = first_group;
            }

            public int getPage4_shop() {
                return page4_shop;
            }

            public void setPage4_shop(int page4_shop) {
                this.page4_shop = page4_shop;
            }

            public int getVip_roll_type() {
                return vip_roll_type;
            }

            public void setVip_roll_type(int vip_roll_type) {
                this.vip_roll_type = vip_roll_type;
            }

            public int getNew_free() {
                return new_free;
            }

            public void setNew_free(int new_free) {
                this.new_free = new_free;
            }

            public List<?> getOrderShops() {
                return orderShops;
            }

            public void setOrderShops(List<?> orderShops) {
                this.orderShops = orderShops;
            }

            public List<?> getOrderShopList() {
                return orderShopList;
            }

            public void setOrderShopList(List<?> orderShopList) {
                this.orderShopList = orderShopList;
            }
        }
    }
}
