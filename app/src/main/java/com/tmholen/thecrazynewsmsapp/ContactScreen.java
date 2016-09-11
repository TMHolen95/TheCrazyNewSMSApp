package com.tmholen.thecrazynewsmsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PERMISSION_REQUEST_WRITE_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_screen);

        //requestMissingPermissions();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(permissionGranted(Manifest.permission.WRITE_CONTACTS)){
            System.out.println("Permission Granted");
            displayContactData();

        }else{
            displayMissingPermissionData();
        }



/*
        ImageView profileImage = (ImageView) findViewById(R.id.myPicture);
//        Uri userProfileImageUri = dataExtractor.getUserInformation().getContactImageUri();
//        profileImage.setImageURI(userProfileImageUri);

        TextView userName  = (TextView) findViewById(R.id.userName);
        String userNameText = dataExtractor.getUserInformation().getContactName();

        userName.setText(userNameText);*/


    }

    private void displayContactData() {
        DataExtractor dataExtractor = new DataExtractor(getContentResolver()); //Data

        ListView contactList = (ListView) findViewById(R.id.contactListView);
        ContactArrayAdapter contactArrayAdapter = new ContactArrayAdapter(this, dataExtractor.getContacts());
        contactList.setAdapter(contactArrayAdapter);
    }

    private void displayMissingPermissionData() {
        ArrayList<Contact> error = new ArrayList<>();
        Tools t = new Tools(){};

        error.add(new Contact("Error", "You must grant this app access to your contacts",
                "Find app in phone settings if you clicked \"never show again\" in the permission dialog"
                , t.ParseResourceToUri(R.drawable.error)));

        ListView contactList = (ListView) findViewById(R.id.contactListView);
        ContactArrayAdapter contactArrayAdapter = new ContactArrayAdapter(this, error);
        contactList.setAdapter(contactArrayAdapter);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_delete) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_WRITE_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //displayContactData();
                } else {
                    System.out.println("Permission denied");
                }
            }
        }
    }

    private void requestMissingPermissions() {
        requestPermission(Manifest.permission.WRITE_CONTACTS, PERMISSION_REQUEST_WRITE_CONTACTS, "This app need access to your contacts to work. \nPlease grant the app access");
        //TODO add more permissions if needed.
    }

    private void requestPermission(String manifestPermissionValue, int permissionRequestInt, String message) {
        if (ContextCompat.checkSelfPermission(this, manifestPermissionValue) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, manifestPermissionValue)) {
                AlertDialog.Builder permissionExplaination = new AlertDialog.Builder(this);
                permissionExplaination.setMessage(message);
                permissionExplaination.setPositiveButton("Ok", null);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{manifestPermissionValue}, permissionRequestInt);
            }
        }
    }

    private boolean permissionGranted(String manifestPermissionValues) {
        boolean result = true;
        result = ContextCompat.checkSelfPermission(this, manifestPermissionValues) == PackageManager.PERMISSION_GRANTED;
        return result;
    }


}
