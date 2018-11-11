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
                        Log.i(TAG, "POI reconnu dans case");
                        Intent intentPOI = new Intent(MainActivity.this,LoadingScreenActivity.class);
                        Log.i(TAG, "Intent créé");
                        intentPOI.putExtra("id",maBibliotheque.get(position).getId());
                        Log.i(TAG, "putExtra fait");
                        startActivity(intentPOI);
                        Log.i(TAG, "startactivity lancé");
                        break;
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

                String myURL = "";

                if (jsonTransfere.getJSONObject(i).getString("media").length()!=0 && jsonTransfere.getJSONObject(i).getString("media")!=null){
                    myURL = jsonTransfere.getJSONObject(i).getString("media");
                }
                else {
                    myURL = "https://st2.depositphotos.com/5777248/10534/v/950/depositphotos_105340898-stock-illustration-palm-tropical-tree-icon-isometric.jpg";
                }
                if (jsonTransfere.getJSONObject(i).has("id")) {
                    maBibliotheque.add(new Destination(jsonTransfere.getJSONObject(i).getString("type"), jsonTransfere.getJSONObject(i).getString("display"), jsonTransfere.getJSONObject(i).getString("id"), myURL));
                }
                if (jsonTransfere.getJSONObject(i).has("poi_id")) {
                    maBibliotheque.add(new Destination(jsonTransfere.getJSONObject(i).getString("type"), jsonTransfere.getJSONObject(i).getString("display"), jsonTransfere.getJSONObject(i).getString("poi_id"), myURL));
                }
                Log.i(TAG, "bibli remplie" + i);
            }
        } catch (JSONException e) {
            Log.i(TAG, "bibli non remplie");
            e.printStackTrace();
        }
    }
}

