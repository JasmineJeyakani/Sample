package com.bayer.turfid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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


public class NewClubActivity extends Activity {
    EditText clubName, address, fName, lName, emailid;
    String clubNamestr, addressstr, fNamestr, lNamestr, emailidstr, sprayMakestr, sprayModelstr;
    Button save;
    EditText sprayMake, sprayModel;
    int pos;
    Context context;
    ImageView img;
    SharedPreferences Prefrence;
    SharedPreferences.Editor editor;
    int cre;
    Boolean model = false;
    String newClubIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_club);
        clubName = (EditText) findViewById(R.id.clubname);
        address = (EditText) findViewById(R.id.clubaddress);
        fName = (EditText) findViewById(R.id.firstname);
        lName = (EditText) findViewById(R.id.lastname);
        emailid = (EditText) findViewById(R.id.emailadd);
        sprayMake = (EditText) findViewById(R.id.spraymake);
        sprayModel = (EditText) findViewById(R.id.spraymodel);
        save = (Button) findViewById(R.id.savabtn);
        img = (ImageView) findViewById(R.id.logo_img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewClubActivity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });
        Prefrence = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        editor = Prefrence.edit();
        context = getParent();
        newClubIntent = Prefrence.getString("newclubVal", "default");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent golfIntent = null;
                if (!Prefrence.getBoolean("firstAccept", false)) {
                    // run your one time code
                    golfIntent = new Intent(NewClubActivity.this, BayerTurfManagementActivity.class);
                    SharedPreferences.Editor editor = Prefrence.edit();
                    editor.putBoolean("firstAccept", true);
                    editor.putBoolean("in10", true);
                    editor.commit();
                } else {
                    if (newClubIntent.equals("sprayCalc")) {
                        golfIntent = new Intent(NewClubActivity.this, SprayCalculationActivity.class);
                        editor.putBoolean("in10", true);
                        editor.commit();
                    } else if (newClubIntent.equals("mapclub")) {
                        golfIntent = new Intent(NewClubActivity.this, BayerMappingTurf.class);
                    } else if (newClubIntent.equals("myclubintent")) {
                        golfIntent = new Intent(NewClubActivity.this, BayerClubTurf.class);
                        editor.putBoolean("in10", true);
                        editor.commit();
                    }
                }
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
                    if (!emailValidator(emailidstr)) {
                        emailid.setError("Enter valid email");
                    } else {
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        List<String> tempClub = db.selectClubName();
                        if (tempClub.contains(clubNamestr)) {
                            clubName.setError("Club name already available");
                        } else {

                            db.insertTable(clubNamestr, addressstr, emailidstr, fNamestr, lNamestr, sprayMakestr, sprayModelstr);
                            editor = Prefrence.edit();
                            editor.putString("setClub", clubNamestr);
                            editor.putString("setArea", "AreaName");
                            editor.putString("setSprayMake", sprayMakestr);
                            editor.putString("setSprayModel", sprayModelstr);
                            editor.commit();
                            List<String> val = db.selectTable();
                            startActivity(golfIntent);
                        }

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

            final ArrayAdapter<String> myAdapternew12 = new ArrayAdapter<String>(NewClubActivity.this, android.R.layout.simple_dropdown_item_1line,
                    listString1);

            sprayMake.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                        new AlertDialog.Builder(NewClubActivity.this)
                                .setTitle("Select Sprayer Model")
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
                                        final ArrayAdapter<String> myAdapternew1 = new ArrayAdapter<String>(NewClubActivity.this, android.R.layout.simple_dropdown_item_1line,
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
                                                        new AlertDialog.Builder(NewClubActivity.this)
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

        Intent i1 = null;

        if (newClubIntent.equals("sprayCalc")) {
            i1 = new Intent(NewClubActivity.this, SprayCalculationActivity.class);

        } else if (newClubIntent.equals("mapclub")) {
            i1 = new Intent(NewClubActivity.this, BayerMappingTurf.class);
        } else if (newClubIntent.equals("myclubintent")) {
            i1 = new Intent(NewClubActivity.this, BayerClubTurf.class);

        }
        startActivity(i1);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(clubName.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(address.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(fName.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(lName.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(emailid.getWindowToken(), 0);


        return super.onTouchEvent(event);
    }
}
