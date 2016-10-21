package com.tmholen.thecrazynewsmsapp.asynctasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static android.R.attr.path;

/**
 * Created by dogsh on 15-Oct-16.
 */

public class LoadAccounts extends AsyncTask<String,Long,List<LoadAccounts.Account>> {

    public interface Callback {
        void update(List<Account> accounts);
    }

    Callback callback;

    public LoadAccounts(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected List<Account> doInBackground(String... path) {
        List<Account> result = new ArrayList<>();

        HttpURLConnection connection = null;
        try{
            URL url = new URL(path[0]);
            connection = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Account[] accounts = gson.fromJson(br, Account[].class);
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
    protected void onPostExecute(List<Account> account) {
        callback.update(account);
    }


    public static class Account{
        Long id;
        String name;
        String number;
        String image;
        String password;
        List<Long> conversationIds;

        public Account(String name, String number, String password, String image, List<Long> conversationIds) {
            this.name = name;
            this.number = number;
            this.password = password;
            this.image = image;
            this.conversationIds = conversationIds;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public void setPassword(String password) {
            this.password = password;
        }


        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getNumber() {
            return number;
        }

        public List<Long> getConversationIds() {
            return conversationIds;
        }

        public void setConversationIds(List<Long> conversationIds) {
            this.conversationIds = conversationIds;
        }
    }

    public static Comparator<Account> accountComparator = new Comparator<Account>() {
        @Override
        public int compare(Account a1, Account a2) {
            return a1.getName().compareTo(a2.getName());
        }
    };

}