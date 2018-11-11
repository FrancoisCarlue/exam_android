package com.example.francois.exam_android;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
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
    JSONObject jsonDetails;
    TextView tvTitle;
    TextView tvDescription;
    ImageView imgViewLeft;
    ImageView imgViewMiddle;
    ImageView imgViewRight;
    int nbMedia;

    private static int LOADING_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "DetailsPOI accessed");
        super.onCreate(savedInstanceState);

        Intent myIntent = getIntent();
        Log.i(TAG, "getINtent fait");
        id = myIntent.getStringExtra("id");

        Log.i(TAG, "getExtra fait");

        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);

        Log.i(TAG, "findviewbyID fait");

        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {
                loadFlux();
            }
        });
        background.start();

        Log.i(TAG, "thread background fait");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.i(TAG,jsonDetails.toString());

                try {
                    tvTitle.setText(jsonDetails.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(TAG, "erreur catch string name");
                }

                Log.i(TAG,"premie try catch passé");

                try {
                    tvDescription.setText(jsonDetails.getString("description"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(TAG, "erreur catch string description");
                }

                Log.i(TAG,"2eùe try catch passé");

                Picasso.get().load("https://www.jardindupicvert.com/19629-large_default/palmier-de-chine.jpg").into(imgViewLeft);


                /*try {
                    Log.i(TAG,"entré dans 3eme try catch");
                    Picasso.get().load(jsonDetails.getJSONArray("medias").getJSONObject(0).getString("url")).into(imgViewLeft);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(TAG, "erreur catch media");
                }*/

                Log.i(TAG,"3eme try catch passé");
                setContentView(R.layout.activity_details_poi);
            }
        }, LOADING_TIME_OUT);
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
