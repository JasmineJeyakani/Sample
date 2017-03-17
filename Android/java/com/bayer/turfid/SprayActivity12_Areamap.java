package com.bayer.turfid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class SprayActivity12_Areamap extends Activity {
    Button continue12ButtonAreamap;
    LinearLayout manulMapping;
    TextView productlabel1;
    EditText areaVal;
    SharedPreferences areaval;
    SharedPreferences.Editor editor;
    Double areavar;
    ImageView backArrow, imgLogo;
    DecimalFormat df = new DecimalFormat("####0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spray_activity10__areamap);

        continue12ButtonAreamap = (Button) findViewById(R.id.continue12Btn);
        areaVal = (EditText) findViewById(R.id.areaTxt);
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
                Intent intent = new Intent(SprayActivity12_Areamap.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });
        productlabel1 = (TextView) findViewById(R.id.productlabel);
        areaval = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        String type = areaval.getString("sprayerType", "default");
        if (type.equals("boom")) {
            CustomTextViewBold t = (CustomTextViewBold) findViewById(R.id.step2);
            t.setText(R.string.boom_step_12);
            String sourceString = "<b> Enter the area to be treated</b> in square metres.";
            productlabel1.setText(Html.fromHtml(sourceString));
        }


        areaVal.setText("");
        if (!areaval.getBoolean("in8", false)) {

            String val = areaval.getString("areaVal", String.valueOf(0));
            if (val == null) {
                areaVal.setText("");
            } else {
                areaVal.setText(String.valueOf(df.format(Double.valueOf(val))) + " Sq.Metres");

                SharedPreferences.Editor editor = areaval.edit();
                editor.putBoolean("in8", true);
                editor.commit();
            }
        } else {
            areaVal.setText("");
//
        }


        areaVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (areaVal.getText().toString().endsWith(" Sq.Metres")) {
                    String val = areaVal.getText().toString();
                    String[] valsep = val.split(" Sq.Metres");
                    String knap = valsep[0];
                    if (knap.endsWith(".00")) {
                        areaVal.setText(knap);
                        areaVal.setSelection(knap.length());
                    } else {
                        areaVal.setText(knap);
                        areaVal.setSelection(areaVal.getText().length());
                    }

                } else {

                }
            }


        });

        areaVal.setOnEditorActionListener(new DoneOnEditorActionListener());
        Bundle b = getIntent().getExtras();
        if (b != null) {
            Float val = b.getFloat("areaValue");
            areaVal.setText(String.valueOf(val) + " Sq.Metres");

        }
        continue12ButtonAreamap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent result4 = new Intent(SprayActivity12_Areamap.this, SprayActivity_Result4.class);
                if (areaVal.getText().toString().equals("")) {
                    areaVal.setError("Enter the value");
                } else {
                    String tempVal = areaVal.getText().toString();
                    String sep[] = tempVal.split("S");

                    areavar = Double.valueOf(sep[0]);

                    if (areavar == 0.1) {
                        editor = areaval.edit();
                        editor.putString("areaVal", String.valueOf(areavar));
                        editor.commit();
                        startActivity(result4);
                    } else if (areavar == 0.2) {
                        editor = areaval.edit();
                        editor.putString("areaVal", String.valueOf(areavar));
                        editor.commit();
                        startActivity(result4);
                    } else if (areavar == 0.3) {
                        editor = areaval.edit();
                        editor.putString("areaVal", String.valueOf(areavar));
                        editor.commit();
                        startActivity(result4);
                    } else if (areavar == 0.4) {
                        editor = areaval.edit();
                        editor.putString("areaVal", String.valueOf(areavar));
                        editor.commit();
                        startActivity(result4);
                    } else if (Math.round(areavar) == 0) {
                        areaVal.setText("");
                    } else {

                        editor = areaval.edit();
                        editor.putString("areaVal", String.valueOf(areavar));
                        editor.commit();
                        startActivity(result4);


                    }


                }
            }


        });
        manulMapping = (LinearLayout) findViewById(R.id.mapBtn);
        manulMapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
                ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() == true) {

                    Intent i = new Intent(SprayActivity12_Areamap.this, MappingActivityCalc.class);
                    startActivity(i);
                } else {
                    Toast.makeText(SprayActivity12_Areamap.this, "Please make sure you have connected with internet", Toast.LENGTH_SHORT).show();
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
                if (areaVal.getText().toString().contains("Sq.Metres")) {

                } else if (areaVal.getText().toString().equals("")) {
                    areaVal.setText("");
                }

                else {
                    String val = areaVal.getText().toString() + " Sq.Metres";
                    areaVal.setText(val);
                }

                return true;
            }
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(areaVal.getWindowToken(), 0);
        if (areaVal.getText().toString().contains(" Sq.Metres")) {

        } else if (areaVal.getText().toString().equals("")) {
            areaVal.setText("");
        }

        else {
            String val = areaVal.getText().toString() + " Sq.Metres";
            areaVal.setText(val);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SprayActivity12_Areamap.this, SprayActivity11_Productlabel.class);
        editor = areaval.edit();
        editor.putBoolean("in11", false);
        editor.putString("areaVal", String.valueOf(areavar));
        editor.commit();
        startActivity(i);
    }
}
