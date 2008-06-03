<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmlui.Pagez" %>
<%@ page import="com.mythredz.htmluibeans.PublicFacebookAppAdd" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Social Surveys";
String navtab = "home";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>
<%@ include file="/template/header.jsp" %>


        <table cellpadding="0" border="0" width="100%">
            <tr>
                <td valign="top">
                    <div class="rounded" style="background: #e6e6e6; padding: 10px;">

                        <table border="0" cellspacing="10">
                        <tr>
                        <td width="25%">
                            <div class="rounded" style="background: #ffffff; padding: 10px;">
                                <center>
                                    <a href="<%=((com.mythredz.htmluibeans.PublicFacebookAppAdd)Pagez.getBeanMgr().get("PublicFacebookAppAdd")).getAddurl()%>" target="_top"><img src="/images/add-64.png" alt="" border="0"/></a>
                                    <br/><a href="<%=((PublicFacebookAppAdd)Pagez.getBeanMgr().get("PublicFacebookAppAdd")).getAddurl()%>" target="_top"><font class="formfieldnamefont">Add</font></a>
                                </center>
                            </div>
                        </td>
                        <td>
                            <font class="normalfont" style="font-weight: bold;">You need to <a href="<%=((PublicFacebookAppAdd)Pagez.getBeanMgr().get("PublicFacebookAppAdd")).getAddurl()%>" target="_top">add the dNeero Facebook application</a> before you can take part in social surveys.  Social surveys allow you to earn real money taking surveys and posting links to your answers on your profile.  There are no gimmicks, tricks or abusive processes to follow.</font><br/>
                        </td>
                        </tr>
                        </table>
                    </div>

                </td>
            </tr>
        </table>



<%@ include file="/template/footer.jsp" %>