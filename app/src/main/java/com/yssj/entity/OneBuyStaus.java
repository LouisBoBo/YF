/**
  * Copyright 2018 bejson.com 
  */
package com.yssj.entity;

/**
 * Auto-generated: 2018-03-29 12:38:28
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class OneBuyStaus {

    private String status;
    private Data data;
    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

    @Override
    public String toString() {
        return "OneBuyStaus{" +
                "status='" + status + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}