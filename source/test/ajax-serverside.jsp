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
<%@ page import="com.mythredz.htmlui.Pagez" %>
<%
    //Hide from snooping eyes... only sysadmins can play
    UserSession userSession=(UserSession) session.getAttribute("userSession");
    userSession=Pagez.getUserSession();
    if (userSession == null || !userSession.getIsloggedin() || !Authorization.isUserSysadmin(userSession.getUser())) {
        response.sendRedirect("/");
        return;
    }
%>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
%>


This is server-side java. Rock on <%=request.getParameter("first_name")%>!