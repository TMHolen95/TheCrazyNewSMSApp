package com.tmholen.thecrazynewsmsapp;

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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.tmholen.thecrazynewsmsapp.contacts.ContactArrayAdapter;
import com.tmholen.thecrazynewsmsapp.messaging.TextMessage;

import java.util.ArrayList;
import java.util.Calendar;

public class MessageScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ListView entryList;
    private static final int PERMISSION_REQUEST_CONTACTS = 1;
    private static final int PERMISSION_REQUEST_SMS = 2;
    private MessageArrayAdapter messageArrayAdapter;
    private PermissionHandler permissions;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ListViewFragment contactFragment;
    private ListViewFragment messageFragment;
    private ContactArrayAdapter contactArrayAdapter;
    private MenuItem previousItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screens);
        entryList = (ListView) findViewById(R.id.entryListView);

        AddDefaultNavigationActivityElementsToScreen();

        permissions = new PermissionHandler(this, this);
        permissions.requestAccessToSms(PERMISSION_REQUEST_SMS);
        new Thread(new Runnable() {
            @Override
            public void run() {
                NavigateToMessageScreen();
            }
        }).start();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(messageArrayAdapter != null){
            messageArrayAdapter.notifyDataSetChanged();
        }
    }

    private void NavigateToMessageScreen(){
        //permissions.requestAccessToSms(PERMISSION_REQUEST_SMS);
        //    if(permissions.permissionGranted(Manifest.permission.SEND_SMS)){
            DisplayMessageData();
        //}else{
            //DisplayMissingPermissionData();
//            System.out.println("Missing permisssions...");
  //      }
    }

    private void DisplayMessageData(){
        ArrayList<TextMessage> featureNotImplemented = new ArrayList<>();
        Tools t = new Tools(){};
        featureNotImplemented.add(new TextMessage("Feature in development",
                "Work work...",
                t.ParseResourceToUri(R.drawable.ic_error),
                "1" ));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        featureNotImplemented.add(new TextMessage("Test Message",
                "Yesterday Test",
                cal,
                "2"
                ));
        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DATE,-100);
        featureNotImplemented.add(new TextMessage("Test Message",
                "Long ago Test",
                cal2,
                "3"
        ));


        messageArrayAdapter = new MessageArrayAdapter(this, featureNotImplemented);;
        displayNewListViewFragment(messageFragment, messageArrayAdapter);
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

        return true;
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
        }else if(id == R.id.action_settings){
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
            case PERMISSION_REQUEST_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    NavigateToMessageScreen();
                } else {
                    System.out.println("Permission denied");
                }
            }
        }
    }

    public void AddDefaultNavigationActivityElementsToScreen(){
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
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


    private void DisplayContactData() {
        DataExtractor dataExtractor = new DataExtractor(getContentResolver()); //Data
        dataExtractor.obtainContactData();
        contactArrayAdapter = new ContactArrayAdapter(this, dataExtractor.getContacts());

        displayNewListViewFragment(contactFragment, contactArrayAdapter);
        /*contactFragment = (ListViewFragment) getSupportFragmentManager().findFragmentById(R.id.listViewFragment);
        contactFragment.getListView().setAdapter(contactArrayAdapter);
        contactArrayAdapter.notifyDataSetChanged();*/

    }

    private void displayNewListViewFragment(ListViewFragment listViewFragment, ArrayAdapter arrayAdapter){
        listViewFragment = (ListViewFragment) getSupportFragmentManager().findFragmentById(R.id.listViewFragment);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(listViewFragment);
        fragmentTransaction.commit();
        listViewFragment.setListViewAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();




    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(previousItem != null){
            previousItem.setChecked(false);
        }
        item.setChecked(true);


        if(id == R.id.nav_message_screen){
            DisplayMessageData();

        }else if(id == R.id.nav_contact_screen){
            /*Intent i = new Intent(appCompatActivity, ContactFragment.class);
            appCompatActivity.startActivity(i);*/
            DisplayContactData();

        }else if(id == R.id.nav_account_info){
            Toast.makeText(this,"Functionality in development.",Toast.LENGTH_SHORT).show();

        }else if(id == R.id.nav_add){
            Toast.makeText(this,"Functionality in development.",Toast.LENGTH_SHORT).show();

        }else if(id == R.id.nav_manage){
            Toast.makeText(this,"Functionality in development.",Toast.LENGTH_SHORT).show();

        }else if(id == R.id.nav_delete){
            Toast.makeText(this,"Functionality in development.",Toast.LENGTH_SHORT).show();
        }
        previousItem = item;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


/*    private void CheckPermissionBeforeDisplayingContacts() {
        permissions.requestAccessToContacts(PERMISSION_REQUEST_CONTACTS);
        if (permissions.permissionGranted(Manifest.permission.WRITE_CONTACTS)) {

        } else {
            DisplayMissingPermissionData();
            System.out.println("Missing permisssions...");
        }
    }*/



/*    private void DisplayMissingPermissionData() {
        ArrayList<Contact> error = new ArrayList<>();
        Tools t = new Tools() {
        };

        error.add(new Contact("Error", "You must grant this app access to your contacts",
                "Find app in phone settings if you clicked \"never show again\" in the permission dialog"
                , t.ParseResourceToUri(R.drawable.ic_error)));

        entryList = (ListView) findViewById(R.id.entryListView);
        contactArrayAdapter = new ContactArrayAdapter(this, error);
        entryList.setAdapter(contactArrayAdapter);
        contactArrayAdapter.notifyDataSetChanged();
    }*/



}
