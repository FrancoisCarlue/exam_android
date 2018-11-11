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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "DetailsPOI accessed");
        super.onCreate(savedInstanceState);

        Intent myIntent = getIntent();

        Log.i(TAG, "json details : " + myIntent.getStringExtra("jsonDetails"));
        try {
            jsonDetails = new JSONObject(myIntent.getStringExtra("jsonDetails"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);

    }
}
