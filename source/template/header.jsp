<% if (!Pagez.getUserSession().getIsfacebookui()) { %>
    <%@ include file="header-myThredz.jsp" %>
<% } else { %>
    <%@ include file="header-facebook.jsp" %>
<% }%>