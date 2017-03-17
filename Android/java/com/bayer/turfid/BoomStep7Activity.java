package com.bayer.turfid;

import com.bayer.turfid.*;

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

public class BoomStep7Activity extends Activity {
    Button continue7actual;
    EditText actualOutputVal;
    SharedPreferences Prefrence;
    SharedPreferences.Editor editor;
    Double actualOutputvar;
    ImageView backArrow, imgLogo;
    DecimalFormat df = new DecimalFormat("####0.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boom_step7);
        continue7actual = (Button) findViewById(R.id.continue7actulabtn);
        actualOutputVal = (EditText) findViewById(R.id.actualOutput);
        actualOutputVal.setOnEditorActionListener(new BoomStep7Activity.DoneOnEditorActionListener());
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
                Intent intent = new Intent(BoomStep7Activity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });

        Prefrence = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        actualOutputVal.setText("");
        if (!Prefrence.getBoolean("in6", false)) {

            String val = Prefrence.getString("nozzleOutput", String.valueOf(0));
            actualOutputVal.setText(String.valueOf(df.format(Double.valueOf(val))) + " Litres/Minute");
        } else {
            actualOutputVal.setText("");
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

                        Intent res2 = new Intent(BoomStep7Activity.this, BoomStep8Activity.class);
                        editor = Prefrence.edit();

                        editor.putString("nozzleOutput", String.valueOf(actualOutputvar));
                        editor.commit();

                        startActivity(res2);

                    }
                    if (actualOutputvar == 0.2) {
                        Intent res2 = new Intent(BoomStep7Activity.this, BoomStep8Activity.class);
                        editor = Prefrence.edit();

                        editor.putString("nozzleOutput", String.valueOf(actualOutputvar));
                        editor.commit();

                        startActivity(res2);
                    } else if (actualOutputvar == 0.3) {
                        Intent res2 = new Intent(BoomStep7Activity.this, BoomStep8Activity.class);
                        editor = Prefrence.edit();

                        editor.putString("nozzleOutput", String.valueOf(actualOutputvar));
                        editor.commit();

                        startActivity(res2);
                    } else if (actualOutputvar == 0.4) {
                        Intent res2 = new Intent(BoomStep7Activity.this, BoomStep8Activity.class);
                        editor = Prefrence.edit();

                        editor.putString("nozzleOutput", String.valueOf(actualOutputvar));
                        editor.commit();

                        startActivity(res2);
                    } else if (String.valueOf(Math.round(actualOutputvar)).equals("0")) {
                        actualOutputVal.setText("");
                    } else {
                        Intent res2 = new Intent(BoomStep7Activity.this, BoomStep8Activity.class);
                        editor = Prefrence.edit();

                        editor.putString("nozzleOutput", String.valueOf(actualOutputvar));
                        editor.commit();

                        startActivity(res2);
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
//            else if(String.valueOf(Math.round(Double.parseDouble(actualOutputVal.getText().toString()))).equals("0"))
//            {
//                actualOutputVal.setText("");
//            }
        else {

            String val = actualOutputVal.getText().toString() + " Litres/Minute";
            actualOutputVal.setText(val);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(BoomStep7Activity.this, SprayActivity6_CalibratedJug.class);
        startActivity(i);
    }
}

