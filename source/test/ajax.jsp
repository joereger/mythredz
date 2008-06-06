<%@ page import="java.util.List" %>
<%@ page import="com.mythredz.dao.hibernate.HibernateUtil" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.mythredz.test.HibernateStaticMemoryClustering" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.util.Time" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.mythredz.htmlui.UserSession" %>
<%@ page import="com.mythredz.htmlui.Authorization" %>
<%@ page import="com.mythredz.util.Str" %>
<%@ page import="com.mythredz.htmlui.Pagez" %>
<%
    //Hide from snooping eyes... only sysadmins can play
    UserSession userSession=(UserSession) session.getAttribute("userSession");
    userSession=Pagez.getUserSession();
    if (userSession == null || !userSession.getIsloggedin() || !Authorization.isUserSysadmin(userSession.getUser())) {
        response.sendRedirect("/");
        return;
    }
%>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
%>

<style type="text/css">



#form_box {
	float: left;
	width: 290px;
	background: #f8f8f8;
	border: 1px solid #d6d6d6;
	border-left-color: #e4e4e4;
	border-top-color: #e4e4e4;
	font-size: 11px;
	font-weight: bold;
	padding: 0.5em;
	margin-top: 10px;
	margin-bottom: 2px;
}

#form_box div {
	height: 25px;
	padding: 0.2em 0.5em;
}

#form_box div.hr {
	border-bottom: 2px solid #e2e2e1;
	height: 0px;
	margin-top: 0pt;
	margin-bottom: 7px;
}

#form_box p {
	float: left;
	margin: 4px 0pt;
	width: 120px;
}


#log {
	float: left;
	padding: 0.5em;
	margin-left: 10px;
	width: 290px;
	border: 1px solid #d6d6d6;
	border-left-color: #e4e4e4;
	border-top-color: #e4e4e4;
	margin-top: 10px;
}

#log_res {
	overflow: auto;
}

#log_res.ajax-loading {
	padding: 20px 0;
	background: url(http://demos.mootools.net/demos/Group/spinner.gif) no-repeat center;
}



</style>

<script type="text/javascript" src="/js/mootools/mootools-release-1.11.js"></script>
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
    });
});
</script>



<form id="myForm" action="/test/ajax-serverside.jsp" method="get">
	<div id="form_box">
		<div>
			<p>First Name:</p>
			<input type="text" name="first_name" value="John" />
		</div>
		<div>
			<p>Last Name:</p>
			<input type="text" name="last_name" value="Q" />
		</div>
		<div>
			<p>E-Mail:</p>
			<input type="text" name="e_mail" value="john.q@mootools.net" />
		</div>
		<div>
			<p>MooTooler:</p>
			 <input type="checkbox" name="mootooler" value="yes" checked="checked" />
		</div>
		<div>
			<p>New to Mootools:</p>
	        <select name="new">
	          <option value="yes" selected="selected">yes</option>
	          <option value="no">no</option>
	        </select>
		</div>
		<div class="hr"><!-- spanner --></div>
		<input type="submit" name="button" id="submitter" />
	<span class="clr"><!-- spanner --></span>
	</div>
</form>
<div id="log">
	<h3>Ajax Response</h3>
	<div id="log_res"><!-- spanner --></div>
</div>
<span class="clr"><!-- spanner --></span>


