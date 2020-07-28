package com.example.debasish.homeservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class PaymentSettingsUser extends AppCompatActivity {
    EditText editTextPayment;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_settings_user);
        editTextPayment=findViewById(R.id.updateServiceman_payment);
        buttonSave=findViewById(R.id.updateServicemanPaymentButton);
        final SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String id=sharedPreferences.getString("id","id");
              String payment=editTextPayment.getText().toString();
                BackGroundTaskPaymentSettings backGroundTaskPaymentSettings=new BackGroundTaskPaymentSettings();
                backGroundTaskPaymentSettings.execute(id,payment);
            }
        });
    }
    private class BackGroundTaskPaymentSettings extends AsyncTask<String,Void,String>
    {
        String phpUrl;
        String jsonString;
        ProgressDialog progressDialog=new ProgressDialog(PaymentSettingsUser.this);
        @Override
        protected void onPreExecute() {
            phpUrl="https://androiddbepizycom.000webhostapp.com/Home%20Service/updateSalaryServiceman.php";
            progressDialog.setMessage("Loading....");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String id=args[0];
            String payment=args[1];
            try {
                URL url=new URL(phpUrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter= new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
                String dataString = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"+
                        URLEncoder.encode("payment","UTF-8")+"="+URLEncoder.encode(payment,"UTF-8");
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
                if(MessageResult.equals("Updated Successfully"))
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),MessageResult,Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(PaymentSettingsUser.this,Settings_Serviceman.class);
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
