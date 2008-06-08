<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.SysadminUserList" %>
<%@ page import="com.mythredz.dbgrid.GridCol" %>
<%@ page import="com.mythredz.dbgrid.Grid" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Users";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
SysadminUserList sysadminUserList = (SysadminUserList)Pagez.getBeanMgr().get("SysadminUserList");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("search")) {
        try {
            sysadminUserList.setSearchemail(Textbox.getValueFromRequest("searchemail", "Email", false, DatatypeString.DATATYPEID));
            sysadminUserList.setSearchfacebookers(CheckboxBoolean.getValueFromRequest("searchfacebookers"));
            sysadminUserList.setSearchfirstname(Textbox.getValueFromRequest("searchfirstname", "Firstname", false, DatatypeString.DATATYPEID));
            sysadminUserList.setSearchlastname(Textbox.getValueFromRequest("searchlastname", "Lastname", false, DatatypeString.DATATYPEID));
            sysadminUserList.setSearchuserid(Textbox.getValueFromRequest("searchuserid", "Userid", false, DatatypeString.DATATYPEID));
            sysadminUserList.initBean();
        } catch (com.mythredz.htmlui.ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%@ include file="/template/header.jsp" %>


    <form action="/sysadmin/userlist.jsp" method="post">
        <input type="hidden" name="dpage" value="/sysadmin/userlist.jsp">
        <input type="hidden" name="action" value="search">
        
        <table cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td valign="top">
                    <font class="tinyfont">Userid</font>
                </td>
                <td valign="top">
                    <font class="tinyfont">First Name</font>
                </td>
                <td valign="top">
                    <font class="tinyfont">Last Name</font>
                </td>
                <td valign="top">
                    <font class="tinyfont">Email</font>
                </td>
                <td valign="top">
                    <font class="tinyfont">Facebook?</font>
                </td>
                <td valign="top">

                </td>
            </tr>
            <tr>
                <td valign="top">
                    <%=Textbox.getHtml("searchuserrid", sysadminUserList.getSearchuserid(), 255, 5, "", "")%>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("searchfirstname", sysadminUserList.getSearchfirstname(), 255, 5, "", "")%>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("searchlastname", sysadminUserList.getSearchlastname(), 255, 5, "", "")%>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("searchemail", sysadminUserList.getSearchemail(), 255, 5, "", "")%>
                </td>
                <td valign="top">
                    <%=CheckboxBoolean.getHtml("searchfacebookers", sysadminUserList.getSearchfacebookers(), "", "")%>
                </td>
                <td valign="top">
                    <input type="submit" class="formsubmitbutton" value="Search">
                </td>
            </tr>
        </table>
    </form>

        <br/>

        <%if (sysadminUserList.getUsers()==null || sysadminUserList.getUsers().size()==0){%>
            <font class="normalfont">No users!</font>
        <%} else {%>
            <%
                ArrayList<GridCol> cols=new ArrayList<GridCol>();
                cols.add(new GridCol("Userid", "<a href=\"/sysadmin/userdetail.jsp?userid=<$userid$>\"><$userid$></a>", false, "", "tinyfont"));
                cols.add(new GridCol("Bloggerid", "<$bloggerid$>", false, "", "tinyfont"));
                cols.add(new GridCol("Nickname", "<$nickname$>", false, "", "tinyfont"));
                cols.add(new GridCol("Name", "<$firstname$> <$lastname$>", false, "", "tinyfont"));
                cols.add(new GridCol("Email", "<$email$>", false, "", "tinyfont"));
                cols.add(new GridCol("Signup Date", "<$createdate|"+Grid.GRIDCOLRENDERER_DATETIMECOMPACT+"$>", false, "", "tinyfont"));
            %>
            <%=Grid.render(sysadminUserList.getUsers(), cols, 200, "/sysadmin/userlist.jsp", "page")%>
        <%}%>






<%@ include file="/template/footer.jsp" %>



