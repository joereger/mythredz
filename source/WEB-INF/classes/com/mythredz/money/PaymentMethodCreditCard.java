package com.mythredz.money;

import org.apache.log4j.Logger;
import com.mythredz.dao.User;
import com.mythredz.dao.Creditcard;
import com.mythredz.money.paypal.CallerFactory;
import com.mythredz.util.Time;
import com.mythredz.util.Str;
import com.paypal.soap.api.*;
import com.paypal.sdk.exceptions.PayPalException;

import java.util.Calendar;

/**
 * User: Joe Reger Jr
 * Date: Oct 11, 2006
 * Time: 1:12:04 PM
 */
public class PaymentMethodCreditCard extends PaymentMethodBase implements PaymentMethod {
   Logger logger = Logger.getLogger(this.getClass().getName());

   public PaymentMethodCreditCard(User user, double amt){
        super(user, amt);
   }

    public void giveUserThisAmt() {
    
        if (amt>=0){
            //@todo eventually I can just look up user's past transactions that were successful and do refunds
            logger.error("Credit card can not pay people money (positive amt sent to giveUserThisAmt()) userid="+user.getUserid()+" amt="+amt);
            return;
        }

        logger.debug("---------- Credit Card Processing Start ----------");
        try{
            if (user.getChargemethodcreditcardid()>0){
                Creditcard cc = Creditcard.get(user.getChargemethodcreditcardid());
                logger.debug("cc.getCreditcardid()="+cc.getCreditcardid());
                logger.debug("cc.getCcnum()="+cc.getCcnum());
                logger.debug("cc.getCctype()="+cc.getCctype());

                DoDirectPaymentRequestType request = new DoDirectPaymentRequestType();
                DoDirectPaymentRequestDetailsType details = new DoDirectPaymentRequestDetailsType();

                CreditCardDetailsType creditCard = new CreditCardDetailsType();
                creditCard.setCreditCardNumber(cc.getCcnum());
                if (cc.getCctype()==Creditcard.CREDITCARDTYPE_VISA){
                    creditCard.setCreditCardType(CreditCardTypeType.Visa);
                } else if (cc.getCctype()==Creditcard.CREDITCARDTYPE_MASTERCARD){
                    creditCard.setCreditCardType(CreditCardTypeType.MasterCard);
                } else if (cc.getCctype()==Creditcard.CREDITCARDTYPE_AMEX){
                    creditCard.setCreditCardType(CreditCardTypeType.Amex);
                } else if (cc.getCctype()==Creditcard.CREDITCARDTYPE_DISCOVER){
                    creditCard.setCreditCardType(CreditCardTypeType.Discover);
                }
                creditCard.setCVV2(cc.getCvv2());
                creditCard.setExpMonth(cc.getCcexpmo());
                creditCard.setExpYear(cc.getCcexpyear());

                PayerInfoType cardOwner = new PayerInfoType();
                cardOwner.setPayerCountry(CountryCodeType.US);

                AddressType address = new AddressType();
                address.setPostalCode(cc.getPostalcode());
                address.setStateOrProvince(cc.getState());
                address.setStreet1(cc.getStreet());
                address.setCountryName("US");
                address.setCountry(CountryCodeType.US);
                address.setCityName(cc.getCity());
                cardOwner.setAddress(address);

                PersonNameType payerName = new PersonNameType();
                payerName.setFirstName(cc.getFirstname());
                payerName.setLastName(cc.getLastname());
                cardOwner.setPayerName(payerName);

                creditCard.setCardOwner(cardOwner);
                details.setCreditCard(creditCard);

                details.setIPAddress(cc.getIpaddress());
                //details.setMerchantSessionId(cc.getMerchantsessionid());
                details.setPaymentAction(PaymentActionCodeType.Sale);

                PaymentDetailsType payment = new PaymentDetailsType();

                BasicAmountType orderTotal = new BasicAmountType();
                orderTotal.setCurrencyID(CurrencyCodeType.USD);
                orderTotal.set_value(Str.formatForFinancialTransactionsNoCommas((-1)*amt));
                payment.setOrderTotal(orderTotal);

                details.setPaymentDetails(payment);
                request.setDoDirectPaymentRequestDetails(details);

                CallerFactory cf = new CallerFactory();

                try{
                    DoDirectPaymentResponseType response = (DoDirectPaymentResponseType) cf.getCaller().call("doDirectPayment", request);
                    logger.debug("Operation Ack: " + response.getAck());
                    logger.debug("Transaction ID: " + response.getTransactionID());
                    logger.debug("CVV2: " + response.getCVV2Code());
                    logger.debug("AVS: " + response.getAVSCode());
                    if (response.getAmount()!=null){
                        logger.debug("Gross Amount: " + response.getAmount().getCurrencyID()+ " " + response.getAmount().get_value());
                    }
                    correlationid = response.getCorrelationID();
                    transactionid = response.getTransactionID();
                    ErrorType[] errors = response.getErrors();
                    if (errors!=null){
                        issuccessful = true;
                        for (int i = 0; i < errors.length; i++) {
                            ErrorType error = errors[i];
                            logger.debug("Error "+i+": "+error.getLongMessage());
                            if (error.getSeverityCode()==SeverityCodeType.Error){
                                logger.error("Credit Card Error: userid="+user.getUserid()+" :"+error.getLongMessage());
                                notes =  notes + "Credit Card Error: "+error.getLongMessage()+" ";
                                issuccessful = false;
                            } else if (error.getSeverityCode()==SeverityCodeType.Warning){
                                logger.error("Credit Card Warning: userid="+user.getUserid()+" :"+error.getLongMessage());
                                notes =  notes + "Credit Card Warning: "+error.getLongMessage()+" ";
                            }  else if (error.getSeverityCode()==SeverityCodeType.CustomCode){
                                logger.error("Credit Card Custom Code Error: userid="+user.getUserid()+" :"+error.getLongMessage());
                                notes =  notes + "Credit Card Custom Code Error: "+error.getLongMessage()+" ";
                            }
                        }
                    } else {
                        issuccessful = true;
                    }

                } catch (PayPalException ppex){
                    ppex.printStackTrace();
                    logger.error(ppex);
                    notes = "An internal server error occurred at "+ Time.dateformatcompactwithtime(Calendar.getInstance())+".  No money was exchanged.";
                    issuccessful = false;
                } catch (Exception ex){
                    ex.printStackTrace();
                    logger.error("",ex);
                    notes = "An internal server error occurred at "+ Time.dateformatcompactwithtime(Calendar.getInstance())+".  No money was exchanged.";
                    issuccessful = false;
                }
            } else {
                notes = user.getFirstname() + " "+user.getLastname()+" (userid="+user.getUserid()+") can't be paid because no credit card is tied to the account.";
                issuccessful = false;
            }
        } catch (Exception ex){
            ex.printStackTrace();
            logger.error("",ex);
            notes = "An internal server error occurred at "+ Time.dateformatcompactwithtime(Calendar.getInstance())+".  No money was exchanged.";
            issuccessful = false;
        }
        logger.debug("---------- Credit Card Processing End ----------");



    }


}
