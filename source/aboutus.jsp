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

    <br/><br/>
    <font class="mediumfont"><b>We're Frickin' Awesome.</b>  What else could you possibly want to know?  It's a hobby project right now.  Write us a check and it'll be a funded hobby project. Strike that... write us a check and it'll be a hyper-official card-carrying Web 2.0 Startup, complete with insane monetization models, quote-unquote conservative projections and lightweight development methodologies.</font>




<%@ include file="/template/footer.jsp" %>
