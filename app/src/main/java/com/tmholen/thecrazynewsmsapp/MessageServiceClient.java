package com.tmholen.thecrazynewsmsapp;

import com.tmholen.thecrazynewsmsapp.messaging.TextMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dogsh on 16-Sep-16.
 */

public class MessageServiceClient {
    private static MessageServiceClient INSTANCE;
    List<List<TextMessage>> data;

    private MessageServiceClient(){
        data = new ArrayList<>();
    }

    public List<TextMessage> getMessages(int index){
        return data.get(index);
    }

    public static synchronized MessageServiceClient getInstance(){
        if(INSTANCE == null){
            INSTANCE = new MessageServiceClient();
        }

        return INSTANCE;
    }
}
