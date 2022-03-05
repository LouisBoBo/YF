package com.yssj.entity;

import java.util.List;

/**
 * Created by Administrator on 2020/4/3 0003.
 */

public class OrderList {
    /**
     * clockIn : 0
     * current_date : 20200403
     * dayEndTime : 1585929599999
     * free_num : 0
     * isVip : 1
     * maxType : 6
     * message : 操作成功.
     * nRaffle_status : 0
     * now : 1585905684956
     * orders : [{"add_time":1585904204000,"address":"吉林省松原市其他 111","advance_sale_days":0,"bak":"hGES1941369365","change":0,"check":0,"consignee":"1666","coupon_id":"0","coupon_price":0,"dpzkm":0,"draw_time":1585904205000,"first_group":2,"free":0,"integral_convert":1,"isTM":0,"is_del":0,"is_draw":1,"is_free":4,"is_freeDelivery":0,"is_kick":0,"is_roll":0,"is_wx":0,"issue_code":"0","issue_status":0,"kickBack":0,"last_time":1586509004000,"new_free":2,"one_deductible":0,"one_from":2,"orderShopList":[],"orderShops":[{"change":0,"color":"米白色","id":136490,"order_code":"200403g3SVkEqE","original_price":2064,"shop_code":"hGES1941369365","shop_name":"通勤POLO领纯色长袖纽扣棉衣","shop_num":1,"shop_pic":"FreaPEmB_600_900.jpg","shop_price":206.4,"size":"M","status":"0","supp_id":2230,"voucher_money":0}],"order_code":"200403g3SVkEqE","order_name":"通勤POLO领纯色长袖纽扣棉衣","order_pic":"FreaPEmB_600_900.jpg","order_price":0,"page4_shop":0,"participation_code":"0","pay_money":0,"pay_status":1,"pay_time":1585904204000,"phone":"13695852858","postage":0,"postcode":"","remain_money":0,"roll_code":"0","shop_from":13,"shop_num":1,"status":2,"supp_id":2230,"supp_money":0,"tfn_money":0,"tfn_money_ago":0,"twofoldness":1,"user_id":946268,"vip_roll_type":-1,"whether_prize":2,"wx_price":0},{"add_time":1585904058000,"address":"吉林省松原市其他 111","advance_sale_days":0,"bak":"hGES1943869373","change":0,"check":0,"consignee":"1666","coupon_id":"0","coupon_price":0,"dpzkm":0,"draw_time":1585904226000,"first_group":2,"free":0,"integral_convert":1,"isTM":0,"is_del":0,"is_draw":1,"is_free":4,"is_freeDelivery":0,"is_kick":0,"is_roll":0,"is_wx":0,"issue_code":"0","issue_status":0,"kickBack":0,"last_time":1586508858000,"new_free":2,"one_deductible":0,"one_from":2,"orderShopList":[],"orderShops":[{"change":0,"color":"黑色","id":136489,"order_code":"200403W92Nt5Tk","original_price":227.5,"shop_code":"hGES1943869373","shop_name":"休闲纯色阔腿裤短裤纽扣休闲裤","shop_num":1,"shop_pic":"I2022z5X_600_900.jpg","shop_price":46.5,"size":"S","status":"0","supp_id":2230,"voucher_money":0}],"order_code":"200403W92Nt5Tk","order_name":"休闲纯色阔腿裤短裤纽扣休闲裤","order_pic":"I2022z5X_600_900.jpg","order_price":0,"page4_shop":0,"participation_code":"0","pay_money":0,"pay_status":1,"pay_time":1585904058000,"phone":"13695852858","postage":0,"postcode":"","remain_money":0,"roll_code":"0","shop_from":13,"shop_num":1,"status":2,"supp_id":2230,"supp_money":0,"tfn_money":0,"tfn_money_ago":0,"twofoldness":1,"user_id":946268,"vip_roll_type":4,"whether_prize":2,"wx_price":0}]
     * pageCount : 1
     * send_num : 0
     * status : 1
     */

    private int clockIn;
    private String current_date;
    private long dayEndTime;
    private int free_num;
    private int isVip;
    private int maxType;
    private String message;
    private int nRaffle_status;
    private long now;
    private int pageCount;
    private int send_num;
    private String status;
    private List<OrdersBean> orders;

    public int getClockIn() {
        return clockIn;
    }

    public void setClockIn(int clockIn) {
        this.clockIn = clockIn;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

    public long getDayEndTime() {
        return dayEndTime;
    }

    public void setDayEndTime(long dayEndTime) {
        this.dayEndTime = dayEndTime;
    }

    public int getFree_num() {
        return free_num;
    }

    public void setFree_num(int free_num) {
        this.free_num = free_num;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public int getMaxType() {
        return maxType;
    }

    public void setMaxType(int maxType) {
        this.maxType = maxType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNRaffle_status() {
        return nRaffle_status;
    }

    public void setNRaffle_status(int nRaffle_status) {
        this.nRaffle_status = nRaffle_status;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSend_num() {
        return send_num;
    }

    public void setSend_num(int send_num) {
        this.send_num = send_num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrdersBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersBean> orders) {
        this.orders = orders;
    }

    public static class OrdersBean {
        /**
         * add_time : 1585904204000
         * address : 吉林省松原市其他 111
         * advance_sale_days : 0
         * bak : hGES1941369365
         * change : 0
         * check : 0
         * consignee : 1666
         * coupon_id : 0
         * coupon_price : 0
         * dpzkm : 0
         * draw_time : 1585904205000
         * first_group : 2
         * free : 0
         * integral_convert : 1
         * isTM : 0
         * is_del : 0
         * is_draw : 1
         * is_free : 4
         * is_freeDelivery : 0
         * is_kick : 0
         * is_roll : 0
         * is_wx : 0
         * issue_code : 0
         * issue_status : 0
         * kickBack : 0
         * last_time : 1586509004000
         * new_free : 2
         * one_deductible : 0
         * one_from : 2
         * orderShopList : []
         * orderShops : [{"change":0,"color":"米白色","id":136490,"order_code":"200403g3SVkEqE","original_price":2064,"shop_code":"hGES1941369365","shop_name":"通勤POLO领纯色长袖纽扣棉衣","shop_num":1,"shop_pic":"FreaPEmB_600_900.jpg","shop_price":206.4,"size":"M","status":"0","supp_id":2230,"voucher_money":0}]
         * order_code : 200403g3SVkEqE
         * order_name : 通勤POLO领纯色长袖纽扣棉衣
         * order_pic : FreaPEmB_600_900.jpg
         * order_price : 0
         * page4_shop : 0
         * participation_code : 0
         * pay_money : 0
         * pay_status : 1
         * pay_time : 1585904204000
         * phone : 13695852858
         * postage : 0
         * postcode :
         * remain_money : 0
         * roll_code : 0
         * shop_from : 13
         * shop_num : 1
         * status : 2
         * supp_id : 2230
         * supp_money : 0
         * tfn_money : 0
         * tfn_money_ago : 0
         * twofoldness : 1
         * user_id : 946268
         * vip_roll_type : -1
         * whether_prize : 2
         * wx_price : 0
         */

        private long add_time;
        private String address;
        private int advance_sale_days;
        private String bak;
        private int change;
        private int check;
        private String consignee;
        private String coupon_id;
        private int coupon_price;
        private int dpzkm;
        private long draw_time;
        private int first_group;
        private int free;
        private int integral_convert;
        private int isTM;
        private int is_del;
        private int is_draw;
        private int is_free;
        private int is_freeDelivery;
        private int is_kick;
        private int is_roll;
        private int is_wx;
        private String issue_code;
        private int issue_status;
        private int kickBack;
        private long last_time;
        private int new_free;
        private int one_deductible;
        private int one_from;
        private String order_code;
        private String order_name;
        private String order_pic;
        private int order_price;
        private int page4_shop;
        private String participation_code;
        private int pay_money;
        private int pay_status;
        private long pay_time;
        private String phone;
        private int postage;
        private String postcode;
        private int remain_money;
        private String roll_code;
        private int shop_from;
        private int shop_num;
        private int status;
        private int supp_id;
        private int supp_money;
        private int tfn_money;
        private int tfn_money_ago;
        private int twofoldness;
        private int user_id;
        private int vip_roll_type;
        private int whether_prize;
        private int wx_price;
        private List<?> orderShopList;
        private List<OrderShopsBean> orderShops;

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getAdvance_sale_days() {
            return advance_sale_days;
        }

        public void setAdvance_sale_days(int advance_sale_days) {
            this.advance_sale_days = advance_sale_days;
        }

        public String getBak() {
            return bak;
        }

        public void setBak(String bak) {
            this.bak = bak;
        }

        public int getChange() {
            return change;
        }

        public void setChange(int change) {
            this.change = change;
        }

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
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

        public int getDpzkm() {
            return dpzkm;
        }

        public void setDpzkm(int dpzkm) {
            this.dpzkm = dpzkm;
        }

        public long getDraw_time() {
            return draw_time;
        }

        public void setDraw_time(long draw_time) {
            this.draw_time = draw_time;
        }

        public int getFirst_group() {
            return first_group;
        }

        public void setFirst_group(int first_group) {
            this.first_group = first_group;
        }

        public int getFree() {
            return free;
        }

        public void setFree(int free) {
            this.free = free;
        }

        public int getIntegral_convert() {
            return integral_convert;
        }

        public void setIntegral_convert(int integral_convert) {
            this.integral_convert = integral_convert;
        }

        public int getIsTM() {
            return isTM;
        }

        public void setIsTM(int isTM) {
            this.isTM = isTM;
        }

        public int getIs_del() {
            return is_del;
        }

        public void setIs_del(int is_del) {
            this.is_del = is_del;
        }

        public int getIs_draw() {
            return is_draw;
        }

        public void setIs_draw(int is_draw) {
            this.is_draw = is_draw;
        }

        public int getIs_free() {
            return is_free;
        }

        public void setIs_free(int is_free) {
            this.is_free = is_free;
        }

        public int getIs_freeDelivery() {
            return is_freeDelivery;
        }

        public void setIs_freeDelivery(int is_freeDelivery) {
            this.is_freeDelivery = is_freeDelivery;
        }

        public int getIs_kick() {
            return is_kick;
        }

        public void setIs_kick(int is_kick) {
            this.is_kick = is_kick;
        }

        public int getIs_roll() {
            return is_roll;
        }

        public void setIs_roll(int is_roll) {
            this.is_roll = is_roll;
        }

        public int getIs_wx() {
            return is_wx;
        }

        public void setIs_wx(int is_wx) {
            this.is_wx = is_wx;
        }

        public String getIssue_code() {
            return issue_code;
        }

        public void setIssue_code(String issue_code) {
            this.issue_code = issue_code;
        }

        public int getIssue_status() {
            return issue_status;
        }

        public void setIssue_status(int issue_status) {
            this.issue_status = issue_status;
        }

        public int getKickBack() {
            return kickBack;
        }

        public void setKickBack(int kickBack) {
            this.kickBack = kickBack;
        }

        public long getLast_time() {
            return last_time;
        }

        public void setLast_time(long last_time) {
            this.last_time = last_time;
        }

        public int getNew_free() {
            return new_free;
        }

        public void setNew_free(int new_free) {
            this.new_free = new_free;
        }

        public int getOne_deductible() {
            return one_deductible;
        }

        public void setOne_deductible(int one_deductible) {
            this.one_deductible = one_deductible;
        }

        public int getOne_from() {
            return one_from;
        }

        public void setOne_from(int one_from) {
            this.one_from = one_from;
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

        public String getOrder_pic() {
            return order_pic;
        }

        public void setOrder_pic(String order_pic) {
            this.order_pic = order_pic;
        }

        public int getOrder_price() {
            return order_price;
        }

        public void setOrder_price(int order_price) {
            this.order_price = order_price;
        }

        public int getPage4_shop() {
            return page4_shop;
        }

        public void setPage4_shop(int page4_shop) {
            this.page4_shop = page4_shop;
        }

        public String getParticipation_code() {
            return participation_code;
        }

        public void setParticipation_code(String participation_code) {
            this.participation_code = participation_code;
        }

        public int getPay_money() {
            return pay_money;
        }

        public void setPay_money(int pay_money) {
            this.pay_money = pay_money;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public long getPay_time() {
            return pay_time;
        }

        public void setPay_time(long pay_time) {
            this.pay_time = pay_time;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getPostage() {
            return postage;
        }

        public void setPostage(int postage) {
            this.postage = postage;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public int getRemain_money() {
            return remain_money;
        }

        public void setRemain_money(int remain_money) {
            this.remain_money = remain_money;
        }

        public String getRoll_code() {
            return roll_code;
        }

        public void setRoll_code(String roll_code) {
            this.roll_code = roll_code;
        }

        public int getShop_from() {
            return shop_from;
        }

        public void setShop_from(int shop_from) {
            this.shop_from = shop_from;
        }

        public int getShop_num() {
            return shop_num;
        }

        public void setShop_num(int shop_num) {
            this.shop_num = shop_num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public int getTwofoldness() {
            return twofoldness;
        }

        public void setTwofoldness(int twofoldness) {
            this.twofoldness = twofoldness;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getVip_roll_type() {
            return vip_roll_type;
        }

        public void setVip_roll_type(int vip_roll_type) {
            this.vip_roll_type = vip_roll_type;
        }

        public int getWhether_prize() {
            return whether_prize;
        }

        public void setWhether_prize(int whether_prize) {
            this.whether_prize = whether_prize;
        }

        public int getWx_price() {
            return wx_price;
        }

        public void setWx_price(int wx_price) {
            this.wx_price = wx_price;
        }

        public List<?> getOrderShopList() {
            return orderShopList;
        }

        public void setOrderShopList(List<?> orderShopList) {
            this.orderShopList = orderShopList;
        }

        public List<OrderShopsBean> getOrderShops() {
            return orderShops;
        }

        public void setOrderShops(List<OrderShopsBean> orderShops) {
            this.orderShops = orderShops;
        }

        public static class OrderShopsBean {
            /**
             * change : 0
             * color : 米白色
             * id : 136490
             * order_code : 200403g3SVkEqE
             * original_price : 2064
             * shop_code : hGES1941369365
             * shop_name : 通勤POLO领纯色长袖纽扣棉衣
             * shop_num : 1
             * shop_pic : FreaPEmB_600_900.jpg
             * shop_price : 206.4
             * size : M
             * status : 0
             * supp_id : 2230
             * voucher_money : 0
             */

            private int change;
            private String color;
            private int id;
            private String order_code;
            private int original_price;
            private String shop_code;
            private String shop_name;
            private int shop_num;
            private String shop_pic;
            private double shop_price;
            private String size;
            private String status;
            private int supp_id;
            private int voucher_money;

            public int getChange() {
                return change;
            }

            public void setChange(int change) {
                this.change = change;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

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

            public int getOriginal_price() {
                return original_price;
            }

            public void setOriginal_price(int original_price) {
                this.original_price = original_price;
            }

            public String getShop_code() {
                return shop_code;
            }

            public void setShop_code(String shop_code) {
                this.shop_code = shop_code;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public int getShop_num() {
                return shop_num;
            }

            public void setShop_num(int shop_num) {
                this.shop_num = shop_num;
            }

            public String getShop_pic() {
                return shop_pic;
            }

            public void setShop_pic(String shop_pic) {
                this.shop_pic = shop_pic;
            }

            public double getShop_price() {
                return shop_price;
            }

            public void setShop_price(double shop_price) {
                this.shop_price = shop_price;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getSupp_id() {
                return supp_id;
            }

            public void setSupp_id(int supp_id) {
                this.supp_id = supp_id;
            }

            public int getVoucher_money() {
                return voucher_money;
            }

            public void setVoucher_money(int voucher_money) {
                this.voucher_money = voucher_money;
            }
        }
    }
}
