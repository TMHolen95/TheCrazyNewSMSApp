package com.tmholen.thecrazynewsmsapp;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tmholen.thecrazynewsmsapp.adapters.DialogArrayAdapter;
import com.tmholen.thecrazynewsmsapp.adapters.MessageArrayAdapter;
import com.tmholen.thecrazynewsmsapp.datastructures.Contact;
import com.tmholen.thecrazynewsmsapp.adapters.ContactArrayAdapter;
import com.tmholen.thecrazynewsmsapp.datastructures.Dialog;
import com.tmholen.thecrazynewsmsapp.datastructures.TextMessage;

import java.util.ArrayList;

import com.tmholen.thecrazynewsmsapp.database.Database;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ListView entryList;
    private static final int PERMISSION_REQUEST_CONTACTS = 1;
    private MessageArrayAdapter messageArrayAdapter;
    private PermissionHandler permissions;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private ContactArrayAdapter contactArrayAdapter;
    private DialogArrayAdapter dialogArrayAdapter;
    private MenuItem previousItem;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screens);
        entryList = (ListView) findViewById(R.id.entryListView);

        AddDefaultNavigationActivityElementsToScreen();
        permissions = new PermissionHandler(this, this);


        permissions.requestAccessToContacts(PERMISSION_REQUEST_CONTACTS);

        if(permissions.permissionGranted(Manifest.permission.WRITE_CONTACTS)){
            db = new Database(getApplicationContext(), null);
            db.onUpgrade(db.getWritableDatabase(), 1, 1);
            addTestDataToDB();
            DisplayMessageData();
        }else{
            DisplayMissingPermissionData();
        }

    }

    private void addTestDataToDB() {
        db.addUser("Tor-Martin Holen", "+47 913 67 954"); //Don't add more than one user, as of this time.
        String user = db.getUserName();

        db.ImportContactData(getContentResolver());
        String c1 = "Mr. Testingson";
        db.addDialog(new TextMessage(c1, "Hello", 1), c1);
        db.updateDialog(new TextMessage(user, "Well....", 1));
        db.updateDialog(new TextMessage(c1, "Goodbye, i don't have time for this", 1));

        String c2 = "Shady person";
        db.addDialog(new TextMessage(c2, "Candy is good", 2), c2);
        db.updateDialog(new TextMessage(user, "Who are you....?", 2));
        db.updateDialog(new TextMessage(c2, "Nobody, but there is free candy in my van", 2));

        String c3 = "Acquaintance";
        db.addDialog(new TextMessage(c3, "How is't going", 3), c3);
        db.updateDialog(new TextMessage(user, "Good?", 3));
        db.updateDialog(new TextMessage(c3, "That's good...", 3));

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (messageArrayAdapter != null) {
            messageArrayAdapter.notifyDataSetChanged();
        }
    }

    private void DisplayDialogData(){
        ArrayList<Dialog> dialogs = db.getDialogMessages();
        dialogArrayAdapter = new DialogArrayAdapter(this, dialogs);
        displayNewListViewFragment(dialogArrayAdapter);
    }

    private void DisplayContactData() {
        ArrayList<Contact> contacts = db.getContacts();
        contactArrayAdapter = new ContactArrayAdapter(this, contacts);

        displayNewListViewFragment(contactArrayAdapter);

    }

    private void DisplayMessageData() {

        ArrayList<TextMessage> textMessages;
        textMessages = db.getLastMessagesInDialogs();

        messageArrayAdapter = new MessageArrayAdapter(this, textMessages);
        displayNewListViewFragment(messageArrayAdapter);
    }

    private void displayNewListViewFragment(ArrayAdapter arrayAdapter) {
        ListViewFragment listViewFragment = (ListViewFragment) getSupportFragmentManager().findFragmentById(R.id.listViewFragment);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(listViewFragment);
        fragmentTransaction.commit();
        listViewFragment.setListViewAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

    private void DisplayMessagingData(){
        ArrayList<TextMessage> textMessages;
        textMessages = db.getLastMessagesInDialogs();
        messageArrayAdapter = new MessageArrayAdapter(this, textMessages);

        displayNewMessagingFragment(messageArrayAdapter);
    }

    private void displayNewMessagingFragment(ArrayAdapter arrayAdapter) {
        MessagingFragment messagingFragment = (MessagingFragment) getSupportFragmentManager().findFragmentById(R.id.messagingFragment);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(messagingFragment);
        fragmentTransaction.commit();
        messagingFragment.setListViewAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    db = new Database(getApplicationContext(), null);
                    db.onUpgrade(db.getWritableDatabase(), 1, 1);
                    addTestDataToDB();
                    DisplayMessageData();
                } else {
                    System.out.println("Permission denied");
                }
            }
        }
    }

    public void AddDefaultNavigationActivityElementsToScreen() {
        EnableToolbar();
        ShowFabButton();
        EnableNavigationDrawer();
        FillNavigationDrawerMenu();
    }

    public void EnableToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    public void ShowFabButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Select contact a contact to send a message", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                DisplayMessagingData();*/
                Intent i = new Intent(getApplicationContext(), Messaging.class);
                startActivity(i);
            }
        });
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
            DisplayMessageData();

        } else if (id == R.id.nav_contact_screen) {
            CheckPermissionBeforeDisplayingContacts();

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


    private void CheckPermissionBeforeDisplayingContacts() {
        permissions.requestAccessToContacts(PERMISSION_REQUEST_CONTACTS);
        if (permissions.permissionGranted(Manifest.permission.WRITE_CONTACTS)) {
            DisplayContactData();
        } else {
            DisplayMissingPermissionData();
            System.out.println("Missing permisssions...");
        }
    }

    private void DisplayMissingPermissionData() {
        ArrayList<Contact> error = new ArrayList<>();
        Tools t = new Tools() {
        };

        error.add(new Contact(1, "You must grant this app access to your contacts",
                "Find app in phone settings if you clicked \"never show again\" in the permission dialog"
                , t.ParseResourceToUriString(R.drawable.ic_error)));
        contactArrayAdapter = new ContactArrayAdapter(this, error);

        displayNewListViewFragment(contactArrayAdapter);
    }


}
