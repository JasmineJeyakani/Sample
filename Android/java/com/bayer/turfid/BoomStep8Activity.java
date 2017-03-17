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

public class BoomStep8Activity extends Activity {
    Button continue7actual;
    EditText actualOutputVal;
    SharedPreferences Prefrence;
    SharedPreferences.Editor editor;
    Double actualOutputvar;
    Double nozzleOutput;
    ImageView backArrow, imgLogo;
    DecimalFormat df = new DecimalFormat("####0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boom_step8);
        continue7actual = (Button) findViewById(R.id.continue7actulabtn);
        actualOutputVal = (EditText) findViewById(R.id.actualOutput);
        actualOutputVal.setOnEditorActionListener(new BoomStep8Activity.DoneOnEditorActionListener());
        Prefrence = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        editor = Prefrence.edit();
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
                Intent intent = new Intent(BoomStep8Activity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });

        Prefrence = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        actualOutputVal.setText("");
        if (!Prefrence.getBoolean("in6", false)) {

            String val = Prefrence.getString("numberOfNozzle", String.valueOf(0));
            actualOutputVal.setText(String.valueOf(df.format(Double.valueOf(val))) + " Nozzles");

        } else {
            actualOutputVal.setText("");

        }


        actualOutputVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actualOutputVal.getText().toString().endsWith(" Nozzles")) {
                    String val = actualOutputVal.getText().toString();
                    String[] valsep = val.split(" Nozzles");
                    String knap = valsep[0];
                    if (knap.endsWith(".00")) {
                        actualOutputVal.setText(knap);
                        actualOutputVal.setSelection(knap.length());
                    } else {
                        actualOutputVal.setText(knap);
                        actualOutputVal.setSelection(actualOutputVal.getText().length());
                    }

                } else {

                }
            }


        });

        continue7actual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actualOutputVal.getText().toString().equals("")) {
                    actualOutputVal.setError("Enter Value");
                }

                else {
                    String tempVal = actualOutputVal.getText().toString();
                    String sep[] = tempVal.split("Nozzles");
                    actualOutputvar = Double.valueOf(sep[0]);
                    if (actualOutputvar == 0.1) {
                        editor = Prefrence.edit();
                        editor.putString("numberOfNozzle", String.valueOf(actualOutputvar));
                        editor.commit();
                        CalculationFunction();

                    } else if (actualOutputvar == 0.2) {
                        editor = Prefrence.edit();
                        editor.putString("numberOfNozzle", String.valueOf(actualOutputvar));
                        editor.commit();
                        CalculationFunction();

                    } else if (actualOutputvar == 0.3) {
                        editor = Prefrence.edit();
                        editor.putString("numberOfNozzle", String.valueOf(actualOutputvar));
                        editor.commit();
                        CalculationFunction();

                    } else if (actualOutputvar == 0.4) {
                        editor = Prefrence.edit();
                        editor.putString("numberOfNozzle", String.valueOf(actualOutputvar));
                        editor.commit();
                        CalculationFunction();

                    } else {
                        editor = Prefrence.edit();
                        editor.putString("numberOfNozzle", String.valueOf(actualOutputvar));
                        editor.commit();
                        CalculationFunction();

                    }


                }

            }

            private void CalculationFunction() {

                nozzleOutput = Double.valueOf(Prefrence.getString("nozzleOutput", String.valueOf(0)));
                Intent res2 = new Intent(BoomStep8Activity.this, BoomStep9Activity.class);
                editor.putString("nozzleCalc", String.valueOf(nozzleOutput * actualOutputvar));
                editor.commit();

                startActivity(res2);
            }
        });

    }

    private class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (actualOutputVal.getText().toString().contains("Nozzles")) {

                } else if (actualOutputVal.getText().toString().equals("")) {
                    actualOutputVal.setText("");
                }
                else {

                    String val = df.format(Double.valueOf(actualOutputVal.getText().toString())) + " Nozzles";
                    actualOutputVal.setText(val);
                }
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(actualOutputVal.getWindowToken(), 0);
        if (actualOutputVal.getText().toString().contains("Nozzles")) {

        } else if (actualOutputVal.getText().toString().equals("")) {
            actualOutputVal.setText("");
        }
        else {

            String val = actualOutputVal.getText().toString() + " Nozzles";
            actualOutputVal.setText(val);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(BoomStep8Activity.this, BoomStep7Activity.class);
        editor = Prefrence.edit();
        editor.putBoolean("in6", false);
        editor.commit();
        startActivity(i);
    }
}

