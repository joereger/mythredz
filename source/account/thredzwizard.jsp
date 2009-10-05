<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.AccountIndex" %>
<%@ page import="com.mythredz.htmluibeans.AccountBalance" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mythredz.dao.hibernate.HibernateUtil" %>
<%@ page import="com.mythredz.dao.Thred" %>
<%@ page import="org.hibernate.criterion.Restrictions" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.mythredz.dao.Post" %>
<%@ page import="com.mythredz.util.Time" %>
<%@ page import="org.hibernate.criterion.Order" %>
<%@ page import="com.mythredz.cache.providers.CacheFactory" %>
<%@ page import="com.mythredz.systemprops.SystemProperty" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Choose Thredz to Get Started";
String navtab = "mythredz";
String acl = "account";
%>

<%@ include file="/template/auth.jsp" %>

<%
    if (Pagez.getRequest().getParameter("action") != null && Pagez.getRequest().getParameter("action").equals("save")) {

        if (request.getParameter("thred1")!=null && !request.getParameter("thred1").equals("")){
            Thred thred = new Thred();
            thred.setCreatedate(new java.util.Date());
            thred.setName(request.getParameter("thred1"));
            thred.setUserid(Pagez.getUserSession().getUser().getUserid());
            thred.setIstwitterupdateon(false);
            thred.setTwitterid("");
            thred.setTwitterpass("");
            thred.setIspingfmupdateon(false);
            thred.setPingfmapikey("");
            try{thred.save();}catch(Exception ex){logger.error("", ex);}
        }

        if (request.getParameter("thred2")!=null && !request.getParameter("thred2").equals("")){
            Thred thred = new Thred();
            thred.setCreatedate(new java.util.Date());
            thred.setName(request.getParameter("thred2"));
            thred.setUserid(Pagez.getUserSession().getUser().getUserid());
            thred.setIstwitterupdateon(false);
            thred.setTwitterid("");
            thred.setTwitterpass("");
            thred.setIspingfmupdateon(false);
            thred.setPingfmapikey("");
            try{thred.save();}catch(Exception ex){logger.error("", ex);}
        }

        if (request.getParameter("thred3")!=null && !request.getParameter("thred3").equals("")){
            Thred thred = new Thred();
            thred.setCreatedate(new java.util.Date());
            thred.setName(request.getParameter("thred3"));
            thred.setUserid(Pagez.getUserSession().getUser().getUserid());
            thred.setIstwitterupdateon(false);
            thred.setTwitterid("");
            thred.setTwitterpass("");
            thred.setIspingfmupdateon(false);
            thred.setPingfmapikey("");
            try{thred.save();}catch(Exception ex){logger.error("", ex);}
        }

        Pagez.getUserSession().setMessage("Your Thredz have been created.  Make an update by typing into one or all of the thred boxes and hitting save.  Be sure to <a href=\"/account/embed.jsp\">embed the widget into your blog</a>.");
        Pagez.sendRedirect("/account/index.jsp");
        return;

    }
%>

<%@ include file="/template/header.jsp" %>


    <div class="rounded" style="padding: 15px; margin: 5px; background: #e6e6e6;">
        <%if(request.getParameter("firsttime")!=null && request.getParameter("firsttime").equals("1")){%>
            <font class="mediumfont">Congrats!  You're all signed up!</font><br/><br/>
        <%}%>
        <font class="mediumfont">You need to create some thredz.  A thred is simply some recurring theme that you'd like to track.  Something you can write a little about each day.  You can always add or change thredz later on.</font>
    </div>

    <br/><br/>

    <%if(request.getParameter("firsttime")!=null && request.getParameter("firsttime").equals("1")){%>
        <div style="float: right;">
            <!-- Google Code for Sign up for myThredz.com Conversion Page -->
            <script language="JavaScript" type="text/javascript">
            <!--
            var google_conversion_id = 1072612680;
            var google_conversion_language = "en_US";
            var google_conversion_format = "1";
            var google_conversion_color = "ffffff";
            var google_conversion_label = "3FESCLD-TxDIirv_Aw";
            //-->
            </script>
            <script language="JavaScript" src="http://www.googleadservices.com/pagead/conversion.js">
            </script>
            <noscript>
            <img height="1" width="1" border="0" src="http://www.googleadservices.com/pagead/conversion/1072612680/?label=3FESCLD-TxDIirv_Aw&amp;script=0"/>
            </noscript>
        </div>
    <%}%>

    <form id="myForm" action="/account/thredzwizard.jsp" method="post">
        <input type="hidden" name="dpage" value="/account/thredzwizard.jsp">
        <input type="hidden" name="action" value="save">


        <table cellpadding="10" cellspacing="0" border="0">

                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">myThred #1</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("thred1", "Work", 20, 20, "", "font-size:45px;")%>
                    </td>
                </tr>

                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">myThred #2</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("thred2", "Family", 20, 20, "", "font-size:45px;")%>
                    </td>
                </tr>

                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">myThred #3</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("thred3", "Hobby", 20, 20, "", "font-size:45px;")%>
                    </td>
                </tr>

                <tr>
                    <td valign="top">
                    </td>
                    <td valign="top">
                        <br/><br/>
                        <input type="submit" class="formsubmitbutton" value="Save Thredz and Get Going!">
                    </td>
                </tr>

            </table>





    </form>


<%@ include file="/template/footer.jsp" %>


