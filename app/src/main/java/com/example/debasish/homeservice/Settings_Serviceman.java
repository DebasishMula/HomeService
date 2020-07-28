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


public class Settings_Serviceman extends AppCompatActivity {
    Toolbar toolbar;
    Button button,updateAccountInfo,tAndC,paymentSettings;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings__serviceman);
        toolbar=findViewById(R.id.toolbar_settingServiceman);
        button=findViewById(R.id.userServiceman_logout);
        updateAccountInfo=findViewById(R.id.servicemanUpdateAccountInfo);
        tAndC=findViewById(R.id.ServicemanTermsAndCondition);
        paymentSettings=findViewById(R.id.ServicemanPaymentSettings);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isLogout=logout();
                if(isLogout==true)
                {
                    Intent intent=new Intent(getApplicationContext(),LogIn.class);
                    startActivity(intent);
                }
            }
        });
     updateAccountInfo.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent1=new Intent(getApplicationContext(),UpdateMyAccountInfo.class);
             startActivity(intent1);
         }
     });
     paymentSettings.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent2=new Intent(getApplicationContext(),PaymentSettingsUser.class);
             startActivity(intent2);
         }
     });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent_home=new Intent(Settings_Serviceman.this,MainActivity.class);
                startActivity(intent_home);

        }
        return true;
    }
    boolean logout()
    {
        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("logged_serviceman",false).apply();
        sharedPreferences.edit().clear();
        sharedPreferences.edit().commit();
        return true;
    }
}
