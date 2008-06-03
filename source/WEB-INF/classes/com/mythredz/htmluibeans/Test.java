package com.mythredz.htmluibeans;

import com.mythredz.htmlui.HtmlUiBean;
import com.mythredz.util.Time;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Calendar;

/**
 * User: Joe Reger Jr
 * Date: Oct 28, 2007
 * Time: 5:28:57 AM
 */
public class Test implements HtmlUiBean {

    private String textbox;
    private String textarea;
    private String dropdown;
    private ArrayList<String> dropdownmultiselect;
    private ArrayList<String> checkboxesvalues;
    private boolean booleantest= false;
    private Calendar cal;
    private Calendar cal2;

    public Test(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("initialized");
    }

    public void initBean() {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("initBean() called");
        booleantest= true;
        textbox = "booyah";
        dropdown = "b";
        checkboxesvalues = new ArrayList<String>();
        checkboxesvalues.add("bradley");
        checkboxesvalues.add("charcoal");
        dropdownmultiselect = new ArrayList<String>();
        dropdownmultiselect.add("donkey");
        dropdownmultiselect.add("snake");
        textarea="megawoowoo";
        cal = Calendar.getInstance();
        cal2 = Calendar.getInstance();
    }

    public void save(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("Saving...");
        logger.debug("textbox="+textbox);
        logger.debug("textarea="+textarea);
        logger.debug("dropdown="+dropdown);
        for (Iterator<String> iterator = checkboxesvalues.iterator(); iterator.hasNext();) {
            String s = iterator.next();
            logger.debug("checkboxesvalues: "+s);
        }
        for (Iterator<String> iterator = dropdownmultiselect.iterator(); iterator.hasNext();) {
            String s = iterator.next();
            logger.debug("dropdownmultiselect: "+s);
        }
        if (booleantest){
            logger.debug("booleantest is true");
        } else {
            logger.debug("booleantest is false");        
        }
        logger.debug("cal="+ Time.dateformatfordb(cal));
        logger.debug("cal2="+ Time.dateformatfordb(cal2));
        logger.debug("Done saving.");
    }


    public String getTextbox() {
        return textbox;
    }

    public void setTextbox(String textbox) {
        this.textbox = textbox;
    }

    public String getDropdown() {
        return dropdown;
    }

    public void setDropdown(String dropdown) {
        this.dropdown = dropdown;
    }

    public boolean getBooleantest() {
        return booleantest;
    }

    public void setBooleantest(boolean booleantest) {
        this.booleantest=booleantest;
    }

    public ArrayList<String> getCheckboxesvalues() {
        return checkboxesvalues;
    }

    public void setCheckboxesvalues(ArrayList<String> checkboxesvalues) {
        this.checkboxesvalues = checkboxesvalues;
    }

    public String getTextarea() {
        return textarea;
    }

    public void setTextarea(String textarea) {
        this.textarea = textarea;
    }

    public ArrayList<String> getDropdownmultiselect() {
        return dropdownmultiselect;
    }

    public void setDropdownmultiselect(ArrayList<String> dropdownmultiselect) {
        this.dropdownmultiselect=dropdownmultiselect;
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal=cal;
    }

    public Calendar getCal2() {
        return cal2;
    }

    public void setCal2(Calendar cal2) {
        this.cal2=cal2;
    }
}
