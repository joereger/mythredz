package com.mythredz.htmlui;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URLEncoder;
import javax.servlet.*;
import javax.servlet.http.*;

import com.mythredz.systemprops.SystemProperty;
import com.mythredz.systemprops.BaseUrl;
import com.mythredz.session.UrlSplitter;

/**
 *
 * @author Jayson Falkner - jayson@jspinsider.com
 */
public class Encode4FacebookResponseStream extends ServletOutputStream {
    // abstraction of the output stream used for compression
    protected OutputStream bufferedOutput = null;

    // state keeping variable for if close() has been called
    protected boolean closed = false;

    // reference to original response
    protected HttpServletResponse response = null;
    protected HttpServletRequest request = null;

    // reference to the output stream to the client's browser
    protected ServletOutputStream output = null;

    // default size of the in-memory buffer
    private int bufferSize = 50000;

    ServletContext sc;

    public Encode4FacebookResponseStream(HttpServletRequest request, HttpServletResponse response, ServletContext sc) throws IOException {
        super();
        closed = false;
        this.sc = sc;
        this.response = response;
        this.request = request;
        this.output = response.getOutputStream();
        bufferedOutput = new ByteArrayOutputStream();
    }

    public void close() throws IOException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.debug("close() called");
        
        // make up a nonce
        String nonce = Integer.toString((int)(Math.random()*Integer.MAX_VALUE));
        // set the nonce in app scope
        sc.setAttribute("nonce", nonce);

        // get the content
        ByteArrayOutputStream baos = (ByteArrayOutputStream) bufferedOutput;

        // make a string out of the response
        String pageText = new String(baos.toByteArray());
        //logger.debug("pageText= "+pageText);

        // use regex to find the links
        //Pattern p = Pattern.compile(" href=\"[^\"]*|action=\"[^\"]*");
        //Pattern p = Pattern.compile(" href=\"[^\"]*|src=\"[^\"]*|action=\"[^\"]*");
        Pattern p = Pattern.compile(" href=\"[^\"]*|src=\"[^\"]*|action=\"[^\"]*|background:url\\(\"[^\"]*");
        Matcher m = p.matcher(pageText);



        String newText = "";
        int offset = 0;
        while (m.find(offset)) {
            //logger.debug("found a match: "+m.toString());

            // update the text
            newText = newText + pageText.substring(offset, m.start());
            // update the offset
            offset = m.end();
            // get the matching string
            String match = pageText.substring(m.start(), m.end());
            //logger.debug("match="+match);

            // get the URL, between the quotes
            String[] split = match.split("\"");
            String url = "";
            if (split.length>=2){
                url = split[1];
            } else {
                logger.error("split.length<2 match="+match);    
            }
            //logger.debug("url="+url);

            //I need to separate the script name from the querystring
            String[] splitUrl = url.split("\\?");
            String scriptname = splitUrl[0];
            //logger.debug("scriptname="+scriptname);

            //Querystring
            String queryString = "";
            if (splitUrl.length>1){
                queryString = "&" + splitUrl[1];
            }

            //Encode the finalized link
            String encoded = url;
            if (url.indexOf("http:")<=-1){
                if (split[0].indexOf("href")>-1){
                    //logger.debug("treating as href");
                    encoded = "/"+ SystemProperty.getProp(SystemProperty.PROP_FACEBOOK_APP_NAME) + "/?dpage="+ URLEncoder.encode(scriptname, "UTF-8")+queryString;
                } else if (split[0].indexOf("src")>-1) {
                    //logger.debug("treating as src");
                    encoded = BaseUrl.get(false)+url.substring(1, url.length());
                } else if (split[0].indexOf("background:url(")>-1) {
                    //logger.debug("treating as background:url(");
                    encoded = BaseUrl.get(false)+url.substring(1, url.length());
                } else if (split[0].indexOf("action")>-1) {
                    //logger.debug("treating as action");
                    encoded = "";
                } else {
                    //logger.debug("no idea what to treat this as");
                }
            }

            // add the match to the new text
            newText = newText + split[0]+"\""+encoded;
        }
        // add the final text
        newText += pageText.substring(offset, pageText.length());



        // set appropriate HTTP headers
//        response.setContentLength(compressedBytes.length);
        output.write(newText.getBytes());
        output.flush();
        output.close();
        closed = true;
    }

    public void flush() throws IOException {
        if (closed) {
            throw new IOException("Cannot flush a closed output stream");
        }
        bufferedOutput.flush();
    }

    public void write(int b) throws IOException {
        if (closed) {
            throw new IOException("Cannot write to a closed output stream");
        }
        // write the byte to the temporary output
        bufferedOutput.write((byte) b);
    }

    public void write(byte b[]) throws IOException {
        write(b, 0, b.length);
    }

    public void write(byte b[], int off, int len) throws IOException {
        System.out.println("writing...");
        if (closed) {
            throw new IOException("Cannot write to a closed output stream");
        }
        // write the content to the buffer
        bufferedOutput.write(b, off, len);
    }

    public boolean closed() {
        return (this.closed);
    }

    public void reset() {
        //noop
    }
}
