package com.mythredz.ui;

import com.mythredz.systemprops.BaseUrl;
import com.mythredz.util.Str;

import java.net.URLEncoder;

/**
 * User: Joe Reger Jr
 * Date: Apr 4, 2007
 * Time: 1:16:17 PM
 */
public class SocialBookmarkLinks {



    public static String getSocialBookmarkLinks(String url, String title){
        try{

            StringBuffer out = new StringBuffer();
            out.append("<table cellpadding='0' cellspacing='0' border='0'>");
            out.append("<tr>");
            out.append("<td nowrap>");
            out.append("Add to: ");
            out.append("</td>");
            out.append("<td>");
            out.append("<a href=\"http://digg.com/submit?phase=2&url="+url+"\" target=\"_blank\">digg</a>");
            out.append(" | ");
            out.append("<a href=\"http://reddit.com/submit?url="+url+"&title="+title+"\" target=\"_blank\">reddit</a>");
            out.append(" | ");
            out.append("<a href=\"http://del.icio.us/post?url="+url+";title="+title+"\" target=\"_blank\">del.icio.us</a>");
            out.append(" | ");
            out.append("<a href=\"http://myweb2.search.yahoo.com/myresults/bookmarklet?t="+title+"&u="+url+"\" target=\"_blank\">yahoo!</a>");
            out.append("</tr>");
            out.append("<tr>");
            out.append("<td>");
            out.append("");
            out.append("</td>");
            out.append("<td>");
            out.append("<a href=\"http://www.blinklist.com/index.php?Action=Blink/addblink.php&Url="+url+"&Title="+title+"\" target=\"_blank\">blinklist</a>");
            out.append(" | ");
            out.append("<a href=\"http://www.spurl.net/spurl.php?url="+url+"&title="+title+"\" target=\"_blank\">spurl</a>");
            out.append(" | ");
            out.append("<a href=\"http://www.furl.net/storeIt.jsp?t="+title+"&u="+url+"\" target=\"_blank\">furl</a>");
            out.append(" | ");
            out.append("<a href=\"http://technorati.com/faves?add="+url+"\" target=\"_blank\">technorati</a>");
            out.append("</td>");
            out.append("</tr>");
            out.append("</table>");

            return out.toString();
        } catch (Exception ex){
            return "";
        }
    }


}
