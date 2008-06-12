<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.AccountIndex" %>
<%@ page import="com.mythredz.htmluibeans.AccountBalance" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mythredz.dao.hibernate.HibernateUtil" %>
<%@ page import="com.mythredz.dao.Thred" %>
<%@ page import="org.hibernate.criterion.Restrictions" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.mythredz.dao.Post" %>
<%@ page import="com.mythredz.util.Time" %>
<%@ page import="org.hibernate.criterion.Order" %>
<%@ page import="com.mythredz.cache.providers.CacheFactory" %>
<%@ page import="com.mythredz.systemprops.SystemProperty" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "";
String navtab = "mythredz";
String acl = "account";
%>

<%@ include file="/template/auth.jsp" %>

<%
    if (Pagez.getRequest().getParameter("action") != null && Pagez.getRequest().getParameter("action").equals("save")) {
        List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
                .add(Restrictions.eq("userid", Pagez.getUserSession().getUser().getUserid()))
                .setCacheable(true)
                .list();
        for (Iterator<Thred> iterator=threds.iterator(); iterator.hasNext();) {
            Thred thred=iterator.next();
            if (Pagez.getRequest().getParameter("threadid-" + thred.getThredid()) != null && !Pagez.getRequest().getParameter("threadid-" + thred.getThredid()).trim().equals("")) {
                if (Pagez.getUserSession().getUser().getUserid()==thred.getUserid()){
                    String in=Pagez.getRequest().getParameter("threadid-" + thred.getThredid()).trim();
                    Post post=new Post();
                    post.setContents(in);
                    post.setDate(new java.util.Date());
                    post.setThredid(thred.getThredid());
                    try {
                        post.save();
                    } catch (Exception ex) {
                        logger.error("", ex);
                    }
                    //Clear the Javascript Embed cache
                    String nameInCache="embedjavascriptservlet-u" + Pagez.getUserSession().getUser().getUserid() + "-makeHttpsIfSSLIsOn" + false;
                    String cacheGroup="embedjavascriptcache" + "/";
                    CacheFactory.getCacheProvider().flush(nameInCache, cacheGroup);
                    String nameInCacheVert="embedjavascriptverticalservlet-u" + Pagez.getUserSession().getUser().getUserid() + "-makeHttpsIfSSLIsOn" + false;
                    String cacheGroupVert="embedjavascriptcache" + "/";
                    CacheFactory.getCacheProvider().flush(nameInCacheVert, cacheGroupVert);

                }
            }
        }
        //Redir to calling page
        String referer = request.getHeader("referer");
        if (referer!=null){
            Pagez.sendRedirect(referer);
            return;
        }
        //Pagez.getUserSession().setMessage("Thredz have been saved!");
    }
%>

<%//@ include file="/template/header.jsp" %>





<%//@ include file="/template/footer.jsp" %>


