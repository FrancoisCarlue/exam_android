package com.example.francois.exam_android;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    private final static String TAG = "MainActivity";
    List<Destination> maBibliotheque = new ArrayList<Destination>();
    JSONArray jsonTransfere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent = getIntent();

        Log.i(TAG, "json transfere : " + myIntent.getStringExtra("jsonTreated"));
        try {
            jsonTransfere = new JSONArray(myIntent.getStringExtra("jsonTreated"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.RemplirLaBibliotheque();

        ListView myListView = getListView();

        DestinationAdapter adapter = new DestinationAdapter(this, maBibliotheque);
        myListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (maBibliotheque.get(position).getType()){
                    case "POI":
                        Intent intentPOI = new Intent(MainActivity.this,DetailsPOI.class);
                        intentPOI.putExtra("id",maBibliotheque.get(position).getId());
                        startActivity(intentPOI);
                }
                Toast.makeText(getApplicationContext(), "vous avez cliqué sur un "+maBibliotheque.get(position).getType(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void RemplirLaBibliotheque() {
        maBibliotheque.clear();
        Log.i(TAG, "bibli accedee");
        try {
            for (int i = 0; i < jsonTransfere.length(); i++) {

                Log.i(TAG, "bibli remplie" + i);
                maBibliotheque.add(new Destination(jsonTransfere.getJSONObject(i).getString("type"), jsonTransfere.getJSONObject(i).getString("display"),jsonTransfere.getJSONObject(i).getString("id")));

            }
        } catch (JSONException e) {
            Log.i(TAG, "bibli non remplie");
            e.printStackTrace();
        }
    }
}

