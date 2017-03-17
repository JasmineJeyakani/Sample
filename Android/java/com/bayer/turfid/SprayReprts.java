package com.bayer.turfid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jasmine Jeyakani on 25-Oct-16.
 */

public class SprayReprts extends Activity {
    ListView spraylist;
    HashMap<String, String> val;
    ArrayList<HashMap<String, String>> oslist;
    ArrayList<HashMap<String, String>> oslist1;
    ImageView imgLogo, mailbtn, closebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sprayrprts);

        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SprayReprts.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });
        spraylist = (ListView) findViewById(R.id.spraylist);
        oslist = new ArrayList<HashMap<String, String>>();
        oslist1 = new ArrayList<HashMap<String, String>>();
        final DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        oslist = db.samp();
        ListAdapter adapter = new SimpleAdapter(
                this, oslist, R.layout.spraylist_item, new String[]{"datefinal", "area"}, new int[]{R.id.textView7, R.id.textView6});


        spraylist.setAdapter(adapter);


        spraylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent i = new Intent(SprayReprts.this, SprayReport5.class);
                i.putExtra("pos", position);
                startActivity(i);


            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SprayReprts.this, BayerTurfManagementActivity.class);
        startActivity(intent);
        finish();

    }
}
