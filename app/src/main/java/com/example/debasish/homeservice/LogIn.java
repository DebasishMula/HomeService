package com.example.debasish.homeservice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

public class LogIn extends AppCompatActivity {
    Button register,log_in,search_as_guest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        register=findViewById(R.id.register);
        log_in=findViewById(R.id.login);
        search_as_guest=findViewById(R.id.search_as_a_guest);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(LogIn.this,Register_User.class);//after clicking register button it will goes to userRegister_Activity
                startActivity(intent1);

            }
        });
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(LogIn.this,a_login.class);//after clicking login button it will goes to login_Activity
                startActivity(intent2);
            }
        });
        search_as_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(LogIn.this,Search_Serviceman.class);
                startActivity(intent3);
            }
        });

    }
}
