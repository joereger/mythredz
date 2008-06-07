<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.PublicIndex" %>


<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "";
String navtab = "home";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>
<%
    PublicIndex publicIndex=(PublicIndex) Pagez.getBeanMgr().get("PublicIndex");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("completeexercise")) {
        try {

        }catch(Exception ex){
            logger.error("", ex);
        }
    }
%>

<%@ include file="/template/header.jsp" %>


<table cellpadding="10" cellspacing="0" border="0">
    <tr>
        <td valign="top">
            <div class="rounded" style="padding: 15px; margin: 5px; background: #e6e6e6;">
                <div class="rounded" style="padding: 15px; margin: 5px; background: #ffffff;">

                    <font class="largefont" style="background: #e6e6e6;">1) Create a list of Thredz.</font>
                    <br/><font class="tinyfont">Themes that you'd like to write a little about every day, every hour or every few minutes.  Starting points: "Work", "Family", "Hobby", "Yard Project."  Anything you want.</font>


                    <br/><br/><font class="largefont" style="background: #e6e6e6;">2) Write a bit.</font>
                    <br/><font class="tinyfont">A few words.  A few sentences.  Just enough to capture what's happening with that theme.</font>


                    <br/><br/><font class="largefont" style="background: #e6e6e6;">3) Embed onto your blog.</font>
                    <br/><font class="tinyfont">A small piece of your blog to hold a running commentary on the Thredz.</font>

                </div>
            </div>
        </td>
        <td valign="top" width="20%">
            <%if (!Pagez.getUserSession().getIsloggedin()){%>
                <div class="rounded" style="padding: 15px; margin: 5px; background: #e6e6e6;">
                    <a href="/registration.jsp"><font class="mediumfont">Sign Up</font></a>
                    <br/><a href="/login.jsp"><font class="mediumfont">Log In</font></a>
                </div>
            <%} else {%>
                <div class="rounded" style="padding: 15px; margin: 5px; background: #e6e6e6;">
                    <font class="smallfont">You're Logged In</font>
                    <br/><a href="/account/index.jsp"><font class="mediumfont">See your Thredz</font></a>
                </div>
            <%}%>
        </td>
    </tr>

</table>




<%@ include file="/template/footer.jsp" %>