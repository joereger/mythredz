<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmlui.Pagez" %>
<%@ page import="com.mythredz.dao.Blogpost" %>
<%@ page import="com.mythredz.htmluibeans.PublicBlog" %>
<%@ page import="com.mythredz.dbgrid.GridCol" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mythredz.dbgrid.Grid" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "The myThredz Blog";
String navtab = "blog";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>
<%
PublicBlog publicBlog = (PublicBlog)Pagez.getBeanMgr().get("PublicBlog");
%>
<%@ include file="/template/header.jsp" %>


     <div style="width: 99%; text-align: right;">
        <a href="/rss.xml"><img src="/images/feed-icon-16x16.png" alt="RSS Feed" width="16" height="16" border="0"/> <font class="smallfont">RSS Feed</font></a>
     </div>
     <br/>

    <%if (publicBlog.getBlogposts()==null || publicBlog.getBlogposts().size()==0){%>
        <font class="normalfont">No blog posts yet.</font>
    <%} else {%>
        <%
        StringBuffer post = new StringBuffer();
        post.append("<a href=\"/blogpost.jsp?blogpostid=<$blogpostid$>\"><font class=\"mediumfont\" style=\"color: #0bae17; font-weight: bold;\"><$title$></font></a>\n" +
"        <br/>\n" +
"        <font class=\"smallfont\"><$body$></font>\n" +
"        <br/>\n" +
"        <font class=\"tinyfont\" style=\"color: #999999;\">Posted by: <$author$> at <$date|"+Grid.GRIDCOLRENDERER_DATETIMECOMPACT+"$></font>\n" +
"        <br/><br/>");

        ArrayList<GridCol> cols=new ArrayList<GridCol>();
        cols.add(new GridCol("", post.toString(), false, "", ""));
        %>
        <%=Grid.render(publicBlog.getBlogposts(), cols, 100, "/blog.jsp", "page")%>
    <%}%>
    <br/><br/> 




<%@ include file="/template/footer.jsp" %>