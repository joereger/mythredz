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
            }
        }
        out.print("Thredz have been saved!<br/>");
    }
%>

<%//@ include file="/template/header.jsp" %>





    <form id="myForm" action="/account/mobile.jsp" method="post">
        <input type="hidden" name="dpage" value="/account/mobile.jsp">
        <input type="hidden" name="action" value="save">


    <input type="submit" class="formsubmitbutton" value="Save myThredz"><br/><br/>


    <%
        List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
                .add(Restrictions.eq("userid", Pagez.getUserSession().getUser().getUserid()))
                .setCacheable(true)
                .list();
        for (Iterator<Thred> iterator=threds.iterator(); iterator.hasNext();) {
            Thred thred=iterator.next();
    %>

                <%=thred.getName()%><br/>
                <textarea name="threadid-<%=thred.getThredid()%>" rows="5" cols="5" style="width: 100%;"></textarea>
                <br/><br/>
            <%

        }
    %>



    <input type="submit" class="formsubmitbutton" value="Save myThredz">


    </form>


<%//@ include file="/template/footer.jsp" %>


