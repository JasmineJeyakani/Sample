package com.bayer.turfid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Mapping_Activity extends Activity {

    TextView walk, mark, clubName, areaName;
    ImageView imgLogo;
    SharedPreferences Prefrence;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping);
        Prefrence = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        walk = (TextView) findViewById(R.id.walkTxt);
        mark = (TextView) findViewById(R.id.markTxt);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        clubName = (TextView) findViewById(R.id.clubname);
        areaName = (TextView) findViewById(R.id.clubarea);


        String club = Prefrence.getString("setClub", "club");
        if (club.equals("club")) {
            clubName.setText("");
        } else {
            clubName.setText(club);
        }


        if (!Prefrence.getBoolean("prepop", false)) {

            String val = Prefrence.getString("clubNameRE", "clubName");
            String val1 = Prefrence.getString("areaNameRE", "clubName");

            clubName.setText(val);
            areaName.setText(val1);

            SharedPreferences.Editor editor = Prefrence.edit();
            editor.putBoolean("prepop", true);
            editor.commit();
        }
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mapping_Activity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });
        clubName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clubName.setError(null);
                final DatabaseHandler db = new DatabaseHandler(Mapping_Activity.this);
                List<String> golfVal = db.selectGolfNamecreate();
                final String[] listString = golfVal.toArray(new String[golfVal.size()]);
                final ArrayAdapter<String> myAdapternew = new ArrayAdapter<String>(Mapping_Activity.this, android.R.layout.simple_dropdown_item_1line,
                        listString);
                AlertDialog.Builder mBuider = new AlertDialog.Builder(getParent());
                mBuider.setTitle("Select Golf Name");
                mBuider.setAdapter(myAdapternew, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clubName.setText(listString[which].toString());

                        if (clubName.getText().toString().equalsIgnoreCase("Create a new Golf Club")) {

                            Intent i = new Intent(Mapping_Activity.this, NewClubActivity.class);
                            editor = Prefrence.edit();
                            editor.putString("newclubVal", "mapclub");
                            editor.commit();
                            startActivity(i);

                        } else {
                            areaName.setText("");
                            //valueResult=false;
                            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(clubName.getWindowToken(), 0);

                        }

                        dialog.dismiss();
                    }
                });

                mBuider.create().show();
            }
        });

        areaName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                areaName.requestFocus();
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                    areaName.setError(null);
                    final DatabaseHandler db1 = new DatabaseHandler(getApplicationContext());
                    List<String> areaValName = db1.selectAreaName(clubName.getText().toString());
                    areaValName = new ArrayList<String>(new LinkedHashSet<String>(areaValName));
                    final String[] listString = areaValName.toArray(new String[areaValName.size()]);
                    final ArrayAdapter<String> myAdapternew = new ArrayAdapter<String>(Mapping_Activity.this, android.R.layout.simple_dropdown_item_1line,
                            listString);
                    AlertDialog.Builder mBuider = new AlertDialog.Builder(getParent());
                    mBuider.setTitle("Select Area Name");


                    mBuider.setAdapter(myAdapternew, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            areaName.setText(listString[which].toString());
                            if (areaName.getText().toString().equalsIgnoreCase("Create a New Area")) {

                                areaName.setText("");
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(areaName, InputMethodManager.SHOW_IMPLICIT);
                                areaName.requestFocus();

                            } else {
                                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(areaName.getWindowToken(), 0);

                            }
                            dialog.dismiss();
                        }
                    });

                    mBuider.create().show();
                }
                return true;
            }
        });
        walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gpsIntent = new Intent(Mapping_Activity.this, GpsMappingActivity.class);
                if (clubName.getText().toString().equals("")) {
                    clubName.setError("Enter clubName");
                } else if (areaName.getText().toString().equals("")) {
                    areaName.setError("Enter area name");
                } else {
                    editor = Prefrence.edit();
                    editor.putString("mapping", "walkmyarea");
                    editor.putString("mapClubName", clubName.getText().toString());
                    editor.putString("mapAreaName", areaName.getText().toString());
                    editor.commit();
                    startActivity(gpsIntent);
                }
            }
        });
        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent marhIntent = new Intent(Mapping_Activity.this, MapManualMapping.class);
                if (clubName.getText().toString().equals("")) {
                    clubName.setError("Enter clubName");
                } else if (areaName.getText().toString().equals("")) {
                    areaName.setError("Enter area name");
                } else {
                    editor = Prefrence.edit();
                    editor.putBoolean("prepop", true);
                    editor.putString("mapping", "markmyarea");
                    editor.putString("mapClubName", clubName.getText().toString());
                    editor.putString("mapAreaName", areaName.getText().toString());
                    editor.commit();
                    startActivity(marhIntent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Mapping_Activity.this, BayerTurfManagementActivity.class);
        startActivity(i);
    }
}
