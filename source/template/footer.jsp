<%@ page import="com.mythredz.pageperformance.PagePerformanceUtil" %>
<% if (!Pagez.getUserSession().getIsfacebookui()) { %>
    <%@ include file="footer-myThredz.jsp" %>
<% } else { %>
    <%@ include file="footer-facebook.jsp" %>
<% }%>

<%
    //Performance recording
    try {
        String prePendPageId = "";
        if (Pagez.getUserSession().getIsfacebookui()){
            prePendPageId = "[FB]";
        }
        long elapsedtimeFooter = Pagez.getElapsedTime();
        PagePerformanceUtil.add(prePendPageId+request.getServletPath(), InstanceProperties.getInstancename(), elapsedtimeFooter);
    } catch (Exception ex) {
        logger.error("", ex);
    }

%>
