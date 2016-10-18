package com.tmholen.thecrazynewsmsapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ListView;
import android.widget.Toast;

import com.tmholen.thecrazynewsmsapp.etc.PermissionHandler;
import com.tmholen.thecrazynewsmsapp.R;
import com.tmholen.thecrazynewsmsapp.adapters.AccountArrayAdapter;
import com.tmholen.thecrazynewsmsapp.adapters.ConversationArrayAdapter;
import com.tmholen.thecrazynewsmsapp.asynctasks.LoadAccounts;
import com.tmholen.thecrazynewsmsapp.data.DownloadedDataHandler;

import java.util.Collections;
import java.util.List;


public class Conversations extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int PERMISSION_REQUEST_CONTACTS = 1;

    private ListView entryList;
    private ConversationArrayAdapter conversationArrayAdapter;

    private AccountArrayAdapter accountArrayAdapter;
    private PermissionHandler permissions;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private MenuItem previousItem;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screens);
        entryList = (ListView) findViewById(R.id.entryListView);

        AddDefaultNavigationActivityElementsToScreen();
        permissions = new PermissionHandler(this, this);

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        if(sharedPreferences.getLong("id", -1L) != -1L){
            new LoadAccounts(
                    new LoadAccounts.Callback() {
                        @Override
                        public void update(List<LoadAccounts.Account> accounts) {
                            Collections.sort(accounts, LoadAccounts.accountComparator);
                            DownloadedDataHandler.getInstance().setAccounts(accounts);
                            accountArrayAdapter = new AccountArrayAdapter(getApplicationContext(), DownloadedDataHandler.getInstance().getAccounts());
                            entryList.setAdapter(accountArrayAdapter);
                            accountArrayAdapter.notifyDataSetChanged();
                        }
                    }
            ).execute("http://192.168.2.4:8080/MessagingServer/service/chat/accounts");

        }else{
            Intent i = new Intent(getApplicationContext(), AccountRegistration.class);
            startActivity(i);
        }

/*        permissions.requestAccessToContacts(PERMISSION_REQUEST_CONTACTS);

        if(permissions.permissionGranted(Manifest.permission.WRITE_CONTACTS)){

        }else{
            DisplayMissingPermissionData();
        }*/

        /*new LoadConversations(
                new LoadConversations.Callback() {
                    @Override
                    public void update(List<LoadConversations.Conversation> conversations) {
                        *//*Collections.sort(conversations, LoadAccounts.accountComparator);*//*
                        DownloadedDataHandler.getInstance().setConversations(conversations);
                        accountArrayAdapter = new AccountArrayAdapter(getApplicationContext(), DownloadedDataHandler.getInstance().getAccounts());
                        entryList.setAdapter(accountArrayAdapter);
                        accountArrayAdapter.notifyDataSetChanged();
                    }
                }
        ).execute("http://192.168.2.4:8080/MessagingServer/service/chat/conversations");*/






    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
/*        if (messageArrayAdapter != null) {
            messageArrayAdapter.notifyDataSetChanged();
        }*/
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
        toolbar.setTitle("Conversations");
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
            //DisplayMessageData();

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
            //DisplayContactData();
        } else {
            System.out.println("Missing permisssions...");
        }
    }



/*    public void ImportContactData(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;

        Cursor c = this.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String photoUriString = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

            if (photoUriString != null) {

                String query = "INSERT INTO " + TABLE_CONTACT + " (" + COLUMN_CONTACT_NAME + ", " + COLUMN_CONTACT_NUMBER + ", " + COLUMN_CONTACT_IMAGE + ") " +
                        " VALUES ('" + name + "', '" + number + "', '" + photoUriString + "')";
                getWritableDatabase().execSQL(query);
            } else {
                String query = "INSERT INTO " + TABLE_CONTACT + " (" + COLUMN_CONTACT_NAME + ", " + COLUMN_CONTACT_NUMBER + ", " + COLUMN_CONTACT_IMAGE + ") " +
                        " VALUES ('" + name + "', '" + number + "', '" + t.ParseMissingImageToUriString() + "')";
                getWritableDatabase().execSQL(query);
            }
            c.moveToNext();
        }
        c.close();
    }*/

}
