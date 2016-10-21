package com.tmholen.thecrazynewsmsapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmholen.thecrazynewsmsapp.asynctasks.LoadAccounts;
import com.tmholen.thecrazynewsmsapp.asynctasks.LoadConversations;
import com.tmholen.thecrazynewsmsapp.R;
import com.tmholen.thecrazynewsmsapp.data.DataHandler;
import com.tmholen.thecrazynewsmsapp.etc.Tools;

import java.util.Calendar;
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
        Tools t = new Tools() {
        };
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_message, parent, false);
        }

        ImageView contactImage = (ImageView) convertView.findViewById(R.id.messageRecipientImage);
        contactImage.setImageURI(t.ParseResourceToUri(R.drawable.ic_person_missing_photo));

        TextView contactName = (TextView) convertView.findViewById(R.id.messageRecipient);


        assert conversation != null;
        Long myConvId = conversation.getId();
        List<LoadAccounts.Account> accounts = DataHandler.getInstance().getAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            try {
                if (accounts.get(i).getConversationIds().contains(myConvId)) {
                    if (!accounts.get(i).getName().equals(DataHandler.getInstance().getMyAccount().getName())) {
                        contactName.setText(accounts.get(i).getName());
                    }
                }
            } catch (NullPointerException ignored) {

            }
        }


        TextView lastMessage = (TextView) convertView.findViewById(R.id.message);
        lastMessage.setText(conversation.getLastMessage().getText());

        TextView lastMessageTimestamp = (TextView) convertView.findViewById(R.id.messageTimestamp);

        Calendar c = t.getCalendarFromTimestamp(conversation.getLastMessage().getTimestampSent());
        lastMessageTimestamp.setText(t.getRelevantStringDate(c));


        return convertView;
    }
}
