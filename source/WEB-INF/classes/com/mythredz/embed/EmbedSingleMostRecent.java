package com.mythredz.embed;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;

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
import com.mythredz.dao.Post;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.util.Num;
import com.mythredz.util.Str;
import com.mythredz.util.Time;
import com.mythredz.cache.providers.CacheFactory;
import com.mythredz.htmlui.Pagez;
import com.mythredz.htmlui.Textbox;
import com.mythredz.htmlui.TextboxSecret;
import com.mythredz.htmlui.CheckboxBoolean;
import com.mythredz.systemprops.SystemProperty;

/**
 * User: joereger
 * Date: Jun 4, 2008
 * Time: 9:04:04 AM
 */
public class EmbedSingleMostRecent extends HttpServlet {



    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("Looking for javascript embed via servlet");
        logger.debug("request.getParameter(\"u\")="+request.getParameter("u"));
        logger.debug("request.getParameter(\"p\")="+request.getParameter("p"));
        logger.debug("request.getParameter(\"h\")="+request.getParameter("h"));
        logger.debug("request.getParameter(\"t\")="+request.getParameter("t"));

        PrintWriter out = response.getWriter();

        //@todo optimize by not instanciating User object, just using int userid
        User user = null;
        if (request.getParameter("u")!=null && Num.isinteger(request.getParameter("u"))){
            user = User.get(Integer.parseInt(request.getParameter("u")));
        }

        //@todo optimize by not instanciating User object, just using int thredid
        Thred thred = null;
        if (request.getParameter("t")!=null && Num.isinteger(request.getParameter("t"))){
            thred = Thred.get(Integer.parseInt(request.getParameter("t")));
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
        if (user!=null && user.getUserid()>0 && thred!=null && thred.getThredid()>0){
            String nameInCache = "embedsinglemostrecent-u"+user.getUserid()+"-makeHttpsIfSSLIsOn"+makeHttpsIfSSLIsOn+"-thredid"+thred.getThredid();
            String cacheGroup = "usercache"+user.getUserid()+"/";
            Object fromCache = CacheFactory.getCacheProvider().get(nameInCache, cacheGroup);
            if (fromCache!=null && cache){
                logger.debug("returning string from cache");
                dynamicPortionOfOutput = (String)fromCache;
            } else {
                logger.debug("rebuilding string and putting it into cache");
                try{
                    dynamicPortionOfOutput = getMostRecent(user, thred, makeHttpsIfSSLIsOn);
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

    private String getMostRecent(User user, Thred thred, boolean makeHttpsIfSSLIsOn){
        StringBuffer out = new StringBuffer();

        out.append("\n<div class=\"thredsinglenormal\" id=\"thredsingle"+thred.getThredid()+"\" style=\"\" onmouseover=\"this.className='thredsinglehot';\" onmouseout=\"this.className='thredsinglenormal';\">");
        List<Post> posts=HibernateUtil.getSession().createCriteria(Post.class)
                .add(Restrictions.eq("thredid", thred.getThredid()))
                .addOrder(Order.desc("date"))
                .addOrder(Order.desc("postid"))
                .setMaxResults(1)
                .setCacheable(true)
                .list();
        for (Iterator<Post> iterator1=posts.iterator(); iterator1.hasNext();) {
            Post post=iterator1.next();
            out.append("\n<div class=\"mythredzdatestampsingle\"><font class=\"mythredzdatestampfontsingle\">"+ Time.dateformatcompactwithtime(post.getDate())+" via <a href=\"http://"+SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/user/"+user.getNickname()+"/\">MyThredz</a></font></div>");
            out.append("\n<div class=\"mythredzcontentsingle\"><font class=\"mythredzsmallfontsingle\">"+post.getContents()+"</font></div>");
        }
        out.append("\n</div>");

        return out.toString();
    }









}
