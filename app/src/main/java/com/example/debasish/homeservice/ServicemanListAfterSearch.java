package com.example.debasish.homeservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.List;


public class ServicemanListAfterSearch extends AppCompatActivity  {
     ListView listView;
     ContatsProvider contatsProvider=new ContatsProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceman_list_after_search);
        listView=findViewById(R.id.listView_of_servicemna);
        List<UserInfo>list=new ArrayList<>();
        listView.setAdapter(new ArrayAdapterInSearchServiceman(this,list));
        final Intent intent=getIntent();
        final String json_result =intent.getStringExtra("json_string");
        try {
            JSONArray jsonArray=new JSONArray(json_result);
            int count=0;
            while(count<jsonArray.length())
            {
                JSONObject jsonObject=jsonArray.getJSONObject(count);
                String id=jsonObject.getString("id");
                int integerID=Integer.parseInt(id);
                String name=jsonObject.getString("name");
                String email=jsonObject.getString("email");
                String phone_number=jsonObject.getString("phone_number");
                String search_place=jsonObject.getString("home_town");
                String skill_1=jsonObject.getString("skill_1");
                String skill_2=jsonObject.getString("skill_2");
                String skill_3=jsonObject.getString("skill_3");
                String skill_4=jsonObject.getString("skill_4");
                String skill_5=jsonObject.getString("skill_5");
                String skill_6=jsonObject.getString("skill_6");
                String skill_7=jsonObject.getString("skill_7");
                String search_field=jsonObject.getString("search_field");
                UserInfo userInfo=new UserInfo(integerID,name,email,phone_number,search_place,skill_1,skill_2,skill_3,skill_4,skill_5,skill_6,skill_7,search_field);
                list.add(userInfo);
                count ++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);//checking if there is any login session
            if (sharedPreferences.getBoolean("logged_user", false))//checking if there any user login session
            {
                UserInfo userInfo=(UserInfo)adapterView.getItemAtPosition(i);
                contatsProvider.setUserInfo(userInfo);
                SharedPreferences.Editor editor=sharedPreferences.edit();
               String user_id= sharedPreferences.getString("id","id");
               String serviceman_id=userInfo.getId();
              Background background=new Background();
              background.execute(user_id,serviceman_id);


            }
            else
            {
                Intent intent1=new Intent(ServicemanListAfterSearch.this,LogIn.class);
                startActivity(intent1);
               Toast.makeText(getApplicationContext(),"You Have't Signed In Yet",Toast.LENGTH_SHORT).show();
            }



            }
    });

    }

     public class Background extends AsyncTask<String,Void,String>
     {
         String phpurl;
         String json_output;
         ProgressDialog progressDialog=new ProgressDialog(ServicemanListAfterSearch.this);
         @Override
         protected void onPreExecute() {
             phpurl="https://androiddbepizycom.000webhostapp.com/Home%20Service/insert_id_who_visited_serviceman.php";
             progressDialog.setMessage("Loading....");
             progressDialog.show();
         }

         @Override
         protected String doInBackground(String... args) {
             String user_id=args[0];
             String serviceman_id=args[1];
             try {
                 URL url=new URL(phpurl);
                 HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                 httpURLConnection.setRequestMethod("POST");
                 OutputStream outputStream=httpURLConnection.getOutputStream();
                 BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                 String dataString= URLEncoder.encode("userId","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8")+"&"+
                         URLEncoder.encode("servicemanId","UTF-8")+"="+URLEncoder.encode(serviceman_id,"UTF-8");
                 bufferedWriter.write(dataString);
                 bufferedWriter.flush();
                 bufferedWriter.close();
                 InputStream inputStream=httpURLConnection.getInputStream();
                 BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                 json_output=bufferedReader.readLine();
                 bufferedReader.close();
                 inputStream.close();
                 httpURLConnection.disconnect();
                 return  json_output;

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
                 String responseResult=jsonObject.getString("response");
                 if(responseResult.equals("id added"))
                 {
                     progressDialog.dismiss();
                     UserInfo userInfo= contatsProvider.getUserInfo();
                     Intent intent=new Intent(ServicemanListAfterSearch.this,MessageToAndHireServiceman.class);
                     intent.putExtra("userInfo",userInfo);
                     startActivity(intent);

                 }
                 else
                 {
                     Toast.makeText(getApplicationContext(),"Invalid Request",Toast.LENGTH_SHORT).show();
                 }
             } catch (JSONException e) {
                 e.printStackTrace();
             }





         }
     }
     public class ContatsProvider
     {
       private  UserInfo userInfo;

         public void setUserInfo(UserInfo userInfo) {
             this.userInfo = userInfo;
         }

         public UserInfo getUserInfo() {
             return userInfo;
         }
     }

}
