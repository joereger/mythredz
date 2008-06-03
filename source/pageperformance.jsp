<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmlui.Pagez" %>
<%@ page import="com.mythredz.dbgrid.Grid" %>
<%@ page import="com.mythredz.dbgrid.GridCol" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mythredz.htmluibeans.PublicPagePerformance" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Page Load Times";
String navtab = "home";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>
<%
    PublicPagePerformance publicPagePerformance = (PublicPagePerformance) Pagez.getBeanMgr().get("PublicPagePerformance");
%>
<%@ include file="/template/header.jsp" %>

        <font class="mediumfont">Recent Metrics</font><br/>
        <font class="smallfont">Use this page to get a peek into the technical world of dNeero.  Here you can see how long, on average, it takes each of the pages on the site to load.  This gives you a sense of the computational challenge required to build each page.  Load Times are in milliseconds... 1000 ms = 1 second.  Pages with a pre-pended [FB] are pages served into the dNeero Facebook App. </font><br/><br/>


        <% if (publicPagePerformance.getPageid()!=null){ %>
            <br/>
            <font class="mediumfont" style="color: #cccccc;"><%=publicPagePerformance.getPageid()%></font><br/><br/>
            <%if (publicPagePerformance.getPpssinglepageid()==null || publicPagePerformance.getPpssinglepageid().size()==0){%>
                <font class="normalfont">No metrics found.</font>
            <%} else {%>
                <%
                    ArrayList<GridCol> cols=new ArrayList<GridCol>();
                    cols.add(new GridCol("Year", "<$year$>", false, "", "tinyfont"));
                    cols.add(new GridCol("Month", "<$month$>", false, "", "tinyfont"));
                    cols.add(new GridCol("Day", "<$day$>", false, "", "tinyfont"));
                    cols.add(new GridCol("Hour", "<$partofday$>", false, "", "tinyfont"));
                    cols.add(new GridCol("Server", "<$servername$>", false, "", "tinyfont"));
                    cols.add(new GridCol("Page", "<a href=\"/pageperformance.jsp?pageid=<$pageid$>\"><$pageid$></a>", false, "", "tinyfont"));
                    cols.add(new GridCol("Avg Load Time (ms)", "<$avgtime$>", false, "", "tinyfont"));
                %>
                <%=Grid.render(publicPagePerformance.getPpssinglepageid(), cols, 200, "/pageperformance.jsp", "pagesingle")%>
            <%}%>

        <%} else {%>


            <%if (publicPagePerformance.getPps()==null || publicPagePerformance.getPps().size()==0){%>
                <font class="normalfont">No metrics found.</font>
            <%} else {%>
                <%
                    ArrayList<GridCol> cols = new ArrayList<GridCol>();
                    cols.add(new GridCol("Year", "<$year$>", false, "", "tinyfont"));
                    cols.add(new GridCol("Month", "<$month$>", false, "", "tinyfont"));
                    cols.add(new GridCol("Day", "<$day$>", false, "", "tinyfont"));
                    cols.add(new GridCol("Hour", "<$partofday$>", false, "", "tinyfont"));
                    cols.add(new GridCol("Server", "<$servername$>", false, "", "tinyfont"));
                    cols.add(new GridCol("Page", "<a href=\"/pageperformance.jsp?pageid=<$pageid$>\"><$pageid$></a>", false, "", "tinyfont"));
                    cols.add(new GridCol("Avg Load Time (ms)", "<$avgtime$>", false, "", "tinyfont"));
                %>
                <%=Grid.render(publicPagePerformance.getPps(), cols, 200, "/pageperformance.jsp", "page")%>
            <%}%>

        <%}%>


<%@ include file="/template/footer.jsp" %>
