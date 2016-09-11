package com.tmholen.thecrazynewsmsapp.messaging;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dogsh on 11-Sep-16.
 */

public class TextMessage implements Serializable{
    private String message;
    private String recipientName;
    private String senderName;
    private Date timestamp;

    public TextMessage(String message, String recipientName, String senderName, Date timestamp) {
        this.message = message;
        this.recipientName = recipientName;
        this.senderName = senderName;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getSenderName() {
        return senderName;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
