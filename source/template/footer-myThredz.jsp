<!-- End Body -->
</div>
</td>
</tr>
</table>

<br/><br/>
<table width="100%" cellspacing="0" border="0" cellpadding="0">
<tr>
    <td background="/images/navtabs2/linedots.gif"><img src="/images/clear.gif" width="1" height="1"/></td>
</tr>

<tr>
    <td bgcolor="#dadada" style="text-align: right; vertical-align: middle;" height="25">
        <center>
        <font class="tinyfont">Copyright 2008. All rights reserved.</font>
        |
        <a href="/eula.jsp"><font class="tinyfont">terms of use and privacy statement</font></a>
        |
        <a href="/aboutus.jsp"><font class="tinyfont">about us</font></a>
        <%if (Pagez.getUserSession().getIsloggedin()) {%>
                |
                <a href="/account/accountsupportissueslist.jsp"><font class="tinyfont">need help?</font></a>
            <%}%>
            <img src="/images/clear.gif" width="10" height="1"/>
        </center>
        </td>
    </tr>
    <tr>
        <td valign="top" align="right">
            <center><font class="tinyfont" style="color: #cccccc; padding-right: 10px;">At Your Service is a Server Called: <%=InstanceProperties.getInstancename()%> which built this page in: <a href="/pageperformance.jsp" style="color: #cccccc;"><%=Pagez.getElapsedTime()%> milliseconds</a></font></center>
        </td>
    </tr>
</table>
<br/>

    </center>

    <script type="text/javascript">
    var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
    document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
    </script>
    <script type="text/javascript">
    var pageTracker = _gat._getTracker("UA-208946-8");
    pageTracker._initData();
    pageTracker._trackPageview();
    </script>

</body>
</html>
