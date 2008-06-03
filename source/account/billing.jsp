<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.ResearcherBilling" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%@ page import="com.mythredz.htmluibeans.AccountBilling" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Billing Details";
String navtab = "researchers";
String acl = "account";
%>
<%@ include file="/template/auth.jsp" %>
<%
    AccountBilling researcherBilling = (AccountBilling) Pagez.getBeanMgr().get("AccountBilling");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
        try {
            researcherBilling.setCccity(Textbox.getValueFromRequest("cccity", "City", true, DatatypeString.DATATYPEID));
            researcherBilling.setCcexpmo(Dropdown.getIntFromRequest("ccexpmo", "Expiration Month", true));
            researcherBilling.setCcexpyear(Dropdown.getIntFromRequest("ccexpyear", "Expiration Year", true));
            researcherBilling.setCcnum(Textbox.getValueFromRequest("ccnum", "Credit Card Number", true, DatatypeString.DATATYPEID));
            researcherBilling.setCcstate(Textbox.getValueFromRequest("ccstate", "State", true, DatatypeString.DATATYPEID));
            researcherBilling.setCctype(Dropdown.getIntFromRequest("cctype", "Credit Card Type", true));
            researcherBilling.setCvv2(Textbox.getValueFromRequest("cvv2", "CVV2", true, DatatypeString.DATATYPEID));
            researcherBilling.setFirstname(Textbox.getValueFromRequest("firstname", "First Name", true, DatatypeString.DATATYPEID));
            researcherBilling.setLastname(Textbox.getValueFromRequest("lastname", "Last Name", true, DatatypeString.DATATYPEID));
            researcherBilling.setPostalcode(Textbox.getValueFromRequest("postalcode", "Zip", true, DatatypeString.DATATYPEID));
            researcherBilling.setStreet(Textbox.getValueFromRequest("street", "Street", true, DatatypeString.DATATYPEID));
            researcherBilling.saveAction();
            Pagez.getUserSession().setMessage("Billing information saved.");
            Pagez.sendRedirect("/researcher/index.jsp");
            return;
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        } catch (Exception ex){
            Pagez.getUserSession().setMessage("There has been an error. Please try again.");
        }
    }
%>
<%@ include file="/template/header.jsp" %>

    <form action="/researcher/researcherbilling.jsp" method="post">
        <input type="hidden" name="dpage" value="/researcher/researcherbilling.jsp">
        <input type="hidden" name="action" value="save">
           <table cellpadding="0" cellspacing="0" border="0">
               <tr>

                   <td valign="top" align="left">
                        <font class="mediumfont" style="color: #cccccc;">Credit Card Info</font>
                            <br/>

                            <table cellpadding="3" cellspacing="0" border="0">

                            <tr>
                               <td valign="top" align="left">
                                    <font class="formfieldnamefont">Name</font>
                                    <br/>
                                    <font class="tinyfont">(first then last)</font>
                               </td>
                               <td valign="top" align="left">
                                    <%=Textbox.getHtml("firstname", researcherBilling.getFirstname(), 255, 15, "", "")%>
                                    <%=Textbox.getHtml("lastname", researcherBilling.getLastname(), 255, 15, "", "")%>
                               </td>
                            </tr>

                            <tr>
                               <td valign="top" align="left">
                                    <font class="formfieldnamefont">Street Address</font>
                               </td>
                               <td valign="top" align="left">
                                    <%=Textbox.getHtml("street", researcherBilling.getStreet(), 255, 30, "", "")%>
                               </td>
                            </tr>


                            <tr>
                               <td valign="top" align="left">
                                    <font class="formfieldnamefont">City, State, Zip</font>
                               </td>
                               <td valign="top" align="left">
                                    <%=Textbox.getHtml("cccity", researcherBilling.getCccity(), 255, 20, "", "")%>
                                    <%=Textbox.getHtml("ccstate", researcherBilling.getCcstate(), 255, 2, "", "")%>
                                    <%=Textbox.getHtml("postalcode", researcherBilling.getPostalcode(), 255, 6, "", "")%>
                               </td>
                            </tr>


                            <tr>
                               <td valign="top" align="left">
                                    <font class="formfieldnamefont">Credit Card Type</font>
                               </td>
                               <td valign="top" align="left">
                                    <%=Dropdown.getHtml("cctype", String.valueOf(researcherBilling.getCctype()), researcherBilling.getCreditcardtypes(), "","")%>
                               </td>
                            </tr>

                            <tr>
                               <td valign="top" align="left">
                                    <font class="formfieldnamefont">Credit Card Number</font>
                               </td>
                               <td valign="top" align="left">
                                    <%=Textbox.getHtml("ccnum", researcherBilling.getCcnum(), 255, 18, "", "")%>
                               </td>
                            </tr>


                            <tr>
                               <td valign="top" align="left">
                                    <font class="formfieldnamefont">Expiration Date</font>
                               </td>
                               <td valign="top" align="left">
                                    <%=Dropdown.getHtml("ccexpmo", String.valueOf(researcherBilling.getCcexpmo()), researcherBilling.getMonthsForCreditcard(), "","")%>
                                    /
                                    <%=Dropdown.getHtml("ccexpyear", String.valueOf(researcherBilling.getCcexpyear()), researcherBilling.getYearsForCreditcard(), "","")%>
                               </td>
                            </tr>



                            <tr>
                               <td valign="top" align="left">
                                    <font class="formfieldnamefont">CVV2</font>
                                    <br/>
                                    <font class="tinyfont">(three digit number on back of card)</font>
                               </td>
                               <td valign="top" align="left">
                                    <%=Textbox.getHtml("cvv2", researcherBilling.getCvv2(), 255, 3, "", "")%>
                               </td>
                            </tr>

                            </table>


                   </td>
               </tr>
           </table>

            <table cellpadding="0" cellspacing="0" border="0">
                <td valign="top">
                    <input type="submit" class="formsubmitbutton" value="Save Billing Details">
                </td>
                <td valign="top">
                </td>
                <td valign="top">
                </td>
            </table>


    </form>

<%@ include file="/template/footer.jsp" %>


