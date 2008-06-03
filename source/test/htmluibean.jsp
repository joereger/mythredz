<%@ page import="com.mythredz.htmluibeans.Test" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mythredz.htmluibeans.TestGrid" %>
<%@ page import="com.mythredz.dbgrid.GridCol" %>
<%@ page import="com.mythredz.dbgrid.Grid" %>
<%@ page import="com.mythredz.htmluibeans.TestGridSubobject" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.TreeMap" %>

<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "HtmlUiBean";
String navtab = "home";
String acl = "public";
%>
<%@ include file="/template/auth.jsp" %>
<%
Test test = (Test) Pagez.getBeanMgr().get("Test");
%>
<%@ include file="/template/header.jsp" %>

<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("save")) {
        try {
            test.setTextbox(Textbox.getValueFromRequest("textbox", "Text Box", false, DatatypeString.DATATYPEID));
            test.setDropdown(Dropdown.getValueFromRequest("dropdown", "Dropdown", false));
            test.setTextarea(Textarea.getValueFromRequest("textarea", "Textarea", false));
            test.setDropdownmultiselect(DropdownMultiselect.getValueFromRequest("dropdownmultiselect", "Dropdownmultiselect", false));
            test.setCheckboxesvalues(Checkboxes.getValueFromRequest("checkboxes", "Checkboxes", false));
            test.setBooleantest(CheckboxBoolean.getValueFromRequest("booleantest"));
            test.setCal(DateTime.getValueFromRequest("cal", "Cal", false));
            test.setCal2(DateTime.getValueFromRequest("cal2", "Cal2", false));
            test.save();
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>

<form action="" method="get">
    <input type="hidden" name="action" value="save">
    <br/><%=Textbox.getHtml("textbox", test.getTextbox(), 255, 35, "", "")%>
    <%
        TreeMap<String, String> options=new TreeMap<String, String>();
        options.put("a", "Option a");
        options.put("b", "Option b");
        options.put("c", "Option c");
    %>
    <br/><%=Dropdown.getHtml("dropdown",test.getDropdown(), options, "","")%>
    <br/><%=Textarea.getHtml("textarea", test.getTextarea(), 3, 35, "", "")%>
    <%
        ArrayList<String> checkboxvalues = test.getCheckboxesvalues();
        ArrayList<String> possiblevalues = new ArrayList<String>();
        possiblevalues.add("another");
        possiblevalues.add("bradley");
        possiblevalues.add("charcoal");
    %>
    <br/><%=Checkboxes.getHtml("checkboxes", checkboxvalues, possiblevalues, "", "")%>
    <%
        ArrayList<String> ddmsv = test.getDropdownmultiselect();
        TreeMap<String, String> possibleddvalues = new TreeMap<String, String>();
        possibleddvalues.put("snake", "snake");
        possibleddvalues.put("donkey", "donkey");
        possibleddvalues.put("arse", "arse");
        possibleddvalues.put("sdfsfd", "sdfsfd");
        possibleddvalues.put("arsssde", "arsssde");
        possibleddvalues.put("32d22dwe", "32d22dwe");
        possibleddvalues.put("werwe23", "werwe23");
    %>
    <br/><%=DropdownMultiselect.getHtml("dropdownmultiselect", ddmsv, possibleddvalues, 3, "", "")%>
    <br/><%=CheckboxBoolean.getHtml("booleantest", test.getBooleantest(), "", "")%> Is True?
    <br/><%=DateTime.getHtml("cal", test.getCal(), "", "")%>
    <br/><%=Date.getHtml("cal2", test.getCal2(), "", "")%>
    <br/><input type="submit" class="formsubmitbutton" value="go">
</form>
<br/><br/>
<%

    ArrayList<TestGrid> rows=new ArrayList<TestGrid>();
    rows.add(new TestGrid(1, "Sally", "A person", 32.7688, new TestGridSubobject(Calendar.getInstance(), "34")));
    rows.add(new TestGrid(2, "Edna", "A senior citizen", 567.998, new TestGridSubobject(Calendar.getInstance(), "78")));
    rows.add(new TestGrid(3, "Pupper", "A dog", 32, new TestGridSubobject(Calendar.getInstance(), "3")));
    rows.add(new TestGrid(4, "Sallton", "A lizard", 32, new TestGridSubobject(Calendar.getInstance(), "3")));
    rows.add(new TestGrid(5, "Antonio", "Another dog", 22.67, new TestGridSubobject(Calendar.getInstance(), "3")));
    rows.add(new TestGrid(6, "gfddg", "Another dog", 22.67, new TestGridSubobject(Calendar.getInstance(), "3")));
    rows.add(new TestGrid(7, "Antddgonio", "dfh dog", 22.67, new TestGridSubobject(Calendar.getInstance(), "3")));
    rows.add(new TestGrid(8, "dfdffgd", "dfh dfhgdh", 22.67, new TestGridSubobject(Calendar.getInstance(), "3")));
    rows.add(new TestGrid(9, "Ansdffsdtdfonio", "Another dfhdfgh", 22.67, new TestGridSubobject(Calendar.getInstance(), "3")));
    rows.add(new TestGrid(10, "dfssdf", "Another dfgdfhdfh", 22.67, new TestGridSubobject(Calendar.getInstance(), "3")));
    ArrayList<GridCol> cols=new ArrayList<GridCol>();
    cols.add(new GridCol("Name", "<a href=\"htmluibean.jsp?id=<$id$>\"><$name$></a>"));
    cols.add(new GridCol("Description", "<$description$>"));
    cols.add(new GridCol("Age", "<$testGridSubobject.age$>"));
    cols.add(new GridCol("Date", "<$testGridSubobject.cal$>"));
    cols.add(new GridCol("Money", "<a href=\"htmluibean.jsp?id=<$id$>\"><$money|"+Grid.GRIDCOLRENDERER_DOUBLEASMONEY+"$></a>"));
%>
<%=Grid.render(rows, cols, 5, "/test/htmluibean.jsp", "page")%>


<%@ include file="/template/footer.jsp" %>