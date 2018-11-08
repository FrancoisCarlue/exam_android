package com.example.francois.exam_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent myIntent = getIntent();
        Log.i(TAG,"json transfere : "+ myIntent.getStringExtra("jsonTreated"));
        try {
            JSONObject jsonTransfere = new JSONObject(myIntent.getStringExtra("jsonTreated"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
