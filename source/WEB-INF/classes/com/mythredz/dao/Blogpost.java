package com.mythredz.dao;

import com.mythredz.dao.hibernate.BasePersistentClass;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.session.AuthControlled;

import java.util.Set;
import java.util.HashSet;
import java.util.Date;

import org.apache.log4j.Logger;



public class Blogpost extends BasePersistentClass implements java.io.Serializable, AuthControlled {


    // Fields
     private int blogpostid;
     private Date date;
     private String author;
     private String title;
     private String body;
     private String categories;
     private Set<Blogpostcomment> blogpostcomments = new HashSet<Blogpostcomment>();




    public static Blogpost get(int id) {
        Logger logger = Logger.getLogger("com.mythredz.dao.Blogpost");
        try {
            logger.debug("Blogpost.get(" + id + ") called.");
            Blogpost obj = (Blogpost) HibernateUtil.getSession().get(Blogpost.class, id);
            if (obj == null) {
                logger.debug("Blogpost.get(" + id + ") returning new instance because hibernate returned null.");
                return new Blogpost();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.mythredz.dao.Blogpost", ex);
            return new Blogpost();
        }
    }

    // Constructors

    /** default constructor */
    public Blogpost() {
    }

    public boolean canRead(User user){
        //@todo Better canRead() implementation
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors


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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Set<Blogpostcomment> getBlogpostcomments() {
        return blogpostcomments;
    }

    public void setBlogpostcomments(Set<Blogpostcomment> blogpostcomments) {
        this.blogpostcomments = blogpostcomments;
    }
}
