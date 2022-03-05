package com.yssj.ui.activity.vip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VipListBean implements Serializable {


    /**
     * viplist : [{"vipcard_id":9,"weight":1,"vip_type":9,"free_count":0,"vip_price":0.1,"original_vip_price":199,"url":"vip/vip_type/freeCard.png","vip_name":"免拼卡","info_url":"vip/vip_equity/freeEquity1/equity1.png,vip/vip_equity/freeEquity1/equity2.png","equity_content":"特权一 免费领取并发货1件美衣,特权二 预存的29.9元可全额购买商品","equity_url":"vip/vip_equity/freeEquity2/equity1.png,vip/vip_equity/freeEquity2/equity2.png","cashabletime":30,"free_num":0,"is_use":1,"vip_equity":"2,3,4","return_money":200,"punch_days":120,"equity":[{"equity_content":"特权一 免费领取并发货1件美衣","index":0,"equity_url":"vip/vip_equity/freeEquity1/equity1.png","showWen":0,"wenContent":"待定"},{"equity_content":"特权二 预存的29.9元可全额购买商品","index":1,"equity_url":"vip/vip_equity/freeEquity1/equity2.png","showWen":0,"wenContent":"待定"}],"equityYet":[{"equity_content":"特权一 免费领取并发货1件美衣","index":0,"equity_url":"vip/vip_equity/freeEquity2/equity1.png","showWen":0,"wenContent":"待定"},{"equity_content":"特权二 预存的29.9元可全额购买商品","index":1,"equity_url":"vip/vip_equity/freeEquity2/equity2.png","showWen":0,"wenContent":"待定"}],"purchase_num":1,"purchase_maxNum":2},{"vipcard_id":8,"weight":2,"vip_type":8,"free_count":0,"vip_price":0.1,"original_vip_price":199,"url":"vip/vip_type/deliveryCard.png","vip_name":"发货卡","info_url":"vip/vip_equity/deliveryEquity1/equity1.png,vip/vip_equity/deliveryEquity1/equity2.png","equity_content":"特权一 拼团订单免拼直接发货,特权二 预存的29.9元可全额购买商品","equity_url":"vip/vip_equity/deliveryEquity2/equity1.png,vip/vip_equity/deliveryEquity2/equity2.png","cashabletime":30,"free_num":0,"is_use":1,"vip_equity":"2,3,4","return_money":200,"punch_days":120,"equity":[{"equity_content":"特权一 拼团订单免拼直接发货","index":0,"equity_url":"vip/vip_equity/deliveryEquity1/equity1.png","showWen":0,"wenContent":"待定"},{"equity_content":"特权二 预存的29.9元可全额购买商品","index":1,"equity_url":"vip/vip_equity/deliveryEquity1/equity2.png","showWen":0,"wenContent":"待定"}],"equityYet":[{"equity_content":"特权一 拼团订单免拼直接发货","index":0,"equity_url":"vip/vip_equity/deliveryEquity2/equity1.png","showWen":0,"wenContent":"待定"},{"equity_content":"特权二 预存的29.9元可全额购买商品","index":1,"equity_url":"vip/vip_equity/deliveryEquity2/equity2.png","showWen":0,"wenContent":"待定"}],"purchase_num":1,"purchase_maxNum":2},{"vipcard_id":7,"weight":3,"vip_type":7,"free_count":0,"vip_price":0.1,"original_vip_price":4.9,"url":"vip/vip_type/trialCard.png","vip_name":"提现卡","info_url":"vip/vip_equity/trialEquity1/equity1.png,vip/vip_equity/trialEquity1/equity2.png,vip/vip_equity/trialEquity1/equity3.png","equity_content":"特权一 立即赠送10次提现,特权二 保底返还5元，最高70元,特权三 预存的4.9元可全额用于购买商品 && 特权一 立即赠送50次提现,特权二 保底返还25元，最高200元,特权三 预存的19.9元可全额用于购买商品","equity_url":"vip/vip_equity/trialEquity2/equity1.png,vip/vip_equity/trialEquity2/equity2.png,vip/vip_equity/trialEquity2/equity3.png","cashabletime":30,"free_num":0,"is_use":1,"vip_equity":"2,3","return_money":200,"punch_days":120,"equity":[{"equity_content":"特权一 立即赠送10次提现","index":0,"equity_url":"vip/vip_equity/trialEquity1/equity1.png","showWen":1},{"equity_content":"特权二 保底返还5元，最高70元","index":1,"equity_url":"vip/vip_equity/trialEquity1/equity2.png","showWen":1},{"equity_content":"特权三 预存的4.9元可全额用于购买商品 ","index":2,"equity_url":"vip/vip_equity/trialEquity1/equity3.png","showWen":0}],"equityYet":[{"equity_content":" 特权一 立即赠送50次提现","index":0,"equity_url":"vip/vip_equity/trialEquity2/equity1.png","showWen":1,"wenContent":"次日生效，连续5日，每日赠送10次提现"},{"equity_content":"特权二 保底返还25元，最高200元","index":1,"equity_url":"vip/vip_equity/trialEquity2/equity2.png","showWen":1,"wenContent":"50次累计提现25元-200元。含分15-30天到账的金额"},{"equity_content":"特权三 预存的19.9元可全额用于购买商品","index":2,"equity_url":"vip/vip_equity/trialEquity2/equity3.png","showWen":0,"wenContent":"50次累计提现25元-200元。含分15-30天到账的金额"}],"purchase_num":1,"purchase_maxNum":1},{"vipcard_id":4,"weight":4,"vip_type":4,"free_count":5,"vip_price":0.1,"original_vip_price":199,"url":"vip/vip_type/diamondsCard.png","vip_name":"钻石会员","head_url":"vip/vip_head/diamondzs.png","info_url":"vip/vip_equity/diamondEquity1/equity1.png,vip/vip_equity/diamondEquity1/equity2.png,vip/vip_equity/diamondEquity1/equity3.png,vip/vip_equity/diamondEquity1/equity4.png","equity_content":"特权一 可立即提现xx元到微信零钱,特权二 免费发货1件199元美衣,特权三 预存的79元可全额用于购买商品,特权四 打卡120天返还400元 &&特权一 免费发货1件199元美衣,特权二 预存的79元可全额用于购买商品,特权三 打卡120天返还400元","equity_url":"vip/vip_equity/diamondEquity2/equity1.png,vip/vip_equity/diamondEquity2/equity2.png,vip/vip_equity/diamondEquity2/equity3.png","cashabletime":0.02,"free_num":1,"is_use":1,"price_section":199,"discount":3.5,"vip_equity":"1,2","return_money":400,"punch_days":120,"equity":[{"equity_content":"特权一 可立即提现xx元到微信零钱","index":0,"equity_url":"vip/vip_equity/diamondEquity1/equity1.png","showWen":0},{"equity_content":"特权二 免费发货1件199元美衣","index":1,"equity_url":"vip/vip_equity/diamondEquity1/equity2.png","showWen":1},{"equity_content":"特权三 预存的79元可全额用于购买商品","index":2,"equity_url":"vip/vip_equity/diamondEquity1/equity3.png","showWen":0},{"equity_content":"特权四 打卡120天返还400元 ","index":3,"equity_url":"vip/vip_equity/diamondEquity1/equity4.png","showWen":0}],"equityYet":[{"equity_content":"特权一 免费发货1件199元美衣","index":0,"equity_url":"vip/vip_equity/diamondEquity2/equity1.png","showWen":1,"wenContent":"需2张钻石卡"},{"equity_content":"特权二 预存的79元可全额用于购买商品","index":1,"equity_url":"vip/vip_equity/diamondEquity2/equity2.png","showWen":0,"wenContent":"需2张钻石卡"},{"equity_content":"特权三 打卡120天返还400元","index":2,"equity_url":"vip/vip_equity/diamondEquity2/equity3.png","showWen":0,"wenContent":"需2张钻石卡"}],"purchase_num":1,"purchase_maxNum":1},{"vipcard_id":5,"weight":5,"vip_type":5,"free_count":5,"vip_price":0.2,"original_vip_price":699,"url":"vip/vip_type/crownCard.png","vip_name":"皇冠会员","head_url":"vip/vip_head/crownhg.png","info_url":"vip/vip_equity/crownEquity1/equity1.png,vip/vip_equity/crownEquity1/equity2.png,vip/vip_equity/crownEquity1/equity3.png,vip/vip_equity/crownEquity1/equity4.png","equity_content":"特权一 免费领取并发货1件美衣,特权二 以会员价用预存款购买商品,特权三 单次可提现50-70元到微信零钱,特权四 打卡120天返还600元","equity_url":"vip/vip_equity/crownEquity2/equity1.png,vip/vip_equity/crownEquity2/equity2.png,vip/vip_equity/crownEquity2/equity3.png,vip/vip_equity/crownEquity2/equity4.png","cashabletime":7,"free_num":1,"is_use":1,"price_section":299,"discount":3,"vip_equity":"1,2","return_money":600,"punch_days":120,"equity":[{"equity_content":"特权一 免费领取并发货1件美衣","index":0,"equity_url":"vip/vip_equity/crownEquity1/equity1.png","showWen":1,"wenContent":"待定"},{"equity_content":"特权二 以会员价用预存款购买商品","index":1,"equity_url":"vip/vip_equity/crownEquity1/equity2.png","showWen":0,"wenContent":"待定"},{"equity_content":"特权三 单次可提现50-70元到微信零钱","index":2,"equity_url":"vip/vip_equity/crownEquity1/equity3.png","showWen":0,"wenContent":"待定"},{"equity_content":"特权四 打卡120天返还600元","index":3,"equity_url":"vip/vip_equity/crownEquity1/equity4.png","showWen":0,"wenContent":"待定"}],"equityYet":[{"equity_content":"特权一 免费领取并发货1件美衣","index":0,"equity_url":"vip/vip_equity/crownEquity2/equity1.png","showWen":1,"wenContent":"待定"},{"equity_content":"特权二 以会员价用预存款购买商品","index":1,"equity_url":"vip/vip_equity/crownEquity2/equity2.png","showWen":0,"wenContent":"待定"},{"equity_content":"特权三 单次可提现50-70元到微信零钱","index":2,"equity_url":"vip/vip_equity/crownEquity2/equity3.png","showWen":0,"wenContent":"待定"},{"equity_content":"特权四 打卡120天返还600元","index":3,"equity_url":"vip/vip_equity/crownEquity2/equity4.png","showWen":0,"wenContent":"待定"}],"purchase_num":1,"purchase_maxNum":-1},{"vipcard_id":6,"weight":6,"vip_type":6,"free_count":5,"vip_price":0.2,"original_vip_price":1599,"url":"vip/vip_type/supremeCard.png","vip_name":"至尊会员","head_url":"vip/vip_head/supremezz.png","info_url":"vip/vip_equity/supremeEquity1/equity1.png,vip/vip_equity/supremeEquity1/equity2.png,vip/vip_equity/supremeEquity1/equity3.png,vip/vip_equity/supremeEquity1/equity4.png","equity_content":"特权一 免费领取并发货1件美衣,特权二 以会员价用预存款购买商品,特权三 单次可提现50-70元到微信零钱,特权四 打卡120天返还1200元","equity_url":"vip/vip_equity/supremeEquity2/equity1.png,vip/vip_equity/supremeEquity2/equity2.png,vip/vip_equity/supremeEquity2/equity3.png,vip/vip_equity/supremeEquity2/equity4.png","cashabletime":0.02,"free_num":5,"is_use":1,"price_section":899,"discount":2.5,"vip_equity":"1,2","return_money":1200,"punch_days":120,"equity":[{"equity_content":"特权一 免费领取并发货1件美衣","index":0,"equity_url":"vip/vip_equity/supremeEquity1/equity1.png","showWen":1,"wenContent":"待定"},{"equity_content":"特权二 以会员价用预存款购买商品","index":1,"equity_url":"vip/vip_equity/supremeEquity1/equity2.png","showWen":0,"wenContent":"待定"},{"equity_content":"特权三 单次可提现50-70元到微信零钱","index":2,"equity_url":"vip/vip_equity/supremeEquity1/equity3.png","showWen":0,"wenContent":"待定"},{"equity_content":"特权四 打卡120天返还1200元","index":3,"equity_url":"vip/vip_equity/supremeEquity1/equity4.png","showWen":0,"wenContent":"待定"}],"equityYet":[{"equity_content":"特权一 免费领取并发货1件美衣","index":0,"equity_url":"vip/vip_equity/supremeEquity2/equity1.png","showWen":1,"wenContent":"待定"},{"equity_content":"特权二 以会员价用预存款购买商品","index":1,"equity_url":"vip/vip_equity/supremeEquity2/equity2.png","showWen":0,"wenContent":"待定"},{"equity_content":"特权三 单次可提现50-70元到微信零钱","index":2,"equity_url":"vip/vip_equity/supremeEquity2/equity3.png","showWen":0,"wenContent":"待定"},{"equity_content":"特权四 打卡120天返还1200元","index":3,"equity_url":"vip/vip_equity/supremeEquity2/equity4.png","showWen":0,"wenContent":"待定"}],"purchase_num":1,"purchase_maxNum":-1}]
     * balance : 0.1
     * is_vip : 1
     * firstDiamondCard : 0
     * firstCashcard : 1
     * userVipList : [{"v_code":"V200225KpmuagLn","vip_type":4,"bonus":0,"vip_num":1,"num":1,"vip_balance":0.1,"end_time":1582616863000,"count":5,"vip_price":0.1,"add_time":1582615063000,"vip_code":"2002250D7xaEAQ","price_section":199}]
     * message : 操作成功.
     * twoDiamondCard : 1
     * landPage : 4
     * trialcash_num : 10
     * status : 1
     */

    private String balance;
    private int is_vip;
    private int firstDiamondCard;
    private int firstCashcard;
    private String message;
    private int twoDiamondCard;
    private int landPage;
    private int trialcash_num;
    private String status;
    private List<ViplistBean> viplist;
    private List<UserVipListBean> userVipList;
    private String raffle_money;


    public String getRaffle_money() {
        return raffle_money;
    }

    public void setRaffle_money(String raffle_money) {
        this.raffle_money = raffle_money;
    }


    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public int getFirstDiamondCard() {
        return firstDiamondCard;
    }

    public void setFirstDiamondCard(int firstDiamondCard) {
        this.firstDiamondCard = firstDiamondCard;
    }

    public int getFirstCashcard() {
        return firstCashcard;
    }

    public void setFirstCashcard(int firstCashcard) {
        this.firstCashcard = firstCashcard;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTwoDiamondCard() {
        return twoDiamondCard;
    }

    public void setTwoDiamondCard(int twoDiamondCard) {
        this.twoDiamondCard = twoDiamondCard;
    }

    public int getLandPage() {
        return landPage;
    }

    public void setLandPage(int landPage) {
        this.landPage = landPage;
    }

    public int getTrialcash_num() {
        return trialcash_num;
    }

    public void setTrialcash_num(int trialcash_num) {
        this.trialcash_num = trialcash_num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ViplistBean> getViplist() {
        return viplist;
    }

    public void setViplist(List<ViplistBean> viplist) {
        this.viplist = viplist;
    }

    public List<UserVipListBean> getUserVipList() {
        return userVipList;
    }

    public void setUserVipList(List<UserVipListBean> userVipList) {
        this.userVipList = userVipList;
    }

    public static class ViplistBean implements Serializable {
        /**
         * vipcard_id : 9
         * weight : 1
         * vip_type : 9
         * free_count : 0
         * vip_price : 0.1
         * original_vip_price : 199.0
         * url : vip/vip_type/freeCard.png
         * vip_name : 免拼卡
         * info_url : vip/vip_equity/freeEquity1/equity1.png,vip/vip_equity/freeEquity1/equity2.png
         * equity_content : 特权一 免费领取并发货1件美衣,特权二 预存的29.9元可全额购买商品
         * equity_url : vip/vip_equity/freeEquity2/equity1.png,vip/vip_equity/freeEquity2/equity2.png
         * cashabletime : 30.0
         * free_num : 0
         * is_use : 1
         * vip_equity : 2,3,4
         * return_money : 200.0
         * punch_days : 120
         * equity : [{"equity_content":"特权一 免费领取并发货1件美衣","index":0,"equity_url":"vip/vip_equity/freeEquity1/equity1.png","showWen":0,"wenContent":"待定"},{"equity_content":"特权二 预存的29.9元可全额购买商品","index":1,"equity_url":"vip/vip_equity/freeEquity1/equity2.png","showWen":0,"wenContent":"待定"}]
         * equityYet : [{"equity_content":"特权一 免费领取并发货1件美衣","index":0,"equity_url":"vip/vip_equity/freeEquity2/equity1.png","showWen":0,"wenContent":"待定"},{"equity_content":"特权二 预存的29.9元可全额购买商品","index":1,"equity_url":"vip/vip_equity/freeEquity2/equity2.png","showWen":0,"wenContent":"待定"}]
         * purchase_num : 1
         * purchase_maxNum : 2
         * head_url : vip/vip_head/diamondzs.png
         * price_section : 199.0
         * discount : 3.5
         */

        private int vipcard_id;
        private int weight;
        private int vip_type;
        private int free_count;
        private double vip_price;
        private double original_vip_price;
        private String url;
        private String vip_name;
        private String info_url;
        private String equity_content;
        private String equity_url;
        private double cashabletime;
        private int free_num;
        private int is_use;
        private String vip_equity;
        private double return_money;
        private int punch_days;
        private int purchase_num;
        private int purchase_maxNum;
        private String head_url;
        private String price_section;
        private double discount;
        private List<VipEquityBase> equity;
        private List<VipEquityBase> equityYet;

        private double vip_balance;
        private String vip_code;
        private int vip_num;
        private int num;
        private int count;
        private String substance;
        private String context;
        private double arrears_price;


        public double getArrears_price() {
            return arrears_price;
        }

        public void setArrears_price(double arrears_price) {
            this.arrears_price = arrears_price;
        }


        public String getSubstance() {
            return substance;
        }

        public void setSubstance(String substance) {
            this.substance = substance;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }


        public double getVip_balance() {
            return vip_balance;
        }

        public void setVip_balance(double vip_balance) {
            this.vip_balance = vip_balance;
        }

        public String getVip_code() {
            return vip_code;
        }

        public void setVip_code(String vip_code) {
            this.vip_code = vip_code;
        }

        public int getVip_num() {
            return vip_num;
        }

        public void setVip_num(int vip_num) {
            this.vip_num = vip_num;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }


        public int getVipcard_id() {
            return vipcard_id;
        }

        public void setVipcard_id(int vipcard_id) {
            this.vipcard_id = vipcard_id;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getVip_type() {
            return vip_type;
        }

        public void setVip_type(int vip_type) {
            this.vip_type = vip_type;
        }

        public int getFree_count() {
            return free_count;
        }

        public void setFree_count(int free_count) {
            this.free_count = free_count;
        }

        public double getVip_price() {
            return vip_price;
        }

        public void setVip_price(double vip_price) {
            this.vip_price = vip_price;
        }

        public double getOriginal_vip_price() {
            return original_vip_price;
        }

        public void setOriginal_vip_price(double original_vip_price) {
            this.original_vip_price = original_vip_price;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVip_name() {
            return vip_name;
        }

        public void setVip_name(String vip_name) {
            this.vip_name = vip_name;
        }

        public String getInfo_url() {
            return info_url;
        }

        public void setInfo_url(String info_url) {
            this.info_url = info_url;
        }

        public String getEquity_content() {
            return equity_content;
        }

        public void setEquity_content(String equity_content) {
            this.equity_content = equity_content;
        }

        public String getEquity_url() {
            return equity_url;
        }

        public void setEquity_url(String equity_url) {
            this.equity_url = equity_url;
        }

        public double getCashabletime() {
            return cashabletime;
        }

        public void setCashabletime(double cashabletime) {
            this.cashabletime = cashabletime;
        }

        public int getFree_num() {
            return free_num;
        }

        public void setFree_num(int free_num) {
            this.free_num = free_num;
        }

        public int getIs_use() {
            return is_use;
        }

        public void setIs_use(int is_use) {
            this.is_use = is_use;
        }

        public String getVip_equity() {
            return vip_equity;
        }

        public void setVip_equity(String vip_equity) {
            this.vip_equity = vip_equity;
        }

        public double getReturn_money() {
            return return_money;
        }

        public void setReturn_money(double return_money) {
            this.return_money = return_money;
        }

        public int getPunch_days() {
            return punch_days;
        }

        public void setPunch_days(int punch_days) {
            this.punch_days = punch_days;
        }

        public int getPurchase_num() {
            return purchase_num;
        }

        public void setPurchase_num(int purchase_num) {
            this.purchase_num = purchase_num;
        }

        public int getPurchase_maxNum() {
            return purchase_maxNum;
        }

        public void setPurchase_maxNum(int purchase_maxNum) {
            this.purchase_maxNum = purchase_maxNum;
        }

        public String getHead_url() {
            return head_url;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }

        public String getPrice_section() {
            return price_section;
        }

        public void setPrice_section(String price_section) {
            this.price_section = price_section;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

        public List<VipEquityBase> getEquity() {
            return equity;
        }

        public void setEquity(List<VipEquityBase> equity) {
            this.equity = equity;
        }

        public List<VipEquityBase> getEquityYet() {
            return equityYet;
        }

        public void setEquityYet(List<VipEquityBase> equityYet) {
            this.equityYet = equityYet;
        }

        public static class EquityBean extends VipEquityBase implements Serializable {
            /**
             * equity_content : 特权一 免费领取并发货1件美衣
             * index : 0
             * equity_url : vip/vip_equity/freeEquity1/equity1.png
             * showWen : 0
             * wenContent : 待定
             */

            private String equity_content;
            private int index;
            private String equity_url;
            private int showWen;
            private String wenContent;
            private ArrayList<String> replaces;


            public ArrayList<String> getReplaces() {
                return replaces;
            }

            public void setReplaces(ArrayList<String> replaces) {
                this.replaces = replaces;
            }


            public String getEquity_content() {
                return equity_content;
            }

            public void setEquity_content(String equity_content) {
                this.equity_content = equity_content;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public String getEquity_url() {
                return equity_url;
            }

            public void setEquity_url(String equity_url) {
                this.equity_url = equity_url;
            }

            public int getShowWen() {
                return showWen;
            }

            public void setShowWen(int showWen) {
                this.showWen = showWen;
            }

            public String getWenContent() {
                return wenContent;
            }

            public void setWenContent(String wenContent) {
                this.wenContent = wenContent;
            }
        }

        public static class EquityYetBean extends VipEquityBase implements Serializable {
            /**
             * equity_content : 特权一 免费领取并发货1件美衣
             * index : 0
             * equity_url : vip/vip_equity/freeEquity2/equity1.png
             * showWen : 0
             * wenContent : 待定
             */

            private String equity_content;
            private int index;
            private String equity_url;
            private int showWen;
            private String wenContent;

            private ArrayList<String> replaces;


            public ArrayList<String> getReplaces() {
                return replaces;
            }

            public void setReplaces(ArrayList<String> replaces) {
                this.replaces = replaces;
            }


            public String getEquity_content() {
                return equity_content;
            }

            public void setEquity_content(String equity_content) {
                this.equity_content = equity_content;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public String getEquity_url() {
                return equity_url;
            }

            public void setEquity_url(String equity_url) {
                this.equity_url = equity_url;
            }

            public int getShowWen() {
                return showWen;
            }

            public void setShowWen(int showWen) {
                this.showWen = showWen;
            }

            public String getWenContent() {
                return wenContent;
            }

            public void setWenContent(String wenContent) {
                this.wenContent = wenContent;
            }
        }
    }

    public static class UserVipListBean implements Serializable {
        /**
         * v_code : V200225KpmuagLn
         * vip_type : 4
         * bonus : 0.0
         * vip_num : 1
         * num : 1
         * vip_balance : 0.1
         * end_time : 1582616863000
         * count : 5
         * vip_price : 0.1
         * add_time : 1582615063000
         * vip_code : 2002250D7xaEAQ
         * price_section : 199.0
         */

        private String v_code;
        private int vip_type;
        private double bonus;
        private int vip_num;
        private int num;
        private double vip_balance;
        private long end_time;
        private int count;
        private double vip_price;
        private long add_time;
        private String vip_code;
        private String price_section;
        private String context;
        private String substance;
        private double arrears_price;


        public double getArrears_price() {
            return arrears_price;
        }

        public void setArrears_price(double arrears_price) {
            this.arrears_price = arrears_price;
        }


        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getSubstance() {
            return substance;
        }

        public void setSubstance(String substance) {
            this.substance = substance;
        }


        public String getV_code() {
            return v_code;
        }

        public void setV_code(String v_code) {
            this.v_code = v_code;
        }

        public int getVip_type() {
            return vip_type;
        }

        public void setVip_type(int vip_type) {
            this.vip_type = vip_type;
        }

        public double getBonus() {
            return bonus;
        }

        public void setBonus(double bonus) {
            this.bonus = bonus;
        }

        public int getVip_num() {
            return vip_num;
        }

        public void setVip_num(int vip_num) {
            this.vip_num = vip_num;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public double getVip_balance() {
            return vip_balance;
        }

        public void setVip_balance(double vip_balance) {
            this.vip_balance = vip_balance;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public double getVip_price() {
            return vip_price;
        }

        public void setVip_price(double vip_price) {
            this.vip_price = vip_price;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public String getVip_code() {
            return vip_code;
        }

        public void setVip_code(String vip_code) {
            this.vip_code = vip_code;
        }

        public String getPrice_section() {
            return price_section;
        }

        public void setPrice_section(String price_section) {
            this.price_section = price_section;
        }
    }
}
