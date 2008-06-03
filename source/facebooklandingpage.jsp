<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmlui.Pagez" %>
<%@ page import="com.mythredz.htmluibeans.PublicFacebookLandingPage" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Social Surveys";
String navtab = "home";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>
<%@ include file="/template/header.jsp" %>

    <a href="<%=((PublicFacebookLandingPage)Pagez.getBeanMgr().get("PublicFacebookLandingPage")).getUrltoredirectto()%>">Click here please.</a>

<%@ include file="/template/footer.jsp" %>