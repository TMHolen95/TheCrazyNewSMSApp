package com.tmholen.thecrazynewsmsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dogsh on 02-Sep-16.
 */

public class ContactArrayAdapter extends ArrayAdapter<Contact> {
    public ContactArrayAdapter(Context context, List<Contact> contacts) {
        super(context, 0, contacts);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Contact contact = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_list_item, parent, false);
        }

        ImageView contactImage = (ImageView) convertView.findViewById(R.id.contactImage);
        contactImage.setImageURI(contact.getContactImageUri());

        TextView contactName = (TextView) convertView.findViewById(R.id.contactName);
        contactName.setText(contact.getContactName());

        TextView contactNumber = (TextView) convertView.findViewById(R.id.contactNumber);
        contactNumber.setText(contact.getContactNumber());

        return convertView;
    }
}
