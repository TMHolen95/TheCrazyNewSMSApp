package com.tmholen.thecrazynewsmsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.tmholen.thecrazynewsmsapp.adapters.MessageArrayAdapter;
import com.tmholen.thecrazynewsmsapp.datastructures.TextMessage;

import java.util.ArrayList;

/**
 * Created by dogsh on 18-Sep-16.
 */

public class Messaging extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private MenuItem previousItem;
    private ArrayList<TextMessage> textMessage;
    private MessageArrayAdapter messageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging_content_screen);
        AddDefaultNavigationActivityElementsToScreen();

        textMessage = new ArrayList<>();
        messageAdapter = new MessageArrayAdapter(this, textMessage);

        final EditText editText = (EditText) findViewById(R.id.messageText);
        ListView messageList = (ListView) findViewById(R.id.messagingListView);
        messageList.setAdapter(messageAdapter);
        ImageButton sendButton  = (ImageButton) findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageAdapter.add(addTextMessage(editText.getText().toString()));
                messageAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
    }

    private TextMessage addTextMessage(String message){
        return new TextMessage("You", message, 1);
    }

    public void AddDefaultNavigationActivityElementsToScreen() {
        //EnableToolbar();
        /*EnableNavigationDrawer();
        FillNavigationDrawerMenu();*/
        /*ShowFabButton();
        EnableNavigationDrawer();
        FillNavigationDrawerMenu();*/
    }

    public void EnableToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    public void ShowFabButton() {
 /*       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//*Snackbar.make(view, "Select contact a contact to send a message", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                DisplayMessagingData();*//*
                Intent i = new Intent(getApplicationContext(), Messaging.class);
                startActivity(i);
            }
        });*/
    }



    public void EnableNavigationDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void FillNavigationDrawerMenu() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (previousItem != null) {
            previousItem.setChecked(false);
        } else {
            navigationView.getMenu().findItem(R.id.nav_message_screen).setChecked(false);
        }
        item.setChecked(true);


        if (id == R.id.nav_message_screen) {
            //DisplayMessageData();

        } else if (id == R.id.nav_contact_screen) {
            //CheckPermissionBeforeDisplayingContacts();

        } else if (id == R.id.nav_account_info) {
            Toast.makeText(this, "Functionality in development.", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_add) {
            Toast.makeText(this, "Functionality in development.", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_manage) {
            Toast.makeText(this, "Functionality in development.", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_delete) {
            Toast.makeText(this, "Functionality in development.", Toast.LENGTH_SHORT).show();
        }
        previousItem = item;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact_screen, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            return true;
        } else if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
