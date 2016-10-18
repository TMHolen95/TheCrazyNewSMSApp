package com.tmholen.thecrazynewsmsapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tmholen.thecrazynewsmsapp.R;
import com.tmholen.thecrazynewsmsapp.asynctasks.LoadAccounts;
import com.tmholen.thecrazynewsmsapp.asynctasks.PostAccount;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by dogsh on 16-Oct-16.
 */

public class AccountRegistration extends AppCompatActivity {
    private String errorMessage = "";
    private Boolean goToMainActivity = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_management);

        final EditText fieldName = (EditText) findViewById(R.id.account_management_name);
        final EditText fieldNumber = (EditText) findViewById((R.id.account_management_number));
        final EditText fieldPassword = (EditText) findViewById((R.id.account_management_password));
        final TextView textExplanation = (TextView) findViewById(R.id.account_management_explanation);

        Button registerButton = (Button) findViewById(R.id.account_management_button2);
        registerButton.setText("Register");
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fieldName.getText().toString().length() < 5) {
                    errorMessage += "Your name must be at least 5 characters long!\n\n";
                    goToMainActivity = false;
                }
                if (fieldNumber.getText().toString().length() < 8) {
                    errorMessage += "Your phone number must be at least 8 numbers long!\n\n";
                    goToMainActivity = false;
                }
                if (fieldPassword.getText().toString().length() < 8) {
                    errorMessage += "Your password must be at least 8 characters long!";
                    goToMainActivity = false;
                }

                if (goToMainActivity) {
                    LoadAccounts.Account registrationAccount = new LoadAccounts.Account(
                            fieldName.getText().toString(),
                            fieldNumber.getText().toString(),
                            fieldPassword.getText().toString(),
                            "",
                            new ArrayList<Long>());

                    try {
                        new PostAccount(
                                "http://192.168.2.4:8080/MessagingServer/service/chat/accounts/create",
                                new PostAccount.Callback() {
                                    @Override
                                    public void onPostExecute(LoadAccounts.Account account, int responseCode) {
                                        if (responseCode == HttpURLConnection.HTTP_OK) {
                                            SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                                            SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
                                            preferenceEditor.putLong("id", account.getId());
                                            preferenceEditor.putString("name", account.getName());
                                            preferenceEditor.putString("number", account.getNumber());
                                            preferenceEditor.commit();

                                            Intent i = new Intent(getApplicationContext(), Conversations.class);
                                            startActivity(i);
                                        }
                                    }
                                }
                        ).execute(registrationAccount);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    textExplanation.setText(errorMessage);
                    textExplanation.setVisibility(View.VISIBLE);
                    errorMessage = "";
                }


            }
        });

        Button loginScreenButton = (Button) findViewById(R.id.account_management_button1);
        loginScreenButton.setText("Login screen");
        loginScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AccountLogin.class);
                startActivity(i);
            }
        });
    }
}
