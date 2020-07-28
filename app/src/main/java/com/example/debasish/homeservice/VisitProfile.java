package com.example.debasish.homeservice;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class VisitProfile extends AppCompatActivity {
    ListView listView;
    List<UserInfo> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_profile);
        listView=findViewById(R.id.listView_of_user_who_visited_profile);


       SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);//checking if there is any login session
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String serviceman_id=sharedPreferences.getString("id","id");
        BackgroundTask backgroundTask=new BackgroundTask();
        backgroundTask.execute(serviceman_id);

    }
    class BackgroundTask extends AsyncTask<String,Void,String>
    {
        String PhpUrl;
        String JsonString;
        ProgressDialog progressDialog=new ProgressDialog(VisitProfile.this);
        @Override
        protected void onPreExecute() {
           PhpUrl="https://androiddbepizycom.000webhostapp.com/Home%20Service/retriving_user_who_visited_userServiceman.php";
           progressDialog.setMessage("Loading...");
           progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String serviceman_id=args[0];
            try {
                URL url=new URL(PhpUrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String dataString= URLEncoder.encode("serviceman_id","UTF-8")+"="+URLEncoder.encode(serviceman_id,"UTF-8");
                bufferedWriter.write(dataString);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();
                while ((JsonString=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(JsonString+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
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
            progressDialog.dismiss();
            try {
                JSONArray jsonArray=new JSONArray(result);
                int count=0;
                while (count<jsonArray.length())
                {
                    JSONObject jsonObject=jsonArray.getJSONObject(count);
                    String name=jsonObject.getString("name");
                    String email=jsonObject.getString("email_address");
                    String phone=jsonObject.getString("phone_number");
                    String town=jsonObject.getString("home_town");
                    UserInfo userInfo=new UserInfo(name,email,phone,town);
                  list.add(userInfo);
                    count++;

                }
                listView.setAdapter(new ArrayAdapterInVistProfile(VisitProfile.this,list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
