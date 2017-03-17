package com.bayer.turfid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class MappingActivityCalc extends Activity {

    TextView walk;
    TextView mark;
    String clubName;
    String areaName;
    ImageView imgLogo;
    SharedPreferences Prefrence;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping_calc);
        Prefrence = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        walk = (TextView) findViewById(R.id.walkTxt);
        mark = (TextView) findViewById(R.id.markTxt);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MappingActivityCalc.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });


        clubName = Prefrence.getString("golfName", "golf");
        areaName = Prefrence.getString("areaName", "area");

        walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
//
                Intent gpsIntent = new Intent(MappingActivityCalc.this, GpsMappingActivity.class);
                editor = Prefrence.edit();
                editor.putString("mapping", "walkmyareacalc");
                editor.commit();
                startActivity(gpsIntent);

            }
        });
        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent marhIntent = new Intent(MappingActivityCalc.this, MapManualMapping.class);
                editor = Prefrence.edit();
                editor.putString("mapping", "markmyareacalc");
                editor.commit();
                startActivity(marhIntent);

            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(MappingActivityCalc.this, SprayActivity12_Areamap.class);

        DatabaseHandler db = new DatabaseHandler(MappingActivityCalc.this);
        List<String> areaVal = db.selectAreaValue(clubName, areaName);
        if (areaVal.size() == 0) {
            startActivity(i);
        } else {
            editor = Prefrence.edit();
            editor.putBoolean("in8", false);
            editor.putString("areaVal", String.valueOf(areaVal.get(areaVal.size() - 1)));
            editor.commit();
            startActivity(i);
        }
    }
}
