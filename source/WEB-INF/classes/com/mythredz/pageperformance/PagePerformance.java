package com.mythredz.pageperformance;

/**
 * User: Joe Reger Jr
 * Date: Mar 9, 2008
 * Time: 11:37:05 AM
 */
public class PagePerformance {

    private String pageid;
    private int year;
    private int month;
    private int day;
    private String partofday;
    private String servername;
    private long totaltime;
    private int totalpageloads;




    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }


    public String getPartofday() {
        return partofday;
    }

    public void setPartofday(String partofday) {
        this.partofday = partofday;
    }

    public String getServername() {
        return servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public long getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(long totaltime) {
        this.totaltime = totaltime;
    }

    public int getTotalpageloads() {
        return totalpageloads;
    }

    public void setTotalpageloads(int totalpageloads) {
        this.totalpageloads = totalpageloads;
    }
}
