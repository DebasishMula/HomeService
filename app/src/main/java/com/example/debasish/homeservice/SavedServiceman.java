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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SavedServiceman extends AppCompatActivity {
    ListView listView;
    List<HiringServicemanInfo> list=new ArrayList<HiringServicemanInfo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_serviceman);
        listView=findViewById(R.id.listView_of_savedServiceman);
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);//checking if there is any login session
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String user_id=sharedPreferences.getString("id","id");
        BackgroundTaskInSavedServiceman backgroundTaskInSavedServiceman=new BackgroundTaskInSavedServiceman();
        backgroundTaskInSavedServiceman.execute(user_id);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HiringServicemanInfo hiringServicemanInfo=(HiringServicemanInfo)adapterView.getItemAtPosition(i);
                Intent intent=new Intent(SavedServiceman.this,ViewSavedServiceman.class);
                intent.putExtra("hiringServicemanInfo",hiringServicemanInfo);
                startActivity(intent);
            }
        });
    }
    class BackgroundTaskInSavedServiceman extends AsyncTask<String,Void,String>
    {
        String PhpUrl;
        String JsonString;
        ProgressDialog progressDialog=new ProgressDialog(SavedServiceman.this);
        @Override
        protected void onPreExecute() {
            PhpUrl="https://androiddbepizycom.000webhostapp.com/Home%20Service/SavedServiceman.php";
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String user_id=args[0];
            try {
                URL url=new URL(PhpUrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String dataString= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8");
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
                    String services_id=jsonObject.getString("services_id");
                    String serviceman_id=jsonObject.getString("serviceman_id");
                    String serviceman_name=jsonObject.getString("serviceman_name");
                    String serviceman_phone_number=jsonObject.getString("serviceman_phone_number");
                    String serviceman_home_town=jsonObject.getString("serviceman_home_town");
                    String working_date=jsonObject.getString("working_date");
                    String working_address=jsonObject.getString("working_address");
                    String working_descrip=jsonObject.getString("working_descrip");
                    HiringServicemanInfo hiringServicemanInfo=new HiringServicemanInfo(services_id,serviceman_id,serviceman_name,serviceman_phone_number,serviceman_home_town,working_date,working_address,working_descrip);

                    list.add(hiringServicemanInfo);
                    count++;

                }
                listView.setAdapter(new ArrayAdapterInSavedTechnician(SavedServiceman.this,list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
