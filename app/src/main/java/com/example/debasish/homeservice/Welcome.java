package com.example.debasish.homeservice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Welcome extends AppCompatActivity {
    private static int splash_time_out=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);//checking if there is any login session
                if (sharedPreferences.getBoolean("logged_user", false))//checking if there any user login session
                    {
                    Intent intent_user = new Intent(getApplicationContext(), MainActivity2.class);
                    startActivity(intent_user);
                }
               else if (sharedPreferences.getBoolean("logged_serviceman", false))//checking if there is any user_serviceman login session
               {
                    Intent intent_serviceman = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent_serviceman);
                }
                else
                {
                    Intent intent=new Intent(Welcome.this,LogIn.class);//if no session created go to login_Activity to create session
                    startActivity(intent);

                }

                finish();
            }
        },splash_time_out);
    }
}
