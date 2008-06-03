<%@ page import="java.net.URLDecoder" %>
<%
    String url = "";
    if (request.getParameter("url") != null) {
        url = URLDecoder.decode(request.getParameter("url"), "UTF-8");
    }
    if(!url.equals("")){
%>
<HEAD>
<SCRIPT language="JavaScript">
<!--
top.location="<%=url%>";
//-->
</SCRIPT>
</HEAD>
<%
    } else {
%>
    No place to redirect to.
<%
    }
%>