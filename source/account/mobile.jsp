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
<%@ page import="com.mythredz.util.Str" %>
<%@ page import="com.mythredz.twitter.TwitterUpdate" %>
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
                //Update twitter
                if (thred.getIstwitterupdateon()) {
                    String posttotwitter=Pagez.getRequest().getParameter("threadid" + thred.getThredid() + "posttotwitter");
                    if (posttotwitter != null && posttotwitter.trim().equals("1")) {
                        TwitterUpdate tu=new TwitterUpdate(thred.getTwitterid(), thred.getTwitterpass(), Str.truncateString(post.getContents(), 160));
                        tu.update();
                    }
                }
            }
        }
        out.print("Thredz have been saved!<br/>");
    }
%>

<%//@ include file="/template/header.jsp" %>




    <form id="myForm" action="/account/mobile.jsp" method="post">
        <input type="hidden" name="dpage" value="/account/mobile.jsp">
        <input type="hidden" name="action" value="save">


    <input type="submit" class="formsubmitbutton" value="Save myThredz"><br/>


    <%
        List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
                .add(Restrictions.eq("userid", Pagez.getUserSession().getUser().getUserid()))
                .setCacheable(true)
                .list();
        for (Iterator<Thred> iterator=threds.iterator(); iterator.hasNext();) {
            Thred thred=iterator.next();
    %>

                <%=thred.getName()%><br/>
                <%if (thred.getIstwitterupdateon()){%>
                    <input type="text" name="threadid-<%=thred.getThredid()%>" maxlength="160" size="20"><br/><input type="checkbox" name="threadid<%=thred.getThredid()%>posttotwitter" value="1" checked /> Update Twitter Status
                <%} else {%>
                    <textarea name="threadid-<%=thred.getThredid()%>" rows="1" cols="20"></textarea>
                <%}%>
                <br/>
            <%

        }
    %>



    <input type="submit" class="formsubmitbutton" value="Save myThredz">


    </form>


<%//@ include file="/template/footer.jsp" %>


