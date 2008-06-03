package com.mythredz.test;

import org.junit.Test;
import org.junit.Assert;
import com.mythredz.dao.User;

/**
 * User: Joe Reger Jr
 * Date: Sep 18, 2006
 * Time: 9:40:05 AM
 */
public class TestTest {

    @Test public void test(){
        User user = User.get(1);
        Assert.assertTrue(user.getFirstname().equals("Bobjhfkjhg"));
    }

    @Test public void testNum1(){
        Assert.assertTrue(1==1);
    }


    @Test public void testNum(){
        Assert.assertTrue(1==2);
    }


}
