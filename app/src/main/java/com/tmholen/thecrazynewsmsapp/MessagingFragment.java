package com.tmholen.thecrazynewsmsapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by dogsh on 18-Sep-16.
 */

public class MessagingFragment extends Fragment {
    private  ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messaging_content_screen, container, false);
        listView = (ListView) view.findViewById(R.id.messagingListView);
        return view;
    }

    public ListView getListView() {
        return listView;
    }

    public void setListViewAdapter(ArrayAdapter a){
        listView.setAdapter(a);
    }
}
