<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.LoginAgreeNewEula" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "End User License Agreement";
String navtab = "home";
String acl = "account";
%>
<%@ include file="/template/auth.jsp" %>
<%
LoginAgreeNewEula loginAgreeNewEula = (LoginAgreeNewEula)Pagez.getBeanMgr().get("LoginAgreeNewEula");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("agree")) {
        try {
            loginAgreeNewEula.setEula(Textarea.getValueFromRequest("eula", "Eula", true));
            loginAgreeNewEula.agree();
            Pagez.sendRedirect("/account/index.jsp");
            return;
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%@ include file="/template/header.jsp" %>


    <form action="/loginagreeneweula.jsp" method="post">
        <input type="hidden" name="dpage" value="/loginagreeneweula.jsp">
        <input type="hidden" name="action" value="agree">
        
            <font class="formfieldnamefont">The End User License Agreement has changed.<br/>You must read and agree to it before you can proceed:</font>
            <br/><br/>
            <input type="submit" class="formsubmitbutton" value="I Agree to the EULA">
            <br/><br/>
            <%=Textarea.getHtml("eula", loginAgreeNewEula.getEula(), 25, 70, "", "")%>
            <br/><br/>
            <input type="submit" class="formsubmitbutton" value="I Agree to the EULA">

    </form>


<%@ include file="/template/footer.jsp" %>
