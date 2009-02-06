package com.mythredz.helpers;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

import com.mythredz.util.Num;

/**
 * User: joereger
 * Date: Feb 6, 2009
 * Time: 9:51:33 AM
 */
public class ThredRssServlet extends HttpServlet {

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("ThredUrlServlet called to service.");
        int twitaskid = 0;
        logger.debug("request.getRequestURI()="+request.getRequestURI());
        String[] split = request.getRequestURI().split("\\/");
        logger.debug("split.length="+split.length);
        if (split.length>=3){
            if (Num.isinteger(split[2])){
                twitaskid=Integer.parseInt(split[2]);
            }
        }
        logger.debug("redirecting to twitaskid="+twitaskid);
        //Pagez.sendRedirect("/thredrss.xml?thredid="+twitaskid);
        getServletConfig().getServletContext().getRequestDispatcher("/thredrss.xml?thredid="+twitaskid).forward(request,response);
        //request.getRequestDispatcher("/twitask.jsp?twitaskid="+twitaskid).forward(request, response);
        //return;
    }



}
