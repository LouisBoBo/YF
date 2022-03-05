package com.yssj.entity;

import java.util.List;

/**
 * Created by qingfeng on 2018/6/1.
 */

public class TestShopList {
    /**
     * pager : {"pageCount":1,"curPage":1,"pageSize":30,"rowCount":28}
     * status : 1
     * listShop : [{"shop_code":"YAIU1741032027","shop_name":"针织衫","shop_price":110.7,"shop_se_price":41,"def_pic":"goSWeF1a_600_900.jpg","virtual_sales":142,"shop_status":2,"kickback":13,"four_pic":"banner_goSWeF1a.jpg,share_goSWeF1a.jpg,share_goSWeF1a.jpg","supp_label":"Forever21","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"YAIU1741832033","shop_name":"衬衫","shop_price":218.4,"shop_se_price":52,"def_pic":"MBkmngU2_600_900.jpg","virtual_sales":141,"shop_status":2,"kickback":17,"four_pic":"banner_MBkmngU2.jpg,share_MBkmngU2.jpg,share_MBkmngU2.jpg","supp_label":"Forever21","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"YAIU1742332039","shop_name":"淑女绣花大摆裙纱网连衣裙","shop_price":447.1,"shop_se_price":117.7,"def_pic":"4Ov0x62m_600_900.jpg","virtual_sales":338,"shop_status":2,"kickback":40,"four_pic":"banner_4Ov0x62m.jpg,share_4Ov0x62m.jpg,share_4Ov0x62m.jpg","supp_label":"Forever21","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"YAIU1742332024","shop_name":"淑女绣花大摆裙纱网连衣裙","shop_price":380.5,"shop_se_price":140.9,"def_pic":"mZ40PAgc_600_900.jpg","virtual_sales":103,"shop_status":2,"kickback":49,"four_pic":"banner_mZ40PAgc.jpg,share_mZ40PAgc.jpg,share_mZ40PAgc.jpg","supp_label":"优衣库","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"YAIU1742332022","shop_name":"连衣裙","shop_price":242.2,"shop_se_price":59.1,"def_pic":"djvzj78u_600_900.jpg","virtual_sales":264,"shop_status":2,"kickback":20,"four_pic":"banner_djvzj78u.jpg,share_djvzj78u.jpg,share_djvzj78u.jpg","supp_label":"优衣库","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"YAIU1743832036","shop_name":"百搭植物花卉微喇裤短裤印染休闲裤","shop_price":159.9,"shop_se_price":39,"def_pic":"X4um3U0n_600_900.jpg","virtual_sales":181,"shop_status":2,"kickback":13,"four_pic":"banner_X4um3U0n.jpg,share_X4um3U0n.jpg,share_X4um3U0n.jpg","supp_label":"Forever21","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"YAIU1742332021","shop_name":"连衣裙","shop_price":264,"shop_se_price":66,"def_pic":"Ldw1UGeE_600_900.jpg","virtual_sales":405,"shop_status":2,"kickback":23,"four_pic":"banner_Ldw1UGeE.jpg,share_Ldw1UGeE.jpg,share_Ldw1UGeE.jpg","supp_label":"优衣库","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"YAIU1742132025","shop_name":"T恤","shop_price":117,"shop_se_price":45,"def_pic":"v4DB0hXA_600_900.jpg","virtual_sales":355,"shop_status":2,"kickback":15,"four_pic":"banner_v4DB0hXA.jpg,share_v4DB0hXA.jpg,share_v4DB0hXA.jpg","supp_label":"Forever21","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"YAIU17428332020","shop_name":"小衫","shop_price":382.2,"shop_se_price":91,"def_pic":"IULpoTm1_600_900.jpg","virtual_sales":179,"shop_status":2,"kickback":30,"four_pic":"banner_IULpoTm1.jpg,share_IULpoTm1.jpg,share_IULpoTm1.jpg","supp_label":"优衣库","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"1AAC163181121","shop_name":"衬衫","shop_price":213.3,"shop_se_price":79,"def_pic":"2dr1Oeq3_600_900.jpg","virtual_sales":122,"shop_status":2,"kickback":16,"four_pic":"banner_2dr1Oeq3.jpg,share_2dr1Oeq3.jpg,share_2dr1Oeq3.jpg","supp_label":"","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX1732435516","shop_name":"文艺复古植物花卉伞裙印染半身裙","shop_price":181.7,"shop_se_price":62.7,"def_pic":"4fM6RNJG_600_900.jpg","virtual_sales":343,"shop_status":2,"kickback":13,"four_pic":"banner_4fM6RNJG.jpg,share_4fM6RNJG.jpg,share_4fM6RNJG.jpg","supp_label":"Forever21","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX17429313456","shop_name":"街头U型领植物花卉八分袖卷边外套","shop_price":319.2,"shop_se_price":84,"def_pic":"nNZUTeQO_600_900.jpg","virtual_sales":191,"shop_status":2,"kickback":17,"four_pic":"banner_nNZUTeQO.jpg,share_nNZUTeQO.jpg,share_nNZUTeQO.jpg","supp_label":"La Babité","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX1732156605","shop_name":"学院圆领卡通动漫五分袖印染T恤","shop_price":190.3,"shop_se_price":76.1,"def_pic":"JHVwJkmb_600_900.jpg","virtual_sales":196,"shop_status":2,"kickback":15,"four_pic":"banner_JHVwJkmb.jpg,share_JHVwJkmb.jpg,share_JHVwJkmb.jpg","supp_label":"E·LAND","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX17328356593","shop_name":"田园一字领植物花卉七分袖露肩小衫","shop_price":219.7,"shop_se_price":84.5,"def_pic":"GTKxTsmW_600_900.jpg","virtual_sales":274,"shop_status":2,"kickback":17,"four_pic":"banner_GTKxTsmW.jpg,share_GTKxTsmW.jpg,share_GTKxTsmW.jpg","supp_label":"E·LAND","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX1732356601","shop_name":"极简纯色拼接款拼接连衣裙","shop_price":298.9,"shop_se_price":110.7,"def_pic":"s85iGRCZ_600_900.jpg","virtual_sales":166,"shop_status":2,"kickback":22,"four_pic":"banner_s85iGRCZ.jpg,share_s85iGRCZ.jpg,share_s85iGRCZ.jpg","supp_label":"E·LAND","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX1733838617","shop_name":"运动休闲条纹阔腿裤长裤口袋休闲裤","shop_price":450.4,"shop_se_price":102.4,"def_pic":"cpfu45jf_600_900.jpg","virtual_sales":471,"shop_status":2,"kickback":20,"four_pic":"banner_cpfu45jf.jpg,share_cpfu45jf.jpg,share_cpfu45jf.jpg","supp_label":"La Babité","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX1641818499","shop_name":"衬衫","shop_price":304.2,"shop_se_price":84.5,"def_pic":"HTes5Mvp_600_900.jpg","virtual_sales":314,"shop_status":2,"kickback":17,"four_pic":"banner_HTes5Mvp.jpg,share_HTes5Mvp.jpg,share_HTes5Mvp.jpg","supp_label":"","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX1641767412","shop_name":"吊带","shop_price":193.1,"shop_se_price":62.3,"def_pic":"0t6mkJgN_600_900.jpg","virtual_sales":360,"shop_status":2,"kickback":12,"four_pic":"banner_0t6mkJgN.jpg,share_0t6mkJgN.jpg,share_0t6mkJgN.jpg","supp_label":"","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX1642167411","shop_name":"T恤","shop_price":186.9,"shop_se_price":45.6,"def_pic":"F2PhLHDy_600_900.jpg","virtual_sales":367,"shop_status":2,"kickback":9,"four_pic":"banner_F2PhLHDy.jpg,share_F2PhLHDy.jpg,share_F2PhLHDy.jpg","supp_label":"","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX1641867419","shop_name":"衬衫","shop_price":253.5,"shop_se_price":84.5,"def_pic":"bRofYr6W_600_900.jpg","virtual_sales":137,"shop_status":2,"kickback":17,"four_pic":"banner_bRofYr6W.jpg,share_bRofYr6W.jpg,share_bRofYr6W.jpg","supp_label":"","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX1641820058","shop_name":"衬衫","shop_price":183.7,"shop_se_price":70.6,"def_pic":"PH1GPsrE_600_900.jpg","virtual_sales":198,"shop_status":2,"kickback":14,"four_pic":"banner_PH1GPsrE.jpg,share_PH1GPsrE.jpg,share_PH1GPsrE.jpg","supp_label":"","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX1642120042","shop_name":"T恤","shop_price":154.7,"shop_se_price":57.3,"def_pic":"m6LzNlcv_600_900.jpg","virtual_sales":195,"shop_status":2,"kickback":11,"four_pic":"banner_m6LzNlcv.jpg,share_m6LzNlcv.jpg,share_m6LzNlcv.jpg","supp_label":"","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX1642120038","shop_name":"T恤","shop_price":219,"shop_se_price":57.6,"def_pic":"jL5bwPKd_600_900.jpg","virtual_sales":116,"shop_status":2,"kickback":11,"four_pic":"banner_jL5bwPKd.jpg,share_jL5bwPKd.jpg,share_jL5bwPKd.jpg","supp_label":"","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX1642157695","shop_name":"T恤","shop_price":279.9,"shop_se_price":82.3,"def_pic":"Dwqm4IAv_600_900.jpg","virtual_sales":301,"shop_status":2,"kickback":16,"four_pic":"banner_Dwqm4IAv.jpg,share_Dwqm4IAv.jpg,share_Dwqm4IAv.jpg","supp_label":"","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX17328335528","shop_name":"极简圆领纯色五分袖镂空小衫","shop_price":193.3,"shop_se_price":77.3,"def_pic":"t2KioDlT_600_900.jpg","virtual_sales":395,"shop_status":2,"kickback":15,"four_pic":"banner_t2KioDlT.jpg,share_t2KioDlT.jpg,share_t2KioDlT.jpg","supp_label":"Forever21","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX1732135523","shop_name":"学院圆领卡通动漫五分袖印染T恤","shop_price":234.5,"shop_se_price":75.6,"def_pic":"v2VNLqk8_600_900.jpg","virtual_sales":172,"shop_status":2,"kickback":15,"four_pic":"banner_v2VNLqk8.jpg,share_v2VNLqk8.jpg,share_v2VNLqk8.jpg","supp_label":"Forever21","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX1732335541","shop_name":"甜美纯色百褶裙蝴蝶结连衣裙","shop_price":327.2,"shop_se_price":99.2,"def_pic":"Jf9GRp8Y_600_900.jpg","virtual_sales":234,"shop_status":2,"kickback":20,"four_pic":"banner_Jf9GRp8Y.jpg,share_Jf9GRp8Y.jpg,share_Jf9GRp8Y.jpg","supp_label":"Forever21","app_shop_group_price":99,"wxcx_shop_group_price":-1},{"shop_code":"CAAX1733835527","shop_name":"极简纯色阔腿裤长裤口袋休闲裤","shop_price":293,"shop_se_price":86.2,"def_pic":"9UBZ8jO0_600_900.jpg","virtual_sales":281,"shop_status":2,"kickback":17,"four_pic":"banner_9UBZ8jO0.jpg,share_9UBZ8jO0.jpg,share_9UBZ8jO0.jpg","supp_label":"Forever21","app_shop_group_price":99,"wxcx_shop_group_price":-1}]
     */

    private PagerBean pager;
    private String status;
    private List<ListShopBean> listShop;

    public PagerBean getPager() {
        return pager;
    }

    public void setPager(PagerBean pager) {
        this.pager = pager;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ListShopBean> getListShop() {
        return listShop;
    }

    public void setListShop(List<ListShopBean> listShop) {
        this.listShop = listShop;
    }

    public static class PagerBean {
        /**
         * pageCount : 1
         * curPage : 1
         * pageSize : 30
         * rowCount : 28
         */

        private int pageCount;
        private int curPage;
        private int pageSize;
        private int rowCount;

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getRowCount() {
            return rowCount;
        }

        public void setRowCount(int rowCount) {
            this.rowCount = rowCount;
        }
    }

    public static class ListShopBean {
        /**
         * shop_code : YAIU1741032027
         * shop_name : 针织衫
         * shop_price : 110.7
         * shop_se_price : 41.0
         * def_pic : goSWeF1a_600_900.jpg
         * virtual_sales : 142
         * shop_status : 2
         * kickback : 13.0
         * four_pic : banner_goSWeF1a.jpg,share_goSWeF1a.jpg,share_goSWeF1a.jpg
         * supp_label : Forever21
         * app_shop_group_price : 99.0
         * wxcx_shop_group_price : -1.0
         */

        private String shop_code;
        private String shop_name;
        private String shop_price;
        private String shop_se_price;
        private String def_pic;
        private int virtual_sales;
        private int shop_status;
        private double kickback;
        private String four_pic;
        private String supp_label;
        private String app_shop_group_price;
        private String wxcx_shop_group_price;

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

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getShop_se_price() {
            return shop_se_price;
        }

        public void setShop_se_price(String shop_se_price) {
            this.shop_se_price = shop_se_price;
        }

        public String getDef_pic() {
            return def_pic;
        }

        public void setDef_pic(String def_pic) {
            this.def_pic = def_pic;
        }

        public int getVirtual_sales() {
            return virtual_sales;
        }

        public void setVirtual_sales(int virtual_sales) {
            this.virtual_sales = virtual_sales;
        }

        public int getShop_status() {
            return shop_status;
        }

        public void setShop_status(int shop_status) {
            this.shop_status = shop_status;
        }

        public double getKickback() {
            return kickback;
        }

        public void setKickback(double kickback) {
            this.kickback = kickback;
        }

        public String getFour_pic() {
            return four_pic;
        }

        public void setFour_pic(String four_pic) {
            this.four_pic = four_pic;
        }

        public String getSupp_label() {
            return supp_label;
        }

        public void setSupp_label(String supp_label) {
            this.supp_label = supp_label;
        }

        public String getApp_shop_group_price() {
            return app_shop_group_price;
        }

        public void setApp_shop_group_price(String app_shop_group_price) {
            this.app_shop_group_price = app_shop_group_price;
        }

        public String getWxcx_shop_group_price() {
            return wxcx_shop_group_price;
        }

        public void setWxcx_shop_group_price(String wxcx_shop_group_price) {
            this.wxcx_shop_group_price = wxcx_shop_group_price;
        }
    }
}
