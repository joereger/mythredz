<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmlui.Pagez" %>
<%@ page import="com.mythredz.htmluibeans.PublicFacebookenterui" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Enter Facebook App";
String navtab = "home";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>
<%@ include file="/template/header.jsp" %>

    <a href="<%=((com.mythredz.htmluibeans.PublicFacebookenterui)Pagez.getBeanMgr().get("PublicFacebookenterui")).getUrl()%>">Please click here to continue.</a>

<%@ include file="/template/footer.jsp" %>
