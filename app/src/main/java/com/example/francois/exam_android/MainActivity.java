package com.example.francois.exam_android;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
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

import java.net.URI;
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

                    case "RESTAURANT":
                        Log.i(TAG, "RESTAURANT reconnu dans case");
                        Intent intentRESTAURANT = null;
                        try {
                            intentRESTAURANT = new Intent(Intent.ACTION_VIEW,Uri.parse(jsonTransfere.getJSONObject(position).getString("web")));
                            intentRESTAURANT.putExtra("id",maBibliotheque.get(position).getId());
                            Log.i(TAG, "Intent créé");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "putExtra fait");
                        startActivity(intentRESTAURANT);
                        Log.i(TAG, "startactivity lancé");
                        break;

                    case "HOTEL":
                        Log.i(TAG, "HOTEL reconnu dans case");
                        Intent intentHOTEL = null;
                        try {
                            intentHOTEL = new Intent(Intent.ACTION_VIEW,Uri.parse(jsonTransfere.getJSONObject(position).getString("web")));
                            intentHOTEL.putExtra("id",maBibliotheque.get(position).getId());
                            Log.i(TAG, "Intent créé");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "putExtra fait");
                        startActivity(intentHOTEL);
                        Log.i(TAG, "startactivity lancé");
                        break;
                }
            }
        });
    }

    private void RemplirLaBibliotheque() {
        maBibliotheque.clear();
        Log.i(TAG, "bibli accedee");
        try {
            for (int i = 0; i < jsonTransfere.length(); i++) {

                String myURL = "";
                double distance = -666.66; //code si jamais la distance n'existe pas
                if (jsonTransfere.getJSONObject(i).has("distance")){
                    distance = jsonTransfere.getJSONObject(i).getDouble("distance");
                }
                if (jsonTransfere.getJSONObject(i).getString("media").length()!=0 && jsonTransfere.getJSONObject(i).getString("media")!=null){
                    myURL = jsonTransfere.getJSONObject(i).getString("media");
                }
                else {
                    myURL = "https://st2.depositphotos.com/5777248/10534/v/950/depositphotos_105340898-stock-illustration-palm-tropical-tree-icon-isometric.jpg";
                }
                if (jsonTransfere.getJSONObject(i).has("id")) {
                    maBibliotheque.add(new Destination(jsonTransfere.getJSONObject(i).getString("type"), jsonTransfere.getJSONObject(i).getString("display"), jsonTransfere.getJSONObject(i).getString("id"), myURL,distance));
                }
                if (jsonTransfere.getJSONObject(i).has("poi_id")) {
                    maBibliotheque.add(new Destination(jsonTransfere.getJSONObject(i).getString("type"), jsonTransfere.getJSONObject(i).getString("display"), jsonTransfere.getJSONObject(i).getString("poi_id"), myURL,distance));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

