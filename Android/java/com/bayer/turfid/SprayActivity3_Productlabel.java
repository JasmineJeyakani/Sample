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

public class SprayActivity3_Productlabel extends Activity {
    TextView productlabelnote;
    Button productcontinue;
    EditText productLabelVal;
    SharedPreferences productlabelval;
    SharedPreferences.Editor editor;
    Double productLabelvar;
    ImageView imgLogo;
    int waterVol;
    ImageView backArrow;
    DecimalFormat df = new DecimalFormat("####0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spray_activity3__productlabel);
        productcontinue = (Button) findViewById(R.id.productLabelContinue);
        productLabelVal = (EditText) findViewById(R.id.productLabelVal);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        productLabelVal.setOnEditorActionListener(new DoneOnEditorActionListener());
        productlabelval = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        waterVol = productlabelval.getInt("prewaterVol", 500);
        if (!productlabelval.getBoolean("prePopulate", true)) {
            productLabelVal.setText("");
            editor = productlabelval.edit();
            editor.putBoolean("prePopulate", true);
            editor.commit();
        } else {
            if (!productlabelval.getBoolean("inres1", false)) {
                String val = productlabelval.getString("ProductLabelVal", String.valueOf(df.format(20)));
                String val2 = df.format(Double.parseDouble(val));
                if (val2.endsWith(".00")) {
                    productLabelVal.setText(Math.round(Double.parseDouble(val2)) + " L/Ha");
                } else {
                    productLabelVal.setText(df.format(Double.parseDouble(val)) + " L/Ha");
                }
            } else {
                String val2 = df.format(Double.parseDouble(String.valueOf(waterVol)));
                if (val2.endsWith(".00")) {
                    productLabelVal.setText(String.valueOf(Math.round(Double.parseDouble(String.valueOf(waterVol)))) + " L/Ha");
                } else {
                    productLabelVal.setText(String.valueOf(df.format(Double.parseDouble(String.valueOf(waterVol)))) + " L/Ha");
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
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SprayActivity3_Productlabel.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });

        String type = productlabelval.getString("sprayerType", "default");
        if (type.equals("boom")) {
            CustomTextViewBold t = (CustomTextViewBold) findViewById(R.id.step3);
            t.setText(R.string.boom_step_3);
        }


        productLabelVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productLabelVal.setError(null);

                if (productLabelVal.getText().toString().endsWith(" L/Ha")) {
                    String val = productLabelVal.getText().toString();
                    String[] valsep = val.split(" L/Ha");
                    String knap = valsep[0];
                    if (knap.endsWith(".00")) {


                        productLabelVal.setText(knap);
                        productLabelVal.setSelection(knap.length());
                    } else {
                        productLabelVal.setText(knap);
                        productLabelVal.setSelection(productLabelVal.getText().length());
                    }
                } else {

                }

            }


        });


        productcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent holdingSpray = new Intent(SprayActivity3_Productlabel.this, SprayActivity4_holdingspray.class);
                if (productLabelVal.getText().toString().equals("")) {
                    productLabelVal.setError("Enter value");
                } else {
                    String tempVal = productLabelVal.getText().toString();
                    String sep[] = tempVal.split("L");
                    productLabelvar = Double.valueOf(sep[0]);
                    if (productLabelvar == 0.1) {
                        editor = productlabelval.edit();
                        editor.putString("ProductLabelVal", String.valueOf(productLabelvar));
                        editor.commit();
                        startActivity(holdingSpray);
                    } else if (productLabelvar == 0.2) {
                        editor = productlabelval.edit();
                        editor.putString("ProductLabelVal", String.valueOf(productLabelvar));
                        editor.commit();
                        startActivity(holdingSpray);
                    } else if (productLabelvar == 0.3) {
                        editor = productlabelval.edit();
                        editor.putString("ProductLabelVal", String.valueOf(productLabelvar));
                        editor.commit();
                        startActivity(holdingSpray);
                    } else if (productLabelvar == 0.4) {
                        editor = productlabelval.edit();
                        editor.putString("ProductLabelVal", String.valueOf(productLabelvar));
                        editor.commit();
                        startActivity(holdingSpray);
                    } else if (Math.round(productLabelvar) == 0) {
                        productLabelVal.setText("");
                    } else {
                        editor = productlabelval.edit();
                        editor.putString("ProductLabelVal", String.valueOf(productLabelvar));
                        editor.commit();
                        startActivity(holdingSpray);
                    }
//                    if(android.os.Build.VERSION.SDK_INT <= 10)
//                    {
//                        parentActivity.startChildActivity("HomeActivity", homeActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                    }else
//                    {
//                        parentActivity.startChildActivity("HomeActivity", homeActivity);
//                    }

                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SprayActivity3_Productlabel.this, SprayActivity2_knapsack.class);
        editor = productlabelval.edit();
        editor.putBoolean("in3", false);
        editor.commit();
        startActivity(i);
    }

    private class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (productLabelVal.getText().toString().contains(" L/Ha")) {


                } else if (productLabelVal.getText().toString().equals("")) {
                    productLabelVal.setText("");
                } else {
                    String val = productLabelVal.getText().toString() + " L/Ha";
                    productLabelVal.setText(val);
                }
                return true;
            }
            return false;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(productLabelVal.getWindowToken(), 0);
        if (productLabelVal.getText().toString().contains(" L/Ha")) {


        } else if (productLabelVal.getText().toString().equals("")) {
            productLabelVal.setText("");
        } else {
            String val = productLabelVal.getText().toString() + " L/Ha";
            productLabelVal.setText(val);
        }
        return super.onTouchEvent(event);
    }
}
