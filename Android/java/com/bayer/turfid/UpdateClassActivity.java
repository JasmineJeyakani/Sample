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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 11-Dec-16.
 */

public class UpdateClassActivity extends Activity {
    EditText clubName, address, fName, lName, emailid;
    String clubNamestr, addressstr, fNamestr, lNamestr, emailidstr, sprayMakestr, sprayModelstr;
    Button save;
    TextView sprayMake, sprayModel;
    int pos;
    SharedPreferences knapsackval;
    SharedPreferences.Editor editor;
    ImageView imgLogo;
    String cName, cAdd, cmake, cModel, cMail;
    int clubpos;
    Boolean model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_club);

        clubName = (EditText) findViewById(R.id.clubname);
        address = (EditText) findViewById(R.id.clubaddress);
        fName = (EditText) findViewById(R.id.firstname);
        lName = (EditText) findViewById(R.id.lastname);
        emailid = (EditText) findViewById(R.id.emailadd);
        sprayMake = (TextView) findViewById(R.id.spraymake);
        sprayModel = (TextView) findViewById(R.id.spraymodel);
        save = (Button) findViewById(R.id.savabtn);
        imgLogo = (ImageView) findViewById(R.id.logo_img);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateClassActivity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });


        Bundle b = getIntent().getExtras();
        if (b != null) {
            cName = b.getString("clubname");
            clubName.setText(cName);
            cAdd = b.getString("addname");
            cmake = b.getString("makename");
            cModel = b.getString("modelname");
            cMail = b.getString("mail");
            fNamestr = b.getString("fname");
            lNamestr = b.getString("lname");
            address.setText(cAdd);
            ;
            sprayMake.setText(cmake);
            sprayModel.setText(cModel);
            emailid.setText(cMail);
            fName.setText(fNamestr);
            lName.setText(lNamestr);


        }
        //  Toast.makeText(this, "club"+clubName.getText().toString(), Toast.LENGTH_SHORT).show();
        final DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        final List<String> tempClub = db.selectClubName();
        final ArrayList<String> tempClub2 = new ArrayList<String>();
        for (int i = 0; i < tempClub.size(); i++) {
            Log.d("val1", tempClub.get(i));
            if (tempClub.get(i).equals(clubName.getText().toString())) {

            } else {
                tempClub2.add(tempClub.get(i));
            }
        }
        for (int i = 0; i < tempClub2.size(); i++) {
            Log.d("val", tempClub2.get(i));
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent golfIntent = new Intent(UpdateClassActivity.this, BayerClubTurf.class);
                if (clubName.getText().toString().equals("")) {
                    clubName.setError("Enter ClubName");
                } else if (address.getText().toString().equals("")) {
                    address.setError("Enter Address");
                } else if (emailid.getText().toString().equals("")) {
                    emailid.setError("Enter Mail");
                } else if (sprayMake.getText().toString().equals("")) {
                    sprayMake.setError("Select sprayMake");
                } else if (sprayModel.getText().toString().equals("")) {
                    sprayModel.setError("Select SprayModel");
                } else {

                    clubNamestr = clubName.getText().toString();
                    addressstr = address.getText().toString();
                    fNamestr = fName.getText().toString();
                    lNamestr = lName.getText().toString();
                    emailidstr = emailid.getText().toString();

                    sprayMakestr = sprayMake.getText().toString();
                    sprayModelstr = sprayModel.getText().toString();
                    Boolean ema = emailValidator(emailidstr);
//                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
//                        List<String> tempClub=db.selectClubName();
                    clubpos = 0;
                    for (int i = 0; i < tempClub2.size(); i++) {
                        if (tempClub2.get(i).equals(clubNamestr)) {
                            clubpos++;
                        }
                    }

                    if (clubpos >= 1) {
                        clubName.setError("Club name already available");
                    } else {
                        knapsackval = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
                        String cname = knapsackval.getString("updateclub", "club");
                        db.updateSprayTablle(clubNamestr, addressstr, emailidstr, sprayMakestr, sprayModelstr, cname);
                        startActivity(golfIntent);

                    }
                }
            }
        });

        try {
            JSONObject jObj = new JSONObject(loadJSONFromAsset());
            final JSONArray jArray = jObj.getJSONArray("sprayer_make");
            final ArrayList<HashMap<String, String>> sprayList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> spray;
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject spr = jArray.getJSONObject(i);
                String sprName = spr.getString("name");
                spray = new HashMap<>();
                spray.put("name", sprName);
                sprayList.add(spray);
            }
            spray = new HashMap<>();
            spray.put("name", "Others");
            sprayList.add(spray);


            final String[] listString1 = new String[sprayList.size()];
            int j = 0;
            for (int index = 0; index < sprayList.size(); index++) {
                HashMap<String, String> map = sprayList.get(index);
                listString1[j++] = map.get("name");
            }

            final ArrayAdapter<String> myAdapternew12 = new ArrayAdapter<String>(UpdateClassActivity.this, android.R.layout.simple_dropdown_item_1line,
                    listString1);

            sprayMake.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                        new AlertDialog.Builder(UpdateClassActivity.this)
                                .setTitle("Select Sprayer Make")
                                .setAdapter(myAdapternew12, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {

                                        sprayMake.setError(null);
                                        if (listString1[which].toString().equals("Others")) {
                                            sprayMake.setText("");
                                            sprayModel.setText("");
                                            model = true;
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.showSoftInput(sprayMake, InputMethodManager.SHOW_IMPLICIT);
                                            sprayMake.requestFocus();
                                        } else {
                                            model = false;
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(sprayMake.getWindowToken(), 0);
                                            sprayMake.setText(listString1[which].toString());
                                            sprayModel.setText("");

                                            pos = which;
                                            final List<String> val = new ArrayList<String>();
                                            JSONObject spr = null;
                                            try {
                                                spr = jArray.getJSONObject(which);
                                                JSONArray sprayModelarr = spr.getJSONArray("sprayer_model");
                                                for (int j1 = 0; j1 < sprayModelarr.length(); j1++)

                                                {
                                                    val.add(sprayModelarr.getString(j1));

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            final String[] valArray = val.toArray(new String[val.size()]);

                                            sprayModel.setText(valArray[0]);

                                        }
                                        //Toast.makeText(NewClubActivity.this, ""+pos, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        final List<String> val = new ArrayList<String>();
                                        JSONObject spr = null;
                                        try {
                                            spr = jArray.getJSONObject(which);
                                            JSONArray sprayModelarr = spr.getJSONArray("sprayer_model");
                                            for (int j1 = 0; j1 < sprayModelarr.length(); j1++)

                                            {
                                                val.add(sprayModelarr.getString(j1));

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        final String[] valArray = val.toArray(new String[val.size()]);
                                        final ArrayAdapter<String> myAdapternew1 = new ArrayAdapter<String>(UpdateClassActivity.this, android.R.layout.simple_dropdown_item_1line,
                                                valArray);
                                        sprayModel.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View view, MotionEvent motionEvent) {

//
                                                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                                    if (model == true) {
                                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                        imm.showSoftInput(sprayModel, InputMethodManager.SHOW_IMPLICIT);
                                                        sprayModel.requestFocus();
                                                    } else {
                                                        new AlertDialog.Builder(UpdateClassActivity.this)
                                                                .setTitle("Select Sprayer Model")
                                                                .setAdapter(myAdapternew1, new DialogInterface.OnClickListener() {

                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        sprayModel.setError(null);
                                                                        sprayModel.setText(val.get(which).toString());
                                                                        dialog.dismiss();
                                                                    }

                                                                }).create().show();
                                                    }
                                                    return false;
                                                }
                                                return true;
                                            }
                                        });
                                    }
                                }).create().show();
                    }
                    return false;
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("sprayer_config.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(UpdateClassActivity.this, BayerClubTurf.class);
        startActivity(i);
    }
}



