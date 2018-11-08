package com.example.francois.exam_android;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashScreen extends AppCompatActivity {

    private final static String TAG = "NetworkActivity";
    JSONObject jsonTreated = new JSONObject();

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {
                loadFlux();
            }
        });
        background.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                i.putExtra("jsonTreated",jsonTreated.toString());
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

        private void loadFlux(){
            HttpURLConnection urlConnection = null;
            try{
                URL url = new URL("http://voyage2.corellis.eu/api/v2/homev2?lat=43.14554197717751&lon=6.00.246207789145&offset=0");
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                String line = convertStreamtoString(in);
                JSONObject jsonObject = new JSONObject(line);
                int compteur = 1;

                for(int i=0;i<jsonObject.getJSONArray("data").length();i++){
                    if ((jsonObject.getJSONArray("data").getJSONObject(i).getString("type").equals("Hotel")) || (jsonObject.getJSONArray("data").getJSONObject(i).getString("type").equals("restaurant")) || (jsonObject.getJSONArray("data").getJSONObject(i).getString("type").equals("city")) || (jsonObject.getJSONArray("data").getJSONObject(i).getString("type").equals("POI"))){
                        jsonTreated.put("element "+compteur,jsonObject.getJSONArray("data").getJSONObject(i));
                        compteur = compteur +1;
                    }
                }
                Log.i(TAG,"Number of entries" + jsonObject.length());
                Log.i(TAG,"Number of locations before" + jsonObject.getJSONArray("data").length());
                Log.i(TAG,"Number of locations after" + jsonTreated.length());
                Log.i(TAG,"Recupere" + jsonTreated.toString());
            }
            catch (MalformedURLException e){
                Log.e(TAG,e.toString());
            }
            catch (IOException e){
                Log.e(TAG,e.toString());
            }
            catch (Exception e){
                Log.e(TAG,e.toString());
            }
            finally {
                urlConnection.disconnect();
            }
        }

        private String convertStreamtoString(InputStream is){
            String line = "";
            StringBuilder total = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            try {
                while ((line = rd.readLine()) != null) {
                    total.append(line);
                }
            }
            catch (Exception e) {
                Toast.makeText(this, "Stream Exception", Toast.LENGTH_SHORT).show();
            }
            return total.toString();
        }



}
