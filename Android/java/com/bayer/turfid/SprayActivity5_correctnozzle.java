package com.bayer.turfid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class SprayActivity5_correctnozzle extends Activity {
    Button continue5btn;
    EditText correctNozzleVal;
    SharedPreferences correctNozzleval;
    SharedPreferences.Editor editor;
    Double correctNozzlevar;
    ImageView imgLogo, backArrow;
    DecimalFormat df = new DecimalFormat("####0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spray_activity5_correctnozzle);
        continue5btn = (Button) findViewById(R.id.continue5btn);
        correctNozzleVal = (EditText) findViewById(R.id.correctNozzle);

        backArrow = (ImageView) findViewById(R.id.leftarrowknapsack);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SprayActivity5_correctnozzle.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });
        correctNozzleval = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);

        String type = correctNozzleval.getString("sprayerType", "default");
        if (type.equals("boom")) {
            CustomTextViewBold t = (CustomTextViewBold) findViewById(R.id.step3);
            t.setText(R.string.boom_step_5);
        }


        correctNozzleVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctNozzleVal.setError(null);
                if (correctNozzleVal.getText().toString().endsWith(" BAR")) {
                    String val = correctNozzleVal.getText().toString();
                    String[] valsep = val.split(" BAR");
                    String knap = valsep[0];
                    if (knap.endsWith(".00")) {
                        correctNozzleVal.setText(knap);
                        correctNozzleVal.setSelection(knap.length());
                    } else {
                        correctNozzleVal.setText(knap);
                        correctNozzleVal.setSelection(correctNozzleVal.getText().length());
                    }


                } else {

                }
            }


        });

        correctNozzleVal.setOnEditorActionListener(new DoneOnEditorActionListener());
        correctNozzleval = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);

        correctNozzleVal.setText("");
        if (!correctNozzleval.getBoolean("in5", false)) {

            String val = correctNozzleval.getString("correctNozzleVal", String.valueOf(0));
            correctNozzleVal.setText(String.valueOf(df.format(Double.valueOf(val))) + " BAR");
        } else {
            correctNozzleVal.setText("");
//
        }


        continue5btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calibratedjug = new Intent(SprayActivity5_correctnozzle.this, SprayActivity6_CalibratedJug.class);
                if (correctNozzleVal.getText().toString().equals("")) {
                    correctNozzleVal.setError("Enter value");
                }
                else {
                    String tempVal = correctNozzleVal.getText().toString();
                    String sep[] = tempVal.split("B");
                    correctNozzlevar = Double.valueOf(sep[0]);

                    if (correctNozzlevar == 0.1) {
                        editor = correctNozzleval.edit();
                        editor.putString("correctNozzleVal", String.valueOf(correctNozzlevar));
                        editor.commit();
                        startActivity(calibratedjug);
                    } else if (correctNozzlevar == 0.2) {
                        editor = correctNozzleval.edit();
                        editor.putString("correctNozzleVal", String.valueOf(correctNozzlevar));
                        editor.commit();
                        startActivity(calibratedjug);
                    } else if (correctNozzlevar == 0.3) {
                        editor = correctNozzleval.edit();
                        editor.putString("correctNozzleVal", String.valueOf(correctNozzlevar));
                        editor.commit();
                        startActivity(calibratedjug);
                    } else if (correctNozzlevar == 0.4) {
                        editor = correctNozzleval.edit();
                        editor.putString("correctNozzleVal", String.valueOf(correctNozzlevar));
                        editor.commit();
                        startActivity(calibratedjug);
                    } else if (Math.round(correctNozzlevar) == 0) {
                        correctNozzleVal.setText("");
                    } else {
                        editor = correctNozzleval.edit();
                        editor.putString("correctNozzleVal", String.valueOf(correctNozzlevar));
                        editor.commit();
                        startActivity(calibratedjug);
                    }
                }
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(correctNozzleVal.getWindowToken(), 0);
        if (correctNozzleVal.getText().toString().contains("BAR")) {

        } else if (correctNozzleVal.getText().toString().equals("")) {
            correctNozzleVal.setText("");
        }

        else {
            String val = correctNozzleVal.getText().toString() + " BAR";
            correctNozzleVal.setText(val);
        }
        return super.onTouchEvent(event);
    }


    private class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (correctNozzleVal.getText().toString().contains("BAR")) {

                } else if (correctNozzleVal.getText().toString().equals("")) {
                    correctNozzleVal.setText("");
                }
                else {
                    String val = df.format(Double.valueOf(correctNozzleVal.getText().toString())) + " BAR";
                    correctNozzleVal.setText(val);
                }
                return true;
            }
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SprayActivity5_correctnozzle.this, SprayActivity_Result1.class);
        startActivity(i);
    }
}