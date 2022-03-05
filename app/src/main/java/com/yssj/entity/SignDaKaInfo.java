package com.yssj.entity;

import java.util.List;

public class SignDaKaInfo {
    /**
     * data : {"clock_in_start_date":1568103262452,"clock_in_last_date":1568103262452,"list":[10]}
     * status : 1
     */

    private DataBean data;
    private String status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * clock_in_start_date : 1568103262452
         * clock_in_last_date : 1568103262452
         * list : [10]
         */

        private long clock_in_start_date;
        private long clock_in_last_date;
        private List<Integer> list;

        public long getClock_in_start_date() {
            return clock_in_start_date;
        }

        public void setClock_in_start_date(long clock_in_start_date) {
            this.clock_in_start_date = clock_in_start_date;
        }

        public long getClock_in_last_date() {
            return clock_in_last_date;
        }

        public void setClock_in_last_date(long clock_in_last_date) {
            this.clock_in_last_date = clock_in_last_date;
        }

        public List<Integer> getList() {
            return list;
        }

        public void setList(List<Integer> list) {
            this.list = list;
        }
    }
}
