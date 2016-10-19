package com.tmholen.thecrazynewsmsapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmholen.thecrazynewsmsapp.R;
import com.tmholen.thecrazynewsmsapp.etc.Tools;
import com.tmholen.thecrazynewsmsapp.asynctasks.LoadAccounts;
import com.tmholen.thecrazynewsmsapp.asynctasks.LoadMessages;
import com.tmholen.thecrazynewsmsapp.data.DataHandler;

import java.util.List;

/**
 * Created by dogsh on 16-Sep-16.
 */

public class MessageArrayAdapter extends ArrayAdapter {
    public MessageArrayAdapter(Context context, List<LoadMessages.Message> messages) {
        super(context, 0, messages);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LoadMessages.Message message = getItem(position);
        Tools t = new Tools() {
        };
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_message, parent, false);
        }

        ImageView recipientImage = (ImageView) convertView.findViewById(R.id.messageRecipientImage);
        recipientImage.setImageURI(t.ParseMissingImageToUri());

        TextView recipientName = (TextView) convertView.findViewById(R.id.messageRecipient);
        LoadAccounts.Account recipient = DataHandler.getInstance().getAccountById(message.getSenderId());
        recipientName.setText(recipient.getName());

        TextView recipientMessage = (TextView) convertView.findViewById(R.id.message);
        recipientMessage.setText(message.getText());

        TextView recipientMessageTimestamp = (TextView) convertView.findViewById(R.id.messageTimestamp);

        /*Calendar timestamp = t.getCalendarFromTimestamp(message.getTimestamp());
        recipientMessageTimestamp.setText(t.getRelevantStringDate(timestamp));*/

        return convertView;
    }

    @Nullable
    @Override
    public LoadMessages.Message getItem(int position) {
        return (LoadMessages.Message) super.getItem(position);
    }
}
