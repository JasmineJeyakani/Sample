package com.bayer.turfid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class SprayActivity8_Sprayertank extends Activity {

    Button continueBtn8SprayerTank;
    EditText sprayTank;
    SharedPreferences sprayTankval;
    SharedPreferences.Editor editor;
    Double sprayTankvar;
    ImageView backArrow, imgLogo;
    DecimalFormat df = new DecimalFormat("####0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spray_activity8__sprayertank);
        sprayTank = (EditText) findViewById(R.id.sparayerTank);
        continueBtn8SprayerTank = (Button) findViewById(R.id.continuebtn8);
        sprayTank.setOnEditorActionListener(new DoneOnEditorActionListener());
        backArrow = (ImageView) findViewById(R.id.leftarrowknapsack);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        imgLogo = (ImageView) findViewById(R.id.logo_img);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SprayActivity8_Sprayertank.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });


        sprayTankval = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        editor = sprayTankval.edit();
        String type = sprayTankval.getString("sprayerType", "default");
        if (type.equals("boom")) {
            CustomTextViewBold t = (CustomTextViewBold) findViewById(R.id.step2);
            t.setText(R.string.boom_step_10);
        }


        sprayTank.setText("");
        if (!sprayTankval.getBoolean("in7", false)) {

            String val = sprayTankval.getString("sprayTankValue1", String.valueOf(0));
            sprayTank.setText(String.valueOf(df.format(Double.valueOf(val))) + " Litres");
        } else {
            sprayTank.setText("");
        }

        sprayTank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sprayTank.getText().toString().endsWith(" Litres")) {
                    String val = sprayTank.getText().toString();
                    String[] valsep = val.split(" Litres");
                    String knap = valsep[0];
                    Log.d("knap", knap);
                    if (knap.endsWith(".00")) {
                        Log.d("knaptrue", knap);
                        sprayTank.setText(knap);
                        sprayTank.setSelection(knap.length());
                    } else {
                        sprayTank.setText(knap);
                        sprayTank.setSelection(sprayTank.getText().length());
                    }


                } else {

                }
            }


        });

        continueBtn8SprayerTank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productLabel = new Intent(SprayActivity8_Sprayertank.this, SprayActivity11_Productlabel.class);
                if (sprayTank.getText().toString().equals("")) {
                    sprayTank.setError("Enter Value");
                }
                else {
                    String tempVal = sprayTank.getText().toString();
                    String sep[] = tempVal.split("L");
                    sprayTankvar = Double.valueOf(sep[0]);

                    if (sprayTankvar == 0.1) {
                        editor.putString("sprayTankValue1", String.valueOf(sprayTankvar));
                        editor.commit();
                        startActivity(productLabel);
                    } else if (sprayTankvar == 0.2) {
                        editor.putString("sprayTankValue1", String.valueOf(sprayTankvar));
                        editor.commit();
                        startActivity(productLabel);
                    } else if (sprayTankvar == 0.3) {
                        editor.putString("sprayTankValue1", String.valueOf(sprayTankvar));
                        editor.commit();
                        startActivity(productLabel);
                    } else if (sprayTankvar == 0.4) {
                        editor.putString("sprayTankValue1", String.valueOf(sprayTankvar));
                        editor.commit();
                        startActivity(productLabel);
                    } else if (Math.round(sprayTankvar) == 0) {
                        sprayTank.setText("");
                    } else {

                        editor = sprayTankval.edit();
                        editor.putString("sprayTankValue1", String.valueOf(sprayTankvar));
                        editor.commit();
                        startActivity(productLabel);
                    }
                }
            }
        });

    }

    private class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (sprayTank.getText().toString().contains("Litres")) {

                } else if (sprayTank.getText().toString().equals("")) {
                    sprayTank.setText("");
                }

                else {
                    String val = df.format(Double.valueOf(sprayTank.getText().toString())) + " Litres";
                    sprayTank.setText(val);
                }

                return true;
            }
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(sprayTank.getWindowToken(), 0);
        if (sprayTank.getText().toString().contains("Litres")) {

        } else if (sprayTank.getText().toString().equals("")) {
            sprayTank.setText("");
        }
        else {
            String val = sprayTank.getText().toString() + " Litres";
            sprayTank.setText(val);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        String type = sprayTankval.getString("sprayerType", "default");
        Intent step8Sprayertank;
        if (type.equals("boom")) {
            step8Sprayertank = new Intent(SprayActivity8_Sprayertank.this, SprayActivity_Result2.class);
            startActivity(step8Sprayertank);
        } else {


            if (!sprayTankval.getBoolean("res2", false)) {
                step8Sprayertank = new Intent(SprayActivity8_Sprayertank.this, SprayActivity_Result2.class);
                startActivity(step8Sprayertank);

            } else {
                step8Sprayertank = new Intent(SprayActivity8_Sprayertank.this, SprayActivity_Result3.class);
                startActivity(step8Sprayertank);
//
            }
        }


    }
}
