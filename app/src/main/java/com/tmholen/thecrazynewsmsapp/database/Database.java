package com.tmholen.thecrazynewsmsapp.database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.tmholen.thecrazynewsmsapp.datastructures.Dialog;
import com.tmholen.thecrazynewsmsapp.Tools;
import com.tmholen.thecrazynewsmsapp.datastructures.Contact;
import com.tmholen.thecrazynewsmsapp.datastructures.TextMessage;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by dogsh on 17-Sep-16.
 */

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userData.db";


    public static final String TABLE_DIALOG = "dialog";
    public static final String COLUMN_DIALOG_ID = "_dialogID";
    public static final String COLUMN_DIALOG_CONTACT = "dialogContact";
    public static final String COLUMN_DIALOG_LAST_MESSAGE = "dialogLastMessage";
    public static final String COLUMN_DIALOG_TIMESTAMP = "dialogTimestamp";

    public static final String TABLE_MESSAGE = "message";
    public static final String COLUMN_MESSAGE_ID = "_messageID";
    public static final String COLUMN_MESSAGE_TEXT = "messageText";
    public static final String COLUMN_MESSAGE_CONTACT = "messageContact";
    public static final String COLUMN_MESSAGE_SENDER_NAME = "messageSenderName";
    public static final String COLUMN_MESSAGE_TIMESTAMP = "messageTimestamp";


    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_ID = "_userID";
    public static final String COLUMN_USER_NAME = "userName";
    public static final String COLUMN_USER_NUMBER = "userNumber";
    public static final String COLUMN_USER_IMAGE = "userImage";

    public static final String TABLE_CONTACT = "contact";
    public static final String COLUMN_CONTACT_ID = "contactID";
    public static final String COLUMN_CONTACT_NAME = "contactName";
    public static final String COLUMN_CONTACT_NUMBER = "contactNumber";
    public static final String COLUMN_CONTACT_IMAGE = "contactImage";


    private Tools t = new Tools() {
    };
    private ContentResolver contentResolver;

    public Database(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_DIALOG + " ( " +
                COLUMN_DIALOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DIALOG_LAST_MESSAGE + " VARCHAR(50), " +
                COLUMN_DIALOG_CONTACT + " VARCHAR(50), " +
                COLUMN_DIALOG_TIMESTAMP + " INTEGER )";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_MESSAGE + " ( " +
                COLUMN_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MESSAGE_TEXT + " VARCHAR(1000), " +
                COLUMN_MESSAGE_CONTACT + " VARCHAR(50), " +
                COLUMN_MESSAGE_SENDER_NAME + " VARCHAR(50), " +
                COLUMN_MESSAGE_TIMESTAMP + " INTEGER, " +
                COLUMN_DIALOG_ID + " INTEGER, " +
                COLUMN_CONTACT_ID + " INTEGER, " +
                COLUMN_USER_ID + " INTEGER, " +
                "FOREIGN KEY (" + COLUMN_DIALOG_ID +
                ") REFERENCES " + TABLE_DIALOG + "(" + COLUMN_DIALOG_ID + "), " +
                "FOREIGN KEY (" + COLUMN_CONTACT_ID +
                ") REFERENCES " + TABLE_CONTACT + "(" + COLUMN_CONTACT_ID + "), " +
                "FOREIGN KEY (" + COLUMN_USER_ID +
                ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "))";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_USER + " ( " +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " VARCHAR(50), " +
                COLUMN_USER_NUMBER + " VARCHAR(20), " +
                COLUMN_USER_IMAGE + " VARCHAR(250) )";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_CONTACT + " ( " +
                COLUMN_CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CONTACT_NAME + " VARCHAR(50), " +
                COLUMN_CONTACT_NUMBER + " VARCHAR(20), " +
                COLUMN_CONTACT_IMAGE + " VARCHAR(250) )";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIALOG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        onCreate(db);
    }

    public void ImportContactData(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;

        Cursor c = this.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String photoUriString = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

            if (photoUriString != null) {

                String query = "INSERT INTO " + TABLE_CONTACT + " (" + COLUMN_CONTACT_NAME + ", " + COLUMN_CONTACT_NUMBER + ", " + COLUMN_CONTACT_IMAGE + ") " +
                        " VALUES ('" + name + "', '" + number + "', '" + photoUriString + "')";
                getWritableDatabase().execSQL(query);
            } else {
                String query = "INSERT INTO " + TABLE_CONTACT + " (" + COLUMN_CONTACT_NAME + ", " + COLUMN_CONTACT_NUMBER + ", " + COLUMN_CONTACT_IMAGE + ") " +
                        " VALUES ('" + name + "', '" + number + "', '" + t.ParseMissingImageToUriString() + "')";
                getWritableDatabase().execSQL(query);
            }
            c.moveToNext();
        }
        c.close();
    }

    public ArrayList<Contact> getContacts() {
        ArrayList<Contact> result = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_CONTACT_ID + ", " + COLUMN_CONTACT_NAME + ", " + COLUMN_CONTACT_NUMBER + ", " + COLUMN_CONTACT_IMAGE + " FROM " + TABLE_CONTACT;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int contactId = c.getInt(c.getColumnIndex(COLUMN_CONTACT_ID));
            String contactName = c.getString(c.getColumnIndex(COLUMN_CONTACT_NAME));
            String contactNumber = c.getString(c.getColumnIndex(COLUMN_CONTACT_NUMBER));
            String contactImage = c.getString(c.getColumnIndex(COLUMN_CONTACT_IMAGE));
            result.add(new Contact(contactId, contactName, contactNumber, contactImage));
            c.moveToNext();
        }
        c.close();

        return result;
    }

    public Contact getContact(int contactID) {
        Contact result;

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_CONTACT_ID + ", " + COLUMN_CONTACT_NAME + ", " + COLUMN_CONTACT_NUMBER + ", " + COLUMN_CONTACT_IMAGE + " FROM " + TABLE_CONTACT +
                " WHERE " + COLUMN_CONTACT_ID + " = " + contactID;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        int contactId = c.getInt(c.getColumnIndex(COLUMN_CONTACT_ID));
        String contactName = c.getString(c.getColumnIndex(COLUMN_CONTACT_NAME));
        String contactNumber = c.getString(c.getColumnIndex(COLUMN_CONTACT_NUMBER));
        String contactImage = c.getString(c.getColumnIndex(COLUMN_CONTACT_IMAGE));
        result = new Contact(contactId, contactName, contactNumber, contactImage);
        c.moveToNext();

        c.close();

        return result;
    }


    public void addUser(String userName, String userNumber) {
        String query = "INSERT INTO " + TABLE_USER + " ( " + COLUMN_USER_NAME + ", " + COLUMN_USER_NUMBER + " ) " +
                " VALUES (' " + userName + "', '" + userNumber + "' )";
        getWritableDatabase().execSQL(query);
    }

    public String getUserName() {
        String result = "";
        String query = "SELECT " + COLUMN_USER_NAME + " FROM " + TABLE_USER;

        Cursor c = getReadableDatabase().rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            result = c.getString(c.getColumnIndex(COLUMN_USER_NAME));
            break;
        }

        c.close();
        return result;
    }

    public void addDialog(TextMessage textMessage, String contactName) {
        addMessage(textMessage);

        String name = t.getValidatedTextForDatabase(contactName);
        String lastMessage = t.getValidatedTextForDatabase(textMessage.getMessage());
        long currentTime = System.currentTimeMillis();
        int messageThreadId = textMessage.getDialogID();

        String query = "INSERT INTO " + TABLE_DIALOG + " (" + COLUMN_DIALOG_LAST_MESSAGE + ", " + COLUMN_DIALOG_CONTACT + ", " + COLUMN_DIALOG_TIMESTAMP + ", " + COLUMN_DIALOG_ID + ") " +
                " VALUES (" + lastMessage + ", " + name + ", " + currentTime + ", " + messageThreadId + ")";
        getWritableDatabase().execSQL(query);
    }

    public void updateDialog(TextMessage textMessage) {
        addMessage(textMessage);

        String name = t.getValidatedTextForDatabase(textMessage.getSender());
        String lastMessage = t.getValidatedTextForDatabase(textMessage.getMessage());
        long currentTime = System.currentTimeMillis();
        int dialogID = textMessage.getDialogID();

        String query = "UPDATE " + TABLE_DIALOG + " SET " + COLUMN_DIALOG_LAST_MESSAGE + " = " + lastMessage + ", " + COLUMN_DIALOG_TIMESTAMP + " = " + currentTime +
                " WHERE " + COLUMN_DIALOG_ID + " = " + dialogID;
        getWritableDatabase().execSQL(query);
    }

    public void addMessage(TextMessage textMessage) {
        String name = t.getValidatedTextForDatabase(textMessage.getSender());
        String message = t.getValidatedTextForDatabase(textMessage.getMessage());
        long currentTime = System.currentTimeMillis();
        int dialogID = textMessage.getDialogID();

        String query = "INSERT INTO " + TABLE_MESSAGE + " (" + COLUMN_MESSAGE_TEXT + ", " + COLUMN_MESSAGE_SENDER_NAME + ", " + COLUMN_MESSAGE_TIMESTAMP + ", " + COLUMN_DIALOG_ID + ") " +
                " VALUES (" + message + ", " + name + ", " + currentTime + ", " + dialogID + ")";
        getWritableDatabase().execSQL(query);
    }

    public ArrayList<TextMessage> getLastMessagesInDialogs() {
        ArrayList<TextMessage> result = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_DIALOG_ID + ", " + COLUMN_MESSAGE_TEXT + ", " + COLUMN_MESSAGE_SENDER_NAME + ", " + COLUMN_MESSAGE_TIMESTAMP + " " + " FROM " + TABLE_MESSAGE
                + " GROUP BY " + COLUMN_DIALOG_ID;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int messageThreadId = c.getInt(c.getColumnIndex(COLUMN_DIALOG_ID));
            String messageText = c.getString(c.getColumnIndex(COLUMN_MESSAGE_TEXT));
            String messageSender = c.getString(c.getColumnIndex(COLUMN_MESSAGE_SENDER_NAME));
            long messageTimestamp = c.getLong(c.getColumnIndex(COLUMN_MESSAGE_TIMESTAMP));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(messageTimestamp);

            result.add(new TextMessage(messageSender, messageText, calendar, messageThreadId));
            c.moveToNext();
        }
        c.close();

        return result;
    }

    public ArrayList<TextMessage> getDialogMessage(int dialogID) {
        ArrayList<TextMessage> result = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_DIALOG_ID + ", " + COLUMN_MESSAGE_TEXT + ", " + COLUMN_MESSAGE_SENDER_NAME + ", " + COLUMN_MESSAGE_TIMESTAMP + " " + " FROM " + TABLE_MESSAGE
                + " WHERE " + COLUMN_DIALOG_ID + " = " + dialogID;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int messageThreadId = c.getInt(c.getColumnIndex(COLUMN_DIALOG_ID));
            String messageText = c.getString(c.getColumnIndex(COLUMN_MESSAGE_TEXT));
            String messageSender = c.getString(c.getColumnIndex(COLUMN_MESSAGE_SENDER_NAME));
            long messageTimestamp = c.getLong(c.getColumnIndex(COLUMN_MESSAGE_TIMESTAMP));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(messageTimestamp);

            result.add(new TextMessage(messageSender, messageText, calendar, messageThreadId));
            c.moveToNext();
        }
        c.close();

        return result;
    }

    public ArrayList<Dialog> getDialogMessages() {
        ArrayList<Dialog> result = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_DIALOG_ID + ", " + COLUMN_DIALOG_LAST_MESSAGE + ", " + COLUMN_DIALOG_CONTACT + ", " + COLUMN_DIALOG_TIMESTAMP + " " + " FROM " + TABLE_DIALOG;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int dialogID = c.getInt(c.getColumnIndex(COLUMN_DIALOG_ID));
            String lastMessageText = c.getString(c.getColumnIndex(COLUMN_DIALOG_LAST_MESSAGE));
            String contact = c.getString(c.getColumnIndex(COLUMN_DIALOG_CONTACT));
            long lastMessageTimestamp = c.getLong(c.getColumnIndex(COLUMN_DIALOG_TIMESTAMP));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(lastMessageTimestamp);

            result.add(new Dialog(contact, lastMessageText, calendar, dialogID));
            c.moveToNext();
        }
        c.close();

        return result;
    }

    public ArrayList<TextMessage> searchMessages(String search){
        ArrayList<TextMessage> result = new ArrayList<>();

        String query = "SELECT " + COLUMN_DIALOG_ID + ", " + COLUMN_MESSAGE_TEXT + ", " + COLUMN_MESSAGE_SENDER_NAME + ", " + COLUMN_MESSAGE_TIMESTAMP + " " + " FROM " + TABLE_MESSAGE
                + " WHERE " + COLUMN_MESSAGE_TEXT + " LIKE " + t.getValidatedTextForDatabase(search);

        Cursor c = getReadableDatabase().rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int messageThreadId = c.getInt(c.getColumnIndex(COLUMN_DIALOG_ID));
            String messageText = c.getString(c.getColumnIndex(COLUMN_MESSAGE_TEXT));
            String messageSender = c.getString(c.getColumnIndex(COLUMN_MESSAGE_SENDER_NAME));
            long messageTimestamp = c.getLong(c.getColumnIndex(COLUMN_MESSAGE_TIMESTAMP));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(messageTimestamp);

            result.add(new TextMessage(messageSender, messageText, calendar, messageThreadId));
            c.moveToNext();
        }
        c.close();

        return result;
    }
}
