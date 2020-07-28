package com.example.debasish.homeservice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    TextView textView_Name,textView_PhoneNumber,textView_Email,textViewNameOnHeader,textViewPlaceOnHeader;
    Button Search_Technician,buttonSavedTechnician;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        drawerLayout=findViewById(R.id.drawer_layout2);
        navigationView=findViewById(R.id.nav_view2);
        toolbar=findViewById(R.id.toolbar_in_user);//connecting toolbar  view with toolbar object
        setSupportActionBar(toolbar);//calling method to support actionbar with toolbar
        ActionBar actionBar=getSupportActionBar();//initializing actionbar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);//setting actionbar indicator
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId())//code to go the user_settings activity
                {
                    case R.id.nav_settings_user:
                        drawerLayout.closeDrawers();
                        Intent settings_intent=new Intent(getApplicationContext(),Settings.class);
                        startActivity(settings_intent);
                        break;
                    case R.id.nav_profile_user:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent profile_intent=new Intent(getApplicationContext(),UpdateMyAccountInfo.class);
                        startActivity(profile_intent);

                }
                return  true;
            }
        });
        textView_Name=findViewById(R.id.userHomeName);
        textView_PhoneNumber=findViewById(R.id.userHomePhoneNumber);
        textView_Email=findViewById(R.id.UserHomeEmail);
        Search_Technician=findViewById(R.id.SearchTechnician);
        buttonSavedTechnician=findViewById(R.id.SavedTechnicianAndWorker);
        View view =navigationView.inflateHeaderView(R.layout.activity_header_on_navigation_of_user);
        textViewNameOnHeader=view.findViewById(R.id.userNameInUserHeader);
        textViewPlaceOnHeader=view.findViewById(R.id.userPlaceInUserHeader);
        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);//checking if there is any user_session
        String id=sharedPreferences.getString("id","id");
       String user_name =sharedPreferences.getString("name","name");
       String user_mail=sharedPreferences.getString("email","email");
       String user_phoneNumber=sharedPreferences.getString("phone_number","phone_number");//getting the user Details from user Session
        String user_city=sharedPreferences.getString("city","city");
        textView_Name.setText(user_name);
        textView_Email.setText(user_mail);
        textView_PhoneNumber.setText(user_phoneNumber);
        textViewNameOnHeader.setText(user_name);
        textViewPlaceOnHeader.setText(user_city);
        Search_Technician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity2.this,Search_Serviceman.class);
                startActivity(intent);
            }
        });
       buttonSavedTechnician.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent savedTechnicianIntent=new Intent(MainActivity2.this,SavedServiceman.class);
               startActivity(savedTechnicianIntent);
           }
       });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //code to open the navigation drawer
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }//to open navigation after clicking action bar



}
