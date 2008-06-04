<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.ChangePassword" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.criterion.Restrictions" %>
<%@ page import="com.mythredz.dao.hibernate.HibernateUtil" %>
<%@ page import="com.mythredz.dao.Thred" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.mythredz.dbgrid.Grid" %>
<%@ page import="com.mythredz.dbgrid.GridCol" %>
<%@ page import="java.util.ArrayList" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Manage Thredz";
String navtab = "mythredz";
String acl = "account";
%>
<%@ include file="/template/auth.jsp" %>

<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
    try {
        Pagez.getUserSession().setMessage("Bleh.");
    } catch (Exception ex) {
        logger.error("", ex);
    }
}
%>
<%@ include file="/template/header.jsp" %>


    <%
        List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
                .add(Restrictions.eq("userid", Pagez.getUserSession().getUser().getUserid()))
                .setCacheable(true)
                .list();

    %>

    <br/><br/>
    <%if (threds==null || threds.size()==0){%>
        <font class="normalfont">You don't have any thredz yet.</font>
    <%} else {%>
        <%
            ArrayList<GridCol> cols=new ArrayList<GridCol>();
            cols.add(new GridCol("Create Date", "<$createdate|" + Grid.GRIDCOLRENDERER_DATETIMECOMPACT + "$>", true, "", "tinyfont", "", ""));
            cols.add(new GridCol("Thred", "<$name$>", false, "", "mediumfont"));
            cols.add(new GridCol("Actions", "<a href=\"/account/thrededit.jsp?thredid=<$thredid$>\">Edit</a> <a href=\"/account/thrededit.jsp?action=delete&thredid=<$thredid$>\">Delete</a>", true, "", "tinyfont"));
        %>
        <%=Grid.render(threds, cols, 50, "/account/thredzlists.jsp", "page")%>
    <%}%>

    <br/><br/>
    <font class="smallfont"><a href="/account/thrededit.jsp">Add a Thred</a></font>


<%@ include file="/template/footer.jsp" %>

