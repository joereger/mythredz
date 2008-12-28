package

com.mythredz.embed;

import com.mythredz.dao.User;
import com.mythredz.dao.Thred;
import com.mythredz.dao.Post;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.util.Time;
import com.mythredz.systemprops.SystemProperty;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;

import java.util.Iterator;
import java.util.List;

/**
 * User: joereger
 * Date: Jun 4, 2008
 * Time: 9:10:41 AM
 */
public class ThredzAsHtml {

    public static int ORIENTATION_VERT = 0;
    public static int ORIENTATION_HORIZ = 1;


    public static String get(User user, boolean makeHttpsIfSSLIsOn, int orientation){
        StringBuffer out = new StringBuffer();
        //CSS for embed
        if (user.getCss()!=null && !user.getCss().equals("")){
            out.append(user.getCss());
        } else {
            out.append(getDefaultCssStyle());
        }
        //Body of embed
        if (orientation==ORIENTATION_HORIZ){
            out.append(getHorizontal(user, makeHttpsIfSSLIsOn));
        } else {
            out.append(getVertical(user, makeHttpsIfSSLIsOn));
        }
        return out.toString();
    }

    public static String getDefaultCssStyle(){
        StringBuffer out = new StringBuffer();
        //Main CSS for embed
        out.append("<style>\n"+
                ".mythredztinyfont {\n" +
                "    font-family: Arial, sans-serif;\n" +
                "    font-size: 9px;\n" +
                "}\n" +
                ".mythredzdatestampfont {\n" +
                "    font-family: Arial, sans-serif;\n" +
                "    font-size: 9px;\n" +
                "    color: #cccccc;\n"+
                "}\n" +
                ".mythredzsmallfont {\n" +
                "    font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "    font-size: 10px;\n" +
                "}\n"+
                ".thredtitleheader {\n" +
                "    width: 100%;\n" +
                "    background: #e6e6e6;\n" +
                "    text-align: center;\n" +
                "    height: 26px;\n"+
                "}\n"+
                ".thredtitlefont {\n" +
                "    font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "    font-size: 12px;\n" +
                "    font-weight: bold;\n"+
                "    color: #666666;\n"+
                "}\n"+
                ".thredztable{\n"+
                "    width: 100%;\n"+
                "}\n"+
                ".thredztablecell{\n"+
                "    border: 0px;\n"+
                "    padding: 0px;\n"+
                "}\n"+
                ".thredbody {\n" +
                "    padding: 0px;\n" +
                "    width: 100%;\n" +
                "    margin: 0px;\n" +
                "    border: 0px solid #ffffff;\n " +
                "    height: 120px;\n " +
                "    overflow: visible;\n " +
                "    text-align: left;\n"+
                "}\n"+
                ".thrednormal{\n" +
                "   padding: 0px;\n width: 100%;\n height: 120px;\n overflow: hidden;\n text-align: left;\n z-index: 1;\n" +
                "}\n"+
                ".thredhot{\n" +
                "   background : #ffffff;\n padding: 0px;\n width: 100%;\n height: 250px;\n overflow:auto;\n text-align: left;\n z-index: 99;\n" +
                "}\n"+
                ".toolbarnormal{\n" +
                "   padding: 0px;\n width: 150px;\n float: right;\n height: 15px;\n overflow: hidden;\n text-align: left;\n z-index: 0;\n" +
                "}\n"+
                ".toolbarhot{\n" +
                "   background : #e6e6e6;\n padding: 0px;\n width: 100%;\n height: 275px;\n overflow: auto;\n text-align: left;\n z-index: 99;\n background-image: url('http://"+SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/images/embed-toolbar-bg.gif')\n" +
                "}\n"+
                ".formfieldnamefont {\n" +
                "    font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "    font-weight: bolder;\n" +
                "    font-size: 12px;\n" +
                "    color: #333333;\n" +
                "}\n"+
                ".smallfont {\n" +
                "    font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "    font-size: 10px;\n" +
                "}\n"+
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
                "}\n"+
                ".thredsinglenormal{}\n"+
                ".thredsinglehot{}\n"+
                ".mythredzdatestampsingle{}\n"+
                ".mythredzdatestampfontsingle{}\n"+
                ".mythredzcontentsingle{}\n"+
                ".mythredzsmallfontsingle{}\n"+
                "</style>");
            return out.toString();
    }

    private static String getHorizontal(User user, boolean makeHttpsIfSSLIsOn){
        Logger logger = Logger.getLogger(ThredzAsHtml.class);
        StringBuffer out = new StringBuffer();
        out.append("\n\n<div class=\"thredbody\">"+"\n");
        List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
            .add(Restrictions.eq("userid", user.getUserid()))
            .setCacheable(true)
            .list();
        out.append("\n<table class=\"thredztable\">");
        out.append("\n<tr>");
        for (Iterator<Thred> iterator=threds.iterator(); iterator.hasNext();) {
            Thred thred=iterator.next();
            double widthDbl=100 / threds.size();
            Double widthBigDbl=new Double(widthDbl);
            int width=widthBigDbl.intValue();
            out.append("\n\n<td class=\"thredztablecell\" valign=\"top\" width=\""+width+"%\">");
            out.append("\n<div class=\"thredtitleheader\">");
            out.append("<font class=\"thredtitlefont\">"+thred.getName()+"</font>");
            out.append("</div>");
            out.append("</td>");
        }
        out.append("\n\n</tr>");
        out.append("\n<tr>");
        for (Iterator<Thred> iterator=threds.iterator(); iterator.hasNext();) {
            Thred thred=iterator.next();
            double widthDbl=100 / threds.size();
            Double widthBigDbl=new Double(widthDbl);
            int width=widthBigDbl.intValue();
            out.append("\n\n<td class=\"thredztablecell\" valign=\"top\">");
            out.append("\n<div class=\"thrednormal\" style=\"\" onmouseover=\"this.className='thredhot';\" onmouseout=\"this.className='thrednormal';\">"+"\n");
            List<Post> posts=HibernateUtil.getSession().createCriteria(Post.class)
                    .add(Restrictions.eq("thredid", thred.getThredid()))
                    .addOrder(Order.desc("date"))
                    .addOrder(Order.desc("postid"))
                    .setMaxResults(25)
                    .setCacheable(true)
                    .list();
            for (Iterator<Post> iterator1=posts.iterator(); iterator1.hasNext();) {
                Post post=iterator1.next();
                out.append("\n<font class=\"mythredzdatestampfont\">"+Time.dateformatcompactwithtime(post.getDate())+"</font>");
                out.append("\n<br/><font class=\"mythredzsmallfont\">"+post.getContents()+"</font><br/><br/>");
            }
            out.append("<div style=\"width: 100%; background: #e6e6e6; text-align: center;\">");
            out.append("<br/><font class=\"thredtitlefont\">");
            out.append("<a href=\"http://"+ SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/user/"+user.getNickname()+"/\">");
            out.append("See All of this Thred");
            out.append("</a>");
            out.append("</font>");
            out.append("<br/>");
            out.append("<font class=\"mythredztinyfont\">Powered by <a href=\"http://"+ SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/registration.jsp\">myThredz</a></font><br/>");
            out.append("<br/><font class=\"mythredztinyfont\" style=\"font-weight: bold;\">Subscribe to this Thred<br/><a href=\"http://"+SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/thredrss.xml?thredid="+thred.getThredid()+"\"><img src=\"http://"+SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/images/rss-20a.gif\" width=\"80\" height=\"15\" alt=\"Subscribe to Thred\" border=0\"\"></a></font>");
            out.append("<br/><br/>");
            out.append("</div>");
            out.append("\n</div>");
            out.append("\n</td>");
        }
        out.append("\n\n</tr>");
        out.append("\n</table>");
        out.append("</div>"+"\n");
        return out.toString();
    }


    private static String getVertical(User user, boolean makeHttpsIfSSLIsOn){
        Logger logger = Logger.getLogger(ThredzAsHtml.class);
        StringBuffer out = new StringBuffer();
        out.append("\n\n<div class=\"thredbody\">"+"\n");
        out.append("\n<table class=\"thredztable\">");
        List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
            .add(Restrictions.eq("userid", user.getUserid()))
            .setCacheable(true)
            .list();
        for (Iterator<Thred> iterator=threds.iterator(); iterator.hasNext();) {
            Thred thred=iterator.next();
            double widthDbl=100 / threds.size();
            Double widthBigDbl=new Double(widthDbl);
            int width=widthBigDbl.intValue();
            width = 100;
            out.append("\n<tr>");
            out.append("\n\n<td class=\"thredztablecell\" valign=\"top\" width=\""+width+"%\">");
            out.append("\n<div class=\"thredtitleheader\"><font class=\"thredtitlefont\">"+thred.getName()+"</font></div>");
            out.append("</td>");
            out.append("</tr>");
            out.append("\n<tr>");
            out.append("\n\n<td class=\"thredztablecell\" valign=\"top\" width=\""+width+"%\">");
            out.append("\n<div class=\"thrednormal\" style=\"\" onmouseover=\"this.className='thredhot';\" onmouseout=\"this.className='thrednormal';\">"+"\n");
            List<Post> posts=HibernateUtil.getSession().createCriteria(Post.class)
                    .add(Restrictions.eq("thredid", thred.getThredid()))
                    .addOrder(Order.desc("date"))
                    .setMaxResults(25)
                    .setCacheable(true)
                    .list();
            for (Iterator<Post> iterator1=posts.iterator(); iterator1.hasNext();) {
                Post post=iterator1.next();
                out.append("\n<font class=\"mythredzdatestampfont\">"+Time.dateformatcompactwithtime(post.getDate())+"</font>");
                out.append("\n<br/><font class=\"mythredzsmallfont\">"+post.getContents()+"</font><br/><br/>");
            }
            out.append("<div style=\"width: 100%; background: #e6e6e6; text-align: center;\">");
            out.append("<br/><font class=\"thredtitlefont\">");
            out.append("<a href=\"http://"+ SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/user/"+user.getNickname()+"/\">");
            out.append("See All of this Thred");
            out.append("</a>");
            out.append("</font>");
            out.append("<br/>");
            out.append("<font class=\"mythredztinyfont\">Powered by <a href=\"http://"+ SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/registration.jsp\">myThredz</a></font><br/>");
            out.append("<br/><font class=\"mythredztinyfont\">Subscribe to this Thred<br/><a href=\"http://"+SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/thredrss.xml?thredid="+thred.getThredid()+"\"><img src=\"http://"+SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/images/rss-20a.gif\" width=\"80\" height=\"15\" alt=\"Subscribe to Thred\" border=0\"\"></a></font>");
            out.append("<br/><br/>");
            out.append("</div>");
            out.append("\n</div>");
            out.append("\n</td>");
            out.append("\n\n</tr>");
        }
        out.append("\n</table>");
        out.append("</div>"+"\n");
        return out.toString();
    }



}
