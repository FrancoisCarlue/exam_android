package com.example.francois.exam_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsPOI extends AppCompatActivity {

    private final static String TAG = "DetailsPOIActivity";
    String id;
    JSONObject jsonDetails;
    TextView tvTitle;
    TextView tvDescription;
    int nbMedia;
    ImageView imgViewLeft;
    ImageView imgViewMiddle;
    ImageView imgViewRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "DetailsPOI accessed");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_poi);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        imgViewLeft = findViewById(R.id.imgViewLeft);
        imgViewMiddle = findViewById(R.id.imgViewMiddle);
        imgViewRight = findViewById(R.id.imgViewRight);

        Intent myIntent = getIntent();

        Log.i(TAG, "json details : " + myIntent.getStringExtra("jsonDetails"));
        try {
            jsonDetails = new JSONObject(myIntent.getStringExtra("jsonDetails"));
            nbMedia = jsonDetails.getJSONArray("medias").length();
            tvTitle.setText(jsonDetails.getString("name"));
            tvDescription.setText(jsonDetails.getString("description"));
            if (nbMedia==1){
                Picasso.get().load(jsonDetails.getJSONArray("medias").getJSONObject(0).getString("url")).into(imgViewMiddle);
            }
            if (nbMedia==2){
                Picasso.get().load(jsonDetails.getJSONArray("medias").getJSONObject(0).getString("url")).into(imgViewLeft);
                Picasso.get().load(jsonDetails.getJSONArray("medias").getJSONObject(1).getString("url")).into(imgViewMiddle);
            }
            if (nbMedia>=3){
                Picasso.get().load(jsonDetails.getJSONArray("medias").getJSONObject(0).getString("url")).into(imgViewLeft);
                Picasso.get().load(jsonDetails.getJSONArray("medias").getJSONObject(1).getString("url")).into(imgViewMiddle);
                Picasso.get().load(jsonDetails.getJSONArray("medias").getJSONObject(2).getString("url")).into(imgViewRight);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
