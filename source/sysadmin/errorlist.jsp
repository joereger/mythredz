<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.SysadminErrorList" %>
<%@ page import="com.mythredz.dbgrid.GridCol" %>
<%@ page import="com.mythredz.dbgrid.Grid" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "SysLog";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
SysadminErrorList sysadminErrorList=(SysadminErrorList) Pagez.getBeanMgr().get("SysadminErrorList");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("refresh")) {
        try {
            sysadminErrorList.setMinleveltoshow(Dropdown.getIntFromRequest("minleveltoshow", "Min Level to Show", false));
            sysadminErrorList.initBean();
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("markallold")) {
        try {
            sysadminErrorList.markallold();
            Pagez.getUserSession().setMessage("Marked old.");
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("deleteall")) {
        try {
            sysadminErrorList.deleteall();
            Pagez.getUserSession().setMessage("Deleted.");
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("onlyerrors")) {
        try {
            sysadminErrorList.onlyerrors();
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("sortbytimesseen")) {
        try {
            sysadminErrorList.sortbytimesseen();
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("deleteindividual")) {
        try {
            sysadminErrorList.deleteindividual();
            Pagez.getUserSession().setMessage("Error deleted.");
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%@ include file="/template/header.jsp" %>

        <form action="/sysadmin/errorlist.jsp" method="post">
            <input type="hidden" name="dpage" value="/sysadmin/errorlist.jsp">
            <input type="hidden" name="action" value="refresh">
            <%=Dropdown.getHtml("minleveltoshow", String.valueOf(sysadminErrorList.getMinleveltoshow()), sysadminErrorList.getLevels(), "", "")%>
            <input type="submit" class="formsubmitbutton" value="Refresh">
        </form>
        <br/>
        <a href="/sysadmin/errorlist.jsp?action=markallold"><font class="smallfont">Mark All Old</font></a>
        <a href="/sysadmin/errorlist.jsp?action=deleteall"><font class="smallfont">Delete All</font></a>
        <a href="/sysadmin/errorlist.jsp?action=onlyerrors"><font class="smallfont">Only Errors</font></a>
        <a href="/sysadmin/errorlist.jsp?action=sortbytimesseen"><font class="smallfont">Most Frequent</font></a>
        <br/><br/>


        <%if (sysadminErrorList.getErrors()==null || sysadminErrorList.getErrors().size()==0){%>
            <font class="normalfont">None found.</font>
        <%} else {%>
            <%
                ArrayList<GridCol> cols=new ArrayList<GridCol>();
                cols.add(new GridCol("Id", "<$errorid$><br/><a href=\"/sysadmin/errorlist.jsp?action=deleteindividual&errorid=<$errorid$>\">delete</a>", false, "", "tinyfont"));
                cols.add(new GridCol("", "<$timesseen$>", false, "", "smallfont"));
                cols.add(new GridCol("Date", "<$date|"+Grid.GRIDCOLRENDERER_DATETIMECOMPACT+"$>", false, "", "tinyfont"));
                cols.add(new GridCol("Error", "<$error$>", false, "", "smallfont"));
            %>
            <%=Grid.render(sysadminErrorList.getErrors(), cols, 250, "/sysadmin/errorlist.jsp", "page")%>
        <%}%>










<%@ include file="/template/footer.jsp" %>



