package com.tmholen.thecrazynewsmsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tmholen.thecrazynewsmsapp.R;
import com.tmholen.thecrazynewsmsapp.adapters.MessageArrayAdapter;
import com.tmholen.thecrazynewsmsapp.asynctasks.LoadAccounts;
import com.tmholen.thecrazynewsmsapp.asynctasks.LoadMessages;
import com.tmholen.thecrazynewsmsapp.asynctasks.PostAccount;
import com.tmholen.thecrazynewsmsapp.asynctasks.PostMessage;
import com.tmholen.thecrazynewsmsapp.data.DataHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.List;

/**
 * Created by dogsh on 18-Sep-16.
 */

public class Messaging extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ListView entryList;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private MenuItem previousItem;
    private MessageArrayAdapter messageArrayAdapter;
    private EditText fieldInput;
    private Button sendButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screens);
        AddDefaultNavigationActivityElementsToScreen();
        entryList = (ListView) findViewById(R.id.mainListView);
        fieldInput = (EditText) findViewById(R.id.mainFieldInput);
        fieldInput.setVisibility(View.VISIBLE);


        Intent intentFromBefore = getIntent();
        Bundle bundle = intentFromBefore.getExtras();
        final Long conversationId = (Long) bundle.get("conversationId");

        new LoadMessages(
                new LoadMessages.Callback() {
                    @Override
                    public void update(List<LoadMessages.Message> messages) {
                        Collections.sort(messages, LoadMessages.messageComparator);
                        DataHandler.getInstance().setMessages(messages);
                        messageArrayAdapter = new MessageArrayAdapter(getApplicationContext(), DataHandler.getInstance().getMessages());
                        entryList.setAdapter(messageArrayAdapter);
                        messageArrayAdapter.notifyDataSetChanged();
                    }
                }
        ).execute("http://" + DataHandler.getInstance().getMyIp()+ ":8080/MessagingServer/service/chat/messages/byConId/"
        + conversationId);


        sendButton = (Button) findViewById(R.id.mainSendButton);
        sendButton.setVisibility(View.VISIBLE);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!fieldInput.getText().toString().equals("")){
                    try {
                        LoadMessages.Message myMessage = new LoadMessages.Message(conversationId,
                                DataHandler.getInstance().getMyAccount().getId(),
                                fieldInput.getText().toString());
                        fieldInput.setText("");

                        new PostMessage(
                                "http://"+ DataHandler.getInstance().getMyIp() +":8080/MessagingServer/service/chat/messages/create",
                                new PostMessage.Callback() {
                                    @Override
                                    public void onPostExecute(LoadMessages.Message message, int responseCode) {
                                        if (responseCode == HttpURLConnection.HTTP_OK) {
                                            DataHandler.getInstance().addMessage(message);
                                            messageArrayAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                        ).execute(myMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }


    public void AddDefaultNavigationActivityElementsToScreen() {
        EnableToolbar();
        EnableNavigationDrawer();
        FillNavigationDrawerMenu();
        ShowFabButton();
        EnableNavigationDrawer();
        FillNavigationDrawerMenu();
    }

    public void EnableToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Chat with:" /*+ DataHandler.getInstance().getAccountById()*/);
    }


    public void ShowFabButton() {
        findViewById(R.id.fab).setVisibility(View.INVISIBLE);
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
            navigationView.getMenu().findItem(R.id.nav_conversation_screen).setChecked(false);
        }
        item.setChecked(true);


        if (id == R.id.nav_conversation_screen) {
            //DisplayMessageData();

        } else if (id == R.id.nav_contact_screen) {
            Toast.makeText(this, "Functionality in development.", Toast.LENGTH_SHORT).show();

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
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

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
        } else if (id == R.id.action_logout) {
            GoToLogin();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void GoToLogin(){
        Intent i = new Intent(getApplicationContext(), AccountLogin.class);
        startActivity(i);
        finish();
    }

}
