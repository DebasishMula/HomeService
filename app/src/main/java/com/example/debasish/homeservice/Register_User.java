package com.example.debasish.homeservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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


public class Register_User extends Activity {
    private EditText et_name, et_mail, et_phone, et_password;
    //Declaring EditText Variable
    Button register_button, serviceman_register_button;
    //Declaring Button Variable
    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__user);
        et_name = (EditText) findViewById(R.id.register_user_name);
        et_mail = (EditText) findViewById(R.id.register_user_email);
        et_phone = (EditText) findViewById(R.id.register_user_phone);
        et_password = (EditText) findViewById(R.id.register_user_password);
        //connecting EditText View with EditText Object
        register_button = (Button) findViewById(R.id.button_register_user);
        serviceman_register_button = findViewById(R.id.button_register_as_a_serviceman);
        //connecting Button View with Button Object
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInfoFromUI();
                if (userInfo.getName().equals("") || userInfo.getEmail().equals("") || userInfo.getPhoneNumber().equals("") || userInfo.getPassword().equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields Invalid", Toast.LENGTH_SHORT).show();
                } else {
                    if (userInfo.getPhoneNumber().length() != 10) {
                        Toast.makeText(getApplicationContext(), "PhoneNumber Should be 10 Numbers", Toast.LENGTH_SHORT).show();
                    } else {
                        if (userInfo.getPassword().length() < 8) {
                            Toast.makeText(getApplicationContext(), "Password should be 8 or more than characters", Toast.LENGTH_SHORT).show();
                        } else {

                                 BackgroundTask  backgroundTask=new BackgroundTask();
                                 backgroundTask.execute(userInfo.getName(),userInfo.getEmail(),userInfo.getPhoneNumber(),userInfo.getPassword());
                        }
                    }
                }
            }
        });
        serviceman_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Register_User.this,Register_serviceman.class);
                startActivity(intent);
            }
        });


    }

    void getInfoFromUI() //creating getFromUI to get all the value from UI
    {
        String name = et_name.getText().toString();
        String email = et_mail.getText().toString();
        String phoneNumber = et_phone.getText().toString();
        String password = et_password.getText().toString();
        userInfo = new UserInfo(name, email, phoneNumber, password);//setting the values by calling the userInfo constructor with four args

    }


    class BackgroundTask extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog = new ProgressDialog(Register_User.this);
        //creating progressDialog
        String add_info_url;
        String json_String;
        @Override
        protected void onPreExecute() {
             add_info_url = "https://androiddbepizycom.000webhostapp.com/Home%20Service/User_Register.php";
            progressDialog.setMessage("loading....");
            progressDialog.show();


        }

        @Override
        protected String doInBackground(String... args) {

            String name=args[0];
            String email=args[1];
            String phoneNumber=args[2];
            String password=args[3];
            //retrieving data from args array given through execute method
            try {
                URL url=new URL(add_info_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_String= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                                    URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                                    URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phoneNumber,"UTF-8")+"&"+
                                    URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(data_String);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                json_String=bufferedReader.readLine();
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return json_String;

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
                String Message_result=jsonObject.getString("response");
                //getting response result from json object
                Toast.makeText(getApplicationContext(),Message_result,Toast.LENGTH_SHORT).show();
                if(Message_result.equals("Register Successfully"))
                {
                    progressDialog.dismiss();
                    Intent intent=new Intent(Register_User.this,a_login.class);
                    startActivity(intent);
                }
                else if(Message_result.equals("The Email Address is already exist"))
                {
                    progressDialog.dismiss();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
