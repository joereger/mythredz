<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.PublicIndex" %>


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

                    <font class="largefont" style="background: #f8c600;">1) Create Some Thredz</font><br/>
                    <font class="tinyfont">Themes that you'd like to write a little about every day, every hour or every few minutes.  Starting points: "Work", "Family", "Hobby", "Yard Project."  Anything you want.  You can have up to seven; three or four is recommended.</font><br/>


                    <br/><font class="largefont" style="background: #f8c600;">2) Write a Bit</font><br/>
                    <font class="tinyfont">A few words.  A few sentences.  Enough to capture what's happening with that Thred at the time.</font><br/>


                    <br/><font class="largefont" style="background: #f8c600;">3) Embed into Your blog</font><br/>
                    <font class="tinyfont">A small piece of your blog to hold a running commentary on the Thredz.  Vertical embed for the sidebar.  Horizontal embed for the top of your blog. See the horizontal embed in action at the top of <a href="http://www.joereger.com">joereger.com</a></font><br/>

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
                    <a href="/images/mythredz-screenshot-02.jpg" target="mythredzscreenshot"><img src="/images/mythredz-screenshot-02-thumb.gif" alt="" width="200" height="120" border="0"></a>
                    <br/><br/><font class="smallfont" style="background: #ffffff;">Screenshot: Embedded in a Blog</font><br/>
                    <font class="tinyfont">Thredz sitting at the top of a blog.  Note that there's one scrollable box for each Thred.</font><br/>
                    <a href="/images/mythredz-screenshot-02.jpg" target="mythredzscreenshot"><font class="tinyfont">Zoom +</font></a>
                </td>
            </tr>
            </table>

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
                <br/><font class="smallfont" style="background: #ffffff;">How do I publish?</font>
                <br/><font class="tinyfont">Once you embed the widget into your blog you can publish directly on your blog... without ever leaving!  You can also publish at myThredz.com.  We also have a low-bandwidth mobile phone page you can use.  And we'd love to make it so that you can publish directly from the embed widget on your blog.</font><br/>
                <br/><font class="smallfont" style="background: #ffffff;">What happens once I publish to a Thred?</font>
                <br/><font class="tinyfont">Your updates are published to your blog.  You have myThredz embedded on your blog.  It takes up a small piece of real estate.  People can see the Thredz that you're tracking and can scroll each one for updates.  Check <a href="http://www.joereger.com">joereger.com</a> for an example.  We'll also automatically update your Twitter status if you like.</font><br/>
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