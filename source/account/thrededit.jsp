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
List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
                .add(Restrictions.eq("userid", Pagez.getUserSession().getUser().getUserid()))
                .setCacheable(true)
                .list();
if (threds!=null && threds.size()>=7){
    Pagez.getUserSession().setMessage("Sorry, you can only have up to seven threds.");
    Pagez.sendRedirect("/account/thredzlist.jsp");
    return;
}
%>

<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
    try {
        thred.setName(Textbox.getValueFromRequest("name", "Thred Name", true, DatatypeString.DATATYPEID));
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
        Pagez.getUserSession().setMessage("Are you sure you want to delete this thred and all posts created for it?  <a href=\"/account/thrededit.jsp?thredid="+thred.getThredid()+"&action=deleteconfirm\">Yes, Delete It</a> <a href=\"/account/thredzlist,jsp\">No, Nevermind</a>");
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
                        <%=Textbox.getHtml("name", thred.getName(), 100, 20, "", "")%>
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

