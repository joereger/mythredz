package com.mythredz.profile;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

import com.mythredz.dao.User;
import com.mythredz.util.Num;
import com.mythredz.util.Str;
import com.mythredz.cache.providers.CacheFactory;
import com.mythredz.embed.ThredzAsHtml;
import com.mythredz.session.UrlSplitter;
import com.mythredz.htmlui.Pagez;

/**
 * User: joereger
 * Date: Jun 5, 2008
 * Time: 9:31:03 AM
 */
public class ProfileServlet extends HttpServlet {

     public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = Logger.getLogger(this.getClass().getName());
       try {

         String nickname = "";

          UrlSplitter urlSplitter =  new UrlSplitter(request);
          String servletPath = urlSplitter.getServletPath();
          String requestUrl = urlSplitter.getRequestUrl();
          logger.debug("servletPath="+servletPath);

          String[] splitpath = requestUrl.split("/");
          if (splitpath.length>=5){
            nickname = splitpath[4];
          }
          logger.debug("nickname="+nickname);
          logger.debug("ProfileServlet - isloggedin="+ Pagez.getUserSession().getIsloggedin());
          getServletConfig().getServletContext().getRequestDispatcher("/profile.jsp?nickname="+nickname).forward(request,response);

        } catch (ServletException ex) {
          logger.error("", ex);
        } catch (IOException ex) {
          logger.error("", ex);
        } catch (Exception ex){
          logger.error("", ex);
        }
    }



}
