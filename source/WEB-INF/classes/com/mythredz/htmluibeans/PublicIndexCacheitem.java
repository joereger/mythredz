package com.mythredz.htmluibeans;

import java.util.Calendar;

/**
 * User: Joe Reger Jr
 * Date: Jan 29, 2008
 * Time: 1:43:02 PM
 */
public class PublicIndexCacheitem {

    private String html;
    private Calendar lastupdated;


    public PublicIndexCacheitem(String html, Calendar lastupdated) {
        this.html = html;
        this.lastupdated = lastupdated;
    }


    public String getHtml() {
        return html;
    }

    public Calendar getLastupdated() {
        return lastupdated;
    }
}
