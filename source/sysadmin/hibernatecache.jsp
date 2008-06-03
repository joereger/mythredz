<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmlui.Pagez" %>
<%@ page import="com.mythredz.htmluibeans.SysadminHibernateCache" %>
<%@ page import="com.mythredz.dao.hibernate.HibernateCacheStats" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Cache";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
SysadminHibernateCache sysadminHibernateCache = (SysadminHibernateCache)Pagez.getBeanMgr().get("SysadminHibernateCache");
%>
<%@ include file="/template/header.jsp" %>



    <a href="/sysadmin/hibernatecache.jsp?action=showmisc"><font class="formfieldnamefont">Show Misc Cache</font></a><br/>
    <a href="/sysadmin/hibernatecache.jsp?action=showhibernate"><font class="formfieldnamefont">Show Hibernate Cache</font></a><br/>
    <br/>




        <%if (request.getParameter("action")!=null && request.getParameter("action").equals("showhibernate")){%>
            <font style="font-size: 15px;">Hibernate Cache</font><br/>
            <%=HibernateCacheStats.getGeneralStats()%>
            <br/>
            <%=sysadminHibernateCache.getCacheashtml()%>
        <%}%>

        <%if (request.getParameter("action")!=null && request.getParameter("action").equals("showmisc")){%>
            <font style="font-size: 15px;">Misc Cache</font><br/>
            <%=sysadminHibernateCache.getMisccacheashtml()%>
        <%}%>


  



<%@ include file="/template/footer.jsp" %>