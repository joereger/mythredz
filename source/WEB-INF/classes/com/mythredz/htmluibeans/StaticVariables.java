package com.mythredz.htmluibeans;


import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.TreeMap;


/**
 * User: Joe Reger Jr
 * Date: Mar 27, 2007
 * Time: 1:56:56 PM
 */
public class StaticVariables {


    public static TreeMap<String, String> getPercentiles(){
        TreeMap<String, String> out = new TreeMap<String, String>();
        out.put(String.valueOf(1), "Top 1%");
        out.put(String.valueOf(5), "Top 5%");
        out.put(String.valueOf(10), "Top 10%");
        out.put(String.valueOf(25), "Top 25%");
        out.put(String.valueOf(50), "Top 50%");
        out.put(String.valueOf(100), "Everybody");
        return out;
    }

    public static TreeMap<String, String> getFundsTypes(){
        TreeMap<String, String> out = new TreeMap<String, String>();
        out.put(String.valueOf(1), "A Funds");
        out.put(String.valueOf(2), "B Funds");
        out.put(String.valueOf(3), "C Funds");
        out.put(String.valueOf(4), "D Funds");
        return out;
    }

    public static TreeMap<String, String> getBlogqualities(){
        TreeMap<String, String> out = new TreeMap<String, String>();
        out.put("0", "0 - Undefined");
        out.put("1", "1 - Low");
        out.put("2", String.valueOf(2));
        out.put("3", String.valueOf(3));
        out.put("4", String.valueOf(4));
        out.put("5", "5 - Average");
        out.put("6", String.valueOf(6));
        out.put("7", String.valueOf(7));
        out.put("8", "6");
        out.put("9", String.valueOf(9));
        out.put("10", "10 - High");
        return out;
    }


}
