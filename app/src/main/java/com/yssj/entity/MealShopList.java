package com.yssj.entity;

import java.util.List;

/**
 * Created by qingfeng on 2018/5/21.
 */

public class MealShopList {
    /**
     * pList : [{"add_date":1526894777576,"code":"PS20180521IqurRU0N","content":"更好发挥","def_pic":"packageShop/2018-05-21/17_26_17.jpg","name":"超值单品","num":100,"p_status":0,"postage":1,"price":1,"r_num":98,"shop_list":[{"four_pic":"share_99Ph9K1K.jpg","shop_code":"YAIU20180521N9O4VSgW","shop_name":"测试","shop_price":27,"shop_se_price":1}],"virtual_sales":392}]
     * pager : {"curPage":1,"pageCount":1,"pageSize":9,"rowCount":0}
     * shopList : []
     * status : 1
     * sys_time : 1526894896148
     */

    private PagerBean pager;
    private String status;
    private long sys_time;
    private List<PListBean> pList;
    private List<?> shopList;

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

    public long getSys_time() {
        return sys_time;
    }

    public void setSys_time(long sys_time) {
        this.sys_time = sys_time;
    }

    public List<PListBean> getPList() {
        return pList;
    }

    public void setPList(List<PListBean> pList) {
        this.pList = pList;
    }

    public List<?> getShopList() {
        return shopList;
    }

    public void setShopList(List<?> shopList) {
        this.shopList = shopList;
    }

    public static class PagerBean {
        /**
         * curPage : 1
         * pageCount : 1
         * pageSize : 9
         * rowCount : 0
         */

        private int curPage;
        private int pageCount;
        private int pageSize;
        private int rowCount;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
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

    public static class PListBean {
        /**
         * add_date : 1526894777576
         * code : PS20180521IqurRU0N
         * content : 更好发挥
         * def_pic : packageShop/2018-05-21/17_26_17.jpg
         * name : 超值单品
         * num : 100
         * p_status : 0
         * postage : 1.0
         * price : 1.0
         * r_num : 98
         * shop_list : [{"four_pic":"share_99Ph9K1K.jpg","shop_code":"YAIU20180521N9O4VSgW","shop_name":"测试","shop_price":27,"shop_se_price":1}]
         * virtual_sales : 392
         */

        private long add_date;
        private String code;
        private String content;
        private String def_pic;
        private String name;
        private int num;
        private int p_status;
        private String postage;
        private String price;

        public String getShop_se_price() {
            return shop_se_price;
        }

        public void setShop_se_price(String shop_se_price) {
            this.shop_se_price = shop_se_price;
        }

        private String shop_se_price;
        private int r_num;
        private int virtual_sales;
        private List<ShopListBean> shop_list;



        private String app_shop_group_price = "1.5";

        public String getApp_shop_group_price() {
            return app_shop_group_price;
        }

        public void setApp_shop_group_price(String app_shop_group_price) {
            this.app_shop_group_price = app_shop_group_price;
        }

        private String wxcx_shop_group_price;


        public long getAdd_date() {
            return add_date;
        }

        public void setAdd_date(long add_date) {
            this.add_date = add_date;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDef_pic() {
            return def_pic;
        }

        public void setDef_pic(String def_pic) {
            this.def_pic = def_pic;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getP_status() {
            return p_status;
        }

        public void setP_status(int p_status) {
            this.p_status = p_status;
        }

        public String getPostage() {
            return postage;
        }

        public void setPostage(String postage) {
            this.postage = postage;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getR_num() {
            return r_num;
        }

        public void setR_num(int r_num) {
            this.r_num = r_num;
        }

        public int getVirtual_sales() {
            return virtual_sales;
        }

        public void setVirtual_sales(int virtual_sales) {
            this.virtual_sales = virtual_sales;
        }

        public List<ShopListBean> getShop_list() {
            return shop_list;
        }

        public void setShop_list(List<ShopListBean> shop_list) {
            this.shop_list = shop_list;
        }

        public class ShopListBean {
            /**
             * four_pic : share_99Ph9K1K.jpg
             * shop_code : YAIU20180521N9O4VSgW
             * shop_name : 测试
             * shop_price : 27.0
             * shop_se_price : 1.0
             */

            private String four_pic;
            private String shop_code;
            private String shop_name;
            private String shop_price;
            private String shop_se_price;


            private String app_shop_group_price = "1.5";

            public String getApp_shop_group_price() {
                return app_shop_group_price;
            }

            public void setApp_shop_group_price(String app_shop_group_price) {
                this.app_shop_group_price = app_shop_group_price;
            }


            public int getMealVirtual_sales() {
                return getVirtual_sales();
            }

            private String wxcx_shop_group_price;

            public String getAll_shop_code() {
                return all_shop_code;
            }

            public void setAll_shop_code(String all_shop_code) {
                this.all_shop_code = all_shop_code;
            }

            private String all_shop_code;

            public String getFour_pic() {
                return four_pic;
            }

            public void setFour_pic(String four_pic) {
                this.four_pic = four_pic;
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
        }
    }
}
