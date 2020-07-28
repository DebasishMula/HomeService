package com.example.debasish.homeservice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class Settings extends AppCompatActivity {
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    Button button,buttonUpAccInfo,buttonTandC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        button=findViewById(R.id.user_logout);
        buttonUpAccInfo=findViewById(R.id.UserUpdateAccountInfo);
        buttonTandC=findViewById(R.id.UserTermsAndCondition);
        toolbar=findViewById(R.id.toolbar_setting);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean islogout=logout();
                if(islogout==true)
                {
                    Intent intent_logout=new Intent(getApplicationContext(),LogIn.class);
                    startActivity(intent_logout);
                }
            }
        });
      buttonUpAccInfo.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent1=new Intent(getApplicationContext(),UpdateMyAccountInfo.class);
              startActivity(intent1);
          }
      });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent_home=new Intent(Settings.this,MainActivity2.class);
                startActivity(intent_home);

        }
        return true;
    }
    boolean logout()
    {
        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("logged_user",false).apply();
        sharedPreferences.edit().clear();
        sharedPreferences.edit().commit();
        return true;
    }
}
