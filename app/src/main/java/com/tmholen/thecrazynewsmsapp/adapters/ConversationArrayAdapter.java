package com.tmholen.thecrazynewsmsapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmholen.thecrazynewsmsapp.asynctasks.LoadConversations;
import com.tmholen.thecrazynewsmsapp.R;
import com.tmholen.thecrazynewsmsapp.etc.Tools;

import java.util.List;

/**
 * Created by dogsh on 18-Sep-16.
 */

public class ConversationArrayAdapter extends ArrayAdapter {

    public ConversationArrayAdapter(Context context, List<LoadConversations.Conversation> conversations) {
        super(context, 0, conversations);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LoadConversations.Conversation conversation = (LoadConversations.Conversation) getItem(position);
        Tools t = new Tools(){};
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_message, parent, false);
        }

        ImageView contactImage = (ImageView) convertView.findViewById(R.id.messageRecipientImage);
        contactImage.setImageURI(t.ParseResourceToUri(R.drawable.ic_person_missing_photo));

        TextView contactName = (TextView) convertView.findViewById(R.id.messageRecipient);
        /*DataHandler.getInstance().getConversationById(conversation.getId())
        contactName.setText(conversation.getContactName());

        TextView lastMessage = (TextView) convertView.findViewById(R.id.message);
        lastMessage.setText(conversation.getLastMessage());

        TextView lastMessageTimestamp  = (TextView)  convertView.findViewById(R.id.messageTimestamp);
        lastMessageTimestamp.setText(conversation.getTimestamp());*/

        return convertView;
    }
}
