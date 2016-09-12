package com.tmholen.thecrazynewsmsapp.contacts;

import android.net.Uri;

import com.tmholen.thecrazynewsmsapp.R;
import com.tmholen.thecrazynewsmsapp.Tools;

import java.io.Serializable;

/**
 * Created by dogsh on 02-Sep-16.
 */

public class Contact implements Serializable {
    private String contactId;
    private Uri contactImageUri;
    private String contactName;
    private String contactNumber;
    private Tools t = new Tools(){};

    public Contact(String contactId,String contactName, String contactNumber, Uri contactImageUri) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.contactImageUri = contactImageUri;
    }

    public Contact(String contactId,String contactName, Uri contactImageUri) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactNumber = "Unknown";
        this.contactImageUri = contactImageUri;
    }

    public Contact(String contactId,String contactName, String contactNumber) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        contactImageUri = t.ParseResourceToUri(R.drawable.ic_person_missing_photo);
    }

    public Contact(String contactId,String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactNumber = "Unknown";
        contactImageUri = t.ParseResourceToUri(R.drawable.ic_person_missing_photo);
    }


    public Uri getContactImageUri() {
        return contactImageUri;
    }

    public void setContactImageUri(Uri contactImageUri) {
        this.contactImageUri = contactImageUri;
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
