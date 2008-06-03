<%@ page import="com.mythredz.systemprops.InstanceProperties" %>
<!-- End Body -->
            </td>
        </tr>
    </table>

    <br/><br/>
    <table width="100%" cellspacing="0" border="0" cellpadding="0">
        <tr>
            <td valign="top" align="left">
                <font class="tinyfont" style="color: #666666; padding-left: 5px;"><a href="/eula.jsp">terms of service</a></font>
            </td>
            <td valign="top" align="right" style="text-align: right;">
                <font class="tinyfont" style="color: #cccccc; padding-right: 5px;">At Your Service is a Server Called: <%=InstanceProperties.getInstancename()%> which built this page in: <a href="/pageperformance.jsp" style="color: #cccccc;"><%=Pagez.getElapsedTime()%> milliseconds</a></font>
            </td>
        </tr>
    </table>

    <%
    String pgFooter = "/";
    if (request.getParameter("dpage")!=null){
        pgFooter = request.getParameter("dpage");
    }
    %>
    <fb:google-analytics uacct="UA-208946-7" page="<%=pgFooter%>"/>

<!--</body>-->
<!--</html>-->
