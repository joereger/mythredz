<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmlui.Pagez" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "What is myThredz?";
String navtab = "whatis";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>
<%@ include file="/template/header.jsp" %>


    <font class="mediumfont"><b>A New Way To Exercise</b></font>
    <br/>
    <font class="smallfont">By building the workout into your day you maintain fitness more deeply without having to devote large chunks of time.</font>




<%@ include file="/template/footer.jsp" %>
