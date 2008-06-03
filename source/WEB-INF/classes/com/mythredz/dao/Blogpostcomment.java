package com.mythredz.dao;

import com.mythredz.dao.hibernate.BasePersistentClass;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.session.AuthControlled;

import java.util.Date;

import org.apache.log4j.Logger;



public class Blogpostcomment extends BasePersistentClass implements java.io.Serializable, AuthControlled {


    // Fields
     private int blogpostcommentid;
     private int blogpostid;
     private Date date;
     private String name;
     private String url;
     private String comment;
     private boolean isapproved;


    public static Blogpostcomment get(int id) {
        Logger logger = Logger.getLogger("com.mythredz.dao.Blogpostcomment");
        try {
            logger.debug("Blogpostcomment.get(" + id + ") called.");
            Blogpostcomment obj = (Blogpostcomment) HibernateUtil.getSession().get(Blogpostcomment.class, id);
            if (obj == null) {
                logger.debug("Blogpostcomment.get(" + id + ") returning new instance because hibernate returned null.");
                return new Blogpostcomment();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.mythredz.dao.Blogpostcomment", ex);
            return new Blogpostcomment();
        }
    }

    // Constructors

    /** default constructor */
    public Blogpostcomment() {
    }

    public boolean canRead(User user){
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors


    public int getBlogpostcommentid() {
        return blogpostcommentid;
    }

    public void setBlogpostcommentid(int blogpostcommentid) {
        this.blogpostcommentid = blogpostcommentid;
    }

    public int getBlogpostid() {
        return blogpostid;
    }

    public void setBlogpostid(int blogpostid) {
        this.blogpostid = blogpostid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean getIsapproved() {
        return isapproved;
    }

    public void setIsapproved(boolean isapproved) {
        this.isapproved = isapproved;
    }
}
