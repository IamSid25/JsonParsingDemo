package com.iam_sid.jsonparsingdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG=MainActivity.class.getSimpleName();
    private ListView lv;

    public ArrayList<String> numbers=new ArrayList<String>();
    public ArrayList<String> emails=new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv=(ListView)findViewById(R.id.list);

        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void,Void,Void>{

        protected void onPreExecute(){
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"JSON data is downloading",Toast.LENGTH_SHORT).show();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            String url="http://api.androidhive.info/contacts/";
            String jsonStr=" ";
            try{
                HttpClient client =new DefaultHttpClient();
                HttpGet request =new HttpGet();
                request.setURI(new URI(url));
                HttpResponse response=client.execute(request);
                jsonStr= EntityUtils.toString(response.getEntity());
            }catch (MalformedURLException e){
                Log.e(TAG,"MalformedURLException: "+e.getMessage());
            }catch (ProtocolException e){
                Log.e(TAG,"ProtocolException: "+e.getMessage());
            }catch (IOException e){
                Log.e(TAG,"IOException: "+e.getMessage());
            }catch (Exception e){
                Log.e(TAG,"Exception: "+e.getMessage());
            }
            Log.e(TAG,"Response fro Url: "+jsonStr);
            if(jsonStr != null){
                try{
                    JSONObject jsonObj=new JSONObject(jsonStr);

                    JSONArray contacts=jsonObj.getJSONArray("contacts");

                    for(int i=0;i<contacts.length();i++)
                    {
                        JSONObject c= contacts.getJSONObject(i);
                        String id=c.getString("id");
                        String name=c.getString("name");
                        String email=c.getString("email");
                        String address=c.getString("address");
                        String gender=c.getString("gender");

                        JSONObject phone=c.getJSONObject("phone");
                        String mobile=phone.getString("mobile");
                        String home=phone.getString("home");
                        String office=phone.getString("office");

                        numbers.add(mobile);
                        emails.add(email);
                    }
                }catch (final JSONException e)
                {
                    Log.e(TAG,"JSON Parsing Error: "+e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Json parsing error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }else {
                Log.e(TAG,"Couldn't get JSON from Server");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Couln't get JSON from server, Check LogCat for possible errors ",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void Result) {
            super.onPostExecute(Result);
            lv.setAdapter(new CustomAdap(getApplicationContext(),numbers,emails));

        }
    }
}
