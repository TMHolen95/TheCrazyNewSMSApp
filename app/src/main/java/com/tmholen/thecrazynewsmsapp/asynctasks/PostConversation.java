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
 * Created by dogsh on 20-Oct-16.
 */

public class PostConversation extends AsyncTask<LoadConversations.Conversation, Void, LoadConversations.Conversation> {
    public interface Callback {
        void onPostExecute(LoadConversations.Conversation conversation, int responseCode);
    }

    private Callback callback;
    private URL url;
    private int responseCode = -1;

    public PostConversation(String url, Callback callback ) throws IOException {
        this.callback = callback;
        this.url = new URL(url);
    }

    @Override
    protected LoadConversations.Conversation doInBackground(LoadConversations.Conversation... conversations) {
        LoadConversations.Conversation result = null;

        HttpURLConnection connection = null;
        try {
            connection = createPostConnection(url);

            JsonWriter wr = new JsonWriter(new OutputStreamWriter(connection.getOutputStream(),"UTF-8"));
            Gson gson = new GsonBuilder().create();
            gson.toJson(conversations[0], LoadConversations.Conversation.class, wr);
            wr.close();

            responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                result = gson.fromJson(br, LoadConversations.Conversation.class);
                br.close();
            }
        } catch (IOException e) {
            Log.d("PostConversation", "doInBackground: ",e);
        }

        if(connection != null) {
            connection.disconnect();
        }

        return result;
    }

    @Override
    protected void onPostExecute(LoadConversations.Conversation conversation) {
        if(callback != null) {
            callback.onPostExecute(conversation,responseCode);
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
