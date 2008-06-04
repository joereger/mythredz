package com.mythredz.dao;

import com.mythredz.dao.hibernate.BasePersistentClass;
import com.mythredz.dao.hibernate.HibernateUtil;
import com.mythredz.session.AuthControlled;

import java.util.Date;

import org.apache.log4j.Logger;

public class Post extends BasePersistentClass implements java.io.Serializable, AuthControlled {


    // Fields
     private int postid;
     private int thredid;
     private Date date;
     private String contents;


    public static Post get(int id) {
        Logger logger = Logger.getLogger("com.mythredz.dao.Post");
        try {
            logger.debug("Post.get(" + id + ") called.");
            Post obj = (Post) HibernateUtil.getSession().get(Post.class, id);
            if (obj == null) {
                logger.debug("Post.get(" + id + ") returning new instance because hibernate returned null.");
                return new Post();
            }
            return obj;
        } catch (Exception ex) {
            logger.error("com.mythredz.dao.Post", ex);
            return new Post();
        }
    }

    // Constructors

    /** default constructor */
    public Post() {
    }

    public boolean canRead(User user){
        return true;
    }

    public boolean canEdit(User user){
        return canRead(user);
    }




    // Property accessors


    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid=postid;
    }

    public int getThredid() {
        return thredid;
    }

    public void setThredid(int thredid) {
        this.thredid=thredid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date=date;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents=contents;
    }
}
