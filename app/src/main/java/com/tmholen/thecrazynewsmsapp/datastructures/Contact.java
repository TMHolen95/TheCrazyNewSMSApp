package com.tmholen.thecrazynewsmsapp.datastructures;

import android.net.Uri;

import com.tmholen.thecrazynewsmsapp.R;
import com.tmholen.thecrazynewsmsapp.Tools;

import java.io.Serializable;

/**
 * Created by dogsh on 02-Sep-16.
 */

public class Contact implements Serializable {
    private int contactId;
    private String contactImageUriAsString;
    private String contactName;
    private String contactNumber;
    private Tools t = new Tools(){};

    public Contact(int contactId,String contactName, String contactNumber, String contactImageUri) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.contactImageUriAsString = contactImageUri;
    }

    public Contact(int contactId, String contactName, String contactNumber) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        contactImageUriAsString = t.ParseResourceToUriString(R.drawable.ic_person_missing_photo);
    }

    public Contact(int contactId,String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactNumber = "Unknown";
        contactImageUriAsString = t.ParseResourceToUriString(R.drawable.ic_person_missing_photo);
    }

    public String getContactImageUriAsString() {
        return contactImageUriAsString;
    }

    public void setContactImageUriAsString(Uri contactImageUriAsString) {
        this.contactImageUriAsString = contactImageUriAsString.toString();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
