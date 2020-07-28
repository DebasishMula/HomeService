package com.example.debasish.homeservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

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

public class ChangePassword extends AppCompatActivity {
    EditText editTextOld,editTextNew,editTextConfirm;
    Button changeButton;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        editTextOld=findViewById(R.id.ch_oldPassword);
        editTextNew=findViewById(R.id.ch_newPassword);
        editTextConfirm=findViewById(R.id.ch_confirmPassword);
        changeButton=findViewById(R.id.ch_changePasswordButton);
        toolbar=findViewById(R.id.toolbar_ch_password);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        final SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPreferences.getBoolean("logged_user",false))
                {
                    String id=sharedPreferences.getString("id","id");
                    String password=sharedPreferences.getString("password","password");
                    String oldPassword=editTextOld.getText().toString();
                    String newPassword=editTextNew.getText().toString();
                    String confirmPassword=editTextConfirm.getText().toString();
                    if(oldPassword.equals("")||newPassword.equals("")||confirmPassword.equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "Invalid Fields", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(oldPassword.equals(password))
                        {
                            if(newPassword.length()>8) {

                                if (newPassword.equals(confirmPassword)) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("password", newPassword);
                                    editor.commit();
                                    BackGroundTaskUser backGroundTaskUser = new BackGroundTaskUser();
                                    backGroundTaskUser.execute(id, newPassword);
                                } else {
                                    Toast.makeText(getApplicationContext(), "New and Confirm Password are not Same", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Password Should be 8 or more than 8 character",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Put Old Password Correctly",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else if(sharedPreferences.getBoolean("logged_serviceman",false))
                {
                    String id=sharedPreferences.getString("id","id");
                    String password=sharedPreferences.getString("password","password");
                    String oldPassword=editTextOld.getText().toString();
                    String newPassword=editTextNew.getText().toString();
                    String confirmPassword=editTextConfirm.getText().toString();
                    if(oldPassword.equals("")||newPassword.equals("")||confirmPassword.equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "Invalid Fields", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(oldPassword.equals(password))
                        {
                            if(newPassword.equals(confirmPassword))
                            {
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("password",newPassword);
                                editor.commit();
                                BackGroundTaskServiceman backGroundTaskServiceman=new BackGroundTaskServiceman();
                                backGroundTaskServiceman.execute(id,newPassword);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"New and Confirm Password are not Same",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Put Old Password Correctly",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent_home=new Intent(ChangePassword.this,UpdateMyAccountInfo.class);
                startActivity(intent_home);

        }
        return true;
    }

    private class BackGroundTaskUser extends AsyncTask<String,Void,String>
    {
        String phpUrl;
        String jsonString;
        ProgressDialog progressDialog=new ProgressDialog(ChangePassword.this);
        @Override
        protected void onPreExecute() {
            phpUrl="https://androiddbepizycom.000webhostapp.com/Home%20Service/updateUserPassword.php";
            progressDialog.setMessage("Loading....");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String id=args[0];
            String password=args[1];
            try {
                URL url=new URL(phpUrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter= new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
                String dataString = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");

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
                if(MessageResult.equals("Password Updated Successfully"))
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),MessageResult,Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ChangePassword.this,UpdateMyAccountInfo.class);
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
    private class BackGroundTaskServiceman extends AsyncTask<String,Void,String>
    {
        String phpUrl;
        String jsonString;
        ProgressDialog progressDialog=new ProgressDialog(ChangePassword.this);
        @Override
        protected void onPreExecute() {
            phpUrl="https://androiddbepizycom.000webhostapp.com/Home%20Service/updateServicemanPassword.php";
            progressDialog.setMessage("Loading....");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String id=args[0];
            String password=args[1];
            try {
                URL url=new URL(phpUrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter= new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
                String dataString = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");

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
                if(MessageResult.equals("Password Updated Successfully"))
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),MessageResult,Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ChangePassword.this,UpdateMyAccountInfo.class);
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
