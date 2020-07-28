package com.example.debasish.homeservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class MessageToAndHireServiceman extends AppCompatActivity {
     TextView TextViewName,TextViewPhoneNumber,TextViewSkill1,TextViewSkill2,TextViewSkill3,TextViewSkill4,TextViewSkill5,TextViewCity,TextViewState,TextViewDist,TextViewSalary;
      Button buttonMessage,buttonHire;
      LinearLayout Ls1,Ls2,Ls3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_to_and_hire_serviceman);
        TextViewName=findViewById(R.id.viewServicemanName);
        TextViewPhoneNumber=findViewById(R.id.viewServicemanPhoneNumber);
        TextViewSkill1=findViewById(R.id.viewServicemanSkill1);
        TextViewSkill2=findViewById(R.id.viewServicemanSkill2);
        TextViewSkill3=findViewById(R.id.viewServicemanSkill3);
        TextViewSkill4=findViewById(R.id.viewServicemanSkill4);
        TextViewSkill5=findViewById(R.id.viewServicemanSkill5);
        TextViewCity=findViewById(R.id.viewServicemanCity);
        TextViewDist=findViewById(R.id.viewServicemanDist);
        TextViewState=findViewById(R.id.viewServicemanState);
        TextViewSalary=findViewById(R.id.viewServicemanSalary);
        buttonMessage=findViewById(R.id.viewServicemanMessageButton);
        buttonHire=findViewById(R.id.viewServicemanHireButton);
        Ls1=findViewById(R.id.ls1);
        Ls2=findViewById(R.id.ls2);
        Ls3=findViewById(R.id.ls3);
        Intent intent=getIntent();
        final UserInfo userInfo=(UserInfo)intent.getSerializableExtra("userInfo");
       final String servicemanId=userInfo.getId();
       final String servicemanName=userInfo.getName();
        TextViewName.setText(userInfo.getName());
        TextViewPhoneNumber.setText(userInfo.getPhoneNumber());
        TextViewSkill1.setText(userInfo.getSearch_skill());
        BackGroundTaskUser backGroundTaskUser=new BackGroundTaskUser();
        backGroundTaskUser.execute(servicemanId);
        buttonHire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hireIntent=new Intent(MessageToAndHireServiceman.this,HiringAServiceman.class);
                hireIntent.putExtra("serviceman_id",servicemanId);
                hireIntent.putExtra("serviceman_name",servicemanName);
                startActivity(hireIntent);
            }
        });



    }
    private class BackGroundTaskUser extends AsyncTask<String,Void,String>
    {
        String phpUrl;
        String jsonString;
        ProgressDialog progressDialog=new ProgressDialog(MessageToAndHireServiceman.this);
        @Override
        protected void onPreExecute() {
            phpUrl="https://androiddbepizycom.000webhostapp.com/Home%20Service/SelectedFromSearchServiceman.php";
            progressDialog.setMessage("Loading....");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String user_id=args[0];
            try {
                URL url=new URL(phpUrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter= new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
                String dataString = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8");
                bufferedWriter.write(dataString);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                jsonString=bufferedReader.readLine();
                bufferedReader.close();
                httpURLConnection.disconnect();
                return  jsonString;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject=new JSONObject(result);

                TextViewCity.setText(jsonObject.getString("city"));
                TextViewDist.setText(jsonObject.getString("dist"));
                TextViewState.setText(jsonObject.getString("state"));
                TextViewSalary.setText(jsonObject.getString("salary"));
                progressDialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // super.onPostExecute(aVoid);
        }
    }


}
