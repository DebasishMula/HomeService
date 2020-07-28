package com.example.debasish.homeservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ViewSavedServices extends AppCompatActivity {
    TextView textViewWorkingDate,textViewWorkingAddress,textViewWorkingDescrip,textViewServiceProviderName,textViewServiceProviderPhNumber;
    Button buttonDeleteService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_services);
        textViewWorkingDate=findViewById(R.id.serviceDateInSavedServices);
        textViewWorkingAddress=findViewById(R.id.serviceAddressInSavedServices);
        textViewWorkingDescrip=findViewById(R.id.serviceDescriptionInSavedServices);
        textViewServiceProviderName=findViewById(R.id.serviceProviderNameInSavedServices);
        textViewServiceProviderPhNumber=findViewById(R.id.serviceProviderPhoneNumberInSavedServices);
        buttonDeleteService=findViewById(R.id.deleteServiceInSavedServices);
        Intent intent=getIntent();
        HiringServicemanInfo hiringServicemanInfo=(HiringServicemanInfo) intent.getSerializableExtra("hiringServicemanInfo");
        textViewWorkingDate.setText(hiringServicemanInfo.getWorking_date());
        textViewWorkingAddress.setText(hiringServicemanInfo.getWorking_address());
        textViewWorkingDescrip.setText(hiringServicemanInfo.getWorking_descrip());
        textViewServiceProviderName.setText(hiringServicemanInfo.getUser_name());
        textViewServiceProviderPhNumber.setText(hiringServicemanInfo.getUser_phone_number());

    }
}
