package com.tmholen.thecrazynewsmsapp;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dogsh on 02-Sep-16.
 */

public class ContactExtractor {
    private ContentResolver contentResolver;
    private Cursor cursor;

    public ContactExtractor(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
        cursor =  contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
    }

    public void printContacts(){
        int i = 0;
        String[] results1 = new String[0];
        while(!cursor.isAfterLast()){
            cursor.moveToNext();
            results1[i] = cursor.getString(1);
            System.out.println(results1[i]);
            i++;
        }

    }
}
