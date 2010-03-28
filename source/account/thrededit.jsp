<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.ChangePassword" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.criterion.Restrictions" %>
<%@ page import="com.mythredz.dao.hibernate.HibernateUtil" %>
<%@ page import="com.mythredz.dao.Thred" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.mythredz.dbgrid.Grid" %>
<%@ page import="com.mythredz.dbgrid.GridCol" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mythredz.util.Num" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Thred";
String navtab = "mythredz";
String acl = "account";
%>
<%@ include file="/template/auth.jsp" %>

<%
    Thred thred=new Thred();
    thred.setCreatedate(new java.util.Date());
    thred.setUserid(Pagez.getUserSession().getUser().getUserid());
    thred.setIstwitterupdateon(false);
    thred.setTwitterusername("");
    thred.setTwitteraccesstoken("");
    thred.setTwitteraccesstokensecret("");
    thred.setIspingfmupdateon(false);
    thred.setPingfmapikey("");
    if (request.getParameter("thredid") != null && Num.isinteger(request.getParameter("thredid"))) {
        thred = Thred.get(Integer.parseInt(request.getParameter("thredid")));
    }
%>

<%
if (thred.getUserid()!=Pagez.getUserSession().getUser().getUserid()){
    Pagez.getUserSession().setMessage("Sorry, you can't edit that thred.");
    Pagez.sendRedirect("/account/thredzlist.jsp");
    return;
}
%>

<%
if (thred.getThredid()==0){
    List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
                    .add(Restrictions.eq("userid", Pagez.getUserSession().getUser().getUserid()))
                    .setCacheable(true)
                    .list();
    if (threds!=null && threds.size()>=7){
        Pagez.getUserSession().setMessage("Sorry, you can only have up to seven threds.");
        Pagez.sendRedirect("/account/thredzlist.jsp");
        return;
    }
}
%>

<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
    try {
        thred.setName(Textbox.getValueFromRequest("name", "Thred Name", true, DatatypeString.DATATYPEID));
        thred.setIstwitterupdateon(CheckboxBoolean.getValueFromRequest("istwitterupdateon"));
        thred.setTwitterusername(Textbox.getValueFromRequest("twitterusername", "Twitter Username", false, DatatypeString.DATATYPEID));
        thred.setTwitteraccesstoken(Textbox.getValueFromRequest("twitteraccesstoken", "Twitter Access Token", false, DatatypeString.DATATYPEID));
        thred.setTwitteraccesstokensecret(Textbox.getValueFromRequest("twitteraccesstokensecret", "Twitter Access Token", false, DatatypeString.DATATYPEID));
        thred.setIspingfmupdateon(CheckboxBoolean.getValueFromRequest("ispingfmupdateon"));
        thred.setPingfmapikey(Textbox.getValueFromRequest("pingfmapikey", "Ping.fm API Key", false, DatatypeString.DATATYPEID));
        thred.save();
        Pagez.getUserSession().setMessage("Thred saved.");
        Pagez.sendRedirect("/account/thredzlist.jsp");
        return;
    } catch (ValidationException vex){
        Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
    } catch (Exception ex) {
        logger.error("", ex);
        Pagez.getUserSession().setMessage("There was some sort of funky error.  A sysadmin has been contacted. Please try again.");
    }
}
%>

<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("delete")) {
    try {
        Pagez.getUserSession().setMessage("Are you sure you want to delete this thred and all posts created for it?  <a href=\"/account/thrededit.jsp?thredid="+thred.getThredid()+"&action=deleteconfirm\">Yes, Delete It</a> <a href=\"/account/thredzlist.jsp\">No, Nevermind</a>");
    } catch (Exception ex) {
        logger.error("", ex);
        Pagez.getUserSession().setMessage("There was some sort of funky error.  A sysadmin has been contacted. Please try again.");
    }
}
%>

<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("deleteconfirm")) {
    try {
        HibernateUtil.getSession().createQuery("delete Post p where p.thredid="+thred.getThredid()).executeUpdate();
        thred.delete();
        Pagez.getUserSession().setMessage("Thred deleted.");
        Pagez.sendRedirect("/account/thredzlist.jsp");
        return;
    } catch (Exception ex) {
        logger.error("", ex);
        Pagez.getUserSession().setMessage("There was some sort of funky error.  A sysadmin has been contacted. Please try again.");
    }
}
%>

<%@ include file="/template/header.jsp" %>


    <br/><br/>
    <form action="/account/thrededit.jsp" method="post">
        <input type="hidden" name="dpage" value="/account/thrededit.jsp">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="thredid" value="<%=request.getParameter("thredid")%>">
            <table cellpadding="0" cellspacing="0" border="0">

                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">Thred Name</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("name", thred.getName(), 20, 20, "", "font-size:45px;")%>
                        <br/><br/><br/>
                    </td>
                </tr>


                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">Twitter</font>
                    </td>
                    <td valign="top">
                        <%=CheckboxBoolean.getHtml("istwitterupdateon", thred.getIstwitterupdateon(), "", "")%> <font class="formfieldnamefont">Yes, Update Twitter</font>
                        <br/>
                        <font class="tinyfont">Sending updates from this Thred to Twitter limits the input size for this Thred to 140 characters.  Note, you must Authorize Twitter to accept updates from this application.</font>
                        <br/>
                        <%if (thred.getTwitteraccesstoken().length()>0 && thred.getTwitteraccesstokensecret().length()>0){%>
                            <font class="formfieldnamefont"><a href="/twitterredirector?thredid=<%=thred.getThredid()%>">Re-Authorize Twitter</a></font>
                        <%} else {%>
                            <font class="formfieldnamefont"><a href="/twitterredirector?thredid=<%=thred.getThredid()%>">Authorize Twitter</a></font>
                        <%}%>
                        <br/><br/>
                    </td>
                </tr>

                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">Ping.fm</font>
                    </td>
                    <td valign="top">
                        <%=CheckboxBoolean.getHtml("ispingfmupdateon", thred.getIspingfmupdateon(), "", "")%> <font class="formfieldnamefont">Yes, Update Ping.fm</font>
                        <br/>
                        <font class="tinyfont">Send updates from this Thred to <a href="http://ping.fm/">Ping.fm</a>.</font>
                        <br/>
                        <font class="formfieldnamefont">Ping.fm App Key:</font>
                        <br/>
                        <%=Textbox.getHtml("pingfmapikey", thred.getPingfmapikey(), 250, 20, "", "")%>
                        <br/>
                        <font class="tinyfont">Get your Ping.fm App key <a href="http://ping.fm/key/">here</a>.</font>
                    </td>
                </tr>



                <tr>
                    <td valign="top">
                    </td>
                    <td valign="top">
                        <br/><br/>
                        <input type="submit" class="formsubmitbutton" value="Save Thred">
                        <%if (thred.getThredid()>0){%>
                            <br/><br/>
                            <a href="/account/thrededit.jsp?thredid=<%=thred.getThredid()%>&action=delete"><font class="tinyfont">Delete Thred</font></a>
                        <%}%>
                    </td>
                </tr>

            </table>
       </form>





<%@ include file="/template/footer.jsp" %>

