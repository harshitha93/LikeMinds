package com.ground0.likeminds;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Arjun on 04-09-2015.
 */
public class LoginActivity extends AppCompatActivity {

    EditText userName,password;
    TextInputLayout tLUsername, tLPassword;


    private void actionHandleDone()
    {
        if(userName.getText().length()!=0 && password.getText().length()!=0)
        {
            tLPassword.setErrorEnabled(false);
            ConnectivityManager conManager =
                    (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
            boolean isConnected = networkInfo!=null && networkInfo.isConnectedOrConnecting();

            if(isConnected)
            {
                startActivity(new Intent(LoginActivity.this,LandingActivity.class));
            }
            else
            {
                Toast.makeText(LoginActivity.this,"You are not connected to the Internet",Toast.LENGTH_LONG).show();
            }
        }
        else if(password.getText().length()==0)
        {

            tLPassword.setErrorEnabled(true);
            tLPassword.setError("Please check your password");
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        userName = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        tLUsername = ((TextInputLayout) findViewById(R.id.username_layout));
        tLPassword = ((TextInputLayout) findViewById(R.id.password_layout));


        findViewById(R.id.action_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionHandleDone();
            }
        });


        userName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled =false;

                if(userName.getText().length()==0)
                {
                    tLUsername.setError("Please check your username");
                    tLUsername.setErrorEnabled(true);
                }
                else
                {
                    tLUsername.setErrorEnabled(false);
                }
                return handled;
            }
        });
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;


                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handled = true;
                    actionHandleDone();
                }
                return handled;
            }
        });




    }


}
