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
            }
        }
        Pagez.getUserSession().setMessage("Thredz have been saved!");
    }
%>

<%@ include file="/template/header.jsp" %>

   <%if (!accountIndex.getMsg().equals("")) {%>
        <div class="rounded" style="padding: 15px; margin: 5px; background: #F2FFBF;">
            <font class="mediumfont"><%=((AccountIndex)Pagez.getBeanMgr().get("AccountIndex")).getMsg()%></font>
        </div>
   <%}%>

   <%if (Pagez.getRequest().getParameter("msg")!=null && Pagez.getRequest().getParameter("msg").equals("autologin")){%>
        <!--<div class="rounded" style="padding: 15px; margin: 5px; background: #F2FFBF;">
            <font class="mediumfont">Your previous session timed out so you've been logged-in automatically!</font>
        </div>-->
   <%}%>
   <%if (!Pagez.getUserSession().getUser().getIsactivatedbyemail()){%>
        <br/>
        <div class="rounded" style="padding: 15px; margin: 5px; background: #ffffcc;">
            <font class="mediumfont" style="color: #666666;">Your account has not yet been activated by email.</font>
            <br/>
            <font class="smallfont">You must activate within 3 days of signup.  Check your email inbox for an activation message.  If you've lost that message... no problem: <img src="/images/clear.gif" width="2" height="1"/><a href="/emailactivationresend.jsp">re-send it</a>.</font>
        </div>
    <%}%>

    <%--<div class="rounded" style="padding: 10px; margin: 10px; background: #e6e6e6;">--%>
        <%--<%String url = "http://"+SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/user/"+Pagez.getUserSession().getUser().getNickname();%>--%>
        <%--<font class="smallfont">Thredz as others see them: <a href="<%=url%>"><%=url%></a></font>--%>
        <%--<br/>--%>
        <%--<font class="smallfont">Embed into your blog: <a href="/account/embed.jsp">How to</a></font>--%>
    <%--</div>--%>

    <style type="text/css">

        #log_res.ajax-loading {
            padding: 20px 0;
            background: url(http://demos.mootools.net/demos/Group/spinner.gif) no-repeat center;
        }


    </style>

    <script type="text/javascript" language="javascript">
        window.addEvent('domready', function(){
            $('myForm').addEvent('submit', function(e) {
                /**
                 * Prevent the submit event
                 */
                new Event(e).stop();

                /**
                 * This empties the log and shows the spinning indicator
                 */
                var log = $('log_res').empty().addClass('ajax-loading');

                /**
                 * send takes care of encoding and returns the Ajax instance.
                 * onComplete removes the spinner from the log.
                 */
                this.send({
                    update: log,
                    onComplete: function() {
                        log.removeClass('ajax-loading');
                    }
                });

                /**
                 *  Clear the form
                 */
                this.reset();


            });
        });

    </script>





    <form id="myForm" action="/account/index-ajax.jsp" method="post">
        <input type="hidden" name="dpage" value="/account/index-ajax.jsp">
        <input type="hidden" name="action" value="save">




    <table cellpadding="0" cellspacing="5" border="0" width="100%">
    <tr>
    <%
        List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
                .add(Restrictions.eq("userid", Pagez.getUserSession().getUser().getUserid()))
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
    %>
            <td valign="top">
                <textarea name="threadid-<%=thred.getThredid()%>" rows="5" cols="5" style="width: 100%;"></textarea>
            </td>
            <%

        }
    %>
    </tr>



    <%
        int colspan=1;
        if (threds != null) {
            colspan=threds.size();
        }
    %>
    <tr>
    <td valign="top" colspan="<%=colspan%>"><center><input type="submit" class="formsubmitbutton" value="Save myThredz"><br/><br/></center></td>
    </tr>
    </table>


    <div id="log">
	<div id="log_res">

    <table cellpadding="0" cellspacing="5" border="0" width="100%">
    <tr>
    <%
        for (Iterator<Thred> iterator=threds.iterator(); iterator.hasNext();) {
            Thred thred=iterator.next();
            double widthDbl=100 / threds.size();
            Double widthBigDbl=new Double(widthDbl);
            int width=widthBigDbl.intValue();
            %><td valign="top" width="<%=width%>%"><%
            List<Post> posts=HibernateUtil.getSession().createCriteria(Post.class)
                    .add(Restrictions.eq("thredid", thred.getThredid()))
                    .addOrder(Order.desc("date"))
                    .setMaxResults(25)
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

    </div>
    </div>


    </form>


<%@ include file="/template/footer.jsp" %>


