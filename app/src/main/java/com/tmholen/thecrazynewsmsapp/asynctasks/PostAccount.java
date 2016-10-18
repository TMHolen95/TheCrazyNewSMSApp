package com.tmholen.thecrazynewsmsapp.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dogsh on 17-Oct-16.
 */

public class PostAccount extends AsyncTask<LoadAccounts.Account, Void, LoadAccounts.Account> {
    public interface Callback {
        void onPostExecute(LoadAccounts.Account account, int responseCode);
    }

    private Callback callback;
    private URL url;
    private int responseCode = -1;

    public PostAccount(String url, Callback callback ) throws IOException {
        this.callback = callback;
        this.url = new URL(url);
    }


    @Override
    protected LoadAccounts.Account doInBackground(LoadAccounts.Account... accounts) {
        LoadAccounts.Account result = null;

        HttpURLConnection connection = null;
        try {
            connection = createPostConnection(url);

            JsonWriter wr = new JsonWriter(new OutputStreamWriter(connection.getOutputStream(),"UTF-8"));
            Gson gson = new GsonBuilder().create();
            gson.toJson(accounts[0], LoadAccounts.Account.class, wr);
            wr.close();

            responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                result = gson.fromJson(br,LoadAccounts.Account.class);
                br.close();
            }
        } catch (IOException e) {
            Log.d("PostMessage", "doInBackground: ",e);
        }

        if(connection != null) {
            connection.disconnect();
        }

        return result;
    }

    @Override
    protected void onPostExecute(LoadAccounts.Account account) {
        if(callback != null) {
            callback.onPostExecute(account,responseCode);
        }
    }

    private static HttpURLConnection createPostConnection(URL url) throws IOException {
        HttpURLConnection result = (HttpURLConnection) url.openConnection();

        result.setDoOutput(true);
        result.setDoInput(true);
        result.setRequestProperty("Content-Type", "application/json");
        result.setRequestProperty("Accept", "application/json");
        result.setRequestMethod("POST");

        return result;
    }
}

