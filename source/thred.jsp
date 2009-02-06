<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.PublicProfile" %>
<%@ page import="com.mythredz.dbgrid.GridCol" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mythredz.dbgrid.Grid" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%@ page import="org.hibernate.criterion.Restrictions" %>
<%@ page import="com.mythredz.dao.Thred" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mythredz.dao.Post" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.mythredz.cache.providers.CacheFactory" %>
<%@ page import="com.mythredz.dao.hibernate.HibernateUtil" %>
<%@ page import="com.mythredz.htmluibeans.AccountIndex" %>
<%@ page import="org.hibernate.criterion.Order" %>
<%@ page import="com.mythredz.util.Time" %>
<%@ page import="com.mythredz.dao.User" %>
<%@ page import="com.mythredz.systemprops.SystemProperty" %>
<%@ page import="com.mythredz.util.Num" %>


<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "";
String navtab = "home";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>
<%
    Thred thred = null;
    if (Num.isinteger(request.getParameter("thredid"))) {
        thred = Thred.get(Integer.parseInt(request.getParameter("thredid")));
    }
%>
<%
if (thred==null || thred.getThredid()==0){
    Pagez.sendRedirect("/index.jsp");
    return;
}
%>
<%
User user = User.get(thred.getUserid());
if (!user.getIsenabled()){
    Pagez.sendRedirect("/index.jsp");
    return;
}
%>
<%@ include file="/template/header.jsp" %>




<font class="largefont" style="background: #e6e6e6;"><%=user.getNickname()%>: <%=thred.getName()%></font>
<br/><font class="tinyfont">http://<%=SystemProperty.getProp(SystemProperty.PROP_BASEURL)%>/user/<%=user.getNickname()%>/</font>

<br/><br/>


    <table cellpadding="0" cellspacing="5" border="0" width="100%">
    <tr>
            <td valign="top" width="100%">
                <font class="normalfont" style="font-weight: bold; background: #ffffff;"><%=thred.getName()%></font>
                <br/><font class="tinyfont" style="font-weight: bold; background: #ffffff;"><a href="http://<%=SystemProperty.getProp(SystemProperty.PROP_BASEURL)%>/thredrss/<%=thred.getThredid()%>/rss.xml">RSS</a></font>
                <br/><br/>
            </td>
    </tr>


    <tr>
    <td valign="top"><%
        List<Post> posts=HibernateUtil.getSession().createCriteria(Post.class)
                .add(Restrictions.eq("thredid", thred.getThredid()))
                .addOrder(Order.desc("date"))
                .setMaxResults(4000)
                .setCacheable(true)
                .list();
        for (Iterator<Post> iterator1=posts.iterator(); iterator1.hasNext();) {
            Post post=iterator1.next();
            %><font class="tinyfont" style="color: #cccccc; background: #999999;"><%=Time.dateformatcompactwithtime(post.getDate())%></font><br/><font class="smallfont"><%=post.getContents()%></font><br/><br/><%
        }
        %></td>
    </tr>
    </table>





<%@ include file="/template/footer.jsp" %>