<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmlui.Pagez" %>
<%@ page import="com.mythredz.htmluibeans.AccountBalance" %>
<%@ page import="com.mythredz.dbgrid.GridCol" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mythredz.dbgrid.Grid" %>
<%@ page import="com.mythredz.htmluibeans.SysadminBalance" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Balance";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
    SysadminBalance sysadminBalance = (SysadminBalance) Pagez.getBeanMgr().get("SysadminBalance");
%>
<%@ include file="/template/header.jsp" %>


    

    <%if (sysadminBalance.getBalances()==null || sysadminBalance.getBalances().size()==0){%>
        <font class="normalfont">There are not yet any balance records.</font>
    <%} else {%>
        <%
        ArrayList<GridCol> cols=new ArrayList<GridCol>();
        cols.add(new GridCol("Id", "<$balanceid$>", true, "", "tinyfont"));
        cols.add(new GridCol("User", "<a href=\"/sysadmin/userdetail.jsp?userid=<$userid$>\"><$username$></a>", true, "", "tinyfont"));
        cols.add(new GridCol("Date", "<$date|"+Grid.GRIDCOLRENDERER_DATETIMECOMPACT+"$>", true, "", "tinyfont", "", "background: #e6e6e6;"));
        cols.add(new GridCol("Description", "<$description$>", false, "", "tinyfont"));
        cols.add(new GridCol("Funds", "<$fundstype$>", true, "", "tinyfont"));
        cols.add(new GridCol("Amount", "<$amt$>", true, "", "tinyfont"));
        cols.add(new GridCol("Balance", "<$currentbalance$>", true, "", "tinyfont"));
        %>
        <%=Grid.render(sysadminBalance.getBalances(), cols, 200, "/account/accountbalance.jsp", "page")%>
    <%}%>



<%@ include file="/template/footer.jsp" %>