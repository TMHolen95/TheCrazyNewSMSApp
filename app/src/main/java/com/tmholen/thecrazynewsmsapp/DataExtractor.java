package com.tmholen.thecrazynewsmsapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.tmholen.thecrazynewsmsapp.contacts.Contact;

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
    }

    public void obtainContactData(){
        cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
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
            cursor.moveToNext();
        }
        cursor.close();
    }

    public void obtainSmsData(){
        cursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){

        }
    }

    //TODO implement this functionality
    public void obtainUserData(){

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
