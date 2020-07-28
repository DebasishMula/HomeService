package com.example.debasish.homeservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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


public class Register_serviceman extends Activity {
    EditText et_name,et_email,et_phoneNumber,et_homeTown,et_password;
    CheckBox chkHP,chkRR,chkEletric,chkWMR,chkPlum,chkCarpent,chkMason;
    Button TC,registerButton;
    UserInfo userInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_serviceman);
        et_name=(EditText)findViewById(R.id.register_serviceman_name);
        et_email=(EditText)findViewById(R.id.register_serviceman_email);
        et_phoneNumber=(EditText)findViewById(R.id.register_serviceman_phone_number);
        et_homeTown=(EditText)findViewById(R.id.register_serviceman_home_town);
        et_password=(EditText)findViewById(R.id.register_serviceman_password);
        //connecting EditText view with EditText object
        chkHP=(CheckBox)findViewById(R.id.register_house_painting);
        chkRR=(CheckBox)findViewById(R.id.register_refrgerator_repairing);
        chkEletric=(CheckBox)findViewById(R.id.register_electrician);
        chkWMR=(CheckBox)findViewById(R.id.register_wasingmachine_repairing);
        chkPlum=(CheckBox)findViewById(R.id.register__plumbing);
        chkCarpent=(CheckBox)findViewById(R.id.register_carpenting);
        chkMason=(CheckBox)findViewById(R.id.register_mason);
        //connecting CheckBox view with CheckBox object
        TC=(Button)findViewById(R.id.register_serviceman_TC);
        registerButton=(Button)findViewById(R.id.button_register_serviceman);
        //connecting Button view with Button object
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInfoFromUI();//call the method getInfoFromUI
                if (userInfo.getName().equals("") || userInfo.getEmail().equals("") || userInfo.getPhoneNumber().equals("") || userInfo.getHomeTown().equals("") || userInfo.getPassword().equals(""))//code for validation is started
                {
                    Toast.makeText(getApplicationContext(), "Fields Invalid", Toast.LENGTH_SHORT).show();
                } else {
                    if (userInfo.getPhoneNumber().length() != 10) {
                        Toast.makeText(getApplicationContext(), "Phone number should be 10 numbers", Toast.LENGTH_SHORT).show();
                    } else {


                        if (userInfo.getSkill1().equals("") && userInfo.getSkill2().equals("") && userInfo.getSkill3().equals("") && userInfo.getSkill4().equals("") && userInfo.getSkill5().equals("") && userInfo.getSkill6().equals("") && userInfo.getSkill7().equals("")) {
                            Toast.makeText(getApplicationContext(), "Add at least one skill", Toast.LENGTH_SHORT).show();
                        } else {
                            if (userInfo.getPassword().length() < 8) {
                                Toast.makeText(getApplicationContext(), "Password should be with 8 or more than 8 characters", Toast.LENGTH_SHORT).show();
                            } else {
                                BackgroundTask backgroundTask = new BackgroundTask();
                                backgroundTask.execute(userInfo.getName(), userInfo.getEmail(), userInfo.getPhoneNumber(), userInfo.getHomeTown(), userInfo.getPassword(), userInfo.getSkill1(), userInfo.getSkill2(), userInfo.getSkill3(), userInfo.getSkill4(), userInfo.getSkill5(), userInfo.getSkill6(), userInfo.getSkill7());
                                // calling execute method of background class and giving all data


                            }
                        }
                    }
                }
            }
        });

    }
    public void getInfoFromUI()//creating a method to get all data from UI
    {

        String name = et_name.getText().toString();
        String email = et_email.getText().toString();
        String phoneNumber = et_phoneNumber.getText().toString();
        String homeTown = et_homeTown.getText().toString();
        String password = et_password.getText().toString();  //get  data from editText UI
        String skill1,skill2,skill3,skill4,skill5,skill6,skill7;
        if (chkHP.isChecked())
        {
             skill1 = chkHP.getText().toString();
        } else {
             skill1 = "";
        }
        if (chkRR.isChecked()) {
             skill2 = chkRR.getText().toString();
        } else {
            skill2 = "";
        }
        if (chkEletric.isChecked()) {
            skill3 = chkEletric.getText().toString();
        } else {
             skill3 = "";
        }
        if (chkWMR.isChecked()) {
             skill4 = chkWMR.getText().toString();
        } else {
             skill4 = "";
        }
        if (chkPlum.isChecked()) {
             skill5 = chkPlum.getText().toString();
        } else {
            skill5 = "";
        }
        if (chkCarpent.isChecked()) {
            skill6 = chkCarpent.getText().toString();
        } else {
            skill6 = "";
        }

        if (chkMason.isChecked()) {
            skill7 = chkMason.getText().toString();
        } else {
             skill7 = "";
        }
        //get data from checkBox UI
        userInfo=new UserInfo(name,email,phoneNumber,homeTown,password,skill1,skill2,skill3,skill4,skill5,skill6,skill7);
        //userInfo constructor calling
    }
    class BackgroundTask extends AsyncTask<String,Void,String>
    {
        String add_info_url;
        String json_string;
        private ProgressDialog progressDialog=new ProgressDialog(Register_serviceman.this);
        //creating progress dialog
        @Override
        protected void onPreExecute() {
            add_info_url="https://androiddbepizycom.000webhostapp.com/Home%20Service/User_Serviceman_Register.php";
            progressDialog.setMessage("loading....");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String name=args[0];
            String email=args[1];
            String phoneNumber=args[2];
            String homeTown=args[3];
            String password=args[4];
            String skill1=args[5];
            String skill2=args[6];
            String skill3=args[7];
            String skill4=args[8];
            String skill5=args[9];
            String skill6=args[10];
            String skill7=args[11];
           //retrieving data from args array given through execute method
            try {
                URL url=new URL(add_info_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string=URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("phoneNumber","UTF-8")+"="+URLEncoder.encode(phoneNumber,"UTF-8")+"&"+
                        URLEncoder.encode("homeTown","UTF-8")+"="+URLEncoder.encode(homeTown,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                        URLEncoder.encode("skill1","UTF-8")+"="+URLEncoder.encode(skill1,"UTF-8")+"&"+
                        URLEncoder.encode("skill2","UTF-8")+"="+URLEncoder.encode(skill2,"UTF-8")+"&"+
                        URLEncoder.encode("skill3","UTF-8")+"="+URLEncoder.encode(skill3,"UTF-8")+"&"+
                        URLEncoder.encode("skill4","UTF-8")+"="+URLEncoder.encode(skill4,"UTF-8")+"&"+
                        URLEncoder.encode("skill5","UTF-8")+"="+URLEncoder.encode(skill5,"UTF-8")+"&"+
                        URLEncoder.encode("skill6","UTF-8")+"="+URLEncoder.encode(skill6,"UTF-8")+"&"+
                        URLEncoder.encode("skill7","UTF-8")+"="+URLEncoder.encode(skill7,"UTF-8");
                bufferedWriter.write(data_string);
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
                JSONObject jsonObject=new JSONObject(json_string);
                String Message_result=jsonObject.getString("response");
                //getting string result from json_array string

                Toast.makeText(getApplicationContext(),Message_result,Toast.LENGTH_SHORT).show();
                //Toast message of result
                if(Message_result.equals("Registered Successfully"))
                {
                    progressDialog.dismiss();
                    //progress dialog will be dismiss

                    Intent intent=new Intent(Register_serviceman.this,a_login.class);
                    startActivity(intent);
                }
                else if (Message_result.equals("Email ID is already exist"))
                {
                    progressDialog.dismiss();
                    //progress dialog will be dismiss
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

    }



}
