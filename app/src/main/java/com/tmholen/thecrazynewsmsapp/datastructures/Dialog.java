package com.tmholen.thecrazynewsmsapp.datastructures;

import com.tmholen.thecrazynewsmsapp.Tools;

import java.util.Calendar;

/**
 * Created by dogsh on 18-Sep-16.
 */


public class Dialog {

    private int dialogID;
    private String lastMessage;
    private String contactName;
    private String timestamp;

    private Tools t = new Tools(){};

    public Dialog(String contactName, String lastMessage, Calendar timestamp, int dialogID) {
        this.contactName = contactName;
        this.lastMessage = lastMessage;
        this.timestamp = t.getRelevantStringDate(timestamp);
        this.dialogID = dialogID;
    }

    public String getContactName() {
        return contactName;
    }

    public int getDialogID() {
        return dialogID;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
