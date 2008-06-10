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
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Embed Thredz in Your Blog";
String navtab = "mythredz";
String acl = "account";
%>
<%@ include file="/template/auth.jsp" %>
<%@ include file="/template/header.jsp" %>

<font class="formfieldnamefont">The codez needed to embed Thredz into your blog.  Simply copy and paste the code into your blog template and you're up and running.  Of course, your blogging system must support javascript includes.</font>
<br/><br/>

<table cellpadding="10" cellspacing="0" border="0">
    <tr>
        <td valign="top">
            <div class="rounded" style="padding: 15px; margin: 5px; background: #e6e6e6;">
            <font class="mediumfont">Vertical Thredz (great for blog sidebar):</font>
            <div class="rounded" style="padding: 15px; margin: 5px; background: #ffffff;">
            &lt;script type="text/javascript" src="http://<%=SystemProperty.getProp(SystemProperty.PROP_BASEURL)%>/embed/horizjs/?u=<%=Pagez.getUserSession().getUser().getUserid()%>">&lt;/script>
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

</table>










<%@ include file="/template/footer.jsp" %>

