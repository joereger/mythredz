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

<%
    User user=null;
    List<User> users = HibernateUtil.getSession().createCriteria(User.class)
                                       .add(Restrictions.eq("nickname", request.getParameter("nickname")))
                                       .setCacheable(true)
                                       .setMaxResults(1)
                                       .list();
    if (users!=null && users.size()>0){
        user = users.get(0);
    }
%>

<%
if (user==null){
    Pagez.sendRedirect("/index.jsp");
    return;    
}
%>

<%
boolean isLoggedInUsersProfile = false;
if (Pagez.getUserSession().getIsloggedin() && Pagez.getUserSession().getUser().getUserid()==user.getUserid()){
    isLoggedInUsersProfile = true;
}
%>

<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "";
String navtab = "home";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>


<%@ include file="/template/header.jsp" %>





<br/><br/>



    <table cellpadding="0" cellspacing="5" border="0" width="100%">
    <tr>
        <%
        List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
                .add(Restrictions.eq("userid", user.getUserid()))
                .setCacheable(true)
                .list();
        for (Iterator<Thred> iterator=threds.iterator(); iterator.hasNext();) {
            Thred thred=iterator.next();
            double widthDbl=100 / threds.size();
            Double widthBigDbl=new Double(widthDbl);
            int width=widthBigDbl.intValue();
            %>
            <td valign="top" width="<%=width%>%">
                <font class="normalfont" style="font-weight: bold;"><%=thred.getName()%></font>
            </td>
            <%

        }
        %>
    </tr>


    <tr>
    <%
        for (Iterator<Thred> iterator=threds.iterator(); iterator.hasNext();) {
            Thred thred=iterator.next();
    %><td valign="top"><%
        List<Post> posts=HibernateUtil.getSession().createCriteria(Post.class)
                .add(Restrictions.eq("thredid", thred.getThredid()))
                .addOrder(Order.desc("date"))
                .setMaxResults(100)
                .setCacheable(true)
                .list();
        for (Iterator<Post> iterator1=posts.iterator(); iterator1.hasNext();) {
            Post post=iterator1.next();
    %><font class="tinyfont" style="color: #cccccc;"><%=Time.dateformatcompactwithtime(post.getDate())%></font><br/><font class="smallfont"><%=post.getContents()%></font><br/><br/><%
        }
        %></td><%

        }
    %>
    </tr>
    </table>





<%@ include file="/template/footer.jsp" %>