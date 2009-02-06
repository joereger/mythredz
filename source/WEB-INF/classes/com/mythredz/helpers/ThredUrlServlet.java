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
public class ThredUrlServlet extends HttpServlet {

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("ThredUrlServlet called to service.");
        int thredid= 0;
        logger.debug("request.getRequestURI()="+request.getRequestURI());
        String[] split = request.getRequestURI().split("\\/");
        logger.debug("split.length="+split.length);
        if (split.length>=3){
            if (Num.isinteger(split[2])){
                thredid=Integer.parseInt(split[2]);
            }
        }
        logger.debug("redirecting to thredid="+ thredid);
        //Pagez.sendRedirect("/thred.jsp?thredid="+thredid);
        getServletConfig().getServletContext().getRequestDispatcher("/thred.jsp?thredid="+ thredid).forward(request,response);
        //return;
    }



}
