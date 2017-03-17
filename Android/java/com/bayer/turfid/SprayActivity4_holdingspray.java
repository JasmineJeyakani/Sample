package com.bayer.turfid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
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

public class SprayActivity4_holdingspray extends Activity {
    Button holdingSprayContinue;
    EditText nozzleDistance;
    SharedPreferences nozzleDistanceval;
    SharedPreferences.Editor editor;
    Double nozzleDistancevar;
    ImageView imgLogo;
    ImageView backArrow;
    TextView productlabel1;
    DecimalFormat df = new DecimalFormat("####0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spray_activity4_holdingspray);
        holdingSprayContinue = (Button) findViewById(R.id.holdingspraycontn);
        nozzleDistance = (EditText) findViewById(R.id.nozzleDistance);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        productlabel1 = (TextView) findViewById(R.id.productlabel);
        backArrow = (ImageView) findViewById(R.id.leftarrowknapsack);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        nozzleDistance.setOnEditorActionListener(new DoneOnEditorActionListener());
        nozzleDistanceval = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SprayActivity4_holdingspray.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });

        String type = nozzleDistanceval.getString("sprayerType", "default");
        if (type.equals("boom")) {
            CustomTextViewBold t = (CustomTextViewBold) findViewById(R.id.step3);
            t.setText(R.string.boom_step_4);
            String sourceString = "Enter the <b>nozzle spacing in metres</b> here.";
            productlabel1.setPadding(15, 40, 10, 40);


            productlabel1.setText(Html.fromHtml(sourceString));
        }

        nozzleDistance.setText("");
        if (!nozzleDistanceval.getBoolean("in4", false)) {

            String val = nozzleDistanceval.getString("NozzleDistanceVal", String.valueOf(0));
            nozzleDistance.setText(String.valueOf(df.format(Double.valueOf(val))) + " Metres");
        } else {
            nozzleDistance.setText("");
        }


        nozzleDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nozzleDistance.setError(null);

                if (nozzleDistance.getText().toString().endsWith(" Metres")) {
                    String val = nozzleDistance.getText().toString();
                    String[] valsep = val.split(" Metres");
                    String knap = valsep[0];
                    if (knap.endsWith(".00")) {
                        nozzleDistance.setText(knap);
                        nozzleDistance.setSelection(knap.length());
                    } else {
                        nozzleDistance.setText(knap);
                        nozzleDistance.setSelection(nozzleDistance.getText().length());
                    }
                } else {

                }

            }


        });

        holdingSprayContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Result1 = new Intent(SprayActivity4_holdingspray.this, SprayActivity_Result1.class);
                if (nozzleDistance.getText().toString().equals("")) {
                    nozzleDistance.setError("Enter value");
                }
                else {
                    String tempVal = nozzleDistance.getText().toString();
                    String sep[] = tempVal.split("M");
                    nozzleDistancevar = Double.valueOf(sep[0]);

                    if (nozzleDistancevar == 0.1) {
                        editor = nozzleDistanceval.edit();
                        editor.putString("NozzleDistanceVal", String.valueOf(nozzleDistancevar));
                        editor.commit();
                        startActivity(Result1);

                    } else if (nozzleDistancevar == 0.2) {
                        editor = nozzleDistanceval.edit();
                        editor.putString("NozzleDistanceVal", String.valueOf(nozzleDistancevar));
                        editor.commit();
                        startActivity(Result1);

                    } else if (nozzleDistancevar == 0.3) {
                        editor = nozzleDistanceval.edit();
                        editor.putString("NozzleDistanceVal", String.valueOf(nozzleDistancevar));
                        editor.commit();
                        startActivity(Result1);

                    } else if (nozzleDistancevar == 0.4) {
                        editor = nozzleDistanceval.edit();
                        editor.putString("NozzleDistanceVal", String.valueOf(nozzleDistancevar));
                        editor.commit();
                        startActivity(Result1);

                    } else if (Math.round(nozzleDistancevar) == 0) {
                        nozzleDistance.setText("");
                    } else {
                        editor = nozzleDistanceval.edit();
                        editor.putString("NozzleDistanceVal", String.valueOf(nozzleDistancevar));
                        editor.commit();
//
                        startActivity(Result1);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SprayActivity4_holdingspray.this, SprayActivity3_Productlabel.class);
        editor = nozzleDistanceval.edit();
        editor.putBoolean("inres1", false);
        editor.commit();
        startActivity(i);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(nozzleDistance.getWindowToken(), 0);
        if (nozzleDistance.getText().toString().contains("Metres")) {
        } else if (nozzleDistance.getText().toString().equals("")) {
            nozzleDistance.setText("");
        } else {
            String val = nozzleDistance.getText().toString() + " Metres";
            nozzleDistance.setText(val);
        }
        return super.onTouchEvent(event);
    }

    private class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (nozzleDistance.getText().toString().contains("Metres")) {

                } else if (nozzleDistance.getText().toString().equals("")) {
                    nozzleDistance.setText("");
                }
                else {
                    String val = df.format(Double.valueOf(nozzleDistance.getText().toString())) + " Metres";
                    nozzleDistance.setText(val);
                }

                return true;
            }
            return false;
        }
    }
}
