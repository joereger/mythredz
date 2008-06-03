package com.mythredz.money;

/**
 * User: Joe Reger Jr
 * Date: Oct 11, 2006
 * Time: 1:08:11 PM
 */
public interface PaymentMethod {

    public static int PAYMENTMETHODCREDITCARD = 1;
    public static int PAYMENTMETHODMANUAL = 2;
    public static int PAYMENTMETHODPAYPAL = 3;

    public void giveUserThisAmt();
    public boolean getIssuccessful();
    public String getNotes();
    public String getCorrelationid();
    public String getTransactionid();
  
}
