package com.tmholen.thecrazynewsmsapp.messaging;

import android.net.Uri;

import com.tmholen.thecrazynewsmsapp.R;
import com.tmholen.thecrazynewsmsapp.Tools;

import java.io.Serializable;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by dogsh on 11-Sep-16.
 */

public class TextMessage implements Serializable{
    private String messageThreadId;
    private String recipientName;
    private String message;
    private String timestamp;
    private Uri recipientImage;
    private Tools t = new Tools(){};
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm - dd.MM.yy", Locale.getDefault());

    public TextMessage(String recipientName, String message, String messageThreadId) {
        this.message = message;
        this.messageThreadId = messageThreadId;
        this.recipientImage = t.ParseResourceToUri(R.drawable.ic_person_missing_photo);
        this.recipientName = recipientName;
        this.timestamp = t.getRelevantStringDate(Calendar.getInstance());
    }

    public TextMessage(String recipientName, String message, Uri recipientImage, String messageThreadId) {
        this.message = message;
        this.messageThreadId = messageThreadId;
        this.recipientImage = recipientImage;
        this.recipientName = recipientName;
        this.timestamp = t.getRelevantStringDate(Calendar.getInstance());
    }

    public TextMessage(String recipientName, String message, Calendar timestamp, String messageThreadId) {
        this.message = message;
        this.messageThreadId = messageThreadId;
        this.recipientImage = t.ParseResourceToUri(R.drawable.ic_person_missing_photo);
        this.recipientName = recipientName;
        this.timestamp = t.getRelevantStringDate(timestamp);
    }

    public TextMessage(String recipientName, String message, Uri recipientImage, Calendar timestamp, String messageThreadId) {
        this.message = message;
        this.messageThreadId = messageThreadId;
        this.recipientImage = recipientImage;
        this.recipientName = recipientName;
        this.timestamp = t.getRelevantStringDate(timestamp);
    }

    public String getMessage() {
        return message;
    }

    public String getMessageThreadId() {
        return messageThreadId;
    }

    public Uri getRecipientImage() {
        return recipientImage;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
