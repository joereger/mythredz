package com.mythredz.embed;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Iterator;

import com.mythredz.dao.User;
import com.mythredz.dao.Thred;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.util.Num;
import com.mythredz.util.Str;
import com.mythredz.cache.providers.CacheFactory;
import com.mythredz.systemprops.SystemProperty;
import com.mythredz.htmlui.Pagez;
import com.mythredz.htmlui.Textbox;
import com.mythredz.htmlui.TextboxSecret;
import com.mythredz.htmlui.CheckboxBoolean;

/**
 * User: joereger
 * Date: Jun 4, 2008
 * Time: 9:04:04 AM
 */
public class EmbedJavascriptVertical extends HttpServlet {



    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("Looking for javascript embed via servlet");
        logger.debug("request.getParameter(\"u\")="+request.getParameter("u"));
        logger.debug("request.getParameter(\"p\")="+request.getParameter("p"));
        logger.debug("request.getParameter(\"h\")="+request.getParameter("h"));

        PrintWriter out = response.getWriter();

        //@todo optimize by not instanciating User object, just using int userid
        User user = null;
        if (request.getParameter("u")!=null && Num.isinteger(request.getParameter("u"))){
            user = User.get(Integer.parseInt(request.getParameter("u")));
        }


        boolean cache = true;
        if (request.getParameter("c")!=null && Num.isinteger(request.getParameter("c"))){
            if (request.getParameter("c").equals("0")){
                cache = false;
            }
        }

        boolean makeHttpsIfSSLIsOn = false;
        if (request.getParameter("h")!=null && Num.isinteger(request.getParameter("h"))){
            if (request.getParameter("h").equals("1")){
                makeHttpsIfSSLIsOn = true;
            }
        }


        StringBuffer output = new StringBuffer();
        //Get dynamic portion of output... the stuff that's cached
        String dynamicPortionOfOutput = "";
        if (user!=null && user.getUserid()>0){
            String nameInCache = "embedjavascriptverticalservlet-u"+user.getUserid()+"-makeHttpsIfSSLIsOn"+makeHttpsIfSSLIsOn;
            String cacheGroup = "embedjavascriptcache"+"/";
            Object fromCache = CacheFactory.getCacheProvider().get(nameInCache, cacheGroup);
            if (fromCache!=null && cache){
                logger.debug("returning string from cache");
                dynamicPortionOfOutput = (String)fromCache;
            } else {
                logger.debug("rebuilding string and putting it into cache");
                try{
                    dynamicPortionOfOutput = ThredzAsHtml.getVertical(user, makeHttpsIfSSLIsOn);
                    //Put bytes into cache
                    CacheFactory.getCacheProvider().put(nameInCache, cacheGroup, dynamicPortionOfOutput);
                } catch (Exception ex){
                    logger.error("",ex);
                }
            }
        } else {
            dynamicPortionOfOutput = "Sorry.  Not found.  userud="+request.getParameter("u");
            dynamicPortionOfOutput = "document.write(\""+dynamicPortionOfOutput+"\");"+"\n";
        }
        //Now add toolbar
        output.append(getToolbar(user.getUserid()));
        //Append dynamic
        output.append(dynamicPortionOfOutput);

        //Clean up and wrap for javascript
        String finalOutput = output.toString();
        finalOutput = Str.cleanForjavascriptAndEscapeDoubleQuote(finalOutput);
        finalOutput = finalOutput.replaceAll("\\n", "\"+\\\n\"");
        finalOutput = finalOutput.replaceAll("\\r", "\"+\\\n\"");
        finalOutput = "document.write(\""+finalOutput+"\");"+"\n";
        //Output to client
        out.print(finalOutput);
    }

    private String getToolbar(int userid){
        StringBuffer out = new StringBuffer();

        out.append("<style>\n"+
                ".toolbarnormalvert{\n" +
                "   background : #ffffff; padding: 0px; width: 100; float: right; height: 15px; overflow : hidden; text-align: left; z-index: 0;\n" +
                "}\n"+
                ".toolbarhotvert{\n" +
                "   background : #e6e6e6; padding: 0px; width: 200; height: 450px; overflow : auto; text-align: left; z-index: 99; background-image: url('http://"+ SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/images/embed-toolbar-bg.gif')\n" +
                "}\n"+
                ".formfieldnamefont {\n" +
                "    font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "    font-weight: bolder;\n" +
                "    font-size: 12px;\n" +
                "    color: #333333;\n" +
                "}"+
                ".smallfont {\n" +
                "    font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "    font-size: 10px;\n" +
                "}"+
                ".formsubmitbutton, submit {\n" +
                "   color:#333;\n" +
                "   font-family:'trebuchet ms',helvetica,sans-serif;\n" +
                "   font-weight:bold;\n" +
                "   font-size: 11px;\n" +
                "   background-color:#cccccc;\n" +
                "   border:2px solid;\n" +
                "   border-top-color:#behb00;\n" +
                "   border-left-color:#behb00;\n" +
                "   border-right-color:#behb00;\n" +
                "   border-bottom-color:#behb00;\n" +
                "   margin: 0px;\n" +
                "   cursor: pointer;\n" +
                "   cursor: hand;\n" +
                "}"+
                "</style>");


        out.append("\n<div class=\"toolbarnormalvert\"  onmouseover=\"this.className='toolbarhotvert';\" onmouseout=\"this.className='toolbarnormalvert';\">"+"\n");
        out.append("<div style=\"text-align: right;\"><font class=\"tinyfont\" style=\"color: #cccccc;\">Publish to myThredz</font></div>");
        out.append("<br/><br/>");
        if (Pagez.getUserSession().getIsloggedin() && Pagez.getUserSession().getUser()!=null && Pagez.getUserSession().getUser().getUserid()==userid){
            out.append(getThredzInputForm());
        } else {
            out.append("<form action=\"http://"+ SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/login.jsp\" method=\"post\">\n" +
                    "        <input type=\"hidden\" name=\"dpage\" value=\"/login.jsp\">\n" +
                    "        <input type=\"hidden\" name=\"action\" value=\"login\">\n" +
                    "        <input type=\"hidden\" name=\"redirtocaller\" value=\"1\">\n" +
                    "\n" +
                    "            <center>\n"+
                    "            <table cellpadding=\"1\" cellspacing=\"5\" border=\"0\">\n" +
                    "\n" +
                    "                <tr>\n" +
                    "                    <td valign=\"top\" colspan=\"2\" width=\"200\">\n" +
                    "                        <font class=\"formfieldnamefont\" style=\"color: #999999; font-size: 16px;\">Log In to myThredz.com</font>\n" +
                    "                    </td>\n" +
                    "                </tr>\n" +
                    "\n" +
                    "                <tr>\n" +
                    "                    <td valign=\"top\">\n" +
                    "                        <font class=\"formfieldnamefont\">Email</font>\n" +
                    "                    </td>\n" +
                    "                    <td valign=\"top\">\n" +
                    "                        "+ Textbox.getHtml("email", "", 255, 10, "", "")+"\n" +
                    "                    </td>\n" +
                    "                </tr>\n" +
                    "\n" +
                    "                <tr>\n" +
                    "                    <td valign=\"top\">\n" +
                    "                        <font class=\"formfieldnamefont\">Password</font>\n" +
                    "                    </td>\n" +
                    "                    <td valign=\"top\">\n" +
                    "                        "+ TextboxSecret.getHtml("password", "", 255, 10, "", "")+"\n" +
                    "                        <br/>\n" +
                    "                        <a href=\"http://"+SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/lostpassword.jsp\"><font class=\"tinyfont\" style=\"color: #000000;\">Lost your password?</font></a>\n" +
                    "                    </td>\n" +
                    "                </tr>\n" +
                    "\n" +
                    "                <tr>\n" +
                    "                    <td valign=\"top\" colspan=\"2\">\n" +
                    "                        "+ CheckboxBoolean.getHtml("keepmeloggedin", true, "", "")+"\n" +
                    "                        <font class=\"formfieldnamefont\">Stay Logged In?</font>\n" +
                    "                    </td>\n" +
                    "                </tr>\n" +
                    "\n" +
                    "                <tr>\n" +
                    "                    <td valign=\"top\">\n" +
                    "                    </td>\n" +
                    "                    <td valign=\"top\">\n" +
                    "                        <input type=\"submit\" class=\"formsubmitbutton\" value=\"Log In\">\n" +
                    "                    </td>\n" +
                    "                </tr>\n" +"\n" +
                    "                <tr>\n" +
                    "                    <td valign=\"top\" colspan=\"2\">\n" +
                    "                        <font class=\"formfieldnamefont\" style=\"color: #999999; font-size: 16px;\">Don't have a myThredz.com Account?</font>\n" +
                    "                        <br/><font class=\"smallfont\">myThredz.com is the microblogging platform that helps you organize your thoughts, share them on your blog and update them from within your blog.</font><br/>\n" +
                    "                        <a href=\"http://"+ SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/registration.jsp\"><font class=\"smallfont\"><b>Get Your Free Account Now!</b></font></a><br/>\n" +
                    "                        <a href=\"http://"+ SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/index.jsp\"><font class=\"smallfont\"><b>Or, Learn More!</b></font></a>\n" +
                    "                    </td>\n" +
                    "                </tr>\n" +
                    "\n" +
                    "            </table>\n" +
                    "            </center>\n"+
                    "\n" +
                    "    </form>");
        }

        out.append("</div>");
        out.append("<div style=\"clear:both;\"></div>");


        return out.toString();
    }

    private String getThredzInputForm(){

        StringBuffer out = new StringBuffer();
        out.append("<form id=\"myFormVertical\" action=\"http://"+SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/account/index-widget.jsp\" method=\"post\">\n" +
                "        <input type=\"hidden\" name=\"dpage\" value=\"http://"+SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/account/index-widget.jsp\">\n" +
                "        <input type=\"hidden\" name=\"action\" value=\"save\">\n" +
                "    <center><table cellpadding=\"0\" cellspacing=\"5\" border=\"0\" width=\"100%\">\n");


                        List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
                                .add(Restrictions.eq("userid", Pagez.getUserSession().getUser().getUserid()))
                                .setCacheable(true)
                                .list();
                        for (Iterator<Thred> iterator=threds.iterator(); iterator.hasNext();) {
                            Thred thred=iterator.next();
                            double widthDbl=100 / threds.size();
                            Double widthBigDbl=new Double(widthDbl);
                            int width=widthBigDbl.intValue();

          out.append("            <tr><td valign=\"top\">\n" +
                "                <font class=\"normalfont\" style=\"font-weight: bold; background: #ffffff;\">"+thred.getName()+"</font><br/>\n" +
                "                <textarea name=\"threadid-"+thred.getThredid()+"\" rows=\"2\" cols=\"5\" style=\"width: 100%;\"></textarea>\n" +
                "            </td></tr>\n");

                        }




                out.append("    <tr>\n" +
                "    <td valign=\"top\"><center><input type=\"submit\" class=\"formsubmitbutton\" value=\"Publish to myThredz\"><br/><br/></center></td>\n" +
                "    </tr>\n" +
                "    </table></center>");

                out.append("</form>");

                return out.toString();
    }







}
