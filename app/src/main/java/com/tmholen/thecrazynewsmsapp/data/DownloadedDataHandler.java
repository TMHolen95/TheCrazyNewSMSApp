package com.tmholen.thecrazynewsmsapp.data;

import com.tmholen.thecrazynewsmsapp.asynctasks.LoadAccounts;
import com.tmholen.thecrazynewsmsapp.asynctasks.LoadConversations;
import com.tmholen.thecrazynewsmsapp.asynctasks.LoadMessages;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dogsh on 16-Oct-16.
 */

public final class DownloadedDataHandler {
    private static volatile DownloadedDataHandler instance = new DownloadedDataHandler();

    List<LoadAccounts.Account> accounts;
    List<LoadMessages.Message> messages;
    List<LoadConversations.Conversation> conversations;

    private DownloadedDataHandler() {
        this.accounts = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.conversations = new ArrayList<>();
    }

    public static DownloadedDataHandler getInstance(){
        return instance;
    }

    public List<LoadAccounts.Account> getAccounts() {
        return accounts;
    }

    public LoadAccounts.Account getAccountById(Long id){
        LoadAccounts.Account result = null;
        for (int i = 0; i < getAccounts().size(); i++) {
            LoadAccounts.Account search = getAccounts().get(i);
            if(search.getId() == id){
                result = search;
                break;
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

    public List<LoadConversations.Conversation> getConversations() {
        return conversations;
    }

    public LoadConversations.Conversation getConversationById(Long id){
        LoadConversations.Conversation result = null;
        for (int i = 0; i < getConversations().size(); i++) {
            LoadConversations.Conversation search = getConversations().get(i);
            if(search.getId() == id){
                result = search;
                break;
            }
        }

        return result;
    }

    public void setConversations(List<LoadConversations.Conversation> conversations) {
        this.conversations = conversations;
    }
}
