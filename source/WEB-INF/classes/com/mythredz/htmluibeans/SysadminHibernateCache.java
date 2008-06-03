package com.mythredz.htmluibeans;

import com.mythredz.dao.hibernate.HibernateCacheStats;
import com.mythredz.cache.providers.CacheFactory;

import com.mythredz.htmlui.Pagez;

import java.io.Serializable;


import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Nov 28, 2006
 * Time: 12:30:25 PM
 */
public class SysadminHibernateCache implements Serializable {

    private String cacheashtml;
    private String misccacheashtml;
    private String iaosqueue;

    public SysadminHibernateCache(){

    }



    public void initBean(){
        if (Pagez.getRequest().getParameter("action")!=null && Pagez.getRequest().getParameter("action").equals("showhibernate")){

            if (Pagez.getRequest().getParameter("region")!=null){
                cacheashtml = HibernateCacheStats.getEntriesForARegion(Pagez.getRequest().getParameter("region"), true);    
            } else {
                cacheashtml = HibernateCacheStats.getCacheDump();
            }
        }
        if (Pagez.getRequest().getParameter("action")!=null && Pagez.getRequest().getParameter("action").equals("showmisc")){
            misccacheashtml = CacheFactory.getCacheProvider().getCacheStatsAsHtml();
        }
        loadIaos();
    }

    private void loadIaos(){

    }


    public String getCacheashtml() {
        return cacheashtml;
    }

    public void setCacheashtml(String cacheashtml) {
        this.cacheashtml = cacheashtml;
    }

    public String getMisccacheashtml() {
        return misccacheashtml;
    }

    public void setMisccacheashtml(String misccacheashtml) {
        this.misccacheashtml = misccacheashtml;
    }

    public String getIaosqueue() {
        return iaosqueue;
    }

    public void setIaosqueue(String iaosqueue) {
        this.iaosqueue = iaosqueue;
    }
}
