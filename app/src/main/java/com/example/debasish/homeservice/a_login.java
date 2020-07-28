package com.example.debasish.homeservice;

import android.app.Activity;
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

public class a_login extends Activity{
    EditText et_email,et_password;
    Button button_login;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_login);
        et_email=findViewById(R.id.serviceman_login_email);
        et_password=findViewById(R.id.serviceman_login_password);
        button_login=findViewById(R.id.serviceman_login_buttom);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 UserInfo userInfo=getInfoFromUI(); //calling the getInfoFromUI() method
                if(userInfo.getEmail().equals("")||userInfo.getPassword().equals(""))//checking if there any field is invalid
                {
                    Toast.makeText(getApplicationContext(),"Fields Invalid",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (userInfo.getPassword().length()<8)//checking if there password  field is less than 8 characters
                    {
                        Toast.makeText(getApplicationContext(),"Password Should be 8 or more than 8 character",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                          BackgroundTask backgroundTask=new BackgroundTask();
                          backgroundTask.execute(userInfo.getEmail(),userInfo.getPassword());
                        // calling execute method of background class and giving all data
                    }
                }
            }
        });
    }
    public UserInfo getInfoFromUI()//creating a method whose return type UserInfo
    {
        String email=et_email.getText().toString();//getting values from UI
        String password=et_password.getText().toString();//getting values from UI
        return new UserInfo(email,password); //calling the constructor to construct values and return UserInfo type variable
    }
     class BackgroundTask extends AsyncTask<String,Void,String>
    {
        String add_info_url;
        String json_string;
        private ProgressDialog progressDialog=new ProgressDialog(a_login.this);
        @Override
        protected void onPreExecute() {
           add_info_url="https://androiddbepizycom.000webhostapp.com/Home%20Service/Serviceman_logIn.php";
           progressDialog.setMessage("Loading....");
           progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String email=args[0];
            String password=args[1];
            try {
                URL url=new URL(add_info_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String dataString= URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(dataString);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                json_string=bufferedReader.readLine();
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return json_string;

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
                JSONArray jsonArray=new JSONArray(result);//getting json array from json string
                JSONObject jsonObject1=jsonArray.getJSONObject(0);//retrieving the 1st json object fro  json array
                String Message_result=jsonObject1.getString("response");//getting response message from 1st json object
                if(Message_result.equals("LogIn Successfully"))
                {
                    JSONObject jsonObject2=jsonArray.getJSONObject(1);//retrieving the 2nd json object fro  json array
                    String id=jsonObject2.getString("id");
                    String name=jsonObject2.getString("name");
                    String password=jsonObject2.getString("password");
                    String phone_number=jsonObject2.getString("phone_number");
                    String city=jsonObject2.getString("city") ;//getting the values from the 2nd json object
                    UserInfo userInfo=getInfoFromUI();
                    progressDialog.dismiss();
                    sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
                    sharedPreferences.edit().putBoolean("logged_user",true).apply();
                    SharedPreferences.Editor editor=sharedPreferences.edit();

                    Intent intent=new Intent(a_login.this,MainActivity2.class);
                    editor.putString("id",id);
                    editor.putString("name",name);
                    editor.putString("email",userInfo.getEmail());
                    editor.putString("phone_number",phone_number);
                    editor.putString("password",password);
                    editor.putString("city",city);
                    editor.commit();
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),Message_result,Toast.LENGTH_LONG).show();
                }

               else if(Message_result.equals("LogIn Successful"))
                {
                    JSONObject jsonObject2=jsonArray.getJSONObject(1);
                    JSONObject jsonObject3=jsonArray.getJSONObject(2);
                    String id=jsonObject2.getString("id");
                    String name=jsonObject2.getString("name");
                    String phone_number=jsonObject2.getString("phone_number");
                    String home_town=jsonObject2.getString("home_town");
                    String skill_1=jsonObject2.getString("skill_1");
                    String skill_2=jsonObject2.getString("skill_2");
                    String skill_3=jsonObject2.getString("skill_3");
                    String skill_4=jsonObject2.getString("skill_4");
                    String skill_5=jsonObject2.getString("skill_5");
                    String skill_6=jsonObject2.getString("skill_6");
                    String skill_7=jsonObject2.getString("skill_7");
                    String image=jsonObject2.getString("image");//getting the values from the 2nd json object
                    String city=jsonObject3.getString("city");
                    String dist=jsonObject3.getString("dist");
                    String state=jsonObject3.getString("state");
                    String no_of_visits=jsonObject3.getString("no_of_visits");
                    UserInfo userInfo=getInfoFromUI();
                   sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
                    sharedPreferences.edit().putBoolean("logged_serviceman",true).apply();
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    Intent intent=new Intent(a_login.this,MainActivity.class);

                    progressDialog.dismiss();

                    editor.putString("id",id);
                    editor.putString("name",name);
                    editor.putString("email",userInfo.getEmail());
                    editor.putString("phone_number",phone_number);
                    editor.putString(userInfo.getEmail()+"home_town",city);
                    editor.putString("image",image);
                    editor.putString("password",userInfo.getPassword());
                    editor.putString("skill_1",skill_1);
                    editor.putString("skill_2",skill_2);
                    editor.putString("skill_3",skill_3);
                    editor.putString("skill_4",skill_4);
                    editor.putString("skill_5",skill_5);
                    editor.putString("skill_6",skill_6);
                    editor.putString("skill_7",skill_7);
                    editor.putString(userInfo.getEmail()+"dist",dist);
                    editor.putString(userInfo.getEmail()+"state",state);
                    editor.putString("no_of_visits",no_of_visits);
                    editor.commit();
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),Message_result,Toast.LENGTH_SHORT).show();
                }
               else if(Message_result.equals("Invalid Details"))
                {
                    Toast.makeText(getApplicationContext(),Message_result,Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
