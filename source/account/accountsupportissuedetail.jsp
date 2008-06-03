<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.AccountSupportIssueDetail" %>
<%@ page import="com.mythredz.dao.Supportissuecomm" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Support: Issue Detail";
String navtab = "help";
String acl = "account";
%>
<%@ include file="/template/auth.jsp" %>
<%
AccountSupportIssueDetail accountSupportIssueDetail = (AccountSupportIssueDetail)Pagez.getBeanMgr().get("AccountSupportIssueDetail");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
        try {
            accountSupportIssueDetail.setNotes(Textarea.getValueFromRequest("notes", "Comments", true));
            accountSupportIssueDetail.newNote();
            Pagez.getUserSession().setMessage("Thanks, your comments have been added to the issue.");
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%@ include file="/template/header.jsp" %>

    <font class="mediumfont"><%=accountSupportIssueDetail.getSupportissue().getSubject()%></font>


    <%
        ArrayList<Supportissuecomm> issues=accountSupportIssueDetail.getSupportissuecomms();
        for (Iterator it=issues.iterator(); it.hasNext();) {
            Supportissuecomm comm=(Supportissuecomm) it.next();
            StringBuffer body = new StringBuffer();
            body.append("<font class=\"tinyfont\">"+comm.getDatetime()+"</font>");
            body.append("<br/>");
            if (comm.getIsfromdneeroadmin()){
                body.append("<font class=\"normalfont\" style=\"font-weight: bold;\">From: dNeero Admin</font>");
            } else {
                body.append("<font class=\"normalfont\" style=\"font-weight: bold;\">From: You</font>");
            }
            body.append("<br/>");
            body.append("<font class=\"normalfont\">"+comm.getNotes()+"</font>");

            %>
            <div class="rounded" style="padding: 15px; margin: 5px; background: #e6e6e6;">
                <%=body.toString()%>
            </div>
            <%
        }
    %>

    <form action="/account/accountsupportissuedetail.jsp" method="post">
        <input type="hidden" name="dpage" value="/account/accountsupportissuedetail.jsp">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="supportissueid" value="<%=accountSupportIssueDetail.getSupportissue().getSupportissueid()%>">

            <table cellpadding="0" cellspacing="0" border="0">

                <tr>
                    <td valign="top">
                    </td>
                    <td valign="top">
                        <%=Textarea.getHtml("notes", accountSupportIssueDetail.getNotes(), 3, 35, "", "")%>
                    </td>
                </tr>

                <tr>
                    <td valign="top">
                    </td>
                    <td valign="top">
                        <input type="submit" class="formsubmitbutton" value="Add Comment to this Issue">
                    </td>
                </tr>

            </table>

    </form>

<%@ include file="/template/footer.jsp" %>

