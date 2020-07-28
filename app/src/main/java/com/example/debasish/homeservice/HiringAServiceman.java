package com.example.debasish.homeservice;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Calendar;

public class HiringAServiceman extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView workingDate;
    EditText workingAddress,workingDes;
    Button datePickUp,hire;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiring_aserviceman);
        workingDate=findViewById(R.id.hireWorkingDate);
        workingAddress=findViewById(R.id.hireWorkingAdress);
        workingDes=findViewById(R.id.hireWorkingDescription);
        datePickUp=findViewById(R.id.hirePickDateButton);
        hire=findViewById(R.id.hireButton);
        SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        final String user_id=sharedPreferences.getString("id","id");
        Intent intent=getIntent();
        final String serviceman_id=intent.getStringExtra("serviceman_id");
        String serviceman_name=intent.getStringExtra("serviceman_name");
       name=serviceman_name;
        datePickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date=workingDate.getText().toString();
                String address=workingAddress.getText().toString();
                String description=workingDes.getText().toString();
                BackGroundTaskHiringServiceman backGroundTaskHiringServiceman=new BackGroundTaskHiringServiceman();
                backGroundTaskHiringServiceman.execute(user_id,serviceman_id,date,address,description);
            }
        });
    }
    private void showDatePickerDialog()
    {
        DatePickerDialog datePickerDialog=new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DATE)

        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
       String date=day+"/"+month+"/"+year;
       workingDate.setText(date);

    }
    private class BackGroundTaskHiringServiceman extends AsyncTask<String,Void,String>
    {
        String phpUrl;
        String jsonString;
        ProgressDialog progressDialog=new ProgressDialog(HiringAServiceman.this);
        @Override
        protected void onPreExecute() {
            phpUrl="https://androiddbepizycom.000webhostapp.com/Home%20Service/HiringServiceman.php";
            progressDialog.setMessage("Loading....");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String serviceman_id=args[1];
            String user_id=args[0];
            String workingDate=args[2];
            String workingAddress=args[3];
            String workingDescrip=args[4];
            try {
                URL url=new URL(phpUrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter= new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
                String dataString = URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8")+"&"+
                        URLEncoder.encode("serviceman_id","UTF-8")+"="+URLEncoder.encode(serviceman_id,"UTF-8")+"&"+
                        URLEncoder.encode("working_date","UTF-8")+"="+URLEncoder.encode(workingDate,"UTF-8")+"&"+
                        URLEncoder.encode("working_address","UTF-8")+"="+URLEncoder.encode(workingAddress,"UTF-8")+"&"+
                        URLEncoder.encode("working_descrip","UTF-8")+"="+URLEncoder.encode(workingDescrip,"UTF-8");
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
                String MessageResult=jsonObject.getString("response");
                if(MessageResult.equals("Successful"))
                {
                    progressDialog.dismiss();
                    Intent intent=new Intent(HiringAServiceman.this,AfterHiringServiceman.class);
                    intent.putExtra("serviceman_name",name);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid Request",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // super.onPostExecute(aVoid);
        }
    }
}
