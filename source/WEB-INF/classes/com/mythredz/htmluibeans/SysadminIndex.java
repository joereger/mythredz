package com.mythredz.htmluibeans;



import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: Joe Reger Jr
 * Date: Oct 4, 2007
 * Time: 7:21:25 PM
 */
public class SysadminIndex implements Serializable {

    private String financialStatsHtml = "";
    private String servermemory = "";

    public SysadminIndex(){

    }

    public void initBean(){
        StringBuffer fin = new StringBuffer();





        //Memory
        StringBuffer mb = new StringBuffer();
        Runtime rt = Runtime.getRuntime();


        double used = 0;
        double free = 0;
        double available = 0;

        used = rt.totalMemory()-rt.freeMemory();
        free = rt.freeMemory();
        available =  rt.maxMemory()-rt.totalMemory();

        double usedpercent = (used/rt.maxMemory()) * 100;
        double freepercent = (free/rt.maxMemory()) * 100;
        double availablepercent = (available/rt.maxMemory()) * 100;

        mb.append("<table cellpadding=0 cellspacing=1 border=4 width=100% >");
        mb.append("<tr>");
        mb.append("<td bgcolor=#ff0000 width="+(int)usedpercent+"% align=center>");
        mb.append("<font face=arial size=-2 color=#ffffff>");
        mb.append("U");
        mb.append("</font>");
        mb.append("</td>");
        mb.append("<td bgcolor=#00ff00 width="+(int)freepercent+"% align=center>");
        mb.append("<font face=arial size=-2 color=#ffffff>");
        mb.append("F");
        mb.append("</font>");
        mb.append("</td>");
        mb.append("<td bgcolor=#cccccc width="+(int)availablepercent+"% align=center>");
        mb.append("<font face=arial size=-2 color=#ffffff>");
        mb.append("A");
        mb.append("</font>");
        mb.append("</td>");
        mb.append("</tr>");
        mb.append("</table>");

        mb.append("<br>");

        mb.append((int)used + " <b>U</b>sed - "+(int)usedpercent+"%<br>");
        mb.append((int)free + " <b>F</b>ree - "+(int)freepercent+"%<br>");
        mb.append((int)available + " <b>A</b>vailable - "+(int)availablepercent+"%<br>");
        mb.append("<font class=\"smallfont\">");
        mb.append("Maximum memory available: " + rt.maxMemory() + "<br>");
        mb.append("Total memory allocated: " + rt.totalMemory() + "<br>");
        mb.append("Free memory unused: " + rt.freeMemory() + "<br>");
        mb.append("</font>");
        mb.append("<br>");
        
        servermemory = mb.toString();

        


    }


    public String getFinancialStatsHtml() {
        return financialStatsHtml;
    }

    public void setFinancialStatsHtml(String financialStatsHtml) {
        this.financialStatsHtml=financialStatsHtml;
    }

    public String getServermemory() {
        return servermemory;
    }

    public void setServermemory(String servermemory) {
        this.servermemory=servermemory;
    }
}
