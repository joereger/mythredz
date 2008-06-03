<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmlui.Pagez" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Support Note Submitted";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%@ include file="/template/header.jsp" %>



        Success!  Your comment has been added.
        <br/><br/>
        <a href="/sysadmin/sysadminsupportissueslist.jsp">Continue</a>




<%@ include file="/template/footer.jsp" %>


