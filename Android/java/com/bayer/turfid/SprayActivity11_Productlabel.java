package com.bayer.turfid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

public class SprayActivity11_Productlabel extends Activity {
    Button continue11productlabel;
    EditText productRateVal;
    SharedPreferences productRateval;
    SharedPreferences.Editor editor;
    Double productRatevar;
    ImageView imgLogo;
    String appRate;
    ImageView backArrow;
    String clubName, areaName;
    DecimalFormat df = new DecimalFormat("####0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spray_activity9__productlabel);
        continue11productlabel = (Button) findViewById(R.id.continue11btn);
        productRateVal = (EditText) findViewById(R.id.productRate);
        productRateVal.setOnEditorActionListener(new DoneOnEditorActionListener());
        productRateval = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        appRate = productRateval.getString("appRateknap", String.valueOf(1));
        clubName = productRateval.getString("golfName", "golf");
        areaName = productRateval.getString("areaName", "area");
        if (!productRateval.getBoolean("prePopulate1", true)) {
            productRateVal.setText("");
            editor = productRateval.edit();
            editor.putBoolean("prePopulate1", true);
            editor.commit();
        } else {
            if (!productRateval.getBoolean("in11", false)) {

                String val = productRateval.getString("productRateVal", String.valueOf(df.format(0)));
                String val2 = df.format(Double.parseDouble(val));
                if (val2.endsWith(".00")) {
                    productRateVal.setText(Math.round(Double.parseDouble(val2)) + " L/Ha");
                } else {
                    productRateVal.setText(df.format(Double.parseDouble(val)) + " L/Ha");
                }

            } else {
                String val2 = df.format(Double.parseDouble(String.valueOf(appRate)));


                if (val2.endsWith(".00")) {
                    productRateVal.setText(String.valueOf(Math.round(Double.parseDouble(String.valueOf(appRate)))) + " L/Ha");
                } else {
                    productRateVal.setText(String.valueOf(df.format(Double.parseDouble(String.valueOf(appRate)))) + " L/Ha");
                }


            }
        }
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
                Intent intent = new Intent(SprayActivity11_Productlabel.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });
        String type = productRateval.getString("sprayerType", "default");
        if (type.equals("boom")) {
            CustomTextViewBold t = (CustomTextViewBold) findViewById(R.id.step2);
            t.setText(R.string.boom_step_11);
        }
        productRateVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (productRateVal.getText().toString().endsWith(" L/Ha")) {
                    String val = productRateVal.getText().toString();
                    String[] valsep = val.split(" L/Ha");
                    String knap = valsep[0];
                    if (knap.endsWith(".00")) {
                        String valsep1 = String.valueOf(Math.round(Double.valueOf(knap)));
                        String knap1 = valsep1;
                        productRateVal.setText(knap1);
                        productRateVal.setSelection(knap1.length());
                    } else {
                        productRateVal.setText(knap);
                        productRateVal.setSelection(productRateVal.getText().length());
                    }
                } else {

                }

            }


        });


        continue11productlabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent areaMap = new Intent(SprayActivity11_Productlabel.this, SprayActivity12_Areamap.class);
                SharedPreferences.Editor editor = productRateval.edit();
                editor.putBoolean("in8", true);
                editor.commit();
                if (productRateVal.getText().toString().equals("")) {
                    productRateVal.setError("Enter the value");
                }
                else {
                    String tempVal = productRateVal.getText().toString();
                    String sep[] = tempVal.split("L");
                    productRatevar = Double.valueOf(sep[0]);
                    if (productRatevar == 0.1) {
                        CalculationFunction(areaMap);
                    } else if (productRatevar == 0.2) {
                        CalculationFunction(areaMap);
                    } else if (productRatevar == 0.3) {
                        CalculationFunction(areaMap);
                    } else if (productRatevar == 0.4) {
                        CalculationFunction(areaMap);
                    } else if (Math.round(productRatevar) == 0) {
                        productRateVal.setText("");
                    } else {
                        CalculationFunction(areaMap);
                    }


                }
            }

            private void CalculationFunction(Intent areaMap) {
                editor = productRateval.edit();
                editor.putString("productRateVal", String.valueOf(productRatevar));
                Log.d("sreeeninini", String.valueOf(productRatevar));
                editor.commit();
                DatabaseHandler db = new DatabaseHandler(SprayActivity11_Productlabel.this);
                List<String> areaVal = db.selectAreaValue(clubName, areaName);
                if (areaVal.size() == 0) {
                    startActivity(areaMap);
                } else {
                    editor = productRateval.edit();
                    editor.putBoolean("in8", false);
                    editor.putString("areaVal", String.valueOf(areaVal.get(areaVal.size() - 1)));
                    editor.commit();
                    startActivity(areaMap);
                }

            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(productRateVal.getWindowToken(), 0);
        if (productRateVal.getText().toString().contains(" L/Ha")) {

        } else if (productRateVal.getText().toString().equals("")) {
            productRateVal.setText("");
        }
        else {
            String val = productRateVal.getText().toString() + " L/Ha";
            productRateVal.setText(val);
        }
        return super.onTouchEvent(event);
    }


    private class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (productRateVal.getText().toString().contains(" L/Ha")) {

                } else if (productRateVal.getText().toString().equals("")) {
                    productRateVal.setText("");
                }
                else {
                    String val = df.format(Double.valueOf(productRateVal.getText().toString())) + " L/Ha";
                    productRateVal.setText(val);
                }

                return true;
            }
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        Intent step8Sprayertank = new Intent(SprayActivity11_Productlabel.this, SprayActivity8_Sprayertank.class);
        editor = productRateval.edit();
        editor.putBoolean("in7", false);
        editor.commit();
        startActivity(step8Sprayertank);
    }
}
