<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.PublicProfile" %>
<%@ page import="com.mythredz.dbgrid.GridCol" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mythredz.dbgrid.Grid" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%
    PublicProfile publicProfile=(PublicProfile) Pagez.getBeanMgr().get("PublicProfile");
%>
<%
if (publicProfile==null || publicProfile.getUser()==null || publicProfile.getUser().getUserid()==0 || !publicProfile.getUser().getIsenabled()){
    Pagez.sendRedirect("/notauthorized.jsp");
    return;
}
%>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = publicProfile.getUser().getFirstname()+" "+publicProfile.getUser().getLastname()+"'s Profile";
String navtab = "home";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>

<%@ include file="/template/header.jsp" %>

    <%if (publicProfile.getMsg()!=null && !publicProfile.getMsg().equals("")){%>
        <center><div class="rounded" style="background: #F2FFBF; text-align: left; padding: 20px;"><font class="smallfont">
        <%=publicProfile.getMsg()%>
        </font></div></center>
        <br/><br/>
    <%}%>

    <div class="rounded" style="background: #e6e6e6; text-align: left; padding: 20px;">
    <table cellpadding="10" cellspacing="0" border="0" width="100%">
        <tr>
            <td valign="top" width="50%">
                <img src="/images/user.png" alt="" border="0" width="128" height="128"/>
            </td>
            <td valign="top">
                <div class="rounded" style="background: #ffffff; text-align: left; padding: 15px;">
                    <table cellpadding="10" cellspacing="0" border="0" width="100%">
                        <tr>
                            <td valign="top" width="50%">
                                <font class="formfieldnamefont">Social Influence Rating (TM)</font>
                            </td>
                            <td valign="top" width="50%">
                                <%if (publicProfile.getBlogger()!=null){%>
                                    <font class="smallfont"><%=publicProfile.getBlogger().getSocialinfluencerating()%></font>
                                    <br/>
                                    <font class="smallfont">Rank: <%=publicProfile.getBlogger().getSocialinfluenceratingranking()%></font>
                                <%} else {%>
                                    <font class="smallfont">na</font>
                                <%}%>
                            </td>
                        </tr>
                        <tr>
                            <td valign="top" width="50%">
                                <font class="formfieldnamefont">Amt Earned for Charity</font>
                            </td>
                            <td valign="top" width="50%">
                                <font class="smallfont"><%=publicProfile.getCharityamtdonatedForscreen()%></font>
                            </td>
                        </tr>
                    </table>
                </div>
            </td>
          </tr>
       </table>
       </div>








<%@ include file="/template/footer.jsp" %>