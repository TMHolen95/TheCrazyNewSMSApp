package com.tmholen.thecrazynewsmsapp.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

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

public class LoadMessages extends AsyncTask<String,Long,List<LoadMessages.Message>> {

    public interface Callback {
        void update(List<Message> messages);
    }

    Callback callback;

    public LoadMessages(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected List<Message> doInBackground(String... path) {
        List<Message> result = new ArrayList<>();

        HttpURLConnection connection = null;
        try{
            URL url = new URL(path[0]);
            connection = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Message[] messages = gson.fromJson(br, Message[].class);
            result.addAll(Arrays.asList(messages));
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
    protected void onPostExecute(List<Message> messages) {
        callback.update(messages);
    }



    public static class Message{
        long id;

        Long conversationId;
        Long senderId;
        String text;
        Long timestamp;

        public Long getConversationId() {
            return conversationId;
        }

        public void setConversationId(Long conversationId) {
            this.conversationId = conversationId;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public Long getSenderId() {
            return senderId;
        }

        public void setSenderId(Long senderId) {
            this.senderId = senderId;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }

    }
    public static Comparator<LoadMessages.Message> messageComparator = new Comparator<Message>() {
        @Override
        public int compare(Message m1, Message m2) {
            return Long.compare(m1.getTimestamp(), m2.getTimestamp());
        }

    };
}
