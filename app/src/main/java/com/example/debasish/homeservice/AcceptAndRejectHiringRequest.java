package com.example.debasish.homeservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
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

public class AcceptAndRejectHiringRequest extends AppCompatActivity {
     TextView textViewDate,textViewAddress,textViewDescrip,textViewName,textViewPhoneNumber;
     Button buttonAccept,buttonDecline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_and_reject_hiring_request);
        textViewDate=findViewById(R.id.serviceDateInServiceView);
        textViewAddress=findViewById(R.id.serviceAddressInServiceView);
        textViewDescrip=findViewById(R.id.serviceDescriptionInServiceView);
        textViewName=findViewById(R.id.serviceProviderNameInServiceView);
        textViewPhoneNumber=findViewById(R.id.serviceProviderPhoneNumberInServiceView);
        buttonAccept=findViewById(R.id.AcceptButtonInViewService);
        buttonDecline=findViewById(R.id.DeclineButtonInViewService);
        Intent intent=getIntent();
        final HiringServicemanInfo hiringServicemanInfo=(HiringServicemanInfo)intent.getSerializableExtra("hiringServicemanInfo");
        final String user_id=hiringServicemanInfo.getUser_id();
        final String hiring_serviceman_id=hiringServicemanInfo.getHiring_serviceman_id();
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);//checking if there is any login session
        SharedPreferences.Editor editor=sharedPreferences.edit();
        final String serviceman_id=sharedPreferences.getString("id","id");
        textViewDate.setText(hiringServicemanInfo.getWorking_date());
        textViewAddress.setText(hiringServicemanInfo.getWorking_address());
        textViewDescrip.setText(hiringServicemanInfo.getWorking_descrip());
        textViewName.setText(hiringServicemanInfo.getUser_name());
        textViewPhoneNumber.setText(hiringServicemanInfo.getUser_phone_number());
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackgroundTaskAcceptRequest backgroundTaskAcceptRequest=new BackgroundTaskAcceptRequest();
                backgroundTaskAcceptRequest.execute(user_id,serviceman_id,hiring_serviceman_id,hiringServicemanInfo.getWorking_date(),hiringServicemanInfo.getWorking_address(),hiringServicemanInfo.getWorking_descrip());
            }
        });
        buttonDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackgroundTaskDeclineRequest backgroundTaskDeclineRequest=new BackgroundTaskDeclineRequest();
                backgroundTaskDeclineRequest.execute(hiring_serviceman_id);
            }
        });
    }
    private class BackgroundTaskAcceptRequest extends AsyncTask<String,Void,String>
    {
        String PhpUrl;
        String JsonString;
        ProgressDialog progressDialog=new ProgressDialog(AcceptAndRejectHiringRequest.this);
        @Override
        protected void onPreExecute() {
            PhpUrl="https://androiddbepizycom.000webhostapp.com/Home%20Service/acceptService.php";
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String user_id=args[0];
            String serviceman_id=args[1];
            String hiring_serviceman_id=args[2];
            String working_date=args[3];
            String working_address=args[4];
            String working_descrip=args[5];
            try {
                URL url=new URL(PhpUrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String dataString= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8")+"&"+
                        URLEncoder.encode("serviceman_id","UTF-8")+"="+URLEncoder.encode(serviceman_id,"UTF-8")+"&"+
                        URLEncoder.encode("hiring_serviceman_id","UTF-8")+"="+URLEncoder.encode(hiring_serviceman_id,"UTF-8")+"&"+
                        URLEncoder.encode("working_date","UTF-8")+"="+URLEncoder.encode(working_date,"UTF-8")+"&"+
                        URLEncoder.encode("working_address","UTF-8")+"="+URLEncoder.encode(working_address,"UTF-8")+"&"+
                        URLEncoder.encode("working_descrip","UTF-8")+"="+URLEncoder.encode(working_descrip,"UTF-8");
                bufferedWriter.write(dataString);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                JsonString=bufferedReader.readLine();
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return JsonString;
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
                String ResponseMessage=jsonObject.getString("response");
                if(ResponseMessage.equals("Accept Successfully"))
                {
                    progressDialog.dismiss();
                    Intent intent=new Intent(AcceptAndRejectHiringRequest.this,MainActivity.class);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private class BackgroundTaskDeclineRequest extends AsyncTask<String,Void,String>
    {
        String PhpUrl;
        String JsonString;
        ProgressDialog progressDialog=new ProgressDialog(AcceptAndRejectHiringRequest.this);
        @Override
        protected void onPreExecute() {
            PhpUrl="https://androiddbepizycom.000webhostapp.com/Home%20Service/DeclineService.php";
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String hiring_serviceman_id=args[0];
            try {
                URL url=new URL(PhpUrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String dataString= URLEncoder.encode("hiring_serviceman_id","UTF-8")+"="+URLEncoder.encode(hiring_serviceman_id,"UTF-8");
                bufferedWriter.write(dataString);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                JsonString=bufferedReader.readLine();
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return JsonString;
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
                String ResponseMessage=jsonObject.getString("response");
                if(ResponseMessage.equals("Deleted Successfully"))
                {
                    progressDialog.dismiss();
                    Intent intent=new Intent(AcceptAndRejectHiringRequest.this,MainActivity.class);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
