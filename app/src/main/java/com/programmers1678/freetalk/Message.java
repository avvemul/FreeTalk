package com.programmers1678.freetalk;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Abhi on 12/9/2015.
 */
public class Message {
    private String username;
    private String message;
    public Message() {}
    public Message(String user, String text) {
        username = user;
        message = text;
    }
    public String getUsername() {
        return username;
    }
    public String getMessage() {
        return message;
    }
    public String time() {
        String sendTime = DateFormat.getDateTimeInstance().format(new Date());
        return sendTime;
    }
}
