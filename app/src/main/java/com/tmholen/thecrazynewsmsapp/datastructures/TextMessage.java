package com.tmholen.thecrazynewsmsapp.datastructures;

import com.tmholen.thecrazynewsmsapp.R;
import com.tmholen.thecrazynewsmsapp.Tools;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by dogsh on 11-Sep-16.
 */

public class TextMessage implements Serializable{
    private int dialogID;
    private String sender;
    private String message;
    private String timestamp;
    private String senderImage;
    private Tools t = new Tools(){};

    public TextMessage(String sender, String message, int dialogID) {
        this.message = message;
        this.dialogID = dialogID;
        this.senderImage = t.ParseResourceToUriString(R.drawable.ic_person_missing_photo);
        this.sender = sender;
        this.timestamp = t.getRelevantStringDate(Calendar.getInstance());
    }

    public TextMessage(String sender,String message, Calendar timestamp, int dialogID) {
        this.message = message;
        this.dialogID = dialogID;
        this.senderImage = t.ParseResourceToUriString(R.drawable.ic_person_missing_photo);
        this.sender = sender;
        this.timestamp = t.getRelevantStringDate(timestamp);
    }

    /*public TextMessage(String sender, String contact, String message, Uri recipientImage, int dialogID) {
        this.message = message;
        this.dialogID = dialogID;
        this.senderImage = recipientImage;
        this.sender = sender;
        this.timestamp = t.getRelevantStringDate(Calendar.getInstance());
    }

    public TextMessage(String sender, String contact, String message, Calendar timestamp, int dialogID) {
        this.message = message;
        this.dialogID = dialogID;
        this.senderImage = t.ParseResourceToUri(R.drawable.ic_person_missing_photo);
        this.sender = sender;
        this.timestamp = t.getRelevantStringDate(timestamp);
    }

    public TextMessage(String sender, String contact, String message, Uri recipientImage, Calendar timestamp, int dialogID) {
        this.message = message;
        this.dialogID = dialogID;
        this.senderImage = recipientImage;
        this.sender = sender;
        this.timestamp = t.getRelevantStringDate(timestamp);
    }*/

    public String getMessage() {
        return message;
    }

    public int getDialogID() {
        return dialogID;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public String getSender() {
        return sender;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
