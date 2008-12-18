package com.mythredz.smtp;



import org.apache.log4j.Logger;

import javax.mail.BodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.imageio.ImageReader;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.util.Calendar;
import java.util.Iterator;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.image.BufferedImage;

import com.mythredz.util.Util;
import com.mythredz.dao.Emailaddress;


/**
 * This EmailApi accepts a raw rfc 2822 message as text, creates javax.mail objects from it
 * and proceeds to try to apply it to a web log.
 *
 * This class also needs to deal with spam and be aware that people will try to use it
 * as a relay.
 */
public class EmailApi {
    public String rawMailMessage;
    javax.mail.internet.MimeMessage mimeMessage;
    javax.mail.internet.MimeMultipart multiPart;
    int accountuserid = -1;
    boolean isMultipart = false;
    String from;
    String to;
    String subject;
    String body;

      /**
     * Constructor
     */
    public EmailApi(javax.mail.internet.MimeMessage mimeMessage, String from, String to){
        this.mimeMessage = mimeMessage;
        if (mimeMessage!=null){
            this.from = from;
            this.to = to;
            startWorking();
        }
    }

    /**
     * Constructor with ability to set debug
     * @param rawMailMessage
     */
    public EmailApi(String rawMailMessage){
        this.rawMailMessage = rawMailMessage;
        mimeMessage = turnStringIntoEmail(rawMailMessage);
        if (mimeMessage!=null){
            startWorking();
        }
    }

    private void startWorking(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        //Set some vars
        logger.debug( "Starting to process mail message.");

        //Parse the message
        boolean parseIn = parseIncomingMessage();
        logger.debug( "Result of parsing incoming raw message:" + parseIn);

        //Iterate all recipients, looking for valid logs
        if (parseIn){
            Emailaddress emailaddress = EmailaddressUtil.getEmailaddress(to);
            if (emailaddress!=null){
                newPost(emailaddress);
            }
        }

    }

    public static javax.mail.internet.MimeMessage turnStringIntoEmail(String rawMailMessage){
        Logger logger = Logger.getLogger(EmailApi.class);
        logger.debug( "rawMailMessage:<br>" + rawMailMessage);
        javax.mail.internet.MimeMessage mimeMessage = null;
        try{
            //Turn the raw message into a mimeMessage using an inputstream
            java.util.Properties properties = new java.util.Properties();
            javax.mail.Session session = javax.mail.Session.getDefaultInstance(properties);
            java.io.ByteArrayInputStream in = new java.io.ByteArrayInputStream(rawMailMessage.getBytes());
            mimeMessage = new javax.mail.internet.MimeMessage(session, in);
            in.close();
        } catch (java.io.UnsupportedEncodingException e){
            //Do nothing... the user sent something like CHINESEBIG5 or BIG5
            logger.debug( e);
        } catch (javax.mail.MessagingException e){
            //Some sort of message formatting problem on the part of the sender
            logger.debug( e);
        } catch (Exception e) {
            logger.error("", e);
        }

        return mimeMessage;
    }

    /**
     * Parses rawMailMessage, creating the more abstracted and powerful javax.mail objects.
     * The rawMailMessage var must already be populated with the raw mail message.
     */
    private boolean parseIncomingMessage(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{

            //Forward emails that should be forwarded
            try {
                //forwardIfNecessary(mimeMessage);
            } catch (Exception e){
                //Do nothing
            }

            //Figure out the content type of this message
            try{
                if (mimeMessage.getContentType().toLowerCase().indexOf("multipart")>-1){
                    isMultipart = true;
                }
            } catch (Exception e){
                //Do nothing
            }
            //Only if content type = multipart
            if (isMultipart){
                //Turn the message into a data source
                javax.mail.internet.MimePartDataSource mpds = new javax.mail.internet.MimePartDataSource(mimeMessage);
                //Turn the data source into a MimeMultipart
                multiPart = new javax.mail.internet.MimeMultipart(mpds);

                //At this point the rawMailMessage is into two much more powerful javax objects.
                //We can work with our shiny new MimeMultipart and MimeMessage.
                //MimeMessage holds the overall message while MimeMultipart holds the body, attachments, etc.

                //If in debug mode, list the parts
                if (1==1){
                    try{
                        logger.debug( "Number of Multiparts = " + multiPart.getCount());
                        for(int i=0; i<multiPart.getCount(); i++){
                            logger.debug( "Multipart #" + i + "<br>" + multiPart.getBodyPart(i).getContent() + "<br>class.getName()=" + multiPart.getBodyPart(i).getContent().getClass().getName());
                            if (multiPart.getBodyPart(i).getContent().getClass().getName().equals("com.sun.mail.Util.BASE64DecoderStream")){
                                String filename = multiPart.getBodyPart(i).getFileName();
                                String contenttype = multiPart.getBodyPart(i).getContentType();
                                logger.debug( "We have an attachment called: " + filename + "<br>contenttype=" + contenttype);
                            }
                        }
                    } catch (Exception e){
                        logger.debug( e);
                    }
                }
            }

            //Get the recipient(s)
            javax.mail.Address[] addresses = mimeMessage.getAllRecipients();
            //Only use the first recipient as the to address
            if (addresses!=null  && addresses.length>0){
                //to = addresses[0].toString();
            }

            //Get the subject
            subject = mimeMessage.getSubject();
            logger.debug( "Subject:" + subject);

            //Get the body
            getBody();


        //} catch (java.io.UnsupportedEncodingException e){
        //    //Do nothing... the user sent something like CHINESEBIG5 or BIG5
        //    reger.core.Util.debug(5, e);
        //    return false;
        } catch (javax.mail.MessagingException e){
            //Some sort of message formatting problem on the part of the sender
            logger.debug( e);
            return false;
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
        return true;
    }

    private void getBody(){
        Logger logger = Logger.getLogger(this.getClass().getName());
        //Get the body of the message
        body="";
        try{
            if (isMultipart){
                 tryToGetBodyOfEmailFromAPart(multiPart);

            } else {
                body = body + String.valueOf(mimeMessage.getContent());
                logger.debug( "Body found as non multipart message:" + body);
            }
        } catch (Exception e){
            logger.debug( e);
        }
        logger.debug( "Final Body:" + body);
    }

    private void tryToGetBodyOfEmailFromAPart(MimeMultipart bodyPart){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try{
            //Iterate all parts of the part
            boolean foundBodyFromPlain = false;
            boolean foundBodyFromHtml = false;
            boolean foundBodyFromElse = false;
            logger.debug( "Iterating all parts of a bodyPart in tryToGetBodyOfEmailFromAPart()");
            for(int i=0; i<bodyPart.getCount(); i++){
                logger.debug("Starting new bodyPart.");
                if (bodyPart.getBodyPart(i).getContentType().toLowerCase().indexOf("multipart")>-1){
                    //It's a part. Send it back to this function, recursively
                    try{
                        tryToGetBodyOfEmailFromAPart((MimeMultipart)bodyPart.getBodyPart(i).getContent());
                    } catch (Exception e){
                        logger.error("", e);
                    }
                } else if (bodyPart.getBodyPart(i).getContentType().toLowerCase().indexOf("image")>-1){
                    //Do nothing
                } else if (bodyPart.getBodyPart(i).getContentType().toLowerCase().indexOf("message")>-1){
                    //It's a message
                    MimeMessage nestedMsg = null;
                    try{
                        nestedMsg = (MimeMessage)bodyPart.getBodyPart(i).getContent();
                    } catch (java.lang.ClassCastException ex){
                        logger.debug( ex);
                    }
                    if (nestedMsg!=null && nestedMsg.getContentType().toLowerCase().indexOf("multipart")>-1){
                        //Convert it from a message to a multipart
                        javax.mail.internet.MimePartDataSource mpds = new javax.mail.internet.MimePartDataSource(nestedMsg);
                        tryToGetBodyOfEmailFromAPart(new javax.mail.internet.MimeMultipart(mpds));
                    } else {
                        if (nestedMsg!=null){
                            body = body + String.valueOf(nestedMsg.getContent());
                            logger.debug("Body found as nested message content:" + String.valueOf(nestedMsg.getContent()));
                        } else {
                            body = body + bodyPart.getBodyPart(i).getContent();
                        }
                    }
                } else if (bodyPart.getBodyPart(i).getContentType().toLowerCase().indexOf("text/plain")>-1){
                    if (!foundBodyFromHtml && !foundBodyFromPlain){
                        body = body + String.valueOf(bodyPart.getBodyPart(i).getContent());
                        foundBodyFromPlain = true;
                        logger.debug( "Body found as text/plain:" + String.valueOf(bodyPart.getBodyPart(i).getContent()));
                    } else {
                        logger.debug( "Body found as text/plain but not added because previous body was found:" + String.valueOf(bodyPart.getBodyPart(i).getContent()));
                    }
                } else if (bodyPart.getBodyPart(i).getContentType().toLowerCase().indexOf("text/html")>-1){
                    if (!foundBodyFromHtml && !foundBodyFromPlain){
                        body = body + String.valueOf(bodyPart.getBodyPart(i).getContent());
                        foundBodyFromHtml = true;
                        logger.debug( "Body found as text/html:" + String.valueOf(bodyPart.getBodyPart(i).getContent()));
                    } else {
                        logger.debug( "Body found as text/html but not added because previous body was found:" + String.valueOf(bodyPart.getBodyPart(i).getContent()));
                    }
                } else {
                    //body = body + String.valueOf(bodyPart.getBodyPart(i).getContent());
                    //foundBodyFromElse = true;
                    logger.debug( "Body found as else");
                }
            }
        } catch (javax.mail.MessagingException e){
            logger.debug( e);
        } catch (Exception e){
            logger.error("", e);
        }
    }







    /**
     * New Entry method.
     */
     private void newPost(Emailaddress emailaddress){
        Logger logger = Logger.getLogger(this.getClass().getName());
        try {

            //Create that sucker
            ProcessBodyTextOfEmail.process(body, emailaddress);



                //Deal with attachments
//                if (isMultipart){
//                    for(int i=0; i<multiPart.getCount(); i++){
//                        findAttachments(multiPart.getBodyPart(i), 0);
//                    }
//                }
        } catch (Exception e) {
            logger.error("", e);
        }

    }

//    private void findAttachments(BodyPart bodyPart, int eventid){
//        Logger logger = Logger.getLogger(this.getClass().getName());
//        try {
//            logger.debug( "bodyPart.getContentType()=" + bodyPart.getContentType() + "<br>Deciding whether to treat as an attachment.");
//            if (bodyPart.getFileName()!=null && !bodyPart.getFileName().equals("")){
//                logger.debug( "Filename is not null");
//                treatBodyPartAsAttachment(eventid, bodyPart);
//            } else if (bodyPart.getContentType().toLowerCase().indexOf("multipart")>-1){
//                logger.debug( "Is multipart");
//                MimeMultipart nestedMultiPart = (MimeMultipart)bodyPart.getContent();
//                for(int i=0; i<nestedMultiPart.getCount(); i++){
//                    findAttachments(nestedMultiPart.getBodyPart(i), eventid);
//                }
//            } else if (bodyPart.getContentType().toLowerCase().indexOf("image")>-1){
//                logger.debug( "Is image... will treat as a body part");
//                treatBodyPartAsAttachment(eventid, bodyPart);
//            } else if (bodyPart.getContentType().toLowerCase().indexOf("message")>-1){
//                logger.debug( "Is message");
//                MimeMessage nestedMsg = (MimeMessage)bodyPart.getContent();
//                if (nestedMsg.getContentType().toLowerCase().indexOf("multipart")>-1){
//                    logger.debug( "Is message with multipart");
//                    javax.mail.internet.MimePartDataSource mpds = new javax.mail.internet.MimePartDataSource(nestedMsg);
//                    MimeMultipart nestedMultiPart = new javax.mail.internet.MimeMultipart(mpds);
//                    for(int i=0; i<nestedMultiPart.getCount(); i++){
//                        findAttachments(nestedMultiPart.getBodyPart(i), eventid);
//                    }
//                }
//            } else {
//                logger.debug( "Not treating bodyPart as an attachment.");
//            }
//        } catch (Exception e){
//            logger.error("", e);
//        }
//    }
//
//    /**
//     * This method attaches all attachments from a message to an event.
//     * @param eventid
//     */
//    public boolean treatBodyPartAsAttachment(int eventid, BodyPart bodyPart) {
//        Logger logger = Logger.getLogger(this.getClass().getName());
//        try {
//            //Start with the #1, not #0 (which is the main body)
//            //for(int i=1; i<multiPart.getCount(); i++){
//                //if (bodyPart.equals()){
//                    int contentlength=0;
//                    String filename="";
//                    byte[] bits = new byte[0];
//
//
//                    //Get the filename
//                    filename = bodyPart.getFileName();
//
//                    logger.debug( "About to decode multipart.bodyPart with filename=" + filename);
//
//
//
//
//                    //Make sure there's enough space left for this user
//                    //Get the size of the incoming file
//                    //@todo content length space limitation?
//                    contentlength=bits.length - 1;
//                    long freespace = contentlength+100;
//                    if ((long)contentlength>freespace) {
//                        logger.debug( "Failed due to freeSpace limitations.<br>contentlength=" + contentlength + "<br>freeSpace=" + freespace);
//                        return false;
//                    }
//
//                    //Figure out a filename
//                    String incomingname = filename;
//                    String incomingnamebase = Util.getFilenameBase(incomingname);
//                    String incomingnameext = Util.getFilenameExtension(incomingname);
//
//                    //Calculate the new dated directory name
//                    Calendar cal = Calendar.getInstance();
//                    int year = cal.get(Calendar.YEAR);
//                    int month = cal.get(Calendar.MONTH)+1;
//                    String monthStr = String.valueOf(month);
//                    if (monthStr.length()==1){
//                        monthStr = "0"+monthStr;
//                    }
//                    String datedDirectoryName = year+"/"+monthStr;
//
//                    //Create directory
//                    String filesdirectory = ".";
//                    File dir = new File(filesdirectory);
//                    dir.mkdirs();
//                    File dirThumbs = new File(filesdirectory+".thumbnails/");
//                    dirThumbs.mkdirs();
//
//                    //Test for file existence... if it exists does, add an incrementer
//                    String finalfilename = incomingname;
//                    File savedFile  = new File(filesdirectory, finalfilename);
//                    int incrementer = 0;
//                    while (savedFile.exists()){
//                        incrementer=incrementer+1;
//                        finalfilename = incomingnamebase+"-"+incrementer;
//                        if (!incomingnameext.equals("")){
//                            finalfilename = finalfilename + "." + incomingnameext;
//                        }
//                        savedFile  = new File(filesdirectory, finalfilename);
//                    }
//
//                    logger.debug( "finalfilename="+datedDirectoryName+"/"+finalfilename);
//
//
//                     //Save the file with the updated filename
//                     //Turn the content into an inputstream
//                     FileOutputStream fileOut = new FileOutputStream(savedFile);
//                     try{
//                         if (bodyPart.getContent().getClass().getName().equals("java.awt.image.BufferedImage")){
//                            logger.debug( "bodyPart.getContent().getClass().getName().equals(\"java.awt.image.BufferedImage\") = true");
//                            BufferedImage bi = (BufferedImage)bodyPart.getContent();
//                            ImageIO.write(bi, getImageFormatName(bi), fileOut);
//
//                         } else {
//                             logger.debug( "bodyPart.getContent().getClass().getName().equals(\"java.awt.image.BufferedImage\") = false");
//                             java.io.InputStream is = (java.io.InputStream)bodyPart.getContent();
//                             byte[] buffer = new byte[64000];
//                             int read = 0;
//                             while(read != -1){
//                                read = is.read(buffer,0,buffer.length);
//                                if(read!=-1){
//                                    fileOut.write(buffer,0,read);
//                                }
//                             }
//                             fileOut.flush();
//                             is.close();
//                         }
//                     } catch (java.lang.ClassCastException ccex){
//                        logger.error("", ccex);
//                     }
//                     fileOut.close();
//
//
//                //}
//            //}
//
//        } catch (Exception e) {
//            logger.error("", e);
//            return false;
//        }
//        return true;
//
//    }
//
//    private static String getImageFormatName(Object o) {
//        Logger logger = Logger.getLogger(EmailApi.class);
//        try {
//            // Create an image input stream on the image
//            ImageInputStream iis = ImageIO.createImageInputStream(o);
//            // Find all image readers that recognize the image format
//            Iterator iter = ImageIO.getImageReaders(iis);
//            if (!iter.hasNext()) {
//                // No readers found
//                return null;
//            }
//            // Use the first reader
//            ImageReader reader = (ImageReader)iter.next();
//            // Close stream
//            iis.close();
//            // Return the format name
//            return reader.getFormatName();
//        } catch (Exception e) {
//            logger.error("", e);
//            return "jpeg";
//        }
//    }


}
