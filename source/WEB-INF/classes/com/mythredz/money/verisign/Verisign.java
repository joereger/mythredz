package com.mythredz.money.verisign;



import java.util.Hashtable;
import java.util.Enumeration;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.Verisign.payment.PFProAPI;
import com.mythredz.systemprops.WebAppRootDir;
import org.apache.log4j.Logger;

/**
 * Communicates with Verisign.
 */
public class Verisign {


    Logger logger = Logger.getLogger(this.getClass().getName());

    //private String verisignHostAddress = "payflow.verisign.com";
    private String verisignHostAddress = "test-payflow.verisign.com";
    private String verisignUser = "regercom";
    private String verisignPassword = "physics1";
    private String verisignVendor = "";
    private String verisignPartner = "verisign";
    private int    verisignHostPort = 443;
    private int    verisignTimeout = 30;
    private String verisignProxyAddress = "";
    private int    verisignProxyPort = 0;
    private String verisignProxyLogon = "";
    private String verisignProxyPassword = "";

    private Hashtable props;


    public Verisign(){

    }

    public String chargeCard(double amt, String acct, int expmonth, int expyear, String street, String zip) throws VerisignException{
        StringBuffer out = new StringBuffer();

        String expmonthStr = String.valueOf(expmonth);
        if (expmonthStr.length()==1){
            expmonthStr = "0" + expmonthStr;
        }
        String expyearStr = String.valueOf(expyear);
        if (expyearStr.length()==1){
            expyearStr = "0" + expyearStr;
        }

        try{
            //Format currency
            NumberFormat formatter = DecimalFormat.getInstance();
            formatter.setMinimumFractionDigits(2);
            formatter.setMaximumFractionDigits(2);
            logger.debug("amt="+amt);
            String amtStr = formatter.format(amt);

            //Set the variables
            props = new Hashtable();
            props.put("AMT", String.valueOf(amtStr));
            props.put("ACCT", String.valueOf(acct));
            props.put("EXPDATE", expmonthStr + expyearStr);
            props.put("STREET", String.valueOf(street));
            props.put("ZIP", String.valueOf(zip));
            props.put("TRXTYPE", String.valueOf("S"));
            props.put("TENDER", String.valueOf("C"));
            //props.put("ACTION", String.valueOf("A"));

        } catch (Exception e){
            logger.error("", e);
            e.printStackTrace();
            VerisignException ex = new VerisignException();
            ex.errorMessage = e.getMessage();
            ex.sentString = buildSubmitString();
            ex.receivedString = "";
            ex.recievedProps =  new Hashtable();
            throw ex;
        }


        //Make the call to verisign
        try{
            out.append(transaction());
        } catch (VerisignException vex){
            logger.debug(vex);
            throw vex;
        } catch (Exception e){
            logger.error("", e);
            VerisignException ex = new VerisignException();
            ex.errorMessage = e.getMessage();
            ex.sentString = buildSubmitString();
            ex.receivedString = "";
            ex.recievedProps = new Hashtable();
            throw ex;
        }


        return out.toString();

    }

    /**
     * This method creates a submitstring, sends it to Verisign, records the results to the database and returns the verisign result code
     */
    private String transaction() throws VerisignException{
        StringBuffer out = new StringBuffer();

        //Submit the transaction to Verisign
        PFProAPI pn = new PFProAPI();
        pn.SetCertPath(WebAppRootDir.getWebAppRootPath() + "/certs");
        pn.CreateContext(verisignHostAddress, verisignHostPort, verisignTimeout, verisignProxyAddress, verisignProxyPort, verisignProxyLogon, verisignProxyPassword);
        String VSResponsestring = pn.SubmitTransaction(buildSubmitString());
        pn.DestroyContext();

        //Put the response into a hash table of name/value pairs
        Hashtable VSResponseHash=parseNVpairs(VSResponsestring);


        if (VSResponseHash==null || VSResponseHash.get("RESULT")==null || !VSResponseHash.get("RESULT").equals("0")){
            //Something went wrong... throw an exception
            VerisignException vex = new VerisignException();
            vex.sentString = buildSubmitString();
            vex.receivedString = VSResponsestring;
            vex.recievedProps =  VSResponseHash;
            if (VSResponseHash.get("RESPMSG")!=null){
                vex.errorMessage = (String)VSResponseHash.get("RESPMSG");
            } else {
                vex.errorMessage = "" + VSResponsestring;
            }
            throw vex;
        }

        //Append to result
        out.append("sentString:");
        out.append("\n");
        out.append(buildSubmitString());
        out.append("\n\n");
        out.append("receivedString:");
        out.append("\n");
        out.append(VSResponsestring);
        out.append("\n\n");
        out.append("receivedProps:");
        out.append("\n");
        out.append(VSResponseHash);

        //Get result to return
        return out.toString();
    }


    private String buildSubmitString(){
        StringBuffer submitString = new StringBuffer();
        //Start paramlist with authentication info
        submitString.append("USER=" + verisignUser + "&VENDOR=" + verisignVendor + "&PARTNER=" + verisignPartner + "&PWD=" + verisignPassword);
        //Add the props as a string
        if (!compilePropsString().equals("")){
            submitString.append("&"+compilePropsString());
        }
        return submitString.toString();
    }



    private String compilePropsString(){
        //Add each of the props
        StringBuffer tmp = new StringBuffer();
        if (props!=null){
            Enumeration keys = props.keys();
            while ( keys.hasMoreElements() ){
                String key = (String)keys.nextElement();
                String value = (String)props.get(key );
                tmp = addNameValuePair(tmp, key, value);
            }
        }

        return tmp.toString();
    }


    private StringBuffer addNameValuePair(StringBuffer tmpLic, String name, String value){
        if (name!=null && !name.equals("")){
            if (value!=null && !value.equals("")){
                if (tmpLic.length()>0){
                    tmpLic.append("&");
                }
                tmpLic.append(name+"="+value);
            }
        }
        return tmpLic;
    }




    public static Hashtable parseNVpairs(String instring){
        //This function parses a string of the format:
        //"name1=val1&name2=val2&name3=val3"
        //It returns a hash table of name/value pairs that can
        //be accessed with the syntax:
        //HashTable test=parseNVpairs("name1=val1&name2=val2&name3=val3")
        //out.println(test("name3"))
        Logger logger = Logger.getLogger(Verisign.class);

        Hashtable outHashtable = new Hashtable();
        logger.debug("Verisign.java parseNVPairs() instring=" + instring);

        if (instring!=null && !instring.equals("")){
            //Split on & to get nv pairs
            String[] splitarray = instring.split("&");
            //reger.core.Util.logStringArrayToDb("splitarray", splitarray);

            if (splitarray!=null && splitarray.length>0){
                for (int i = 0; i < splitarray.length; i++) {
                    //Split on = to split to name and value
                    String[] tmpArr = splitarray[i].split("=");
                    if (tmpArr!=null && tmpArr.length>=2){
                        //Add to the hashtable
                        logger.debug("Verisign.java parseNVPairs() outHashtable.put("+tmpArr[0]+", "+tmpArr[1]+")");
                        outHashtable.put(tmpArr[0], tmpArr[1]);
                    } else if (tmpArr!=null && tmpArr.length==1){
                        //Add to the hashtable
                        logger.debug("Verisign.java parseNVPairs() outHashtable.put("+tmpArr[0]+", )");
                        outHashtable.put(tmpArr[0], "");
                    }

                }
            }
        }
        //reger.core.Util.logHashTableToDb("outHashtable", outHashtable);
        return outHashtable;
    }








}

