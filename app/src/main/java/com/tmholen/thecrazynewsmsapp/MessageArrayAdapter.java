package com.tmholen.thecrazynewsmsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmholen.thecrazynewsmsapp.messaging.TextMessage;

import java.util.List;

/**
 * Created by dogsh on 16-Sep-16.
 */

public class MessageArrayAdapter extends ArrayAdapter {
    public MessageArrayAdapter(Context context, List<TextMessage> textMessages) {
        super(context, 0, textMessages);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextMessage message = (TextMessage) getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_list_item, parent, false);
        }

        ImageView recipientImage = (ImageView) convertView.findViewById(R.id.messageRecipientImage);
        recipientImage.setImageURI(message.getRecipientImage());

        TextView recipientName = (TextView) convertView.findViewById(R.id.messageRecipient);
        recipientName.setText(message.getRecipientName());

        TextView recipientMessage = (TextView) convertView.findViewById(R.id.message);
        recipientMessage.setText(message.getMessage());

        TextView recipientMessageTimestamp  = (TextView)  convertView.findViewById(R.id.messageTimestamp);
        recipientMessageTimestamp.setText(message.getTimestamp());

        return convertView;
    }
}
