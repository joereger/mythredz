<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.PublicBlogPost" %>
<%@ page import="com.mythredz.util.Time" %>
<%@ page import="com.mythredz.dbgrid.GridCol" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mythredz.dbgrid.Grid" %>
<%@ page import="com.mythredz.util.Util" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%@ page import="com.mythredz.util.RandomString" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "";
String navtab = "blog";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>
<%
PublicBlogPost publicBlogPost = (PublicBlogPost) Pagez.getBeanMgr().get("PublicBlogPost");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("addcomment")) {
        try {
            publicBlogPost.setName(Textbox.getValueFromRequest("name", "Name", false, DatatypeString.DATATYPEID));
            publicBlogPost.setUrl(Textbox.getValueFromRequest("url", "Url", false, DatatypeString.DATATYPEID));
            publicBlogPost.setComment(Textarea.getValueFromRequest("comment", "Comment", true));
            publicBlogPost.setJ_captcha_response(Textbox.getValueFromRequest("j_captcha_response", "Squiggly Letters", false, DatatypeString.DATATYPEID));
            publicBlogPost.setCaptchaId(request.getParameter("captchaId"));
            publicBlogPost.postComment();
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%
    String captchaId=RandomString.randomAlphanumeric(10);
%>
<%@ include file="/template/header.jsp" %>

    <font class="mediumfont" style="color: #0bae17;"><%=publicBlogPost.getBlogpost().getTitle()%></font>
    <br/>
    <font class="smallfont"><%=publicBlogPost.getBlogpost().getBody()%></font>
    <br/>
    <font class="tinyfont" style="color: #cccccc;">Posted by: <%=publicBlogPost.getBlogpost().getAuthor()%> at <%=Time.dateformatcompactwithtime(Time.getCalFromDate(publicBlogPost.getBlogpost().getDate()))%></font>
    <br/><br/>

     <%if (!Pagez.getUserSession().getIsfacebookui()){%>
        <%if (publicBlogPost.getBlogpost()==null || publicBlogPost.getBlogpost().getBlogpostcomments()==null || publicBlogPost.getBlogpost().getBlogpostcomments().size()==0){%>

        <%} else {%>
            <%
                StringBuffer comment=new StringBuffer();
                comment.append("<font class=\"tinyfont\"><$date|"+Grid.GRIDCOLRENDERER_DATETIMECOMPACT+"$></font>\n"+
    "        <font class=\"smallfont\">Comment by: </font>\n" +
    "        <a href=\"<$url$>\">\n" +
    "            <font class=\"smallfont\" style=\"color: #0000ff;\"><$name$></font>\n" +
    "        </a>\n" +
    "        <br/>\n" +
    "        <font class=\"tinyfont\"><$comment$></font>\n" +
    "        <br/><br/>");

                ArrayList<GridCol> cols=new ArrayList<GridCol>();
                cols.add(new GridCol("", comment.toString(), false, "", ""));
            %>
            <%=Grid.render(Util.setToArrayList(publicBlogPost.getBlogpost().getBlogpostcomments()), cols, 100, "/blogpost.jsp?blogpostid" + publicBlogPost.getBlogpost().getBlogpostid(), "page")%>
        <%}%>

        <form action="/blogpost.jsp" method="post">
            <input type="hidden" name="dpage" value="/blogpost.jsp">
            <input type="hidden" name="action" value="addcomment">
            <input type="hidden" name="blogpostid" value="<%=publicBlogPost.getBlogpost().getBlogpostid()%>">
            <input type="hidden" name="captchaId" value="<%=captchaId%>">
            <br/><br/>
            <font class="formfieldnamefont">Post a comment:</font>
            <br/>
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">Name</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("name", publicBlogPost.getName(), 255, 35, "", "")%>
                    </td>
                </tr>

                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">Url</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("url", publicBlogPost.getUrl(), 255, 35, "", "")%>
                    </td>
                </tr>

                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">Comment</font>
                    </td>
                    <td valign="top">
                        <%=Textarea.getHtml("comment", publicBlogPost.getComment(), 5, 40, "", "")%>
                    </td>
                </tr>

                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">Prove You're a Human</font>
                    </td>
                    <td valign="top">
                        <div style="border: 1px solid #ccc; padding: 3px;">
                        <%=Textbox.getHtml("j_captcha_response", publicBlogPost.getJ_captcha_response(), 255, 35, "", "")%>
                        <br/>
                        <font class="tinyfont">(type the squiggly letters that appear below)</font>
                        <br/>
                        <table cellpadding="0" cellspacing="0" border="0">
                            <tr>
                                <td><img src="/images/clear.gif" alt="" width="1" height="100"></img></td>
                                <td style="background: url(/images/loading-captcha.gif);">
                                    <img src="/images/clear.gif" alt="" width="200" height="1"></img><br/>
                                    <img src="/jcaptcha?captchaId=<%=captchaId%>" alt=""/>
                                </td>
                            </tr>
                        </table>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td valign="top">
                    </td>
                    <td valign="top">
                        <input type="submit" class="formsubmitbutton" value="Post Comment">
                    </td>
                </tr>



             </table>

         </form>
     <%}%>

<%@ include file="/template/footer.jsp" %>