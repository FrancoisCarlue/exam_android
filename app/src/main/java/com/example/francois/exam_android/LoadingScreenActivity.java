package com.example.francois.exam_android;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoadingScreenActivity extends AppCompatActivity {

    private final static String TAG = "LoadingScreen";
    String id;
    JSONObject jsonDetails;

    //Introduce an delay
    private final int WAIT_TIME = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        Log.i(TAG, "Loading screen lancé");
        findViewById(R.id.mainSpinner1).setVisibility(View.VISIBLE);

        Intent myIntent = getIntent();
        id = myIntent.getStringExtra("id");

        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {
                loadFlux();
            }
        });
        background.start();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent i = new Intent(LoadingScreenActivity.this, DetailsPOI.class);
                i.putExtra("jsonDetails",jsonDetails.toString());
                startActivity(i);
                finish();
            }
        }, WAIT_TIME);
    }


    private void loadFlux(){
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL("http://voyage2.corellis.eu/api/v2/poi?id="+id);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            String line = convertStreamtoString(in);
            JSONObject jsonObject = new JSONObject(line);
            jsonDetails = jsonObject.getJSONArray("data").getJSONObject(0);

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
