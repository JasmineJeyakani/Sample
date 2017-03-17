
package com.bayer.turfid;

import com.bayer.turfid.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.style.TtsSpan;
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

public class BoomStep2Activity extends Activity {
    TextView knapsacktxt;
    Button continuebtn;
    EditText knapsaceSecVal;
    SharedPreferences knapsackval;
    SharedPreferences.Editor editor;
    Double knapsackvar;
    ImageView imgLogo;
    ImageView backArrow;
    DecimalFormat df = new DecimalFormat("####0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boom_step2);
        knapsacktxt = (TextView) findViewById(R.id.kanpsacktxt);
        continuebtn = (Button) findViewById(R.id.continuebtn2);
        knapsaceSecVal = (EditText) findViewById(R.id.knapsackSec);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        backArrow = (ImageView) findViewById(R.id.leftarrowknapsack);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        knapsaceSecVal.setOnEditorActionListener(new BoomStep2Activity.DoneOnEditorActionListener());
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BoomStep2Activity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
                finish();

            }
        });


        knapsackval = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        editor = knapsackval.edit();

        knapsaceSecVal.setText("");
        if (!knapsackval.getBoolean("in3", false)) {

            String val = knapsackval.getString("knapSackSecVal", String.valueOf(0));
            knapsaceSecVal.setText(String.valueOf(Math.round(Double.valueOf(val))) + " Seconds");
        } else {
            knapsaceSecVal.setText("");
//
        }


        knapsaceSecVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                knapsaceSecVal.setError(null);
                if (knapsaceSecVal.getText().toString().endsWith(" Seconds")) {

                    String val = knapsaceSecVal.getText().toString();
                    String[] valsep = val.split(" Seconds");
                    String knap = String.valueOf(Math.round(Double.valueOf(valsep[0])));

                    knapsaceSecVal.setText(knap);
                    knapsaceSecVal.setSelection(knap.length());

                } else {

                }
            }


        });


        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent productlabel = new Intent(BoomStep2Activity.this, SprayActivity3_Productlabel.class);

                if (knapsaceSecVal.getText().toString().equals("")) {
                    knapsaceSecVal.setError("Enter the value");
                }

                else {
                    String tempVal = knapsaceSecVal.getText().toString();
                    String sep[] = tempVal.split("S");
                    knapsackvar = Double.valueOf(sep[0]);

                    if (String.valueOf(Math.round(knapsackvar)).equals("0")) {
                        knapsaceSecVal.setText("");
                    } else {
                        editor.putString("knapSackSecVal", String.valueOf(knapsackvar));
                        editor.commit();

                        startActivity(productlabel);
                    }


                }


            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(BoomStep2Activity.this, SprayCalculationActivity.class);
        editor = knapsackval.edit();
        editor.putBoolean("in10", false);
        editor.commit();
        startActivity(i);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(knapsaceSecVal.getWindowToken(), 0);
        if (knapsaceSecVal.getText().toString().contains("Seconds")) {


        } else if (knapsaceSecVal.getText().toString().equals("")) {
            knapsaceSecVal.setText("");
        } else {
            String val = knapsaceSecVal.getText().toString() + " Seconds";
            knapsaceSecVal.setText(val);
        }

        return super.onTouchEvent(event);
    }

    private class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (knapsaceSecVal.getText().toString().contains("Seconds")) {


                } else if (knapsaceSecVal.getText().toString().equals("")) {
                    knapsaceSecVal.setText("");
                } else {
                    String val = knapsaceSecVal.getText().toString() + " Seconds";
                    knapsaceSecVal.setText(val);
                }
                return true;
            }
            return false;
        }

    }
}
