package com.tmholen.thecrazynewsmsapp.activities;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tmholen.thecrazynewsmsapp.R;
import com.tmholen.thecrazynewsmsapp.adapters.AccountArrayAdapter;
import com.tmholen.thecrazynewsmsapp.asynctasks.LoadAccounts;
import com.tmholen.thecrazynewsmsapp.asynctasks.LoadConversations;
import com.tmholen.thecrazynewsmsapp.asynctasks.PostConversation;
import com.tmholen.thecrazynewsmsapp.data.DataHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dogsh on 20-Oct-16.
 */

public class Contacts extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView entryList;
    private AccountArrayAdapter accountArrayAdapter;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private MenuItem previousItem;
    private DataHandler dh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screens);
        entryList = (ListView) findViewById(R.id.mainListView);

        AddDefaultNavigationActivityElementsToScreen();

        dh = DataHandler.getInstance();

        if (dh.getMyAccount() != null) {
            PopulateListview();
            UpdateNavigationHeader();
        } else {
            GoToLogin();
        }

        entryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                LoadAccounts.Account contactAccount = (LoadAccounts.Account) adapterView.getItemAtPosition(i);
                LoadAccounts.Account myAccount = DataHandler.getInstance().getMyAccount();

                List<Long> accountIds = new ArrayList<Long>();
                accountIds.add(myAccount.getId());
                accountIds.add(contactAccount.getId());

                LoadConversations.Conversation newConversation = new LoadConversations.Conversation(accountIds);

                try {
                    new PostConversation(
                            "http://"+ DataHandler.getInstance().getMyIp() +":8080/MessagingServer/service/chat/conversations/create",
                            new PostConversation.Callback(){
                                @Override
                                public void onPostExecute(LoadConversations.Conversation conversation, int responseCode) {
                                    if (responseCode == HttpURLConnection.HTTP_OK) {
                                        Intent intent = new Intent(getApplicationContext(), Messaging.class);
                                        intent.putExtra("conversationId", conversation.getId());
                                        DataHandler.getInstance().addConversation(conversation);
                                        startActivity(intent);
                                    }
                                }
                            }
                    ).execute(newConversation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
            DataHandler.getInstance().setMyAccount(null);
            GoToLogin();
            return true;
        }


        return super.onOptionsItemSelected(item);
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
        fab.setVisibility(View.GONE);
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
        navigationView.getMenu().findItem(R.id.nav_contact_screen).setChecked(true);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (previousItem != null) {
            previousItem.setChecked(false);
        }
        if(item == navigationView.getMenu().findItem(R.id.nav_conversation_screen) || item == navigationView.getMenu().findItem(R.id.nav_contact_screen)){
            item.setChecked(true);
            previousItem = item;
        }

        if (id == R.id.nav_conversation_screen) {
            item.setChecked(false);
            Intent i = new Intent(getApplicationContext(), Conversations.class);
            startActivity(i);

        } else if (id == R.id.nav_contact_screen) {
            item.setChecked(false);
            Intent i = new Intent(getApplicationContext(), Contacts.class);
            startActivity(i);

        } else if (id == R.id.nav_account_info) {
            Toast.makeText(this, "Functionality in development.", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_add) {
            Toast.makeText(this, "Functionality in development.", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_manage) {
            Toast.makeText(this, "Functionality in development.", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_delete) {
            Toast.makeText(this, "Functionality in development.", Toast.LENGTH_SHORT).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void GoToLogin() {
        Intent i = new Intent(getApplicationContext(), AccountLogin.class);
        startActivity(i);
        finish();
    }

    private void UpdateNavigationHeader() {
        TextView myName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userName);
        myName.setText(DataHandler.getInstance().getMyAccount().getName());
        TextView myNumber = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userNumber);
        myNumber.setText(DataHandler.getInstance().getMyAccount().getNumber());
    }

    private void PopulateListview() {
        new LoadAccounts(
                new LoadAccounts.Callback() {
                    @Override
                    public void update(List<LoadAccounts.Account> accounts) {
                        Collections.sort(accounts, LoadAccounts.accountComparator);
                        for (int i = 0; i < accounts.size(); i++) {
                            if (dh.getMyAccount().getId().equals(accounts.get(i).getId())) {
                                accounts.remove(i); //We don't want to list our own account in the contact list
                                break;
                            }
                        }
                        DataHandler.getInstance().setAccounts(accounts);
                        accountArrayAdapter = new AccountArrayAdapter(getApplicationContext(), DataHandler.getInstance().getAccounts());
                        entryList.setAdapter(accountArrayAdapter);
                        accountArrayAdapter.notifyDataSetChanged();
                    }
                }
        ).execute("http://" + DataHandler.getInstance().getMyIp() + ":8080/MessagingServer/service/chat/accounts");


    }
}
