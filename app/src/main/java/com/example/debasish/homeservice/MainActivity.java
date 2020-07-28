package com.example.debasish.homeservice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {
    LinearLayout ll1;
    TextView textViewName,textViewEmail,textViewPhoneNumber,textViewVisitsOnProfile,textViewMessageRequest,textViewSavedService,textViewServiceAlerts,textViewNameOnHeader,textViewLocationOnHeader;
    //connecting TextView View with TextView object
    private DrawerLayout drawerLayout;
    Button UpdateProfile,AddImgButton,buttonVisitOnProfile,buttonMessageRequest,buttonServiceAlerts,buttonSavedServices,buttonSavedTeam,buttonMakeA_Group;
    //connecting Button View with Button object
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);//connecting layout view with layout object
        NavigationView navigationView = findViewById(R.id.nav_view);//connecting navigation view with navigation object
        Toolbar toolbar = findViewById(R.id.toolbar);//connecting toolbar  view with toolbar object
        setSupportActionBar(toolbar); //calling method to support actionbar with toolbar
        ActionBar actionBar = getSupportActionBar();//initializing actionbar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);//setting actionbar indicator
        textViewName=findViewById(R.id.servicemanHomeName);
        textViewEmail=findViewById(R.id.ServicemanHomeEmail);
        textViewPhoneNumber=findViewById(R.id.ServicemanHomePhoneNumber);
        textViewVisitsOnProfile=findViewById(R.id.ServicemanHomeVisitsOnProfileTextView);
        textViewMessageRequest=findViewById(R.id.ServicemanHomeMessageRequestTextView);
        textViewSavedService=findViewById(R.id.ServicemanHomSavedServiceTextView);
        textViewServiceAlerts=findViewById(R.id.ServicemanHomeServiceAlertsTextView);
        //binding the textView object with the id of the textView view
        View view=navigationView.inflateHeaderView(R.layout.activity_header_on_navigation_of_user_serviceman);
        textViewNameOnHeader=view.findViewById(R.id.servicemanNameInUserServicemanHeader);
        textViewLocationOnHeader=view.findViewById(R.id.servicemanPlaceInUserServicemanHeader);

        buttonVisitOnProfile=findViewById(R.id.ServicemanHomeVisitsOnProfileButton);
        buttonMessageRequest=findViewById(R.id.ServicemanHomeMessageRequestButton);
        buttonSavedServices=findViewById(R.id.ServicemanHomeSavedServiceButton);
        buttonServiceAlerts=findViewById(R.id.ServicemanHomeServiceAlertsButton);
        buttonSavedTeam=findViewById(R.id.ServicemanHomeSavedTeamButton);
        buttonMakeA_Group=findViewById(R.id.ServicemanHomeMakeA_GroupButton);
        AddImgButton=findViewById(R.id.add_img);
        UpdateProfile=findViewById(R.id.ServicemanHomeUpdateAccountInfo);
        ////binding the Button object with the id of the Button view
        ll1=findViewById(R.id.servicemanHomeLL1);
        ////binding the LinearLayout  object with the id of the LinearLayout view
        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);//checking if there is any serviceman session
        String serviceman_id =sharedPreferences.getString("id","id");
        String serviceman_name =sharedPreferences.getString("name","name");
        String serviceman_email =sharedPreferences.getString("email","email");
        String serviceman_PhoneNumber =sharedPreferences.getString("phone_number","phone_number");
        String no_of_visits=sharedPreferences.getString("no_of_visits","no_of_visits");
        String serviceman_homeTown =sharedPreferences.getString(serviceman_email+"homeTown",serviceman_email+"homeTown");
        String serviceman_dist =sharedPreferences.getString(serviceman_email+"dist",serviceman_email+"dist");//getting serviceman_details from serviceman session
        textViewName.setText(serviceman_name);
        textViewEmail.setText(serviceman_email);
        textViewPhoneNumber.setText(serviceman_PhoneNumber);
        textViewVisitsOnProfile.setText(no_of_visits);
        textViewNameOnHeader.setText(serviceman_name);
        textViewLocationOnHeader.setText(serviceman_dist);
        if(no_of_visits.equals("0"))
        {
            textViewVisitsOnProfile.setTextColor(Color.rgb(0,0,0));
        }
        else
        {
            textViewVisitsOnProfile.setTextColor(Color.rgb(220,0,0));
        }
        if(serviceman_dist.equals(""))
        {
            ll1.setVisibility(View.VISIBLE);
        }

        AddImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ImageProcessing1.class);
                startActivity(intent);

            }
        });
        buttonVisitOnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,VisitProfile.class);
                startActivity(intent);
            }
        });
        UpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUpdateProfile=new Intent(MainActivity.this,UpdateMyAccountInfo.class);
                startActivity(intentUpdateProfile);
            }
        });
        buttonServiceAlerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentServiceAlerts=new Intent(MainActivity.this,service_alerts.class);
                startActivity(intentServiceAlerts);
            }
        });
        buttonSavedServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent savedServicesIntent=new Intent(MainActivity.this,SavedServices.class);
                startActivity(savedServicesIntent);
            }
        });
       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
               switch(menuItem.getItemId())
               {
                   case R.id.nav_settings:
                       drawerLayout.closeDrawer(GravityCompat.START);
                       Intent settings_intent=new Intent(getApplicationContext(),Settings_Serviceman.class);
                       startActivity(settings_intent);
                       break;
                   case R.id.nav_profile:
                       drawerLayout.closeDrawer(GravityCompat.START);
                       Intent profile_intent=new Intent(getApplicationContext(),UpdateMyAccountInfo.class);
                       startActivity(profile_intent);
               }
               return true;
           }
       });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }//to open navigation after clicking action bar





}

