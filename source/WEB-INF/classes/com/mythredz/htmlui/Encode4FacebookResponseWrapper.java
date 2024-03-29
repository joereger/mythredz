package com.mythredz.htmlui;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Encode4FacebookResponseWrapper extends HttpServletResponseWrapper {
    protected HttpServletResponse origResponse = null;
    protected HttpServletRequest origRequest = null;
    protected ServletOutputStream stream = null;
    protected PrintWriter writer = null;
    ServletContext sc;

    public Encode4FacebookResponseWrapper(HttpServletRequest request, HttpServletResponse response, ServletContext sc) {
        super(response);
        this.sc = sc;
        origResponse = response;
        origRequest = request;
    }

    public ServletOutputStream createOutputStream() throws IOException {
        return (new Encode4FacebookResponseStream(origRequest, origResponse, sc));
    }

    public void finishResponse() {
        try {
            if (writer != null) {
                writer.close();
            } else {
                if (stream != null) {
                    stream.close();
                }
            }
        } catch (IOException e) {}
    }

    public void flushBuffer() throws IOException {
        stream.flush();
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called!");
        }

        if (stream == null)
            stream = createOutputStream();
        return (stream);
    }

    public PrintWriter getWriter() throws IOException {
        if (writer != null) {
            return (writer);
        }

        if (stream != null) {
            throw new IllegalStateException("getOutputStream() has already been called!");
        }

        stream = createOutputStream();
        // BUG FIX 2003-12-01 Reuse content's encoding, don't assume UTF-8
        writer = new PrintWriter(new OutputStreamWriter(stream, origResponse.getCharacterEncoding()));
        return (writer);
    }

    public void setContentLength(int length) {}
}
