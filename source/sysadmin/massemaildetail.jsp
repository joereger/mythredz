<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.SysadminMassemailDetail" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Mass Email Detail: "+((SysadminMassemailDetail)Pagez.getBeanMgr().get("SysadminMassemailDetail")).getMassemail().getSubject();
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
SysadminMassemailDetail sysadminMassemailDetail = (SysadminMassemailDetail)Pagez.getBeanMgr().get("SysadminMassemailDetail");
%>
<%
    if (request.getParameter("action") != null) {
        try {
            sysadminMassemailDetail.getMassemail().setSubject(Textbox.getValueFromRequest("subject", "Subject", true, DatatypeString.DATATYPEID));
            sysadminMassemailDetail.getMassemail().setTxtmessage(Textarea.getValueFromRequest("txtmessage", "Text Message", true));
            sysadminMassemailDetail.getMassemail().setHtmlmessage(Textarea.getValueFromRequest("htmlmessage", "Html Message", true));
            sysadminMassemailDetail.setTestsendemailaddress(Textbox.getValueFromRequest("testsendemailaddress", "Test Send Email Address", false, DatatypeString.DATATYPEID));
            if (request.getParameter("action").equals("save")){
                sysadminMassemailDetail.save();
                Pagez.getUserSession().setMessage("Saved!");
            } else if (request.getParameter("action").equals("send")){
                sysadminMassemailDetail.send();
                Pagez.sendRedirect("/sysadmin/massemailsend.jsp?massemailid="+sysadminMassemailDetail.getMassemail().getMassemailid());
                return;
            } else if (request.getParameter("action").equals("testsend")){
                sysadminMassemailDetail.testSend();
                Pagez.getUserSession().setMessage("Test send done.");
            } else if (request.getParameter("action").equals("copy")){
                sysadminMassemailDetail.copy();
                Pagez.getUserSession().setMessage("Mass Email Copied!");
                Pagez.sendRedirect("/sysadmin/massemaillist.jsp");
                return;
            }
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%@ include file="/template/header.jsp" %>


    <script language="JavaScript" type="text/javascript">
      var panels = new Array('panel1', 'panel2', 'panel3', 'panel4');
      var selectedTab = null;
      function showPanel(tab, name)
      {
        if (selectedTab)
        {
          selectedTab.style.backgroundColor = '';
          selectedTab.style.paddingTop = '';
          selectedTab.style.marginTop = '4px';
        }
        selectedTab = tab;
        selectedTab.style.backgroundColor = 'white';
        selectedTab.style.paddingTop = '6px';
        selectedTab.style.marginTop = '0px';

        for(i = 0; i < panels.length; i++){
          document.getElementById(panels[i]).style.display = (name == panels[i]) ? 'block':'none';
        }
        return false;
      }
    </script>
    <div id="tabs">
    <a href="" class="tab" onmousedown="return event.returnValue = showPanel(this, 'panel1');" id="tab1" onclick="return false;">Details</a>
    <a href="" class="tab" onmousedown="return event.returnValue = showPanel(this, 'panel2');" onclick="return false;">Html Preview</a>
    <a href="" class="tab" onmousedown="return event.returnValue = showPanel(this, 'panel3');" onclick="return false;">Txt Preview</a>
    <a href="" class="tab" onmousedown="return event.returnValue = showPanel(this, 'panel4');" onclick="return false;">Subject Preview</a>
    </div>
    <div class="panel" id="panel1" style="display: block">
        <img src="/images/clear.gif" width="700" height="1"/><br/>
        <form action="/sysadmin/massemaildetail.jsp" method="post">
            <input type="hidden" name="dpage" value="/sysadmin/massemaildetail.jsp">
            <input type="hidden" name="action" value="save" id="action">
            <input type="hidden" name="massemailid" value="<%=sysadminMassemailDetail.getMassemail().getMassemailid()%>">

            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">Subject</font>
                    </td>
                    <td valign="top">
                        <%=Textbox.getHtml("subject", sysadminMassemailDetail.getMassemail().getSubject(), 255, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">Text Message</font>
                    </td>
                    <td valign="top">
                        <%=Textarea.getHtml("txtmessage", sysadminMassemailDetail.getMassemail().getTxtmessage(), 3, 35, "", "")%>
                    </td>
                </tr>
                <tr>
                    <td valign="top">
                        <font class="formfieldnamefont">Html Message</font>
                    </td>
                    <td valign="top">
                        <%=Textarea.getHtml("htmlmessage", sysadminMassemailDetail.getMassemail().getHtmlmessage(), 3, 35, "", "")%>
                    </td>
                </tr>
             </table>
             <input type="submit" class="formsubmitbutton" value="Save and Preview"><br/>
             <input type="submit" class="formsubmitbutton" value="Send" onclick="document.getElementById('action').value='send';">


            <br/><br/>
            <font class="formfieldnamefont">Test-send to this email address:</font>
            <br/>
            <%=Textbox.getHtml("testsendemailaddress", sysadminMassemailDetail.getTestsendemailaddress(), 255, 35, "", "")%>
            <input type="submit" class="formsubmitbutton" value="Test Send" onclick="document.getElementById('action').value='testsend';">

            <br/><br/>
            <font class="formfieldnamefont">Create a copy of this Mass Email:</font>
            <br/>
            <input type="submit" class="formsubmitbutton" value="Copy" onclick="document.getElementById('action').value='copy';">

        </form>
        <br/><br/>
        <font class="smallfont">
             <br/>&lt;$user.email$>
             <br/>&lt;$user.firstname$>
             <br/>&lt;$user.lastname$>
             <br/>&lt;$user.userid$>
             <br/>&lt;$baseUrl.includinghttp$>
        </font>
    </div>
    <div class="panel" id="panel2" style="display: none">
        <img src="/images/clear.gif" width="700" height="1"/><br/>
        <%=sysadminMassemailDetail.getHtmlPreview()%>
    </div>
    <div class="panel" id="panel3" style="display: none">
        <img src="/images/clear.gif" width="700" height="1"/><br/>
        <textarea rows="12" cols="80" name="txtPreview"><%=sysadminMassemailDetail.getTxtPreview()%></textarea>
    </div>
    <div class="panel" id="panel4" style="display: none">
        <img src="/images/clear.gif" width="700" height="1"/><br/>
        <font class="normalfont" style="color: #000000;"><%=sysadminMassemailDetail.getSubjectPreview()%></font>
    </div>


<%@ include file="/template/footer.jsp" %>



