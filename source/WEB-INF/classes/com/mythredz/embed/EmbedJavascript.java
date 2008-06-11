package com.mythredz.embed;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

import com.mythredz.dao.User;
import com.mythredz.cache.providers.CacheFactory;
import com.mythredz.util.Str;
import com.mythredz.util.Num;

/**
 * User: joereger
 * Date: Jun 4, 2008
 * Time: 9:04:04 AM
 */
public class EmbedJavascript extends HttpServlet {



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



        String output = "";
        if (user!=null && user.getUserid()>0){
            String nameInCache = "embedjavascriptservlet-u"+user.getUserid()+"-makeHttpsIfSSLIsOn"+makeHttpsIfSSLIsOn;
            String cacheGroup = "embedjavascriptcache"+"/";
            Object fromCache = CacheFactory.getCacheProvider().get(nameInCache, cacheGroup);
            if (fromCache!=null && cache){
                logger.debug("returning string from cache");
                output = (String)fromCache;
            } else {
                logger.debug("rebuilding string and putting it into cache");
                try{
                    String thredzashtml = ThredzAsHtml.get(user, makeHttpsIfSSLIsOn);
                    output = Str.cleanForjavascriptAndEscapeDoubleQuote(thredzashtml);
                    output = output.replaceAll("\\n", "\"+\\\n\"");
                    output = output.replaceAll("\\r", "\"+\\\n\"");
                    output = "document.write(\""+output+"\");"+"\n";
                    //Put bytes into cache
                    CacheFactory.getCacheProvider().put(nameInCache, cacheGroup, output);
                } catch (Exception ex){
                    logger.error("",ex);
                }
            }
        } else {
            output = "Sorry.  Not found.  userud="+request.getParameter("u");
            output = "document.write(\""+output+"\");"+"\n";
        }
        //Output to client
        out.print(output);
    }



   



}
