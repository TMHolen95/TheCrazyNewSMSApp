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

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getNumber() {
            return number;
        }
    }

    public static Comparator<Account> accountComparator = new Comparator<Account>() {
        @Override
        public int compare(Account a1, Account a2) {
            return a1.getName().compareTo(a2.getName());
        }
    };

}


/*

public class LoadUsers extends AsyncTask<String,Long,List<LoadUsers.User>> {
    static SimpleDateFormat DF      = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    static SimpleDateFormat DFSHORT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");


    interface Callback {
        void update(List<User> usrs);
    }

    Callback callback;

    public LoadUsers(Callback callback) {
        this.callback =callback;
    }

    @Override
    protected List<User> doInBackground(String... path) {
        List<User> result = new ArrayList<>();

        HttpURLConnection con = null;
        try {
            URL url = new URL(path[0]);
            con = (HttpURLConnection)url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                @Override
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    String value = json.getAsString();
                    try {
                        return value.length() > 25 ? DF.parse(value) : DFSHORT.parse(value);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return null;
                }
            });
            Gson gson = builder.create();
            User[] users = gson.fromJson(br,User[].class);
            result.addAll(Arrays.asList(users));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(con != null) {con.disconnect();}
        }

        return result;
    }


    @Override
    protected void onPostExecute(List<User> users) {
        callback.update(users);
    }*/
