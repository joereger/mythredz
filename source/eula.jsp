<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmlui.Pagez" %>
<%@ page import="com.mythredz.htmluibeans.PublicEula" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Terms of Use and Privacy Statement";
String navtab = "home";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>
<%
PublicEula publicEula = (PublicEula)Pagez.getBeanMgr().get("PublicEula");
%>
<%@ include file="/template/header.jsp" %>

    <center>
    <textarea rows="15" cols="80"><%=publicEula.getEula()%></textarea>
    </center>

<%@ include file="/template/footer.jsp" %>
