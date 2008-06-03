package com.mythredz.money;

import com.mythredz.dao.User;
import com.mythredz.dao.Balance;

import com.mythredz.util.Str;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * User: Joe Reger Jr
 * Date: Oct 11, 2006
 * Time: 1:41:48 PM
 */
public class MoveMoneyInAccountBalance {



    public static void pay(User user, double amt, String desc){
        Logger logger = Logger.getLogger(MoveMoneyInAccountBalance.class);

        double originalAmt = amt;
        String originalDesc = desc;


        Balance balance = new Balance();
        balance.setAmt(amt);
        balance.setDate(new Date());
        balance.setDescription(desc);
        CurrentBalanceCalculator cbc = new CurrentBalanceCalculator(user);
        balance.setCurrentbalance(cbc.getCurrentbalance() + amt);
        balance.setUserid(user.getUserid());
        try{balance.save();}catch (Exception ex){logger.error("",ex);}


    }



    public static void charge(User user, double amt, String desc){
        Logger logger = Logger.getLogger(MoveMoneyInAccountBalance.class);
        Balance balance = new Balance();
        balance.setAmt((-1)*amt);
        balance.setDate(new Date());
        balance.setDescription(desc);
        CurrentBalanceCalculator cbc = new CurrentBalanceCalculator(user);
        balance.setCurrentbalance(cbc.getCurrentbalance() - amt);
        balance.setUserid(user.getUserid());
        try{balance.save();}catch (Exception ex){logger.error("",ex);}
    }



}
