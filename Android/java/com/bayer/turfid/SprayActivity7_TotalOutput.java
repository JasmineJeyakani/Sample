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

public class SprayActivity7_TotalOutput extends Activity {
    Button continue7actual;
    EditText actualOutputVal;
    SharedPreferences Prefrence;
    SharedPreferences.Editor editor;
    Double actualOutputvar;
    Double nozzlePressure;
    Double nozzleOutput;
    Double nozzleSpacing;
    Double speedCalc;
    double waterVolumeCalc;
    Double productLabelVal, waterRange;

    ImageView backArrow, imgLogo;
    Double nozzleOutput1;
    DecimalFormat df = new DecimalFormat("####0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spray_activity7__total_output);
        continue7actual = (Button) findViewById(R.id.continue7actulabtn);
        actualOutputVal = (EditText) findViewById(R.id.actualOutput);
        actualOutputVal.setOnEditorActionListener(new DoneOnEditorActionListener());
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
                Intent intent = new Intent(SprayActivity7_TotalOutput.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });

        Prefrence = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        actualOutputVal.setText("");
        if (!Prefrence.getBoolean("in6", false)) {

            String val = Prefrence.getString("actualOutputVal", String.valueOf(0));
            actualOutputVal.setText(String.valueOf(df.format(Double.valueOf(val))) + " Litres/Minute");
        } else {
            actualOutputVal.setText("");
//
        }


        actualOutputVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actualOutputVal.getText().toString().endsWith(" Litres/Minute")) {
                    String val = actualOutputVal.getText().toString();
                    String[] valsep = val.split(" Litres/Minute");
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
                    String sep[] = tempVal.split("L");
                    actualOutputvar = Double.valueOf(sep[0]);
                    if (actualOutputvar == 0.1) {
                        editor.putString("actualOutputVal", String.valueOf(actualOutputvar));
                        editor.commit();
                        CalculationFunction();

                    } else if (actualOutputvar == 0.2) {
                        editor.putString("actualOutputVal", String.valueOf(actualOutputvar));
                        editor.commit();
                        CalculationFunction();

                    } else if (actualOutputvar == 0.3) {
                        editor.putString("actualOutputVal", String.valueOf(actualOutputvar));
                        editor.commit();
                        CalculationFunction();

                    } else if (actualOutputvar == 0.4) {
                        editor.putString("actualOutputVal", String.valueOf(actualOutputvar));
                        editor.commit();
                        CalculationFunction();

                    } else if (Math.round(actualOutputvar) == 0) {
                        actualOutputVal.setText("");

                    } else {
                        editor.putString("actualOutputVal", String.valueOf(actualOutputvar));
                        editor.commit();
                        CalculationFunction();
                    }
                }
            }

            private void CalculationFunction() {
                nozzlePressure = Double.valueOf(Prefrence.getString("correctNozzleVal", String.valueOf(0)));
                nozzleOutput = Double.valueOf(Prefrence.getString("actualOutputVal", String.valueOf(0)));
                nozzleSpacing = Double.valueOf(Prefrence.getString("NozzleDistanceVal", String.valueOf(0)));
                speedCalc = Double.valueOf(Prefrence.getString("speedCalc", String.valueOf(0)));
                nozzleOutput1 = Double.valueOf(Prefrence.getString("actualOutputVal", String.valueOf(0)));
                waterRange = Double.valueOf(Prefrence.getInt("prewaterVol", Integer.valueOf(0)));
                String val1 = String.valueOf(600 * nozzleOutput1);
                double val2 = Double.valueOf(val1) / Double.valueOf(speedCalc);
                waterVolumeCalc = Double.valueOf(val2) / Double.valueOf(nozzleSpacing);
                Log.d("val1", val1);
                Log.d("val2", String.valueOf(val2));
                Log.d("sppedcalc", String.valueOf(speedCalc));
                Log.d("watervol", String.valueOf(waterVolumeCalc));


                productLabelVal = Double.valueOf(Prefrence.getString("ProductLabelVal", String.valueOf(0)));
                if ((nozzleOutput < waterRange) && (productLabelVal < waterRange)) {

                    Intent res2 = new Intent(SprayActivity7_TotalOutput.this, SprayActivity_Result2.class);
                    editor.putString("waterVolumeCalc1", String.valueOf(waterVolumeCalc));
                    editor.putBoolean("res2", false);
                    editor.commit();
//
                    startActivity(res2);
                } else {
                    Intent res3 = new Intent(SprayActivity7_TotalOutput.this, SprayActivity_Result3.class);
//
                    editor.putString("waterVolumeCalc1", String.valueOf(waterVolumeCalc));
                    editor.putBoolean("res2", true);
                    editor.commit();
                    startActivity(res3);
//
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
                if (actualOutputVal.getText().toString().contains("Litres/Minute")) {

                } else if (actualOutputVal.getText().toString().equals("")) {
                    actualOutputVal.setText("");
                }
                else {

                    String val = df.format(Double.valueOf(actualOutputVal.getText().toString())) + " Litres/Minute";
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
        if (actualOutputVal.getText().toString().contains("Litres/Minute")) {

        } else if (actualOutputVal.getText().toString().equals("")) {
            actualOutputVal.setText("");
        }
        else {

            String val = df.format(Double.valueOf(actualOutputVal.getText().toString())) + " Litres/Minute";
            actualOutputVal.setText(val);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SprayActivity7_TotalOutput.this, SprayActivity6_CalibratedJug.class);
        startActivity(i);
    }
}
