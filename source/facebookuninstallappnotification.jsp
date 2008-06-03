<%@ page import="com.mythredz.util.Num" %>
<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.criterion.Restrictions" %>
<%@ page import="com.mythredz.dao.hibernate.HibernateUtil" %>
<%@ page import="com.mythredz.dao.User" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.mythredz.xmpp.SendXMPPMessage" %>
<%
    Logger logger = Logger.getLogger(this.getClass().getName());
    logger.debug("a facebook app was uninstalled");
    logger.debug("fb_sig_user="+request.getParameter("fb_sig_user"));
    if (request.getParameter("fb_sig_user")!=null && Num.isinteger(request.getParameter("fb_sig_user"))) {
        logger.debug("fb_sig_user is an integer");
        //@todo somehow validate that this is a good request from the actual servers... don't want script kiddies uninstalling (not that it does any more than setting a flag)
        int facebookuserid = Integer.parseInt(request.getParameter("fb_sig_user"));
        List users = HibernateUtil.getSession().createCriteria(User.class)
                .add(Restrictions.eq("facebookuserid", Integer.valueOf(facebookuserid)))
                .setCacheable(true)
                .list();
        for (Iterator iterator = users.iterator(); iterator.hasNext();) {
            User user = (User)iterator.next();
            user.setIsfacebookappremoved(true);
            user.setEmail("");
            user.setFacebookappremoveddate(new Date());
            try {user.save();} catch (Exception ex) {logger.error("",ex);}
            SendXMPPMessage xmpp = new SendXMPPMessage(SendXMPPMessage.GROUP_CUSTOMERSUPPORT, "Uninstalled Facebook App by " + user.getFirstname() + " " + user.getLastname());
            xmpp.send();
            logger.debug("user noted as app removed userid="+user.getUserid());
        }
    }
%>