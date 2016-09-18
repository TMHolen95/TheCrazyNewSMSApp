package com.tmholen.thecrazynewsmsapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmholen.thecrazynewsmsapp.datastructures.Dialog;
import com.tmholen.thecrazynewsmsapp.R;
import com.tmholen.thecrazynewsmsapp.Tools;

import java.util.List;

/**
 * Created by dogsh on 18-Sep-16.
 */

public class DialogArrayAdapter extends ArrayAdapter {

    public DialogArrayAdapter(Context context, List<Dialog> dialogs) {
        super(context, 0, dialogs);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Dialog dialog = (Dialog) getItem(position);
        Tools t = new Tools(){};
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_message, parent, false);
        }

        ImageView contactImage = (ImageView) convertView.findViewById(R.id.messageRecipientImage);
        contactImage.setImageURI(t.ParseResourceToUri(R.drawable.ic_person_missing_photo));

        TextView contactName = (TextView) convertView.findViewById(R.id.messageRecipient);
        contactName.setText(dialog.getContactName());

        TextView lastMessage = (TextView) convertView.findViewById(R.id.message);
        lastMessage.setText(dialog.getLastMessage());

        TextView lastMessageTimestamp  = (TextView)  convertView.findViewById(R.id.messageTimestamp);
        lastMessageTimestamp.setText(dialog.getTimestamp());

        return convertView;
    }
}
