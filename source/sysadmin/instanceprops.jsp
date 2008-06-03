<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.SysadminInstanceProps" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "InstanceProps... Be Careful!!!";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
SysadminInstanceProps sysadminInstanceProps = (SysadminInstanceProps)Pagez.getBeanMgr().get("SysadminInstanceProps");
%>                 
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
        try {
            sysadminInstanceProps.setDbConnectionUrl(Textbox.getValueFromRequest("dbConnectionUrl", "dbConnectionUrl", true, DatatypeString.DATATYPEID));
            sysadminInstanceProps.setDbDriverName(Textbox.getValueFromRequest("dbDriverName", "dbDriverName", true, DatatypeString.DATATYPEID));
            sysadminInstanceProps.setDbMaxActive(Textbox.getValueFromRequest("dbMaxActive", "dbMaxActive", true, DatatypeString.DATATYPEID));
            sysadminInstanceProps.setDbMaxIdle(Textbox.getValueFromRequest("dbMaxIdle", "dbMaxIdle", true, DatatypeString.DATATYPEID));
            sysadminInstanceProps.setDbMaxWait(Textbox.getValueFromRequest("dbMaxWait", "dbMaxWait", true, DatatypeString.DATATYPEID));
            sysadminInstanceProps.setDbMinIdle(Textbox.getValueFromRequest("dbMinIdle", "dbMinIdle", true, DatatypeString.DATATYPEID));
            sysadminInstanceProps.setDbPassword(TextboxSecret.getValueFromRequest("dbPassword", "dbPassword", true, DatatypeString.DATATYPEID));
            sysadminInstanceProps.setDbUsername(Textbox.getValueFromRequest("dbUsername", "dbUsername", true, DatatypeString.DATATYPEID));
            sysadminInstanceProps.setInstancename(Textbox.getValueFromRequest("instancename", "instancename", true, DatatypeString.DATATYPEID));
            sysadminInstanceProps.setRunScheduledTasksOnThisInstance(Textbox.getValueFromRequest("runScheduledTasksOnThisInstance", "runScheduledTasksOnThisInstance", true, DatatypeString.DATATYPEID));
            sysadminInstanceProps.setAbsolutepathtoexerciseimages(Textbox.getValueFromRequest("absolutepathtoexerciseimages", "absolutepathtoexerciseimages", false, DatatypeString.DATATYPEID));
            sysadminInstanceProps.saveProps();
            Pagez.getUserSession().setMessage("Props saved.");
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%@ include file="/template/header.jsp" %>

    <form action="/sysadmin/instanceprops.jsp" method="post">
        <input type="hidden" name="dpage" value="/sysadmin/instanceprops.jsp">
        <input type="hidden" name="action" value="save">

        <table cellpadding="0" cellspacing="0" border="0">

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">Instance Name</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("instancename", sysadminInstanceProps.getInstancename(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">dbConnectionUrl</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("dbConnectionUrl", sysadminInstanceProps.getDbConnectionUrl(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">dbUsername</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("dbUsername", sysadminInstanceProps.getDbUsername(), 255, 35, "", "")%>
                </td>
            </tr>


            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">dbPassword</font>
                </td>
                <td valign="top">
                    <%=TextboxSecret.getHtml("dbPassword", sysadminInstanceProps.getDbPassword(), 255, 35, "", "")%>
                </td>
            </tr>


            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">dbMaxActive</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("dbMaxActive", sysadminInstanceProps.getDbMaxActive(), 255, 35, "", "")%>
                </td>
            </tr>


            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">dbMaxIdle</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("dbMaxIdle", sysadminInstanceProps.getDbMaxIdle(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">dbMinIdle</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("dbMinIdle", sysadminInstanceProps.getDbMinIdle(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">dbMaxWait</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("dbMaxWait", sysadminInstanceProps.getDbMaxWait(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">dbDriverName</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("dbDriverName", sysadminInstanceProps.getDbDriverName(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">runScheduledTasksOnThisInstance</font>
                    <br/>
                    <font class="tinyfont">0 or 1</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("runScheduledTasksOnThisInstance", sysadminInstanceProps.getRunScheduledTasksOnThisInstance(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">absolutepathtoexerciseimages</font>
                    <br/>
                    <font class="tinyfont">0 or 1</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("absolutepathtoexerciseimages", sysadminInstanceProps.getAbsolutepathtoexerciseimages(), 255, 35, "", "")%>
                </td>
            </tr>


            <tr>
                <td valign="top">
                </td>
                <td valign="top">
                    <br/><br/>
                    <input type="submit" class="formsubmitbutton" value="Save Instance Props">
                </td>
            </tr>


        </table>


    </form>



<%@ include file="/template/footer.jsp" %>