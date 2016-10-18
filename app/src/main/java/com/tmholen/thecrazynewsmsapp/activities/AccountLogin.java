package com.tmholen.thecrazynewsmsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tmholen.thecrazynewsmsapp.R;

/**
 * Created by dogsh on 18-Oct-16.
 */

public class AccountLogin extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_management);

        final TextView textTitle = (TextView) findViewById(R.id.account_management_title);
        textTitle.setText("Account Login");

        final EditText fieldName = (EditText) findViewById(R.id.account_management_name);
        fieldName.setVisibility(View.VISIBLE);

        final EditText fieldNumber = (EditText) findViewById((R.id.account_management_number));
        final EditText fieldPassword = (EditText) findViewById((R.id.account_management_password));
        final TextView textExplanation = (TextView) findViewById(R.id.account_management_explanation);

        Button loginButton = (Button) findViewById(R.id.account_management_button2);
        loginButton.setText("Login");

        Button registrationScreenButton = (Button) findViewById(R.id.account_management_button1);
        registrationScreenButton.setText("Registration Screen");
        registrationScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AccountRegistration.class);
                startActivity(i);
            }
        });



    }
}
