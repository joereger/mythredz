<%@ page import="com.mythredz.systemprops.InstanceProperties"%>
<%@ page import="com.mythredz.util.GeneralException" %>
<%
    //Only do this page if we have an invalid database connection.
//Otherwise, anybody's going to be able to reset the database
//configuration whenever they want. Which isn't cool.
    if (InstanceProperties.haveValidConfig()) {
        response.sendRedirect("setup-02.jsp");
        return;
    }
%>
<%
    //Save the properties, test them
    String errortext = "";
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
        if (!InstanceProperties.haveNewConfigToTest()) {
            if (request.getParameter("dbserver") != null && request.getParameter("dbport") != null && request.getParameter("dbname") != null) {
                String dbConnUrl = "jdbc:mysql://" + request.getParameter("dbserver") + ":" + request.getParameter("dbport") + "/" + request.getParameter("dbname") + "?autoReconnect=true";
                InstanceProperties.setDbConnectionUrl(dbConnUrl);
            }
            if (request.getParameter("dbusername") != null) {
                InstanceProperties.setDbUsername(request.getParameter("dbusername"));
            }
            if (request.getParameter("dbpassword") != null) {
                InstanceProperties.setDbPassword(request.getParameter("dbpassword"));
            }
            if (request.getParameter("dbmaxactive") != null) {
                InstanceProperties.setDbMaxActive(request.getParameter("dbmaxactive"));
            } else {
                InstanceProperties.setDbMaxActive("100");
            }
            if (request.getParameter("dbmaxidle") != null) {
                InstanceProperties.setDbMaxIdle(request.getParameter("dbmaxidle"));
            } else {
                InstanceProperties.setDbMaxIdle("20");
            }
            if (request.getParameter("dbminidle") != null) {
                InstanceProperties.setDbMinIdle(request.getParameter("dbminidle"));
            } else {
                InstanceProperties.setDbMinIdle("10");
            }
            if (request.getParameter("dbmaxwait") != null) {
                InstanceProperties.setDbMaxWait(request.getParameter("dbmaxwait"));
            } else {
                InstanceProperties.setDbMaxWait("10000");
            }
            if (request.getParameter("runscheduledtasksonthisinstance") != null && request.getParameter("runscheduledtasksonthisinstance").equals("1")) {
                InstanceProperties.setRunScheduledTasksOnThisInstance(true);
            } else {
                InstanceProperties.setRunScheduledTasksOnThisInstance(false);
            }
            //Save if it passes the connection test
            try {
                InstanceProperties.save();
                if (InstanceProperties.haveValidConfig()) {
                    response.sendRedirect("setup-02.jsp");
                    return;
                } else {
                    errortext = "There was an error.  This may mean that the application couldn't connect to the database.  This may also mean that the application did connect to the database but that the account it used didn't have enough permissions to run the application properly.";
                }
            } catch (GeneralException gex) {
                errortext = "There was an error.  This may mean that the application couldn't connect to the database.  This may also mean that the application did connect to the database but that the account it used didn't have enough permissions to run the application properly." + gex.getErrorsAsSingleString();
            }

        }
    }
%>


<%@ include file="setup-header.jsp" %>

<font face=arial size=+3 color=#cccccc>Database Connection Setup</font>
<br><br>

<%
if (!errortext.equals("")){
%>
<font face=arial size=+2 color=#ff0000><%=errortext%></font>
<br><br>
<font face=arial size=+1 color=#cccccc>Use your browser's back button to adjust the settings.</font>
<%
} else {
%>

    <%
        if (!InstanceProperties.haveNewConfigToTest()) {
    %>

        <center>
        <form action=setup-01.jsp method=post>
        <input type=hidden name=action value=save>

        <table cellpadding=5 cellspacing=0 width=75% border=0>

        <!-- Begin Prop -->
        <tr>
        <td valign=top align=left colspan=2>
        <font face=arial size=+2 color=#cccccc>
        Database Machine Location
        </font>
        </td>
        </tr>

        <tr>
        <td valign=top align=left>
        <font face=arial size=-1>
        <input type=text name=dbserver value="localhost" size=45 maxlength=255>
        </font>
        </td>
        <td valign=top align=left>
        <font face=arial size=-1>
        The name of the server that MySQL is installed on.   This can be a machine name like 'localhost' or an ip address like '127.0.0.1'
        </font>
        </td>
        </tr>
        <!-- End Prop -->

        <!-- Begin Prop -->
        <tr>
        <td valign=top align=left colspan=2>
        <font face=arial size=+2 color=#cccccc>
        Database Port
        </font>
        </td>
        </tr>
        <tr>
        <td valign=top align=left>
        <font face=arial size=-1>
        <input type=text name=dbport value="3306" size=45 maxlength=255>
        </font>
        </td>
        <td valign=top align=left>
        <font face=arial size=-1>
        The port that MySql is running on.  By default it runs on port '3306'
        </font>
        </td>
        </tr>
        <!-- End Prop -->

        <!-- Begin Prop -->
        <tr>
        <td valign=top align=left colspan=2>
        <font face=arial size=+2 color=#cccccc>
        Database Name
        </font>
        </td>
        </tr>
        <tr>
        <td valign=top align=left>
        <font face=arial size=-1>
        <input type=text name=dbname value="reger" size=45 maxlength=255>
        </font>
        </td>
        <td valign=top align=left>
        <font face=arial size=-1>
        The name of the database on MySql that should be used to store blog entries and other data.  This database needs to exist already in MySql (it can be empty), and the username/password combo you define on this page must be able to access it... the application will setup the tables needed to run the blogging server. See the username for access requirements.
        </font>
        </td>
        </tr>
        <!-- End Prop -->

        <!-- Begin Prop -->
        <tr>
        <td valign=top align=left colspan=2>
        <font face=arial size=+2 color=#cccccc>
        Database Username
        </font>
        </td>
        </tr>
        <tr>
        <td valign=top align=left>
        <font face=arial size=-1>
        <input type=text name=dbusername value="username" size=45 maxlength=255>
        </font>
        </td>
        <td valign=top align=left>
        <font face=arial size=-1>
        This username must already be set up in MySql and must have Select, Insert, Update, Delete, Create, Drop, Alter and Index privileges on the database specified in the connection string above.  If you're having trouble, start by granting this user all permissions and then back off of them one at a time to increase security.  It must have the permissions listed to be able to upgrade the database as upgrades are launched.
        </font>
        </td>
        </tr>
        <!-- End Prop -->

        <!-- Begin Prop -->
        <tr>
        <td valign=top align=left colspan=2>
        <font face=arial size=+2 color=#cccccc>
        Database Password
        </font>
        </td>
        </tr>
        <tr>
        <td valign=top align=left>
        <font face=arial size=-1>
        <input type=text name=dbpassword value="password" size=45 maxlength=255>
        </font>
        </td>
        <td valign=top align=left>
        <font face=arial size=-1>
        The password for the user specified above.
        </font>
        </td>
        </tr>
        <!-- End Prop -->

        <!-- Begin Prop -->
        <tr>
        <td valign=top align=left colspan=2>
        <font face=arial size=+2 color=#cccccc>
        Run Scheduled Tasks on this Instance?
        </font>
        </td>
        </tr>
        <tr>
        <td valign=top align=left>
        <font face=arial size=-1>
        <input type=text name=runscheduledtasksonthisinstance value="0" size=45 maxlength=255>
        </font>
        </td>
        <td valign=top align=left>
        <font face=arial size=-1>
        Whether or not scheduled tasks will be run on this instance.  If in production, chances are there's already an instance responsible for running them.  If in development, set this to '1'
        </font>
        </td>
        </tr>
        <!-- End Prop -->

        <!-- Begin Prop -->
        <!--
        <tr>
        <td valign=top align=left colspan=2>
        <font face=arial size=+2 color=#cccccc>
        Database Max Active Connections
        </font>
        </td>
        </tr>
        <tr>
        <td valign=top align=left>
        <font face=arial size=-1>
        <input type=text name=dbmaxactive value="30" size=45 maxlength=255>
        </font>
        </td>
        <td valign=top align=left>
        <font face=arial size=-1>
        The maximum number of connections to the database.  MySql must be configured to accept at least this many connections concurrently.  Start with a low number like 30 and increase as required.
        </font>
        </td>
        </tr>
        -->
        <!-- End Prop -->

        <!-- Begin Prop -->
        <!--
        <tr>
        <td valign=top align=left colspan=2>
        <font face=arial size=+2 color=#cccccc>
        Database Max Idle Connections
        </font>
        </td>
        </tr>
        <tr>
        <td valign=top align=left>
        <font face=arial size=-1>
        <input type=text name=dbmaxidle value="15" size=45 maxlength=255>
        </font>
        </td>
        <td valign=top align=left>
        <font face=arial size=-1>
        The maximum number of idle connections to the database.  More idle connections allow the application to burst into action more quickly.  Start with a number about half of your maximum connections.
        </font>
        </td>
        </tr>
        -->
        <!-- End Prop -->

        <!-- Begin Prop -->
        <!--
        <tr>
        <td valign=top align=left colspan=2>
        <font face=arial size=+2 color=#cccccc>
        Database Min Active Connections
        </font>
        </td>
        </tr>
        <tr>
        <td valign=top align=left>
        <font face=arial size=-1>
        <input type=text name=dbminidle value="10" size=45 maxlength=255>
        </font>
        </td>
        <td valign=top align=left>
        <font face=arial size=-1>
        The minimum number of idle connections to the database.  Start with 25% of your max connections.
        </font>
        </td>
        </tr>
        -->
        <!-- End Prop -->

        <!-- Begin Prop -->
        <!--
        <tr>
        <td valign=top align=left colspan=2>
        <font face=arial size=+2 color=#cccccc>
        Database Max Wait
        </font>
        </td>
        </tr>
        <tr>
        <td valign=top align=left>
        <font face=arial size=-1>
        <input type=text name=dbmaxwait value="10000" size=45 maxlength=255>
        </font>
        </td>
        <td valign=top align=left>
        <font face=arial size=-1>
        The maximum amount of time in ms to wait for a response from the database.  Start with 10000 and adjust as necessary.
        </font>
        </td>
        </tr>
        -->
        <!-- End Prop -->



        <tr>
        <td valign=top align=left colspan=2>
        <font face=arial size=+2 color=#cccccc>
        <input type=submit value="Save Database Configuration">
        </font>
        </form>
        </td>
        </tr>


        </table>


        </form>
        </center>

    <%
    } else {
    %>
        <br><br>
        <font face=arial size=+1 color=#cccccc>Database connectivity is being tested.  If you are the system admin, please refresh in a few seconds as it's likely that somebody other than you has entered invalid database parameters.</font>
    <%
    }
    %>

<%
}
%>

<%@ include file="setup-footer.jsp" %>