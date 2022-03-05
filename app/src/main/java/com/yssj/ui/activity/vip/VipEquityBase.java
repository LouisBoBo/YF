package com.yssj.ui.activity.vip;

import java.io.Serializable;
import java.util.ArrayList;

public class VipEquityBase implements Serializable {
    private String equity_content;
    private int index;
    private String equity_url;

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

    private ArrayList<String> replaces;


    public ArrayList<String> getReplaces() {
        return replaces;
    }

    public void setReplaces(ArrayList<String> replaces) {
        this.replaces = replaces;
    }

    private int showWen;
    private String wenContent;

}
