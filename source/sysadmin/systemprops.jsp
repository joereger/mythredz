<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.SysadminSystemProps" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "SystemProps... Be Careful!!!";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
    SysadminSystemProps sysadminSystemProps=(SysadminSystemProps) Pagez.getBeanMgr().get("SysadminSystemProps");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
        try {
            sysadminSystemProps.setBaseurl(Textbox.getValueFromRequest("baseurl", "baseurl", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.setFacebook_api_key(Textbox.getValueFromRequest("facebook_api_key", "facebook_api_key", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.setFacebook_api_secret(Textbox.getValueFromRequest("facebook_api_secret", "facebook_api_secret", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.setFacebook_app_name(Textbox.getValueFromRequest("facebook_app_name", "facebook_app_name", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.setIsbeta(Textbox.getValueFromRequest("isbeta", "isbeta", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.setIseverythingpasswordprotected(Textbox.getValueFromRequest("iseverythingpasswordprotected", "iseverythingpasswordprotected", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.setIssslon(Textbox.getValueFromRequest("issslon", "issslon", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.setPaypalapipassword(Textbox.getValueFromRequest("paypalapipassword", "paypalapipassword", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.setPaypalapiusername(Textbox.getValueFromRequest("paypalapiusername", "paypalapiusername", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.setPaypalenabled(Textbox.getValueFromRequest("paypalenabled", "paypalenabled", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.setPaypalenvironment(Textbox.getValueFromRequest("paypalenvironment", "paypalenvironment", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.setPaypalsignature(Textbox.getValueFromRequest("paypalsignature", "paypalsignature", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.setSendxmpp(Textbox.getValueFromRequest("sendxmpp", "sendxmpp", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.setSmtpoutboundserver(Textbox.getValueFromRequest("smtpoutboundserver", "smtpoutboundserver", true, DatatypeString.DATATYPEID));
            sysadminSystemProps.saveProps();
            Pagez.getUserSession().setMessage("Save complete.");
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%@ include file="/template/header.jsp" %>


    <form action="/sysadmin/systemprops.jsp" method="post">
        <input type="hidden" name="dpage" value="/sysadmin/systemprops.jsp">
        <input type="hidden" name="action" value="save">

        <table cellpadding="0" cellspacing="0" border="0">

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">Base Url</font>
                    <br/>
                    <font class="tinyfont">The base url that this instance is installed at.  No http:// and no trailing slash!  Ex: www.pingfit.com</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("baseurl", sysadminSystemProps.getBaseurl(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">SendXMPP</font>
                    <br/>
                    <font class="tinyfont">0 or 1.  Whether or not to send XMPP notifications from this installation.</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("sendxmpp", sysadminSystemProps.getSendxmpp(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">SmtpOutboundServer</font>
                    <br/>
                    <font class="tinyfont">Smtp server name or ip address to use to send email.  Default is 'localhost'</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("smtpoutboundserver", sysadminSystemProps.getSmtpoutboundserver(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">IsEverythingPasswordProtected</font>
                    <br/>
                    <font class="tinyfont">0 or 1. Whether or not a password is required to see anything.  Used for beta capability.</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("iseverythingpasswordprotected", sysadminSystemProps.getIseverythingpasswordprotected(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">IsBeta</font>
                    <br/>
                    <font class="tinyfont">0 or 1.  Whether the system is operating in BETA mode or not.</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("isbeta", sysadminSystemProps.getIsbeta(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">PayPalApiUsername</font>
                    <br/>
                    <font class="tinyfont">PayPal API username.</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("paypalapiusername", sysadminSystemProps.getPaypalapiusername(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">PayPalApiPassword</font>
                    <br/>
                    <font class="tinyfont">PayPal API password.</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("paypalapipassword", sysadminSystemProps.getPaypalapipassword(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">PayPalSignature</font>
                    <br/>
                    <font class="tinyfont">PayPal signature.</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("paypalsignature", sysadminSystemProps.getPaypalsignature(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">PayPalEnvironment</font>
                    <br/>
                    <font class="tinyfont">PayPal environment.  I.e. 'sandbox' or 'live'</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("paypalenvironment", sysadminSystemProps.getPaypalenvironment(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">PaypalEnabled?</font>
                    <br/>
                    <font class="tinyfont">0 or 1.  Whether or not PayPal integration is turned on.</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("paypalenabled", sysadminSystemProps.getPaypalenabled(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">IsSSLOn</font>
                    <br/>
                    <font class="tinyfont">0 or 1.  Whether SSL is installed at the server level for this install.</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("issslon", sysadminSystemProps.getIssslon(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">Facebook App Name</font>
                    <br/>
                    <font class="tinyfont">joestest or dneero</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("facebook_app_name", sysadminSystemProps.getFacebook_app_name(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">Facebook API Key</font>
                    <br/>
                    <font class="tinyfont">joestest is dece0e9c9bc48fa1078cbc5a0680cea3</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("facebook_api_key", sysadminSystemProps.getFacebook_api_key(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">Facebook API Secret</font>
                    <br/>
                    <font class="tinyfont">joestest is fde4c4950c909948fe3ada5676a19d2a</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("facebook_api_secret", sysadminSystemProps.getFacebook_api_secret(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                </td>
                <td valign="top">
                    <br/><br/>
                    <input type="submit" class="formsubmitbutton" value="Save System Props">
                </td>
             </tr>

        </table>

    </form>

<%@ include file="/template/footer.jsp" %>