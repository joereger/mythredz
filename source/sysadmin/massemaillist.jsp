<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmlui.Pagez" %>
<%@ page import="com.mythredz.dbgrid.GridCol" %>
<%@ page import="com.mythredz.dbgrid.Grid" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mythredz.htmluibeans.SysadminMassemailList" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Mass Emails";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
SysadminMassemailList sysadminMassemailList = (SysadminMassemailList)Pagez.getBeanMgr().get("SysadminMassemailList");
%>
<%@ include file="/template/header.jsp" %>



    <%if (sysadminMassemailList.getMassemails()==null || sysadminMassemailList.getMassemails().size()==0){%>
        <font class="normalfont">No old mass emails... get to a'sendin'!</font>
    <%} else {%>
        <%
            ArrayList<GridCol> cols=new ArrayList<GridCol>();
            cols.add(new GridCol("Id", "<$massemailid$>", false, "", "tinyfont"));
            cols.add(new GridCol("Date", "<$date|" + Grid.GRIDCOLRENDERER_DATETIMECOMPACT + "$>", false, "", "tinyfont"));
            cols.add(new GridCol("Processed Userid", "<$lastuseridprocessed$>", false, "", "tinyfont"));
            cols.add(new GridCol("Subject", "<a href=\"/sysadmin/massemaildetail.jsp?massemailid=<$massemailid$>\"><$subject$></a>", false, "", "tinyfont"));
            cols.add(new GridCol("Status", "<$status$>", false, "", "tinyfont"));
        %>
        <%=Grid.render(sysadminMassemailList.getMassemails(), cols, 200, "/sysadmin/massemaillist.jsp", "page")%>
    <%}%>



     <br/><br/>
     <a href="/sysadmin/massemaildetail.jsp"><font class="mediumfont">New Mass Email</font></a>



<%@ include file="/template/footer.jsp" %>



