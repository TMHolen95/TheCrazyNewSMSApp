package com.tmholen.thecrazynewsmsapp.asynctasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by dogsh on 15-Oct-16.
 */

public class LoadConversations extends AsyncTask<String,Long,List<LoadConversations.Conversation>> {
    public interface Callback {
        void update(List<Conversation> conversations);
    }

    Callback callback;

    public LoadConversations(LoadConversations.Callback callback) {
        this.callback = callback;
    }

    @Override
    protected List<Conversation> doInBackground(String... path) {
        List<Conversation> result = new ArrayList<>();

        HttpURLConnection connection = null;
        try{
            URL url = new URL(path[0]);
            connection = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Conversation[] accounts = gson.fromJson(br, Conversation[].class);
            result.addAll(Arrays.asList(accounts));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }

        return result;

    }


    @Override
    protected void onPostExecute(List<Conversation> conversations) {
        callback.update(conversations);
    }


    public static class Conversation{
        Long id;
        List<LoadMessages.Message> messages;
        List<LoadAccounts.Account> account;
        Long timestamp;

        public Long getId() {
            return id;
        }
    }

/*    public static Comparator<Conversation> conversationComparator = new Comparator<Conversation>() {
        @Override
        public int compare(LoadAccounts.Account a1, LoadAccounts.Account a2) {
            return a1.getName().compareTo(a2.getName());
        }
    };*/
}
