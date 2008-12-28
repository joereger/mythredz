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
<%@ page import="com.mythredz.systemprops.SystemProperty" %>
<%@ page import="com.mythredz.embed.ThredzAsHtml" %>
<%@ page import="com.mythredz.helpers.AfterPostingTodo" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Embed Thredz in Your Blog";
String navtab = "mythredz";
String acl = "account";
%>
<%@ include file="/template/auth.jsp" %>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("savecss")) {
        try {
            Pagez.getUserSession().getUser().setCss(Textarea.getValueFromRequest("css", "Custom CSS", false));
            Pagez.getUserSession().getUser().save();
            AfterPostingTodo.clearCaches(Pagez.getUserSession().getUser());
            Pagez.getUserSession().setMessage("Custom CSS saved.");
        } catch (Exception ex) {
            logger.error("", ex);
            Pagez.getUserSession().setMessage("There was some sort of funky error.  A sysadmin has been contacted. Please try again.");
        }
    }
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("resetcss")) {
    try {
        Pagez.getUserSession().getUser().setCss("");
        Pagez.getUserSession().getUser().save();
        AfterPostingTodo.clearCaches(Pagez.getUserSession().getUser());
        Pagez.getUserSession().setMessage("CSS reset.");
    } catch (Exception ex) {
        logger.error("", ex);
        Pagez.getUserSession().setMessage("There was some sort of funky error.  A sysadmin has been contacted. Please try again.");
    }
}
%>
<%@ include file="/template/header.jsp" %>

<font class="formfieldnamefont">The codez needed to embed Thredz into your blog.  Simply copy and paste the code into your blog template and you're up and running.  Of course, your blogging system must support javascript includes.</font>
<br/><br/>

<table cellpadding="10" cellspacing="0" border="0">
    <tr>
        <td valign="top">
            <div class="rounded" style="padding: 15px; margin: 5px; background: #e6e6e6;">
            <font class="mediumfont">Vertical Thredz (great for blog sidebar):</font>
            <div class="rounded" style="padding: 15px; margin: 5px; background: #ffffff;">
            &lt;script type="text/javascript" src="http://<%=SystemProperty.getProp(SystemProperty.PROP_BASEURL)%>/embed/vertjs/?u=<%=Pagez.getUserSession().getUser().getUserid()%>">&lt;/script>
            </div>
            </div>
        </td>
      </tr>
      <tr>
        <td valign="top">
            <div class="rounded" style="padding: 15px; margin: 5px; background: #e6e6e6;">
            <font class="mediumfont">Horizontal Thredz (great for center/top of blog):</font>
            <div class="rounded" style="padding: 15px; margin: 5px; background: #ffffff;">
            &lt;script type="text/javascript" src="http://<%=SystemProperty.getProp(SystemProperty.PROP_BASEURL)%>/embed/horizjs/?u=<%=Pagez.getUserSession().getUser().getUserid()%>">&lt;/script>
            </div>
            </div>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <div class="rounded" style="padding: 15px; margin: 5px; background: #cccccc;">
            <font class="mediumfont">Custom CSS:</font>
            <form action="/account/embed.jsp" method="post">
                <input type="hidden" name="dpage" value="/account/embed.jsp">
                <input type="hidden" name="action" value="savecss">
                <%
                    String css = ThredzAsHtml.getDefaultCssStyle();
                    if (Pagez.getUserSession().getUser().getCss()!=null && !Pagez.getUserSession().getUser().getCss().equals("")){
                        css=Pagez.getUserSession().getUser().getCss();
                    }
                %>
                <%=Textarea.getHtml("css", css, 15, 40, "", "width: 100%;")%>

                <br/><br/>
                <input type="submit" class="formsubmitbutton" value="Save Custom CSS">
                <br/>
                <a href="/account/embed.jsp?action=resetcss"><font class="tinyfont">Reset CSS</font></a>
            </form>
            </div>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <div class="rounded" style="padding: 15px; margin: 5px; background: #cccccc;">
            <font class="mediumfont">Once you get myThredz embedded, be sure to mouse over Publish to MyThredz for in-blog functionality.</font>
            </div>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <div class="rounded" style="padding: 15px; margin: 5px; background: #cccccc;">
            <font class="mediumfont">You can also embed only the most recent update from single thredz:</font>
            <br/>
            <%
                List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
                        .add(Restrictions.eq("userid", Pagez.getUserSession().getUser().getUserid()))
                        .setCacheable(true)
                        .list();

            %>
            <%if (threds==null || threds.size()==0){%>
                <font class="normalfont">You don't have any thredz yet.</font>
            <%} else {%>
                <%
                    ArrayList<GridCol> cols=new ArrayList<GridCol>();
                    cols.add(new GridCol("Thred", "<$name$>", false, "", "mediumfont"));
                    cols.add(new GridCol("Embed Code", "&lt;script type=\"text/javascript\" src=\"http://"+SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/embed/singlemostrecent/?u="+Pagez.getUserSession().getUser().getUserid()+"&t=<$thredid$>\">&lt;/script>", true, "", "tinyfont"));
                %>
                <%=Grid.render(threds, cols, 50, "/account/embed.jsp", "page")%>
            <%}%>
            </div>
        </td>
    </tr>

</table>










<%@ include file="/template/footer.jsp" %>

