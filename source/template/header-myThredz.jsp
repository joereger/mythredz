<%@ page import="com.mythredz.htmlui.Pagez" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;CHARSET=iso-8859-1"/>
    <title>myThredz</title>
    <link rel="stylesheet" type="text/css" href="/css/basic.css"/>
    <link rel="stylesheet" type="text/css" href="/css/myThredz.css"/>
    <!--<link rel="stylesheet" type="text/css" href="/js/niftycube/niftyCorners.css"/>-->
    <link rel="alternate" type="application/rss+xml" title="myThredz" href="http://www.mythredz.com/rss.xml"/>
    <meta name="description" content="myThredz"/>
    <meta name="keywords" content="fitness,workout"/>
    <script type="text/javascript" src="/js/mootools/mootools-release-1.11.js"></script>


    <%--<script type="text/javascript" src="/js/niftycube/niftycube.js"></script>--%>
    <%--<script type="text/javascript">--%>
        <%--NiftyLoad=function() {--%>
            <%--Nifty("div.rounded", "big");--%>
        <%--}--%>
    <%--</script>--%>
    <!--[if IE]>
    <style type="text/css">
    p.iepara{ /*Conditional CSS- For IE (inc IE7), create 1em spacing between menu and paragraph that follows*/
    padding-top: 1em;
    }
    </style>
    <![endif]-->

</head>
<body background="/images/template-v1/bg.gif" LEFTMARGIN="0" TOPMARGIN="0" MARGINWIDTH="0" MARGINHEIGHT="0"><center>
<table width="786" cellspacing="0" border="0" cellpadding="0">
<tr>
    <td rowspan="2"><a href="/"><img src="/images/template-v1/mythredz-logo.gif" width="234" height="65" alt="" border="0"></a></td>
    <td  valign="center" style="text-align: right;">
        <img src="/images/clear.gif" width="1" height="6" alt=""/><br/> 
		<%if (navtab.equals("home")){%><font class="navtabfontlevel1" style="background: #ffffff;"><a href="/index.jsp"><b>Home</b></a></font><%}%>
        <%if (!navtab.equals("home")){%><font class="navtabfontlevel1"><a href="/index.jsp"><b>Home</b></a></font><%}%>
        <%if (!navtab.equals("mythredz") && Pagez.getUserSession().getIsloggedin()){%><font class="navtabfontlevel1"><a href="/account/index.jsp"><b>Publish</b></a></font><%}%>
        <%if (navtab.equals("mythredz") && Pagez.getUserSession().getIsloggedin()){%><font class="navtabfontlevel1" style="background: #ffffff;"><a href="/account/index.jsp"><b>Publish</b></a></font><%}%>
        <%if (!navtab.equals("youraccount") && Pagez.getUserSession().getIsloggedin()){%><font class="navtabfontlevel1"><a href="/account/accountsettings.jsp"><b>Settings</b></a></font><%}%>
        <%if (navtab.equals("youraccount") && Pagez.getUserSession().getIsloggedin()){%><font class="navtabfontlevel1" style="background: #ffffff;"><a href="/account/accountsettings.jsp"><b>Settings</b></a></font><%}%>
        <%if (1==2 && !navtab.equals("youraccount") && !Pagez.getUserSession().getIsloggedin()){%><font class="navtabfontlevel1"><a href="/registration.jsp"><b>Sign Up</b></a></font><%}%>
        <%if (1==2 && navtab.equals("youraccount") && !Pagez.getUserSession().getIsloggedin()){%><font class="navtabfontlevel1" style="background: #ffffff;"><a href="/registration.jsp"><b>Sign Up</b></a></font><%}%>
        <%if (navtab.equals("blog")){%><font class="navtabfontlevel1" style="background: #ffffff;"><a href="/blog.jsp"><b>Blog</b></a></font><%}%>
        <%if (!navtab.equals("blog")){%><font class="navtabfontlevel1"><a href="/blog.jsp"><b>Blog</b></a></font><%}%>
        <%if (!navtab.equals("help") && Pagez.getUserSession().getIsloggedin()){%><font class="navtabfontlevel1"><a href="/account/accountsupportissueslist.jsp"><b>Help</b></a></font><%}%>
        <%if (navtab.equals("help") && Pagez.getUserSession().getIsloggedin()){%><font class="navtabfontlevel1" style="background: #ffffff;"><a href="/account/accountsupportissueslist.jsp"><b>Help</b></a></font><%}%>
        <%if (Pagez.getUserSession().getIsSysadmin() && !navtab.equals("sysadmin")){%><font class="navtabfontlevel1"><a href="/sysadmin/index.jsp"><b>SysAdmin</b></a></font><%}%>
        <%if (Pagez.getUserSession().getIsSysadmin() && navtab.equals("sysadmin")){%><font class="navtabfontlevel1" style="background: #ffffff;"><a href="/sysadmin/index.jsp"><b>SysAdmin</b></a></font><%}%>
	</td>
</tr>
<tr>
    <td valign="center" style="text-align: right;">
		<%if (navtab.equals("home")){%>
            <img src="/images/clear.gif" alt="" width="10" height="1"/>
            <%if (1==2 && !Pagez.getUserSession().getIsloggedin()){%><a href="/registration.jsp"><font class="subnavfont" style="color: #000000;">Sign Up</font></a><%}%>
            <img src="/images/clear.gif" alt="" width="10" height="1"/>
            <%if (1==2 && !Pagez.getUserSession().getIsloggedin()){%><a href="/login.jsp"><font class="subnavfont" style="color: #000000;">Log In</font></a><%}%>
        <%}%>

        <%if (navtab.equals("youraccount")){%>
            <%if (Pagez.getUserSession().getIsloggedin()){%>
                <img src="/images/clear.gif" alt="" width="10" height="1"/>
                <a href="/account/accountsettings.jsp"><font class="subnavfont" style="color: #000000;">Account Settings</font></a>
                <img src="/images/clear.gif" alt="" width="10" height="1"/>
                <a href="/account/changepassword.jsp"><font class="subnavfont" style="color: #000000;">Change Password</font></a>
                <img src="/images/clear.gif" alt="" width="10" height="1"/>
                <a href="/account/thredzlist.jsp"><font class="subnavfont" style="color: #000000;">Add/Edit Thredz</font></a>
            <%}%>
        <%}%>

        <%if (navtab.equals("mythredz")){%>
            <%if (Pagez.getUserSession().getIsloggedin()){%>
                <img src="/images/clear.gif" alt="" width="10" height="1"/>
                <a href="/account/index.jsp"><font class="subnavfont" style="color: #000000;">Publish to myThredz</font></a>
                <img src="/images/clear.gif" alt="" width="10" height="1"/>
                <a href="/account/thredzlist.jsp"><font class="subnavfont" style="color: #000000;">Add/Edit Thredz</font></a>
                <img src="/images/clear.gif" alt="" width="10" height="1"/>                               
                <a href="/account/embed.jsp"><font class="subnavfont" style="color: #000000;">Embed in Your Blog</font></a>
                <img src="/images/clear.gif" alt="" width="10" height="1"/>
                <%if (Pagez.getUserSession().getIsloggedin()){%><a href="/user/<%=Pagez.getUserSession().getUser().getNickname()%>/"><font class="subnavfont" style="color: #000000;">Profile</font></a><%}%>
                <img src="/images/clear.gif" alt="" width="10" height="1"/>
                <a href="/account/mobile.jsp"><font class="subnavfont" style="color: #000000;">Mobile Device Screen</font></a>
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
</table><table width="786" cellspacing="0" border="0" cellpadding="0">
<tr>
    <td valign="top">
        <%if (pagetitle!=null && !pagetitle.equals("")){%>
            <font class="pagetitlefont"><%=pagetitle%></font>
            <br/><br/>
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
    </td>
    <td valign="top" style="text-align: right;">
        <%if (!Pagez.getUserSession().getIsloggedin()){%>
                <div style="text-align: right;">
                    <font class="subnavfont">Already have an account?<img src="/images/clear.gif" width="20" height="1"/><a href="/login.jsp">Log In</a></font>
                    <br/>
                    <font class="subnavfont">Want to get one?<img src="/images/clear.gif" width="20" height="1"/><a href="/registration.jsp">Sign Up</a></font>
                </div>
            <%}%>
            <%if (Pagez.getUserSession().getIsloggedin()){%>
                <div style="text-align: right;">
                    <font class="subnavfont">Hi, <%=Pagez.getUserSession().getUser().getFirstname()%> <%=Pagez.getUserSession().getUser().getLastname()%>! <a href="/login.jsp?action=logout">Log Out</a></font>
                </div>
            <%}%>
        </td>
    </tr>
</table>

<table width="786" cellspacing="0" border="0" cellpadding="0">
<tr>
    <td>
    <div style="text-align: left;">
<!-- Start Body -->
