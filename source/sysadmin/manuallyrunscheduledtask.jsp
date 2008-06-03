<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.mythredz.htmluibeans.SysadminManuallyRunScheduledTask" %>
<%@ page import="com.mythredz.htmlui.*" %>
<%
Logger logger = Logger.getLogger(this.getClass().getName());
String pagetitle = "Manually Run Scheduled Task";
String navtab = "sysadmin";
String acl = "sysadmin";
%>
<%@ include file="/template/auth.jsp" %>
<%
SysadminManuallyRunScheduledTask sysadminManuallyRunScheduledTask = (SysadminManuallyRunScheduledTask)Pagez.getBeanMgr().get("SysadminManuallyRunScheduledTask");
%>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("run")) {
        try {
            if (request.getParameter("task")!=null){
                if (request.getParameter("task").equals("MoveMoneyAround")){
                    sysadminManuallyRunScheduledTask.runMoveMoneyAround();
                } else if (request.getParameter("task").equals("DeleteOldPersistentlogins")){
                    sysadminManuallyRunScheduledTask.runDeleteOldPersistentlogins();
                } else if (request.getParameter("task").equals("SystemStats")){
                    sysadminManuallyRunScheduledTask.runSystemStats();
                } else if (request.getParameter("task").equals("SendMassemails")){
                    sysadminManuallyRunScheduledTask.runSendMassemails();
                } else if (request.getParameter("task").equals("PagePerformanceRecordAndFlush")){
                    sysadminManuallyRunScheduledTask.runPagePerformanceRecordAndFlush();
                } else {
                    throw new ValidationException("task not found.");    
                }
            } else {
                throw new ValidationException("task not defined.");
            }
        } catch (ValidationException vex) {
            Pagez.getUserSession().setMessage(vex.getErrorsAsSingleString());
        }
    }
%>
<%@ include file="/template/header.jsp" %>



<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=CloseSurveysByDate"><font class="mediumfont">CloseSurveysByDate</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=CloseSurveysByNumRespondents"><font class="mediumfont">CloseSurveysByNumRespondents</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=NotifyBloggersOfNewOffers"><font class="mediumfont">NotifyBloggersOfNewOffers</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=PendingToOpenSurveys"><font class="mediumfont">PendingToOpenSurveys</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=QualityAverager"><font class="mediumfont">QualityAverager</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=MoveMoneyAround"><font class="mediumfont">MoveMoneyAround</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=ResearcherRemainingBalanceOperations"><font class="mediumfont">ResearcherRemainingBalanceOperations</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=DeleteOldPersistentlogins"><font class="mediumfont">DeleteOldPersistentlogins</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=SocialInfluenceRatingUpdate"><font class="mediumfont">SocialInfluenceRatingUpdate</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=SystemStats"><font class="mediumfont">SystemStats</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=SendMassemails"><font class="mediumfont">SendMassemails</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=ImpressionActivityObjectQueue"><font class="mediumfont">ImpressionActivityObjectQueue</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=CharityCalculateAmountDonated"><font class="mediumfont">CharityCalculateAmountDonated</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=UpdateResponsePoststatus"><font class="mediumfont">UpdateResponsePoststatus</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=UpdateResponsePoststatusAll"><font class="mediumfont">UpdateResponsePoststatus(All)</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=PayForSurveyResponsesOncePosted"><font class="mediumfont">PayForSurveyResponsesOncePosted</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=ImpressionPayments"><font class="mediumfont">ImpressionPayments</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=SystemStatsFinancial"><font class="mediumfont">SystemStatsFinancial</font></a>
<br/><a href="/sysadmin/manuallyrunscheduledtask.jsp?action=run&task=PagePerformanceRecordAndFlush"><font class="mediumfont">PagePerformanceRecordAndFlush</font></a>



<%@ include file="/template/footer.jsp" %>