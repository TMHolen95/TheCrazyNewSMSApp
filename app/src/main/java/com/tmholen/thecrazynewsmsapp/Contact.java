package com.tmholen.thecrazynewsmsapp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dogsh on 02-Sep-16.
 */

public class Contact implements Serializable {
    private int contactImage;
    private String contactName;
    private String contactNumber;
    private Date contactBirthday;

    public Contact(String contactName, String contactNumber) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.contactImage = R.drawable.my_profile_picture;
    }

    public Contact(String contactName, String contactNumber, int contactImage, Date contactBirthday) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.contactImage = contactImage;
        this.contactBirthday = contactBirthday;
    }

    public Date getContactBirthday() {
        return contactBirthday;
    }

    public void setContactBirthday(Date contactBirthday) {
        this.contactBirthday = contactBirthday;
    }

    public int getContactImage() {
        return contactImage;
    }

    public void setContactImage(int contactImage) {
        this.contactImage = contactImage;
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
