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
<%@ page import="com.mythredz.dao.Post" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Post";
String navtab = "mythredz";
String acl = "account";
%>
<%@ include file="/template/auth.jsp" %>

<%
    Post post=new Post();
    if (request.getParameter("postid") != null && Num.isinteger(request.getParameter("postid"))) {
        post=Post.get(Integer.parseInt(request.getParameter("postid")));
    }
    if (post.getPostid()<=0){
        Pagez.getUserSession().setMessage("Sorry, you can't edit that post.");
        Pagez.sendRedirect("/account/index.jsp");
        return;
    }
%>

<%
Thred thred = null;
thred = Thred.get(post.getThredid());
if (thred!=null){
    if (thred.getUserid()!=Pagez.getUserSession().getUser().getUserid()){
        Pagez.getUserSession().setMessage("Sorry, you can't edit that post.");
        Pagez.sendRedirect("/account/index.jsp");
        return;
    }
}
%>


<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
    try {
        post.setContents(Textarea.getValueFromRequest("postcontent", "Post Content", true));
        post.save();
        Pagez.getUserSession().setMessage("Post saved.");
        Pagez.sendRedirect("/account/index.jsp");
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
        Pagez.getUserSession().setMessage("Are you sure you want to delete this post?  <a href=\"/account/postedit.jsp?postid="+post.getPostid()+"&action=deleteconfirm\">Yes, Delete It</a> <a href=\"/account/postedit.jsp?postid="+post.getPostid()+"\">No, Nevermind</a>");
    } catch (Exception ex) {
        logger.error("", ex);
        Pagez.getUserSession().setMessage("There was some sort of funky error.  A sysadmin has been contacted. Please try again.");
    }
}
%>

<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("deleteconfirm")) {
    try {
        post.delete();
        Pagez.getUserSession().setMessage("Post deleted.");
        Pagez.sendRedirect("/account/index.jsp");
        return;
    } catch (Exception ex) {
        logger.error("", ex);
        Pagez.getUserSession().setMessage("There was some sort of funky error.  A sysadmin has been contacted. Please try again.");
    }
}
%>

<%@ include file="/template/header.jsp" %>


    <br/><br/>
    <form action="/account/postedit.jsp" method="post">
        <input type="hidden" name="dpage" value="/account/postedit.jsp">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="postid" value="<%=request.getParameter("postid")%>">
            <table cellpadding="0" cellspacing="0" border="0">

                <tr>
                    <td valign="top" colspan="2">
                        <font class="formfieldnamefont">Post Content:</font>
                        <br/>
                        <%=Textarea.getHtml("postcontent", post.getContents(), 5, 45, "", "")%>
                    </td>
                </tr>



                <tr>
                    <td valign="top">
                    </td>
                    <td valign="top">
                        <br/><br/>
                        <input type="submit" class="formsubmitbutton" value="Save Post">
                        <%if (thred.getThredid()>0){%>
                            <br/><br/>
                            <a href="/account/postedit.jsp?postid=<%=post.getPostid()%>&action=delete"><font class="tinyfont">Delete Post</font></a>
                        <%}%>
                    </td>
                </tr>

            </table>
       </form>





<%@ include file="/template/footer.jsp" %>

