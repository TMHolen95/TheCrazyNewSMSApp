package com.tmholen.thecrazynewsmsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tmholen.thecrazynewsmsapp.R;
import com.tmholen.thecrazynewsmsapp.asynctasks.LoadConversations;

/**
 * Created by dogsh on 16-Oct-16.
 */

public class RegisterUser extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_management);

        Button registerButton = (Button) findViewById(R.id.account_management_register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Conversations.class);
                startActivity(i);

            }
        });
    }
}
