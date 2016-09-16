package com.tmholen.thecrazynewsmsapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.tmholen.thecrazynewsmsapp.contacts.Contact;
import com.tmholen.thecrazynewsmsapp.contacts.ContactArrayAdapter;

import java.util.ArrayList;

/**
 * Created by dogsh on 16-Sep-16.
 */

public class ContactScreen extends AppCompatActivity {
    Toolbar toolbar;
    ListView entryList;
    ContactArrayAdapter contactArrayAdapter;
    PermissionHandler permissions;
    private static final int PERMISSION_REQUEST_CONTACTS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_screen);
        entryList = (ListView) findViewById(R.id.entryListView);


        UiComponents ui = new UiComponents(this, toolbar);
        ui.AddDefaultNavigationActivityElementsToScreen();

        permissions = new PermissionHandler(this,this);

        CheckPermissionBeforeDisplayingContacts();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        contactArrayAdapter.notifyDataSetChanged();
    }

    /*
    private void NavigateToContactScreen(){
        if(permissionGranted(Manifest.permission.WRITE_CONTACTS)){
            DisplayContactData();
        }else{
            DisplayMissingPermissionData();
            requestAccessToContacts(PERMISSION_REQUEST_CONTACTS);
        }
    }
*/

    private void CheckPermissionBeforeDisplayingContacts() {
        permissions.requestAccessToContacts(PERMISSION_REQUEST_CONTACTS);
        if (permissions.permissionGranted(Manifest.permission.WRITE_CONTACTS)) {
            DisplayContactData();
        } else {
            DisplayMissingPermissionData();
            System.out.println("Missing permisssions...");
        }
    }

    private void DisplayContactData() {
        DataExtractor dataExtractor = new DataExtractor(getContentResolver()); //Data
        dataExtractor.obtainContactData();
        contactArrayAdapter = new ContactArrayAdapter(this, dataExtractor.getContacts());
        entryList.setAdapter(contactArrayAdapter);
        contactArrayAdapter.notifyDataSetChanged();
    }

    private void DisplayMissingPermissionData() {
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CheckPermissionBeforeDisplayingContacts();
                } else {
                    System.out.println("Permission denied");
                }
            }

        }
    }
}
