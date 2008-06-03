package com.mythredz.test;

import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.List;
import java.util.Iterator;
import java.util.Calendar;

import com.mythredz.util.Time;

/**
 * User: Joe Reger Jr
 * Date: Sep 18, 2006
 * Time: 9:45:26 AM
 */
public class TestRunner {

    public String runTests(){
        StringBuffer out = new StringBuffer();
        out.append("Test run on: "+ Time.dateformatcompactwithtime(Calendar.getInstance())+"<br/>");
        //List of tests to be run
        out.append(printFailures(runSingleTest(TestTest.class)));
        return out.toString();
    }

    private List<Failure> runSingleTest(java.lang.Class clazz){
        org.junit.runner.JUnitCore testRunner = new org.junit.runner.JUnitCore();
        Result result = testRunner.run(clazz);
        return result.getFailures();
    }

    private String printFailures(List<Failure> failures){
        StringBuffer out = new StringBuffer();
        out.append("<font face=arial size=-2>");
        for (Iterator<Failure> iterator = failures.iterator(); iterator.hasNext();) {
            Failure failure = iterator.next();
            out.append("<font face=arial size=+1>");
            out.append("<b>");
            out.append(failure.getDescription());
            out.append("</b>");
            out.append("</font>");
            out.append("<br>");
            out.append(failure.getTestHeader());
            out.append("<br>");
            out.append(failure.getMessage());
            out.append("<br>");
            out.append("<textarea cols=90 rows=8 style=\"font-size: 10px;\">");
            out.append(failure.getTrace());
            out.append("</textarea>");
            out.append("<br><br>");
        }
        out.append("</font>");
        return out.toString();
    }



}
