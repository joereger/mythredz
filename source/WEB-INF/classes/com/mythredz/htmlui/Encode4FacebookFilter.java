package com.mythredz.htmlui;

import org.apache.log4j.Logger;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * User: Joe Reger Jr
 * Date: Nov 26, 2007
 * Time: 2:14:46 AM
 */
public class Encode4FacebookFilter implements Filter {
    ServletContext sc = null;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        Logger logger = Logger.getLogger(this.getClass().getName());


            // check that it is a HTTP request
            if (req instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) req;
                HttpServletResponse response = (HttpServletResponse) res;
                
                if (Pagez.getUserSession()!=null && Pagez.getUserSession().getIsfacebookui() && request.getRequestURL().indexOf(".jpg")==-1 && request.getRequestURL().indexOf(".css")==-1 && request.getRequestURL().indexOf(".gif")==-1 && request.getRequestURL().indexOf(".png")==-1 && request.getRequestURL().indexOf(".js")==-1 && request.getRequestURL().indexOf(".swf")==-1){    
                    logger.debug("+++ doFilter() begin");



                    // nonce encode the normal output
                    Encode4FacebookResponseWrapper wrappedResponse = new Encode4FacebookResponseWrapper(request, response, sc);

                    // make sure a session exists
                    HttpSession session = request.getSession(true);

             

                    chain.doFilter(request, wrappedResponse);

                    // finish the response
                    wrappedResponse.finishResponse();
                    
                    logger.debug("+++ doFilter() end");
                } else {
                    chain.doFilter(req, res);
                }
            } 


    }

    public void init(FilterConfig filterConfig) {
        // reference the context
        sc = filterConfig.getServletContext();
    }

    public void destroy() {
        // noop
    }
}
