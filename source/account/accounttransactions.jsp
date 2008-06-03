<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmlui.Pagez" %>
<%@ page import="com.mythredz.htmluibeans.AccountBalancetransaction" %>
<%@ page import="com.mythredz.dbgrid.GridCol" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mythredz.dbgrid.Grid" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Account Transactions";
String navtab = "youraccount";
String acl = "account";
%>
<%@ include file="/template/auth.jsp" %>
<%
AccountBalancetransaction accountBalancetransaction = (AccountBalancetransaction) Pagez.getBeanMgr().get("AccountBalancetransaction");
%>
<%@ include file="/template/header.jsp" %>

        <%if (accountBalancetransaction.getBalances() == null || accountBalancetransaction.getBalances().size() == 0) {%>
            <font class="normalfont">There are not yet any financial transactions on your account.  Go fill out some surveys!  Or create some!</font>
        <%} else {%>
            <%
                ArrayList<GridCol> cols=new ArrayList<GridCol>();
                cols.add(new GridCol("Id", "<$balancetransactionid$>", true, "", "tinyfont"));
                cols.add(new GridCol("Date", "<$date$>", true, "", "tinyfont", "", "background: #e6e6e6;"));
                cols.add(new GridCol("Success?", "<$issuccessful$>", false, "", "tinyfont"));
                cols.add(new GridCol("Description", "<$description$>", false, "", "tinyfont"));
                cols.add(new GridCol("Notes", "<$notes$>", false, "", "tinyfont"));
                cols.add(new GridCol("Amount", "<$amt$>", true, "", "tinyfont"));
            %>
            <%=Grid.render(accountBalancetransaction.getBalances(), cols, 50, "/account/accounttransactions.jsp", "page")%>
        <%}%>


<%@ include file="/template/footer.jsp" %>

