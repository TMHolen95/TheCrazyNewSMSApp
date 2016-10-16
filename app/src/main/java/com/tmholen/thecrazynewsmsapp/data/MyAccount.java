package com.tmholen.thecrazynewsmsapp.data;

import android.app.Activity;
import android.content.SharedPreferences;

import com.tmholen.thecrazynewsmsapp.asynctasks.LoadAccounts;

/**
 * Created by dogsh on 16-Oct-16.
 */

public class MyAccount extends Activity {

    public static final String PREFS_NAME = "MyAccount";
    SharedPreferences myAccount;
    SharedPreferences.Editor editor;

    LoadAccounts.Account account;

    public MyAccount(){
        myAccount = getSharedPreferences(PREFS_NAME, 0);
        editor = myAccount.edit();
    }

}
