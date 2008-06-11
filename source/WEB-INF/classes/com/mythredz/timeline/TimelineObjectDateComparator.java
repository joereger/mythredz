package com.mythredz.timeline;

import java.util.Comparator;
import java.util.Calendar;
import java.util.Date;


/**
 * User: joereger
 * Date: Jun 11, 2008
 * Time: 10:44:34 AM
 */
public class TimelineObjectDateComparator implements Comparator {

    public int compare(Object obj1, Object obj2){

        if (!(obj1 instanceof TimelineObject) || !(obj2 instanceof TimelineObject)){
            throw new ClassCastException("A TimelineObject object expected.");
        }

        TimelineObject to1 = (TimelineObject)obj1;
        TimelineObject to2 = (TimelineObject)obj2;

		Date d1 = to1.getPost().getDate();
		Date d2 = to2.getPost().getDate();

		Long time1 = Long.parseLong(String.valueOf(d1.getTime()));
		Long time2 = Long.parseLong(String.valueOf(d2.getTime()));

		int t1 = time1.intValue();
		int t2 = time2.intValue();

		return (t1-t2);
	}
}
