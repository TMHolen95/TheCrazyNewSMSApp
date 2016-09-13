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

import com.tmholen.thecrazynewsmsapp.contacts.Contact;
import com.tmholen.thecrazynewsmsapp.contacts.ContactArrayAdapter;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    ListView entryList;
    private static final int PERMISSION_REQUEST_CONTACTS = 1;
    private static final int PERMISSION_REQUEST_SMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_screen);
        entryList = (ListView) findViewById(R.id.entryListView);
        EnableToolbar();
        ShowFabButton();
        EnableNavigationDrawer();
        FillNavigationDrawerMenu();

        NavigateToMessageScreen();
    }

    private void EnableToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void ShowFabButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }

    private void EnableNavigationDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void FillNavigationDrawerMenu() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void setProfileImage(){
        /*//TODO implement this along with the method in the data extractor class
        ImageView profileImage = (ImageView) findViewById(R.id.myPicture);
        Uri userProfileImageUri = dataExtractor.getUserInformation().getContactImageUri();
        profileImage.setImageURI(userProfileImageUri);

        TextView userName  = (TextView) findViewById(R.id.userName);
        String userNameText = dataExtractor.getUserInformation().getContactName();

        userName.setText(userNameText);*/
    }

    private void NavigateToMessageScreen(){
        requestAccessToContacts(PERMISSION_REQUEST_SMS);
        if(permissionGranted(Manifest.permission.READ_SMS)){
            DisplayMessageData();
        }else{
            DisplayMissingPermissionData();

        }
    }

    private void DisplayMessageData(){
        ArrayList<Contact> featureNotImplemented = new ArrayList<>();
        Tools t = new Tools(){};
        featureNotImplemented.add(new Contact("FeatureNotImplemented", "Feature not yet implemented",
                "Coming soon"
                , t.ParseResourceToUri(R.drawable.ic_error)));
        
        ContactArrayAdapter contactArrayAdapter = new ContactArrayAdapter(this, featureNotImplemented);
        entryList.setAdapter(contactArrayAdapter);
        contactArrayAdapter.notifyDataSetChanged();
    }

    private void NavigateToContactScreen(){
        if(permissionGranted(Manifest.permission.WRITE_CONTACTS)){
            DisplayContactData();
        }else{
            DisplayMissingPermissionData();
            requestAccessToContacts(PERMISSION_REQUEST_CONTACTS);
        }
    }

    private void DisplayContactData() {
        DataExtractor dataExtractor = new DataExtractor(getContentResolver()); //Data
        dataExtractor.obtainContactData();
        ContactArrayAdapter contactArrayAdapter = new ContactArrayAdapter(this, dataExtractor.getContacts());
        entryList.setAdapter(contactArrayAdapter);
        contactArrayAdapter.notifyDataSetChanged();

    }

    private void NavigateToAccountInfoScreen(){

    }


    private void DisplayAccountInfoData(){

    }


    private void DisplayMissingPermissionData() {
        ArrayList<Contact> error = new ArrayList<>();
        Tools t = new Tools(){};

        error.add(new Contact("Error", "You must grant this app access to your contacts",
                "Find app in phone settings if you clicked \"never show again\" in the permission dialog"
                , t.ParseResourceToUri(R.drawable.ic_error)));

        entryList = (ListView) findViewById(R.id.entryListView);
        ContactArrayAdapter contactArrayAdapter = new ContactArrayAdapter(this, error);
        entryList.setAdapter(contactArrayAdapter);
        contactArrayAdapter.notifyDataSetChanged();
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

        switch(id){
            case R.id.nav_message_screen:{
                NavigateToMessageScreen();
            }
            case R.id.nav_contact_screen:{
                NavigateToContactScreen();
            }
            case R.id.nav_account_info:{
                NavigateToAccountInfoScreen();
            }
            case R.id.nav_add:{

            }
            case R.id.nav_manage:{

            }
            case R.id.nav_delete:{

            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    NavigateToContactScreen();
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

    public void requestAccessToContacts(int permissionRequestCode) {
        requestPermission(Manifest.permission.WRITE_CONTACTS,
                permissionRequestCode,
                "This app need access to your contacts to work. \nPlease grant the app access");
    }

    public void requestAccessToSms(int permissionRequestCode) {
        requestPermission(Manifest.permission.SEND_SMS,
                permissionRequestCode,
                "This app need access to your contacts to work. \nPlease grant the app access");
    }

    public void requestPermission(String manifestPermissionValue, int permissionRequestInt, String message) {
            if (ContextCompat.checkSelfPermission(this, manifestPermissionValue) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, manifestPermissionValue)) {
                    AlertDialog.Builder permissionExplanation = new AlertDialog.Builder(this);
                    permissionExplanation.setMessage(message);
                    permissionExplanation.setPositiveButton("Ok", null);
                    permissionExplanation.create();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{manifestPermissionValue}, permissionRequestInt);
                }
            }

    }

    public boolean permissionGranted(String manifestPermissionValues) {
        boolean result = true;
        result = ContextCompat.checkSelfPermission(this, manifestPermissionValues) == PackageManager.PERMISSION_GRANTED;
        return result;
    }

}
