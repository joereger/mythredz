package com.mythredz.timeline;

import com.mythredz.dao.User;

import java.util.*;

/**
 * User: joereger
 * Date: Jun 11, 2008
 * Time: 9:19:44 AM
 */
public class Positioner {

    private ArrayList<TimelineObject> originalObjects;
    private ArrayList<TimelineObject> objects = new ArrayList<TimelineObject>();
    private int canvaswidth = 0;
    private int canvasheight = 0;
    private int numberofthreds = 0;
    private int heightperthred = 0;
    private HashMap<Integer, Integer> thredheightoffset = new HashMap<Integer, Integer>();
    private int secondsperpixel = 0;


    public Positioner(User user, ArrayList<TimelineObject> objectsToPosition, int canvasheight, int canvaswidth){
        this.originalObjects = objectsToPosition;
        this.objects = objectsToPosition;
        this.canvaswidth = canvaswidth;
        this.canvasheight = canvasheight;
    }

    public void position(){
        //Sort objects
        sortObjects();
        //Figure out how tall each thred should be
        calculateNumberOfThreds();
        if (numberofthreds>0){
            heightperthred = canvasheight/numberofthreds;
        } else {
            heightperthred = canvasheight;
        }
        //Set the threadoffsets
        calculateThredOffsets();
        //Calculate seconds per pixel
        calculateSecondsPerPixel();
        //Put all onto timeline in first sub-thred slot
        putIntoFirstThredSlot();

    }

    private void calculateSecondsPerPixel(){
        Date firstdate = objects.get(0).getPost().getDate();
        Date lastdate = objects.get(objects.size()).getPost().getDate();
        Long totalsecs = firstdate.getTime() - lastdate.getTime();
        int totalseconds = totalsecs.intValue()/1000;
        secondsperpixel = totalseconds/canvaswidth;
    }

    private void sortObjects(){
        Collections.sort(objects, new TimelineObjectDateComparator());
    }

    private void calculateNumberOfThreds(){
        HashMap<Integer, String> thredids = new HashMap<Integer, String>();
        for (Iterator<TimelineObject> iterator=objects.iterator(); iterator.hasNext();) {
            TimelineObject timelineObject=iterator.next();
            thredids.put(timelineObject.getThred().getThredid(), "");
        }
        numberofthreds = thredids.size();
    }

    private void calculateThredOffsets(){
        HashMap<Integer, Integer> out = new HashMap<Integer, Integer>();
        //Iterate objects to get a list of unnique thredids
        HashMap<Integer, String> thredids = new HashMap<Integer, String>();
        for (Iterator<TimelineObject> iterator=objects.iterator(); iterator.hasNext();) {
            TimelineObject timelineObject=iterator.next();
            thredids.put(timelineObject.getThred().getThredid(), "");
        }
        //Iterate thredids to calculate offsets
        Iterator keyValuePairs = thredids.entrySet().iterator();
        for (int i = 0; i < thredids.size(); i++){
            Map.Entry mapentry = (Map.Entry) keyValuePairs.next();
            Integer thredid = (Integer)mapentry.getKey();
            out.put(thredid, (i+1)*heightperthred);
        }
        thredheightoffset = out;
    }

    private void putIntoFirstThredSlot(){

    }




    public ArrayList<TimelineObject> getObjects(){
        return objects;
    }

}
