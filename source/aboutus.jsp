<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmlui.Pagez" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "About Us";
String navtab = "home";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>
<%@ include file="/template/header.jsp" %>


    <font class="mediumfont"><b>We're Awesome.</b>  What else could you possibly want to know?  It's a hobby project right now.  Write us a check and it'll be a funded hobby... I mean, we'll be a funded Web 2.0 startup.</font>




<%@ include file="/template/footer.jsp" %>
