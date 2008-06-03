<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.AccountSupportIssuesList" %>
<%@ page import="com.mythredz.dbgrid.GridCol" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mythredz.dbgrid.Grid" %>
<%@ page import="com.mythredz.htmluibeans.AccountNewSupportIssue" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Support Issues";
String navtab = "help";
String acl = "account";
%>
<%@ include file="/template/auth.jsp" %>
<%
AccountNewSupportIssue accountNewSupportIssue = (AccountNewSupportIssue) Pagez.getBeanMgr().get("AccountNewSupportIssue");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
        try {
            accountNewSupportIssue.setSubject(Textbox.getValueFromRequest("subject", "Subject", true, DatatypeString.DATATYPEID));
            accountNewSupportIssue.setNotes(Textarea.getValueFromRequest("notes", "Issue Description", true));
            accountNewSupportIssue.newIssue();
            Pagez.getUserSession().setMessage("Your issue has been added.");
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%@ include file="/template/header.jsp" %>
    <br/><br/>
    <%if (((AccountSupportIssuesList) Pagez.getBeanMgr().get("AccountSupportIssuesList")).getSupportissues() == null || ((AccountSupportIssuesList) Pagez.getBeanMgr().get("AccountSupportIssuesList")).getSupportissues().size() == 0) {%>

    <%} else {%>
        <%
            ArrayList<GridCol> cols=new ArrayList<GridCol>();
            cols.add(new GridCol("Id", "<$supportissueid$>", true, "", "tinyfont"));
            cols.add(new GridCol("Date", "<$datetime$>", true, "", "tinyfont", "", "background: #e6e6e6;"));
            cols.add(new GridCol("Subject", "<a href=\"/account/accountsupportissuedetail.jsp?supportissueid=<$supportissueid$>\"><$subject$></a>", false, "", "tinyfont"));
        %>
        <%=Grid.render(((AccountSupportIssuesList) Pagez.getBeanMgr().get("AccountSupportIssuesList")).getSupportissues(), cols, 10, "/account/accountsupportissueslist.jsp", "page")%>
    <%}%>


    <form action="/account/accountsupportissueslist.jsp" method="post">
        <input type="hidden" name="dpage" value="/account/accountsupportissueslist.jsp">
        <input type="hidden" name="action" value="save">
            <br/>
            <font class="mediumfont">Ask a Question. Make an Observation. Recommend an Improvement.</font>
            <br/>
            <font class="smallfont">Use this form to ask us anything at all about your account.  Report bugs.  Tell us where you're confused.  Tell us what could be better.  All communications will be archived and tracked for you here in the support section.</font>
            <br/><br/>
            <table cellpadding="0" cellspacing="0" border="0">

                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">Subject:</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("subject", accountNewSupportIssue.getSubject(), 255, 20, "", "")%>
                    </td>
                </tr>

                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">Issue Description:</font>
                    </td>
                    <td valign="top">
                        <%=Textarea.getHtml("notes", accountNewSupportIssue.getNotes(), 3, 35, "", "")%>
                    </td>
                </tr>

                <tr>
                    <td valign="top">
                    </td>
                    <td valign="top">
                        <input type="submit" class="formsubmitbutton" value="Create a New Support Issue">
                    </td>
                </tr>

            </table>
        </form>


<%@ include file="/template/footer.jsp" %>


