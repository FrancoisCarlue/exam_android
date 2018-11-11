package com.example.francois.exam_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailsPOI extends AppCompatActivity {

    private final static String TAG = "DetailsPOIActivity";
    String id;
    TextView tvTitle;
    TextView tvDescription;
    ImageView imgViewLeft;
    ImageView imgViewMiddle;
    ImageView imgViewRight;
    int nbMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_poi);

        Intent myIntent = getIntent();
        id = myIntent.getStringExtra("id");

        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {
                loadFlux();
            }
        });
        background.start();

    }


    private void loadFlux(){
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL("http://voyage2.corellis.eu/api/v2/poi?id="+id);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            String line = convertStreamtoString(in);
            JSONObject jsonObject = new JSONObject(line);

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
