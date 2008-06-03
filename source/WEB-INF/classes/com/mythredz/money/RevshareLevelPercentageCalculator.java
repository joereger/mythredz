package com.mythredz.money;

import org.apache.log4j.Logger;

/**
 * User: Joe Reger Jr
 * Date: Sep 5, 2006
 * Time: 8:32:43 AM
 */
public class RevshareLevelPercentageCalculator {

    public static double REVSHARETOPPERCENT = 3;

    public static double getPercentToShare(int level){
        Logger logger = Logger.getLogger(RevshareLevelPercentageCalculator.class);
        try{
            //Calculate percentage to share at this level
            double percenttoshare = REVSHARETOPPERCENT;
            for(int i=1; i<level; i++){
                percenttoshare = percenttoshare/2;
            }
            return percenttoshare;
        } catch (Exception ex){
            logger.debug("Percent to share couldn't be calculated. Returning 0. level="+level);
            logger.error("",ex);
            return 0;
        }
    }

    public static double getAmountToShare(double revenueShareIsBasedOn, int level){
        Logger logger = Logger.getLogger(RevshareLevelPercentageCalculator.class);
        try{
            return revenueShareIsBasedOn * (getPercentToShare(level)/100);
        } catch (Exception ex){
            logger.debug("Amount to share couldn't be calculated. Returning 0. revenueShareIsBasedOn=" + revenueShareIsBasedOn + " level="+level);
            logger.error("",ex);
            return 0;
        }
    }

}
