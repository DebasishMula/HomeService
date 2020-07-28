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
import android.widget.TextView;
import android.widget.Toast;

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

public class UpdateMyAccountInfo extends AppCompatActivity {
    TextView textViewName,textViewEmail,textViewPassword;
    EditText editTextPhone,editTextCity,editTextDist,editTextState;
    Button buttonChangePassword,buttonUpdateProfile;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_my_account_info);
        textViewName=findViewById(R.id.update_account_name);
        textViewEmail=findViewById(R.id.update_account_email);
        textViewPassword=findViewById(R.id.update_account_password);
        editTextPhone=findViewById(R.id.update_account_phone);
        editTextCity=findViewById(R.id.update_account_city);
        editTextDist=findViewById(R.id.update_account_dist);
        editTextState=findViewById(R.id.update_account_state);
        buttonChangePassword=findViewById(R.id.change_button_fr_updatePassword);
        buttonUpdateProfile=findViewById(R.id.update_profile_button);
        final SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ChangePassword.class);
                startActivity(intent);
            }
        });
        if(sharedPreferences.getBoolean("logged_user",false))
        {
            final String id=sharedPreferences.getString("id","id");
            String user_name =sharedPreferences.getString("name","name");
            String user_mail=sharedPreferences.getString("email","email");
            String user_password=sharedPreferences.getString("password","password");
            final String user_phoneNumber=sharedPreferences.getString("phone_number","phone_number");
            final String user_city=sharedPreferences.getString("city","city");
            String user_dist=sharedPreferences.getString("dist","dist");
            String user_state=sharedPreferences.getString("state","state");//getting the user Details from user Session
            textViewName.setText(user_name);
            textViewEmail.setText(user_mail);
            textViewPassword.setText(user_password);
            editTextPhone.setText(user_phoneNumber);
            editTextCity.setText(user_city);
            editTextDist.setText(user_dist);
            editTextState.setText(user_state);

           buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user_upPhoneNumber=editTextPhone.getText().toString();
                    String user_upCity=editTextCity.getText().toString();
                    String user_upDist=editTextDist.getText().toString();
                    String user_upState=editTextState.getText().toString();
                    if(user_upPhoneNumber.equals("")||user_upCity.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Invalid Fields",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("city",user_upCity);
                        editor.putString("phone_number",user_phoneNumber);
                        editor.putString("dist",user_upDist);
                        editor.putString("state",user_upState);
                        editor.commit();
                        BackGroundTaskUser backGroundTaskUser=new BackGroundTaskUser();
                        backGroundTaskUser.execute(id,user_upCity,user_upPhoneNumber);
                    }
                }
            });
        }
        else if(sharedPreferences.getBoolean("logged_serviceman",false))
        {
           final String serviceman_id =sharedPreferences.getString("id","id");
            String serviceman_name =sharedPreferences.getString("name","name");
            final String serviceman_email =sharedPreferences.getString("email","email");
            final String serviceman_PhoneNumber =sharedPreferences.getString("phone_number","phone_number");
            String serviceman_password=sharedPreferences.getString("password","password");
            String serviceman_homeTown =sharedPreferences.getString(serviceman_email+"home_town",serviceman_email+"home_town");
            final String serviceman_dist=sharedPreferences.getString(serviceman_email+"dist",serviceman_email+"dist");
            final String serviceman_state=sharedPreferences.getString(serviceman_email+"state",serviceman_email+"state");//getting serviceman_details from serviceman session
            textViewName.setText(serviceman_name);
            textViewEmail.setText(serviceman_email);
            textViewPassword.setText(serviceman_password);
            editTextPhone.setText(serviceman_PhoneNumber);
            editTextCity.setText(serviceman_homeTown);
            editTextDist.setText(serviceman_dist);
            editTextState.setText(serviceman_state);

           buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String serviceman_upPhoneNumber=editTextPhone.getText().toString();
                   String serviceman_upCity=editTextCity.getText().toString();
                   String serviceman_upState=editTextState.getText().toString();
                   String serviceman_upDist=editTextDist.getText().toString();
                   if(serviceman_upPhoneNumber.equals("")||serviceman_upCity.equals("")||serviceman_upDist.equals("")||serviceman_upState.equals(""))
                   {
                       Toast.makeText(getApplicationContext(),"Invalid Fields",Toast.LENGTH_SHORT).show();
                   }
                   else
                   {

                       SharedPreferences.Editor editor=sharedPreferences.edit();
                       editor.putString(serviceman_email+"home_town",serviceman_upCity);
                       editor.putString("phone_number",serviceman_upPhoneNumber);
                       editor.putString(serviceman_email+"dist",serviceman_upDist);
                       editor.putString(serviceman_email+"state",serviceman_upState);
                       editor.commit();
                       BackGroundTaskServiceman backGroundTaskServiceman=new BackGroundTaskServiceman();
                       backGroundTaskServiceman.execute(serviceman_id,serviceman_upCity,serviceman_upDist,serviceman_upState,serviceman_upPhoneNumber);
                   }
               }
           });
        }


    }
   private class BackGroundTaskServiceman extends AsyncTask<String,Void,String>
    {
        String phpUrl;
        String jsonString;
        ProgressDialog progressDialog=new ProgressDialog(UpdateMyAccountInfo.this);
        @Override
        protected void onPreExecute() {
            phpUrl="https://androiddbepizycom.000webhostapp.com/Home Service/UpdateServicemanAccountInfo.php";
            progressDialog.setMessage("Loading....");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String serviceman_id=args[0];
            String serviceman_city=args[1];
            String serviceman_dist=args[2];
            String serviceman_state=args[3];
            String serviceman_phoneNumber=args[4];
            try {
                URL url=new URL(phpUrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter= new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
                String dataString = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(serviceman_id,"UTF-8")+"&"+
                        URLEncoder.encode("city","UTF-8")+"="+URLEncoder.encode(serviceman_city,"UTF-8")+"&"+
                        URLEncoder.encode("dist","UTF-8")+"="+URLEncoder.encode(serviceman_dist,"UTF-8")+"&"+
                        URLEncoder.encode("state","UTF-8")+"="+URLEncoder.encode(serviceman_state,"UTF-8")+"&"+
                        URLEncoder.encode("phone_number","UTF-8")+"="+URLEncoder.encode(serviceman_phoneNumber,"UTF-8");
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
                    Intent intent=new Intent(UpdateMyAccountInfo.this,MainActivity.class);
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
   private class BackGroundTaskUser extends AsyncTask<String,Void,String>
    {
        String phpUrl;
        String jsonString;
        ProgressDialog progressDialog=new ProgressDialog(UpdateMyAccountInfo.this);
        @Override
        protected void onPreExecute() {
            phpUrl="https://androiddbepizycom.000webhostapp.com/Home Service/UpdateUserAccountInfo.php";
            progressDialog.setMessage("Loading....");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String user_id=args[0];
            String user_city=args[1];
            String user_phoneNumber=args[2];
            try {
                URL url=new URL(phpUrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter= new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
                String dataString = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8")+"&"+
                        URLEncoder.encode("phone_number","UTF-8")+"="+URLEncoder.encode(user_phoneNumber,"UTF-8")+"&"+
                        URLEncoder.encode("city","UTF-8")+"="+URLEncoder.encode(user_city,"UTF-8");
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
                    Intent intent=new Intent(UpdateMyAccountInfo.this,MainActivity2.class);
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
