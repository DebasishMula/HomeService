package com.example.debasish.homeservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AfterHiringServiceman extends AppCompatActivity {
    TextView textViewName;
    Button buttonBackToHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_hiring_serviceman);
        textViewName=findViewById(R.id.servicemanNameInHiringConfirmationMessage);
        buttonBackToHome=findViewById(R.id.backToHomeButtonInHiringConfirmationMessage);
        Intent intent=getIntent();
        textViewName.setText(intent.getStringExtra("serviceman_name"));
        buttonBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent=new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(homeIntent);
            }
        });
    }
}
