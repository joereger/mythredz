package com.mythredz.timeline;

import com.mythredz.dao.Post;
import com.mythredz.dao.Thred;

import java.util.Calendar;

/**
 * User: joereger
 * Date: Jun 11, 2008
 * Time: 9:13:18 AM
 */
public interface TimelineObject {

    public Post getPost();
    public Thred getThred();
    public int getX1();
    public int getY1();
    public int getX2();
    public int getY2();

}
