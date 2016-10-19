package com.tmholen.thecrazynewsmsapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tmholen.thecrazynewsmsapp.R;
import com.tmholen.thecrazynewsmsapp.asynctasks.LoadAccounts;
import com.tmholen.thecrazynewsmsapp.data.DataHandler;

import java.util.List;

/**
 * Created by dogsh on 18-Oct-16.
 */

public class AccountLogin extends AppCompatActivity {
    private String errorMessage = "";
    private Boolean goToMainActivity = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_management);

        final TextView textTitle = (TextView) findViewById(R.id.account_management_title);
        textTitle.setText("Account Login");

        final EditText fieldName = (EditText) findViewById(R.id.account_management_name);
        fieldName.setVisibility(View.GONE);

        final EditText fieldNumber = (EditText) findViewById((R.id.account_management_number));
        final EditText fieldPassword = (EditText) findViewById((R.id.account_management_password));
        final TextView textExplanation = (TextView) findViewById(R.id.account_management_explanation);

        Button loginButton = (Button) findViewById(R.id.account_management_button2);
        loginButton.setText("Login");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (fieldNumber.getText().toString().length() < 8) {
                    errorMessage += "Your phone number must be at least 8 numbers long!\n\n";
                    goToMainActivity = false;
                }
                if (fieldPassword.getText().toString().length() < 4) {
                    errorMessage += "Your password must be at least 4 characters long!";
                    goToMainActivity = false;
                }

                if (goToMainActivity) {
                    String path = "http://192.168.2.4:8080/MessagingServer/service/chat/login/" +
                            fieldNumber.getText().toString() + "/" + fieldPassword.getText().toString();
                    new LoadAccounts(
                            new LoadAccounts.Callback() {
                                @Override
                                public void update(List<LoadAccounts.Account> accounts) {
                                    if (accounts.size() == 1) {
                                        Log.i("Account info", "" + accounts.get(0).getId());
                                        Log.i("Account info", accounts.get(0).getName());
                                        Log.i("Account info", accounts.get(0).getNumber());

                                        DataHandler.getInstance().setMyAccount(accounts.get(0));

                                        Intent i = new Intent(getApplicationContext(), Conversations.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        textExplanation.setText("Login failed:\nIncorrect login credentials");
                                        textExplanation.setVisibility(View.VISIBLE);
                                    }

                                }
                            }
                    ).execute(path);
                }else{
                    textExplanation.setText(errorMessage);
                    textExplanation.setVisibility(View.VISIBLE);
                    goToMainActivity = true;
                }

            }
        });


        Button registrationScreenButton = (Button) findViewById(R.id.account_management_button1);
        registrationScreenButton.setText("Registration Screen");
        registrationScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AccountRegistration.class);
                startActivity(i);
                finish();
            }
        });


    }
}
