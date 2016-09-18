package com.tmholen.thecrazynewsmsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tmholen.thecrazynewsmsapp.adapters.MessageArrayAdapter;
import com.tmholen.thecrazynewsmsapp.database.Database;
import com.tmholen.thecrazynewsmsapp.datastructures.Contact;
import com.tmholen.thecrazynewsmsapp.datastructures.TextMessage;

/**
 * Created by dogsh on 16-Sep-16.
 */

public class ListViewFragment extends Fragment {
    private ListView listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_content_screen, container, false);
        listView = (ListView) view.findViewById(R.id.entryListView);


        return view;
    }

    public ListView getListView() {
        return listView;
    }

    public void setListViewAdapter(ArrayAdapter a){
        listView.setAdapter(a);
    }
}
