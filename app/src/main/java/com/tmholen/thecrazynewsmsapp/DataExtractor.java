package com.tmholen.thecrazynewsmsapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by dogsh on 02-Sep-16.
 */

public class DataExtractor {
    private ContentResolver contentResolver;
    private ArrayList<Contact> contacts;
    private Contact userInformation;
    private Cursor cursor;


    public DataExtractor(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
        contacts = new ArrayList<>();
        obtainContactInformation();
        //obtainUserInformation();
    }

    public void obtainContactInformation(){
        cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String photoUriString = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
            if(photoUriString != null){
                Uri photoUri = Uri.parse(photoUriString);
                contacts.add(new Contact(contactId, name, number, photoUri));
            }else{
                contacts.add(new Contact(contactId, name, number));
            }
        }
        cursor.close();
    }


    //TODO implement this functionality
    public void obtainUserInformation(){

        //Account[] accounts = AccountManager.getA()
        cursor = contentResolver.query(ContactsContract.Profile.CONTENT_URI,null,null,null,null);
        while (cursor.moveToNext()) {
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Profile._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Profile.DISPLAY_NAME));
            String photoUriString = cursor.getString(cursor.getColumnIndex(ContactsContract.Profile.PHOTO_URI));
            if(photoUriString != null){
                Uri photoUri = Uri.parse(photoUriString);
                userInformation = new Contact(contactId, name, photoUri);
            }else{
                userInformation = new Contact(contactId, name);
            }
        }
        cursor.close();
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public Contact getUserInformation() {
        return userInformation;
    }
}
