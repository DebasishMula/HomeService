package com.example.debasish.homeservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ViewSavedServiceman extends AppCompatActivity {
    TextView textViewWorkingDate,textViewWorkingAddress,textViewWorkingDescrip,textViewServicemanName,textViewservicemanPhNumber;
    Button buttonDeleteServiceman;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_serviceman);
        textViewWorkingDate=findViewById(R.id.serviceDateInSavedServiceman);
        textViewWorkingAddress=findViewById(R.id.serviceAddressInSavedServiceman);
        textViewWorkingDescrip=findViewById(R.id.serviceDescriptionInSavedServiceman);
        textViewServicemanName=findViewById(R.id.servicemanNameInSavedServiceman);
        textViewservicemanPhNumber=findViewById(R.id.servicemanPhoneNumberInSavedServiceman);
        buttonDeleteServiceman=findViewById(R.id.deleteServicemanInSavedServiceman);
        Intent intent=getIntent();
        HiringServicemanInfo hiringServicemanInfo=(HiringServicemanInfo)intent.getSerializableExtra("hiringServicemanInfo");
        textViewWorkingDate.setText(hiringServicemanInfo.getWorking_date());
        textViewWorkingAddress.setText(hiringServicemanInfo.getWorking_address());
        textViewWorkingDescrip.setText(hiringServicemanInfo.getWorking_descrip());
        textViewServicemanName.setText(hiringServicemanInfo.getUser_name());
        textViewservicemanPhNumber.setText(hiringServicemanInfo.getUser_phone_number());
    }
}
