package com.tmholen.thecrazynewsmsapp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.StackView;

/**
 * Created by dogsh on 16-Sep-16.
 */

public class UiComponents implements NavigationView.OnNavigationItemSelectedListener {

    AppCompatActivity appCompatActivity;
    Toolbar toolbar;

    public UiComponents(AppCompatActivity appCompatActivity, Toolbar toolbar) {
        this.appCompatActivity = appCompatActivity;
        this.toolbar = toolbar;
    }

    public void AddDefaultNavigationActivityElementsToScreen(){
        EnableToolbar();
        ShowFabButton();
        EnableNavigationDrawer();
        FillNavigationDrawerMenu();
    }

    public void EnableToolbar() {
        toolbar = (Toolbar) appCompatActivity.findViewById(R.id.toolbar);
        appCompatActivity.setSupportActionBar(toolbar);
    }


    public void ShowFabButton() {
        FloatingActionButton fab = (FloatingActionButton) appCompatActivity.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }

    public void EnableNavigationDrawer() {
        DrawerLayout drawer = (DrawerLayout) appCompatActivity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                appCompatActivity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void FillNavigationDrawerMenu() {
        NavigationView navigationView = (NavigationView) appCompatActivity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id){
            case R.id.nav_message_screen:{
                Intent i = new Intent(appCompatActivity, MessageScreen.class);
                appCompatActivity.startActivity(i, ActivityOptions.makeSceneTransitionAnimation(appCompatActivity).toBundle());
            }
            case R.id.nav_contact_screen:{
                Intent i = new Intent(appCompatActivity, ContactScreen.class);
                appCompatActivity.startActivity(i, ActivityOptions.makeSceneTransitionAnimation(appCompatActivity).toBundle());
            }
            case R.id.nav_account_info:{
                //NavigateToAccountInfoScreen();
            }
            case R.id.nav_add:{

            }
            case R.id.nav_manage:{

            }
            case R.id.nav_delete:{

            }
        }

        DrawerLayout drawer = (DrawerLayout) appCompatActivity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}