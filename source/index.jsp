<%@ page import="com.mythredz.dao.User" %>
<%@ page import="com.mythredz.dao.hibernate.HibernateUtil" %>
<%@ page import="com.mythredz.dbgrid.Grid" %>
<%@ page import="com.mythredz.dbgrid.GridCol" %>
<%@ page import="com.mythredz.htmluibeans.PublicIndex" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="org.hibernate.criterion.Order" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>


<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "";
String navtab = "home";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>
<%
    PublicIndex publicIndex=(PublicIndex) Pagez.getBeanMgr().get("PublicIndex");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("completeexercise")) {
        try {

        }catch(Exception ex){
            logger.error("", ex);
        }
    }
%>

<%@ include file="/template/header.jsp" %>


<table cellpadding="10" cellspacing="0" border="0">
    <tr>
        <td valign="top" style="text-align: left;">
            <%--<div class="rounded" style="padding: 15px; margin: 5px; background: #e6e6e6;">--%>
                <%--<div class="rounded" style="padding: 15px; margin: 5px; background: #ffffff;">--%>

                    <font class="largefont" style="background: #bfdbff;">1) Create the Thredz</font><br/>
                    <font class="tinyfont">Themes that you'd like to write a little about every day, every hour or every few minutes.  "Work" or "Family" or "Training for My Marathon" or "Learning to Live with Cancer"... whatever's happening in your life.  You can have up to seven; three or four is recommended.</font><br/>


                    <br/><font class="largefont" style="background: #bfdbff;">2) Spread the Thredz</font><br/>
                    <font class="tinyfont">Share your status with friends and family.  We give you a profile page listing your progress on each of the thredz.  You can embed into a blog.  Or we'll update your Twitter status for you.  Get the word out people!</font><br/>


                    <br/><font class="largefont" style="background: #bfdbff;">3) Magical Nightly Email</font><br/>
                    <font class="tinyfont">We'll gently prompt you with a nightly email.  But it's no ordinary email.  Just hit reply and tell us what happened in each of your thredz.  We'll parse it and update your Thredz.  Easy... no need to log in to a website to update your status.  Send us a few words.  A few sentences.  Whatever you're up for.  Of course, you can always update your status via the web or mobile too.  Or you can ignore your Thredz and not update them.  But what fun would that be?</font><br/>

                <!--</div>-->
            <!--</div>-->

            <br/><br/>
            <table cellpadding="10" cellspacing="0" border="0">
            <tr>
                <td valign="top">
                    <a href="/images/mythredz-screenshot-01.jpg" target="mythredzscreenshot"><img src="/images/mythredz-screenshot-01-thumb.gif" alt="" width="200" height="120" border="0"></a>
                    <br/><br/><font class="smallfont" style="background: #ffffff;">Screenshot: Publishing Screen</font><br/>
                    <font class="tinyfont">Once you define your Thredz you get a dead-simple publishing screen with an input box for each one.  You type a bit and click Publish.  Your updates are saved.</font><br/>
                    <a href="/images/mythredz-screenshot-01.jpg" target="mythredzscreenshot"><font class="tinyfont">Zoom +</font></a>
                </td>
                <td valign="top">
                    <a href="/images/mythredz-screenshot-04.jpg" target="mythredzscreenshot"><img src="/images/mythredz-screenshot-04-thumb.gif" alt="" width="200" height="120" border="0"></a>
                    <br/><br/><font class="smallfont" style="background: #ffffff;">Screenshot: Updating via Email</font><br/>
                    <font class="tinyfont">It's so easy to update your Thredz via email.</font><br/>
                    <a href="/images/mythredz-screenshot-04.jpg" target="mythredzscreenshot"><font class="tinyfont">Zoom +</font></a>
                </td>
            </tr>
            <tr>
                <td valign="top">
                    <a href="/images/mythredz-screenshot-03.jpg" target="mythredzscreenshot"><img src="/images/mythredz-screenshot-03-thumb.gif" alt="" width="200" height="120" border="0"></a>
                    <br/><br/><font class="smallfont" style="background: #ffffff;">Screenshot: Embedded in Side of Blog</font><br/>
                    <font class="tinyfont">Once you define your Thredz you get a dead-simple publishing screen with an input box for each one.  You type a bit and click Publish.  Your updates are saved.</font><br/>
                    <a href="/images/mythredz-screenshot-03.jpg" target="mythredzscreenshot"><font class="tinyfont">Zoom +</font></a>
                </td>
                <td valign="top">
                    <a href="/images/mythredz-screenshot-02.jpg" target="mythredzscreenshot"><img src="/images/mythredz-screenshot-02-thumb.gif" alt="" width="200" height="120" border="0"></a>
                    <br/><br/><font class="smallfont" style="background: #ffffff;">Screenshot: Embedded in Top of Blog</font><br/>
                    <font class="tinyfont">You can embed a horizontal or a vertical version of MyThredz.  This is a screenshot of a horizontal embed.</font><br/>
                    <a href="/images/mythredz-screenshot-02.jpg" target="mythredzscreenshot"><font class="tinyfont">Zoom +</font></a>
                </td>
            </tr>
            </table>

            <!-- List of Profiles -->

            <%
                List<User> users = HibernateUtil.getSession().createCriteria(User.class)
                        .addOrder(Order.desc("userid"))
                        .setCacheable(true)
                        .setMaxResults(100)
                        .list();

            %>

            <br/><br/>
            <%if (users ==null || users.size()==0){%>
                <font class="normalfont">No Users yet.</font>
            <%} else {%>
                <%
                    ArrayList<GridCol> cols=new ArrayList<GridCol>();
                    cols.add(new GridCol("Profiles", "<a href=\"/user/<$nickname$>\"><$nickname$></a>", true, "", "tinyfont"));
                %>
                <%=Grid.render(users, cols, 50, "/index.jsp", "page")%>
            <%}%>


            <!-- List of Profiles End -->

        </td>
        <td valign="top" width="50%">
            <%if (!Pagez.getUserSession().getIsloggedin()){%>
                <div class="rounded" style="padding: 15px; background: #e6e6e6;">
                    <font class="smallfont">You're Not Logged In</font>
                    <br/><a href="/login.jsp"><font class="mediumfont">>Log In</font></a>
                    <br/><a href="/registration.jsp"><font class="mediumfont">>Sign Up</font></a>
                </div>
            <%} else {%>
                <div class="rounded" style="padding: 15px; background: #e6e6e6;">
                    <font class="smallfont">You're Logged In (because you rock)</font>
                    <br/><a href="/account/index.jsp"><font class="mediumfont">> Publish Stuff</font></a>
                    <br/><a href="/user/<%=Pagez.getUserSession().getUser().getNickname()%>/"><font class="mediumfont">> How Others See You</font></a>
                    <br/><a href="/account/embed.jsp"><font class="mediumfont">> Embed into Your Blog</font></a>
                </div>
            <%}%>
            <br/><br/>
            <div class="rounded" style="padding: 15px; background: #e6e6e6;">
                <font class="smallfont" style="background: #ffffff;">What is myThredz?</font>
                <br/><font class="tinyfont">A microblogging system that you can use to track, organize and publish those short updates that aren't quite worth a full blog post.</font><br/>
                <br/><font class="smallfont" style="background: #ffffff;">What's unique about it?</font>
                <br/><font class="tinyfont">The concept of Thredz.  These are themes.  You choose a Thred that you want to track... "Work" or "Family" or "Training for My Marathon" or "Learning to Live with Cancer"... whatever's happening in your life.  You then post your updates to one of these Thredz.</font><br/>
                <br/><font class="smallfont" style="background: #ffffff;">What else is unique about it?</font>
                <br/><font class="tinyfont">The email interface.  We'll send you a nightly email.  You simply reply to it, updating the status of each of your Thredz.</font><br/>
                <br/><font class="smallfont" style="background: #ffffff;">How do I publish?</font>
                <br/><font class="tinyfont">Once you embed the widget into your blog you can publish directly on your blog... without ever leaving!  You can also publish at myThredz.com.  We also have a low-bandwidth mobile phone page you can use.  And you can reply to the nightly emails.  Lots of ways to publish.</font><br/>
                <br/><font class="smallfont" style="background: #ffffff;">What happens once I publish to a Thred?</font>
                <br/><font class="tinyfont">Your updates are published to your blog.  You have myThredz embedded on your blog.  It takes up a small piece of real estate.  People can see the Thredz that you're tracking and can scroll each one for updates.  Check <a href="http://www.joereger.com">joereger.com</a> for an example.  We'll also automatically update your Twitter status if you like.</font><br/>
                <br/><font class="smallfont" style="background: #ffffff;">Can I update my Twitter status?</font>
                <br/><font class="tinyfont">Yep.  You can control Twitter settings per Thred... so some can update Twitter while others won't.</font><br/>
                <br/><font class="smallfont" style="background: #ffffff;">Why do we need this?</font>
                <br/><font class="tinyfont">There's tremendous value in the details of life but often such details tend to overwhelm a focused blog.  Enter microblogging.  We have single-Thred microblogging systems like Twitter, etc.  But their organization of themes isn't (yet?) strong and their embeds are simplistic.  You get one publishing stream, that's it.  We wanted something that takes over a little more screen real estate than the tiny Twitter embed while illustrating the Thredz.  In this way your blog readers who don't care about your work can simply ignore the "Work" Thred (assuming you have one.)</font><br/>
                <br/><font class="smallfont" style="background: #ffffff;">What blogging systems are supported?</font>
                <br/><font class="tinyfont">Any blogging system that allows you to publish Javascript includes.  At this point we don't have a list.  Let us know what we're missing.</font><br/>
                <br/><font class="smallfont" style="background: #ffffff;">Is this a social network?</font>
                <br/><font class="tinyfont">No.  It could be.  We could do the whole Follow thing and create a river of friend news.  But we don't even have users yet. We believe in the bootstrapping ethos.  Kick us in the nads if you want social features.</font><br/>
                <br/><font class="smallfont" style="background: #ffffff;">Does it have RSS?</font>
                <br/><font class="tinyfont">What do you think this is... 1997?  Yes, you get an RSS feed for each of your Thredz.</font><br/>
                <br/><font class="smallfont" style="background: #ffffff;">How much does it cost?</font>
                <br/><font class="tinyfont">Completely free.  At this point we have no idea how/if we'll make money on it.  Dear Twitter, Please master a monetization model on your VC's nickel so that we can apply it to our stuff.  We have no nickels but we'll catch you up when Web 3.0 comes around.  Sincerely, MyThredz</font><br/>
            </div>
        </td>
    </tr>

</table>




<%@ include file="/template/footer.jsp" %>