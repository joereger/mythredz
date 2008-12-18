<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%@ page import="com.mythredz.htmluibeans.AccountSettings" %>
<%@ page import="com.mythredz.scheduledjobs.SendEmailReminderToUpdateStatus" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Account Settings";
String navtab = "youraccount";
String acl = "account";
%>
<%@ include file="/template/auth.jsp" %>
<%
AccountSettings accountSettings = (AccountSettings) Pagez.getBeanMgr().get("AccountSettings");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
    try {
        accountSettings.setFirstname(Textbox.getValueFromRequest("firstname", "First Name", true, DatatypeString.DATATYPEID));
        accountSettings.setLastname(Textbox.getValueFromRequest("lastname", "Last Name", true, DatatypeString.DATATYPEID));
        accountSettings.setNickname(Textbox.getValueFromRequest("nickname", "Nickname", true, DatatypeString.DATATYPEID));
        accountSettings.setEmail(Textbox.getValueFromRequest("email", "Email", true, DatatypeString.DATATYPEID));
        accountSettings.setIsemailnightlyon(CheckboxBoolean.getValueFromRequest("isemailnightlyon"));
        accountSettings.saveAction();
        Pagez.getUserSession().setMessage("Settings saved.");
    } catch (com.mythredz.htmlui.ValidationException vex) {
        Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
    }
}
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("sendnightlyemailnow")) {
        try {
            SendEmailReminderToUpdateStatus.sendForOneUser(Pagez.getUserSession().getUser());
                    Pagez.getUserSession().setMessage("Email sent!");
        } catch (Exception ex) {
            logger.error("", ex);
            Pagez.getUserSession().setMessage("Sorry, there was an error sending your email.");
        }
    }
%>
<%@ include file="/template/header.jsp" %>



    <br/><br/>
    <form action="/account/accountsettings.jsp" method="post">
        <input type="hidden" name="dpage" value="/account/accountsettings.jsp">
        <input type="hidden" name="action" value="save">

            <table cellpadding="3" cellspacing="0" border="0">

                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">First Name</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("firstname", accountSettings.getFirstname(), 255, 20, "", "")%>
                    </td>
                </tr>

                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">Last Name</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("lastname", accountSettings.getLastname(), 255, 20, "", "")%>
                    </td>
                </tr>

                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">Nickname</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("nickname", accountSettings.getNickname(), 255, 20, "", "")%>
                    </td>
                </tr>

                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">Email</font>
                        <br/>
                        <font class="tinyfont">This is used to log in.</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("email", accountSettings.getEmail(), 255, 20, "", "")%>
                        <br/>
                        <font class="tinyfont">Changing email will require re-activation</font>
                    </td>
                </tr>

                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">Nightly Email Form</font>
                        <br/>
                        <font class="tinyfont">Every night we'll send you an email.<br/>You'll simply hit Reply to tell us what happened.<br/>We'll update your thredz.<br/>It's easy, you'll like it.<br/><a href="/account/accountsettings.jsp?action=sendnightlyemailnow">Send one now.</a></font>
                    </td>
                    <td valign="top">
                        <%=CheckboxBoolean.getHtml("isemailnightlyon", accountSettings.getIsemailnightlyon(), "", "")%>
                        <font class="tinyfont">Yes, Email Form Me</font>
                    </td>
                </tr>


                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">Password</font>
                        <br/>
                        <font class="tinyfont">Password changes are handled on a separate screen.</font>
                    </td>
                    <td valign="top">
                        <a href="/account/changepassword.jsp"><font class="smallfont">Change Password</font></a>
                    </td>
                </tr>





                <tr>
                    <td valign="top">
                    </td>
                    <td valign="top">
                        <br/><br/>
                        <input type="submit" class="formsubmitbutton" value="Save Settings">
                    </td>
                </tr>

            </table>

    </form>


<%@ include file="/template/footer.jsp" %>


