<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.SysadminSupportIssueDetail" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.mythredz.dao.Supportissuecomm" %>
<%@ page import="com.mythredz.util.Str" %>
<%@ page import="com.mythredz.util.Time" %>
<%@ page import="java.util.TreeMap" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Support Issue Detail";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
    SysadminSupportIssueDetail sysadminSupportIssueDetail=(SysadminSupportIssueDetail) Pagez.getBeanMgr().get("SysadminSupportIssueDetail");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
        try {
            sysadminSupportIssueDetail.setStatus(Dropdown.getValueFromRequest("status", "Status", true));
            sysadminSupportIssueDetail.setNotes(Textarea.getValueFromRequest("notes", "Notes", false));
            sysadminSupportIssueDetail.newNote();
            Pagez.sendRedirect("/sysadmin/sysadminsupportissueslist.jsp");
            return;
        } catch (com.mythredz.htmlui.ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%@ include file="/template/header.jsp" %>


            <div class="rounded" style="padding: 10px; margin: 10px; background: #33FF00;">
                <font class="mediumfont"><%=sysadminSupportIssueDetail.getSupportissue().getSubject()%></font>
            </div>



            <%
            for (Iterator<Supportissuecomm> iterator=sysadminSupportIssueDetail.getSupportissuecomms().iterator(); iterator.hasNext();){
                Supportissuecomm supportissuecomm = iterator.next();
                %>
                <div class="rounded" style="padding: 10px; margin: 10px; background: #e6e6e6;">
                    <font class="smallfont" style="font-weight: bold;"><%=Time.dateformatcompactwithtime(Time.getCalFromDate(supportissuecomm.getDatetime()))%></font>
                    <br/>
                    <%if (!supportissuecomm.getIsfromdneeroadmin()){%>
                        <a href="/sysadmin/userdetail.jsp?userid=<%=sysadminSupportIssueDetail.getFromuser().getUserid()%>"><font class="smallfont" style="font-weight: bold;">From: <%=sysadminSupportIssueDetail.getFromuser().getFirstname()%> <%=sysadminSupportIssueDetail.getFromuser().getLastname()%></font></a>
                    <%} else {%>
                        <font class="smallfont" style="font-weight: bold;">dNeero Admin</font>
                    <%}%>
                    <% if (sysadminSupportIssueDetail.getFromuser().getFacebookuserid()>0){ %>
                        <font class="smallfont" style="font-weight: bold;">(Facebook User)</font>
                    <% } %>
                    <br/>
                    <font class="smallfont"><%=supportissuecomm.getNotes()%></font>
                </div>
                <%
            }
            %>



        <form action="/sysadmin/sysadminsupportissuedetail.jsp" method="post">
            <input type="hidden" name="dpage" value="/sysadmin/sysadminsupportissuedetail.jsp">
            <input type="hidden" name="action" value="save">
            <input type="hidden" name="supportissueid" value="<%=sysadminSupportIssueDetail.getSupportissueid()%>">

            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td valign="top">
                        <%=Textarea.getHtml("notes", sysadminSupportIssueDetail.getNotes(), 8, 72, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top">
                        <%
                            TreeMap<String, String> options=new TreeMap<String, String>();
                            options.put("0", "Open");
                            options.put("1", "Working");
                            options.put("2", "Closed");
                        %>
                        <br/><%=Dropdown.getHtml("status", String.valueOf(sysadminSupportIssueDetail.getStatus()), options, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top">
                        <input type="submit" class="formsubmitbutton" value="Add a Comment">
                    </td>
                </tr>

            </table>

        </form>





<%@ include file="/template/footer.jsp" %>



