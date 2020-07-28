package com.example.debasish.homeservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class Search_Serviceman extends AppCompatActivity {
     Spinner fieldSpinner,placeSpinner;
     Button Searchbutton;
     Toolbar toolbar;
    UserInfo userInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__serviceman);
        fieldSpinner=findViewById(R.id.SearchFieldSpinner);
        placeSpinner=findViewById(R.id.SearchPlaceSpinner);
        Searchbutton=findViewById(R.id.searchbutton);
        toolbar = findViewById(R.id.toolbar_in_search);//connecting toolbar  view with toolbar object
        setSupportActionBar(toolbar); //calling method to support actionbar with toolbar
        ActionBar actionBar = getSupportActionBar();//initializing actionbar
        actionBar.setDisplayHomeAsUpEnabled(true);
       actionBar.setHomeAsUpIndicator(R.drawable.ic_back);//setting actionbar indicator
        userInfo=new UserInfo();
      ArrayAdapter  fieldArrayAdapter=ArrayAdapter.createFromResource(this,R.array.Service_field,android.R.layout.simple_spinner_item);
        fieldArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //creating ArrayAdapter instance that contains serviceField list
        ArrayAdapter placeArrayAdapter=ArrayAdapter.createFromResource(this,R.array.Service_Place,android.R.layout.simple_spinner_item);
        placeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //creating ArrayAdapter instance that contains place list
        fieldSpinner.setAdapter(fieldArrayAdapter);
        fieldSpinner.setOnItemSelectedListener(new FieldSpinner());
        placeSpinner.setAdapter(placeArrayAdapter);
        placeSpinner.setOnItemSelectedListener(new PlaceSpinner());
       Searchbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Background background=new Background();
               background.execute(userInfo.getSkill1(),userInfo.getHomeTown());

           }
       });
    }

    class FieldSpinner implements AdapterView.OnItemSelectedListener
    {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String selectedField=adapterView.getItemAtPosition(i).toString();
            userInfo.setSkill1(selectedField);

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
    class PlaceSpinner implements AdapterView.OnItemSelectedListener
    {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String selectedPlace=adapterView.getItemAtPosition(i).toString();
            userInfo.setHomeTown(selectedPlace);

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
    class Background extends AsyncTask<String,Void,String>
    {
        String phpUrl;
        String JsonString;
        private ProgressDialog progressDialog=new ProgressDialog(Search_Serviceman.this);
        //creating progress dialog
        @Override
        protected void onPreExecute() {
             phpUrl="https://androiddbepizycom.000webhostapp.com/Home%20Service/SearchServiceman.php";
             progressDialog.setMessage("loading....");
             progressDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String field=args[0];
            String place=args[1];
            try {
                URL url=new URL(phpUrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String dataString= URLEncoder.encode("town","UTF-8")+"="+URLEncoder.encode(place,"UTF-8")+"&"+
                        URLEncoder.encode("field","UTF-8")+"="+URLEncoder.encode(field,"UTF-8");
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
            if(result.equals("[]"))
            {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"No "+userInfo.getSkill1()+" available in "+userInfo.getHomeTown(),Toast.LENGTH_LONG).show();
            }
            else
            {
                progressDialog.dismiss();
                Intent intent=new Intent(Search_Serviceman.this,ServicemanListAfterSearch.class);
                intent.putExtra("json_string",result);
                startActivity(intent);

            }



        }
    }
}

