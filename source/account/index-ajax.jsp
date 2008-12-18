<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.AccountIndex" %>
<%@ page import="com.mythredz.htmluibeans.AccountBalance" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mythredz.dao.hibernate.HibernateUtil" %>
<%@ page import="com.mythredz.dao.Thred" %>
<%@ page import="org.hibernate.criterion.Restrictions" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.mythredz.dao.Post" %>
<%@ page import="com.mythredz.util.Time" %>
<%@ page import="org.hibernate.criterion.Order" %>
<%@ page import="com.mythredz.cache.providers.CacheFactory" %>
<%@ page import="com.mythredz.systemprops.SystemProperty" %>
<%@ page import="twitter4j.Twitter" %>
<%@ page import="twitter4j.Status" %>
<%@ page import="com.mythredz.util.Str" %>
<%@ page import="com.mythredz.twitter.TwitterUpdate" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "";
String navtab = "mythredz";
String acl = "account";
%>
<%
AccountIndex accountIndex = (AccountIndex) Pagez.getBeanMgr().get("AccountIndex");
%>
<%@ include file="/template/auth.jsp" %>

<%
    if (Pagez.getRequest().getParameter("action") != null && Pagez.getRequest().getParameter("action").equals("save")) {
        List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
                .add(Restrictions.eq("userid", Pagez.getUserSession().getUser().getUserid()))
                .setCacheable(true)
                .list();
        for (Iterator<Thred> iterator=threds.iterator(); iterator.hasNext();) {
            Thred thred=iterator.next();
            if (Pagez.getRequest().getParameter("threadid-" + thred.getThredid()) != null && !Pagez.getRequest().getParameter("threadid-" + thred.getThredid()).trim().equals("")) {
                if (Pagez.getUserSession().getUser().getUserid() == thred.getUserid()) {
                    String in=Pagez.getRequest().getParameter("threadid-" + thred.getThredid()).trim();
                    Post post=new Post();
                    post.setContents(in);
                    post.setDate(new java.util.Date());
                    post.setThredid(thred.getThredid());
                    try {
                        post.save();
                    } catch (Exception ex) {
                        logger.error("", ex);
                    }
                    //Clear the Javascript Embed cache
                    String nameInCache="embedjavascriptservlet-u" + Pagez.getUserSession().getUser().getUserid() + "-makeHttpsIfSSLIsOn" + false;
                    String cacheGroup="embedjavascriptcache" + "/";
                    CacheFactory.getCacheProvider().flush(nameInCache, cacheGroup);
                    String nameInCacheVert="embedjavascriptverticalservlet-u" + Pagez.getUserSession().getUser().getUserid() + "-makeHttpsIfSSLIsOn" + false;
                    String cacheGroupVert="embedjavascriptcache" + "/";
                    CacheFactory.getCacheProvider().flush(nameInCacheVert, cacheGroupVert);
                    //Update twitter
                    if (thred.getIstwitterupdateon()) {
                        String posttotwitter = Pagez.getRequest().getParameter("threadid" + thred.getThredid()+"posttotwitter");
                        if (posttotwitter!=null && posttotwitter.trim().equals("1")){
                            TwitterUpdate tu = new TwitterUpdate(thred.getTwitterid(), thred.getTwitterpass(), Str.truncateString(post.getContents(), 140));
                            tu.update();
                        }
                    }
                }
            }
        }
        //Pagez.getUserSession().setMessage("Thredz have been saved!");
    }
%>

<%//@ include file="/template/header.jsp" %>




    <table cellpadding="0" cellspacing="5" border="0" width="100%">
    




    <tr>
    <%
        List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
                .add(Restrictions.eq("userid", Pagez.getUserSession().getUser().getUserid()))
                .setCacheable(true)
                .list();
        logger.debug("threds.size()="+threds.size());
        for (Iterator<Thred> iterator=threds.iterator(); iterator.hasNext();) {
            Thred thred=iterator.next();
            double widthDbl=100 / threds.size();
            Double widthBigDbl=new Double(widthDbl);
            int width=widthBigDbl.intValue();
            %><td valign="top" width="<%=width%>%"><div style="height: 35px;"><font class="normalfont" style="font-weight: bold; background: #ffffff;"><%=thred.getName()%></font></div><%
                List<Post> posts=HibernateUtil.getSession().createCriteria(Post.class)
                        .add(Restrictions.eq("thredid", thred.getThredid()))
                        .addOrder(Order.desc("date"))
                        .addOrder(Order.desc("postid"))
                        .setMaxResults(25)
                        .setCacheable(true)
                        .list();
                for (Iterator<Post> iterator1=posts.iterator(); iterator1.hasNext();) {
                    Post post=iterator1.next();
                    %><font class="tinyfont" style="color: #cccccc; background: #999999;"><%=Time.dateformatcompactwithtime(post.getDate())%></font><br/><font class="smallfont"><%=post.getContents()%></font><%
                    %><br/><a href="/account/postedit.jsp?postid=<%=post.getPostid()%>"><font class="tinyfont">edit</font></a><br/><%
                    %><br/><br/><%
                }
                %></td><%

        }
    %>
    </tr>
    </table>


<%//@ include file="/template/footer.jsp" %>


