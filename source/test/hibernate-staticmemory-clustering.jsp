<%@ page import="java.util.List" %>
<%@ page import="com.mythredz.dao.hibernate.HibernateUtil" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.mythredz.test.HibernateStaticMemoryClustering" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.util.Time" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.mythredz.htmlui.UserSession" %>
<%@ page import="com.mythredz.htmlui.Authorization" %>
<%@ page import="com.mythredz.util.Str" %>
<%
//Hide from snooping eyes... only sysadmins can play
UserSession userSession = (UserSession) session.getAttribute("userSession");
if (userSession == null || !userSession.getIsloggedin() || !Authorization.isUserSysadmin(userSession.getUser())) {
    response.sendRedirect("/");
    return;
}
%>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
%>




<a href="hibernate-staticmemory-clustering.jsp">Refresh</a><br/><br/>
<a href="hibernate-staticmemory-clustering.jsp?action=loadtomemory">Load to Memory</a><br/>
<a href="hibernate-staticmemory-clustering.jsp?action=editlocalinstances">Edit Local Instance (not objs in memory)</a><br/>
<a href="hibernate-staticmemory-clustering.jsp?action=saveallinmemory">Call save() on all in Memory</a><br/>

