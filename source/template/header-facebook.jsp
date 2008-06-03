<%@ page import="com.mythredz.systemprops.BaseUrl" %>
<%@ page import="com.mythredz.util.Io" %>
<%@ page import="com.mythredz.systemprops.WebAppRootDir" %>
<!--<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"-->
          <!--"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">-->
<!--<html>-->
<!--<head>-->
  <!--<title>PingFit.com</title>-->
  <style type="text/css"><%=Io.textFileRead(WebAppRootDir.getWebAppRootPath()+"/css/basic.css").toString()%></style>
  <!--<link rel="stylesheet" type="text/css" href="/css/basic.css" />-->
  <style type="text/css"><%=Io.textFileRead(WebAppRootDir.getWebAppRootPath()+"/css/pingFit-facebook.css").toString()%></style>
  <!--<link rel="stylesheet" type="text/css" href="/css/pingFit-facebook.css" />-->
  <!--<style type="text/css"><%//=Io.textFileRead(WebAppRootDir.getWebAppRootPath()+"/js/niftycube/niftyCorners.css").toString()%></style>-->
  <!--<link rel="stylesheet" type="text/css" href="/js/niftycube/niftyCorners.css" />-->
  <!--<script type="text/javascript" src="/js/niftycube/niftycube.js"></script>-->
  <!--<script type="text/javascript">
   NiftyLoad=function(){
       Nifty("div.rounded","big");
   }
  </script>-->
<!--</head>-->
<!--<body>-->
          

    <table width="100%" cellspacing="0" border="0" cellpadding="0">
        <tr>
            <td bgcolor="#dadada" style="text-align: left; vertical-align: bottom; border-bottom: 1px solid #666666;" colspan="5" height="45">
                <div width="100%" style="background: #cccccc">
                    <%if (Pagez.getUserSession().getIsloggedin()){%><a href="/account/accountbalance.jsp"><font class="subnavfont" style="padding-left: 12px; color: #000000; background: #cccccc;">Balance</font></a><%}%>
                    <%if (Pagez.getUserSession().getIsloggedin()){%><a href="/account/accountsettings.jsp"><font class="subnavfont" style="padding-left: 12px; color: #000000; background: #cccccc;">Settings</font></a><%}%>
                    <a href="/blog.jsp"><font class="subnavfont" style="padding-left: 12px; color: #000000; background: #cccccc;">Blog</font></a>
                    <a href="/blogger/facebookfaq.jsp"><font class="subnavfont" style="padding-left: 12px; color: #000000; background: #cccccc;">FAQ</font></a>
                    <%if (Pagez.getUserSession().getIsloggedin()){%><a href="/account/accountsupportissueslist.jsp"><font class="subnavfont" style="padding-left: 12px; color: #000000; background: #cccccc;">Help</font></a><%}%>
                </div>
            </td>
        </tr>
        <!--<tr>
            <td  bgcolor="#666666" colspan="5"><img src="/images/clear.gif" width="1" height="1"/></td>
        </tr>-->
        <%if (Pagez.getUserSession().getIsloggedin()){%>
            <tr>
                <td bgcolor="#ffffff" style="text-align: right; vertical-align: top;" colspan="5" height="15">
                    <div style="padding: 0px; text-align: right;">
                        <font class="subnavfont">Hi, <%=Pagez.getUserSession().getUser().getFirstname()%> <%=Pagez.getUserSession().getUser().getLastname()%>! <img src="/images/clear.gif" width="15" height="1"/></font>
                    </div>
                </td>
            </tr>
        <%}%>

    </table>
    <br/>

    <table width="624" cellspacing="0" border="0" cellpadding="5">
        <tr>
            <td valign="top">
                <table width="100%" cellspacing="0" border="0" cellpadding="1">
                    <tr>
                        <td valign="top">
                            <font class="pagetitlefont"><%=pagetitle%></font>
                        </td>
                        <td valign="top" width="100">
                             <img src="/images/dneero-logo-small.gif" width="100" height="46" border="0"/>
                        </td>
                    </tr>
                 </table>
                 <%
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

