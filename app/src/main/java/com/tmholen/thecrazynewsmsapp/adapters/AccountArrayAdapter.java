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

import java.util.List;

/**
 * Created by dogsh on 15-Oct-16.
 */

public class AccountArrayAdapter extends ArrayAdapter<LoadAccounts.Account> {

    public AccountArrayAdapter(Context context, List<LoadAccounts.Account> accounts) {
        super(context, 0, accounts);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LoadAccounts.Account account = getItem(position);
        Tools t = new Tools(){};
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_contact, parent, false);
        }

        ImageView contactImage = (ImageView) convertView.findViewById(R.id.contactImage);
        contactImage.setImageURI(t.ParseMissingImageToUri());

        TextView contactName = (TextView) convertView.findViewById(R.id.contactName);
        contactName.setText(account.getName());

        TextView contactNumber = (TextView) convertView.findViewById(R.id.contactNumber);
        contactNumber.setText(account.getNumber());

        return convertView;
    }

    @Nullable
    @Override
    public LoadAccounts.Account getItem(int position) {
        return super.getItem(position);
    }
}
