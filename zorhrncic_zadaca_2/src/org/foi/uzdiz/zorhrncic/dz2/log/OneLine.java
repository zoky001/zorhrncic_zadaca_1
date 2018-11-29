/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.zorhrncic.dz2.log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Zoran
 */
public class OneLine {

    private Date date;
    private String data;
    private Long no;
    private SimpleDateFormat format = new SimpleDateFormat("dd. MM. yyyy HH:mm:ss.SSS");

    private boolean isStatistic = false;

    public OneLine() {
    }

    public OneLine(Date date, String data, Long no, boolean is) {
        this.date = date;
        this.data = data;
        this.no = no;
        this.isStatistic = is;
    }

    @Override
    public String toString() {
        return no + ".    " + format.format(date) + "     " + data;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public boolean isIsStatistic() {
        return isStatistic;
    }

    public void setIsStatistic(boolean isStatistic) {
        this.isStatistic = isStatistic;
    }

}
