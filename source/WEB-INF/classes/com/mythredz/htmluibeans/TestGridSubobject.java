package com.mythredz.htmluibeans;

import java.util.Calendar;

/**
 * User: Joe Reger Jr
 * Date: Nov 7, 2007
 * Time: 3:14:39 PM
 */
public class TestGridSubobject {

    private Calendar cal;
    private String age;

    public TestGridSubobject(Calendar cal, String age){
        this.cal = cal;
        this.age = age;    
    }


    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal=cal;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age=age;
    }
}
