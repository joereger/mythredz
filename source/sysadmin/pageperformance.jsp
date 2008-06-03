<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.SysadminUserList" %>
<%@ page import="com.mythredz.dbgrid.GridCol" %>
<%@ page import="com.mythredz.dbgrid.Grid" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mythredz.dao.Pageperformance" %>
<%@ page import="com.mythredz.pageperformance.PagePerformance" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.mythredz.htmluibeans.SysadminPagePerformance" %>
<%@ page import="com.mythredz.htmlui.ValidationException" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Page Performance";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
    SysadminPagePerformance sysadminPagePerformance = (SysadminPagePerformance) Pagez.getBeanMgr().get("SysadminPagePerformance");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("find")) {
        try {
            sysadminPagePerformance.setStartDate(com.mythredz.htmlui.Date.getValueFromRequest("startdate", "Start Date", true));
            sysadminPagePerformance.setEndDate(com.mythredz.htmlui.Date.getValueFromRequest("enddate", "End Date", true));
            sysadminPagePerformance.find();
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%@ include file="/template/header.jsp" %>

        <%
        if (sysadminPagePerformance.getPageid()!=null){
            %>
            <br/>
            <font class="mediumfont"><%=sysadminPagePerformance.getPageid()%></font><br/><br/>
            <%if (sysadminPagePerformance.getPpssinglepageid()==null || sysadminPagePerformance.getPpssinglepageid().size()==0){%>
                <font class="normalfont">No pps found dude.</font>
            <%} else {%>
                <%
                    ArrayList<GridCol> cols=new ArrayList<GridCol>();
                    cols.add(new GridCol("Year", "<$year$>", false, "", "tinyfont"));
                    cols.add(new GridCol("Month", "<$month$>", false, "", "tinyfont"));
                    cols.add(new GridCol("Day", "<$day$>", false, "", "tinyfont"));
                    cols.add(new GridCol("Part of Day", "<$partofday$>", false, "", "tinyfont"));
                    cols.add(new GridCol("Server", "<$servername$>", false, "", "tinyfont"));
                    cols.add(new GridCol("Pageid", "<$pageid$>", false, "", "tinyfont"));
                    cols.add(new GridCol("Loads", "<$totalloads$>", false, "", "tinyfont"));
                    cols.add(new GridCol("Avg Time", "<$avgtime$>", false, "", "tinyfont"));
                %>
                <%=Grid.render(sysadminPagePerformance.getPpssinglepageid(), cols, 200, "/sysadmin/pageperformance.jsp", "pagesingle")%>
            <%}%>
            <br/>  
        <%
        }
        %>

        <br/>
        <font class="mediumfont">Unrecorded Entries</font><br/><br/>
        <table cellpadding='3' cellspacing='3' border='0'>
        <%
            ArrayList<PagePerformance> pps = PagePerformanceUtil.getPagePerformances();
            for (Iterator<PagePerformance> iterator = pps.iterator(); iterator.hasNext();) {
                PagePerformance pagePerformance =  iterator.next();
                double avgLoadtime = pagePerformance.getTotaltime()/pagePerformance.getTotalpageloads();
                %>
                <tr>
                    <td valign="top"><font class="tinyfont">Hr:<%=pagePerformance.getPartofday()%></font></td>
                    <td valign="top"><font class="tinyfont"><%=pagePerformance.getPageid()%></font></td>
                    <td valign="top"><font class="tinyfont"><%=pagePerformance.getTotalpageloads()%></font></td>
                    <td valign="top"><font class="tinyfont"><%=pagePerformance.getTotaltime()%></font></td>
                    <td valign="top"><font class="tinyfont"><%=avgLoadtime%></font></td>
                </tr>
                <%
            }
        %>
        </table>

        <br/><br/>
        <font class="mediumfont">Recent PagePerformance DB Entries</font><br/><br/>
        <form action="/sysadmin/pageperformance.jsp" method="post">
            <input type="hidden" name="dpage" value="/sysadmin/pageperformance.jsp">
            <input type="hidden" name="action" value="find">
            <%=com.mythredz.htmlui.Date.getHtml("startdate", sysadminPagePerformance.getStartDate(), "", "")%>
            <%=com.mythredz.htmlui.Date.getHtml("enddate", sysadminPagePerformance.getEndDate(), "", "")%>
            <br/>
            <input type="submit" value="Find">
        </form>
        <%if (sysadminPagePerformance.getPps()==null || sysadminPagePerformance.getPps().size()==0){%>
            <font class="normalfont">No pps found dude.</font>
        <%} else {%>
            <%
                ArrayList<GridCol> cols=new ArrayList<GridCol>();
                cols.add(new GridCol("Year", "<$year$>", false, "", "tinyfont"));
                cols.add(new GridCol("Month", "<$month$>", false, "", "tinyfont"));
                cols.add(new GridCol("Day", "<$day$>", false, "", "tinyfont"));
                cols.add(new GridCol("Part of Day", "<$partofday$>", false, "", "tinyfont"));
                cols.add(new GridCol("Server", "<$servername$>", false, "", "tinyfont"));
                cols.add(new GridCol("Pageid", "<a href=\"/sysadmin/pageperformance.jsp?pageid=<$pageid$>\"><$pageid$></a>", false, "", "tinyfont"));
                cols.add(new GridCol("Loads", "<$totalloads$>", false, "", "tinyfont"));
                cols.add(new GridCol("Avg Time", "<$avgtime$>", false, "", "tinyfont"));
            %>
            <%=Grid.render(sysadminPagePerformance.getPps(), cols, 200, "/sysadmin/pageperformance.jsp", "page")%>
        <%}%>


<%@ include file="/template/footer.jsp" %>



