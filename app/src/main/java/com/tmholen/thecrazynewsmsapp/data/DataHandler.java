package com.tmholen.thecrazynewsmsapp.data;

import com.tmholen.thecrazynewsmsapp.asynctasks.LoadAccounts;
import com.tmholen.thecrazynewsmsapp.asynctasks.LoadConversations;
import com.tmholen.thecrazynewsmsapp.asynctasks.LoadMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by dogsh on 16-Oct-16.
 */

public final class DataHandler {
    private static volatile DataHandler instance = new DataHandler();

    List<LoadAccounts.Account> accounts;
    List<LoadMessages.Message> messages;
    List<LoadConversations.Conversation> conversations;
    LoadAccounts.Account myAccount;
    //String myIp = "158.38.193.21";
    String myIp = "192.168.2.4";

    private DataHandler() {
        this.accounts = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.conversations = new ArrayList<>();
    }

    public String getMyIp() {
        return myIp;
    }

    public static DataHandler getInstance(){
        return instance;
    }

    public List<LoadAccounts.Account> getAccounts() {
        return accounts;
    }

    public LoadAccounts.Account getAccountById(Long id){
        LoadAccounts.Account result = null;

        if(Objects.equals(id, myAccount.getId())){
            result = myAccount;
        }else{
            for (int i = 0; i < getAccounts().size(); i++) {
                LoadAccounts.Account search = getAccounts().get(i);
                if(Objects.equals(search.getId(), id)){
                    result = search;
                    break;
                }
            }
        }

        return result;
    }

    public void setAccounts(List<LoadAccounts.Account> accounts) {
        this.accounts = accounts;
    }

    public List<LoadMessages.Message> getMessages() {
        return messages;
    }

    public void setMessages(List<LoadMessages.Message> messages) {
        this.messages = messages;
    }

    public void addMessage(LoadMessages.Message message){
        messages.add(message);
    }

    public List<LoadConversations.Conversation> getConversations() {
        return conversations;
    }

    public LoadConversations.Conversation getConversationById(Long id){
        LoadConversations.Conversation result = null;
        for (int i = 0; i < getConversations().size(); i++) {
            LoadConversations.Conversation search = getConversations().get(i);
            if(Objects.equals(search.getId(), id)){
                result = search;
                break;
            }
        }

        return result;
    }

    public void setConversations(List<LoadConversations.Conversation> conversations) {
        this.conversations = conversations;
    }

    public void addConversation(LoadConversations.Conversation conversation) {
        conversations.add(conversation);
    }

    public void setMyAccount(LoadAccounts.Account account){
        myAccount = account;
    }

    public LoadAccounts.Account getMyAccount() {
        return myAccount;
    }

    /*    public void ImportContactData(ContentResolver contentResolver) {
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
    }*/


}
