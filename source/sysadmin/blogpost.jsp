<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.SysadminBlogpost" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%@ page import="com.mythredz.dbgrid.GridCol" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mythredz.dbgrid.Grid" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Blog Posting";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
SysadminBlogpost sysadminBlogpost = (SysadminBlogpost)Pagez.getBeanMgr().get("SysadminBlogpost");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
        try {
            sysadminBlogpost.setAuthor(Textbox.getValueFromRequest("author", "Author", true, DatatypeString.DATATYPEID));
            sysadminBlogpost.setBody(Textarea.getValueFromRequest("body", "Body", true));
            sysadminBlogpost.setTitle(Textbox.getValueFromRequest("title", "Title", true, DatatypeString.DATATYPEID));
            sysadminBlogpost.setCategories(Textbox.getValueFromRequest("categories", "Categories", false, DatatypeString.DATATYPEID));
            sysadminBlogpost.setDate(DateTime.getValueFromRequest("date", "Date", true).getTime());
            sysadminBlogpost.save();
            Pagez.getUserSession().setMessage("Blog post saved.");
            Pagez.sendRedirect("/sysadmin/blogpost.jsp");
            return;
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("delete")) {
        try {
            sysadminBlogpost.delete();
            Pagez.getUserSession().setMessage("Blog post deleted.");
            Pagez.sendRedirect("/sysadmin/blogpost.jsp");
            return;
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%@ include file="/template/header.jsp" %>

    <form action="/sysadmin/blogpost.jsp" method="post">
        <input type="hidden" name="dpage" value="/sysadmin/blogpost.jsp">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="blogpostid" value="<%=sysadminBlogpost.getBlogpostid()%>">

        <table cellpadding="0" cellspacing="0" border="0">

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">Date</font>
                </td>
                <td valign="top">
                    <%=DateTime.getHtml("date", sysadminBlogpost.getDate(), "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">Author</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("author", sysadminBlogpost.getAuthor(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">Title</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("title", sysadminBlogpost.getTitle(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">Body</font>
                </td>
                <td valign="top">
                    <%=Textarea.getHtml("body", sysadminBlogpost.getBody(), 10, 75, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                    <font class="formfieldnamefont">Categories</font>
                    <br/>
                    <font class="smallfont">(comma-separated)</font>
                </td>
                <td valign="top">
                    <%=Textbox.getHtml("categories", sysadminBlogpost.getCategories(), 255, 35, "", "")%>
                </td>
            </tr>

            <tr>
                <td valign="top">
                </td>
                <td valign="top">
                    <input type="submit" class="formsubmitbutton" value="Save!">
                    <%if (sysadminBlogpost.getBlogpostid()>0){%>
                        <br/><br/>
                        <a href="/sysadmin/blogpost.jsp?blogpostid=<%=sysadminBlogpost.getBlogpostid()%>&action=delete"><font class="tinyfont">Delete</font></a>
                    <%}%>
                </td>
            </tr>

        </table>
    </form>



    <br/><br/>

    <%if (sysadminBlogpost.getBlogposts()==null || sysadminBlogpost.getBlogposts().size()==0){%>
        <font class="normalfont">No blog posts</font>
    <%} else {%>
        <%
            ArrayList<GridCol> cols=new ArrayList<GridCol>();
            cols.add(new GridCol("Date", "<$date|"+Grid.GRIDCOLRENDERER_DATETIMECOMPACT+"$>", true, "", "smallfont"));
            cols.add(new GridCol("Title", "<a href=\"/sysadmin/blogpost.jsp?blogpostid=<$blogpostid$>\"><$title$></a>", true, "", "smallfont"));
            cols.add(new GridCol("Author", "<$author$>", true, "", "smallfont"));
        %>
        <%=Grid.render(sysadminBlogpost.getBlogposts(), cols, 50, "/sysadmin/blogpost.jsp", "page")%>
    <%}%>






<%@ include file="/template/footer.jsp" %>


