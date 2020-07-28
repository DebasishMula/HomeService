package com.example.debasish.homeservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class service_alerts extends AppCompatActivity {
     ListView listView;
     List<HiringServicemanInfo> list=new ArrayList<HiringServicemanInfo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_alerts);
        listView=findViewById(R.id.listView_of_user_who_wants_to_hire);

        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);//checking if there is any login session
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String serviceman_id=sharedPreferences.getString("id","id");
        BackgroundTask backgroundTask=new BackgroundTask();
        backgroundTask.execute(serviceman_id);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HiringServicemanInfo hiringServicemanInfo=(HiringServicemanInfo)adapterView.getItemAtPosition(i);
                Intent intent=new Intent(service_alerts.this,AcceptAndRejectHiringRequest.class);
                intent.putExtra("hiringServicemanInfo",hiringServicemanInfo);
                startActivity(intent);
            }
        });
    }
    class BackgroundTask extends AsyncTask<String,Void,String>
    {
        String PhpUrl;
        String JsonString;
        ProgressDialog progressDialog=new ProgressDialog(service_alerts.this);
        @Override
        protected void onPreExecute() {
            PhpUrl="https://androiddbepizycom.000webhostapp.com/Home%20Service/retriving_user_who_wants_to_hire.php";
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
                    String hiring_serviceman_id=jsonObject.getString("hiring_serviceman_id");
                    String user_id=jsonObject.getString("user_id");
                    String user_name=jsonObject.getString("user_name");
                    String user_phone_number=jsonObject.getString("user_phone_number");
                    String user_home_town=jsonObject.getString("user_home_town");
                    String working_date=jsonObject.getString("working_date");
                    String working_assress=jsonObject.getString("working_address");
                    String working_descrip=jsonObject.getString("working_descrip");
                    HiringServicemanInfo hiringServicemanInfo=new HiringServicemanInfo(hiring_serviceman_id,user_id,user_name,user_phone_number,user_home_town,working_date,working_assress,working_descrip);

                    list.add(hiringServicemanInfo);
                    count++;

                }
                listView.setAdapter(new ArrayAdapterInServiceAlerts(service_alerts.this,list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
