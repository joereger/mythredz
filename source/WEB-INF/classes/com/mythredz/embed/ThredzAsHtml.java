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

    public static String get(User user, boolean makeHttpsIfSSLIsOn){
        Logger logger = Logger.getLogger(ThredzAsHtml.class);
        StringBuffer out = new StringBuffer();

        int totHeight = 120;
        

        out.append("<style>\n"+
                ".tinyfont {\n" +
                "    font-family: Arial, sans-serif;\n" +
                "    font-size: 9px;\n" +
                "}\n" +
                "\n" +
                ".smallfont {\n" +
                "    font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "    font-size: 10px;\n" +
                "}\n"+
                ".normalfont {\n" +
                "    font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "    font-size: 12px;\n" +
                "}\n"+
                ".thrednormal{\n" +
                "   background : #ffffff; padding: 0px; width: 100%; height: "+(totHeight-0)+"px; overflow : hidden; text-align: left; z-index: 1;\n" +
                "}\n"+
                ".thredhot{\n" +
                "   background : #ffffff; padding: 0px; width: 100%; height: 250px; overflow : auto; text-align: left; z-index: 99;\n" +
                "}\n"+
                "</style>");

        out.append("\n\n<div style=\"background : #ffffff; padding: 0px; width: margin: 0px; 95%; border: 0px solid #ffffff; height: "+totHeight+"px; overflow: visible; text-align: left;\">"+"\n");




        List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
            .add(Restrictions.eq("userid", user.getUserid()))
            .setCacheable(true)
            .list();




        out.append("\n<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" width=\"100%\">");

        out.append("\n<tr>");

        for (Iterator<Thred> iterator=threds.iterator(); iterator.hasNext();) {
            Thred thred=iterator.next();
            double widthDbl=100 / threds.size();
            Double widthBigDbl=new Double(widthDbl);
            int width=widthBigDbl.intValue();
            out.append("\n\n<td valign=\"top\" width=\""+width+"%\">");
            out.append("\n<div style=\"width: 100%; background: #e6e6e6; text-align: center; height: 26px;\">");
            out.append("<font class=\"normalfont\" style=\"font-weight: bold; color: #666666;\">"+thred.getName()+"</font>");
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
            out.append("\n\n<td valign=\"top\">");
            out.append("\n<div class=\"thrednormal\" style=\"\" onmouseover=\"this.className='thredhot';\" onmouseout=\"this.className='thrednormal';\">"+"\n");
            //out.append("\n<br/>");
            List<Post> posts=HibernateUtil.getSession().createCriteria(Post.class)
                    .add(Restrictions.eq("thredid", thred.getThredid()))
                    .addOrder(Order.desc("date"))
                    .addOrder(Order.desc("postid"))
                    .setMaxResults(25)
                    .setCacheable(true)
                    .list();
            for (Iterator<Post> iterator1=posts.iterator(); iterator1.hasNext();) {
                Post post=iterator1.next();
                out.append("\n<font class=\"tinyfont\" style=\"color: #cccccc;\">"+Time.dateformatcompactwithtime(post.getDate())+"</font>");
                out.append("\n<br/><font class=\"smallfont\">"+post.getContents()+"</font><br/><br/>");
            }

            out.append("<div style=\"width: 100%; background: #e6e6e6; text-align: center;\">");
            out.append("<br/><font class=\"normalfont\" style=\"font-weight: bold; color: #666666;\">");
            out.append("<a href=\"http://"+ SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/user/"+user.getNickname()+"/\">");
            out.append("See All of this Thred");
            out.append("</a>");
            out.append("</font>");
            out.append("<br/>");
            out.append("<font class=\"tinyfont\">Powered by <a href=\"http://"+ SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/registration.jsp\">myThredz</a></font><br/>");
            out.append("<br/><font class=\"tinyfont\" style=\"font-weight: bold;\">Subscribe to this Thred<br/><a href=\"http://"+SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/thredrss.xml?thredid="+thred.getThredid()+"\"><img src=\"http://"+SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/images/rss-20a.gif\" width=\"80\" height=\"15\" alt=\"Subscribe to Thred\" border=0\"\"></a></font>");
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


    public static String getVertical(User user, boolean makeHttpsIfSSLIsOn){
        Logger logger = Logger.getLogger(ThredzAsHtml.class);
        StringBuffer out = new StringBuffer();




        out.append("<style>\n"+
                ".tinyfont {\n" +
                "    font-family: Arial, sans-serif;\n" +
                "    font-size: 9px;\n" +
                "}\n" +
                "\n" +
                ".smallfont {\n" +
                "    font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "    font-size: 10px;\n" +
                "}\n"+
                ".normalfont {\n" +
                "    font-family: Verdana, Arial, Helvetica, sans-serif;\n" +
                "    font-size: 12px;\n" +
                "}\n"+
                ".thrednormal{\n" +
                "   background : #ffffff; padding: 0px; width: 100%; height: 100px; overflow : hidden; text-align: left; z-index: 1;\n" +
                "}\n"+
                ".thredhot{\n" +
                "   background : #ffffff; padding: 0px; width: 100%; height: 250px; overflow : auto; text-align: left; z-index: 99;\n" +
                "}\n"+
                "</style>");

        out.append("\n\n<div style=\"background : #ffffff; padding: 0px; margin: 0px; width: 95%; border: 0px solid #ffffff; text-align: left;\">"+"\n");


        out.append("\n<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" width=\"100%\">");
        //out.append("<tr>");
            List<Thred> threds=HibernateUtil.getSession().createCriteria(Thred.class)
                .add(Restrictions.eq("userid", user.getUserid()))
                .setCacheable(true)
                .list();



        for (Iterator<Thred> iterator=threds.iterator(); iterator.hasNext();) {
            Thred thred=iterator.next();
            double widthDbl=100 / threds.size();
            Double widthBigDbl=new Double(widthDbl);
            int width=widthBigDbl.intValue();
            out.append("\n<tr>");
            out.append("\n\n<td valign=\"top\" width=\""+width+"%\">");
            out.append("\n<div style=\"width: 100%; background: #e6e6e6; text-align: center; height: 26px;\"><font class=\"normalfont\" style=\"font-weight: bold; color: #666666;\">"+thred.getName()+"</font></div>");
            out.append("</td>");
            out.append("</tr>");
            out.append("\n<tr>");
            out.append("\n\n<td valign=\"top\" width=\""+width+"%\">");
            out.append("\n<div class=\"thrednormal\" style=\"\" onmouseover=\"this.className='thredhot';\" onmouseout=\"this.className='thrednormal';\">"+"\n");
            List<Post> posts=HibernateUtil.getSession().createCriteria(Post.class)
                    .add(Restrictions.eq("thredid", thred.getThredid()))
                    .addOrder(Order.desc("date"))
                    .setMaxResults(25)
                    .setCacheable(true)
                    .list();
            for (Iterator<Post> iterator1=posts.iterator(); iterator1.hasNext();) {
                Post post=iterator1.next();
                out.append("\n<font class=\"tinyfont\" style=\"color: #cccccc;\">"+Time.dateformatcompactwithtime(post.getDate())+"</font>");
                out.append("\n<br/><font class=\"smallfont\">"+post.getContents()+"</font><br/><br/>");
            }

            out.append("<div style=\"width: 100%; background: #e6e6e6; text-align: center;\">");
            out.append("<br/><font class=\"normalfont\" style=\"font-weight: bold; color: #666666;\">");
            out.append("<a href=\"http://"+ SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/user/"+user.getNickname()+"/\">");
            out.append("See All of this Thred");
            out.append("</a>");
            out.append("</font>");
            out.append("<br/>");
            out.append("<font class=\"tinyfont\">Powered by <a href=\"http://"+ SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/registration.jsp\">myThredz</a></font><br/>");
            out.append("<br/><font class=\"tinyfont\" style=\"font-weight: bold;\">Subscribe to this Thred<br/><a href=\"http://"+SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/thredrss.xml?thredid="+thred.getThredid()+"\"><img src=\"http://"+SystemProperty.getProp(SystemProperty.PROP_BASEURL)+"/images/rss-20a.gif\" width=\"80\" height=\"15\" alt=\"Subscribe to Thred\" border=0\"\"></a></font>");
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
