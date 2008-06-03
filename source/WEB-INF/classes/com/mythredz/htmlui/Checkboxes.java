package com.mythredz.htmlui;

import com.mythredz.util.Str;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * User: Joe Reger Jr
 * Date: Oct 28, 2007
 * Time: 2:01:39 AM
 */
public class Checkboxes {

    public static String getHtml(String name, ArrayList<String> values, ArrayList<String> options, String styleclass, String style){
        StringBuffer out = new StringBuffer();

        if (styleclass!=null && !styleclass.equals("")){
            styleclass = "class=\""+styleclass+"\"";
        }
        if (style!=null && !style.equals("")){
            style = "style=\""+style+"\"";
        }

        for (Iterator iterator = options.iterator(); iterator.hasNext();) {
            String s = (String) iterator.next();
            String selected = "";
            if (isSelected(s, values)){
                selected = " checked='true'";
            }
            out.append("<input type=\"checkbox\" name=\""+ Str.cleanForHtml(name) +"\" value=\""+Str.cleanForHtml(s.trim())+"\" style "+selected+"><font "+styleclass+" "+style+">" + s.trim() +"</font>");
            out.append("<br/>");
        }

        return out.toString();

    }

    private static boolean isSelected(String option, ArrayList<String> values){
        for (Iterator<String> iterator = values.iterator(); iterator.hasNext();) {
            String value = iterator.next();
            if (value.equals(option)){
                return true;
            }
        }
        return false;
    }



    public static ArrayList<String> getValueFromRequest(String name, String prettyName, boolean isrequired) throws ValidationException {
        ArrayList<String> out = new ArrayList<String>();
        if (Pagez.getRequest().getParameterValues(name)!=null){
            String[] selectedOptions = Pagez.getRequest().getParameterValues(name);
            for (int i = 0; i < selectedOptions.length; i++) {
                String selectedOption = selectedOptions[i];
                out.add(selectedOption);
            }
        }
        if (isrequired && out.size()==0){
            throw new ValidationException(prettyName+" is required.");
        }
        return out;
    }



}
