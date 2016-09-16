package com.tmholen.thecrazynewsmsapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.tmholen.thecrazynewsmsapp.contacts.Contact;
import com.tmholen.thecrazynewsmsapp.contacts.ContactArrayAdapter;
import com.tmholen.thecrazynewsmsapp.messaging.TextMessage;

import java.util.ArrayList;
import java.util.Calendar;

public class MessageScreen extends AppCompatActivity {
    Toolbar toolbar;
    ListView entryList;
    private static final int PERMISSION_REQUEST_SMS = 2;
    MessageArrayAdapter messageArrayAdapter;
    PermissionHandler permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_screen);
        entryList = (ListView) findViewById(R.id.entryListView);

        UiComponents ui = new UiComponents(this, toolbar);
        ui.AddDefaultNavigationActivityElementsToScreen();

        permissions = new PermissionHandler(this, this);
        permissions.requestAccessToSms(PERMISSION_REQUEST_SMS);

        NavigateToMessageScreen();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        messageArrayAdapter.notifyDataSetChanged();
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

        messageArrayAdapter = new MessageArrayAdapter(this, featureNotImplemented);
        entryList.setAdapter(messageArrayAdapter);
        messageArrayAdapter.notifyDataSetChanged();
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
/*            case PERMISSION_REQUEST_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    NavigateToContactScreen();
                } else {
                    System.out.println("Permission denied");
                }
            }*/
            case PERMISSION_REQUEST_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    NavigateToMessageScreen();
                } else {
                    System.out.println("Permission denied");
                }
            }
        }
    }







}
