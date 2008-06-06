<%@ page import="com.mythredz.htmlui.Pagez" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;CHARSET=iso-8859-1"/>
    <title>myThredz</title>
    <link rel="stylesheet" type="text/css" href="/css/basic.css"/>
    <link rel="stylesheet" type="text/css" href="/css/myThredz.css"/>
    <link rel="stylesheet" type="text/css" href="/js/niftycube/niftyCorners.css"/>
    <link rel="alternate" type="application/rss+xml" title="myThredz" href="http://www.mythredz.com/rss.xml"/>
    <meta name="description" content="myThredz"/>
    <meta name="keywords" content="fitness,workout"/>
    <script type="text/javascript" src="/js/mootools/mootools-release-1.11.js"></script>


    <script type="text/javascript" src="/js/niftycube/niftycube.js"></script>
    <script type="text/javascript">
        NiftyLoad=function() {
            Nifty("div.rounded", "big");
        }
    </script>
    <!--[if IE]>
    <style type="text/css">
    p.iepara{ /*Conditional CSS- For IE (inc IE7), create 1em spacing between menu and paragraph that follows*/
    padding-top: 1em;
    }
    </style>
    <![endif]-->

</head>
<body>

<table width="786" cellspacing="0" border="0" cellpadding="0">
<tr>
    <td valign="top">
        <img src="/images/clear.gif" width="10" height="1" align="left"/>
        <a href="/"><img src="/images/mythredz-logo.gif" height="75" border="0" alt=""></a>
        <br/>
        <img src="/images/clear.gif" width="1" height="10"/>
    </td>
    <td valign="top" style="text-align: right;">
        <%if (!Pagez.getUserSession().getIsloggedin()){%>
                <div style="padding: 10px; text-align: right;">
                <font class="subnavfont">Already have an account?<img src="/images/clear.gif" width="20" height="1"/><a href="/login.jsp">Log In</a></font>
                <br/>
                <font class="subnavfont">Want to get one?<img src="/images/clear.gif" width="20" height="1"/><a href="/registration.jsp">Sign Up</a></font>
                </div>
            <%}%>
            <%if (Pagez.getUserSession().getIsloggedin()){%>
                <div style="padding: 10px; text-align: right;">
                    <font class="subnavfont">Hi, <%=Pagez.getUserSession().getUser().getFirstname()%> <%=Pagez.getUserSession().getUser().getLastname()%>! <a href="/login.jsp?action=logout">Log Out</a></font>
                    <br/>
                    <font class="subnavfont">Need <a href="/account/accountsupportissueslist.jsp">Help?</a></font>
                </div>
            <%}%>
        </td>
    </tr>
    <%--<tr>--%>
        <%--<td valign="top" bgcolor="#00709e" colspan="2">--%>
            <%--<ul id="thicktabs">--%>
                <%--<%if (navtab.equals("home")){%><li><a id="leftmostitem" href="/index.jsp">Home</a></li><%}%>--%>
                <%--<%if (!navtab.equals("home")){%><li><a id="leftmostitem" href="/index.jsp">Home</a></li><%}%>--%>
                <%--<%if (!navtab.equals("youraccount") && Pagez.getUserSession().getIsloggedin()){%><li><a href="/account/index.jsp">Your Account</a></li><%}%>--%>
                <%--<%if (navtab.equals("youraccount") && Pagez.getUserSession().getIsloggedin()){%><li><a href="/account/index.jsp">Your Account</a></li><%}%>--%>
                <%--<%if (!navtab.equals("youraccount") && !Pagez.getUserSession().getIsloggedin()){%><li><a href="/registration.jsp">Sign Up</a></li><%}%>--%>
                <%--<%if (navtab.equals("youraccount") && !Pagez.getUserSession().getIsloggedin()){%><li><a href="/registration.jsp">Sign Up</a></li><%}%>--%>
                <%--<%if (Pagez.getUserSession().getIsSysadmin() && !navtab.equals("sysadmin")){%><li><a href="/sysadmin/index.jsp">SysAdmin</a></li><%}%>--%>
                <%--<%if (Pagez.getUserSession().getIsSysadmin() && navtab.equals("sysadmin")){%><li><a href="/sysadmin/index.jsp">SysAdmin</a></li><%}%>--%>
            <%--</ul>--%>
        <%--</td>--%>
    <%--</tr>--%>
    <tr>
        <td valign="top" colspan="2">
            <ul class="glossymenu">
                <%if (navtab.equals("home")){%><li class="current"><a href="/index.jsp"><b>Home</b></a></li><%}%>
                <%if (!navtab.equals("home")){%><li><a href="/index.jsp"><b>Home</b></a></li><%}%>
                <%if (!navtab.equals("mythredz") && Pagez.getUserSession().getIsloggedin()){%><li><a href="/account/index.jsp"><b>myThredz</b></a></li><%}%>
                <%if (navtab.equals("mythredz") && Pagez.getUserSession().getIsloggedin()){%><li class="current"><a href="/account/index.jsp"><b>myThredz</b></a></li><%}%>
                <%if (!navtab.equals("youraccount") && Pagez.getUserSession().getIsloggedin()){%><li><a href="/account/accountsettings.jsp"><b>Settings</b></a></li><%}%>
                <%if (navtab.equals("youraccount") && Pagez.getUserSession().getIsloggedin()){%><li class="current"><a href="/account/accountsettings.jsp"><b>Settings</b></a></li><%}%>
                <%if (!navtab.equals("youraccount") && !Pagez.getUserSession().getIsloggedin()){%><li><a href="/registration.jsp"><b>Sign Up</b></a></li><%}%>
                <%if (navtab.equals("youraccount") && !Pagez.getUserSession().getIsloggedin()){%><li class="current"><a href="/registration.jsp"><b>Sign Up</b></a></li><%}%>
                <%if (navtab.equals("blog")){%><li class="current"><a href="/blog.jsp"><b>Our Blog</b></a></li><%}%>
                <%if (!navtab.equals("blog")){%><li><a href="/blog.jsp"><b>Our Blog</b></a></li><%}%>
                <%if (!navtab.equals("help") && Pagez.getUserSession().getIsloggedin()){%><li><a href="/account/accountsupportissueslist.jsp"><b>Help</b></a></li><%}%>
                <%if (navtab.equals("help") && Pagez.getUserSession().getIsloggedin()){%><li class="current"><a href="/account/accountsupportissueslist.jsp"><b>Help</b></a></li><%}%>
                <%if (1==2 && !navtab.equals("help") && !Pagez.getUserSession().getIsloggedin()){%><li><a href="/account/accountsupportissueslist.jsp"><b>Help</b></a></li><%}%>
                <%if (1==2 && navtab.equals("help") && !Pagez.getUserSession().getIsloggedin()){%><li class="current"><a href="/account/accountsupportissueslist.jsp"><b>Help</b></a></li><%}%>
                <%if (Pagez.getUserSession().getIsSysadmin() && !navtab.equals("sysadmin")){%><li><a href="/sysadmin/index.jsp"><b>SysAdmin</a></li><%}%>
                <%if (Pagez.getUserSession().getIsSysadmin() && navtab.equals("sysadmin")){%><li class="current"><a href="/sysadmin/index.jsp"><b>SysAdmin</b></a></li><%}%>
            </ul>
        </td>
    </tr>

    <tr>
        <td bgcolor="#dadada" style="text-align: left; vertical-align: middle;" colspan="2" height="25">
            <%if (navtab.equals("home")){%>
                <img src="/images/clear.gif" alt="" width="10" height="1"/>
                <%if (!Pagez.getUserSession().getIsloggedin()){%><a href="/registration.jsp"><font class="subnavfont" style="color: #000000;">Sign Up</font></a><%}%>
                <img src="/images/clear.gif" alt="" width="10" height="1"/>
                <%if (!Pagez.getUserSession().getIsloggedin()){%><a href="/login.jsp"><font class="subnavfont" style="color: #000000;">Log In</font></a><%}%>
            <%}%>

            <%if (navtab.equals("youraccount")){%>
                <%if (Pagez.getUserSession().getIsloggedin()){%>
                    <img src="/images/clear.gif" alt="" width="10" height="1"/>
                    <a href="/account/accountsettings.jsp"><font class="subnavfont" style="color: #000000;">Account Settings</font></a>
                    <img src="/images/clear.gif" alt="" width="10" height="1"/>
                    <a href="/account/changepassword.jsp"><font class="subnavfont" style="color: #000000;">Change Password</font></a>
                <%}%>
            <%}%>

            <%if (navtab.equals("mythredz")){%>
                <%if (Pagez.getUserSession().getIsloggedin()){%>
                    <img src="/images/clear.gif" alt="" width="10" height="1"/>
                    <a href="/account/index.jsp"><font class="subnavfont" style="color: #000000;">Main</font></a>
                    <img src="/images/clear.gif" alt="" width="10" height="1"/>
                    <a href="/account/thredzlist.jsp"><font class="subnavfont" style="color: #000000;">Manage Thredz</font></a>
                <%}%>
            <%}%>

            <%if (navtab.equals("sysadmin")){%>
                <%if (Pagez.getUserSession().getIsloggedin() && Pagez.getUserSession().getIsSysadmin()){%>
                    <a href="/sysadmin/errorlist.jsp"><font class="subnavfont" style=" color: #000000;">Log</font></a>
                    <a href="/sysadmin/transactions.jsp"><font class="subnavfont" style=" color: #000000;">Trans</font></a>
                    <a href="/sysadmin/balance.jsp"><font class="subnavfont" style=" color: #000000;">Balance</font></a>
                    <a href="/sysadmin/userlist.jsp"><font class="subnavfont" style=" color: #000000;">Users</font></a>
                    <a href="/sysadmin/editeula.jsp"><font class="subnavfont" style=" color: #000000;">Eula</font></a>
                    <a href="/sysadmin/sysadminsupportissueslist.jsp"><font class="subnavfont" style=" color: #000000;">Support</font></a>
                    <a href="/sysadmin/manuallyrunscheduledtask.jsp"><font class="subnavfont" style=" color: #000000;">Scheds</font></a>
                    <a href="/sysadmin/systemprops.jsp"><font class="subnavfont" style=" color: #000000;">SysProps</font></a>
                    <a href="/sysadmin/instanceprops.jsp"><font class="subnavfont" style=" color: #000000;">InsProps</font></a>
                    <a href="/sysadmin/hibernatecache.jsp"><font class="subnavfont" style=" color: #000000;">Cache</font></a>
                    <a href="/sysadmin/massemaillist.jsp"><font class="subnavfont" style=" color: #000000;">Email</font></a>
                    <a href="/sysadmin/pageperformance.jsp"><font class="subnavfont" style=" color: #000000;">Perf</font></a>
                    <a href="/sysadmin/blogpost.jsp"><font class="subnavfont" style=" color: #000000;">Blog</font></a>
                <%}%>
            <%}%>
        </td>
    </tr>
    <tr>
        <td background="/images/navtabs2/linedots.gif" colspan="2"><img src="/images/clear.gif" width="1" height="1"/></td>
    </tr>
</table>


    <table width="775" cellspacing="0" border="0" cellpadding="10">
        <tr>
            <td valign="top">
                <%if (pagetitle!=null && !pagetitle.equals("")){%>
                    <font class="pagetitlefont"><%=pagetitle%></font>
                    <br/>
                <%}%>
                <%
                logger.debug("Pagez.getUserSession().getMessage()="+Pagez.getUserSession().getMessage());
                if (Pagez.getUserSession().getMessage()!=null && !Pagez.getUserSession().getMessage().equals("")){
                    %>
                    <br/>
                    <center><div class="rounded" style="background: #F2FFBF; text-align: left; padding: 20px;"><font class="formfieldnamefont"><%=Pagez.getUserSession().getMessage()%></font></div></center>
                    <br/><br/>
                    <%
                    //Clear the message since it's been displayed
                    Pagez.getUserSession().setMessage("");
                }
                %>
                <!-- Begin Body -->